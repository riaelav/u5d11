package valeriapagliarini.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import valeriapagliarini.u5d11.entities.Employee;
import valeriapagliarini.u5d11.exceptions.ValidationException;
import valeriapagliarini.u5d11.payloads.EmployeeDTO;
import valeriapagliarini.u5d11.payloads.NewEmployeeRespDTO;
import valeriapagliarini.u5d11.services.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //GET
    @GetMapping
    public List<Employee> findAll() {
        return this.employeeService.findAll();
    }

    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeeRespDTO createEmployee(@RequestBody @Validated EmployeeDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            //creo lista di errori
            List<String> errors = validationResult.getFieldErrors().stream()
                    //mappo la lista
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        Employee newEmployee = this.employeeService.save(payload);
        return new NewEmployeeRespDTO(newEmployee.getId());
    }

    //GET PER ID
    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable Long employeeId) {
        return employeeService.findById(employeeId);
    }

    //PUT
    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable Long employeeId, @RequestBody @Validated EmployeeDTO payload,
                                   BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return this.employeeService.findByIdAndUpdate(employeeId, payload);
    }

    //DELETE
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long employeeId) {
        employeeService.findByIdAndDelete(employeeId);
    }

    //PATCH UPLOAD IMG
    @PatchMapping("/{employeeId}/avatar")
    public String uploadImage(@PathVariable Long employeeId,
                              @RequestParam("avatar") MultipartFile file) {
        return this.employeeService.uploadAvatar(employeeId, file);
    }


}
