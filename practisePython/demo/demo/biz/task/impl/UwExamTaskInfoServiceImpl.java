package demo.demo.biz.task.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cmrh.im.http.model.ImageInfo;
import com.cmrh.nuw.healthExamination.app.biz.task.UwExamTaskInfoService;
import com.cmrh.nuw.healthExamination.domain.task.dao.UwExamTaskHoldDao;
import com.cmrh.nuw.healthExamination.domain.task.dao.UwExamTaskInfoDao;
import com.cmrh.nuw.healthExamination.domain.task.dao.UwExamTaskOprDao;
import com.cmrh.nuw.healthExamination.domain.task.dao.UwExamTaskResultDao;
import com.cmrh.nuw.healthExamination.domain.task.dto.QueryTaskParam;
import com.cmrh.nuw.healthExamination.domain.task.dto.TaskResultClassifyDTO;
import com.cmrh.nuw.healthExamination.domain.task.dto.UwExamTaskInfoDTO;
import com.cmrh.nuw.healthExamination.domain.task.dto.UwExamTaskResultDTO;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskHold;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskInfo;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskOpr;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskResult;
import com.cmrh.nuw.healthExamination.infra.utils.common.Pagination;
import com.cmrh.nuw.healthExamination.infra.utils.constant.SystemConstant;
import com.cmrh.sf.framework.workflow.message.domain.ImageMessage;
import com.cmrh.sf.framework.workflow.message.impl.ImageMessageListener;
import com.cmrh.uw.commons.web.util.StringUtil;
import com.cmrh.uw.image.biz.service.ImageService;
import com.cmrh.uw.underwriting.integration.dao.UwPhysicalExamNoteDao;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.httpclient.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.cmrh.uw.baseinfo.common.Constant.PHYSICAL_IMAGE_TYPE;
import static com.cmrh.uw.baseinfo.common.Constant.WORKFLOW_IMAGE_MSG_SERVICE_ID;

@Service("UwExamTaskInfoServiceImpl")
public class UwExamTaskInfoServiceImpl implements UwExamTaskInfoService {

	private static final Logger logger = LoggerFactory.getLogger(UwExamTaskInfoServiceImpl.class);
	@Autowired
	private UwExamTaskInfoDao uwExamTaskInfoDao;
	@Autowired
	private UwExamTaskHoldDao uwExamTaskHoldDao;
	@Autowired
	private UwExamTaskResultDao uwExamTaskResultDao;
	@Autowired
	private UwExamTaskOprDao uwExamTaskOprDao;
	@Autowired
    private ImageMessageListener imageMessageListener;
	@Autowired
	private UwPhysicalExamNoteDao uwPhysicalExamNoteDao;

	@Autowired
	private ImageService imageService;


	@Override
	public Pagination getTaskList(QueryTaskParam queryTaskParam,String userCode) {
		logger.info("方法:getTaskList开始");
		Pagination pagination = new Pagination();
        pagination.setPageSize(queryTaskParam.getRows());
        pagination.setPage(queryTaskParam.getCurrentPage());

		/**用户所属机构*/
		List<String> userBranchCodes = uwExamTaskInfoDao.queryBranchCodeByUserId(userCode);

		//如果同时存在总公司与分公司，与分公司为所属机构
		if (CollectionUtils.isNotEmpty(userBranchCodes)&&userBranchCodes.size()>1&&userBranchCodes.contains("86")){
			userBranchCodes.remove("86");
		}
		queryTaskParam.setUserBranchCodes(userBranchCodes);
        //判断数量
        Integer total = uwExamTaskInfoDao.getTaskListCount(queryTaskParam);
        pagination.setTotal(total);
        if (total <= 0) {
        	return pagination;
        }

        //返回查询数据
        List<UwExamTaskInfoDTO> uwExamTaskInfoList = uwExamTaskInfoDao.getTaskList(queryTaskParam);
        pagination.setData(uwExamTaskInfoList);
        logger.info("方法:getTaskList结束");
        return pagination;
	}

