/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.mail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Session;
import javax.mail.Transport;

/**
 *
 * @author David
 */
public class MailAccount {

    private String SMTP_HOST_NAME;
    private int SMTP_HOST_PORT = 465;
    private String SMTP_AUTH_USER;
    private String SMTP_AUTH_PWD;

    public boolean isValid(String EmailAddress, String Password) {
        Pattern p = Pattern.compile("@[1-9a-z]*");
        Matcher m = p.matcher(EmailAddress);

        String host = "";

        if (m.find()) {
            host = m.group().substring(1);
        }
        SMTP_HOST_NAME = "smtp.gmail.com";
        SMTP_AUTH_USER = EmailAddress;
        SMTP_AUTH_PWD = Password;

        /*switch (host) {
            case "kpn":
                SMTP_HOST_NAME = "smtp.kpnmail.com";
                SMTP_HOST_PORT = 25;
                break;
            case "hotmail":
                SMTP_HOST_NAME = "smtp.live.com";
                SMTP_HOST_PORT = 25;
                break;
        }*/
        
        try {
            test();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void test() throws Exception {
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");

        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
        transport.close();
    }
}
