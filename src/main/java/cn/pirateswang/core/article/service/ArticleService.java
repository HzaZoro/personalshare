package cn.pirateswang.core.article.service;

import cn.pirateswang.common.base.service.BaseService;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.core.article.dto.*;
import cn.pirateswang.core.article.entity.ArticleEntity;
import com.github.pagehelper.PageInfo;

public interface ArticleService extends BaseService<ArticleEntity> {
    
    public ResultVO<PageInfo<ArticlePageResponseDTO>> page(ArticlePageRequestDTO requestDTO);
    
    public ResultVO<?> create(ArticleCreateDTO requestDTO);

    public ResultVO<?> update(ArticleUpdateDTO requestDTO);

    public ResultVO<ArticleDetailDTO> detail(Long articleId);
    
    public ResultVO<?> enable(Long articleId);

    public ResultVO<?> disable(Long articleId);
    
    public ResultVO<?> delete(Long articleId);
}
