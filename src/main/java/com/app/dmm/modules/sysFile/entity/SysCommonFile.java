package com.app.dmm.modules.sysFile.entity;

import com.app.dmm.core.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 系统文件表
 */
public class SysCommonFile extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String fileUrl;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 创建人
     */
    private String createName;


    private String deleteFlag;

    public SysCommonFile() {
    }

    public SysCommonFile(String fileName, String fileUrl, String fileType, String fileSize, Long createId, String createName,Date createTime,String deleteFlag) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.createId = createId;
        this.createName = createName;
        setCreateTime(createTime);
        this.deleteFlag = deleteFlag;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}