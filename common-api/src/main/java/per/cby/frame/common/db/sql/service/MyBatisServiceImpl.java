package per.cby.frame.common.db.sql.service;

import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.db.sql.mapper.Mapper;

/**
 * Mybatis数据访问通用服务
 * 
 * @author chenboyang
 */
public class MyBatisServiceImpl implements MyBatisService {

    @Autowired(required = false)
    private Mapper mapper;

    @Override
    public Mapper mapper() {
        return mapper;
    }

}
