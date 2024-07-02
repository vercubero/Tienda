package Tienda.demo.service.impl;

import Tienda.demo.dao.CategoriaDao;
import Tienda.demo.domain.Categoria;
import Tienda.demo.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    public List<Categoria> getCategorias(boolean activo) {
        List<Categoria> lista = categoriaDao.findAll();

        //Filtrar si quiero solo activos
        if (activo) {
            lista.removeIf(c -> !c.isActivo());
        }
        return lista;
    }

}
