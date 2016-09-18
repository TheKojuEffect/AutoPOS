package io.koju.autopos.party.web;

import io.koju.autopos.Application;
import io.koju.autopos.accounting.domain.Ledger;
import io.koju.autopos.accounting.web.LedgerResource;
import io.koju.autopos.accounting.repo.LedgerRepository;
import io.koju.autopos.accounting.service.LedgerService;
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
public class LedgerResourceIntTest {


    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(0);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(1);
    private static final String DEFAULT_REMARKS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private LedgerRepository ledgerRepository;

    @Inject
    private LedgerService ledgerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLedgerMockMvc;

    private Ledger ledger;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LedgerResource ledgerResource = new LedgerResource();
        ReflectionTestUtils.setField(ledgerResource, "ledgerService", ledgerService);
        this.restLedgerMockMvc = MockMvcBuilders.standaloneSetup(ledgerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ledger = new Ledger();
        ledger.setBalance(DEFAULT_BALANCE);
        ledger.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createLedger() throws Exception {
        int databaseSizeBeforeCreate = ledgerRepository.findAll().size();

        // Create the Ledger

        restLedgerMockMvc.perform(post("/api/ledgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ledger)))
                .andExpect(status().isCreated());

        // Validate the Ledger in the database
        List<Ledger> ledgers = ledgerRepository.findAll();
        assertThat(ledgers).hasSize(databaseSizeBeforeCreate + 1);
        Ledger testLedger = ledgers.get(ledgers.size() - 1);
        assertThat(testLedger.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testLedger.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerRepository.findAll().size();
        // set the field null
        ledger.setBalance(null);

        // Create the Ledger, which fails.

        restLedgerMockMvc.perform(post("/api/ledgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ledger)))
                .andExpect(status().isBadRequest());

        List<Ledger> ledgers = ledgerRepository.findAll();
        assertThat(ledgers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLedgers() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgers
        restLedgerMockMvc.perform(get("/api/ledgers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ledger.getId().intValue())))
                .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getLedger() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get the ledger
        restLedgerMockMvc.perform(get("/api/ledgers/{id}", ledger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ledger.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLedger() throws Exception {
        // Get the ledger
        restLedgerMockMvc.perform(get("/api/ledgers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLedger() throws Exception {
        // Initialize the database
        ledgerService.save(ledger);

        int databaseSizeBeforeUpdate = ledgerRepository.findAll().size();

        // Update the ledger
        Ledger updatedLedger = new Ledger();
        updatedLedger.setId(ledger.getId());
        updatedLedger.setBalance(UPDATED_BALANCE);
        updatedLedger.setRemarks(UPDATED_REMARKS);

        restLedgerMockMvc.perform(put("/api/ledgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLedger)))
                .andExpect(status().isOk());

        // Validate the Ledger in the database
        List<Ledger> ledgers = ledgerRepository.findAll();
        assertThat(ledgers).hasSize(databaseSizeBeforeUpdate);
        Ledger testLedger = ledgers.get(ledgers.size() - 1);
        assertThat(testLedger.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testLedger.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteLedger() throws Exception {
        // Initialize the database
        ledgerService.save(ledger);

        int databaseSizeBeforeDelete = ledgerRepository.findAll().size();

        // Get the ledger
        restLedgerMockMvc.perform(delete("/api/ledgers/{id}", ledger.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ledger> ledgers = ledgerRepository.findAll();
        assertThat(ledgers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
