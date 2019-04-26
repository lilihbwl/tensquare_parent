package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findAll());
    }
    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(spitId));
    }
    @RequestMapping(method = RequestMethod.POST)
    public Result addSpit(@RequestBody Spit spit){
        System.out.println(spit.getNickname());
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable String spitId,@RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String spitId){
        spitService.delete(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentId(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> page1 = spitService.findByParentId(parentid, page, size);
        return new Result(true,StatusCode.OK,"删除成功",new PageResult<Spit>(page1.getTotalElements(),page1.getContent()));
    }
    @RequestMapping(value="/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){
        spitService.thumbup(spitId);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
