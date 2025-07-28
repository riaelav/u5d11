package valeriapagliarini.u5d11.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import valeriapagliarini.u5d11.entities.Employee;
import valeriapagliarini.u5d11.exceptions.BadRequestException;
import valeriapagliarini.u5d11.exceptions.NotFoundException;
import valeriapagliarini.u5d11.payloads.EmployeeDTO;
import valeriapagliarini.u5d11.repositories.EmployeeRepository;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private Cloudinary imgUploader;

    public Employee save(EmployeeDTO payload) {
        this.employeeRepository.findByEmail(payload.email())
                .ifPresent(employee -> {
                    throw new BadRequestException("Email" + employee.getEmail() + "already used");
                });

        employeeRepository.findByUsername(payload.username())
                .ifPresent(e -> {
                    throw new BadRequestException("Username '" + e.getUsername() + "' is already taken.");
                });
        Employee employee = new Employee(payload.username(), payload.firstName(), payload.lastName(), payload.email());
        employee.setProfileImage("https://ui-avatars.com/api/?name=" + payload.firstName() + "+" + payload.lastName());
        Employee savedEmployee = this.employeeRepository.save(employee);
        return savedEmployee;
    }


    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long employeeId) {
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    public Employee findByIdAndUpdate(Long employeeId, EmployeeDTO payload) {
        Employee found = this.findById(employeeId);
        if (!found.getEmail().equals(payload.email()))
            this.employeeRepository.findByEmail(payload.email()).ifPresent(employee -> {
                throw new BadRequestException("Email" + employee.getEmail() + "already used");
            });
        found.setUsername(payload.username());
        found.setFirstName(payload.firstName());
        found.setLastName(payload.lastName());
        found.setEmail(payload.email());
        found.setProfileImage("https://ui-avatars.com/api/?name=" + payload.firstName() + "+" + payload.lastName());

        Employee modifiedEmployee = this.employeeRepository.save(found);
        return modifiedEmployee;
    }

    public void findByIdAndDelete(Long employeeId) {
        Employee found = this.findById(employeeId);
        this.employeeRepository.delete(found);
    }

    //UPLOAD IMMAGINE

    public String uploadAvatar(Long employeeId, MultipartFile file) {
        try {
            Map result = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageURL = (String) result.get("url");

            Employee employee = this.findById(employeeId);
            employee.setProfileImage(imageURL);
            this.employeeRepository.save(employee);
            return imageURL;
        } catch (Exception e) {
            throw new BadRequestException("Sorry, problems with saving of the file");
        }
    }
}
