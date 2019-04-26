package com.tensquare.base.dao.dao;

import com.tensquare.base.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LabelDao extends JpaRepository<Label,String> ,JpaSpecificationExecutor<Label> {

}
