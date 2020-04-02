package cn.pirateswang.core.articleContent.mapper;

import cn.pirateswang.common.base.mapper.BaseMapper;
import cn.pirateswang.core.articleContent.entity.ArticleContentEntity;
import cn.pirateswang.core.articleContent.mapper.sql.ArticleContentProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ArticleContentMapper extends BaseMapper<ArticleContentEntity> {

    @SelectProvider(type = ArticleContentProvider.class,method = "findArticlContentByArticlId")
    public List<ArticleContentEntity> findArticlContentByArticlId(@Param("articlId") Long articlId);
    
}
