package demo.demo.domain.hospital.dto;

import com.cmrh.nuw.healthExamination.domain.hospital.entity.UwExamHospitalPhone;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UwExamHospitalDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7977740404172397640L;

	/**业务主键**/
	private  String  id;

	/**机构名**/
	private  String  branchName;

	/**机构代码**/
	private  String  branchCode;

	/**医院名**/
	private  String  hospitalName;

	/**医院地址**/
	private  String  hospitalAddress;

	/**医院联系人**/
	private  String  contractPerson;

	/**联系电话**/
	private  String  contractPersonPhone;

	/**维护人**/
	private  String  inputPerson;

	/**维护人电话**/
	private  String  inputPersonPhone;

	/**状态  0/1/2  合作中/已终止/暂停中**/
	private  String  status;
	
	private  String  createdUser;
	
	private  String  updatedUser;
	
	private  Date  createdDate;
	
	private  Date  updatedDate;
	
	/**医院预约电话*/
	private List<UwExamHospitalPhone> uwExamHospitalPhoneList;
	
}
