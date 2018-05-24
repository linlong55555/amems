package com.eray.pbs.util.idoc;

public class E2NTHDR
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
     * Notification No
     */
    public String qMNUM;
    /**
     * Notification Type
     */
    public String qMART;
    /**
     * Sales Order Number
     */
    public String vBELN;
    /**
     * Order Number
     */
    public String aUFNR;
    /**
     * System Status for Notification
     */
    public String sTTXT;
    /**
     * User Status Line
     */
    public String uSTXT;
    /**
     * Functional location label
     */
    public String sTRNO;
    /**
     * Equipment Number
     */
    public String eQUNR;
    /**
     * Assembly
     */
    public String bAUTL;
    /**
     * Short Text
     */
    public String qMTXT;
    /**
     * Priority
     */
    public String pRIOK;
    /**
     * Start of Malfunction (Date)
     */
    public String aUSVN;
    /**
     * Start of Malfunction (Time)
     */
    public String aUZTV;
    /**
     * Required start date
     */
    public String sTRMN;
    /**
     * Required Start Time
     */
    public String sTRUR;
    /**
     * Required End Date
     */
    public String lTRMN;
    /**
     * Required end time
     */
    public String lTRUR;
    /**
     * Maintenance Planning Plant
     */
    public String iWERK;
    /**
     * Work center
     */
    public String aRBPLE;
    /**
     * Maintenance plant
     */
    public String sWERK;
    /**
     * Planner Group for Customer Service and Plant Maintenance
     */
    public String iNGRP;
    /**
     * Location of maintenance object
     */
    public String sTORT;
    /**
     * Room
     */
    public String mSGRP;
    /**
     * Plant section
     */
    public String bEBER;
    /**
     * Company Code
     */
    public String bUKRS;
    /**
     * Business Area
     */
    public String gSBER;
    /**
     * Controlling Area
     */
    public String kOKRS;
    /**
     * Cost Center
     */
    public String kOSTL;
    /**
     * Standing order number
     */
    public String dAUFN;
    /**
     * Date of Notification
     */
    public String qMDAT;
    /**
     * Notification Time
     */
    public String mZEIT;
    /**
     * Department responsible
     */
    public String dEPTRESP;
    /**
     * Name for list displays
     */
    public String dEPTDESCR;
    /**
     * Breakdown Indicator
     */
    public String mSAUS;
    /**
     * Effect on Operation
     */
    public String aUSWK;
    /**
     * Catalog Profile
     */
    public String rBNR;
    /**
     * Language according to ISO 639
     */
    public String nOTIF_LANGU;
    /**
     * Long Material Number for Field BAUTL
     */
    public String bAUTL_EXTERNAL;
    /**
     * Version Number for Field BAUTL
     */
    public String bAUTL_VERSION;
    /**
     * External GUID for Field BAUTL
     */
    public String bAUTL_GUID;

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
     * @return 通知类型
     */
    public String getQMART()
    {
        return qMART;
    }

    public void setQMART(String qMART)
    {
        this.qMART = qMART;
    }

    /**
     * @return 合同单号
     */
    public String getVBELN()
    {
        return vBELN;
    }

    public void setVBELN(String vBELN)
    {
        this.vBELN = vBELN;
    }

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
     * @return 通知的系统状态
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
     * @return 部门编号
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
     * @return 装配
     */
    public String getBAUTL()
    {
        return bAUTL;
    }

    public void setBAUTL(String bAUTL)
    {
        this.bAUTL = bAUTL;
    }

    /**
     * @return 短文本
     */
    public String getQMTXT()
    {
        return qMTXT;
    }

    public void setQMTXT(String qMTXT)
    {
        this.qMTXT = qMTXT;
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
     * @return 故障发生日期
     */
    public String getAUSVN()
    {
        return aUSVN;
    }

    public void setAUSVN(String aUSVN)
    {
        this.aUSVN = aUSVN;
    }

    /**
     * @return 故障发生时间
     */
    public String getAUZTV()
    {
        return aUZTV;
    }

    public void setAUZTV(String aUZTV)
    {
        this.aUZTV = aUZTV;
    }

    /**
     * @return 要求开始日期
     */
    public String getSTRMN()
    {
        return sTRMN;
    }

    public void setSTRMN(String sTRMN)
    {
        this.sTRMN = sTRMN;
    }

    /**
     * @return 要求开始时间
     */
    public String getSTRUR()
    {
        return sTRUR;
    }

    public void setSTRUR(String sTRUR)
    {
        this.sTRUR = sTRUR;
    }

    /**
     * @return 要求结束日期
     */
    public String getLTRMN()
    {
        return lTRMN;
    }

    public void setLTRMN(String lTRMN)
    {
        this.lTRMN = lTRMN;
    }

    /**
     * @return 要求结束时间
     */
    public String getLTRUR()
    {
        return lTRUR;
    }

    public void setLTRUR(String lTRUR)
    {
        this.lTRUR = lTRUR;
    }

    /**
     * @return 维修计划工厂
     */
    public String getIWERK()
    {
        return iWERK;
    }

    public void setIWERK(String iWERK)
    {
        this.iWERK = iWERK;
    }

    /**
     * @return 工作中心
     */
    public String getARBPLE()
    {
        return aRBPLE;
    }

    public void setARBPLE(String aRBPLE)
    {
        this.aRBPLE = aRBPLE;
    }

    /**
     * @return 维修工厂
     */
    public String getSWERK()
    {
        return sWERK;
    }

    public void setSWERK(String sWERK)
    {
        this.sWERK = sWERK;
    }

    /**
     * @return 客户服务和工厂维修的计划组
     */
    public String getINGRP()
    {
        return iNGRP;
    }

    public void setINGRP(String iNGRP)
    {
        this.iNGRP = iNGRP;
    }

    /**
     * @return 维修对象的位置
     */
    public String getSTORT()
    {
        return sTORT;
    }

    public void setSTORT(String sTORT)
    {
        this.sTORT = sTORT;
    }

    /**
     * @return 房间号
     */
    public String getMSGRP()
    {
        return mSGRP;
    }

    public void setMSGRP(String mSGRP)
    {
        this.mSGRP = mSGRP;
    }

    /**
     * @return 工厂部门
     */
    public String getBEBER()
    {
        return bEBER;
    }

    public void setBEBER(String bEBER)
    {
        this.bEBER = bEBER;
    }

    /**
     * @return 公司编码
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
     * @return 成本中心
     */
    public String getKOSTL()
    {
        return kOSTL;
    }

    public void setKOSTL(String kOSTL)
    {
        this.kOSTL = kOSTL;
    }

    /**
     * @return 固定的工单号
     */
    public String getDAUFN()
    {
        return dAUFN;
    }

    public void setDAUFN(String dAUFN)
    {
        this.dAUFN = dAUFN;
    }

    /**
     * @return 通知日期
     */
    public String getQMDAT()
    {
        return qMDAT;
    }

    public void setQMDAT(String qMDAT)
    {
        this.qMDAT = qMDAT;
    }

    /**
     * @return 通知时间
     */
    public String getMZEIT()
    {
        return mZEIT;
    }

    public void setMZEIT(String mZEIT)
    {
        this.mZEIT = mZEIT;
    }

    /**
     * @return 责任部门
     */
    public String getDEPTRESP()
    {
        return dEPTRESP;
    }

    public void setDEPTRESP(String dEPTRESP)
    {
        this.dEPTRESP = dEPTRESP;
    }

    /**
     * @return 清单名称
     */
    public String getDEPTDESCR()
    {
        return dEPTDESCR;
    }

    public void setDEPTDESCR(String dEPTDESCR)
    {
        this.dEPTDESCR = dEPTDESCR;
    }

    /**
     * @return 故障指示者
     */
    public String getMSAUS()
    {
        return mSAUS;
    }

    public void setMSAUS(String mSAUS)
    {
        this.mSAUS = mSAUS;
    }

    /**
     * @return 对工序的影响
     */
    public String getAUSWK()
    {
        return aUSWK;
    }

    public void setAUSWK(String aUSWK)
    {
        this.aUSWK = aUSWK;
    }

    /**
     * @return 目录概览
     */
    public String getRBNR()
    {
        return rBNR;
    }

    public void setRBNR(String rBNR)
    {
        this.rBNR = rBNR;
    }

    /**
     * @return 根据ISO 639编码
     */
    public String getNOTIF_LANGU()
    {
        return nOTIF_LANGU;
    }

    public void setNOTIF_LANGU(String nOTIF_LANGU)
    {
        this.nOTIF_LANGU = nOTIF_LANGU;
    }

    /**
     * @return 现场物料长编号
     */
    public String getBAUTL_EXTERNAL()
    {
        return bAUTL_EXTERNAL;
    }

    public void setBAUTL_EXTERNAL(String bAUTL_EXTERNAL)
    {
        this.bAUTL_EXTERNAL = bAUTL_EXTERNAL;
    }

    /**
     * @return 现场版本号
     */
    public String getBAUTL_VERSION()
    {
        return bAUTL_VERSION;
    }

    public void setBAUTL_VERSION(String bAUTL_VERSION)
    {
        this.bAUTL_VERSION = bAUTL_VERSION;
    }

    /**
     * @return 现场外部GUID
     */
    public String getBAUTL_GUID()
    {
        return bAUTL_GUID;
    }

    public void setBAUTL_GUID(String bAUTL_GUID)
    {
        this.bAUTL_GUID = bAUTL_GUID;
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
