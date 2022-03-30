package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;
import com.lagou.domain.Role_menu_relation;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    //注入mapper对象
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> findALlRole(Role role) {
        List<Role> roles = roleMapper.findAllRole(role);

        return roles;
    }

    @Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        List<Integer> id = roleMapper.findMenuByRoleId(roleId);
        return id;
    }

    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {
        //第一件事，清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());
        //第二件事，为角色分配菜单
        for (Integer mid : roleMenuVo.getMenuIdList()) {
            Role_menu_relation relation = new Role_menu_relation();
            relation.setMenuId(mid);
            relation.setRoleId(roleMenuVo.getRoleId());
            //封装数据
            Date date = new Date();
            relation.setCreatedTime(date);
            relation.setUpdatedTime(date);
            relation.setCreatedBy("System");
            relation.setUpdatedby("System");

            roleMapper.RoleContextMenu(relation);
        }

    }

    @Override
    public void deleteRole(Integer roleId) {
        //调用根据roleId清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleId);

        roleMapper.deleteRole(roleId);
    }
}
