package com.eray.pbs.util.idoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.eray.util.io.UnicodeReader;

public class IDocHelper
{
    private static String segment;
    private static String mandt;
    private static String docnum;
    private static String segnum;
    private static String psgnum;
    private static String hlevel;
    private static String sdata;
    private static String temp;
    
    public static IDoc parseTxt(File iDocFile) throws IOException
    {
        if (iDocFile == null)
        {
            return null;
        }
        BufferedReader in = new BufferedReader(new UnicodeReader(new FileInputStream(iDocFile), Charset
                .defaultCharset().name()));
        int start = 0;
        String line;
        IDoc iDoc = new IDoc();
        while ((line = in.readLine()) != null)
        {
            if (start == 0)
            {
                iDoc.getDataRecord().setTABNAM(line.substring(0, 10).trim());
                iDoc.getDataRecord().setMANDT(line.substring(10, 13).trim());
                iDoc.getDataRecord().setDOCNUM(line.substring(13, 29).trim());
                iDoc.getDataRecord().setSEGNUM(line.substring(29, 35).trim());
                iDoc.getDataRecord().setSEGNAM(line.substring(35, 45).trim());
                iDoc.getDataRecord().setPSGNUM(line.substring(45, 51).trim());
                iDoc.getDataRecord().setHLEVEL(line.substring(51, 53).trim());
                iDoc.getDataRecord().setDTINT2(line.substring(53, 55).trim());
                iDoc.getDataRecord().setSDATA(line.substring(55).trim());
                start++;
                continue;
            }
            if (line.trim().length() == 0)
            {
                continue;
            }
            TransToIdocData(line, iDoc);
        }
        in.close();
        return iDoc;
    }

