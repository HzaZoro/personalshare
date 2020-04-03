package cn.pirateswang.core.article.entity;

import cn.pirateswang.common.base.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "article")
public class ArticleEntity extends BaseEntity {

    /**
     * 用户ID（作者）
     */
    private Long userId;

    /**
     * 文章分类主键ID
     */
    private Long classifyId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章简介
     */
    private String articleSynopsis;

    /**
     * 浏览量
     */
    private Long articleViews;

    /**
     * 文章发表时间
     */
    private Date articleDate;

    /**
     * 是否启用  0:否  1:是
     */
    private Integer inUse;

    public Long getUserId() {
        return userId;
    }

    public String getArticleSynopsis() {
        return articleSynopsis;
    }

    public void setArticleSynopsis(String articleSynopsis) {
        this.articleSynopsis = articleSynopsis;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Long getArticleViews() {
        return articleViews;
    }

    public void setArticleViews(Long articleViews) {
        this.articleViews = articleViews;
    }

    public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }

    public Integer getInUse() {
        return inUse;
    }

    public void setInUse(Integer inUse) {
        this.inUse = inUse;
    }
}
