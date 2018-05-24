package com.eray.pbs.util.idoc;

public class E2ORTOB_PARTNR
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
     * Partner Function
     */
    private String pARVW;
    /**
     * Partner
     */
    private String pARNR;
    /**
     * Description
     */
    private String vTEXT;
    /**
     * Language according to ISO 639
     */
    private String vTEXT_LANGU;
    /**
     * Primary Contact Name
     */
    private String c_NAME1;
    /**
     * First name
     */
    private String c_NAMEV1;
    /**
     * Primary Contact Telephone Number
     */
    private String c_TELF1;
    /**
     * Primary contact function
     */
    private String c_FUNC1;
    /**
     * Primary contact language
     */
    private String c_LANG1;
    /**
     * Secondary Contact Name
     */
    private String c_NAME2;
    /**
     * First name
     */
    private String c_NAMEV2;
    /**
     * Secondary Contact Telephone Number
     */
    private String c_TELF2;
    /**
     * Secondary contact function
     */
    private String c_FUNC2;
    /**
     * Secondary contact language
     */
    private String c_LANG2;

    public String getPARVW()
    {
        return pARVW;
    }

    public void setPARVW(String pARVW)
    {
        this.pARVW = pARVW;
    }

    public String getPARNR()
    {
        return pARNR;
    }

    public void setPARNR(String pARNR)
    {
        this.pARNR = pARNR;
    }

    public String getVTEXT()
    {
        return vTEXT;
    }

    public void setVTEXT(String vTEXT)
    {
        this.vTEXT = vTEXT;
    }

    public String getVTEXT_LANGU()
    {
        return vTEXT_LANGU;
    }

    public void setVTEXT_LANGU(String vTEXT_LANGU)
    {
        this.vTEXT_LANGU = vTEXT_LANGU;
    }

    public String getC_NAME1()
    {
        return c_NAME1;
    }

    public void setC_NAME1(String c_NAME1)
    {
        this.c_NAME1 = c_NAME1;
    }

    public String getC_NAMEV1()
    {
        return c_NAMEV1;
    }

    public void setC_NAMEV1(String c_NAMEV1)
    {
        this.c_NAMEV1 = c_NAMEV1;
    }

    public String getC_TELF1()
    {
        return c_TELF1;
    }

    public void setC_TELF1(String c_TELF1)
    {
        this.c_TELF1 = c_TELF1;
    }

    public String getC_FUNC1()
    {
        return c_FUNC1;
    }

    public void setC_FUNC1(String c_FUNC1)
    {
        this.c_FUNC1 = c_FUNC1;
    }

    public String getC_LANG1()
    {
        return c_LANG1;
    }

    public void setC_LANG1(String c_LANG1)
    {
        this.c_LANG1 = c_LANG1;
    }

    public String getC_NAME2()
    {
        return c_NAME2;
    }

    public void setC_NAME2(String c_NAME2)
    {
        this.c_NAME2 = c_NAME2;
    }

    public String getC_NAMEV2()
    {
        return c_NAMEV2;
    }

    public void setC_NAMEV2(String c_NAMEV2)
    {
        this.c_NAMEV2 = c_NAMEV2;
    }

    public String getC_TELF2()
    {
        return c_TELF2;
    }

    public void setC_TELF2(String c_TELF2)
    {
        this.c_TELF2 = c_TELF2;
    }

    public String getC_FUNC2()
    {
        return c_FUNC2;
    }

    public void setC_FUNC2(String c_FUNC2)
    {
        this.c_FUNC2 = c_FUNC2;
    }

    public String getC_LANG2()
    {
        return c_LANG2;
    }

    public void setC_LANG2(String c_LANG2)
    {
        this.c_LANG2 = c_LANG2;
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
