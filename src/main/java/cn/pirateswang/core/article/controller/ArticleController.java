package cn.pirateswang.core.article.controller;

import cn.pirateswang.common.publicVO.PageDTO;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.core.article.dto.ArticlePageRequestDTO;
import cn.pirateswang.core.article.dto.ArticlePageResponseDTO;
import cn.pirateswang.core.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    @PostMapping("/page")
    public ResultVO<PageDTO<ArticlePageResponseDTO>> page(@RequestBody ArticlePageRequestDTO requestDTO){
        return articleService.page(requestDTO);
    }
    
    
}
