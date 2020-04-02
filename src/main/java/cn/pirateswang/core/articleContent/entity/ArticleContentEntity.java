package cn.pirateswang.core.articleContent.entity;

import cn.pirateswang.common.base.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "article_content")
public class ArticleContentEntity extends BaseEntity {

    /**
     * 文章主键ID
     */
    private Long articleId;

    /**
     * 文章内容
     */
    private String articleContent;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}
