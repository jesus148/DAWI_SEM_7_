<!DOCTYPE html>
<html lang="esS" >
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>
<script type="text/javascript" src="js/global.js"></script>

<link rel="stylesheet" href="css/bootstrap.css"/>
<link rel="stylesheet" href="css/dataTables.bootstrap.min.css"/>
<link rel="stylesheet" href="css/bootstrapValidator.css"/>
<title>Ejemplos de CIBERTEC - Jorge Jacinto </title>
</head>
<body> 

 <div class="container">
 <h3>Crud de Empleado</h3>
		 <div class="col-md-23" >  

				   <div class="row" style="height: 70px">
						<div class="col-md-2" >
								<input class="form-control" id="id_txt_filtro"  name="filtro" placeholder="Ingrese el nombre" type="text" maxlength="30"/>
						</div>
						<div class="col-md-2" >
							<button type="button" class="btn btn-primary" id="id_btn_filtrar" style="width: 150px">FILTRA</button>
						</div>
						<div class="col-md-2">
                 <!-- 		ABRE EL MODAL DEL REGISTRAR -->
							<button type="button" data-toggle='modal'  data-target="#id_div_modal_registra"  class='btn btn-success' style="width: 150px">REGISTRA</button>
						</div>
					</div>
					<div class="row" > 
						<div class="col-md-12">
								<div class="content" >
						
									<table id="id_table" class="table table-striped table-bordered" >
										<thead>
											<tr>
										<!-- 	MOSTRANDO LOS DATOS QUE QUEREMOS -->
												<th style="width: 5%" >ID</th>
												<th style="width: 22%">Nombre</th>
												<th style="width: 23%">Apellidos</th>
												<th style="width: 15%">FechaNacimiento/th>
												<th style="width: 15%">Pais</th>
<!-- 												//BOTONES CON EVENTOS  -->
												<th style="width: 10%">Actualiza</th> <!--MODAL ACTUALIZA -->
												<th style="width: 10%">Elimina</th> <!-- ESTADO -->
											</tr>
										</thead>
											<tbody>
											</tbody>
										</table>
								</div>	
						</div>
					</div>
		  </div>
  
  
  
  
  
  
  
<!--   MODAL REGISTRAR  -->
  <div class="modal fade" id="id_div_modal_registra" >
			<div class="modal-dialog" style="width: 60%">
		
				<!-- Modal content-->
				<div class="modal-content">
				<div class="modal-header" style="padding: 35px 50px">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4><span class="glyphicon glyphicon-ok-sign"></span> Registro de modalidad</h4>
				</div>
				<div class="modal-body" style="padding: 20px 10px;">
						<form id="id_form_registra" accept-charset="UTF-8" class="form-horizontal"     method="post">
		                    <div class="panel-group" id="steps">
		                        <!-- Step 1 -->
		                        <div class="panel panel-default">
		                            <div class="panel-heading">
		                                <h4 class="panel-title"><a data-toggle="collapse" data-parent="#steps" href="#stepOne">Datos de Modalidad</a></h4>
		                            </div>
		                            <div id="stepOne" class="panel-collapse collapse in">
		                                <div class="panel-body">
		                                     <div class="form-group">
		                                        <label class="col-lg-3 control-label" for="id_reg_nombres">Nombre</label>
		                                        <div class="col-lg-8">
													<input class="form-control" id="id_reg_nombres" name="nombres" placeholder="Ingrese el Nombre" type="text" maxlength="20"/>
		                                        </div>
		                                    </div>
		                                    <div class="form-group">
		                                        <label class="col-lg-3 control-label" for="id_apellidos">Apellidos</label>
		                                        <div class="col-lg-3">
													<input class="form-control" id="id_apellidoss" name="apellidos" placeholder="Ingrese Apellidos" type="text" maxlength="20"/>
		                                        </div>
		                                    </div>
		                                    <div class="form-group">
		                                        <label class="col-lg-3 control-label" for="id_fechaNacimiento">Fecha Nacimiento</label>
		                                        <div class="col-lg-3">
													<input class="form-control" id="id_fechaNacimiento" name="fechaNacimiento"   type="date"/>
		                                        </div>
		                                    </div>		   
		                                   
		                                    <div class="form-group">
		                                        <label class="col-lg-3 control-label" for="id_reg_pais">Pais</label>
		                                        <div class="col-lg-3">
													 <select id="id_reg_pais" name="pais" class='form-control'>
							                            	<option value=" ">[Seleccione]</option>    
							                         </select>
		                                        </div>
		                                    </div>
		                                    <div class="form-group">
		                                        <div class="col-lg-9 col-lg-offset-3">
		                                        	<button type="button" class="btn btn-primary" id="id_btn_registra">REGISTRA</button>
		                                        </div>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                        
		                    </div>
		                </form>   
				
				</div>
			</div>
		</div>
			
		</div>
  
  
  
  
  
  
</div>

<script type="text/javascript">



// COMBO PARA CARGAR EL COMBO AL INCIAR
$.getJSON("listaPais", {}, function(data){
	$.each(data, function(i,item){
		//id del modal                           atributos clase guia =
		$("#id_reg_pais").append("<option value="+item.idPais +">"+ item.nombre +"</option>");
		
	});
});




