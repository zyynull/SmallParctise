package demo.demo.domain.task.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientInformationDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 719601018622011503L;

	/**客户号*/
	private String clientNo;
	
	/**客户名*/
	private String clientName;
	
	/**性别*/
	private String sexCode;
	/**性别描述*/
	private String sexDesc;
	
	/**投保日期*/
	private String applyDate;
	
	/**生日*/
	private String age;
	
	/**职务*/
	private String position;
	
	/**职业描述*/
	private String occupationDesc;
	
	/**职业等级*/
	private String occupationGrade;
	
	/**工作单位*/
	private String workUnit;
	/*出生日期*/
	private String birthday;
}
