package demo.demo.domain.hospital.dao;

import com.cmrh.nuw.healthExamination.domain.hospital.dto.QueryHospitalParam;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.UwExamHospitalDTO;
import com.cmrh.nuw.healthExamination.domain.hospital.entity.UwExamHospitalPhone;
import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 医院基础信息接口
 * */
@Repository("UwExamHospitalDao")
public class UwExamHospitalDao  extends BaseDao {
	
	public UwExamHospitalDao() {
        super.namespace = UwExamHospitalDao.class.getName() + ".";
    }
	
    public Integer getHospitalListCount(QueryHospitalParam parameter) {
        return (Integer) super.query("getHospitalListCount", parameter);
    }

    public List<UwExamHospitalDTO> getHospitalList(QueryHospitalParam parameter) {
        return super.queryList("getHospitalList", parameter);
    }
    
    public List<UwExamHospitalPhone> getPhoneList(String hospitalId){
    	return super.queryList("getPhoneList", hospitalId);
    }
    
    public Integer insertUwExamHospital(UwExamHospitalDTO uwExamHospitalDTO) {
    	return super.insert("insertNewHospital", uwExamHospitalDTO);
    }
    
    public Integer updateUwExamHospital(UwExamHospitalDTO uwExamHospitalDTO) {
    	return super.update("updateNewHospital", uwExamHospitalDTO);
    }
    
    /**新增医院电话, 默认有效*/
    public Integer insertPhone(UwExamHospitalPhone phone) {
    	return super.insert("insertPhone", phone);
    }
    
    /**逻辑删除医院电话*/
    public Integer deletePhone(String hospitalId) {
    	return super.update("deletePhone", hospitalId);
    }
    
    /**更新医院电话 只更新数据,并不更新有无效*/
    public Integer updatePhone(UwExamHospitalPhone phone) {
    	return super.update("updatePhone", phone);
    }
    
    public List<Map<String,String>> getHospitalNameAndId(String status){
		return super.queryList("getHospitalNameAndId",status);
	}
    
    public List<Map<String, String>> getBranchNameAndCode(){
    	return super.queryList("getBranchNameAndCode");
    }
    
    public Integer checkBranchHospital(UwExamHospitalDTO uwExamHospitalDTO) {
    	return (Integer) super.query("checkBranchHospital",uwExamHospitalDTO);
    }

    /**
     * 获取一个新的id
     * @return
     */
    public Integer getNewHospitalId(){
     return (Integer) super.query("getNewHospitalId");
    }
}
