package io.koju.autopos.accounting.web;

import io.koju.autopos.Application;
import io.koju.autopos.accounting.domain.DayBookEntry;
import io.koju.autopos.accounting.service.DayBookEntryRepository;
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


/**
 * Test class for the DayBookEntryResource REST controller.
 *
 * @see DayBookEntryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DayBookEntryResourceIntTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_INCOMING_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_INCOMING_AMOUNT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_OUTGOING_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_OUTGOING_AMOUNT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_MISC_EXPENSES = new BigDecimal(0);
    private static final BigDecimal UPDATED_MISC_EXPENSES = new BigDecimal(1);

    @Inject
    private DayBookEntryRepository dayBookEntryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDayBookEntryMockMvc;

    private DayBookEntry dayBookEntry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DayBookEntryResource dayBookEntryResource = new DayBookEntryResource();
        ReflectionTestUtils.setField(dayBookEntryResource, "dayBookEntryRepository", dayBookEntryRepository);
        this.restDayBookEntryMockMvc = MockMvcBuilders.standaloneSetup(dayBookEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dayBookEntry = new DayBookEntry();
        dayBookEntry.setDate(DEFAULT_DATE);
        dayBookEntry.setIncomingAmount(DEFAULT_INCOMING_AMOUNT);
        dayBookEntry.setOutgoingAmount(DEFAULT_OUTGOING_AMOUNT);
        dayBookEntry.setMiscExpenses(DEFAULT_MISC_EXPENSES);
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
        List<DayBookEntry> dayBookEntries = dayBookEntryRepository.findAll();
        assertThat(dayBookEntries).hasSize(databaseSizeBeforeCreate + 1);
        DayBookEntry testDayBookEntry = dayBookEntries.get(dayBookEntries.size() - 1);
        assertThat(testDayBookEntry.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDayBookEntry.getIncomingAmount()).isEqualTo(DEFAULT_INCOMING_AMOUNT);
        assertThat(testDayBookEntry.getOutgoingAmount()).isEqualTo(DEFAULT_OUTGOING_AMOUNT);
        assertThat(testDayBookEntry.getMiscExpenses()).isEqualTo(DEFAULT_MISC_EXPENSES);
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

        List<DayBookEntry> dayBookEntries = dayBookEntryRepository.findAll();
        assertThat(dayBookEntries).hasSize(databaseSizeBeforeTest);
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

        List<DayBookEntry> dayBookEntries = dayBookEntryRepository.findAll();
        assertThat(dayBookEntries).hasSize(databaseSizeBeforeTest);
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

        List<DayBookEntry> dayBookEntries = dayBookEntryRepository.findAll();
        assertThat(dayBookEntries).hasSize(databaseSizeBeforeTest);
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

        List<DayBookEntry> dayBookEntries = dayBookEntryRepository.findAll();
        assertThat(dayBookEntries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDayBookEntries() throws Exception {
        // Initialize the database
        dayBookEntryRepository.saveAndFlush(dayBookEntry);

        // Get all the dayBookEntries
        restDayBookEntryMockMvc.perform(get("/api/day-book-entries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dayBookEntry.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].incomingAmount").value(hasItem(DEFAULT_INCOMING_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].outgoingAmount").value(hasItem(DEFAULT_OUTGOING_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].miscExpenses").value(hasItem(DEFAULT_MISC_EXPENSES.intValue())));
    }

    @Test
    @Transactional
    public void getDayBookEntry() throws Exception {
        // Initialize the database
        dayBookEntryRepository.saveAndFlush(dayBookEntry);

        // Get the dayBookEntry
        restDayBookEntryMockMvc.perform(get("/api/day-book-entries/{id}", dayBookEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dayBookEntry.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.incomingAmount").value(DEFAULT_INCOMING_AMOUNT.intValue()))
            .andExpect(jsonPath("$.outgoingAmount").value(DEFAULT_OUTGOING_AMOUNT.intValue()))
            .andExpect(jsonPath("$.miscExpenses").value(DEFAULT_MISC_EXPENSES.intValue()));
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
        dayBookEntryRepository.saveAndFlush(dayBookEntry);
        int databaseSizeBeforeUpdate = dayBookEntryRepository.findAll().size();

        // Update the dayBookEntry
        DayBookEntry updatedDayBookEntry = new DayBookEntry();
        updatedDayBookEntry.setId(dayBookEntry.getId());
        updatedDayBookEntry.setDate(UPDATED_DATE);
        updatedDayBookEntry.setIncomingAmount(UPDATED_INCOMING_AMOUNT);
        updatedDayBookEntry.setOutgoingAmount(UPDATED_OUTGOING_AMOUNT);
        updatedDayBookEntry.setMiscExpenses(UPDATED_MISC_EXPENSES);

        restDayBookEntryMockMvc.perform(put("/api/day-book-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDayBookEntry)))
                .andExpect(status().isOk());

        // Validate the DayBookEntry in the database
        List<DayBookEntry> dayBookEntries = dayBookEntryRepository.findAll();
        assertThat(dayBookEntries).hasSize(databaseSizeBeforeUpdate);
        DayBookEntry testDayBookEntry = dayBookEntries.get(dayBookEntries.size() - 1);
        assertThat(testDayBookEntry.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDayBookEntry.getIncomingAmount()).isEqualTo(UPDATED_INCOMING_AMOUNT);
        assertThat(testDayBookEntry.getOutgoingAmount()).isEqualTo(UPDATED_OUTGOING_AMOUNT);
        assertThat(testDayBookEntry.getMiscExpenses()).isEqualTo(UPDATED_MISC_EXPENSES);
    }

    @Test
    @Transactional
    public void deleteDayBookEntry() throws Exception {
        // Initialize the database
        dayBookEntryRepository.saveAndFlush(dayBookEntry);
        int databaseSizeBeforeDelete = dayBookEntryRepository.findAll().size();

        // Get the dayBookEntry
        restDayBookEntryMockMvc.perform(delete("/api/day-book-entries/{id}", dayBookEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DayBookEntry> dayBookEntries = dayBookEntryRepository.findAll();
        assertThat(dayBookEntries).hasSize(databaseSizeBeforeDelete - 1);
    }
}