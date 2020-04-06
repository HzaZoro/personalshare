package cn.pirateswang.core.article.controller;

import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.core.article.dto.*;
import cn.pirateswang.core.article.service.ArticleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Api(tags = {"文章信息接口"})
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "文章列表【分页查询】")
    @PostMapping("/page")
    public ResultVO<PageInfo<ArticlePageResponseDTO>> page(@RequestBody ArticlePageRequestDTO requestDTO){
        return articleService.page(requestDTO);
    }

    @ApiOperation(value="文章数据【创建】")
    @PostMapping("/create")
    public ResultVO<?> create(@RequestBody ArticleCreateDTO requestDTO){
        return articleService.create(requestDTO);
    }

    @ApiOperation(value="文章数据【更新】")
    @PostMapping("/update")
    public ResultVO<?> update(@RequestBody ArticleUpdateDTO requestDTO){
        return articleService.update(requestDTO);
    }

    @ApiOperation(value="文章数据【详情】")
    @GetMapping("/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name="articleId",value = "文章主键ID",required = true,paramType = "query",dataType = "Long")
    })
    public ResultVO<ArticleDetailDTO> detail(@RequestParam("articleId") Long articleId){
        return articleService.detail(articleId);
    }

    @ApiOperation(value="文章数据【启用】")
    @GetMapping("/enable")
    @ApiImplicitParams({
            @ApiImplicitParam(name="articleId",value = "文章主键ID",required = true,paramType = "query",dataType = "Long")
    })
    public ResultVO<?> enable(@RequestParam("articleId") Long articleId){
        return articleService.enable(articleId);
    }

    @ApiOperation(value="文章数据【停用】")
    @GetMapping("/disable")
    @ApiImplicitParams({
            @ApiImplicitParam(name="articleId",value = "文章主键ID",required = true,paramType = "query",dataType = "Long")
    })
    public ResultVO<?> disable(@RequestParam("articleId") Long articleId){
        return articleService.disable(articleId);
    }

    @ApiOperation(value="文章数据【删除】")
    @GetMapping("/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name="articleId",value = "文章主键ID",required = true,paramType = "query",dataType = "Long")
    })
    public ResultVO<?> delete(@RequestParam("articleId") Long articleId){
        return articleService.delete(articleId);
    }

}
