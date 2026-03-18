
package com.github.thundax.bacon.common.security.annotation;

import com.github.thundax.bacon.common.security.component.BaconResourceServerAutoConfiguration;
import com.github.thundax.bacon.common.security.component.BaconResourceServerConfiguration;
import com.github.thundax.bacon.common.security.feign.BaconFeignClientConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用Pig资源服务器注解
 * <p>
 * 通过导入相关配置类启用Pig资源服务器功能
 *
 */
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({ BaconResourceServerAutoConfiguration.class, BaconResourceServerConfiguration.class,
		BaconFeignClientConfiguration.class })
public @interface EnableBaconResourceServer {

}
