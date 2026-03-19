package com.github.thundax.bacon.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.thundax.bacon.sys.api.dto.DeptDTO;
import com.github.thundax.bacon.sys.entity.Dept;
import com.github.thundax.bacon.sys.api.vo.DeptExcelVo;
import com.github.thundax.bacon.sys.service.DeptService;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.common.log.annotation.SysLog;
import com.github.thundax.bacon.common.security.annotation.HasPermission;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门管理前端控制器
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/dept")
@Tag(name = "系统：部门管理模块", description = "提供系统部门的查询、维护、导入导出和树结构管理能力")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class DeptController {

	private final DeptService deptService;

	/**
	 * 通过 ID 查询部门
	 * @param id 部门ID
	 * @return 包含部门信息的响应对象
	 */
	@GetMapping("/{id}")
	@Operation(summary = "通过 ID 查询部门", description = "用于通过 ID 查询部门")
	public R<DeptDTO> getById(@PathVariable Long id) {
		return R.ok(toDto(deptService.getById(id)));
	}

	/**
	 * 查询全部部门列表
	 * @return 包含全部部门列表的响应结果
	 */
	@GetMapping("/list")
	@Operation(summary = "查询全部部门列表", description = "用于查询全部部门列表")
	public R<List<DeptDTO>> listDepts() {
		return R.ok(deptService.list().stream().map(this::toDto).toList());
	}

	/**
	 * 查询部门树
	 * @param deptName 部门名称
	 * @return 包含部门树数据的响应结果
	 */
	@GetMapping(value = "/tree")
	@Operation(summary = "查询部门树", description = "用于查询部门树")
	public R getDeptTree(String deptName) {
		return R.ok(deptService.getDeptTree(deptName));
	}

	/**
	 * 新增部门
	 * @param sysDept 部门对象
	 * @return 操作结果
	 */
	@SysLog("新增部门")
	@PostMapping
	@HasPermission("sys_dept_add")
	@Operation(summary = "新增部门", description = "用于新增部门")
	public R saveDept(@Valid @RequestBody DeptDTO dept) {
		Dept entity = new Dept();
		BeanUtil.copyProperties(dept, entity);
		return R.ok(deptService.save(entity));
	}

	/**
	 * 根据ID删除部门
	 * @param id 部门ID
	 * @return 操作结果，成功返回true，失败返回false
	 */
	@SysLog("删除部门")
	@DeleteMapping("/{id}")
	@HasPermission("sys_dept_del")
	@Operation(summary = "根据 ID 删除部门", description = "用于根据 ID 删除部门")
	public R removeById(@PathVariable Long id) {
		return R.ok(deptService.removeDeptById(id));
	}

	/**
	 * 修改部门
	 * @param sysDept 部门对象
	 * @return 操作结果，成功返回success，失败返回false
	 */
	@SysLog("修改部门")
	@PutMapping
	@HasPermission("sys_dept_edit")
	@Operation(summary = "修改部门", description = "用于修改部门")
	public R updateDept(@Valid @RequestBody DeptDTO dept) {
		Dept entity = new Dept();
		BeanUtil.copyProperties(dept, entity);
		entity.setUpdateTime(LocalDateTime.now());
		return R.ok(deptService.updateById(entity));
	}

	/**
	 * 查询部门子级列表
	 * @param deptId 部门ID
	 * @return 包含子级部门列表的响应结果
	 */
	@GetMapping(value = "/getDescendantList/{deptId}")
	@Operation(summary = "查询部门子级列表", description = "用于查询部门子级列表")
	public R<List<DeptDTO>> getDescendantList(@PathVariable Long deptId) {
		return R.ok(deptService.listDescendants(deptId).stream().map(this::toDto).toList());
	}

	/**
	 * 导出部门数据
	 * @return 部门数据列表
	 */
	@ResponseExcel
	@GetMapping("/export")
	@Operation(summary = "导出部门数据", description = "用于导出部门数据")
	public List<DeptExcelVo> exportDepts() {
		return deptService.exportDepts();
	}

	/**
	 * 导入部门信息
	 * @param excelVOList 部门Excel数据列表
	 * @param bindingResult 数据校验结果
	 * @return 导入结果
	 */
	@PostMapping("import")
	@Operation(summary = "导入部门信息", description = "用于导入部门信息")
	public R importDept(@RequestExcel List<DeptExcelVo> excelVOList, BindingResult bindingResult) {
		return deptService.importDept(excelVOList, bindingResult);
	}

	private DeptDTO toDto(Dept dept) {
		if (dept == null) {
			return null;
		}
		DeptDTO dto = new DeptDTO();
		BeanUtil.copyProperties(dept, dto);
		return dto;
	}

}
