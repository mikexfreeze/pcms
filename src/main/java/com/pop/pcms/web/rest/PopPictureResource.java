package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.service.PopPictureService;
import com.pop.pcms.web.rest.util.HeaderUtil;
import com.pop.pcms.web.rest.util.PaginationUtil;
import com.pop.pcms.service.dto.PopPictureDTO;
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
import java.util.stream.Collectors;

/**
 * REST controller for managing PopPicture.
 */
@RestController
@RequestMapping("/api")
public class PopPictureResource {

    private final Logger log = LoggerFactory.getLogger(PopPictureResource.class);

    private static final String ENTITY_NAME = "popPicture";
        
    private final PopPictureService popPictureService;

    public PopPictureResource(PopPictureService popPictureService) {
        this.popPictureService = popPictureService;
    }

    /**
     * POST  /pop-pictures : Create a new popPicture.
     *
     * @param popPictureDTO the popPictureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new popPictureDTO, or with status 400 (Bad Request) if the popPicture has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pop-pictures")
    @Timed
    public ResponseEntity<PopPictureDTO> createPopPicture(@RequestBody PopPictureDTO popPictureDTO) throws URISyntaxException {
        log.debug("REST request to save PopPicture : {}", popPictureDTO);
        if (popPictureDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new popPicture cannot already have an ID")).body(null);
        }
        PopPictureDTO result = popPictureService.save(popPictureDTO);
        return ResponseEntity.created(new URI("/api/pop-pictures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pop-pictures : Updates an existing popPicture.
     *
     * @param popPictureDTO the popPictureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated popPictureDTO,
     * or with status 400 (Bad Request) if the popPictureDTO is not valid,
     * or with status 500 (Internal Server Error) if the popPictureDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pop-pictures")
    @Timed
    public ResponseEntity<PopPictureDTO> updatePopPicture(@RequestBody PopPictureDTO popPictureDTO) throws URISyntaxException {
        log.debug("REST request to update PopPicture : {}", popPictureDTO);
        if (popPictureDTO.getId() == null) {
            return createPopPicture(popPictureDTO);
        }
        PopPictureDTO result = popPictureService.save(popPictureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, popPictureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pop-pictures : get all the popPictures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of popPictures in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pop-pictures")
    @Timed
    public ResponseEntity<List<PopPictureDTO>> getAllPopPictures(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PopPictures");
        Page<PopPictureDTO> page = popPictureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pop-pictures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pop-pictures/:id : get the "id" popPicture.
     *
     * @param id the id of the popPictureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the popPictureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pop-pictures/{id}")
    @Timed
    public ResponseEntity<PopPictureDTO> getPopPicture(@PathVariable Long id) {
        log.debug("REST request to get PopPicture : {}", id);
        PopPictureDTO popPictureDTO = popPictureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(popPictureDTO));
    }

    /**
     * DELETE  /pop-pictures/:id : delete the "id" popPicture.
     *
     * @param id the id of the popPictureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pop-pictures/{id}")
    @Timed
    public ResponseEntity<Void> deletePopPicture(@PathVariable Long id) {
        log.debug("REST request to delete PopPicture : {}", id);
        popPictureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
