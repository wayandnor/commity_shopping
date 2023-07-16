package com.nor.cs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.model.product.SkuAttrValue;
import com.nor.cs.model.product.SkuImage;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.product.SkuPoster;
import com.nor.cs.model.vo.product.SkuInfoQueryVo;
import com.nor.cs.model.vo.product.SkuInfoVo;
import com.nor.cs.mq.constant.MqConst;
import com.nor.cs.mq.service.RabbitService;
import com.nor.cs.product.consts.SkuCheckStatusConst;
import com.nor.cs.product.consts.SkuPublishStatusConst;
import com.nor.cs.product.mapper.SkuInfoMapper;
import com.nor.cs.product.service.api.SkuAttrValueService;
import com.nor.cs.product.service.api.SkuImageService;
import com.nor.cs.product.service.api.SkuInfoService;
import com.nor.cs.product.service.api.SkuPosterService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {
    @Resource
    private SkuImageService skuImageService;
    @Resource
    private SkuPosterService skuPosterService;
    @Resource
    private SkuAttrValueService skuAttrValueService;
    @Resource
    private RabbitService rabbitService;

    @Override
    public IPage<SkuInfo> listByPageAndFilter(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        Long categoryId = skuInfoQueryVo.getCategoryId();
        String skuType = skuInfoQueryVo.getSkuType();
        String spuName = skuInfoQueryVo.getKeyword();
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        if (skuType != null) {
            wrapper.eq(SkuInfo::getSkuType, skuType);
        }
        if (spuName != null) {
            wrapper.like(SkuInfo::getSkuName, spuName);
        }
        Page<SkuInfo> skuInfoPage = baseMapper.selectPage(pageParam, wrapper);
        return skuInfoPage;
    }

    @Override
    @Transactional
    public void saveDetailSkuInfo(SkuInfoVo skuInfoVo) {
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.insert(skuInfo);

        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuInfo.getId());
            }
            skuImageService.saveBatch(skuImagesList);
        }

        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }

    }

    @Override
    public SkuInfoVo getSkuInfoVoById(Long id) {
        SkuInfo skuInfo = baseMapper.selectById(id);
        List<SkuImage> skuImageList = skuImageService.getImageListById(id);
        List<SkuPoster> skuPosterList = skuPosterService.getPosterListById(id);
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.getAttrValueById(id);
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);
        return skuInfoVo;
    }

    @Override
    @Transactional
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.updateById(skuInfo);

        Long id = skuInfoVo.getId();

        skuAttrValueService.remove(new LambdaQueryWrapper<SkuAttrValue>().eq(SkuAttrValue::getSkuId, id));
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(id);
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }

        skuImageService.remove(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, id));
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(id);
            }
            skuImageService.saveBatch(skuImagesList);
        }

        skuPosterService.remove(new LambdaQueryWrapper<SkuPoster>().eq(SkuPoster::getSkuId, id));
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(id);
            }
            skuPosterService.saveBatch(skuPosterList);
        }
    }


    @Override
    public boolean updateSkuPublishStatus(Long id, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(id);
        if (status.equals(SkuPublishStatusConst.ON_SELL.getStatus())) {
            if (skuInfo.getPublishStatus().equals(SkuPublishStatusConst.UNSHELVED.getStatus())) {
                skuInfo.setPublishStatus(SkuPublishStatusConst.ON_SELL.getStatus());
                baseMapper.updateById(skuInfo);
                rabbitService.sendMessage(MqConst.GOODS_EXCHANGE_DIRECT,MqConst.GOODS_ROUTING_PUT_ON_SALE, id);
                return true;
            }
        } else if (status.equals(SkuPublishStatusConst.UNSHELVED.getStatus())) {
            if (skuInfo.getPublishStatus().equals(SkuPublishStatusConst.ON_SELL.getStatus())) {
                skuInfo.setPublishStatus(SkuPublishStatusConst.UNSHELVED.getStatus());
                baseMapper.updateById(skuInfo);
                rabbitService.sendMessage(MqConst.GOODS_EXCHANGE_DIRECT,MqConst.GOODS_ROUTING_UNSHELVE,id);
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateCheckSkuStatus(Long id, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(id);
        skuInfo.setCheckStatus(SkuCheckStatusConst.CHECKED.getCheckStatus());
        baseMapper.updateById(skuInfo);
    }

    @Override
    public void updateSkuNewExclusive(Long id, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(id);
        skuInfo.setIsNewPerson(status);
        baseMapper.updateById(skuInfo);
    }

    @Override
    public List<SkuInfo> querySkuInfoByKeyword(String keyword) {
        List<SkuInfo> skuInfos = baseMapper.selectList(
                new LambdaQueryWrapper<SkuInfo>().like(SkuInfo::getSkuName, keyword)
        );
        return skuInfos;
    }
}
