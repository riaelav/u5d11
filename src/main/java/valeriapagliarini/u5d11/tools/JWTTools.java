package valeriapagliarini.u5d11.tools;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import valeriapagliarini.u5d11.entities.Employee;
import valeriapagliarini.u5d11.exceptions.UnauthorizedException;

import java.util.Date;

public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    private String createToken(Employee employee) {


        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) // Data di emissione del token (IAT - Issued At), va messa in millisecondi
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Data di scadenza del Token (Expiration Date), anche questa in millisecondi
                .subject(String.valueOf(employee.getId())) // Subject, ovvero a chi appartiene il token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Firmo il token tramite un algoritmo specifico che si chiama HMAC, passandogli il segreto
                .compact(); // Assemblo il tutto ottenendo la stringa finale che sarà il token

    }

    public void verifyToken(String accessToken) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(accessToken);
        } catch (Exception ex) {
            // .parse() ci lancerà diversi tipi di eccezioni a seconda che il token sia stato manipolato o scaduto o malformato
            throw new UnauthorizedException("Problemi con il token! Effettuare di nuovo il login!"); // --> 401
        }

    }


}
