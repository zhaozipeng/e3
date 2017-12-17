package com.e3.service.goods.service.impl;

import com.e3.service.goods.mapper.SysPermissionMapper;
import com.e3.service.goods.mapper.SysUserMapper;
import com.e3.service.goods.pojo.ActiveUser;
import com.e3.service.goods.pojo.SysUser;
import com.e3.service.goods.pojo.SysUserExample;
import com.e3.service.goods.service.SysUserService;
import com.e3.utils.E3Result;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/11/20.
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    E3Result result=null;
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysPermissionMapper permissionMapper;
    @Override
    public E3Result checkUser(String usercode, String password) {
        SysUserExample exampl=new SysUserExample();
        exampl.createCriteria().andUsercodeEqualTo(usercode);
       List<SysUser>sysUsers= sysUserMapper.selectByExample(exampl);
        if(sysUsers!=null&&sysUsers.size()>0){
           SysUser sysUser= sysUsers.get(0);
           //因为is加密之后的密码   判断密码  表里面的盐值
            String salt=sysUser.getSalt();
            //加密后的密码
            String pwd=DigestUtils.md5DigestAsHex((password+salt).getBytes());
            if(!sysUser.getPassword().equals(pwd)){
                //用户名或者密码错误
                result=E3Result.bulid("400","用户名或者密码错误",null);
            }else {
                //登陆成功 要知道有哪些权限
                //TODO
                ActiveUser activeUser = new ActiveUser();
                activeUser.getUserid(sysUser.getId());
                activeUser.getUsercode(sysUser.getUsercode());
                activeUser.getUsername(sysUser.getUsername());
                //获取用户可查看的菜单
                activeUser.setMenus(permissionMapper.selectMenuList(sysUser.getId()));
                //查看用户可拥有的权限
                activeUser.setPermissions(permissionMapper.selectPermissionList(sysUser.getId()));


                //验证成功 返回对象
                result=E3Result.ok(activeUser);

            }


        }else {
            //用户不存在
         result = E3Result.bulid("400", "用户不存在", null);

        }

        return result;
    }
}
