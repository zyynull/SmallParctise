package demo.demo.biz.hospital;

import com.cmrh.nuw.healthExamination.domain.hospital.dto.QueryHospitalParam;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.UwExamHospitalDTO;
import com.cmrh.nuw.healthExamination.infra.utils.common.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 医院基础信息接口
 * @author ex-zrh001 2019/12/31
 * */
public interface UwExamHospitalService {
	
	/**
     * 获取医院列表
     * 模糊查询
     * @param branchName 所属机构名
     * @param hospitalName 医院名
     * @return
     */
	Pagination getBranchHospitalList(QueryHospitalParam queryHospitalParam);
	
	/**
	 * 新增/更新医院信息
	 * @param uwExamHospitalDTO
	 * @return 医院ID
	 * */
	String createNewHospital(UwExamHospitalDTO uwExamHospitalDTO);
	
	/**
	 * 医院模板选择,获取医院ID跟医院名
	 * */
	List<Map<String,String>> getHospitalNameAndId(String status);
	
	/**
	 * 获取机构名及Code
	 * */
	List<Map<String,String>> getBranchNameAndCode();



}
