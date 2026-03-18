
package com.github.thundax.bacon;

import com.github.thundax.bacon.common.security.annotation.EnableBaconResourceServer;
import com.github.thundax.bacon.common.swagger.annotation.EnableBaconDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 单体版本启动器，运行此模块即可启动整个系统
 *
 */
@SpringBootApplication
@EnableBaconResourceServer
@EnableBaconDoc(value = "admin", isMicro = false)
public class BaconBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaconBootApplication.class, args);
	}

}
