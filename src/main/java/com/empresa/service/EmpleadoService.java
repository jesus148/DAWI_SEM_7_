package com.empresa.service;

import java.util.List;
import java.util.Optional;

import com.empresa.entity.Empleado;

public interface EmpleadoService {

	//Crud
	//METODO PARA INSERTAR EMPLEADO
	public abstract Empleado insertaEmpleado(Empleado obj);
	
	//METODO ACTUALIZA EMPLEADO
	public abstract Empleado actualizaEmpleado(Empleado obj);
	
	//METODO PARA LISTAR ALL EMPLEADO
	public abstract List<Empleado> listaPorNombreApellidoLike(String filtro);
	
	
	//METODO PARA BUSCAR  EMPLEADO SEGUN EL ID
	public abstract Optional<Empleado> buscaEmpleado(int idEmpleado);
	
	
	//METODO PARA NO PERMITIR REGISTRAR EMPLEADO Q TENGAN EL MISMO NOMBRE O APELLIDO
	public abstract List<Empleado> listaPorNombreApellidoIgual(String nombre, String apellido); 
}
