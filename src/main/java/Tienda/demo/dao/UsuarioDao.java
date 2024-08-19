package Tienda.demo.dao;

import Tienda.demo.domain.Usuario;
import Tienda.demo.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioDao 
          extends JpaRepository<Usuario,Long>{
    
    public Usuario findByUsername(String username);
    
}