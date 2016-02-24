package io.koju.autopos.web.rest;

import io.koju.autopos.Application;
import io.koju.autopos.domain.BillItem;
import io.koju.autopos.repository.BillItemRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BillItemResource REST controller.
 *
 * @see BillItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BillItemResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(0);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(1);

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";
    private static final String DEFAULT_ISSUED_BY = "AA";
    private static final String UPDATED_ISSUED_BY = "BB";

    @Inject
    private BillItemRepository billItemRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBillItemMockMvc;

    private BillItem billItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BillItemResource billItemResource = new BillItemResource();
        ReflectionTestUtils.setField(billItemResource, "billItemRepository", billItemRepository);
        this.restBillItemMockMvc = MockMvcBuilders.standaloneSetup(billItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        billItem = new BillItem();
        billItem.setQuantity(DEFAULT_QUANTITY);
        billItem.setRate(DEFAULT_RATE);
        billItem.setAmount(DEFAULT_AMOUNT);
        billItem.setDate(DEFAULT_DATE);
        billItem.setRemarks(DEFAULT_REMARKS);
        billItem.setIssuedBy(DEFAULT_ISSUED_BY);
    }

    @Test
    @Transactional
    public void createBillItem() throws Exception {
        int databaseSizeBeforeCreate = billItemRepository.findAll().size();

        // Create the BillItem

        restBillItemMockMvc.perform(post("/api/billItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billItem)))
                .andExpect(status().isCreated());

        // Validate the BillItem in the database
        List<BillItem> billItems = billItemRepository.findAll();
        assertThat(billItems).hasSize(databaseSizeBeforeCreate + 1);
        BillItem testBillItem = billItems.get(billItems.size() - 1);
        assertThat(testBillItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBillItem.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testBillItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBillItem.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBillItem.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testBillItem.getIssuedBy()).isEqualTo(DEFAULT_ISSUED_BY);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = billItemRepository.findAll().size();
        // set the field null
        billItem.setQuantity(null);

        // Create the BillItem, which fails.

        restBillItemMockMvc.perform(post("/api/billItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billItem)))
                .andExpect(status().isBadRequest());

        List<BillItem> billItems = billItemRepository.findAll();
        assertThat(billItems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = billItemRepository.findAll().size();
        // set the field null
        billItem.setRate(null);

        // Create the BillItem, which fails.

        restBillItemMockMvc.perform(post("/api/billItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billItem)))
                .andExpect(status().isBadRequest());

        List<BillItem> billItems = billItemRepository.findAll();
        assertThat(billItems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = billItemRepository.findAll().size();
        // set the field null
        billItem.setAmount(null);

        // Create the BillItem, which fails.

        restBillItemMockMvc.perform(post("/api/billItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billItem)))
                .andExpect(status().isBadRequest());

        List<BillItem> billItems = billItemRepository.findAll();
        assertThat(billItems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = billItemRepository.findAll().size();
        // set the field null
        billItem.setDate(null);

        // Create the BillItem, which fails.

        restBillItemMockMvc.perform(post("/api/billItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billItem)))
                .andExpect(status().isBadRequest());

        List<BillItem> billItems = billItemRepository.findAll();
        assertThat(billItems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillItems() throws Exception {
        // Initialize the database
        billItemRepository.saveAndFlush(billItem);

        // Get all the billItems
        restBillItemMockMvc.perform(get("/api/billItems?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(billItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].issuedBy").value(hasItem(DEFAULT_ISSUED_BY.toString())));
    }

    @Test
    @Transactional
    public void getBillItem() throws Exception {
        // Initialize the database
        billItemRepository.saveAndFlush(billItem);

        // Get the billItem
        restBillItemMockMvc.perform(get("/api/billItems/{id}", billItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(billItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.issuedBy").value(DEFAULT_ISSUED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBillItem() throws Exception {
        // Get the billItem
        restBillItemMockMvc.perform(get("/api/billItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillItem() throws Exception {
        // Initialize the database
        billItemRepository.saveAndFlush(billItem);

		int databaseSizeBeforeUpdate = billItemRepository.findAll().size();

        // Update the billItem
        billItem.setQuantity(UPDATED_QUANTITY);
        billItem.setRate(UPDATED_RATE);
        billItem.setAmount(UPDATED_AMOUNT);
        billItem.setDate(UPDATED_DATE);
        billItem.setRemarks(UPDATED_REMARKS);
        billItem.setIssuedBy(UPDATED_ISSUED_BY);

        restBillItemMockMvc.perform(put("/api/billItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billItem)))
                .andExpect(status().isOk());

        // Validate the BillItem in the database
        List<BillItem> billItems = billItemRepository.findAll();
        assertThat(billItems).hasSize(databaseSizeBeforeUpdate);
        BillItem testBillItem = billItems.get(billItems.size() - 1);
        assertThat(testBillItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBillItem.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testBillItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBillItem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBillItem.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testBillItem.getIssuedBy()).isEqualTo(UPDATED_ISSUED_BY);
    }

    @Test
    @Transactional
    public void deleteBillItem() throws Exception {
        // Initialize the database
        billItemRepository.saveAndFlush(billItem);

		int databaseSizeBeforeDelete = billItemRepository.findAll().size();

        // Get the billItem
        restBillItemMockMvc.perform(delete("/api/billItems/{id}", billItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BillItem> billItems = billItemRepository.findAll();
        assertThat(billItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
