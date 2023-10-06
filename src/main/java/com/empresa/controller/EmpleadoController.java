package com.empresa.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import com.empresa.entity.Empleado;

import com.empresa.service.EmpleadoService;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Controller
public class EmpleadoController {

	
	
	@Autowired
	private EmpleadoService empleadoService;
	
	
	
	@RequestMapping("/verCrudEmpleado")
	public String verInicio() {
		return "crudEmpleado";
	}
	
	
	@GetMapping("/ConsultaCrudEmpleado")
	@ResponseBody
	public List<Empleado> consulta(String filtro) {
		return empleadoService.listaPorNombreApellidoLike("%"+filtro+"%");
	}
	
	
	
	
	@PostMapping("/registraCrudEmpleado")
	@ResponseBody
	public Map<?, ?> registra(Empleado obj) { 
		HashMap<String, Object> map = new HashMap<String, Object>();
		obj.setEstado(1);
		obj.setFechaRegistro(new Date());
		obj.setFechaActualizacion(new Date());  
		
		List<Empleado> lstSalida = empleadoService.listaPorNombreApellidoIgual(obj.getNombres(), obj.getApellidos());
		//CollectionUtils.isEmpty : dara true si esta vacio y false si esta lleno
		if (!CollectionUtils.isEmpty(lstSalida)) {
			map.put("mensaje", "El empleado " + obj.getNombres() + " " + obj.getApellidos() + " ya existe");
			return map;
		}
		
		
		
		Empleado objSalida = empleadoService.insertaEmpleado(obj);
		if (objSalida == null) {
			map.put("mensaje", "Error en el registro");
		} else {
			List<Empleado> lista = empleadoService.listaPorNombreApellidoLike("%");
			map.put("lista", lista);
			map.put("mensaje", "Registro exitoso");
		}
		return map;
	}
	
	
	
	
	
	
	@ResponseBody
	@PostMapping("/eliminaCrudEmpleado")
	public Map<?, ?> elimina(int id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Empleado objEmpleado= empleadoService.buscaEmpleado(id).get();
		objEmpleado.setFechaActualizacion(new Date());  
		objEmpleado.setEstado( objEmpleado.getEstado() == 1 ? 0 : 1);
		Empleado objSalida = empleadoService.actualizaEmpleado(objEmpleado);
		if (objSalida == null) {
			map.put("mensaje", "Error en actualizar");
		} else {
			List<Empleado> lista = empleadoService.listaPorNombreApellidoLike("%");
			map.put("lista", lista);
		}
		return map;
	}
	
	
	
	
	
	
	
	@GetMapping("/buscaEmpleadoMayorEdad")
	@ResponseBody
	public String validaFecha(String fechaNacimiento) {
		log.info(">> validaFecha >> " + fechaNacimiento);
		//Convierte de String a Date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaIngresada = null;
		try {
			fechaIngresada =  sdf.parse(fechaNacimiento);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Se obtiene la fecha limite del mayor de edad
		Calendar cal = Calendar.getInstance(); //5/10/2023
		int anioActual = cal.get(Calendar.YEAR);
		cal.set(Calendar.YEAR, anioActual - 18);
		Date fechaMayorEdadLimite = cal.getTime(); //5/10/2005
		
		if(fechaIngresada.before(fechaMayorEdadLimite)) {
			return "{\"valid\":true}";
		}else {
			return "{\"valid\":false}";
		}
	}
	
	
	
	
	
	
	
	
}
