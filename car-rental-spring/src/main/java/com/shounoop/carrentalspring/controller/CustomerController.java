package com.shounoop.carrentalspring.controller;

import com.shounoop.carrentalspring.dto.BookACarDto;
import com.shounoop.carrentalspring.dto.CarDto;
import com.shounoop.carrentalspring.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(customerService.getAllCars());
    }

    @PostMapping("/car/book")
    public ResponseEntity<Void> bookACar(@RequestBody BookACarDto bookACarDto) {
        boolean isSuccessful = customerService.bookACar(bookACarDto);

        if (isSuccessful) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long carId) {
        CarDto carDto = customerService.getCarById(carId);

        if (carDto != null) {
            return ResponseEntity.ok(carDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/car/bookings/{userId}")
    public ResponseEntity<List<BookACarDto>> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
    }

    // New Endpoint to Update Booking Status
    @PutMapping("/car/bookings/{bookingId}/status")
    public ResponseEntity<String> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestBody String status
    ) {
        boolean isUpdated = customerService.updateBookingStatus(bookingId, status);

        if (isUpdated) {
            return ResponseEntity.ok("Booking status updated successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid booking ID or status");
    }

    @DeleteMapping("/car/bookings/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
        return customerService.deleteBooking(bookingId)
                ? ResponseEntity.ok("Booking deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
    }
}
