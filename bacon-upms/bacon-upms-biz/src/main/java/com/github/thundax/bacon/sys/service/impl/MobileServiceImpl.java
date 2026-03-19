package com.github.thundax.bacon.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.thundax.bacon.sys.entity.User;
import com.github.thundax.bacon.sys.mapper.UserMapper;
import com.github.thundax.bacon.sys.service.MobileService;
import com.github.thundax.bacon.common.core.constant.SecurityConstants;
import com.github.thundax.bacon.common.core.exception.ErrorCodes;
import com.github.thundax.bacon.common.core.support.DefaultCodeCacheService;
import com.github.thundax.bacon.common.core.util.MsgUtils;
import com.github.thundax.bacon.common.core.util.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 手机登录相关业务实现类
 *
 */
@Slf4j
@Service
@AllArgsConstructor
public class MobileServiceImpl implements MobileService {

	private final UserMapper userMapper;

	private final DefaultCodeCacheService defaultCodeCacheService;

	/**
	 * 发送手机验证码
	 * @param mobile 手机号码
	 * @return 返回操作结果，包含验证码发送状态及验证码信息
	 */
	@Override
	public R<Boolean> sendSmsCode(String mobile) {
		List<User> userList = userMapper.selectList(Wrappers.<User>query().lambda().eq(User::getPhone, mobile));

		if (CollUtil.isEmpty(userList)) {
			log.info("手机号未注册: {}", mobile);
			return R.ok(Boolean.FALSE, MsgUtils.getMessage(ErrorCodes.SYS_APP_PHONE_UNREGISTERED, mobile));
		}

		String codeObj = defaultCodeCacheService.get(mobile);

		if (codeObj != null) {
			log.info("手机号验证码未过期: {}, {}", mobile, codeObj);
			return R.ok(Boolean.FALSE, MsgUtils.getMessage(ErrorCodes.SYS_APP_SMS_OFTEN));
		}

		String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));
		log.info("手机号验证码生成成功: {}, {}", mobile, code);
		defaultCodeCacheService.put(mobile, code);

		// 集成短信服务发送验证码
		SmsBlend smsBlend = SmsFactory.getSmsBlend();
		if (Objects.isNull(smsBlend)) {
			return R.ok(Boolean.FALSE, MsgUtils.getMessage(ErrorCodes.SYS_SMS_BLEND_UNREGISTERED));
		}

		SmsResponse smsResponse = smsBlend.sendMessage(mobile, new LinkedHashMap<>(Map.of("code", code)));
		log.debug("短信验证码发送结果: {}", smsResponse);
		return R.ok(Boolean.TRUE);
	}

}
