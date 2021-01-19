package demo.demo.domain.hospital.entity;

import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDomain;
import lombok.Data;

/**
 * 医院电话表
 * */
@Data
public class UwExamHospitalPhone extends BaseDomain{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8729106434920125292L;

	/**业务主键**/
	private String id;

	/**医院关联ID**/
	private String hospitalId;

	/**电话**/
	private String phone;

	/**状态**/
	private String phoneStatus;
	
	/**有效 Y/N*/
	private String isValid;

}