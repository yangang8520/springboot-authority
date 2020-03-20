package com.sxbang.springbootauthority.service;

import com.alibaba.fastjson.JSONArray;
import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.model.SysPermission;

public interface PermissionService {

    Results<JSONArray> listAllPermission();

    Results<SysPermission> listByRoleId(Integer intValue);

    Results<SysPermission> getMenuAll();

    Results<SysPermission> save(SysPermission sysPermission);

    SysPermission getSysPermissionById(Integer id);

    Results updateSysPermission(SysPermission sysPermission);

    Results delete(Integer id);

    Results<SysPermission> getMenu(Long userId);
}
