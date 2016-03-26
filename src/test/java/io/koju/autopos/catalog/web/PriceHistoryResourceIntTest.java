package io.koju.autopos.catalog.web;

import io.koju.autopos.Application;

import io.koju.autopos.catalog.domain.PriceHistory;
import io.koju.autopos.catalog.service.PriceHistoryRepository;
import io.koju.autopos.web.rest.TestUtil;
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

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);

    private static final BigDecimal DEFAULT_MARKED_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_MARKED_PRICE = new BigDecimal(1);
    private static final String DEFAULT_REMARKS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

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

        restPriceHistoryMockMvc.perform(post("/api/price-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceHistory)))
                .andExpect(status().isCreated());

        // Validate the PriceHistory in the database
        List<PriceHistory> priceHistories = priceHistoryRepository.findAll();
        assertThat(priceHistories).hasSize(databaseSizeBeforeCreate + 1);
        PriceHistory testPriceHistory = priceHistories.get(priceHistories.size() - 1);
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

        restPriceHistoryMockMvc.perform(post("/api/price-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceHistory)))
                .andExpect(status().isBadRequest());

        List<PriceHistory> priceHistories = priceHistoryRepository.findAll();
        assertThat(priceHistories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarkedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceHistoryRepository.findAll().size();
        // set the field null
        priceHistory.setMarkedPrice(null);

        // Create the PriceHistory, which fails.

        restPriceHistoryMockMvc.perform(post("/api/price-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(priceHistory)))
                .andExpect(status().isBadRequest());

        List<PriceHistory> priceHistories = priceHistoryRepository.findAll();
        assertThat(priceHistories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceHistories() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

        // Get all the priceHistories
        restPriceHistoryMockMvc.perform(get("/api/price-histories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(priceHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].markedPrice").value(hasItem(DEFAULT_MARKED_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getPriceHistory() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

        // Get the priceHistory
        restPriceHistoryMockMvc.perform(get("/api/price-histories/{id}", priceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(priceHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.markedPrice").value(DEFAULT_MARKED_PRICE.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceHistory() throws Exception {
        // Get the priceHistory
        restPriceHistoryMockMvc.perform(get("/api/price-histories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceHistory() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);
        int databaseSizeBeforeUpdate = priceHistoryRepository.findAll().size();

        // Update the priceHistory
        PriceHistory updatedPriceHistory = new PriceHistory();
        updatedPriceHistory.setId(priceHistory.getId());
        updatedPriceHistory.setDate(UPDATED_DATE);
        updatedPriceHistory.setMarkedPrice(UPDATED_MARKED_PRICE);
        updatedPriceHistory.setRemarks(UPDATED_REMARKS);

        restPriceHistoryMockMvc.perform(put("/api/price-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPriceHistory)))
                .andExpect(status().isOk());

        // Validate the PriceHistory in the database
        List<PriceHistory> priceHistories = priceHistoryRepository.findAll();
        assertThat(priceHistories).hasSize(databaseSizeBeforeUpdate);
        PriceHistory testPriceHistory = priceHistories.get(priceHistories.size() - 1);
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
        restPriceHistoryMockMvc.perform(delete("/api/price-histories/{id}", priceHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceHistory> priceHistories = priceHistoryRepository.findAll();
        assertThat(priceHistories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
