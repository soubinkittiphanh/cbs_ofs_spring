package com.ldb.core.service;

import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void sendEmail() {
        logger.info("Sending mail.....");
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("billboyslm@gmail.com", "kittiphanhsoubin@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);

    }

    public void sendEmailWithAttachment() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("billboyslm@gmail.com");

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        // helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

        // FileSystemResource file = new FileSystemResource(new
        // File("classpath:android.png"));

        // Resource resource = new ClassPathResource("android.png");
        // InputStream input = resource.getInputStream();

        // ResourceUtils.getFile("classpath:android.png");

        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }

    public void mail_new(String from, String to, String sub, String msg)
            throws AddressException, MessagingException, IOException {

        final String username = "soubin@ldblao.la";
        // final String password = "XXXXXXXXXXXX";
        final String password = "ikmOL,@123";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "gw1.security-gateway.net"); // ldb-ftm.ldblao.la | smtp.gmail.com
        prop.put("mail.smtp.port", "587"); // | 25 | 587

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    // InternetAddress.parse("vixayonh@ldblao.la, vixayonh@gmail.com")
                    InternetAddress.parse("kittiphanhsoubin@gmail.com"));
            message.setSubject("Testing inside TLS 123456");

            // 3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("This is message body " + "\n\n Please do not spam my email! 123456");

            // 4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            // String filename = "test.txt";//change accordingly
            // DataSource source = new FileDataSource(filename);
            // messageBodyPart2.setDataHandler(new DataHandler(source));
            // messageBodyPart2.setFileName(filename);

            // 5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            // 6) set the multiplart object to the message object
            message.setContent(multipart);

            // message.setText("Dear Mail Crawler," + "\n\n Please do not spam my email!
            // 123456");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
