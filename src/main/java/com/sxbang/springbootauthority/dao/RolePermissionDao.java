package com.sxbang.springbootauthority.dao;

import com.sxbang.springbootauthority.model.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RolePermissionDao {

    @Delete("delete from sys_role_permission where permissionId = #{permissionId}")
    int delete(RolePermission rolePermission);

    int save(@Param("roleId") Integer id,@Param("permissionIds") List<Long> permissionIds);

    @Delete("delete from sys_role_permission where roleId = #{roleId}")
    int deleteRolePermission(Integer roleId);

    @Select("select * from sys_role_permission where roleId = #{roleId}")
    List<RolePermission> listAllRolePermissionByRoleId(Integer roleId);

    @Delete("delete from sys_role_permission where permissionId = #{permissionId}")
    int deleteByPermissionId(Integer permissionId);
}
