package com.sxbang.springbootauthority.service;

import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.dto.RoleDto;
import com.sxbang.springbootauthority.model.SysRole;
import com.sxbang.springbootauthority.model.SysUser;

public interface RoleService {
    Results<SysRole> getAllRoles();

    Results<SysRole> getAllRolesByPage(Integer offset, Integer limit);

    SysRole getRoleByName(String name);

    Results<SysRole> save(RoleDto roleDto);

    SysRole getRoleById(int id);

    int update(RoleDto roleDto);

    Results delete(Integer id);

    Results<SysRole> getRoleByFuzzyName(String name, Integer offset, Integer limit);

    Results deleteAllRole(String json);
}
