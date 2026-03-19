package com.github.thundax.bacon.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.thundax.bacon.sys.api.dto.PostDTO;
import com.github.thundax.bacon.sys.entity.Post;
import com.github.thundax.bacon.sys.api.vo.PostExcelVO;
import com.github.thundax.bacon.sys.service.PostService;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.common.log.annotation.SysLog;
import com.github.thundax.bacon.common.security.annotation.HasPermission;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位信息表管理控制器
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/post")
@Tag(name = "系统：岗位信息表管理模块", description = "提供系统岗位的查询、维护、导入导出等管理能力")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class PostController {

	private final PostService postService;

	/**
	 * 查询岗位列表
	 * @return 包含岗位列表的响应结果
	 */
	@GetMapping("/list")
	@Operation(summary = "查询岗位列表", description = "用于查询岗位列表")
	public R<List<PostDTO>> listPosts() {
		return R.ok(postService.list(Wrappers.emptyWrapper()).stream().map(this::toDto).toList());
	}

	/**
	 * 分页查询岗位信息
	 * @param page 分页参数对象
	 * @param post 岗位查询条件对象
	 * @return 分页查询结果
	 */
	@GetMapping("/page")
	@HasPermission("sys_post_view")
	@Operation(summary = "分页查询岗位", description = "用于分页查询岗位")
	public R<IPage<PostDTO>> getPostPage(@ParameterObject Page<Post> page, @ParameterObject PostDTO post) {
		IPage<Post> result = postService.page(page, Wrappers.<Post>lambdaQuery()
			.like(StrUtil.isNotBlank(post.getPostName()), Post::getPostName, post.getPostName()));
		Page<PostDTO> dtoPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
		dtoPage.setRecords(result.getRecords().stream().map(this::toDto).toList());
		return R.ok(dtoPage);
	}

	/**
	 * 通过 ID 查询岗位
	 * @param postId 岗位id
	 * @return 包含岗位信息的响应结果
	 */
	@HasPermission("sys_post_view")
	@GetMapping("/details/{postId}")
	@Operation(summary = "通过 ID 查询岗位", description = "用于通过 ID 查询岗位")
	public R<PostDTO> getById(@PathVariable("postId") Long postId) {
		return R.ok(toDto(postService.getById(postId)));
	}

	/**
	 * 查询岗位详情
	 * @param query 查询条件
	 * @return 统一响应结果R，包含查询到的岗位信息
	 */
	@GetMapping("/details")
	@HasPermission("sys_post_view")
	@Operation(summary = "查询岗位详情", description = "用于查询岗位详情")
	public R<PostDTO> getDetails(PostDTO query) {
		Post post = new Post();
		BeanUtil.copyProperties(query, post);
		return R.ok(toDto(postService.getOne(Wrappers.query(post), false)));
	}

	/**
	 * 新增岗位信息
	 * @param post 岗位信息对象
	 * @return 操作结果
	 */
	@PostMapping
	@SysLog("新增岗位")
	@HasPermission("sys_post_add")
	@Operation(summary = "新增岗位", description = "用于新增岗位")
	public R<Boolean> savePost(@Valid @RequestBody PostDTO post) {
		Post entity = new Post();
		BeanUtil.copyProperties(post, entity);
		return R.ok(postService.save(entity));
	}

	/**
	 * 修改岗位信息
	 * @param post 岗位信息对象
	 * @return 操作结果
	 */
	@PutMapping
	@SysLog("修改岗位")
	@HasPermission("sys_post_edit")
	@Operation(summary = "修改岗位", description = "用于修改岗位")
	public R<Boolean> updatePost(@Valid @RequestBody PostDTO post) {
		Post entity = new Post();
		BeanUtil.copyProperties(post, entity);
		return R.ok(postService.updateById(entity));
	}

	/**
	 * 通过 ID 批量删除岗位
	 * @param ids 岗位id数组
	 * @return 统一返回结果
	 */
	@DeleteMapping
	@SysLog("删除岗位")
	@HasPermission("sys_post_del")
	@Operation(summary = "通过 ID 删除岗位", description = "用于通过 ID 删除岗位")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(postService.removeBatchByIds(CollUtil.toList(ids)));
	}

	/**
	 * 导出岗位数据到 Excel
	 * @return 岗位信息Excel文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
	@HasPermission("sys_post_export")
	@Operation(summary = "导出岗位数据到 Excel", description = "用于导出岗位数据到 Excel")
	public List<PostExcelVO> exportPosts() {
		return postService.listPosts();
	}

	/**
	 * 导入岗位信息
	 * @param excelVOList 岗位Excel数据列表
	 * @param bindingResult 数据校验结果
	 * @return 导入结果
	 */
	@PostMapping("/import")
	@HasPermission("sys_post_export")
	@Operation(summary = "导入岗位信息", description = "用于导入岗位信息")
	public R importRole(@RequestExcel List<PostExcelVO> excelVOList, BindingResult bindingResult) {
		return postService.importPost(excelVOList, bindingResult);
	}

	private PostDTO toDto(Post post) {
		if (post == null) {
			return null;
		}
		PostDTO dto = new PostDTO();
		BeanUtil.copyProperties(post, dto);
		return dto;
	}

}
