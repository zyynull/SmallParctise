package demo.demo.domain.task.dto;

import com.cmrh.uw.common.util.PaginatedHelper;
import lombok.Data;

import java.util.List;

/**
 * 查询条件分页入参
 * */
@Data
public class QueryTaskParam extends PaginatedHelper {
	
	private String userCode;
	/**账号所属机构代码*/
	private List<String> userBranchCodes;
	/**任务状态*/
	private String status;
	/**客户号*/
	private String clientNo;
	/**体检任务号*/
	private String taskNo;
	/**机构代码*/
	private String branchCode;
}
