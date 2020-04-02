package cn.pirateswang.core.articleContent.mapper.sql;

import cn.pirateswang.common.base.mapper.sql.BaseSqlProvider;
import org.apache.ibatis.annotations.Param;

public class ArticleContentProvider extends BaseSqlProvider {
    
    public String findArticlContentByArticlId(@Param("articlId") Long articlId){
        StringBuffer sql = new StringBuffer();
        sql.append(" select c.* from article_content c where c.delete_flg = 0 and c.article_id = #{articlId} order by c.create_time desc ");
        return sql.toString();
    }
    
}
