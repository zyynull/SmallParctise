package demo.demo.biz.task.impl;

import com.cmrh.nuw.healthExamination.app.biz.task.UwExamTaskManageService;
import com.cmrh.nuw.healthExamination.domain.task.dao.UwExamTaskInfoDao;
import com.cmrh.nuw.healthExamination.domain.task.dto.QueryTaskParam;
import com.cmrh.nuw.healthExamination.domain.task.dto.UwExamTaskInfoDTO;
import com.cmrh.nuw.healthExamination.infra.utils.common.Pagination;
import com.cmrh.nuw.healthExamination.infra.utils.constant.SystemConstant;
import com.cmrh.uw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Date: 2020/4/20 14:52
 * @Modified: Last modified by
 */
@Service("UwExamTaskManageServiceImpl")
public class UwExamTaskManageServiceImpl implements UwExamTaskManageService {

    @Autowired
    private UwExamTaskInfoDao uwExamTaskInfoDao;



    @Override
    public Pagination getTaskList(QueryTaskParam queryTaskParam) {

        Pagination pagination = new Pagination();
        pagination.setPageSize(queryTaskParam.getRows());
        pagination.setPage(queryTaskParam.getCurrentPage());

        //判断数量
        Integer total = uwExamTaskInfoDao.selectManageTasksCount(queryTaskParam);
        pagination.setTotal(total);
        if (total <= 0)
            return pagination;

        //返回查询数据
        List<UwExamTaskInfoDTO> uwExamTaskInfoDaoTaskList = uwExamTaskInfoDao.selectManageTasks(queryTaskParam);
        pagination.setData(uwExamTaskInfoDaoTaskList);
        return pagination;
    }


    @Override
    public void allocateTasks(UwExamTaskInfoDTO uwExamTaskInfoDTO) {

        UwExamTaskInfoDTO uwExamTaskInfo = uwExamTaskInfoDao.getUwExamTaskTask(uwExamTaskInfoDTO.getTaskNo());

        if (uwExamTaskInfo == null ||
                StringUtil.equalsAny(uwExamTaskInfo.getStatus(),
                        SystemConstant.TaskStatus.DONE,
                        SystemConstant.TaskStatus.CANCEL,
                        SystemConstant.TaskStatus.EMPTY_WRITE_OFF)){
            return;
        }

        //如果认为状态是未开始，设置状态为已领取
        if (StringUtil.equalsAny(uwExamTaskInfo.getStatus(),SystemConstant.TaskStatus.NOT_YET)) {
            uwExamTaskInfoDTO.setStatus(SystemConstant.TaskStatus.RECEIVED);
        }
        uwExamTaskInfoDao.allocateTasks(uwExamTaskInfoDTO);
    }

    @Override
    public void modifyTasks(UwExamTaskInfoDTO uwExamTaskInfoDTO) {

        UwExamTaskInfoDTO uwExamTaskInfo = uwExamTaskInfoDao.getUwExamTaskTask(uwExamTaskInfoDTO.getTaskNo());

        if (uwExamTaskInfo == null ||
                StringUtil.equalsAny(uwExamTaskInfo.getStatus(),
                        SystemConstant.TaskStatus.DONE,
                        SystemConstant.TaskStatus.EMPTY_WRITE_OFF)){
            return;
        }

        uwExamTaskInfoDao.modifyTasks(uwExamTaskInfoDTO);
    }
}
