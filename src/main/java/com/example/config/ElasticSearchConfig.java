package com.example.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import com.example.dao.CourseDAOImpl;

@Configuration
@PropertySource(value = "classpath:elasticsearch.properties")
@EnableElasticsearchRepositories
public class ElasticSearchConfig {
    @Value("${spring.elasticsearch.host}")  
    private String host;  
    @Value("${spring.elasticsearch.port}")  
    private int port;
	private static final Logger logger = LoggerFactory.getLogger(CourseDAOImpl.class);
	@Bean
	public TransportClient TransportClient(){
	    Settings settings = Settings.builder()  
	            .put("cluster.name", "my-application").build();
        TransportClient client = null;
        try {
        	client = TransportClient
                .builder()
                .settings(settings)  
                .build()
                .addTransportAddress(  
                        new InetSocketTransportAddress(InetAddress.getByName(host), port)  
                );
        }catch(UnknownHostException e) {
        	logger.error("ElasticSearch create error:"+e);
        }
        return client;
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
	        return new ElasticsearchTemplate(TransportClient());
	}
}