    private static void TransToIdocData(String line, IDoc iDoc)
    {
        if (line.length() < 63)
        {
            return;
        }
        segment = line.substring(0, 30).trim();
        mandt = line.substring(30, 33).trim();
        docnum = line.substring(33, 49).trim();
        segnum = line.substring(49, 55).trim();
        psgnum = line.substring(55, 61).trim();
        hlevel = line.substring(61, 63).trim();
        sdata = line.substring(63);

        if (segment.startsWith("E2ORHDR"))
        {
            if (segment.startsWith("E2ORHDR_LTXT"))
            {
                E2ORHDR_LTXT e2ORHDR_LTXT = new E2ORHDR_LTXT();
                e2ORHDR_LTXT.setSEGMENT(segment);
                e2ORHDR_LTXT.setMANDT(mandt);
                e2ORHDR_LTXT.setDOCNUM(docnum);
                e2ORHDR_LTXT.setSEGNUM(segnum);
                e2ORHDR_LTXT.setPSGNUM(psgnum);
                e2ORHDR_LTXT.setHLEVEL(hlevel);
                e2ORHDR_LTXT.setSDATA(sdata);
                if (sdata.length() >= 2)
                {
                    e2ORHDR_LTXT.setTDFORMAT(sdata.substring(0, 2).trim());
                    e2ORHDR_LTXT.setTDLINE(sdata.substring(2).trim());
                }
                iDoc.getE2ORHDR_LTXTList().add(e2ORHDR_LTXT);
            } else
            {
                E2ORHDR e2ORHDR = new E2ORHDR();
                e2ORHDR.setSEGMENT(segment);
                e2ORHDR.setMANDT(mandt);
                e2ORHDR.setDOCNUM(docnum);
                e2ORHDR.setSEGNUM(segnum);
                e2ORHDR.setPSGNUM(psgnum);
                e2ORHDR.setHLEVEL(hlevel);
                e2ORHDR.setSDATA(sdata);
                if (sdata.length() >= 322)
                {
                    e2ORHDR.setAUFNR(sdata.substring(0, 12).trim());
                    e2ORHDR.setSTRNO(sdata.substring(12, 52).trim());
                    e2ORHDR.setEQUNR(sdata.substring(52, 70).trim());
                    e2ORHDR.setQMNUM(sdata.substring(70, 82).trim());
                    e2ORHDR.setKTEXT(sdata.substring(82, 122).trim());
                    e2ORHDR.setAUART(sdata.substring(122, 126).trim());
                    e2ORHDR.setSTTXT(sdata.substring(126, 166).trim());
                    e2ORHDR.setUSTXT(sdata.substring(166, 206).trim());
                    e2ORHDR.setILART(sdata.substring(206, 209).trim());
                    e2ORHDR.setBUKRS(sdata.substring(209, 213).trim());
                    e2ORHDR.setKOKRS(sdata.substring(213, 217).trim());
                    e2ORHDR.setGSBER(sdata.substring(217, 221).trim());
                    e2ORHDR.setECOST(sdata.substring(221, 234).trim());
                    e2ORHDR.setWAERS(sdata.substring(234, 239).trim());
                    e2ORHDR.setPRIOK(sdata.substring(239, 240).trim());
                    e2ORHDR.setTERKZ(sdata.substring(240, 241).trim());
                    e2ORHDR.setGSTRS(sdata.substring(241, 249).trim());
                    e2ORHDR.setGSUZS(sdata.substring(249, 255).trim());
                    e2ORHDR.setGLTRS(sdata.substring(255, 263).trim());
                    e2ORHDR.setGLUZS(sdata.substring(263, 269).trim());
                    e2ORHDR.setGSTRP(sdata.substring(269, 277).trim());
                    e2ORHDR.setGSUZP(sdata.substring(277, 283).trim());
                    e2ORHDR.setGLTRP(sdata.substring(283, 291).trim());
                    e2ORHDR.setGLUZP(sdata.substring(291, 297).trim());
                    e2ORHDR.setARBPL(sdata.substring(297, 305).trim());
                    e2ORHDR.setMAUFNR(sdata.substring(305, 317).trim());
                    e2ORHDR.setWERKS(sdata.substring(317, 321).trim());
                    e2ORHDR.setANLZU(sdata.substring(321, 322).trim());
                    e2ORHDR.setLANGU_ISO(sdata.substring(322).trim());
                }
                iDoc.setE2ORHDR(e2ORHDR);
            }
        } else if (segment.startsWith("ZZREVISON"))
        {
            ZZREVISON zZREVISON = new ZZREVISON();
            zZREVISON.setSEGMENT(segment);
            zZREVISON.setMANDT(mandt);
            zZREVISON.setDOCNUM(docnum);
            zZREVISON.setSEGNUM(segnum);
            zZREVISON.setPSGNUM(psgnum);
            zZREVISON.setHLEVEL(hlevel);
            zZREVISON.setSDATA(sdata);
            zZREVISON.setREVNR(sdata.trim());
            iDoc.setZZREVISON(zZREVISON);
        } else if (segment.startsWith("ZE1ORHDR_EXT"))
        {
            ZE1ORHDR_EXT zE1ORHDR_EXT = new ZE1ORHDR_EXT();
            zE1ORHDR_EXT.setSEGMENT(segment);
            zE1ORHDR_EXT.setMANDT(mandt);
            zE1ORHDR_EXT.setDOCNUM(docnum);
            zE1ORHDR_EXT.setSEGNUM(segnum);
            zE1ORHDR_EXT.setPSGNUM(psgnum);
            zE1ORHDR_EXT.setHLEVEL(hlevel);
            zE1ORHDR_EXT.setSDATA(sdata);
            zE1ORHDR_EXT.setTPLKZ(sdata.trim());
            iDoc.setZE1ORHDR_EXT(zE1ORHDR_EXT);
        } else if (segment.startsWith("ZZCARDNO"))
        {
            ZZCARDNO zZCARDNO = new ZZCARDNO();
            zZCARDNO.setSEGMENT(segment);
            zZCARDNO.setMANDT(mandt);
            zZCARDNO.setDOCNUM(docnum);
            zZCARDNO.setSEGNUM(segnum);
            zZCARDNO.setPSGNUM(psgnum);
            zZCARDNO.setHLEVEL(hlevel);
            zZCARDNO.setSDATA(sdata);
            if (sdata.length() >= 45)
            {
                zZCARDNO.setZZCARDNO(sdata.substring(0, 40).trim());
                zZCARDNO.setZZCONTRNO(sdata.substring(40, 45).trim());
                zZCARDNO.setZZNEWCARDNO(sdata.substring(45).trim());
            }
            iDoc.setZZCARDNO(zZCARDNO);
        } else if (segment.startsWith("E2ORTOB"))
        {
            if (segment.startsWith("E2ORTOB_PARTNR"))
            {
                if (segment.startsWith("E2ORTOB_PARTNR_ADR"))
                {
                    E2ORTOB_PARTNR_ADR e2ORTOB_PARTNR_ADR = new E2ORTOB_PARTNR_ADR();
                    e2ORTOB_PARTNR_ADR.setSEGMENT(segment);
                    e2ORTOB_PARTNR_ADR.setMANDT(mandt);
                    e2ORTOB_PARTNR_ADR.setDOCNUM(docnum);
                    e2ORTOB_PARTNR_ADR.setSEGNUM(segnum);
                    e2ORTOB_PARTNR_ADR.setPSGNUM(psgnum);
                    e2ORTOB_PARTNR_ADR.setHLEVEL(hlevel);
                    e2ORTOB_PARTNR_ADR.setSDATA(sdata);
                    if (sdata.length() >= 486)
                    {
                        e2ORTOB_PARTNR_ADR.setTITLE_MEDI(sdata.substring(0, 20).trim());
                        e2ORTOB_PARTNR_ADR.setNAME1(sdata.substring(20, 60).trim());
                        e2ORTOB_PARTNR_ADR.setNAME2(sdata.substring(60, 100).trim());
                        e2ORTOB_PARTNR_ADR.setNAME3(sdata.substring(100, 140).trim());
                        e2ORTOB_PARTNR_ADR.setNAME4(sdata.substring(140, 180).trim());
                        e2ORTOB_PARTNR_ADR.setHOUSE_NUM1(sdata.substring(180, 190).trim());
                        e2ORTOB_PARTNR_ADR.setSTREET(sdata.substring(190, 250).trim());
                        e2ORTOB_PARTNR_ADR.setCITY1(sdata.substring(250, 290).trim());
                        e2ORTOB_PARTNR_ADR.setCITY2(sdata.substring(290, 330).trim());
                        e2ORTOB_PARTNR_ADR.setREGION(sdata.substring(330, 333).trim());
                        e2ORTOB_PARTNR_ADR.setPOST_CODE1(sdata.substring(333, 343).trim());
                        e2ORTOB_PARTNR_ADR.setCOUNTRY(sdata.substring(343, 346).trim());
                        e2ORTOB_PARTNR_ADR.setTEL_NUMBER(sdata.substring(346, 376).trim());
                        e2ORTOB_PARTNR_ADR.setTEL_EXTENS(sdata.substring(376, 386).trim());
                        e2ORTOB_PARTNR_ADR.setSTR_SUPPL1(sdata.substring(386, 426).trim());
                        e2ORTOB_PARTNR_ADR.setSTR_SUPPL2(sdata.substring(426, 466).trim());
                        e2ORTOB_PARTNR_ADR.setSORT1(sdata.substring(466, 486).trim());
                        // e2ORTOB_PARTNR_ADR.setSORT2(sdata.substring(486,
                        // 506).trim());
                        // e2ORTOB_PARTNR_ADR.setTIME_ZONE(sdata.substring(506,
                        // 512).trim());
                        // e2ORTOB_PARTNR_ADR.setNAME_CO(sdata.substring(512,
                        // 552).trim());
                        // e2ORTOB_PARTNR_ADR.setLOCATION(sdata.substring(552,
                        // 592).trim());
                        // e2ORTOB_PARTNR_ADR.setFLOOR(sdata.substring(592,
                        // 602).trim());
                        // e2ORTOB_PARTNR_ADR.setROOMNUMBER(sdata.substring(602,
                        // 612).trim());
                        // e2ORTOB_PARTNR_ADR.setBUILDING(sdata.substring(612,
                        // 632).trim());
                        // e2ORTOB_PARTNR_ADR.setHOUSE_NUM2(sdata.substring(632,
                        // 642).trim());
                        e2ORTOB_PARTNR_ADR.setLANGU_ISO(sdata.substring(486).trim());
                    }
                    iDoc.setE2ORTOB_PARTNR_ADR(e2ORTOB_PARTNR_ADR);
                } else
                {
                    E2ORTOB_PARTNR e2ORTOB_PARTNR = new E2ORTOB_PARTNR();
                    e2ORTOB_PARTNR.setSEGMENT(segment);
                    e2ORTOB_PARTNR.setMANDT(mandt);
                    e2ORTOB_PARTNR.setDOCNUM(docnum);
                    e2ORTOB_PARTNR.setSEGNUM(segnum);
                    e2ORTOB_PARTNR.setPSGNUM(psgnum);
                    e2ORTOB_PARTNR.setHLEVEL(hlevel);
                    e2ORTOB_PARTNR.setSDATA(sdata);
                    if (sdata.length() >= 34)
                    {
                        e2ORTOB_PARTNR.setPARVW(sdata.substring(0, 2).trim());
                        temp=sdata.substring(2, 14).trim();
                        if(temp!=null && !temp.equals("")){
                            while(temp.startsWith("0")){
                                temp=temp.substring(1);
                            }
                        }
                        e2ORTOB_PARTNR.setPARNR(temp);
                        e2ORTOB_PARTNR.setVTEXT(sdata.substring(14, 34).trim());
                        e2ORTOB_PARTNR.setVTEXT_LANGU(sdata.substring(34).trim());
                        // e2ORTOB_PARTNR.setC_NAME1(sdata.substring(36,
                        // 71).trim());
                        // e2ORTOB_PARTNR.setC_NAMEV1(sdata.substring(71,
                        // 106).trim());
                        // e2ORTOB_PARTNR.setC_TELF1(sdata.substring(106,
                        // 122).trim());
                        // e2ORTOB_PARTNR.setC_FUNC1(sdata.substring(122,
                        // 142).trim());
                        // e2ORTOB_PARTNR.setC_LANG1(sdata.substring(142,
                        // 144).trim());
                        // e2ORTOB_PARTNR.setC_NAME2(sdata.substring(144,
                        // 179).trim());
                        // e2ORTOB_PARTNR.setC_NAMEV2(sdata.substring(179,
                        // 214).trim());
                        // e2ORTOB_PARTNR.setC_TELF2(sdata.substring(214,
                        // 230).trim());
                        // e2ORTOB_PARTNR.setC_FUNC2(sdata.substring(230,
                        // 250).trim());
                        // e2ORTOB_PARTNR.setC_LANG2(sdata.substring(250).trim());
                    }
                    iDoc.setE2ORTOB_PARTNR(e2ORTOB_PARTNR);
                }
            } else if (segment.startsWith("E2ORTOB_MSRPOINT"))
            {

            } else if (segment.startsWith("E2ORTOB_MSRREADING"))
            {

            } else if (segment.startsWith("E2ORTOB_ADR"))
            {

            } else
            {
                E2ORTOB e2ORTOB = new E2ORTOB();
                e2ORTOB.setSEGMENT(segment);
                e2ORTOB.setMANDT(mandt);
                e2ORTOB.setDOCNUM(docnum);
                e2ORTOB.setSEGNUM(segnum);
                e2ORTOB.setPSGNUM(psgnum);
                e2ORTOB.setHLEVEL(hlevel);
                e2ORTOB.setSDATA(sdata);
                if (sdata.length() >= 200)
                {
                    e2ORTOB.setSTRNO(sdata.substring(0, 40).trim());
                    e2ORTOB.setPLTXT(sdata.substring(40, 80).trim());
                    e2ORTOB.setEQUNR(sdata.substring(80, 98).trim());
                    e2ORTOB.setEQKTX(sdata.substring(98, 138).trim());
                    e2ORTOB.setBAUTL(sdata.substring(138, 156).trim());
                    e2ORTOB.setBAUTX(sdata.substring(156, 196).trim());
                    e2ORTOB.setSTORT(sdata.substring(196, 206).trim());
                    e2ORTOB.setEQFNR(sdata.substring(206).trim());
                    // e2ORTOB.setSERNR(sdata.substring(236, 254).trim());
                    // e2ORTOB.setMATNR(sdata.substring(254, 272).trim());
                    // e2ORTOB.setDATAB(sdata.substring(272, 280).trim());
                    // e2ORTOB.setDAUFN(sdata.substring(280, 292).trim());
                    // e2ORTOB.setHERST(sdata.substring(292, 322).trim());
                    // e2ORTOB.setHERLD(sdata.substring(322, 325).trim());
                    // e2ORTOB.setBAUJJ(sdata.substring(325, 329).trim());
                    // e2ORTOB.setBAUMM(sdata.substring(329, 331).trim());
                    // e2ORTOB.setELIEF(sdata.substring(331, 341).trim());
                    // e2ORTOB.setVNAME(sdata.substring(341, 376).trim());
                    // e2ORTOB.setANSDT(sdata.substring(376, 384).trim());
                    // e2ORTOB.setTYPBZ(sdata.substring(384, 404).trim());
                    // e2ORTOB.setMAPAR(sdata.substring(404, 434).trim());
                    // e2ORTOB.setSERGE(sdata.substring(434, 464).trim());
                    // e2ORTOB.setTOB_LANGU(sdata.substring(212).trim());
                    // e2ORTOB.setBAUTL_EXTERNAL(sdata.substring(466,
                    // 506).trim());
                    // e2ORTOB.setBAUTL_VERSION(sdata.substring(506,
                    // 516).trim());
                    // e2ORTOB.setBAUTL_GUID(sdata.substring(516, 548).trim());
                    // e2ORTOB.setMATNR_EXTERNAL(sdata.substring(548,
                    // 588).trim());
                    // e2ORTOB.setMATNR_VERSION(sdata.substring(588,
                    // 598).trim());
                    // e2ORTOB.setMATNR_GUID(sdata.substring(598).trim());
                }
                iDoc.setE2ORTOB(e2ORTOB);
            }
        } else if (segment.startsWith("E2OROPR"))
        {
            E2OROPR e2OROPR = new E2OROPR();
            e2OROPR.setSEGMENT(segment);
            e2OROPR.setMANDT(mandt);
            e2OROPR.setDOCNUM(docnum);
            e2OROPR.setSEGNUM(segnum);
            e2OROPR.setPSGNUM(psgnum);
            e2OROPR.setHLEVEL(hlevel);
            e2OROPR.setSDATA(sdata);
            if (sdata.length() >= 166)
            {
                e2OROPR.setVORNR(sdata.substring(0, 4).trim());
                e2OROPR.setUVORN(sdata.substring(4, 8).trim());
                e2OROPR.setLTXA1(sdata.substring(8, 48).trim());
                e2OROPR.setARBPL(sdata.substring(48, 56).trim());
                e2OROPR.setSTEUS(sdata.substring(56, 60).trim());
                e2OROPR.setLARNT(sdata.substring(60, 66).trim());
                e2OROPR.setANZZL(sdata.substring(66, 69).trim());
                e2OROPR.setARBEI(sdata.substring(69, 78).trim());
                e2OROPR.setDAUNO(sdata.substring(78, 85).trim());
                e2OROPR.setARBEH(sdata.substring(85, 88).trim());
                e2OROPR.setFSAVD(sdata.substring(88, 96).trim());
                e2OROPR.setFSAVZ(sdata.substring(96, 102).trim());
                e2OROPR.setFSEDD(sdata.substring(102, 110).trim());
                e2OROPR.setFSEDZ(sdata.substring(110, 116).trim());
                e2OROPR.setRUECK(sdata.substring(116, 126).trim());
                e2OROPR.setSTTXT(sdata.substring(126, 166).trim());
                e2OROPR.setUSTXT(sdata.substring(166).trim());
            }
            iDoc.getE2OROPRList().add(e2OROPR);
        } else if (segment.startsWith("E2NTHDR"))
        {
            E2NTHDR e2NTHDR = new E2NTHDR();
            e2NTHDR.setSEGMENT(segment);
            e2NTHDR.setMANDT(mandt);
            e2NTHDR.setDOCNUM(docnum);
            e2NTHDR.setSEGNUM(segnum);
            e2NTHDR.setPSGNUM(psgnum);
            e2NTHDR.setHLEVEL(hlevel);
            e2NTHDR.setSDATA(sdata);
            if (sdata.length() >= 421)
            {
                e2NTHDR.setQMNUM(sdata.substring(0, 12).trim());
                e2NTHDR.setQMART(sdata.substring(12, 14).trim());
                e2NTHDR.setVBELN(sdata.substring(14, 24).trim());
                e2NTHDR.setAUFNR(sdata.substring(24, 36).trim());
                e2NTHDR.setSTTXT(sdata.substring(36, 76).trim());
                e2NTHDR.setUSTXT(sdata.substring(76, 116).trim());
                e2NTHDR.setSTRNO(sdata.substring(116, 156).trim());
                e2NTHDR.setEQUNR(sdata.substring(156, 174).trim());
                e2NTHDR.setBAUTL(sdata.substring(174, 192).trim());
                e2NTHDR.setQMTXT(sdata.substring(192, 232).trim());
                e2NTHDR.setPRIOK(sdata.substring(232, 233).trim());
                e2NTHDR.setAUSVN(sdata.substring(233, 241).trim());
                e2NTHDR.setAUZTV(sdata.substring(241, 247).trim());
                e2NTHDR.setSTRMN(sdata.substring(247, 255).trim());
                e2NTHDR.setSTRUR(sdata.substring(255, 261).trim());
                e2NTHDR.setLTRMN(sdata.substring(261, 269).trim());
                e2NTHDR.setLTRUR(sdata.substring(269, 275).trim());
                e2NTHDR.setIWERK(sdata.substring(275, 279).trim());
                e2NTHDR.setARBPLE(sdata.substring(279, 287).trim());
                e2NTHDR.setSWERK(sdata.substring(287, 291).trim());
                e2NTHDR.setINGRP(sdata.substring(291, 294).trim());
                e2NTHDR.setSTORT(sdata.substring(294, 304).trim());
                e2NTHDR.setMSGRP(sdata.substring(304, 312).trim());
                e2NTHDR.setBEBER(sdata.substring(312, 315).trim());
                e2NTHDR.setBUKRS(sdata.substring(315, 319).trim());
                e2NTHDR.setGSBER(sdata.substring(319, 323).trim());
                e2NTHDR.setKOKRS(sdata.substring(323, 327).trim());
                e2NTHDR.setKOSTL(sdata.substring(327, 337).trim());
                e2NTHDR.setDAUFN(sdata.substring(337, 349).trim());
                e2NTHDR.setQMDAT(sdata.substring(349, 357).trim());
                e2NTHDR.setMZEIT(sdata.substring(357, 363).trim());
                e2NTHDR.setDEPTRESP(sdata.substring(363, 375).trim());
                e2NTHDR.setDEPTDESCR(sdata.substring(375, 410).trim());
                e2NTHDR.setMSAUS(sdata.substring(410, 411).trim());
                e2NTHDR.setAUSWK(sdata.substring(411, 412).trim());
                e2NTHDR.setRBNR(sdata.substring(412, 421).trim());
                e2NTHDR.setNOTIF_LANGU(sdata.substring(421).trim());
                // e2NTHDR.setBAUTL_EXTERNAL(sdata.substring(423, 463).trim());
                // e2NTHDR.setBAUTL_VERSION(sdata.substring(463, 473).trim());
                // e2NTHDR.setBAUTL_GUID(sdata.substring(473).trim());
            }
            iDoc.setE2NTHDR(e2NTHDR);
        }
    }
}
