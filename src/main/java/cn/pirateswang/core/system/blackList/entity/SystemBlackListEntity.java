package cn.pirateswang.core.system.blackList.entity;

import cn.pirateswang.common.base.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "system_black_list")
public class SystemBlackListEntity extends BaseEntity {
    
    private String ip;
    
    private Integer port;
    
    private Integer type;
    
    private Integer inUse;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInUse() {
        return inUse;
    }

    public void setInUse(Integer inUse) {
        this.inUse = inUse;
    }
}
