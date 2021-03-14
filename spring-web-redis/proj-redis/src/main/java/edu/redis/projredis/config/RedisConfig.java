package edu.redis.projredis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.DefaultJedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisPoolingClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Profile("!test")
@Configuration
public class RedisConfig {

	@Bean
	public JedisClientConfiguration jedisClientConfig()
	{
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder builder = (JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder(); 
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(10);
		config.setMaxIdle(8);
		config.setMinIdle(4);
		return builder.poolConfig(config).build();
	}
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		redisConfig.setHostName("localhost");
		redisConfig.setPort(6379);
		return new JedisConnectionFactory(redisConfig,jedisClientConfig());
	}
	 
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	    RedisTemplate<String, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    return template;
	}
	
}
