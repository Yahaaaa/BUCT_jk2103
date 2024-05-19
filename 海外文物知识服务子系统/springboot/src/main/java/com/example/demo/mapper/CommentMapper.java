package com.example.demo.mapper;

import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CommentMapper {
    @Select("select * from comment where user_id=#{user_id}")
    public List<Comment> findCommentsByUser_id(@Param("user_id") Integer user_id);

    @Select("select * from comment where artifact_id=#{artifact_id}")
    public List<Comment> findCommentsByArtifact_id(Integer artifact_id);

    @Insert("insert into comment(user_id, artifact_id, content, create_time) value " +
            "(#{userId},#{artifactId},#{content},#{createTime} )")
    public void insertComment(Comment comment);
    @Update("update comment set user_name=#{username},avatar_url=#{avatarUrl} where user_id=#{id}")
    public void updateImageUserName(User user);
}
