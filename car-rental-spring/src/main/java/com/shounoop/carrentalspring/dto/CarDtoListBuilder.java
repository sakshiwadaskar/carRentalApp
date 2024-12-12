package com.shounoop.carrentalspring.dto;

import java.util.List;

public class CarDtoListBuilder {
    private final CarDtoListDto carDtoListDto;

    public CarDtoListBuilder() {
        this.carDtoListDto = new CarDtoListDto();
    }

    public CarDtoListBuilder addCarDtos(List<CarDto> carDtos) {
        this.carDtoListDto.setCarDtoList(carDtos);
        return this;
    }

    public CarDtoListDto build() {
        return this.carDtoListDto;
    }
}