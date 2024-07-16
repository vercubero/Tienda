/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
*/
package Tienda.demo;
 
import Tienda.demo.domain.Categoria;
import Tienda.demo.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;
 
    @GetMapping("/listado")
    public String listado(Model model) {
        List<Categoria> lista = categoriaService.getCategorias(false);
        model.addAttribute("categorias", lista);
        return "/categoria/listado";
    }
}