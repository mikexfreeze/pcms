package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.domain.PopJudge;
import com.pop.pcms.service.PopJudgeService;
import com.pop.pcms.vo.ErrorCode;
import com.pop.pcms.vo.RequestVO;
import com.pop.pcms.vo.ResultDto;
import com.pop.pcms.web.rest.util.BaseValidatorUtil;
import com.pop.pcms.web.rest.util.ErrorMsgUtils;
import com.pop.pcms.web.rest.util.HeaderUtil;
import com.pop.pcms.web.rest.util.PaginationUtil;
import com.pop.pcms.service.dto.PopJudgeDTO;
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
 * REST controller for managing PopJudge.
 */
@RestController
@RequestMapping("/api")
public class PopJudgeResource {

    private final Logger log = LoggerFactory.getLogger(PopJudgeResource.class);

    private static final String ENTITY_NAME = "popJudge";
    private static final String TAG_JUDGE = "大众摄影评委";

    private final PopJudgeService popJudgeService;

    public PopJudgeResource(PopJudgeService popJudgeService) {
        this.popJudgeService = popJudgeService;
    }

    /**
     * POST  /add-judge : Create a new popJudge.
     *
     * @param popJudgeDTO the popJudgeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new popJudgeDTO, or with status 400 (Bad Request) if the popJudge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-judge")
    @Timed
    @ApiOperation(value = "添加评委api", tags = {TAG_JUDGE}, notes = "大众摄影评委添加。")
    public ResponseEntity<ResultDto<PopJudgeDTO>> createPopJudge(@RequestBody RequestVO<PopJudgeDTO> popJudgeDTO) {
        try {
            log.debug("REST request to save PopJudge : {}", popJudgeDTO);
            //获取入参中的PopJudgeDTO对象进行业务处理
            PopJudgeDTO dto = popJudgeDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String validMsg = BaseValidatorUtil.valConstraint(dto);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            if (dto.getId() != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new popJudge cannot already have an ID")).body(null);
            }
            //PopJudgeDTO对象入库
            PopJudgeDTO result = popJudgeService.save(dto);
            ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "成功!");
            return ResponseEntity.created(new URI("/api/pop-judges/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(resultDto);
        } catch (Exception e) {
            log.error("PopJudgeResource->createPopJudge发生系统异常:{}", e);
            ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }

    }

    /**
     * POST  /del-pop-judges
     * 删除大众摄影评委
     */
    @PostMapping("/del-pop-judges")
    @Timed
    @ApiOperation(value = "删除大众摄影评委api", tags = {TAG_JUDGE}, notes = "大众摄影评委删除。")
    public ResponseEntity<ResultDto<PopJudgeDTO>> delJudge(@RequestBody RequestVO<PopJudgeDTO> popJudgeDTO) {
        try {
            //获取入参中的PopJudgeDTO对象进行业务处理
            PopJudgeDTO dto = popJudgeDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //PopJudgeDTO对象入库
            Long id = dto.getId();
            popJudgeService.delete(id);
            ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SUCCESS, "删除成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopJudgeResource->delJudge发生系统异常:{}", e);
            ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }

    }

    /**
     * POST  /update-pop-judges
     * 更新大众摄影评委
     */
    @PostMapping("/update-pop-judges")
    @Timed
    @ApiOperation(value = "更新大众摄影评委api", tags = {TAG_JUDGE}, notes = "大众摄影评委添加。")
    public ResponseEntity<ResultDto<PopJudgeDTO>> updatePopJudge(@RequestBody RequestVO<PopJudgeDTO> popJudgeDTO) {
        try {
            //获取入参中的PopJudgeDTO对象进行业务处理
            PopJudgeDTO dto = popJudgeDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1", "pf2", "pf3", "pf4", "pf5"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //如果ID为空或者ID为0  则不进行更新操作
            if (dto.getId() == null || dto.getId() == 0l) {
                //返回错误的结果集
                ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, "id不能为空!");
                return ResponseEntity.ok().body(resultDto);
            }
            //PopJudgeDTO对象入库
            PopJudgeDTO result = popJudgeService.save(dto);
            ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "更新成功!");
            return ResponseEntity.created(new URI("/api/pop-judges/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(resultDto);
        } catch (Exception e) {
            log.error("PopJudgeResource->updatePopJudge发生系统异常:{}", e);
            ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * POST  /list-pop-judges
     * 列表大众摄影评委
     */
    @PostMapping("/list-pop-judges")
    @Timed
    @ApiOperation(value = "查询列表大众摄影评委api", tags = {TAG_JUDGE}, notes = "大众摄影评委列表。")
    public ResponseEntity<ResultDto<PopJudgeDTO>> listPopJudge(@RequestBody RequestVO<PopJudgeDTO> popJudgeDTO, @ApiParam Pageable pageable) {
        try {
            //获取入参中的PopJudgeDTO对象进行业务处理
            PopJudgeDTO dto = popJudgeDTO.getData();
            PopJudge p=new PopJudge();
            Page<PopJudgeDTO> page = popJudgeService.findAll(pageable);
            ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(page, ErrorCode.REQUEST_SUCCESS, "查询成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopJudgeResource->listPopJudge发生系统异常:{}", e);
            ResultDto<PopJudgeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

//    /**
//     * POST  /pop-judges : Create a new popJudge.
//     *
//     * @param popJudgeDTO the popJudgeDTO to create
//     * @return the ResponseEntity with status 201 (Created) and with body the new popJudgeDTO, or with status 400 (Bad Request) if the popJudge has already an ID
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PostMapping("/pop-judges")
//    @Timed
//    public ResponseEntity<PopJudgeDTO> createPopJudge(@RequestBody PopJudgeDTO popJudgeDTO) throws URISyntaxException {
//        log.debug("REST request to save PopJudge : {}", popJudgeDTO);
//        if (popJudgeDTO.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new popJudge cannot already have an ID")).body(null);
//        }
//        PopJudgeDTO result = popJudgeService.save(popJudgeDTO);
//        return ResponseEntity.created(new URI("/api/pop-judges/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * PUT  /pop-judges : Updates an existing popJudge.
//     *
//     * @param popJudgeDTO the popJudgeDTO to update
//     * @return the ResponseEntity with status 200 (OK) and with body the updated popJudgeDTO,
//     * or with status 400 (Bad Request) if the popJudgeDTO is not valid,
//     * or with status 500 (Internal Server Error) if the popJudgeDTO couldnt be updated
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PutMapping("/pop-judges")
//    @Timed
//    public ResponseEntity<PopJudgeDTO> updatePopJudge(@RequestBody PopJudgeDTO popJudgeDTO) throws URISyntaxException {
//        log.debug("REST request to update PopJudge : {}", popJudgeDTO);
//        if (popJudgeDTO.getId() == null) {
//            return createPopJudge(popJudgeDTO);
//        }
//        PopJudgeDTO result = popJudgeService.save(popJudgeDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, popJudgeDTO.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * GET  /pop-judges : get all the popJudges.
//     *
//     * @param pageable the pagination information
//     * @return the ResponseEntity with status 200 (OK) and the list of popJudges in body
//     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
//     */
//    @GetMapping("/pop-judges")
//    @Timed
//    public ResponseEntity<List<PopJudgeDTO>> getAllPopJudges(@ApiParam Pageable pageable)
//        throws URISyntaxException {
//        log.debug("REST request to get a page of PopJudges");
//        Page<PopJudgeDTO> page = popJudgeService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pop-judges");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
//
//    /**
//     * GET  /pop-judges/:id : get the "id" popJudge.
//     *
//     * @param id the id of the popJudgeDTO to retrieve
//     * @return the ResponseEntity with status 200 (OK) and with body the popJudgeDTO, or with status 404 (Not Found)
//     */
//    @GetMapping("/pop-judges/{id}")
//    @Timed
//    public ResponseEntity<PopJudgeDTO> getPopJudge(@PathVariable Long id) {
//        log.debug("REST request to get PopJudge : {}", id);
//        PopJudgeDTO popJudgeDTO = popJudgeService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(popJudgeDTO));
//    }
//
//    /**
//     * DELETE  /pop-judges/:id : delete the "id" popJudge.
//     *
//     * @param id the id of the popJudgeDTO to delete
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @DeleteMapping("/pop-judges/{id}")
//    @Timed
//    public ResponseEntity<Void> deletePopJudge(@PathVariable Long id) {
//        log.debug("REST request to delete PopJudge : {}", id);
//        popJudgeService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }

}
