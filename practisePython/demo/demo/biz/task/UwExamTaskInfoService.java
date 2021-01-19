package demo.demo.biz.task;

import com.alibaba.fastjson.JSONObject;
import com.cmrh.im.http.model.ImageInfo;
import com.cmrh.nuw.healthExamination.domain.task.dto.QueryTaskParam;
import com.cmrh.nuw.healthExamination.domain.task.dto.TaskResultClassifyDTO;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskHold;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskInfo;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskOpr;
import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskResult;
import com.cmrh.nuw.healthExamination.infra.utils.common.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 体检任务信息接口
 * */
public interface UwExamTaskInfoService {

	/**
	 * 获取任务列表
	 * */
	Pagination getTaskList(QueryTaskParam queryTaskParam, String userCode);

	/**
	 * 设为个人任务池
	 * **/
	void setPrivate(String userCode, String taskNo);

	/**
	 * 获取体检结果明细中的体检项目部分
	 * **/
	Map<String,List<String>> getExamProject(String clientNo, String noteSeq);

	/**
	 * 获取体检结果明细中的体检结果录入部分
	 * */
	List<TaskResultClassifyDTO> getAllExamResult(String hospitalId, String gender);

	/**
	 * 获取体检结果明细中客户信息及体检信息部分
	 * */
	Map<String,Object> getTaskBaseInfo(String noteSeq, String clientNo, String taskNo);

	/**
	 * 处理暂存数据 增删改查都在同一个方法内
	 *
	 * @param choose 流程选择  1增/改,2查,3删
	 * @param taskNo 删查所需
	 * @param uwExamTaskHold 增改
	 * */
	Map<String,Object> handleHoldDate(int choose, UwExamTaskHold uwExamTaskHold);

	/**
	 * 体检信息录入
	 *
	 * */
	void updateTaskInfo(UwExamTaskInfo uwExamTaskInfo, String user);

	/**
	 * 生成一条体检任务信息
	 * */
	void createTaskInfo(String clientNo, String noteSeq, String taskType, String createdUser);

	/**
	 * 体检结果录入
	 * */
	void createTaskResult(List<UwExamTaskResult> taskResultList, String taskNo, String user);

	/**
	 * 取消体检结果录入
	 * @param taskNo
	 * @param remark 说明
	 * @param user
	 * */
	void cancelTask(String taskNo, String remark, String user, String status);

	/**
	 * 通过核保任务取消体检任务
	 * @param taskCode 核保任务号
	 * @param
	 * @param user
	 * */
	void cancelTask(String taskCode, String user, String status);

	/**
	 * 添加业务操作数据
	 * @param uwExamTaskOpr
	 */
	void creatTaskOpr(UwExamTaskOpr uwExamTaskOpr, String user);

	/**
	 * 体检结果提交, 保证在同一个事务中
	 * */
	void commitTaskInfoResult(JSONObject datas, String user);

	/**
	 * 获取必填项
	 * */
	List<String> getMustItemNo(String clientNo, String noteSeq, String hospitalId);



	List<ImageInfo> getImageInfo(String clientNo, String docType2, String noteSeq);


	/**
	 * 获取体检费用
	 * */
	String getExamCharge(List<String> itemNoList, String hospitalId, String applyGender);

    /**
     * 回销体检函
     * @return
     */
    void writeOffPhysicalExamNote(String clientNo, String noteSeq);

	/**
	 * 通过核保任务号获取提交任务号
	 * @param taskCode
	 * @return
	 */
    String getUwExamTaskTaskNo(String taskCode);


	/**
	 * 通过客户号及任务序列获取体检任务号
	 * @param clientNo
	 * @param noteSeq
	 * @return taskNo
	 */
	String getUwExamTaskTaskNo(String clientNo, String noteSeq);
}
