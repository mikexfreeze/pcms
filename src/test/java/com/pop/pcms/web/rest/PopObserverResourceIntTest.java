package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopObserver;
import com.pop.pcms.repository.PopObserverRepository;
import com.pop.pcms.service.PopObserverService;
import com.pop.pcms.service.dto.PopObserverDTO;
import com.pop.pcms.service.mapper.PopObserverMapper;

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
 * Test class for the PopObserverResource REST controller.
 *
 * @see PopObserverResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopObserverResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    @Autowired
    private PopObserverRepository popObserverRepository;

    @Autowired
    private PopObserverMapper popObserverMapper;

    @Autowired
    private PopObserverService popObserverService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopObserverMockMvc;

    private PopObserver popObserver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopObserverResource popObserverResource = new PopObserverResource(popObserverService);
        this.restPopObserverMockMvc = MockMvcBuilders.standaloneSetup(popObserverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopObserver createEntity(EntityManager em) {
        PopObserver popObserver = new PopObserver()
                .userId(DEFAULT_USER_ID)
                .userName(DEFAULT_USER_NAME);
        return popObserver;
    }

    @Before
    public void initTest() {
        popObserver = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopObserver() throws Exception {
        int databaseSizeBeforeCreate = popObserverRepository.findAll().size();

        // Create the PopObserver
        PopObserverDTO popObserverDTO = popObserverMapper.popObserverToPopObserverDTO(popObserver);

        restPopObserverMockMvc.perform(post("/api/pop-observers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popObserverDTO)))
            .andExpect(status().isCreated());

        // Validate the PopObserver in the database
        List<PopObserver> popObserverList = popObserverRepository.findAll();
        assertThat(popObserverList).hasSize(databaseSizeBeforeCreate + 1);
        PopObserver testPopObserver = popObserverList.get(popObserverList.size() - 1);
        assertThat(testPopObserver.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPopObserver.getUserName()).isEqualTo(DEFAULT_USER_NAME);
    }

    @Test
    @Transactional
    public void createPopObserverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popObserverRepository.findAll().size();

        // Create the PopObserver with an existing ID
        PopObserver existingPopObserver = new PopObserver();
        existingPopObserver.setId(1L);
        PopObserverDTO existingPopObserverDTO = popObserverMapper.popObserverToPopObserverDTO(existingPopObserver);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopObserverMockMvc.perform(post("/api/pop-observers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopObserverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopObserver> popObserverList = popObserverRepository.findAll();
        assertThat(popObserverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopObservers() throws Exception {
        // Initialize the database
        popObserverRepository.saveAndFlush(popObserver);

        // Get all the popObserverList
        restPopObserverMockMvc.perform(get("/api/pop-observers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popObserver.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPopObserver() throws Exception {
        // Initialize the database
        popObserverRepository.saveAndFlush(popObserver);

        // Get the popObserver
        restPopObserverMockMvc.perform(get("/api/pop-observers/{id}", popObserver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popObserver.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPopObserver() throws Exception {
        // Get the popObserver
        restPopObserverMockMvc.perform(get("/api/pop-observers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopObserver() throws Exception {
        // Initialize the database
        popObserverRepository.saveAndFlush(popObserver);
        int databaseSizeBeforeUpdate = popObserverRepository.findAll().size();

        // Update the popObserver
        PopObserver updatedPopObserver = popObserverRepository.findOne(popObserver.getId());
        updatedPopObserver
                .userId(UPDATED_USER_ID)
                .userName(UPDATED_USER_NAME);
        PopObserverDTO popObserverDTO = popObserverMapper.popObserverToPopObserverDTO(updatedPopObserver);

        restPopObserverMockMvc.perform(put("/api/pop-observers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popObserverDTO)))
            .andExpect(status().isOk());

        // Validate the PopObserver in the database
        List<PopObserver> popObserverList = popObserverRepository.findAll();
        assertThat(popObserverList).hasSize(databaseSizeBeforeUpdate);
        PopObserver testPopObserver = popObserverList.get(popObserverList.size() - 1);
        assertThat(testPopObserver.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPopObserver.getUserName()).isEqualTo(UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPopObserver() throws Exception {
        int databaseSizeBeforeUpdate = popObserverRepository.findAll().size();

        // Create the PopObserver
        PopObserverDTO popObserverDTO = popObserverMapper.popObserverToPopObserverDTO(popObserver);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopObserverMockMvc.perform(put("/api/pop-observers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popObserverDTO)))
            .andExpect(status().isCreated());

        // Validate the PopObserver in the database
        List<PopObserver> popObserverList = popObserverRepository.findAll();
        assertThat(popObserverList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopObserver() throws Exception {
        // Initialize the database
        popObserverRepository.saveAndFlush(popObserver);
        int databaseSizeBeforeDelete = popObserverRepository.findAll().size();

        // Get the popObserver
        restPopObserverMockMvc.perform(delete("/api/pop-observers/{id}", popObserver.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopObserver> popObserverList = popObserverRepository.findAll();
        assertThat(popObserverList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopObserver.class);
    }
}
