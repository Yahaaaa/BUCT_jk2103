package org.jeecg.modules.demo.se.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: quartz日志
 * @Author: jeecg-boot
 * @Date:   2024-05-08
 * @Version: V1.0
 */
@Data
@TableName("se_qrtz_log")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="quartz日志")
public class SeQrtzLog implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private String id;
	/**创建人*/
    @Schema(description = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建日期")
    private Date createTime;
	/**更新人*/
    @Schema(description = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新日期")
    private Date updateTime;
	/**是否自动执行(否0, 是1)*/
	@Excel(name = "是否自动执行(否0, 是1)", width = 15)
    @Schema(description = "是否自动执行(否0, 是1)")
    private Integer isAuto;
	/**是否成功(否0, 是1)*/
	@Excel(name = "是否成功(否0, 是1)", width = 15)
    @Schema(description = "是否成功(否0, 是1)")
    private Integer isSuccess;
	/**操作类型(备份0, 还原1)*/
	@Excel(name = "操作类型(备份0, 还原1)", width = 15)
    @Schema(description = "操作类型(备份0, 还原1)")
    private Integer operateType;
	/**日志内容*/
	@Excel(name = "日志内容", width = 15)
    @Schema(description = "日志内容")
    private String logContent;
	/**耗时*/
	@Excel(name = "耗时", width = 15)
    @Schema(description = "耗时")
    private Long costTime;
}
