package com.sxbang.springbootauthority.service;

import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.dto.UserDto;
import com.sxbang.springbootauthority.model.SysUser;

public interface UserService {

    SysUser getUser(String username);

    Results<SysUser> getAllUsersByPage(Integer offset, Integer limit);

    Results<SysUser> save(SysUser user, Integer roleId);

    SysUser getUserByPhone(String telephone);

    SysUser getUserById(Long id);

    Results<SysUser> update(UserDto userDto, Integer roleId);

    int deleteUser(Long id);

    Results<SysUser> getUserByFuzzyUsername(String username, Integer offset, Integer limit);

    Results deleteAllUser(String json);

    Results changePassword(String username, String oldPassword, String newPassword);
}

