package com.eray.pbs.util.idoc;

public class E2ORHDR_LTXT
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
     * Tag column
     */
    private String tDFORMAT;
    /**
     * Text Line
     */
    private String tDLINE;

    /**
     * @return 标签列
     */
    public String getTDFORMAT()
    {
        return tDFORMAT;
    }

    public void setTDFORMAT(String tDFORMAT)
    {
        this.tDFORMAT = tDFORMAT;
    }

    /**
     * @return 文本行
     */
    public String getTDLINE()
    {
        return tDLINE;
    }

    public void setTDLINE(String tDLINE)
    {
        this.tDLINE = tDLINE;
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
