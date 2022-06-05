package com.example.news_port1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.news_port1.model.Passage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PassageMapper extends BaseMapper {
    int deleteByPrimaryKey(Integer passageId);

    int insert(Passage record);

    int insertSelective(Passage record);
    List<Passage> selectAll();
   List<Passage> selectPassagesByUserId(String userId);
    Passage selectByPrimaryKey(Integer passageId);

    int updateByPrimaryKeySelective(Passage record);

    int updateByPrimaryKeyWithBLOBs(Passage record);

    int updateByPrimaryKey(Passage record);

    List<Passage> searchPassage(String searchKey);

    List<Passage> hotSearch();

    List<Passage> selectLatestPassage();
}
