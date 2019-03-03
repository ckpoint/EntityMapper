package hsim.mapper.entity;

import hsim.mapper.entity.domain.MapAuditEntity;
import hsim.mapper.entity.domain.MapEntity;
import hsim.mapper.entity.domain.Users;
import hsim.mapper.entity.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.h2.engine.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EntityApplicationTests {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void contextLoads() {

        Users user = new Users();
        user = this.usersRepository.save(user);
        log.info(user.toString());

    }

}
