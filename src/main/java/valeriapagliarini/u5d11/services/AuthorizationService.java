package valeriapagliarini.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import valeriapagliarini.u5d11.entities.Employee;
import valeriapagliarini.u5d11.exceptions.UnauthorizedException;
import valeriapagliarini.u5d11.payloads.LoginDTO;
import valeriapagliarini.u5d11.tools.JWTTools;

@Service
public class AuthorizationService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bCrypt;


    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        Employee found = this.employeeService.findByEmail(body.email());

        if (bCrypt.matches(body.password(), found.getPassword())) {

            String accessToken = jwtTools.createToken(found);
            return accessToken;
        } else {

            throw new UnauthorizedException("not authorized");
        }


    }
}


