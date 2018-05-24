package com.eray.framework.saibong.po;

/**
 * @Description saibong_rule 采番规则表
 * @CreateTime 2018-1-10 下午2:21:41
 * @CreateBy 刘兵
 */
public class SaibongRule {
	
	private String cfkey;

	private String dprtcode;
	
    private String gznr;
    
    public String getCfkey() {
        return cfkey;
    }

    public void setCfkey(String cfkey) {
        this.cfkey = cfkey == null ? null : cfkey.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getGznr() {
        return gznr;
    }

    public void setGznr(String gznr) {
        this.gznr = gznr == null ? null : gznr.trim();
    }

	@Override
	public String toString() {
		return "SaibongRule [cfkey=" + cfkey + ", dprtcode=" + dprtcode
				+ ", gznr=" + gznr + "]";
	}
    
}