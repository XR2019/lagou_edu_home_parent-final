package com.lagou.service.impl;

import com.lagou.dao.MenuMapper;
import com.lagou.domain.Menu;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    //注入mapper对象
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> findSubMenuListByPid(int pid) {
        List<Menu> menus = menuMapper.findSubMenuListByPid(pid);
        return menus;
    }

    @Override
    public List<Menu> findAllMenu() {
        List<Menu> menus = menuMapper.findAllMenu();
        return menus;
    }

    @Override
    public Menu findMenuById(Integer id) {
        return menuMapper.findMenuById(id);
    }
}
