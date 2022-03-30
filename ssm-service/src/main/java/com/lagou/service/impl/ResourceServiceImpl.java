package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.ResourceMapper;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVo;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    //注入mapper对象
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public PageInfo<Resource> findAllResourceByPage(ResourceVo resourceVo) {
        //分页查询
        PageHelper.startPage(resourceVo.getCurrentPage(),resourceVo.getPageSize());
        List<Resource> resources = resourceMapper.findAllResourceByPage(resourceVo);
        PageInfo<Resource> pageInfo = new PageInfo<>(resources);

        return pageInfo;
    }

    /*
        添加资源信息
     */

    @Override
    public void saveResource(Resource resource) {
        //封装数据
        resource.setCreatedTime(new Date());
        resource.setUpdatedTime(new Date());
        resource.setCreatedBy("System");
        resource.setUpdatedBy("System");

        resourceMapper.saveResource(resource);
    }

    /*
        更新资源
     */
    @Override
    public void updateResource(Resource resource) {

        resource.setCreatedTime(new Date());
        resource.setUpdatedTime(new Date());
        resource.setCreatedBy("system");
        resource.setUpdatedBy("system");

        resourceMapper.updateResource(resource);
    }

    /*
        根据id删除资源信息
     */
    @Override
    public void deleteResource(Integer id) {
        resourceMapper.deleteResource(id);
    }
}
