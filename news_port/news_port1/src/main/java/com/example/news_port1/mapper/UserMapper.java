package com.example.news_port1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.news_port1.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);
     List<User> selectAll();
    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    List<User> checkUser();

    boolean admitUser(String userId);
}
