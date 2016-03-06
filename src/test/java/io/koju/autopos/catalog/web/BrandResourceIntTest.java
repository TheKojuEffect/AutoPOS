package io.koju.autopos.catalog.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.koju.autopos.Application;
import io.koju.autopos.catalog.domain.Brand;
import io.koju.autopos.catalog.service.BrandRepository;

import io.koju.autopos.security.SecurityTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BrandResource REST controller.
 *
 * @see BrandResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BrandResourceIntTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";

    @Inject
    private BrandRepository brandRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private ObjectMapper objectMapper;

    private MockMvc restBrandMockMvc;

    private Brand brand;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BrandResource brandResource = new BrandResource();
        ReflectionTestUtils.setField(brandResource, "brandRepository", brandRepository);
        this.restBrandMockMvc = MockMvcBuilders.standaloneSetup(brandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        brand = new Brand();
        brand.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBrand() throws Exception {

        SecurityTestUtil.makeSystemUserCurrentUser();

        int databaseSizeBeforeCreate = brandRepository.findAll().size();

        // Create the Brand

        restBrandMockMvc.perform(post("/api/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(brand)))
                .andExpect(status().isCreated());

        // Validate the Brand in the database
        List<Brand> brands = brandRepository.findAll();
        assertThat(brands).hasSize(databaseSizeBeforeCreate + 1);
        Brand testBrand = brands.get(brands.size() - 1);
        assertThat(testBrand.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandRepository.findAll().size();
        // set the field null
        brand.setName(null);

        // Create the Brand, which fails.

        restBrandMockMvc.perform(post("/api/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(brand)))
                .andExpect(status().isBadRequest());

        List<Brand> brands = brandRepository.findAll();
        assertThat(brands).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrands() throws Exception {
        SecurityTestUtil.makeSystemUserCurrentUser();

        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brands
        restBrandMockMvc.perform(get("/api/brands?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get the brand
        restBrandMockMvc.perform(get("/api/brands/{id}", brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(brand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBrand() throws Exception {
        // Get the brand
        restBrandMockMvc.perform(get("/api/brands/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrand() throws Exception {
        SecurityTestUtil.makeSystemUserCurrentUser();

        // Initialize the database
        brandRepository.saveAndFlush(brand);

		int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // Update the brand
        brand.setName(UPDATED_NAME);

        restBrandMockMvc.perform(put("/api/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(brand)))
                .andExpect(status().isOk());

        // Validate the Brand in the database
        List<Brand> brands = brandRepository.findAll();
        assertThat(brands).hasSize(databaseSizeBeforeUpdate);
        Brand testBrand = brands.get(brands.size() - 1);
        assertThat(testBrand.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteBrand() throws Exception {
        SecurityTestUtil.makeSystemUserCurrentUser();
        // Initialize the database
        brandRepository.saveAndFlush(brand);

		int databaseSizeBeforeDelete = brandRepository.findAll().size();

        // Get the brand
        restBrandMockMvc.perform(delete("/api/brands/{id}", brand.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Brand> brands = brandRepository.findAll();
        assertThat(brands).hasSize(databaseSizeBeforeDelete - 1);
    }
}
