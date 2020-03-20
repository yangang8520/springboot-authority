package com.sxbang.springbootauthority.controller;

import com.alibaba.fastjson.JSONArray;
import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.dto.RoleDto;
import com.sxbang.springbootauthority.model.SysPermission;
import com.sxbang.springbootauthority.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("permission")
@Slf4j
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/listAllPermission", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:permission:query')")
    public Results<JSONArray> listAllPermission() {
        return permissionService.listAllPermission();
    }

    @RequestMapping(value = "/listAllPermissionByRoleId", method = RequestMethod.GET)
    @ResponseBody
    public Results<SysPermission> listAllPermissionByRoleId(RoleDto roleDto) {
        log.info(getClass().getName() + " : param = " + roleDto);
        return permissionService.listByRoleId(roleDto.getId().intValue());
    }

    @GetMapping("/menuAll")
    @ResponseBody
    public Results getMenuAll(){
        return permissionService.getMenuAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('sys:permission:add')")
    public String addPermission(Model model) {
        model.addAttribute("sysPermission", new SysPermission());
        return "permission/permission-add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Results<SysPermission> savePermission(@RequestBody SysPermission permission) {
        return permissionService.save(permission);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('sys:permission:edit')")
    public String editPermission(Model model, SysPermission permission) {
        model.addAttribute("sysPermission", permissionService.getSysPermissionById(permission.getId()));
        return "permission/permission-add";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Results updatePermission(@RequestBody SysPermission permission) {
        return permissionService.updateSysPermission(permission);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:permission:del')")
    public Results deletePermission(SysPermission sysPermission) {
        return permissionService.delete(sysPermission.getId());
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public Results<SysPermission> getMenu(Long userId) {
        Results<SysPermission> menu = permissionService.getMenu(userId);
        return menu;
    }
}
