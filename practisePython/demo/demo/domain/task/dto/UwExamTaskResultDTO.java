package demo.demo.domain.task.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UwExamTaskResultDTO implements Serializable, Comparable<UwExamTaskResultDTO>{

	private static final long serialVersionUID = -6800352249950070678L;

	/**项目分类*/
	private String itemClassfy;
	
	/**项目编号*/
	private String itemNo;
	
	/**明细*/
	private String itemDescription;
	
	/**参考值起始*/
	private String referenceStart;
	
	/**参考值结束*/
	private String referenceEnd;
	
	/**参考值结束*/
	private String referenceText;
	
	/**参考值类型*/
	private String referenceType;
	
	/**单位*/
	private String unit;
	
	/**0/1 正常异常*/
	private String conclusion;
	
	/**排序序号 有可能为null*/
	private Integer sortNo;

	@Override
	public int compareTo(UwExamTaskResultDTO o) {
		//如果sortNo 不为空 比较 sortNo
		if(this.sortNo!=null && o.sortNo!=null) {
			return this.sortNo - o.sortNo;
		}
		//如果只有this.sortNo 为空. 则-1
		if(this.sortNo==null && o.sortNo!=null) {
			return 1;
		}
		//如果只有o.sortNo 为空 则 1
		if(this.sortNo!=null && o.sortNo==null) {
			return -1;
		}
		//如果两方都为空 则按项目编号顺序排序
		if(this.sortNo==null && o.sortNo==null) {
			return Integer.valueOf(this.itemNo.substring(2)) - Integer.valueOf(o.itemNo.substring(2));
		}
		return 0;
	}
}
