package cn.pirateswang.core.articleContent.service;

import cn.pirateswang.common.base.service.BaseService;
import cn.pirateswang.core.articleContent.entity.ArticleContentEntity;

import java.util.List;

public interface ArticleContentService extends BaseService<ArticleContentEntity> {
    
    public List<ArticleContentEntity> findArticlContentByArticlId(Long articlId);
    
}
