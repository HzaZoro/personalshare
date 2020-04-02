package cn.pirateswang.core.articleClassify.entity;

import cn.pirateswang.common.base.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "article_classify")
public class ArticleClassifyEntity extends BaseEntity {

    /**
     * 分类名称
     */
    private String classifyName;

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}
