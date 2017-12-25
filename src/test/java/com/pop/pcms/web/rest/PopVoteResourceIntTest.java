package com.pop.pcms.web.rest;

import com.pop.pcms.PcmsApp;

import com.pop.pcms.config.SecurityBeanOverrideConfiguration;

import com.pop.pcms.domain.PopVote;
import com.pop.pcms.domain.PopContribute;
import com.pop.pcms.repository.PopVoteRepository;
import com.pop.pcms.service.PopVoteService;
import com.pop.pcms.service.dto.PopVoteDTO;
import com.pop.pcms.service.mapper.PopVoteMapper;

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

import com.pop.pcms.domain.enumeration.VoteStatus;
import com.pop.pcms.domain.enumeration.VoteType;
/**
 * Test class for the PopVoteResource REST controller.
 *
 * @see PopVoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PcmsApp.class, SecurityBeanOverrideConfiguration.class})
public class PopVoteResourceIntTest {

    private static final VoteStatus DEFAULT_STATUS = VoteStatus.SELECTED;
    private static final VoteStatus UPDATED_STATUS = VoteStatus.UNSELECTED;

    private static final VoteType DEFAULT_VOTE_TYPE = VoteType.TRUNK;
    private static final VoteType UPDATED_VOTE_TYPE = VoteType.BRANCH;

    @Autowired
    private PopVoteRepository popVoteRepository;

    @Autowired
    private PopVoteMapper popVoteMapper;

    @Autowired
    private PopVoteService popVoteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPopVoteMockMvc;

    private PopVote popVote;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopVoteResource popVoteResource = new PopVoteResource(popVoteService);
        this.restPopVoteMockMvc = MockMvcBuilders.standaloneSetup(popVoteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopVote createEntity(EntityManager em) {
        PopVote popVote = new PopVote()
                .status(DEFAULT_STATUS)
                .voteType(DEFAULT_VOTE_TYPE);
        // Add required entity
        PopContribute contribute = PopContributeResourceIntTest.createEntity(em);
        em.persist(contribute);
        em.flush();
        popVote.setContribute(contribute);
        return popVote;
    }

    @Before
    public void initTest() {
        popVote = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopVote() throws Exception {
        int databaseSizeBeforeCreate = popVoteRepository.findAll().size();

        // Create the PopVote
        PopVoteDTO popVoteDTO = popVoteMapper.popVoteToPopVoteDTO(popVote);

        restPopVoteMockMvc.perform(post("/api/pop-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popVoteDTO)))
            .andExpect(status().isCreated());

        // Validate the PopVote in the database
        List<PopVote> popVoteList = popVoteRepository.findAll();
        assertThat(popVoteList).hasSize(databaseSizeBeforeCreate + 1);
        PopVote testPopVote = popVoteList.get(popVoteList.size() - 1);
        assertThat(testPopVote.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPopVote.getVoteType()).isEqualTo(DEFAULT_VOTE_TYPE);
    }

    @Test
    @Transactional
    public void createPopVoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popVoteRepository.findAll().size();

        // Create the PopVote with an existing ID
        PopVote existingPopVote = new PopVote();
        existingPopVote.setId(1L);
        PopVoteDTO existingPopVoteDTO = popVoteMapper.popVoteToPopVoteDTO(existingPopVote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopVoteMockMvc.perform(post("/api/pop-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopVoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopVote> popVoteList = popVoteRepository.findAll();
        assertThat(popVoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopVotes() throws Exception {
        // Initialize the database
        popVoteRepository.saveAndFlush(popVote);

        // Get all the popVoteList
        restPopVoteMockMvc.perform(get("/api/pop-votes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popVote.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].voteType").value(hasItem(DEFAULT_VOTE_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getPopVote() throws Exception {
        // Initialize the database
        popVoteRepository.saveAndFlush(popVote);

        // Get the popVote
        restPopVoteMockMvc.perform(get("/api/pop-votes/{id}", popVote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popVote.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.voteType").value(DEFAULT_VOTE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPopVote() throws Exception {
        // Get the popVote
        restPopVoteMockMvc.perform(get("/api/pop-votes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopVote() throws Exception {
        // Initialize the database
        popVoteRepository.saveAndFlush(popVote);
        int databaseSizeBeforeUpdate = popVoteRepository.findAll().size();

        // Update the popVote
        PopVote updatedPopVote = popVoteRepository.findOne(popVote.getId());
        updatedPopVote
                .status(UPDATED_STATUS)
                .voteType(UPDATED_VOTE_TYPE);
        PopVoteDTO popVoteDTO = popVoteMapper.popVoteToPopVoteDTO(updatedPopVote);

        restPopVoteMockMvc.perform(put("/api/pop-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popVoteDTO)))
            .andExpect(status().isOk());

        // Validate the PopVote in the database
        List<PopVote> popVoteList = popVoteRepository.findAll();
        assertThat(popVoteList).hasSize(databaseSizeBeforeUpdate);
        PopVote testPopVote = popVoteList.get(popVoteList.size() - 1);
        assertThat(testPopVote.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPopVote.getVoteType()).isEqualTo(UPDATED_VOTE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPopVote() throws Exception {
        int databaseSizeBeforeUpdate = popVoteRepository.findAll().size();

        // Create the PopVote
        PopVoteDTO popVoteDTO = popVoteMapper.popVoteToPopVoteDTO(popVote);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopVoteMockMvc.perform(put("/api/pop-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popVoteDTO)))
            .andExpect(status().isCreated());

        // Validate the PopVote in the database
        List<PopVote> popVoteList = popVoteRepository.findAll();
        assertThat(popVoteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopVote() throws Exception {
        // Initialize the database
        popVoteRepository.saveAndFlush(popVote);
        int databaseSizeBeforeDelete = popVoteRepository.findAll().size();

        // Get the popVote
        restPopVoteMockMvc.perform(delete("/api/pop-votes/{id}", popVote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopVote> popVoteList = popVoteRepository.findAll();
        assertThat(popVoteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopVote.class);
    }
}
