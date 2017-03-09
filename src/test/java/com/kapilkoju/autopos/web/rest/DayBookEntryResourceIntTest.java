package com.kapilkoju.autopos.web.rest;

import com.kapilkoju.autopos.AutoPosApp;

import com.kapilkoju.autopos.domain.DayBookEntry;
import com.kapilkoju.autopos.repository.DayBookEntryRepository;
import com.kapilkoju.autopos.service.DayBookEntryService;
import com.kapilkoju.autopos.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DayBookEntryResource REST controller.
 *
 * @see DayBookEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoPosApp.class)
public class DayBookEntryResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_INCOMING_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_INCOMING_AMOUNT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_OUTGOING_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_OUTGOING_AMOUNT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_MISC_EXPENSES = new BigDecimal(0);
    private static final BigDecimal UPDATED_MISC_EXPENSES = new BigDecimal(1);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private DayBookEntryRepository dayBookEntryRepository;

    @Autowired
    private DayBookEntryService dayBookEntryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDayBookEntryMockMvc;

    private DayBookEntry dayBookEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DayBookEntryResource dayBookEntryResource = new DayBookEntryResource(dayBookEntryService);
        this.restDayBookEntryMockMvc = MockMvcBuilders.standaloneSetup(dayBookEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DayBookEntry createEntity(EntityManager em) {
        DayBookEntry dayBookEntry = new DayBookEntry()
                .date(DEFAULT_DATE)
                .incomingAmount(DEFAULT_INCOMING_AMOUNT)
                .outgoingAmount(DEFAULT_OUTGOING_AMOUNT)
                .miscExpenses(DEFAULT_MISC_EXPENSES)
                .remarks(DEFAULT_REMARKS);
        return dayBookEntry;
    }

    @Before
    public void initTest() {
        dayBookEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createDayBookEntry() throws Exception {
        int databaseSizeBeforeCreate = dayBookEntryRepository.findAll().size();

        // Create the DayBookEntry

        restDayBookEntryMockMvc.perform(post("/api/day-book-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayBookEntry)))
            .andExpect(status().isCreated());

        // Validate the DayBookEntry in the database
        List<DayBookEntry> dayBookEntryList = dayBookEntryRepository.findAll();
        assertThat(dayBookEntryList).hasSize(databaseSizeBeforeCreate + 1);
        DayBookEntry testDayBookEntry = dayBookEntryList.get(dayBookEntryList.size() - 1);
        assertThat(testDayBookEntry.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDayBookEntry.getIncomingAmount()).isEqualTo(DEFAULT_INCOMING_AMOUNT);
        assertThat(testDayBookEntry.getOutgoingAmount()).isEqualTo(DEFAULT_OUTGOING_AMOUNT);
        assertThat(testDayBookEntry.getMiscExpenses()).isEqualTo(DEFAULT_MISC_EXPENSES);
        assertThat(testDayBookEntry.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createDayBookEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dayBookEntryRepository.findAll().size();

        // Create the DayBookEntry with an existing ID
        DayBookEntry existingDayBookEntry = new DayBookEntry();
        existingDayBookEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDayBookEntryMockMvc.perform(post("/api/day-book-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDayBookEntry)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DayBookEntry> dayBookEntryList = dayBookEntryRepository.findAll();
        assertThat(dayBookEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayBookEntryRepository.findAll().size();
        // set the field null
        dayBookEntry.setDate(null);

        // Create the DayBookEntry, which fails.

        restDayBookEntryMockMvc.perform(post("/api/day-book-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayBookEntry)))
            .andExpect(status().isBadRequest());

        List<DayBookEntry> dayBookEntryList = dayBookEntryRepository.findAll();
        assertThat(dayBookEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncomingAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayBookEntryRepository.findAll().size();
        // set the field null
        dayBookEntry.setIncomingAmount(null);

        // Create the DayBookEntry, which fails.

        restDayBookEntryMockMvc.perform(post("/api/day-book-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayBookEntry)))
            .andExpect(status().isBadRequest());

        List<DayBookEntry> dayBookEntryList = dayBookEntryRepository.findAll();
        assertThat(dayBookEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOutgoingAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayBookEntryRepository.findAll().size();
        // set the field null
        dayBookEntry.setOutgoingAmount(null);

        // Create the DayBookEntry, which fails.

        restDayBookEntryMockMvc.perform(post("/api/day-book-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayBookEntry)))
            .andExpect(status().isBadRequest());

        List<DayBookEntry> dayBookEntryList = dayBookEntryRepository.findAll();
        assertThat(dayBookEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMiscExpensesIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayBookEntryRepository.findAll().size();
        // set the field null
        dayBookEntry.setMiscExpenses(null);

        // Create the DayBookEntry, which fails.

        restDayBookEntryMockMvc.perform(post("/api/day-book-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayBookEntry)))
            .andExpect(status().isBadRequest());

        List<DayBookEntry> dayBookEntryList = dayBookEntryRepository.findAll();
        assertThat(dayBookEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDayBookEntries() throws Exception {
        // Initialize the database
        dayBookEntryRepository.saveAndFlush(dayBookEntry);

        // Get all the dayBookEntryList
        restDayBookEntryMockMvc.perform(get("/api/day-book-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayBookEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].incomingAmount").value(hasItem(DEFAULT_INCOMING_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].outgoingAmount").value(hasItem(DEFAULT_OUTGOING_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].miscExpenses").value(hasItem(DEFAULT_MISC_EXPENSES.intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getDayBookEntry() throws Exception {
        // Initialize the database
        dayBookEntryRepository.saveAndFlush(dayBookEntry);

        // Get the dayBookEntry
        restDayBookEntryMockMvc.perform(get("/api/day-book-entries/{id}", dayBookEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dayBookEntry.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.incomingAmount").value(DEFAULT_INCOMING_AMOUNT.intValue()))
            .andExpect(jsonPath("$.outgoingAmount").value(DEFAULT_OUTGOING_AMOUNT.intValue()))
            .andExpect(jsonPath("$.miscExpenses").value(DEFAULT_MISC_EXPENSES.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDayBookEntry() throws Exception {
        // Get the dayBookEntry
        restDayBookEntryMockMvc.perform(get("/api/day-book-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDayBookEntry() throws Exception {
        // Initialize the database
        dayBookEntryService.save(dayBookEntry);

        int databaseSizeBeforeUpdate = dayBookEntryRepository.findAll().size();

        // Update the dayBookEntry
        DayBookEntry updatedDayBookEntry = dayBookEntryRepository.findOne(dayBookEntry.getId());
        updatedDayBookEntry
                .date(UPDATED_DATE)
                .incomingAmount(UPDATED_INCOMING_AMOUNT)
                .outgoingAmount(UPDATED_OUTGOING_AMOUNT)
                .miscExpenses(UPDATED_MISC_EXPENSES)
                .remarks(UPDATED_REMARKS);

        restDayBookEntryMockMvc.perform(put("/api/day-book-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDayBookEntry)))
            .andExpect(status().isOk());

        // Validate the DayBookEntry in the database
        List<DayBookEntry> dayBookEntryList = dayBookEntryRepository.findAll();
        assertThat(dayBookEntryList).hasSize(databaseSizeBeforeUpdate);
        DayBookEntry testDayBookEntry = dayBookEntryList.get(dayBookEntryList.size() - 1);
        assertThat(testDayBookEntry.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDayBookEntry.getIncomingAmount()).isEqualTo(UPDATED_INCOMING_AMOUNT);
        assertThat(testDayBookEntry.getOutgoingAmount()).isEqualTo(UPDATED_OUTGOING_AMOUNT);
        assertThat(testDayBookEntry.getMiscExpenses()).isEqualTo(UPDATED_MISC_EXPENSES);
        assertThat(testDayBookEntry.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingDayBookEntry() throws Exception {
        int databaseSizeBeforeUpdate = dayBookEntryRepository.findAll().size();

        // Create the DayBookEntry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDayBookEntryMockMvc.perform(put("/api/day-book-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayBookEntry)))
            .andExpect(status().isCreated());

        // Validate the DayBookEntry in the database
        List<DayBookEntry> dayBookEntryList = dayBookEntryRepository.findAll();
        assertThat(dayBookEntryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDayBookEntry() throws Exception {
        // Initialize the database
        dayBookEntryService.save(dayBookEntry);

        int databaseSizeBeforeDelete = dayBookEntryRepository.findAll().size();

        // Get the dayBookEntry
        restDayBookEntryMockMvc.perform(delete("/api/day-book-entries/{id}", dayBookEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DayBookEntry> dayBookEntryList = dayBookEntryRepository.findAll();
        assertThat(dayBookEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayBookEntry.class);
    }
}
