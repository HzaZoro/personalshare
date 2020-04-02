package cn.pirateswang.core.article.dto;

import cn.pirateswang.common.base.dto.BaseSearchPageDTO;

import java.util.Date;

public class ArticlePageRequestDTO extends BaseSearchPageDTO {

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 发表搜索开始时间
     */
    private Date startDate;

    /**
     * 发表搜索结束时间
     */
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
}
