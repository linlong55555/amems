package com.eray.pbs.util.idoc;

public class DataRecord
{
    /**
     * Name of external structure
     */
    private String tABNAM;
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
     * Name of SAP segment
     */
    private String sEGNAM;
    /**
     * Number of the higher-level SAP segment
     */
    private String pSGNUM;
    /**
     * Hierarchy level of SAP segment
     */
    private String hLEVEL;
    /**
     * Blank field for EDI_DD
     */
    private String dTINT2;
    /**
     * Application data
     */
    private String sDATA;
    /**
     * @return 扩展数据名称
     */
    public String getTABNAM()
    {
        return tABNAM;
    }
    public void setTABNAM(String tABNAM)
    {
        this.tABNAM = tABNAM;
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
     * @return SAP片段号
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
     * @return SAP片段名称
     */
    public String getSEGNAM()
    {
        return sEGNAM;
    }
    public void setSEGNAM(String sEGNAM)
    {
        this.sEGNAM = sEGNAM;
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
     * @return EDI_DD预留字段
     */
    public String getDTINT2()
    {
        return dTINT2;
    }
    public void setDTINT2(String dTINT2)
    {
        this.dTINT2 = dTINT2;
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
