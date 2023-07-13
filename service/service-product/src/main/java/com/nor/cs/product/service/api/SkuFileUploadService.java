package com.nor.cs.product.service.api;

import org.springframework.web.multipart.MultipartFile;

public interface SkuFileUploadService {
    String uploadFile(MultipartFile file);
}
