package cn.pirateswang.common.publicEnum;

/**
 * 返回状态枚举
 */
public enum ErrorEnum {
    
    SUCCESS(0,"成功"),
    SYSTEM_ERROR(-1,"网络繁忙,请稍后重试"),
    ILLEGAL_DOMAIN(1,"非法域名"),
    ILLEGAL_VISIT(2,"访问IP被禁止"),
    TOKEN_VERIFY_FAILURE(3,"token验证不通过"),
    TOKEN_VERIFY_EXCEPTION(4,"token验证异常"),
    TOKEN_OVERDUE(5,"登录超时"),
    COOKIE_IS_NULL(6,"Cookie获取失败")
    
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
