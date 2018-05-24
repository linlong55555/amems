package com.eray.pbs.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "sap_impterror")
public class ImptError extends BaseEntity
{
	private String fileName; // 文件名
    private Integer fileType; // 文件类型
    private String errorInfo; // 错误信息
    private Timestamp imptTime;
    

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
    			"; errorInfo:"+errorInfo+
    			"; imptTime:"+imptTime;
    }
    
	/**
	 * @return the fileName
	 */
    @Column(name = "filename_")
	public String getFileName()
	{
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	/**
	 * @return the fileType
	 */
	@Column(name = "filetype_")
	public Integer getFileType()
	{
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(Integer fileType)
	{
		this.fileType = fileType;
	}
	/**
	 * @return the errorInfo
	 */
	@Column(name = "errorinfo_")
	public String getErrorInfo()
	{
		return errorInfo;
	}
	/**
	 * @param errorInfo the errorInfo to set
	 */
	public void setErrorInfo(String errorInfo)
	{
		this.errorInfo = errorInfo;
	}
	/**
	 * @return the imptTime
	 */
	@Column(name = "impttime_")
	public Timestamp getImptTime()
	{
		return imptTime;
	}
	/**
	 * @param imptTime the imptTime to set
	 */
	public void setImptTime(Timestamp imptTime)
	{
		this.imptTime = imptTime;
	}
}
