package com.kapilkoju.autopos.web.rest;

import com.kapilkoju.autopos.AutoPosApp;

import com.kapilkoju.autopos.domain.SaleLine;
import com.kapilkoju.autopos.domain.Sale;
import com.kapilkoju.autopos.domain.Item;
import com.kapilkoju.autopos.repository.SaleLineRepository;
import com.kapilkoju.autopos.service.SaleLineService;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SaleLineResource REST controller.
 *
 * @see SaleLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoPosApp.class)
public class SaleLineResourceIntTest {

    private static final String DEFAULT_BUYER = "AAAAAAAAAA";
    private static final String UPDATED_BUYER = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(0);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(1);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private SaleLineRepository saleLineRepository;

    @Autowired
    private SaleLineService saleLineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaleLineMockMvc;

    private SaleLine saleLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SaleLineResource saleLineResource = new SaleLineResource(saleLineService);
        this.restSaleLineMockMvc = MockMvcBuilders.standaloneSetup(saleLineResource)
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
    public static SaleLine createEntity(EntityManager em) {
        SaleLine saleLine = new SaleLine()
                .buyer(DEFAULT_BUYER)
                .quantity(DEFAULT_QUANTITY)
                .rate(DEFAULT_RATE)
                .remarks(DEFAULT_REMARKS);
        // Add required entity
        Sale sale = SaleResourceIntTest.createEntity(em);
        em.persist(sale);
        em.flush();
        saleLine.setSale(sale);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        saleLine.setItem(item);
        return saleLine;
    }

    @Before
    public void initTest() {
        saleLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaleLine() throws Exception {
        int databaseSizeBeforeCreate = saleLineRepository.findAll().size();

        // Create the SaleLine

        restSaleLineMockMvc.perform(post("/api/sale-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleLine)))
            .andExpect(status().isCreated());

        // Validate the SaleLine in the database
        List<SaleLine> saleLineList = saleLineRepository.findAll();
        assertThat(saleLineList).hasSize(databaseSizeBeforeCreate + 1);
        SaleLine testSaleLine = saleLineList.get(saleLineList.size() - 1);
        assertThat(testSaleLine.getBuyer()).isEqualTo(DEFAULT_BUYER);
        assertThat(testSaleLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSaleLine.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testSaleLine.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createSaleLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saleLineRepository.findAll().size();

        // Create the SaleLine with an existing ID
        SaleLine existingSaleLine = new SaleLine();
        existingSaleLine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleLineMockMvc.perform(post("/api/sale-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSaleLine)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SaleLine> saleLineList = saleLineRepository.findAll();
        assertThat(saleLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBuyerIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleLineRepository.findAll().size();
        // set the field null
        saleLine.setBuyer(null);

        // Create the SaleLine, which fails.

        restSaleLineMockMvc.perform(post("/api/sale-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleLine)))
            .andExpect(status().isBadRequest());

        List<SaleLine> saleLineList = saleLineRepository.findAll();
        assertThat(saleLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleLineRepository.findAll().size();
        // set the field null
        saleLine.setQuantity(null);

        // Create the SaleLine, which fails.

        restSaleLineMockMvc.perform(post("/api/sale-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleLine)))
            .andExpect(status().isBadRequest());

        List<SaleLine> saleLineList = saleLineRepository.findAll();
        assertThat(saleLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleLineRepository.findAll().size();
        // set the field null
        saleLine.setRate(null);

        // Create the SaleLine, which fails.

        restSaleLineMockMvc.perform(post("/api/sale-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleLine)))
            .andExpect(status().isBadRequest());

        List<SaleLine> saleLineList = saleLineRepository.findAll();
        assertThat(saleLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaleLines() throws Exception {
        // Initialize the database
        saleLineRepository.saveAndFlush(saleLine);

        // Get all the saleLineList
        restSaleLineMockMvc.perform(get("/api/sale-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].buyer").value(hasItem(DEFAULT_BUYER.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getSaleLine() throws Exception {
        // Initialize the database
        saleLineRepository.saveAndFlush(saleLine);

        // Get the saleLine
        restSaleLineMockMvc.perform(get("/api/sale-lines/{id}", saleLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saleLine.getId().intValue()))
            .andExpect(jsonPath("$.buyer").value(DEFAULT_BUYER.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSaleLine() throws Exception {
        // Get the saleLine
        restSaleLineMockMvc.perform(get("/api/sale-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaleLine() throws Exception {
        // Initialize the database
        saleLineService.save(saleLine);

        int databaseSizeBeforeUpdate = saleLineRepository.findAll().size();

        // Update the saleLine
        SaleLine updatedSaleLine = saleLineRepository.findOne(saleLine.getId());
        updatedSaleLine
                .buyer(UPDATED_BUYER)
                .quantity(UPDATED_QUANTITY)
                .rate(UPDATED_RATE)
                .remarks(UPDATED_REMARKS);

        restSaleLineMockMvc.perform(put("/api/sale-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSaleLine)))
            .andExpect(status().isOk());

        // Validate the SaleLine in the database
        List<SaleLine> saleLineList = saleLineRepository.findAll();
        assertThat(saleLineList).hasSize(databaseSizeBeforeUpdate);
        SaleLine testSaleLine = saleLineList.get(saleLineList.size() - 1);
        assertThat(testSaleLine.getBuyer()).isEqualTo(UPDATED_BUYER);
        assertThat(testSaleLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSaleLine.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testSaleLine.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingSaleLine() throws Exception {
        int databaseSizeBeforeUpdate = saleLineRepository.findAll().size();

        // Create the SaleLine

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSaleLineMockMvc.perform(put("/api/sale-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleLine)))
            .andExpect(status().isCreated());

        // Validate the SaleLine in the database
        List<SaleLine> saleLineList = saleLineRepository.findAll();
        assertThat(saleLineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSaleLine() throws Exception {
        // Initialize the database
        saleLineService.save(saleLine);

        int databaseSizeBeforeDelete = saleLineRepository.findAll().size();

        // Get the saleLine
        restSaleLineMockMvc.perform(delete("/api/sale-lines/{id}", saleLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SaleLine> saleLineList = saleLineRepository.findAll();
        assertThat(saleLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleLine.class);
    }
}
