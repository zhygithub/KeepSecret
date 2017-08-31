package zhy.scau.com.keepyourword.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 80234812 on 2017/8/16.
 */
public class EncryptUtils {

    /**
     * MD% 字符串 加密
     * @param textNeedEn
     * @return
     */
    public static String md5Encrypt(String textNeedEn){
        if(TextUtils.isEmpty(textNeedEn)){
            return BaseConstant.NOTHING;
        }

        MessageDigest md5 = null;
        StringBuilder result = new StringBuilder(BaseConstant.NOTHING);

        try{
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(textNeedEn.getBytes());
            String temp;

            for(byte b: bytes){
                temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1){
                    temp = "0" + temp;
                }
                result.append(temp);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
