package cn.pirateswang.common.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="文章列表分页基础信息")
public class BaseSearchPageDTO {

    @ApiModelProperty(value="页码")
    private Integer pageNo = 1;

    @ApiModelProperty(value="每页数据条数")
    private Integer pageSize = 10;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
