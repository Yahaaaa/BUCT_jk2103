package org.jeecg.modules.demo.se.controller;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.se.entity.SEUser;
import org.jeecg.modules.demo.se.service.ISEUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2024-05-07
 * @Version: V1.0
 */
@Tag(name="user")
@RestController
@RequestMapping("/se/user")
@Slf4j
public class SEUserController extends JeecgController<SEUser, ISEUserService> {
	@Autowired
	private ISEUserService userService;
	
	/**
	 * 分页列表查询
	 *
	 * @param SEUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "user-分页列表查询")
	@Operation(summary="user-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SEUser>> queryPageList(SEUser SEUser,
											   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
											   HttpServletRequest req) {
		QueryWrapper<SEUser> queryWrapper = QueryGenerator.initQueryWrapper(SEUser, req.getParameterMap());
		Page<SEUser> page = new Page<SEUser>(pageNo, pageSize);
		IPage<SEUser> pageList = userService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param SEUser
	 * @return
	 */
	@AutoLog(value = "user-添加")
	@Operation(summary="user-添加")
	//@RequiresPermissions("se:user:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody SEUser SEUser) {
		userService.save(SEUser);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param SEUser
	 * @return
	 */
	@AutoLog(value = "user-编辑")
	@Operation(summary="user-编辑")
	//@RequiresPermissions("se:user:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody SEUser SEUser) {
		userService.updateById(SEUser);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "user-通过id删除")
	@Operation(summary="user-通过id删除")
	//@RequiresPermissions("se:user:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		userService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "user-批量删除")
	@Operation(summary="user-批量删除")
	//@RequiresPermissions("se:user:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.userService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "user-通过id查询")
	@Operation(summary="user-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SEUser> queryById(@RequestParam(name="id",required=true) String id) {
		SEUser SEUser = userService.getById(id);
		if(SEUser ==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(SEUser);
	}

	 /**
	  * 通过id禁用
	  *
	  * @param id
	  * @return
	  */
	 //@AutoLog(value = "user-通过id禁用")
	 @Operation(summary="user-通过id禁用")
	 @GetMapping(value = "/banById")
	 public Result<SEUser> banById(@RequestParam(name="id",required=true) String id) {
		 SEUser SEUser = userService.getById(id);
		 if(SEUser ==null) {
			 return Result.error("未找到对应数据");
		 }
		 userService.banById(SEUser);
		 return Result.OK("禁用成功!");
	 }

	 /**
	  * 通过id解禁
	  *
	  * @param id
	  * @return
	  */
	 //@AutoLog(value = "user-通过id解禁")
	 @Operation(summary="user-通过id解禁")
	 @GetMapping(value = "/unbanById")
	 public Result<SEUser> unbanById(@RequestParam(name="id",required=true) String id) {
		 SEUser SEUser = userService.getById(id);
		 if(SEUser ==null) {
			 return Result.error("未找到对应数据");
		 }
		 userService.unbanById(SEUser);
		 return Result.OK("解禁成功!");
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param SEUser
    */
    @RequiresPermissions("se:user:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SEUser SEUser) {
        return super.exportXls(request, SEUser, SEUser.class, "user");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("se:user:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, SEUser.class);
    }

}
