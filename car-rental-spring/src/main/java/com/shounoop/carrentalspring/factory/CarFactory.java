package com.shounoop.carrentalspring.factory;

import com.shounoop.carrentalspring.dto.CarDto;
import com.shounoop.carrentalspring.entity.Car;

import java.io.IOException;
//Factory Pattern to encapsulate the object creation logic for Car.
//The CarFactory class made a singleton to ensure only one instance is ever created.
public class CarFactory {
    private static CarFactory instance;

    private CarFactory() {
        // Private constructor to prevent instantiation
    }

    public static CarFactory getInstance() {
        if (instance == null) {
            synchronized (CarFactory.class) {
                if (instance == null) {
                    instance = new CarFactory();
                }
            }
        }
        return instance;
    }

    public Car createCarFromDto(CarDto carDto) throws IOException {
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

