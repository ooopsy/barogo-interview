package com.li.delivery.order;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import com.li.delivery.order.model.OrderListRequestDTO;
@SpringBootTest
class DateRuleTests {

	@Autowired
    private Validator validator;
	
	@Test
    void testTomorow() {
    	Calendar cal=Calendar.getInstance();
	    cal.add(Calendar.DATE,1);
	    OrderListRequestDTO requestDTO = new OrderListRequestDTO();
    	requestDTO.setEndDate(cal.getTime());
    	BindException errors = new BindException(requestDTO, "order");
        validator.validate(requestDTO, errors);
        assertTrue(errors.hasErrors(), "has error");
    }
	
	
    @Test
    void testToday() {
    	Date today = new Date();
    	OrderListRequestDTO requestDTO = new OrderListRequestDTO();
    	requestDTO.setEndDate(today);
    	requestDTO.setStartDate(today);
    	
    	BindException errors = new BindException(requestDTO, "order");
        validator.validate(requestDTO, errors);
        assertFalse(errors.hasErrors());
        getThreeDaysBefore();
    }
    
    
    private Date getThreeDaysBefore() {
		Calendar cal=Calendar.getInstance();
	    cal.add(Calendar.DATE,-2);
	    Date date  = cal.getTime();
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    
	    try {
	    	date = format.parse(format.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

}
