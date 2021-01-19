package demo.demo.domain.task.dao;

import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskOpr;
import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 体检任务暂存接口
 * */
@Repository("UwExamTaskOprDao")
public class UwExamTaskOprDao extends BaseDao{
	
	public UwExamTaskOprDao() {
        super.namespace = UwExamTaskOprDao.class.getName() + ".";
    }
	
	/**
	 * 新增业务数据
	 * */
	public Integer create(UwExamTaskOpr uwExamTaskOpr) {
		return super.insert("createUwExamTaskOpr", uwExamTaskOpr) ;
	}
	
}
