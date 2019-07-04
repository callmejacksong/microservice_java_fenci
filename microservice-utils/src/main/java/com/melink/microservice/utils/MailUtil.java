package com.melink.microservice.utils;

import com.melink.microservice.exception.PlatformException;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;


public class MailUtil {
	private static JavaMailSenderImpl senderImpl;

	private static MailUtil mailUtil;

	private static MimeMessage mailMessage;

	private static MimeMessageHelper messageHelper;

	public static final String FORM = "developer@siyanhui.com";

	public static final String NAME = "developer@siyanhui.com";

	public static final String PASSWORD = "Apphills0818";

	public static final String BCC_PERSON = "cheng.li@dongtu.com";
	
	public static final String PERSONAL =  "表情云开发者";

	public static final String PERSONAL_OPEN =  "动图宇宙";
	
	public static final String EN_PERSONAL =  "Mojif Developer";

	public MailUtil() {
		senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost("smtp.exmail.qq.com");
		senderImpl.setPort(465);
		// 建立邮件消息,发送简单邮件和html邮件的区别
		mailMessage = senderImpl.createMimeMessage();
		try {
			// 设置寄件人
			messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");
//			messageHelper.setFrom(FORM, PERSONAL);
			messageHelper.setBcc(BCC_PERSON);
			senderImpl.setUsername(NAME); // 根据自己的情况,设置username
			senderImpl.setPassword(PASSWORD); // 根据自己的情况, 设置password
			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
			prop.put("mail.smtp.timeout", "25000");
			//开启安全协议
			MailSSLSocketFactory sf = null;
			try {
				sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true);
			} catch (GeneralSecurityException e1) {
				e1.printStackTrace();
			}
			prop.put("mail.smtp.ssl.enable", "true");
			prop.put("mail.smtp.ssl.socketFactory", sf);
			senderImpl.setJavaMailProperties(prop);
		} catch (MessagingException e) {
			throw PlatformException.asRuntimeException(e);
		}
	}

	public static MailUtil newInstance() {
		if (mailUtil == null) {
			mailUtil = new MailUtil();
		}
		return mailUtil;
	}

	public void sendOpenRegisterMail(String to, String appId, String appSecret) {
		try {
			messageHelper.setFrom(FORM, PERSONAL);
			messageHelper.setTo(to);
			messageHelper.setSubject("开始使用表情云SDK | 让开发者轻松拥有表情商店");
			String html="<html><head><base target=\"_blank\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><style type=\"text/css\">::-webkit-scrollbar {display: none;}</style>";
			html+="<style type=\"text/css\">body {margin: 0 auto; padding: 0; font-family: Microsoft Yahei, Tahoma, Arial; color: #333333; background-color: #fff;}";
			html+="a {color: ##36cab6;line-height: 22px;text-decoration: none;}a:hover { text-decoration: underline;color: #36cab6;}td {font-family: 'Microsoft YaHei';}</style></head>";
			html+="<body><table width=\"900\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"font-family: 'Microsoft YaHei';\"><tbody><tr><td>";
			html+="<table width=\"900\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border: 1px solid #edecec; border-top: none; border-bottom: none; padding: 0 20px; font-size: 14px; color: #333333;\"></table>";
			html+="<table width=\"900\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#36cab6\" height=\"70\" style=\"font-family: 'Microsoft YaHei';\"><tbody><tr>";
			html+="<td width=\"74\" height=\"26\" border=\"0\" align=\"center\" valign=\"middle\" style=\"padding-left: 20px;\"><a href=\"http://bqmm.wanganyi.net/\" target=\"_blank\"> <img src=\"http://sdk.static.dongtu.com/register-mail/logo.png\" border=\"0\">";
			html+="</a></td><td width=\"703\" height=\"48\" colspan=\"2\" align=\"right\" valign=\"middle\" style=\"color: #ffffff; padding-right: 20px;\"></td></tr></tbody></table></td></tr><tr><td>";
			html+="<table width=\"900\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border: 1px solid #edecec; border-top: none; border-bottom: none; padding: 0 20px; font-size: 14px; color: #333333;\"></table>";
			html+="<table width=\"900\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border: 1px solid #edecec; border-top: none; border-bottom: none; padding: 0 20px; font-size: 14px; color: #333333;\"><tbody><tr>";
			html+="<td width=\"760\" height=\"46\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">开发者你好，</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">感谢注册表情云SDK，下面是您接入的key:</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\">&nbsp;</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 19px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">AppId:<span style=\"font-size:19px;color:#36cab6;\">"+appId+"</span></td>";
			html+="</tr><tr><td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 19px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">AppSecret:<span style=\"font-size:19px;color:#36cab6;\">"+appSecret+"</span></td>";
			html+="</tr><tr><td width=\"720\" height=\"32\" colspan=\"2\" style=\"padding-left: 40px;\">&nbsp;</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei'; color:#999999\">请妥善保存，谨防泄露。</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei'; color:#999999\">*注意：此key提供";
			html+="<span style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei'; color:red\">30天试用</span>时间，如需继续试用,请联系";
			html+="<span style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei'; color:black\">表情云开发者运营</span></td></tr><tr>";
			html+="<td width=\"720\" height=\"32\" colspan=\"2\" style=\"padding-left: 40px;\">&nbsp;</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei';\"><img src=\"http://sdk.static.dongtu.com/register-mail/qq.png\">  592044922(李诚)</td>";
			html+="<td width=\"160\" colspan=\"1\" rowspan=\"6\"><img src=\"http://sdk.static.dongtu.com/register-mail/showgirl.png\"></td></tr><tr>";
			html+="<td width=\"720\" height=\"20\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: top; font-family: 'Microsoft YaHei';\"><img src=\"http://sdk.static.dongtu.com/register-mail/email.png\">  cheng.li@siyanhui.com</td>";
			html+="</tr><tr><td width=\"720\" height=\"32\" colspan=\"2\" style=\"padding-left: 40px;\">&nbsp;</td></tr><tr>";
			html+="<td width=\"50\" height=\"30\" border=\"0\" align=\"left\" colspan=\"1\" style=\"font-size: 19px; vertical-align: top; font-family: 'Microsoft YaHei';\"> <img src=\"http://sdk.static.dongtu.com/register-mail/developerqr.png\"></td>";
			html+="<td width=\"500\" height=\"30\" border=\"0\" align=\"left\" colspan=\"1\" style=\"font-size: 19px; vertical-align: bottom; font-family: 'Microsoft YaHei';\"> ";
			html+="<a href=\"http://biaoqingmm.com/download.html\" style=\"color: #36cab6;\">> 下载SDK</a><br /><a href=\"http://open.biaoqingmm.com/doc/sdk/\" style=\"color: #36cab6;\">> 查看接入文档</a></td></tr>";
			html+="<tr><td width=\"50\" height=\"32\" colspan=\"1\" style=\"font-size: 19px; vertical-align: left; font-family: 'Microsoft YaHei';\">开发者交流群</td></tr><tr>";
			html+="<td width=\"720\" height=\"32\" colspan=\"2\" align=\"center\" style=\"padding-left:190px; color: #7f7f7f;font-size: 14px; \">Copyright © 2014-2016 www.biaoqingmm.com All Rights Reserved.</td></tr></tbody></table></td>";
			html+="</tr></tbody></table><table width=\"900\" height=\"18\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr>";
			html+="<td width=\"900\" height=\"18\" align=\"center\" valign=\"middle\"><img border=\"0\" height=\"18\" src=\"http://sdk.static.dongtu.com/register-mail/foot.png\" width=\"900px\"></td></tr></tbody></table>";
			html+="<style type=\"text/css\">body {font-size: 14px;font-family: arial, verdana, sans-serif;line-height: 1.666;padding: 0;margin: 0;overflow: auto;white-space: normal;word-wrap: break-word;min-height: 100px}";
			html+="td, input, button, select, body {font-family: Helvetica, 'Microsoft Yahei', verdana}";
			html+="pre {white-space: pre-wrap;white-space: -moz-pre-wrap;white-space: -pre-wrap;white-space: -o-pre-wrap;word-wrap: break-word;width: 95%}";
			html+="th, td {font-family: arial, verdana, sans-serif;line-height: 1.666}img {border: 0}";
			html+="header, footer, section, aside, article, nav, hgroup, figure, figcaption{display: block}</style></body></html>";
			// true 表示启动HTML格式的邮件
			messageHelper.setText(html, true);
			messageHelper.setSentDate(new Date());
			senderImpl.send(mailMessage);
		} catch (MessagingException e) {
			throw PlatformException.asRuntimeException(e);
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendOpenRegisterEnMail(String to,String name, String appId, String appSecret) {
		try {
		    messageHelper.setFrom(FORM, EN_PERSONAL);
			messageHelper.setTo(to);
			messageHelper.setSubject("Your Mojif Sticker SDK key");
			String html="<html><head><base target=\"_blank\"><meta http-equiv=\"Content-Type\" content=\"text/html;\"><style type=\"text/css\">::-webkit-scrollbar {display: none;}</style>";
			html+="<style type=\"text/css\">body {margin: 0 auto; padding: 0; font-family: Microsoft Yahei, Tahoma, Arial; color: #333333; background-color: #fff;}";
			html+="a {color: ##36cab6;line-height: 22px;text-decoration: none;}a:hover { text-decoration: underline;}td {font-family: 'Microsoft YaHei';}</style></head>";
			html+="<body><table width=\"900\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"font-family: 'Microsoft YaHei';\"><tbody><tr><td>";
			html+="<table width=\"900\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding: 0 20px; font-size: 14px; color: #333333;\"></table>";
			html+="<table width=\"900\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding: 0 20px; font-size: 14px; color: #333333;\"><tbody><tr>";
			html+="<td width=\"760\" height=\"46\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">Dear "+name+"</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">Thank you for applying for Mojif Sticker SDK. Here is your app key:</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\">&nbsp;</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 19px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">AppId: <span style=\"font-size:19px;\">"+appId+"</span></td>";
			html+="</tr><tr><td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 19px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">AppSecret: <span style=\"font-size:19px;\">"+appSecret+"</span></td>";
			html+="</tr><tr><td width=\"720\" height=\"32\" colspan=\"2\" style=\"padding-left: 40px;\">&nbsp;</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">Please keep it in secure. and do not disclose it to others.</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">If you have any question with our SDK, you can contact us via ";
			html+="<span style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei'; color:red\">support@mojif.com</span> anytime.</td></tr><tr>";
			html+="<td width=\"720\" height=\"32\" colspan=\"2\" style=\"padding-left: 40px;\">&nbsp;</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: bottom; font-family: 'Microsoft YaHei';\">Thanks,</td></tr><tr>";
			html+="<td width=\"720\" height=\"20\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 16px; vertical-align: top; font-family: 'Microsoft YaHei';\">The Mojif Team</td>";
			html+="</tr><tr><td width=\"720\" height=\"32\" colspan=\"2\" style=\"padding-left: 40px;\">&nbsp;</td></tr><tr>";
			html+="<td width=\"720\" height=\"30\" border=\"0\" align=\"left\" colspan=\"2\" style=\"font-size: 19px; vertical-align: bottom; font-family: 'Microsoft YaHei';\"> ";
			html+="<a href=\"http://www.mojif.com/download.html\">> Download SDK</a><br /><a href=\"http://open.biaoqingmm.com/doc/sdk_en/\" >> Documentation</a></td></tr>";
			html+="</tbody></table></td>";
			html+="</tr></tbody></table>";
			html+="<style type=\"text/css\">body {font-size: 14px;font-family: arial, verdana, sans-serif;line-height: 1.666;padding: 0;margin: 0;overflow: auto;white-space: normal;word-wrap: break-word;min-height: 100px}";
			html+="td, input, button, select, body {font-family: Helvetica, 'Microsoft Yahei', verdana}";
			html+="pre {white-space: pre-wrap;white-space: -moz-pre-wrap;white-space: -pre-wrap;white-space: -o-pre-wrap;word-wrap: break-word;width: 95%}";
			html+="th, td {font-family: arial, verdana, sans-serif;line-height: 1.666}img {border: 0}";
			html+="header, footer, section, aside, article, nav, hgroup, figure, figcaption{display: block}</style></body></html>";
			messageHelper.setText(html, true);
			messageHelper.setSentDate(new Date());
			senderImpl.send(mailMessage);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendVerificationMail(String email, String url) {
		try {
			messageHelper.setFrom(FORM, PERSONAL);
			messageHelper.setTo(email);
			messageHelper.setSubject("[表情云开发者]邮箱激活");
			String html = "<html><head><base target=\"_blank\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><style type=\"text/css\">";
			html += "::-webkit-scrollbar{ display: none; }</style><style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}";
			html += "</style></head><body><b>亲爱的用户 <a href=\"mailto:" + email + " target=\"_blank\" \">" + email + "</a></b><br><br>您好！<br><br>";
			html += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\"><tbody><tr><td valign=\"top\">为了保障您帐号的安全性，请点击下面的链接激活邮箱：";
			html += "<br><br><br><br><div align=\"left\"><a href=\"" + url + "\" target=\"_blank\"><b><font style=\"font-size:12px\">"+url+"</font></b></a></div>";
			html += "<br>-----------------------------------------------<br><br><br><b>本链接在您验证过一次后将自动失效。</b><br><br>感谢您使用表情云！</td></tr><tr>";
			html += "</tr></tbody></table><table></table> <style type=\"text/css\">";
			html += "body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}";
			html += " td, input, button, select, body{font-family:Helvetica, 'Microsoft Yahei', verdana} pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;";
			html += "white-space:-o-pre-wrap;word-wrap:break-word;width:95%} th,td{font-family:arial,verdana,sans-serif;line-height:1.666} img{ border:0}";
			html += "header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}</style>";
			html += "<style id=\"ntes_link_color\" type=\"text/css\">a,td a{color:#064977}</style></body></html>";
			messageHelper.setText(html, true);
			messageHelper.setSentDate(new Date());
			senderImpl.send(mailMessage);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendResetPasswordMail(String email, String url) {
		try {
			messageHelper.setFrom(FORM, PERSONAL);
			messageHelper.setTo(email);
			messageHelper.setSubject("[表情云开发者]密码重置");
			String html ="<html><head><base target=\"_blank\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><style type=\"text/css\">";
			html +="::-webkit-scrollbar{ display: none; }</style><style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}";
			html +="</style></head><body>";
			html +="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\" align=\"center\"><tbody><tr><td><p>点击以下链接来重置密码:</p><a href=\"" + url + "\" target=\"_blank\">"+url+"</a></td></tr>";
			html +="</tbody></table><table></table> <style type=\"text/css\">";
			html +="body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}";
			html +=" td, input, button, select, body{font-family:Helvetica, 'Microsoft Yahei', verdana} pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;";
			html +="white-space:-o-pre-wrap;word-wrap:break-word;width:95%} th,td{font-family:arial,verdana,sans-serif;line-height:1.666} img{ border:0}";
			html +="header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}</style>";
			html +="<style id=\"ntes_link_color\" type=\"text/css\">a,td a{color:#064977}</style></body></html>";
			messageHelper.setText(html, true);
			messageHelper.setSentDate(new Date());
			senderImpl.send(mailMessage);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendIpAccountRegister(String email, String name,String link,String registrant_email,String telephone,String company,String description,String registrant_name) {
		try {
			messageHelper.setFrom(FORM, PERSONAL_OPEN);
			messageHelper.setTo(email);
			messageHelper.setSubject("官网内容主页申请");
			String text = "主页名称:	" + name + "\n";
			text += "主页链接:	" + link + "\n";
			text += "姓名:	" + registrant_name + "\n";
			text += "联系邮箱:	" + registrant_email + "\n";
			text += "联系电话:	" + telephone + "\n";
			text += "公司:	" + company + "\n";
			text += "主页内容描述:	" + description + "\n";
			messageHelper.setText(text, false);
			messageHelper.setSentDate(new Date());
			senderImpl.send(mailMessage);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args){
		MailUtil mailUtil = MailUtil.newInstance();
		mailUtil.sendIpAccountRegister("wen.liu@siyanhui.com","测试ip","www.baidu.com","11111@qq.com","1823212311","似颜绘","hahah","李阳");
	}

}
