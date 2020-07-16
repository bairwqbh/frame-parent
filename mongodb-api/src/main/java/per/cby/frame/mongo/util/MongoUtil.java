package per.cby.frame.mongo.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import lombok.experimental.UtilityClass;

/**
 * MongoDB数据库帮助类
 * 
 * @author chenboyang
 * @since 2019年12月19日
 *
 */
@UtilityClass
public class MongoUtil {

    /**
     * 根据字段进行升序排序
     * 
     * @param query  查询条件
     * @param fields 字段
     * @return 查询条件
     */
    public Query orderByAsc(Query query, String... fields) {
        return order(query, Direction.ASC, fields);
    }

    /**
     * 根据字段进行降序排序
     * 
     * @param query  查询条件
     * @param fields 字段
     * @return 查询条件
     */
    public Query orderByDesc(Query query, String... fields) {
        return order(query, Direction.DESC, fields);
    }

    /**
     * 根据字段排序
     * 
     * @param query     查询条件
     * @param direction 排序方式
     * @param fields    字段
     * @return 查询条件
     */
    public Query order(Query query, Direction direction, String... fields) {
        return query.with(Sort.by(direction, fields));
    }

    /**
     * 模糊匹配
     * 
     * @param keyword 关键字
     * @param fields  字段
     * @return 查询条件
     */
    public Query like(Query query, String keyword, String... fields) {
        return regex(query, ".*" + keyword + ".*", fields);
    }

    /**
     * 左匹配
     * 
     * @param query   查询条件
     * @param keyword 关键字
     * @param fields  字段
     * @return 查询条件
     */
    public Query leftLike(Query query, String keyword, String... fields) {
        return regex(query, keyword + ".*", fields);
    }

    /**
     * 右匹配
     * 
     * @param query   查询条件
     * @param keyword 关键字
     * @param fields  字段
     * @return 查询条件
     */
    public Query rightLike(Query query, String keyword, String... fields) {
        return regex(query, ".*" + keyword, fields);
    }

    /**
     * 表达式匹配
     * 
     * @param query  查询条件
     * @param regex  表达式
     * @param fields 字段
     * @return 查询条件
     */
    public Query regex(Query query, String regex, String... fields) {
        if (query == null || regex == null || ArrayUtils.isEmpty(fields)) {
            return query;
        }
        Criteria[] criterias = new Criteria[fields.length];
        for (int i = 0; i < fields.length; i++) {
            criterias[i] = Criteria.where(fields[i]).regex(regex);
        }
        return query.addCriteria(new Criteria().orOperator(criterias));
    }

    /**
     * 区间匹配
     * 
     * @param query 查询条件
     * @param field 字段
     * @param start 开始
     * @param end   结束
     * @return 查询条件
     */
    public Query bettwen(Query query, String field, Object start, Object end) {
        if (query == null || StringUtils.isBlank(field) || (start == null && end == null)) {
            return query;
        }
        Criteria criteria = Criteria.where(field);
        if (start != null) {
            criteria.gte(start);
        }
        if (end != null) {
            criteria.lt(end);
        }
        return query.addCriteria(criteria);
    }

}
