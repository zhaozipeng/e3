package com.e3.service.user.service.impl;

import com.e3.service.user.mapper.TbUserMapper;
import com.e3.service.user.pojo.TbUser;
import com.e3.service.user.pojo.TbUserExample;
import com.e3.service.user.service.UserService;
import com.e3.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/2.
 */
@Service
public class UserServiceImpl implements UserService{
    @Resource
    TbUserMapper tbUserMapper;
    @Resource
    JedisUtils jedisUtils;
    /**
     * 可选参数1、2、3分别代表username、phone、email 重复

     * @param param
     * @param type
     * @return
     */
    @Override
    public E3Result checkData(String param, Integer type) {
        TbUserExample example=new TbUserExample();
         switch (type){
             case 1:
                example.createCriteria().andUsernameEqualTo(param);
                 break;
             case 2:
                 example.createCriteria().andPhoneEqualTo(param);

                 break;
             case 3:
                 example.createCriteria().andEmailEqualTo(param);

                 break;


         }

        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
            if(tbUsers!=null&&tbUsers.size()>0){
                    //说明数据库中有数据
                return E3Result.ok(false);
            }
        return E3Result.ok(true);
    }

    @Override
    public E3Result login(String username, String password) {
        //1--先根据用户名查询用户是否存在
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if(tbUsers==null||tbUsers.size()==0){
            return E3Result.bulid("400","用户名不存在",null);
        }
        //取出用户信息
        TbUser tbUser = tbUsers.get(0);
        String dbPassword = tbUser.getPassword();
        String mdPassword=DigestUtils.md5DigestAsHex((password+tbUser.getSalt()).getBytes());
        if(!dbPassword.equals(mdPassword)){
            return E3Result.bulid("400","用户名或者密码错误",null);
        }
        //生成token
        String token=UUID.randomUUID().toString();
        //将用户储存在redis中
        tbUser.setPassword(null);
        jedisUtils.set("USER:TT_TOKEN:"+token, JsonUtils.objectToJson(tbUser));
        jedisUtils.expire("USER:TT_TOKEN:"+token,30*60);
        return E3Result.ok(token);
    }

    @Override
    public E3Result regist(TbUser tbUser) {
        E3Result result =null;
        //校验用户名
        if(TextUtils.isEmpty(tbUser.getUsername())){
            return E3Result.bulid("400","用户名不能为空","");
        }
        //校验是否重复
             result = checkData(tbUser.getUsername(), 1);
            if(!(boolean)result.getData()){
                return E3Result.bulid("400","用户名不能重复","");

                 }
             result = checkData(tbUser.getPhone(), 1);
            if(!(boolean)result.getData()){
                return E3Result.bulid("400","手机号不能重复","");

                 }
         //校验结束之后
        //1--补全信息
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        //2生成salt
        long salt = SnowflakeIdWorker.getInstance().nextId();
        String newPwd= DigestUtils.md5DigestAsHex((tbUser.getPassword()+salt).getBytes());
        //设置密码和salt
        tbUser.setPassword(newPwd);
        tbUser.setSalt(salt+"");
        //4存到数据库中
        tbUserMapper.insert(tbUser);
        return E3Result.ok(null);

    }

    @Override
    public E3Result logout(String token) {
        jedisUtils.del("USER:TT_TOKEN:" + token);
        return E3Result.ok(null);
    }

    @Override
    public E3Result selectUserByToken(String token) {
        String s = jedisUtils.get("USER:TT_TOKEN:" + token);
        if(TextUtils.isEmpty(s)){
            return E3Result.bulid("400","用户过期或不存在",null);
        }
        TbUser tbUser = JsonUtils.jsonToPojo(s, TbUser.class);
        return E3Result.ok(tbUser);
    }


}
