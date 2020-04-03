package cn.pirateswang.core.article.service.impl;

import cn.pirateswang.common.base.service.impl.BaseServiceImpl;
import cn.pirateswang.common.publicEnum.ErrorEnum;
import cn.pirateswang.common.publicVO.CurrentUser;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.common.utils.ResultVOUtil;
import cn.pirateswang.core.article.dto.*;
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

    @Override
    public ResultVO<?> update(ArticleUpdateDTO requestDTO) {
        log.info("=====>ArticleServiceImpl----->update【START】");
        log.info("【更新文章】REQUEST:{}",JSON.toJSONString(requestDTO));

        String articleTitle = requestDTO.getArticleTitle();
        Long classifyId = requestDTO.getClassifyId();
        Long id = requestDTO.getId();
        if(id == null){
            log.info("【更新文章】article表主键ID为空");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_ID_IS_NULL);
        }
        if(StringUtils.isBlank(articleTitle)){
            log.info("【更新文章】文章标题未填写");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_TITLE_IS_NULL);
        }
        if(classifyId == null){
            log.info("【更新文章】文章分类未选择");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_CLASSIFY_ID_IS_NULL);
        }

        ArticleEntity articleEntity = this.getUnDeleted(id);
        if(articleEntity == null){
            log.info("【更新文章】该文章信息不存在或已被删除");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_IS_NULL);
        }

        ArticleClassifyEntity classifyEntity = articleClassifyService.getUnDeleted(classifyId);
        if(classifyEntity == null){
            log.info("【更新文章】文章分类不存在或已删除");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_CLASSIFY_IS_NULL);
        }

        if(articleEntity.getClassifyId() == null || articleEntity.getClassifyId().longValue() != classifyId.longValue()){
            log.info("【更新文章】文章分类ID已改变,原分类ID:{} ----> 现分类ID:{} ",articleEntity.getClassifyId(),classifyId);
            articleEntity.setClassifyId(classifyId);
        }else{
            log.info("【更新文章】文章分类未发生改变");
        }

        if(!StringUtils.equals(articleTitle,articleEntity.getArticleTitle())){
            log.info("【更新文章】文章标题已改变，原文章标题：{} ----> 现文章标题: {}",articleEntity.getArticleTitle(),articleTitle);
            articleEntity.setArticleTitle(articleTitle);
        }else{
            log.info("【更新文章】文章标题未发生改变");
        }

        ArticleContentEntity articleContentEntity = null;
        List<ArticleContentEntity> articlContentList = articleContentService.findArticlContentByArticlId(id);
        if(articlContentList == null || articlContentList.isEmpty()){
            log.info("【更新文章】未发现相关文章内容信息,新增一条文件内容信息");
            articleContentEntity = new ArticleContentEntity();
        }else if(articlContentList.size() == 1){
            log.info("【更新文章】发现一条文章内容信息");
            articleContentEntity = articlContentList.get(0);
        }else{
            log.info("【更新文章】发现{}条文章内容信息,需删除多余记录",articlContentList.size());
            for(int cnt = 0;cnt < articlContentList.size();cnt ++){
                ArticleContentEntity articleContent = articlContentList.get(cnt);
                if(cnt == 0){
                    log.info("【更新文章】保留该文章内容信息,主键ID:{}",articleContent.getId());
                    articleContentEntity = articleContent;
                }else{
                    log.info("【更新文章】该文章内容需删除,主键ID:{}",articleContent.getId());
                    articleContent.setDeleteFlg(1);
                    articleContentService.save(articleContent);
                }
            }
        }

        articleContentEntity.setArticleContent(StringUtils.isBlank(requestDTO.getArticleContent())? StringUtils.EMPTY:requestDTO.getArticleContent());
        articleContentService.save(articleContentEntity);
        this.save(articleEntity);
        log.info("【更新文章】文章内容已更新");
        log.info("<=====ArticleServiceImpl<-----update【E N D】");
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO<ArticleDetailDTO> detail(Long articleId) {
        log.info("=====>ArticleServiceImpl----->detail【START】");
        log.info("【文章详情】REQUEST: 文章主键ID: {}",articleId);

        if(articleId == null){
            log.info("【文章详情】article表主键ID为空");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_ID_IS_NULL);
        }

        ArticleDetailDTO detail = articleMapper.detail(articleId);
        if(detail == null){
            log.info("【文章详情】未发现文章详情信息,该详情不存在或已删除,articleId:{}",articleId);
            return ResultVOUtil.error(ErrorEnum.ARTICLE_IS_NULL);
        }

        ArticleContentEntity articleContentEntity = null;
        List<ArticleContentEntity> articlContentList = articleContentService.findArticlContentByArticlId(articleId);
        if(articlContentList == null || articlContentList.isEmpty()){
            log.info("【文章详情】未发现相关文章内容信息");
            articleContentEntity = new ArticleContentEntity();
        }else if(articlContentList.size() == 1){
            log.info("【文章详情】发现一条文章内容信息");
            articleContentEntity = articlContentList.get(0);
        }else{
            log.info("【文章详情】发现{}条文章内容信息,需删除多余记录",articlContentList.size());
            for(int cnt = 0;cnt < articlContentList.size();cnt ++){
                ArticleContentEntity articleContent = articlContentList.get(cnt);
                if(cnt == 0){
                    log.info("【文章详情】保留该文章内容信息,主键ID:{}",articleContent.getId());
                    articleContentEntity = articleContent;
                }else{
                    log.info("【文章详情】该文章内容需删除,主键ID:{}",articleContent.getId());
                    articleContent.setDeleteFlg(1);
                    articleContentService.save(articleContent);
                }
            }
        }

        detail.setArticleContent(articleContentEntity.getArticleContent());
        log.info("【文章详情】已查询出文章相关信息");
        log.info("<=====ArticleServiceImpl<-----detail【E N D】");
        return ResultVOUtil.success(detail);
    }

    @Override
    public ResultVO<?> enable(Long articleId) {
        log.info("=====>ArticleServiceImpl----->enable【START】");
        log.info("【文章启用】REQUEST: 文章主键ID: {}",articleId);

        if(articleId == null){
            log.info("【文章启用】article表主键ID为空");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_ID_IS_NULL);
        }

        ArticleEntity articleEntity = this.getUnDeleted(articleId);
        if(articleEntity == null){
            log.info("【文章启用】该文章信息不存在或已被删除");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_IS_NULL);
        }

        articleEntity.setInUse(1);
        this.save(articleEntity);
        log.info("【文章启用】文章已启用,articleId:{}",articleId);
        log.info("<=====ArticleServiceImpl<-----enable【E N D】");
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO<?> disable(Long articleId) {
        log.info("=====>ArticleServiceImpl----->disable【START】");
        log.info("【文章停用】REQUEST: 文章主键ID: {}",articleId);

        if(articleId == null){
            log.info("【文章停用】article表主键ID为空");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_ID_IS_NULL);
        }

        ArticleEntity articleEntity = this.getUnDeleted(articleId);
        if(articleEntity == null){
            log.info("【文章停用】该文章信息不存在或已被删除");
            return ResultVOUtil.error(ErrorEnum.ARTICLE_IS_NULL);
        }

        articleEntity.setInUse(0);
        this.save(articleEntity);
        log.info("【文章停用】文章已停用,articleId:{}",articleId);
        log.info("<=====ArticleServiceImpl<-----disable【E N D】");
        return ResultVOUtil.success();
    }
}
