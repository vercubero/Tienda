package Tienda.demo.service.impl;

import Tienda.demo.dao.CategoriaDao;
import Tienda.demo.domain.Categoria;
import Tienda.demo.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

     @Override
    @Transactional(readOnly=true)
    public Categoria getCategoria(Categoria categoria) {
        return categoriaDao.findById(categoria.getIdCategoria()).orElse(null);
    }

    
    @Override
    @Transactional
    public void save(Categoria categoria) {
        categoriaDao.save(categoria);
    }

    @Override
    @Transactional
    public void delete(Categoria categoria) {
        categoriaDao.delete(categoria);
    }
}
