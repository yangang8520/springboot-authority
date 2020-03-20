package com.sxbang.springbootauthority.controller;


import com.sxbang.springbootauthority.base.result.PageTableRequest;
import com.sxbang.springbootauthority.base.result.ResponseCode;
import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.dto.RoleDto;
import com.sxbang.springbootauthority.dto.UserDto;
import com.sxbang.springbootauthority.model.SysRole;
import com.sxbang.springbootauthority.model.SysUser;
import com.sxbang.springbootauthority.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    @ResponseBody
    public Results<SysRole> getAll() {
        log.info("RoleController.getAll()");
        return roleService.getAllRoles();
    }

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:query')")
    public Results<SysRole> getRole(PageTableRequest request){
        log.info("UserController.getUsers(): Param ( request1 " + request + ")");
        request.countOffset();
        return roleService.getAllRolesByPage(request.getOffset(), request.getLimit());
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:role:add')")
    public String addRole(Model model) {
        model.addAllAttributes(Collections.singleton(new SysRole()));
        return "role/role-add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Results<SysRole> saveRole(@RequestBody RoleDto roleDto) {

        return roleService.save(roleDto);
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    public String editRole(Model model, SysRole sysRole) {
        model.addAllAttributes(Collections.singleton(roleService.getRoleById(sysRole.getId())));
        return "role/role-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public Results<SysRole> updateRole(@RequestBody RoleDto roleDto) {

        roleService.update(roleDto);
        return Results.success();
    }

    @GetMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:del')")
    public Results<SysRole> deleteRole(RoleDto roleDto) {
        return roleService.delete(roleDto.getId());
    }

    @GetMapping("/findRoleByFuzzyName")
    @ResponseBody
    public Results<SysRole> getRoleByFuzzyName(PageTableRequest request, String name){
        log.info("RoleController.findRoleByFuzzyName(): Param ( request1 =" + request + ", name ="+ name +")");
        request.countOffset();
        return roleService.getRoleByFuzzyName(name, request.getOffset(), request.getLimit());
    }

    @GetMapping("/deleteAll")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:del')")
    public Results deleteAllRole(String json) {
        return roleService.deleteAllRole(json);
    }
}
