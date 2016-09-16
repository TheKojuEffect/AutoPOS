package io.koju.autopos.party.web;

import io.koju.autopos.Application;
import io.koju.autopos.party.api.VehicleApi;
import io.koju.autopos.party.domain.Vehicle;
import io.koju.autopos.party.repo.VehicleRepo;
import io.koju.autopos.party.service.VehicleService;
import io.koju.autopos.web.rest.TestUtil;
import org.junit.Before;
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
public class VehicleApiIntTest {

    private static final String DEFAULT_NUMBER = "A";
    private static final String UPDATED_NUMBER = "B";
    private static final String DEFAULT_REMARKS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private VehicleRepo vehicleRepository;

    @Inject
    private VehicleService vehicleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehicleApi vehicleApi = new VehicleApi(vehicleRepository, vehicleService);
        ReflectionTestUtils.setField(vehicleApi, "vehicleRepository", vehicleRepository);
        this.restVehicleMockMvc = MockMvcBuilders.standaloneSetup(vehicleApi)
                                                 .setCustomArgumentResolvers(pageableArgumentResolver)
                                                 .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vehicle = new Vehicle();
        vehicle.setNumber(DEFAULT_NUMBER);
        vehicle.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle

        restVehicleMockMvc.perform(post("/api/vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vehicle)))
                          .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicles = vehicleRepository.findAll();
        assertThat(vehicles).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicles.get(vehicles.size() - 1);
        assertThat(testVehicle.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testVehicle.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setNumber(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc.perform(post("/api/vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vehicle)))
                          .andExpect(status().isBadRequest());

        List<Vehicle> vehicles = vehicleRepository.findAll();
        assertThat(vehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicles
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc"))
                          .andExpect(status().isOk())
                          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                          .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
                          .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                          .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", vehicle.getId()))
                          .andExpect(status().isOk())
                          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                          .andExpect(jsonPath("$.id").value(vehicle.getId().intValue()))
                          .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
                          .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", Long.MAX_VALUE))
                          .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle
        Vehicle updatedVehicle = new Vehicle();
        updatedVehicle.setId(vehicle.getId());
        updatedVehicle.setNumber(UPDATED_NUMBER);
        updatedVehicle.setRemarks(UPDATED_REMARKS);

        restVehicleMockMvc.perform(put("/api/vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedVehicle)))
                          .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicles = vehicleRepository.findAll();
        assertThat(vehicles).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicles.get(vehicles.size() - 1);
        assertThat(testVehicle.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testVehicle.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);
        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();

        // Get the vehicle
        restVehicleMockMvc.perform(delete("/api/vehicles/{id}", vehicle.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                          .andExpect(status().isOk());

        // Validate the database is empty
        List<Vehicle> vehicles = vehicleRepository.findAll();
        assertThat(vehicles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
