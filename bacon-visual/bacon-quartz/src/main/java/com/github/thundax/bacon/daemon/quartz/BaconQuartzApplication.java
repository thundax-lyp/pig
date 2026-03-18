package com.github.thundax.bacon.daemon.quartz;

import com.github.thundax.bacon.common.feign.annotation.EnableBaconFeignClients;
import com.github.thundax.bacon.common.security.annotation.EnableBaconResourceServer;
import com.github.thundax.bacon.common.swagger.annotation.EnableBaconDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * PigQuartz应用启动类
 * <p>
 * 集成定时任务、Feign客户端、资源服务及服务发现功能
 *
 */
@EnableBaconDoc("job")
@EnableBaconFeignClients
@EnableBaconResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class BaconQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaconQuartzApplication.class, args);
	}

}
