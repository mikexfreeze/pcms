package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.domain.PopCompetition;
import com.pop.pcms.service.PopCompetitionService;
import com.pop.pcms.vo.ErrorCode;
import com.pop.pcms.vo.RequestVO;
import com.pop.pcms.vo.ResultDto;
import com.pop.pcms.web.rest.util.BaseValidatorUtil;
import com.pop.pcms.web.rest.util.ErrorMsgUtils;
import com.pop.pcms.web.rest.util.HeaderUtil;
import com.pop.pcms.web.rest.util.PaginationUtil;
import com.pop.pcms.service.dto.PopCompetitionDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 大众摄影 活动添加Controller
 * REST controller for managing PopCompetition.
 */
@RestController
@RequestMapping("/api")
public class PopCompetitionResource {

    private final Logger log = LoggerFactory.getLogger(PopCompetitionResource.class);

    private static final String ENTITY_NAME = "popCompetition";

    @Autowired
    private final PopCompetitionService popCompetitionService;

    public PopCompetitionResource(PopCompetitionService popCompetitionService) {
        this.popCompetitionService = popCompetitionService;
    }

    /**
     * POST  /pop-competitions
     * 添加大众摄影活动
     */
    @PostMapping("/add-pop-competitions")
    @Timed
    @ApiOperation(value = "添加大众摄影活动api", tags = {"大众摄影活动Service"}, notes = "大众摄影活动添加。")
    public ResponseEntity<ResultDto<PopCompetitionDTO>> createPopCompetition(@RequestBody RequestVO<PopCompetitionDTO> popCompetitionDTO) {
        try {
            //获取入参中的PopCompetitionDTO对象进行业务处理
            PopCompetitionDTO dto = popCompetitionDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf2", "pf3", "pf4", "pf5"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            if (dto.getId() != null) {
                //返回错误的结果集
                ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, "id必须为空!");
                return ResponseEntity.ok().body(resultDto);
            }
            //PopCompetitionDTO对象入库
            PopCompetitionDTO result = popCompetitionService.save(dto);
            ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "添加成功!");
            return ResponseEntity.created(new URI("/api/pop-competitions/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(resultDto);
        } catch (Exception e) {
            log.error("PopCompetitionResource->createPopCompetition发生系统异常:{}", e);
            ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * POST  /pop-competitions
     * 删除大众摄影活动
     */
    @PostMapping("/del-pop-competitions")
    @Timed
    @ApiOperation(value = "删除大众摄影活动api", tags = {"大众摄影活动Service"}, notes = "大众摄影活动删除。")
    public ResponseEntity<ResultDto<PopCompetitionDTO>> delCompetition(@RequestBody RequestVO<PopCompetitionDTO> popCompetitionDTO) {
        try {
            //获取入参中的PopCompetitionDTO对象进行业务处理
            PopCompetitionDTO dto = popCompetitionDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //PopCompetitionDTO对象入库
            Long id = dto.getId();
            popCompetitionService.delete(id);
            ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SUCCESS, "删除成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopCompetitionResource->delCompetition发生系统异常:{}", e);
            ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }

    }

    /**
     * POST  /pop-competitions
     * 更新大众摄影活动
     */
    @PostMapping("/update-pop-competitions")
    @Timed
    @ApiOperation(value = "更新大众摄影活动api", tags = {"大众摄影活动Service"}, notes = "大众摄影活动添加。")
    public ResponseEntity<ResultDto<PopCompetitionDTO>> updatePopCompetition(@RequestBody RequestVO<PopCompetitionDTO> popCompetitionDTO) {
        try {
            //获取入参中的PopCompetitionDTO对象进行业务处理
            PopCompetitionDTO dto = popCompetitionDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1", "pf2", "pf3", "pf4", "pf5"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //如果ID为空或者ID为0  则不进行更新操作
            if (dto.getId() == null || dto.getId() == 0l) {
                //返回错误的结果集
                ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, "id不能为空!");
                return ResponseEntity.ok().body(resultDto);
            }
            //PopCompetitionDTO对象入库
            PopCompetitionDTO result = popCompetitionService.save(dto);
            ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "更新成功!");
            return ResponseEntity.created(new URI("/api/pop-competitions/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(resultDto);
        } catch (Exception e) {
            log.error("PopCompetitionResource->updatePopCompetition发生系统异常:{}", e);
            ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * POST  /pop-competitions
     * 列表大众摄影活动
     */
    @PostMapping("/list-pop-competitions")
    @Timed
    @ApiOperation(value = "查询列表大众摄影活动api", tags = {"大众摄影活动Service"}, notes = "大众摄影活动列表。")
    public ResponseEntity<ResultDto<PopCompetitionDTO>> listPopCompetition(@RequestBody RequestVO<PopCompetitionDTO> popCompetitionDTO, @ApiParam Pageable pageable) {
        try {
            //获取入参中的PopCompetitionDTO对象进行业务处理
            PopCompetitionDTO dto = popCompetitionDTO.getData();
            PopCompetition p=new PopCompetition();
            p.setStatus(dto.getStatus());
            p.setTitle(dto.getTitle());
            Page<PopCompetitionDTO> page = popCompetitionService.findAllByTitleLikeAndStatus(pageable,p);
            ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(page, ErrorCode.REQUEST_SUCCESS, "查询成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopCompetitionResource->listPopCompetition发生系统异常:{}", e);
            ResultDto<PopCompetitionDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * PUT  /pop-competitions : Updates an existing popCompetition.
     *
     * @param popCompetitionDTO the popCompetitionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated popCompetitionDTO,
     * or with status 400 (Bad Request) if the popCompetitionDTO is not valid,
     * or with status 500 (Internal Server Error) if the popCompetitionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @PutMapping("/pop-competitions")
//    @Timed
//    public ResponseEntity<PopCompetitionDTO> updatePopCompetition(@RequestBody PopCompetitionDTO popCompetitionDTO) throws URISyntaxException {
//        log.debug("REST request to update PopCompetition : {}", popCompetitionDTO);
//        if (popCompetitionDTO.getId() == null) {
//            return createPopCompetition(popCompetitionDTO);
//        }
//        PopCompetitionDTO result = popCompetitionService.save(popCompetitionDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, popCompetitionDTO.getId().toString()))
//            .body(result);
//    }

    /**
     * GET  /pop-competitions : get all the popCompetitions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of popCompetitions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pop-competitions")
    @Timed
    public ResponseEntity<List<PopCompetitionDTO>> getAllPopCompetitions(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PopCompetitions");
        Page<PopCompetitionDTO> page = popCompetitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pop-competitions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pop-competitions/:id : get the "id" popCompetition.
     *
     * @param id the id of the popCompetitionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the popCompetitionDTO, or with status 404 (Not Found)
     */
//    @GetMapping("/pop-competitions/{id}")
//    @Timed
//    public ResponseEntity<PopCompetitionDTO> getPopCompetition(@PathVariable Long id) {
//        log.debug("REST request to get PopCompetition : {}", id);
//        PopCompetitionDTO popCompetitionDTO = popCompetitionService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(popCompetitionDTO));
//    }

    /**
     * DELETE  /pop-competitions/:id : delete the "id" popCompetition.
     *
     * @param id the id of the popCompetitionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
//    @DeleteMapping("/pop-competitions/{id}")
//    @Timed
//    public ResponseEntity<Void> deletePopCompetition(@PathVariable Long id) {
//        log.debug("REST request to delete PopCompetition : {}", id);
//        popCompetitionService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }

}
