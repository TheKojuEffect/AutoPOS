package io.koju.autopos.web.rest;

import io.koju.autopos.Application;
import io.koju.autopos.domain.StockHistory;
import io.koju.autopos.repository.StockHistoryRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StockHistoryResource REST controller.
 *
 * @see StockHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StockHistoryResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    @Inject
    private StockHistoryRepository stockHistoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStockHistoryMockMvc;

    private StockHistory stockHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StockHistoryResource stockHistoryResource = new StockHistoryResource();
        ReflectionTestUtils.setField(stockHistoryResource, "stockHistoryRepository", stockHistoryRepository);
        this.restStockHistoryMockMvc = MockMvcBuilders.standaloneSetup(stockHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        stockHistory = new StockHistory();
        stockHistory.setDate(DEFAULT_DATE);
        stockHistory.setQuantity(DEFAULT_QUANTITY);
        stockHistory.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createStockHistory() throws Exception {
        int databaseSizeBeforeCreate = stockHistoryRepository.findAll().size();

        // Create the StockHistory

        restStockHistoryMockMvc.perform(post("/api/stockHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockHistory)))
                .andExpect(status().isCreated());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistorys = stockHistoryRepository.findAll();
        assertThat(stockHistorys).hasSize(databaseSizeBeforeCreate + 1);
        StockHistory testStockHistory = stockHistorys.get(stockHistorys.size() - 1);
        assertThat(testStockHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testStockHistory.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStockHistory.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockHistoryRepository.findAll().size();
        // set the field null
        stockHistory.setDate(null);

        // Create the StockHistory, which fails.

        restStockHistoryMockMvc.perform(post("/api/stockHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockHistory)))
                .andExpect(status().isBadRequest());

        List<StockHistory> stockHistorys = stockHistoryRepository.findAll();
        assertThat(stockHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockHistoryRepository.findAll().size();
        // set the field null
        stockHistory.setQuantity(null);

        // Create the StockHistory, which fails.

        restStockHistoryMockMvc.perform(post("/api/stockHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockHistory)))
                .andExpect(status().isBadRequest());

        List<StockHistory> stockHistorys = stockHistoryRepository.findAll();
        assertThat(stockHistorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockHistorys() throws Exception {
        // Initialize the database
        stockHistoryRepository.saveAndFlush(stockHistory);

        // Get all the stockHistorys
        restStockHistoryMockMvc.perform(get("/api/stockHistorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stockHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getStockHistory() throws Exception {
        // Initialize the database
        stockHistoryRepository.saveAndFlush(stockHistory);

        // Get the stockHistory
        restStockHistoryMockMvc.perform(get("/api/stockHistorys/{id}", stockHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stockHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStockHistory() throws Exception {
        // Get the stockHistory
        restStockHistoryMockMvc.perform(get("/api/stockHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockHistory() throws Exception {
        // Initialize the database
        stockHistoryRepository.saveAndFlush(stockHistory);

		int databaseSizeBeforeUpdate = stockHistoryRepository.findAll().size();

        // Update the stockHistory
        stockHistory.setDate(UPDATED_DATE);
        stockHistory.setQuantity(UPDATED_QUANTITY);
        stockHistory.setRemarks(UPDATED_REMARKS);

        restStockHistoryMockMvc.perform(put("/api/stockHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockHistory)))
                .andExpect(status().isOk());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistorys = stockHistoryRepository.findAll();
        assertThat(stockHistorys).hasSize(databaseSizeBeforeUpdate);
        StockHistory testStockHistory = stockHistorys.get(stockHistorys.size() - 1);
        assertThat(testStockHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testStockHistory.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStockHistory.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteStockHistory() throws Exception {
        // Initialize the database
        stockHistoryRepository.saveAndFlush(stockHistory);

		int databaseSizeBeforeDelete = stockHistoryRepository.findAll().size();

        // Get the stockHistory
        restStockHistoryMockMvc.perform(delete("/api/stockHistorys/{id}", stockHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StockHistory> stockHistorys = stockHistoryRepository.findAll();
        assertThat(stockHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
