package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopAwardConfig;
import com.pop.pcms.repository.PopAwardConfigRepository;
import com.pop.pcms.service.PopAwardConfigService;
import com.pop.pcms.service.dto.PopAwardConfigDTO;
import com.pop.pcms.service.mapper.PopAwardConfigMapper;

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

/**
 * Test class for the PopAwardConfigResource REST controller.
 *
 * @see PopAwardConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopAwardConfigResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final String DEFAULT_BACKGROUND = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE = "BBBBBBBBBB";

    @Autowired
    private PopAwardConfigRepository popAwardConfigRepository;

    @Autowired
    private PopAwardConfigMapper popAwardConfigMapper;

    @Autowired
    private PopAwardConfigService popAwardConfigService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopAwardConfigMockMvc;

    private PopAwardConfig popAwardConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopAwardConfigResource popAwardConfigResource = new PopAwardConfigResource(popAwardConfigService);
        this.restPopAwardConfigMockMvc = MockMvcBuilders.standaloneSetup(popAwardConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopAwardConfig createEntity(EntityManager em) {
        PopAwardConfig popAwardConfig = new PopAwardConfig()
                .name(DEFAULT_NAME)
                .amount(DEFAULT_AMOUNT)
                .background(DEFAULT_BACKGROUND)
                .template(DEFAULT_TEMPLATE);
        return popAwardConfig;
    }

    @Before
    public void initTest() {
        popAwardConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopAwardConfig() throws Exception {
        int databaseSizeBeforeCreate = popAwardConfigRepository.findAll().size();

        // Create the PopAwardConfig
        PopAwardConfigDTO popAwardConfigDTO = popAwardConfigMapper.popAwardConfigToPopAwardConfigDTO(popAwardConfig);

        restPopAwardConfigMockMvc.perform(post("/api/pop-award-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAwardConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the PopAwardConfig in the database
        List<PopAwardConfig> popAwardConfigList = popAwardConfigRepository.findAll();
        assertThat(popAwardConfigList).hasSize(databaseSizeBeforeCreate + 1);
        PopAwardConfig testPopAwardConfig = popAwardConfigList.get(popAwardConfigList.size() - 1);
        assertThat(testPopAwardConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPopAwardConfig.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPopAwardConfig.getBackground()).isEqualTo(DEFAULT_BACKGROUND);
        assertThat(testPopAwardConfig.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    public void createPopAwardConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popAwardConfigRepository.findAll().size();

        // Create the PopAwardConfig with an existing ID
        PopAwardConfig existingPopAwardConfig = new PopAwardConfig();
        existingPopAwardConfig.setId(1L);
        PopAwardConfigDTO existingPopAwardConfigDTO = popAwardConfigMapper.popAwardConfigToPopAwardConfigDTO(existingPopAwardConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopAwardConfigMockMvc.perform(post("/api/pop-award-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopAwardConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopAwardConfig> popAwardConfigList = popAwardConfigRepository.findAll();
        assertThat(popAwardConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopAwardConfigs() throws Exception {
        // Initialize the database
        popAwardConfigRepository.saveAndFlush(popAwardConfig);

        // Get all the popAwardConfigList
        restPopAwardConfigMockMvc.perform(get("/api/pop-award-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popAwardConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].background").value(hasItem(DEFAULT_BACKGROUND.toString())))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE.toString())));
    }

    @Test
    @Transactional
    public void getPopAwardConfig() throws Exception {
        // Initialize the database
        popAwardConfigRepository.saveAndFlush(popAwardConfig);

        // Get the popAwardConfig
        restPopAwardConfigMockMvc.perform(get("/api/pop-award-configs/{id}", popAwardConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popAwardConfig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.background").value(DEFAULT_BACKGROUND.toString()))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPopAwardConfig() throws Exception {
        // Get the popAwardConfig
        restPopAwardConfigMockMvc.perform(get("/api/pop-award-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopAwardConfig() throws Exception {
        // Initialize the database
        popAwardConfigRepository.saveAndFlush(popAwardConfig);
        int databaseSizeBeforeUpdate = popAwardConfigRepository.findAll().size();

        // Update the popAwardConfig
        PopAwardConfig updatedPopAwardConfig = popAwardConfigRepository.findOne(popAwardConfig.getId());
        updatedPopAwardConfig
                .name(UPDATED_NAME)
                .amount(UPDATED_AMOUNT)
                .background(UPDATED_BACKGROUND)
                .template(UPDATED_TEMPLATE);
        PopAwardConfigDTO popAwardConfigDTO = popAwardConfigMapper.popAwardConfigToPopAwardConfigDTO(updatedPopAwardConfig);

        restPopAwardConfigMockMvc.perform(put("/api/pop-award-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAwardConfigDTO)))
            .andExpect(status().isOk());

        // Validate the PopAwardConfig in the database
        List<PopAwardConfig> popAwardConfigList = popAwardConfigRepository.findAll();
        assertThat(popAwardConfigList).hasSize(databaseSizeBeforeUpdate);
        PopAwardConfig testPopAwardConfig = popAwardConfigList.get(popAwardConfigList.size() - 1);
        assertThat(testPopAwardConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPopAwardConfig.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPopAwardConfig.getBackground()).isEqualTo(UPDATED_BACKGROUND);
        assertThat(testPopAwardConfig.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPopAwardConfig() throws Exception {
        int databaseSizeBeforeUpdate = popAwardConfigRepository.findAll().size();

        // Create the PopAwardConfig
        PopAwardConfigDTO popAwardConfigDTO = popAwardConfigMapper.popAwardConfigToPopAwardConfigDTO(popAwardConfig);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopAwardConfigMockMvc.perform(put("/api/pop-award-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popAwardConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the PopAwardConfig in the database
        List<PopAwardConfig> popAwardConfigList = popAwardConfigRepository.findAll();
        assertThat(popAwardConfigList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopAwardConfig() throws Exception {
        // Initialize the database
        popAwardConfigRepository.saveAndFlush(popAwardConfig);
        int databaseSizeBeforeDelete = popAwardConfigRepository.findAll().size();

        // Get the popAwardConfig
        restPopAwardConfigMockMvc.perform(delete("/api/pop-award-configs/{id}", popAwardConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopAwardConfig> popAwardConfigList = popAwardConfigRepository.findAll();
        assertThat(popAwardConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopAwardConfig.class);
    }
}
