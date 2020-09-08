package com.jin.app.service;

import com.jin.app.core.service.CrudService;
import com.jin.app.entity.SysMenu;

import java.util.List;


public interface SysMenuService extends CrudService<SysMenu> {
    List<SysMenu> findByUser(String userName);
}
