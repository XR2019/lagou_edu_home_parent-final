package com.lagou.controller;

import com.lagou.domain.ResourceCategory;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ResourceCategory")
public class ResourceCategoryController {
    //注入service层对象
    @Autowired
    private ResourceCategoryService resourceCategoryService;

    @RequestMapping("/findAllResourceCategory")
    public ResponseResult findAllResourceCategory() {
        List<ResourceCategory> categories = resourceCategoryService.findAllResourceCategory();
        ResponseResult responseResult = new ResponseResult(true,200,"查询所有分类信息成功",categories);
        return responseResult;
    }

    @RequestMapping("/saveOrUpdateResourceCategory")
    public ResponseResult saveOrUpdateResourceCategory(@RequestBody ResourceCategory resourceCategory){

        if(resourceCategory.getId() == null){
            resourceCategoryService.saveResourceCategory(resourceCategory);
        }else {
            resourceCategoryService.updateResourceCategory(resourceCategory);
        }

        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", "");
        return responseResult;
    }

    /*
        删除资源分类
     */
    @RequestMapping("/deleteResourceCategory")
    public ResponseResult deleteResourceCategory(Integer id){
        resourceCategoryService.deleteResourceCategory(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", "");
        return responseResult;
    }
}
