package org.jeecg.modules.demo.se.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2024-05-07
 * @Version: V1.0
 */
@Data
@TableName("user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="user")
public class SEUser implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id")
    private Integer id;
	/**用户昵称*/
	@Excel(name = "用户昵称", width = 15)
    @Schema(description = "用户昵称")
    private String username;
	/**密码*/
	@Excel(name = "密码", width = 15)
    @Schema(description = "密码")
    private String password;
	/**头像URL*/
	@Excel(name = "头像URL", width = 15)
    @Schema(description = "头像URL")
    private String avatarUrl;
	/**电子邮件地址*/
	@Excel(name = "电子邮件地址", width = 15)
    @Schema(description = "电子邮件地址")
    private String email;
	/**手机号码*/
	@Excel(name = "手机号码", width = 15)
    @Schema(description = "手机号码")
    private String phone;
	/**createTime*/
    @Schema(description = "createTime")
    private Date createTime;
	/**updateTime*/
    @Schema(description = "updateTime")
    private Date updateTime;
	/**isbanned*/
	@Excel(name = "isbanned", width = 15)
    @Schema(description = "isbanned")
    private Integer isbanned;
	/**sex*/
	@Excel(name = "sex", width = 15)
    @Schema(description = "sex")
    private String sex;
	/**age*/
	@Excel(name = "age", width = 15)
    @Schema(description = "age")
    private String age;
}
