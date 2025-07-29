package valeriapagliarini.u5d11.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import valeriapagliarini.u5d11.payloads.LoginDTO;
import valeriapagliarini.u5d11.payloads.LoginRespDTO;
import valeriapagliarini.u5d11.services.AuthorizationService;
import valeriapagliarini.u5d11.services.EmployeeService;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        String accessToken = authorizationService.checkCredentialsAndGenerateToken(body);
        return new LoginRespDTO(accessToken);
    }


}