	@Override
	@Transactional
	public void setPrivate(String userCode,String taskNo) {
		logger.info("方法:setPrivate结束");
		Map<String,String> map = new HashMap<String,String>();
		map.put("userCode", userCode);
		map.put("taskNo", taskNo);
		uwExamTaskInfoDao.setPrivate(map);
	}

	@Override
	public LinkedHashMap<String,List<String>> getExamProject(String clientNo, String noteSeq) {
		Map<String,String> condition = new HashMap<>();
		condition.put("clientNo", clientNo);
		condition.put("noteSeq", noteSeq);
		List<Map<String, String>> list = uwExamTaskInfoDao.getExamProject(condition);
		LinkedHashMap<String,List<String>> resultMap = new LinkedHashMap<>();
		for (Map<String, String> map : list) {
			String title = map.get("title");
			List<String> MapList = resultMap.get(title);
			if(MapList==null) {
				MapList = new ArrayList<String>();
			}
			if (!StringUtil.isEmpty(map.get("content"))){
				MapList.add(map.get("content"));
				resultMap.put(title, MapList);
			}
		}
		return resultMap;
	}

	@Override
	public List<TaskResultClassifyDTO> getAllExamResult(String hospitalId,String gender) {
		Map<String,String> map = new HashMap<>();
		map.put("hospitalId", hospitalId);
		map.put("gender", gender);
		List<UwExamTaskResultDTO> list = uwExamTaskInfoDao.getGeneralExamResult(map);
		if(StringUtil.isNotEmpty(hospitalId)) {
			list.addAll(uwExamTaskInfoDao.getPrivateExamResult(map));
		}
		return TaskResultClassifyDTO.handleArrayData(list);
	}

	@Override
	public Map<String,Object> getTaskBaseInfo(String noteSeq, String clientNo, String taskNo) {
		Map<String,Object> map = new HashMap<>();
		Map<String,String> condition = new HashMap<>();
		condition.put("noteSeq", noteSeq);
		condition.put("clientNo", clientNo);
		map.put("client", uwExamTaskInfoDao.getClientInformation(condition));
		map.put("underWrite", uwExamTaskInfoDao.getUnderWriteInfo(condition));
		map.put("examProject", getExamProject(clientNo,noteSeq));
		map.put("examTaskInfo", uwExamTaskInfoDao.getExamTaskInfo(taskNo));
		return map;
	}

	@Override
	@Transactional
	public Map<String,Object> handleHoldDate(int choose, UwExamTaskHold uwExamTaskHold) {
		String taskNo = uwExamTaskHold.getTaskNo();
		Map<String,Object> result = new HashMap<>();
		//流程选择  1增/改,2查,3删
		if(choose == 1) {
			result.put("result", uwExamTaskHoldDao.merge(uwExamTaskHold));
		}else if(choose == 2) {
			result.put("result", uwExamTaskHoldDao.get(taskNo));
		}else if(choose == 3) {
			result.put("result", uwExamTaskHoldDao.delete(taskNo));
		}else {
			return null;
		}
		
		return result;
	}

	@Override
	@Transactional
	public void updateTaskInfo(UwExamTaskInfo uwExamTaskInfo,String user) {
		uwExamTaskInfo.setUpdatedUser(user);
		uwExamTaskInfoDao.updateTaskInfo(uwExamTaskInfo);
	}
	
	@Override
	@Transactional
	public void createTaskInfo(String clientNo,String noteSeq,String taskType,String createdUser) {
		Map<String,String> map = new HashMap<>();
		map.put("clientNo", clientNo);
		map.put("noteSeq", noteSeq);
		//taskType =1 体检录入
		map.put("taskType", taskType);
		map.put("createdUser", createdUser);
		map.put("submitter", createdUser);
		//流水号头
		StringBuffer taskNo = new StringBuffer("TE");
		taskNo.append(DateUtil.formatDate(new Date(), "yyyyMMdd"));
		map.put("taskNo", taskNo.toString());
		uwExamTaskInfoDao.createTaskInfo(map);
	}

