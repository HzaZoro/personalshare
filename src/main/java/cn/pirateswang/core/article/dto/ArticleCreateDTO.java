package cn.pirateswang.core.article.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "文章创建信息")
public class ArticleCreateDTO {

    @ApiModelProperty(value = "文章标题",required = true)
    private String articleTitle;

    @ApiModelProperty(value = "文章简介",required = false)
    private String articleSynopsis;

    @ApiModelProperty(value = "文章分类主键ID",required = true)
    private Long classifyId;

    @ApiModelProperty(value = "文章内容",required = false)
    private String articleContent;

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

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleSynopsis() {
        return articleSynopsis;
    }

    public void setArticleSynopsis(String articleSynopsis) {
        this.articleSynopsis = articleSynopsis;
    }
}
