package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopPicture;
import com.pop.pcms.repository.PopPictureRepository;
import com.pop.pcms.service.PopPictureService;
import com.pop.pcms.service.dto.PopPictureDTO;
import com.pop.pcms.service.mapper.PopPictureMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PopPictureResource REST controller.
 *
 * @see PopPictureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopPictureResourceIntTest {

    private static final String DEFAULT_PIC_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PIC_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_SHOOT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SHOOT_ADDRESS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SHOOT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHOOT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PopPictureRepository popPictureRepository;

    @Autowired
    private PopPictureMapper popPictureMapper;

    @Autowired
    private PopPictureService popPictureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopPictureMockMvc;

    private PopPicture popPicture;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopPictureResource popPictureResource = new PopPictureResource(popPictureService);
        this.restPopPictureMockMvc = MockMvcBuilders.standaloneSetup(popPictureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopPicture createEntity(EntityManager em) {
        PopPicture popPicture = new PopPicture()
                .picPath(DEFAULT_PIC_PATH)
                .remark(DEFAULT_REMARK)
                .shootAddress(DEFAULT_SHOOT_ADDRESS)
                .shootDate(DEFAULT_SHOOT_DATE);
        return popPicture;
    }

    @Before
    public void initTest() {
        popPicture = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopPicture() throws Exception {
        int databaseSizeBeforeCreate = popPictureRepository.findAll().size();

        // Create the PopPicture
        PopPictureDTO popPictureDTO = popPictureMapper.popPictureToPopPictureDTO(popPicture);

        restPopPictureMockMvc.perform(post("/api/pop-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popPictureDTO)))
            .andExpect(status().isCreated());

        // Validate the PopPicture in the database
        List<PopPicture> popPictureList = popPictureRepository.findAll();
        assertThat(popPictureList).hasSize(databaseSizeBeforeCreate + 1);
        PopPicture testPopPicture = popPictureList.get(popPictureList.size() - 1);
        assertThat(testPopPicture.getPicPath()).isEqualTo(DEFAULT_PIC_PATH);
        assertThat(testPopPicture.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testPopPicture.getShootAddress()).isEqualTo(DEFAULT_SHOOT_ADDRESS);
        assertThat(testPopPicture.getShootDate()).isEqualTo(DEFAULT_SHOOT_DATE);
    }

    @Test
    @Transactional
    public void createPopPictureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popPictureRepository.findAll().size();

        // Create the PopPicture with an existing ID
        PopPicture existingPopPicture = new PopPicture();
        existingPopPicture.setId(1L);
        PopPictureDTO existingPopPictureDTO = popPictureMapper.popPictureToPopPictureDTO(existingPopPicture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopPictureMockMvc.perform(post("/api/pop-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopPictureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopPicture> popPictureList = popPictureRepository.findAll();
        assertThat(popPictureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopPictures() throws Exception {
        // Initialize the database
        popPictureRepository.saveAndFlush(popPicture);

        // Get all the popPictureList
        restPopPictureMockMvc.perform(get("/api/pop-pictures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popPicture.getId().intValue())))
            .andExpect(jsonPath("$.[*].picPath").value(hasItem(DEFAULT_PIC_PATH.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].shootAddress").value(hasItem(DEFAULT_SHOOT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].shootDate").value(hasItem(DEFAULT_SHOOT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPopPicture() throws Exception {
        // Initialize the database
        popPictureRepository.saveAndFlush(popPicture);

        // Get the popPicture
        restPopPictureMockMvc.perform(get("/api/pop-pictures/{id}", popPicture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popPicture.getId().intValue()))
            .andExpect(jsonPath("$.picPath").value(DEFAULT_PIC_PATH.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.shootAddress").value(DEFAULT_SHOOT_ADDRESS.toString()))
            .andExpect(jsonPath("$.shootDate").value(DEFAULT_SHOOT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPopPicture() throws Exception {
        // Get the popPicture
        restPopPictureMockMvc.perform(get("/api/pop-pictures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopPicture() throws Exception {
        // Initialize the database
        popPictureRepository.saveAndFlush(popPicture);
        int databaseSizeBeforeUpdate = popPictureRepository.findAll().size();

        // Update the popPicture
        PopPicture updatedPopPicture = popPictureRepository.findOne(popPicture.getId());
        updatedPopPicture
                .picPath(UPDATED_PIC_PATH)
                .remark(UPDATED_REMARK)
                .shootAddress(UPDATED_SHOOT_ADDRESS)
                .shootDate(UPDATED_SHOOT_DATE);
        PopPictureDTO popPictureDTO = popPictureMapper.popPictureToPopPictureDTO(updatedPopPicture);

        restPopPictureMockMvc.perform(put("/api/pop-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popPictureDTO)))
            .andExpect(status().isOk());

        // Validate the PopPicture in the database
        List<PopPicture> popPictureList = popPictureRepository.findAll();
        assertThat(popPictureList).hasSize(databaseSizeBeforeUpdate);
        PopPicture testPopPicture = popPictureList.get(popPictureList.size() - 1);
        assertThat(testPopPicture.getPicPath()).isEqualTo(UPDATED_PIC_PATH);
        assertThat(testPopPicture.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testPopPicture.getShootAddress()).isEqualTo(UPDATED_SHOOT_ADDRESS);
        assertThat(testPopPicture.getShootDate()).isEqualTo(UPDATED_SHOOT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPopPicture() throws Exception {
        int databaseSizeBeforeUpdate = popPictureRepository.findAll().size();

        // Create the PopPicture
        PopPictureDTO popPictureDTO = popPictureMapper.popPictureToPopPictureDTO(popPicture);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopPictureMockMvc.perform(put("/api/pop-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popPictureDTO)))
            .andExpect(status().isCreated());

        // Validate the PopPicture in the database
        List<PopPicture> popPictureList = popPictureRepository.findAll();
        assertThat(popPictureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopPicture() throws Exception {
        // Initialize the database
        popPictureRepository.saveAndFlush(popPicture);
        int databaseSizeBeforeDelete = popPictureRepository.findAll().size();

        // Get the popPicture
        restPopPictureMockMvc.perform(delete("/api/pop-pictures/{id}", popPicture.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopPicture> popPictureList = popPictureRepository.findAll();
        assertThat(popPictureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopPicture.class);
    }
}
