package cn.pirateswang.core.articleComment.entity;

import cn.pirateswang.common.base.entity.BaseEntity;

public class ArticleCommentEntity extends BaseEntity {

    /**
     * 用户主键ID
     */
    private Long userId;

    /**
     * 文章主键ID
     */
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
