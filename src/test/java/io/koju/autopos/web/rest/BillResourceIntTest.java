package io.koju.autopos.web.rest;

import io.koju.autopos.Application;
import io.koju.autopos.domain.Bill;
import io.koju.autopos.repository.BillRepository;
import io.koju.autopos.service.BillService;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
 * Test class for the BillResource REST controller.
 *
 * @see BillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BillResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);

    private static final BigDecimal DEFAULT_SUB_TOTAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_SUB_TOTAL = new BigDecimal(1);

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TAXABLE_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TAXABLE_AMOUNT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(0);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(1);

    private static final BigDecimal DEFAULT_GRAND_TOTAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_GRAND_TOTAL = new BigDecimal(1);
    private static final String DEFAULT_CLIENT = "AA";
    private static final String UPDATED_CLIENT = "BB";
    private static final String DEFAULT_REMARKS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_ISSUED_BY = "AA";
    private static final String UPDATED_ISSUED_BY = "BB";

    @Inject
    private BillRepository billRepository;

    @Inject
    private BillService billService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBillMockMvc;

    private Bill bill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BillResource billResource = new BillResource();
        ReflectionTestUtils.setField(billResource, "billService", billService);
        this.restBillMockMvc = MockMvcBuilders.standaloneSetup(billResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bill = new Bill();
        bill.setDate(DEFAULT_DATE);
        bill.setSubTotal(DEFAULT_SUB_TOTAL);
        bill.setDiscount(DEFAULT_DISCOUNT);
        bill.setTaxableAmount(DEFAULT_TAXABLE_AMOUNT);
        bill.setTax(DEFAULT_TAX);
        bill.setGrandTotal(DEFAULT_GRAND_TOTAL);
        bill.setClient(DEFAULT_CLIENT);
        bill.setRemarks(DEFAULT_REMARKS);
        bill.setIssuedBy(DEFAULT_ISSUED_BY);
    }

    @Test
    @Transactional
    public void createBill() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isCreated());

        // Validate the Bill in the database
        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeCreate + 1);
        Bill testBill = bills.get(bills.size() - 1);
        assertThat(testBill.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBill.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
        assertThat(testBill.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testBill.getTaxableAmount()).isEqualTo(DEFAULT_TAXABLE_AMOUNT);
        assertThat(testBill.getTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testBill.getGrandTotal()).isEqualTo(DEFAULT_GRAND_TOTAL);
        assertThat(testBill.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testBill.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testBill.getIssuedBy()).isEqualTo(DEFAULT_ISSUED_BY);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setDate(null);

        // Create the Bill, which fails.

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setSubTotal(null);

        // Create the Bill, which fails.

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setDiscount(null);

        // Create the Bill, which fails.

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxableAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setTaxableAmount(null);

        // Create the Bill, which fails.

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setTax(null);

        // Create the Bill, which fails.

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrandTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setGrandTotal(null);

        // Create the Bill, which fails.

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setClient(null);

        // Create the Bill, which fails.

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssuedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setIssuedBy(null);

        // Create the Bill, which fails.

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBills() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the bills
        restBillMockMvc.perform(get("/api/bills?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.intValue())))
                .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].taxableAmount").value(hasItem(DEFAULT_TAXABLE_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.intValue())))
                .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())))
                .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].issuedBy").value(hasItem(DEFAULT_ISSUED_BY.toString())));
    }

    @Test
    @Transactional
    public void getBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bill.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.taxableAmount").value(DEFAULT_TAXABLE_AMOUNT.intValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.intValue()))
            .andExpect(jsonPath("$.grandTotal").value(DEFAULT_GRAND_TOTAL.intValue()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.issuedBy").value(DEFAULT_ISSUED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBill() throws Exception {
        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBill() throws Exception {
        // Initialize the database
        billService.save(bill);

        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill
        Bill updatedBill = new Bill();
        updatedBill.setId(bill.getId());
        updatedBill.setDate(UPDATED_DATE);
        updatedBill.setSubTotal(UPDATED_SUB_TOTAL);
        updatedBill.setDiscount(UPDATED_DISCOUNT);
        updatedBill.setTaxableAmount(UPDATED_TAXABLE_AMOUNT);
        updatedBill.setTax(UPDATED_TAX);
        updatedBill.setGrandTotal(UPDATED_GRAND_TOTAL);
        updatedBill.setClient(UPDATED_CLIENT);
        updatedBill.setRemarks(UPDATED_REMARKS);
        updatedBill.setIssuedBy(UPDATED_ISSUED_BY);

        restBillMockMvc.perform(put("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBill)))
                .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = bills.get(bills.size() - 1);
        assertThat(testBill.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBill.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
        assertThat(testBill.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testBill.getTaxableAmount()).isEqualTo(UPDATED_TAXABLE_AMOUNT);
        assertThat(testBill.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testBill.getGrandTotal()).isEqualTo(UPDATED_GRAND_TOTAL);
        assertThat(testBill.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testBill.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testBill.getIssuedBy()).isEqualTo(UPDATED_ISSUED_BY);
    }

    @Test
    @Transactional
    public void deleteBill() throws Exception {
        // Initialize the database
        billService.save(bill);

        int databaseSizeBeforeDelete = billRepository.findAll().size();

        // Get the bill
        restBillMockMvc.perform(delete("/api/bills/{id}", bill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
