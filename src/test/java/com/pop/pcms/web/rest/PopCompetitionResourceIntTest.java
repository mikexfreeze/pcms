package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopCompetition;
import com.pop.pcms.repository.PopCompetitionRepository;
import com.pop.pcms.service.PopCompetitionService;
import com.pop.pcms.service.dto.PopCompetitionDTO;
import com.pop.pcms.service.mapper.PopCompetitionMapper;

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

import com.pop.pcms.domain.enumeration.CompetitionStatus;
import com.pop.pcms.domain.enumeration.ArticleType;
import com.pop.pcms.domain.enumeration.CompetitionType;
/**
 * Test class for the PopCompetitionResource REST controller.
 *
 * @see PopCompetitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopCompetitionResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final CompetitionStatus DEFAULT_STATUS = CompetitionStatus.NOTSTART;
    private static final CompetitionStatus UPDATED_STATUS = CompetitionStatus.CONTRIBUTE_BEGIN;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_STOP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STOP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RESULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_RESULT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_URL = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_DIR = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_DIR = "BBBBBBBBBB";

    private static final ArticleType DEFAULT_ARTICLE_TYPE = ArticleType.SINGLE;
    private static final ArticleType UPDATED_ARTICLE_TYPE = ArticleType.GROUP;

    private static final CompetitionType DEFAULT_COMPETITION_TYPE = CompetitionType.ONLINE;
    private static final CompetitionType UPDATED_COMPETITION_TYPE = CompetitionType.OFFLINE;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private PopCompetitionRepository popCompetitionRepository;

    @Autowired
    private PopCompetitionMapper popCompetitionMapper;

    @Autowired
    private PopCompetitionService popCompetitionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopCompetitionMockMvc;

    private PopCompetition popCompetition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopCompetitionResource popCompetitionResource = new PopCompetitionResource(popCompetitionService);
        this.restPopCompetitionMockMvc = MockMvcBuilders.standaloneSetup(popCompetitionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopCompetition createEntity(EntityManager em) {
        PopCompetition popCompetition = new PopCompetition()
                .title(DEFAULT_TITLE)
                .status(DEFAULT_STATUS)
                .startDate(DEFAULT_START_DATE)
                .stopDate(DEFAULT_STOP_DATE)
                .resultUrl(DEFAULT_RESULT_URL)
                .contentUrl(DEFAULT_CONTENT_URL)
                .assetDir(DEFAULT_ASSET_DIR)
                .articleType(DEFAULT_ARTICLE_TYPE)
                .competitionType(DEFAULT_COMPETITION_TYPE)
                .remark(DEFAULT_REMARK);
        return popCompetition;
    }

    @Before
    public void initTest() {
        popCompetition = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopCompetition() throws Exception {
        int databaseSizeBeforeCreate = popCompetitionRepository.findAll().size();

        // Create the PopCompetition
        PopCompetitionDTO popCompetitionDTO = popCompetitionMapper.popCompetitionToPopCompetitionDTO(popCompetition);

        restPopCompetitionMockMvc.perform(post("/api/pop-competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popCompetitionDTO)))
            .andExpect(status().isCreated());

        // Validate the PopCompetition in the database
        List<PopCompetition> popCompetitionList = popCompetitionRepository.findAll();
        assertThat(popCompetitionList).hasSize(databaseSizeBeforeCreate + 1);
        PopCompetition testPopCompetition = popCompetitionList.get(popCompetitionList.size() - 1);
        assertThat(testPopCompetition.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPopCompetition.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPopCompetition.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPopCompetition.getStopDate()).isEqualTo(DEFAULT_STOP_DATE);
        assertThat(testPopCompetition.getResultUrl()).isEqualTo(DEFAULT_RESULT_URL);
        assertThat(testPopCompetition.getContentUrl()).isEqualTo(DEFAULT_CONTENT_URL);
        assertThat(testPopCompetition.getAssetDir()).isEqualTo(DEFAULT_ASSET_DIR);
        assertThat(testPopCompetition.getArticleType()).isEqualTo(DEFAULT_ARTICLE_TYPE);
        assertThat(testPopCompetition.getCompetitionType()).isEqualTo(DEFAULT_COMPETITION_TYPE);
        assertThat(testPopCompetition.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createPopCompetitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popCompetitionRepository.findAll().size();

        // Create the PopCompetition with an existing ID
        PopCompetition existingPopCompetition = new PopCompetition();
        existingPopCompetition.setId(1L);
        PopCompetitionDTO existingPopCompetitionDTO = popCompetitionMapper.popCompetitionToPopCompetitionDTO(existingPopCompetition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopCompetitionMockMvc.perform(post("/api/pop-competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopCompetitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopCompetition> popCompetitionList = popCompetitionRepository.findAll();
        assertThat(popCompetitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopCompetitions() throws Exception {
        // Initialize the database
        popCompetitionRepository.saveAndFlush(popCompetition);

        // Get all the popCompetitionList
        restPopCompetitionMockMvc.perform(get("/api/pop-competitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popCompetition.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].stopDate").value(hasItem(DEFAULT_STOP_DATE.toString())))
            .andExpect(jsonPath("$.[*].resultUrl").value(hasItem(DEFAULT_RESULT_URL.toString())))
            .andExpect(jsonPath("$.[*].contentUrl").value(hasItem(DEFAULT_CONTENT_URL.toString())))
            .andExpect(jsonPath("$.[*].assetDir").value(hasItem(DEFAULT_ASSET_DIR.toString())))
            .andExpect(jsonPath("$.[*].articleType").value(hasItem(DEFAULT_ARTICLE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].competitionType").value(hasItem(DEFAULT_COMPETITION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getPopCompetition() throws Exception {
        // Initialize the database
        popCompetitionRepository.saveAndFlush(popCompetition);

        // Get the popCompetition
        restPopCompetitionMockMvc.perform(get("/api/pop-competitions/{id}", popCompetition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popCompetition.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.stopDate").value(DEFAULT_STOP_DATE.toString()))
            .andExpect(jsonPath("$.resultUrl").value(DEFAULT_RESULT_URL.toString()))
            .andExpect(jsonPath("$.contentUrl").value(DEFAULT_CONTENT_URL.toString()))
            .andExpect(jsonPath("$.assetDir").value(DEFAULT_ASSET_DIR.toString()))
            .andExpect(jsonPath("$.articleType").value(DEFAULT_ARTICLE_TYPE.toString()))
            .andExpect(jsonPath("$.competitionType").value(DEFAULT_COMPETITION_TYPE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPopCompetition() throws Exception {
        // Get the popCompetition
        restPopCompetitionMockMvc.perform(get("/api/pop-competitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopCompetition() throws Exception {
        // Initialize the database
        popCompetitionRepository.saveAndFlush(popCompetition);
        int databaseSizeBeforeUpdate = popCompetitionRepository.findAll().size();

        // Update the popCompetition
        PopCompetition updatedPopCompetition = popCompetitionRepository.findOne(popCompetition.getId());
        updatedPopCompetition
                .title(UPDATED_TITLE)
                .status(UPDATED_STATUS)
                .startDate(UPDATED_START_DATE)
                .stopDate(UPDATED_STOP_DATE)
                .resultUrl(UPDATED_RESULT_URL)
                .contentUrl(UPDATED_CONTENT_URL)
                .assetDir(UPDATED_ASSET_DIR)
                .articleType(UPDATED_ARTICLE_TYPE)
                .competitionType(UPDATED_COMPETITION_TYPE)
                .remark(UPDATED_REMARK);
        PopCompetitionDTO popCompetitionDTO = popCompetitionMapper.popCompetitionToPopCompetitionDTO(updatedPopCompetition);

        restPopCompetitionMockMvc.perform(put("/api/pop-competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popCompetitionDTO)))
            .andExpect(status().isOk());

        // Validate the PopCompetition in the database
        List<PopCompetition> popCompetitionList = popCompetitionRepository.findAll();
        assertThat(popCompetitionList).hasSize(databaseSizeBeforeUpdate);
        PopCompetition testPopCompetition = popCompetitionList.get(popCompetitionList.size() - 1);
        assertThat(testPopCompetition.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPopCompetition.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPopCompetition.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPopCompetition.getStopDate()).isEqualTo(UPDATED_STOP_DATE);
        assertThat(testPopCompetition.getResultUrl()).isEqualTo(UPDATED_RESULT_URL);
        assertThat(testPopCompetition.getContentUrl()).isEqualTo(UPDATED_CONTENT_URL);
        assertThat(testPopCompetition.getAssetDir()).isEqualTo(UPDATED_ASSET_DIR);
        assertThat(testPopCompetition.getArticleType()).isEqualTo(UPDATED_ARTICLE_TYPE);
        assertThat(testPopCompetition.getCompetitionType()).isEqualTo(UPDATED_COMPETITION_TYPE);
        assertThat(testPopCompetition.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingPopCompetition() throws Exception {
        int databaseSizeBeforeUpdate = popCompetitionRepository.findAll().size();

        // Create the PopCompetition
        PopCompetitionDTO popCompetitionDTO = popCompetitionMapper.popCompetitionToPopCompetitionDTO(popCompetition);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopCompetitionMockMvc.perform(put("/api/pop-competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popCompetitionDTO)))
            .andExpect(status().isCreated());

        // Validate the PopCompetition in the database
        List<PopCompetition> popCompetitionList = popCompetitionRepository.findAll();
        assertThat(popCompetitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopCompetition() throws Exception {
        // Initialize the database
        popCompetitionRepository.saveAndFlush(popCompetition);
        int databaseSizeBeforeDelete = popCompetitionRepository.findAll().size();

        // Get the popCompetition
        restPopCompetitionMockMvc.perform(delete("/api/pop-competitions/{id}", popCompetition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopCompetition> popCompetitionList = popCompetitionRepository.findAll();
        assertThat(popCompetitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopCompetition.class);
    }
}
