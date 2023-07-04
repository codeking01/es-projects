package cn.itcast.hotel.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author CodeKing
 * @since 2023/7/3  18:24
 */
@Component
public class RestHighLevelConfig {
    @Bean
    public RestHighLevelClient client(){
        return  new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://10.234.170.128:9200")
        ));
    }
}
