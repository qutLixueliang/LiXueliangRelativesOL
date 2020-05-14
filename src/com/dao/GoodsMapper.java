package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Goods;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);
    
    //  查询
    public List<Goods> getAll(Map<String, Object> map);

    public List<Goods> getByPage(Map<String, Object> map);
    //  获取信息的条数
    public int getCount(Map<String, Object> map);
    //  模糊查询
    public List<Goods> select(Map<String, Object> map);

    public List<Goods> getTop(Map<String, Object> map);
}