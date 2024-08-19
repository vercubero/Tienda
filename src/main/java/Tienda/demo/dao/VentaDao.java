package Tienda.demo.dao;

import Tienda.demo.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VentaDao 
          extends JpaRepository<Venta,Long>{
    
}
