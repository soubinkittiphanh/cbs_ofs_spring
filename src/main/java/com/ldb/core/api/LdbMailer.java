// package com.ldb.core.api;

// import java.net.PasswordAuthentication;
// import java.util.Properties;

// import javax.websocket.Session;

// public class LdbMailer {
    
//     public void mail_new(String from, String to,String sub,String msg) throws AddressException, MessagingException, IOException {

//         final String username = "malaythip@ldblao.la";
//         //final String password = "XXXXXXXXXXXX";
//         final String password = "";

//         Properties prop = new Properties();
//         prop.put("mail.smtp.host", "gw1.security-gateway.net"); // ldb-ftm.ldblao.la | smtp.gmail.com
//         prop.put("mail.smtp.port", "587"); //  | 25 | 587


//         Session session = Session.getInstance(prop,
//                 new javax.mail.Authenticator() {
//                     protected PasswordAuthentication getPasswordAuthentication() {
//                         return new PasswordAuthentication(username, password);
//                     }
//                 });

//         try {

//             Message message = new MimeMessage(session);
//             message.setFrom(new InternetAddress(from));
//             message.setRecipients(
//                     Message.RecipientType.TO,
//                     //InternetAddress.parse("vixayonh@ldblao.la, vixayonh@gmail.com")
//                     InternetAddress.parse("vixayonh@gmail.com")
//             );
//             message.setSubject("Testing inside TLS 123456");


//             //3) create MimeBodyPart object and set your message text
//             BodyPart messageBodyPart1 = new MimeBodyPart();
//             messageBodyPart1.setText("This is message body " + "\n\n Please do not spam my email! 123456");

//             //4) create new MimeBodyPart object and set DataHandler object to this object
//             MimeBodyPart messageBodyPart2 = new MimeBodyPart();

//             String filename = "test.txt";//change accordingly
//             DataSource source = new FileDataSource(filename);
//             messageBodyPart2.setDataHandler(new DataHandler(source));
//             messageBodyPart2.setFileName(filename);


//             //5) create Multipart object and add MimeBodyPart objects to this object
//             Multipart multipart = new MimeMultipart();
//             multipart.addBodyPart(messageBodyPart1);
//             multipart.addBodyPart(messageBodyPart2);

//             //6) set the multiplart object to the message object
//             message.setContent(multipart);



//             //message.setText("Dear Mail Crawler," + "\n\n Please do not spam my email! 123456");

//             Transport.send(message);

//             System.out.println("Done");

//         } catch (MessagingException e) {
//             e.printStackTrace();
//         }


//     }


//     public void sendmail_SIMPLE(String from, String toss, String subject, String body) throws AddressException, MessagingException, IOException {
//         Properties propertie = new Properties();
//         propertie.put("mail.smtp.auth", "false");
//         propertie.put("mail.smtp.starttls.enable", "true");
//         propertie.put("mail.smtp.host", globalProperties.getMail_host());
//         propertie.put("mail.smtp.port", globalProperties.getMail_port());
//         //propertie.put("mail.smtp.ssl.enable", "true");
//         //propertie.put("mail.smtp.ssl.trust", globalProperties.getMail_host());

//         Session session = Session.getInstance(propertie, new javax.mail.Authenticator() {
//             protected PasswordAuthentication getPasswordAuthentication() { //vixayonh@ldblao.la //globalProperties.getMail_username()
//                 return new PasswordAuthentication(globalProperties.getMail_username(), "Yonh@1234");
//             }
//         });

//         Message msg = new MimeMessage(session);
//         msg.setFrom(new InternetAddress(from, false));


//         msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toss));
//         //msg.addRecipient(Message.RecipientType.CC, new InternetAddress("vixayonh@ldblao.la"));
//         msg.setSubject(subject);
//         msg.setContent(body, "text/html");
//         msg.setSentDate(new Date());

//         // creates message part
//         MimeBodyPart messageBodyPart = new MimeBodyPart();
//         messageBodyPart.setContent(body, "text/html");

//         // creates multi-part
//         Multipart multipart = new MimeMultipart();
//         multipart.addBodyPart(messageBodyPart);

//         // adds attachments
//         /*
//         if (fileToAttach != null && fileToAttach.length > 0) {
//             for (String filePath : fileToAttach) {
//                 MimeBodyPart attachPart = new MimeBodyPart();

//                 try {
//                     attachPart.attachFile(filePath);
//                 } catch (IOException ex) {
//                     ex.printStackTrace();
//                 }

//                 multipart.addBodyPart(attachPart);
//             }
//         }
//         */

//         // sets the multi-part as e-mail's content
//         msg.setContent(multipart);

//         // sends the e-mail
//         Transport.send(msg);

//     }
// }
