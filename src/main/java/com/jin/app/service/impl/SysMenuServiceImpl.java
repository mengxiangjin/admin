package com.jin.app.service.impl;

import com.jin.app.core.page.PageRequest;
import com.jin.app.core.page.PageResult;
import com.jin.app.dao.SysMenuMapper;
import com.jin.app.entity.SysMenu;
import com.jin.app.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public int save(SysMenu record) {
        return 0;
    }

    @Override
    public int delete(SysMenu record) {
        return 0;
    }

    @Override
    public int delete(List<SysMenu> records) {
        return 0;
    }

    @Override
    public SysMenu findById(long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return null;
    }

    @Override
    public List<SysMenu> findByUser(String userName) {
        return sysMenuMapper.findByUser(userName);
    }
}
