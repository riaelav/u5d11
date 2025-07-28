package valeriapagliarini.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import valeriapagliarini.u5d11.entities.Booking;
import valeriapagliarini.u5d11.entities.Employee;
import valeriapagliarini.u5d11.entities.Travel;
import valeriapagliarini.u5d11.exceptions.BadRequestException;
import valeriapagliarini.u5d11.exceptions.NotFoundException;
import valeriapagliarini.u5d11.payloads.BookingDTO;
import valeriapagliarini.u5d11.repositories.BookingRepository;
import valeriapagliarini.u5d11.repositories.EmployeeRepository;
import valeriapagliarini.u5d11.repositories.TravelRepository;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TravelRepository travelRepository;

    public Booking save(BookingDTO payload) {
        if (bookingRepository.existsByEmployeeIdAndRequestDate(payload.employeeId(), payload.requestDate())) {
            throw new BadRequestException("Employee already has a booking on " + payload.requestDate());
        }

        Employee employee = employeeRepository.findById(payload.employeeId())
                .orElseThrow(() -> new NotFoundException(payload.employeeId()));

        Travel travel = travelRepository.findById(payload.travelId())
                .orElseThrow(() -> new NotFoundException(payload.travelId()));

        Booking booking = new Booking(payload.requestDate(), employee, travel, payload.notes());
        return bookingRepository.save(booking);
    }


    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(bookingId));
    }

    public void findByIdAndDelete(Long bookingId) {
        Booking found = this.findById(bookingId);
        this.bookingRepository.delete(found);
    }
}
