package hsim.mapper.entity;

import hsim.mapper.entity.domain.Users;
import hsim.mapper.entity.model.UsersModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class EntityApplicationTests {

    @Test
    public void contextLoads() {

        UsersModel userModel = new UsersModel();
        userModel.setAge(20);
        userModel.setEmail("hsim@daou.co.kr");
        userModel.setName("hsim");
        userModel.setGender("MAN");
        userModel.setStrs(Arrays.asList("123", "456"));
        userModel.setId(1L);

        Users users = new Users();
        users.updateFromObj(userModel);

        log.info(users.toString());

    }


}