	@Override
	@Transactional
	public void createTaskResult(List<UwExamTaskResult> taskResultList,String taskNo,String user) {
		logger.info("createTaskResult"+taskResultList);
		int initialItemNo = 1;
		for (UwExamTaskResult uwExamTaskResult : taskResultList) {
			uwExamTaskResult.setTaskNo(taskNo);
			uwExamTaskResult.setCreatedUser(user);
			uwExamTaskResult.setUpdatedUser(user);
			if (StringUtil.isEmpty(uwExamTaskResult.getReferenceType())){
				//默认为定量
				uwExamTaskResult.setReferenceType("0");
			}
			if(StringUtil.isEmpty(uwExamTaskResult.getItemNo())) {
				uwExamTaskResult.setItemNo("NI"+initialItemNo);
				initialItemNo++;
			}
		}
		uwExamTaskResultDao.createTaskResult(taskResultList);
	}


	@Override
	@Transactional
	public void cancelTask(String taskCode, String user,String status ) {
		String taskNo = this.getUwExamTaskTaskNo(taskCode);
		if (StringUtil.isEmpty(taskNo)) return;
		this.cancelTask(taskNo,"",user,status);
	}

	@Override
	@Transactional
	public void cancelTask(String taskNo, String remark, String user,String status) {
		Map<String,String> map = new HashMap<>();
		map.put("taskNo", taskNo);
		map.put("remark", remark);
		map.put("user", user);
		map.put("status",status);
		uwExamTaskInfoDao.cancelTask(map);
	}
	
	@Override
	@Transactional
	public void creatTaskOpr(UwExamTaskOpr uwExamTaskOpr,String user) {
		uwExamTaskOpr.setCreatedUser(user);
		uwExamTaskOpr.setUpdatedUser(user);
		uwExamTaskOprDao.create(uwExamTaskOpr);
	}

	@Override
	@Transactional
	public void commitTaskInfoResult(JSONObject datas, String user) {
		//获取体检任务信息 
    	JSONObject taskInfoJson = datas.getJSONObject("ExamTaskInfo");
    	String collectExamResult = datas.getString("collectEaxmResult");

    	//转化为实体
    	UwExamTaskInfo uwExamTaskInfo=JSON.parseObject(taskInfoJson.toJSONString(), UwExamTaskInfo.class);
    	//状态为2是完成
    	uwExamTaskInfo.setStatus(SystemConstant.TWO);
    	uwExamTaskInfo.setExamResult(collectExamResult);
    	updateTaskInfo(uwExamTaskInfo,user);
    	
    	//获取体检任务结果数组
    	JSONArray resultjson = datas.getJSONArray("ExamResult");
    	//转为List
    	List<UwExamTaskResult> taskResultList = JSON.parseObject(resultjson.toJSONString(), new TypeReference<List<UwExamTaskResult>>(){});
    	
    	createTaskResult(taskResultList,uwExamTaskInfo.getTaskNo(),user);
    	String taskNo = uwExamTaskInfo.getTaskNo();
    	//获取业务操作信息
    	JSONObject taskoprJson = datas.getJSONObject("ExamTaskOpr");
    	UwExamTaskOpr uwExamTaskOpr = JSON.parseObject(taskoprJson.toJSONString(), UwExamTaskOpr.class);
    	uwExamTaskOpr.setId(taskNo);
    	creatTaskOpr(uwExamTaskOpr, user);
    	
    	//删除暂存数据
    	UwExamTaskHold uwExamTaskHold = new UwExamTaskHold();
    	uwExamTaskHold.setTaskNo(taskNo);
    	//3是删除
		//不做删除，用于核保预览
    	//handleHoldDate(3, uwExamTaskHold);
	}

	@Override
	public List<String> getMustItemNo(String clientNo, String noteSeq, String hospitalId) {
		Map<String,String> condition = new HashMap<>();
		condition.put("noteSeq", noteSeq);
		condition.put("clientNo", clientNo);
		condition.put("hospitalId", hospitalId);
		return uwExamTaskInfoDao.getExamItemNoList(condition);
	}

