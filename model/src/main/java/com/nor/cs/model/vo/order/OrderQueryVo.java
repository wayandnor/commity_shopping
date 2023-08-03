package com.nor.cs.model.vo.order;

import com.nor.cs.enums.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderQueryVo {
	

	@ApiModelProperty(value = "订单号")
	private String orderNo;

	@ApiModelProperty(value = "收货人信息")
	private String receiver;

	@ApiModelProperty(value = "订单状态")
	private OrderStatus orderStatus;

	@ApiModelProperty(value = "团长id")
	private Long leaderId;

	@ApiModelProperty(value = "仓库id")
	private Long wareId;

	@ApiModelProperty(value = "创建时间")
	private String createTimeBegin;
	private String createTimeEnd;

}

