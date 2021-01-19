package demo.demo.domain.task.entity;

import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDomain;
import lombok.Data;

import java.io.Serializable;

@Data
public class UwExamTaskHold extends BaseDomain implements Serializable{
		
	/**任务编号**/
	private String taskNo;
	
	/**暂存数据**/
	private String data;
	
}
