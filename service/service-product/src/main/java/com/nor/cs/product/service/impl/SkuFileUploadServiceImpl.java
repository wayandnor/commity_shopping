package com.nor.cs.product.service.impl;

import com.nor.cs.product.service.api.SkuFileUploadService;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectRequest;
import com.obs.services.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
@Service
public class SkuFileUploadServiceImpl implements SkuFileUploadService {
    @Resource
    private ObsClient obsClient;
    
    @Value("${huawei-cloud.bucket-name}")
    private String bucketName;
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            InputStream fileInputStream = file.getInputStream();
            PutObjectRequest request = new PutObjectRequest();
            String originObjectName = file.getOriginalFilename();
            String objectUuidPrefix = UUID.randomUUID().toString().replaceAll("-","");
            String objectKey = objectUuidPrefix + originObjectName;
            request.setBucketName(bucketName);
            request.setInput(fileInputStream);
            request.setObjectKey(objectKey);
            PutObjectResult putObjectResult = obsClient.putObject(request);
            String objectUrl = putObjectResult.getObjectUrl();
            return objectUrl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
