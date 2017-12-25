package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.service.PopAwardConfigService;
import com.pop.pcms.vo.ErrorCode;
import com.pop.pcms.vo.RequestVO;
import com.pop.pcms.vo.ResultDto;
import com.pop.pcms.web.rest.util.BaseValidatorUtil;
import com.pop.pcms.web.rest.util.ErrorMsgUtils;
import com.pop.pcms.service.dto.PopAwardConfigDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**奖项配置
 * REST controller for managing PopAwardConfig.
 */
@RestController
@RequestMapping("/api")
public class PopAwardConfigResource {

    private final Logger log = LoggerFactory.getLogger(PopAwardConfigResource.class);

    @Autowired
    private final PopAwardConfigService popAwardConfigService;

    public PopAwardConfigResource(PopAwardConfigService popAwardConfigService) {
        this.popAwardConfigService = popAwardConfigService;
    }

    /**
     * 添加摄影奖项配置api
     * POST  /pop-award-configs : Create a new popAwardConfig.
     *
     */
    @PostMapping("/add-pop-award-configs")
    @ApiOperation(value = "添加摄影奖项配置api", tags = {"大众摄影奖项Service"}, notes = "添加活动奖项。")
    @Timed
    public ResponseEntity<ResultDto<PopAwardConfigDTO>> createPopAwardConfig(@RequestBody RequestVO<PopAwardConfigDTO> popAwardConfigDTO){
        try {
            //获取入参中的PopAwardConfigDTO对象进行业务处理
            PopAwardConfigDTO dto = popAwardConfigDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1" };
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //PopSubjectDTO对象入库
            PopAwardConfigDTO result = popAwardConfigService.save(dto);
            ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "执行成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopAwardConfigResource->createPopAwardConfig发生系统异常:{}", e);
            ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }

    }

//    @PutMapping("/pop-award-configs")
//    @Timed
//    public ResponseEntity<PopAwardConfigDTO> updatePopAwardConfig(@RequestBody PopAwardConfigDTO popAwardConfigDTO) throws URISyntaxException {
//        log.debug("REST request to update PopAwardConfig : {}", popAwardConfigDTO);
//        if (popAwardConfigDTO.getId() == null) {
//            return createPopAwardConfig(popAwardConfigDTO);
//        }
//        PopAwardConfigDTO result = popAwardConfigService.save(popAwardConfigDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, popAwardConfigDTO.getId().toString()))
//            .body(result);
//    }

//    /**
//     * GET  /pop-award-configs : get all the popAwardConfigs.
//     *
//     * @param pageable the pagination information
//     * @return the ResponseEntity with status 200 (OK) and the list of popAwardConfigs in body
//     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
//     */
//    @GetMapping("/pop-award-configs")
//    @Timed
//    public ResponseEntity<List<PopAwardConfigDTO>> getAllPopAwardConfigs(@ApiParam Pageable pageable)
//        throws URISyntaxException {
//        log.debug("REST request to get a page of PopAwardConfigs");
//        Page<PopAwardConfigDTO> page = popAwardConfigService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pop-award-configs");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * GET  /pop-award-configs/:id : get the "id" popAwardConfig.
     *
     * @param id the id of the popAwardConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the popAwardConfigDTO, or with status 404 (Not Found)
     */
//    @GetMapping("/pop-award-configs/{id}")
//    @Timed
//    public ResponseEntity<PopAwardConfigDTO> getPopAwardConfig(@PathVariable Long id) {
//        log.debug("REST request to get PopAwardConfig : {}", id);
//        PopAwardConfigDTO popAwardConfigDTO = popAwardConfigService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(popAwardConfigDTO));
//    }

    /**
     * 删除奖项配置
     * DELETE  /pop-award-configs/:id : delete the "id" popAwardConfig.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/del-pop-award-configs/")
    @Timed
    @ApiOperation(value = "删除摄影奖项配置api", tags = {"大众摄影奖项Service"}, notes = "删除奖项配置。")
    public ResponseEntity<ResultDto<PopAwardConfigDTO>> deletePopAwardConfig(@RequestBody RequestVO<PopAwardConfigDTO> popAwardConfigDTO) {
        try {
            //获取入参中的PopSubjectDTO对象进行业务处理
            PopAwardConfigDTO dto = popAwardConfigDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1" };
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            Long id = dto.getId();
            popAwardConfigService.delete(id);
            //PopSubjectDTO对象入库
            ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SUCCESS, "删除成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopAwardConfigResource->deletePopAwardConfig发生系统异常:{}", e);
            ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * 根据主题ID获取奖项配置
     * DELETE  /pop-award-configs/:id : delete the "id" popAwardConfig.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/list-pop-award-configs/")
    @Timed
    @ApiOperation(value = "查询摄影奖项配置api", tags = {"大众摄影奖项Service"}, notes = "根据主题ID获取奖项配置。")
    public ResponseEntity<ResultDto<PopAwardConfigDTO>> listPopAwardConfig(@RequestBody RequestVO<PopAwardConfigDTO> popAwardConfigDTO) {
        try {
            //获取入参中的PopSubjectDTO对象进行业务处理
            PopAwardConfigDTO dto = popAwardConfigDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf3" };
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //获取主题ID
            Long id = dto.getSubjectId();
            List<PopAwardConfigDTO> listResult=popAwardConfigService.findBySubjectId(id);
            //PopSubjectDTO对象入库
            ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(listResult, ErrorCode.REQUEST_SUCCESS, "查询成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopAwardConfigResource->listPopAwardConfig发生系统异常:{}", e);
            ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * POST  /pop-competitions
     * 更新大众摄影奖项配置
     */
    @PostMapping("/update-pop-award-configs")
    @Timed
    @ApiOperation(value = "更新摄影奖项配置api", tags = {"大众摄影奖项Service"}, notes = "更新摄影奖项配置。")
    public ResponseEntity<ResultDto<PopAwardConfigDTO>> updatePopAwardConfig(@RequestBody RequestVO<PopAwardConfigDTO> popCompetitionDTO) {
        try {
            //获取入参中的PopSubjectDTO对象进行业务处理
            PopAwardConfigDTO dto = popCompetitionDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf2","pf3"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //如果ID为空或者ID为0  则不进行更新操作
            if (dto.getId() == null || dto.getId() == 0l) {
                //返回错误的结果集
                ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, "id不能为空!");
                return ResponseEntity.ok().body(resultDto);
            }
            //PopSubjectDTO对象入库
            PopAwardConfigDTO result = popAwardConfigService.save(dto);
            ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "更新成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopAwardConfigResource->updatePopAwardConfig发生系统异常:{}", e);
            ResultDto<PopAwardConfigDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }
}
