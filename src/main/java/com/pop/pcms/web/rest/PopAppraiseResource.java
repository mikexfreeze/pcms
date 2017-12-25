package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.service.PopAppraiseService;
import com.pop.pcms.vo.ErrorCode;
import com.pop.pcms.vo.RequestVO;
import com.pop.pcms.vo.ResultDto;
import com.pop.pcms.web.rest.util.BaseValidatorUtil;
import com.pop.pcms.web.rest.util.ErrorMsgUtils;
import com.pop.pcms.web.rest.util.HeaderUtil;
import com.pop.pcms.web.rest.util.PaginationUtil;
import com.pop.pcms.service.dto.PopAppraiseDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * 评选设置
 * REST controller for managing PopAppraise.
 */
@RestController
@RequestMapping("/api")
public class PopAppraiseResource {

    private final Logger log = LoggerFactory.getLogger(PopAppraiseResource.class);

    private static final String ENTITY_NAME = "popAppraise";

    private final PopAppraiseService popAppraiseService;

    public PopAppraiseResource(PopAppraiseService popAppraiseService) {
        this.popAppraiseService = popAppraiseService;
    }

    /**
     * 创建评选-评选轮次
     * POST  /pop-appraises : Create a new popAppraise.
     */
    @PostMapping("/add-pop-appraises")
    @Timed
    @ApiOperation(value = "添加大众摄影评选api", tags = {"大众摄影评选Service"}, notes = "大众摄影评选添加。")
    public ResponseEntity<ResultDto<PopAppraiseDTO>> createPopAppraise(@RequestBody RequestVO<PopAppraiseDTO> popAppraiseDTO) throws URISyntaxException {
        try {
            PopAppraiseDTO dto = popAppraiseDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1", "pf2", "pf3", "pf4", "pf5"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopAppraiseDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            if (dto.getId() != null) {
                //返回错误的结果集
                ResultDto<PopAppraiseDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, "id必须为空!");
                return ResponseEntity.ok().body(resultDto);
            }
            //PopCompetitionDTO对象入库
            PopAppraiseDTO result = popAppraiseService.save(dto);
            ResultDto<PopAppraiseDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "添加成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopAppraiseResource->createPopAppraise发生系统异常:{}", e);
            ResultDto<PopAppraiseDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * 创建评选-评选轮次修改
     * POST  /pop-appraises : Create a new popAppraise.
     */
    @PostMapping("/update-pop-appraises")
    @Timed
    @ApiOperation(value = "大众摄影评选修改api", tags = {"大众摄影评选Service"}, notes = "大众摄影评选修改。")
    public ResponseEntity<ResultDto<PopAppraiseDTO>> updatePopAppraise(@RequestBody RequestVO<PopAppraiseDTO> popAppraiseDTO) throws URISyntaxException {
        try {
            //获取入参中的PopCompetitionDTO对象进行业务处理
            PopAppraiseDTO dto = popAppraiseDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1", "pf2", "pf3", "pf4", "pf5","pf6"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopAppraiseDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //PopCompetitionDTO对象入库
            PopAppraiseDTO result = popAppraiseService.save(dto);
            ResultDto<PopAppraiseDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "修改成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopAppraiseResource->updatePopAppraise发生系统异常:{}", e);
            ResultDto<PopAppraiseDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * PUT  /pop-appraises : Updates an existing popAppraise.
     *
     * @param popAppraiseDTO the popAppraiseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated popAppraiseDTO,
     * or with status 400 (Bad Request) if the popAppraiseDTO is not valid,
     * or with status 500 (Internal Server Error) if the popAppraiseDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @PutMapping("/pop-appraises")
//    @Timed
//    public ResponseEntity<PopAppraiseDTO> updatePopAppraise(@RequestBody PopAppraiseDTO popAppraiseDTO) throws URISyntaxException {
//        log.debug("REST request to update PopAppraise : {}", popAppraiseDTO);
//        if (popAppraiseDTO.getId() == null) {
//            return createPopAppraise(popAppraiseDTO);
//        }
//        PopAppraiseDTO result = popAppraiseService.save(popAppraiseDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, popAppraiseDTO.getId().toString()))
//            .body(result);
//    }

    /**
     * GET  /pop-appraises : get all the popAppraises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of popAppraises in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pop-appraises")
    @Timed
    public ResponseEntity<List<PopAppraiseDTO>> getAllPopAppraises(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PopAppraises");
        Page<PopAppraiseDTO> page = popAppraiseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pop-appraises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pop-appraises/:id : get the "id" popAppraise.
     *
     * @param id the id of the popAppraiseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the popAppraiseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pop-appraises/{id}")
    @Timed
    public ResponseEntity<PopAppraiseDTO> getPopAppraise(@PathVariable Long id) {
        log.debug("REST request to get PopAppraise : {}", id);
        PopAppraiseDTO popAppraiseDTO = popAppraiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(popAppraiseDTO));
    }

    /**
     * DELETE  /pop-appraises/:id : delete the "id" popAppraise.
     *
     * @param id the id of the popAppraiseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pop-appraises/{id}")
    @Timed
    public ResponseEntity<Void> deletePopAppraise(@PathVariable Long id) {
        log.debug("REST request to delete PopAppraise : {}", id);
        popAppraiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
