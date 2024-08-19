package Tienda.demo.controller;


import Tienda.demo.service.CategoriaService;
import Tienda.demo.service.ProductoService;
import Tienda.demo.domain.Producto;
import Tienda.demo.service.ProductoService;
import Tienda.demo.service.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import Tienda.demo.service.CategoriaService;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @GetMapping("/listado")
    public String listado(Model model) {

        var lista = productoService.getProductos(false);
        
        model.addAttribute("productos", lista);
        model.addAttribute("totalProductos", lista.size());
        var categorias = categoriaService.getCategorias(true);
                
        model.addAttribute("categorias", categorias);
        return "/producto/listado";
    }

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @PostMapping("/guardar")
    public String guardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {

        if (!imagenFile.isEmpty()) { // Se debe subir una imagen 
            //Primero se guarda la producto... para obtener el idCtegoria nuevo
            productoService.save(producto);
            String ruta = firebaseStorageService.cargaImagen(imagenFile,
                    "Producto", producto.getIdProducto());
            producto.setRutaImagen(ruta);
        }
        productoService.save(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String modifica(Producto producto,
            Model model) {
        producto = productoService
                .getProducto(producto);
        model.addAttribute("producto", producto);
        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);
        return "producto/modifica";
    }

    @GetMapping("/eliminar/{idProducto}")
    public String elimina(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }

}