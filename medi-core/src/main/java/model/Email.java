package model;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {

    // method to send email to the patient using the email address, subject and message content provided as arguments to the method
    public void sendEmail(String toAddress, String subject, String messageContent) {
        final String from = "medi.core.customercare@gmail.com";
        final String password = "eihqtulprgaynasi";

        // setting the properties for the email to be sent using the gmail smtp server and the email address and password of the sender
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // creating a session object to authenticate the sender using the email address and password of the sender and sending the email to the patient using the email address provided
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
           /**
            * creating a message object to set the sender, recipient, subject and message content of the email to be sent to the
            * patient using the email address provided as an argument to the method and sending the email to the patient using the
            * email address provided as an argument to the method using the session object created earlier to authenticate the sender
            * using the email address and password of the sender and sending the email to the patient using the email address provided
            * as an argument to the method and the subject and message content provided as arguments to the method
            * */
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);
            message.setText(messageContent);  // message content to be sent to the patient using the email address provided as an argument to the method and the subject and message content provided as arguments to the method
            Transport.send(message); // sending the email to the patient using the email address provided as an argument to the method and the subject and message content provided as arguments to the method
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
