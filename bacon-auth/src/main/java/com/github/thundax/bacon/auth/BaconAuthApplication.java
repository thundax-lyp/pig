
package com.github.thundax.bacon.auth;

import com.github.thundax.bacon.common.feign.annotation.EnableBaconFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 认证授权中心应用启动类
 *
 */
@EnableBaconFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class BaconAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaconAuthApplication.class, args);
	}

}
