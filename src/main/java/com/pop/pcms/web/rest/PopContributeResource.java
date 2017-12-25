package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.domain.PopViewPicture;
import com.pop.pcms.service.PopContributeService;
import com.pop.pcms.service.PopPictureService;
import com.pop.pcms.service.PopSubjectService;
import com.pop.pcms.service.dto.PopPictureDTO;
import com.pop.pcms.vo.ErrorCode;
import com.pop.pcms.vo.RequestVO;
import com.pop.pcms.vo.ResultDto;
import com.pop.pcms.web.rest.util.BaseValidatorUtil;
import com.pop.pcms.web.rest.util.ErrorMsgUtils;
import com.pop.pcms.web.rest.util.PaginationUtil;
import com.pop.pcms.service.dto.PopContributeDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 投稿API
 * REST controller for managing PopContribute.
 */
@RestController
@RequestMapping("/api")
public class PopContributeResource {

    private final Logger log = LoggerFactory.getLogger(PopContributeResource.class);

    private static final String ENTITY_NAME = "popContribute";

    @Autowired
    private final PopContributeService popContributeService;

    //投稿对应的作品信息
    @Autowired
    private PopPictureService popPictureService;

    //主题service
    @Autowired
    private PopSubjectService popSubjectService;

    //作品展示
//    @Autowired
//    private PopPictureViewService popPictureViewService;

    public PopContributeResource(PopContributeService popContributeService) {
        this.popContributeService = popContributeService;
    }

    /**
     * 投稿添加
     * POST  /pop-contributes : Create a new popContribute.
     */
    @PostMapping("/add-pop-contributes")
    @Timed
    @ApiOperation(value = "添加大众摄影投稿api", tags = {"大众摄影投稿Service"}, notes = "大众摄影投稿添加。")
    public ResponseEntity<ResultDto<PopContributeDTO>> createPopContribute(@RequestBody RequestVO<PopContributeDTO> popContributeDTO) throws URISyntaxException {
        try {
            //校验参数 如果不通过则返回失败信息
            PopContributeDTO dto = popContributeDTO.getData();
            String[] args = {"pf2", "pf3", "pf4"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //PopContributeDTO对象入库
            PopContributeDTO result = popContributeService.save(dto);
            ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "更新成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopContributeResource->createPopContribute发生系统异常:{}", e);
            ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * PUT  /pop-contributes : Updates an existing popContribute.
     *
     * @param popContributeDTO the popContributeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated popContributeDTO,
     * or with status 400 (Bad Request) if the popContributeDTO is not valid,
     * or with status 500 (Internal Server Error) if the popContributeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @PutMapping("/pop-contributes")
//    @Timed
//    public ResponseEntity<PopContributeDTO> updatePopContribute(@RequestBody PopContributeDTO popContributeDTO) throws URISyntaxException {
//        log.debug("REST request to update PopContribute : {}", popContributeDTO);
//        if (popContributeDTO.getId() == null) {
//            return createPopContribute(popContributeDTO);
//        }
//        PopContributeDTO result = popContributeService.save(popContributeDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, popContributeDTO.getId().toString()))
//            .body(result);
//    }

    /**
     * GET  /pop-contributes : get all the popContributes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of popContributes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pop-contributes")
    @Timed
    public ResponseEntity<List<PopContributeDTO>> getAllPopContributes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PopContributes");
        Page<PopContributeDTO> page = popContributeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pop-contributes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pop-contributes/:id : get the "id" popContribute.
     *
     * @param id the id of the popContributeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the popContributeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pop-contributes/{id}")
    @Timed
    public ResponseEntity<PopContributeDTO> getPopContribute(@PathVariable Long id) {
        log.debug("REST request to get PopContribute : {}", id);
        PopContributeDTO popContributeDTO = popContributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(popContributeDTO));
    }

    /**
     * 删除征稿配置
     * DELETE  /pop-contributes/:id : delete the "id" popContribute.
     */
    @PostMapping("/del-pop-contributes")
    @Timed
    @ApiOperation(value = "删除大众摄影投稿api", tags = {"大众摄影投稿Service"}, notes = "大众摄影投稿删除。")
    public ResponseEntity<ResultDto<PopContributeDTO>> deletePopContribute(@RequestBody RequestVO<PopContributeDTO> popCompetitionDTO) {
        try {
            //获取入参中的PopCompetitionDTO对象进行业务处理
            PopContributeDTO dto = popCompetitionDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (org.apache.commons.lang.StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //PopSubjectDTO对象入库
//            Long id = dto.getId();
//            List<PopPictureDTO> dtoList= popPictureService.findByContributeId(id);
            //先删除
            popContributeService.deletePopContributeDTO(dto);
            ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SUCCESS, "删除成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopContributeResource->deletePopContribute发生系统异常:{}", e);
            ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }


    /**
     * POST  /pop-competitions
     * 更新大众摄影投稿配置
     */
    @PostMapping("/update-pop-contributes")
    @Timed
    @ApiOperation(value = "更新大众摄影投稿api", tags = {"大众摄影投稿Service"}, notes = "大众摄影投稿更新。")
    public ResponseEntity<ResultDto<PopContributeDTO>> updatePopContribute(@RequestBody RequestVO<PopContributeDTO> popCompetitionDTO) {
        try {
            //获取入参中的PopContributeDTO对象进行业务处理
            PopContributeDTO dto = popCompetitionDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1", "pf2", "pf3"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (org.apache.commons.lang.StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //如果ID为空或者ID为0  则不进行更新操作
            if (dto.getId() == null || dto.getId() == 0l) {
                //返回错误的结果集
                ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, "id不能为空!");
                return ResponseEntity.ok().body(resultDto);
            }
            PopContributeDTO result = popContributeService.save(dto);
            ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "更新成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopContributeResource->updatePopContribute发生系统异常:{}", e);
            ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * POST  /pop-competitions
     * 列表大众摄影投稿
     */
    @PostMapping("/list-pop-contributes")
    @Timed
    @ApiOperation(value = "列表大众摄影投稿api", tags = {"大众摄影投稿Service"}, notes = "大众摄影投稿列表。")
    public ResponseEntity<ResultDto<PopContributeDTO>> listPopCompetition(@RequestBody RequestVO<PopContributeDTO> popCompetitionDTO, @ApiParam Pageable pageable) {
        try {
            PopContributeDTO dto = popCompetitionDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf5"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (org.apache.commons.lang.StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //1 先找到对应的活动ID
            List<PopViewPicture> page = popPictureService.findByContributeId(dto.getCompetitionId());
            ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(page, ErrorCode.REQUEST_SUCCESS, "查询成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopContributeResource->listPopCompetition发生系统异常:{}", e);
            ResultDto<PopContributeDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }
}
