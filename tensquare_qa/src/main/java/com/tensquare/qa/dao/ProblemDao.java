package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
	@Query(value = "SELECT * FROM tb_problem t1,tb_pl t2 WHERE t1.id=t2.problemid AND t2.labelid=? ORDER BY t1.replytime DESC", nativeQuery = true)
    Page<Problem> newlist(String labelid, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem t1,tb_pl t2 WHERE t1.id=t2.problemid AND t2.labelid=? ORDER BY t1.reply DESC", nativeQuery = true)
    Page<Problem> hotlist(String labelid, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem t1,tb_pl t2 WHERE t1.id=t2.problemid AND t2.labelid=? AND t1.reply=0 ORDER BY t1.updatetime DESC", nativeQuery = true)
    Page<Problem> waitlist(String labelid, Pageable pageable);
}
