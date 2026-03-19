package com.github.thundax.bacon.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thundax.bacon.sys.entity.Post;
import com.github.thundax.bacon.sys.api.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 岗位信息表 Mapper 接口
 *
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

	/**
	 * 通过用户ID，查询岗位信息
	 * @param userId 用户id
	 * @return 岗位信息
	 */
	List<Post> listPostsByUserId(Long userId);

	/**
	 * 通过用户ID查询轻量岗位展示对象
	 * @param userId 用户id
	 * @return 岗位展示对象
	 */
	List<PostVO> listPostVosByUserId(Long userId);

}
