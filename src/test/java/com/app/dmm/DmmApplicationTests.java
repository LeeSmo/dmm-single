package com.app.dmm;

import com.app.dmm.modules.user.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class DmmApplicationTests {

    @Test
    void contextLoads() {
    }
   /* @Autowired
    private SysUserMapper userMapper;
    @Test
    public ResultResponse findAll1() {
        ResultResponse<SysUser> response = new ResultResponse<SysUser>();
        SysUser user = new SysUser();
        user.setUserName("aa");
        user.setPassword("11111");
        int i = userMapper.insert(user);
        System.out.println(i);
        return response;
    }*/
   /*@Autowired
   private RedisTemplate redisTemplate;
    @Test
    public void testObj() throws Exception {
        SysUser user=new SysUser(1, "java的架构师技术栈", "man");
        ValueOperations<String, SysUser> operations=redisTemplate.opsForValue();
        operations.set("fdd2", user);
        boolean exists=redisTemplate.hasKey("fdd2");
        System.out.println("redis是否存在相应的key"+exists);
        SysUser getUser = (SysUser)redisTemplate.opsForValue().get("fdd2");
        System.out.println("从redis数据库获取的user:"+getUser.toString());
    }*/
}
