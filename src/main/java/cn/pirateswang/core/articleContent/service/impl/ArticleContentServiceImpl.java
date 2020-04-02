package cn.pirateswang.core.articleContent.service.impl;

import cn.pirateswang.common.base.service.impl.BaseServiceImpl;
import cn.pirateswang.core.articleContent.entity.ArticleContentEntity;
import cn.pirateswang.core.articleContent.mapper.ArticleContentMapper;
import cn.pirateswang.core.articleContent.service.ArticleContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleContentServiceImpl extends BaseServiceImpl<ArticleContentEntity> implements ArticleContentService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ArticleContentMapper articleContentMapper;

    @Override
    public List<ArticleContentEntity> findArticlContentByArticlId(Long articlId) {
        return articleContentMapper.findArticlContentByArticlId(articlId);
    }

}
