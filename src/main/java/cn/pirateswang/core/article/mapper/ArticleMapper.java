package cn.pirateswang.core.article.mapper;

import cn.pirateswang.common.base.mapper.BaseMapper;
import cn.pirateswang.core.article.dto.ArticleDetailDTO;
import cn.pirateswang.core.article.dto.ArticlePageResponseDTO;
import cn.pirateswang.core.article.entity.ArticleEntity;
import cn.pirateswang.core.article.mapper.sql.ArticleProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {
    
    @SelectProvider(type = ArticleProvider.class,method = "queryByParams")
    public List<ArticlePageResponseDTO> queryByParams(@Param("articleTitle") String articleTitle, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @SelectProvider(type = ArticleProvider.class,method = "detail")
    public ArticleDetailDTO detail(@Param("articleId") Long articleId );
}
