package Tienda.demo.service;

import Tienda.demo.domain.Categoria;
import java.util.List;

public interface CategoriaService {
 
    //Metodo que obtiene lista de categorias
    public List <Categoria> getCategorias(boolean activo);
}
