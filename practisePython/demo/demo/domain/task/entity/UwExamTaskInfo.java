package demo.demo.domain.task.entity;

import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDomain;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UwExamTaskInfo extends BaseDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -103231765489920730L;

	/**任务编号**/
	private String taskNo;
	
	/**任务类型 1体检录入**/
	private String taskType;
	
	/**客户号**/
	private String clientNo;
	
	/**时效**/
	private String usedTime;
	
	/**体检预约时间**/
	private Date examAppointmentDate;
	
	/**体检时间*/
	private Date examDate;
	
	/**体检费用*/
	private Double examCharge;
	
	/**报告领取时间*/
	private Date reportObtainDate;
	
	/**报告类型*/
	private String reportType;
	
	/**照会序号**/
	private Integer NoteSeq;
	
	/**状态 0/1/2 未开始/暂存中/已完成**/
	private String status;
	
	/**说明信息*/
	private String remark;
	
	/**实际体检医院*/
	private String actualHospital;

	/**
	 * 任务提交人
	 */
	private String submitter;

	/**
	 * 任务提交时间
	 */
	private Date submitDate;
	/**
	 * 任务处理人
	 */
	private String operatedUser;
	/**
	 * 任务完成时间
	 */
	private Date finishDate;
	/**
	 * 体检结果
	 */
	private String examResult;

	/**
	 * 体检结果异常项备注
	 */
	private String abnormalItemsRemark;
}
