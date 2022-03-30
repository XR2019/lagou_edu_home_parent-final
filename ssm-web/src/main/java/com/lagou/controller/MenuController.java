package com.lagou.controller;

import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    //注入service层对象
    @Autowired
    private MenuService menuService;
    /*
        查询所有菜单信息
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu() {
        List<Menu> menus = menuService.findAllMenu();
        ResponseResult responseResult = new ResponseResult(true,200,"查询所有菜单信息成功",menus);
        return responseResult;
    }

    /*
        回显菜单信息
     */
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id) {
        //根据id的值判断当前是更新还是添加操作，判断id的值是否为-1，如果为-1则就是添加操作
        if (id == -1) {
            //添加操作,回显信息中就不需要查询menu信息
            List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);
            //封装数据
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("menuInfo",null);
            hashMap.put("parentMenuList",subMenuListByPid);
            ResponseResult responseResult = new ResponseResult(true,200,"添加回显信息成功",hashMap);
            return responseResult;
        } else {
            //进行修改操作,回显所有menu信息
           Menu menu = menuService.findMenuById(id);
            List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(id);
            //封装数据
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("menuInfo",menu);
            hashMap.put("parentMenuList",subMenuListByPid);
            ResponseResult responseResult = new ResponseResult(true,200,"修改回显成功",hashMap);

            return responseResult;

        }
    }

}
