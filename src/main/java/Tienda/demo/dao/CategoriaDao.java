package Tienda.demo.dao;

import Tienda.demo.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaDao extends JpaRepository <Categoria, Long>{
    
}
