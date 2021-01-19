package demo.demo.infra.utils.common;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * @Author: Mo Zhipeng
 * @Description: dao层基础类
 * @Date: 2019/8/8 15:02
 * @Modified: Last modified by
 */
public class BaseDao<T> extends SqlMapClientDaoSupport {
    protected String namespace;

    public int update(String statementName, Object params) {
        return super.getSqlMapClientTemplate().update(namespace + statementName, params);
    }

    public List<T> queryList(String statementName) {
        return super.getSqlMapClientTemplate().queryForList(namespace + statementName);
    }


    public List<T> queryList(String statementName, Object params) {
        return super.getSqlMapClientTemplate().queryForList(namespace + statementName, params);
    }

    public Integer queryCount(String statementName, Object params) {
        return (Integer) super.getSqlMapClientTemplate().queryForObject(namespace + statementName, params);
    }

    public Object query(String statementName, Object params) {
        return super.getSqlMapClientTemplate().queryForObject(namespace + statementName, params);
    }

    public Object query(String statementName) {
        return super.getSqlMapClientTemplate().queryForObject(namespace + statementName);
    }

    public Integer insert(String statementName, Object params) {
        return (Integer) super.getSqlMapClientTemplate().insert(namespace + statementName, params);
    }
    
    public Integer delete(String statementName, Object params) {
    	return super.getSqlMapClientTemplate().delete(namespace +statementName, params);
    }

}
