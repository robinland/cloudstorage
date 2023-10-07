package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import java.util.List;


@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Select("SELECT filename FROM FILES WHERE userid = #{userId}")
    String[] getFileListings(Integer userId);

    @Select("SELECT * from FILES where userId = #{userId}")
    List<File> getAllFile(int userId);

    @Select("SELECT * from FILES where fileId = #{fileId}")
    File getFileById(int fileId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(int fileId);

    @Update("UPDATE FILES SET fileName = #{fileName}, contentType = #{contentType}, fileSize = #{fileSize}, fileData = #{fileData}")
    void updateFile(File file);
}
