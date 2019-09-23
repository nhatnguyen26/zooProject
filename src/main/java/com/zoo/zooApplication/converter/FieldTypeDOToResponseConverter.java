package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.FieldTypeDO;
import com.zoo.zooApplication.response.FieldResponse;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.response.FieldTypeResponse;
import com.zoo.zooApplication.response.PriceChart;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FieldTypeDOToResponseConverter {

	private PriceChartDOToResponseConverter priceChartDOToResponseConverter;

	@Inject
	public FieldTypeDOToResponseConverter(PriceChartDOToResponseConverter priceChartDOToResponseConverter) {
		this.priceChartDOToResponseConverter = priceChartDOToResponseConverter;
	}

	public FieldType convert(@NotNull final FieldTypeDO fieldTypeDO) {
		Objects.requireNonNull(fieldTypeDO);
		return FieldType
			.builder()
			.id(fieldTypeDO.getId())
			.name(fieldTypeDO.getName())
			.priceCharts(convertPriceCharts(fieldTypeDO))
			.build();
	}

	private List<PriceChart> convertPriceCharts(FieldTypeDO fieldTypeDO) {
		if (CollectionUtils.isNotEmpty(fieldTypeDO.getPriceCharts())) {
			return fieldTypeDO
				.getPriceCharts()
				.stream()
				.map(priceChartDOToResponseConverter::convert)
				.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	public FieldTypeResponse convert(@NotNull final List<FieldTypeDO> fieldTypeDOList) {
		Objects.requireNonNull(fieldTypeDOList);

		return FieldTypeResponse
			.builder()
			.fieldTypes(convertFieldTypes(fieldTypeDOList))
			.build();
	}

	private List<FieldType> convertFieldTypes(List<FieldTypeDO> fieldTypeDOList) {
		return fieldTypeDOList
			.stream()
			.map(this::convert)
			.collect(Collectors.toList());
	}
}
