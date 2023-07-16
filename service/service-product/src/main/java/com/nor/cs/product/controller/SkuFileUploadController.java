package com.nor.cs.product.controller;

import com.nor.cs.common.result.Result;
import com.nor.cs.product.service.api.SkuFileUploadService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
//@CrossOrigin
@RequestMapping("admin/product")
public class SkuFileUploadController {
    @Resource
    private SkuFileUploadService fileUploadService;
    
    @PostMapping("fileUpload")
    public Result uploadFile(MultipartFile file) {
        String resultUrl = fileUploadService.uploadFile(file);
        return Result.successWithData(resultUrl);
    }
}
