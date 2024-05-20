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
 * @Description: artifact
 * @Author: jeecg-boot
 * @Date:   2024-05-07
 * @Version: V1.0
 */
@Data
@TableName("artifact")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="artifact")
public class Artifact implements Serializable {
    private static final long serialVersionUID = 1L;

	/**文物唯一标识符*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "文物唯一标识符")
    private Integer id;
	/**文物名称*/
	@Excel(name = "文物名称", width = 15)
    @Schema(description = "文物名称")
    private String artifactname;
	/**文物名称中文*/
	@Excel(name = "文物名称中文", width = 15)
    @Schema(description = "文物名称中文")
    private String artifactnameChinese;
	/**国家*/
	@Excel(name = "国家", width = 15)
    @Schema(description = "国家")
    private String country;
	/**文物时代*/
	@Excel(name = "文物时代", width = 15)
    @Schema(description = "文物时代")
    private String relictime;
	/**文物时代中文*/
	@Excel(name = "文物时代中文", width = 15)
    @Schema(description = "文物时代中文")
    private String relictimeChinese;
	/**文物类别*/
	@Excel(name = "文物类别", width = 15)
    @Schema(description = "文物类别")
    private String material;
	/**文物类别中文*/
	@Excel(name = "文物类别中文", width = 15)
    @Schema(description = "文物类别中文")
    private String materialChinese;
	/**文物藏馆*/
	@Excel(name = "文物藏馆", width = 15)
    @Schema(description = "文物藏馆")
    private String library;
	/**文物藏馆中文*/
	@Excel(name = "文物藏馆中文", width = 15)
    @Schema(description = "文物藏馆中文")
    private String libraryChinese;
	/**文物尺寸*/
	@Excel(name = "文物尺寸", width = 15)
    @Schema(description = "文物尺寸")
    private String size;
	/**文物尺寸中文*/
	@Excel(name = "文物尺寸中文", width = 15)
    @Schema(description = "文物尺寸中文")
    private String sizeChinese;
	/**文物描述*/
	@Excel(name = "文物描述", width = 15)
    @Schema(description = "文物描述")
    private String description;
	/**文物描述中文*/
	@Excel(name = "文物描述中文", width = 15)
    @Schema(description = "文物描述中文")
    private String descriptionChinese;
	/**文物图片地址*/
	@Excel(name = "文物图片地址", width = 15)
    @Schema(description = "文物图片地址")
    private String moreurl;
	/**文物原地址*/
	@Excel(name = "文物原地址", width = 15)
    @Schema(description = "文物原地址")
    private String imageurl;
	/**文物时间顺序*/
	@Excel(name = "文物时间顺序", width = 15)
    @Schema(description = "文物时间顺序")
    private Integer orderTime;
}
