package com.nor.cs.product.service;

import org.springframework.web.multipart.MultipartFile;

public interface SkuFileUploadService {
    String uploadFile(MultipartFile file);
}
