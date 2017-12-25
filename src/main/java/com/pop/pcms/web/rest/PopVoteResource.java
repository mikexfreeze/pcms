package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.service.PopVoteService;
import com.pop.pcms.vo.ErrorCode;
import com.pop.pcms.vo.RequestVO;
import com.pop.pcms.vo.ResultDto;
import com.pop.pcms.web.rest.util.BaseValidatorUtil;
import com.pop.pcms.web.rest.util.ErrorMsgUtils;
import com.pop.pcms.web.rest.util.HeaderUtil;
import com.pop.pcms.web.rest.util.PaginationUtil;
import com.pop.pcms.service.dto.PopVoteDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing PopVote.
 */
@RestController
@RequestMapping("/api")
public class PopVoteResource {

    private final Logger log = LoggerFactory.getLogger(PopVoteResource.class);

    private static final String ENTITY_NAME = "popVote";
    private static final String TAG_VOTE = "大众摄影投票";

    private final PopVoteService popVoteService;

    public PopVoteResource(PopVoteService popVoteService) {
        this.popVoteService = popVoteService;
    }

    /**
     * POST  /add-vote : Create a new popVote.
     *
     * @param popVoteDTO the popVoteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new popVoteDTO, or with status 400 (Bad Request) if the popVote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-vote")
    @Timed
    @ApiOperation(value = "投票api", tags = {TAG_VOTE}, notes = "大众摄影投票。")
    public ResponseEntity<ResultDto<PopVoteDTO>> createPopVote(@RequestBody RequestVO<PopVoteDTO> popVoteDTO) {
        try {
            log.debug("REST request to save PopVote : {}", popVoteDTO);
            //获取入参中的PopVoteDTO对象进行业务处理
            PopVoteDTO dto = popVoteDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String validMsg = BaseValidatorUtil.valConstraint(dto);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            if (dto.getId() != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new popVote cannot already have an ID")).body(null);
            }
            //PopVoteDTO对象入库
            PopVoteDTO result = popVoteService.save(dto);
            ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "成功!");
            return ResponseEntity.created(new URI("/api/pop-votes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(resultDto);
        } catch (Exception e) {
            log.error("PopVoteResource->createPopVote发生系统异常:{}", e);
            ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }

    }

    /**
     * POST  /del-pop-votes
     * 删除大众摄影投票
     */
    @PostMapping("/del-pop-votes")
    @Timed
    @ApiOperation(value = "删除大众摄影投票api", tags = {TAG_VOTE}, notes = "大众摄影投票删除。")
    public ResponseEntity<ResultDto<PopVoteDTO>> delVote(@RequestBody RequestVO<PopVoteDTO> popVoteDTO) {
        try {
            //获取入参中的PopVoteDTO对象进行业务处理
            PopVoteDTO dto = popVoteDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //PopVoteDTO对象入库
            Long id = dto.getId();
            popVoteService.delete(id);
            ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SUCCESS, "删除成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopVoteResource->delVote发生系统异常:{}", e);
            ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }

    }

    /**
     * POST  /update-pop-votes
     * 更新大众摄影投票
     */
    @PostMapping("/update-pop-votes")
    @Timed
    @ApiOperation(value = "更新大众摄影投票api", tags = {TAG_VOTE}, notes = "大众摄影投票添加。")
    public ResponseEntity<ResultDto<PopVoteDTO>> updatePopVote(@RequestBody RequestVO<PopVoteDTO> popVoteDTO) {
        try {
            //获取入参中的PopVoteDTO对象进行业务处理
            PopVoteDTO dto = popVoteDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1", "pf2", "pf3", "pf4", "pf5"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //如果ID为空或者ID为0  则不进行更新操作
            if (dto.getId() == null || dto.getId() == 0l) {
                //返回错误的结果集
                ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, "id不能为空!");
                return ResponseEntity.ok().body(resultDto);
            }
            //PopVoteDTO对象入库
            PopVoteDTO result = popVoteService.save(dto);
            ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "更新成功!");
            return ResponseEntity.created(new URI("/api/pop-votes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(resultDto);
        } catch (Exception e) {
            log.error("PopVoteResource->updatePopVote发生系统异常:{}", e);
            ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * POST  /list-pop-votes
     * 列表大众摄影投票
     */
    @PostMapping("/list-pop-votes")
    @Timed
    @ApiOperation(value = "查询列表大众摄影投票api", tags = {TAG_VOTE}, notes = "大众摄影投票列表。")
    public ResponseEntity<ResultDto<PopVoteDTO>> listPopVote(@RequestBody RequestVO<PopVoteDTO> popVoteDTO, @ApiParam Pageable pageable) {
        try {
            //获取入参中的PopVoteDTO对象进行业务处理
            PopVoteDTO dto = popVoteDTO.getData();
            Page<PopVoteDTO> page = popVoteService.findAll(pageable);
            ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(page, ErrorCode.REQUEST_SUCCESS, "查询成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopVoteResource->listPopVote发生系统异常:{}", e);
            ResultDto<PopVoteDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }
//
//    /**
//     * POST  /pop-votes : Create a new popVote.
//     *
//     * @param popVoteDTO the popVoteDTO to create
//     * @return the ResponseEntity with status 201 (Created) and with body the new popVoteDTO, or with status 400 (Bad Request) if the popVote has already an ID
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PostMapping("/pop-votes")
//    @Timed
//    public ResponseEntity<PopVoteDTO> createPopVote(@Valid @RequestBody PopVoteDTO popVoteDTO) throws URISyntaxException {
//        log.debug("REST request to save PopVote : {}", popVoteDTO);
//        if (popVoteDTO.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new popVote cannot already have an ID")).body(null);
//        }
//        PopVoteDTO result = popVoteService.save(popVoteDTO);
//        return ResponseEntity.created(new URI("/api/pop-votes/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * PUT  /pop-votes : Updates an existing popVote.
//     *
//     * @param popVoteDTO the popVoteDTO to update
//     * @return the ResponseEntity with status 200 (OK) and with body the updated popVoteDTO,
//     * or with status 400 (Bad Request) if the popVoteDTO is not valid,
//     * or with status 500 (Internal Server Error) if the popVoteDTO couldnt be updated
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PutMapping("/pop-votes")
//    @Timed
//    public ResponseEntity<PopVoteDTO> updatePopVote(@Valid @RequestBody PopVoteDTO popVoteDTO) throws URISyntaxException {
//        log.debug("REST request to update PopVote : {}", popVoteDTO);
//        if (popVoteDTO.getId() == null) {
//            return createPopVote(popVoteDTO);
//        }
//        PopVoteDTO result = popVoteService.save(popVoteDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, popVoteDTO.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * GET  /pop-votes : get all the popVotes.
//     *
//     * @param pageable the pagination information
//     * @return the ResponseEntity with status 200 (OK) and the list of popVotes in body
//     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
//     */
//    @GetMapping("/pop-votes")
//    @Timed
//    public ResponseEntity<List<PopVoteDTO>> getAllPopVotes(@ApiParam Pageable pageable)
//        throws URISyntaxException {
//        log.debug("REST request to get a page of PopVotes");
//        Page<PopVoteDTO> page = popVoteService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pop-votes");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
//
//    /**
//     * GET  /pop-votes/:id : get the "id" popVote.
//     *
//     * @param id the id of the popVoteDTO to retrieve
//     * @return the ResponseEntity with status 200 (OK) and with body the popVoteDTO, or with status 404 (Not Found)
//     */
//    @GetMapping("/pop-votes/{id}")
//    @Timed
//    public ResponseEntity<PopVoteDTO> getPopVote(@PathVariable Long id) {
//        log.debug("REST request to get PopVote : {}", id);
//        PopVoteDTO popVoteDTO = popVoteService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(popVoteDTO));
//    }
//
//    /**
//     * DELETE  /pop-votes/:id : delete the "id" popVote.
//     *
//     * @param id the id of the popVoteDTO to delete
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @DeleteMapping("/pop-votes/{id}")
//    @Timed
//    public ResponseEntity<Void> deletePopVote(@PathVariable Long id) {
//        log.debug("REST request to delete PopVote : {}", id);
//        popVoteService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }

}
