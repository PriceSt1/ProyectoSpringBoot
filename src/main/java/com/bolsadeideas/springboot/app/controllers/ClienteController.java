package com.bolsadeideas.springboot.app.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteDao;
	private IUploadFileService fileService;

	private final Logger log = LoggerFactory.getLogger(getClass());
	private final static String UPLOADS_FOLDER="uploads";

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);

	}

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = clienteDao.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en nuestra BBDD");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Detalles del cliente: " + cliente.getNombre() + " " + cliente.getApellido());
		return "ver";
	}

	@RequestMapping(value = { "", "/" })
	public String Index(Model model) {
		return "index";
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	private String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = (Pageable) PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteDao.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@RequestMapping(value = "/nuevoCliente")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de alta de Clientes");
		return "nuevoCliente";
	}

	// con la session lo que conseguimos es que se guarden variables como el cliente
	// en la session del usuario
	// en lugar de poner lineas como esta en la pagina de listar:
	// <input type="hidden" th:field="*{id}" />
	// para que sepamos quienes somos
	
	
	@RequestMapping(value = "/nuevoCliente", method = RequestMethod.POST)
	private String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status, @RequestParam("file") MultipartFile foto) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de alta de Clientes");
			return "nuevoCliente";
		}

		if (!foto.isEmpty()) {
			/*
			 * Path directorioRecurso = Paths.get("src//main//resources//static/uploads");
			 * String rootPath=directorioRecurso.toFile().getAbsolutePath(); String
			 * rootPath="C://Temp//uploads";
			 */

			// Aqui se sustituye la foto antigua por una nueva
			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto().length() > 0) {
				Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
				File archivoFile = rootPath.toFile();
				if (archivoFile.exists() && archivoFile.canRead()) {
					archivoFile.delete();
				}
			}
			String uniqueFilename = fileService.copy(foto);
			flash.addFlashAttribute("info", "Has subido correctamente " +  uniqueFilename);
			cliente.setFoto(uniqueFilename);
		}
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado correctamente"
				: "Cliente inseratado correctamente";
		clienteDao.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/listar";
	}

	@RequestMapping(value = "/eliminarCliente/{id}")
	private String Eliminar(@PathVariable("id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = clienteDao.findOne(id);
			clienteDao.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado correctamente");

			Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
			File archivo = rootPath.toFile();
			if (archivo.exists() && archivo.canRead()) {
				if (archivo.delete()) {
					flash.addFlashAttribute("info", "La Foto " + cliente.getFoto() + " ha sido borrada con exito.");
				}
			}
		}

		return "redirect:/listar";
	}

	@RequestMapping(value = "/nuevoCliente/{id}")
	private String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteDao.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "Cliente no existe en la base de datos");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "Id erroneo");
			return "redirect:/listar";

		}
		model.put("cliente", cliente);
		model.put("Titulo", "Editar Cliente");
		return "nuevoCliente";
	}
}
