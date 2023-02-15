package com.li.delivery.order.contants;

public enum OrderState {
	
	CREATED(1), ACCEPTD(2), ARRANGED(3), COOKING(4), PICKING(5), DELIVERING(6), DONE(7);
	
	private int stateNumber;
	
	private OrderState(int  stateNumber) {
        this.stateNumber = stateNumber;
    }
	public int getStateNumber() {
		return stateNumber;
	}

	public void setStateNumber(int stateNumber) {
		this.stateNumber = stateNumber;
	}
	
}
