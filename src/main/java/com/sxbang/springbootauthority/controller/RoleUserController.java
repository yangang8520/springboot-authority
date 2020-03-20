package com.sxbang.springbootauthority.controller;

import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.service.RoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("roleuser")
@Slf4j
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;

    @PostMapping("/getRoleUserByUserId")
    @ResponseBody
    public Results getRoleUserByUserId(Integer userId) {

        log.info("RoleUserController.getRoleUserByUserId: param = " + userId);
        return roleUserService.getSysRoleUserByUserId(userId);
    }

}