//METODO PARA BUSCAR Y LISTAR 
$("#id_btn_filtrar").click(function(){
	var fil=$("#id_txt_filtro").val(); //el id del input 
	//url del controller , "filtro" igual en el contrller el parametro
	$.getJSON("ConsultaCrudEmpleado",{"filtro":fil}, function (lista){
		agregarGrilla(lista);
	});
});





//METODO REGISTRA 
//el id del boton modal registra
$("#id_btn_registra").click(function(){
	//VALIDATOR
	var validator = $('#id_form_registra').data('bootstrapValidator');
    validator.validate();
	
    if (validator.isValid()) {
        $.ajax({
          type: "POST",
          url: "registraCrudEmpleado", //url del controller
          data: $('#id_form_registra').serialize(), //id del formulario envia todo
          success: function(data){
        	  agregarGrilla(data.lista); //lista de la variable map = al controller
        	  $('#id_div_modal_registra').modal("hide"); //esconde el modal
        	  mostrarMensaje(data.mensaje);//mustra mensaje , mensaje es la variable map = al controller 
        	  limpiarFormulario(); //limpia el formulario
        	  validator.resetForm();
          },
          error: function(){
        	  mostrarMensaje(MSG_ERROR);
          }
        });
        
    }
});






//METODO PARA LIMPIAR EL FORMULARIO

function limpiarFormulario(){	
	$('#id_reg_nombres').val('');
	$('#id_apellidoss').val('');
	$('#id_fechaNacimiento').val('');
	$('#id_reg_pais').val('');

}




//METODO PARA CAMBIAR EL ESTADO 
function accionEliminar(id){	
    $.ajax({
          type: "POST",
          url: "eliminaCrudEmpleado",  //URL DEL CONTROLADOR 
          data: {"id":id}, //"id" : parametro del controller 
          success: function(data){
        	  agregarGrilla(data.lista);
          },
          error: function(){
        	  mostrarMensaje(MSG_ERROR);
          }
     });
}



//METODO PARA AGREGAR A LA TABLA
function agregarGrilla(lista){
	 $('#id_table').DataTable().clear();
	 $('#id_table').DataTable().destroy();
	 $('#id_table').DataTable({
			data: lista,
			searching: false,
			ordering: true,
			processing: true,
			pageLength: 5,
			lengthChange: false,
			columns:[
				//ATRUBUTOS = A LA CLASE GUIA , solo queremos de toda esa data de la bd alugnos ,  segun la tabla arriba y su espacio
				{data: "idEmpleado"},
				{data: "nombres"},
				{data: "apellidos"},
				{data: "fechaNacimiento"},
				//queremos mostrar el nombre saldra segun el id , esto mostrara en la tabla
				{data: "pais.nombre"},
				//METODO PARA EDITAR 
				{data: function(row, type, val, meta){
					var salida='<button type="button" style="width: 90px" class="btn btn-info btn-sm" onclick="editar(\''+row.idEmpleado + '\',\'' + row.nombres +'\',\'' + row.apellidos  +'\',\'' + row.fechaNacimiento + '\',\''  + row.pais.idPais + '\')">Editar</button>';
					return salida;
				},className:'text-center'},	
				//METODO PARA ELIMINAR O CAMBIAR EL ESTADO
				{data: function(row, type, val, meta){                                                                                                           //DEL ID MUESTRA ACTIVO O INACTIVO               
				    var salida='<button type="button" style="width: 90px" class="btn btn-warning btn-sm" onclick="accionEliminar(\'' + row.idEmpleado + '\')">'+ (row.estado == 1? 'Activo':'Inactivo') +  '</button>';
					return salida;
				},className:'text-center'},													
			]                                     
	    });
}









//VALIDACIONES PARA EL MODAL REGISTRA
//el id del moodal registra su form
$('#id_form_registra').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
    	//los names de los inputs = a clase guia y a ala bd
    	"nombres": {
    		selector : '#id_reg_nombres', //id de los inputs
            validators: {
                notEmpty: {
                    message: 'El nombre es un campo obligatorio'
                },
                stringLength :{
                	message:'El nombre es de 5 a 100 caracteres',
                	min : 5,
                	max : 100
                }
            }
        },
        "apellidos": {
    		selector : '#id_apellidoss',
            validators: {
                notEmpty: {
                    message: 'El número de hombres es un campo obligatorio'
                },
                stringLength :{
                	message:'El nombre es de 5 a 100 caracteres',
                	min : 5,
                	max : 100
                }
      
            }
        },
        "fechaNacimiento": {
    		selector : '#id_fechaNacimiento',
            validators: {
            	notEmpty: {
                    message: 'la fecha es un campo obligatorio'
                },
                //ACA BUSCAREMOS Q EL EMPLEADO SEA MAYOR DE EDAD
                remote:{
                	delay:1000,
                	url:	'buscaEmpleadoMayorEdad', //URL DE CONTROLLER
                	message:	'el empleado debe ser mayor de edad' //mensaje en caso se cumpla
                	
                	
                }
            }
        },
        //esto para enviar al la bd
        "pais.idPais": {
    		selector : '#id_reg_pais',
            validators: {
            	notEmpty: {
                    message: 'El pais un campo obligatorio'
                },
            }
        },
    	
    }   
});

</script>
    
</body>
</html> 