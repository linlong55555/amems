package com.eray.pbs.util.idoc;

public class E2OROPR
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
     * Operation/Activity Number
     */
    private String vORNR;
    /**
     * Suboperation
     */
    private String uVORN;
    /**
     * Operation short text
     */
    private String lTXA1;
    /**
     * Work center
     */
    private String aRBPL;
    /**
     * Control key
     */
    private String sTEUS;
    /**
     * Activity Type
     */
    private String lARNT;
    /**
     * Number of capacities required
     */
    private String aNZZL;
    /**
     * Work involved in the activity
     */
    private String aRBEI;
    /**
     * Normal duration of the activity
     */
    private String dAUNO;
    /**
     * Unit for work
     */
    private String aRBEH;
    /**
     * Earliest scheduled start: Execution (date)
     */
    private String fSAVD;
    /**
     * Earliest scheduled start: Execution (time)
     */
    private String fSAVZ;
    /**
     * Earliest scheduled finish: Execution (date)
     */
    private String fSEDD;
    /**
     * Earliest scheduled finish: Execution (time)
     */
    private String fSEDZ;
    /**
     * Completion confirmation number for the operation
     */
    private String rUECK;
    /**
     * System status line
     */
    private String sTTXT;
    /**
     * User Status Line
     */
    private String uSTXT;

    /**
     * @return 工序号/活动号
     */
    public String getVORNR()
    {
        return vORNR;
    }

    public void setVORNR(String vORNR)
    {
        this.vORNR = vORNR;
    }

    /**
     * @return 子工序
     */
    public String getUVORN()
    {
        return uVORN;
    }

    public void setUVORN(String uVORN)
    {
        this.uVORN = uVORN;
    }

    /**
     * @return 工序短文本
     */
    public String getLTXA1()
    {
        return lTXA1;
    }

    public void setLTXA1(String lTXA1)
    {
        this.lTXA1 = lTXA1;
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
     * @return 控制主键
     */
    public String getSTEUS()
    {
        return sTEUS;
    }

    public void setSTEUS(String sTEUS)
    {
        this.sTEUS = sTEUS;
    }

    /**
     * @return 活动类型
     */
    public String getLARNT()
    {
        return lARNT;
    }

    public void setLARNT(String lARNT)
    {
        this.lARNT = lARNT;
    }

    /**
     * @return 所需能力数
     */
    public String getANZZL()
    {
        return aNZZL;
    }

    public void setANZZL(String aNZZL)
    {
        this.aNZZL = aNZZL;
    }

    /**
     * @return 活动中影响的工单
     */
    public String getARBEI()
    {
        return aRBEI;
    }

    public void setARBEI(String aRBEI)
    {
        this.aRBEI = aRBEI;
    }

    
    /**
     * @return 活动普遍持续时间
     */
    public String getDAUNO()
    {
        return dAUNO;
    }

    public void setDAUNO(String dAUNO)
    {
        this.dAUNO = dAUNO;
    }

    /**
     * @return 工作计量单位
     */
    public String getARBEH()
    {
        return aRBEH;
    }

    public void setARBEH(String aRBEH)
    {
        this.aRBEH = aRBEH;
    }

    /**
     * @return 最早计划开始日期
     */
    public String getFSAVD()
    {
        return fSAVD;
    }

    public void setFSAVD(String fSAVD)
    {
        this.fSAVD = fSAVD;
    }

    /**
     * @return 最早计划开始时间
     */
    public String getFSAVZ()
    {
        return fSAVZ;
    }

    public void setFSAVZ(String fSAVZ)
    {
        this.fSAVZ = fSAVZ;
    }

    /**
     * @return 最早计划完成日期
     */
    public String getFSEDD()
    {
        return fSEDD;
    }

    public void setFSEDD(String fSEDD)
    {
        this.fSEDD = fSEDD;
    }

    /**
     * @return 最早计划完成时间
     */
    public String getFSEDZ()
    {
        return fSEDZ;
    }

    public void setFSEDZ(String fSEDZ)
    {
        this.fSEDZ = fSEDZ;
    }

    /**
     * @return 工序完成确认号
     */
    public String getRUECK()
    {
        return rUECK;
    }

    public void setRUECK(String rUECK)
    {
        this.rUECK = rUECK;
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
