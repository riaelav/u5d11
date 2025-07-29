package valeriapagliarini.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    //ottenere tutti gli impiegati solo gli admin
    //GET
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Employee> findAll() {
        return this.employeeService.findAll();
    }

    //creare account solo per gli admin
    //POST
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee findById(@PathVariable Long employeeId) {
        return employeeService.findById(employeeId);
    }

    //PUT
    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
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

    //cancellare solo gli admin

    //DELETE
    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
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

    //    ME ENDPOINTS

    @GetMapping("/me")
    public Employee getMyProfile(@AuthenticationPrincipal Employee authenticatedEmployee) {
        return authenticatedEmployee;
    }

    @PutMapping("/me")
    public Employee updateMyProfile(@AuthenticationPrincipal Employee authenticatedEmployee,
                                    @RequestBody @Validated EmployeeDTO payload) {
        return this.employeeService.findByIdAndUpdate(authenticatedEmployee.getId(), payload);
    }


    @DeleteMapping("/me")
    public void deleteMyProfile(@AuthenticationPrincipal Employee authenticatedEmployee) {
        this.employeeService.findByIdAndDelete(authenticatedEmployee.getId());
    }

}
