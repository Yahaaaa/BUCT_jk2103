package org.jeecg.modules.demo.se.job;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.se.entity.Comment;
import org.jeecg.modules.demo.se.entity.SEUser;
import org.jeecg.modules.demo.se.entity.SeQrtzLog;
import org.jeecg.modules.demo.se.service.ICommentService;
import org.jeecg.modules.demo.se.service.ISeQrtzLogService;
import org.jeecg.modules.demo.se.service.impl.CommentServiceImpl;
import org.jeecg.modules.demo.se.service.impl.SeQrtzLogServiceImpl;
import org.jeecg.modules.demo.se.util.DBUtil;
import org.jeecg.modules.demo.se.util.StringUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static org.jeecg.modules.demo.se.util.DBUtil.findListByNamedParam;
import static org.jeecg.modules.demo.se.util.DBUtil.getNamedParameterJdbcTemplate;


/**
 * @author Custom
 * @date 2024/5/8
 */
@Slf4j
public class BackupJob implements Job {
    private final String[] EXCEPT = {"demo_", "jeecg_", "onl", "jimu", "sys_", "rep_", "qrtz_", "auth_", "django_"};
    /**
     * 若参数变量名修改 QuartzJobController中也需对应修改
     */
    private String parameter;

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long beginTime = System.currentTimeMillis();
        int isSuccess = 0;
        String content = "操作成功!";
        List<String> finishedTable = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(this.parameter);

            String sourceName = (String) json.get("source");
            String targetName = (String) json.get("target");

            NamedParameterJdbcTemplate source = getNamedParameterJdbcTemplate(sourceName);
            NamedParameterJdbcTemplate target = getNamedParameterJdbcTemplate(targetName);

            if (source == null) {
                throw new Exception("source名称错误");
            }
            if (target == null) {
                throw new Exception("target名称错误");
            }

            var list = source.queryForList("SHOW TABLES", new HashMap<>(), String.class);

            for (String tableName : list) {
                boolean flag = true;
                for (String s : EXCEPT) {
                    if (tableName.startsWith(s)) {
                        flag = false;
                    }
                }
                if (flag) {
                    backup(source, target, tableName);
                    finishedTable.add(tableName);
                }
            }
            isSuccess = 1;
        } catch (Exception e) {
            content = e.getMessage();
        } finally {
            content += "已同步" + finishedTable;
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            saveLog(isSuccess, content, time);
        }
    }

    private void backup(NamedParameterJdbcTemplate source, NamedParameterJdbcTemplate target, String tableName) {
        // 创建新表
        var list = DBUtil.findListByNamedParam(source, "SHOW CREATE TABLE " + tableName, null);
        String createSql = (String) list.get(0).get("CREATE TABLE");
        String dropSql = "DROP TABLE IF EXISTS " + tableName;
        target.update(dropSql, new HashMap<>());
        target.update(createSql, new HashMap<>());

        // 查询全量数据
        List<Map<String, Object>> entityList = source.queryForList("SELECT * FROM " + tableName, new HashMap<>());

        // 写入全量数据
        if (!entityList.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            Map<String, Object> entity = entityList.get(0);
            // 拼接模板sql
            sb.append("INSERT INTO ")
                    .append(tableName)
                    .append("(")
                    .append(String.join(",", entity.keySet()))
                    .append(") VALUES (:")
                    .append(String.join(", :", entity.keySet()))
                    .append(")");
            String insertSql = sb.toString();

            // 格式化数据
            MapSqlParameterSource[] batchParams = new MapSqlParameterSource[entityList.size()];
            for (int i = 0;i < batchParams.length;i ++) {
                MapSqlParameterSource params = new MapSqlParameterSource();
                entityList.get(i).forEach((k, v) -> params.addValue(k, v));
                batchParams[i] = params;
            }

            target.batchUpdate(insertSql, batchParams);
        }
    }

    private void saveLog(int isSuccess, String content, long time) {
        ISeQrtzLogService seQrtzLogService = SpringContextUtils.getBean(SeQrtzLogServiceImpl.class);
        SeQrtzLog seQrtzLog = new SeQrtzLog();
        seQrtzLog.setCreateTime(new Date());
        seQrtzLog.setIsAuto(0);
        seQrtzLog.setOperateType(0);
        seQrtzLog.setIsSuccess(isSuccess);
        seQrtzLog.setLogContent(content);
        seQrtzLog.setCostTime(time);
        seQrtzLogService.save(seQrtzLog);
    }
}
