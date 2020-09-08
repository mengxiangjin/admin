package com.jin.app.service.impl;

import com.jin.app.core.page.MybatisPageHelper;
import com.jin.app.core.page.PageRequest;
import com.jin.app.core.page.PageResult;
import com.jin.app.dao.SysDictMapper;
import com.jin.app.entity.SysDict;
import com.jin.app.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    /*
    * 增加字典记录
    * */
    @Override
    public int save(SysDict record) {
        if (record.getId()==null||record.getId()==0){
            //传入id=0时，字典插入记录（id为自动增长后的字段）
            return sysDictMapper.insertSelective(record);
        }
        //传入非0的id即更新字典表字段 (传入5->更新id=5的字典记录表)
        return sysDictMapper.updateByPrimaryKeySelective(record);
    }

    /*
    * 单一删除
    * */
    @Override
    public int delete(SysDict record) {
        return sysDictMapper.deleteByPrimaryKey(record.getId());
    }

    /*
    * 批量删除
    * */
    @Override
    public int delete(List<SysDict> records) {
        for (SysDict item:records){
            delete(item);
        }
        return 1;
    }

    /*
    * 字典主键查询
    * */
    @Override
    public SysDict findById(long id) {
        return sysDictMapper.selectByPrimaryKey(id);
    }

    /*
    * 分页查询（字典表）
    * */
    @Override
    public PageResult findPage(PageRequest pageRequest) {
        //获取前台是否传来分页条件带label参数
        //pageRequest pageNum，pageSize，params
        Object label = pageRequest.getParam("label");
        if (label!=null){
            return MybatisPageHelper.findPage(pageRequest,sysDictMapper,"findPageByLabel",label);
        }
        return MybatisPageHelper.findPage(pageRequest,sysDictMapper);
    }

    /*
    * 标签名查询
    * */
    @Override
    public List<SysDict> findByLabel(String label) {
        return sysDictMapper.findByLable(label);
    }
}
