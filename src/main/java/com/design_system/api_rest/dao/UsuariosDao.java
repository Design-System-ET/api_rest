package com.design_system.api_rest.dao;

import com.design_system.api_rest.pojo.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosDao extends JpaRepository<Usuario, Long>{
    
    //listar todos los usuarios
    List<Usuario> findByNombre(String nombre);
    
    // Eliminar un usuario por su id
    void deleteById(Long id);
}
