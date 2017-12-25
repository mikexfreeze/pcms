package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopAppraise;
import com.pop.pcms.repository.PopAppraiseRepository;
import com.pop.pcms.service.PopAppraiseService;
import com.pop.pcms.service.dto.PopAppraiseDTO;
import com.pop.pcms.service.mapper.PopAppraiseMapper;

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

import com.pop.pcms.domain.enumeration.AppraiseStatus;
/**
 * Test class for the PopAppraiseResource REST controller.
 *
 * @see PopAppraiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopAppraiseResourceIntTest {

    private static final Long DEFAULT_ROUND = 1L;
    private static final Long UPDATED_ROUND = 2L;

    private static final String DEFAULT_APPRAISE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_APPRAISE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_POLL_NUM = "AAAAAAAAAA";
    private static final String UPDATED_POLL_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_AWARD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AWARD_NAME = "BBBBBBBBBB";

    private static final AppraiseStatus DEFAULT_STATUS = AppraiseStatus.VOTING;
    private static final AppraiseStatus UPDATED_STATUS = AppraiseStatus.FINISHED;

    private static final String DEFAULT_AWARD_CONFIG_ID = "AAAAAAAAAA";
    private static final String UPDATED_AWARD_CONFIG_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_APPRAISE = 1L;
    private static final Long UPDATED_PARENT_APPRAISE = 2L;

    @Autowired
    private PopAppraiseRepository popAppraiseRepository;

    @Autowired
    private PopAppraiseMapper popAppraiseMapper;

    @Autowired
    private PopAppraiseService popAppraiseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopAppraiseMockMvc;

    private PopAppraise popAppraise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopAppraiseResource popAppraiseResource = new PopAppraiseResource(popAppraiseService);
        this.restPopAppraiseMockMvc = MockMvcBuilders.standaloneSetup(popAppraiseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopAppraise createEntity(EntityManager em) {
        PopAppraise popAppraise = new PopAppraise()
                .round(DEFAULT_ROUND)
                .appraiseType(DEFAULT_APPRAISE_TYPE)
                .pollNum(DEFAULT_POLL_NUM)
                .remark(DEFAULT_REMARK)
                .awardName(DEFAULT_AWARD_NAME)
                .status(DEFAULT_STATUS)
                .awardConfigId(DEFAULT_AWARD_CONFIG_ID)
                .parentAppraise(DEFAULT_PARENT_APPRAISE);
        return popAppraise;
    }

    @Before
    public void initTest() {
        popAppraise = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopAppraise() throws Exception {
        int databaseSizeBeforeCreate = popAppraiseRepository.findAll().size();

        // Create the PopAppraise
        PopAppraiseDTO popAppraiseDTO = popAppraiseMapper.popAppraiseToPopAppraiseDTO(popAppraise);

        restPopAppraiseMockMvc.perform(post("/api/pop-appraises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAppraiseDTO)))
            .andExpect(status().isCreated());

        // Validate the PopAppraise in the database
        List<PopAppraise> popAppraiseList = popAppraiseRepository.findAll();
        assertThat(popAppraiseList).hasSize(databaseSizeBeforeCreate + 1);
        PopAppraise testPopAppraise = popAppraiseList.get(popAppraiseList.size() - 1);
        assertThat(testPopAppraise.getRound()).isEqualTo(DEFAULT_ROUND);
        assertThat(testPopAppraise.getAppraiseType()).isEqualTo(DEFAULT_APPRAISE_TYPE);
        assertThat(testPopAppraise.getPollNum()).isEqualTo(DEFAULT_POLL_NUM);
        assertThat(testPopAppraise.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testPopAppraise.getAwardName()).isEqualTo(DEFAULT_AWARD_NAME);
        assertThat(testPopAppraise.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPopAppraise.getAwardConfigId()).isEqualTo(DEFAULT_AWARD_CONFIG_ID);
        assertThat(testPopAppraise.getParentAppraise()).isEqualTo(DEFAULT_PARENT_APPRAISE);
    }

    @Test
    @Transactional
    public void createPopAppraiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popAppraiseRepository.findAll().size();

        // Create the PopAppraise with an existing ID
        PopAppraise existingPopAppraise = new PopAppraise();
        existingPopAppraise.setId(1L);
        PopAppraiseDTO existingPopAppraiseDTO = popAppraiseMapper.popAppraiseToPopAppraiseDTO(existingPopAppraise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopAppraiseMockMvc.perform(post("/api/pop-appraises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopAppraiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopAppraise> popAppraiseList = popAppraiseRepository.findAll();
        assertThat(popAppraiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopAppraises() throws Exception {
        // Initialize the database
        popAppraiseRepository.saveAndFlush(popAppraise);

        // Get all the popAppraiseList
        restPopAppraiseMockMvc.perform(get("/api/pop-appraises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popAppraise.getId().intValue())))
            .andExpect(jsonPath("$.[*].round").value(hasItem(DEFAULT_ROUND.intValue())))
            .andExpect(jsonPath("$.[*].appraiseType").value(hasItem(DEFAULT_APPRAISE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pollNum").value(hasItem(DEFAULT_POLL_NUM.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].awardName").value(hasItem(DEFAULT_AWARD_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].awardConfigId").value(hasItem(DEFAULT_AWARD_CONFIG_ID.toString())))
            .andExpect(jsonPath("$.[*].parentAppraise").value(hasItem(DEFAULT_PARENT_APPRAISE.intValue())));
    }

    @Test
    @Transactional
    public void getPopAppraise() throws Exception {
        // Initialize the database
        popAppraiseRepository.saveAndFlush(popAppraise);

        // Get the popAppraise
        restPopAppraiseMockMvc.perform(get("/api/pop-appraises/{id}", popAppraise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popAppraise.getId().intValue()))
            .andExpect(jsonPath("$.round").value(DEFAULT_ROUND.intValue()))
            .andExpect(jsonPath("$.appraiseType").value(DEFAULT_APPRAISE_TYPE.toString()))
            .andExpect(jsonPath("$.pollNum").value(DEFAULT_POLL_NUM.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.awardName").value(DEFAULT_AWARD_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.awardConfigId").value(DEFAULT_AWARD_CONFIG_ID.toString()))
            .andExpect(jsonPath("$.parentAppraise").value(DEFAULT_PARENT_APPRAISE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPopAppraise() throws Exception {
        // Get the popAppraise
        restPopAppraiseMockMvc.perform(get("/api/pop-appraises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopAppraise() throws Exception {
        // Initialize the database
        popAppraiseRepository.saveAndFlush(popAppraise);
        int databaseSizeBeforeUpdate = popAppraiseRepository.findAll().size();

        // Update the popAppraise
        PopAppraise updatedPopAppraise = popAppraiseRepository.findOne(popAppraise.getId());
        updatedPopAppraise
                .round(UPDATED_ROUND)
                .appraiseType(UPDATED_APPRAISE_TYPE)
                .pollNum(UPDATED_POLL_NUM)
                .remark(UPDATED_REMARK)
                .awardName(UPDATED_AWARD_NAME)
                .status(UPDATED_STATUS)
                .awardConfigId(UPDATED_AWARD_CONFIG_ID)
                .parentAppraise(UPDATED_PARENT_APPRAISE);
        PopAppraiseDTO popAppraiseDTO = popAppraiseMapper.popAppraiseToPopAppraiseDTO(updatedPopAppraise);

        restPopAppraiseMockMvc.perform(put("/api/pop-appraises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAppraiseDTO)))
            .andExpect(status().isOk());

        // Validate the PopAppraise in the database
        List<PopAppraise> popAppraiseList = popAppraiseRepository.findAll();
        assertThat(popAppraiseList).hasSize(databaseSizeBeforeUpdate);
        PopAppraise testPopAppraise = popAppraiseList.get(popAppraiseList.size() - 1);
        assertThat(testPopAppraise.getRound()).isEqualTo(UPDATED_ROUND);
        assertThat(testPopAppraise.getAppraiseType()).isEqualTo(UPDATED_APPRAISE_TYPE);
        assertThat(testPopAppraise.getPollNum()).isEqualTo(UPDATED_POLL_NUM);
        assertThat(testPopAppraise.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testPopAppraise.getAwardName()).isEqualTo(UPDATED_AWARD_NAME);
        assertThat(testPopAppraise.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPopAppraise.getAwardConfigId()).isEqualTo(UPDATED_AWARD_CONFIG_ID);
        assertThat(testPopAppraise.getParentAppraise()).isEqualTo(UPDATED_PARENT_APPRAISE);
    }

    @Test
    @Transactional
    public void updateNonExistingPopAppraise() throws Exception {
        int databaseSizeBeforeUpdate = popAppraiseRepository.findAll().size();

        // Create the PopAppraise
        PopAppraiseDTO popAppraiseDTO = popAppraiseMapper.popAppraiseToPopAppraiseDTO(popAppraise);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopAppraiseMockMvc.perform(put("/api/pop-appraises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAppraiseDTO)))
            .andExpect(status().isCreated());

        // Validate the PopAppraise in the database
        List<PopAppraise> popAppraiseList = popAppraiseRepository.findAll();
        assertThat(popAppraiseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopAppraise() throws Exception {
        // Initialize the database
        popAppraiseRepository.saveAndFlush(popAppraise);
        int databaseSizeBeforeDelete = popAppraiseRepository.findAll().size();

        // Get the popAppraise
        restPopAppraiseMockMvc.perform(delete("/api/pop-appraises/{id}", popAppraise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopAppraise> popAppraiseList = popAppraiseRepository.findAll();
        assertThat(popAppraiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopAppraise.class);
    }
}
