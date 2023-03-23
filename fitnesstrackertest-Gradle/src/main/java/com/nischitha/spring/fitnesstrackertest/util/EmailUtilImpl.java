package com.nischitha.spring.fitnesstrackertest.util;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtilImpl implements EmailUtil {

	@Autowired
	private JavaMailSender sender;

	@Override
	public void sendEmail(String toAddress, String body, String subject) {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper;
		String fromAddress = "noreply@myfitnessbuddy.com";
		String fromName = "MyFITNESSBUDDY";
		
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			
			String htmlBody="<html><head><style>#socialMedia{text-align:center;}#socialMedia ul{list-style: none;} #socialMedia ul li{display:inline-block;"
					+ "            margin: 25px 15px;} #socialMedia img{width: 40px;"
					+ "            height: 40px;"
					+ "            cursor: pointer;}#mainHeader{text-align:center;}</style></head><body><div id='mainHeader'><h3>Thank you for signing up with MYFITNESSBUDDY!</h3><p>Our goal is to help you track your fitness journey<p><h2>Lets get strarted!</h2></p> <div><img style='height:500px;width:100%;position:relative;' src='cid:BackgroundImage' /> <div class=\"social-media\">\r\n"
					+ "               <div id='socialMedia'> <ul>"
					+ "                    <li>"
					+ "                        <img src='cid:facebookImage'  />"
					+ "                    </li>"
					+ "                    <li>"
					+ "                        <img src='cid:instagramImage'  />"
					+ "                    </li>"
					+ "                    <li>"
					+ "                        <img src='cid:twitterImage' />"
					+ "                    </li>"
					+ "                    <li>"
					+ "                        <img src='cid:linkedinImage'  />"
					+ "                    </li>"
					+ "                </ul></div>"
					+ "            </body></html>";
			
			helper.setText(htmlBody,true);

			helper.addInline("BackgroundImage", new ClassPathResource("/static/FitnessTrackerTest-Images/e11.jpg"));
			helper.addInline("facebookImage", new ClassPathResource("/static/FitnessTrackerTest-Images/facebook.png"));
			helper.addInline("instagramImage", new ClassPathResource("/static/FitnessTrackerTest-Images/instagram.png"));
			helper.addInline("twitterImage", new ClassPathResource("/static/FitnessTrackerTest-Images/twitter.png"));
			helper.addInline("linkedinImage", new ClassPathResource("/static/FitnessTrackerTest-Images/linkedin.png"));
			
			
			InternetAddress from = new InternetAddress(fromAddress, fromName);
			helper.setFrom(from);

		} catch (MessagingException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sender.send(message);

		/*
		 * String htmlBody="<html><body"; 
		 * String imagePath="C:\Users\Dell\Desktop\complete-javascript-course-master\complete-javascript-course-master\PersonalProject\Images\instagram.png";
		 * File imageFile = new File(imagePath); 
		 * String imageResourceName =imageFile.getName();
		 * 
		 * // Add the image to the email body helper.setText("<html><body>" +
		 * "<img style='height:40px;width:40px;' src='cid:" + imageResourceName + "' />"
		 * + body + "</body></html>", true);
		 * 
		 * helper.addInline(imageResourceName, imageFile);
		 * 
		 * 
		 * htmlBody+="</html></body>";
		 * 
		 */

	}

}
