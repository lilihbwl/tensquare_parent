package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;



/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    @Query(value = "SELECT a.* FROM tb_problem a, tb_pl b WHERE a.id = b.problemid AND b.labelid=? ORDER BY a.replytime DESC",nativeQuery = true)
    public Page<Problem> findNewProblem(String labelId, Pageable pageable);
    @Query(value = "SELECT a.* FROM tb_problem a, tb_pl b WHERE a.id = b.problemid AND b.labelid=? ORDER BY a.reply DESC",nativeQuery = true)
    public Page<Problem> findHotProblem(String labelId, Pageable pageable);
    @Query(value = "SELECT a.* FROM tb_problem a, tb_pl b WHERE a.id = b.problemid AND b.labelid=? AND a.reply= 0 ORDER BY a.reply DESC",nativeQuery = true)
    public Page<Problem> findWaitProblem(String labelId, Pageable pageable);
}
