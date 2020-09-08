package com.jin.app.controller;

import com.jin.app.core.http.HttpResult;
import com.jin.app.core.page.PageRequest;
import com.jin.app.entity.SysDict;
import com.jin.app.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    /*
    * 增加字典记录
    * */
    @PostMapping("save")
    public HttpResult save(@RequestBody SysDict record){
        int i = sysDictService.save(record);
        return HttpResult.ok(i);
    }

    /*
    * 删除单一
    * */
    @PostMapping("delete")
    public HttpResult delete(@RequestBody SysDict record){
        return HttpResult.ok(sysDictService.delete(record));
    }

    /*
    * 批量删除
    * */
    @PostMapping("deleteMost")
    public HttpResult delete(@RequestBody List<SysDict> records){
        return HttpResult.ok(sysDictService.delete(records));
    }

    /*
    * 分页查询
    * */
    @PostMapping("findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        return HttpResult.ok(sysDictService.findPage(pageRequest));
    }

    /*
    * 标签查询
    * */
    @GetMapping("findByLabel")
    public HttpResult findByLabel(@RequestParam String label){
        return HttpResult.ok(sysDictService.findByLabel(label));
    }

}
