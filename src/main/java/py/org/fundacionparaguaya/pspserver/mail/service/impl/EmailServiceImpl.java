package py.org.fundacionparaguaya.pspserver.mail.service.impl;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.mail.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSenderImpl mailSender;

    public EmailServiceImpl(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {

            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(mail);
        } catch (Exception exception) {
            throw new CustomParameterizedException(
                    "Mail server connection failed");
        }
    }

    @Override
    public void sendSimpleMessageUsingTemplate(String to, String subject,
            SimpleMailMessage template, String... templateArgs) {
        String text = String.format(template.getText(), templateArgs);
        sendSimpleMessage(to, subject, text);
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject,
            String text, String pathToAttachment) {
        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(
                    new File(pathToAttachment));
            helper.addAttachment("File", file);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new CustomParameterizedException("Error sending email");
        }

    }

}