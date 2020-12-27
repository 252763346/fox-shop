package com.fh;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName test
 * @Deacription TODO
 * @Author 路东生
 * @Date 2020/12/20 18:50
 **/
public class test {

    @Test
    void test(){
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);
    }

}
