package com.eray.thjw.productionplan.po;

import java.math.BigDecimal;
import java.util.Date;

public class PlaneDailyUsage {
    private String fjzch;

    private BigDecimal rJsfxsj;

    private BigDecimal rSsdsj;

    private BigDecimal rJcsj;

    private Integer rQljxh;

    private Integer rJcxh;

    private Integer rWdgxh;

    private Integer rSsdxh;

    private Integer rN1;

    private Integer rN2;

    private Integer rN3;

    private Integer rN4;

    private Integer rN5;

    private Integer rN6;

    private Integer rTsjk1;

    private Integer rTsjk2;

    private String whrid;

    private Date whsj;
    
    private String dprtcode;

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public BigDecimal getrJsfxsj() {
        return rJsfxsj;
    }

    public void setrJsfxsj(BigDecimal rJsfxsj) {
        this.rJsfxsj = rJsfxsj;
    }

    public BigDecimal getrSsdsj() {
        return rSsdsj;
    }

    public void setrSsdsj(BigDecimal rSsdsj) {
        this.rSsdsj = rSsdsj;
    }

    public BigDecimal getrJcsj() {
        return rJcsj;
    }

    public void setrJcsj(BigDecimal rJcsj) {
        this.rJcsj = rJcsj;
    }

    public Integer getrQljxh() {
        return rQljxh;
    }

    public void setrQljxh(Integer rQljxh) {
        this.rQljxh = rQljxh;
    }

    public Integer getrJcxh() {
        return rJcxh;
    }

    public void setrJcxh(Integer rJcxh) {
        this.rJcxh = rJcxh;
    }

    public Integer getrWdgxh() {
        return rWdgxh;
    }

    public void setrWdgxh(Integer rWdgxh) {
        this.rWdgxh = rWdgxh;
    }

    public Integer getrSsdxh() {
        return rSsdxh;
    }

    public void setrSsdxh(Integer rSsdxh) {
        this.rSsdxh = rSsdxh;
    }

    public Integer getrN1() {
        return rN1;
    }

    public void setrN1(Integer rN1) {
        this.rN1 = rN1;
    }

    public Integer getrN2() {
        return rN2;
    }

    public void setrN2(Integer rN2) {
        this.rN2 = rN2;
    }

    public Integer getrN3() {
        return rN3;
    }

    public void setrN3(Integer rN3) {
        this.rN3 = rN3;
    }

    public Integer getrN4() {
        return rN4;
    }

    public void setrN4(Integer rN4) {
        this.rN4 = rN4;
    }

    public Integer getrN5() {
        return rN5;
    }

    public void setrN5(Integer rN5) {
        this.rN5 = rN5;
    }

    public Integer getrN6() {
        return rN6;
    }

    public void setrN6(Integer rN6) {
        this.rN6 = rN6;
    }

    public Integer getrTsjk1() {
        return rTsjk1;
    }

    public void setrTsjk1(Integer rTsjk1) {
        this.rTsjk1 = rTsjk1;
    }

    public Integer getrTsjk2() {
        return rTsjk2;
    }

    public void setrTsjk2(Integer rTsjk2) {
        this.rTsjk2 = rTsjk2;
    }

    public String getWhrid() {
        return whrid;
    }

    public void setWhrid(String whrid) {
        this.whrid = whrid == null ? null : whrid.trim();
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}
}