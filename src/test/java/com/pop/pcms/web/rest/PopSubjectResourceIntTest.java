package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopSubject;
import com.pop.pcms.repository.PopSubjectRepository;
import com.pop.pcms.service.PopSubjectService;
import com.pop.pcms.service.dto.PopSubjectDTO;
import com.pop.pcms.service.mapper.PopSubjectMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pop.pcms.domain.enumeration.PopSubjectStatus;
/**
 * Test class for the PopSubjectResource REST controller.
 *
 * @see PopSubjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopSubjectResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_MAX_LIMIT = 1L;
    private static final Long UPDATED_MAX_LIMIT = 2L;

    private static final Long DEFAULT_GROUP_MAX_LIMIT = 1L;
    private static final Long UPDATED_GROUP_MAX_LIMIT = 2L;

    private static final String DEFAULT_ASSET_DIR = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_DIR = "BBBBBBBBBB";

    private static final PopSubjectStatus DEFAULT_STATUS = PopSubjectStatus.VOTE_BEGIN;
    private static final PopSubjectStatus UPDATED_STATUS = PopSubjectStatus.VOTE_FINISH;

    @Autowired
    private PopSubjectRepository popSubjectRepository;

    @Autowired
    private PopSubjectMapper popSubjectMapper;

    @Autowired
    private PopSubjectService popSubjectService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopSubjectMockMvc;

    private PopSubject popSubject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopSubjectResource popSubjectResource = new PopSubjectResource(popSubjectService);
        this.restPopSubjectMockMvc = MockMvcBuilders.standaloneSetup(popSubjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopSubject createEntity(EntityManager em) {
        PopSubject popSubject = new PopSubject()
                .name(DEFAULT_NAME)
                .maxLimit(DEFAULT_MAX_LIMIT)
                .groupMaxLimit(DEFAULT_GROUP_MAX_LIMIT)
                .assetDir(DEFAULT_ASSET_DIR)
                .status(DEFAULT_STATUS);
        return popSubject;
    }

    @Before
    public void initTest() {
        popSubject = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopSubject() throws Exception {
        int databaseSizeBeforeCreate = popSubjectRepository.findAll().size();

        // Create the PopSubject
        PopSubjectDTO popSubjectDTO = popSubjectMapper.popSubjectToPopSubjectDTO(popSubject);

        restPopSubjectMockMvc.perform(post("/api/pop-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popSubjectDTO)))
            .andExpect(status().isCreated());

        // Validate the PopSubject in the database
        List<PopSubject> popSubjectList = popSubjectRepository.findAll();
        assertThat(popSubjectList).hasSize(databaseSizeBeforeCreate + 1);
        PopSubject testPopSubject = popSubjectList.get(popSubjectList.size() - 1);
        assertThat(testPopSubject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPopSubject.getMaxLimit()).isEqualTo(DEFAULT_MAX_LIMIT);
        assertThat(testPopSubject.getGroupMaxLimit()).isEqualTo(DEFAULT_GROUP_MAX_LIMIT);
        assertThat(testPopSubject.getAssetDir()).isEqualTo(DEFAULT_ASSET_DIR);
        assertThat(testPopSubject.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPopSubjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popSubjectRepository.findAll().size();

        // Create the PopSubject with an existing ID
        PopSubject existingPopSubject = new PopSubject();
        existingPopSubject.setId(1L);
        PopSubjectDTO existingPopSubjectDTO = popSubjectMapper.popSubjectToPopSubjectDTO(existingPopSubject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopSubjectMockMvc.perform(post("/api/pop-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopSubjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopSubject> popSubjectList = popSubjectRepository.findAll();
        assertThat(popSubjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopSubjects() throws Exception {
        // Initialize the database
        popSubjectRepository.saveAndFlush(popSubject);

        // Get all the popSubjectList
        restPopSubjectMockMvc.perform(get("/api/pop-subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popSubject.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].maxLimit").value(hasItem(DEFAULT_MAX_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].groupMaxLimit").value(hasItem(DEFAULT_GROUP_MAX_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].assetDir").value(hasItem(DEFAULT_ASSET_DIR.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPopSubject() throws Exception {
        // Initialize the database
        popSubjectRepository.saveAndFlush(popSubject);

        // Get the popSubject
        restPopSubjectMockMvc.perform(get("/api/pop-subjects/{id}", popSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popSubject.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.maxLimit").value(DEFAULT_MAX_LIMIT.intValue()))
            .andExpect(jsonPath("$.groupMaxLimit").value(DEFAULT_GROUP_MAX_LIMIT.intValue()))
            .andExpect(jsonPath("$.assetDir").value(DEFAULT_ASSET_DIR.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPopSubject() throws Exception {
        // Get the popSubject
        restPopSubjectMockMvc.perform(get("/api/pop-subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopSubject() throws Exception {
        // Initialize the database
        popSubjectRepository.saveAndFlush(popSubject);
        int databaseSizeBeforeUpdate = popSubjectRepository.findAll().size();

        // Update the popSubject
        PopSubject updatedPopSubject = popSubjectRepository.findOne(popSubject.getId());
        updatedPopSubject
                .name(UPDATED_NAME)
                .maxLimit(UPDATED_MAX_LIMIT)
                .groupMaxLimit(UPDATED_GROUP_MAX_LIMIT)
                .assetDir(UPDATED_ASSET_DIR)
                .status(UPDATED_STATUS);
        PopSubjectDTO popSubjectDTO = popSubjectMapper.popSubjectToPopSubjectDTO(updatedPopSubject);

        restPopSubjectMockMvc.perform(put("/api/pop-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popSubjectDTO)))
            .andExpect(status().isOk());

        // Validate the PopSubject in the database
        List<PopSubject> popSubjectList = popSubjectRepository.findAll();
        assertThat(popSubjectList).hasSize(databaseSizeBeforeUpdate);
        PopSubject testPopSubject = popSubjectList.get(popSubjectList.size() - 1);
        assertThat(testPopSubject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPopSubject.getMaxLimit()).isEqualTo(UPDATED_MAX_LIMIT);
        assertThat(testPopSubject.getGroupMaxLimit()).isEqualTo(UPDATED_GROUP_MAX_LIMIT);
        assertThat(testPopSubject.getAssetDir()).isEqualTo(UPDATED_ASSET_DIR);
        assertThat(testPopSubject.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPopSubject() throws Exception {
        int databaseSizeBeforeUpdate = popSubjectRepository.findAll().size();

        // Create the PopSubject
        PopSubjectDTO popSubjectDTO = popSubjectMapper.popSubjectToPopSubjectDTO(popSubject);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopSubjectMockMvc.perform(put("/api/pop-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popSubjectDTO)))
            .andExpect(status().isCreated());

        // Validate the PopSubject in the database
        List<PopSubject> popSubjectList = popSubjectRepository.findAll();
        assertThat(popSubjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopSubject() throws Exception {
        // Initialize the database
        popSubjectRepository.saveAndFlush(popSubject);
        int databaseSizeBeforeDelete = popSubjectRepository.findAll().size();

        // Get the popSubject
        restPopSubjectMockMvc.perform(delete("/api/pop-subjects/{id}", popSubject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopSubject> popSubjectList = popSubjectRepository.findAll();
        assertThat(popSubjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopSubject.class);
    }
}
