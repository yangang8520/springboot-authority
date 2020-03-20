package com.sxbang.springbootauthority.controller;

import com.sxbang.springbootauthority.base.result.PageTableRequest;
import com.sxbang.springbootauthority.base.result.ResponseCode;
import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.dto.UserDto;
import com.sxbang.springbootauthority.model.SysUser;
import com.sxbang.springbootauthority.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    @ResponseBody
    public SysUser user(@PathVariable String username){
        log.info("UserController.user(): param ( username = " + username + " )");
        return userService.getUser(username);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @ResponseBody
    @ApiOperation(value = "分页获取用户信息", notes = "分页获取用户信息")//描述
    @ApiImplicitParam(name = "request", value = "分页查询实体类", required = false)
    public Results<SysUser> getUsers(PageTableRequest request){
        log.info("UserController.getUsers(): Param ( request1 " + request + ")");
        request.countOffset();
        return userService.getAllUsersByPage(request.getOffset(), request.getLimit());
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public String addUser(Model model) {
        model.addAllAttributes(Collections.singleton(new SysUser()));
        return "user/user-add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Results<SysUser> saveUser(UserDto userDto, Integer roleId) {

        SysUser sysUser = null;

        sysUser = userService.getUserByPhone(userDto.getTelephone());
        if(sysUser != null && sysUser.equals(userDto.getId())) {
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(), ResponseCode.PHONE_REPEAT.getMessage());
        }

        userDto.setStatus(1);
        //userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        return userService.save(userDto,roleId);
    }

    String pattern = "yyyy-MM-dd";
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public String editUser(Model model, SysUser sysUser) {
        model.addAllAttributes(Collections.singleton(userService.getUserById(sysUser.getId())));
        return "user/user-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public Results<SysUser> updateUser(UserDto userDto, Integer roleId) {

        SysUser sysUser = null;

        sysUser = userService.getUserByPhone(userDto.getTelephone());
        if(sysUser != null && sysUser.equals(userDto.getId())) {
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(), ResponseCode.PHONE_REPEAT.getMessage());
        }

        return userService.update(userDto,roleId);
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:del')")
    @ResponseBody
    public Results deleteUser(UserDto userDto) {
        int count = userService.deleteUser(userDto.getId());
        if (count > 0 ){
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    @GetMapping("/findUserByFuzzyUsername")
    @ResponseBody
    public Results<SysUser> findUserByFuzzyUsername(PageTableRequest request, String username){
        log.info("UserController.findUserByFuzzyUsername(): Param ( request1 =" + request + ", username ="+ username +")");
        request.countOffset();
        return userService.getUserByFuzzyUsername(username, request.getOffset(), request.getLimit());
    }

    @GetMapping("/deleteAll")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    public Results deleteAllUser(String json) {
        return userService.deleteAllUser(json);
    }

    @PostMapping("/changePassword")
    @ApiOperation(value = "修改密码")
    @ResponseBody
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        return userService.changePassword(username, oldPassword, newPassword);
    }
}
