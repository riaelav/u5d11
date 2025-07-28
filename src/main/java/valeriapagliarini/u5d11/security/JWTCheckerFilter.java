package valeriapagliarini.u5d11.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import valeriapagliarini.u5d11.exceptions.UnauthorizedException;
import valeriapagliarini.u5d11.tools.JWTTools;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //verifico che ci sia Authorization Header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Insert token in Authorization Header in the correct format")
    //estraggo il token dall'header
        String accessToken = authHeader.replace("Bearer", "");
        //verifico che il token non sia stato manipolato e che non sia scaduto
        jwtTools.verifyToken(accessToken);

        //se è tutto ok proseguo
        filterChain.doFilter(request,response);

        // se qualcosa non va errore 401
    }

    // disabilito questo filtro per determinati endpoints tipo /auth/login e /auth/register
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());

    }
}
