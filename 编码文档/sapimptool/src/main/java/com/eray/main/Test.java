package com.eray.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.eray.pbs.util.idoc.E2ORHDR_LTXT;
import com.eray.pbs.util.idoc.E2OROPR;
import com.eray.pbs.util.idoc.IDoc;
import com.eray.pbs.util.idoc.IDocHelper;

public class Test
{
	public static void main3(String[] args)
	{
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost("27.17.59.98");
		senderImpl.setPort(25);
		// 建立邮件消息
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		// 设置收件人，寄件人 用数组发送多个邮件
		// String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
		// mailMessage.setTo(array);
		mailMessage.setTo("popshadow@163.com");
		mailMessage.setFrom("zhenghao@e-ray.com.cn");
		mailMessage.setSubject("测试文本邮件发送 ");
		mailMessage.setText("测试邮件发送！！");

		senderImpl.setUsername("zhenghao"); // 根据自己的情况,设置username
		senderImpl.setPassword("Admin1234"); // 根据自己的情况, 设置password

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println(" 邮件发送成功.. ");
	}

	public static void main(String[] args) throws IOException
	{
		File file=new File("C:/Tmp/result.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		//BufferedReader input = new BufferedReader(new FileReader(file));
		
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		
		File errorDir=new File("C:/Tmp/error");
		
		File[] files=errorDir.listFiles();
		IDoc idoc =null;
		String line=System.getProperty("line.separator");
		for (File errorFile : files)
        {
			idoc = IDocHelper.parseTxt(errorFile);
			output.write(line+"解析文件:"+errorFile.getName());
			output.write(line+"AUFNR"+"_"+"Order Number" + ":" + idoc.getE2ORHDR().getAUFNR());
			output.write(line+"STRNO"+"_"+"Functional location label" + ":" + idoc.getE2ORHDR().getSTRNO());
			output.write(line+"EQUNR"+"_"+"Equipment Number" + ":" + idoc.getE2ORHDR().getEQUNR());
			output.write(line+"QMNUM"+"_"+"Notification No" + ":" + idoc.getE2ORHDR().getQMNUM());
			output.write(line+"KTEXT"+"_"+"Description" + ":" + idoc.getE2ORHDR().getKTEXT());
			output.write(line+"AUART"+"_"+"Order Type" + ":" + idoc.getE2ORHDR().getAUART());
			output.write(line+"STTXT"+"_"+"System status line" + ":" + idoc.getE2ORHDR().getSTTXT());
			output.write(line+"USTXT"+"_"+"User Status Line" + ":" + idoc.getE2ORHDR().getUSTXT());
			output.write(line+"ILART"+"_"+"Maintenance activity type" + ":" + idoc.getE2ORHDR().getILART());
			output.write(line+"BUKRS"+"_"+"Company Code" + ":" + idoc.getE2ORHDR().getBUKRS());
			output.write(line+"KOKRS"+"_"+"Controlling Area" + ":" + idoc.getE2ORHDR().getKOKRS());
			output.write(line+"GSBER"+"_"+"Business Area" + ":" + idoc.getE2ORHDR().getGSBER());
			output.write(line+"ECOST"+"_"+"Estimated total costs of order" + ":" + idoc.getE2ORHDR().getECOST());
			output.write(line+"WAERS"+"_"+"Currency Key" + ":" + idoc.getE2ORHDR().getWAERS());
			output.write(line+"PRIOK"+"_"+"Priority" + ":" + idoc.getE2ORHDR().getPRIOK());
			output.write(line+"TERKZ"+"_"+"Scheduling type" + ":" + idoc.getE2ORHDR().getTERKZ());
			output.write(line+"GSTRS"+"_"+"Scheduled start date." + ":" + idoc.getE2ORHDR().getGSTRS());
			output.write(line+"GSUZS"+"_"+"Scheduled start time" + ":" + idoc.getE2ORHDR().getGSUZS());
			output.write(line+"GLTRS"+"_"+"Scheduled end date" + ":" + idoc.getE2ORHDR().getGLTRS());
			output.write(line+"GLUZS"+"_"+"Scheduled end time" + ":" + idoc.getE2ORHDR().getGLUZS());
			output.write(line+"GSTRP"+"_"+"Earliest start date" + ":" + idoc.getE2ORHDR().getGSTRP());
			output.write(line+"GSUZP"+"_"+"Earliest start time" + ":" + idoc.getE2ORHDR().getGSUZP());
			output.write(line+"GLTRP"+"_"+"Latest finish date" + ":" + idoc.getE2ORHDR().getGLTRP());
			output.write(line+"GLUZP"+"_"+"Latest finish time" + ":" + idoc.getE2ORHDR().getGLUZP());
			output.write(line+"ARBPL"+"_"+"Work center" + ":" + idoc.getE2ORHDR().getARBPL());
			output.write(line+"MAUFNR"+"_"+"Number of superior order" + ":" + idoc.getE2ORHDR().getMAUFNR());
			output.write(line+"WERKS"+"_"+"Plant" + ":" + idoc.getE2ORHDR().getWERKS());
			output.write(line+"ANLZU"+"_"+"Syst.Condition" + ":" + idoc.getE2ORHDR().getANLZU());
			output.write(line+"LANGU_ISO"+"_"+"Language according to ISO 639" + ":" + idoc.getE2ORHDR().getLANGU_ISO());

			output.write(line+"ZZCARDNO"+"_"+"TaskId" + ":" + idoc.getZZCARDNO().getZZCARDNO());
			output.write(line+"ZZCONTRNO"+"_"+"ControlNo" + ":" + idoc.getZZCARDNO().getZZCONTRNO());
			output.write(line+"REVNR"+"_"+"RevisionId" + ":" + idoc.getZZREVISON().getREVNR());
			output.write(line+"BUKRS"+"_"+"CompanyCode" + ":" + idoc.getE2NTHDR().getBUKRS());
			output.write(line+"TPLKZ"+"_"+"FuncLocationStructureIndicator" + ":" + idoc.getZE1ORHDR_EXT().getTPLKZ());
			String description = "";
			for (E2ORHDR_LTXT e2ORHDR_LTXT : idoc.getE2ORHDR_LTXTList())
			{
				if(e2ORHDR_LTXT.getTDLINE()==null){
					continue;
				}
				if ("".equals(description))
				{
					description = description + e2ORHDR_LTXT.getTDLINE();
				} else
				{
					description = description + " " + e2ORHDR_LTXT.getTDLINE();
				}
			}
			if (description.equals(""))
			{
				output.write(line+"KTEXT"+"_"+"Description" + ":" + idoc.getE2ORHDR().getKTEXT());
			} else
			{
				output.write(line+"KTEXT"+"_"+"Description" + ":" + description);
			}

			if (idoc.getE2ORTOB_PARTNR().getPARNR() != null)
			{
				output.write(line+"----------------------Partner Start---------------------");
				output.write(line+"NAME1"+"_"+"NAME" + ":" + idoc.getE2ORTOB_PARTNR_ADR().getNAME1());
				output.write(line+"STREET"+"_"+"Street" + ":" + idoc.getE2ORTOB_PARTNR_ADR().getSTREET());
				output.write(line+"CITY1"+"_"+"CITY" + ":" + idoc.getE2ORTOB_PARTNR_ADR().getCITY1());
				output.write(line+"POST_CODE1"+"_"+"c" + ":" + idoc.getE2ORTOB_PARTNR_ADR().getPOST_CODE1());
				output.write(line+"SORT1"+"_"+"SearchTerm" + ":" + idoc.getE2ORTOB_PARTNR_ADR().getSORT1());
				output.write(line+"COUNTRY"+"_"+"CountryKey" + ":" + idoc.getE2ORTOB_PARTNR_ADR().getCOUNTRY());
				output.write(line+"TEL_NUMBER"+"_"+"FirstTelephone" + ":" + idoc.getE2ORTOB_PARTNR_ADR().getTEL_NUMBER());
				output.write(line+"PARVW"+"_"+"PartnerFunction" + ":" + idoc.getE2ORTOB_PARTNR().getPARVW());
				output.write(line+"PARNR"+"_"+"PartnerId" + ":" + idoc.getE2ORTOB_PARTNR().getPARNR());
				output.write(line+"VTEXT"+"_"+"Description" + ":" + idoc.getE2ORTOB_PARTNR().getVTEXT());
				output.write(line+"AUFNR"+"_"+"WorkId" + ":" + idoc.getE2ORHDR().getAUFNR());
				output.write(line+"----------------------Partner End---------------------");
			}

			if (idoc.getE2OROPRList() != null && idoc.getE2OROPRList().size() > 0)
			{
				output.write(line+"----------------------Operation Start---------------------");
				int i = 10;
				for (E2OROPR e2OROPR : idoc.getE2OROPRList())
				{
					output.write(line+"Operation Number:00" + i);
					output.write(line+"VORNR"+"_"+"OperationId" + ":" + e2OROPR.getVORNR());
					output.write(line+"LTXA1"+"_"+"ShortText" + ":" + e2OROPR.getLTXA1());
					output.write(line+"ARBPL"+"_"+"WorkCenter" + ":" + e2OROPR.getARBPL());
					output.write(line+"STEUS"+"_"+"ControlKey" + ":" + e2OROPR.getSTEUS());
					output.write(line+"LARNT"+"_"+"ActivityType" + ":" + e2OROPR.getLARNT());
					output.write(line+"ARBEI"+"_"+"WorkActivity" + ":" + e2OROPR.getARBEI());
					output.write(line+"DAUNO"+"_"+"NormalDuration" + ":" + e2OROPR.getDAUNO());
					output.write(line+"ARBEH"+"_"+"UnitForWork" + ":" + e2OROPR.getARBEH());
					output.write(line+"FSAVD"+"_"+"ScheduledStartDate" + ":" + e2OROPR.getFSAVD());
					output.write(line+"FSAVZ"+"_"+"ScheduledStartTime" + ":" + e2OROPR.getFSAVZ());
					output.write(line+"FSEDD"+"_"+"ScheduledFinishDate" + ":" + e2OROPR.getFSEDD());
					output.write(line+"FSEDZ"+"_"+"ScheduledFinishTime" + ":" + e2OROPR.getFSEDZ());
					i = i + 10;
				}
				output.write(line+"----------------------Operation End---------------------");
			}
			output.write(line);
        }
		
		output.close();
		
		

	}
}
