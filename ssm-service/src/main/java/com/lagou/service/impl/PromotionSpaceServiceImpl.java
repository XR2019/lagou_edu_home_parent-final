package com.lagou.service.impl;

import com.lagou.dao.PromotionSpaceMapper;
import com.lagou.domain.PromotionSpace;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PromotionSpaceServiceImpl implements PromotionSpaceService {
    //注入dao层对象
    @Autowired
    private PromotionSpaceMapper promotionSpaceMapper;
    @Override
    public List<PromotionSpace> findAllPromotionSpace() {
        List<PromotionSpace> promotionSpace = promotionSpaceMapper.findAllPromotionSpace();
        return promotionSpace;
    }

    @Override
    public void savePromotionSpace(PromotionSpace promotionSpace) {
        //封装数据
        promotionSpace.setSpaceKey(UUID.randomUUID().toString());
        Date date = new Date();
        promotionSpace.setCreateTime(date);
        promotionSpace.setUpdateTime(date);
        promotionSpace.setIsDel(0);
        //调用mapper方法
        promotionSpaceMapper.savePromotionSpace(promotionSpace);
    }

    @Override
    public void updatePromotionSpace(PromotionSpace promotionSpace) {
        //封装数据
        Date date = new Date();
        promotionSpace.setUpdateTime(date);
        promotionSpaceMapper.updatePromotionSpace(promotionSpace);
    }

    @Override
    public PromotionSpace findPromotionSpaceById(int id) {
        PromotionSpace promotionSpaceById = promotionSpaceMapper.findPromotionSpaceById(id);
        return promotionSpaceById;
    }
}
