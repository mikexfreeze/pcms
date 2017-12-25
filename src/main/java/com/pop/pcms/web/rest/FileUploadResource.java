package com.pop.pcms.web.rest;

import com.pop.pcms.vo.ResultDto;
import com.pop.pcms.web.rest.util.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


/**
 * 文件上传组件
 * Created by zhangjinye on 2017/2/27.
 */
@RestController
@RequestMapping("/api")
public class FileUploadResource {

    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);

    //设置文件上传路径
    @Value("${file.location}")
    private String location;

    //文件上传OK
    private String file_ok = "1000";
    //文件上传失败,发生系统错误
    private String file_error = "1001";
    //文件为空
    private String file_empty = "1002";

    //图片访问地址
    @Value("${file.nginxFileUpload}")
    private String nginxFileLoad;


    /**
     * 文件上传具体实现方法
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "文件上传功能", tags = {"文件上传Service"}, notes = "文件上传功能。")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        if (file == null) {
            log.error("文件上传失败,失败原因:{}");
            return errorMsg("文件上传失败", file_empty);
        }
        //老的文件名
        String oldFileName = file.getOriginalFilename();
        //文件后缀名
        String newFileName = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
        String fileName = convertTimeToFileName(id) + "." + newFileName;
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new
                    BufferedOutputStream(new FileOutputStream(new File(location + fileName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                log.error("文件上传失败,失败原因:{}", e);
                return errorMsg("文件上传失败", file_error);
            }
            return successMsg(nginxFileLoad + fileName, file_ok);
        } else {
            log.error("文件内容为空，上传失败!");
            return errorMsg("文件上传失败", file_empty);
        }
    }

    private String convertTimeToFileName(Long id) {
        long t = System.currentTimeMillis();
        return String.valueOf("Z(D)_" + id + "_" + t);
    }


    /**
     * 返回错误信息
     *
     * @param msg
     * @param code
     * @return
     */
    public static String errorMsg(String msg, String code) {
        ResultDto<String> dto = new ResultDto<String>();
        dto.setMsg(msg);
        dto.setCode(code);
        String errorMsg = JsonUtils.obj2str(dto);
        return errorMsg;
    }

    /**
     * 返回成功信息
     *
     * @param
     * @param code 当前输入的成功信息对象
     * @return
     */
    public static <T> String successMsg(T data, String code) {
        ResultDto<T> dto = new ResultDto<T>();
        dto.setMsg("查询成功!");
        dto.setCode(code);
        dto.setData(data);
        String errorMsg = JsonUtils.obj2str(dto);
        return errorMsg;
    }
}
