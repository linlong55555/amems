package com.eray.pbs.util.idoc;

public class E2ORHDR
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
     * Order Number
     */
    private String aUFNR;
    /**
     * Functional location label
     */
    private String sTRNO;
    /**
     * Equipment Number
     */
    private String eQUNR;
    /**
     * Notification No
     */
    private String qMNUM;
    /**
     * Description
     */
    private String kTEXT;
    /**
     * Order Type
     */
    private String aUART;
    /**
     * System status line
     */
    private String sTTXT;
    /**
     * User Status Line
     */
    private String uSTXT;
    /**
     * Maintenance activity type
     */
    private String iLART;
    /**
     * Company Code
     */
    private String bUKRS;
    /**
     * Controlling Area
     */
    private String kOKRS;
    /**
     * Business Area
     */
    private String gSBER;
    /**
     * Estimated total costs of order
     */
    private String eCOST;
    /**
     * Currency Key
     */
    private String wAERS;
    /**
     * Priority
     */
    private String pRIOK;
    /**
     * Scheduling type
     */
    private String tERKZ;
    /**
     * Scheduled start date.
     */
    private String gSTRS;
    /**
     * Scheduled start time
     */
    private String gSUZS;
    /**
     * Scheduled end date
     */
    private String gLTRS;
    /**
     * Scheduled end time
     */
    private String gLUZS;
    /**
     * Earliest start date
     */
    private String gSTRP;
    /**
     * Earliest start time
     */
    private String gSUZP;
    /**
     * Latest finish date
     */
    private String gLTRP;
    /**
     * Latest finish time
     */
    private String gLUZP;
    /**
     * Work center
     */
    private String aRBPL;
    /**
     * Number of superior order
     */
    private String mAUFNR;
    /**
     * Plant
     */
    private String wERKS;
    /**
     * Syst.Condition
     */
    private String aNLZU;
    /**
     * Language according to ISO 639
     */
    private String lANGU_ISO;

    /**
     * @return 工单号
     */
    public String getAUFNR()
    {
        return aUFNR;
    }

    public void setAUFNR(String aUFNR)
    {
        this.aUFNR = aUFNR;
    }

    /**
     * @return 飞机尾翼标签
     */
    public String getSTRNO()
    {
        return sTRNO;
    }

    public void setSTRNO(String sTRNO)
    {
        this.sTRNO = sTRNO;
    }

    /**
     * @return 设备号
     */
    public String getEQUNR()
    {
        return eQUNR;
    }

    public void setEQUNR(String eQUNR)
    {
        this.eQUNR = eQUNR;
    }

    /**
     * @return 通知编号
     */
    public String getQMNUM()
    {
        return qMNUM;
    }

    public void setQMNUM(String qMNUM)
    {
        this.qMNUM = qMNUM;
    }

    /**
     * @return 描述
     */
    public String getKTEXT()
    {
        return kTEXT;
    }

    public void setKTEXT(String kTEXT)
    {
        this.kTEXT = kTEXT;
    }

    /**
     * @return 工单类型
     */
    public String getAUART()
    {
        return aUART;
    }

    public void setAUART(String aUART)
    {
        this.aUART = aUART;
    }

    /**
     * @return 系统状态行
     */
    public String getSTTXT()
    {
        return sTTXT;
    }

    public void setSTTXT(String sTTXT)
    {
        this.sTTXT = sTTXT;
    }

    /**
     * @return 用户状态行
     */
    public String getUSTXT()
    {
        return uSTXT;
    }

    public void setUSTXT(String uSTXT)
    {
        this.uSTXT = uSTXT;
    }

    /**
     * @return 维修活动类型
     */
    public String getILART()
    {
        return iLART;
    }

    public void setILART(String iLART)
    {
        this.iLART = iLART;
    }

    /**
     * @return 公司编号
     */
    public String getBUKRS()
    {
        return bUKRS;
    }

    public void setBUKRS(String bUKRS)
    {
        this.bUKRS = bUKRS;
    }

    /**
     * @return 成本控制范围
     */
    public String getKOKRS()
    {
        return kOKRS;
    }

    public void setKOKRS(String kOKRS)
    {
        this.kOKRS = kOKRS;
    }

    /**
     * @return 业务范围
     */
    public String getGSBER()
    {
        return gSBER;
    }

    public void setGSBER(String gSBER)
    {
        this.gSBER = gSBER;
    }

    /**
     * @return 预测汇总成本中心
     */
    public String getECOST()
    {
        return eCOST;
    }

    public void setECOST(String eCOST)
    {
        this.eCOST = eCOST;
    }

    /**
     * @return 流通键
     */
    public String getWAERS()
    {
        return wAERS;
    }

    public void setWAERS(String wAERS)
    {
        this.wAERS = wAERS;
    }

    /**
     * @return 优先级
     */
    public String getPRIOK()
    {
        return pRIOK;
    }

    public void setPRIOK(String pRIOK)
    {
        this.pRIOK = pRIOK;
    }

    /**
     * @return 计划类型
     */
    public String getTERKZ()
    {
        return tERKZ;
    }

    public void setTERKZ(String tERKZ)
    {
        this.tERKZ = tERKZ;
    }

    /**
     * @return 计划开始日期
     */
    public String getGSTRS()
    {
        return gSTRS;
    }

    public void setGSTRS(String gSTRS)
    {
        this.gSTRS = gSTRS;
    }

    /**
     * @return 计划开始时间
     */
    public String getGSUZS()
    {
        return gSUZS;
    }

    public void setGSUZS(String gSUZS)
    {
        this.gSUZS = gSUZS;
    }

    /**
     * @return 计划结束日期
     */
    public String getGLTRS()
    {
        return gLTRS;
    }

    public void setGLTRS(String gLTRS)
    {
        this.gLTRS = gLTRS;
    }

    /**
     * @return 计划结束时间
     */
    public String getGLUZS()
    {
        return gLUZS;
    }

    public void setGLUZS(String gLUZS)
    {
        this.gLUZS = gLUZS;
    }

    /**
     * @return 最早开始日期
     */
    public String getGSTRP()
    {
        return gSTRP;
    }

    public void setGSTRP(String gSTRP)
    {
        this.gSTRP = gSTRP;
    }

    /**
     * @return 最早开始时间
     */
    public String getGSUZP()
    {
        return gSUZP;
    }

    public void setGSUZP(String gSUZP)
    {
        this.gSUZP = gSUZP;
    }

    /**
     * @return 最晚结束日期
     */
    public String getGLTRP()
    {
        return gLTRP;
    }

    public void setGLTRP(String gLTRP)
    {
        this.gLTRP = gLTRP;
    }

    /**
     * @return 最晚结束时间
     */
    public String getGLUZP()
    {
        return gLUZP;
    }

    public void setGLUZP(String gLUZP)
    {
        this.gLUZP = gLUZP;
    }

    /**
     * @return 工作中心
     */
    public String getARBPL()
    {
        return aRBPL;
    }

    public void setARBPL(String aRBPL)
    {
        this.aRBPL = aRBPL;
    }

    /**
     * @return 高级工单号
     */
    public String getMAUFNR()
    {
        return mAUFNR;
    }

    public void setMAUFNR(String mAUFNR)
    {
        this.mAUFNR = mAUFNR;
    }

    /**
     * @return 执行工厂
     */
    public String getWERKS()
    {
        return wERKS;
    }

    public void setWERKS(String wERKS)
    {
        this.wERKS = wERKS;
    }

    /**
     * @return 系统状态
     */
    public String getANLZU()
    {
        return aNLZU;
    }

    public void setANLZU(String aNLZU)
    {
        this.aNLZU = aNLZU;
    }

    /**
     * @return 根据ISO 639编码
     */
    public String getLANGU_ISO()
    {
        return lANGU_ISO;
    }

    public void setLANGU_ISO(String lANGU_ISO)
    {
        this.lANGU_ISO = lANGU_ISO;
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
