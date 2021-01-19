package demo.demo.infra.utils.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * ******************************************************************
 * function
 * Copyright (c) @author ex-zrh001 2019年12月31日
 * @version  1.0
 ******************************************************************
 */
@Data
@ToString
public abstract class BaseDomain implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 138225575582077723L;

	/** 录入人 */
	private String createdUser;

	/** 录入日期 */
	private Date createdDate;

	/** 更新人 */
	private Date updatedDate;

	/** 更新日期 */
	private String updatedUser;
}
