package com.example.news_port1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.news_port1.model.Follow;
import com.example.news_port1.model.Good;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodMapper extends BaseMapper {
    int deleteByPrimaryKey(Good key);
    List<Good> selectAllGood(String userId);
    int insert(Good record);

    int insertSelective(Good record);
}
