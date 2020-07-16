package per.cby.frame.mongo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * Mongo基础对象
 * </p>
 *
 * @author chenboyang
 * @since 2019-11-14
 */
@Data
@Accessors(chain = true)
public abstract class BaseMongoModel {

    @ApiModelProperty("主键")
    protected String id;

}
