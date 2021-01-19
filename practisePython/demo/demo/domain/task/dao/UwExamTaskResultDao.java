package demo.demo.domain.task.dao;

import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskResult;
import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 体检任务暂存接口
 * */
@Repository("UwExamTaskResultDao")
public class UwExamTaskResultDao extends BaseDao{
	
	public UwExamTaskResultDao() {
        super.namespace = UwExamTaskResultDao.class.getName() + ".";
    }
	
	public Integer createTaskResult(List<UwExamTaskResult> taskResultList) {
		return super.insert("createTaskResult", taskResultList);
	}
	
}
