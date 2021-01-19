package demo.demo.domain.task.entity;

import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDomain;
import lombok.Data;

import java.io.Serializable;

@Data
public class UwExamTaskResult extends BaseDomain implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5798497865443983109L;

	/**任务号**/
	private String taskNo;
	
	/**项目名**/
	private String itemNo;
	
	/**项目分类**/
	private String itemClassfy;
	
	/**备选项目名称**/
	private String itemDescriptionBak;

	/**备选项目名称,用于新增**/
	private String itemDescription;
	
	/**参考值类型**/
	private String referenceType;
	
	/**参考值起始**/
	private String referenceStart;
	
	/**参考值结束**/
	private String referenceEnd;
	
	/**文本参考值**/
	private String referenceText;
	
	/**单位**/
	private String unit;
	
	/**结果**/
	private String result;
	
	/**说明**/
	private String remark;
	
	/**0/1 正常异常*/
	private String conclusion;
	
}