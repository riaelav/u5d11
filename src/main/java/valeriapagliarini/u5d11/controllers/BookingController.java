package valeriapagliarini.u5d11.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import valeriapagliarini.u5d11.entities.Booking;
import valeriapagliarini.u5d11.exceptions.ValidationException;
import valeriapagliarini.u5d11.payloads.BookingDTO;
import valeriapagliarini.u5d11.services.BookingService;
import valeriapagliarini.u5d11.services.EmployeeService;
import valeriapagliarini.u5d11.services.TravelService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    BookingService bookingService;
    @Autowired
    TravelService travelService;


    // GET ALL
    @GetMapping
    public List<Booking> findAll() {
        return bookingService.findAll();
    }


    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody @Valid BookingDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return bookingService.save(payload);
    }

    // GET BY ID
    @GetMapping("/{bookingId}")
    public Booking findById(@PathVariable Long bookingId) {
        return bookingService.findById(bookingId);
    }

    // DELETE
    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long bookingId) {
        bookingService.findByIdAndDelete(bookingId);
    }


}
