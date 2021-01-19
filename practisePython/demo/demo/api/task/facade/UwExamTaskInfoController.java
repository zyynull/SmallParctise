package demo.demo.api.task.facade;

import com.alibaba.fastjson.JSONObject;
import com.cmrh.nuw.healthExamination.app.biz.task.UwExamTaskInfoService;
import com.cmrh.nuw.healthExamination.domain.task.dto.QueryTaskParam;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskHold;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskInfo;
import com.cmrh.nuw.healthExamination.infra.utils.common.WebResponse;
import com.cmrh.nuw.healthExamination.infra.utils.constant.SystemConstant;
import com.cmrh.uw.commons.web.util.StringUtil;
import com.cmrh.uw.image.biz.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 核保体检 - 体检任务相关
 * 
 * @author ex-zrh001 2019/12/31
 * */

@RequestMapping("uwExam/ExamTask")
@Controller
public class UwExamTaskInfoController {
    
	private static final Logger logger = LoggerFactory.getLogger(UwExamTaskInfoController.class);
    @Autowired
    private UwExamTaskInfoService uwExamTaskInfoService;
    @Autowired
    private ImageService imageService;

    
    /**
     * 任务池
     *
     *
     * @param queryTaskParam
     * @return
     */
    @RequestMapping(value = "/TaskList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getTaskList(HttpServletRequest request,QueryTaskParam queryTaskParam) {
    	logger.info("uwExam/ExamTask/TaskList:queryTaskParam:" + queryTaskParam);

        String remoteUser = request.getRemoteUser();
    	if(StringUtil.isNotEmpty(queryTaskParam.getUserCode())) {
    		//说明是私人任务池
    		queryTaskParam.setUserCode(remoteUser);
    	}
        return WebResponse.create(uwExamTaskInfoService.getTaskList(queryTaskParam,remoteUser)).status(HttpStatus.OK);
    }
    
    /**
     * 设为个人任务池
     *
     * @param userCode 登录用户
     * @param taskNo 任务编号
     * @return
     */
    @RequestMapping(value = "/setPrivate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity setPrivate(HttpServletRequest request,String taskNo) {
    	logger.info("uwExam/ExamTask/setPrivate:taskNo:" + taskNo);
        String userCode = request.getRemoteUser();
    	uwExamTaskInfoService.setPrivate(userCode, taskNo);
    	return WebResponse.create().status(HttpStatus.OK);
    }
    
