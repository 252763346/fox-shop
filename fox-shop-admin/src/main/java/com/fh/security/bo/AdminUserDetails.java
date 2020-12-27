package com.fh.security.bo;

import com.fh.admin.entity.UmsAdmin;
import com.fh.resource.entity.UmsResource;
import com.fh.role.entity.UmsRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName AdminUserDetails
 * @Deacription TODO
 * @Author 路东生
 * @Date 2020/12/20 14:32
 **/
public class AdminUserDetails implements UserDetails {

    private UmsAdmin umsAdmin;
    private List<UmsResource> resourceList;
    private List<UmsRole> roleList;

    public AdminUserDetails(UmsAdmin umsAdmin,List<UmsResource> resourceList,List<UmsRole> roleList){
        this.umsAdmin=umsAdmin;
        this.resourceList=resourceList;
        this.roleList=roleList;
    }

    //给用户授予角色和权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list=new ArrayList<>();
        resourceList.forEach(resource->{
            list.add(new SimpleGrantedAuthority(resource.getKeyword()));
        });
        roleList.forEach(role->{
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getKeyword()));
        });
        return list;
    }

    //获取密码
    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    //获取用户名
    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
