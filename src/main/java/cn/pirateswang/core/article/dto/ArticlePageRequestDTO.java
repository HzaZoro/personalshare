package cn.pirateswang.core.article.dto;

import cn.pirateswang.common.base.dto.BaseSearchPageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value="文章列表分页请求信息")
public class ArticlePageRequestDTO extends BaseSearchPageDTO {

    @ApiModelProperty(value="文章标题",required = false)
    private String articleTitle;

    @ApiModelProperty(value="作者",required = false)
    private String userName;

    @ApiModelProperty(value="文章分类",required = false)
    private Long classifyId;

    @ApiModelProperty(value="文章发表开始时间",required = false)
    private Date startDate;

    @ApiModelProperty(value="文章发表结束时间",required = false)
    private Date endDate;

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }
}
