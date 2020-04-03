package cn.pirateswang.core.system.blackList.mapper.sql;

import cn.pirateswang.common.base.mapper.sql.BaseSqlProvider;

public class SystemBlackListProvider extends BaseSqlProvider {
    
    public String findAllBlackList(){
        StringBuffer sql = new StringBuffer();
        sql.append(" select s.* from system_black_list s where s.delete_flg = 0 and s.in_use = 1 ");
        return sql.toString();
    }
    
}
