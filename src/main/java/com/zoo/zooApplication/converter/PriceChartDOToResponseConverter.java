package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.PriceChartDO;
import com.zoo.zooApplication.response.PriceChart;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
public class PriceChartDOToResponseConverter {
	public PriceChart convert(@NotNull final PriceChartDO priceChartDO) {
		Objects.requireNonNull(priceChartDO);

		return PriceChart
			.builder()
			.priceAmount(priceChartDO.getPriceAmount())
			.currencyId(priceChartDO.getCurrencyId())
			.timeStart(priceChartDO.getTimeStart())
			.timeEnd(priceChartDO.getTimeEnd())
			.build();
	}
}
