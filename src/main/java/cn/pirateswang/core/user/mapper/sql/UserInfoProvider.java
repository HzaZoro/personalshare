package cn.pirateswang.core.user.mapper.sql;

import cn.pirateswang.common.base.BaseSqlProvider;
import org.apache.ibatis.annotations.Param;

public class UserInfoProvider extends BaseSqlProvider {

    public String findByLoginName(@Param("loginName") String loginName){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from user_info u where u.login_name = #{loginName}");
        return sql.toString();
    }

}
