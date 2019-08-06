package com.sopen.controller.gmail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

public class SendGmail_controller extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Wire
	@Wire
	Button btnSubmit;
	@Wire
	Textbox txtYourName, txtPhone, txtBEmail, txtBMemo;
	@Wire
	Intbox intBAge;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@Listen("onClick = #btnSubmit")
	public void submit() throws AddressException, MessagingException {
		EntityGmail entityG = new EntityGmail();
		entityG.setName(txtYourName.getValue());
		entityG.setAge(intBAge.getValue());
		entityG.setPhone(txtPhone.getValue());
		entityG.setEmail(txtBEmail.getValue());
		entityG.setMemo(txtBMemo.getValue());
		sendText(entityG);
		alert("Ban da gui thanh cong");
	}

	public static void sendText(EntityGmail entityG) throws AddressException, MessagingException {
		Properties mailServerProperties;
		Session getMailSession;
		MimeMessage mailMessage;

		// Step1: setup Mail Server
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");

		// Step2: get Mail Session
		// new Authenticator() {
//		@Override
//		protected PasswordAuthentication getPasswordAuthentication() {
//			return new PasswordAuthentication("sontungvatm@gmail.com", "sontung1695");
//		}
//	}
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		mailMessage = new MimeMessage(getMailSession);

		mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("sontung1695@gmail.com")); // Thay abc
																											// bằng địa
		// chỉ người nhận

		// Bạn có thể chọn CC, BCC
//    generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("cc@gmail.com")); //Địa chỉ cc gmail

		mailMessage.setSubject("Phan hoi cua nguoi dung");
		mailMessage.setText(entityG.toString());

		// Step3: Send mail
		Transport transport = getMailSession.getTransport("smtp");

		// Thay your_gmail thành gmail của bạn, thay your_password thành mật khẩu gmail
		// của bạn
		transport.connect("smtp.gmail.com", "sontungvatm@gmail.com", "sontung1695");
		transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
		transport.close();
	}
}
