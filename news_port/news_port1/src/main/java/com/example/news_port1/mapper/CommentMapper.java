package com.example.news_port1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.news_port1.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper {
    int deleteByPrimaryKey(Integer commentId);
    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByPrimaryKey(Integer commentId);
    Comment selectByAll(String userId,Integer passageId,String review);
    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);
}
