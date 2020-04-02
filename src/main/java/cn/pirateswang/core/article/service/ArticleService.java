package cn.pirateswang.core.article.service;

import cn.pirateswang.common.base.service.BaseService;
import cn.pirateswang.common.publicVO.PageDTO;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.core.article.dto.ArticlePageRequestDTO;
import cn.pirateswang.core.article.dto.ArticlePageResponseDTO;
import cn.pirateswang.core.article.entity.ArticleEntity;

public interface ArticleService extends BaseService<ArticleEntity> {
    
    public ResultVO<PageDTO<ArticlePageResponseDTO>> page(ArticlePageRequestDTO requestDTO);
}
