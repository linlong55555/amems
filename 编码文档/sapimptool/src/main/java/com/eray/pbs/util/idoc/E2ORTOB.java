package com.eray.pbs.util.idoc;

public class E2ORTOB
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
     * Functional location label
     */
    private String sTRNO;
    /**
     * Description of functional location
     */
    private String pLTXT;
    /**
     * Equipment Number
     */
    private String eQUNR;
    /**
     * Description of technical object
     */
    private String eQKTX;
    /**
     * Assembly
     */
    private String bAUTL;
    /**
     * Description of PM Assembly
     */
    private String bAUTX;
    /**
     * Location of maintenance object
     */
    private String sTORT;
    /**
     * Sort field
     */
    private String eQFNR;
    /**
     * Serial number
     */
    private String sERNR;
    /**
     * Material Number
     */
    private String mATNR;
    /**
     * Start-up Date of the Technical Object
     */
    private String dATAB;
    /**
     * Standing order number
     */
    private String dAUFN;
    /**
     * Manufacturer of asset
     */
    private String hERST;
    /**
     * Country of manufacture
     */
    private String hERLD;
    /**
     * Year of construction
     */
    private String bAUJJ;
    /**
     * Month of construction
     */
    private String bAUMM;
    /**
     * Vendor number
     */
    private String eLIEF;
    /**
     * Name of vendor
     */
    private String vNAME;
    /**
     * Acquisition date
     */
    private String aNSDT;
    /**
     * Manufacturer model number
     */
    private String tYPBZ;
    /**
     * Manufacturer part number
     */
    private String mAPAR;
    /**
     * Manufacturer serial number
     */
    private String sERGE;
    /**
     * Language according to ISO 639
     */
    private String tOB_LANGU;
    /**
     * Long Material Number for Field BAUTL
     */
    private String bAUTL_EXTERNAL;
    /**
     * Version Number for Field BAUTL
     */
    private String bAUTL_VERSION;
    /**
     * External GUID for Field BAUTL
     */
    private String bAUTL_GUID;
    /**
     * Material Number
     */
    private String mATNR_EXTERNAL;
    /**
     * Version Number for MATNR Field
     */
    private String mATNR_VERSION;
    /**
     * External GUID for MATNR Field
     */
    private String mATNR_GUID;

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
     * @return 描述
     */
    public String getPLTXT()
    {
        return pLTXT;
    }

    public void setPLTXT(String pLTXT)
    {
        this.pLTXT = pLTXT;
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
     * @return 技术对象描述
     */
    public String getEQKTX()
    {
        return eQKTX;
    }

    public void setEQKTX(String eQKTX)
    {
        this.eQKTX = eQKTX;
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
     * @return PM装配描述
     */
    public String getBAUTX()
    {
        return bAUTX;
    }

    public void setBAUTX(String bAUTX)
    {
        this.bAUTX = bAUTX;
    }

    /**
     * @return 维修物位置
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
     * @return 查询领域
     */
    public String getEQFNR()
    {
        return eQFNR;
    }

    public void setEQFNR(String eQFNR)
    {
        this.eQFNR = eQFNR;
    }

    /**
     * @return 串号
     */
    public String getSERNR()
    {
        return sERNR;
    }

    public void setSERNR(String sERNR)
    {
        this.sERNR = sERNR;
    }

    /**
     * @return 材料号
     */
    public String getMATNR()
    {
        return mATNR;
    }

    public void setMATNR(String mATNR)
    {
        this.mATNR = mATNR;
    }

    /**
     * @return 技术开始日期
     */
    public String getDATAB()
    {
        return dATAB;
    }

    public void setDATAB(String dATAB)
    {
        this.dATAB = dATAB;
    }

    /**
     * @return 固定单号
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
     * @return 生产商
     */
    public String getHERST()
    {
        return hERST;
    }

    public void setHERST(String hERST)
    {
        this.hERST = hERST;
    }

    /**
     * @return 产地
     */
    public String getHERLD()
    {
        return hERLD;
    }

    public void setHERLD(String hERLD)
    {
        this.hERLD = hERLD;
    }

    /**
     * @return 生产年份
     */
    public String getBAUJJ()
    {
        return bAUJJ;
    }

    public void setBAUJJ(String bAUJJ)
    {
        this.bAUJJ = bAUJJ;
    }

    /**
     * @return 生产月
     */
    public String getBAUMM()
    {
        return bAUMM;
    }

    public void setBAUMM(String bAUMM)
    {
        this.bAUMM = bAUMM;
    }

    /**
     * @return 供应商号
     */
    public String getELIEF()
    {
        return eLIEF;
    }

    public void setELIEF(String eLIEF)
    {
        this.eLIEF = eLIEF;
    }

    /**
     * @return 供应商名称
     */
    public String getVNAME()
    {
        return vNAME;
    }

    public void setVNAME(String vNAME)
    {
        this.vNAME = vNAME;
    }

    /**
     * @return 购买日期
     */
    public String getANSDT()
    {
        return aNSDT;
    }

    public void setANSDT(String aNSDT)
    {
        this.aNSDT = aNSDT;
    }

    /**
     * @return 生产商型号
     */
    public String getTYPBZ()
    {
        return tYPBZ;
    }

    public void setTYPBZ(String tYPBZ)
    {
        this.tYPBZ = tYPBZ;
    }

    /**
     * @return 生产商部件号
     */
    public String getMAPAR()
    {
        return mAPAR;
    }

    public void setMAPAR(String mAPAR)
    {
        this.mAPAR = mAPAR;
    }

    /**
     * @return 生产商串号
     */
    public String getSERGE()
    {
        return sERGE;
    }

    public void setSERGE(String sERGE)
    {
        this.sERGE = sERGE;
    }

    /**
     * @return 根据ISO 639编码
     */
    public String getTOB_LANGU()
    {
        return tOB_LANGU;
    }

    public void setTOB_LANGU(String tOB_LANGU)
    {
        this.tOB_LANGU = tOB_LANGU;
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
     * @return 材料号
     */
    public String getMATNR_EXTERNAL()
    {
        return mATNR_EXTERNAL;
    }

    public void setMATNR_EXTERNAL(String mATNR_EXTERNAL)
    {
        this.mATNR_EXTERNAL = mATNR_EXTERNAL;
    }

    /**
     * @return MATNR现场版本号
     */
    public String getMATNR_VERSION()
    {
        return mATNR_VERSION;
    }

    public void setMATNR_VERSION(String mATNR_VERSION)
    {
        this.mATNR_VERSION = mATNR_VERSION;
    }

    /**
     * @return MATNR现场外部GUID
     */
    public String getMATNR_GUID()
    {
        return mATNR_GUID;
    }

    public void setMATNR_GUID(String mATNR_GUID)
    {
        this.mATNR_GUID = mATNR_GUID;
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
