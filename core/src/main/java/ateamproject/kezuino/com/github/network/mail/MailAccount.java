/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.mail;

import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

/**
 *
 * @author David
 */
public class MailAccount {

    public static boolean isValid(String EmailAddress, String Password) {
        String SMTP_HOST_NAME = "smtp.gmail.com";
        int SMTP_HOST_PORT = 465;
        String SMTP_AUTH_USER = EmailAddress;
        String SMTP_AUTH_PWD = Password;

        try {
            Properties props = new Properties();

            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.host", SMTP_HOST_NAME);
            props.put("mail.smtps.auth", "true");

            Session mailSession = Session.getDefaultInstance(props);
            mailSession.setDebug(false);
            Transport transport = mailSession.getTransport();

            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
            transport.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
