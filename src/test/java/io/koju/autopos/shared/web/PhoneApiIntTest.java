package io.koju.autopos.shared.web;

import io.koju.autopos.Application;
import io.koju.autopos.shared.domain.Phone;
import io.koju.autopos.shared.service.PhoneRepository;
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
 * Test class for the PhoneNumberResource REST controller.
 *
 * @see PhoneApi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PhoneApiIntTest {

    private static final String DEFAULT_NUMBER = "AAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBB";

    @Inject
    private PhoneRepository phoneRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPhoneNumberMockMvc;

    private Phone phone;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PhoneApi phoneApi = new PhoneApi(phoneRepository);
        ReflectionTestUtils.setField(phoneApi, "phoneNumberRepository", phoneRepository);
        this.restPhoneNumberMockMvc = MockMvcBuilders.standaloneSetup(phoneApi)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        phone = new Phone();
        phone.setNumber(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createPhoneNumber() throws Exception {
        int databaseSizeBeforeCreate = phoneRepository.findAll().size();

        // Create the Phone

        restPhoneNumberMockMvc.perform(post("/api/phones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(phone)))
                .andExpect(status().isCreated());

        // Validate the Phone in the database
        List<Phone> phones = phoneRepository.findAll();
        assertThat(phones).hasSize(databaseSizeBeforeCreate + 1);
        Phone testPhone = phones.get(phones.size() - 1);
        assertThat(testPhone.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneRepository.findAll().size();
        // set the field null
        phone.setNumber(null);

        // Create the Phone, which fails.

        restPhoneNumberMockMvc.perform(post("/api/phones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(phone)))
                .andExpect(status().isBadRequest());

        List<Phone> phones = phoneRepository.findAll();
        assertThat(phones).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhoneNumbers() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);

        // Get all the phoneNumbers
        restPhoneNumberMockMvc.perform(get("/api/phones?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(phone.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }

    @Test
    @Transactional
    public void getPhoneNumber() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);

        // Get the phone
        restPhoneNumberMockMvc.perform(get("/api/phones/{id}", phone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(phone.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingPhoneNumber() throws Exception {
        // Get the phone
        restPhoneNumberMockMvc.perform(get("/api/phones/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneNumber() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);
        int databaseSizeBeforeUpdate = phoneRepository.findAll().size();

        // Update the phone
        Phone updatedPhone = new Phone();
        updatedPhone.setId(phone.getId());
        updatedPhone.setNumber(UPDATED_NUMBER);

        restPhoneNumberMockMvc.perform(put("/api/phones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPhone)))
                .andExpect(status().isOk());

        // Validate the Phone in the database
        List<Phone> phones = phoneRepository.findAll();
        assertThat(phones).hasSize(databaseSizeBeforeUpdate);
        Phone testPhone = phones.get(phones.size() - 1);
        assertThat(testPhone.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void deletePhoneNumber() throws Exception {
        // Initialize the database
        phoneRepository.saveAndFlush(phone);
        int databaseSizeBeforeDelete = phoneRepository.findAll().size();

        // Get the phone
        restPhoneNumberMockMvc.perform(delete("/api/phones/{id}", phone.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Phone> phones = phoneRepository.findAll();
        assertThat(phones).hasSize(databaseSizeBeforeDelete - 1);
    }
}
