package cn.pirateswang.core.user.mapper.sql;

import cn.pirateswang.common.base.BaseSqlProvider;
import org.apache.ibatis.annotations.Param;

public class UserInfoProvider extends BaseSqlProvider {

    public String findByLoginName(@Param("loginName") String loginName){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from user_info u where u.login_name = #{loginName} and u.delete_flg = 0 and u.in_use = 1");
        return sql.toString();
    }

}
