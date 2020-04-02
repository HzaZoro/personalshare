package cn.pirateswang.common.publicVO;

import java.util.Collections;
import java.util.List;

public class PageDTO<T> {
    
    private Integer pageNo = 1;
    
    private Integer pageSize = 10;
    
    private Long totalCount;
    
    private List<T> result = Collections.emptyList();

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

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
