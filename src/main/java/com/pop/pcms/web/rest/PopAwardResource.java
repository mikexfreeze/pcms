package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.service.PopAwardService;
import com.pop.pcms.web.rest.util.HeaderUtil;
import com.pop.pcms.web.rest.util.PaginationUtil;
import com.pop.pcms.service.dto.PopAwardDTO;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PopAward.
 */
@RestController
@RequestMapping("/api")
public class PopAwardResource {

    private final Logger log = LoggerFactory.getLogger(PopAwardResource.class);

    private static final String ENTITY_NAME = "popAward";

    private final PopAwardService popAwardService;

    public PopAwardResource(PopAwardService popAwardService) {
        this.popAwardService = popAwardService;
    }

    /**
     * POST  /pop-awards : Create a new popAward.
     *
     * @param popAwardDTO the popAwardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new popAwardDTO, or with status 400 (Bad Request) if the popAward has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pop-awards")
    @Timed
    public ResponseEntity<PopAwardDTO> createPopAward(@RequestBody PopAwardDTO popAwardDTO) throws URISyntaxException {
        log.debug("REST request to save PopAward : {}", popAwardDTO);
        if (popAwardDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new popAward cannot already have an ID")).body(null);
        }
        PopAwardDTO result = popAwardService.save(popAwardDTO);
        return ResponseEntity.created(new URI("/api/pop-awards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pop-awards : Updates an existing popAward.
     *
     * @param popAwardDTO the popAwardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated popAwardDTO,
     * or with status 400 (Bad Request) if the popAwardDTO is not valid,
     * or with status 500 (Internal Server Error) if the popAwardDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pop-awards")
    @Timed
    public ResponseEntity<PopAwardDTO> updatePopAward(@RequestBody PopAwardDTO popAwardDTO) throws URISyntaxException {
        log.debug("REST request to update PopAward : {}", popAwardDTO);
        if (popAwardDTO.getId() == null) {
            return createPopAward(popAwardDTO);
        }
        PopAwardDTO result = popAwardService.save(popAwardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, popAwardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pop-awards : get all the popAwards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of popAwards in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pop-awards")
    @Timed
    public ResponseEntity<List<PopAwardDTO>> getAllPopAwards(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PopAwards");
        Page<PopAwardDTO> page = popAwardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pop-awards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pop-awards/:id : get the "id" popAward.
     *
     * @param id the id of the popAwardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the popAwardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pop-awards/{id}")
    @Timed
    public ResponseEntity<PopAwardDTO> getPopAward(@PathVariable Long id) {
        log.debug("REST request to get PopAward : {}", id);
        PopAwardDTO popAwardDTO = popAwardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(popAwardDTO));
    }

    /**
     * DELETE  /pop-awards/:id : delete the "id" popAward.
     *
     * @param id the id of the popAwardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pop-awards/{id}")
    @Timed
    public ResponseEntity<Void> deletePopAward(@PathVariable Long id) {
        log.debug("REST request to delete PopAward : {}", id);
        popAwardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
