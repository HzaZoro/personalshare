package cn.pirateswang.core.article.service.impl;

import cn.pirateswang.common.base.service.impl.BaseServiceImpl;
import cn.pirateswang.common.publicEnum.ErrorEnum;
import cn.pirateswang.common.publicVO.PageDTO;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.common.utils.ResultVOUtil;
import cn.pirateswang.core.article.dto.ArticlePageRequestDTO;
import cn.pirateswang.core.article.dto.ArticlePageResponseDTO;
import cn.pirateswang.core.article.entity.ArticleEntity;
import cn.pirateswang.core.article.mapper.ArticleMapper;
import cn.pirateswang.core.article.service.ArticleService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleEntity> implements ArticleService {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResultVO<PageDTO<ArticlePageResponseDTO>> page(ArticlePageRequestDTO requestDTO) {
        log.info("=====>ArticleServiceImpl----->page【START】");
        log.info("【查询文章分页】REQUEST:{}", JSON.toJSONString(requestDTO));
        if(requestDTO == null){
            log.info("请求参数为空,重置该请求");
            requestDTO = new ArticlePageRequestDTO();
        }

        Page<ArticleEntity> page = PageHelper.startPage(requestDTO.getPageNo(), requestDTO.getPageSize());
        List<ArticlePageResponseDTO> articleList = articleMapper.queryByParams(requestDTO.getArticleTitle(), requestDTO.getStartDate(), requestDTO.getEndDate());
        if(articleList != null && !articleList.isEmpty()){
            log.info("【查询文章分页】共查询到{}条记录",articleList.size());
        }else{
            log.info("【查询文章分页】未查询到相关记录");
        }
        PageDTO<ArticlePageResponseDTO> pageDTO = new PageDTO<>();
        pageDTO.setPageNo(requestDTO.getPageNo());
        pageDTO.setPageSize(requestDTO.getPageSize());
        pageDTO.setTotalCount(page.getTotal());
        pageDTO.setResult(articleList);

        log.info("<=====ArticleServiceImpl<-----page【E N D】");
        return ResultVOUtil.success(pageDTO);
    }
}
