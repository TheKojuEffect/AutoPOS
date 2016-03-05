package io.koju.autopos.web.rest;

import io.koju.autopos.Application;
import io.koju.autopos.catalog.domain.PriceHistory;
import io.koju.autopos.catalog.web.PriceHistoryResource;
import io.koju.autopos.catalog.service.PriceHistoryRepository;

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
 * Test class for the PriceHistoryResource REST controller.
 *
 * @see PriceHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PriceHistoryResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);

    private static final BigDecimal DEFAULT_MARKED_PRICE = new BigDecimal(20);
    private static final BigDecimal UPDATED_MARKED_PRICE = new BigDecimal(21);
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    @Inject
    private PriceHistoryRepository priceHistoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPriceHistoryMockMvc;

    private PriceHistory priceHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PriceHistoryResource priceHistoryResource = new PriceHistoryResource();
        ReflectionTestUtils.setField(priceHistoryResource, "priceHistoryRepository", priceHistoryRepository);
        this.restPriceHistoryMockMvc = MockMvcBuilders.standaloneSetup(priceHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        priceHistory = new PriceHistory();
        priceHistory.setDate(DEFAULT_DATE);
        priceHistory.setMarkedPrice(DEFAULT_MARKED_PRICE);
        priceHistory.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createPriceHistory() throws Exception {
        int databaseSizeBeforeCreate = priceHistoryRepository.findAll().size();

        // Create the PriceHistory

        restPriceHistoryMockMvc.perform(post("/api/priceHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceHistory)))
                .andExpect(status().isCreated());

        // Validate the PriceHistory in the database
        List<PriceHistory> priceHistorys = priceHistoryRepository.findAll();
        assertThat(priceHistorys).hasSize(databaseSizeBeforeCreate + 1);
        PriceHistory testPriceHistory = priceHistorys.get(priceHistorys.size() - 1);
        assertThat(testPriceHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPriceHistory.getMarkedPrice()).isEqualTo(DEFAULT_MARKED_PRICE);
        assertThat(testPriceHistory.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceHistoryRepository.findAll().size();
        // set the field null
        priceHistory.setDate(null);

        // Create the PriceHistory, which fails.

        restPriceHistoryMockMvc.perform(post("/api/priceHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceHistory)))
                .andExpect(status().isBadRequest());

        List<PriceHistory> priceHistorys = priceHistoryRepository.findAll();
        assertThat(priceHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarkedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceHistoryRepository.findAll().size();
        // set the field null
        priceHistory.setMarkedPrice(null);

        // Create the PriceHistory, which fails.

        restPriceHistoryMockMvc.perform(post("/api/priceHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceHistory)))
                .andExpect(status().isBadRequest());

        List<PriceHistory> priceHistorys = priceHistoryRepository.findAll();
        assertThat(priceHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceHistorys() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

        // Get all the priceHistorys
        restPriceHistoryMockMvc.perform(get("/api/priceHistorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(priceHistory.getId().intValue())))
//                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].markedPrice").value(hasItem(DEFAULT_MARKED_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getPriceHistory() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

        // Get the priceHistory
        restPriceHistoryMockMvc.perform(get("/api/priceHistorys/{id}", priceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(priceHistory.getId().intValue()))
//            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.markedPrice").value(DEFAULT_MARKED_PRICE.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceHistory() throws Exception {
        // Get the priceHistory
        restPriceHistoryMockMvc.perform(get("/api/priceHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceHistory() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

		int databaseSizeBeforeUpdate = priceHistoryRepository.findAll().size();

        // Update the priceHistory
        priceHistory.setDate(UPDATED_DATE);
        priceHistory.setMarkedPrice(UPDATED_MARKED_PRICE);
        priceHistory.setRemarks(UPDATED_REMARKS);

        restPriceHistoryMockMvc.perform(put("/api/priceHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceHistory)))
                .andExpect(status().isOk());

        // Validate the PriceHistory in the database
        List<PriceHistory> priceHistorys = priceHistoryRepository.findAll();
        assertThat(priceHistorys).hasSize(databaseSizeBeforeUpdate);
        PriceHistory testPriceHistory = priceHistorys.get(priceHistorys.size() - 1);
        assertThat(testPriceHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPriceHistory.getMarkedPrice()).isEqualTo(UPDATED_MARKED_PRICE);
        assertThat(testPriceHistory.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deletePriceHistory() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

		int databaseSizeBeforeDelete = priceHistoryRepository.findAll().size();

        // Get the priceHistory
        restPriceHistoryMockMvc.perform(delete("/api/priceHistorys/{id}", priceHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceHistory> priceHistorys = priceHistoryRepository.findAll();
        assertThat(priceHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
