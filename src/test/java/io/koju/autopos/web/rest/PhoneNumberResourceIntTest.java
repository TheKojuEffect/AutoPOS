package io.koju.autopos.web.rest;

import io.koju.autopos.Application;
import io.koju.autopos.domain.PhoneNumber;
import io.koju.autopos.repository.PhoneNumberRepository;
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
 * @see PhoneNumberResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PhoneNumberResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBB";

    @Inject
    private PhoneNumberRepository phoneNumberRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPhoneNumberMockMvc;

    private PhoneNumber phoneNumber;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PhoneNumberResource phoneNumberResource = new PhoneNumberResource();
        ReflectionTestUtils.setField(phoneNumberResource, "phoneNumberRepository", phoneNumberRepository);
        this.restPhoneNumberMockMvc = MockMvcBuilders.standaloneSetup(phoneNumberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        phoneNumber = new PhoneNumber();
        phoneNumber.setNumber(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createPhoneNumber() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberRepository.findAll().size();

        // Create the PhoneNumber

        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
                .andExpect(status().isCreated());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll();
        assertThat(phoneNumbers).hasSize(databaseSizeBeforeCreate + 1);
        PhoneNumber testPhoneNumber = phoneNumbers.get(phoneNumbers.size() - 1);
        assertThat(testPhoneNumber.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberRepository.findAll().size();
        // set the field null
        phoneNumber.setNumber(null);

        // Create the PhoneNumber, which fails.

        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
                .andExpect(status().isBadRequest());

        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll();
        assertThat(phoneNumbers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhoneNumbers() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get all the phoneNumbers
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(phoneNumber.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getPhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers/{id}", phoneNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(phoneNumber.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPhoneNumber() throws Exception {
        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);
        int databaseSizeBeforeUpdate = phoneNumberRepository.findAll().size();

        // Update the phoneNumber
        PhoneNumber updatedPhoneNumber = new PhoneNumber();
        updatedPhoneNumber.setId(phoneNumber.getId());
        updatedPhoneNumber.setNumber(UPDATED_NUMBER);

        restPhoneNumberMockMvc.perform(put("/api/phone-numbers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPhoneNumber)))
                .andExpect(status().isOk());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll();
        assertThat(phoneNumbers).hasSize(databaseSizeBeforeUpdate);
        PhoneNumber testPhoneNumber = phoneNumbers.get(phoneNumbers.size() - 1);
        assertThat(testPhoneNumber.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void deletePhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);
        int databaseSizeBeforeDelete = phoneNumberRepository.findAll().size();

        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(delete("/api/phone-numbers/{id}", phoneNumber.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll();
        assertThat(phoneNumbers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
