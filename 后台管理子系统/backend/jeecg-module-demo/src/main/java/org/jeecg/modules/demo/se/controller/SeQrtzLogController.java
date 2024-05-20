package org.jeecg.modules.demo.se.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.se.entity.SeQrtzLog;
import org.jeecg.modules.demo.se.service.ISeQrtzLogService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: quartz日志
 * @Author: jeecg-boot
 * @Date:   2024-05-08
 * @Version: V1.0
 */
@Tag(name="quartz日志")
@RestController
@RequestMapping("/se/seQrtzLog")
@Slf4j
public class SeQrtzLogController extends JeecgController<SeQrtzLog, ISeQrtzLogService> {
	@Autowired
	private ISeQrtzLogService seQrtzLogService;
	
	/**
	 * 分页列表查询
	 *
	 * @param seQrtzLog
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "quartz日志-分页列表查询")
	@Operation(summary="quartz日志-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SeQrtzLog>> queryPageList(SeQrtzLog seQrtzLog,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SeQrtzLog> queryWrapper = QueryGenerator.initQueryWrapper(seQrtzLog, req.getParameterMap());
		Page<SeQrtzLog> page = new Page<SeQrtzLog>(pageNo, pageSize);
		IPage<SeQrtzLog> pageList = seQrtzLogService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param seQrtzLog
	 * @return
	 */
	@AutoLog(value = "quartz日志-添加")
	@Operation(summary="quartz日志-添加")
	@RequiresPermissions("se:se_qrtz_log:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody SeQrtzLog seQrtzLog) {
		seQrtzLogService.save(seQrtzLog);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param seQrtzLog
	 * @return
	 */
	@AutoLog(value = "quartz日志-编辑")
	@Operation(summary="quartz日志-编辑")
	@RequiresPermissions("se:se_qrtz_log:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody SeQrtzLog seQrtzLog) {
		seQrtzLogService.updateById(seQrtzLog);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "quartz日志-通过id删除")
	@Operation(summary="quartz日志-通过id删除")
	@RequiresPermissions("se:se_qrtz_log:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		seQrtzLogService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "quartz日志-批量删除")
	@Operation(summary="quartz日志-批量删除")
	@RequiresPermissions("se:se_qrtz_log:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.seQrtzLogService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "quartz日志-通过id查询")
	@Operation(summary="quartz日志-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SeQrtzLog> queryById(@RequestParam(name="id",required=true) String id) {
		SeQrtzLog seQrtzLog = seQrtzLogService.getById(id);
		if(seQrtzLog==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(seQrtzLog);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param seQrtzLog
    */
    @RequiresPermissions("se:se_qrtz_log:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SeQrtzLog seQrtzLog) {
        return super.exportXls(request, seQrtzLog, SeQrtzLog.class, "quartz日志");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("se:se_qrtz_log:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, SeQrtzLog.class);
    }

}
