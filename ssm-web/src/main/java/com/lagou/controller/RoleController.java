package com.lagou.controller;

import com.lagou.domain.*;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    //注入service对象
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;
    /*
        根据条件进行查询所有角色
     */
    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role) {
        List<Role> roles = roleService.findALlRole(role);
        ResponseResult responseResult = new ResponseResult(true,200,"查询所有角色成功",roles);

        return responseResult;
    }

    /*
        查询所有父子菜单信息（分配菜单的第一个接口）
     */

    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu() {
        //-1表示查询所有的父子级菜单
        List<Menu> menus = menuService.findSubMenuListByPid(-1);
        //响应数据
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("parentMenuList",menus);
        ResponseResult responseResult = new ResponseResult(true,200,"查询所有的父子菜单成功",map);

        return responseResult;
    }

    /*
        根据角色id查询关联的菜单信息id
     */
    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuByRoleId(Integer roleId) {
        List<Integer> menuByRoleId = roleService.findMenuByRoleId(roleId);
        ResponseResult responseResult = new ResponseResult(true,200,"查询角色关联的菜单信息成功",menuByRoleId);
        return responseResult;
    }

    /*
        为角色分配菜单
     */
    @RequestMapping("/RoleContextMenu")
    public ResponseResult RoleContextMenu(@RequestBody RoleMenuVo roleMenuVo) {

        roleService.roleContextMenu(roleMenuVo);
        ResponseResult responseResult = new ResponseResult(true,200,"响应成功",null);
        return responseResult;
    }

    /*
        删除角色的方法
     */
    @RequestMapping("/deleteRole")
    public ResponseResult deleteRole(Integer id) {
        roleService.deleteRole(id);
        ResponseResult responseResult = new ResponseResult(true,200,"删除角色成功",null);
        return responseResult;
    }

}
