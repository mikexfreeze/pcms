package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopContribute;
import com.pop.pcms.repository.PopContributeRepository;
import com.pop.pcms.service.PopContributeService;
import com.pop.pcms.service.dto.PopContributeDTO;
import com.pop.pcms.service.mapper.PopContributeMapper;

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

import com.pop.pcms.domain.enumeration.ContributeType;
import com.pop.pcms.domain.enumeration.ContributeStatus;
/**
 * Test class for the PopContributeResource REST controller.
 *
 * @see PopContributeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopContributeResourceIntTest {

    private static final ContributeType DEFAULT_CONTRIBUTE_TYPE = ContributeType.SINGLE;
    private static final ContributeType UPDATED_CONTRIBUTE_TYPE = ContributeType.MULTIPLE;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_DIR = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_DIR = "BBBBBBBBBB";

    private static final ContributeStatus DEFAULT_STATUS = ContributeStatus.NORMAL;
    private static final ContributeStatus UPDATED_STATUS = ContributeStatus.DELETE;

    private static final Long DEFAULT_AUTHOR = 1L;
    private static final Long UPDATED_AUTHOR = 2L;

    @Autowired
    private PopContributeRepository popContributeRepository;

    @Autowired
    private PopContributeMapper popContributeMapper;

    @Autowired
    private PopContributeService popContributeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopContributeMockMvc;

    private PopContribute popContribute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopContributeResource popContributeResource = new PopContributeResource(popContributeService);
        this.restPopContributeMockMvc = MockMvcBuilders.standaloneSetup(popContributeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopContribute createEntity(EntityManager em) {
        PopContribute popContribute = new PopContribute()
                .contributeType(DEFAULT_CONTRIBUTE_TYPE)
                .title(DEFAULT_TITLE)
                .assetDir(DEFAULT_ASSET_DIR)
                .status(DEFAULT_STATUS)
                .author(DEFAULT_AUTHOR);
        return popContribute;
    }

    @Before
    public void initTest() {
        popContribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopContribute() throws Exception {
        int databaseSizeBeforeCreate = popContributeRepository.findAll().size();

        // Create the PopContribute
        PopContributeDTO popContributeDTO = popContributeMapper.popContributeToPopContributeDTO(popContribute);

        restPopContributeMockMvc.perform(post("/api/pop-contributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popContributeDTO)))
            .andExpect(status().isCreated());

        // Validate the PopContribute in the database
        List<PopContribute> popContributeList = popContributeRepository.findAll();
        assertThat(popContributeList).hasSize(databaseSizeBeforeCreate + 1);
        PopContribute testPopContribute = popContributeList.get(popContributeList.size() - 1);
        assertThat(testPopContribute.getContributeType()).isEqualTo(DEFAULT_CONTRIBUTE_TYPE);
        assertThat(testPopContribute.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPopContribute.getAssetDir()).isEqualTo(DEFAULT_ASSET_DIR);
        assertThat(testPopContribute.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPopContribute.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
    }

    @Test
    @Transactional
    public void createPopContributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popContributeRepository.findAll().size();

        // Create the PopContribute with an existing ID
        PopContribute existingPopContribute = new PopContribute();
        existingPopContribute.setId(1L);
        PopContributeDTO existingPopContributeDTO = popContributeMapper.popContributeToPopContributeDTO(existingPopContribute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopContributeMockMvc.perform(post("/api/pop-contributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopContributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopContribute> popContributeList = popContributeRepository.findAll();
        assertThat(popContributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopContributes() throws Exception {
        // Initialize the database
        popContributeRepository.saveAndFlush(popContribute);

        // Get all the popContributeList
        restPopContributeMockMvc.perform(get("/api/pop-contributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popContribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].contributeType").value(hasItem(DEFAULT_CONTRIBUTE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].assetDir").value(hasItem(DEFAULT_ASSET_DIR.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.intValue())));
    }

    @Test
    @Transactional
    public void getPopContribute() throws Exception {
        // Initialize the database
        popContributeRepository.saveAndFlush(popContribute);

        // Get the popContribute
        restPopContributeMockMvc.perform(get("/api/pop-contributes/{id}", popContribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popContribute.getId().intValue()))
            .andExpect(jsonPath("$.contributeType").value(DEFAULT_CONTRIBUTE_TYPE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.assetDir").value(DEFAULT_ASSET_DIR.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPopContribute() throws Exception {
        // Get the popContribute
        restPopContributeMockMvc.perform(get("/api/pop-contributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopContribute() throws Exception {
        // Initialize the database
        popContributeRepository.saveAndFlush(popContribute);
        int databaseSizeBeforeUpdate = popContributeRepository.findAll().size();

        // Update the popContribute
        PopContribute updatedPopContribute = popContributeRepository.findOne(popContribute.getId());
        updatedPopContribute
                .contributeType(UPDATED_CONTRIBUTE_TYPE)
                .title(UPDATED_TITLE)
                .assetDir(UPDATED_ASSET_DIR)
                .status(UPDATED_STATUS)
                .author(UPDATED_AUTHOR);
        PopContributeDTO popContributeDTO = popContributeMapper.popContributeToPopContributeDTO(updatedPopContribute);

        restPopContributeMockMvc.perform(put("/api/pop-contributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popContributeDTO)))
            .andExpect(status().isOk());

        // Validate the PopContribute in the database
        List<PopContribute> popContributeList = popContributeRepository.findAll();
        assertThat(popContributeList).hasSize(databaseSizeBeforeUpdate);
        PopContribute testPopContribute = popContributeList.get(popContributeList.size() - 1);
        assertThat(testPopContribute.getContributeType()).isEqualTo(UPDATED_CONTRIBUTE_TYPE);
        assertThat(testPopContribute.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPopContribute.getAssetDir()).isEqualTo(UPDATED_ASSET_DIR);
        assertThat(testPopContribute.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPopContribute.getAuthor()).isEqualTo(UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void updateNonExistingPopContribute() throws Exception {
        int databaseSizeBeforeUpdate = popContributeRepository.findAll().size();

        // Create the PopContribute
        PopContributeDTO popContributeDTO = popContributeMapper.popContributeToPopContributeDTO(popContribute);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopContributeMockMvc.perform(put("/api/pop-contributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popContributeDTO)))
            .andExpect(status().isCreated());

        // Validate the PopContribute in the database
        List<PopContribute> popContributeList = popContributeRepository.findAll();
        assertThat(popContributeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopContribute() throws Exception {
        // Initialize the database
        popContributeRepository.saveAndFlush(popContribute);
        int databaseSizeBeforeDelete = popContributeRepository.findAll().size();

        // Get the popContribute
        restPopContributeMockMvc.perform(delete("/api/pop-contributes/{id}", popContribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopContribute> popContributeList = popContributeRepository.findAll();
        assertThat(popContributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopContribute.class);
    }
}
