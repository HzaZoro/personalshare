package cn.pirateswang.core.system.accessRecord.entity;

import cn.pirateswang.common.base.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统访问记录表
 */
@Entity
@Table(name = "system_access_record")
public class SystemAccessRecordEntity extends BaseEntity {

    /**
     * 访问用户主键ID
     */
    private Long userId;

    /**
     * 访问源IP
     */
    private String ip;

    /**
     * 访问目的端口
     */
    private Integer port;

    /**
     * 访问开始时间
     */
    private Date requestStartTime;

    /**
     * 访问结束时间
     */
    private Date requestEndTime;

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 请求返回码
     */
    private Integer responseCode;

    /**
     * 请求返回信息
     */
    private String responseMsg;

    /**
     * 接口处理时长 单位：毫秒
     */
    private Long processingTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Date getRequestStartTime() {
        return requestStartTime;
    }

    public void setRequestStartTime(Date requestStartTime) {
        this.requestStartTime = requestStartTime;
    }

    public Date getRequestEndTime() {
        return requestEndTime;
    }

    public void setRequestEndTime(Date requestEndTime) {
        this.requestEndTime = requestEndTime;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public Long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Long processingTime) {
        this.processingTime = processingTime;
    }
}
