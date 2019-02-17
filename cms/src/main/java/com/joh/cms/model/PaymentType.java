package com.joh.cms.model;

import java.util.stream.Stream;

public enum PaymentType {
	CASH, DEBT, SEMIDEBT;

	public static String[] paymentTypes() {
		return Stream.of(PaymentType.values()).map(PaymentType::toString).toArray(String[]::new);
	}
}
