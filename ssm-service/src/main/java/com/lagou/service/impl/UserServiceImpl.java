package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    //注入dao层的mapper对象
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {
        //我们可以考虑使用pageHelper插件进行分页操作
        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());
        List<User> users = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<User>(users);

        System.out.println("总条数：" + pageInfo.getTotal());
        System.out.println("总页数：" + pageInfo.getPages());
        System.out.println("当前页：" + pageInfo.getPageNum());
        System.out.println("每页显示长度：" + pageInfo.getPageSize());
        System.out.println("是否是第一页：" + pageInfo.isIsFirstPage());
        System.out.println("是否是最后一页：" + pageInfo.isIsLastPage());
        //将pageInfo对象作为方法的返回值进行返回
        return pageInfo;
    }

    @Override
    public void updateUserStatus(int id, String status) {
        userMapper.updateUserStatus(id , status);
    }

    @Override
    public User login(User user) throws Exception {
        //用户登录
        User user2 = userMapper.login(user);  //包含密文密码
        if (user2 != null && Md5.verify(user.getPassword(),"lagou",user2.getPassword())) {
                return user2;
        } else {
            return null;
        }

    }

    //分配角色回显
    @Override
    public List<Role> findUserRelationRoleById(Integer id) {
        List<Role> list = userMapper.findUserRelationRoleById(id);
        return list;
    }


    /*
        分配角色
     */
    @Override
    public void userContextRole(UserVo userVo) {
        //1.根据用户id清空中间表关联关系
        userMapper.deleteUserContextRole(userVo.getUserId());

        //2.再重新建立关联关系
        List<Integer> roleIdList = userVo.getRoleIdList();
        for (Integer roleId : roleIdList) {
            //封装数据
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(roleId);
            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);

            user_role_relation.setCreatedBy("System");
            user_role_relation.setUpdatedby("System");

            userMapper.userContextRole(user_role_relation);
        }
    }

    //获取用户权限信息
    @Override
    public ResponseResult getUserPermissions(Integer userId) {
        //1.获取当前用户拥有的角色
        List<Role> roleList = userMapper.findUserRelationRoleById(userId);
        //2.获取角色id，保存到list集合当中
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
        }
        //3.根据角色id查询父级菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);
        //4.查询封装父菜单关联的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenu);
        }
        //5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);
        //6.封装数据并返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("menuList",parentMenu);
        map.put("resourceList",resourceList);

        ResponseResult responseResult = new ResponseResult(true,200,"获取用户权限信息成功",map);

        return responseResult;
    }
}
