package com.jin.app.dao;


import com.jin.app.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    /*
    * 分页
    * */
    List<SysDict> findPage();

    /*
    * 标签查询
    * @Param（value） mapper.xml文件写sql语句时#{varchar} varchar是依据param中value值
    *       而不是传入的形参（相当于给形参起别名）
    * */
    List<SysDict> findByLable(@Param(value ="label") String label);

    //标签分页
    List<SysDict> findPageByLabel(@Param(value ="label") String label);


}