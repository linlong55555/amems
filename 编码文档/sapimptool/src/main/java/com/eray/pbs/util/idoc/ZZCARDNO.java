package com.eray.pbs.util.idoc;

public class ZZCARDNO
{
    /**
     * Name of SAP segment
     */
    private String sEGMENT;
    /**
     * Client
     */
    private String mANDT;
    /**
     * IDoc number
     */
    private String dOCNUM;
    /**
     * Number of SAP segment
     */
    private String sEGNUM;
    /**
     * Number of the higher-level SAP segment
     */
    private String pSGNUM;
    /**
     * Hierarchy level of SAP segment
     */
    private String hLEVEL;
    /**
     * Application data
     */
    private String sDATA;
    /**
     * A&D: External ID of Task List
     */
    private String zZCARDNO;
    /**
     * R/2 table control number
     */
    private String zZCONTRNO;
    /**
     * new card number
     */
    private String zZNEWCARDNO;
    /**
     * original card number
     */
    private String zZORIGINAL;

    /**
     * @return 工卡号
     */
    public String getZZCARDNO()
    {
        return zZCARDNO;
    }

    public void setZZCARDNO(String zZCARDNO)
    {
        this.zZCARDNO = zZCARDNO;
    }

    /**
     * @return 控制号
     */
    public String getZZCONTRNO()
    {
        return zZCONTRNO;
    }

    public void setZZCONTRNO(String zZCONTRNO)
    {
        this.zZCONTRNO = zZCONTRNO;
    }

    /**
     * @return 新卡号
     */
    public String getZZNEWCARDNO()
    {
        return zZNEWCARDNO;
    }

    public void setZZNEWCARDNO(String zZNEWCARDNO)
    {
        this.zZNEWCARDNO = zZNEWCARDNO;
    }

    /**
     * @return 原卡号
     */
    public String getZZORIGINAL()
    {
        return zZORIGINAL;
    }

    public void setZZORIGINAL(String zZORIGINAL)
    {
        this.zZORIGINAL = zZORIGINAL;
    }

    /**
     * @return SAP片段名称
     */
    public String getSEGMENT()
    {
        return sEGMENT;
    }

    public void setSEGMENT(String sEGMENT)
    {
        this.sEGMENT = sEGMENT;
    }

    /**
     * @return 客户端
     */
    public String getMANDT()
    {
        return mANDT;
    }

    public void setMANDT(String mANDT)
    {
        this.mANDT = mANDT;
    }

    /**
     * @return IDoc编号
     */
    public String getDOCNUM()
    {
        return dOCNUM;
    }

    public void setDOCNUM(String dOCNUM)
    {
        this.dOCNUM = dOCNUM;
    }

    /**
     * @return SAP片段编号
     */
    public String getSEGNUM()
    {
        return sEGNUM;
    }

    public void setSEGNUM(String sEGNUM)
    {
        this.sEGNUM = sEGNUM;
    }

    /**
     * @return 最上位SAP片段号
     */
    public String getPSGNUM()
    {
        return pSGNUM;
    }

    public void setPSGNUM(String pSGNUM)
    {
        this.pSGNUM = pSGNUM;
    }

    /**
     * @return SAP片段的层次级别
     */
    public String getHLEVEL()
    {
        return hLEVEL;
    }

    public void setHLEVEL(String hLEVEL)
    {
        this.hLEVEL = hLEVEL;
    }

    /**
     * @return 应用数据
     */
    public String getSDATA()
    {
        return sDATA;
    }

    public void setSDATA(String sDATA)
    {
        this.sDATA = sDATA;
    }
}
