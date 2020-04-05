package cn.pirateswang.core.article.controller;

import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.core.article.dto.*;
import cn.pirateswang.core.article.service.ArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    @PostMapping("/page")
    public ResultVO<PageInfo<ArticlePageResponseDTO>> page(@RequestBody ArticlePageRequestDTO requestDTO){
        return articleService.page(requestDTO);
    }
    
    @PostMapping("/create")
    public ResultVO<?> create(@RequestBody ArticleCreateDTO requestDTO){
        return articleService.create(requestDTO);
    }

    @PostMapping("/update")
    public ResultVO<?> update(@RequestBody ArticleUpdateDTO requestDTO){
        return articleService.update(requestDTO);
    }

    @GetMapping("/detail")
    public ResultVO<ArticleDetailDTO> detail(@RequestParam("articleId") Long articleId){
        return articleService.detail(articleId);
    }
    
    @GetMapping("/enable")
    public ResultVO<?> enable(@RequestParam("articleId") Long articleId){
        return articleService.enable(articleId);
    }

    @GetMapping("/disable")
    public ResultVO<?> disable(@RequestParam("articleId") Long articleId){
        return articleService.disable(articleId);
    }
    
    @GetMapping("/delete")
    public ResultVO<?> delete(@RequestParam("articleId") Long articleId){
        return articleService.delete(articleId);
    }

}
