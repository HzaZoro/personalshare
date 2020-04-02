package cn.pirateswang.core.SystemAccessRecord.entity;

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
    private String IP;

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
     * 访问用户类型 1:系统注册用户  2:游客
     */
    private Integer userVisitType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
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

    public Integer getUserVisitType() {
        return userVisitType;
    }

    public void setUserVisitType(Integer userVisitType) {
        this.userVisitType = userVisitType;
    }
}
