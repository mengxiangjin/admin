package com.jin.app.service;

import com.jin.app.core.service.CrudService;
import com.jin.app.entity.SysDict;

import java.util.List;


public interface SysDictService extends CrudService<SysDict> {

    /*
    * 根据标签名称查询
    * */
    List<SysDict> findByLabel(String label);
}
