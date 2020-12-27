package com.fh.menu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fh.menu.entity.UmsMenu;
import com.fh.menu.mapper.UmsMenuMapper;
import com.fh.menu.service.IUmsMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author lds
 * @since 2020-12-17
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements IUmsMenuService {
    @Resource
    IUmsMenuService umsMenuService;
    @Resource
    UmsMenuMapper menuMapper;

    //查询动态菜单栏
    @Override
    public List<Map<String, Object>> queryMenuTreeList(String username) {
        List<UmsMenu> menuList = menuMapper.queryMenuTreeList(username);
        return getMenuData(menuList,0l);
    }
    //递归
    private List<Map<String, Object>> getMenuData(List<UmsMenu> menuList, Long pid) {
        List<Map<String, Object>> list=new ArrayList<>();
        menuList.forEach(menuData->{
            Map<String, Object> map=null;
            if(menuData.getParentId().equals(pid)){
                map=new HashMap<>();
                map.put("icon",menuData.getIcon());
                map.put("index",menuData.getName());
                map.put("title",menuData.getTitle());
                //判断为父节点，才自己调用自己
                if(menuData.getParentId()==0){
                    map.put("subs",getMenuData(menuList,menuData.getId()));
                }
            }
            if(map!=null){
                list.add(map);
            }
        });
        return list;
    }


    //查询菜单
    @Override
    public List<Map<String, Object>> queryTreeList() {
        List<UmsMenu> treeList = umsMenuService.list();
        return getTreeData(treeList,0l);
    }
    //递归
    private List<Map<String, Object>> getTreeData(List<UmsMenu> treeList,Long pid) {
        List<Map<String, Object>> list=new ArrayList<>();
        treeList.forEach(treeData->{
            Map<String, Object> map=null;
            if(treeData.getParentId().equals(pid)){
                map=new HashMap<>();
                map.put("id",treeData.getId());
                map.put("label",treeData.getTitle());
                map.put("children",getTreeData(treeList,treeData.getId()));
            }
            if(map!=null){
                list.add(map);
            }
        });
        return list;

    }



}
