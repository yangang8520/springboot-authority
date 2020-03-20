package com.sxbang.springbootauthority.service.impl;

import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.dao.RoleUserDao;
import com.sxbang.springbootauthority.dao.UserDao;
import com.sxbang.springbootauthority.dto.UserDto;
import com.sxbang.springbootauthority.model.SysRoleUser;
import com.sxbang.springbootauthority.model.SysUser;
import com.sxbang.springbootauthority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleUserDao roleUserDao;

    @Override
    public SysUser getUser(String username){
        return userDao.getUser(username);
    }

    @Override
    public Results<SysUser> getAllUsersByPage(Integer offset, Integer limit) {
        //count user-list
        return Results.success(userDao.countAllUsers().intValue(), userDao.getAllUsersByPage(offset,limit));
    }

    @Override
    public Results<SysUser> save(SysUser user, Integer roleId) {

        if(roleId != null){
            //user
            userDao.save(user);
            //roleUser
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(roleId);
            sysRoleUser.setUserId(user.getId().intValue());
            roleUserDao.save(sysRoleUser);

            return Results.success();
        }
        return Results.failure();
    }

    @Override
    public SysUser getUserByPhone(String telephone) {

        return userDao.getUserByPhone(telephone);
    }

    @Override
    public SysUser getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public Results<SysUser> update(UserDto userDto, Integer roleId) {
        if ((roleId) != null) {
            //sys_user
            userDao.updateUser(userDto);
            //sys_role_user
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(userDto.getId().intValue());
            sysRoleUser.setRoleId(roleId);
            if (roleUserDao.getSysRoleUserByUserId(userDto.getId().intValue()) != null) {
                roleUserDao.updateSysRoleUser(sysRoleUser);
            }else {
                roleUserDao.save(sysRoleUser);
            }
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    @Override
    public int deleteUser(Long id) {


        roleUserDao.deleteRoleUserByUserId(id.intValue());

        return userDao.deleteUser(id.intValue());
    }

    @Override
    public Results<SysUser> getUserByFuzzyUsername(String username, Integer offset, Integer limit) {
        //count user-list
        return Results.success(userDao.getUserByFuzzyUsername(username).intValue(), userDao.getUserByFuzzyUsernameByPage(username,offset,limit));
    }

    @Override
    public Results deleteAllUser(String json) {
        String[] split = json.split(",");
        for (int i = 0; i < split.length; i++) {
            roleUserDao.deleteRoleUserByUserId(Integer.parseInt(split[i]));
            userDao.deleteUser(Integer.parseInt(split[i]));
        }
        return Results.success();
    }

    @Override
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        SysUser u = userDao.getUser(username);
        if (u == null) {
            return Results.failure(1,"用户不存在");
        }
        if (!new BCryptPasswordEncoder().encode(oldPassword).equals(u.getPassword())) {
            return Results.failure(1,"旧密码错误");
        }
        userDao.changePassword(u.getId(), new BCryptPasswordEncoder().encode(newPassword));
        return Results.success();
    }
}
