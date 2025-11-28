package com.project007.controller;

import com.project007.config.CdiContainerManager;
import com.project007.entity.Usuario;
import com.project007.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private IUserService getUserService() {
        return CdiContainerManager.getContainer().select(IUserService.class).get();
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = getUserService().crear(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> usuarios = getUserService().selectAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return getUserService().findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        // Asegurarse de que el ID en el path y en el cuerpo coincidan o usar el del path
        usuario.setId(id);
        Usuario actualizado = getUserService().actualizar(usuario);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        getUserService().eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
