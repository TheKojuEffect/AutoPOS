package io.koju.autopos.trade.web;

import io.koju.autopos.Application;
import io.koju.autopos.trade.domain.Receipt;
import io.koju.autopos.trade.service.ReceiptRepository;
import io.koju.autopos.web.rest.TestUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class ReceiptResourceIntTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(1);
    private static final String DEFAULT_RECEIVED_BY = "AA";
    private static final String UPDATED_RECEIVED_BY = "BB";
    private static final String DEFAULT_REMARKS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restReceiptMockMvc;

    private Receipt receipt;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReceiptResource receiptResource = new ReceiptResource();
        ReflectionTestUtils.setField(receiptResource, "receiptRepository", receiptRepository);
        this.restReceiptMockMvc = MockMvcBuilders.standaloneSetup(receiptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        receipt = new Receipt();
        receipt.setDate(DEFAULT_DATE);
        receipt.setAmount(DEFAULT_AMOUNT);
        receipt.setReceivedBy(DEFAULT_RECEIVED_BY);
        receipt.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createReceipt() throws Exception {
        int databaseSizeBeforeCreate = receiptRepository.findAll().size();

        // Create the Receipt

        restReceiptMockMvc.perform(post("/api/receipts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(receipt)))
                .andExpect(status().isCreated());

        // Validate the Receipt in the database
        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeCreate + 1);
        Receipt testReceipt = receipts.get(receipts.size() - 1);
        assertThat(testReceipt.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testReceipt.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testReceipt.getReceivedBy()).isEqualTo(DEFAULT_RECEIVED_BY);
        assertThat(testReceipt.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setDate(null);

        // Create the Receipt, which fails.

        restReceiptMockMvc.perform(post("/api/receipts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(receipt)))
                .andExpect(status().isBadRequest());

        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setAmount(null);

        // Create the Receipt, which fails.

        restReceiptMockMvc.perform(post("/api/receipts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(receipt)))
                .andExpect(status().isBadRequest());

        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceivedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setReceivedBy(null);

        // Create the Receipt, which fails.

        restReceiptMockMvc.perform(post("/api/receipts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(receipt)))
                .andExpect(status().isBadRequest());

        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReceipts() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);

        // Get all the receipts
        restReceiptMockMvc.perform(get("/api/receipts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(receipt.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].receivedBy").value(hasItem(DEFAULT_RECEIVED_BY.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);

        // Get the receipt
        restReceiptMockMvc.perform(get("/api/receipts/{id}", receipt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(receipt.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.receivedBy").value(DEFAULT_RECEIVED_BY.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReceipt() throws Exception {
        // Get the receipt
        restReceiptMockMvc.perform(get("/api/receipts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);
        int databaseSizeBeforeUpdate = receiptRepository.findAll().size();

        // Update the receipt
        Receipt updatedReceipt = new Receipt();
        updatedReceipt.setId(receipt.getId());
        updatedReceipt.setDate(UPDATED_DATE);
        updatedReceipt.setAmount(UPDATED_AMOUNT);
        updatedReceipt.setReceivedBy(UPDATED_RECEIVED_BY);
        updatedReceipt.setRemarks(UPDATED_REMARKS);

        restReceiptMockMvc.perform(put("/api/receipts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedReceipt)))
                .andExpect(status().isOk());

        // Validate the Receipt in the database
        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeUpdate);
        Receipt testReceipt = receipts.get(receipts.size() - 1);
        assertThat(testReceipt.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testReceipt.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testReceipt.getReceivedBy()).isEqualTo(UPDATED_RECEIVED_BY);
        assertThat(testReceipt.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);
        int databaseSizeBeforeDelete = receiptRepository.findAll().size();

        // Get the receipt
        restReceiptMockMvc.perform(delete("/api/receipts/{id}", receipt.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
