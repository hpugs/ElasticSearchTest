package com.hpugs.elasticsearch.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
public class ElasticSearchClientConfig {

    @Value("${elasticsearch.hostname}")
    private String hostname;

    @Value("${elasticsearch.port}")
    private Integer port;

    @Value("${elasticsearch.connTimeout}")
    private Integer connTimeout;

    @Value("${elasticsearch.socketTimeout}")
    private Integer socketTimeout;

    @Value("${elasticsearch.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        // 初始化 RestClient, hostName 和 port 填写集群的内网 VIP 地址与端口
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(hostname, port, "http"))
                .setRequestConfigCallback(builder -> {
                    builder.setConnectTimeout(connTimeout);
                    builder.setSocketTimeout(socketTimeout);
                    builder.setConnectionRequestTimeout(connectionRequestTimeout);
                    return builder;
                });

        //保活策略
        //注意事项：RestHighLevelClient 长时间没有流量访问，隔段时间访问可能出现socket超时或者8,000milliseconds timeout on connection http-outgoing-1[ACTIVE]等错误。
        // 解决方案就是在集成RestHighLevelClient 是设置保活策略，即下面的代码的的：
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setDefaultIOReactorConfig(IOReactorConfig.custom()
                        .setSoKeepAlive(true)
                        .build()));

        // 设置认证信息
        if(StringUtils.isNotEmpty(username)&&StringUtils.isNotEmpty(password)) {
            //如果没配置密码就可以不用下面这两部
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
            restClientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
                httpAsyncClientBuilder.disableAuthCaching();
                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            });
        }
        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
        return client;


        // 简单创建RestClient
//        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
//                //指定客户端ip和端口
//                RestClient.builder(new HttpHost(hostname, port, "http"))
//        );
//        return restHighLevelClient;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(@Qualifier("restHighLevelClient") RestHighLevelClient restHighLevelClient) {
        return new ElasticsearchRestTemplate(restHighLevelClient);
    }

}
