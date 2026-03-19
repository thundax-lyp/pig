package com.github.thundax.bacon.codegen;

import com.github.thundax.bacon.common.datasource.annotation.EnableDynamicDataSource;
import com.github.thundax.bacon.common.feign.annotation.EnableBaconFeignClients;
import com.github.thundax.bacon.common.security.annotation.EnableBaconResourceServer;
import com.github.thundax.bacon.common.swagger.annotation.EnableBaconDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 代码生成模块应用启动类
 *
 */
@EnableDynamicDataSource
@EnableBaconFeignClients
@EnableBaconDoc("gen")
@EnableDiscoveryClient
@EnableBaconResourceServer
@SpringBootApplication
public class BaconCodeGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaconCodeGenApplication.class, args);
	}

}
