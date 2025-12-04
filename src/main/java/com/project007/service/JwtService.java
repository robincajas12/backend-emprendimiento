package com.project007.service;

import com.project007.entity.Entrada;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@ApplicationScoped
public class JwtService {

    //nunca debe ser hardcodeada ni expuesta en el control de versiones.
    private static final String SECRET_KEY_STRING = System.getenv("JWT_SECRET") != null ? 
            System.getenv("JWT_SECRET") : 
            "mi_clave_secreta_para_la_app_del_proyect007";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    }

    public String generarTokenEntrada(Entrada entrada) {
        long eventoId = entrada.getTipoEntrada().getEvento().getId();
        Date fechaFinEvento = entrada.getTipoEntrada().getEvento().getFechaFin();
        String nombreTipoEntrada = entrada.getTipoEntrada().getNombre();
        String nombreAsistente = entrada.getAsistente().getNombreCompleto();

        return Jwts.builder()
                .id(entrada.getId().toString())              //JWT ID
                .subject(entrada.getAsistente().getId().toString()) 
                .audience().add(String.valueOf(eventoId)).and() 
                .claim("ticketType", nombreTipoEntrada)      
                .claim("userName", nombreAsistente)          
                .issuedAt(new Date())
                .expiration(fechaFinEvento)                  
                .signWith(getSigningKey())
                .compact();
    }

    public Claims validarToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}