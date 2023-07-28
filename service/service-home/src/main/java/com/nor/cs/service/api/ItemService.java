package com.nor.cs.service.api;

import java.util.Map;

public interface ItemService {
    Map<String, Object> getProductDeatail(Long itemId, Long userId);
}
