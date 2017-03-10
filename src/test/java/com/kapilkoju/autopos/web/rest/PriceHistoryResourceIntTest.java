package com.kapilkoju.autopos.web.rest;

import com.kapilkoju.autopos.AutoPosApp;

import com.kapilkoju.autopos.domain.PriceHistory;
import com.kapilkoju.autopos.domain.Item;
import com.kapilkoju.autopos.repository.PriceHistoryRepository;
import com.kapilkoju.autopos.service.PriceHistoryService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static com.kapilkoju.autopos.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PriceHistoryResource REST controller.
 *
 * @see PriceHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoPosApp.class)
public class PriceHistoryResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_MARKED_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_MARKED_PRICE = new BigDecimal(1);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private PriceHistoryService priceHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriceHistoryMockMvc;

    private PriceHistory priceHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PriceHistoryResource priceHistoryResource = new PriceHistoryResource(priceHistoryService);
        this.restPriceHistoryMockMvc = MockMvcBuilders.standaloneSetup(priceHistoryResource)
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
    public static PriceHistory createEntity(EntityManager em) {
        PriceHistory priceHistory = new PriceHistory()
            .date(DEFAULT_DATE)
            .markedPrice(DEFAULT_MARKED_PRICE)
            .remarks(DEFAULT_REMARKS);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        priceHistory.setItem(item);
        return priceHistory;
    }

    @Before
    public void initTest() {
        priceHistory = createEntity(em);
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
        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PriceHistory testPriceHistory = priceHistoryList.get(priceHistoryList.size() - 1);
        assertThat(testPriceHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPriceHistory.getMarkedPrice()).isEqualTo(DEFAULT_MARKED_PRICE);
        assertThat(testPriceHistory.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createPriceHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceHistoryRepository.findAll().size();

        // Create the PriceHistory with an existing ID
        priceHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceHistoryMockMvc.perform(post("/api/price-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceHistory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeCreate);
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

        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeTest);
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

        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceHistories() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

        // Get all the priceHistoryList
        restPriceHistoryMockMvc.perform(get("/api/price-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priceHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
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
        priceHistoryService.save(priceHistory);

        int databaseSizeBeforeUpdate = priceHistoryRepository.findAll().size();

        // Update the priceHistory
        PriceHistory updatedPriceHistory = priceHistoryRepository.findOne(priceHistory.getId());
        updatedPriceHistory
            .date(UPDATED_DATE)
            .markedPrice(UPDATED_MARKED_PRICE)
            .remarks(UPDATED_REMARKS);

        restPriceHistoryMockMvc.perform(put("/api/price-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPriceHistory)))
            .andExpect(status().isOk());

        // Validate the PriceHistory in the database
        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeUpdate);
        PriceHistory testPriceHistory = priceHistoryList.get(priceHistoryList.size() - 1);
        assertThat(testPriceHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPriceHistory.getMarkedPrice()).isEqualTo(UPDATED_MARKED_PRICE);
        assertThat(testPriceHistory.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceHistory() throws Exception {
        int databaseSizeBeforeUpdate = priceHistoryRepository.findAll().size();

        // Create the PriceHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPriceHistoryMockMvc.perform(put("/api/price-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceHistory)))
            .andExpect(status().isCreated());

        // Validate the PriceHistory in the database
        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePriceHistory() throws Exception {
        // Initialize the database
        priceHistoryService.save(priceHistory);

        int databaseSizeBeforeDelete = priceHistoryRepository.findAll().size();

        // Get the priceHistory
        restPriceHistoryMockMvc.perform(delete("/api/price-histories/{id}", priceHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceHistory.class);
    }
}
