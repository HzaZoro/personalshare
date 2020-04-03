package cn.pirateswang.core.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ArticlePageResponseDTO {
    
    private Long id;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date articleDate;
    
    private String articleTitle;

    private String articleSynopsis;
    
    private Long classifyId;
    
    private Long userId;
    
    private Long articleViews;
    
    private String userName;
    
    private String classifyName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getArticleSynopsis() {
        return articleSynopsis;
    }

    public void setArticleSynopsis(String articleSynopsis) {
        this.articleSynopsis = articleSynopsis;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleViews() {
        return articleViews;
    }

    public void setArticleViews(Long articleViews) {
        this.articleViews = articleViews;
    }
}
