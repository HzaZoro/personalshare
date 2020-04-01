package cn.pirateswang.common.utils;

import cn.pirateswang.common.publicEnum.ErrorEnum;
import cn.pirateswang.common.publicVO.ResultVO;
import org.apache.commons.lang3.StringUtils;

public class ResultVOUtils {
    
    public static <T> ResultVO<T> success(T object){
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setData(object);
        resultVO.setCode(ErrorEnum.SUCCESS.getCode());
        resultVO.setMsg(ErrorEnum.SUCCESS.getMsg());
        return resultVO;
    }
    
    public static <T> ResultVO<T> success(){
        return success(null);
    }
    
    public static <T> ResultVO<T> error(ErrorEnum errorEnum){
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(errorEnum.getCode());
        resultVO.setMsg(errorEnum.getMsg());
        return resultVO;
    }
    
    public static <T> ResultVO<T> error(ErrorEnum errorEnum,String customMsg,T data){
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(errorEnum.getCode());
        resultVO.setMsg(errorEnum.getMsg() + (StringUtils.isBlank(customMsg) ? "": ("," + customMsg)));
        resultVO.setData(data);
        return resultVO;
    }
    
    
}
