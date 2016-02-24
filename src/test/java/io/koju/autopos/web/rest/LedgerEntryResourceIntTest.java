package io.koju.autopos.web.rest;

import io.koju.autopos.Application;
import io.koju.autopos.domain.LedgerEntry;
import io.koju.autopos.repository.LedgerEntryRepository;

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
 * Test class for the LedgerEntryResource REST controller.
 *
 * @see LedgerEntryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LedgerEntryResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);
    private static final String DEFAULT_PARTICULAR = "AA";
    private static final String UPDATED_PARTICULAR = "BB";
    private static final String DEFAULT_FOLIO = "A";
    private static final String UPDATED_FOLIO = "B";

    private static final BigDecimal DEFAULT_DEBIT = new BigDecimal(0);
    private static final BigDecimal UPDATED_DEBIT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_CREDIT = new BigDecimal(0);
    private static final BigDecimal UPDATED_CREDIT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(0);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(1);
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    @Inject
    private LedgerEntryRepository ledgerEntryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLedgerEntryMockMvc;

    private LedgerEntry ledgerEntry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LedgerEntryResource ledgerEntryResource = new LedgerEntryResource();
        ReflectionTestUtils.setField(ledgerEntryResource, "ledgerEntryRepository", ledgerEntryRepository);
        this.restLedgerEntryMockMvc = MockMvcBuilders.standaloneSetup(ledgerEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ledgerEntry = new LedgerEntry();
        ledgerEntry.setDate(DEFAULT_DATE);
        ledgerEntry.setParticular(DEFAULT_PARTICULAR);
        ledgerEntry.setFolio(DEFAULT_FOLIO);
        ledgerEntry.setDebit(DEFAULT_DEBIT);
        ledgerEntry.setCredit(DEFAULT_CREDIT);
        ledgerEntry.setBalance(DEFAULT_BALANCE);
        ledgerEntry.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createLedgerEntry() throws Exception {
        int databaseSizeBeforeCreate = ledgerEntryRepository.findAll().size();

        // Create the LedgerEntry

        restLedgerEntryMockMvc.perform(post("/api/ledgerEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ledgerEntry)))
                .andExpect(status().isCreated());

        // Validate the LedgerEntry in the database
        List<LedgerEntry> ledgerEntrys = ledgerEntryRepository.findAll();
        assertThat(ledgerEntrys).hasSize(databaseSizeBeforeCreate + 1);
        LedgerEntry testLedgerEntry = ledgerEntrys.get(ledgerEntrys.size() - 1);
        assertThat(testLedgerEntry.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testLedgerEntry.getParticular()).isEqualTo(DEFAULT_PARTICULAR);
        assertThat(testLedgerEntry.getFolio()).isEqualTo(DEFAULT_FOLIO);
        assertThat(testLedgerEntry.getDebit()).isEqualTo(DEFAULT_DEBIT);
        assertThat(testLedgerEntry.getCredit()).isEqualTo(DEFAULT_CREDIT);
        assertThat(testLedgerEntry.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testLedgerEntry.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerEntryRepository.findAll().size();
        // set the field null
        ledgerEntry.setDate(null);

        // Create the LedgerEntry, which fails.

        restLedgerEntryMockMvc.perform(post("/api/ledgerEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ledgerEntry)))
                .andExpect(status().isBadRequest());

        List<LedgerEntry> ledgerEntrys = ledgerEntryRepository.findAll();
        assertThat(ledgerEntrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParticularIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerEntryRepository.findAll().size();
        // set the field null
        ledgerEntry.setParticular(null);

        // Create the LedgerEntry, which fails.

        restLedgerEntryMockMvc.perform(post("/api/ledgerEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ledgerEntry)))
                .andExpect(status().isBadRequest());

        List<LedgerEntry> ledgerEntrys = ledgerEntryRepository.findAll();
        assertThat(ledgerEntrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDebitIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerEntryRepository.findAll().size();
        // set the field null
        ledgerEntry.setDebit(null);

        // Create the LedgerEntry, which fails.

        restLedgerEntryMockMvc.perform(post("/api/ledgerEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ledgerEntry)))
                .andExpect(status().isBadRequest());

        List<LedgerEntry> ledgerEntrys = ledgerEntryRepository.findAll();
        assertThat(ledgerEntrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerEntryRepository.findAll().size();
        // set the field null
        ledgerEntry.setCredit(null);

        // Create the LedgerEntry, which fails.

        restLedgerEntryMockMvc.perform(post("/api/ledgerEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ledgerEntry)))
                .andExpect(status().isBadRequest());

        List<LedgerEntry> ledgerEntrys = ledgerEntryRepository.findAll();
        assertThat(ledgerEntrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerEntryRepository.findAll().size();
        // set the field null
        ledgerEntry.setBalance(null);

        // Create the LedgerEntry, which fails.

        restLedgerEntryMockMvc.perform(post("/api/ledgerEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ledgerEntry)))
                .andExpect(status().isBadRequest());

        List<LedgerEntry> ledgerEntrys = ledgerEntryRepository.findAll();
        assertThat(ledgerEntrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLedgerEntrys() throws Exception {
        // Initialize the database
        ledgerEntryRepository.saveAndFlush(ledgerEntry);

        // Get all the ledgerEntrys
        restLedgerEntryMockMvc.perform(get("/api/ledgerEntrys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ledgerEntry.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].particular").value(hasItem(DEFAULT_PARTICULAR.toString())))
                .andExpect(jsonPath("$.[*].folio").value(hasItem(DEFAULT_FOLIO.toString())))
                .andExpect(jsonPath("$.[*].debit").value(hasItem(DEFAULT_DEBIT.intValue())))
                .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT.intValue())))
                .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getLedgerEntry() throws Exception {
        // Initialize the database
        ledgerEntryRepository.saveAndFlush(ledgerEntry);

        // Get the ledgerEntry
        restLedgerEntryMockMvc.perform(get("/api/ledgerEntrys/{id}", ledgerEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ledgerEntry.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.particular").value(DEFAULT_PARTICULAR.toString()))
            .andExpect(jsonPath("$.folio").value(DEFAULT_FOLIO.toString()))
            .andExpect(jsonPath("$.debit").value(DEFAULT_DEBIT.intValue()))
            .andExpect(jsonPath("$.credit").value(DEFAULT_CREDIT.intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLedgerEntry() throws Exception {
        // Get the ledgerEntry
        restLedgerEntryMockMvc.perform(get("/api/ledgerEntrys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLedgerEntry() throws Exception {
        // Initialize the database
        ledgerEntryRepository.saveAndFlush(ledgerEntry);

		int databaseSizeBeforeUpdate = ledgerEntryRepository.findAll().size();

        // Update the ledgerEntry
        ledgerEntry.setDate(UPDATED_DATE);
        ledgerEntry.setParticular(UPDATED_PARTICULAR);
        ledgerEntry.setFolio(UPDATED_FOLIO);
        ledgerEntry.setDebit(UPDATED_DEBIT);
        ledgerEntry.setCredit(UPDATED_CREDIT);
        ledgerEntry.setBalance(UPDATED_BALANCE);
        ledgerEntry.setRemarks(UPDATED_REMARKS);

        restLedgerEntryMockMvc.perform(put("/api/ledgerEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ledgerEntry)))
                .andExpect(status().isOk());

        // Validate the LedgerEntry in the database
        List<LedgerEntry> ledgerEntrys = ledgerEntryRepository.findAll();
        assertThat(ledgerEntrys).hasSize(databaseSizeBeforeUpdate);
        LedgerEntry testLedgerEntry = ledgerEntrys.get(ledgerEntrys.size() - 1);
        assertThat(testLedgerEntry.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLedgerEntry.getParticular()).isEqualTo(UPDATED_PARTICULAR);
        assertThat(testLedgerEntry.getFolio()).isEqualTo(UPDATED_FOLIO);
        assertThat(testLedgerEntry.getDebit()).isEqualTo(UPDATED_DEBIT);
        assertThat(testLedgerEntry.getCredit()).isEqualTo(UPDATED_CREDIT);
        assertThat(testLedgerEntry.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testLedgerEntry.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteLedgerEntry() throws Exception {
        // Initialize the database
        ledgerEntryRepository.saveAndFlush(ledgerEntry);

		int databaseSizeBeforeDelete = ledgerEntryRepository.findAll().size();

        // Get the ledgerEntry
        restLedgerEntryMockMvc.perform(delete("/api/ledgerEntrys/{id}", ledgerEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LedgerEntry> ledgerEntrys = ledgerEntryRepository.findAll();
        assertThat(ledgerEntrys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
