package com.li.delivery.order.controller;


import java.util.Date;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.li.delivery.common.contants.ResultCode;
import com.li.delivery.common.exception.RuntimeExceptionBase;
import com.li.delivery.common.model.ResponseModel;
import com.li.delivery.order.model.OrderListRequestDTO;
import com.li.delivery.order.model.OrderModel;
import com.li.delivery.order.model.OrderUpdateAddressModel;
import com.li.delivery.order.service.OrderService;


@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	
	@GetMapping
    public ResponseModel<List<OrderModel>> getUserOrders(@Validated OrderListRequestDTO orderListRequestDTO){
		
		if (orderListRequestDTO.getEndDate() == null || orderListRequestDTO.getStartDate() == null) {
			throw new RuntimeExceptionBase(ResultCode.BAD_REQUEST, "질못된 조회 날짜 입니다.");
		}
		
		if (orderListRequestDTO.getEndDate().before(orderListRequestDTO.getStartDate())) {
			throw new RuntimeExceptionBase(ResultCode.BAD_REQUEST, "질못된 조회 날짜 입니다.");
		}
		int dateRange = differentDays(orderListRequestDTO.getStartDate(), orderListRequestDTO.getEndDate());
		
		if (dateRange >= 2) {
			throw new RuntimeExceptionBase(ResultCode.BAD_REQUEST, "질못된 조회 날짜 입니다.");
		}
		List<OrderModel> userOrders = orderService.getUserOrders(orderListRequestDTO);
		for (OrderModel order : userOrders) {
			order.getUser().setPassword("");
		}
		return new ResponseModel<>(ResultCode.OK, userOrders);
    }
	
	@PostMapping("/{orderId}")
    public ResponseModel<OrderModel> updateUserOrderAddress(@PathVariable("orderId") String orderId,@Validated @RequestBody OrderUpdateAddressModel addressModel){
		OrderModel order = orderService.updateOrderAddress(orderId, addressModel);
		return new ResponseModel<>(ResultCode.OK, order);
    }
	
	
	public static int differentDays(Date date1,Date date2){
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
		return days;
	}
	
}
