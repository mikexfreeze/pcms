package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.service.PopAppraiseArticleService;
import com.pop.pcms.web.rest.util.HeaderUtil;
import com.pop.pcms.web.rest.util.PaginationUtil;
import com.pop.pcms.service.dto.PopAppraiseArticleDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PopAppraiseArticle.
 */
@RestController
@RequestMapping("/api")
public class PopAppraiseArticleResource {

    private final Logger log = LoggerFactory.getLogger(PopAppraiseArticleResource.class);

    private static final String ENTITY_NAME = "popAppraiseArticle";

    private final PopAppraiseArticleService popAppraiseArticleService;

    public PopAppraiseArticleResource(PopAppraiseArticleService popAppraiseArticleService) {
        this.popAppraiseArticleService = popAppraiseArticleService;
    }

    /**
     * POST  /pop-appraise-articles : Create a new popAppraiseArticle.
     *
     * @param popAppraiseArticleDTO the popAppraiseArticleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new popAppraiseArticleDTO, or with status 400 (Bad Request) if the popAppraiseArticle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pop-appraise-articles")
    @Timed
    public ResponseEntity<PopAppraiseArticleDTO> createPopAppraiseArticle(@RequestBody PopAppraiseArticleDTO popAppraiseArticleDTO) throws URISyntaxException {
        log.debug("REST request to save PopAppraiseArticle : {}", popAppraiseArticleDTO);
        if (popAppraiseArticleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new popAppraiseArticle cannot already have an ID")).body(null);
        }
        PopAppraiseArticleDTO result = popAppraiseArticleService.save(popAppraiseArticleDTO);
        return ResponseEntity.created(new URI("/api/pop-appraise-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pop-appraise-articles : Updates an existing popAppraiseArticle.
     *
     * @param popAppraiseArticleDTO the popAppraiseArticleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated popAppraiseArticleDTO,
     * or with status 400 (Bad Request) if the popAppraiseArticleDTO is not valid,
     * or with status 500 (Internal Server Error) if the popAppraiseArticleDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pop-appraise-articles")
    @Timed
    public ResponseEntity<PopAppraiseArticleDTO> updatePopAppraiseArticle(@RequestBody PopAppraiseArticleDTO popAppraiseArticleDTO) throws URISyntaxException {
        log.debug("REST request to update PopAppraiseArticle : {}", popAppraiseArticleDTO);
        if (popAppraiseArticleDTO.getId() == null) {
            return createPopAppraiseArticle(popAppraiseArticleDTO);
        }
        PopAppraiseArticleDTO result = popAppraiseArticleService.save(popAppraiseArticleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, popAppraiseArticleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pop-appraise-articles : get all the popAppraiseArticles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of popAppraiseArticles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pop-appraise-articles")
    @Timed
    public ResponseEntity<List<PopAppraiseArticleDTO>> getAllPopAppraiseArticles(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PopAppraiseArticles");
        Page<PopAppraiseArticleDTO> page = popAppraiseArticleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pop-appraise-articles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pop-appraise-articles/:id : get the "id" popAppraiseArticle.
     *
     * @param id the id of the popAppraiseArticleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the popAppraiseArticleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pop-appraise-articles/{id}")
    @Timed
    public ResponseEntity<PopAppraiseArticleDTO> getPopAppraiseArticle(@PathVariable Long id) {
        log.debug("REST request to get PopAppraiseArticle : {}", id);
        PopAppraiseArticleDTO popAppraiseArticleDTO = popAppraiseArticleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(popAppraiseArticleDTO));
    }

    /**
     * DELETE  /pop-appraise-articles/:id : delete the "id" popAppraiseArticle.
     *
     * @param id the id of the popAppraiseArticleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pop-appraise-articles/{id}")
    @Timed
    public ResponseEntity<Void> deletePopAppraiseArticle(@PathVariable Long id) {
        log.debug("REST request to delete PopAppraiseArticle : {}", id);
        popAppraiseArticleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
