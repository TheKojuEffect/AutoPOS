package com.kapilkoju.autopos.catalog.service;

import com.kapilkoju.autopos.Application;
import com.kapilkoju.autopos.catalog.domain.Brand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WithUserDetails("admin")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BrandRepoTest {

    @Autowired
    private BrandRepo brandRepo;

    @Test
    public void testSave() {
        final Brand brand = new Brand();
        brand.setName("Brand one");

        brandRepo.save(brand);

        assertThat(brand.getId(), is(notNullValue()));
    }

}