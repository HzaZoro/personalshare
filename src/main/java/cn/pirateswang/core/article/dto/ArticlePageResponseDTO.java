package cn.pirateswang.core.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value="文章列表分页查询返回信息")
public class ArticlePageResponseDTO {

    @ApiModelProperty(value="文章信息主键ID")
    private Long id;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="文章发表时间")
    private Date articleDate;

    @ApiModelProperty(value="文章标题")
    private String articleTitle;

    @ApiModelProperty(value="文章简介")
    private String articleSynopsis;

    @ApiModelProperty(value="文章分类主键ID")
    private Long classifyId;

    @ApiModelProperty(value="文章作者主键ID")
    private Long userId;

    @ApiModelProperty(value="文章浏览量")
    private Long articleViews;

    @ApiModelProperty(value="文章作者名称")
    private String userName;

    @ApiModelProperty(value="文章分类名称")
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
