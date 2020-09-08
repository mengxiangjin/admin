package com.jin.app.service.impl;

import com.jin.app.core.page.MybatisPageHelper;
import com.jin.app.core.page.PageRequest;
import com.jin.app.core.page.PageResult;
import com.jin.app.dao.SysMenuMapper;
import com.jin.app.dao.SysUserMapper;
import com.jin.app.dao.SysUserRoleMapper;
import com.jin.app.entity.SysMenu;
import com.jin.app.entity.SysRole;
import com.jin.app.entity.SysUser;
import com.jin.app.entity.SysUserRole;
import com.jin.app.service.SysMenuService;
import com.jin.app.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysMenuService sysMenuService;



    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest,sysUserMapper);
    }

    @Override
    public List<SysUser> findAllUser() {
        return sysUserMapper.findAllUser();
    }

    @Override
    public List<SysUser> findPage() {
        return null;
    }

    @Override
    public SysUser findByName(String name) {
        return sysUserMapper.findByName(name);
    }

    /*
    * user->user_role->role_menu->menu（多表查询）
    * 权限在menu表中的perms字段
    * */
    @Override
    public Set<String> findPermissions(String userName) {
        Set<String> perms = new HashSet<>();
        List<SysMenu> menuList = sysMenuService.findByUser(userName);
        for (SysMenu menu : menuList){
            if(menu.getPerms() != null && !"".equals(menu.getPerms())) {
                perms.add(menu.getPerms());
            }
        }
        return perms;
    }

    @Override
    public List<SysUserRole> findUserRoles(Long userId) {
        return sysUserRoleMapper.findUserRoles(userId);
    }

    @Override
    @Transactional
    public int save(SysUser record) {
        Long id = null;
        if(record.getId() == null || record.getId() == 0) {
            // 新增用户
            sysUserMapper.insertSelective(record);
            id = record.getId();
        } else {
            // 更新用户信息（若id！=0且数据库又没有此id，则什么操作也不做，更新0）
            sysUserMapper.updateByPrimaryKeySelective(record);
        }
        // 更新用户角色
        //id=0即新增加进来的用户（不会更新用户角色）
        if(id != null && id == 0) {
            return 1;
        }
        if(id != null) {
            for(SysUserRole sysUserRole:record.getUserRoles()) {
                sysUserRole.setUserId(id);
            }
        } else {
            //更新用户信息后，也需要sys_user_role表中删除改user_id对应的记录
            sysUserRoleMapper.deleteByUserId(record.getId());
        }
        for(SysUserRole sysUserRole:record.getUserRoles()) {
            //更新用户权限（根据前台传入的record.getUserRoles()）插入到sys_user_role表中
            sysUserRoleMapper.insertSelective(sysUserRole);
        }
        return 1;
    }

    @Override
    public int delete(SysUser record) {
        return sysUserMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysUser> records) {
        for (SysUser record : records){
            delete(record);
        }
        return 1;
    }

    @Override
    public SysUser findById(long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }


}
