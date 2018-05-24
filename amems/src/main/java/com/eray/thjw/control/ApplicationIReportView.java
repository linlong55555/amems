package com.eray.thjw.control;
  
import java.util.Map;  
  
import net.sf.jasperreports.engine.JasperPrint;  
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;  
  
/** 
 * SpringMVC + IReport整合 视图处理扩展 
 */  
public class ApplicationIReportView extends JasperReportsMultiFormatView {  
    private JasperReport jasperReport;  
      
    private String reportDataKeyExt;
    
    public ApplicationIReportView() { 
        super();  
    }  
  
    protected JasperPrint fillReport(Map<String, Object> model) throws Exception {  
        if (model.containsKey("url")) { 
            setUrl(String.valueOf(model.get("url")));  
            this.jasperReport = loadReport();  
        }  
        if(StringUtils.isNotBlank(reportDataKeyExt) && model.get(reportDataKeyExt)!=null){
        	setReportDataKey(reportDataKeyExt);
        	//model.put(reportDataKeyExt, model.get(reportDataKeyExt));
        } 
        else{
        	setReportDataKey(null);
        }
        return super.fillReport(model);  
    }  
      
    protected JasperReport getReport() {  
        return this.jasperReport;  
    }

	public String getReportDataKeyExt() {
		return reportDataKeyExt;
	}

	public void setReportDataKeyExt(String reportDataKeyExt) {
		this.reportDataKeyExt = reportDataKeyExt;
	}

}  