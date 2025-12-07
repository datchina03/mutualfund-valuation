package com.example.cams.mf.model;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="Buy/Redeem Order Request")
public record OrderRequest(
		@Schema(name="order type",example="buy/redeem")
		String orderType,
		@Schema(name="user id shown in the display")
		long userId,
		@Schema(name="Fund id")
		long fundId,
		@Schema(name="buy/redeem order mutual fund units",example="10.00")
		BigDecimal orderUnits,
		@Schema(name="nav from the user",example="10.00")
		BigDecimal userNav) {

}
