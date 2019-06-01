package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.interceptor.KeyGenerator;
import java.lang.reflect.Method;

@Configuration 
@PropertySource(value = "classpath:/application.properties") 
@EnableCaching 
public class CacheConfig extends CachingConfigurerSupport { 
	@Value("${spring.redis.host}") 
	private String host; 
	@Value("${spring.redis.port}") 
	private int port; 
	@Value("${spring.redis.timeout}") 
	private int timeout; 
	@Bean 
	public KeyGenerator wiselyKeyGenerator(){ 
		return new KeyGenerator() { 
			@Override 
			public Object generate(Object target, Method method, Object... params) { 
				StringBuilder sb = new StringBuilder(); 
				sb.append(target.getClass().getName()); 
				sb.append(method.getName()); 
				for (Object obj : params) { 
					sb.append(obj.toString()); 
				} 
				return sb.toString(); 
			} }; 
			} 
	@Bean 
	public JedisConnectionFactory redisConnectionFactory() { 
		JedisConnectionFactory factory = new JedisConnectionFactory(); 
		factory.setHostName(host); 
		factory.setPort(port); 
		factory.setTimeout(timeout); //设置连接超时时间
		//factory.afterPropertiesSet(); 
		return factory; 
		} 
	@Bean 
	public CacheManager cacheManager(StringRedisTemplate redisTemplate) { 
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate); 
		// Number of seconds before expiration. Defaults to unlimited (0) 
		cacheManager.setDefaultExpiration(20); //设置key-value超时时间 
		return cacheManager; 
	} 
	@Bean 
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) { 
		StringRedisTemplate template = new StringRedisTemplate(factory); 
		setSerializer(template); //设置序列化工具，这样ReportBean不需要实现Serializable接口 
		template.afterPropertiesSet(); 
		return template; 
	} 
	@Bean 
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) { 

    RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
    template.setConnectionFactory(factory);
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    template.setKeySerializer(stringSerializer);
    template.setHashKeySerializer(stringSerializer);
	return template; 
	} 
	private void setSerializer(StringRedisTemplate template) { 
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = 
				new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		//om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
	} 
}

