package per.cby.frame.common.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import per.cby.frame.common.util.BaseUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 分页信息类
 * 
 * @author chenboyang
 * 
 * @param <T> 业务类型
 */
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "Page对象", description = "分页信息")
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 最小页号 */
    private final int MIN_PAGE = 1;

    /** 最小行数 */
    private final int MIN_SIZE = 1;

    /** 最大行数 */
    private final int MAX_SIZE = 1000;

    @ApiModelProperty("当前页")
    private int page = 1;

    @ApiModelProperty("每页总行数")
    private int size = 10;

    @ApiModelProperty("总记录数")
    private int total;

    @ApiModelProperty("总页数")
    private int pages;

    @ApiModelProperty(value = "数据集", hidden = true)
    private List<T> list;

    public Page(int page, int size) {
        setPage(page);
        setSize(size);
    }

    public Page(Page<?> page) {
        setPage(page.getPage());
        setSize(page.getSize());
        setTotal(page.getTotal());
        setPages(page.getPage());
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page >= MIN_PAGE) {
            this.page = page;
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size >= MIN_SIZE && size <= MAX_SIZE) {
            this.size = size;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        int pages = (total / size) + (total % size == 0 ? 0 : 1);
        setPages(pages);
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
        if (pages > 0 && pages < page) {
            setPage(pages);
        }
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 将分布信息对象转换为Map对象
     * 
     * @return Map对象
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = BaseUtil.newHashMap(5);
        map.put("page", page);
        map.put("size", size);
        map.put("total", total);
        map.put("pages", pages);
        map.put("list", list);
        return map;
    }

}
