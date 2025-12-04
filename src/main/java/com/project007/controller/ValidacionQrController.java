package com.project007.controller;

import com.project007.config.CdiContainerManager;
import com.project007.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/validacion")
public class ValidacionQrController {

    private JwtService getJwtService() {
        return CdiContainerManager.getContainer().select(JwtService.class).get();
    }

    @PostMapping("/escanear")
    public ResponseEntity<?> validarQr(@RequestBody Map<String, String> payload) {
        String tokenQr = payload.get("token"); //envia el contenido del QR
        
        //por si el token esta expirado
        try {
            Claims claims = getJwtService().validarToken(tokenQr);

            return ResponseEntity.ok(Map.of(
                "esValido", true,
                "mensaje", "Acceso Autorizado",
                "datos", Map.of(
                    "asistente", claims.get("userName"),
                    "tipoEntrada", claims.get("ticketType"),
                    "eventoId", claims.getAudience(),
                    "entradaId", claims.getId()
                )
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                "esValido", false,
                "mensaje", "Token inválido, expirado o manipulado."
            ));
        }
    }
}