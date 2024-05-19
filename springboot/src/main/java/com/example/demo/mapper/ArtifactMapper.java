package com.example.demo.mapper;

import com.example.demo.entity.Artifact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.*;

@Repository
@Mapper
public interface ArtifactMapper {

    @Select("Select id,relicTime,library_Chinese,imageUrl,material_Chinese, artifactName_Chinese, size_Chinese, description_Chinese from artifact where artifactName like CONCAT('%', #{artifactname}, '%') or artifactName_Chinese like CONCAT('%', #{artifactname}, '%')")
    public List<Artifact> findByArtifactName(String artifactname);

    public List<Artifact> DeepSearch(String s);

    @Select("Select id,relicTime,library_Chinese,imageUrl,material_Chinese, artifactName_Chinese, size_Chinese, description_Chinese from artifact where library like CONCAT('%',#{libraryName},'%') or library_Chinese like CONCAT('%',#{libraryName},'%') ")
    public List<Artifact> findByLibraryName(String libraryName);

    @Select("Select id,relicTime,library_Chinese,imageUrl,material_Chinese, artifactName_Chinese, size_Chinese, description_Chinese from artifact where relicTime like CONCAT('%',#{relicTime},'%')")
    public List<Artifact> findByRelicTime(String relicTime);

    @Select("select id,relicTime,library_Chinese,imageUrl, material_Chinese,artifactName_Chinese, size_Chinese, description_Chinese from artifact where id=#{artifactId}")
    public Artifact findById(Integer artifactId);

    @Select("SELECT id,relicTime,library_Chinese,imageUrl, material_Chinese,artifactName_Chinese, size_Chinese, description_Chinese FROM artifact ")
    public List<Artifact> findAllRandom();

    @Select("select id,relicTime,library_Chinese,imageUrl, material_Chinese,artifactName_Chinese, size_Chinese, description_Chinese from artifact where " +
            "material_Chinese like CONCAT('%',#{materialChinese},'%') " +
            "or library_Chinese=#{libraryChinese} " +
            "or relicTime_chinese=#{libraryChinese} " +
            "and id !=#{id} order by rand() LIMIT 8 ")
    public List<Artifact> findVague(Artifact artifact);
}

