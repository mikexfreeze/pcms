package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopAward;
import com.pop.pcms.repository.PopAwardRepository;
import com.pop.pcms.service.PopAwardService;
import com.pop.pcms.service.dto.PopAwardDTO;
import com.pop.pcms.service.mapper.PopAwardMapper;

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

import com.pop.pcms.domain.enumeration.AwardStatus;
/**
 * Test class for the PopAwardResource REST controller.
 *
 * @see PopAwardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopAwardResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_BACKGROUND = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND = "BBBBBBBBBB";

    private static final AwardStatus DEFAULT_STATUS = AwardStatus.PUBLISH;
    private static final AwardStatus UPDATED_STATUS = AwardStatus.NOTICE;

    @Autowired
    private PopAwardRepository popAwardRepository;

    @Autowired
    private PopAwardMapper popAwardMapper;

    @Autowired
    private PopAwardService popAwardService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopAwardMockMvc;

    private PopAward popAward;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopAwardResource popAwardResource = new PopAwardResource(popAwardService);
        this.restPopAwardMockMvc = MockMvcBuilders.standaloneSetup(popAwardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopAward createEntity(EntityManager em) {
        PopAward popAward = new PopAward()
                .name(DEFAULT_NAME)
                .content(DEFAULT_CONTENT)
                .background(DEFAULT_BACKGROUND)
                .status(DEFAULT_STATUS);
        return popAward;
    }

    @Before
    public void initTest() {
        popAward = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopAward() throws Exception {
        int databaseSizeBeforeCreate = popAwardRepository.findAll().size();

        // Create the PopAward
        PopAwardDTO popAwardDTO = popAwardMapper.popAwardToPopAwardDTO(popAward);

        restPopAwardMockMvc.perform(post("/api/pop-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAwardDTO)))
            .andExpect(status().isCreated());

        // Validate the PopAward in the database
        List<PopAward> popAwardList = popAwardRepository.findAll();
        assertThat(popAwardList).hasSize(databaseSizeBeforeCreate + 1);
        PopAward testPopAward = popAwardList.get(popAwardList.size() - 1);
        assertThat(testPopAward.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPopAward.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPopAward.getBackground()).isEqualTo(DEFAULT_BACKGROUND);
        assertThat(testPopAward.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPopAwardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popAwardRepository.findAll().size();

        // Create the PopAward with an existing ID
        PopAward existingPopAward = new PopAward();
        existingPopAward.setId(1L);
        PopAwardDTO existingPopAwardDTO = popAwardMapper.popAwardToPopAwardDTO(existingPopAward);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopAwardMockMvc.perform(post("/api/pop-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopAwardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopAward> popAwardList = popAwardRepository.findAll();
        assertThat(popAwardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopAwards() throws Exception {
        // Initialize the database
        popAwardRepository.saveAndFlush(popAward);

        // Get all the popAwardList
        restPopAwardMockMvc.perform(get("/api/pop-awards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popAward.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].background").value(hasItem(DEFAULT_BACKGROUND.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPopAward() throws Exception {
        // Initialize the database
        popAwardRepository.saveAndFlush(popAward);

        // Get the popAward
        restPopAwardMockMvc.perform(get("/api/pop-awards/{id}", popAward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popAward.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.background").value(DEFAULT_BACKGROUND.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPopAward() throws Exception {
        // Get the popAward
        restPopAwardMockMvc.perform(get("/api/pop-awards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopAward() throws Exception {
        // Initialize the database
        popAwardRepository.saveAndFlush(popAward);
        int databaseSizeBeforeUpdate = popAwardRepository.findAll().size();

        // Update the popAward
        PopAward updatedPopAward = popAwardRepository.findOne(popAward.getId());
        updatedPopAward
                .name(UPDATED_NAME)
                .content(UPDATED_CONTENT)
                .background(UPDATED_BACKGROUND)
                .status(UPDATED_STATUS);
        PopAwardDTO popAwardDTO = popAwardMapper.popAwardToPopAwardDTO(updatedPopAward);

        restPopAwardMockMvc.perform(put("/api/pop-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAwardDTO)))
            .andExpect(status().isOk());

        // Validate the PopAward in the database
        List<PopAward> popAwardList = popAwardRepository.findAll();
        assertThat(popAwardList).hasSize(databaseSizeBeforeUpdate);
        PopAward testPopAward = popAwardList.get(popAwardList.size() - 1);
        assertThat(testPopAward.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPopAward.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPopAward.getBackground()).isEqualTo(UPDATED_BACKGROUND);
        assertThat(testPopAward.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPopAward() throws Exception {
        int databaseSizeBeforeUpdate = popAwardRepository.findAll().size();

        // Create the PopAward
        PopAwardDTO popAwardDTO = popAwardMapper.popAwardToPopAwardDTO(popAward);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopAwardMockMvc.perform(put("/api/pop-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAwardDTO)))
            .andExpect(status().isCreated());

        // Validate the PopAward in the database
        List<PopAward> popAwardList = popAwardRepository.findAll();
        assertThat(popAwardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopAward() throws Exception {
        // Initialize the database
        popAwardRepository.saveAndFlush(popAward);
        int databaseSizeBeforeDelete = popAwardRepository.findAll().size();

        // Get the popAward
        restPopAwardMockMvc.perform(delete("/api/pop-awards/{id}", popAward.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopAward> popAwardList = popAwardRepository.findAll();
        assertThat(popAwardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopAward.class);
    }
}
