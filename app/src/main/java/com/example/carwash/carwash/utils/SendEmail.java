package com.example.carwash.carwash.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.carwash.carwash.constants.EmailConfig;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by monageng on 2017/05/30.
 */

public class SendEmail extends AsyncTask<Void, Void, Void> {

    //Declaring Variables
    private Context context;
    private Session session;

    //Information to send email
    private String email;
    private String subject;
    private String message;

    private String attachmentPath;

    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public SendEmail(Context context, String email, String subject, String message, String attachmentPath){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.attachmentPath = attachmentPath;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EmailConfig.EMAIL_USERNAME, EmailConfig.EMAIL_PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(EmailConfig.EMAIL_REPLY));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);

            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setText(message);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(bodyPart);

                BodyPart attBody = new MimeBodyPart();
                String fileName = attachmentPath;
                DataSource  dataSource = new FileDataSource(fileName);
                attBody.setDataHandler(new DataHandler(dataSource));
                attBody.setFileName(fileName);
                multipart.addBodyPart(attBody);

                mm.setContent(multipart);
            }

            //Sending email
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
