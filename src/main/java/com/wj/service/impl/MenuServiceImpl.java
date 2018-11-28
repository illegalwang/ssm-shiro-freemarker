package com.wj.service.impl;

import com.wj.bean.model.SysMenu;
import com.wj.bean.model.SysMenuExample;
import com.wj.dao.MenuMapper;
import com.wj.dao.mapper.SysMenuMapper;
import com.wj.service.MenuService;
import com.wj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisi on 2018/11/21.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> selectMenuList() {
        List<SysMenu> menus = this.menuMapper.selectMenuList();
        List<SysMenu> menuList = createMenuList(menus);
        System.out.println(menuList);
        return menuList;
    }

    @Override
    public void addMenu(SysMenu menu) {
        sysMenuMapper.insertSelective(menu);
    }

    @Override
    public void updateMenu(SysMenu menu) {
        SysMenuExample example = new SysMenuExample();
        example.createCriteria().andIdEqualTo(menu.getId());
        sysMenuMapper.updateByExampleSelective(menu, example);
    }

    @Override
    public void deleteMenu(Integer id) {
        sysMenuMapper.deleteByPrimaryKey(id);
    }

    public List<SysMenu> createMenuList(List<SysMenu> rootList) {
        List<SysMenu> menus = new ArrayList<>();
        for (int i = 0; i < rootList.size(); i++) {
            if (rootList.get(i).getParentId() == 0) {
                menus.add(rootList.get(i));
            }
        }
        for (SysMenu menu : menus) {
            menu.setChildList(getChildList(menu.getId(), rootList));
        }
        return menus;
    }

    public List<SysMenu> getChildList(int parentId, List<SysMenu> rootList) {
        List<SysMenu> childList = new ArrayList<>();
        for (SysMenu menu : rootList) {
            if (menu.getParentId() != 0) {
                if (menu.getParentId() == parentId) {
                    childList.add(menu);
                }
             }
        }
        for (SysMenu menu : childList) {
            if (menu.getMenuPath().contains("#")) {
                menu.setChildList(getChildList(menu.getId(), rootList));
            }
        }
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
