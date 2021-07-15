package com.erirodri.dbConsumer.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;

/*
       Clase para filtrar el contenido enviado para hacer la utentificación
       y ser enviada a spring security para realizar el proceso
       correspondiente
 */
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //El siguiente parametro es el que sera enviado a "ApplicationSecurityConfig" para realizar
    //    el flujo de autentificación
    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class); // Se toma lo que viene en el Request y se manda a la forma definida en la clase que se indica
            // Se asignan los datos obtenidos en el request del cliente a una variable Autentication
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
            );
            return authenticationManager.authenticate(authentication);
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    // Si el authenticationManager encuentra algun error con lo asignado, no se entraria a este nuevo metodo
    // El siguiente metodo se encargara de generar el Token que sera enviado al cliente
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // Se define una Key para crear el token
        String key="securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";
        //Se construye el token
        String token = Jwts.builder()
                            .setSubject(authResult.getName())//Asignar el nombre del usuario autenticado
                            .claim("authorities",authResult.getAuthorities())
                            .setIssuedAt(new Date())
                            .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                            .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                            .compact();
        response.addHeader("Authorization","Bearer "+token); //Asignar el token generado en un header del response
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
