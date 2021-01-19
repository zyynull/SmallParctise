package demo.demo.domain.task.dao;

import com.cmrh.nuw.healthExamination.domain.task.entity.UwExamTaskHold;
import com.cmrh.nuw.healthExamination.infra.utils.common.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 体检任务暂存接口
 * */
@Repository("UwExamTaskHoldDao")
public class UwExamTaskHoldDao extends BaseDao{
	
	public UwExamTaskHoldDao() {
        super.namespace = UwExamTaskHoldDao.class.getName() + ".";
    }
	
	/**
	 * 新增/更新暂存数据
	 * */
	public Integer merge(UwExamTaskHold uwExamTaskHold) {
		return super.insert("mergeUwExamTaskHold", uwExamTaskHold);
	}
	
	/**
	 * 查询暂存数据
	 * */
	public UwExamTaskHold get(String taskNo) {
		return (UwExamTaskHold) super.query("getUwExamTaskHold", taskNo);
	}

	
	/**
	 * 删除暂存数据,在体检结果提交后,暂存数据已经是无用的垃圾数据,可以清除
	 * */
	public Integer delete(String taskNo) {
		return super.delete("deleteUwExamTaskHold", taskNo);
	}
	
}
