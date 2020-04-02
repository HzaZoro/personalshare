package cn.pirateswang.core.article.mapper.sql;

import cn.pirateswang.common.base.mapper.sql.BaseSqlProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public class ArticleProvider extends BaseSqlProvider {
    
    public String queryByParams(@Param("articleTitle") String articleTitle, @Param("startDate") Date startDate, 
                                @Param("endDate") Date endDate){
        StringBuffer sql = new StringBuffer();
        sql.append(" select a.id,a.article_date,a.article_title,a.article_views,a.classify_id,a.user_id,u.user_name,c.classify_name from article a ");
        sql.append(" left join user_info u on a.user_id = u.id ");
        sql.append(" left join article_classify c on c.id = a.classify_id ");
        sql.append(" where a.delete_flg = 0 and a.in_use = 1  ");
        if(StringUtils.isNotBlank(articleTitle)){
            sql.append(" and a.article_title like %#{articleTitle}% ");
        }
        if(startDate != null){
            sql.append(" and a.article_date >= #{startDate} ");
        }
        if(endDate != null){
            sql.append(" and a.article_date <= #{endDate} ");
        }
        sql.append(" order by a.article_date desc ");
        return sql.toString();
    }
    
}
