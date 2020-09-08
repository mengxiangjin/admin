package com.jin.app.controller;

import com.jin.app.constant.SysConstants;
import com.jin.app.core.http.HttpResult;
import com.jin.app.core.page.PageRequest;
import com.jin.app.core.page.PageResult;
import com.jin.app.entity.SysUser;
import com.jin.app.service.SysUserService;
import com.jin.app.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    /*
    * 测试
    * */
    @GetMapping("findAll")
    public Object findAllUser(){
        List<SysUser> users = sysUserService.findAllUser();
        return users;
    }

    /*
    * 分页查询(service返回的是pageResult)
    * 还有个分页查询（service返回的是List<SysUser> 未完善）
    * */
    @PostMapping("findPage")
    public HttpResult findAllUser(@RequestBody PageRequest pageRequest){

        PageResult result = sysUserService.findPage(pageRequest);
        HttpResult httpResult = HttpResult.ok(result);
        return httpResult;
    }

    /*
    * 保存用户
    * */
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysUser record) {
        SysUser user = sysUserService.findById(record.getId());
        if(user != null) {
            if(SysConstants.ADMIN.equalsIgnoreCase(user.getName())) {
                return HttpResult.error("超级管理员不允许修改!");
            }
        }
        if(record.getPassword() != null) {
            String salt = PasswordUtils.getSalt();
            if(user == null) {
                // 新增用户
                if(sysUserService.findByName(record.getName()) != null) {
                    return HttpResult.error("用户名已存在!");
                }
                String password = PasswordUtils.encode(record.getPassword(), salt);
                record.setSalt(salt);
                record.setPassword(password);
            } else {
                // 修改用户, 且修改了密码
                if(!record.getPassword().equals(user.getPassword())) {
                    String password = PasswordUtils.encode(record.getPassword(), salt);
                    record.setSalt(salt);
                    record.setPassword(password);
                }
            }
        }
        return HttpResult.ok(sysUserService.save(record));
    }

    /*
    * 删除用户
    * */
    @PostMapping("delete")
    public HttpResult delete(@RequestBody List<SysUser> records){
        for (SysUser record:records){
            SysUser user = sysUserService.findById(record.getId());
            if (user!=null && SysConstants.ADMIN.equalsIgnoreCase(user.getName())){
                return HttpResult.error("admin不允许被删除");
            }
        }
        return HttpResult.ok(sysUserService.delete(records));
    }

    /*
    * 用户名查询
    * */
    @GetMapping("findByName")
    public HttpResult findByName(@RequestParam String userName){
        return HttpResult.ok(sysUserService.findByName(userName));
    }

    /*
    * 根据用户名查询用户权限 user->user_role->role_menu->menu
    * */
    @GetMapping("findPermission")
    public HttpResult findPermissions(@RequestParam String userName){
        return HttpResult.ok(sysUserService.findPermissions(userName));
    }

    /*
    * 获取用户角色 user->user_role->role
    * */
    @GetMapping("findUserRoles")
    public HttpResult findUserRoles(@RequestParam Long userId){
        return HttpResult.ok(sysUserService.findUserRoles(userId));
    }

}
