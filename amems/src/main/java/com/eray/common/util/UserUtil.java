package com.eray.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import com.eray.thjw.ctx.SysContext;

/**
 * Created by houxy on 2016/8/1.
 */
public class UserUtil{

    public static String MD5 (String msg){
            String str  = msg + SysContext.SALT_VALUE; //待加密字符串
            String md5Str = DigestUtils.md5Hex(str);
            return md5Str;
    }
}