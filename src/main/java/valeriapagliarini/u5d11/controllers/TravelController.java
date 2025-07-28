package valeriapagliarini.u5d11.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import valeriapagliarini.u5d11.entities.Travel;
import valeriapagliarini.u5d11.exceptions.ValidationException;
import valeriapagliarini.u5d11.payloads.TravelDTO;
import valeriapagliarini.u5d11.payloads.UpdateTravelStatusDTO;
import valeriapagliarini.u5d11.services.TravelService;

import java.util.List;

@RestController
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    private TravelService travelService;

    // GET ALL
    @GetMapping
    public List<Travel> findAll() {
        return travelService.findAll();
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Travel createTravel(@RequestBody @Validated TravelDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return travelService.save(payload);
    }

    // GET BY ID
    @GetMapping("/{travelId}")
    public Travel findById(@PathVariable Long travelId) {
        return travelService.findById(travelId);
    }

    // PUT
    @PutMapping("/{travelId}")
    public Travel updateTravel(@PathVariable Long travelId, @RequestBody @Validated TravelDTO payload,
                               BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return travelService.findByIdAndUpdate(travelId, payload);
    }

    // DELETE
    @DeleteMapping("/{travelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTravel(@PathVariable Long travelId) {
        travelService.findByIdAndDelete(travelId);
    }

    //PATCH
    @PatchMapping("/{travelId}/status")
    public Travel updateStatus(@PathVariable Long travelId, @RequestBody @Valid UpdateTravelStatusDTO payload,
                               BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return travelService.updateStatus(travelId, payload.status());
    }

}
