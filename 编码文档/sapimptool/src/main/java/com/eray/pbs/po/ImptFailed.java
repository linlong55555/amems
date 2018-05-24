package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 实体类：导入记录统计
 * 
 * @since 1.0 15 Aug 2014
 * @author Hao.Z
 * 
 */
@Entity
@Table(name = "sap_imptfailed")
public class ImptFailed extends BaseEntity
{
    private String fileName; // 文件名
    private Integer fileType; // 文件类型
    private String line; // 数据信息
    private String errorInfo; // 错误信息
    private Integer dealflag;
    

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; fileName:"+fileName+
    			"; fileType:"+fileType+
    			"; line:"+line+
    			"; errorInfo:"+errorInfo+
    			"; dealflag:"+dealflag;
    }

    @Column(name = "filename_")
    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    @Column(name = "errorinfo_")
    public String getErrorInfo()
    {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo)
    {
        this.errorInfo = errorInfo;
    }

    @Column(name = "filetype_")
    public Integer getFileType()
    {
        return fileType;
    }

    public void setFileType(Integer fileType)
    {
        this.fileType = fileType;
    }

    /**
     * @return the line
     */
    @Column(name = "line_")
    public String getLine()
    {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(String line)
    {
        this.line = line;
    }

	/**
	 * @return the dealflag
	 */
    @Column(name = "dealflag_")
	public Integer getDealflag()
	{
		return dealflag;
	}

	/**
	 * @param dealflag the dealflag to set
	 */
	public void setDealflag(Integer dealflag)
	{
		this.dealflag = dealflag;
	}


}
