package com.fh.memberlevel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.memberlevel.entity.UmsMemberLevel;
import com.fh.memberlevel.service.IUmsMemberLevelService;
import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 会员等级表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/member")
public class UmsMemberLevelController {
    @Resource
    IUmsMemberLevelService umsMemberLevelService;

    @GetMapping("/{defaultStatus}")
    public CommonsReturn queryMemberLevel(@PathVariable("defaultStatus") Integer defaultStatus){
        QueryWrapper<UmsMemberLevel> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("default_status",defaultStatus);
        List<UmsMemberLevel> memberLevelList=umsMemberLevelService.list(queryWrapper);
        return CommonsReturn.success(memberLevelList);
    }



}
