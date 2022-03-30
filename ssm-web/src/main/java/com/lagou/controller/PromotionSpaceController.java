package com.lagou.controller;

import com.lagou.domain.PromotionSpace;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/PromotionSpace")
public class PromotionSpaceController {

    //注入service层对象
    @Autowired
    private PromotionSpaceService promotionSpaceService;
    /*
        查询广告位列表
     */
    @RequestMapping("/findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace() {
        List<PromotionSpace> spaces = promotionSpaceService.findAllPromotionSpace();
        ResponseResult responseResult = new ResponseResult(true,200,"查询所有广告位成功",spaces);
        return responseResult;
    }

    /*
        添加&修改广告位
     */
    @RequestMapping("/saveOrUpdatePromotionSpace")
    public ResponseResult savePromotionSpace(@RequestBody PromotionSpace promotionSpace) {
        try {
            if (promotionSpace.getId() == null) {
                //新增广告位
                promotionSpaceService.savePromotionSpace(promotionSpace);
                ResponseResult responseResult = new ResponseResult(true,200,"新增广告位成功",null);
                return responseResult;
            } else {
                //修改广告位
                promotionSpaceService.updatePromotionSpace(promotionSpace);
                ResponseResult responseResult = new ResponseResult(true,200,"修改广告位成功",null);
                return responseResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
        根据id查询广告位信息
     */
    @RequestMapping("/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(@RequestParam int id) {
        PromotionSpace promotionSpace = promotionSpaceService.findPromotionSpaceById(id);
        ResponseResult responseResult = new ResponseResult(true,200,"根据id查询广告位信息成功",promotionSpace);
        return responseResult;
    }



}
