package com.project007.service;

import com.project007.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    Usuario crear(Usuario usuario);

    Optional<Usuario> findById(Long id);

    List<Usuario> selectAll();
    
    Usuario actualizar(Usuario usuario);

    void eliminar(Long id);
}

