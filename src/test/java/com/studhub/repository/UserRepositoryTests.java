package com.studhub.repository;

import com.studhub.entity.Role;
import com.studhub.entity.User;
import com.studhub.entity.UserStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@Transactional
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@TestPropertySource("/application-test.properties")
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final Date creation = Date.from(
            LocalDateTime
                    .of(2020, 1, 1, 12, 0, 0)
                    .atZone(ZoneId.systemDefault()).
                    toInstant()
    );

    @Test
    public void testfindAll() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<User> result = userRepository.findAll(pageable);

        User user1 = new User();
        user1.setId(1);
        user1.setCreated(creation);
        user1.setLastModified(creation);
        user1.setUsername("admin");
        user1.setFirstName("Ivan");
        user1.setLastName("Ivanov");
        user1.setPassword("1234");
        user1.setCourses(new ArrayList<>());
        user1.setFollowing(new ArrayList<>());
        user1.setFollowers(new ArrayList<>());
        user1.setStatus(UserStatus.ENABLED);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        roles.add(roleRepository.findByName("ROLE_ADMIN"));
        user1.setRoles(new ArrayList<>(roles));

        Assert.assertEquals(0, result.getNumber());
        Assert.assertEquals(5, result.getTotalPages());
        Assert.assertEquals(14, result.getTotalElements());
        Assert.assertEquals(user1, result.getContent().get(0));
    }
}
