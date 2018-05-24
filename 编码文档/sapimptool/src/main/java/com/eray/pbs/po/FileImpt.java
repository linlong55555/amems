package com.eray.pbs.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "sap_fileimpt")
public class FileImpt extends BaseEntity
{
    private String fileName;
    private Integer fileType;
    private Timestamp fileDate;
    private Integer isImpt;
    private Timestamp imptTime;
    private Long fileSize;
    private Integer success;
    private Integer failed;

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
    			"; fileDate:"+fileDate+
    			"; isImpt:"+isImpt+
    			"; imptTime:"+imptTime+
    			"; fileSize:"+fileSize+
    			"; success:"+success+
    			"; failed:"+failed;
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
    
    @Column(name = "filetype_")
    public Integer getFileType()
    {
        return fileType;
    }

    public void setFileType(Integer fileType)
    {
        this.fileType = fileType;
    }

    @Column(name = "filedate_")
    public Timestamp getFileDate()
    {
        return fileDate;
    }

    public void setFileDate(Timestamp fileDate)
    {
        this.fileDate = fileDate;
    }

    @Column(name = "isimpt_")
    public Integer getIsImpt()
    {
        return isImpt;
    }

    public void setIsImpt(Integer isImpt)
    {
        this.isImpt = isImpt;
    }

    @Column(name = "impttime_")
    public Timestamp getImptTime()
    {
        return imptTime;
    }

    public void setImptTime(Timestamp imptTime)
    {
        this.imptTime = imptTime;
    }

    @Column(name = "filesize_")
    public Long getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
    }

    @Column(name = "success_")
    public Integer getSuccess()
    {
        return success;
    }

    public void setSuccess(Integer success)
    {
        this.success = success;
    }

    @Column(name = "failed_")
    public Integer getFailed()
    {
        return failed;
    }

    public void setFailed(Integer failed)
    {
        this.failed = failed;
    }
}
