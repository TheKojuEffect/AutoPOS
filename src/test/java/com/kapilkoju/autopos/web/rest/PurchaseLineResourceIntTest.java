package com.kapilkoju.autopos.web.rest;

import com.kapilkoju.autopos.AutoPosApp;

import com.kapilkoju.autopos.domain.PurchaseLine;
import com.kapilkoju.autopos.domain.Purchase;
import com.kapilkoju.autopos.domain.Item;
import com.kapilkoju.autopos.repository.PurchaseLineRepository;
import com.kapilkoju.autopos.service.PurchaseLineService;
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
 * Test class for the PurchaseLineResource REST controller.
 *
 * @see PurchaseLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoPosApp.class)
public class PurchaseLineResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(0);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(1);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private PurchaseLineService purchaseLineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPurchaseLineMockMvc;

    private PurchaseLine purchaseLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PurchaseLineResource purchaseLineResource = new PurchaseLineResource(purchaseLineService);
        this.restPurchaseLineMockMvc = MockMvcBuilders.standaloneSetup(purchaseLineResource)
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
    public static PurchaseLine createEntity(EntityManager em) {
        PurchaseLine purchaseLine = new PurchaseLine()
            .quantity(DEFAULT_QUANTITY)
            .rate(DEFAULT_RATE)
            .remarks(DEFAULT_REMARKS);
        // Add required entity
        Purchase purchase = PurchaseResourceIntTest.createEntity(em);
        em.persist(purchase);
        em.flush();
        purchaseLine.setPurchase(purchase);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        purchaseLine.setItem(item);
        return purchaseLine;
    }

    @Before
    public void initTest() {
        purchaseLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseLine() throws Exception {
        int databaseSizeBeforeCreate = purchaseLineRepository.findAll().size();

        // Create the PurchaseLine
        restPurchaseLineMockMvc.perform(post("/api/purchase-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseLine)))
            .andExpect(status().isCreated());

        // Validate the PurchaseLine in the database
        List<PurchaseLine> purchaseLineList = purchaseLineRepository.findAll();
        assertThat(purchaseLineList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseLine testPurchaseLine = purchaseLineList.get(purchaseLineList.size() - 1);
        assertThat(testPurchaseLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPurchaseLine.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testPurchaseLine.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createPurchaseLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseLineRepository.findAll().size();

        // Create the PurchaseLine with an existing ID
        purchaseLine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseLineMockMvc.perform(post("/api/purchase-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseLine)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PurchaseLine> purchaseLineList = purchaseLineRepository.findAll();
        assertThat(purchaseLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseLineRepository.findAll().size();
        // set the field null
        purchaseLine.setQuantity(null);

        // Create the PurchaseLine, which fails.

        restPurchaseLineMockMvc.perform(post("/api/purchase-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseLine)))
            .andExpect(status().isBadRequest());

        List<PurchaseLine> purchaseLineList = purchaseLineRepository.findAll();
        assertThat(purchaseLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseLineRepository.findAll().size();
        // set the field null
        purchaseLine.setRate(null);

        // Create the PurchaseLine, which fails.

        restPurchaseLineMockMvc.perform(post("/api/purchase-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseLine)))
            .andExpect(status().isBadRequest());

        List<PurchaseLine> purchaseLineList = purchaseLineRepository.findAll();
        assertThat(purchaseLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchaseLines() throws Exception {
        // Initialize the database
        purchaseLineRepository.saveAndFlush(purchaseLine);

        // Get all the purchaseLineList
        restPurchaseLineMockMvc.perform(get("/api/purchase-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getPurchaseLine() throws Exception {
        // Initialize the database
        purchaseLineRepository.saveAndFlush(purchaseLine);

        // Get the purchaseLine
        restPurchaseLineMockMvc.perform(get("/api/purchase-lines/{id}", purchaseLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseLine.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseLine() throws Exception {
        // Get the purchaseLine
        restPurchaseLineMockMvc.perform(get("/api/purchase-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseLine() throws Exception {
        // Initialize the database
        purchaseLineService.save(purchaseLine);

        int databaseSizeBeforeUpdate = purchaseLineRepository.findAll().size();

        // Update the purchaseLine
        PurchaseLine updatedPurchaseLine = purchaseLineRepository.findOne(purchaseLine.getId());
        updatedPurchaseLine
            .quantity(UPDATED_QUANTITY)
            .rate(UPDATED_RATE)
            .remarks(UPDATED_REMARKS);

        restPurchaseLineMockMvc.perform(put("/api/purchase-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchaseLine)))
            .andExpect(status().isOk());

        // Validate the PurchaseLine in the database
        List<PurchaseLine> purchaseLineList = purchaseLineRepository.findAll();
        assertThat(purchaseLineList).hasSize(databaseSizeBeforeUpdate);
        PurchaseLine testPurchaseLine = purchaseLineList.get(purchaseLineList.size() - 1);
        assertThat(testPurchaseLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPurchaseLine.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testPurchaseLine.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseLine() throws Exception {
        int databaseSizeBeforeUpdate = purchaseLineRepository.findAll().size();

        // Create the PurchaseLine

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPurchaseLineMockMvc.perform(put("/api/purchase-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseLine)))
            .andExpect(status().isCreated());

        // Validate the PurchaseLine in the database
        List<PurchaseLine> purchaseLineList = purchaseLineRepository.findAll();
        assertThat(purchaseLineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePurchaseLine() throws Exception {
        // Initialize the database
        purchaseLineService.save(purchaseLine);

        int databaseSizeBeforeDelete = purchaseLineRepository.findAll().size();

        // Get the purchaseLine
        restPurchaseLineMockMvc.perform(delete("/api/purchase-lines/{id}", purchaseLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseLine> purchaseLineList = purchaseLineRepository.findAll();
        assertThat(purchaseLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseLine.class);
    }
}
