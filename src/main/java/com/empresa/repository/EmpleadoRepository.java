package com.empresa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.entity.Empleado;



public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
	
	//METODO PARA LISTAR ALL EMPLEADO, consulta personalizada, BUSCAR EL PARAMETRO EN EL NOMBRE Y EN EL APELLIDO
	@Query("select e from Empleado e where e.nombres like ?1 or e.apellidos like ?1")
	public List<Empleado> ListaEmpleadoPorNombreApellidoLike(String filtro);
	
	
	//METODO PARA NO PERMITIR REGISTRAR EMPLEADO Q TENGAN EL MISMO NOMBRE O APELLIDO
	@Query("select e from Empleado e where e.nombres = ?1 and e.apellidos = ?2 ")
	public List<Empleado> listaEmpleadoPorNombreApellidoIgual(String nombre, String apellido);

}
