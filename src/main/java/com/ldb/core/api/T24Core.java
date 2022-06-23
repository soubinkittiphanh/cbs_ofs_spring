package com.ldb.core.api;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class T24Core {
    @Value("${corebank.version.passreset}")
    private String resetPasswordVersion;
    @Value("${corebank.uat.ip}")
    private String ipaddress;
    @Value("${corebank.uat.port}")
    private String port;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private InputStream ins;
    private String ofsResDecode;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private int timeOut=0;

    public T24Core(Socket socket,int timeOut) {
        this.socket=socket;
        this.timeOut=timeOut;
    }

    public String postOfs(String ofsMsg, int timeOut) {
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            ins = socket.getInputStream();
            dis = new DataInputStream(ins);
            byte[] ofsReq = ofsMsg.getBytes("utf-8");
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(bao);
            dataOutputStream.writeInt(ofsReq.length);
            dataOutputStream.flush();
            dataOutputStream.write(ofsReq);
            dataOutputStream.flush();
            ofsReq = bao.toByteArray();
            dos.write(ofsReq);
            dos.flush();
            logger.info("OFS has been sent");
            int timer = 0;
            while (dis.available() == 0 && timer < timeOut) {
                Thread.sleep(500);
                timer += 500;
                logger.info("Waiting for response from T24...");
            }
            logger.info("Total elapsed time is: " + timer);
            if (timer < timeOut) {
                int size = dis.readInt();
                logger.info("Response size is: " + size);
                if (size > 0) {
                    int nSizeRead = 0;
                    byte[] ofsRes = new byte[size];
                    while (size > 0) {
                        byte[] bBuffer = new byte[size];
                        int sizeBuffer = ins.read(bBuffer);
                        System.arraycopy(bBuffer, 0, ofsRes, nSizeRead, sizeBuffer);
                        nSizeRead += sizeBuffer;
                        size -= sizeBuffer;

                    }
                    // Convert string to human language
                    ofsResDecode = new String(ofsRes, "utf-8");
                    // Customer error message for more informative
                    customT24Error(ofsResDecode);
                }
            } else {
                socket = null;
                ofsResDecode = "CONNECTION_ERROR_BTW_T24";
            }
        } catch (ConnectException ce) {
            logger.error("ConnectException caught while sending a package, Hence, trying to reconnect..." + ce);
            socket = null;
            return "CONNECTION_ERROR";
        } catch (SocketException se) {
            System.out
                    .println("Connection Exception caught while sending a package, Hence, trying to reconnect..." + se);
            socket = null;
            return "CONNECTION_ERROR";
        } catch (SocketTimeoutException ste) {
            logger.error(
                    "SocketTimeoutException caught while sending a package, Hence, trying to reconnect..." + ste);
            socket = null;
            return "CONNECTION_ERROR";
        } catch (IOException exe) {
            logger.error("IOException caught while sending a package, Hence, trying to reconnect..." + exe);
            socket = null;
            return "CONNECTION_ERROR";
        } catch (InterruptedException exe) {
            exe.printStackTrace(System.out);
            return "CONNECTION_ERROR";
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    dos = null;
                    dis = null;
                    ins = null;
                    logger.error("Connection added to the pool for re-use.");
                } catch (Exception ex) {
                    logger.error("Exception Caught While adding a connections to the pool." + ex);
                    ex.printStackTrace(System.out);
                }
            }

        }
        closeT24Socket();
        return ofsResDecode;
    }

    private void customT24Error(String erCode) {

        if (ofsResDecode == null) {
            logger.error("No Response from T24");
            ofsResDecode = "NO_REPONSE";
        } else {
            switch (erCode) {
                case "OFSERROR_TIMEOUT":
                    ofsResDecode = "TIME_OUT";
                    break;
                case "INVALID/ NO SIGN ON NAME SUPPLIED DURING SIGN ON PROCESS":
                    ofsResDecode = "INVALID_LOGIN";
                    break;
                case "SECURITY VIOLATION DURING SIGN ON PROCESS":
                    ofsResDecode = "SECURITY VIOLATION";
                    break;
                default:
                    ofsResDecode = erCode;

            }
            ;
        }
    }

    private void closeT24Socket() {
        try {
            logger.info("T24 Connection closed");
            socket.close();
        } catch (IOException ex) {
            logger.error("Cannot close socket " + ex);
        }
    }
}
