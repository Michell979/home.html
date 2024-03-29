package org.michell.controller;

import java.util.List;

import org.michell.model.Categoria;
import org.michell.service.IntCategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//anotacion @RequestMapping 
@RequestMapping(value="/categorias")
public class CategoriasController {
	
	@Autowired
	private IntCategoriasService categoriasService;
	
	
	
	@GetMapping(value = "/indexPaginate")
 public String mostrarIndexPaginado(Model model, Pageable page) {
        Page<Categoria>lista = categoriasService.buscarTodas(page);
        model.addAttribute("categorias", lista);
        return "categorias/listaCategorias";
    }
	
	
	@RequestMapping (value="/editar", method=RequestMethod.GET)
	public String editar(@RequestParam("id")int idCategoria, Model model) {
		Categoria categoria = categoriasService.buscarPorId(idCategoria);
		//System.out.println(categoria);
		model.addAttribute("categoria", categoria);
		return "categorias/formCategorias";
	}
	
	
	@RequestMapping(value="/eliminar",method=RequestMethod.GET)
public String eliminar(@RequestParam("id")int idCategoria,
		RedirectAttributes attributes) {
	categoriasService.eliminar(idCategoria);
	attributes.addFlashAttribute("msg", "Categoria Eliminada");
	return "redirect:/categorias/index";
	}
	
	
	
	//anotacion anterios para solicitar peticiones 
	//@GetMapping("index")
	//RequestMapping a nivel del metodo
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String mostrarIndex(Model model) {
		List<Categoria> lista =categoriasService.obtenerTodas();
		for(Categoria c : lista) {
			System.out.println(c);
		}
		model.addAttribute("categorias",lista);
		return "categorias/listaCategorias";
		
	}
	
	//@GetMapping("/crear")
	@RequestMapping(value="/crear",method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategorias";
	}
	
	@RequestMapping(value="/guardar",method=RequestMethod.POST)
	public String guardar (Categoria categoria, RedirectAttributes attributes) {
		//categoria.setId(categoriasService.obtenerTodas().size()+1);
		categoriasService.guardar(categoria);
		attributes.addFlashAttribute("msg", "La categoria se guardo");
		return "redirect:/categorias/indexPaginate";
	}
		
		
	//@PostMapping("guardar")
	//@RequestMapping(value="/guardar",method=RequestMethod.POST)
	//hacer la vinculacuion de lo selementos dle formulario 
	//public String guardar (@RequestParam("nombre") String nombre, 
		//	@RequestParam("descripcion") String descripcion) {
		//System.out.println("Nombre: "+ nombre);
		//System.out.println("Descripcion: "+descripcion);
		//return "categorias/listaCategorias";
	
	

}
