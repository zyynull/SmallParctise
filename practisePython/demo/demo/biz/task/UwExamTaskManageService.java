package demo.demo.biz.task;

import com.cmrh.nuw.healthExamination.domain.task.dto.QueryTaskParam;
import com.cmrh.nuw.healthExamination.domain.task.dto.UwExamTaskInfoDTO;
import com.cmrh.nuw.healthExamination.infra.utils.common.Pagination;

/**
 * @Description:
 * @Date: 2020/4/20 14:52
 * @Modified: Last modified by
 */
public interface UwExamTaskManageService {

    /**
     * 获取任务列表
     * */
    Pagination getTaskList(QueryTaskParam queryTaskParam);

    /**
     * 分配任务
     * @param uwExamTaskInfoDTO
     * @return
     */
    void allocateTasks(UwExamTaskInfoDTO uwExamTaskInfoDTO);

    void modifyTasks(UwExamTaskInfoDTO uwExamTaskInfoDTO);
}
