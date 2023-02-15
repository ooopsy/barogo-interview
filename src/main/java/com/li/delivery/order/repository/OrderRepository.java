package com.li.delivery.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.li.delivery.order.model.OrderListRequestDTO;
import com.li.delivery.order.model.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel, String> {
	@Query(value="SELECT x FROM OrderModel x WHERE x.user.id = :#{#requestDTO.userId} AND DATE_FORMAT(x.createDate,'%Y-%m-%d') >= DATE_FORMAT(:#{#requestDTO.startDate},'%Y-%m-%d') AND DATE_FORMAT(x.createDate,'%Y-%m-%d') <= DATE_FORMAT(:#{#requestDTO.endDate},'%Y-%m-%d') order by x.createDate desc")
    List<OrderModel> findOrderByUserId (@Param("requestDTO") OrderListRequestDTO requestDTO);
}
