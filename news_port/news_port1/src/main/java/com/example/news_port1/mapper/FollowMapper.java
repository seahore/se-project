package com.example.news_port1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.news_port1.model.Follow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper extends BaseMapper {
    int deleteByPrimaryKey(Follow key);

    int insert(Follow record);

    int insertSelective(Follow record);
    int deleteFollowUser(String userId,String userIdFollowed);
    List<Follow> selectAllFollowUser(String userId);
}
