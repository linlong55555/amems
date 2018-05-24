package com.eray.thjw.project2.po;

import java.math.BigDecimal;

import com.eray.thjw.po.User;

/**
 * 
 * @Description b_g_003
 * @CreateTime 2017年8月19日 下午2:42:13
 * @CreateBy 岳彬彬
 */
public class Instructionsource {
    private String dprtcode;

    private String pgdid;

    private String pgdh;
    
    private BigDecimal bb;
    /** 指令类型*/
    private Integer zlxl;

    private String zlid;

    private String zlbh;
    
    private String zlbb;
    
    private String ywzt;
    
    private Integer ywdjzt;
    
    private User zpr;//指派人
    
    private TechnicalAttached technicalAttached;//技术评估单-附加信息
    
    private Todo todo;//待办事宜
    
    private Airworthiness lywj;//来源文件(适航性资料)

    public User getZpr() {
		return zpr;
	}

	public void setZpr(User zpr) {
		this.zpr = zpr;
	}

	public TechnicalAttached getTechnicalAttached() {
		return technicalAttached;
	}

	public void setTechnicalAttached(TechnicalAttached technicalAttached) {
		this.technicalAttached = technicalAttached;
	}

	public Todo getTodo() {
		return todo;
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}

	public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getPgdid() {
        return pgdid;
    }

    public void setPgdid(String pgdid) {
        this.pgdid = pgdid == null ? null : pgdid.trim();
    }

    public String getPgdh() {
        return pgdh;
    }

    public void setPgdh(String pgdh) {
        this.pgdh = pgdh == null ? null : pgdh.trim();
    }

    public Integer getZlxl() {
        return zlxl;
    }

    public void setZlxl(Integer zlxl) {
        this.zlxl = zlxl;
    }

    public String getZlid() {
        return zlid;
    }

    public void setZlid(String zlid) {
        this.zlid = zlid == null ? null : zlid.trim();
    }

    public String getZlbh() {
        return zlbh;
    }

    public void setZlbh(String zlbh) {
        this.zlbh = zlbh == null ? null : zlbh.trim();
    }

	public String getZlbb() {
		return zlbb;
	}

	public void setZlbb(String zlbb) {
		this.zlbb = zlbb;
	}

	public String getYwzt() {
		return ywzt;
	}

	public void setYwzt(String ywzt) {
		this.ywzt = ywzt;
	}

	public BigDecimal getBb() {
		return bb;
	}

	public void setBb(BigDecimal bb) {
		this.bb = bb;
	}

	public Airworthiness getLywj() {
		return lywj;
	}

	public void setLywj(Airworthiness lywj) {
		this.lywj = lywj;
	}

	public Integer getYwdjzt() {
		return ywdjzt;
	}

	public void setYwdjzt(Integer ywdjzt) {
		this.ywdjzt = ywdjzt;
	}
	
}