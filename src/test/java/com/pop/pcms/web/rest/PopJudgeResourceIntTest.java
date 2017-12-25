package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopJudge;
import com.pop.pcms.repository.PopJudgeRepository;
import com.pop.pcms.service.PopJudgeService;
import com.pop.pcms.service.dto.PopJudgeDTO;
import com.pop.pcms.service.mapper.PopJudgeMapper;

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

import com.pop.pcms.domain.enumeration.JudgeStatus;
/**
 * Test class for the PopJudgeResource REST controller.
 *
 * @see PopJudgeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopJudgeResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_COLOR_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_FLAG = "BBBBBBBBBB";

    private static final JudgeStatus DEFAULT_VOTE_STATUS = JudgeStatus.SUBMITTED;
    private static final JudgeStatus UPDATED_VOTE_STATUS = JudgeStatus.NOT_SUBMITTED;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    @Autowired
    private PopJudgeRepository popJudgeRepository;

    @Autowired
    private PopJudgeMapper popJudgeMapper;

    @Autowired
    private PopJudgeService popJudgeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopJudgeMockMvc;

    private PopJudge popJudge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopJudgeResource popJudgeResource = new PopJudgeResource(popJudgeService);
        this.restPopJudgeMockMvc = MockMvcBuilders.standaloneSetup(popJudgeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopJudge createEntity(EntityManager em) {
        PopJudge popJudge = new PopJudge()
                .userId(DEFAULT_USER_ID)
                .colorFlag(DEFAULT_COLOR_FLAG)
                .voteStatus(DEFAULT_VOTE_STATUS)
                .userName(DEFAULT_USER_NAME);
        return popJudge;
    }

    @Before
    public void initTest() {
        popJudge = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopJudge() throws Exception {
        int databaseSizeBeforeCreate = popJudgeRepository.findAll().size();

        // Create the PopJudge
        PopJudgeDTO popJudgeDTO = popJudgeMapper.popJudgeToPopJudgeDTO(popJudge);

        restPopJudgeMockMvc.perform(post("/api/pop-judges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popJudgeDTO)))
            .andExpect(status().isCreated());

        // Validate the PopJudge in the database
        List<PopJudge> popJudgeList = popJudgeRepository.findAll();
        assertThat(popJudgeList).hasSize(databaseSizeBeforeCreate + 1);
        PopJudge testPopJudge = popJudgeList.get(popJudgeList.size() - 1);
        assertThat(testPopJudge.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPopJudge.getColorFlag()).isEqualTo(DEFAULT_COLOR_FLAG);
        assertThat(testPopJudge.getVoteStatus()).isEqualTo(DEFAULT_VOTE_STATUS);
        assertThat(testPopJudge.getUserName()).isEqualTo(DEFAULT_USER_NAME);
    }

    @Test
    @Transactional
    public void createPopJudgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popJudgeRepository.findAll().size();

        // Create the PopJudge with an existing ID
        PopJudge existingPopJudge = new PopJudge();
        existingPopJudge.setId(1L);
        PopJudgeDTO existingPopJudgeDTO = popJudgeMapper.popJudgeToPopJudgeDTO(existingPopJudge);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopJudgeMockMvc.perform(post("/api/pop-judges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopJudgeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopJudge> popJudgeList = popJudgeRepository.findAll();
        assertThat(popJudgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopJudges() throws Exception {
        // Initialize the database
        popJudgeRepository.saveAndFlush(popJudge);

        // Get all the popJudgeList
        restPopJudgeMockMvc.perform(get("/api/pop-judges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popJudge.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].colorFlag").value(hasItem(DEFAULT_COLOR_FLAG.toString())))
            .andExpect(jsonPath("$.[*].voteStatus").value(hasItem(DEFAULT_VOTE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPopJudge() throws Exception {
        // Initialize the database
        popJudgeRepository.saveAndFlush(popJudge);

        // Get the popJudge
        restPopJudgeMockMvc.perform(get("/api/pop-judges/{id}", popJudge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popJudge.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.colorFlag").value(DEFAULT_COLOR_FLAG.toString()))
            .andExpect(jsonPath("$.voteStatus").value(DEFAULT_VOTE_STATUS.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPopJudge() throws Exception {
        // Get the popJudge
        restPopJudgeMockMvc.perform(get("/api/pop-judges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopJudge() throws Exception {
        // Initialize the database
        popJudgeRepository.saveAndFlush(popJudge);
        int databaseSizeBeforeUpdate = popJudgeRepository.findAll().size();

        // Update the popJudge
        PopJudge updatedPopJudge = popJudgeRepository.findOne(popJudge.getId());
        updatedPopJudge
                .userId(UPDATED_USER_ID)
                .colorFlag(UPDATED_COLOR_FLAG)
                .voteStatus(UPDATED_VOTE_STATUS)
                .userName(UPDATED_USER_NAME);
        PopJudgeDTO popJudgeDTO = popJudgeMapper.popJudgeToPopJudgeDTO(updatedPopJudge);

        restPopJudgeMockMvc.perform(put("/api/pop-judges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popJudgeDTO)))
            .andExpect(status().isOk());

        // Validate the PopJudge in the database
        List<PopJudge> popJudgeList = popJudgeRepository.findAll();
        assertThat(popJudgeList).hasSize(databaseSizeBeforeUpdate);
        PopJudge testPopJudge = popJudgeList.get(popJudgeList.size() - 1);
        assertThat(testPopJudge.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPopJudge.getColorFlag()).isEqualTo(UPDATED_COLOR_FLAG);
        assertThat(testPopJudge.getVoteStatus()).isEqualTo(UPDATED_VOTE_STATUS);
        assertThat(testPopJudge.getUserName()).isEqualTo(UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPopJudge() throws Exception {
        int databaseSizeBeforeUpdate = popJudgeRepository.findAll().size();

        // Create the PopJudge
        PopJudgeDTO popJudgeDTO = popJudgeMapper.popJudgeToPopJudgeDTO(popJudge);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopJudgeMockMvc.perform(put("/api/pop-judges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popJudgeDTO)))
            .andExpect(status().isCreated());

        // Validate the PopJudge in the database
        List<PopJudge> popJudgeList = popJudgeRepository.findAll();
        assertThat(popJudgeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopJudge() throws Exception {
        // Initialize the database
        popJudgeRepository.saveAndFlush(popJudge);
        int databaseSizeBeforeDelete = popJudgeRepository.findAll().size();

        // Get the popJudge
        restPopJudgeMockMvc.perform(delete("/api/pop-judges/{id}", popJudge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopJudge> popJudgeList = popJudgeRepository.findAll();
        assertThat(popJudgeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopJudge.class);
    }
}
