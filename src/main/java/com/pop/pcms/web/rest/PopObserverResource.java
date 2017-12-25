package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.service.PopObserverService;
import com.pop.pcms.vo.ErrorCode;
import com.pop.pcms.vo.RequestVO;
import com.pop.pcms.vo.ResultDto;
import com.pop.pcms.web.rest.util.BaseValidatorUtil;
import com.pop.pcms.web.rest.util.ErrorMsgUtils;
import com.pop.pcms.web.rest.util.HeaderUtil;
import com.pop.pcms.web.rest.util.PaginationUtil;
import com.pop.pcms.service.dto.PopObserverDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
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
 * REST controller for managing PopObserver.
 */
@RestController
@RequestMapping("/api")
public class PopObserverResource {

    private final Logger log = LoggerFactory.getLogger(PopObserverResource.class);

    private static final String ENTITY_NAME = "popObserver";
    private static final String TAG_OBSERVER = "大众摄影观察员";

    private final PopObserverService popObserverService;

    public PopObserverResource(PopObserverService popObserverService) {
        this.popObserverService = popObserverService;
    }

    /**
     * POST  /add-observer : Create a new popObserver.
     *
     * @param popObserverDTO the popObserverDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new popObserverDTO, or with status 400 (Bad Request) if the popObserver has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-observer")
    @Timed
    @ApiOperation(value = "添加观察员api", tags = {TAG_OBSERVER}, notes = "大众摄影观察员添加。")
    public ResponseEntity<ResultDto<PopObserverDTO>> createPopObserver(@RequestBody RequestVO<PopObserverDTO> popObserverDTO) {
        try {
            log.debug("REST request to save PopCompetition : {}", popObserverDTO);
            //获取入参中的PopObserverDTO对象进行业务处理
            PopObserverDTO dto = popObserverDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String validMsg = BaseValidatorUtil.valConstraint(dto);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            if (dto.getId() != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new popObserver cannot already have an ID")).body(null);
            }
            //PopObserverDTO对象入库
            PopObserverDTO result = popObserverService.save(dto);
            ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "成功!");
            return ResponseEntity.created(new URI("/api/pop-competitions/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(resultDto);
        } catch (Exception e) {
            log.error("PopObserverResource->createPopObserver发生系统异常:{}", e);
            ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }

    }

    /**
     * POST  /del-pop-observers
     * 删除大众摄影观察员
     */
    @PostMapping("/del-pop-observers")
    @Timed
    @ApiOperation(value = "删除大众摄影观察员api", tags = {TAG_OBSERVER}, notes = "大众摄影观察员删除。")
    public ResponseEntity<ResultDto<PopObserverDTO>> delObserver(@RequestBody RequestVO<PopObserverDTO> popObserverDTO) {
        try {
            //获取入参中的PopObserverDTO对象进行业务处理
            PopObserverDTO dto = popObserverDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //PopObserverDTO对象入库
            Long id = dto.getId();
            popObserverService.delete(id);
            ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SUCCESS, "删除成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopObserverResource->delObserver发生系统异常:{}", e);
            ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }

    }

    /**
     * POST  /update-pop-observers
     * 更新大众摄影观察员
     */
    @PostMapping("/update-pop-observers")
    @Timed
    @ApiOperation(value = "更新大众摄影观察员api", tags = {TAG_OBSERVER}, notes = "大众摄影观察员添加。")
    public ResponseEntity<ResultDto<PopObserverDTO>> updatePopObserver(@RequestBody RequestVO<PopObserverDTO> popObserverDTO) {
        try {
            //获取入参中的PopObserverDTO对象进行业务处理
            PopObserverDTO dto = popObserverDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1", "pf2", "pf3", "pf4", "pf5"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //如果ID为空或者ID为0  则不进行更新操作
            if (dto.getId() == null || dto.getId() == 0l) {
                //返回错误的结果集
                ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, "id不能为空!");
                return ResponseEntity.ok().body(resultDto);
            }
            //PopObserverDTO对象入库
            PopObserverDTO result = popObserverService.save(dto);
            ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "更新成功!");
            return ResponseEntity.created(new URI("/api/pop-observers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(resultDto);
        } catch (Exception e) {
            log.error("PopObserverResource->updatePopObserver发生系统异常:{}", e);
            ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * POST  /list-pop-observers
     * 列表大众摄影观察员
     */
    @PostMapping("/list-pop-observers")
    @Timed
    @ApiOperation(value = "查询列表大众摄影观察员api", tags = {TAG_OBSERVER}, notes = "大众摄影观察员列表。")
    public ResponseEntity<ResultDto<PopObserverDTO>> listPopObserver(@RequestBody RequestVO<PopObserverDTO> popObserverDTO, @ApiParam Pageable pageable) {
        try {
            //获取入参中的PopObserverDTO对象进行业务处理
            PopObserverDTO dto = popObserverDTO.getData();
            Page<PopObserverDTO> page = popObserverService.findAll(pageable);
            ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(page, ErrorCode.REQUEST_SUCCESS, "查询成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopObserverResource->listPopObserver发生系统异常:{}", e);
            ResultDto<PopObserverDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }
//
//    /**
//     * POST  /pop-observers : Create a new popObserver.
//     *
//     * @param popObserverDTO the popObserverDTO to create
//     * @return the ResponseEntity with status 201 (Created) and with body the new popObserverDTO, or with status 400 (Bad Request) if the popObserver has already an ID
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PostMapping("/pop-observers")
//    @Timed
//    public ResponseEntity<PopObserverDTO> createPopObserver(@RequestBody PopObserverDTO popObserverDTO) throws URISyntaxException {
//        log.debug("REST request to save PopObserver : {}", popObserverDTO);
//        if (popObserverDTO.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new popObserver cannot already have an ID")).body(null);
//        }
//        PopObserverDTO result = popObserverService.save(popObserverDTO);
//        return ResponseEntity.created(new URI("/api/pop-observers/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * PUT  /pop-observers : Updates an existing popObserver.
//     *
//     * @param popObserverDTO the popObserverDTO to update
//     * @return the ResponseEntity with status 200 (OK) and with body the updated popObserverDTO,
//     * or with status 400 (Bad Request) if the popObserverDTO is not valid,
//     * or with status 500 (Internal Server Error) if the popObserverDTO couldnt be updated
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PutMapping("/pop-observers")
//    @Timed
//    public ResponseEntity<PopObserverDTO> updatePopObserver(@RequestBody PopObserverDTO popObserverDTO) throws URISyntaxException {
//        log.debug("REST request to update PopObserver : {}", popObserverDTO);
//        if (popObserverDTO.getId() == null) {
//            return createPopObserver(popObserverDTO);
//        }
//        PopObserverDTO result = popObserverService.save(popObserverDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, popObserverDTO.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * GET  /pop-observers : get all the popObservers.
//     *
//     * @param pageable the pagination information
//     * @return the ResponseEntity with status 200 (OK) and the list of popObservers in body
//     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
//     */
//    @GetMapping("/pop-observers")
//    @Timed
//    public ResponseEntity<List<PopObserverDTO>> getAllPopObservers(@ApiParam Pageable pageable)
//        throws URISyntaxException {
//        log.debug("REST request to get a page of PopObservers");
//        Page<PopObserverDTO> page = popObserverService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pop-observers");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
//
//    /**
//     * GET  /pop-observers/:id : get the "id" popObserver.
//     *
//     * @param id the id of the popObserverDTO to retrieve
//     * @return the ResponseEntity with status 200 (OK) and with body the popObserverDTO, or with status 404 (Not Found)
//     */
//    @GetMapping("/pop-observers/{id}")
//    @Timed
//    public ResponseEntity<PopObserverDTO> getPopObserver(@PathVariable Long id) {
//        log.debug("REST request to get PopObserver : {}", id);
//        PopObserverDTO popObserverDTO = popObserverService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(popObserverDTO));
//    }
//
//    /**
//     * DELETE  /pop-observers/:id : delete the "id" popObserver.
//     *
//     * @param id the id of the popObserverDTO to delete
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @DeleteMapping("/pop-observers/{id}")
//    @Timed
//    public ResponseEntity<Void> deletePopObserver(@PathVariable Long id) {
//        log.debug("REST request to delete PopObserver : {}", id);
//        popObserverService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }

}
