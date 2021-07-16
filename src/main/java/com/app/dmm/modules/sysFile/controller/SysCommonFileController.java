package com.app.dmm.modules.sysFile.controller;

import com.app.dmm.config.ConfigProperties;
import com.app.dmm.core.ApiResult;
import com.app.dmm.modules.sysFile.entity.SysCommonFile;
import com.app.dmm.modules.sysFile.service.SysCommonFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/sys/file")
@Api(tags = "系统文件")
public class SysCommonFileController {

    @Autowired
    private SysCommonFileService sysCommonFileService;

    @Autowired
    private ConfigProperties properties;


    /**
     * 文件上传
     * @param file
     */
    @ApiOperation("文件上传")
    /*使用SpringBoot实现简单文件上传(上传至本地)*/
    @PostMapping("/upload.do")
    public String upload(@RequestBody MultipartFile file, HttpServletRequest request) throws IOException {//MultipartFile 接收前端传过来的文件
        // 注意 前端传参的name要和MultipartFile的对象名保持一直 比如此处对象名为file 则前端传参的name也要为file
        String filePath = null;
        try {
            String oriFileName = file.getOriginalFilename();                     //获取上传文件的文件名
            String ext = getFileExt(oriFileName);                                // 获取文件扩展名
            long fileSize = file.getSize();                                      // 文件大小，单位字节
            String fileName = getName(oriFileName);                              // 新文件名
            String path = properties.getFILE_PATH();                             //指定上传路径
            String savePath = getRootFolder(path,"FILE");        // 保存路径
            File newFile = new File(savePath,fileName);                    //创建新文件对象 指定文件路径为拼接好的路径

            String fileUrl = savePath.replaceFirst(path, "") + "/" + fileName;
            filePath = savePath + fileName;                                    //拼接成为新文件的路径
            long starTime = System.currentTimeMillis();
            file.transferTo(newFile);                                      //将前端传递过来的文件输送给新文件 这里需要抛出IO异常 throws IOException
            long endTime = System.currentTimeMillis();
            SysCommonFile sysFile = new SysCommonFile(oriFileName,fileUrl,ext,fileSize+"",new Long(10001),"超管",new Date(),"n");
            sysCommonFileService.save(sysFile);
            log.info("本次执行上传耗时......" + (endTime - starTime) + " ms  文件保存成功");

        }catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;                                            //上传完成后将文件路径返回给前端用作图片回显或增加时的文件路径值等
    }


    /**
     * 根据字符串创建本地目录 并按照日期建立子目录返回
     *
     * @param path  系统文件路径
     * @param modelType 系统模块名
     * @return d:/upload/ **** /20141010
     */
    private String getRootFolder(String path,String modelType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        path += "/" + modelType + "/" + sdf.format(new Date());
        File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                return "";
            }
        }
        return path;
    }

    /**
     * 获取文件扩展名,小写
     *
     * @return string
     */
    private String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 依据原始文件名生成新文件名
     *
     * @return
     */
    private String getName(String fileName) {
        Random random = new Random();
        return random.nextInt(10000) + System.currentTimeMillis() + this.getFileExt(fileName);
    }

    /**
     * 文件删除
     */
    @ApiOperation("文件删除")
    @PostMapping(value = "/fileDelete.do")
    public ApiResult<Object> fileDelete(){
        //ApiResult<String> res = new Result<String>();
        try {

            return ApiResult.OK();
        } catch (Exception e) {
            return ApiResult.error("获取验证码出错"+e.getMessage());
        }
    }

    /**
     * 文件下载
     */
    @ApiOperation("文件下载")
    @PostMapping(value = "/download.do")
    public ApiResult<Object> fileDownload(){
        //ApiResult<String> res = new Result<String>();
        try {
            List<SysCommonFile> fileList = sysCommonFileService.findAll();
            return ApiResult.OK(fileList);
        } catch (Exception e) {
            return ApiResult.error("获取验证码出错"+e.getMessage());
        }
    }


}
