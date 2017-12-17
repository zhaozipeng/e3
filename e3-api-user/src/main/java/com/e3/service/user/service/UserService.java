package com.e3.service.user.service;

import com.e3.service.user.pojo.TbUser;
import com.e3.utils.E3Result;

/**
 * Created by Administrator on 2017/12/2.
 */
public interface UserService {
    E3Result checkData(String param, Integer type);
    E3Result login(String username, String password);

    E3Result regist(TbUser tbUser);

    E3Result logout(String token);

    E3Result selectUserByToken(String token);
}
