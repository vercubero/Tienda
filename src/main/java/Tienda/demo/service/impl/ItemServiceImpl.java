package Tienda.demo.service.impl;

import Tienda.demo.dao.FacturaDao;
import Tienda.demo.dao.ProductoDao;
import Tienda.demo.dao.UsuarioDao;
import Tienda.demo.dao.VentaDao;
import Tienda.demo.domain.Factura;
import Tienda.demo.domain.Item;
import Tienda.demo.domain.Producto;
import Tienda.demo.domain.Usuario;
import Tienda.demo.domain.Venta;
import Tienda.demo.service.ItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public List<Item> gets() {
        return listaItems;
    }

    @Override
    public Item get(Item item) {
        for (Item i : listaItems) {
            if (i.getIdProducto() == item.getIdProducto()) {
                return i;
            }
        }

        return null;
    }

    @Override
    public void delete(Item item) {
        var posicion = -1;
        var existe = false;
        for (Item i : listaItems) {
            posicion++;
            if (i.getIdProducto() == item.getIdProducto()) {
                existe = true;
                break;
            }
        }

        if (existe) {
            listaItems.remove(posicion);
        }
    }

    @Override
    public void save(Item item) {
        var existe = false;
        for (Item i : listaItems) {
            if (i.getIdProducto() == item.getIdProducto()) {
                existe = true;
                if (i.getCantidad() < i.getExistencias()) {
                    i.setCantidad(i.getCantidad() + 1);
                }
                break;
            }
        }

        if (!existe) {
            item.setCantidad(1);
            listaItems.add(item);
        }

    }

    @Override
    public void update(Item item) {
        for (Item i : listaItems) {
            if (i.getIdProducto() == item.getIdProducto()) {
                i.setCantidad(i.getCantidad() + 1);
            break;
        }
    }
}
@Autowired
private UsuarioDao usuarioDao;
@Autowired
private ProductoDao productoDao;
@Autowired
private FacturaDao facturaDao;
@Autowired
private VentaDao ventaDao;
    
@Override
public void facturar() {
    // se debe recuperar el usuario autenticado y obtener su ID usuario 
    String username;
    Object principal = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    
    if (principal instanceof UserDetails userDetails){
        username=userDetails.getUsername();
        
    }else{
        username=principal.toString();
    }
    
    if (username.isBlank()){
        System.out.println("username en blanco...");
        return;
    }
    
    Usuario usuario = usuarioDao.findByUsername(username);
    if (usuario == null){
        System.out.println("Usuario no existe en usuarios..");
        return;
    }
    
    // se debe regiustrar la factura icluyendo el usuario 
    Factura factura = new Factura (usuario.getIdUsuario());
    factura=facturaDao.save(factura);
    // se debe registrar las ventas de acada producto , actualizando existencias
    double total=0;
    for (Item i : listaItems){
        Venta venta = new Venta(factura.getIdFactura(),
        i.getIdProducto(),
        i.getPrecio(),
        i.getCantidad());
        ventaDao.save(venta);
        Producto producto = productoDao.getReferenceById(i.getIdProducto());
        producto.setExistencias(producto.getExistencias()-i.getCantidad());
        productoDao.save(producto);
        total+=i.getCantidad()*i.getPrecio();
        
        
    }
    
    
    // se debe registrar el total de la venta en la factura 
    factura.setTotal(total);
    facturaDao.save(factura);
    
    
    // se debe limpiar el carrito la lista..
    listaItems.clear();
    }

}