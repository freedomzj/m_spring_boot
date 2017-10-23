package com.mapper;

import com.domain.PersistentLogins;
import com.domain.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zengjie on 17/6/21.
 */
@Mapper
public interface UserMapper extends  SqlMapper {

    @Select("SELECT * FROM USER WHERE username = #{username}")
    User findByName(@Param("username") String username);

    @Insert("INSERT INTO USER(username, password) VALUES(#{username}, #{password})")
    int insert(@Param("username") String name, @Param("password") String password);

    User findDepartment(@Param("id") Long id);

    void saveToken(PersistentLogins persistentLogins);
}
