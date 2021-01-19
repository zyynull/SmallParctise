package demo.demo.domain.task.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class UwExamTaskInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6386076674234149338L;

	/**任务编号**/
	@NotBlank
	private String taskNo;
	
	/**任务类型 1体检录入**/
	private String taskType;
	
	/**机构名*/
	private String branchName;
	
	/**客户号**/
	@NotBlank
	private String clientNo;
	
	/**客户号**/
	private String clientName;
	
	/**时效**/
	private Double usedTime;
	
	/**体检预约时间**/
	private String examAppointmentDate;
	
	/**照会序号**/
	@NotBlank
	private Integer NoteSeq;
	
	/**状态 0/1/2 未开始/暂存中/已完成**/
	private String status;
	
	/**说明信息*/
	private String remark;
	
	private String updatedDate;
	
	private String updatedUser;
	
	private String createdUser;
	
	private String createdDate;
	
	private String applyNo;
	
	/**实际体检医院*/
	private String actualHospital;

	 /**任务提交人*/
	private String submitter;
	/**
	 * 任务提交时间
	 */
	private String submitDate;
	/**
	 * 是否扫描体检报告
	 */
	private String isScan;
	/**
	 * 是否扫描描述
	 */
	private String isScanDesc;

	private String operatedUser;
	/**
	 * 任务状态描述
	 */
	private String statusDesc;
}
