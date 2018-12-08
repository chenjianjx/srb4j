package com.github.chenjianjx.srb4jfullsample.impl.itcase.support.mail;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by chenjianjx@gmail.com on 10/11/18.
 */
public class DummyMailSender implements MailSender {
    private static Logger logger = LoggerFactory.getLogger(DummyMailSender.class);

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        logger.info("Mail sent. The message is " + simpleMessage);
    }

    @Override
    public void send(SimpleMailMessage[] simpleMessages) throws MailException {
        if (simpleMessages == null || simpleMessages.length == 0) {
            logger.info("No message is sent since simpleMessages is empty");
        }
        for (SimpleMailMessage msg : simpleMessages) {
            logger.info("Mail sent. The message is " + msg);
        }

    }
}
