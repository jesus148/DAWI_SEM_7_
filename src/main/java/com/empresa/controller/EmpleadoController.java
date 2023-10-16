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

	
	
	//SERVICE
	@Autowired
	private EmpleadoService empleadoService;
	
	
	
	//VER EL JSP CRUD MODALIDAD
	@RequestMapping("/verCrudEmpleado")
	public String verInicio() {
		return "crudEmpleado"; //debe ser igual al nombre del jsp
	}
	
	
	
	
	//METODO PARA CARGAR EL COMBO PAIS ESTA EN EL CONTROL CARGACOMBOCONTROLLER
	
	
	
	
	
	
	
	//METODO PARA LISTAR ALL EMPLEADO O SEGUN EL PARAMETRO
	@GetMapping("/ConsultaCrudEmpleado")
	@ResponseBody
	public List<Empleado> consulta(String filtro) {
		return empleadoService.listaPorNombreApellidoLike("%"+filtro+"%");
	}
	
	
	
	
	
	
	
	//METODO BUSCAR EMPLEADO CONTROLLER
	@PostMapping("/registraCrudEmpleado")
	@ResponseBody
	public Map<?, ?> registra(Empleado obj) {  //RECIbe el objeto del fromulario
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//DE ESE OBJETO MODIFICA Y AGREGA DATOS PARA LA BD
		obj.setEstado(1);
		obj.setFechaRegistro(new Date());
		obj.setFechaActualizacion(new Date());
		
		//METODO PARA NO PERMITIR REGISTRAR EMPLEADO Q TENGAN EL MISMO NOMBRE O APELLIDO
		List<Empleado> lstSalida = empleadoService.listaPorNombreApellidoIgual(obj.getNombres(), obj.getApellidos());
		//CollectionUtils.isEmpty : dara true si esta vacio y false si esta lleno
		if (!CollectionUtils.isEmpty(lstSalida)) {
			map.put("mensaje", "El empleado " + obj.getNombres() + " " + obj.getApellidos() + " ya existe");
			return map; //FINALIZA EL PROCESO
		}
		
		
		
		
		
		//METODO PARA INSERTAR SI NO SE REPITE
		Empleado objSalida = empleadoService.insertaEmpleado(obj);
		//DATOS DE SALIDA
		if (objSalida == null) {
			map.put("mensaje", "Error en el registro");
		} else {
			List<Empleado> lista = empleadoService.listaPorNombreApellidoLike("%"); //VUELVE A LISTAR
			map.put("lista", lista);//"lista" : igual en el jsp
			map.put("mensaje", "Registro exitoso");// "mensaje" : igual en el jsp
		}
		return map;
	}
	
	
	
	
	
	
	
	
	
	//METODO PARA CAMBIAR EL ESTADO
	
	@ResponseBody
	@PostMapping("/eliminaCrudEmpleado")
	public Map<?, ?> elimina(int id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//METODO BUSCAR EL EMPLEADO
		Empleado objEmpleado= empleadoService.buscaEmpleado(id).get();
		//de esa data obtenida modificamos ciertos datos
		objEmpleado.setFechaActualizacion(new Date());  
		objEmpleado.setEstado( objEmpleado.getEstado() == 1 ? 0 : 1);
		
		//METODO PARA ACTUALIZAR EL OBJETO
		Empleado objSalida = empleadoService.actualizaEmpleado(objEmpleado);
		
		//datos de regreso
		if (objSalida == null) {
			map.put("mensaje", "Error en actualizar");
		} else {
			List<Empleado> lista = empleadoService.listaPorNombreApellidoLike("%");
			map.put("lista", lista);
		}
		return map;
	}
	
	
	
	
	
	
	//METODO PARA VERIFICAR Q EL EMPLEADO SEA MAYOR DE EDAD
	@GetMapping("/buscaEmpleadoMayorEdad")
	@ResponseBody
	public String validaFecha(String fechaNacimiento) {
		log.info(">> validaFecha >> " + fechaNacimiento);
		
		
		//CONVIRTIENDO DE STRING A DATE
		//Convierte de String a Date con 1 formato segun la bd, del jsp recibe como string  x eso
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaIngresada = null;
		//recordar cuando convertimos de string a date recomendable usar try catch
		try {
			fechaIngresada =  sdf.parse(fechaNacimiento);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		
//		Java Calendar getInstance()
//		El método getInstance() de la clase java.util.Calendar es un método estático. Este método se utiliza con el objeto de calendario
//		para obtener la instancia del calendario de acuerdo con la zona horaria actual establecida por el entorno de ejecución de Java.
//		Obtiene una instancia de un calendario, bien sea la instancia por defecto si no le especificamos anda o bien la instancia que se asocie 
//		a la TimeZone o el Locale que le pasemos como parámetro.
		
		
		//Se obtiene la fecha limite del mayor de edad
		 //esto obtiene la fecha actual(saldra en este formato mycal1 :Mon Oct 16 16:59:01 IST 2023 ) , segun tu zona horaria
		Calendar cal = Calendar.getInstance();
		int anioActual = cal.get(Calendar.YEAR); // de cal fecha actual quiero solo el año de esa fecha
//		El método set(int calndr_field , int new_val ) en la clase Calendar se usa para establecer el valor de calndr_field en new_val. 
//		El campo anterior de este calendario será reemplazado por un campo nuevo.
//		Parámetros: el método toma dos parámetros:
//
//			(Calendar.YEAR : Este es de tipo Calendario y se refiere al campo del calendario que se va a modificar.
//			anioActual - 18 : Esto se refiere al nuevo valor con el que se reemplazará.(2023- 18)
		cal.set(Calendar.YEAR, anioActual - 18);
		Date fechaMayorEdadLimite = cal.getTime(); //16/10/2005
		
		//VERIFICA SI ES MAYOR DE EDAD
		if(fechaIngresada.before(fechaMayorEdadLimite)) {
			return "{\"valid\":true}";//SI LA fecha ingresa es antes del  //5/10/2005 entonces es mayor y registra
		}else {
			return "{\"valid\":false}"; // y sino no lo registra
		}
	}
	
	
	
	
	
	
	
	
}
