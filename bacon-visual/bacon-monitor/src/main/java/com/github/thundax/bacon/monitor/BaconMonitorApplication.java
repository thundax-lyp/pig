
package com.github.thundax.bacon.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 监控中心应用启动类
 *
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class BaconMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaconMonitorApplication.class, args);
	}

}
