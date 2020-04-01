package cn.pirateswang.common.publicEnum;

/**
 * 返回状态枚举
 */
public enum ErrorEnum {
    
    SUCCESS(0,"成功"),
    SYSTEM_ERROR(-1,"网络繁忙,请稍后重试"),
    ILLEGAL_DOMAIN(1,"非法域名"),
    ILLEGAL_VISIT(2,"访问IP被禁止")
    ;    
    
    private Integer code;
    
    private String msg;

    ErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public Integer getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
}
