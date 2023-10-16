package com.empresa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.entity.Empleado;
import com.empresa.repository.EmpleadoRepository;

@Service
public class EmpleadoIServicempl implements EmpleadoService {

	@Autowired
	private EmpleadoRepository repository;
	
	//METODO PARA INSERTAR EMPLEADO
	@Override
	public Empleado insertaEmpleado(Empleado obj) {
		return repository.save(obj);
	}

	//METODO ACTUALIZA EMPLEADO
	@Override
	public Empleado actualizaEmpleado(Empleado obj) {
		return repository.save(obj);
	}

	
	//METODO PARA LISTAR ALL EMPLEADO
	@Override
	public List<Empleado> listaPorNombreApellidoLike(String filtro) {
	   return repository.ListaEmpleadoPorNombreApellidoLike(filtro);
	}

	
	
	//METODO PARA BUSCAR  EMPLEADO SEGUN EL ID
	@Override
	public Optional<Empleado> buscaEmpleado(int idEmpleado) {
		return repository.findById(idEmpleado);
	}

	
	//METODO PARA NO PERMITIR REGISTRAR EMPLEADO Q TENGAN EL MISMO NOMBRE O APELLIDO
	@Override
	public List<Empleado> listaPorNombreApellidoIgual(String nombre, String apellido) {
		return repository.listaEmpleadoPorNombreApellidoIgual(nombre, apellido);
	}
	
	

}
