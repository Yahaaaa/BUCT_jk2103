package org.jeecg.modules.demo.testComment.controller;

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
import org.jeecg.modules.demo.testComment.entity.TestComment;
import org.jeecg.modules.demo.testComment.service.ITestCommentService;

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
 * @Description: 测试评论
 * @Author: jeecg-boot
 * @Date:   2024-04-15
 * @Version: V1.0
 */
@Tag(name="测试评论")
@RestController
@RequestMapping("/org.jeecg.modules/testComment")
@Slf4j
public class TestCommentController extends JeecgController<TestComment, ITestCommentService> {
	@Autowired
	private ITestCommentService testCommentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param testComment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "测试评论-分页列表查询")
	@Operation(summary="测试评论-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TestComment>> queryPageList(TestComment testComment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TestComment> queryWrapper = QueryGenerator.initQueryWrapper(testComment, req.getParameterMap());
		Page<TestComment> page = new Page<TestComment>(pageNo, pageSize);
		IPage<TestComment> pageList = testCommentService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param testComment
	 * @return
	 */
	@AutoLog(value = "测试评论-添加")
	@Operation(summary="测试评论-添加")
	@RequiresPermissions("org.jeecg.modules:test_comment:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TestComment testComment) {
		testCommentService.save(testComment);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param testComment
	 * @return
	 */
	@AutoLog(value = "测试评论-编辑")
	@Operation(summary="测试评论-编辑")
	@RequiresPermissions("org.jeecg.modules:test_comment:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TestComment testComment) {
		testCommentService.updateById(testComment);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "测试评论-通过id删除")
	@Operation(summary="测试评论-通过id删除")
	@RequiresPermissions("org.jeecg.modules:test_comment:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testCommentService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "测试评论-批量删除")
	@Operation(summary="测试评论-批量删除")
	@RequiresPermissions("org.jeecg.modules:test_comment:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testCommentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "测试评论-通过id查询")
	@Operation(summary="测试评论-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TestComment> queryById(@RequestParam(name="id",required=true) String id) {
		TestComment testComment = testCommentService.getById(id);
		if(testComment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(testComment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param testComment
    */
    @RequiresPermissions("org.jeecg.modules:test_comment:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestComment testComment) {
        return super.exportXls(request, testComment, TestComment.class, "测试评论");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("org.jeecg.modules:test_comment:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TestComment.class);
    }

}
