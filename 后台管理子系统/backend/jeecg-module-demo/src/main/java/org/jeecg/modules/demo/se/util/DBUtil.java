package org.jeecg.modules.demo.se.util;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.jeecg.common.util.dynamic.db.DynamicDBUtil.getDbSourceByDbKey;

/**
 * @author Custom
 * @date 2024/5/8
 */
@Slf4j
public class DBUtil {
    /**
     * 根据数据源获取NamedParameterJdbcTemplate
     * @param dbKey
     * @return
     */
    public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(String dbKey) {
        DruidDataSource dataSource = getDbSourceByDbKey(dbKey);
        return new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * 查询列表数据
     * @param npJdbcTemplate
     * @param sql
     * @param param
     * @return
     */
    public static List<Map<String, Object>> findListByNamedParam(NamedParameterJdbcTemplate npJdbcTemplate, String sql, Map<String, Object> param) {
        List<Map<String, Object>> list = npJdbcTemplate.queryForList(sql, param);
        return list;
    }


}
