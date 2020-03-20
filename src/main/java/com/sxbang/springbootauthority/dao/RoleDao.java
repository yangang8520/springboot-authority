package com.sxbang.springbootauthority.dao;

import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.dto.RoleDto;
import com.sxbang.springbootauthority.model.SysRole;
import com.sxbang.springbootauthority.model.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleDao {

    @Select("select * from sys_role t")
    List<SysRole> getAllRoles();

    @Select("select count(*) from sys_role t ")
    Long countAllRoles();

    @Select("select * from sys_role t order by t.id limit #{startPosition} , #{limit}")
    List<SysRole> getAllRolesByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);

    @Select("select * from sys_role where name = #{name}")
    SysRole getRoleByName(String name);

    int saveRole(RoleDto roleDto);

    @Select("select * from sys_role where id = #{id}")
    SysRole getRoleById(int id);

    int update(RoleDto roleDto);

    @Delete("delete from sys_role where id = #{id}")
    int delete(int id);

    @Select("select count(*) from sys_role t where t.name like '%${name}%' ")
    Long getRoleByFuzzyName(@Param("name") String name);

    @Select("select * from sys_role t where t.name like '%${name}%' limit #{startPosition} , #{limit}")
    List<SysRole> getRoleByFuzzyNameByPage(@Param("name") String name,@Param("startPosition") Integer startPosition,@Param("limit") Integer limit);
}
