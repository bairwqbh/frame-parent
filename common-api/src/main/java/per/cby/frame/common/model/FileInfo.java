package per.cby.frame.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文件保存信息
 * 
 * @author chenboyang
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "FileInfo对象", description = "文件信息")
public class FileInfo {

    @ApiModelProperty("文件原名称")
    private String originName;

    @ApiModelProperty("文件保存至服务器名称")
    private String fileName;

    @ApiModelProperty("文件保存至服务器的相对路径")
    private String path;

    @ApiModelProperty("文件后缀")
    private String suffix;

    @ApiModelProperty("文件大小")
    private long size;

}
