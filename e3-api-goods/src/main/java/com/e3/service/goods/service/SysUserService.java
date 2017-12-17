package com.e3.service.goods.service;

import com.e3.utils.E3Result;

/**
 * Created by Administrator on 2017/11/20.
 */
public interface SysUserService {

    E3Result checkUser(String usercode, String password);
}
