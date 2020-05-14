package com.dao;

import com.entity.News;

import java.util.List;
import java.util.Map;

public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKeyWithBLOBs(News record);

    int updateByPrimaryKey(News record);

    public News checkUname(String account);

    //  查询
    public List<News> getAll(Map<String, Object> map);
    //  分页显示
    public List<News> getByPage(Map<String, Object> map);
    //  获取信息的条数
    public int getCount(Map<String, Object> map);
    //  模糊查询
    public List<News> getTop(Map<String, Object> map);
}