package demo.demo.biz.hospital.impl;

import com.cmrh.nuw.healthExamination.app.biz.hospital.UwExamHospitalService;
import com.cmrh.nuw.healthExamination.domain.hospital.dao.UwExamHospitalDao;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.QueryHospitalParam;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.UwExamHospitalDTO;
import com.cmrh.nuw.healthExamination.domain.hospital.entity.UwExamHospitalPhone;
import com.cmrh.nuw.healthExamination.infra.utils.common.Pagination;
import com.cmrh.nuw.healthExamination.infra.utils.constant.SystemConstant;
import com.cmrh.uw.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("UwExamHospitalServiceImpl")
public class UwExamHospitalServiceImpl implements UwExamHospitalService{

	private static final Logger logger = LoggerFactory.getLogger(UwExamHospitalServiceImpl.class);
	
	@Autowired
	private UwExamHospitalDao uwExamHospitalDao;

	@Override
	public Pagination getBranchHospitalList(QueryHospitalParam queryHospitalParam) {
		logger.info("方法:getBranchHospitalList开始");
		Pagination pagination = new Pagination();
        pagination.setPageSize(queryHospitalParam.getRows());
        pagination.setPage(queryHospitalParam.getCurrentPage());
        //判断数量
        Integer total = uwExamHospitalDao.getHospitalListCount(queryHospitalParam);
        pagination.setTotal(total);
        if (total <= 0) {
        	return pagination;
        }
        //返回查询数据
        List<UwExamHospitalDTO> uwExamHospitalDtoList = uwExamHospitalDao.getHospitalList(queryHospitalParam);
        //设置医院电话
        for (UwExamHospitalDTO uwExamHospitalDTO : uwExamHospitalDtoList) {
        	uwExamHospitalDTO.setUwExamHospitalPhoneList(uwExamHospitalDao.getPhoneList(uwExamHospitalDTO.getId()));
		}
        pagination.setData(uwExamHospitalDtoList);
        logger.info("方法:getBranchHospitalList结束");
        return pagination;
	}

	@Override
	@Transactional
	public String createNewHospital(UwExamHospitalDTO uwExamHospitalDTO) {
		logger.info("方法:creatNewHospital开始");
		//有ID是更新 没ID是新增
		if(StringUtil.isEmpty(uwExamHospitalDTO.getId())) {
			logger.info("方法:creatNewHospital新增");
			//查询该机构下是否有同名体检机构
			Integer isExistInt = uwExamHospitalDao.checkBranchHospital(uwExamHospitalDTO);
			boolean isExist = isExistInt!=null && isExistInt>0;
			if(isExist) {
				throw new RuntimeException("该机构下已存在同名体检医院");
			}
			//新增
			String hospitalId = uwExamHospitalDao.getNewHospitalId()+"";
			uwExamHospitalDTO.setId(hospitalId);
			uwExamHospitalDao.insertUwExamHospital(uwExamHospitalDTO);
			//新增电话
			List<UwExamHospitalPhone> uwExamHospitalPhoneList = uwExamHospitalDTO.getUwExamHospitalPhoneList();
			for (UwExamHospitalPhone uwExamHospitalPhone : uwExamHospitalPhoneList) {
				if(StringUtil.isNotEmpty(uwExamHospitalPhone.getPhone())) {
					uwExamHospitalPhone.setHospitalId(hospitalId);
					uwExamHospitalPhone.setCreatedUser(uwExamHospitalDTO.getCreatedUser());
					uwExamHospitalDao.insertPhone(uwExamHospitalPhone);
				}
			}
			//在上传时新增
			return hospitalId;
		}else {
			logger.info("方法:creatNewHospital更新");
			String updateUser = uwExamHospitalDTO.getUpdatedUser();
			//更新
			uwExamHospitalDao.updateUwExamHospital(uwExamHospitalDTO);
			String hospitalId = uwExamHospitalDTO.getId();
			//更新电话
			//先将该网点全部电话置为无效
			uwExamHospitalDao.deletePhone(uwExamHospitalDTO.getId());
			//然后从前端传的List判断 有ID的 更新, 没ID的新增
			List<UwExamHospitalPhone> uwExamHospitalPhoneList = uwExamHospitalDTO.getUwExamHospitalPhoneList();
			for (UwExamHospitalPhone uwExamHospitalPhone : uwExamHospitalPhoneList) {
				if(StringUtil.isNotEmpty(uwExamHospitalPhone.getPhone())) {
					if(StringUtil.isEmpty(uwExamHospitalPhone.getId())) {
						//新增
						uwExamHospitalPhone.setHospitalId(hospitalId);
						uwExamHospitalPhone.setCreatedUser(updateUser);
						uwExamHospitalDao.insertPhone(uwExamHospitalPhone);
					}else {
						//更新
						uwExamHospitalPhone.setHospitalId(hospitalId);
						uwExamHospitalPhone.setIsValid(SystemConstant.Y);
						uwExamHospitalPhone.setUpdatedUser(updateUser);
						uwExamHospitalDao.updatePhone(uwExamHospitalPhone);
					}
				}
			}
			//模板在上传时更新
			return uwExamHospitalDTO.getId();
		}
	}

	@Override
	public List<Map<String, String>> getHospitalNameAndId(String status) {
		return uwExamHospitalDao.getHospitalNameAndId(status);
	}

	@Override
	public List<Map<String, String>> getBranchNameAndCode() {
		return uwExamHospitalDao.getBranchNameAndCode();
	}

	
}
