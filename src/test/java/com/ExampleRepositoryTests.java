package com;

import com.domain.City;
import com.domain.User;
import com.repository.CityRepository;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;
/**
 * Created by zengjie on 17/6/22.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExampleRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void testExample() throws Exception {
        this.entityManager.persist(new City("上海", "上海真的好啊好"));
        City city = this.cityRepository.findName("上海");
        assertThat(city.getName()).isEqualTo("上海");
        assertThat(city.getState()).isEqualTo("上海真的好啊好");
    }

}
