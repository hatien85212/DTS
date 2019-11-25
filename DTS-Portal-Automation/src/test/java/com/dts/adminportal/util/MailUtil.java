package com.dts.adminportal.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.Assert;

import dts.com.adminportal.model.Constant;

public class MailUtil {
	private static Result result = new Result();
	
	public static String getNewEmailContent(String host, String username, String password){
		Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(host, username, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message msg = inbox.getMessage(inbox.getMessageCount());
            Object body = msg.getContent();
            String content;
            if(body instanceof String){
            	content = (String) body;
            }else{
            	Multipart mp = (Multipart) body;
                BodyPart bp = mp.getBodyPart(0);
                content = (String) bp.getContent();
                
            }
          //close the store and folder objects
            inbox.close(false);
            store.close();
            return content;
            
        } catch (MessagingException mex) {
            result.addErrorLog("Mail exception! Cannot get the email content from server");
            Assert.assertTrue(false);
            return null;
        } catch (IOException e){
        	result.addErrorLog("Cannot get the email content from server");
        	Assert.assertTrue(false);
            return null;
        }
	}
	
	public static String getLinkActive(String host, String username,
			String password) {
		try {
			String site = null;
			String emailContent = MailUtil.getNewEmailContent(host, username,
					password);
			if (emailContent.contains("reset")) {
				site = "https://devportal.dts.com/saap";
			} else {
				site = "http://devportal.dts.com/saap";
			}
			int begin_index = emailContent.indexOf(site);
			String link = emailContent.substring(begin_index,
					emailContent.indexOf("\"", begin_index));
			result.addLog("Link active for user: " + link);
			return link;
		} catch (IndexOutOfBoundsException e) {
			result.addErrorLog("Link active not found");
			Assert.assertTrue(false);
			return null;
		}
	}
	
	public static int getEmailMessageCount(String host, String username, String password){
		Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(host, username, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();
          //close the store and folder objects
            inbox.close(false);
            store.close();
            result.addLog("Total email: " + messageCount);
            return messageCount;
            
        } catch (MessagingException mex) {
            result.addErrorLog("Mail exception! Cannot get the email message from server");
            Assert.assertTrue(false);
            return -1;
        }
	}
	
	public static void waitForNewInboxMessage(String host, String username, String password, int currentMessage) {
		Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(host, username,password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            // Wait for new message sent to inbox folder
            int time = 0;
    		result.addLog("Total message: " + currentMessage);
            while(time/1000 < Constant.TIME_WAIT * 10){
            	System.err.println("Wait for new email...");
            	Thread.sleep(Constant.TIME_SLEEP);
            	time = time + Constant.TIME_SLEEP;
            	inbox.close(false);
            	inbox.open(Folder.READ_ONLY);
            	if(inbox.getMessageCount() > currentMessage){
            		result.addLog("Total message: " + inbox.getMessageCount());
            		result.addLog("New email found");
            		inbox.close(false);
            		store.close();
            		return;
            	}
            }
            //close the store and folder objects
            result.addErrorLog("New email is still not sent to inbox folder");
            inbox.close(false);
            store.close();
            Assert.assertTrue(false);
            
        } catch (MessagingException mex) {
            result.addErrorLog("Mail exception! Cannot get the email message from server");
            Assert.assertTrue(false);
        } catch (InterruptedException e){
        	result.addErrorLog("Interupt exception");
            Assert.assertTrue(false);
        }
	}
	
	public static String getNewEmailSubject(String host, String username, String password){
		Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(host, username, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message msg = inbox.getMessage(inbox.getMessageCount());
            String subject = msg.getSubject();
          //close the store and folder objects
            inbox.close(false);
            store.close();
            result.addLog("New email subject: " + subject);
            return subject;
            
        } catch (MessagingException mex) {
        	 result.addErrorLog("Mail exception! Cannot get the email message from server");
             Assert.assertTrue(false);
            return null;
        }
	}
	
	public static void main(String[] args) throws InterruptedException{
		String content = getNewEmailContent("imap.mail.yahoo.com", "pdtstesting123@yahoo.com", "dtspassword");
		System.out.println(content);
	
	
	}
	
}
