
package com.github.thundax.bacon.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关应用
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BaconGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaconGatewayApplication.class, args);
	}

}
