package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVo;

import java.util.List;

public interface ResourceService {

    /*
        资源分页，以及多条件查询
     */
    public PageInfo<Resource> findAllResourceByPage(ResourceVo resourceVo);

    /*
      新增资源信息
    */
    public void saveResource(Resource resource);

    /*
         修改资源信息
     */
    public void updateResource(Resource resource);


    /*
        根据id删除对应的资源信息
     */
    public void deleteResource(Integer id);

}
