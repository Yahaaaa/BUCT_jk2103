package com.example.demo.controller;

import com.example.demo.Exception.IllegalCommentException;
import com.example.demo.Exception.NullContentException;
import com.example.demo.Exception.RemarkErrorException;
import com.example.demo.common.Result;
import com.example.demo.entity.Artifact;
import com.example.demo.entity.Comment;
import com.example.demo.entity.PerfectArtifact;
import com.example.demo.mapper.ArtifactMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.demo.common.ResultCode.*;

@RestController
public class CommentController {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ArtifactMapper artifactMapper;
    //    @CrossOrigin(origins = "http://localhost:6103")
    @PostMapping("/artifact/{id}")
    public Result<?> remark(@RequestBody Comment comment){
        try{
            System.out.println(comment);
            //检查用户是否被禁止评论,1为被禁止评论
            if(userMapper.findById(comment.getUserId()).isIsBanned()){
                throw new RemarkErrorException();
            }
            else if(comment.getContent().isEmpty()){
                throw new NullContentException();
            }
            else{
                String forbiddens[]={"傻逼","脑残","蠢货","nmsl","你妈死了","我测你的吗"};
                boolean isContains=false;
                for(String words:forbiddens){
                    if(comment.getContent().contains(words)){
                        isContains=true;
                        break;
                    }
                }
                if(isContains){
                    userMapper.updateIsBannedByUserId(comment.getUserId());
                    throw new IllegalCommentException();
                }else{
                    LocalDateTime localDateTime = LocalDateTime.now();
                    String date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    comment.setCreateTime(date);
                    commentMapper.insertComment(comment);
                    commentMapper.updateImageUserName(userMapper.findById(comment.getUserId()));
                    Artifact artifact=artifactMapper.findById(comment.getArtifactId());
                    artifact.comments=commentMapper.findCommentsByArtifact_id(comment.getArtifactId());
                    for(Comment comment1:artifact.comments){
                        comment1.setAvatarUrl(userMapper.findById(comment1.getUserId()).getAvatarUrl());
                        comment1.setUserName(userMapper.findById(comment1.getUserId()).getUsername());
                    }
                    PerfectArtifact perfectArtifact=new PerfectArtifact();
                    perfectArtifact.setArtifact(artifact);
                    List<Artifact> relatedlist=artifactMapper.findVague(artifact);
                    perfectArtifact.setRelatedArtifact(relatedlist);
                    return Result.success(perfectArtifact);
                }
            }
        }
        catch (Exception e){
            if(e instanceof RemarkErrorException)return Result.error(REMARK_ERROR.code,REMARK_ERROR.msg);
            else if(e instanceof NullContentException) return Result.error(REMARK_NULL_ERROR.code, REMARK_NULL_ERROR.msg);
            else if(e instanceof IllegalCommentException) return Result.error(REMARK_ILLEGAL_ERROR.code, REMARK_ILLEGAL_ERROR.msg);
            else return Result.error(ERROR.code, ERROR.msg);
        }
    }
}
