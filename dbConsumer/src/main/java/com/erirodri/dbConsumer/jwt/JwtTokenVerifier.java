package com.erirodri.dbConsumer.jwt;

import com.erirodri.dbConsumer.api.dbConsumerController;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
        CLASE DONDE SE CREA EL FILTRO ENCARGADO DE VALIDAR EL TOKEN QUE SERA ENVIADO
            EN EL HEADER DE LA PETICION (REQUEST)
 */

public class JwtTokenVerifier extends OncePerRequestFilter {

    private static Logger log = LoggerFactory.logger(JwtTokenVerifier.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //1. Primero validamos que no venga vacio el campo Authorization en el Request
        String authorizationHeader= request.getHeader("Authorization");


        if(Strings.isNullOrEmpty(authorizationHeader)|| !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);  //Indicamos que esta mal el contenido
            return; //Finalizamos la validación
        }
        // Guardamos el token en una variable limpiando el identificador que asignamos al momento de crear el Token de respuesta (Clase que define el token)
        String token = authorizationHeader.replace("Bearer ","");
        // Retomamos el key que se definio para poder crear el token
        String secretKey="securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";
        log.info("Token: "+token);

        //2. En caso de que venga informado de forma correcta el campo Authorization en el Request
        try {

            // Se convierte el token con respecto al secretKey a un valor procesable por JWT
            Jwt claimsJws = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .parse(token);
            // Se guarda el body del valor generado
            Claims body = (Claims) claimsJws.getBody();
            //Se obtiene el nombre del usuario al que se le asigno el token
            String username = body.getSubject();

            //Tomando como referencia los datos que observamos al validar el token en https://jwt.io/, sabemos cual es la Key del map
            // para obtener el listado de autorizaciónes que tiene asignado el usuario en cuestion
            List<Map<String,String>> authorities = (List<Map<String,String>>) body.get("authorities");

            //Es necesario mapear la lista de autorizaciones en variable que sea aceptada por el metodo de Authentication
            Set<SimpleGrantedAuthority> authoritySet = authorities.stream()
                    // Se indica el key del map para saber la autoridad que tiene (Tomando como referencia los datos que observamos al validar el token en https://jwt.io/)
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    // Se guarda con Collector
                    .collect(Collectors.toSet());

            //Se procede a realizar la Autenticación
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authoritySet
            );
            //Se asigna el resultado de la autentificación
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (JwtException ex){
            throw new IllegalAccessError(String.format("Token %s can not be truest",token));
        }
        //Si todo salio bien se asigna el resultado
        filterChain.doFilter(request,response);

    }
}
