package com.tensquare.search.controller;


import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Repeatable;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article){
        articleService.add(article);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    @RequestMapping(value = "/{key}/{page}/{size}",method = RequestMethod.POST)
    public Result findByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size){
        try {
            key=new String(key.getBytes("iso8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Page<Article> pageData=articleService.findByKey(key,page,size);
        return new Result(true,StatusCode.OK,"添加成功",new PageResult<Article>(pageData.getTotalElements(),pageData.getContent()));
    }
}
