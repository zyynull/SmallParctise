package demo.demo.domain.hospital.dto;

import com.cmrh.uw.common.util.PaginatedHelper;
import lombok.Data;

/**
 * 查询条件分页入参
 * */
@Data
public class QueryHospitalParam extends PaginatedHelper {

	private String branchCode;
	
	private String hospitalName;
}
