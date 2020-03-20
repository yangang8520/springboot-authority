package com.sxbang.springbootauthority.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.dao.PermissionDao;
import com.sxbang.springbootauthority.dao.RolePermissionDao;
import com.sxbang.springbootauthority.model.SysPermission;
import com.sxbang.springbootauthority.service.PermissionService;
import com.sxbang.springbootauthority.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService  {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public Results<JSONArray> listAllPermission() {
        log.info(getClass().getName() + "listAllPermission()");
        List datas = permissionDao.findAll();
        JSONArray array = new JSONArray();
        log.info(getClass().getName() + ".setPermissionsTree(?,?,?)");
        TreeUtils.setPermissionsTree(0, datas, array);
        return Results.success(array);
    }


    @Override
    public Results<SysPermission> listByRoleId(Integer roleId) {
        List<SysPermission> datas = permissionDao.listByRoleId(roleId);
        return Results.success(0, datas);
    }

    @Override
    public Results<SysPermission> getMenuAll() {
        return Results.success(0, permissionDao.findAll());
    }

    @Override
    public Results<SysPermission> save(SysPermission sysPermission) {
        return (permissionDao.save(sysPermission) > 0) ? Results.success() : Results.failure();
    }

    @Override
    public SysPermission getSysPermissionById(Integer id) {
        return permissionDao.getSysPermissionById(id);
    }

    @Override
    public Results updateSysPermission(SysPermission sysPermission) {
        return (permissionDao.update(sysPermission) > 0) ? Results.success() : Results.failure();
    }

    @Override
    public Results delete(Integer id) {
        rolePermissionDao.deleteByPermissionId(id);
        List<SysPermission> list = permissionDao.selectByParentId(id);
        if (list.size() > 0) {
            for (int i=0 ; i < list.size(); i++) {
                rolePermissionDao.deleteByPermissionId(list.get(i).getId());
            }
        }
        permissionDao.deleteById(id);
        permissionDao.deleteByParentId(id);
        return Results.success();
    }

    @Override
    public Results getMenu(Long userId) {
        List<SysPermission> datas = permissionDao.listByUserId(userId);
        datas = datas.stream().filter(p -> p.getType().equals("1")).collect(Collectors.toList());
        JSONArray array = new JSONArray();
        TreeUtils.setPermissionsTree(0, datas, array);
        return Results.success(array);
    }
}
