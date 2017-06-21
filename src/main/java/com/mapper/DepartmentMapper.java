package com.mapper;

import com.domain.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zengjie on 17/6/21.
 */
@Mapper
public interface DepartmentMapper extends  SqlMapper {

    /**
     * Created by zengjie on 17/6/21.
     */


    @Select("SELECT * FROM department WHERE name = #{name}")
    Department findByName(@Param("name") String name);

}
