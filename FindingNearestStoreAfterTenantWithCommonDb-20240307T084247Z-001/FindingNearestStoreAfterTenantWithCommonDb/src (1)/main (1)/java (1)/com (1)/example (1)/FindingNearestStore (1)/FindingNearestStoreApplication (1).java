package com.example.FindingNearestStore;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.security.Security;

@SpringBootApplication()
@EnableCaching
public class FindingNearestStoreApplication {
	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		SpringApplication.run(FindingNearestStoreApplication.class, args);

	}

}
