package per.cby.frame.common.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <h1>数据对象基础模型</h1>
 * <p>
 * 主要包含了主键、创建时间、更新时间等
 * </p>
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public abstract class BaseModel<T> extends Model<BaseModel<T>> implements BaseMethod {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

    @ApiModelProperty("创建时间")
    protected LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    protected LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
