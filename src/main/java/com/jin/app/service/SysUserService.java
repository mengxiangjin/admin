package com.jin.app.service;

import com.jin.app.core.service.CrudService;
import com.jin.app.entity.SysMenu;
import com.jin.app.entity.SysRole;
import com.jin.app.entity.SysUser;
import com.jin.app.entity.SysUserRole;

import java.util.List;
import java.util.Set;

public interface SysUserService extends CrudService<SysUser> {

    List<SysUser> findAllUser();

    List<SysUser> findPage();

    SysUser findByName(String name);

    Set<String> findPermissions(String userName);

    List<SysUserRole> findUserRoles(Long userId);
}
