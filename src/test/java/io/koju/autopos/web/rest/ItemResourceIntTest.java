package io.koju.autopos.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.koju.autopos.Application;
import io.koju.autopos.catalog.domain.Item;
import io.koju.autopos.catalog.service.ItemCodeUtil;
import io.koju.autopos.catalog.web.ItemResource;
import io.koju.autopos.catalog.service.ItemRepository;
import io.koju.autopos.catalog.service.ItemService;

import io.koju.autopos.security.SecurityTestUtil;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ItemResource REST controller.
 *
 * @see ItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ItemResourceIntTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final BigDecimal DEFAULT_MARKED_PRICE = new BigDecimal(20);
    private static final BigDecimal UPDATED_MARKED_PRICE = new BigDecimal(1);

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private ItemService itemService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private ObjectMapper objectMapper;

    private MockMvc restItemMockMvc;

    private Item item;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemResource itemResource = new ItemResource();
        ReflectionTestUtils.setField(itemResource, "itemService", itemService);
        this.restItemMockMvc = MockMvcBuilders.standaloneSetup(itemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        item = new Item();
        item.setName(DEFAULT_NAME);
        item.setDescription(DEFAULT_DESCRIPTION);
        item.setRemarks(DEFAULT_REMARKS);
        item.setMarkedPrice(DEFAULT_MARKED_PRICE);
    }

    @Test
    @Transactional
    public void createItem() throws Exception {
        SecurityTestUtil.makeSystemUserCurrentUser();
        int databaseSizeBeforeCreate = itemRepository.findAll().size();

        // Create the Item

        restItemMockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(item)))
                .andExpect(status().isCreated());

        // Validate the Item in the database
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(databaseSizeBeforeCreate + 1);
        Item testItem = items.get(items.size() - 1);
        assertThat(testItem.getCode()).isEqualTo(ItemCodeUtil.getCode(testItem.getId()));
        assertThat(testItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItem.getDescription().get()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItem.getRemarks().get()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testItem.getMarkedPrice()).isEqualByComparingTo(DEFAULT_MARKED_PRICE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setName(null);

        // Create the Item, which fails.

        restItemMockMvc.perform(post("/api/items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(item)))
                .andExpect(status().isBadRequest());

        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarkedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setMarkedPrice(null);

        // Create the Item, which fails.

        restItemMockMvc.perform(post("/api/items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andExpect(status().isBadRequest());

        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItems() throws Exception {
        SecurityTestUtil.makeSystemUserCurrentUser();
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the items
        restItemMockMvc.perform(get("/api/items?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(item.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(ItemCodeUtil.getCode(item.getId()).toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].markedPrice").value(hasItem(DEFAULT_MARKED_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getItem() throws Exception {

        SecurityTestUtil.makeSystemUserCurrentUser();


        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", item.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(item.getId().intValue()))
            .andExpect(jsonPath("$.code").value(ItemCodeUtil.getCode(item.getId()).toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.markedPrice").value(DEFAULT_MARKED_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItem() throws Exception {
        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItem() throws Exception {
        SecurityTestUtil.makeSystemUserCurrentUser();
        // Initialize the database
        itemRepository.saveAndFlush(item);

		int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Update the item
        item.setName(UPDATED_NAME);
        item.setDescription(UPDATED_DESCRIPTION);
        item.setRemarks(UPDATED_REMARKS);
        item.setMarkedPrice(UPDATED_MARKED_PRICE);

        restItemMockMvc.perform(put("/api/items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(item)))
                .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(databaseSizeBeforeUpdate);
        Item testItem = items.get(items.size() - 1);
        assertThat(testItem.getCode()).isEqualTo(ItemCodeUtil.getCode(testItem.getId()));
        assertThat(testItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItem.getDescription().get()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItem.getRemarks().get()).isEqualTo(UPDATED_REMARKS);
        assertThat(testItem.getMarkedPrice()).isEqualTo(UPDATED_MARKED_PRICE);
    }

    @Test
    @Transactional
    public void deleteItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

		int databaseSizeBeforeDelete = itemRepository.findAll().size();

        // Get the item
        restItemMockMvc.perform(delete("/api/items/{id}", item.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(databaseSizeBeforeDelete - 1);
    }
}
