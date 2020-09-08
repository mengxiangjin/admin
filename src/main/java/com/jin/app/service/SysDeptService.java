package com.jin.app.service;

import com.jin.app.core.service.CrudService;
import com.jin.app.entity.SysDept;

import java.util.List;

public interface SysDeptService extends CrudService<SysDept> {
    List<SysDept> findTree();
}
