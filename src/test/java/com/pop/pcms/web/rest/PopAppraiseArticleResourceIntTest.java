package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopAppraiseArticle;
import com.pop.pcms.repository.PopAppraiseArticleRepository;
import com.pop.pcms.service.PopAppraiseArticleService;
import com.pop.pcms.service.dto.PopAppraiseArticleDTO;
import com.pop.pcms.service.mapper.PopAppraiseArticleMapper;

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

import com.pop.pcms.domain.enumeration.AppraiseArticleStatus;
/**
 * Test class for the PopAppraiseArticleResource REST controller.
 *
 * @see PopAppraiseArticleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopAppraiseArticleResourceIntTest {

    private static final AppraiseArticleStatus DEFAULT_STATUS = AppraiseArticleStatus.SELECTED;
    private static final AppraiseArticleStatus UPDATED_STATUS = AppraiseArticleStatus.UNSELECTED;

    private static final String DEFAULT_MULTIPLE_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_MULTIPLE_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_MULTIPLE_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_MULTIPLE_SCORE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REVOTE = false;
    private static final Boolean UPDATED_REVOTE = true;

    @Autowired
    private PopAppraiseArticleRepository popAppraiseArticleRepository;

    @Autowired
    private PopAppraiseArticleMapper popAppraiseArticleMapper;

    @Autowired
    private PopAppraiseArticleService popAppraiseArticleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopAppraiseArticleMockMvc;

    private PopAppraiseArticle popAppraiseArticle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopAppraiseArticleResource popAppraiseArticleResource = new PopAppraiseArticleResource(popAppraiseArticleService);
        this.restPopAppraiseArticleMockMvc = MockMvcBuilders.standaloneSetup(popAppraiseArticleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopAppraiseArticle createEntity(EntityManager em) {
        PopAppraiseArticle popAppraiseArticle = new PopAppraiseArticle()
                .status(DEFAULT_STATUS)
                .multipleFlag(DEFAULT_MULTIPLE_FLAG)
                .multipleScore(DEFAULT_MULTIPLE_SCORE)
                .revote(DEFAULT_REVOTE);
        return popAppraiseArticle;
    }

    @Before
    public void initTest() {
        popAppraiseArticle = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopAppraiseArticle() throws Exception {
        int databaseSizeBeforeCreate = popAppraiseArticleRepository.findAll().size();

        // Create the PopAppraiseArticle
        PopAppraiseArticleDTO popAppraiseArticleDTO = popAppraiseArticleMapper.popAppraiseArticleToPopAppraiseArticleDTO(popAppraiseArticle);

        restPopAppraiseArticleMockMvc.perform(post("/api/pop-appraise-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAppraiseArticleDTO)))
            .andExpect(status().isCreated());

        // Validate the PopAppraiseArticle in the database
        List<PopAppraiseArticle> popAppraiseArticleList = popAppraiseArticleRepository.findAll();
        assertThat(popAppraiseArticleList).hasSize(databaseSizeBeforeCreate + 1);
        PopAppraiseArticle testPopAppraiseArticle = popAppraiseArticleList.get(popAppraiseArticleList.size() - 1);
        assertThat(testPopAppraiseArticle.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPopAppraiseArticle.getMultipleFlag()).isEqualTo(DEFAULT_MULTIPLE_FLAG);
        assertThat(testPopAppraiseArticle.getMultipleScore()).isEqualTo(DEFAULT_MULTIPLE_SCORE);
        assertThat(testPopAppraiseArticle.isRevote()).isEqualTo(DEFAULT_REVOTE);
    }

    @Test
    @Transactional
    public void createPopAppraiseArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popAppraiseArticleRepository.findAll().size();

        // Create the PopAppraiseArticle with an existing ID
        PopAppraiseArticle existingPopAppraiseArticle = new PopAppraiseArticle();
        existingPopAppraiseArticle.setId(1L);
        PopAppraiseArticleDTO existingPopAppraiseArticleDTO = popAppraiseArticleMapper.popAppraiseArticleToPopAppraiseArticleDTO(existingPopAppraiseArticle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopAppraiseArticleMockMvc.perform(post("/api/pop-appraise-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopAppraiseArticleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopAppraiseArticle> popAppraiseArticleList = popAppraiseArticleRepository.findAll();
        assertThat(popAppraiseArticleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopAppraiseArticles() throws Exception {
        // Initialize the database
        popAppraiseArticleRepository.saveAndFlush(popAppraiseArticle);

        // Get all the popAppraiseArticleList
        restPopAppraiseArticleMockMvc.perform(get("/api/pop-appraise-articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popAppraiseArticle.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].multipleFlag").value(hasItem(DEFAULT_MULTIPLE_FLAG.toString())))
            .andExpect(jsonPath("$.[*].multipleScore").value(hasItem(DEFAULT_MULTIPLE_SCORE.toString())))
            .andExpect(jsonPath("$.[*].revote").value(hasItem(DEFAULT_REVOTE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPopAppraiseArticle() throws Exception {
        // Initialize the database
        popAppraiseArticleRepository.saveAndFlush(popAppraiseArticle);

        // Get the popAppraiseArticle
        restPopAppraiseArticleMockMvc.perform(get("/api/pop-appraise-articles/{id}", popAppraiseArticle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popAppraiseArticle.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.multipleFlag").value(DEFAULT_MULTIPLE_FLAG.toString()))
            .andExpect(jsonPath("$.multipleScore").value(DEFAULT_MULTIPLE_SCORE.toString()))
            .andExpect(jsonPath("$.revote").value(DEFAULT_REVOTE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPopAppraiseArticle() throws Exception {
        // Get the popAppraiseArticle
        restPopAppraiseArticleMockMvc.perform(get("/api/pop-appraise-articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopAppraiseArticle() throws Exception {
        // Initialize the database
        popAppraiseArticleRepository.saveAndFlush(popAppraiseArticle);
        int databaseSizeBeforeUpdate = popAppraiseArticleRepository.findAll().size();

        // Update the popAppraiseArticle
        PopAppraiseArticle updatedPopAppraiseArticle = popAppraiseArticleRepository.findOne(popAppraiseArticle.getId());
        updatedPopAppraiseArticle
                .status(UPDATED_STATUS)
                .multipleFlag(UPDATED_MULTIPLE_FLAG)
                .multipleScore(UPDATED_MULTIPLE_SCORE)
                .revote(UPDATED_REVOTE);
        PopAppraiseArticleDTO popAppraiseArticleDTO = popAppraiseArticleMapper.popAppraiseArticleToPopAppraiseArticleDTO(updatedPopAppraiseArticle);

        restPopAppraiseArticleMockMvc.perform(put("/api/pop-appraise-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAppraiseArticleDTO)))
            .andExpect(status().isOk());

        // Validate the PopAppraiseArticle in the database
        List<PopAppraiseArticle> popAppraiseArticleList = popAppraiseArticleRepository.findAll();
        assertThat(popAppraiseArticleList).hasSize(databaseSizeBeforeUpdate);
        PopAppraiseArticle testPopAppraiseArticle = popAppraiseArticleList.get(popAppraiseArticleList.size() - 1);
        assertThat(testPopAppraiseArticle.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPopAppraiseArticle.getMultipleFlag()).isEqualTo(UPDATED_MULTIPLE_FLAG);
        assertThat(testPopAppraiseArticle.getMultipleScore()).isEqualTo(UPDATED_MULTIPLE_SCORE);
        assertThat(testPopAppraiseArticle.isRevote()).isEqualTo(UPDATED_REVOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPopAppraiseArticle() throws Exception {
        int databaseSizeBeforeUpdate = popAppraiseArticleRepository.findAll().size();

        // Create the PopAppraiseArticle
        PopAppraiseArticleDTO popAppraiseArticleDTO = popAppraiseArticleMapper.popAppraiseArticleToPopAppraiseArticleDTO(popAppraiseArticle);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopAppraiseArticleMockMvc.perform(put("/api/pop-appraise-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAppraiseArticleDTO)))
            .andExpect(status().isCreated());

        // Validate the PopAppraiseArticle in the database
        List<PopAppraiseArticle> popAppraiseArticleList = popAppraiseArticleRepository.findAll();
        assertThat(popAppraiseArticleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopAppraiseArticle() throws Exception {
        // Initialize the database
        popAppraiseArticleRepository.saveAndFlush(popAppraiseArticle);
        int databaseSizeBeforeDelete = popAppraiseArticleRepository.findAll().size();

        // Get the popAppraiseArticle
        restPopAppraiseArticleMockMvc.perform(delete("/api/pop-appraise-articles/{id}", popAppraiseArticle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopAppraiseArticle> popAppraiseArticleList = popAppraiseArticleRepository.findAll();
        assertThat(popAppraiseArticleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopAppraiseArticle.class);
    }
}