    /**
     * 体检信息录入基础信息及体检信息查询
     *
     * @param noteSeq 体检函编号
     * @param clientNo 客户号
     * @param taskNo 任务编号
     * @param hospitalId 医院id 非必须
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/taskBaseInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity taskBaseInfo(HttpServletRequest request,String noteSeq,String clientNo,String taskNo) throws IOException {
    	logger.info("uwExam/ExamTask/taskBaseInfo:taskNo:" + taskNo + "/clientNo:"+clientNo);
    	Map<String,Object> result = uwExamTaskInfoService.getTaskBaseInfo(noteSeq, clientNo, taskNo);
    	return WebResponse.create(result).status(HttpStatus.OK);
    }
    
    
    /**
     * 暂存数据的增改查
     *
     * @param choose 流程编号 1增,2查,3改
     * @param uwExamTaskHold 暂存内容
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/handleHoldDate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity handleHoldDate(HttpServletRequest request,@RequestParam("choose")int choose,
    		@RequestBody UwExamTaskHold uwExamTaskHold) throws IOException {
    	logger.info("uwExam/ExamTask/handleHoldDate:choose:" + choose + "/uwExamTaskHold:"+uwExamTaskHold);
    	String user = request.getRemoteUser();
    	uwExamTaskHold.setUpdatedUser(user);
    	uwExamTaskHold.setCreatedUser(user);
    	Map<String,Object> result = uwExamTaskInfoService.handleHoldDate(choose, uwExamTaskHold);
    	UwExamTaskInfo uwExamTaskInfo = new UwExamTaskInfo();
    	uwExamTaskInfo.setTaskNo(uwExamTaskHold.getTaskNo());
    	//状态1是暂存
    	uwExamTaskInfo.setStatus(SystemConstant.ONE);
    	uwExamTaskInfoService.updateTaskInfo(uwExamTaskInfo,user);
		return WebResponse.create(result).status(HttpStatus.OK);
    }


    /**
     * 查询暂存数据
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/taskResultDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getTaskResultDetail(@RequestParam("clientNo") String clientNo,@RequestParam("noteSeq")String noteSeq)  {
        String taskNo = uwExamTaskInfoService.getUwExamTaskTaskNo(clientNo,noteSeq);
        UwExamTaskHold uwExamTaskHold = new UwExamTaskHold();
        uwExamTaskHold.setTaskNo(taskNo);
        Map<String,Object> result = uwExamTaskInfoService.handleHoldDate(2, uwExamTaskHold);
        UwExamTaskInfo uwExamTaskInfo = new UwExamTaskInfo();
        uwExamTaskInfo.setTaskNo(uwExamTaskHold.getTaskNo());
        return WebResponse.create(result).status(HttpStatus.OK);
    }


    
    /**
     * 体检任务结果录入
     *
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/createTaskResult", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createTaskResult(HttpServletRequest request,@RequestBody JSONObject datas) {
    	logger.info("uwExam/ExamTask/createTaskResult:datas:" + datas);
    	String user = request.getRemoteUser();
    	uwExamTaskInfoService.commitTaskInfoResult(datas, user);
        String clientNo = datas.getString("clientNo");
        String noteSeq = datas.getString("noteSeq");
    	uwExamTaskInfoService.writeOffPhysicalExamNote(clientNo,noteSeq);
    	return WebResponse.create().status(HttpStatus.OK);
    }
    
    

    /**
     * 取消体检任务
     *
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/cancelTask", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity cancelTask(String taskNo,String remark,String status,HttpServletRequest request) {
    	logger.info("uwExam/ExamTask/cancelTask:taskNo:" + taskNo+"/remark:"+remark);
    	String user = request.getRemoteUser();
    	uwExamTaskInfoService.cancelTask(taskNo, remark, user,status);
    	return WebResponse.create().status(HttpStatus.OK);
    }
    
    
    /**
     * 体检项目获取
     *
     * @return
     */
    @RequestMapping(value = "/allExamResult", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllExamResult(String hospitalId,String gender){
    	logger.info("uwExam/ExamTask/allExamResult:hospitalId:" + hospitalId +"/gender:"+gender);
    	return WebResponse.create(uwExamTaskInfoService.getAllExamResult(hospitalId,gender)).status(HttpStatus.OK);
    }
    
    /**
     * 获取影像资料
     * */
    @RequestMapping(value = "/taskImage", method = RequestMethod.GET)
    public ResponseEntity taskImage(String clientNo,String noteSeq) {
    	//1123是体检照会 写死即可
    	return WebResponse.create(uwExamTaskInfoService.getImageInfo(clientNo, "1123",noteSeq)).status(HttpStatus.OK);
    }
    
    /**
     * 获取必填项列表
     * */
    @RequestMapping(value = "/getMustItemNo", method = RequestMethod.GET)
    public ResponseEntity getMustItemNo(HttpServletRequest request,String noteSeq,String clientNo,String hospitalId) {
    	List<String> itemNoList = uwExamTaskInfoService.getMustItemNo(clientNo, noteSeq, hospitalId);
    	//用于计算体检费用
    	request.getSession().setAttribute("itemNoList", itemNoList);
    	return WebResponse.create(itemNoList).status(HttpStatus.OK);
    }
    
    /**
     * 获取体检费用
     * */
    @RequestMapping(value = "/getExamCharge", method = RequestMethod.GET)
    public ResponseEntity getExamCharge(HttpServletRequest request,String hospitalId,String applyGender) {
    	List<String> itemNoList = (List<String>) request.getSession().getAttribute("itemNoList");
    	String examCharge = "0";
    	if(!CollectionUtils.isEmpty(itemNoList)) {
    		examCharge = uwExamTaskInfoService.getExamCharge(itemNoList,hospitalId,applyGender);
    	}
    	//清空会话值
    	request.getSession().setAttribute("itemNoList", null);
    	return WebResponse.create(examCharge).status(HttpStatus.OK);
    }
}
