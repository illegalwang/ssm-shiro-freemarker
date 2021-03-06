package com.wj.dao;

import com.wj.bean.model.SysUser;

import java.util.List;
import java.util.Set;

/**
 * Created by wisi on 2018/11/6.
 */
public interface UserMapper {
    SysUser selectUserByName(String username);

    List<SysUser> selectUserList();

    Set<String> listPermissionByUserId(String userId);
}