	@Override
	public List<ImageInfo> getImageInfo(String clientNo, String docType2,String noteSeq) {
		String key = "ClientNo:"+clientNo+",noteSeq:"+noteSeq;

		// 查询体检函下发时间
		Date decisingDate = uwPhysicalExamNoteDao.getUwPhysicalExamDecidingDateByNoteSeq(clientNo, noteSeq);
		if (decisingDate == null) {
			logger.warn("[{}]Physical Exam note doesnt exist",key);
			return Lists.newArrayList();
		}
		// 查询客户在该体检函下发之后的图像上传记录，需要确保体检函已经上传
		ImageMessage imageScanEvent = null;
		try {
			imageScanEvent = uwPhysicalExamNoteDao.getImageScanEvent(clientNo,
					decisingDate,
					PHYSICAL_IMAGE_TYPE,
					WORKFLOW_IMAGE_MSG_SERVICE_ID);
			if (imageScanEvent == null) {
				return Lists.newArrayList();
			}
		} catch (Exception e) {
			logger.warn("[{}]handleImageMessage Error,{}",key,e);
		}

		List <ImageInfo> imageInfos=imageService.queryImageByMainIndexAndDocType2(clientNo, docType2);
		logger.warn("[{}]queryImageByMainIndexAndDocType2 List,{}",key,imageInfos);
		return imageInfos;
	}

	@Override
	public String getExamCharge(List<String> itemNoList,String hospitalId,String applyGender) {
		Map<String,Object> conditionMap = new HashMap<>();
		conditionMap.put("itemNoList", itemNoList);
		conditionMap.put("hospitalId", hospitalId);
		conditionMap.put("applyGender", applyGender);
		List<Map<String,String>> prizeGroup = uwExamTaskInfoDao.getExamChargeGroupCode(conditionMap);
		String sumSingle = uwExamTaskInfoDao.getExamChargeNullCode(conditionMap);
		BigDecimal bigDecimal = new BigDecimal("0");
		for (Map<String, String> map : prizeGroup) {
			BigDecimal bigDecimal1 = new BigDecimal(map.get("prize"));
			bigDecimal = bigDecimal.add(bigDecimal1);
		}
		BigDecimal bigDecimal2 = new BigDecimal("0");
		if(StringUtil.isNotEmpty(sumSingle)) {
			 bigDecimal2 =bigDecimal2.add(new BigDecimal(sumSingle));
		}
		bigDecimal = bigDecimal.add(bigDecimal2);
		return bigDecimal.toString();
	}


    @Override
	public void writeOffPhysicalExamNote(String clientNo, String noteSeq) {
		String key = "ClientNo:"+clientNo+",noteSeq:"+noteSeq;
		// 查询体检函下发时间
		Date decisingDate = uwPhysicalExamNoteDao.getUwPhysicalExamDecidingDateByNoteSeq(clientNo, noteSeq);
		if (decisingDate == null) {
			logger.warn("[{}]Physical Exam note doesnt exist",key);
			return;
		}
		// 查询客户在该体检函下发之后的图像上传记录，需要确保体检函已经上传
		ImageMessage imageScanEvent = null;
		try {
			imageScanEvent = uwPhysicalExamNoteDao.getImageScanEvent(clientNo,
					decisingDate,
					PHYSICAL_IMAGE_TYPE,
                    WORKFLOW_IMAGE_MSG_SERVICE_ID);
			if (imageScanEvent == null) {
				return;
			}
			imageMessageListener.handleImageMessage(imageScanEvent);
		} catch (Exception e) {
			logger.warn("[{}]handleImageMessage Error,{}",key,e);
		}

		logger.info("[{}]Physical Exam Note wirteoff result:{}",
				key, imageScanEvent==null?"":imageScanEvent.getHandleResult());
	}


	public String getUwExamTaskTaskNo(String taskCode){
		return uwExamTaskInfoDao.getUwExamTaskTaskNo(taskCode);
	}

	@Override
	public String getUwExamTaskTaskNo(String clientNo, String noteSeq) {
		Map<String,String> param = new HashMap<>();
		param.put("clientNo",clientNo);
		param.put("noteSeq",noteSeq);
		return uwExamTaskInfoDao.getUwExamTaskTaskNoByClientNoAndNoteSeq(param);
	}

}
