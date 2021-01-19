package demo.demo.domain.hospital.entity;

import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDomain;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UwExamHospitalItem extends BaseDomain {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7568243135789663613L;

	/**业务主键**/
	private String id;
	
	/**项目编号**/
	private String itemNo;
	
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
	
	/**专属项目明细**/
	private String itemDescriptionBak;
	
	/**有否有效 Y/N**/
	private String isValid;
	
	/**体检项目排序*/
	private Integer sortNo;
	
	public static List<Map<String,String>> getTitle() {
		List<Map<String,String>> titleList = new ArrayList<Map<String,String>>();
		Map<String,String> itemClassify = new HashMap<String, String>();
		itemClassify.put("prop", "itemClassify");
		itemClassify.put("value", "项目名称");
		titleList.add(itemClassify);
		
		Map<String,String> itemNo = new HashMap<String, String>();
		itemNo.put("prop", "itemNo");
		itemNo.put("value", "项目编号");
		titleList.add(itemNo);
		
		Map<String,String> itemDescription = new HashMap<String, String>();
		itemDescription.put("prop", "itemDescription");
		itemDescription.put("value", "项目明细");
		titleList.add(itemDescription);
		
		Map<String,String> sortNo = new HashMap<String, String>();
		sortNo.put("prop", "sortNo");
		sortNo.put("value", "项目明细排序");
		titleList.add(sortNo);
		
		Map<String,String> referenceType = new HashMap<String, String>();
		referenceType.put("prop", "referenceType");
		referenceType.put("value", "参考值类型");
		titleList.add(referenceType);
		
		Map<String,String> reference = new HashMap<String, String>();
		reference.put("prop", "reference");
		reference.put("value", "参考值");
		titleList.add(reference);
		
		Map<String,String> unit = new HashMap<String, String>();
		unit.put("prop", "unit");
		unit.put("value", "单位");
		titleList.add(unit);
		
		Map<String,String> prize = new HashMap<String, String>();
		prize.put("prop", "prize");
		prize.put("value", "价格");
		titleList.add(prize);
		
		Map<String,String> prizeGroupDesc = new HashMap<String, String>();
		prizeGroupDesc.put("prop", "prizeGroupDesc");
		prizeGroupDesc.put("value", "价格组合");
		titleList.add(prizeGroupDesc);
		return titleList;
	}
}
