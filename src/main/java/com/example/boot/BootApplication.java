package com.example.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/* jap Auditing(감시) 사용시 설정 */
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

}





//package com.example.boot;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//@SpringBootApplication
//@EnableJpaAuditing
//public class BootApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(BootApplication.class, args);
//	}
//
//}
