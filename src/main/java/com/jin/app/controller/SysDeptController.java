package com.jin.app.controller;

import com.jin.app.core.http.HttpResult;
import com.jin.app.entity.SysDept;
import com.jin.app.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping("save")
    public HttpResult save(@RequestBody SysDept record){
        return HttpResult.ok(sysDeptService.save(record));
    }

    @PostMapping("delete")
    public HttpResult delete(@RequestBody List<SysDept> records){
        return HttpResult.ok(sysDeptService.delete(records));
    }

    /*
    * 查询部门层级关系（dept 存在非数据库字段（））
    *   List<SysDept> children;
    *   String parentName;
    *   Integer level; 最高树节点为0
    * */
    @GetMapping("findTree")
    public HttpResult findTree(){
        return HttpResult.ok(sysDeptService.findTree());
    }
}
