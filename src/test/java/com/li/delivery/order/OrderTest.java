package com.li.delivery.order;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.li.delivery.common.contants.ResultCode;
import com.li.delivery.common.exception.RuntimeExceptionBase;
import com.li.delivery.order.contants.OrderState;
import com.li.delivery.order.controller.OrderController;
import com.li.delivery.order.model.OrderListRequestDTO;
import com.li.delivery.order.model.OrderModel;
import com.li.delivery.order.model.OrderUpdateAddressModel;
import com.li.delivery.order.repository.OrderRepository;
import com.li.delivery.order.service.OrderService;
import com.li.delivery.user.model.UserDetailModel;
import com.li.delivery.user.model.UserModel;
import com.li.delivery.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class OrderTest {

	@Autowired
	private OrderService orderService;
	
	@Autowired 
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderController orderController;
	
	@Autowired
	private UserRepository userRepository;
	
	private String userId = "0c621199-e58c-4c80-91c2-ea0944edd9ba";
	@BeforeAll
	public void ensureUser() {
		UserModel userModel= userRepository.findUserByUserName("oopsy");
		
		if (userModel == null) {
			UserModel newUser = makeUser();
			userRepository.save(newUser);
		}
		userModel= userRepository.findUserByUserName("oopsy");
		this.userId = userModel.getId();
	}
	
	@Test
    public void createOrder() {
		orderService.setOrderRepository(this.orderRepository);
		OrderListRequestDTO requestDTO = new OrderListRequestDTO();
		requestDTO.setUserId(userId);
		requestDTO.setEndDate(new Date());
		requestDTO.setStartDate(new Date());
		List<OrderModel> beforeInsert = orderRepository.findOrderByUserId(requestDTO);
		UserModel user = new UserModel();
		user.setId(userId);
		OrderModel orderModel= new OrderModel();
		orderModel.setUser(user);
		orderModel.setAddress("구로동");
		orderModel.setOrderState(OrderState.ACCEPTD);
		orderService.createOrder(orderModel);
		List<OrderModel> afterInsert = orderRepository.findOrderByUserId(requestDTO);
		assertEquals(beforeInsert.size() +1, afterInsert.size());
    }
	
	@Test
    public void getOrdersMoreThanThreeDays() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -4);
		
		Date startDate = calendar.getTime();
		Date endDate = new Date();
		
		
		OrderListRequestDTO requestDTO = new OrderListRequestDTO();
		requestDTO.setUserId(userId);
		requestDTO.setStartDate(startDate);
		requestDTO.setEndDate(endDate);
		
		RuntimeExceptionBase re = Assertions.assertThrows(RuntimeExceptionBase.class, () -> {
			orderController.getUserOrders(requestDTO);
	    });
		assertEquals(ResultCode.BAD_REQUEST, re.getErrorCode());
    }
	
	@Test
    public void getOrdersStartBigerThanEnd() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		
		Date startDate = new Date();
		Date endDate = calendar.getTime();
		
		
		OrderListRequestDTO requestDTO = new OrderListRequestDTO();
		requestDTO.setUserId(userId);
		requestDTO.setStartDate(startDate);
		requestDTO.setEndDate(endDate);
		
		RuntimeExceptionBase re = Assertions.assertThrows(RuntimeExceptionBase.class, () -> {
			orderController.getUserOrders(requestDTO);
	    });
		assertEquals(ResultCode.BAD_REQUEST, re.getErrorCode());
    }
	
	
	@Test
    public void updateNotExistingOrder() {
		
		
		String orderId = "dummy";
		String address = "dummy";
		
		OrderUpdateAddressModel orderUpdateAddressModel = new OrderUpdateAddressModel();
		orderUpdateAddressModel.setAddress(address);
		
		RuntimeExceptionBase re = Assertions.assertThrows(RuntimeExceptionBase.class, () -> {
			orderService.updateOrderAddress(orderId, orderUpdateAddressModel);
	    });
		assertEquals(ResultCode.NOT_FOUND, re.getErrorCode());
    }
	
	@Test
    public void updateIllegalStateOrder() {
		
		OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
		
		OrderModel deliveringOrder = new OrderModel();
		deliveringOrder.setOrderState(OrderState.DELIVERING);
		Optional<OrderModel> option = Optional.of(deliveringOrder);
		when(orderRepository.findById("dummy")).thenReturn(option);
		orderService.setOrderRepository(orderRepository);
		
		String orderId = "dummy";
		String address = "dummy";
		OrderUpdateAddressModel orderUpdateAddressModel = new OrderUpdateAddressModel();
		orderUpdateAddressModel.setAddress(address);
		RuntimeExceptionBase re = Assertions.assertThrows(RuntimeExceptionBase.class, () -> {
			orderService.updateOrderAddress(orderId, orderUpdateAddressModel);
	    });
		assertEquals(ResultCode.NOT_ALLOW, re.getErrorCode());
    }
	
	@Test
    public void updateOrderWithSameAddress() {
		
		OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
		
		OrderModel deliveringOrder = new OrderModel();
		deliveringOrder.setOrderState(OrderState.ACCEPTD);
		deliveringOrder.setAddress("구로동");
		Optional<OrderModel> option = Optional.of(deliveringOrder);
		when(orderRepository.findById("dummy")).thenReturn(option);
		orderService.setOrderRepository(orderRepository);
		
		String orderId = "dummy";
		String address = "구로동";
		OrderUpdateAddressModel orderUpdateAddressModel = new OrderUpdateAddressModel();
		orderUpdateAddressModel.setAddress(address);
		RuntimeExceptionBase re = Assertions.assertThrows(RuntimeExceptionBase.class, () -> {
			orderService.updateOrderAddress(orderId, orderUpdateAddressModel);
	    });
		assertEquals(ResultCode.DUPLICATE, re.getErrorCode());
    }
	
	@Test
    public void updateOthersOrder() {
		
		OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
		OrderModel order = new OrderModel();
		order.setOrderState(OrderState.ACCEPTD);
		UserModel userModel = new UserModel();
		userModel.setId("user1");
		order.setUser(userModel);
		Optional<OrderModel> option = Optional.of(order);
		when(orderRepository.findById("dummy")).thenReturn(option);
		
		setNotDummyLoginUser();
		orderService.setOrderRepository(orderRepository);
		String orderId = "dummy";
		String address = "dummy";
		OrderUpdateAddressModel orderUpdateAddressModel = new OrderUpdateAddressModel();
		orderUpdateAddressModel.setAddress(address);
		RuntimeExceptionBase re = Assertions.assertThrows(RuntimeExceptionBase.class, () -> {
			orderService.updateOrderAddress(orderId, orderUpdateAddressModel);
	    });
		assertEquals(ResultCode.ACCESS_DENY, re.getErrorCode());
    }

	private void setNotDummyLoginUser() {
		List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ROLE"));
		UserDetailModel userDetails = new UserDetailModel("user2", "", authorityList, "user2");
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
	}
	
	
	
	private UserModel makeUser() {
		UserModel newUser = new UserModel();
		newUser.setId(userId);
		newUser.setUserName("oopsy");
		newUser.setName("이성");
		newUser.setPassword("Aaaaaaaaaaa1@#");
		return newUser;
	}
	
}
