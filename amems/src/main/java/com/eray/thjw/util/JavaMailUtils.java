package com.eray.thjw.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class JavaMailUtils extends BaseController{
	 //发件人地址
    public static String senderAddress = "yuebin120320@163.com";
    //收件人地址
    public static String recipientAddress = "yuebinbin@e-ray.com.cn";
    //发件人账户名
    public static String senderAccount = "yuebin120320";
    //发件人账户密码
    public static String senderPassword = "a123456";
    //发件人的SMTP服务器地址
    public static String smtpAddress = "smtp.163.com";
    
    public static FreeMarkerConfigurer freeMarkerConfigurer = null;
    
    public synchronized static FreeMarkerConfigurer getConfig() throws IOException, TemplateException {
		if (freeMarkerConfigurer == null) {
			freeMarkerConfigurer = freemarkerConfig();
		}
		return freeMarkerConfigurer;
	}
    
    public static Session setProperty(){
    	//1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", smtpAddress);
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //true开启控制台打印，false不打印
        session.setDebug(false);
        return session;
    }  
    
    
    
    /**
     * 
     * @Description 邮件样例
     * @CreateTime 2018年4月27日 下午1:42:54
     * @CreateBy 岳彬彬
     * @param session
     * @return
     * @throws Exception
     */
    public static MimeMessage getMimeMessage(Session session) throws Exception{
        //1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //2.设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        //4.设置邮件主题
        msg.setSubject("待办事宜","UTF-8");
        // 获取模版对象
//        Template template = freemarker.getConfiguration().getTemplate("mail.ftl");

        // 准备的 model data
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", "11111");
        
        // 解析 模版和 数据 （使用了模板，5、6、7、8、9步骤都不能使用）
        String content = getMailText("D:/workSpace/amems/src/main/webapp/WEB-INF/templet/","email.ftl",map);    
        // 设置正文部分  
        BodyPart contentPart = new MimeBodyPart();  
//      设置文本为html 文本  
       contentPart.setContent(content, "text/html; charset=utf-8");  
        // 5. 创建图片"节点"
        MimeBodyPart image = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh = new DataHandler(new FileDataSource("D:\\hxfj.jpg"));
        // 将图片数据添加到"节点"
        image.setDataHandler(dh);
        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
        image.setContentID("mailTestPic");    
         
        // 6. 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent("<a href ='#' onclick ='localhost:8080/amems'></a>", "text/html;charset=UTF-8");
         
        // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related");    // 关联关系
         
        // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
        // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);
         
        // 9. 创建附件"节点"
        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh2 = new DataHandler(new FileDataSource("D:\\hxfj.jpg"));
        // 将附件数据添加到"节点"
        attachment.setDataHandler(dh2);
        // 设置附件的文件名（需要编码）
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));       
         
        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(contentPart);
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系
 
        // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mm);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
         
        return msg;
    }
    /**
     * 
     * @Description 配置模板
     * @CreateTime 2018年5月2日 上午9:41:52
     * @CreateBy 岳彬彬
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        result.setConfiguration(factory.createConfiguration());
        return result;
    }
    /**
     * 
     * @Description 模板转换成邮件正文内容
     * @CreateTime 2018年4月27日 下午5:09:26
     * @CreateBy 岳彬彬
     * @param bath
     * @param templateName
     * @param map
     * @return 
     */
    public static String getMailText(String bath, String templateName,
            Map<String, Object> map) {
    	TemplateLoader templateLoader=null;     
        //使用FileTemplateLoader  
       Configuration cfg;
       String htmlText = "";
	try {
		cfg = getConfig().getConfiguration();
		templateLoader=new FileTemplateLoader(new File(bath));  
		cfg.setTemplateLoader(templateLoader);  		
		// 通过指定模板名获取FreeMarker模板实例
		Template tpl = cfg.getTemplate(templateName,"utf-8");
		// FreeMarker通过Map传递动态数据
		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
	} catch (IOException e) {
		new BusinessException("未找到模板文件");
	} catch (TemplateException e) {
		new BusinessException("未找到模板文件");
	}            
        return htmlText;
    }
    
    
    public static void main(String[] args) throws Exception {
        //1、连接邮件服务器的参数配置
        Session session = setProperty();
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(senderAccount, senderPassword);
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());
         
        //5、关闭邮件连接
        transport.close();
    }
}
