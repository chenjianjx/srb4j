package com.github.chenjianjx.srb4jfullsample.impl.support.mail;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.github.chenjianjx.srb4jfullsample.impl.support.template.FreemarkerTemplateFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service
public class MailEngine {

	private static final Logger logger = LoggerFactory
			.getLogger(MailEngine.class);

	private static final Logger smtpLogger = LoggerFactory
			.getLogger("smtpLogger");

	private final FreemarkerTemplateFactory templateFactory = FreemarkerTemplateFactory
			.getInstance();

	@Resource
	MailSender mailSender;

	private ExecutorService sendingThreadPool;

	@PostConstruct
	public void init() {
		sendingThreadPool = Executors.newCachedThreadPool();
	}

	public void sendMessageAsync(final SimpleMailMessage msg,
			final String templateName, final Map<String, Object> model)
			throws MailException {
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					sendMessage(msg, templateName, model);
					smtpLogger
							.info("Mail successfully sent to " + msg.getTo()[0]
									+ " regarding " + msg.getSubject());
				} catch (Exception e) {
					String errMsg = "fail to send email to " + msg.getTo()[0]
							+ " regarding " + msg.getSubject();
					smtpLogger.error(errMsg);
					logger.error(errMsg, e);
				}
			}

		};
		sendingThreadPool.submit(task);

	}

	/**
	 * Send a simple message based on a Freemarker template.
	 * 
	 * @param msg
	 *            the message to populate
	 * @param templateName
	 *            the Freemarker template to use (relative to classpath)
	 * @param model
	 *            a map containing key/value pairs
	 */
	public void sendMessage(SimpleMailMessage msg, String templateName,
			Map<String, Object> model) throws MailException {

		Template template = templateFactory.getTemplate(templateName);

		String result = render(model, template);

		msg.setText(result);
		send(msg);
	}

	/**
	 * Send a simple message with pre-populated values.
	 * 
	 * @param msg
	 *            the message to send
	 * @throws org.springframework.mail.MailException
	 *             when SMTP server is down
	 */
	private void send(SimpleMailMessage msg) throws MailException {
		mailSender.send(msg);

	}

	/**
	 * Convenience method for sending messages with attachments.
	 *
	 * @param recipients
	 *            array of e-mail addresses
	 * @param sender
	 *            e-mail address of sender
	 * @param resource
	 *            attachment from classpath
	 * @param bodyText
	 *            text in e-mail
	 * @param subject
	 *            subject of e-mail
	 * @param attachmentName
	 *            name for attachment
	 * @throws MessagingException
	 *             thrown when can't communicate with SMTP server
	 */
	@SuppressWarnings("unused")
	private void sendMessage(String[] recipients, String sender,
			ClassPathResource resource, String bodyText, String subject,
			String attachmentName) throws MessagingException {
		MimeMessage message = ((JavaMailSenderImpl) mailSender)
				.createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(recipients);

		helper.setText(bodyText);
		helper.setSubject(subject);

		helper.addAttachment(attachmentName, resource);

		((JavaMailSenderImpl) mailSender).send(message);
	}

	private String render(Map<String, Object> model, Template template) {
		StringWriter out = new StringWriter();
		try {
			template.process(model, out);
		} catch (TemplateException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		String result = out.toString();
		return result;

	}
}
