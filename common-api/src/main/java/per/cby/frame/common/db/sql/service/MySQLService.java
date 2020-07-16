package per.cby.frame.common.db.sql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import per.cby.frame.common.db.sql.mapper.DDLMapper;
import per.cby.frame.common.db.sql.mapper.MySQLMapper;

/**
 * Mybatis数据访问通用服务
 * 
 * @author chenboyang
 */
@Lazy
@Service("__MYSQL_SERVICE__")
public class MySQLService implements DDLService {

    @Autowired(required = false)
    private MySQLMapper mysqlMapper;

    @Override
    public DDLMapper ddlMapper() {
        return mysqlMapper;
    }

}
