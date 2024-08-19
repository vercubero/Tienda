package Tienda.demo.controller;


import Tienda.demo.domain.Item;
import Tienda.demo.domain.Producto;
import Tienda.demo.service.ItemService;
import Tienda.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;

@Controller

public class CarritoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ItemService itemService;

    @GetMapping("/carrito/listado")
    public String listado(Model model) {
        var lista = itemService.gets();
        var carritoTotal=0;
        for (Item i : lista) {
            carritoTotal+=(i.getCantidad() * i.getPrecio());

        }
        model.addAttribute("items",lista);
        model.addAttribute("carritoTotal",carritoTotal);
        

        return "/carrito/listado";
    }

    @GetMapping("/carrito/agregar/{idProducto}")
    public ModelAndView agregar(Item item, Model model) {
        Item item2 = itemService.get(item);
        if (item2 == null) {
            Producto p = productoService.getProducto(item);
            item2 = new Item(p);
        }
        itemService.save(item2);
        var lista = itemService.gets();
        var totalCarrito = 0;
        var totalCompra = 0;
        for (Item i : lista) {
            totalCarrito += i.getCantidad();
            totalCompra += (i.getCantidad() * i.getPrecio());
        }
        model.addAttribute("listaitems", lista);
        model.addAttribute("listaTotal", totalCarrito);
        model.addAttribute("carritoTotal", totalCompra);
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }
    @GetMapping("/carrito/eliminar/{idProducto}")
    public String eliminar (Item item){
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }
    @GetMapping("/carrito/modificar/{idProducto}")
    public String modificar (Item item ,Model model){
        item = itemService.get(item);
        model.addAttribute("item",item);
        return "/carrito/modifica";
    }
    @PostMapping("/carrito/guardar")
    public String guardar (Item item ){
        itemService.update(item);
        return "redirect:/carrito/listado";
    }
    @GetMapping("/facturar/carrito")
    public String facturar (){
        itemService.facturar();
        return "redirect:/";
    }
}
