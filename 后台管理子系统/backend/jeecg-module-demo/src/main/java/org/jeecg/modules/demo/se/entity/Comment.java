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
 * @Description: comment
 * @Author: jeecg-boot
 * @Date:   2024-05-07
 * @Version: V1.0
 */
@Data
@TableName("comment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id")
    private Integer id;
	/**userId*/
	@Excel(name = "userId", width = 15)
    @Schema(description = "userId")
    private Integer userId;
	/**artifactId*/
	@Excel(name = "artifactId", width = 15)
    @Schema(description = "artifactId")
    private Integer artifactId;
	/**评论内容*/
	@Excel(name = "评论内容", width = 15)
    @Schema(description = "评论内容")
    private String content;
	/**createTime*/
    @Schema(description = "createTime")
    private String createTime;
}
