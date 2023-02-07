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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
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
	
	//con la session lo que conseguimos es que se guarden variables como el cliente en la session del usuario
	//en lugar de poner lineas como esta en la pagina de listar:
	//<input type="hidden" th:field="*{id}" />
	//para que sepamos quienes somos
	@RequestMapping(value="/nuevoCliente", method=RequestMethod.POST)
	private String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo","Formulario de alta de Clientes");
			return "nuevoCliente";
		}
		clienteDao.save(cliente);
		status.setComplete();
		return "redirect:listar";
	}
	
	@RequestMapping(value="/eliminarCliente/{id}")
	private String Eliminar(@PathVariable("id") Long id) {
		if (id>0) {
			clienteDao.delete(id);
		}
		return "redirect:listar";
	}
	@RequestMapping(value = "/nuevoCliente/{id}")
	private String editar(@PathVariable(value = "id") Long id, Map<String,Object> model) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteDao.findOne(id);
		}else {
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("Titulo", "Editar Cliente");
		return "nuevoCliente";
	}
}