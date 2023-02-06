package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.validation.Valid;

@Controller
public class ClienteController {

	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	private String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "listar";
	}
	@RequestMapping(value="/nuevoCliente")
	public String crear(Map<String,Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de alta de Clientes");
		return "nuevoCliente";
	}
	
	@RequestMapping(value="/nuevoCliente", method=RequestMethod.POST)
	private String guardar(@Valid Cliente cliente, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("titulo","Formulario de alta de Clientes");
			return "nuevoCliente";
		}
		clienteDao.save(cliente);
		return "redirect:listar";
	}
	
	@RequestMapping(value="/eliminarCliente/{id}", method = RequestMethod.GET)
	private String Eliminar(@PathVariable("id") String id) {
		System.out.println(id);
		//clienteDao.delete(id);
		return "redirect:listar";
	}
}
