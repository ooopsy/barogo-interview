package com.li.delivery.order.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.li.delivery.common.contants.ResultCode;
import com.li.delivery.common.exception.RuntimeExceptionBase;
import com.li.delivery.order.contants.OrderState;
import com.li.delivery.order.model.OrderListRequestDTO;
import com.li.delivery.order.model.OrderModel;
import com.li.delivery.order.model.OrderUpdateAddressModel;
import com.li.delivery.order.repository.OrderRepository;
import com.li.delivery.user.model.UserDetailModel;

@Service
public class OrderService {

	private OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public void setOrderRepository(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public void createOrder(OrderModel orderModel) {
		orderRepository.save(orderModel);
	}
	
	public List<OrderModel> getUserOrders(OrderListRequestDTO orderModel) {
		UserDetailModel user = (UserDetailModel)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		orderModel.setUserId(user.getId());
		return orderRepository.findOrderByUserId(orderModel);
	}

	public OrderModel updateOrderAddress(String orderId, OrderUpdateAddressModel addressModel) {
		Optional<OrderModel> orderOption = orderRepository.findById(orderId);
		if (orderOption.isEmpty()) {
			throw new RuntimeExceptionBase(ResultCode.NOT_FOUND, "잘못된 order id");
		}
		
		OrderModel orderModel = orderOption.get();
		
		if (Objects.equals(addressModel.getAddress(), orderModel.getAddress())) {
			throw new RuntimeExceptionBase(ResultCode.DUPLICATE, "동일한 주소로 수정할 수가 없습니다.");
		}
		
		if (orderModel.getOrderState().getStateNumber() > OrderState.PICKING.getStateNumber()) {
			throw new RuntimeExceptionBase(ResultCode.NOT_ALLOW, orderModel.getOrderState() + " 주소를 수정할 수가 없습니다.");
		}
		
		UserDetailModel user = (UserDetailModel)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!Objects.equals(user.getId(), orderModel.getUser().getId())) {
			throw new RuntimeExceptionBase(ResultCode.ACCESS_DENY, "자신의 order 만 수정가능 합니다.");
		}

		
		orderModel.setAddress(addressModel.getAddress());
		orderRepository.save(orderModel);
		return orderModel;
	}
	
}
