package com.github.thundax.bacon.common.file;

import com.github.thundax.bacon.common.file.core.FileProperties;
import com.github.thundax.bacon.common.file.local.LocalFileAutoConfiguration;
import com.github.thundax.bacon.common.file.oss.OssAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * AWS 自动配置类
 *
 */
@Import({ LocalFileAutoConfiguration.class, OssAutoConfiguration.class })
@EnableConfigurationProperties({ FileProperties.class })
public class FileAutoConfiguration {

}
