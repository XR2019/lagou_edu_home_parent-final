package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    //注入service层对象
    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/findAllResource")
    public ResponseResult findAllResourceByPage(@RequestBody ResourceVo resourceVo) {
        PageInfo<Resource> pageInfo = resourceService.findAllResourceByPage(resourceVo);
        ResponseResult responseResult = new ResponseResult(true,200,"资源信息分页多条件查询成功",pageInfo);
        return responseResult;
    }


    /*
       添加&编辑
    */
    @RequestMapping("/saveOrUpdateResource")
    public ResponseResult saveOrUpdateResource(@RequestBody Resource resource){

        if(resource.getId() ==null ){
            resourceService.saveResource(resource);
        }else {
            resourceService.updateResource(resource);
        }

        ResponseResult responseResult = new ResponseResult(true,200,"响应成功","");
        return responseResult;
    }


    @RequestMapping("/deleteResource")
    public ResponseResult deleteResource(Integer id){

        resourceService.deleteResource(id);
        ResponseResult responseResult = new ResponseResult(true,200,"响应成功","");
        return responseResult;
    }


}
