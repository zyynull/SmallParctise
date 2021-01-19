package demo.demo.domain.task.entity;

import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDomain;
import lombok.Data;

import java.io.Serializable;

@Data
public class UwExamTaskOpr extends BaseDomain implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -5888531641806129251L;
	
	/**
	 * 主键 taskNo
	 * */
	private String id;
	
	/**
	 * 是否有选择医院模板  
	 * 无/有  null/医院ID
	 * */
	private String moudle;
	
	/**
	 * 是否有改变模板内容
	 * 0/1/2 无/增/删
	 * **/
	private String change;
}
