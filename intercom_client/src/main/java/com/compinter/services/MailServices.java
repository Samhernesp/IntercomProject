package com.compinter.services;


import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class MailServices {

    private static String emailFrom = "compuinternet01@gmail.com";
    private static String passwordFrom = "nyxgxpcajcogpyoe";
    private String emailTo;
    private String subject;
    private String content;

    private Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;

    public MailServices() {
        mProperties = new Properties();
    }

    //Crea un Email con un mail que tiene como parámetros correo destino y numero de apartamento
    private void createEmail(String mailTo, int number) {
        emailTo = mailTo.trim();
        subject = "¡Alerta de seguridad!";
        content = "Eres el contacto de emergencia del apartamento #" + number +
         ". Te notificamos que se encuentra en peligro y ha presionado su botón de pánico";
        
        // Protocolo simple de transferencia de correo SMTP
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user",emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");
        
        mSession = Session.getDefaultInstance(mProperties);
        
        
        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mCorreo.setSubject(subject);
            mCorreo.setText(content, "ISO-8859-1", "html");
                     
            
        } catch (AddressException ex) {
            Logger.getLogger(MailServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Envía un correo de Alerta a un contacto de enmergencia previamente registrado,
    //al ser pulsado el botón de pánico, y muestra una alerta con el resultado de exito
    //o de error en caso de no completar algun campo.
    public void sendEmail() {
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, passwordFrom);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
            
            JOptionPane.showMessageDialog(null, "Correo enviado");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MailServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Realiza el llamado a crear Email y enviar el Email al destinatario.
    //Tiene como parámetros el Email de enmergencia y el número del apartamento.
	public void createAndSendEmail(String emergencyEmail, int number) {
        createEmail(emergencyEmail, number+1);
        sendEmail();
	}
}
