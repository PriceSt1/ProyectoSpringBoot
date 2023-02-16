package com.bolsadeideas.springboot.app.models.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

public interface IClienteService {
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageRequest);
	public void save(Cliente cliente);
	public Cliente findOne(Long id);
	public void delete(Long id);
	

}