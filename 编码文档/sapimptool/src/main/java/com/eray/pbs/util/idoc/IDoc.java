package com.eray.pbs.util.idoc;

import java.util.ArrayList;
import java.util.List;

public class IDoc
{
    private DataRecord dataRecord;
    private E2ORHDR e2ORHDR;
    private ZZREVISON zZREVISON;
    private ZE1ORHDR_EXT zE1ORHDR_EXT;
    private ZZCARDNO zZCARDNO;
    private List<E2ORHDR_LTXT> e2ORHDR_LTXTList;
    private E2ORTOB e2ORTOB;
    private E2ORTOB_PARTNR e2ORTOB_PARTNR;
    private E2ORTOB_PARTNR_ADR e2ORTOB_PARTNR_ADR;
    private List<E2OROPR> e2OROPRList;
    private E2NTHDR e2NTHDR;
    
    public IDoc(){
        dataRecord=new DataRecord();
        e2ORHDR=new E2ORHDR();
        zZREVISON=new ZZREVISON();
        zE1ORHDR_EXT=new ZE1ORHDR_EXT();
        zZCARDNO=new ZZCARDNO();
        e2ORHDR_LTXTList=new ArrayList<E2ORHDR_LTXT>();
        e2ORTOB=new E2ORTOB();
        e2ORTOB_PARTNR=new E2ORTOB_PARTNR();
        e2ORTOB_PARTNR_ADR=new E2ORTOB_PARTNR_ADR();
        e2OROPRList=new ArrayList<E2OROPR>();
        e2NTHDR=new E2NTHDR();
    }
    
    public DataRecord getDataRecord()
    {
        return dataRecord;
    }
    public void setDataRecord(DataRecord dataRecord)
    {
        this.dataRecord = dataRecord;
    }
    public E2ORHDR getE2ORHDR()
    {
        return e2ORHDR;
    }
    public void setE2ORHDR(E2ORHDR e2ORHDR)
    {
        this.e2ORHDR = e2ORHDR;
    }
    public ZZREVISON getZZREVISON()
    {
        return zZREVISON;
    }
    public void setZZREVISON(ZZREVISON zZREVISON)
    {
        this.zZREVISON = zZREVISON;
    }
    public ZE1ORHDR_EXT getZE1ORHDR_EXT()
    {
        return zE1ORHDR_EXT;
    }
    public void setZE1ORHDR_EXT(ZE1ORHDR_EXT zE1ORHDR_EXT)
    {
        this.zE1ORHDR_EXT = zE1ORHDR_EXT;
    }
    public ZZCARDNO getZZCARDNO()
    {
        return zZCARDNO;
    }
    public void setZZCARDNO(ZZCARDNO zZCARDNO)
    {
        this.zZCARDNO = zZCARDNO;
    }
    public List<E2ORHDR_LTXT> getE2ORHDR_LTXTList()
    {
        return e2ORHDR_LTXTList;
    }
    public void setE2ORHDR_LTXTList(List<E2ORHDR_LTXT> e2ORHDR_LTXTList)
    {
        this.e2ORHDR_LTXTList = e2ORHDR_LTXTList;
    }
    public E2ORTOB getE2ORTOB()
    {
        return e2ORTOB;
    }
    public void setE2ORTOB(E2ORTOB e2ortob)
    {
        e2ORTOB = e2ortob;
    }
    public E2ORTOB_PARTNR getE2ORTOB_PARTNR()
    {
        return e2ORTOB_PARTNR;
    }
    public void setE2ORTOB_PARTNR(E2ORTOB_PARTNR e2ortob_PARTNR)
    {
        e2ORTOB_PARTNR = e2ortob_PARTNR;
    }
    public E2ORTOB_PARTNR_ADR getE2ORTOB_PARTNR_ADR()
    {
        return e2ORTOB_PARTNR_ADR;
    }
    public void setE2ORTOB_PARTNR_ADR(E2ORTOB_PARTNR_ADR e2ortob_PARTNR_ADR)
    {
        e2ORTOB_PARTNR_ADR = e2ortob_PARTNR_ADR;
    }
    public List<E2OROPR> getE2OROPRList()
    {
        return e2OROPRList;
    }
    public void setE2OROPRList(List<E2OROPR> e2oroprList)
    {
        e2OROPRList = e2oroprList;
    }
    public E2NTHDR getE2NTHDR()
    {
        return e2NTHDR;
    }
    public void setE2NTHDR(E2NTHDR e2nthdr)
    {
        e2NTHDR = e2nthdr;
    }
}
