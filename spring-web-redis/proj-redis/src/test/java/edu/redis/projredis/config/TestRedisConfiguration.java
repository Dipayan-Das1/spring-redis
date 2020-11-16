package edu.redis.projredis.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisConfiguration {
	private RedisServer redisServer;

	public TestRedisConfiguration(@Value("${spring.redis.host}") String host, @Value("${spring.redis.port}") int port) {
		this.redisServer = new RedisServer(port);
	}

	@PostConstruct
	public void postConstruct() {
		redisServer.start();
	}

	@PreDestroy
	public void preDestroy() {
		redisServer.stop();
	}

	@Bean
	public LettuceConnectionFactory redisConnectionFactory(@Value("${spring.redis.host}") String host,
			@Value("${spring.redis.port}") int port) {
		return new LettuceConnectionFactory(host, port);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		return template;
	}
}
