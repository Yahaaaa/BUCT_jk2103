package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Artifact;
import com.example.demo.entity.Comment;
import com.example.demo.entity.PerfectArtifact;
import com.example.demo.mapper.ArtifactMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class ArtifactController {

    @Autowired
    ArtifactMapper artifactMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;

    @CrossOrigin(origins = "http://localhost:6103")
    //文物部分共有六个url,严格按照下面的url格式访问  order为0 年份从前到后排序  order为1 年份从后到前
    @GetMapping("/searchAll")

    //http://localhost:8080/searchAll?order=xxx

    public Result<?>searchAll(String order){
        List<Artifact> list=artifactMapper.findAllRandom();
        if(Integer.parseInt(order)==1){
            Collections.reverse(list);
            System.out.println(list);
            return Result.success(list,list.size());   //返回所有文物 已排序
        }
        else return Result.success(list,list.size());
    }

    @CrossOrigin(origins = "http://localhost:6103")
    @GetMapping("/artifact")//文物名称

    //http://localhost:8080/artifact?artifactName=xxx&order=xxx;

    public Result<?> search_by_name(String artifactName,String order) {

        List<Artifact> list=artifactMapper.findByArtifactName(artifactName);
        if(Integer.parseInt(order)==0){
            return Result.success(list,list.size());
        }
        else {
            Collections.reverse(list);
            return Result.success(list,list.size());
        }
        //调用ArtifactMapper中的select by artifactName 将list返回至前端。
    }

    @CrossOrigin(origins = "http://localhost:6103")
    @GetMapping("/search_museum")//博物馆名称

    //http://localhost:8080/search_museum?museumName=xxx&order=xxx;

    public Result<?> search_by_museum(String museumName,String order){

        List<Artifact> list= artifactMapper.findByLibraryName(museumName);
        if(Integer.parseInt(order)==0){
            return Result.success(list,list.size());
        }
        else {
            Collections.reverse(list);
            return Result.success(list,list.size());
        }
    }

    @CrossOrigin(origins = "http://localhost:6103")
    @GetMapping("/search_relicTime")//文物年代

    //http://localhost:8080/search_relicTime?relicTime=xxx&order=xxx;

    public Result<?> search_by_time(String relicTime,String order){
        List<Artifact> list= artifactMapper.findByRelicTime(relicTime);
        if(Integer.parseInt(order)==0){
            return Result.success(list,list.size());
        }
        else {
            Collections.reverse(list);
            return Result.success(list,list.size());
        }
    }

    @CrossOrigin(origins = "http://localhost:6103")
    @PostMapping("/advanced_search")
    public Result<?> DeepSearch(@RequestBody Map<String, Object> requestMap){
        String condition = (String) requestMap.get("condition");
        Integer order = (Integer) requestMap.get("order");
        List<Artifact> list=artifactMapper.DeepSearch(condition);
        if(order==0){
            return Result.success(list,list.size());
        }
        else {
            Collections.reverse(list);
            return Result.success(list,list.size());
        }
    }

    @CrossOrigin(origins = "http://localhost:6103")
    @GetMapping("/artifact/{id}")//文物详情页面

    //http://localhost:8080/artifact/{id}

    public Result<?> artifact_details(@PathVariable String id){

        Artifact artifact=artifactMapper.findById(Integer.parseInt(id));
        artifact.comments=commentMapper.findCommentsByArtifact_id(Integer.parseInt(id));
        for(Comment comment:artifact.comments){
            comment.setAvatarUrl(userMapper.findById(comment.getUserId()).getAvatarUrl());
            comment.setUserName(userMapper.findById(comment.getUserId()).getUsername());
        }
        PerfectArtifact perfectArtifact=new PerfectArtifact();
        perfectArtifact.setArtifact(artifact);
        List<Artifact> relatedlist=artifactMapper.findVague(artifact);
        perfectArtifact.setRelatedArtifact(relatedlist);
        return Result.success(perfectArtifact);
    }

}
