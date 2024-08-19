package Tienda.demo.dao;

import com.tienda_vt.domain.Usuario;
import com.tienda_vt.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioDao 
          extends JpaRepository<Usuario,Long>{
    
    public Usuario findByUsername(String username);
    
}