package demo.demo.domain.task.dao;

import com.cmrh.nuw.healthExamination.domain.task.dto.ClientInformationDTO;
import com.cmrh.nuw.healthExamination.domain.task.dto.QueryTaskParam;
import com.cmrh.nuw.healthExamination.domain.task.dto.UwExamTaskInfoDTO;
import com.cmrh.nuw.healthExamination.domain.task.dto.UwExamTaskResultDTO;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskInfo;
import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDao;
import com.cmrh.uw.medical.undwrt.util.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 体检任务基础信息接口
 * */
@Repository("UwExamTaskInfoDao")
public class UwExamTaskInfoDao  extends BaseDao {
	
	public UwExamTaskInfoDao() {
        super.namespace = UwExamTaskInfoDao.class.getName() + ".";
    }


    public List<String> queryBranchCodeByUserId(String userCode){
		return super.queryList("queryBranchCodeByUserId",userCode);
	}


	public String getUwExamTaskTaskNo(String taskCode){
		return (String) super.query("getUwExamTaskTaskNo", taskCode);
	}

	public List<UwExamTaskInfoDTO> getTaskList(QueryTaskParam queryTaskParam){
		return super.queryList("getTaskList", queryTaskParam);
	}
	
	public Integer getTaskListCount(QueryTaskParam queryTaskParam) {
		return (Integer) super.query("getTaskListCount", queryTaskParam);
	}

	public List<UwExamTaskInfoDTO> selectManageTasks(QueryTaskParam queryTaskParam){
		return super.queryList("selectManageTasks", queryTaskParam);
	}

	public Integer selectManageTasksCount(QueryTaskParam queryTaskParam) {
		return (Integer) super.query("selectManageTasksCount", queryTaskParam);
	}
	
	public void setPrivate(Map<String,String> map) {
		super.update("setPrivate", map);
	}
	
	public ClientInformationDTO getClientInformation(Map<String,String> map) {
		return (ClientInformationDTO) super.query("getClientInformation", map);
	}
	
	public UwExamTaskInfo getExamTaskInfo(String taskNo) {
		return (UwExamTaskInfo) super.query("getExamTaskInfo", taskNo);
	}
	
	/**
	 * String clientNo
	 * String noteSeq
	 * **/
	public List<Map<String,String>> getExamProject(Map<String,String> map){
		return super.queryList("getExamProject", map);
	}
	
	/**
	 * @param hospitalId
	 * @param gender 1,2 男/女
	 * **/
	public List<UwExamTaskResultDTO> getGeneralExamResult(Map<String,String> map){
		return super.queryList("getGeneralExamResult", map);
	}
	
	/**
	 * @param hospitalId
	 * @param gender 1,2 男/女
	 * NC13-NC22这10项, 在2019年12月31日开发时暂时没有定通用项目名称, 每个医院的项目名不一样, 没法做基表匹配,
			所以需要在查出130项之外, 再单独把每个医院的项目查出来,医院的表内有备选项目名,用于单独项目显示
	 * */
	public List<UwExamTaskResultDTO> getPrivateExamResult(Map<String,String> map){
		return super.queryList("getPrivateExamResult", map);
	}
	
	/**
	 * String clientNo
	 * String noteSeq
	 * **/
	public Map<String,String> getUnderWriteInfo(Map<String,String> map){
		return (Map<String, String>) super.query("getUnderWriteInfo", map);
	}
	
	/**
	 * String clientNo
	 * String noteSeq
	 * **/
	public List<String> getExamItemNoList(Map<String,String> map){
		return super.queryList("getExamItemNoList", map);
	}
	
	public Integer updateTaskInfo(UwExamTaskInfo uwExamTaskInfo) {
		return super.update("updateTaskInfo", uwExamTaskInfo);
	}
	
	public Integer createTaskInfo(Map<String,String> map) {
		return super.insert("createTaskInfo", map);
	}
	
	public Integer cancelTask(Map<String,String> map) {
		return super.update("cancelTask", map);
	}
	
	public List<Map<String,String>> getExamChargeGroupCode(Map<String,Object> conditionMap) {
		return super.queryList("getExamChargeGroupCode", conditionMap);
	}
	
	public String getExamChargeNullCode(Map<String,Object> conditionMap) {
		return (String) super.query("getExamChargeNullCode", conditionMap);
	}

	public void deleteImageTypeConfig (String imageType, String serviceId) {
		if (StringUtils.isEmpty(imageType) && StringUtils.isEmpty(serviceId)) {
			logger.warn("ImageType or serviceId is null, exit");
			return;
		}
		Map<String, String> param = MapUtil.createStringMap2("imageType", imageType, Pair.of("serviceId", serviceId));
		super.delete("deleteImageTypeConfig", param);
	}

	public void mergeImageTypeConfig (String imateType, String serviceId) {
		Map<String, String> param = MapUtil.createStringMap2("imageType", imateType, Pair.of("serviceId", serviceId));
		super.update("mergeImageTypeConfig", param);
	}

    public String getUwExamTaskTaskNoByClientNoAndNoteSeq(Map<String, String> param) {
		return (String) super.query("getUwExamTaskTaskNoByClientNoAndNoteSeq", param);
    }

    public void  allocateTasks(UwExamTaskInfoDTO uwExamTaskInfoDTO){
        super.update("allocateTasks", uwExamTaskInfoDTO);
    }


    public void  modifyTasks(UwExamTaskInfoDTO uwExamTaskInfoDTO){
        super.update("modifyTasks", uwExamTaskInfoDTO);
    }

    public UwExamTaskInfoDTO getUwExamTaskTask(String taskNo){
        return (UwExamTaskInfoDTO) super.query("getUwExamTaskTask", taskNo);
    }



}
