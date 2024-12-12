package com.shounoop.carrentalspring.services.admin;

import com.shounoop.carrentalspring.dto.*;
import com.shounoop.carrentalspring.entity.BookACar;
import com.shounoop.carrentalspring.entity.Car;
import com.shounoop.carrentalspring.enums.BookCarStatus;
import com.shounoop.carrentalspring.repository.BookACarRepository;
import com.shounoop.carrentalspring.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import com.shounoop.carrentalspring.factory.CarFactory;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CarRepository carRepository;
    private final BookACarRepository bookACarRepository;

    @Override
    public boolean postCar(CarDto carDto) throws IOException {
        try {
            Car car = CarFactory.getInstance().createCarFromDto(carDto);
            carRepository.save(car);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarDto getCarById(Long id) {
        return carRepository.findById(id).map(Car::getCarDto).orElse(null); // map() is a method that applies a given function to each element of a stream
    }

    private boolean validateCarDto(CarDto carDto) {
        return carDto.getName() != null &&
                carDto.getBrand() != null &&
                carDto.getPrice() != null;
    }


    @Override
    public boolean updateCar(Long id, CarDto carDto) throws IOException {

        if (!validateCarDto(carDto)) {
            return false; // Validation failed
        }

        Optional<Car> optionalCar = carRepository.findById(id);

        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();

            Car updatedCar = CarFactory.getInstance().createCarFromDto(carDto);
            updatedCar.setId(existingCar.getId()); // Preserve the ID

            // Retain existing fields if not provided in CarDto
            if (existingCar.getImage() != null && carDto.getImage() == null) {
                updatedCar.setImage(existingCar.getImage());
            }

            carRepository.save(updatedCar);
            return true;
        }

        return false;
    }

    @Override
    public List<BookACarDto> getBookings() {
        return bookACarRepository.findAll().stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
    }

    @Override
    public boolean changeBookingStatus(Long id, String status) {
        Optional<BookACar> optionalBookACar = bookACarRepository.findById(id);

        if (optionalBookACar.isPresent()) {
            BookACar bookACar = optionalBookACar.get();

            if (Objects.equals(status, "Approve")) {
                bookACar.setBookCarStatus(BookCarStatus.APPROVED);
            } else {
                bookACar.setBookCarStatus(BookCarStatus.REJECTED);
            }

            bookACarRepository.save(bookACar);

            return true;
        }

        return false;
    }

    @Override
    public CarDtoListDto searchCar(SearchCarDto searchCarDto) {
        Car car = new Car();
        car.setBrand(searchCarDto.getBrand());
        car.setType(searchCarDto.getType());
        car.setTransmission(searchCarDto.getTransmission());
        car.setColor(searchCarDto.getColor());

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Car> carExample = Example.of(car, exampleMatcher);

        List<CarDto> carDtos = carRepository.findAll(carExample)
                .stream()
                .map(Car::getCarDto)
                .collect(Collectors.toList());

        return new CarDtoListBuilder().addCarDtos(carDtos).build();
    }
}
