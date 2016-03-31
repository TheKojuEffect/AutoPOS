package io.koju.autopos.party.web;

import io.koju.autopos.Application;
import io.koju.autopos.party.domain.Vendor;
import io.koju.autopos.party.service.VendorRepository;
import io.koju.autopos.party.web.VendorResource;
import io.koju.autopos.web.rest.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Test class for the VendorResource REST controller.
 *
 * @see VendorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VendorResourceIntTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";
    private static final String DEFAULT_REMARKS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_PHONE_NUMBERS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBERS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private VendorRepository vendorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVendorMockMvc;

    private Vendor vendor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VendorResource vendorResource = new VendorResource();
        ReflectionTestUtils.setField(vendorResource, "vendorRepository", vendorRepository);
        this.restVendorMockMvc = MockMvcBuilders.standaloneSetup(vendorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vendor = new Vendor();
        vendor.setName(DEFAULT_NAME);
        vendor.setRemarks(DEFAULT_REMARKS);
        vendor.setPhoneNumbers(DEFAULT_PHONE_NUMBERS);
    }

    @Test
    @Transactional
    public void createVendor() throws Exception {
        int databaseSizeBeforeCreate = vendorRepository.findAll().size();

        // Create the Vendor

        restVendorMockMvc.perform(post("/api/vendors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vendor)))
                .andExpect(status().isCreated());

        // Validate the Vendor in the database
        List<Vendor> vendors = vendorRepository.findAll();
        assertThat(vendors).hasSize(databaseSizeBeforeCreate + 1);
        Vendor testVendor = vendors.get(vendors.size() - 1);
        assertThat(testVendor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVendor.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testVendor.getPhoneNumbers()).isEqualTo(DEFAULT_PHONE_NUMBERS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setName(null);

        // Create the Vendor, which fails.

        restVendorMockMvc.perform(post("/api/vendors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vendor)))
                .andExpect(status().isBadRequest());

        List<Vendor> vendors = vendorRepository.findAll();
        assertThat(vendors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumbersIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setPhoneNumbers(null);

        // Create the Vendor, which fails.

        restVendorMockMvc.perform(post("/api/vendors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vendor)))
                .andExpect(status().isBadRequest());

        List<Vendor> vendors = vendorRepository.findAll();
        assertThat(vendors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVendors() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendors
        restVendorMockMvc.perform(get("/api/vendors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].phoneNumbers").value(hasItem(DEFAULT_PHONE_NUMBERS.toString())));
    }

    @Test
    @Transactional
    public void getVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get the vendor
        restVendorMockMvc.perform(get("/api/vendors/{id}", vendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vendor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.phoneNumbers").value(DEFAULT_PHONE_NUMBERS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVendor() throws Exception {
        // Get the vendor
        restVendorMockMvc.perform(get("/api/vendors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Update the vendor
        Vendor updatedVendor = new Vendor();
        updatedVendor.setId(vendor.getId());
        updatedVendor.setName(UPDATED_NAME);
        updatedVendor.setRemarks(UPDATED_REMARKS);
        updatedVendor.setPhoneNumbers(UPDATED_PHONE_NUMBERS);

        restVendorMockMvc.perform(put("/api/vendors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedVendor)))
                .andExpect(status().isOk());

        // Validate the Vendor in the database
        List<Vendor> vendors = vendorRepository.findAll();
        assertThat(vendors).hasSize(databaseSizeBeforeUpdate);
        Vendor testVendor = vendors.get(vendors.size() - 1);
        assertThat(testVendor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVendor.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testVendor.getPhoneNumbers()).isEqualTo(UPDATED_PHONE_NUMBERS);
    }

    @Test
    @Transactional
    public void deleteVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        int databaseSizeBeforeDelete = vendorRepository.findAll().size();

        // Get the vendor
        restVendorMockMvc.perform(delete("/api/vendors/{id}", vendor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Vendor> vendors = vendorRepository.findAll();
        assertThat(vendors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
