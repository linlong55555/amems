package com.eray.thjw.project2.po;

/**
 * 
 * @Description t_todo_rs 待办事宜表明细
 * @CreateTime 2017-8-21 上午11:03:05
 * @CreateBy 孙霁
 */
public class Todors {
	private String id;

    private String mainid;

    private String ywid;

    private String ywbh;

    private String ywbb;

    private String ywzt;
    
    private String lyid; //来源id
    
    private Integer ywdjzt; //业务单据状态
    
    private Todo todo;
    
	public Integer getYwdjzt() {
		return ywdjzt;
	}

	public void setYwdjzt(Integer ywdjzt) {
		this.ywdjzt = ywdjzt;
	}

	public Todo getTodo() {
		return todo;
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}

	public String getLyid() {
		return lyid;
	}

	public void setLyid(String lyid) {
		this.lyid = lyid;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public String getYwid() {
        return ywid;
    }

    public void setYwid(String ywid) {
        this.ywid = ywid == null ? null : ywid.trim();
    }

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh == null ? null : ywbh.trim();
    }

    public String getYwzt() {
        return ywzt;
    }

    public void setYwzt(String ywzt) {
        this.ywzt = ywzt == null ? null : ywzt.trim();
    }

	public String getYwbb() {
		return ywbb;
	}

	public void setYwbb(String ywbb) {
		this.ywbb = ywbb;
	}

    
}