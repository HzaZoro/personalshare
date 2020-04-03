package cn.pirateswang.core.article.dto;

public class ArticleCreateDTO {

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章简介
     */
    private String articleSynopsis;

    /**
     * 文章分类ID
     */
    private Long classifyId;

    /**
     * 文章内容
     */
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
