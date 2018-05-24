package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "sap_revision")
public class Revision extends BaseEntity
{
    private String revisionId;
    private String line;
    private String imptFilename;
    

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; revisionId:"+revisionId+
    			"; line:"+line+
    			"; imptFilename:"+imptFilename;
    }
    /**
     * @return the revisionId
     */
    @Column(name = "revisionid_")
    public String getRevisionId()
    {
        return revisionId;
    }
    /**
     * @param revisionId the revisionId to set
     */
    public void setRevisionId(String revisionId)
    {
        this.revisionId = revisionId;
    }
    /**
     * @return the imptFilename
     */
    @Column(name = "imptfilename_")
    public String getImptFilename()
    {
        return imptFilename;
    }
    /**
     * @param imptFilename the imptFilename to set
     */
    public void setImptFilename(String imptFilename)
    {
        this.imptFilename = imptFilename;
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
}
