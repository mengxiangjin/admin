package com.jin.app.service.impl;

import com.jin.app.core.page.PageRequest;
import com.jin.app.core.page.PageResult;
import com.jin.app.dao.SysDeptMapper;
import com.jin.app.entity.SysDept;
import com.jin.app.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public int save(SysDept record) {
        if (record.getId()==null||record.getId()==0){
            return sysDeptMapper.insertSelective(record);
        }
        return sysDeptMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysDept record) {
        return sysDeptMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysDept> records) {
        for (SysDept record:records){
            delete(record);
        }
        return 1;
    }

    @Override
    public SysDept findById(long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return null;
    }

    /*
    * 查询树层级关系
    * */
    @Override
    public List<SysDept> findTree() {
        //存放最高级节点
        List<SysDept> depts = new ArrayList<SysDept>();
        //存放机构所有数据
        List<SysDept> deptList = sysDeptMapper.findAll();
        for (SysDept record : deptList){
            //若机构无父id字段值，则为根节点，设置等级，加入到depts集合中
            if (record.getParentId()==null||record.getParentId()==0){
                record.setLevel(0);
                depts.add(record);
            }
        }
        //查询子节点
        findChildren(depts,deptList);
        return depts;
    }

    private void findChildren(List<SysDept> depts, List<SysDept> deptList) {
        for (SysDept dept : depts){
            List<SysDept> list = new ArrayList<>();
            for (SysDept sysDept : deptList){
                //成立则sysdept为dept子节点
                if (sysDept.getParentId()==dept.getId()){
                    sysDept.setLevel(dept.getLevel()+1);
                    sysDept.setParentName(dept.getName());
                    list.add(sysDept);
                }
            }
            dept.setChildren(list);
            //递归找子树的子树
            findChildren(list,deptList);
        }
    }
}
