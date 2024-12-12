//package com.shounoop.carrentalspring.entity;
//
//import com.shounoop.carrentalspring.dto.CarDto;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.Date;
//
//@Entity
//@Data
//@Table(name = "cars")
//public class Car {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String brand;
//    private String color;
//    private String name;
//    private String type;
//    private String transmission;
//    private String description;
//    private Long price;
//    private Integer year;
//
//    @Column(columnDefinition = "longblob")
//    private byte[] image;
//
//    public CarDto getCarDto() {
//        CarDto carDto = new CarDto();
//        carDto.setId(id);
//        carDto.setName(name);
//        carDto.setBrand(brand);
//        carDto.setColor(color);
//        carDto.setPrice(price);
//        carDto.setDescription(description);
//        carDto.setType(type);
//        carDto.setTransmission(transmission);
//        carDto.setYear(year);
//        carDto.setReturnedImage(image);
//        return carDto;
//    }
//}

package com.shounoop.carrentalspring.entity;

import com.shounoop.carrentalspring.dto.CarDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String color;
    private String name;
    private String type;
    private String transmission;
    private String description;
    private Long price;
    private Integer year;

    @Column(columnDefinition = "longblob")
    private byte[] image;

    // Factory Method Pattern
    public static Car createCar(String type, CarDto carDto) {
        Car car = new Car();
        // Common attributes for all cars
        car.setName(carDto.getName());
        car.setBrand(carDto.getBrand());
        car.setColor(carDto.getColor());
        car.setTransmission(carDto.getTransmission());
        car.setYear(carDto.getYear());
        car.setDescription(carDto.getDescription());
        car.setType(type);

        // Specific configurations based on type
        switch (type.toLowerCase()) {
            case "luxury" -> {
                car.setPrice(carDto.getPrice() * 2); // Premium pricing for luxury
            }
            case "sport" -> {
                car.setPrice(carDto.getPrice() * 3); // Higher pricing for sports cars
            }
            default -> {
                car.setPrice(carDto.getPrice()); // Standard pricing
            }
        }

        return car;
    }

    // Existing methods
    public CarDto getCarDto() {
        CarDto carDto = new CarDto();
        carDto.setId(id);
        carDto.setName(name);
        carDto.setBrand(brand);
        carDto.setColor(color);
        carDto.setPrice(price);
        carDto.setDescription(description);
        carDto.setType(type);
        carDto.setTransmission(transmission);
        carDto.setYear(year);
        carDto.setReturnedImage(image);
        return carDto;
    }

    // Builder Pattern (keeping existing implementation)
    public static class CarBuilder {
        private Car car = new Car();

        public CarBuilder withBrand(String brand) {
            car.setBrand(brand);
            return this;
        }

        public CarBuilder withName(String name) {
            car.setName(name);
            return this;
        }

        public CarBuilder withColor(String color) {
            car.setColor(color);
            return this;
        }

        public CarBuilder withType(String type) {
            car.setType(type);
            return this;
        }

        public CarBuilder withTransmission(String transmission) {
            car.setTransmission(transmission);
            return this;
        }

        public CarBuilder withDescription(String description) {
            car.setDescription(description);
            return this;
        }

        public CarBuilder withPrice(Long price) {
            car.setPrice(price);
            return this;
        }

        public CarBuilder withYear(Integer year) {
            car.setYear(year);
            return this;
        }

        public CarBuilder withImage(byte[] image) {
            car.setImage(image);
            return this;
        }

        public Car build() {
            return car;
        }
    }
}
