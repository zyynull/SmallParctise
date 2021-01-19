package demo.demo.domain.hospital.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UwExamHospitalItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 68943315561301885L;

	/**业务主键**/
	private String id;
	
	/**项目编号**/
	private String itemNo;
	
	/**项目分类*/
	private String itemClassify;
	
	/**项目明细*/
	private String itemDescription;
	
	/**参考值类型 0/1 定量/定性**/
	private String referenceType;
	
	/**参考值开始**/
	private String referenceStart;
	
	/**参考值结束**/
	private String referenceEnd;
	
	/**文本参考值**/
	private String referenceText;
	
	/**单位**/
	private String unit;
	
	/**价格**/
	private String prize;
	
	/**价格码**/
	private String prizeGroupCode; 
	
	/**价格码描述**/
	private String prizeGroupDesc;
	
	/**医院ID**/
	private String hospitalId;
	
	/**适用性别 0/1/2 全/男/女**/
	private String applyGender;
	
	/**创建人*/
	private String createdUser;
	
	/**更新人*/
	private String updatedUser;
	
	/**体检项目排序*/
	private Double sortNo;
}
