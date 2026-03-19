package com.github.thundax.bacon.sys;

import com.github.thundax.bacon.common.feign.annotation.EnableBaconFeignClients;
import com.github.thundax.bacon.common.security.annotation.EnableBaconResourceServer;
import com.github.thundax.bacon.common.swagger.annotation.EnableBaconDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户统一管理系统
 *
 */
@EnableBaconDoc(value = "admin")
@EnableBaconFeignClients
@EnableBaconResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class BaconSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaconSysApplication.class, args);
	}

}
