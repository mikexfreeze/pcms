package com.pop.pcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pop.pcms.service.PopSubjectService;
import com.pop.pcms.vo.ErrorCode;
import com.pop.pcms.vo.RequestVO;
import com.pop.pcms.vo.ResultDto;
import com.pop.pcms.web.rest.util.BaseValidatorUtil;
import com.pop.pcms.web.rest.util.ErrorMsgUtils;
import com.pop.pcms.web.rest.util.HeaderUtil;
import com.pop.pcms.service.dto.PopSubjectDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 主题添加controller
 * REST controller for managing PopSubject.
 */
@RestController
@RequestMapping("/api")
public class PopSubjectResource {

    private final Logger log = LoggerFactory.getLogger(PopSubjectResource.class);

    private static final String ENTITY_NAME = "popSubject";

    @Autowired
    private final PopSubjectService popSubjectService;

    public PopSubjectResource(PopSubjectService popSubjectService) {
        this.popSubjectService = popSubjectService;
    }

    /**
     * 添加主题
     * POST  /pop-subjects : Create a new popSubject.
     *
     */
    @PostMapping("/add-pop-subjects")
    @ApiOperation(value = "添加大众摄影主题api", tags = {"大众摄影主题Service"}, notes = "根据活动ID查询主题。")
    @Timed
    public ResponseEntity<ResultDto<PopSubjectDTO>> createPopSubject(@RequestBody RequestVO<PopSubjectDTO> popSubjectDTO) throws URISyntaxException {
        try {
            PopSubjectDTO dto = popSubjectDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf2", "pf3", "pf4", "pf5"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            if (dto.getId() != null) {
                ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, "id必须为空!");
                return ResponseEntity.ok().body(resultDto);
            }
            PopSubjectDTO result = popSubjectService.save(dto);
            ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "添加成功!");
            return ResponseEntity.created(new URI("/api/pop-subjects/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(resultDto);
        }catch (Exception e){
            log.error("PopSubjectResource->createPopSubject发生系统异常:{}", e);
            ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "发生系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }

    }


    /**
     * 删除摄影主题
     * DELETE  /pop-subjects/:id : delete the "id" popSubject.
     *
     */
    @PostMapping("/del-pop-subjects/")
    @ApiOperation(value = "删除大众摄影主题api", tags = {"大众摄影主题Service"}, notes = "大众摄影主题删除。")
    @Timed
    public ResponseEntity<ResultDto<PopSubjectDTO>> deletePopSubject(@RequestBody RequestVO<PopSubjectDTO> popCompetitionDTO) {
        try {
            //获取入参中的PopCompetitionDTO对象进行业务处理
            PopSubjectDTO dto = popCompetitionDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //PopSubjectDTO对象入库
            Long id = dto.getId();
            popSubjectService.delete(id);
            ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SUCCESS, "删除成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopSubjectResource->deletePopSubject发生系统异常:{}", e);
            ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }

    }


    /**
     * POST  /pop-competitions
     * 更新大众摄影主题
     */
    @PostMapping("/update-pop-subjects")
    @Timed
    @ApiOperation(value = "更新大众摄影主题api", tags = {"大众摄影主题Service"}, notes = "大众摄影活动更新。")
    public ResponseEntity<ResultDto<PopSubjectDTO>> updatePopSubject(@RequestBody RequestVO<PopSubjectDTO> popCompetitionDTO) {
        try {
            //获取入参中的PopSubjectDTO对象进行业务处理
            PopSubjectDTO dto = popCompetitionDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf1", "pf2", "pf3", "pf4"};
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //如果ID为空或者ID为0  则不进行更新操作
            if (dto.getId() == null || dto.getId() == 0l) {
                //返回错误的结果集
                ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, "id不能为空!");
                return ResponseEntity.ok().body(resultDto);
            }
            //PopSubjectDTO对象入库
            PopSubjectDTO result = popSubjectService.save(dto);
            ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "更新成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopSubjectResource->updatePopCompetition发生系统异常:{}", e);
            ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }

    /**
     * 根据活动ID查询当前活动下的所有主题
     * @param popCompetitionDTO
     * @return
     */
    @PostMapping("/qry-pop-subjects-bycompetition_id")
    @Timed
    @ApiOperation(value = "大众摄影主题查询api", tags = {"大众摄影主题Service"}, notes = "根据活动ID查询当前活动下的主题。")
    public ResponseEntity<ResultDto<PopSubjectDTO>> qryPopSubjectByCompeId(@RequestBody RequestVO<PopSubjectDTO> popCompetitionDTO) {
        try {
            //获取入参中的PopSubjectDTO对象进行业务处理
            PopSubjectDTO dto = popCompetitionDTO.getData();
            //校验参数 如果不通过则返回失败信息
            String[] args = {"pf5" };
            String validMsg = BaseValidatorUtil.valConstraint(dto, args);
            if (StringUtils.isNotEmpty(validMsg)) {
                //返回错误的结果集
                ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_VERIFY, validMsg);
                return ResponseEntity.ok().body(resultDto);
            }
            //PopSubjectDTO对象入库
            List<PopSubjectDTO> result = popSubjectService.findByCompetitionId(dto.getCompetitionId());
            ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(result, ErrorCode.REQUEST_SUCCESS, "执行成功!");
            return ResponseEntity.ok().body(resultDto);
        } catch (Exception e) {
            log.error("PopSubjectResource->qryPopSubjectByCompeId发生系统异常:{}", e);
            ResultDto<PopSubjectDTO> resultDto = ErrorMsgUtils.successMsg(null, ErrorCode.REQUEST_SYSTEM_ERROR, "系统错误!");
            return ResponseEntity.ok().body(resultDto);
        }
    }
}
