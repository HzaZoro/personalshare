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
    COOKIE_IS_NULL(6,"Cookie获取失败"),
    
    
    REQUEST_PARAM_IS_NULL(15,"请求参数为空"),
    
    
    LOGIN_USER_NAME_IS_NULL(20,"用户名未填写"),
    LOGIN_PASSWORD_IS_NULL(21,"密码未填写"),
    SYSTEM_USER_INFO_ERROR(22,"系统设置用户信息有误"),
    PASSWORD_ERROR(23,"密码有误"),
    USER_INFO_IS_NOT_EXIST(25,"用户信息不存在"),
    USER_INFO_MORE_THAN_ONE(26,"查询到多条用户信息"),
    
    
    ARTICLE_TITLE_IS_NULL(50,"文章标题未填写"),
    ARTICLE_CLASSIFY_ID_IS_NULL(51,"文章分类未选择"),
    ARTICLE_CLASSIFY_IS_NULL(52,"文章分类不存在或已删除"),
    ARTICLE_ID_IS_NULL(53,"文章主键ID为空"),
    ARTICLE_IS_NULL(54,"文章信息不存在或已被删除"),
    
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
