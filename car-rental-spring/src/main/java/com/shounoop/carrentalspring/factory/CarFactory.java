package com.shounoop.carrentalspring.factory;

import com.shounoop.carrentalspring.dto.CarDto;
import com.shounoop.carrentalspring.entity.Car;

import java.io.IOException;
//Factory Pattern to encapsulate the object creation logic for Car.
public class CarFactory {
    public static Car createCarFromDto(CarDto carDto) throws IOException {
        Car car = new Car();
        car.setName(carDto.getName());
        car.setBrand(carDto.getBrand());
        car.setColor(carDto.getColor());
        car.setDescription(carDto.getDescription());
        car.setPrice(carDto.getPrice());
        car.setTransmission(carDto.getTransmission());
        car.setType(carDto.getType());
        car.setYear(carDto.getYear());
        car.setImage(carDto.getImage().getBytes());
        return car;
    }
}
