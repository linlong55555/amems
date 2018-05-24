package com.eray.thjw.po;

import java.io.Serializable;

/**
 * @author zhuchao
 * @description 所有实体的基类
 */
@SuppressWarnings("serial")
public abstract class AbstractEntity implements Serializable {
	public abstract Object getId();
}