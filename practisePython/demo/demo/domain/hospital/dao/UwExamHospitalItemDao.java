package demo.demo.domain.hospital.dao;

import com.cmrh.nuw.healthExamination.domain.hospital.dto.UwExamHospitalItemDTO;
import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 医院项目接口信息
 * */
@Repository("UwExamHospitalItemDao")
public class UwExamHospitalItemDao  extends BaseDao {
	
	public UwExamHospitalItemDao() {
        super.namespace = UwExamHospitalItemDao.class.getName() + ".";
    }

	public Integer insert(UwExamHospitalItemDTO uwExamBaseHospitalDTO) {
		return super.insert("insertUwExamBaseHospital", uwExamBaseHospitalDTO);
	}
	
	/**
	 * 将该医院下的该性别下的项目置为无效
	 * @param hospitalId
	 * @param applyGender
	 * */
	public Integer deleteItem(Map<String,String> map) {
		return super.update("deleteItem", map);
	}
	
	/**
	 * 判断条件内数据是否存在, 存在则更新, 不存在则新增
	 * */
	public void mergeHospitalItem(UwExamHospitalItemDTO uwExamHospitalItemDTO) {
		super.insert("mergeHospitalItem", uwExamHospitalItemDTO);
	}

	public Integer checkItem(UwExamHospitalItemDTO uwExamHospitalItemDTO){
	    return (Integer) super.query("checkItem",uwExamHospitalItemDTO);
    }

	/**
	 * 导出的方法跟体检任务结果录入的地方差不多, 唯一不同的是Inner Join了医院项目表
	 * @param hospitalId
	 * @param gender 1,2 男/女
	 * **/
	public List<UwExamHospitalItemDTO> getExportExamResult(Map<String,String> map){
		return super.queryList("getExportExamResult", map);
	}
}
