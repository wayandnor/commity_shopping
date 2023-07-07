package com.nor.cs.common.config;

import com.obs.services.ObsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadFileConfig {
    @Value("${huawei-cloud.endpoint}")
    private String endpoint;
    
    @Value("${huawei-cloud.ak}")
    private String ak;
    
    @Value("${huawei-cloud.sk}")
    private String sk;
    
    @Bean
    public ObsClient createObsClient() {
        ObsClient obsClient = new ObsClient(ak, sk, endpoint);
        return obsClient;
    }
}
