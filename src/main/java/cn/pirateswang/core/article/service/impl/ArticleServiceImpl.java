package cn.pirateswang.core.article.service.impl;

import cn.pirateswang.common.base.service.impl.BaseServiceImpl;
import cn.pirateswang.common.publicEnum.ErrorEnum;
import cn.pirateswang.common.publicVO.CurrentUser;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.common.utils.ResultVOUtil;
import cn.pirateswang.core.article.dto.ArticleCreateDTO;
import cn.pirateswang.core.article.dto.ArticlePageRequestDTO;
import cn.pirateswang.core.article.dto.ArticlePageResponseDTO;
import cn.pirateswang.core.article.entity.ArticleEntity;
import cn.pirateswang.core.article.mapper.ArticleMapper;
import cn.pirateswang.core.article.service.ArticleService;
import cn.pirateswang.core.articleClassify.entity.ArticleClassifyEntity;
import cn.pirateswang.core.articleClassify.service.ArticleClassifyService;
import cn.pirateswang.core.articleContent.entity.ArticleContentEntity;
import cn.pirateswang.core.articleContent.service.ArticleContentService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleEntity> implements ArticleService {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ArticleMapper articleMapper;
    
    @Autowired
    private ArticleClassifyService articleClassifyService;
    
    @Autowired
    private ArticleContentService articleContentService;

    @Override
    public ResultVO<PageInfo<ArticlePageResponseDTO>> page(ArticlePageRequestDTO requestDTO) {
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
        PageInfo<ArticlePageResponseDTO> pageInfo = new PageInfo<ArticlePageResponseDTO>(articleList);
        
        log.info("<=====ArticleServiceImpl<-----page【E N D】");
        return ResultVOUtil.success(pageInfo);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultVO<?> create(ArticleCreateDTO requestDTO) {
        log.info("=====>ArticleServiceImpl----->create【START】");
        log.info("【创建文章】REQUEST:{}",JSON.toJSONString(requestDTO));
        if(requestDTO == null){
            log.info("【创建文章】参入参数为空");
            return ResultVOUtil.error(ErrorEnum.REQUEST_PARAM_IS_NULL);
        }

        CurrentUser currentUser = getCurrentUser();
        if(currentUser == null){
            log.info("【创建文章】登录用户已失效,请重新登录");
            return ResultVOUtil.error(ErrorEnum.TOKEN_OVERDUE);
        }

        String articleTitle = requestDTO.getArticleTitle();
        Long classifyId = requestDTO.getClassifyId();
        if(StringUtils.isBlank(articleTitle)){
            log.info("【创建文章】文章标题未填写");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_TITLE_IS_NULL);
        }
        if(classifyId == null){
            log.info("【创建文章】文章分类未选择");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_CLASSIFY_ID_IS_NULL);
        }

        ArticleClassifyEntity classifyEntity = articleClassifyService.getUnDeleted(classifyId);
        if(classifyEntity == null){
            log.info("【创建文章】文章分类不存在或已删除");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_CLASSIFY_IS_NULL);
        }

        ArticleEntity articleEntity = new ArticleEntity();
        ArticleContentEntity articleContentEntity = new ArticleContentEntity();
        BeanUtils.copyProperties(requestDTO,articleEntity);
        articleEntity.setClassifyId(classifyId);
        articleEntity.setUserId(currentUser.getId());
        articleEntity.setArticleDate(new Date());
        this.save(articleEntity);
        log.info("【创建文章】生成article表内容成功,保存数据为:{}",JSON.toJSONString(articleEntity));
        articleContentEntity.setArticleContent(requestDTO.getArticleContent());
        articleContentEntity.setArticleId(articleEntity.getId());
        articleContentService.save(articleContentEntity);
        log.info("【创建文章】生成article_content表内容成功,保存数据为:{}",JSON.toJSONString(articleContentEntity));

        log.info("<=====ArticleServiceImpl<-----create【E N D】");
        return ResultVOUtil.success();
    }
}
