package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;
@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Spit> findAll(){
        return spitDao.findAll();
    }
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }
    /*
    吐槽使给所有的父节点加一
     */
    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());//时间
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        findParent(spit);
        spitDao.save(spit);
        return;
    }
    void findParent(Spit spit){
        if(spit.getParentid()==null)
            return ;
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
        Update update=new Update();
        update.inc("comment",1);
        mongoTemplate.updateFirst(query,update,"spit");
        findParent(findById(spit.getParentid()));
    }
    /*
    更新操作
     */
    public void update(Spit spit){
        spitDao.save(spit);
        return ;
    }
    public void delete(String id){
        spitDao.deleteById(id);
        return ;
    }
    public Page<Spit> findByParentId(String parentId ,Integer page,Integer size){
        Pageable pageable=PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentId,pageable);
    }

    public void thumbup(String spitId){
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
