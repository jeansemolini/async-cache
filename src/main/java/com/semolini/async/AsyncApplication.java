package com.semolini.async;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializerConfig;
import com.semolini.async.cache.UserSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.ObjectInputFilter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableCaching
public class AsyncApplication {

	public static void main(String[] args) {

		SpringApplication.run(AsyncApplication.class, args).close();
	}

	@Bean(name = "exemple")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		return executor;
	}

	@Bean(name = "exercicio")
	public Executor taskExecutor1() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(3);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Teste-");
		executor.initialize();
		return executor;
	}

	@Bean
	public Config hazelCastConfig() {
		Config config = new Config();
		config.addMapConfig(mapConfig());
		config.getSerializationConfig().addSerializerConfig(serializerConfig());
		return config;
	}

	private MapConfig mapConfig() {
		MapConfig mapConfig = new MapConfig("urls");
		mapConfig.setTimeToLiveSeconds(15);
		mapConfig.setMaxIdleSeconds(15);
		return mapConfig;
	}

	private SerializerConfig serializerConfig() {
		return new SerializerConfig().setImplementation(new UserSerializer()).setTypeClass(CompletableFuture.class);
	}

}
