package com.sxbang.springbootauthority.service.impl;

import com.sxbang.springbootauthority.base.result.ResponseCode;
import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.dao.RoleDao;
import com.sxbang.springbootauthority.dao.RolePermissionDao;
import com.sxbang.springbootauthority.dao.RoleUserDao;
import com.sxbang.springbootauthority.dto.RoleDto;
import com.sxbang.springbootauthority.model.RolePermission;
import com.sxbang.springbootauthority.model.SysRole;
import com.sxbang.springbootauthority.model.SysRoleUser;
import com.sxbang.springbootauthority.model.SysUser;
import com.sxbang.springbootauthority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private RoleUserDao roleUserDao;

    @Override
    public Results<SysRole> getAllRoles() {
        return Results.success(50, roleDao.getAllRoles());
    }

    @Override
    public Results<SysRole> getAllRolesByPage(Integer offset, Integer limit) {
        //count user-list
        return Results.success(roleDao.countAllRoles().intValue(), roleDao.getAllRolesByPage(offset,limit));
    }

    @Override
    public SysRole getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Override
    public Results<SysRole> save(RoleDto roleDto) {
        //1、先保存角色
        roleDao.saveRole(roleDto);
        List<Long> permissionIds = roleDto.getPermissionIds();
        //移除0，permission id是从1开始
        permissionIds.remove(0L);
        //2、保存角色对应的权限
        if (!CollectionUtils.isEmpty(permissionIds)) {
            rolePermissionDao.save(roleDto.getId(), permissionIds);
        }
        return Results.success();
    }

    @Override
    public SysRole getRoleById(int id) {
        return roleDao.getRoleById(id);
    }


    @Override
    public int update(RoleDto roleDto) {
        List<Long> permissionIds = roleDto.getPermissionIds();
        permissionIds.remove(0L);
        //1、更新角色权限之前要删除该角色的所有权限
        rolePermissionDao.deleteRolePermission(roleDto.getId());

        //2、判断该角色是否有赋予权限值，有就添加
        if (!CollectionUtils.isEmpty(permissionIds)) {
            rolePermissionDao.save(roleDto.getId(), permissionIds);
        }
        return roleDao.update(roleDto);
    }

    @Override
    public Results delete(Integer id) {
        List<SysRoleUser> datas = roleUserDao.listAllSysRoleUserByRoleId(id);
        if (datas.size() > 0){
            roleUserDao.deleteRoleUserByRoleId(id);
        }
        List<RolePermission> data =rolePermissionDao.listAllRolePermissionByRoleId(id);
        if (data.size() > 0){
            rolePermissionDao.deleteRolePermission(id);
        }
        roleDao.delete(id);
        return Results.failure(ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getCode(), ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getMessage());
    }

    @Override
    public Results<SysRole> getRoleByFuzzyName(String name, Integer offset, Integer limit) {

        return Results.success(roleDao.getRoleByFuzzyName(name).intValue(), roleDao.getRoleByFuzzyNameByPage(name,offset,limit));
    }

    @Override
    public Results deleteAllRole(String json) {
        String[] split = json.split(",");
        for (int i = 0; i < split.length; i++) {
            List<SysRoleUser> datas = roleUserDao.listAllSysRoleUserByRoleId(Integer.parseInt(split[i]));
            if (datas.size() > 0){
                roleUserDao.deleteRoleUserByRoleId(Integer.parseInt(split[i]));
            }
            List<RolePermission> data =rolePermissionDao.listAllRolePermissionByRoleId(Integer.parseInt(split[i]));
            if (data.size() > 0){
                rolePermissionDao.deleteRolePermission(Integer.parseInt(split[i]));
            }
            roleDao.delete(Integer.parseInt(split[i]));
        }
        return Results.success();
    }
}
