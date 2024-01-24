/**
 * 
 * NOTA: Las funciones con terminacion REcall se llaman para cuando se recarga la pagina, para que no se realize 
 * la validacion de algun cambio y no se tenga que recalcular las comisiones
 *  
 */


  // Funciones que estan en ordenAfiliacionAdquirente.xhtml

              function startAdq() {
            	
              	loadCoType('img_contracts/inc/cotype.xml', 'forma:client_categorycode');
				adquirerOption();
				adquirerPesosDolares(); //en base a la divisa y tramite se habilitan o deshabilitan campos de numeros de cuenta o afiliacion 
				//si el tramite es "Cambio de banco adquirente" se habilita el campo de banco actual 
				edFill('forma:affiliation_servicetype','forma:affiliation_currentbank', 'Cambio de Banco Adquirente');
				//si el tramite es "alta" se deshabilitan los campos de afiliacion pesos y dolares
				edFills('forma:affiliation_servicetype','forma:affiliation_numaffilmn','forma:affiliation_numaffildlls','Alta');
				//se habilitan o deshabilitan los campos de fianza
				var seleccion= valorSeleccionGarantiaLiquida();
				if(seleccion==1){
					onchangeFianza();
				}
				
				
                Calendar.setup({ inputField: "forma:affiliation_duedate", ifFormat: "%d/%m/%Y"});
                
                if(document.getElementById("forma:loaded").value == 'true'){
                	document.getElementById("forma:recalculateCommisionTable").value = document.getElementById("forma:recalculateCommisionTable").value;
                }else{
                	document.getElementById("forma:recalculateCommisionTable").value = false;
                	document.getElementById("forma:loaded").value = true;
                }
                var alliance = document.getElementById("forma:affiliation_alliance").value;
          	  //si la alianza es micros o netpay
          	    if(alliance == "Micros" || alliance == "Netpay" ){
          	    	if(document.getElementById("forma:affiliation_quantpinpad").value >0 ){ //y tiene equipos pinpad
          	    		activateEquipments();//se activan los equipos
          	    	}
          	    	if(alliance=="Netpay"){//si alianza es netpay
          	    		if(document.getElementById("forma:affiliation_tiempoaire").checked==true){//y tiene tiempo aire
          	    			activateTA();//se activa el bloque de companias telefonicas
          	    		}else{
          	    			deactivateTA();//si no tiene TA, se desactivan
          	    		}
          	    	}
          	    }
              }
           
              function inicioAdquirente(){  
/*
 					POSIBLE SOLUCION PARA CUANDO YA SE HABIA CONTRATADO UN DISPOSIVO BLUETOOTH
             	    var tpvBlueTel = document.getElementById("forma:tpvBlueTel");
            	    alert('tpvBlueTel: '+tpvBlueTel.value);
            	    if(tpvBlueTel.value>0){
            	    	document.getElementById("forma:recalculateCommisionTable").value = true;
            	    }
            	    tpvBlueTel.value=0;
*/
//					var affiliation_rateMposmn = document.getElementById("forma:affiliation_rateMposmn");
//					alert('valor de affiliation_rateMposmn: '+affiliation_rateMposmn.value);
	            	var red = valorRed();
	  	       		var plan = document.getElementById("forma:affiliation_comercialplan").value;
	  	           	var planIxe = document.getElementById("forma:affiliation_comercialplanIxe").value;
            	  	//paqueteTPVNominaRecall();//para paquete tpvnomina (empleados, penalizacion y checkbox)
	          		checkOptionMonthlyRateRecall();//si selecciono sustituir la renta en base a la divisa se habilita o deshabilitan
	          		//validationTPV_Payroll();//en base al plan se habilita o deshabilita tpv nomina
	          		//Inhabilitar Fianza
	          		var seleccion= valorSeleccionGarantiaLiquida();
	          		if(seleccion==1){
	          			inicializarFianza();//revisa si requiere o no fianza en base al producto, giro y teclado abierto. ->onchangefianza
	          		}
	          		inicializarSolucion(); //-> inicializarAlianza ->inicializarPaquete & inicializarIntegracion
	          		onChangeDivisa();
	          		inicializarModalidad();
	          		inicializarCuotaAfiliacion();//para cuota de afiliacion en base al producto
	          		inicializarCashback();
	          		inicializarCaptacion();
	              	inicializarPlanes(red, plan, planIxe);//en base a la red se muestra el combo de planes banorte o ixe
	              	inicializarEquipos();
	              	inicializarTiempoAire();
	              	cargarXMLGiros();
	              	inicializarBuroExterno();
	              	inicializarServiciosEspeciales();
	              	onchangeRentBy();
	              	var comboPaquete = document.getElementById("forma:affiliation_chargeType");
	              	if(comboPaquete.selectedIndex != 0)
	              		onchangePaquete();
	              	
	              	inicializarCybersource();
	              	inicializarGarantiaLiquida();//F-83585 Garantia Liquida
	              	inicializarMujerPyME();
              }
              
              
              function activateAlliance(){
            	  var descProducto = document.getElementById("forma:affiliation_productdesc").value;
            	  if(descProducto == "Interredes" || descProducto == "Terminal punto de venta"){
            		  
            	  }
            	  
            	  
              }
              
              
              /**
               * Funcion llamada antes de ir a calcular la tabla de comisiones (validaciones)
               * @returns {Boolean}
               */
              function activatePlan(){
            	  var planBanorte = document.getElementById("forma:affiliation_comercialplan");//plan o esquema comercial
            	  var planIxe = document.getElementById("forma:affiliation_comercialplanIxe");//plan o esquema comercial
            	  var red = valorRed();
            	  var montoSustituirPesos = document.getElementById("forma:replaceAmountratemn");
            	  var montoSustituirDolares = document.getElementById("forma:replaceAmountratedlls");
            	  var giro = document.getElementById("forma:client_categorycode").value;//giro
            	  var seleccionGarantia = valorSeleccionGarantiaLiquida();//GARANTIA LIQUIDA
            	  
            	  //se activan los planes para que se envie el valor
            	  planBanorte.disabled =false;
            	  planIxe.disabled =false;
            	  
            	  var alliance = document.getElementById("forma:affiliation_alliance").value;//alianza
            	  var chargeType = document.getElementById("forma:affiliation_chargeType").value;//paquete
            	  var producto = document.getElementById("forma:affiliation_productdesc").value;
            	  
            	  //valida que haya una red seleccionada
            	  if(red==null){
            		alert('Por favor selecciona la RED: Banorte o Ixe y elige un plan o esquema comercial');
            		return false;
            	  }           
            	  //valida que haya un giro seleccionado
            	  if(giro.trim()==""){
            		  alert('Por favor selecciona No. de Giro y Nombre');
            		  document.getElementById("forma:client_categorycode").focus();
            		  return false;
            	  }
            	  //valida que haya un plan seleccionado
            	  if(red==0 && planBanorte.value==0){
            		  alert('Por favor selecciona un plan o esquema comercial Banorte');
            		  planBanorte.focus();
            		  return false;
            	  }else if(red==1 && planIxe.value==0){
            		  alert('Por favor selecciona un plan o esquema comercial Ixe');
            		  planIxe.focus();
            		  return false;
            	  }
            	
            	//valida seleccion de fianza o garantia liquida
              	/*  if(seleccionGarantia==""||seleccionGarantia==null){
              		alert('Por favor selecciona fianza o garantia liquida');
              		return false;
              	  }*/

            	  //valida que haya un paquete seleccionado si se eligio alianza cybersource
            	  if(alliance == "Tradicional" || alliance == "Hosted" || alliance == "Call Center" || (alliance == "Agregador" && producto == "Comercio Electronico")){
            		  var comboIntegracion = document.getElementById("forma:affiliation_integration");
            		  
            		  if(comboIntegracion.value == "Cybersource Enterprise Revision Manual" || 
                 			 comboIntegracion.value == "Cybersource Enterprise Autenticación Selectiva" ||
                 			 comboIntegracion.value == "Cybersource Direct" ||
                 			 comboIntegracion.value == "Cybersource Hosted" ||
                 			 comboIntegracion.value == "Cybersource Call Center" ||
                 			 comboIntegracion.value == "Cybersource Enterprise Call Center"){
            			  if(chargeType == 0){
            				  alert('Para la Alianza Cybersource, debe seleccionar un Paquete');
            				  document.getElementById("forma:affiliation_chargeType").focus();
            				  return false;
            			  }
            		  }
            	  }
            	  if(!validateCybersource()){
            		  return false;
            	  }
            	  
            	  //valida que no se haya seleccionado un paquete para giros especiales
            	 /* var girosEspeciales = new Array("005422","008398","008211","008241","008299","000742","008011","008021","008031",
            			  						  "008041","008042","008049","008351","005072","005074","005251","005533","007230");*/
             	  var paquetes  	  = document.getElementById("forma:affiliation_chargeType");
            	  var producto		  = document.getElementById("forma:affiliation_productdesc").value;
            	  var plan			  = document.getElementById("forma:affiliation_comercialplan").value;
            	 /* var giro 			  = document.getElementById("forma:client_categorycode").value;
            	  giro 				  = giro.substr(0,6);*/
            	  var requierePaquete = true;
            	  //alert(producto + plan + giro + requierePaquete);
            	 /* var isGiroEspecial  = false;
              
            	  //se identifica si es un giro especial
            	  for (var i = 0; i < girosEspeciales.length; i++) {
        			  if(giro==girosEspeciales[i]){
        				  isGiroEspecial = true;
        				  break;
        			  }
        		  }    */ 	
	            // se valida el producto y se indica si se requiere o no
				// paquete con base al giro y el plan
				if ("Terminal Personal Banorte (Mpos)" != producto) {
					requierePaquete = false;
				}else if ("16" == plan || "17" == plan || "18" == plan || "19" == plan) {
					requierePaquete = true;
				} else if ("15" == plan || "5" == plan || "6" == plan){ 
					requierePaquete = false;             
				 }
            	  
            	  if(requierePaquete && chargeType == 0){
            		  alert("Debe selecionar un paquete");
            		  document.getElementById("forma:affiliation_chargeType").focus();
            		  return false;
            	  }
            	  montoSustituirPesos.disabled=false;
            	  montoSustituirDolares.disabled=false;
            	  calcularGarantiaLiquida();//garantia liquida
            	  //inicializarEquipos();//borrar
            	  return true;
              }
                            
              /**
               * Funcion que se ejecuta al oprimir la liga de ENVIAR para continuar a la siguiente pantalla
               * @returns {Boolean} regresa valor si es valido o no avanzar a la siguiente pantalla
               */
              function validateSend(){
            	 var recalculate = document.getElementById("forma:recalculateCommisionTable");
            	 var plan = document.getElementById("forma:affiliation_comercialplan");
            	 var planIxe = document.getElementById("forma:affiliation_comercialplanIxe");
            	 var planSeleccionado = 0;
            	 var red=valorRed();
            	 
            	 var seleccion= valorSeleccionGarantiaLiquida();
            	 //var exentar = document.getElementById("forma:exentDep");//exenta fianza checkbox
            	 var auth = document.getElementById("forma:affiliation_officerdepositexent");//quien autoriza exentar
            	 var company = document.getElementById("forma:affiliation_depositcompany");//compa�ia fianza
            	 var amount = document.getElementById("forma:affiliation_depositamount");//monto fianza
            	 var dueDate = document.getElementById("forma:affiliation_duedate");//fecha expiracion
            	 
            	 var ventasEstimadasMensuales = document.getElementById("forma:aff_ventasEstimadasMensuales");//Ventas Estimadas Mensuales
            	 var montoEstimadoDeTransaccion = document.getElementById("forma:aff_montoEstimadoDeTransaccion");//Monto estimado transaccion
            	 var fianzaOculta = document.getElementById("forma:fianzaOculta");
            	 
            	 //para validar si es excepcion o disminucion
            	 var porcentajeGL=document.getElementById("forma:aff_porcentajeGL");
            	 
            	 var montoInicial=document.getElementById("forma:aff_montoInicial");
      			var auxMontoInicial=document.getElementById("forma:aff_auxMontoInicial");
      			var montoPromDiario = document.getElementById("forma:aff_montoPromDiario");
      			var auxMontoPromDiario=document.getElementById("forma:aff_auxMontoPromDiario");
      			var porcentajeGL=document.getElementById("forma:aff_porcentajeGL");
      			var excepcionPorceGL=document.getElementById("forma:aff_excepcionPorceGL");
      			var montoGL = document.getElementById("forma:aff_montoGL");
      			var auxMontoGL=document.getElementById("forma:aff_auxMontoGL");
      			var porcentajeInicialGL=document.getElementById("forma:aff_porcentajeInicialGL");
      			var auxPorcentajeInicialGL=document.getElementById("forma:aff_auxPorcentajeInicialGL");
      			var montoInicialGL=document.getElementById("forma:aff_montoInicialGL");
      			var auxMontoInicialGL=document.getElementById("forma:aff_auxMontoInicialGL");
      			var porcentajeRestanteGL=document.getElementById("forma:aff_porcentajeRestanteGL");
      			var auxPorcentajeRestanteGL=document.getElementById("forma:aff_auxPorcentajeRestanteGL");
      			var montoRestanteGL=document.getElementById("forma:aff_montoRestanteGL");
      			var auxMontoRestanteGL=document.getElementById("forma:aff_auxMontoRestanteGL");
      			var promMontoDiarioGL=document.getElementById("forma:aff_promMontoDiarioGL");
      			var auxPromMontoDiarioGL=document.getElementById("forma:aff_auxPromMontoDiarioGL");
      			var porcentajeDiarioGL=document.getElementById("forma:aff_porcentajeDiarioGL");
      			var auxPorcentajeDiarioGL=document.getElementById("forma:aff_auxPorcentajeDiarioGL");
      			var diasAproxGL=document.getElementById("forma:aff_diasAproxGL");
      			var auxDiasAproxGL=document.getElementById("forma:aff_auxDiasAproxGL");
      			var optionPorcentajeVentasDiarias=document.getElementById("forma:optionPorcentajeVentasDiarias");
      			var auxOptionPorcentajeVentasDiarias=document.getElementById("forma:auxOptionPorcentajeVentasDiarias");
      			var optionMontoFijoDiario=document.getElementById("forma:optionMontoFijoDiario");
      			var auxOptionMontoFijoDiario=document.getElementById("forma:auxOptionMontoFijoDiario");

      			var glOriginal=document.getElementById("forma:aff_glOriginal");
      			var auxGlOriginal=document.getElementById("forma:aff_auxGlOriginal");
      			
      			var comentariosDisminucionExcepcionGL=document.getElementById("forma:aff_comentariosDisminucionExcepcionGL");
      			
      			//Esto campos sustituyen a comentariosDisminucionExcepcionGL
      			var exencionConvenienciaComercialVIP=document.getElementById("forma:exencionConvenienciaComercialVIP");
      			var exencionOtros=document.getElementById("forma:exencionOtros");
      			var exencionJustificacion=document.getElementById("forma:exencionJustificacion");
      			//conocimiento del cliente
      			var solvenciaEconimicaSi=document.getElementById("forma:solvenciaEconimicaSi");
      			var solvenciaEconimicaNo=document.getElementById("forma:solvenciaEconimicaNo");
      			var visitaOcularRecienteSi=document.getElementById("forma:visitaOcularRecienteSi");
      			var visitaOcularRecienteNo=document.getElementById("forma:visitaOcularRecienteNo");
      			var riesgoReputacionalOperacionalSi=document.getElementById("forma:riesgoReputacionalOperacionalSi");
      			var riesgoReputacionalOperacionalNo=document.getElementById("forma:riesgoReputacionalOperacionalNo");
      			var descBienServicioOfrece=document.getElementById("forma:descBienServicioOfrece");
      			var territorioNacionalSi=document.getElementById("forma:territorioNacionalSi");
      			var territorioNacionalNo=document.getElementById("forma:territorioNacionalNo");
      			var territorioNacionalEspecificacion=document.getElementById("forma:territorioNacionalEspecificacion");
      			var enNombreDeUnTerceroSi=document.getElementById("forma:enNombreDeUnTerceroSi");
      			var enNombreDeUnTerceroNo=document.getElementById("forma:enNombreDeUnTerceroNo");
      			var enNombreDeUnTerceroEspecificacion=document.getElementById("forma:enNombreDeUnTerceroEspecificacion");
      			var antiguedadAnio=document.getElementById("forma:antiguedadAnio");
      			var antiguedadMeses=document.getElementById("forma:antiguedadMeses");
      			 
            	 //asigna el valor del plan seleccionado
            	 if(red==0){
            		 planSeleccionado = plan.value;
            	 }else{
            		 planSeleccionado = planIxe.value;
            	 }
            	 //valida que no tenga que recalcular la tabla de comisiones
            	 if(recalculate.value=='true'){
            		 if(planSeleccionado==5||planSeleccionado==6){
            			 recalcularTabla(false);
//            			 return true; 
            		 }else{
            			 alert('Es necesario recalcular las comisiones antes de continuar');
                		 return false;	
            		 }
            	 }
            	 
            	  if(validacionMicros()==false){
            		return false;  
            	  }
            	  if(!validateCybersource()){
            		  return false;
            	  }
            	  
            	  //Version 2
            	  //alert("valor de fianza oculta: "+fianzaOculta.value);
            	  if(seleccion==1){//garantia liquida. se salta validacion de deposito de fianza
	            	  
            		  if(fianzaOculta.value=="true"){
               			
               			
               			
               			montoInicial.value="";
               			auxMontoInicial.value=montoInicial.value;
               			montoPromDiario.value="";
               			auxMontoPromDiario.value=montoPromDiario.value;
               			porcentajeGL.value="";
               			excepcionPorceGL.value="";
               			montoGL.value="";
               			auxMontoGL.value=montoGL.value;
               			porcentajeInicialGL.value="";
               			auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
               			montoInicialGL.value="";
               			auxMontoInicialGL.value=montoInicialGL.value;
               			porcentajeRestanteGL.value="";
               			auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
               			montoRestanteGL.value="";
               			auxMontoRestanteGL.value=montoRestanteGL.value;
               			promMontoDiarioGL.value="";
               			auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
               			porcentajeDiarioGL.value="";
               			auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
               			diasAproxGL.value="";
               			auxDiasAproxGL.value=diasAproxGL.value;
               			glOriginal.value="";
               			auxGlOriginal.value=glOriginal.value;
               			
               			comentariosDisminucionExcepcionGL.value="";
               			
               			//Esto campos sustituyen a comentariosDisminucionExcepcionGL
               			//exencionConvenienciaComercialVIP.value="";
               			exencionConvenienciaComercialVIP.checked = false;
               			exencionOtros.value="";
               			exencionJustificacion.value="";
               			//conocimiento del cliente
               			solvenciaEconimicaSi.checked = false;
               			solvenciaEconimicaNo.checked = false;
               			visitaOcularRecienteSi.checked = false;
               			visitaOcularRecienteNo.checked = false;
               			riesgoReputacionalOperacionalSi.checked = false;
               			riesgoReputacionalOperacionalNo.checked = false;
               			descBienServicioOfrece.value="";
               			territorioNacionalSi.checked = false;
               			territorioNacionalNo.checked = false;
               			territorioNacionalEspecificacion.value="";
               			enNombreDeUnTerceroSi.checked = false;
               			enNombreDeUnTerceroNo.checked = false;
               			enNombreDeUnTerceroEspecificacion.value="";

               			antiguedadAnio.value="";
               			antiguedadMeses.value="";

               			optionPorcentajeVentasDiarias.value="";
               			auxOptionPorcentajeVentasDiarias.value=optionPorcentajeVentasDiarias.value;
               			optionMontoFijoDiario.value="";
               			auxOptionMontoFijoDiario.value=optionMontoFijoDiario.value;
               		 }
            		  
            		  if(validateDeposit()==false){
	            		  return false;
	            	  }
	            	  
	            	  
            	  }else{
            		 auth.disabled=true; auth.value="";
             		 company.disabled=true; company.value="";
             		 amount.disabled=true; amount.value="";
             		 dueDate.disabled=true; dueDate.value="";
             		 if(fianzaOculta.value=="true"){
             			
             			
             			
             			montoInicial.value="";
             			auxMontoInicial.value=montoInicial.value;
             			montoPromDiario.value="";
             			auxMontoPromDiario.value=montoPromDiario.value;
             			porcentajeGL.value="";
             			excepcionPorceGL.value="";
             			montoGL.value="";
             			auxMontoGL.value=montoGL.value;
             			porcentajeInicialGL.value="";
             			auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
             			montoInicialGL.value="";
             			auxMontoInicialGL.value=montoInicialGL.value;
             			porcentajeRestanteGL.value="";
             			auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
             			montoRestanteGL.value="";
             			auxMontoRestanteGL.value=montoRestanteGL.value;
             			promMontoDiarioGL.value="";
             			auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
             			porcentajeDiarioGL.value="";
             			auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
             			diasAproxGL.value="";
             			auxDiasAproxGL.value=diasAproxGL.value;
             			glOriginal.value="";
             			auxGlOriginal.value=glOriginal.value;
             			
             			comentariosDisminucionExcepcionGL.value="";
             			
             			//Esto campos sustituyen a comentariosDisminucionExcepcionGL
             			exencionConvenienciaComercialVIP.checked = false;
               			exencionOtros.value="";
               			exencionJustificacion.value="";
               			//conocimiento del cliente
               			solvenciaEconimicaSi.checked = false;
               			solvenciaEconimicaNo.checked = false;
               			visitaOcularRecienteSi.checked = false;
               			visitaOcularRecienteNo.checked = false;
               			riesgoReputacionalOperacionalSi.checked = false;
               			riesgoReputacionalOperacionalNo.checked = false;
               			descBienServicioOfrece.value="";
               			territorioNacionalSi.checked = false;
               			territorioNacionalNo.checked = false;
               			territorioNacionalEspecificacion.value="";
               			enNombreDeUnTerceroSi.checked = false;
               			enNombreDeUnTerceroNo.checked = false;
               			enNombreDeUnTerceroEspecificacion.value="";
               			antiguedadAnio.value="";
               			antiguedadMeses.value="";

             			optionPorcentajeVentasDiarias.value="";
             			auxOptionPorcentajeVentasDiarias.value=optionPorcentajeVentasDiarias.value;
             			optionMontoFijoDiario.value="";
             			auxOptionMontoFijoDiario.value=optionMontoFijoDiario.value;
             		 }
             		
            	  }
            	  
            	  //Garantia Liquida: validacion de campo ventas Estimadas Mensuales
            	  if(ventasEstimadasMensuales.value=="" || ventasEstimadasMensuales.value==null){
            		  //if(confirm("Favor de ingresar ventas estimadas mensuales")) {
            		  	  alert("Favor de ingresar ventas estimadas mensuales");
            			  ventasEstimadasMensuales.focus();
            			  return false;
			    		//}
            	  }
            	  
            	//Garantia Liquida: validacion de campo ventas Estimadas Mensuales
            	  if(montoEstimadoDeTransaccion.value=="" || montoEstimadoDeTransaccion.value==null){
            		  //if(confirm("Favor de ingresar monto estimado de transacción")) {
            		  	  alert("Favor de ingresar monto estimado de transacción");
            			  montoEstimadoDeTransaccion.focus();
            			  return false;
			    		//}
            	  }
            	  
/*            	//Garantia Liquida: validacion de campo comentarios Disminucion Excepcion GL
            	  if(porcentajeGL.value=="excepcion" || porcentajeGL.value=="disminucion"){
            		  var comentariosDisminucionExcepcionGL=document.getElementById("forma:aff_comentariosDisminucionExcepcionGL");
            		  if(comentariosDisminucionExcepcionGL.value.length==0){
            			  	  alert("Favor de ingresar comentarios de motivos disminución o exención de garantía líquida");
            				  comentariosDisminucionExcepcionGL.focus();
                			  return false;
    			    		
            		  }
            	  }
*/            	  
            	//Garantia Liquida: validacion de campo comentarios Disminucion Excepcion GL
            	  if(porcentajeGL.value=="excepcion" || porcentajeGL.value=="disminucion" || porcentajeGL.value=="5"){
            		  //alert('FALTAN VALIDACIONES DE COMENTARIOS EXENCION validacion de comentarios exencion');
            		  if(exencionConvenienciaComercialVIP.checked==false){
	            		  if(exencionOtros.value=="" || exencionOtros.value==null){
	                		  	  alert("Favor de ingresar 'otros' en motivos disminución o Exención de Garantía Líquida");
	                		  	  exencionOtros.focus();
	                			  return false;
	                	  }
            		  }
            		  if(exencionJustificacion.value=="" || exencionJustificacion.value==null){
            		  	  alert("Favor de proporcionar detalle de la justificación");
            		  	  exencionJustificacion.focus();
            			  return false;
            		  }
            		  if(solvenciaEconimicaSi.checked==false && solvenciaEconimicaNo.checked==false){
            			  alert("Favor de seleccionar si el cliente cuenta con solvencia económica");
            			  solvenciaEconimicaSi.focus();
            			  return false;
            		  }
            		  if(visitaOcularRecienteSi.checked==false && visitaOcularRecienteNo.checked==false){
            			  alert("Favor de seleccionar si se realizó visita ocular reciente");
            			  visitaOcularRecienteSi.focus();
            			  return false;
            		  }
            		  if(riesgoReputacionalOperacionalSi.checked==false && riesgoReputacionalOperacionalNo.checked==false){
            			  alert("Favor de seleccionar si el cliente ofrece bienes y servicios legales");
            			  riesgoReputacionalOperacionalSi.focus();
            			  return false;
            		  }
            		  if(descBienServicioOfrece.value=="" || descBienServicioOfrece.value==null){
            		  	  alert("Favor describir que tipo(s) de producto(s) o servicio(s) ofrece");
            		  	  descBienServicioOfrece.focus();
            			  return false;
            		  }
            		  if(territorioNacionalSi.checked==false && territorioNacionalNo.checked==false){
            			  alert("Favor de seleccionar si el servicio se ofrece dentro del territorio nacional");
            			  territorioNacionalSi.focus();
            			  return false;
            		  }
            		  if(territorioNacionalNo.checked==true){
            			  if(territorioNacionalEspecificacion.value=="" || territorioNacionalEspecificacion.value==null){
                		  	  alert("Favor de proporcionar especificación del Territorio Nacional");
                		  	  territorioNacionalEspecificacion.focus();
                			  return false;
                		  }
            		  }
            		  if(territorioNacionalSi.checked==true){
            			  territorioNacionalEspecificacion.value="";
            		  }
            		  if(enNombreDeUnTerceroSi.checked==false && enNombreDeUnTerceroNo.checked==false){
            			  alert("Favor de seleccionar si el servicio brindado se ofrece en nombre de un tercero");
            			  enNombreDeUnTerceroNo.focus();
            			  return false;
            		  }
            		  if(enNombreDeUnTerceroSi.checked==true){
            			  if(enNombreDeUnTerceroEspecificacion.value=="" || enNombreDeUnTerceroEspecificacion.value==null){
                		  	  alert("Favor de proporcionar especificación del servicio brindado se ofrece en nombre de un tercero");
                		  	  enNombreDeUnTerceroEspecificacion.focus();
                			  return false;
                		  }
            		  }
            		  if(enNombreDeUnTerceroNo.checked==true){
            			  enNombreDeUnTerceroEspecificacion.value="";
            		  }
            		  if(exencionJustificacion.value=="" || exencionJustificacion.value==null){
            		  	  alert("Favor de proporcionar detalle de la justificación");
            		  	  exencionJustificacion.focus();
            			  return false;
            		  }
            		  if(antiguedadAnio.value=="" || antiguedadAnio.value==null){
            		  	  alert("Favor de proporcionar años de antigüedad con el banco");
            		  	  antiguedadAnio.focus();
            			  return false;
            		  }
            		  if(antiguedadMeses.value=="" || antiguedadMeses.value==null){
            		  	  alert("Favor de proporcionar meses de antigüedad con el banco");
            		  	  antiguedadMeses.focus();
            			  return false;
            		  }
            	  }
            			  
            	  if(document.getElementById("forma:affiliation_tiempoaire").checked==true){
            		  if(validateTA()==false){
            			  return false;
            		  }
            	  }
            	  if(!validateMpos()){
            		  return false;
            	  }
            	  
            	  if("false"==validarDescripcionGiro()){
            		  return false;
            	  }
            	  
            	  document.getElementById("forma:affiliation_havedepositcompany").disabled=false;
            	  plan.disabled=false;
            	  planIxe.disabled=false;
            	  return true;
              }
              /**
               * Funcion que valida que las comisiones de las compa�ias telefonicas esten dentro de los margenes establecidos
               * 0% y 5%
               * @returns {Boolean} si cumple o no con la condicion
               */
              function validateTA(){
            	var valorTelcel = document.getElementById("forma:affiliation_telcel").value;
            	var valorMovistar = document.getElementById("forma:affiliation_movistar").value;
            	var valorIusacell = document.getElementById("forma:affiliation_iusacell").value;
            	var valorNextel = document.getElementById("forma:affiliation_nextel").value;
            	if((valorTelcel>5 || valorTelcel<0)||
            			(valorMovistar>5 || valorMovistar<0)||
            			(valorIusacell>5 || valorIusacell<0)||
            			(valorNextel>5 || valorNextel<0)
            			)
            	{
            		alert("Las Comisiones de Tiempo Aire deben ser entre 0% y 5%");
            		document.getElementById("forma:affiliation_telcel").focus();
            		return false;
            	}
            	return true;
              }
              
              
              function validateDeposit(){
            	 var comboRequiereFianza = document.getElementById("forma:affiliation_havedepositcompany"); //requiere fianza combo
             	 var checkboxExentar = document.getElementById("forma:exentDep");//exenta fianza checkbox
             	 var campoExentaFianza = document.getElementById("forma:affiliation_officerdepositexent");
             	 var campoCiaFianza = document.getElementById("forma:affiliation_depositcompany");
             	 var campoMontoFianza = document.getElementById("forma:affiliation_depositamount");
             	 var campoFechaExpFianza = document.getElementById("forma:affiliation_duedate");
             	 var seleccion= valorSeleccionGarantiaLiquida();//garantia liquida
             	 if(seleccion==1){
	         		 if(comboRequiereFianza.value==1){
	             		 if(checkboxExentar.checked==true){
	             			if(campoExentaFianza.value==""){
	             				alert('Escriba el nombre de quien autoriza exentar la fianza');
	             				return false;
	             			}
	             		 }else{
	             			//if(seleccion!=0){
	             			 if((campoCiaFianza.value=="")||
	             					(campoMontoFianza.value=="")||
	             					(campoFechaExpFianza.value=="")) //tenía valie
	             					{
	    	             				alert('Escriba los datos completos de la fianza');
	    	             				return false;
	             					}
	             			}
	             		}
	             }
	             return true;
              }
              
              function validateMpos(){
            	  if(document.getElementById("forma:affiliation_productdesc").value != 'Terminal Personal Banorte (Mpos)')
            		  return true;
            	  else{
            		  if((document.getElementById("forma:mposMonthlyrate").value == "" || document.getElementById("forma:mposMonthlyrate").value == "0") 
            				  && (document.getElementById("forma:mposEquipment").value == "" || document.getElementById("forma:mposEquipment").value == "0")){
            			  alert('Es necesario selecionar al menos un equipo Mpos');
            			  return false;
            		  }else
            			  return true;
            	  }
              }
              
              /**
               * agregamos la red a los comentarios
               */
              function agregarRed(){
            	  var red=valorRed();
            	  var comentarios = document.getElementById("forma:comments");
            	  var banco;
            	  if (red==0){
            		  banco=" - Red: Banorte";
            	  }else if(red==1){
            		  banco=" - Red: Ixe";
            	  }
            	  comentarios.value = comentarios.value+banco;
//            	  alert(comentarios.value);
              }
              
              function  validacionMicros(){
            	  var alliance = document.getElementById("forma:affiliation_alliance").value;
            	  var chargeType = document.getElementById("forma:affiliation_chargeType").value;
            	  var sel_prod = document.getElementById("forma:affiliation_productdesc").value;
            	  var plan = document.getElementById("forma:affiliation_comercialplan");
             	  var planIxe = document.getElementById("forma:affiliation_comercialplanIxe");
            	  var red = valorRed();
            	  var tpvPinpadPropia = document.getElementById("forma:ownTpvPinpad");
            	  var pinpad = document.getElementById("forma:affiliation_quantpinpad").value;
            	  
            	  if(!activatePlan()){ //valida que haya red, plan y paquete seleccionado en caso de alianza cybersource
            	  		return false;
            	  }
            	  //valida que haya una red seleccionada
            	  if(red==null){
            	  		alert('Debe seleccionar una red');
            	  		return false;
            	  }
            	  //valida que haya un plan seleccionado
            	  if(plan.value==0 && planIxe.value==0){
          	  		alert('Por favor selecciona un plan');
          	  		return false;
            	  }
            	  //valida que haya paquete seleccionado si la alianza es micros y al menos un equipo pinpad si no es propio
            	  if(alliance == "Micros"){
            		  if(chargeType == 0){
            			  alert('Debe seleccionar un Paquete');
            			  document.getElementById("forma:affiliation_chargeType").focus();
            			  return false;
            		  }
            		  var tpvInternet = document.getElementById("forma:tpvInternet").value;
            		  if(tpvInternet > 0){//TPV con conexi�n a internet (fija)
            			  //unblockEmployeePenalty();
            			  return true;
            		  }else if(tpvInternet==0 && tpvPinpadPropia.checked==false){
            			  if(sel_prod=="Interredes"){
            				  if(pinpad==0){
            					  alert('Es necesario selecionar al menos un equipo TPV con conexion a internet (fija)');
                    			  document.getElementById("forma:tpvInternet").focus();
                    			  return false; 
            				  }
            			  }else{
            				  alert('Es necesario selecionar al menos un equipo TPV con conexion a internet (fija)');
                			  document.getElementById("forma:tpvInternet").focus();
                			  return false;  
            			  }
            		  }
            		  alert('La funcionalidad del Plan Micros solo se instalara en los equipos PinPad');
            		  //unblockEmployeePenalty();
            		  return true;
            	  }else if(alliance == "Netpay"){ //valida que haya un paquete seleccionado si la alianza es netpay
            		  if(chargeType == 0){
            			  alert('Debe seleccionar un Paquete');
            			  document.getElementById("forma:affiliation_chargeType").focus();
            			  return false;
            		  }
//            		  if(sel_prod!="Comercio Electronico"){ //y que tenga un equipo pinpad en caso de que no haya pinpad propia
	            		  
//	            		  if(pinpad > 0){
//	            			  unblockEmployeePenalty();
//	            			  //return true;
//	            		  }else 
        			  if(sel_prod=="Terminal punto de venta"){
	        			  if(pinpad==0 && tpvPinpadPropia.cheched==false){
	        				  alert("Es necesario selecionar al menos un equipo Pinpad");
	            			  document.getElementById("forma:affiliation_quantpinpad").focus();
	            			  return false;
	        			  }
            		  }
            	  }
            	  
            	 if(validarEquipos()==false){
            		 return false;
            	 }
            	 
            	 //unblockEmployeePenalty();
            	 return true;
              }
              
              
              /**
               * Funcion que valida la integracion y renta de cybersource
               */
              function validateCybersource(){
            	  	var comboProducto = document.getElementById("forma:affiliation_productdesc");
            	  	var comboIntegracion = document.getElementById("forma:affiliation_integration");
             	 	var comboAlianza = document.getElementById("forma:affiliation_alliance");
             	 	var comboRentaDolar = document.getElementById("forma:affiliation_rentaDolar");
             	 	var comboRentBy = document.getElementById("forma:rentBy");
             	 	var groupNo = document.getElementById("forma:groupNo");
             	 	
             	 	if(comboAlianza.value == "Tradicional" || comboAlianza.value == "Hosted" || comboAlianza.value == "Call Center" || (comboAlianza.value == "Agregador" && comboProducto.value == "Comercio Electronico")){
             	 		if(comboIntegracion.value==0){
             	 			alert('Para Alianza Cybersource, es necesario elegir la integracion');
             	 			comboIntegracion.focus();
             	 			return false;
             	 		}
             	 		if(comboIntegracion.value=="Cybersource Enterprise Revision Manual"
             	 			|| comboIntegracion.value=="Cybersource Enterprise Autenticación Selectiva"
             	 			|| comboIntegracion.value=="Cybersource Enterprise Call Center"){
             	 			if(comboRentaDolar.value==0){
             	 				alert('Seleccione el valor de Renta en Dolares para la integracion seleccionada');
                 	 			comboIntegracion.focus();
                 	 			return false;
             	 			}
             	 		}
             	 		
             	 		
             	 		if(comboIntegracion.value == "Cybersource Enterprise Revision Manual" || 
             	 				comboIntegracion.value == "Cybersource Enterprise Autenticación Selectiva" ||
             	 				comboIntegracion.value == "Cybersource Direct" ||
             	 				comboIntegracion.value == "Cybersource Hosted" ||
             	 				comboIntegracion.value == "Cybersource Call Center" ||
             	 				comboIntegracion.value == "Cybersource Enterprise Call Center"){
             	 			if(comboRentBy.value == 0){
             	 				alert('Seleccione el valor de Renta Por');
             	 				comboRentBy.focus();
             	 				return false;
             	 			}else if(comboRentBy.value == "Grupo"){
             	 				if(groupNo.value == "undefined" || groupNo.value == ""){
             	 					alert('Debe definir el número de grupo al que pertenece');
             	 					groupNo.focus();
             	 					return false;
             	 				}
             	 			}
             	 		}
             	 	}
             	 	
             	 	return true;
              }
              
              
              /*function unblockEmployeePenalty(){
            	  document.getElementById("forma:tpv_number_employee").disabled =false;
         		  document.getElementById("forma:tpv_penalty").disabled =false;
              }*/
              
              
              /**
               * Funcion para validar el producto y los equipos elegidos
               * @returns {Boolean}
               */
              function validarEquipos(){
            	  var producto = document.getElementById("forma:affiliation_productdesc");
            	  var equipos = new Array(
              			"forma:tpvTel",
              			"forma:tpvMovil",
              			"forma:tpvInternet",
              			"forma:tpvInternetTel",
              			"forma:affiliation_quantpinpad",
              			"forma:tpvBlueTel",
              			"forma:tpvGprs",
              			"forma:tpvBlueInternet",
              			"forma:wifiMovil",
              			"forma:wifiTel",
              			"forma:affiliation_quantmanual"
              			); 
            	  var totalEquipos = parseInt(0);
            	  var tpvPinpadPropia = document.getElementById("forma:ownTpvPinpad");
            	  var numEquipos = parseInt(0);
            	  var modalidad = document.getElementById("forma:affiliation_modality");
            	  var comboAlianza = document.getElementById("forma:affiliation_alliance");
            	  var equipoGprs = document.getElementById("forma:tpvGprs");
            	  var equipoPinpad = document.getElementById("forma:affiliation_quantpinpad");
            	  	for (var i=0; i<equipos.length; i++){
            	  		numEquipos = parseInt(document.getElementById(equipos[i]).value);
            	  		totalEquipos = parseInt(totalEquipos) + parseInt(numEquipos);
  					}
            	  
            	  	if(producto.value=="Terminal punto de venta"){
            	  		if(comboAlianza.value=="Netpay"){
            	  			if(equipoGprs.value==0){
            	  				if(tpvPinpadPropia.checked==false){
            	  					if(modalidad.value!="Multicomercio/Servicomercio"){
            	  						alert('Favor de seleccionar al menos una TPV con conexion celular (GPRS)');
                    	  				equipoGprs.focus();
                    	  				return false;
            	  					}
            	  				}
            	  			}
            	  		}
            	  	}
            	  	
            	  	if(producto.value=="Interredes"){
            	  		if(modalidad.value!="Multicomercio/Servicomercio"){
            	  			if(equipoPinpad.value==0){
            	  				if(tpvPinpadPropia.checked==false){
            	  					alert('Favor de seleccionar al menos un equipo Pinpad');
            	  					return false;
            	  				}
            	  			}
	            	  	}
	            	}
            	  	
            	  return true;
              }
              
              
              
          // FUNCIONES NUEVAS
              function fillComboAllianceFull(selIndex){
            	  var alliance = document.getElementById("forma:affiliation_alliance");
            	  var alliance_hidd = document.getElementById("forma:affiliation_alliance_hidd");
            	  var valAlliance = alliance.options[selIndex].value;
//            	  var sizeCombo = alliance.options.length;
            	  
            	  //alliance.options.length = 0;
            	  alliance.options.length = 1;
//            	  alert(selIndex);
            	  /*var option0 = document.createElement('option');
            	    alliance.options.add(option0, 0);
            	    alliance.options[0].value = "Ninguna";
            	    alliance.options[0].innerText = "Ninguna";
            	    */
            	    var option1 = document.createElement('option');
            	    alliance.options.add(option1, 1);
            	    alliance.options[1].value = "Micros";
            	    alliance.options[1].innerText = "Micros";
            	    alliance.options[1].innerHTML = "Micros";
            	    
            	    var option2 = document.createElement('option');
            	    alliance.options.add(option2, 2);
            	    alliance.options[2].value = "Netpay";
            	    alliance.options[2].innerText = "Netpay (Caja Total Banorte)";
            	    alliance.options[2].innerHTML = "Netpay (Caja Total Banorte)";
            	    
            	    var option3 = document.createElement('option');
            	    alliance.options.add(option3, 3);
            	    alliance.options[3].value = "Agregador";
            	    alliance.options[3].innerText = "Agregador";
            	    alliance.options[3].innerHTML = "Agregador";
            	    
            	    for(var i=0; i<alliance.options.length ;i++){
               	    	if(valAlliance == alliance.options[i].value){
               	    		alliance.options[i].selected=true;
               	    	}
               	    }
            	    
            	    alliance_hidd.value = alliance.value;
             	    
    
              }
              
              
              
              
              function fillComboAllianceNetPay(selIndex){
            	  var alliance = document.getElementById("forma:affiliation_alliance");
            	  var alliance_hidd = document.getElementById("forma:affiliation_alliance_hidd");
            	  var valAlliance = alliance.options[selIndex].value;
             	 
            	  //  alliance.options.length = 0;
            	  alliance.options.length = 1;
            	  
            	  /*  var option0 = document.createElement('option');
            	    alliance.options.add(option0, 0);
            	    alliance.options[0].value = "Ninguna";
            	    alliance.options[0].innerText = "Ninguna";
            	   */ 
            	    var option1 = document.createElement('option');
            	    alliance.options.add(option1, 1);
            	    alliance.options[1].value = "Netpay";
            	    alliance.options[1].innerText = "Netpay (Caja Total Banorte)";
            	    alliance.options[1].innerHTML = "Netpay (Caja Total Banorte)";
            	    
            	    var option2 = document.createElement('option');
            	    alliance.options.add(option2, 2);
            	    alliance.options[2].value = "Agregador";
            	    alliance.options[2].innerText = "Agregador";
            	    alliance.options[2].innerHTML = "Agregador";
            	    
//            	    if(selIndex>-1 && selIndex<=alliance.options.length){
//            	    	if(selIndex==2 && alliance.options[selIndex].value=="Agregador"){ // Por el paso de parametros con javascript se tuvo que forzar a cambiar el indice seleccionado ya que el combo inicia cargado con la lista completa y cuando es netpay regresa 2
//            	    		alliance.options[2].selected=true; 
//            	    	}else if(selIndex==2){
//            	    		alliance.options[1].selected=true; 
//            	    	}else{
//            	    		alliance.options[selIndex].selected=true;
//            	    	}
//            	    }
//            	    if(selIndex==4){
//            	    	alliance.options[2].selected=true;
//            	    }
            	    var sizeCombo = alliance.options.length;
               	    for(var i=0; i<sizeCombo ;i++){
               	    	if(valAlliance == alliance.options[i].value){
               	    		alliance.options[i].selected=true;
               	    	}
               	    }
            	    alliance_hidd.value = alliance.value;
            	    
              }
              
              
              function  fillComboAllianceMicros(selIndex){
            	  var alliance = document.getElementById("forma:affiliation_alliance");
            	  var alliance_hidd = document.getElementById("forma:affiliation_alliance_hidd");
             	 
            	  //  alliance.options.length = 0;
            	  alliance.options.length = 1;
            	    
            	   /* var option0 = document.createElement('option');
            	    alliance.options.add(option0, 0);
            	    alliance.options[0].value = "Ninguna";
            	    alliance.options[0].innerText = "Ninguna";
            	   */ 
            	    var option1 = document.createElement('option');
            	    alliance.options.add(option1, 1);
            	    alliance.options[1].value = "Micros";
            	    alliance.options[1].innerText = "Micros";
            	    alliance.options[1].innerHTML = "Micros";
            	    
            	    var option2 = document.createElement('option');
            	    alliance.options.add(option2, 2);
            	    alliance.options[2].value = "Agregador";
            	    alliance.options[2].innerText = "Agregador";
            	    alliance.options[2].innerHTML = "Agregador";
            	    
            	    if(selIndex>-1 && selIndex<=alliance.options.length){
            	    	alliance.options[selIndex].selected=true;
            	    }
            	    if(selIndex==4){
            	    	alliance.options[1].selected=true;
            	    }//agregador
             	    
            	    alliance_hidd.value = alliance.value;

              }
              
              //(gina) se incluye agregador para todos los productos
              function  fillAllianceAgregator(selIndex){
            	  var alliance = document.getElementById("forma:affiliation_alliance");
            	  var alliance_hidd = document.getElementById("forma:affiliation_alliance_hidd");
            	  var producto = document.getElementById("forma:affiliation_productdesc").value;
            	  var solucion 	= document.getElementById("forma:affiliation_soluciontype").value;
            	  var valAlliance = alliance.options[selIndex].value;
            	  
            	  if((producto == "Terminal punto de venta")||(producto=="Cargo Recurrente")){
            		  
	            		  alliance.options.length = 1;//para que se quede solo la opcion de "ninguna"
	              	    
		              	  var option1 = document.createElement('option'); //se agrega la opcion de agregador
		              	  alliance.options.add(option1, 1);
		              	  alliance.options[1].value = "Agregador";
		              	  alliance.options[1].innerText = "Agregador";
		              	  alliance.options[1].innerHTML = "Agregador";
	              	    
		              	  for(var i=0; i<alliance.options.length; i++){
	               	    	if(valAlliance == alliance.options[i].value){
	               	    		alliance.options[i].selected=true;
	               	    	}
		              	  }
            		  
            	  }
            	  alliance_hidd.value = alliance.value; 
              }
              
              
              
//              function fillComboPaquete(selIndex, paqueteVal) {
//            	  var alliance = document.getElementById("forma:affiliation_alliance").value;
//            	  var paquetes = document.getElementById("forma:affiliation_chargeType");
//            	  var paquetes_hidd = document.getElementById("forma:affiliation_chargeType_hidd");
//            	  var sel_prod = document.getElementById("forma:affiliation_productdesc").value;
//              	  var selected = selIndex;
//              	  
//              	  paquetes.options.length = 1;
//              	  
//            	  if(alliance == "Micros"){
//            		  var option = document.createElement('option');
//            		  paquetes.options.add(option, 1);
//            		  paquetes.options[1].value = "(+)0.15% en tasa";
//            		  paquetes.options[1].innerText = "(+)0.15% en tasa";
//            		  paquetes.options[1].innerHTML = "(+)0.15% en tasa";
//            		  
//            		  var option2 = document.createElement('option');
//            		  paquetes.options.add(option2, 2);
//            		  paquetes.options[2].value = "(+)$1.4 por transaccion";
//            		  paquetes.options[2].innerText = "(+)$1.4 por transaccion";
//            		  paquetes.options[2].innerHTML = "(+)$1.4 por transaccion";
//            		  
//            		  var option3 = document.createElement('option');
//            		  paquetes.options.add(option3, 3);
//            		  paquetes.options[3].value = "Estudio de Rentabilidad";
//            		  paquetes.options[3].innerText = "Estudio de Rentabilidad";
//            		  paquetes.options[3].innerHTML = "Estudio de Rentabilidad";
//            		  
//            	  }else if(alliance == "Cybersource"){
//            		  var option = document.createElement('option');
//            		  paquetes.options.add(option, 1);
//            		  paquetes.options[1].value = "(+)0.1% en tasa";
//            		  paquetes.options[1].innerText = "(+)0.1% en tasa";
//            		  paquetes.options[1].innerHTML = "(+)0.1% en tasa";
//            		  
//            		  var option2 = document.createElement('option');
//            		  paquetes.options.add(option2, 2);
//            		  paquetes.options[2].value = "(+)$1.10 por transaccion";
//            		  paquetes.options[2].innerText = "(+)$1.10 por transaccion";
//            		  paquetes.options[2].innerHTML = "(+)$1.10 por transaccion";
//            		  
//            		  var option3 = document.createElement('option');
//            		  paquetes.options.add(option3, 3);
//            		  paquetes.options[3].value = "Estudio de Rentabilidad";
//            		  paquetes.options[3].innerText = "Estudio de Rentabilidad";
//            		  paquetes.options[3].innerHTML = "Estudio de Rentabilidad";
//            		  
//            	  }else if(alliance == "Netpay"){
//            		  if(sel_prod=="Comercio Electronico"){
//            			  var option = document.createElement('option');
//	            		  paquetes.options.add(option, 1);
//	            		  paquetes.options[1].value = "(+)% en tasa";
//	            		  paquetes.options[1].innerText = "(+)% en tasa";
//	            		  paquetes.options[1].innerHTML = "(+)% en tasa"; 
//	            		  
//	            		  var option2 = document.createElement('option');
//	            		  paquetes.options.add(option2, 2);
//	            		  paquetes.options[2].value = "(+)$1 por transaccion";
//	            		  paquetes.options[2].innerText = "(+)$1 por transaccion";
//	            		  paquetes.options[2].innerHTML = "(+)$1 por transaccion";
//	            		  
//	            		  var option3 = document.createElement('option');
//	            		  paquetes.options.add(option3, 3);
//	            		  paquetes.options[3].value = "Estudio de Rentabilidad";
//	            		  paquetes.options[3].innerText = "Estudio de Rentabilidad";
//	            		  paquetes.options[3].innerHTML = "Estudio de Rentabilidad";
//	            		  
//            		  }else{
//	            		  var option = document.createElement('option');
//	            		  paquetes.options.add(option, 1);
//	            		  paquetes.options[1].value = "(+)% en tasa";
//	            		  paquetes.options[1].innerText = "(+)% en tasa"; 
//	            		  paquetes.options[1].innerHTML = "(+)% en tasa"; 
//	            		  
//	            		  var option2 = document.createElement('option');
//	            		  paquetes.options.add(option2, 2);
//	            		  paquetes.options[2].value = "(+)$1 por transaccion";
//	            		  paquetes.options[2].innerText = "(+)$1 por transaccion";
//	            		  paquetes.options[2].innerHTML = "(+)$1 por transaccion";
//	            		  
//	            		  var option3 = document.createElement('option');
//	            		  paquetes.options.add(option3, 3); 
//	            		  paquetes.options[3].value = "(+)$75 Renta Ad. por Eq";
//	            		  paquetes.options[3].innerText = "(+)$75 Renta Ad. por Eq";
//	            		  paquetes.options[3].innerHTML = "(+)$75 Renta Ad. por Eq";
//	            		  
//	            		  var option4 = document.createElement('option');
//	            		  paquetes.options.add(option4, 4);
//	            		  paquetes.options[4].value = "Estudio de Rentabilidad";
//	            		  paquetes.options[4].innerText = "Estudio de Rentabilidad";
//	            		  paquetes.options[4].innerHTML = "Estudio de Rentabilidad";
//            		  }
//            	  }
//            	  
//            	  if(alliance!="Ninguna"){
//            		  if(selIndex>3){
//	            		  if(selIndex==4 && paqueteVal!="Estudio de Rentabilidad") selected=1; // primera opcion de netpay cuando se refresca la pagina dado que el combo esta precargado con todas las opciones posibles (micros y netpay)
//	            		  if(selIndex==5) selected=2;
//	            		  if(selIndex==6) selected=3;
//	            		  if(selIndex==7) selected=4;
//	            		  if(selIndex==8) selected=1;
//	            		  if(selIndex==9) selected=2;
//	            		  if(selIndex==10 && paquetes.options.length==4) selected=3;
//	            		  if(selIndex==10 && paquetes.options.length==5) selected=4;
//	            		  if(selIndex==11) selected=1;
//	            		  if(selIndex==12) selected=2;
//	            		  if(selIndex==13 && paqueteVal=="Estudio de Rentabilidad" && alliance=="Netpay") selected=4;//netpay con estudio de rentabilidad
//	            		  if(selIndex==13 && paqueteVal=="Estudio de Rentabilidad" && alliance=="Micros") selected=3; //micros con estudio de rentabilidad
//	            	  }
//            		  
//	            	  if(selected>-1){
//	            		  if(alliance=="Agregador"){
//	            			  paquetes.disabled=true;
//	            		  }else{
//	 	            		 paquetes.options[selected].selected=true;
//	            		  }
//	            	  }
//            	  }
//            	  
//            	  paquetes_hidd.value = paquetes.value;
//            	            	    
//              }
              
              function alliance_change(){
            	  var alianza_val = document.getElementById("forma:affiliation_alliance");
            	  var plan = document.getElementById("forma:affiliation_comercialplan");
             	  var planIxe = document.getElementById("forma:affiliation_comercialplanIxe");
             	  var profile = valorRed();
             	  var selected = 0;
             	  
             	  plan.disabled=false;
             	  if(plan.selectedIndex>0 && planIxe.selectedIndex==0){
             		  selected = plan.value;
             	  }else if(planIxe.selectedIndex>0 && plan.selectedIndex==0){
             		  selected = planIxe.value;
             	  }
            	  if(alianza_val.value=="Agregador"){
                	  alert('Para el alta de la afiliacion de Agregador deberas contar con el VoBo de Producto Negocio Adquirente');
                	  agregadorSpecial(true, selected, profile);
            	  }else{
            		  agregadorSpecial(false, selected, profile);
            	  }
            	  alliance();
              }
              
              function alliance(){
            	  var paquetes = document.getElementById("forma:affiliation_chargeType");
            	  var alianza_val = document.getElementById("forma:affiliation_alliance");
            	  var alliance_hidd 	= document.getElementById("forma:affiliation_alliance_hidd");
            	  
            	  alliance_hidd.value = alianza_val.value;
            	  
            	  var plan = document.getElementById("forma:affiliation_comercialplan");
             	  var planIxe = document.getElementById("forma:affiliation_comercialplanIxe");
             	  var profile = valorRed();
             	  var selected = 0;
             	  if(profile == 0 && plan.value<8){//del 1 al 7 son banorte profile 0 es banorte
             		  selected = plan.value;
             	  }else if(profile == 1 && planIxe.value>7){//del 8 al 14 son ixe y profile 1 es ixe
             		  selected = planIxe.value;
             	  }
            	  if(alianza_val.value=="Agregador"){
            		  agregadorSpecial(true, selected, profile);
            	  }else{
            		  agregadorSpecial(false, selected, profile);
            	  }
            	  if(alianza_val.value!="Ninguna"){
            			  plan.disabled=false;
                		  planIxe.disabled=false;  
            	  }
            	  adquirerOption();
            	  inicializarCashback();
            	  
              }

              function companiasTelefonicas(){
            		return new Array(
            				"affiliation_telcel",
            				"affiliation_movistar",
            				"affiliation_iusacell",
            				"affiliation_nextel"
            		        );
              }
              
              
              function deactivateTA(){
            	  var companias = companiasTelefonicas();
            	  for(var i =0; i<companias.length; i++){
            		  document.getElementById("forma:"+companias[i]).value="";
            		  document.getElementById("forma:"+companias[i]).disabled=true;
            	  }
              }

              function activateTA(){
	              	var companias = companiasTelefonicas();
	              	var tiempo_aire =  document.getElementById("forma:affiliation_tiempoaire");
	
	              	if(tiempo_aire.checked==true){
	              		for(var i =0; i<companias.length; i++){
	              			document.getElementById("forma:"+companias[i]).disabled=false;
	              			if(document.getElementById("forma:"+companias[i]).value==null || document.getElementById("forma:"+companias[i]).value==""){
	              				document.getElementById("forma:"+companias[i]).value="5.0";
	              			}
	              		}
	              	 } else{
	              		 deactivateTA();
	              		 tiempo_aire.disabled=false;
	              	 }
              }

              
              function validateCommTA(fill){
              	var compania= document.getElementById('forma:' + fill);
              	if(compania ==null || compania=="" || compania.value <0 || compania.value >5){
              		 alert("Favor de proporcionar una comision entre 0% y 5%");
              		 compania.focus();
              		 return false;
              	}
              }
              
             function checkPlanForMonthlyRate(){
            	var planBanorte = document.getElementById("forma:affiliation_comercialplan").value;
            	var planIxe = document.getElementById("forma:affiliation_comercialplanIxe").value;
            	if(planBanorte!=6 || planIxe!=6 ||
            			planBanorte!=5 || planBanorte!=5){
            		 document.getElementById("forma:optionMonthlyratemn").disabled =false;//combo de sustituir renta
	           		 document.getElementById("forma:optionMonthlyratedlls").disabled =false;
	           		 document.getElementById("forma:recalculateCommisionTable").value = true;
            	}else{
            		document.getElementById("forma:optionMonthlyratemn").disabled =true;
         			document.getElementById("forma:optionMonthlyratedlls").disabled =true;
            	}
             }
             
             function checkPlanForMonthlyRateRecall(){
            	 var comercialPlan = document.getElementById("forma:affiliation_comercialplan");
            	 var comercialPlanIxe = document.getElementById("forma:affiliation_comercialplanIxe");
            	//si el plan elegido es diferente a "otro" o "estudio de rentabilidad"
            	 if(comercialPlan.value != 5 && comercialPlan.value != 6 &&
            			 comercialPlanIxe.value != 5 && comercialPlanIxe.value != 6 ){
            		 document.getElementById("forma:optionMonthlyratemn").disabled =false; //combo de sustituir renta mensual se habilita
            		 document.getElementById("forma:optionMonthlyratedlls").disabled =false;// pesos y dolares
         		}else{//si el plan es otro o estudio de rentabilidad
         			document.getElementById("forma:optionMonthlyratemn").disabled =true;//combo de sustituir renta mensual se deshabilita
         			document.getElementById("forma:optionMonthlyratedlls").disabled =true;//pesos y dolares
         		}
             }
             
             /**
              * Funcion que se llama al cambiar el valor del combo de sustituir renta
              */
             function checkOptionMonthlyRateMN(){
            	 var sustituirPesos = document.getElementById("forma:optionMonthlyratemn");
            	  
            	 if(sustituirPesos.value == "SiSaldo" || sustituirPesos.value == "SiFac"){
            		 document.getElementById("forma:replaceAmountratemn").disabled = false;
            		 document.getElementById("forma:affiliation_monthlyratemn").disabled = false;
            		 document.getElementById("forma:affiliation_monthlyinvoicingminmn").disabled = false;
            		 document.getElementById("forma:recalculateCommisionTable").value = true;
            	 }else{
            		 document.getElementById("forma:replaceAmountratemn").value=0;
            		 document.getElementById("forma:replaceAmountratemn").disabled =true;
            		 document.getElementById("forma:affiliation_monthlyratemn").value=0;
            		 document.getElementById("forma:affiliation_monthlyratemn").disabled = true;
            		 document.getElementById("forma:affiliation_monthlyinvoicingminmn").value = 0;
            		 document.getElementById("forma:affiliation_monthlyinvoicingminmn").disabled = true;
            		 document.getElementById("forma:recalculateCommisionTable").value = true;
            	 }
            	  
             }
             
             function checkOptionMonthlyRateRecall(){
            	 var optionMonthlyRate = document.getElementById("forma:optionMonthlyratemn");
            	 var optionMonthlyRateSelected = optionMonthlyRate[optionMonthlyRate.selectedIndex].value;	
            	 
            	 if(optionMonthlyRateSelected == "SiSaldo" || optionMonthlyRateSelected == "SiFac"){
            		 document.getElementById("forma:replaceAmountratemn").disabled =false;
            	 }else{
            		 document.getElementById("forma:replaceAmountratemn").disabled =true;
            	 }
            	 
            	 optionMonthlyRate = document.getElementById("forma:optionMonthlyratedlls");
            	 optionMonthlyRateSelected = optionMonthlyRate[optionMonthlyRate.selectedIndex].value;	
            	 
            	 if(optionMonthlyRateSelected == "SiSaldo" || optionMonthlyRateSelected == "SiFac"){
            		 document.getElementById("forma:replaceAmountratedlls").disabled =false;
            	 }else{
            		 document.getElementById("forma:replaceAmountratedlls").disabled =true;
            	 }
            	 
             }
         
             /**
              * Funcion que se llama al cambiar el valor del combo de sustituir renta en dolares
              */
             function checkOptionMonthlyRateDLLS(){
            	 var optionMonthlyRate = document.getElementById("forma:optionMonthlyratedlls");
            	 if(optionMonthlyRate.value == "SiSaldo" || optionMonthlyRate.value == "SiFac"){
            		 document.getElementById("forma:replaceAmountratedlls").disabled = false;
            		 document.getElementById("forma:affiliation_monthlyratedlls").disabled = false;
            		 document.getElementById("forma:affiliation_monthlyinvoicingmindlls").disabled = false;
            		 document.getElementById("forma:recalculateCommisionTable").value = true;
            	 }else{
            		 document.getElementById("forma:replaceAmountratedlls").disabled =true;
            		 document.getElementById("forma:affiliation_monthlyratedlls").disabled = true;
            		 document.getElementById("forma:affiliation_monthlyinvoicingmindlls").disabled = true;
            		 document.getElementById("forma:recalculateCommisionTable").value = true;
            	 }
            	 
             }
             
             /*function paqueteTPVNomina(){
            	 
           	  var isChecked = document.getElementById("forma:affiliation_tpv_payroll").checked;
           	  
           	  if( verifyPlanSelected()){
           		  
	           	  if(isChecked == true ){
	           		  
		            	  var r=confirm("Al seleccionar esta opci\u00f3n disminuir\u00e1n las tasas de cr\u00e9dito y d\u00e9bito, pero tambi\u00e9n se exigir\u00e1 un n\u00famero de empleados activos, de tal forma que si el cliente no cumple, se le cobrar\u00e1 una penalizaci\u00f3n.  Deseas contratar el Paquete TPV-Nomina?");
		            	  if (r==true){
		            		  document.getElementById("forma:affiliation_tpv_payroll").checked = true;
		            		  setInfoActEmployee();
		            		  checkPlanForMonthlyRate();
		            		  document.getElementById("forma:recalculateCommisionTable").value = true;
		            	    }
		            	  else{
		            		  document.getElementById("forma:affiliation_tpv_payroll").checked = false;
		            		  document.getElementById("forma:tpv_number_employee").value="";
		            		  document.getElementById("forma:tpv_penalty").value="";
		            	    }
		            	  
	           	  }
	           	  else {
	           		document.getElementById("forma:tpv_number_employee").value="";
          		  document.getElementById("forma:tpv_penalty").value="";
	           	  }
	           	  
           	  }
             }*/
             
             /*function paqueteTPVNominaRecall(){
              	  var isChecked = document.getElementById("forma:affiliation_tpv_payroll").checked;
              	  
              	  if( verifyPlanSelected()){//si el id del plan es 1,2,4,7
	   	           	  if(isChecked == true ){//si esta seleccionado tpv nomina
	            		  document.getElementById("forma:affiliation_tpv_payroll").checked = true; //se queda seleccionado
	            		  setInfoActEmployee();//se verifica checkbox y numero de empleados y penalizacion y activa o desactiva tpv
	            		  checkPlanForMonthlyRateRecall();  //en base al plan se activa o desactiva la opcion de sustituir renta mensual
	   	           	  }else{//si no se selecciono tpv nomina
	   	           		  document.getElementById("forma:affiliation_tpv_payroll").checked = false;//se limpia el checkbox
	   	           		  document.getElementById("forma:tpv_number_employee").value="";//se limpia numero de empleados
	   	           		  document.getElementById("forma:tpv_penalty").value="";//se limpia penalizacion
	   	           	  }
              	  }else{
              		  document.getElementById("forma:tpv_number_employee").value="";//se limpia numero de empleados
 	           		  document.getElementById("forma:tpv_penalty").value="";//se limpia penalizacion
              	  }
                }*/
             
             
             /*function setInfoActEmployee(){
            	 var plan = document.getElementById("forma:affiliation_comercialplan").value;
            	 if(document.getElementById("forma:affiliation_tpv_payroll").checked){//si selecciono tpv nomina
	            	 if(plan=="1"){//plan 15
	            		 document.getElementById("forma:tpv_number_employee").value=5;
	            		 document.getElementById("forma:tpv_penalty").value=150;
	            	 }else if (plan=="2"){//plan 35
	            		 document.getElementById("forma:tpv_number_employee").value=7;
	            		 document.getElementById("forma:tpv_penalty").value=200;
	            	 }else if (plan=="4"){//plan 150
	            		 document.getElementById("forma:tpv_number_employee").value=10;
	            		 document.getElementById("forma:tpv_penalty").value=250;
	            	 }else if (plan=="7"){//plan 500
	            		 document.getElementById("forma:tpv_number_employee").value=15;
	            		 document.getElementById("forma:tpv_penalty").value=300;
	            	 }else {//si es otro plan que no aplique
	            		 document.getElementById("forma:tpv_number_employee").value=0;//se limpia numero de empleados
	            		 document.getElementById("forma:tpv_penalty").value=0;//se limpia penalizacion
	            		 document.getElementById("forma:affiliation_tpv_payroll").checked = false;//se quita la palomita de tpv
	            		 document.getElementById("forma:affiliation_tpv_payroll").disabled =true;//se deshabilita
	            	 }
            	 }else{
            		 document.getElementById("forma:tpv_number_employee").value="";//se limpia numero de empleados
            		 document.getElementById("forma:tpv_penalty").value="";//se limpia penalizacion
            	 }
             }*/
             
             
             function verifyPlanSelected(){
            	 var plan = document.getElementById("forma:affiliation_comercialplan").value;
            	 if(plan=="1" || plan=="2" ||plan=="4" ||plan=="7"){
            		return true; 
            	 }else {
            		 return false;
            	 }
             }
             
             function planSelectedAction(){
            	 var red = valorRed();
            	 if(red==null){
            		 alert('Por favor seleccione Red: Banorte o Ixe');
            		 document.getElementById("forma:red").focus();
            		 return false;
            	 }
            	 setInfoActEmployee();
            	 
            	 //validationTPV_Payroll();
            	 
            	 var red = valorRed();
            	 var selectedPlan;
            	 if(red==0){//banorte
            		 selectedPlan = document.getElementById("forma:affiliation_comercialplan").value;
            	 }else if(red==1){//ixe
            		 selectedPlan = document.getElementById("forma:affiliation_comercialplanIxe").value;
            	 }
            	 if(selectedPlan==6){//"otro"
            		 alert("Por favor especifique solo la clave. Cualquier comentario adicional realizarlo en la seccion 'Comentarios'");
            	 }
            	 
            	 recalculateCommisionTable();
             }
             
             /*function validationTPV_Payroll(){
            	 var plan = document.getElementById("forma:affiliation_comercialplan").value;
            	 
            	 if(plan=="1"){
            		 document.getElementById("forma:affiliation_tpv_payroll").disabled =false;
            	 }else if (plan=="2"){
            		 document.getElementById("forma:affiliation_tpv_payroll").disabled =false;
            	 }else if (plan=="4"){
            		 document.getElementById("forma:affiliation_tpv_payroll").disabled =false;
            	 }else if (plan=="7"){
            		 document.getElementById("forma:affiliation_tpv_payroll").disabled =false;
            	 }else {
            		 document.getElementById("forma:affiliation_tpv_payroll").checked = false;
            		 document.getElementById("forma:affiliation_tpv_payroll").disabled =true;
            	 }
        	 
             }*/
             
             /* *****************************   FUNCIONES CYBERSOURCE  ******************************************* */
             
             
 /*            function fillComboAllianceCybersource(valAlliance){

               	  var alliance 			= document.getElementById("forma:affiliation_alliance");
               	  var alliance_hidd 	= document.getElementById("forma:affiliation_alliance_hidd");
               	  //alliance.options.length = 0;
               	  alliance.options.length = 1;
               	  
               	
               	    var option1 = document.createElement('option');
               	    alliance.options.add(option1, 1);
               	    alliance.options[1].value = "Cybersource";
               	    alliance.options[1].innerText = "Cybersource";
               	    alliance.options[1].innerHTML = "Cybersource";
               	    
               	    var option2 = document.createElement('option');
               	    alliance.options.add(option2, 2);
               	    alliance.options[2].value = "Netpay";
               	    alliance.options[2].innerText = "Netpay (Caja Total Banorte)";
               	    alliance.options[2].innerHTML = "Netpay (Caja Total Banorte)";
               	    
               	    //(gina) agrego opcion para agregador ya que aplica para todos los productos
               	    var option3 = document.createElement('option');
            	    alliance.options.add(option3, 3);
            	    alliance.options[3].value = "Agregador";
            	    alliance.options[3].innerText = "Agregador";
            	    alliance.options[3].innerHTML = "Agregador";
               	    
               	    
               	    var sizeCombo = alliance.options.length;
               	    for(var i=0; i<sizeCombo ;i++){
               	    	if(valAlliance == alliance.options[i].value){
               	    		alliance.options[i].selected=true;
               	    	}
               	    }
               	 
               	    alliance_hidd.value = alliance.value;
               	    document.getElementById("forma:affiliation_currentbank").disabled =true;
               	    activateIntegrationCombo(valAlliance);
               	 
             }


             function activateIntegrationCombo(valAlliance){
             
            	 if(valAlliance == "Cybersource"){
            		 document.getElementById("forma:affiliation_integration").disabled =false;
            	 }else{
            		 document.getElementById("forma:affiliation_integration").value=0;
            		 document.getElementById("forma:affiliation_integration").disabled =true;
            	 }
             
             }


             function activateRentaDolar(){
            	 
            	 var integration 		= document.getElementById("forma:affiliation_integration").value;
            	 var valAlliance 		= document.getElementById("forma:affiliation_alliance").value;
                 
            	 if(valAlliance == "Cybersource"){
            		 if(integration == "Enterprise Revision Manual" || integration == "Enterprise 3D Secure" || integration == "Enterprise 3D Secure"){
	            		 document.getElementById("forma:affiliation_rentaDolar").disabled =false;
	            	 }else{
	            		 document.getElementById("forma:affiliation_rentaDolar").disabled =true;
	            		 document.getElementById("forma:affiliation_rentaDolar").value =0;
	            	 }
            	 } else{
            		 document.getElementById("forma:affiliation_rentaDolar").disabled =true;
            		 document.getElementById("forma:affiliation_rentaDolar").value =0;
            	 }
            	 
            	 recalculateCommisionTable();
             
             }*/
             
             function inicializarRentBy(){
            	 var comboRentaPor = document.getElementById("forma:rentBy");
            	 var comboIntegracion = document.getElementById("forma:affiliation_integration");
            	 var groupNo = document.getElementById("forma:groupNo").disabled = true;        		 
            	 
            	 if(comboIntegracion.value == "Cybersource Enterprise Revision Manual" ||
            			 comboIntegracion.value == "Cybersource Enterprise Autenticación Selectiva" ||
            			 comboIntegracion.value == "Cybersource Direct" ||
            			 comboIntegracion.value == "Cybersource Hosted" ||
            			 comboIntegracion.value == "Cybersource Call Center" ||
            			 comboIntegracion.value == "Cybersource Enterprise Call Center"){
            		 comboRentaPor.disabled = false;
            	 }else{
            		 comboRentaPor.value = 0;
            		 comboRentaPor.disabled = true;
            	 }
            	 onchangeRentBy();
             }
             
             function onchangeRentBy(){
            	 var rentBy = document.getElementById("forma:rentBy").value;
            	 var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');
		    	var antiguedad = document.getElementById("forma:antiguedad");
		    	var groupNo = document.getElementById("forma:groupNo");
		    	
            	
            	 if(rentBy == "Grupo"){
            		 document.getElementById("forma:groupNo").disabled = false;
            	 }else{
            		 //document.getElementById("forma:groupNo").disabled = true;
            		 //document.getElementById("forma:groupNo").value = "";
            		 if(valorVentaMensual.value=="" || valorVentaMensual.value==null){
            			 document.getElementById("forma:groupNo").disabled = true;
            			 document.getElementById("forma:groupNo").value = "";
                 	}else{
                 		if(valorVentaMensual.value>=100000 && antiguedad.value==-6){
                 				groupNo.disabled = false;      			
     	               	}else{
     	               		document.getElementById("forma:groupNo").disabled = true;
     	               		document.getElementById("forma:groupNo").value = "";
     	               	 }
                 	}
            	 }
             }
             
             function inicializarCybersource(){
            	 var comboProducto = document.getElementById("forma:affiliation_productdesc"); 
            	 var comboAlianza = document.getElementById("forma:affiliation_alliance");
            	 var comboIntegracion =  document.getElementById("forma:affiliation_integration");
            	 
            	 
            	 document.getElementById("forma:limitedCoverageCyberMn").disabled = true;
            	 document.getElementById("forma:wideCoverageCyberMn").disabled = true;
            	 
            	 
            	 if(comboIntegracion.value == "Cybersource Enterprise Revision Manual" || 
         			 comboIntegracion.value == "Cybersource Enterprise Autenticación Selectiva" ||
         			 comboIntegracion.value == "Cybersource Direct" ||
         			 comboIntegracion.value == "Cybersource Hosted" ||
         			 comboIntegracion.value == "Cybersource Call Center" ||
         			 comboIntegracion.value == "Cybersource Enterprise Call Center"){
            		 document.getElementById("forma:limitedCoverageCyberDlls").disabled = false;
            		 document.getElementById("forma:wideCoverageCyberDlls").disabled = false;
            	 }
            	 
            	 
            	 
            	 
	      	 	
            	 if(comboAlianza.value == "Tradicional" || comboAlianza.value == "Hosted" || comboAlianza.value == "Call Center" || (comboAlianza.value == "Agregador" && comboProducto.value == "Comercio Electronico")){
            		 document.getElementById("forma:transactionFeeCyberMn").disabled = true;
            		 document.getElementById("forma:transactionFeeCyberDlls").disabled = true;
//            		 document.getElementById("forma:limitedCoverageCyberMn").disabled = true;
//            		 document.getElementById("forma:limitedCoverageCyberDlls").disabled = true;
            	 }else{
            		 document.getElementById("forma:transactionFeeCyberMn").disabled = false; 
            		 document.getElementById("forma:transactionFeeCyberDlls").disabled = false;
            		 document.getElementById("forma:limitedCoverageCyberMn").disabled = false;
            		 document.getElementById("forma:limitedCoverageCyberDlls").disabled = false;
            	 }
             }

             
             /**
              * 
              */
             function onchangeSolucion(){
            	 inicializarCashback();
            	 inicializarTiempoAire();
            	 inicializarSolucion();
             }
             /**
              * Funcion para habilitar/deshabilitar la opcion de impulso captación en base al producto, solucion, divisa y alianza
              */
             function inicializarCaptacion(){ 
            	var checkboxCaptacion	  = document.getElementById("forma:impulsoCaptacion");
            	var captacionSeleccionado = document.getElementById("forma:impulsoCaptacion").checked; 
            	var producto 			  = document.getElementById("forma:affiliation_productdesc").value;
            	var plan			  	  = document.getElementById("forma:affiliation_comercialplan").value;
            	
            	var mujerPyMESeleccion	  			= document.getElementById("forma:mujerPyME").checked;//para que esta chequeado MujerPyME Banorte           	 
            	var desactivarMujerPyME 			= document.getElementById("forma:desactivarMujerPyME");
            	var esCuentaMujerPyME  	  			= document.getElementById("forma:esCuentaMujerPyME").value;
            	var noEsCuentaMujerPyMEValidada 	= document.getElementById("forma:noEsCuentaMujerPyMEValidada").value;
            	var isMsgMujerPyME 					= document.getElementById("forma:isMsgMujerPyME");
            	
            	var esClienteMujerPyME  	  		= document.getElementById("forma:esClienteMujerPyME").value;
            	var noEsClienteMujerPyMEValidado	= document.getElementById("forma:noEsClienteMujerPyMEValidado").value;
            	
            	var esCuentaMujerPyMEDlls  			= document.getElementById("forma:esCuentaMujerPyMEDlls").value;
            	var noEsCuentaMujerPyMEValidadaDlls = document.getElementById("forma:noEsCuentaMujerPyMEValidadaDlls").value;
         
           	             	            	
            		if(producto == "Interredes" || producto == "Terminal punto de venta"||producto == "Comercio Electronico" || 
                 	   producto == "Terminal Personal Banorte (Mpos)"|| producto == "Cargo Recurrente"){
            			if ("5" == plan || "6" == plan || "16" == plan || "17" == plan || "18" == plan || "19" == plan) {
		                    if (captacionSeleccionado == true){
		                    	checkboxCaptacion.disabled = false;
		                    	checkboxCaptacion.checked = true;
		                    }else{//si no estaba seleccionado checkboxCaptacion
		                    	checkboxCaptacion.disabled = false;
		                    } 
	                	}else{//si no cumple con las condiciones de plan
	                		checkboxCaptacion.disabled = true;
	                		checkboxCaptacion.checked = false;
	                	}
                	}else{//si no cumple con las condiciones de producto
                		checkboxCaptacion.disabled = true;
                		checkboxCaptacion.checked = false;
                	}
            		
            		if(mujerPyMESeleccion == true){
                  		if(desactivarMujerPyME.value == "true"){
                  			
						}else{
							if(esClienteMujerPyME=="true"){
								if(esCuentaMujerPyME=="true"){
									checkboxCaptacion.checked=true;
									if(isMsgMujerPyME.value=="true"){
										alert("Es necesario validar que la cuenta cargo de la afiliación es producto 0376 Mujer Pyme");
										isMsgMujerPyME.value= "false";
									}
								}else{
									checkboxCaptacion.checked=false;
								}
														
							}
						}
                  	 }else{
                	}
             }
             
             /**
              * F-83585 - Garantia Liquida
              * Funcion para habilitar/deshabilitar el apartado de garantia Liquida en base al producto, solucion, divisa y alianza
              */
             function inicializarGarantiaLiquida(){ 
            	 
            	 /* 	SE INICIALIZA CUANDO SEA:
            	  * •	Comercio Electrónico.
					•	Cargos Periódicos. 
					•	Terminal punto de venta cuando se seleccione teclado abierto del apartado servicios especiales.
					•	Interredes cuando se seleccione teclado abierto en el apartado de servicios especiales.
					•	Seleccionen el giro agregador 5399.
					•	El buró interno sea malo. 
					•	Antigüedad menor a 6 meses y que sus ventas estimadas mensuales sean mayores a 25 mil.
					•	Giros de Alto Riesgo.

            	  */
            	 var comboRequiereFianza = document.getElementById("forma:affiliation_havedepositcompany"); //requiere fianza combo
            	 var checkboxExentar = document.getElementById("forma:exentDep");//exenta fianza checkbox
            	 var comboProducto = document.getElementById("forma:affiliation_productdesc"); //producto combo
            	 var checkboxTecladoAbierto = document.getElementById("forma:affiliation_openkey"); // teclado abierto checkbox
            	 var giro = document.getElementById("forma:client_categorycode").value;//giro
            	 var numGiro = giro.substring(0,6);//numero de giro
            	 var comboAlianza = document.getElementById("forma:affiliation_alliance");//alianza
            	 var altoRiesgo = false;
            	 var comboBuroInterno = document.getElementById("forma:affiliation_internalcredithistory");
            	 var comboBuroExterno = document.getElementById("forma:affiliation_externalcredithistory");
            	 var antiguedad = document.getElementById("forma:antiguedad");
            	 var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');
            	 var seleccion= valorSeleccionGarantiaLiquida();
            	 var seleccionGarantia = document.getElementById("forma:seleccionGarantia");
            	 var fianzaOculta = document.getElementById("forma:fianzaOculta");
            	 fianzaOculta.value="false";
            	 
            	 var seleccionGiroAltoRiesgoGL= document.getElementById("forma:seleccionGiroAltoRiesgoGL");
            	 seleccionGiroAltoRiesgoGL.value="false";           	 
            	 var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');            	 
            	 var radios = document.getElementsByName('forma:seleccionGarantia');
            	 var comboIntegracion = document.getElementById("forma:affiliation_integration");
            	 var groupNo = document.getElementById("forma:groupNo").value;
            	 
//            	 alert('valorVentaMensual.value<20000000:: '+(valorVentaMensual.value<20000000) +' ....valorVentaMensual.value==null:: '+(valorVentaMensual.value==null)+'...valorVentaMensual.value=="":: '+(valorVentaMensual.value==""));
            	 
            	 if(valorVentaMensual.value<20000000 || valorVentaMensual.value==null || valorVentaMensual.value==""){
        			 mostrarSeleccionGarantiaLiquida("none");
        			 seleccion=0;
        			 radios[0].checked=true;
        		 }
            	 
            	 mostrarSeleccionGarantiaLiquida("none");
            	 mostrarGarantiaLiquida("none");
            	 mostrarFianza("none");
            	 mostrarComentariosDisminucionExcepcionGL("none");

      			if("true"==isGiroGL(numGiro)){
      				altoRiesgo=true;
      				seleccionGiroAltoRiesgoGL.value="true";
      			}
      			
            	 if(comboAlianza.value=="Agregador"){
            		 altoRiesgo=true;
            	 }

            	 if(comboProducto.value=="Cargo Recurrente"){
            		 altoRiesgo=true;
            	 }
            	 if(checkboxTecladoAbierto.checked && (comboProducto.value=="Interredes" || comboProducto.value=="Terminal punto de venta")){
            		 altoRiesgo=true;
            	 }

            	 if(comboBuroInterno.value=="Malo"){
                     	
            		 altoRiesgo=true;
            	 }

            	 if(comboBuroExterno.value=="Malo"){
                     		 altoRiesgo=true;
            	 }
            	 //if(antiguedad.value==-6 && (valorVentaMensual.value>=25000)){
                 if(antiguedad.value==-6 && (valorVentaMensual.value>=100000)){//F-98896 Mejora Garantia Liquida: RF001
                	 if(groupNo == "" || groupNo == null){
                		 altoRiesgo=true;
                	 }else{
                		 if(altoRiesgo){//si otra caracteristica activo previamente GL lo deja
                			 altoRiesgo=true;
                		 }else{
                			 altoRiesgo=false;
                		 }
                	 }
            		 
            	 }
                 
                 if(comboProducto.value=="Comercio Electronico"){
                	 if(comboIntegracion.value == "Sin Cybersource / Con 3D"){
                		 if(altoRiesgo){//si otra caracteristica activo previamente GL lo deja
                			 altoRiesgo=true;
                		 }else{
                			 altoRiesgo=false;
                		 }
                	 }else{
                		 altoRiesgo=true;
                	 }
            		 
            	 }

            	 if(altoRiesgo){//si es alto riesgo si requiere fianza
            		 
            		 if(valorVentaMensual.value>=20000000 && comboProducto.value!="Cargo Recurrente"){
            			 mostrarSeleccionGarantiaLiquida("block");

            		 }else{
            			 mostrarSeleccionGarantiaLiquida("none");//F-83585 Garantia Liquida
            		 }
            		 
            		 if(seleccion==0){
            			 
            			 //Oculta Fianza y pone "NO" requiere fianza
            			 mostrarFianza("none");
            			 checkboxExentar.disabled=true;
            			 checkboxExentar.checked=false;
            			 comboRequiereFianza.value=0; //"NO"
            			 onchangeFianza();
            			 
            			 mostrarComentariosDisminucionExcepcionGL("none");
            			 mostrarGarantiaLiquida("block");
            			 onchangePorcentajeGL();
            			 
            		 }else if(seleccion==1){//fianza
            			 mostrarComentariosDisminucionExcepcionGL("none");
            			 mostrarFianza("block");
            			 fianzaOculta.value="true";
            			 checkboxExentar.disabled=true;
                		 checkboxExentar.checked=false;
                		 onchangeFianza();
                		 
            		 }
            		 
            	 }else{//si no es alto riesgo no requiere fianza            		 
            		 fianzaOculta.value="true";
            		 mostrarFianza("none");
            		 mostrarSeleccionGarantiaLiquida("none");//F-83585 Garantia Liquida
            		 mostrarComentariosDisminucionExcepcionGL("none");
            		 
            		 checkboxExentar.disabled=true;
            		 checkboxExentar.checked=false;
            		 comboRequiereFianza.value=0; //"NO"
            		 onchangeFianza();
            	 }
            	 //alert("valor de GL o Fianza: "+seleccion);
             }
             
                         
             //Garantia Liquida: Metodo para validar la venta mensual
             function isValidVentasMensuales(){
            	var comboPlanBanorte = document.getElementById("forma:affiliation_comercialplan");
            	var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');
            	
            	if(valorVentaMensual.value=="" || valorVentaMensual.value==null){
            		inicializarGarantiaLiquida();
            	}else{
            		if(comboPlanBanorte.value>=16 && comboPlanBanorte.value<=19){
	            		if(comboPlanBanorte.value == 16 && valorVentaMensual.value<15000){
	            			alert("Ventas estimadas mensuales debe ser mayor de 15 mil");	
	            			valorVentaMensual.focus();
	           		 	}
	            		if(comboPlanBanorte.value == 17 && valorVentaMensual.value<35000){
	            				alert("Ventas estimadas mensuales debe ser mayor de 35 mil");
	            				valorVentaMensual.focus();
	           		 	}
	            		if(comboPlanBanorte.value == 18 && valorVentaMensual.value<75000){
	            				alert("Ventas estimadas mensuales debe ser mayor de 75 mil");
	            				valorVentaMensual.focus();
	           		 	}
	            		
	            		if(comboPlanBanorte.value == 19 && valorVentaMensual.value<200000){
	            				alert("Ventas estimadas mensuales debe ser mayor de 200 mil");
	            				valorVentaMensual.focus();
	           		 	}
            		}
            		inicializarGroupNo();
            		inicializarGarantiaLiquida();
            		calcularGarantiaLiquida();
            		
            	}	
            }
             
           //Garantia Liquida: Metodo para validar monto fianza 
             function isValidMontoFianza(){
            	var comboPlanBanorte = document.getElementById("forma:affiliation_comercialplan");
            	var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');

            	var montoFianza= document.getElementById('forma:affiliation_depositamount');
            	var seleccion= valorSeleccionGarantiaLiquida();
            	var comboRequiereFianza = document.getElementById("forma:affiliation_havedepositcompany"); //requiere fianza combo
           	    var checkboxExentar = document.getElementById("forma:exentDep");//exenta fianza checkbox
           	 
            	if(valorVentaMensual.value>=20000000 && seleccion==1 && checkboxExentar.disabled==false && comboRequiereFianza.value==1){
            		if((valorVentaMensual.value*0.5) > (montoFianza.value)){
	            		alert("Monto fianza debe ser mayor al 50% de la venta estimadas mensuales");
	            		montoFianza.value=valorVentaMensual.value*0.5;
	            		montoFianza.focus();
            		}
            	}else{
            		montoFianza.value="";
            			
            	}	
            	
            }
             
             
             function onchangeVentasEstimadas(){
            	 inicializarGarantiaLiquida();
            	 
             }
             
           //Garantia Liquida: Metodo para calcular montos de cuadro de garantiaLiquida
             function calcularGarantiaLiquida(){
            	 var montoInicial=document.getElementById("forma:aff_montoInicial");
            	 var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');
            	 var montoPromDiario = document.getElementById("forma:aff_montoPromDiario");
            	 var montoGL = document.getElementById("forma:aff_montoGL");
            	 var porcentajeGL=document.getElementById("forma:aff_porcentajeGL");
            	 var excepcionPorceGL=document.getElementById("forma:aff_excepcionPorceGL");
            	 var facturacionMensualMinimaExigida=document.getElementById("forma:affiliation_monthlyinvoicingminmn");
            	 var porcentajeInicialGL=document.getElementById("forma:aff_porcentajeInicialGL");
            	 var montoInicialGL=document.getElementById("forma:aff_montoInicialGL");
            	 var porcentajeRestanteGL=document.getElementById("forma:aff_porcentajeRestanteGL");
            	 var montoRestanteGL=document.getElementById("forma:aff_montoRestanteGL");
            	 var porcentajeDiarioGL=document.getElementById("forma:aff_porcentajeDiarioGL");
            	 var promMontoDiarioGL=document.getElementById("forma:aff_promMontoDiarioGL");
            	 var diasAproxGL=document.getElementById("forma:aff_diasAproxGL");
            	 var glOriginal=document.getElementById("forma:aff_glOriginal");
            	 //aux
            	 var auxMontoInicial=document.getElementById("forma:aff_auxMontoInicial");
            	 var auxMontoGL=document.getElementById("forma:aff_auxMontoGL");
            	 var auxMontoPromDiario=document.getElementById("forma:aff_auxMontoPromDiario");
            	 var auxMontoPromDiario=document.getElementById("forma:aff_auxMontoPromDiario");
            	 var auxPorcentajeInicialGL=document.getElementById("forma:aff_auxPorcentajeInicialGL");
            	 var auxMontoInicialGL=document.getElementById("forma:aff_auxMontoInicialGL");
            	 var auxPorcentajeRestanteGL=document.getElementById("forma:aff_auxPorcentajeRestanteGL");
            	 var auxMontoRestanteGL=document.getElementById("forma:aff_auxMontoRestanteGL");
            	 var auxPromMontoDiarioGL=document.getElementById("forma:aff_auxPromMontoDiarioGL");
            	 var auxPorcentajeDiarioGL=document.getElementById("forma:aff_auxPorcentajeDiarioGL");
            	 var auxDiasAproxGL=document.getElementById("forma:aff_auxDiasAproxGL");
            	 var auxGlOriginal=document.getElementById("forma:aff_auxGlOriginal");
            	 
            	 
            	 var optionPorcentajeVentasDiarias=document.getElementById("forma:optionPorcentajeVentasDiarias");
            	 var auxOptionPorcentajeVentasDiarias=document.getElementById("forma:auxOptionPorcentajeVentasDiarias");
            	 
            	 var optionMontoFijoDiario=document.getElementById("forma:optionMontoFijoDiario");
            	 var auxOptionMontoFijoDiario=document.getElementById("forma:auxOptionMontoFijoDiario");
            	 
            	 var giro = document.getElementById("forma:client_categorycode").value;//giro
            	 var numGiro = giro.substring(0,6);//numero de giro
 
//-------------------------- cuarta version-----------------------------
            	 
            	 auxMontoInicial.value=montoInicial.value;
            	 
             	 optionPorcentajeVentasDiarias.value="0";
             	 optionMontoFijoDiario.value="";
             	 auxOptionPorcentajeVentasDiarias.value=optionPorcentajeVentasDiarias.value;
             	 auxOptionMontoFijoDiario.value=optionMontoFijoDiario.value;
             	 
             	 montoPromDiario.value=valorVentaMensual.value/20;
             	 auxMontoPromDiario.value=montoPromDiario.value;
             	 
             	//glOriginal.value=Math.round((0.15)*valorVentaMensual.value);//gl original antes se calculaba al 15%
             	glOriginal.value=Math.round((0.10)*valorVentaMensual.value);
             	auxGlOriginal.value=glOriginal.value;
             	 if(porcentajeGL.value=="excepcion"){
             		 if(excepcionPorceGL.value > 0 && excepcionPorceGL.value<8 && excepcionPorceGL.value!=null && excepcionPorceGL.value!=""){
             			montoGL.value=Math.round((excepcionPorceGL.value/100)*valorVentaMensual.value);
                     	auxMontoGL.value=montoGL.value;
                     	if(montoGL.value<=5000){
                 			if(montoGL.value>0){
                     			montoGL.value=5000;
                     			auxMontoGL.value=montoGL.value;
                     		}
                    		
                    		if(facturacionMensualMinimaExigida.value<1000000){
                        		 porcentajeInicialGL.value=100;
                        		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
                        		 porcentajeRestanteGL.value=0;
                        		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
                        		 porcentajeDiarioGL.value=2.5;
                        		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
                        	 }else if(facturacionMensualMinimaExigida.value>1000000 && facturacionMensualMinimaExigida.value<10000000){
                        		 porcentajeInicialGL.value=100;
                        		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
                        		 porcentajeRestanteGL.value=0;
                        		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
                        		 porcentajeDiarioGL.value=2.5;
                        		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
                        	 }else if(facturacionMensualMinimaExigida.value>10000000){
                        		 porcentajeInicialGL.value=100;
                        		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
                        		 porcentajeRestanteGL.value=0;
                        		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
                        		 porcentajeDiarioGL.value=2.5;
                        		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
                        	 }
                        	 
                        	 montoInicialGL.value=montoGL.value*(porcentajeInicialGL.value/100);
                        	 auxMontoInicialGL.value=montoInicialGL.value;
                        	 montoRestanteGL.value=0;
                        	 auxMontoRestanteGL.value=montoRestanteGL.value;
                        	 promMontoDiarioGL.value=Math.round(montoPromDiario.value*(porcentajeDiarioGL.value/100));
                        	 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
                        	 
                        	 if(isNaN(montoRestanteGL.value/promMontoDiarioGL.value)){
                        		 diasAproxGL.value=0;// 0/0=NAN por eso la condicion para que ponga cero
                        	 }else{
                        		 diasAproxGL.value=Math.ceil(montoRestanteGL.value/promMontoDiarioGL.value);
                        		 
                        	 }
                        	 auxDiasAproxGL.value=diasAproxGL.value;
                    		
                 		 }else{
    		             	 if(facturacionMensualMinimaExigida.value<1000000){
    		             		 porcentajeInicialGL.value=0;
    		             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
    		             		 porcentajeRestanteGL.value=100;
    		             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
    		             		 porcentajeDiarioGL.value=2.5;
    		             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
    		             	 }else if(facturacionMensualMinimaExigida.value>1000000 && facturacionMensualMinimaExigida.value<10000000){
    		             		 porcentajeInicialGL.value=0;
    		             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
    		             		 porcentajeRestanteGL.value=100;
    		             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
    		             		 porcentajeDiarioGL.value=2.5;
    		             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
    		             	 }else if(facturacionMensualMinimaExigida.value>10000000){
    		             		 porcentajeInicialGL.value=0;
    		             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
    		             		 porcentajeRestanteGL.value=100;
    		             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
    		             		 porcentajeDiarioGL.value=2.5;
    		             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
    		             	 }
    		             	 
    		             	 montoInicialGL.value=montoGL.value*(porcentajeInicialGL.value/100);
    		             	 auxMontoInicialGL.value=montoInicialGL.value;
    		             	 montoRestanteGL.value=montoGL.value-montoInicialGL.value;
    		             	 auxMontoRestanteGL.value=montoRestanteGL.value;
    		             	 promMontoDiarioGL.value=Math.round(montoPromDiario.value*(porcentajeDiarioGL.value/100));
    		             	 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
    		             	 
    		             	 if(isNaN(montoRestanteGL.value/promMontoDiarioGL.value)){
    		             		 diasAproxGL.value=0;// 0/0=NAN por eso la condicion para que ponga cero
    		             	 }else{
    		             		 diasAproxGL.value=Math.ceil(montoRestanteGL.value/promMontoDiarioGL.value);
    		             		 
    		             	 }
    		             	 auxDiasAproxGL.value=diasAproxGL.value;
    		             }
                 		 
                 		 
 	           		 }else{

	            		 if(excepcionPorceGL.value != 0){	 
	            		 
	            			 alert("Disminucion % GL debe ser menor a 8");
	            		 }

 	           			excepcionPorceGL.value=0;
 	           			excepcionPorceGL.disabled=true;

	            		 montoGL.value=0;
	            		 auxMontoGL.value=montoGL.value;
	            		 	 
	            		 montoPromDiario.value=0;
	            		 auxMontoPromDiario.value=montoPromDiario.value;
	            		    	 
	            		 porcentajeInicialGL.value=0;
	            		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	            		 porcentajeRestanteGL.value=0;
	            		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	            		 porcentajeDiarioGL.value=0;
	            		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	            		 	 
	            		 montoInicialGL.value=0;
	            		 auxMontoInicialGL.value=montoInicialGL.value;
	            		 montoRestanteGL.value=0;
	            		 auxMontoRestanteGL.value=montoRestanteGL.value;
	            		 promMontoDiarioGL.value=0;
	            		 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
	            		     	 
	            		 diasAproxGL.value=0;
	            		 auxDiasAproxGL.value=diasAproxGL.value;
	            		 
	            		 //glOriginal.value=Math.round((0.15)*valorVentaMensual.value);////gl original antes se calculaba al 15%
	            		 glOriginal.value=Math.round((0.10)*valorVentaMensual.value);//original
	            		 
 	           		 }
             		 
             	 }else if(porcentajeGL.value=="disminucion"){
             		 if(excepcionPorceGL.value > 0 && excepcionPorceGL.value<8 && excepcionPorceGL.value!=null && excepcionPorceGL.value!=""){
             			montoGL.value=Math.round((excepcionPorceGL.value/100)*valorVentaMensual.value);
                     	auxMontoGL.value=montoGL.value;
                     	if(montoGL.value<=5000){
                 			if(montoGL.value>0){
                     			montoGL.value=5000;
                     			auxMontoGL.value=montoGL.value;
                     		}
                    		
                    		if(facturacionMensualMinimaExigida.value<1000000){
                        		 porcentajeInicialGL.value=100;
                        		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
                        		 porcentajeRestanteGL.value=0;
                        		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
                        		 porcentajeDiarioGL.value=2.5;
                        		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
                        	 }else if(facturacionMensualMinimaExigida.value>1000000 && facturacionMensualMinimaExigida.value<10000000){
                        		 porcentajeInicialGL.value=100;
                        		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
                        		 porcentajeRestanteGL.value=0;
                        		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
                        		 porcentajeDiarioGL.value=2.5;
                        		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
                        	 }else if(facturacionMensualMinimaExigida.value>10000000){
                        		 porcentajeInicialGL.value=100;
                        		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
                        		 porcentajeRestanteGL.value=0;
                        		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
                        		 porcentajeDiarioGL.value=2.5;
                        		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
                        	 }
                        	 
                        	 montoInicialGL.value=montoGL.value*(porcentajeInicialGL.value/100);
                        	 auxMontoInicialGL.value=montoInicialGL.value;
                        	 montoRestanteGL.value=0;
                        	 auxMontoRestanteGL.value=montoRestanteGL.value;
                        	 promMontoDiarioGL.value=Math.round(montoPromDiario.value*(porcentajeDiarioGL.value/100));
                        	 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
                        	 
                        	 if(isNaN(montoRestanteGL.value/promMontoDiarioGL.value)){
                        		 diasAproxGL.value=0;// 0/0=NAN por eso la condicion para que ponga cero
                        	 }else{
                        		 diasAproxGL.value=Math.ceil(montoRestanteGL.value/promMontoDiarioGL.value);
                        		 
                        	 }
                        	 auxDiasAproxGL.value=diasAproxGL.value;
                    		
                 		 }else{
    		             	 if(facturacionMensualMinimaExigida.value<1000000){
    		             		 porcentajeInicialGL.value=0;
    		             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
    		             		 porcentajeRestanteGL.value=100;
    		             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
    		             		 porcentajeDiarioGL.value=2.5;
    		             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
    		             	 }else if(facturacionMensualMinimaExigida.value>1000000 && facturacionMensualMinimaExigida.value<10000000){
    		             		 porcentajeInicialGL.value=0;
    		             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
    		             		 porcentajeRestanteGL.value=100;
    		             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
    		             		 porcentajeDiarioGL.value=2.5;
    		             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
    		             	 }else if(facturacionMensualMinimaExigida.value>10000000){
    		             		 porcentajeInicialGL.value=0;
    		             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
    		             		 porcentajeRestanteGL.value=100;
    		             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
    		             		 porcentajeDiarioGL.value=2.5;
    		             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
    		             	 }
    		             	 
    		             	 montoInicialGL.value=montoGL.value*(porcentajeInicialGL.value/100);
    		             	 auxMontoInicialGL.value=montoInicialGL.value;
    		             	 montoRestanteGL.value=montoGL.value-montoInicialGL.value;
    		             	 auxMontoRestanteGL.value=montoRestanteGL.value;
    		             	 promMontoDiarioGL.value=Math.round(montoPromDiario.value*(porcentajeDiarioGL.value/100));
    		             	 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
    		             	 
    		             	 if(isNaN(montoRestanteGL.value/promMontoDiarioGL.value)){
    		             		 diasAproxGL.value=0;// 0/0=NAN por eso la condicion para que ponga cero
    		             	 }else{
    		             		 diasAproxGL.value=Math.ceil(montoRestanteGL.value/promMontoDiarioGL.value);
    		             		 
    		             	 }
    		             	 auxDiasAproxGL.value=diasAproxGL.value;
    		             }
                 		 
                 		 
 	           		 }else{
	            		 if(excepcionPorceGL.value != 0){	 
	            		 
	            			 alert("Disminucion % GL debe ser menor a 8");
	            		 }
 	           			excepcionPorceGL.value=0;
 	           			excepcionPorceGL.disabled=false;  
	            		 
	            		 montoGL.value=0;
	            		 auxMontoGL.value=montoGL.value;
	            		 	 
	            		 montoPromDiario.value=0;
	            		 auxMontoPromDiario.value=montoPromDiario.value;
	            		    	 
	            		 porcentajeInicialGL.value=0;
	            		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	            		 porcentajeRestanteGL.value=0;
	            		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	            		 porcentajeDiarioGL.value=0;
	            		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	            		 	 
	            		 montoInicialGL.value=0;
	            		 auxMontoInicialGL.value=montoInicialGL.value;
	            		 montoRestanteGL.value=0;
	            		 auxMontoRestanteGL.value=montoRestanteGL.value;
	            		 promMontoDiarioGL.value=0;
	            		 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
	            		     	 
	            		 diasAproxGL.value=0;
	            		 auxDiasAproxGL.value=diasAproxGL.value;
	            		 
	            		 //glOriginal.value=Math.round((0.15)*valorVentaMensual.value);//gl original antes se calculaba al 15%
	            		 glOriginal.value=Math.round((0.10)*valorVentaMensual.value);
	            		 auxGlOriginal.value=glOriginal.value;
	            		 
 	           		 }
             		 
             	 }else{
             		 if(numGiro!="005399"){
             			 
	             		 excepcionPorceGL.value="";
	             		 montoGL.value=Math.ceil((porcentajeGL.value/100)*valorVentaMensual.value);
	             		 auxMontoGL.value=montoGL.value;
	             		 if(montoGL.value<5000){
	             			if(montoGL.value>0){
                     			montoGL.value=5000;
                     			auxMontoGL.value=montoGL.value;
                     		}
	                		
	                		if(facturacionMensualMinimaExigida.value<1000000){
	                    		 //porcentajeInicialGL.value=100;//antes
	                			 porcentajeInicialGL.value=0;
	                    		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	                    		 //porcentajeRestanteGL.value=0;//antes
	                    		 porcentajeRestanteGL.value=100;
	                    		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	                    		 porcentajeDiarioGL.value=2.5;
	                    		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	                    	 }else if(facturacionMensualMinimaExigida.value>1000000 && facturacionMensualMinimaExigida.value<10000000){
	                    		 //porcentajeInicialGL.value=100;//antes
	                    		 porcentajeInicialGL.value=0;
	                    		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	                    		 //porcentajeRestanteGL.value=0;//antes
	                    		 porcentajeRestanteGL.value=100;
	                    		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	                    		 porcentajeDiarioGL.value=2.5;
	                    		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	                    	 }else if(facturacionMensualMinimaExigida.value>10000000){
	                    		 //porcentajeInicialGL.value=100;//antes
	                    		 porcentajeInicialGL.value=0;
	                    		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	                    		 //porcentajeRestanteGL.value=0;//antes
	                    		 porcentajeRestanteGL.value=100;
	                    		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	                    		 porcentajeDiarioGL.value=2.5;
	                    		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	                    	 }
	                    	 
	                    	 montoInicialGL.value=montoGL.value*(porcentajeInicialGL.value/100);
	                    	 auxMontoInicialGL.value=montoInicialGL.value;
	                    	 //montoRestanteGL.value=0;//antes
	                    	 montoRestanteGL.value=montoGL.value-montoInicialGL.value;
	                    	 auxMontoRestanteGL.value=montoRestanteGL.value;
	                    	 promMontoDiarioGL.value=Math.round(montoPromDiario.value*(porcentajeDiarioGL.value/100));
	                    	 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
	                    	 
	                    	 if(isNaN(montoRestanteGL.value/promMontoDiarioGL.value)){
	                    		 diasAproxGL.value=0;// 0/0=NAN por eso la condicion para que ponga cero
	                    	 }else{
	                    		 diasAproxGL.value=Math.ceil(montoRestanteGL.value/promMontoDiarioGL.value);
	                    		 
	                    	 }
	                    	 auxDiasAproxGL.value=diasAproxGL.value;
	                		
	             		 }else{
			             	 if(facturacionMensualMinimaExigida.value<1000000){
			             		 porcentajeInicialGL.value=0;
			             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
			             		 porcentajeRestanteGL.value=100;
			             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
			             		 porcentajeDiarioGL.value=2.5;
			             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
			             	 }else if(facturacionMensualMinimaExigida.value>1000000 && facturacionMensualMinimaExigida.value<10000000){
			             		 porcentajeInicialGL.value=0;
			             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
			             		 porcentajeRestanteGL.value=100;
			             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
			             		 porcentajeDiarioGL.value=2.5;
			             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
			             	 }else if(facturacionMensualMinimaExigida.value>10000000){
			             		 porcentajeInicialGL.value=0;
			             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
			             		 porcentajeRestanteGL.value=100;
			             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
			             		 porcentajeDiarioGL.value=2.5;
			             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
			             	 }
			             	 
			             	 montoInicialGL.value=montoGL.value*(porcentajeInicialGL.value/100);
			             	 auxMontoInicialGL.value=montoInicialGL.value;
			             	 montoRestanteGL.value=montoGL.value-montoInicialGL.value;
			             	 auxMontoRestanteGL.value=montoRestanteGL.value;
			             	 promMontoDiarioGL.value=Math.round(montoPromDiario.value*(porcentajeDiarioGL.value/100));
			             	 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
			             	 
			             	 if(isNaN(montoRestanteGL.value/promMontoDiarioGL.value)){
			             		 diasAproxGL.value=0;// 0/0=NAN por eso la condicion para que ponga cero
			             	 }else{
			             		 diasAproxGL.value=Math.ceil(montoRestanteGL.value/promMontoDiarioGL.value);
			             		 
			             	 }
			             	 auxDiasAproxGL.value=diasAproxGL.value;
			             }
	             	 }else{			//else para cuando sea GL con agregadores
	             		 
	             		 excepcionPorceGL.value="";
	             		 if(valorVentaMensual.value<=100000000){
	             			montoGL.value=500000;
	             		 }else if(valorVentaMensual.value>100000000 && valorVentaMensual.value<=500000000){
	             			montoGL.value=1000000;
	             		 }else if(valorVentaMensual.value>500000000){
	             			montoGL.value=1500000;
	             		 }
	             		 
	             		 auxMontoGL.value=montoGL.value;
	             		 
	             		if(facturacionMensualMinimaExigida.value<1000000){
		             		 porcentajeInicialGL.value=0;
		             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
		             		 porcentajeRestanteGL.value=100;
		             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
		             		 porcentajeDiarioGL.value=2.5;
		             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
		             	 }else if(facturacionMensualMinimaExigida.value>1000000 && facturacionMensualMinimaExigida.value<10000000){
		             		 porcentajeInicialGL.value=0;
		             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
		             		 porcentajeRestanteGL.value=100;
		             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
		             		 porcentajeDiarioGL.value=2.5;
		             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
		             	 }else if(facturacionMensualMinimaExigida.value>10000000){
		             		 porcentajeInicialGL.value=0;
		             		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
		             		 porcentajeRestanteGL.value=100;
		             		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
		             		 porcentajeDiarioGL.value=2.5;
		             		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
		             	 }
		             	 
		             	 montoInicialGL.value=montoGL.value*(porcentajeInicialGL.value/100);
		             	 auxMontoInicialGL.value=montoInicialGL.value;
		             	 montoRestanteGL.value=montoGL.value-montoInicialGL.value;
		             	 auxMontoRestanteGL.value=montoRestanteGL.value;
		             	 promMontoDiarioGL.value=Math.round(montoPromDiario.value*(porcentajeDiarioGL.value/100));
		             	 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
		             	 
		             	 if(isNaN(montoRestanteGL.value/promMontoDiarioGL.value)){
		             		 diasAproxGL.value=0;// 0/0=NAN por eso la condicion para que ponga cero
		             	 }else{
		             		 diasAproxGL.value=Math.ceil(montoRestanteGL.value/promMontoDiarioGL.value);

		             		 if(!isFinite(diasAproxGL.value)){
		             			
		             			diasAproxGL.value=0;
		             		 }
		             	 }
		             	 auxDiasAproxGL.value=diasAproxGL.value;
	               		
		             		 
	             	 }
             	 }
             	 
             }
             /**
              * Funcion para habilitar/deshabilitar la opcion de cashback en base al producto, solucion, divisa y alianza
              * llama a onchangeCashback
              */
             function inicializarCashback(){ 
            	var checkboxCashback 	 = document.getElementById("forma:aff_cashback");
            	var cashbackSeleccionado = document.getElementById("forma:aff_cashback").checked; 
            	var producto 			 = document.getElementById("forma:affiliation_productdesc").value;
            	var solucion 		 	 = document.getElementById("forma:affiliation_soluciontype").value;
            	var divisa 				 = document.getElementById("forma:affiliation_currency").value;
            	var alianza 			 = document.getElementById("forma:affiliation_alliance").value;
            	
            			if(producto == "Interredes" || producto == "Terminal punto de venta"){
                		if(solucion == "Internet"){
                    		if(divisa == "Pesos"){
                    			if(alianza == "Ninguna"){
	                    			if (cashbackSeleccionado == true){
	                    				checkboxCashback.disabled = false;
	                    				checkboxCashback.checked = true;
	                    			}else{//si no estaba seleccionado cashback
	                    				checkboxCashback.disabled = false;
	                    			} 
                    			}else {//si no cumple con la condicion de la alianza
                        				checkboxCashback.disabled = true;
                        				checkboxCashback.checked = false;
                                }
                    		}else {//si no cumple con la condicion de la divisa
                    			checkboxCashback.disabled = true;
                    			checkboxCashback.checked = false;
                    		}
                    	}else {//si no cmple con la condicion de la solucion
                    		checkboxCashback.disabled = true;
                    		checkboxCashback.checked = false;
                    	}
                	}else{//si no cumple con las condiciones de producto
                    	    checkboxCashback.disabled = true;
                        	checkboxCashback.checked = false;
                }
                	onchangeCashback();
             }

             /**
              * Es llamada por inicializarCashback
              * Funcion que habilita/deshabilita la comision por cashback en la tabla de comisiones cuando cambia la seleccion del servicio
              */
             function onchangeCashback(){
            	 var checkboxCashback = document.getElementById("forma:aff_cashback");
            	 var comisionCargoCashback = document.getElementById("forma:aff_commCbChrg");
            	 
             	if(checkboxCashback.checked == false){
	           		 comisionCargoCashback.value = "0.0";
	           		 comisionCargoCashback.disabled = true;
	          	}else{
	           		 comisionCargoCashback.disabled = false;
             	}
             }
             
             function onchangeGiro(){ 
            	 var producto 	= document.getElementById("forma:affiliation_productdesc").value;
            	 var giro		= document.getElementById("forma:client_categorycode").value;//giro            	
            	 var giro		= giro.substring(0,6);//numero de giro
            	 var checkboxCashback = document.getElementById("forma:aff_cashback");
            	 var bandera=isGiroCashBack(giro);
            	 var seleccion= valorSeleccionGarantiaLiquida();
            	 
            	 validarDescripcionGiro();// valida que no se modifique la descripcion del giro seleccionado
            	 checkboxCashback.disabled = !bandera;
            	 
            	 if(seleccion==1){
            		 inicializarFianza();
            	 }
            	 
            	 inicializarAlianza();
            	 inicializarPaquete();
            	 inicializarGarantiaLiquida();//garantia liquida
             }
             //garantia liquida
             function onchangeBuroInterno(){ 
            	 var buroInterno = document.getElementById("forma:affiliation_internalcredithistory");
            	 var seleccion= valorSeleccionGarantiaLiquida();
                 inicializarGarantiaLiquida();//garantia liquida
            	 if(seleccion==1){
            		 inicializarFianza();
            	 }
            	 
             }
           //garantia liquida
             function onchangeBuroExterno(){ 
            	 var buroExterno = document.getElementById("forma:affiliation_externalcredithistory");
            	 var seleccion= valorSeleccionGarantiaLiquida();
            	 inicializarGarantiaLiquida();//garantia liquida

            	 if(seleccion==1){
            		 inicializarFianza();
            	 }
            	 
             }
             
           //garantia liquida
             function onchangeTecladoAbeirto(){ 
            	 var tecladoAbierto = document.getElementById("forma:affiliation_openkey");
            	 inicializarGarantiaLiquida();//garantia liquida
            	 
            	 inicializarFianza();
            	 
             }
             
             
           //Garantia Liquida: S/N monto Inicial, si= calcula   no= bloquea
             function onchangePorcentajeGL(){ 
            	 //alert("dentro de porcentaje GL")
            	 var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');
            	 var porcentajeGL=document.getElementById("forma:aff_porcentajeGL");
            	 var excepcionPorcentajeGL=document.getElementById("forma:aff_excepcionPorceGL");
            	 var montoGL = document.getElementById("forma:aff_montoGL");
            	 var montoPromDiario = document.getElementById("forma:aff_montoPromDiario");
            	 var porcentajeInicialGL=document.getElementById("forma:aff_porcentajeInicialGL");
            	 var porcentajeRestanteGL=document.getElementById("forma:aff_porcentajeRestanteGL");
            	 var porcentajeDiarioGL=document.getElementById("forma:aff_porcentajeDiarioGL");
            	 var montoInicialGL=document.getElementById("forma:aff_montoInicialGL");
            	 var montoRestanteGL=document.getElementById("forma:aff_montoRestanteGL");
            	 var promMontoDiarioGL=document.getElementById("forma:aff_promMontoDiarioGL");
            	 var diasAproxGL=document.getElementById("forma:aff_diasAproxGL");
            	 var glOriginal=document.getElementById("forma:aff_glOriginal");
            	 
            	 var comentariosDisminucionExcepcionGL=document.getElementById("forma:aff_comentariosDisminucionExcepcionGL");
            	 
            	 //Esto campos sustituyen a comentariosDisminucionExcepcionGL
            	 var exencionConvenienciaComercialVIP=document.getElementById("forma:exencionConvenienciaComercialVIP");
            	 var exencionOtros=document.getElementById("forma:exencionOtros");
            	 var exencionJustificacion=document.getElementById("forma:exencionJustificacion");
            	//conocimiento del cliente
            	 var solvenciaEconimicaSi=document.getElementById("forma:solvenciaEconimicaSi");
            	 var solvenciaEconimicaNo=document.getElementById("forma:solvenciaEconimicaNo");
            	 var visitaOcularRecienteSi=document.getElementById("forma:visitaOcularRecienteSi");
            	 var visitaOcularRecienteNo=document.getElementById("forma:visitaOcularRecienteNo");
            	 var riesgoReputacionalOperacionalSi=document.getElementById("forma:riesgoReputacionalOperacionalSi");
            	 var riesgoReputacionalOperacionalNo=document.getElementById("forma:riesgoReputacionalOperacionalNo");
            	 var descBienServicioOfrece=document.getElementById("forma:descBienServicioOfrece");
            	 var territorioNacionalSi=document.getElementById("forma:territorioNacionalSi");
            	 var territorioNacionalNo=document.getElementById("forma:territorioNacionalNo");
            	 var territorioNacionalEspecificacion=document.getElementById("forma:territorioNacionalEspecificacion");
            	 var enNombreDeUnTerceroSi=document.getElementById("forma:enNombreDeUnTerceroSi");
            	 var enNombreDeUnTerceroNo=document.getElementById("forma:enNombreDeUnTerceroNo");
            	 var enNombreDeUnTerceroEspecificacion=document.getElementById("forma:enNombreDeUnTerceroEspecificacion");
            	 var antiguedadAnio=document.getElementById("forma:antiguedadAnio");
            	 var antiguedadMeses=document.getElementById("forma:antiguedadMeses");
            	 
            	 //aux
            	  var auxMontoGL=document.getElementById("forma:aff_auxMontoGL");
            	  var auxMontoPromDiario=document.getElementById("forma:aff_auxMontoPromDiario");
            	  var auxPorcentajeInicialGL=document.getElementById("forma:aff_auxPorcentajeInicialGL");
            	  var auxMontoInicialGL=document.getElementById("forma:aff_auxMontoInicialGL");
            	  var auxPorcentajeRestanteGL=document.getElementById("forma:aff_auxPorcentajeRestanteGL");
            	  var auxMontoRestanteGL=document.getElementById("forma:aff_auxMontoRestanteGL");
            	  var auxPromMontoDiarioGL=document.getElementById("forma:aff_auxPromMontoDiarioGL");
            	  var auxPorcentajeDiarioGL=document.getElementById("forma:aff_auxPorcentajeDiarioGL");
            	  var auxDiasAproxGL=document.getElementById("forma:aff_auxDiasAproxGL");
            	  var auxGlOriginal=document.getElementById("forma:aff_auxGlOriginal");
            	  

            	 if(porcentajeGL.value=="excepcion"){
            		 if(enNombreDeUnTerceroSi.checked==false && (enNombreDeUnTerceroEspecificacion.value=="" || enNombreDeUnTerceroEspecificacion.value==null)){
            			 enNombreDeUnTerceroEspecificacion.disabled=true;
            			 enNombreDeUnTerceroEspecificacion.value=""; 
            		 }
            		 if(enNombreDeUnTerceroNo.checked==true){
            			 enNombreDeUnTerceroEspecificacion.disabled=true;
            			 enNombreDeUnTerceroEspecificacion.value="";	 
            		 }
            		 if(territorioNacionalNo.checked==false && (territorioNacionalEspecificacion.value=="" || territorioNacionalEspecificacion.value==null)){
            			 territorioNacionalEspecificacion.disabled=true;
            			 territorioNacionalEspecificacion.value=""; 
            		 }
            		 if(territorioNacionalSi.checked==true){
            			 territorioNacionalEspecificacion.disabled=true;
            			 territorioNacionalEspecificacion.value=""; 
            		 }
//        			 territorioNacionalEspecificacion.value="";
//            		 enNombreDeUnTerceroEspecificacion.disabled=true;
//        			 territorioNacionalEspecificacion.disabled=true;
            		 mostrarComentariosDisminucionExcepcionGL("block");        			 
            		 if(excepcionPorcentajeGL.value=="" || excepcionPorcentajeGL.value==null){
	            		 excepcionPorcentajeGL.value=0;
	            		 //excepcionPorcentajeGL.disabled=false;//original        		 
	            		 excepcionPorcentajeGL.disabled=true;
	            		 
	            		 montoGL.value=0;
	            		 auxMontoGL.value=montoGL.value;
	            		 	 
	            		 montoPromDiario.value=0;
	            		 auxMontoPromDiario.value=montoPromDiario.value;
	            		    	 
	            		 porcentajeInicialGL.value=0;
	            		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	            		 porcentajeRestanteGL.value=0;
	            		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	            		 porcentajeDiarioGL.value=0;
	            		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	            		 	 
	            		 montoInicialGL.value=0;
	            		 auxMontoInicialGL.value=montoInicialGL.value;
	            		 montoRestanteGL.value=0;
	            		 auxMontoRestanteGL.value=montoRestanteGL.value;
	            		 promMontoDiarioGL.value=0;
	            		 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
	            		     	 
	            		 diasAproxGL.value=0;
	            		 auxDiasAproxGL.value=diasAproxGL.value;
	            		 
	            		 //glOriginal.value=Math.round((0.15)*valorVentaMensual.value);//gl original antes se calculaba al 15%
	            		 glOriginal.value=Math.round((0.10)*valorVentaMensual.value);
	            		 auxGlOriginal.value=glOriginal.value;
	            		 
	            		 
	            				 
	            		 
            		 }else{
            			 excepcionPorcentajeGL.disabled=true;
            			 calcularGarantiaLiquida();
            			 
            		 }
            	 }else if(porcentajeGL.value=="disminucion"){
            		 if(enNombreDeUnTerceroSi.checked==false && (enNombreDeUnTerceroEspecificacion.value=="" || enNombreDeUnTerceroEspecificacion.value==null)){
            			 enNombreDeUnTerceroEspecificacion.disabled=true;
            			 enNombreDeUnTerceroEspecificacion.value=""; 
            		 }
            		 if(enNombreDeUnTerceroNo.checked==true){
            			 enNombreDeUnTerceroEspecificacion.disabled=true;
            			 enNombreDeUnTerceroEspecificacion.value="";	 
            		 }
            		 if(territorioNacionalNo.checked==false && (territorioNacionalEspecificacion.value=="" || territorioNacionalEspecificacion.value==null)){
            			 territorioNacionalEspecificacion.disabled=true;
            			 territorioNacionalEspecificacion.value=""; 
            		 }
            		 if(territorioNacionalSi.checked==true){
            			 territorioNacionalEspecificacion.disabled=true;
            			 territorioNacionalEspecificacion.value=""; 
            		 }
            		 mostrarComentariosDisminucionExcepcionGL("block"); 
//            		 enNombreDeUnTerceroEspecificacion.value="";
//        			 territorioNacionalEspecificacion.value="";
//            		 enNombreDeUnTerceroEspecificacion.disabled=true;
//        			 territorioNacionalEspecificacion.disabled=true;
            		 if(excepcionPorcentajeGL.value=="" || excepcionPorcentajeGL.value==null){
	            		 excepcionPorcentajeGL.value=0;
	            		 excepcionPorcentajeGL.disabled=false;            		 
	            		 
	            		 montoGL.value=0;
	            		 auxMontoGL.value=montoGL.value;
	            		 	 
	            		 montoPromDiario.value=0;
	            		 auxMontoPromDiario.value=montoPromDiario.value;
	            		    	 
	            		 porcentajeInicialGL.value=0;
	            		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	            		 porcentajeRestanteGL.value=0;
	            		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	            		 porcentajeDiarioGL.value=0;
	            		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	            		 	 
	            		 montoInicialGL.value=0;
	            		 auxMontoInicialGL.value=montoInicialGL.value;
	            		 montoRestanteGL.value=0;
	            		 auxMontoRestanteGL.value=montoRestanteGL.value;
	            		 promMontoDiarioGL.value=0;
	            		 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
	            		     	 
	            		 diasAproxGL.value=0;
	            		 auxDiasAproxGL.value=diasAproxGL.value;
	            		 
	            		 //glOriginal.value=Math.round((0.15)*valorVentaMensual.value);//gl original antes se calculaba al 15%
	            		 glOriginal.value=Math.round((0.10)*valorVentaMensual.value);
	            		 auxGlOriginal.value=glOriginal.value;
	            		 
	            				 
	            		 
            		 }else{
            			 excepcionPorcentajeGL.disabled=false;
            			 calcularGarantiaLiquida();
            			 
            		 }
            	 }
            	//Se agrego por que el usuario queria ver los comentarios en la opcion de 8%
            	 //else if(porcentajeGL.value=="8"){
            	 else if(porcentajeGL.value=="5"){
            		 if(enNombreDeUnTerceroSi.checked==false && (enNombreDeUnTerceroEspecificacion.value=="" || enNombreDeUnTerceroEspecificacion.value==null)){
            			 enNombreDeUnTerceroEspecificacion.disabled=true;
            			 enNombreDeUnTerceroEspecificacion.value=""; 
            		 }
            		 if(enNombreDeUnTerceroNo.checked==true){
            			 enNombreDeUnTerceroEspecificacion.disabled=true;
            			 enNombreDeUnTerceroEspecificacion.value="";	 
            		 }
            		 if(territorioNacionalNo.checked==false && (territorioNacionalEspecificacion.value=="" || territorioNacionalEspecificacion.value==null)){
            			 territorioNacionalEspecificacion.disabled=true;
            			 territorioNacionalEspecificacion.value=""; 
            		 }
            		 if(territorioNacionalSi.checked==true){
            			 territorioNacionalEspecificacion.disabled=true;
            			 territorioNacionalEspecificacion.value=""; 
            		 }
            		 mostrarComentariosDisminucionExcepcionGL("block"); 
            		 excepcionPorcentajeGL.value="";
            		 excepcionPorcentajeGL.disabled=true;
            		 excepcionPorcentajeGL.value="";
            		 calcularGarantiaLiquida();
            		 
            	 }else{
            		 comentariosDisminucionExcepcionGL.value="";
            		 exencionConvenienciaComercialVIP.checked = false;
            		 exencionOtros.value="";
            		 exencionJustificacion.value="";
            		//conocimiento del cliente
            		 solvenciaEconimicaSi.checked = false;
            		 solvenciaEconimicaNo.checked = false;
            		 visitaOcularRecienteSi.checked = false;
            		 visitaOcularRecienteNo.checked = false;
            		 riesgoReputacionalOperacionalSi.checked = false;
            		 riesgoReputacionalOperacionalNo.checked = false;
            		 descBienServicioOfrece.value="";
            		 territorioNacionalSi.checked = false;
            		 territorioNacionalNo.checked = false;
            		 territorioNacionalEspecificacion.value="";
            		 enNombreDeUnTerceroSi.checked = false;
            		 enNombreDeUnTerceroNo.checked = false;
            		 enNombreDeUnTerceroEspecificacion.value="";
            		 antiguedadAnio.value="";
            		 antiguedadMeses.value="";
            		 mostrarComentariosDisminucionExcepcionGL("none"); 
            		 excepcionPorcentajeGL.value="";
            		 excepcionPorcentajeGL.disabled=true;
            		 excepcionPorcentajeGL.value="";
            		 calcularGarantiaLiquida();
            		 
            	 }
            		 
             }
             
           //Garantia Liquida: S/N monto Inicial, si= calcula   no= bloquea
             function onclickPorcentajeGL(){ 

            	 var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');
            	 var porcentajeGL=document.getElementById("forma:aff_porcentajeGL");
            	 var excepcionPorcentajeGL=document.getElementById("forma:aff_excepcionPorceGL");
            	 var montoGL = document.getElementById("forma:aff_montoGL");
            	 var montoPromDiario = document.getElementById("forma:aff_montoPromDiario");
            	 var porcentajeInicialGL=document.getElementById("forma:aff_porcentajeInicialGL");
            	 var porcentajeRestanteGL=document.getElementById("forma:aff_porcentajeRestanteGL");
            	 var porcentajeDiarioGL=document.getElementById("forma:aff_porcentajeDiarioGL");
            	 var montoInicialGL=document.getElementById("forma:aff_montoInicialGL");
            	 var montoRestanteGL=document.getElementById("forma:aff_montoRestanteGL");
            	 var promMontoDiarioGL=document.getElementById("forma:aff_promMontoDiarioGL");
            	 var diasAproxGL=document.getElementById("forma:aff_diasAproxGL");
            	 var glOriginal=document.getElementById("forma:aff_glOriginal");
            	 var comentariosDisminucionExcepcionGL=document.getElementById("forma:aff_comentariosDisminucionExcepcionGL");
            	 //Esto campos sustituyen a comentariosDisminucionExcepcionGL
            	 var exencionConvenienciaComercialVIP=document.getElementById("forma:exencionConvenienciaComercialVIP");
            	 var exencionOtros=document.getElementById("forma:exencionOtros");
            	 var exencionJustificacion=document.getElementById("forma:exencionJustificacion");
            	//conocimiento del cliente
            	 var solvenciaEconimicaSi=document.getElementById("forma:solvenciaEconimicaSi");
            	 var solvenciaEconimicaNo=document.getElementById("forma:solvenciaEconimicaNo");
            	 var visitaOcularRecienteSi=document.getElementById("forma:visitaOcularRecienteSi");
            	 var visitaOcularRecienteNo=document.getElementById("forma:visitaOcularRecienteNo");
            	 var riesgoReputacionalOperacionalSi=document.getElementById("forma:riesgoReputacionalOperacionalSi");
            	 var riesgoReputacionalOperacionalNo=document.getElementById("forma:riesgoReputacionalOperacionalNo");
            	 var descBienServicioOfrece=document.getElementById("forma:descBienServicioOfrece");
            	 var territorioNacionalSi=document.getElementById("forma:territorioNacionalSi");
            	 var territorioNacionalNo=document.getElementById("forma:territorioNacionalNo");
            	 var territorioNacionalEspecificacion=document.getElementById("forma:territorioNacionalEspecificacion");
            	 var enNombreDeUnTerceroSi=document.getElementById("forma:enNombreDeUnTerceroSi");
            	 var enNombreDeUnTerceroNo=document.getElementById("forma:enNombreDeUnTerceroNo");
            	 var enNombreDeUnTerceroEspecificacion=document.getElementById("forma:enNombreDeUnTerceroEspecificacion");
            	 var antiguedadAnio=document.getElementById("forma:antiguedadAnio");
            	 var antiguedadMeses=document.getElementById("forma:antiguedadMeses");
            	 //aux
            	  var auxMontoGL=document.getElementById("forma:aff_auxMontoGL");
            	  var auxMontoPromDiario=document.getElementById("forma:aff_auxMontoPromDiario");
            	  var auxPorcentajeInicialGL=document.getElementById("forma:aff_auxPorcentajeInicialGL");
            	  var auxMontoInicialGL=document.getElementById("forma:aff_auxMontoInicialGL");
            	  var auxPorcentajeRestanteGL=document.getElementById("forma:aff_auxPorcentajeRestanteGL");
            	  var auxMontoRestanteGL=document.getElementById("forma:aff_auxMontoRestanteGL");
            	  var auxPromMontoDiarioGL=document.getElementById("forma:aff_auxPromMontoDiarioGL");
            	  var auxPorcentajeDiarioGL=document.getElementById("forma:aff_auxPorcentajeDiarioGL");
            	  var auxDiasAproxGL=document.getElementById("forma:aff_auxDiasAproxGL");
            	  var auxGlOriginal=document.getElementById("forma:aff_auxGlOriginal");
            	 
            	 if(porcentajeGL.value=="excepcion"){
            		 if(excepcionPorcentajeGL.value=="" || excepcionPorcentajeGL.value==null){
	            		 excepcionPorcentajeGL.value=0;
	            		 excepcionPorcentajeGL.disabled=true;
	            		 
	            		 montoGL.value=0;
	            		 auxMontoGL.value=montoGL.value;
	            		 	 
	            		 montoPromDiario.value=0;
	            		 auxMontoPromDiario.value=montoPromDiario.value;
	            		    	 
	            		 porcentajeInicialGL.value=0;
	            		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	            		 porcentajeRestanteGL.value=0;	
	            		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	            		 porcentajeDiarioGL.value=0;
	            		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	            		 	 
	            		 montoInicialGL.value=0;
	            		 auxMontoInicialGL.value=montoInicialGL.value;
	            		 montoRestanteGL.value=0;
	            		 auxMontoRestanteGL.value=montoRestanteGL.value;
	            		 promMontoDiarioGL.value=0;
	            		 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
	            		     	 
	            		 diasAproxGL.value=0;
	            		 auxDiasAproxGL.value=diasAproxGL.value;
	            		 
	            		 //glOriginal.value=Math.round((0.15)*valorVentaMensual.value);//gl original antes se calculaba al 15%
	            		 glOriginal.value=Math.round((0.10)*valorVentaMensual.value);
	            		 auxGlOriginal.value=glOriginal.value;
	            		 
            		 }else{
            			 excepcionPorcentajeGL.value=0;
            			 excepcionPorcentajeGL.disabled=true; 
	            		 
	            		 montoGL.value=0;
	            		 auxMontoGL.value=montoGL.value;
	            		 	 
	            		 montoPromDiario.value=0;
	            		 auxMontoPromDiario.value=montoPromDiario.value;
	            		    	 
	            		 porcentajeInicialGL.value=0;
	            		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	            		 porcentajeRestanteGL.value=0;
	            		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	            		 porcentajeDiarioGL.value=0;
	            		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	            		 	 
	            		 montoInicialGL.value=0;
	            		 auxMontoInicialGL.value=montoInicialGL.value;
	            		 montoRestanteGL.value=0;
	            		 auxMontoRestanteGL.value=montoRestanteGL.value;
	            		 promMontoDiarioGL.value=0;
	            		 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
	            		     	 
	            		 diasAproxGL.value=0;
	            		 auxDiasAproxGL.value=diasAproxGL.value;
	            		 
	            		 //glOriginal.value=Math.round((0.15)*valorVentaMensual.value);//gl original antes se calculaba al 15%
	            		 glOriginal.value=Math.round((0.10)*valorVentaMensual.value);
	            		 auxGlOriginal.value=glOriginal.value;
	            		 
            			 excepcionPorcentajeGL.disabled=true; 
            		 }
            	 }else if(porcentajeGL.value=="disminucion"){
            		 if(excepcionPorcentajeGL.value=="" || excepcionPorcentajeGL.value==null){
	            		 excepcionPorcentajeGL.value=0;
	            		 excepcionPorcentajeGL.disabled=false;            		 
	            		 
	            		 montoGL.value=0;
	            		 auxMontoGL.value=montoGL.value;
	            		 	 
	            		 montoPromDiario.value=0;
	            		 auxMontoPromDiario.value=montoPromDiario.value;
	            		    	 
	            		 porcentajeInicialGL.value=0;
	            		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	            		 porcentajeRestanteGL.value=0;	
	            		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	            		 porcentajeDiarioGL.value=0;
	            		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	            		 	 
	            		 montoInicialGL.value=0;
	            		 auxMontoInicialGL.value=montoInicialGL.value;
	            		 montoRestanteGL.value=0;
	            		 auxMontoRestanteGL.value=montoRestanteGL.value;
	            		 promMontoDiarioGL.value=0;
	            		 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
	            		     	 
	            		 diasAproxGL.value=0;
	            		 auxDiasAproxGL.value=diasAproxGL.value;
	            		 
	            		 //glOriginal.value=Math.round((0.15)*valorVentaMensual.value);//gl original antes se calculaba al 15%
	            		 glOriginal.value=Math.round((0.10)*valorVentaMensual.value);
	            		 auxGlOriginal.value=glOriginal.value;
	            		 
            		 }else{
            			 excepcionPorcentajeGL.value=0;
	            		 excepcionPorcentajeGL.disabled=false;            		 
	            		 
	            		 montoGL.value=0;
	            		 auxMontoGL.value=montoGL.value;
	            		 	 
	            		 montoPromDiario.value=0;
	            		 auxMontoPromDiario.value=montoPromDiario.value;
	            		    	 
	            		 porcentajeInicialGL.value=0;
	            		 auxPorcentajeInicialGL.value=porcentajeInicialGL.value;
	            		 porcentajeRestanteGL.value=0;
	            		 auxPorcentajeRestanteGL.value=porcentajeRestanteGL.value;
	            		 porcentajeDiarioGL.value=0;
	            		 auxPorcentajeDiarioGL.value=porcentajeDiarioGL.value;
	            		 	 
	            		 montoInicialGL.value=0;
	            		 auxMontoInicialGL.value=montoInicialGL.value;
	            		 montoRestanteGL.value=0;
	            		 auxMontoRestanteGL.value=montoRestanteGL.value;
	            		 promMontoDiarioGL.value=0;
	            		 auxPromMontoDiarioGL.value=promMontoDiarioGL.value;
	            		     	 
	            		 diasAproxGL.value=0;
	            		 auxDiasAproxGL.value=diasAproxGL.value;
	            		 
	            		 //glOriginal.value=Math.round((0.15)*valorVentaMensual.value);//gl original antes se calculaba al 15%
	            		 glOriginal.value=Math.round((0.10)*valorVentaMensual.value);
	            		 auxGlOriginal.value=glOriginal.value;
	            		 
            			 excepcionPorcentajeGL.disabled=false;
            		 }
            	 }else{
            		 comentariosDisminucionExcepcionGL.value="";
            		//Esto campos sustituyen a comentariosDisminucionExcepcionGL
            		 exencionConvenienciaComercialVIP.checked = false;
            		 exencionOtros.value="";
            		 exencionJustificacion.value="";
            		//conocimiento del cliente
            		 solvenciaEconimicaSi.checked = false;
            		 solvenciaEconimicaNo.checked = false;
            		 visitaOcularRecienteSi.checked = false;
            		 visitaOcularRecienteNo.checked = false;
            		 riesgoReputacionalOperacionalSi.checked = false;
            		 riesgoReputacionalOperacionalNo.checked = false;
            		 descBienServicioOfrece.value="";
            		 territorioNacionalSi.checked = false;
            		 territorioNacionalNo.checked = false;
            		 territorioNacionalEspecificacion.value="";
            		 enNombreDeUnTerceroSi.checked = false;
            		 enNombreDeUnTerceroNo.checked = false;
            		 enNombreDeUnTerceroEspecificacion.value="";
            		 antiguedadAnio.value="";
            		 antiguedadMeses.value="";
            		 excepcionPorcentajeGL.value="";
            		 excepcionPorcentajeGL.disabled=true;
            		 excepcionPorcentajeGL.value="";
            		 calcularGarantiaLiquida();
            	 }
            		 
             }
                       
             //Garantia Liquida: S/N monto Inicial, si= calcula   no= bloquea
             function onchangeMontoInicial(){ 

            	 var montoInicial=document.getElementById("forma:aff_montoInicial");
            	 var montoPromDiario=document.getElementById("forma:aff_montoPromDiario");
            	 var porcentajeGL=document.getElementById("forma:aff_porcentajeGL");
            	 var excepcionPorcentajeGL=document.getElementById("forma:aff_excepcionPorceGL");
            	 var montoGL=document.getElementById("forma:aff_montoGL");
            	 var porcentajeInicialGL=document.getElementById("forma:aff_porcentajeInicialGL");
            	 var montoInicialGL=document.getElementById("forma:aff_montoInicialGL");
            	 var porcentajeRestanteGL=document.getElementById("forma:aff_porcentajeRestanteGL");
            	 var montoRestanteGL=document.getElementById("forma:aff_montoRestanteGL");
            	 var porcentajeDiarioGL=document.getElementById("forma:aff_porcentajeDiarioGL");
            	 var promMontoDiarioGL=document.getElementById("forma:aff_promMontoDiarioGL");
            	 var optionPorcentajeVentasDiarias=document.getElementById("forma:optionPorcentajeVentasDiarias");
            	 var optionMontoFijoDiario=document.getElementById("forma:optionMontoFijoDiario");
            	 var auxMontoInicial=document.getElementById("forma:aff_auxMontoInicial");
            	 var glOriginal=document.getElementById("forma:aff_glOriginal");
            	 
            	 if(montoInicial.value=="true"){
            		 auxMontoInicial.value=montoInicial.value;
            		 //porcentajeGL.value="15";
            		 porcentajeGL.value="10";
            		 porcentajeGL.disabled=false;
            		 calcularGarantiaLiquida();
            	 }else{
            		 auxMontoInicial.value=montoInicial.value;
            		 montoPromDiario.value="";
            		 montoPromDiario.disabled=true;
            		 //porcentajeGL.value="15";
            		 porcentajeGL.value="10";
            		 porcentajeGL.disabled=false;
            		 excepcionPorcentajeGL.value="";
            		 excepcionPorcentajeGL.disabled=true;            		 
            		 montoGL.value="";
            		 montoGL.disabled=true;
            		 porcentajeInicialGL.value="";
            		 porcentajeInicialGL.disabled=true;
            		 montoInicialGL.value="";
            		 montoInicialGL.disabled=true;
            		 porcentajeRestanteGL.value="";
            		 porcentajeRestanteGL.disabled=true;
            		 montoRestanteGL.value="";
            		 montoRestanteGL.disabled=true;            		 
            		 porcentajeDiarioGL.value="";
            		 porcentajeDiarioGL.disabled=true;
            		 promMontoDiarioGL.value="";
            		 promMontoDiarioGL.disabled=true;
            		 optionPorcentajeVentasDiarias.disabled=true;
            		 optionMontoFijoDiario.disabled=true;
            		 glOriginal.value="";
            		 glOriginal.disabled=true;
            		 calcularGarantiaLiquida();
            	 }
            		 
             }
              
           //Garantia Liquida: muestra apartado de GL
             function onchangeSeleccionGL(){ 
            	 var seleccion=valorSeleccionGarantiaLiquida();
            	 if(seleccion==1){
            		 mostrarGarantiaLiquida("none");
            		 mostrarFianza("block");
            		 mostrarComentariosDisminucionExcepcionGL("none");
            		 inicializarFianza();//nuevo
            	 }else{
            		 isValidVentasMensuales();
                	 onchangeMontoInicial();
                	 inicializarFianza();// para desactivar fianza y que sus campos se inicialicen
            		 mostrarGarantiaLiquida("block");
            		 mostrarComentariosDisminucionExcepcionGL("none");
            		 mostrarFianza("none");
            	 }
            		 
             }
             
             
             
             
             /**
              * Funcion que establece si se requiere o no fianza en base al giro, producto y el servicio de teclado abierto
              * llama a onchangeFianza
              */
             function inicializarFianza(){

            	 var seleccion= valorSeleccionGarantiaLiquida();
            	 var comboRequiereFianza = document.getElementById("forma:affiliation_havedepositcompany"); //requiere fianza combo
            	 var checkboxExentar = document.getElementById("forma:exentDep");//exenta fianza checkbox
            	 
            	 if(seleccion==1){

	            	 var comboProducto = document.getElementById("forma:affiliation_productdesc"); //producto combo
	            	 var checkboxTecladoAbierto = document.getElementById("forma:affiliation_openkey"); // teclado abierto checkbox
	            	 var giro = document.getElementById("forma:client_categorycode").value;//giro
	            	 var numGiro = giro.substring(0,6);//numero de giro
	            	 var comboAlianza = document.getElementById("forma:affiliation_alliance");//alianza
	            	 var altoRiesgo = false;
	            	 var comboBuroInterno = document.getElementById("forma:affiliation_internalcredithistory");
	            	 var comboBuroExterno = document.getElementById("forma:affiliation_externalcredithistory");
	            	 var antiguedad = document.getElementById("forma:antiguedad");
	            	 
	            	 comboRequiereFianza.disabled=false;	            	 
			 		
					 if("true"==isGiroGL(numGiro)){
		   				altoRiesgo=true;
		   			}
			 		
	            	 if(comboAlianza.value=="Agregador"){
	            		 altoRiesgo=true;
	            	 }
	            	 if(comboProducto.value=="Comercio Electronico"){
	            		 altoRiesgo=true;
	            	 }
	            	 if(checkboxTecladoAbierto.checked){
	            		 altoRiesgo=true;
	            	 }
	            	 if(comboBuroInterno.value=="Malo" || comboBuroExterno.value=="Malo"){
	            		 altoRiesgo=true;
	            	 }
	            	 if(antiguedad.value==-6){
	            		 altoRiesgo=true;
	            	 }
	            	 if(altoRiesgo){//si es alto riesgo si requiere fianza
	            		 checkboxExentar.disabled=false;
	            		 comboRequiereFianza.value=1;//"SI"
	            		 
	            	 }else{//si no es alto riesgo no requiere fianza
	            		 checkboxExentar.disabled=true;
	            		 checkboxExentar.checked=false;
	            		 comboRequiereFianza.value=0; //"NO"
	            	 }
	            	 
	            	 comboRequiereFianza.disabled=true;
	            	 onchangeFianza();
            	 }else if(seleccion==0){
            		//si no es alto riesgo no requiere fianza
            		 checkboxExentar.disabled=true;
            		 checkboxExentar.checked=false;
            		 comboRequiereFianza.value=0; //"NO"
            		 onchangeFianza();
            	 }
             }
             
             
             /**
              * es llamada por inicializarFianza
              * Funcion que habilita/deshabilita los campos de fianza (monto, exentar, autoriza, expiracion)en base a si requiere o no la fianza
              * @returns {Boolean}
              */
             function onchangeFianza() {
            	    var checkboxExentarFianza = document.getElementById("forma:exentDep");
            	    var campoCiaFianza = document.getElementById("forma:affiliation_depositcompany");
            	    var campoAutorizaExentar = document.getElementById("forma:affiliation_officerdepositexent");
            	    var campoMontoFianza = document.getElementById("forma:affiliation_depositamount");
            	    var campoFechaExpFianza = document.getElementById("forma:affiliation_duedate");
            	    var comboReqFianza = document.getElementById("forma:affiliation_havedepositcompany");
            	    
                	var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');

            	    if(comboReqFianza.value==0){ //si "NO" requiere fianza
            	    	checkboxExentarFianza.checked=false; checkboxExentarFianza.disabled=true; //se limpian valores y se deshabilitan
            	    	campoCiaFianza.disabled = true; campoCiaFianza.value="";
            	        campoAutorizaExentar.disabled = true; campoAutorizaExentar.value="";
            	        campoMontoFianza.disabled = true; campoMontoFianza.value="";
            	        campoFechaExpFianza.disabled = true; campoFechaExpFianza.value="";
            	    }else if(checkboxExentarFianza.checked==false){ //"SI" requiere fianza pero "NO" esta exenta
            	    	checkboxExentarFianza.disabled=false;//se habilitan los campos de fianza
            	    	campoCiaFianza.disabled=false;
            	    	campoMontoFianza.disabled = false;
            	    	campoFechaExpFianza.disabled = false;
            	    	campoAutorizaExentar.disabled=true;
            	    	//*********************************** 
            	    	//valida montoFianza con el 50% o mas de ventas mensuales cunado aplique
            	    	if(campoMontoFianza.value!=null || campoMontoFianza.value!=""){
            	    		campoMontoFianza.value= (valorVentaMensual.value*0.5);
            	    	}
            	    	isValidMontoFianza();//valida montoFianza con el 50% o mas de ventas mensuales cunado aplique
            	    	//*********************************** 
            	    }else if(checkboxExentarFianza.checked==true){ //cuando "SI" exenta
            	    	campoCiaFianza.disabled=true;
            	    	campoMontoFianza.disabled = true;
            	    	campoFechaExpFianza.disabled = true;
            	    }
            	    recalcularTabla(true);
            	    return false;
            }
             
             function onchangeAmex(){
            	 var checkboxAmex = document.getElementById("forma:amex");
            	 if(checkboxAmex.checked){
            		 alert("Para dualidad American Express sera necesario requisitar contrato y documentos de soporte por separado.");
            	 }
             }
             
             
             /**
              * Funcion que habilita/deshabilita los campos de fianza si se quiere exentar o no
              */
             function onchangeExentDeposit(){
            	 var exentar = document.getElementById("forma:exentDep");//exenta fianza checkbox
            	 var auth = document.getElementById("forma:affiliation_officerdepositexent");//quien autoriza exentar
            	 var company = document.getElementById("forma:affiliation_depositcompany");//compa�ia fianza
            	 var amount = document.getElementById("forma:affiliation_depositamount");//monto fianza
            	 var dueDate = document.getElementById("forma:affiliation_duedate");//fecha expiracion
             	
            	 var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');
            	 var montoFianza= document.getElementById('forma:affiliation_depositamount');

            	 if(exentar.checked==true){
            		 auth.disabled=false;
            		 company.disabled=true; company.value="";
            		 amount.disabled=true; amount.value="";
            		 dueDate.disabled=true; dueDate.value="";
            	 }else{
            		 auth.disabled=true; auth.value="";
            		 company.disabled=false;
            		 amount.disabled=false;
            		 dueDate.disabled=false;
            		 montoFianza.value=valorVentaMensual.value*0.5;
            		 
            	 }
             }

             function agregadorSpecial(deshabilitar, selected, profile){
            	 var plan = document.getElementById("forma:affiliation_comercialplan");
            	 var planIxe = document.getElementById("forma:affiliation_comercialplanIxe");
            	 var tecladoAbierto = document.getElementById("forma:affiliation_openkey");
            	 var ventaForzada = document.getElementById("forma:affiliation_forceauth");
            	 var cashBack = document.getElementById("forma:aff_cashback");
            	 var qps = document.getElementById("forma:affiliation_qps");
            	 var tiempoAire = document.getElementById("forma:affiliation_tiempoaire");
            	 var amex = document.getElementById("forma:amex");
            	 var tpv = document.getElementById("forma:tvpUnattended");
            	 var pagomovil = document.getElementById("forma:mobilePymnt");
            	 
            	 if(deshabilitar==true){
            		 tecladoAbierto.disabled=deshabilitar; tecladoAbierto.checked=false;
                	 ventaForzada.disabled=deshabilitar; ventaForzada.checked=false;
                	 cashBack.disabled=deshabilitar; cashBack.checked=false;
                	 qps.disabled=deshabilitar; qps.checked=false;
                	 tiempoAire.disabled=deshabilitar; tiempoAire.checked=false;
                	 amex.disabled=deshabilitar; amex.checked=false;
                	 tpv.disabled=deshabilitar; tpv.checked=false;
                	 pagomovil.disabled=deshabilitar; pagomovil.checked=false;
                	 
                	 plan[1].disabled=deshabilitar;
                	 plan[2].disabled=deshabilitar;
                	 plan[3].disabled=deshabilitar;
                	 plan[4].disabled=deshabilitar;
                	 
                	 planIxe[1].disabled=deshabilitar;
                	 planIxe[2].disabled=deshabilitar;
                	 planIxe[3].disabled=deshabilitar;
                	 planIxe[4].disabled=deshabilitar;
                	 planIxe[5].disabled=deshabilitar;
                	 planIxe[6].disabled=deshabilitar;
                	 planIxe[7].disabled=deshabilitar;
                	 
                	 if(profile==0){//si el plan elegido es banorte
                		 if(plan.selectedIndex<5){//y el plan no es elegible
                    		 plan.options[0].selected=deshabilitar;	 //se cambia a seleccione
                    	 }else {
                    		 plan.value=selected;//si no, se selecciona el valor que trae
                    	 }
                	 }else if(profile==1){//si el plan elegido es ixe, 
                		 if(planIxe.selectedIndex<8){//y es de los no elegibles
                        	 planIxe.options[0].selected=deshabilitar; //se cambia a seleccione
                    	 }else{
                    		 planIxe.value=selected;//si es plan elegible, se queda el que traia
                    	 }
                	 }else{//si no trae plan elegido
                		 plan.selectedIndex=0;
                		 planIxe.selectedIndex=0;
                	 }
            	 }else{//si no eligio agregador
                	 plan[0].disabled=deshabilitar;
                	 plan[1].disabled=deshabilitar;
                	 plan[2].disabled=deshabilitar;
                	 plan[3].disabled=deshabilitar;
                	 plan[4].disabled=deshabilitar;
                	 
                	 planIxe[0].disabled=deshabilitar; 
                	 planIxe[1].disabled=deshabilitar;
                	 planIxe[2].disabled=deshabilitar;
                	 planIxe[3].disabled=deshabilitar;
                	 planIxe[4].disabled=deshabilitar;
                	 planIxe[5].disabled=deshabilitar;
                	 planIxe[6].disabled=deshabilitar;
                	 planIxe[7].disabled=deshabilitar;
                	 
                	 if(profile==0){//si el plan elegido es banorte
                    		 plan.value=selected;//si no, se selecciona el valor que trae
                	 }else if(profile==1){//si el plan elegido es ixe, 
                    		 planIxe.value=selected;//si es plan elegible, se queda el que traia
                	 }else{//si no trae plan elegido
                		 plan.selectedIndex=0;
                		 planIxe.selectedIndex=0;
                	 }
                	 tecladoAbierto.disabled=deshabilitar; 
                	 ventaForzada.disabled=deshabilitar; 
                	 cashBack.disabled=deshabilitar; 
                	 qps.disabled=deshabilitar;
                	 tiempoAire.disabled=deshabilitar; 
                	 amex.disabled=deshabilitar;
                	 tpv.disabled=deshabilitar; 
                	 pagomovil.disabled=deshabilitar;
            	 }
             }
             
             /**
              * Funcion que habilita/deshabilita y pone por default el valor de cuota de afiliacion en base al producto y la divisa seleccionada
              */
             function inicializarCuotaAfiliacion(){

            	 var divisa = document.getElementById("forma:affiliation_currency");
            	 var pesos = document.getElementById("forma:affiliation_affiliationratemn");
            	 var dlls = document.getElementById("forma:affiliation_affiliationratedlls");
            	 var planElegido = planSeleccionado();
         		 
            	 if(divisa.value=="Pesos"){
            		 pesos.disabled = false;
            		 dlls.disabled = true;
            		 dlls.value = "0";

            	 }else if(divisa.value=="Dolares"){
            		 dlls.disabled = false;
            		 pesos.disabled = true;
            		 pesos.value = "0";
            	 }else{
            		 pesos.disabled = false;
            		 dlls.disabled = false;
            	 }
            	 
            	 if(planElegido == 5){
        			 dlls.value = "0";
        			 pesos.value = "0";
        		 }

             }

             
             /**
              * Funcion para mostrar el combo de planes en base a la red seleccionada
              * @param red es el perfil seleccionado (banorte o ixe)
              * @param planBanorteSeleccionado si trae algun valor ya seleccionado de banorte (en caso de edicion)
              * @param planIxeSeleccionado si trae algun valor ya seleccionado de ixe (en caso de edicion)
              */
             function inicializarPlanes(red, planBanorteSeleccionado, planIxeSeleccionado){
            	 var planesIxe = document.getElementById("forma:affiliation_comercialplanIxe");
            	 var planesBanorte = document.getElementById("forma:affiliation_comercialplan");
            	 var ixeLabel = document.getElementById("forma:ixeLabel");
            	 var banorteLabel = document.getElementById("forma:banorteLabel");
            	 var errorRed = document.getElementById("forma:errorRed");
            	 var campoOtroPlan = document.getElementById("forma:otherCommercialPlan");
            	 /*if(red!=null){
            		 if(red==1){ //ixe
                		 planesIxe.style.display="inline";
                		 planesBanorte.style.display = "none";
                		 banorteLabel.style.display="none";
                		 ixeLabel.style.display="";
                		 planesBanorte.value=0;
                		 planesIxe.value=planIxeSeleccionado;
                		 errorRed.style.display="none";
                	 }else{ //banorte*/
                		 planesIxe.style.display="none";
                		 planesBanorte.style.display = "inline-block";
                		 ixeLabel.style.display="none";
                		 banorteLabel.style.display="";
                		 planesIxe.value=0;
                		 planesBanorte.value=planBanorteSeleccionado;
                		 errorRed.style.display="none";
                	 /*}
            	 }else{
            		 planesIxe.style.display="none";
            		 planesBanorte.style.display="none";
            		 document.getElementById("forma:red").focus();
            		 errorRed.style.display="";
//            		 errorRed.style="color:red";
            	 }*/
            	 campoOtroPlan.disabled=true;
            	 inicializarAlianza();
            	 
            	 if("Terminal Personal Banorte (Mpos)" == document.getElementById("forma:affiliation_productdesc").value){
            		 planesBanorte.options[1].disabled = false;
            	 }else{
            		 planesBanorte.options[1].disabled = true;
            	 }
             }
             
             
             
             function valorRed(){
            	 var radios = document.getElementsByName('forma:red');
            	 var r=null;

            	 for (var i = 0, length = radios.length; i < length; i++) {
            	     if (radios[i].checked) {
            	         r=(radios[i].value);
            	         break;
            	     }
            	 }
            	 return r;
             }
             
             
             //Garantia Liquida
             function valorSeleccionGarantiaLiquida(){
            	 var radios = document.getElementsByName('forma:seleccionGarantia');
            	 var r=null;

            	 for (var i = 0, length = radios.length; i < length; i++) {
            	     if (radios[i].checked) {
            	         r=(radios[i].value);
            	         break;
            	     }
            	 }            	 
            	 return r;
             }
             
             
             
             /**
              * Es llamada cuando cambia el valor de divisa, producto
              * Funcion que establece si es necesario o no recalcular la tabla de comisiones
              * @param boleano
              */
             function recalcularTabla(boleano){
            	 var campoRecalcularHidden = document.getElementById("forma:recalculateCommisionTable");
            	 campoRecalcularHidden.value = boleano;
             }
             
        //------------------------------------------------------------
        //        ginita
        // -------------------------------------------------------------
             
             /**
              * es llamada por inicializarFianza
              * Funcion que llena el combo de paquete en base a la alianza seleccionada
              */
             function inicializarPaquete() {
            	/* var girosEspeciales = new Array("005422","008398","008211","008241","008299","000742","008011","008021","008031",
 						  						 "008041","008042","008049","008351","005072","005074","005251","005533","007230");*/
            	 var alliance 		 = document.getElementById("forma:affiliation_alliance").value;
            	 var paquetes 		 = document.getElementById("forma:affiliation_chargeType");
            	 var paquetes_hidd 	 = document.getElementById("forma:affiliation_chargeType_hidd");
            	 var sel_prod 		 = document.getElementById("forma:affiliation_productdesc").value;
                 var arregloOpciones = new Array();
                 var red 			 = valorRed();
                 var plan			 = document.getElementById("forma:affiliation_comercialplan").value;
                 var estadoUsuario 	 = document.getElementById("forma:userState").value;
                 var giro 			 = (document.getElementById("forma:client_categorycode").value).substring(0,6);
               /*  var isGiroEspecial  = false;
                 
	           	  //se identifica si es un giro especial
	           	  for (var i = 0; i < girosEspeciales.length; i++) {
	       			  if(giro==girosEspeciales[i]){
	       				  isGiroEspecial = true;
	       				  break;
	       			  }
	       		  }  
            	  
                 if(estadoUsuario=="OCDT"){
                	 if(alliance == "Micros"){
                		 paquetes.disabled=false;
                		 arregloOpciones = ["(+)0.15 ppb en tasa", "(+)$1.40 por transaccion", "Renta Mensual $200 por equipo + 0.05 ppb en tasa", "Estudio de Rentabilidad", "Otro", "OCCIDENTE 2016"];
                	 }else if(alliance == "Cybersource"){
                		 paquetes.disabled=false;
                		 arregloOpciones = ["(+)0.1 ppb en tasa", "(+)$1.10 por transaccion", "Estudio de Rentabilidad","OCCIDENTE 2016"];
                	 }else if(alliance == "Netpay"){
                		 paquetes.disabled=false;
                		 if(sel_prod=="Terminal punto de venta"){//tpv, netpay
                			 if(giro=="005812" || giro=="005813" || giro=="005814"){
                				 arregloOpciones=["(+)0.5 ppb en tasa", "RCRC"];
                			 }else{	
                				 arregloOpciones=["(+)0.5 ppb en tasa"];
                			 }
                		 }else if(sel_prod=="Interredes"){ //interredes, netpay
                			 arregloOpciones=["2- 1 a 20 afiliaciones", "3- (+)de 20 afiliaciones", "4- Estudio de Rentabilidad", "5- Otro", "OCCIDENTE 2016"];
                		 }else if(sel_prod=="Comercio Electronico"){//comercio electronico, netpay
                			 paquetes.disabled=false;
                			 arregloOpciones = ["(+)% en tasa", "(+)$1 por transaccion", "Estudio de Rentabilidad"];
                		 }else{//cargo recurrente
                			 paquetes.disabled=false;
                			 arregloOpciones = ["(+)% en tasa", "(+)$1 por transaccion", "(+)$75 Renta Ad. por Eq", "Estudio de Rentabilidad"];
                		 }
                	 }else if(alliance=="Agregador"){ //no aplica paquete
                		 if(giro=="005812" || giro=="005813" || giro=="005814"){
                			 paquetes.disabled=false;
            				 arregloOpciones = ["RCRC"];
                		 }else{
                			 paquetes_hidd.value="0";
                    		 paquetes.disabled=true;
                		 }
                	 }else{//ninguna
                		 paquetes.disabled=false;
                		 if(sel_prod=="Terminal punto de venta"){
                			 if(giro=="005812" || giro=="005813" || giro=="005814"){
                				 arregloOpciones = ["OCCIDENTE 2016", "RCRC"];
                			 }else{
                    			 arregloOpciones = ["OCCIDENTE 2016"];
                    		 }
                		 }else if(sel_prod == "Terminal Personal Banorte (Mpos)"){
                			 arregloOpciones = ["Tasa + $0.40 por tx", "Tasa + 25 ppb", "Estudio de Rentabilidad", "OCCIDENTE 2016"];
                		 }else{
                			 arregloOpciones = ["OCCIDENTE 2016"];
                		 }
                		 
                	 }
                 }else if (estadoUsuario=="SLP"){
                	 if(alliance=="Ninguna"){
                		 if(red==0){//solo banorte
                			 if(sel_prod=="Terminal punto de venta"){
                				 if(giro=="005812" || giro=="005813"){
                					 paquetes.disabled=false;
                					 arregloOpciones = ["SLP 2014", "RCRC"];
                				 }else{
                					 if(giro=="005814"){
                						 paquetes.disabled=false;
                						 arregloOpciones = ["RCRC"];
                					 }
                				 }
                			 }else if(sel_prod=="Interredes"){
								if(giro=="005812" || giro=="005813"){
									paquetes.disabled=false;
									arregloOpciones = ["SLP 2014",];
								}
                			 }else if(sel_prod == "Terminal Personal Banorte (Mpos)"){
                				 arregloOpciones = ["Tasa + $0.40 por tx", "Tasa + 25 ppb", "Estudio de Rentabilidad", "SLP 2014"];
                			 }
                		 }else{
                			 paquetes.value=0;
                			 paquetes.disabled=true;
                			 paquetes_hidd.value="0";
                		 }
                	 }else if(alliance == "Micros"){
                		 paquetes.disabled=false;
                		 arregloOpciones = ["(+)0.15 ppb en tasa", "(+)$1.40 por transaccion", "Renta Mensual $200 por equipo + 0.05 ppb en tasa", "Estudio de Rentabilidad", "Otro"];
                	 }else if(alliance == "Cybersource"){
                		 paquetes.disabled=false;
                		 arregloOpciones = ["(+)0.1 ppb en tasa", "(+)$1.10 por transaccion", "Estudio de Rentabilidad"];
                	 }else  if(alliance == "Netpay"){
                		 paquetes.disabled=false;
                		 if(sel_prod=="Terminal punto de venta"){ //tpv, netpay
                			 if(giro=="005812" || giro=="005813" || giro=="005814"){
                				 arregloOpciones=["(+)0.5 ppb en tasa", "RCRC"];
                			 }else{
                				 arregloOpciones=["(+)0.5 ppb en tasa"];
                			 }
                		 }else if(sel_prod=="Interredes"){ //interredes, netpay
                			 arregloOpciones=["2- 1 a 20 afiliaciones", "3- (+)de 20 afiliaciones", "4- Estudio de Rentabilidad", "5- Otro"];
                		 }else if(sel_prod=="Comercio Electronico"){//comercio electronico, netpay
                			 paquetes.disabled=false;
                			 arregloOpciones = ["(+)% en tasa", "(+)$1 por transaccion", "Estudio de Rentabilidad"];
                		 }else{
                			 paquetes.disabled=false;
                			 arregloOpciones = ["(+)% en tasa", "(+)$1 por transaccion", "(+)$75 Renta Ad. por Eq", "Estudio de Rentabilidad"];
                		 } 
                	 }else{
                		 if(giro=="005812" || giro=="005813" || giro=="005814"){
    						 paquetes.value=0;
        					 paquetes.disabled=false;
        					 arregloOpciones=["RCRC"];
    					 }else{
        					 paquetes.value=0;
        					 paquetes.disabled=true;
    					 }
                	 }
                 }else if(estadoUsuario=="ALL"){
                	 if(sel_prod=="Terminal punto de venta"){ //OCDT+TPV
                		 //if(red=="0"){//BANORTE
                			 if(alliance=="Ninguna"){
                				 if(giro=="005812" || giro=="005813"){//CAFETERIAS y BARES BEBIDAS ALCOHOLICAS (TABERNAS) (DISCOTECAS)
                					 paquetes.disabled=false;
                					 arregloOpciones = ["SLP 2014","OCCIDENTE 2016", "RCRC"];
                				 }else{
                					 if(giro=="005814"){//COMERCIO QUE VENDE COMIDA PREPARADA PARA CONSUMO I
                						 paquetes.disabled=false;
                						 arregloOpciones = ["OCCIDENTE 2016", "RCRC"];
                					 }else{
                						 paquetes.disabled=false;
                    					 arregloOpciones = ["Ninguno","OCCIDENTE 2016"]; 
                					 }
                				 }
                			 }else if(alliance=="Micros"){
                				 paquetes.disabled=false;
                				 arregloOpciones = ["(+)0.15 ppb en tasa", "(+)$1.40 por transaccion", "Renta Mensual $200 por equipo + 0.05 ppb en tasa", "Estudio de Rentabilidad", "Otro", "OCCIDENTE 2016"];
                			 }else{
                				 if(alliance =="Netpay"){//netpay
                					 paquetes.value=0;
                					 paquetes.disabled=false;
                					 if(giro=="005812" || giro=="005813" || giro=="005814"){
                						 arregloOpciones=["(+)0.5 ppb en tasa", "RCRC"]; 
                					 }else{
                						 arregloOpciones=["(+)0.5 ppb en tasa"];
                					 }
                				 }else{//agregador
                					 if(giro=="005812" || giro=="005813" || giro=="005814"){
                						 paquetes.value=0;
                    					 paquetes.disabled=false;
                    					 arregloOpciones=["RCRC"];
                					 }else{
	                					 paquetes.value=0;
	                					 paquetes.disabled=true;
                					 }
                				 }
                			 }
                	 }else if(sel_prod=="Interredes"){ //OCDT+INTERREDES
                		 //if(red=="0"){//BANORTE
                			 if(alliance=="Ninguna"){
                				 if(giro=="005812" || giro=="005813"){//restaurantes
                					 paquetes.disabled=false;
                					 arregloOpciones = ["SLP 2014", "OCCIDENTE 2016"];
                				 }else{//otro giro
   	          						paquetes.disabled=false;
   	          						arregloOpciones = ["OCCIDENTE 2016"];
   	          					}
                			 }else if(alliance=="Micros"){
                				 paquetes.disabled=false;
                				 arregloOpciones = ["(+)0.15 ppb en tasa", "(+)$1.40 por transaccion", "Renta Mensual $200 por equipo + 0.05 ppb en tasa", "Estudio de Rentabilidad", "Otro", "OCCIDENTE 2016"];
                			 }else if(alliance=="Netpay"){
                				 paquetes.disabled=false;
                				 arregloOpciones = ["2- 1 a 20 afiliaciones", "3- (+)de 20 afiliaciones", "4- Estudio de Rentabilidad", "5- Otro", "OCCIDENTE 2016"];
                			 }else{
         						paquetes.disabled=false;
         						arregloOpciones=["2- 1 a 20 afiliaciones", "3- (+)de 20 afiliaciones", "4- Estudio de Rentabilidad", "5- Otro","OCCIDENTE 2016"];	
         					}
                	 }else if(sel_prod=="Comercio Electronico"){//OCDT+COMERCIO ELECTRONICO
                		 paquetes.disabled=false;
                		 if(alliance == "Cybersource"){
                			 paquetes.disabled=false;
                			 arregloOpciones = ["(+)0.1 ppb en tasa", "(+)$1.10 por transaccion", "Estudio de Rentabilidad","OCCIDENTE 2016"];
                		 }else if(alliance=="Ninguna"){
                			 arregloOpciones=["Ninguno","OCCIDENTE 2016"];
                		 }else{//agregador
                			 paquetes.value=0;
                			 paquetes.disabled=true;
                		 }
                	 }else if(sel_prod == "Terminal Personal Banorte (Mpos)"){
                		 arregloOpciones = ["Tasa + $0.40 por tx", "Tasa + 25 ppb", "Estudio de Rentabilidad"];
                	 }else{//Cargo Recurrente
                		 paquetes.disabled=false;
                		 if(alliance=="Ninguna"){
                			 arregloOpciones=["Ninguno","OCCIDENTE 2016"];
                		 }else{//agregador
                			 paquetes.value=0;
                			 paquetes.disabled=true;
                		 }
                	 }
                 }else{//otros estados (normales)
                	 if(alliance == "Micros"){
                		 paquetes.disabled=false;
                		 arregloOpciones = ["(+)0.15 ppb en tasa", "(+)$1.40 por transaccion", "Renta Mensual $200 por equipo + 0.05 ppb en tasa", "Estudio de Rentabilidad", "Otro"];
                	 }else if(alliance == "Cybersource"){
                		 paquetes.disabled=false;
                		 arregloOpciones = ["(+)0.1 ppb en tasa", "(+)$1.10 por transaccion", "Estudio de Rentabilidad"];
                	 }else  if(alliance == "Netpay"){
                		 paquetes.disabled=false;
                		 if(sel_prod=="Terminal punto de venta"){ //tpv, netpay
                			 arregloOpciones=["(+)0.5 ppb en tasa"];
                		 }else if(sel_prod=="Interredes"){ //interredes, netpay
                			 arregloOpciones=["2- 1 a 20 afiliaciones", "3- (+)de 20 afiliaciones", "4- Estudio de Rentabilidad", "5- Otro"];
                		 }else if(sel_prod=="Comercio Electronico"){//comercio electronico, netpay
                			 paquetes.disabled=false;
                			 arregloOpciones = ["(+)% en tasa", "(+)$1 por transaccion", "Estudio de Rentabilidad"];
                		 }else{
                			 paquetes.disabled=false;
                			 arregloOpciones = ["(+)% en tasa", "(+)$1 por transaccion", "(+)$75 Renta Ad. por Eq", "Estudio de Rentabilidad"];
                		 } 
                	 }else{//ninguna
                		 if(sel_prod=="Terminal punto de venta"){
                			 paquetes.disabled=false;
                			 arregloOpciones=["RCRC"];
                		 }else if("Terminal Personal Banorte (Mpos)"){
                			 arregloOpciones = ["Tasa + $0.40 por tx", "Tasa + 25 ppb", "Estudio de Rentabilidad"];
                		 }else{
                			 paquetes.value=0;
                    		 paquetes.disabled=true;
                    		 paquetes_hidd.value="0";
                		 }
                		 
                	 }
                 }
*/
                 if(alliance == "Micros"){
                	 arregloOpciones = ["(+)0.15 ppb en tasa", "(+)$1.40 por transaccion", "Renta Mensual $200 por equipo + 0.05 ppb en tasa", "Estudio de Rentabilidad", "Otro"];
                	 paquetes.disabled=false;
                 }else if(alliance == "Tradicional" || alliance == "Hosted" || alliance == "Call Center"){
                	 var comboIntegracion = document.getElementById("forma:affiliation_integration");
                	 
                	 if(comboIntegracion.value == "Cybersource Enterprise Revision Manual" || 
                			 comboIntegracion.value == "Cybersource Enterprise Autenticación Selectiva" ||
                			 comboIntegracion.value == "Cybersource Direct" ||
                			 comboIntegracion.value == "Cybersource Hosted" ||
                			 comboIntegracion.value == "Cybersource Call Center" ||
                			 comboIntegracion.value == "Cybersource Enterprise Call Center"){
                		 arregloOpciones = ["Estudio de Rentabilidad"];
                		 paquetes.disabled = false;
                	 }else{
                		 arregloOpciones = [];
                		 paquetes.disabled = true;
                	 }
                	 
                 }else if(alliance == "Netpay"){
                	 if(sel_prod=="Terminal punto de venta"){
                		 if(giro=="005812" || giro=="005813" || giro=="005814")
                    		 arregloOpciones = ["(+)0.5 ppb en tasa", "RCRC"];
                    	 else
                    		 arregloOpciones = ["(+)0.5 ppb en tasa"];
                		 paquetes.disabled=false;
                	 }else if(sel_prod=="Interredes"){
                		 arregloOpciones = ["2- 1 a 20 afiliaciones", "3- (+)de 20 afiliaciones", "4- Estudio de Rentabilidad", "5- Otro"];
                		 paquetes.disabled=false;
                	 }else if(sel_prod=="Comercio Electronico"){
                		 arregloOpciones = ["(+)% en tasa", "(+)$1 por transaccion", "Estudio de Rentabilidad"];
                		 paquetes.disabled=false;
                	 }else if(sel_prod=="Cargo Recurrente"){
                		 arregloOpciones = ["(+)% en tasa", "(+)$1 por transaccion", "(+)$75 Renta Ad. por Eq", "Estudio de Rentabilidad"];
                		 paquetes.disabled=false;
                	 }
                 }else if(alliance == "Agregador"){
                	 if(sel_prod=="Interredes"){
                		 arregloOpciones = ["2- 1 a 20 afiliaciones", "3- (+)de 20 afiliaciones", "4- Estudio de Rentabilidad", "5- Otro"];
                		 paquetes.disabled = false;
                	 }else if(sel_prod=="Terminal punto de venta"){
                		 if(giro=="005812" || giro=="005813" || giro=="005814"){
                			 arregloOpciones = ["RCRC"];
                			 paquetes.disabled=false;
                		 }else{
                			 paquetes_hidd.value="0";
                			 paquetes.disabled=true;
                		 }
                	 }else if(sel_prod=="Cargo Recurrente"){
                		 paquetes.value = 0;
                		 paquetes.disabled = true;
                	 }else if(sel_prod=="Comercio Electronico"){
                		 var comboIntegracion = document.getElementById("forma:affiliation_integration");
                    	 
                    	 if(comboIntegracion.value == "Cybersource Enterprise Revision Manual" || 
                    			 comboIntegracion.value == "Cybersource Enterprise Autenticación Selectiva" ||
                    			 comboIntegracion.value == "Cybersource Direct" ||
                    			 comboIntegracion.value == "Cybersource Hosted" ||
                    			 comboIntegracion.value == "Cybersource Call Center" ||
                    			 comboIntegracion.value == "Cybersource Enterprise Call Center"){
                    		 arregloOpciones = ["Estudio de Rentabilidad"];
                    		 paquetes.disabled = false;
                    	 }else{
                    		 arregloOpciones = [];
                    		 paquetes.disabled = true;
                    	 }
                    	 
                	 }else if(sel_prod=="Terminal Personal Banorte (Mpos)"){
                		 arregloOpciones = ["Estudio de Rentabilidad"];
                    	 paquetes.disabled=false;
                	 }
                 }else if(alliance == "Ninguna"){
                	 if(sel_prod=="Terminal punto de venta"){
                		 if(giro=="005812" || giro=="005813" || giro=="005814"){
                			 arregloOpciones = ["RCRC"];
                			 paquetes.disabled=false;
                		 }else{ 
                			 arregloOpciones = ["Ninguno"];
                    		 paquetes.disabled=false;
                		 }
                	 
                	 }else if(sel_prod=="Terminal Personal Banorte (Mpos)"){
                		/* if(isGiroEspecial == true){
                			 arregloOpciones = [];
                    		 paquetes.disabled = true;
                		 }else{}*/
                		 arregloOpciones = ["Tasa + $0.40 por tx", "Tasa + 25 ppb", "Estudio de Rentabilidad"];
                		 paquetes.disabled=false;
                	 }else if(sel_prod=="Cargo Recurrente"){
                		 arregloOpciones = ["Ninguno"];
                		 paquetes.disabled=false;
                	 }
                 }
                 
                 paquetes.value=paquetes_hidd.value;
                 llenarComboGeneral("forma:affiliation_chargeType", arregloOpciones);
                 paquetes_hidd.value=paquetes.value;
                 planesSLP(false);
                 onchangePaquete();
             }
             
             
             /**
              * Funcion para deshabilitar el combo de planes que se ejecuta al cambiar el valor del paquete
              */
             function onchangePaquete(){
            	 var comboPaquete = document.getElementById("forma:affiliation_chargeType");
            	 var red = valorRed();
            	 var comboPlan;
            	 var paqueteHidden = document.getElementById("forma:affiliation_chargeType_hidd");

            	 if(red!=null){
            		 if(red==0){ //banorte
                		 comboPlan = document.getElementById("forma:affiliation_comercialplan");
                	 }else if(red==1){ //ixe
                		 comboPlan = document.getElementById("forma:affiliation_comercialplanIxe");
                	 }
            	  
                	 if(comboPaquete.value=="Estudio de Rentabilidad"||
                		comboPaquete.value=="4- Estudio de Rentabilidad"||
                	 	comboPaquete.value=="Reciprocidad"){
                		 comboPlan.value=5;
                		 comboPlan.disabled=true;
                	 }else if(comboPaquete.value=="Otro" || comboPaquete.value=="5- Otro"){
                		 comboPlan.value=6;
                		 comboPlan.disabled=true;
                		 
                	 }else if(comboPaquete.value=="SLP 2014" || comboPaquete.value=="OCCIDENTE 2016"){
                		 alert('Recuerda que al cumplir el plazo de vencimiento de esta campa\u00f1a las comisiones aplican en condiciones regulares');
                		 planesSLP(true);
                	 }else{
                		 comboPlan.disabled=false;
                		 planesSLP(false);
                	 }
                	 if(comboPaquete.value=="RCRC"){
                		 comboPlan.options[1].selected = true;
                		 comboPlan.options[1].disabled = false;
                		 comboPlan.options[2].disabled = true;
                		 comboPlan.options[3].disabled = true;
                		 comboPlan.options[4].disabled = true;
                		 comboPlan.options[5].disabled = true;
                		 comboPlan.options[6].disabled = true;
                		 comboPlan.options[7].disabled = true;
                	 }else{
                		 comboPlan.options[2].disabled = false;
                		 comboPlan.options[3].disabled = false;
                		 comboPlan.options[4].disabled = false;
                		 comboPlan.options[5].disabled = false;
                		 comboPlan.options[6].disabled = false;
                		 comboPlan.options[7].disabled = false;
                	 }
            	 }

           	  	 onchangePlan();
            	 paqueteHidden.value=comboPaquete.value;
            	 recalcularTabla(true);
//            	 }
             }
             
             function planesSLP(deshabilitar){
            	 var idComboPlanBanorte="forma:affiliation_comercialplan";
                 var idComboPlanIxe="forma:affiliation_comercialplanIxe";
                 var planB = document.getElementById("forma:affiliation_comercialplan");
                 var planI = document.getElementById("forma:affiliation_comercialplanIxe");
                 var opcionesDeshabilitarBanorte=["1"];//424
                 var opcionesDeshabilitarIxe=["8","9"]; //attributeoption: attributeid=781
                 var red = valorRed();
                 var opcionSeleccionada=null;
                 if(red==0){
                	 	if(planB.selectedIndex==-1){
                	 		planB.options[0].selected=true;
                	 	}else{
                	 		opcionSeleccionada=planB.options[planB.selectedIndex];
                       	 toggleComboOptions(idComboPlanBanorte, opcionesDeshabilitarBanorte, deshabilitar);
                       	 //Si la opcion seleccionada esta deshabilitada, entonces se pone por default la primer opcion
                    	   	 if(opcionSeleccionada!=null && opcionSeleccionada.disabled){
                    	   		planB.options[0].selected=true;
                    	   	 }else if(opcionSeleccionada===undefined){
                    	   		planB.options[0].selected=true;
                    	   	 }else{
                    	   		planB.value=opcionSeleccionada.value;
                    	   	 }	
                	 	}
                		 	 
                 }else{//ixe
                	 if(planI.selectedIndex==-1){
                		 planI.options[0].selected=true;
                	 }else{
                		 opcionSeleccionada=planI.options[planI.selectedIndex];
                    	 toggleComboOptions(idComboPlanIxe, opcionesDeshabilitarIxe, deshabilitar); 
                    	 //Si la opcion seleccionada esta deshabilitada, entonces se pone por default la primer opcion
                 	   	 if(opcionSeleccionada!=null && opcionSeleccionada.disabled){
                 	   		planI.options[0].selected=true;
                 	   	 }else if(opcionSeleccionada===undefined){
                 	   		planI.options[0].selected=true; 
                 	   	 }else{
                 	   		 planI.value=opcionSeleccionada.value;
                 	   	 }
                	 }
                	 
                 }
         	   	
             }
             
             /**
              * Funcion para inicializar alianza, llena el combo en base al producto y solucion seleccionada
              * Llama a la funcion de inicializarPaquete e inicializarIntegracion
              */
             function inicializarAlianza(){
            	 var comboAlianza = document.getElementById("forma:affiliation_alliance");
            	 var producto = document.getElementById("forma:affiliation_productdesc").value;
            	 var giro = (document.getElementById("forma:client_categorycode").value).substring(0,6);
            	 var solucion 	= document.getElementById("forma:affiliation_soluciontype").value;
            	 var alianzaHidden = document.getElementById("forma:affiliation_alliance_hidd");
            	 var elementosAlianza = new Array();
            	 var red = valorRed();

            	 if( producto == "Cargo Recurrente" ){
            		 //if(red=="1" || red==null){//ixe
            		 //	elementosAlianza=["Ninguna"];	 
            		 //}else{//banorte
            		 elementosAlianza = ["Ninguna", "Agregador"];
            		 //}
            	 }
            	 if(producto == "Comercio Electronico"){
            		 //if(red=="1" || red==null){//ixe
            		 //	elementosAlianza=["Ninguna", "Cybersource"];	 
            		 //}else{//banorte
            		 elementosAlianza = ["Tradicional", "Hosted", "Call Center", "Agregador"];
            		 //}
            	 }
            	 if(producto == "Interredes"){
            		 if(solucion=="Internet"){
            			 if(giro=="005814"||giro=="005812"||giro=="005813"|| //comida rapida y restaurantes
            					 giro=="003747"||giro=="007011"||giro=="007012"){ //hoteles
            				 //if(red=="1" || red==null){
            				 //elementosAlianza=["Ninguna", "Micros", "Netpay"];
            				 //}else{
            				 elementosAlianza=["Ninguna", "Micros", "Netpay", "Agregador"];
            				 //}
            			 }else{
            				 //if(red=="1" || red==null){
            				 //elementosAlianza=["Ninguna", "Netpay"];
            				 //}else{
            				 elementosAlianza=["Ninguna", "Netpay", "Agregador"]; 
            				 //}
            			 }
            		 }else{
            			 //if(red=="1" || red==null){
            			 	//elementosAlianza=["Ninguna"];
            			 //}else{
            			 elementosAlianza=["Ninguna", "Agregador"];
            			 //}
            		 }
            	 }
            	 if(producto=="Terminal Personal Banorte (Mpos)"){
            		 elementosAlianza = ["Ninguna"];
            	 }
            	 if(producto=="Terminal punto de venta"){
            		 if(solucion=="Internet"){
            			 if(giro=="005814"||giro=="005812"||giro=="005813"|| //restaurantes
            					 giro=="003747"||giro=="007011"||giro=="007012"){ //hoteles
            				 //if(red=="1" || red==null){//ixe
            				 	//elementosAlianza=["Ninguna", "Micros"];
            				 //}else{//banorte
            				 elementosAlianza=["Ninguna", "Netpay", "Agregador"];
            				 //}
            			 }else{ //si el giro es diferente a hoteles y restaurantes
            				 //if(red=="1" || red==null){ //ixe
            				 	//elementosAlianza=["Ninguna", "Netpay"];
            				 //}else{ //banorte
            				 elementosAlianza=["Ninguna", "Netpay", "Agregador"];
            				 //}
            			 }
            		 }else{
            			 //if(red=="1" || red==null){ //ixe
            			 	//elementosAlianza=["Ninguna"];
            			 //}else{ //banorte
            			 elementosAlianza=["Ninguna", "Agregador"];
            			 //}
            		 }
            	 }
            		 
            	 llenarComboGeneral("forma:affiliation_alliance", elementosAlianza, 0);
            	 alianzaHidden.value = comboAlianza.value;
            	 inicializarPaquete();
            	 inicializarIntegracion();
             }
             

             /**
              * Funcion que se ejecuta al cambiar el valor de la alianza
              * Llama a la funcion de inicializarPaquete
              */
             function onchangeAlianza(){
            	var comboAlianza = document.getElementById("forma:affiliation_alliance");
            	var comboPlanesBanorte = document.getElementById("forma:affiliation_comercialplan");
           	  	var comboPlanesIxe = document.getElementById("forma:affiliation_comercialplanIxe");
           	  	var red = valorRed();
           	  	var valorComboPlanes = 0;
           	  	var alianzaHidden = document.getElementById("forma:affiliation_alliance_hidd");
           	  	var checkboxTiempoAire = document.getElementById("forma:affiliation_tiempoaire");
           	  	var paqueteSeleccionado = document.getElementById("forma:affiliation_chargeType").value;
           	  	var seleccion= valorSeleccionGarantiaLiquida();
           	  
           	  	if(red==0){//banorte
           	  		valorComboPlanes = comboPlanesBanorte.value;
	           	}else if(red==1){//ixe
	           		valorComboPlanes = comboPlanesIxe.value;
	           	}
           	  	alianzaHidden.value = comboAlianza.value;
            	
           	  	if( comboAlianza.value == "Micros" || comboAlianza.value == "Netpay"){ 
          			equipos("desactivar");
          			inicializarServiciosEspeciales();
          			//if(comboAlianza.value=="Netpay"){
          				//inicializarTiempoAire();
          			//}
          		}else if(comboAlianza.value=="Agregador"){
   	              	 alert('Para el alta de la afiliacion de Agregador deberas contar con el VoBo de Producto Negocio Adquirente');
	              	 inicializarAgregador(true, valorComboPlanes, red);
	          	}else{
          			equipos("activar");
          			checkboxTiempoAire.disabled=true;
          			inicializarAgregador(false, valorComboPlanes, red);
          			inicializarServiciosEspeciales();
          		}
	          		
            	recalcularTabla(true);
            	if(seleccion==1){
            		inicializarFianza();
            	}
            	
            	inicializarGarantiaLiquida();
            	inicializarPaquete();
            	inicializarIntegracion();
            	inicializarPlanes(red, comboPlanesBanorte.value, comboPlanesIxe.value);
            	validarRentaMensual();
             }
             
             
             /**
              * Funcion para inicializar los campos en base a la divisa seleccionada
              * llama a la funcion de inicializarCuotaAfiliacion e inicializarCashback
              */
             function onChangeDivisa(){
            	 var divisa = document.getElementById("forma:affiliation_currency");
            	 var tramite = document.getElementById("forma:affiliation_servicetype");
            	 var afiliacionMn = document.getElementById("forma:affiliation_numaffilmn");
            	 var afiliacionDlls = document.getElementById("forma:affiliation_numaffildlls");
            	 var cuentaMn = document.getElementById("forma:affiliation_accountnumbermn");
            	 var cuentaDlls = document.getElementById("forma:affiliation_accountnumberdlls");
            	 
            	 if (divisa.value == "Pesos"){
            	   	if(tramite.value == "Alta"){
            	       	afiliacionMn.disabled = true;
            	       	afiliacionDlls.disabled = true;
            	    }
            	   	cuentaMn.disabled = false;
            	   	cuentaDlls.value = "";
            	   	cuentaDlls.disabled = true;
            	   	afiliacionDlls.value = "";
            	    afiliacionDlls.disabled = true;
            	 }else{ //si la divisa es dolares o ambos
            	    if(tramite.value == "Alta"){
            	        afiliacionMn.disabled = false;
            	        afiliacionDlls.disabled = true;
            	    }
            	   	cuentaMn.disabled = false;
            	    cuentaDlls.disabled = false;
            	    afiliacionDlls.disabled = false;
            	 }
            	 recalcularTabla(true);
            	 inicializarCuotaAfiliacion();//revisa el campo "cuota de afiliacion" en la tabla de comisiones
            	 inicializarCashback();
            	 inicializarTablaComisiones();
//            	 setCommisionTable();
            	 onchangePlan();
            	 validarRentaMensual();
             }
             
             
             /**
              * Funcion que activa o desactiva el checkbox de venta de tiempo aire en base a la alianza
              */
             function inicializarTiempoAire(){
            	var checkboxTiempoAire = document.getElementById("forma:affiliation_tiempoaire");
            	var comboAlianza = document.getElementById("forma:affiliation_alliance");
            	var solucion 	= document.getElementById("forma:affiliation_soluciontype");
            	if(solucion.value=="Internet" && comboAlianza.value=="Netpay"){
                	checkboxTiempoAire.disabled=false;
            	}else{
           			checkboxTiempoAire.disabled=true;
           			checkboxTiempoAire.checked=false;
           		}
            	onchangeTiempoAire();
             }
             
             
             /**
              * Funcion para habilitar o deshabilitar las Comisiones para cada compa�ia telefonica
              */
             function onchangeTiempoAire(){
            	 var checkboxTiempoAire = document.getElementById("forma:affiliation_tiempoaire");
             		if(checkboxTiempoAire.checked==true){
             			activateTA();
    				}else{
    					deactivateTA();
    				}
             }
             
             
             function onchangeProducto(){
            	 var seleccion= valorSeleccionGarantiaLiquida();
            	 inicializarEquipos();
            	 inicializarSolucion(); // ->inicializarAlianza -> inicializarPaquete & inicializarIntegracion -> onchangeIntegracion
            	 inicializarCuotaAfiliacion();
            	 inicializarGarantiaLiquida();//F-83585 Garantia Liquida
            	 inicializarMujerPyME();
            	 
            	 if(seleccion==1){
            		 inicializarFianza(); //-> onchangeFianza
            	 }
            	 
            	 inicializarModalidad();
            	 inicializarCashback(); //-> onchangeCashback
            	 inicializarCaptacion();
            	 inicializarTiempoAire(); //-> onchangeTiempoAire
            	 inicializarPaquete();
            	 inicializarBuroExterno();
            	 inicializarServiciosEspeciales();
            	 validarRentaMensual();
            	 onchangeRentBy();
            	 inicializarPlanes();
            	 inicializarDivisas();
            	 
             }
           
             /**
              * Funcion para ocultar o mostrar el recuadro de equipos en base al producto seleccionado
              */
             function inicializarEquipos(){
            	 var comboProducto=document.getElementById("forma:affiliation_productdesc");

            	 if(comboProducto.value=="Cargo Recurrente" || comboProducto.value=="Comercio Electronico"){
            		 equipos("desactivar");
            		 mostrarEquipos("none");
            		 mostrarMpos("none");            		 
            	 }else if(comboProducto.value=="Terminal Personal Banorte (Mpos)"){
            		 equipos("activar");
            		 mostrarEquipos("none");
            		 mostrarMpos("block");
            	 }else{
            		 equipos("activar");
            		 mostrarEquipos("block");
            		 mostrarMpos("none");
            	 }
             }
             
             /**
              * Funcion que habilita o deshabilita los equipos
              * @param accion "desactivar" o "activar"
              */
             function equipos(accion){
            	var equipments = new Array(
            			"forma:affiliation_quantmanual",
            			"forma:tpvTel",
            			"forma:tpvMovil",
            			"forma:tpvInternet",
            			"forma:tpvInternetTel",
            			"forma:affiliation_quantpinpad",
            			"forma:tpvBlueTel",
            			"forma:tpvGprs",
            			"forma:tpvBlueInternet",
            			"forma:wifiMovil",
            			"forma:wifiTel"
            			);
            		if(accion=="desactivar"){
            			for (var i = 0; i < equipments.length; i++){
        					document.getElementById(equipments[i]).value="0";
        					document.getElementById(equipments[i]).disabled=true;
        				}
            		}else if(accion=="activar"){
            			for (var i = 0; i < equipments.length; i++){
        					document.getElementById(equipments[i]).disabled=false;
        					if(document.getElementById(equipments[i]).value=="" ||
        							document.getElementById(equipments[i]).value==null)
        					{
        						document.getElementById(equipments[i]).value=0;
        					}
        				}
            		}
            		//para desactivar el equipo de validacion en base al producto
    				var producto = document.getElementById("forma:affiliation_productdesc");
    				if(producto.value=="Terminal punto de venta"){
    					document.getElementById("forma:tpvGprs").disabled=false;
    				}else if(producto.value=="Interredes"){
    					if(document.getElementById("forma:affiliation_alliance").value == "Micros")
    						document.getElementById("forma:tpvInternet").disabled=false;
    					else
    						document.getElementById("forma:affiliation_quantpinpad").disabled=false;
    				}
             }
             
             /**
              * Funcion que habilita y deshabilita la modalidad en base al producto seleccionado
              */
             function inicializarModalidad(){
            	 var producto = document.getElementById("forma:affiliation_productdesc");
            	 var modalidad = document.getElementById("forma:affiliation_modality");
            	 
            	 if(producto.value=="Cargo Recurrente" ||
            			 producto.value=="Comercio Electronico")
            	 {
            		 modalidad.disabled=true;
            	 }else{
            		 modalidad.disabled=false;
            	 }
             }
             
             
             /**
              * Funcion que inicializa campos en base al plan seleccionado
              * inicializarPaquete y onchange
              */
             function onchangePlan(){
            	var comboPlanBanorte = document.getElementById("forma:affiliation_comercialplan");
             	var comboPlanIxe = document.getElementById("forma:affiliation_comercialplanIxe");
             	var red = valorRed();
               	var planSeleccionado=null;
               	var otroPlan = document.getElementById("forma:otherCommercialPlan");
               	var comisionIntnlMn = document.getElementById("forma:affiliation_avcommisionintnlmn");
           		var comisionIntnlDlls = document.getElementById("forma:affiliation_avcommisionintnldlls");
           		var rentaMensualPesos = document.getElementById("forma:affiliation_monthlyratemn");
           		var cboRentaMensualPesos = document.getElementById("forma:optionMonthlyratemn");
            	var rentaMensualDolares = document.getElementById("forma:affiliation_monthlyratedlls");
            	var cboRentaMensualDolares = document.getElementById("forma:optionMonthlyratedlls");
           		var divisa = document.getElementById("forma:affiliation_currency");
           		
               	if(red==0){//banorte
               		planSeleccionado = comboPlanBanorte.value;
               	}else if(red==1){//ixe
               		planSeleccionado = comboPlanIxe.value;
               	}
               	
          /*     	var checkboxCaptacion = document.getElementById("forma:impulsoCaptacion");
               	var comisionCaptacion = document.getElementById("forma:aff_captacionmn");
           	 
            	if(checkboxCaptacion.checked == false){
            		comisionCaptacion.value = "0.0";
            		comisionCaptacion.disabled = true;
	          	}*/
               	
               	inicializarCaptacion();
               	inicializarMujerPyME();
               	inicializarTablaComisiones();
               	
               	if(planSeleccionado==6){ //"otro"
               		var campoOtroPlan = document.getElementById("forma:otherCommercialPlan");
               		if(campoOtroPlan.value==""){
               			alert("Por favor especifique solo la clave. Cualquier comentario adicional realizarlo en la seccion 'Comentarios'");
               		}
               		otroPlan.disabled = false; //se activa el campo de "especifique"
               		
               		
               		//Habilita Comision internacional divisas o pesos dependiendo de la divisa seleccionada
           			if (divisa.value== "Pesos") {//Divisa en pesos
           				comisionIntnlMn.disabled=false;
           				comisionIntnlDlls.disabled=true;
           				rentaMensualPesos.disabled=false;
           				rentaMensualDolares.disabled=true;
           				cboRentaMensualPesos.disabled=false;
           				cboRentaMensualDolares.disabled=true;
         			} else if(divisa.value=="Dolares"){//si la divisa es diferente a pesos
         				comisionIntnlMn.disabled=true;
           				comisionIntnlDlls.disabled=false;
           				rentaMensualPesos.disabled=true;
           				rentaMensualDolares.disabled=false;
           				cboRentaMensualPesos.disabled=true;
           				cboRentaMensualDolares.disabled=false;
         			}else { //ambos
         				comisionIntnlMn.disabled=false;
           				comisionIntnlDlls.disabled=false;
           				rentaMensualPesos.disabled=false;
           				rentaMensualDolares.disabled=false;
           				cboRentaMensualPesos.disabled=false;
           				cboRentaMensualDolares.disabled=false;
         			}
               		
               	}else{
               		otroPlan.value="";
               		otroPlan.disabled=true;
               		
       				
       				if(planSeleccionado==5){ //"Estudio de Rentabilidad"
	       				//Habilita Comision internacional y Renta Mensual, divisas o pesos dependiendo de la divisa seleccionada
	           			if (divisa.value== "Pesos") {//Divisa en pesos
	           				comisionIntnlMn.disabled=false;
	           				comisionIntnlDlls.disabled=true;
	           				rentaMensualPesos.disabled=false;
	           				rentaMensualDolares.disabled=true;
	           				cboRentaMensualPesos.disabled=false;
	           				cboRentaMensualDolares.disabled=true;
	         			} else if(divisa.value=="Dolares"){//si la divisa es diferente a pesos
	         				comisionIntnlMn.disabled=true;
	           				comisionIntnlDlls.disabled=false;
	           				rentaMensualPesos.disabled=true;
	           				rentaMensualDolares.disabled=false;
	           				cboRentaMensualPesos.disabled=true;
	           				cboRentaMensualDolares.disabled=false;
	         			}else { //ambos
	         				comisionIntnlMn.disabled=false;
	           				comisionIntnlDlls.disabled=false;
	           				rentaMensualPesos.disabled=false;
	           				rentaMensualDolares.disabled=false;
	           				cboRentaMensualPesos.disabled=false;
	           				cboRentaMensualDolares.disabled=false;
	         			}
       				}else{
       					comisionIntnlMn.disabled=true;
           				comisionIntnlDlls.disabled=true;
           				rentaMensualPesos.disabled=true;
           				rentaMensualDolares.disabled=true;
           				cboRentaMensualPesos.disabled=true;
           				cboRentaMensualDolares.disabled=true;
       				}
               	}
               	
               	/*if(planSeleccionado == 19 || planSeleccionado == 24){
               		document.getElementById("forma:affiliation_monthlyratemn").disabled=false;
               		document.getElementById("forma:optionMonthlyratemn").disabled=false;
               		document.getElementById("forma:affiliation_monthlyratedlls").disabled=false;
               		document.getElementById("forma:optionMonthlyratedlls").disabled=false;
               	}else{
               		document.getElementById("forma:affiliation_monthlyratemn").disabled=true;
               		document.getElementById("forma:optionMonthlyratemn").disabled=true;
               		document.getElementById("forma:affiliation_monthlyratedlls").disabled=true;
               		document.getElementById("forma:optionMonthlyratedlls").disabled=true;
               	}*/
               	
               	recalcularTabla(true);
               	inicializarSustitucionRenta();
               	inicializarCuotaAfiliacion();
               	validarRentaMensual();
               	enabledSustitucionRentaMensual();
               	isValidVentasMensuales();
             }
             
             
             /**
              * Funcion que habilita o deshabilita el campo de renta mensual en base a la divisa elegida
              * es llamado por inicializar tabla comisiones
              */
             function inicializarRentaMensual(){
            	var rentaMensualPesos = document.getElementById("forma:affiliation_monthlyratemn");
            	var rentaMensualDolares = document.getElementById("forma:affiliation_monthlyratedlls");
             	var divisa = document.getElementById("forma:affiliation_currency");
	           			if (divisa.value== "Pesos") {
	           				rentaMensualDolares.value="0";
	           				rentaMensualDolares.disabled=true;
	           				rentaMensualPesos.disabled=false;
	         			} else if(divisa.value=="Dolares"){//si la divisa es diferente a pesos
	         				rentaMensualDolares.disabled=false;
	         				rentaMensualPesos.value="0";
	         				rentaMensualPesos.disabled=true;	
	         			}else { //ambos
	         				rentaMensualDolares.disabled=false;
	         				rentaMensualPesos.disabled=false;
	         			}
             }
             
             
             /**
              * Funcion para habilitar o deshabilitar el combo de solucion en base al producto
              * manda llamar a llenar el combo de la alianza
              */
             function inicializarSolucion(){
            	 var producto = document.getElementById("forma:affiliation_productdesc");
            	 var solucion 	= document.getElementById("forma:affiliation_soluciontype");
            	 var comboAlianza = document.getElementById("forma:affiliation_alliance");
            	 var comboPaquete = document.getElementById("forma:affiliation_chargeType");
            	 if(producto.value=="Comercio Electronico"){
            		 solucion.disabled=true;
            	 }else{
            		 solucion.disabled=false;
            	 }
            	 if(solucion.value=="Tradicional"){
            		 comboPaquete.value=0;
            		 comboPaquete.disabled=true;
            	 }
            	 //Se manda llamar para llenar el combo de la alianza dependiendo si trae o no solucion se llena el combo de alianza
            	 inicializarAlianza();
             }
             
             
             /**
              * Funcion para llenar cualquier combo con las opciones recibidas, ademas selecciona el valor del combo que trae actualmente
              * @param idCombo el id del combo a llenar
              * @param arregloOpciones arreglo de opciones a agregar
              * @param iniciarEnPosicion indice, permite llenar los elementos a partir de equis posicion y si no se manda, inicia del indice 1(se supone que el 0 ya esta establecido)
              */
             function llenarComboGeneral(idCombo, arregloOpciones, iniciarEnPosicion){
            	 var combo = document.getElementById(idCombo);
            	 if(combo==null){
            		 return false;
            	 }
            	 var indice = (iniciarEnPosicion!=null)?iniciarEnPosicion:1;
            	 var valorActualCombo = combo.value;
            	 combo.options.length=indice;
            	 if(arregloOpciones!=null && arregloOpciones.length>0){
            		 for(var i=0; i<arregloOpciones.length; i++){
            			  var option = document.createElement('option');
	   	           		  combo.options.add(option, i+indice);
	   	           		  combo.options[i+indice].value = arregloOpciones[i];
	   	           		  combo.options[i+indice].innerText = arregloOpciones[i];
	   	           		  combo.options[i+indice].innerHTML = arregloOpciones[i];
	   	           		  //para cambiar nombre a netpay
	   	           		  if(arregloOpciones[i]=="Netpay"){
	   	           			  combo.options[i+indice].innerText = "Netpay (Caja Total Banorte)";
	   	           			  combo.options[i+indice].innerHTML = "Netpay (Caja Total Banorte)";
	   	           		  }
            		 }
            		 var encontro = false;
                	 for(var i=0; i<combo.options.length; i++){
    	    	    	if(valorActualCombo == combo.options[i].value){
    	    	    		combo.options[i].selected=true;
    	    	    		encontro=true;
    	    	    	}
    	             }
                	 if(encontro == false){
                		 combo.options[0].selected=true;
                	 }
            	 }
             }
             
             
             /**
              * es llamada por inicializarIntegracion
              * Funcion que habilita o deshabilita el combo de renta en dolares en base a la alianza e integracion seleccionada (solo cybersource)
              */
             function onchangeIntegracion(){
            	 var comboProducto = document.getElementById("forma:affiliation_productdesc");
            	 var comboIntegracion = document.getElementById("forma:affiliation_integration");
            	 var comboAlianza = document.getElementById("forma:affiliation_alliance");
            	 var comboRentaDolar = document.getElementById("forma:affiliation_rentaDolar");
                 
            	 if(comboAlianza.value == "Tradicional" || comboAlianza.value == "Call Center" || (comboAlianza.value == "Agregador" && comboProducto.value == "Comercio Electronico")){
            		 if(comboIntegracion.value == "Cybersource Enterprise Revision Manual" ||
            				 comboIntegracion.value == "Cybersource Enterprise Autenticación Selectiva" ||
            				 comboIntegracion.value == "Cybersource Enterprise Call Center"){
	            		 comboRentaDolar.disabled =false;
	            	 }else{
	            		 comboRentaDolar.disabled =true;
	            		 comboRentaDolar.value =0;
	            	 }
            	 } else{
            		 comboRentaDolar.disabled =true;
            		 comboRentaDolar.value =0;
            	 }
            	 inicializarPaquete();
            	 inicializarRentBy();
            	 recalcularTabla(true);
            	 inicializarGarantiaLiquida();
             }
             
             
             /**
              * Funcion que habilita o deshabilita el combo de integracion en base a la alianza seleccionada (solo comercio electronico con cybersource)
              */
             function inicializarIntegracion(){
            	 var comboAlianza = document.getElementById("forma:affiliation_alliance");
            	 var comboIntegracion = document.getElementById("forma:affiliation_integration");
            	 
            	 var comboProducto = document.getElementById("forma:affiliation_productdesc");
            	 var elementosIntegracion = new Array();
            	 
            	 if(comboAlianza.value == "Tradicional"){
            		 comboIntegracion.disabled =false;
            		 elementosIntegracion = ["Sin Cybersource / Sin 3D", "Sin Cybersource / Con 3D", "Cybersource Enterprise Revision Manual", "Cybersource Enterprise Autenticación Selectiva", "Cybersource Direct"];
            		 document.getElementById("forma:ayudaIntegracionCyberSource").hidden = false;
            	 }else if(comboAlianza.value == "Hosted"){
            		 comboIntegracion.disabled =false;
            		 elementosIntegracion = ["Payworks Hosted", "Cybersource Hosted"];
            		 document.getElementById("forma:ayudaIntegracionCyberSource").hidden = false;
            	 }else if(comboAlianza.value == "Call Center"){
            		 comboIntegracion.disabled =false;
            		 elementosIntegracion = ["Sin Cybersource / Sin 3D", "Payworks Punto de Venta"];
            		 document.getElementById("forma:ayudaIntegracionCyberSource").hidden = false;
            	 }else if(comboAlianza.value == "Agregador" && comboProducto.value == "Comercio Electronico"){
            		 comboIntegracion.disabled =false;
            		 elementosIntegracion = ["Sin Cybersource / Sin 3D", "Sin Cybersource / Con 3D", "Cybersource Enterprise Revision Manual", "Cybersource Enterprise Autenticación Selectiva", "Cybersource Direct", "Cybersource Call Center", "Cybersource Enterprise Call Center"];
            		 document.getElementById("forma:ayudaIntegracionCyberSource").hidden = false;
            	 }else{
            		 comboIntegracion.value=0;
            		 comboIntegracion.disabled =true;
            		 document.getElementById("forma:ayudaIntegracionCyberSource").hidden = true;
            	 }
            	 
            	 llenarComboGeneral("forma:affiliation_integration", elementosIntegracion, 0);
            	 onchangeIntegracion();
             }
             
             
             /**
              * Funcion que se ejecuta en base a la alianza(onchange) habilita o deshabilita campos y planes que aplican para alianza agregador
              * @param deshabilitar es la instruccion "true"(deshabilitar) o "false"(habilitar) 
              * @param valorPlanSeleccionado es el valor del combo de plan que ya trae seleccionado (en caso de edicion)
              * @param red (es la red que se tiene seleccionada Banorte/Ixe)
              */
             function inicializarAgregador(deshabilitar, valorPlanSeleccionado, red){
                var plan = document.getElementById("forma:affiliation_comercialplan");
                var planIxe = document.getElementById("forma:affiliation_comercialplanIxe");
                var listaCheckBox = [
             	                      "forma:affiliation_openkey",
             	                      "forma:affiliation_forceauth",
             	                      //"forma:affiliation_tpv_payroll",
             	                      "forma:affiliation_qps",
             	                      "forma:affiliation_tiempoaire",
             	                      "forma:amex",
             	                      "forma:dcc"
             	  ];
                var idComboPlanBanorte="forma:affiliation_comercialplan";
                var idComboPlanIxe="forma:affiliation_comercialplanIxe";
                var opcionesDeshabilitarPlanBanorte=["1","2","4","7"];
                var opcionesDeshabilitarPlanIxe=["8","9","10","11","12","13","14"];
                
                if(deshabilitar==true){
             	  toggleCheckbox(listaCheckBox,deshabilitar); //se deshabilitan los campos
             	  toggleComboOptions(idComboPlanBanorte,opcionesDeshabilitarPlanBanorte,deshabilitar);
             	  toggleComboOptions(idComboPlanIxe,opcionesDeshabilitarPlanIxe,deshabilitar);
             	  
             	  if(red==0){//si el plan elegido es banorte
                 	   	 var opcionSeleccionada=plan.options[plan.selectedIndex];
                 	   	 //Si la opcion seleccionada esta deshabilitada, entonces se pone por default la primer opcion
                 	   	 if(opcionSeleccionada!=null && opcionSeleccionada.disabled){
                 	   		plan.options[0].selected=true;
                 	   	 }else{
                 	   		plan.value=valorPlanSeleccionado;
                 	   	 }
                  }else if(red==1){//si el plan elegido es ixe,
                 	   var opcionSeleccionada=planIxe.options[planIxe.selectedIndex];//obtenemos la opcion seleccionada actual
                 	   	 //Si la opcion seleccionada esta deshabilitada, entonces se pone por default la primer opcion
                 	   	 if(opcionSeleccionada!=null && opcionSeleccionada.disabled){
                 	   		planIxe.options[0].selected=true;
                 	   	 }else{
                 	   		planIxe.value=valorPlanSeleccionado;
                 	   	 }
                    }else{//si no trae plan elegido
                          plan.selectedIndex=0;
                          planIxe.selectedIndex=0;
                    }
                }else{//si no eligio agregador
             	  toggleCheckbox(listaCheckBox,deshabilitar);
             	  toggleComboOptions(idComboPlanBanorte,opcionesDeshabilitarPlanBanorte,deshabilitar);//se va a habilitar, ya que deshabilitar=false
             	  toggleComboOptions(idComboPlanIxe,opcionesDeshabilitarPlanIxe,deshabilitar);//Se va a habilitar
                    
                    if(red==0){//si el plan elegido es banorte
                 	   plan.value=valorPlanSeleccionado;//si no, se selecciona el valor que trae
                    }else if(red==1){//si el plan elegido es ixe,
                        planIxe.value=valorPlanSeleccionado;//si es plan elegible, se queda el que traia
                    }else{//si no trae plan elegido
                        plan.selectedIndex=0;
                        planIxe.selectedIndex=0;
                    }
                }
                inicializarTiempoAire();
             }

             
             /**
              * Funcion que deshabilita / habilita un arreglo de checkboxes. Cuando se deshabilita un campo se quita el checked a ese checkbox.
              * Pero cuando se habilita, solamente se habilita, no se marca como checked.
              * @param arregloIdsCheckbox Arreglo de los id's de los checkboxes que queremos habilitar o deshabilitar.
              * @param disabled bandera booleana que indica si se va a deshabilitar (true)  o no.
              */
             function toggleCheckbox(arregloIdsCheckbox,disabled){
                 var markAsChecked=!disabled;//Si lo va a deshabilitar entonces no lo deja marcado, 
                 if(arregloIdsCheckbox!=null && arregloIdsCheckbox.length>0){
                     for(var i=0;i<arregloIdsCheckbox.length;i++){
                         var idCheckbox=arregloIdsCheckbox[i];
                         var checkbox=document.getElementById(idCheckbox);
                         if(checkbox!=null){
                             checkbox.disabled=disabled;
                             //Si se va a deshabilitar, entonces se le quita el checked.
                             if(disabled){
                             	checkbox.checked=markAsChecked;	
                             }
                         }
                     }
                 }
             }

             
             /**
              * Habilita o deshabilita las opciones de un combo.
              * @param idCombo Id del combo del cual queremos habilitar o deshabilitar opciones especificas.
              * @param arregloValoresOpciones Arreglo de los valores para deshabilitar o habilitar estas opciones dentro del combo "idCombo"
              * @param disabled Bandera para habilitar (false) o deshabilitar(true) las opciones proporcionadas en el "arregloValoresOpciones"
              * @returns {Boolean}
              * Uso: toggleComboOptions('comboNombres',['op1','op2'],true);
              * En este caso, el combo con id:"comboNombres", tiene varias opciones, de estas, solo se deshabilitaran "op1" y "op2". 
              * Para habilitar estas 2 opciones se hace lo mismo, pero el ultimo valor cambia a "false". 
              */
             function toggleComboOptions(idCombo,arregloValoresOpciones,disabled){
             	var combo=document.getElementById(idCombo);
             	if(combo==null){
             		return false;
             	}
             	if(arregloValoresOpciones!=null && arregloValoresOpciones.length>0){
             		var opciones=combo.options;
             		for(var i=0;i<opciones.length;i++){
             			var opcion=opciones[i];
             			var valorOpcion=opcion.value;
             			//Si la opcion esta en el arreglo de opciones que vamos a habilitar/deshabilitar
             			var existeOpcionEnArreglo=containsInArray(valorOpcion,arregloValoresOpciones);
             			if(existeOpcionEnArreglo){
             				opcion.disabled=disabled;//Si se va a deshabilitar, entonces se deshabilita, sino, como quiera cambia el disabled a false.
             			}
             		}
             	}
             }
             /**
              * Funcion generica que revisa si un valor esta dentro de una lista de valores.
              * @param valueToFind Valor a encontrar
              * @param array Arreglo de valores en el cual se va a buscar si existe el valor "valueToFind"
              * @returns {Boolean} Boolean que indica si se encontro o no el valor en el arreglo de valores proporcionado.
              */
             function containsInArray(valueToFind,array){
             	var exist=false;
             	if(array!=null && array.length>0 && valueToFind!=null){
             		for(var i=0;i<array.length;i++){
             			var element=array[i];
             			if(valueToFind==element){
             				exist=true;
             				break;
             			}
             		}
             	}
             	return exist;
             }
             
            /**
             * Funcion que muestra mensaje de confirmacion para agregar mas equipos, en base a la alianza
             */
         	function onchangeQuantEquipment(){
        		var numEquiposTpv 	= document.getElementById("forma:tpvGprs").value;
        		var numPinpad = document.getElementById("forma:affiliation_quantpinpad").value;
        		var alianzaSeleccionada 	= document.getElementById("forma:affiliation_alliance").value;
        		var otroEquipo = document.getElementById("forma:affiliation_quantmanual");//como ejemplo de otro equipo
            	
        		if(alianzaSeleccionada == "Micros"){
          	    	if(numEquiposTpv >0 || numPinpad>0){//TPV GPRS o PIN PAD
          	    		if(otroEquipo.disabled==true){
          	    			alert("Si se requiere y asi se negocio con el cliente,  podras seleccionar algun otro equipo, " +
            						"en el entendido de que estos equipos no estaran conectados bajo la misma plataforma de MICROS. " +
            						"Valida en la Normativa los equipos disponibles para operar la funcionalidad de MICROS.");
            				activateEquipments();
          	    		}
        			}else if (numEquiposTpv == 0) {
        				deactivateEquipments();
        			}
          	    }else  if(alianzaSeleccionada == "Netpay"){
          	    	if(numEquiposTpv >0 || numPinpad>0){
          	    		if(otroEquipo.disabled==true){
          	    			alert("Valida en la Normativa los equipos disponibles para operar la funcionalidad de Caja Total.");
              	    		equipos("activar");
          	    		}
          	    	}
          	    }
        		recalculateCommisionTable();
        	}
             
         	
         	function mostrarEquipos(display){
         		   var prefix = "forma:";
         		   var bloqueEquipos = document.getElementById("forma:equipmentDetail");
         		   var equipos = new Array(
         					"tpvTel",
         					"tpvMovil",
         					"tpvInternet",
         					"tpvInternetTel",
         					"affiliation_quantpinpad",
         					"tpvBlueTel",
         					"tpvGprs",
         					"tpvBlueInternet",
         					"wifiMovil",
         					"wifiTel",
         					"affiliation_quantmanual"
         					);
         			
         			if(display == "none"){
         				for (var i = 0; i < equipos.length; i++){
         					document.getElementById(prefix + equipos[i]).value="0";
         				}
         			}	
         			
         			bloqueEquipos.style.display =display;

         		}
         	
         		function mostrarMpos(display){
         			var prefix = "forma:";
         			var bloqueEquipos = document.getElementById("forma:equipmentMpos");
         			var equipos = new Array(
          					"mposMonthlyrate",
          					"mposEquipment"
          					);
          			
          			if(display == "none"){
          				for (var i = 0; i < equipos.length; i++){
          					document.getElementById(prefix + equipos[i]).value="0";
          				}
          			}	
          			
          			bloqueEquipos.style.display =display;
         		} 
         		
         		//F-83585 HeB - Garantia Liquida: Mostrar options
         		function mostrarSeleccionGarantiaLiquida(display){
         			//var prefix = "forma:";
         			var bloqueSeleccionGarantiaLiquida = document.getElementById("forma:seleccionGarantiaLiquida");
         			//alert('Dentro de mostrarSeleccionGarantiaLiquida: '+display);
         			/*
         			 * 
         			 var equipos = new Array(
         					"aff_seleccion_garantiaLiquida"
          					);
          			
          			if(display == "none"){
          				for (var i = 0; i < equipos.length; i++){
          					document.getElementById(prefix + equipos[i]).value="0";
          				}
          			}	
          			*/
         			
         			bloqueSeleccionGarantiaLiquida.style.display =display;
         			
         		} 
         		
         		//F-83585 HeB - Garantia Liquida: Mostrar Garantia Liquida
         		function mostrarGarantiaLiquida(display){

         			var bloqueGarantiaLiquida = document.getElementById("forma:apartadoGarantiaLiquida");
         			bloqueGarantiaLiquida.style.display =display;
         		} 
         		
         		//F-83585 HeB - Garantia Liquida: Mostrar Fianza
         		function mostrarFianza(display){
         			var prefix = "forma:";
         			var bloqueFianza = document.getElementById("forma:apartadoFianza");
         			bloqueFianza.style.display =display;
         		} 
         	
         	
         		//F-83585 HeB - Garantia Liquida: Mostrar Comentarios Motivos Disminución o Excepción de GL
         		function mostrarComentariosDisminucionExcepcionGL(display){
         			var prefix = "forma:";
         			var bloqueComentariosDisminucionExcepcionGL = document.getElementById("forma:apartadoComentariosDisminucionExcepcionGL");
         			bloqueComentariosDisminucionExcepcionGL.style.display =display;
         		} 
         	
         		function onchangeRed(){
         			var red = valorRed();
         			
         			inicializarPlanes(red);
         			inicializarCuotaAfiliacion();
         			cargarXMLGiros();
         			validaGiroIxe();
         			cargarCotizadores(red);
         		}
         		
         		function validaGiroIxe(){
         			 var giro = document.getElementById("forma:client_categorycode");//giro
                	 var numGiro = giro.value.substring(0,6);//numero de giro
                	 var red = valorRed();
                	 if(red==1){
                		 if(numGiro=="005300"||numGiro=="005310"||numGiro=="005311"||
                         	 	numGiro=="005331"||numGiro=="005411")
                         	 	{
                         		 	giro.value="";
                         		 	giro.innerText="";
                         		 	giro.innerHTML="";
                         	 	}	 
                	 }
         		}
         		
         		/**
         		 * Funcion que se llama al hacer cambio de plan para inicializar los campos de sustituir renta mensual y valor
         		 */
         		function inicializarSustitucionRenta(){
         			var comboSustituirPesos = document.getElementById("forma:optionMonthlyratemn");
         			var comboSustituirDolares = document.getElementById("forma:optionMonthlyratedlls");
         			var divisa = document.getElementById("forma:affiliation_currency");
         			var montoSustituirPesos = document.getElementById("forma:replaceAmountratemn");
         			var montoSustituirDolares =  document.getElementById("forma:replaceAmountratedlls");
         			
         			if(divisa.value=="Pesos"){
         				if(comboSustituirPesos.value=="No"){
         					montoSustituirPesos.value=0;
         				}
         			}else if(divisa.value=="Dolares"){
         				if(comboSustituirDolares.value=="No"){
         					montoSustituirDolares.value=0;
         				}
         			}else{
         				if(comboSustituirPesos.value=="No"){
         					montoSustituirPesos.value=0;
         				}
         				if(comboSustituirDolares.value=="No"){
         					montoSustituirDolares.value=0;
         				}
         			}
         		}
         		
         		function inicializarTablaComisiones(){
         			var comboPlanBanorte = document.getElementById("forma:affiliation_comercialplan");
                 	var comboPlanIxe = document.getElementById("forma:affiliation_comercialplanIxe");
                 	var sustituirRentaMn = document.getElementById("forma:optionMonthlyratemn");
                 	var sustituirRentaDlls = document.getElementById("forma:optionMonthlyratedlls");
                 	var red = valorRed();
                   	var planSeleccionado=null;
                   	var otrosConceptos = document.getElementById("forma:affiliation_otherconcept1des");
                   	var pesos = getAdquirerPesosCtrlsArray();
                	var dolares = getAdquirerDollarCtrlsArray();
                	var divisa = document.getElementById("forma:affiliation_currency");
                   	var otroPlan = document.getElementById("forma:otherCommercialPlan");

                   	if(red==0){//banorte
                   		planSeleccionado = comboPlanBanorte.value;
                   	}else if(red==1){//ixe
                   		planSeleccionado = comboPlanIxe.value;
                   	}
                   	
                   	if(planSeleccionado!=5 && planSeleccionado!=6){
                 		sustituirRentaMn.disabled =false;//
     	           	 	sustituirRentaDlls.disabled =false;
     	           	 	otrosConceptos.disabled=true;
     	           	 	
    	 	           	for (var i = 0; i < dolares.length; i++) {
    	        			document.getElementById("forma:"+dolares[i]).disabled = true;
    	        		}
    	 	           for (var i = 0; i < pesos.length; i++) {
    	 	        	  document.getElementById("forma:"+pesos[i]).disabled = true;
    	 	           }
    	 	           otroPlan.disabled=true;
    	 	           otroPlan.value="";
    	 	           
    	 	           document.getElementById("forma:affiliation_commisiontcmn").readOnly = true;
    	 	           document.getElementById("forma:affiliation_commisiontcdlls").readOnly = true;
    	 	           document.getElementById("forma:affiliation_commisiontdmn").readOnly = true;
    	 	           document.getElementById("forma:affiliation_commisiontddlls").readOnly = true;
    	 	           document.getElementById("forma:affiliation_commisionintnlmn").readOnly = true;
    	 	           document.getElementById("forma:affiliation_commisionintnldlls").readOnly = true;
    	 	           
                 	}else{ //si el plan es estudio de rentabilidad u otro
                 		sustituirRentaMn.disabled =true;
              			sustituirRentaDlls.disabled =true;
              			
              			if (divisa.value== "Pesos") {
            				for (var i = 0; i < dolares.length; i++) {
            					document.getElementById("forma:"+dolares[i]).value = "0";
            					document.getElementById("forma:"+dolares[i]).disabled = true;
            				}
            				for (var i = 0; i < pesos.length; i++) {
            					document.getElementById("forma:"+pesos[i]).disabled = false;
            				}
            			} else if(divisa.value=="Dolares"){//si la divisa es diferente a pesos
            				for (var i = 0; i < dolares.length; i++) {
            					document.getElementById("forma:"+dolares[i]).disabled = false;
            				}
            				for (var i = 0; i < pesos.length; i++) {
            					document.getElementById("forma:"+pesos[i]).disabled = true;
            					document.getElementById("forma:"+pesos[i]).value="0";
            				}
            			}else {
            				for (var i = 0; i < pesos.length; i++) {
            					document.getElementById("forma:"+pesos[i]).disabled = false;
            				}
            				for (var i = 0; i < dolares.length; i++) {
            					document.getElementById("forma:"+dolares[i]).disabled = false;
            				}
            			}
              			
              			document.getElementById("forma:affiliation_commisiontcmn").readOnly = false;
              			document.getElementById("forma:affiliation_commisiontcdlls").readOnly = false;
              			document.getElementById("forma:affiliation_commisiontdmn").readOnly = false;
              			document.getElementById("forma:affiliation_commisiontddlls").readOnly = false;
              			document.getElementById("forma:affiliation_commisionintnlmn").readOnly = false;
              			document.getElementById("forma:affiliation_commisionintnldlls").readOnly = false;
                 	}
                   	inicializarRentaMensual();
         		}
         		
         		function onkeyupEquipment(equipment){
         			//var id = "forma:";
         			var eqpmt = document.getElementById(equipment);
         			if(eqpmt.value=="" || eqpmt.value==null){
         				eqpmt.value=0;
         			}
         		}
         		
         		/**
         		 * Revisa si requiere o no fianza en base a la antiguedad.  Llama a inicializarFianza
         		 */
         		function onchangeAntiqueness(){
         			var seleccion= valorSeleccionGarantiaLiquida();
         			if(seleccion==1){
         				inicializarFianza();
         			}
         			inicializarGroupNo();//F-98896 Mejora Garantia Liquida
         			inicializarGarantiaLiquida();//F-83585 Garantia Liquida
         		}
             
         		function planSeleccionado(){
         			var plan = document.getElementById("forma:affiliation_comercialplan");
         			var planIxe = document.getElementById("forma:affiliation_comercialplanIxe");
         			var planSeleccionado = 0;
         			var red=valorRed();
               	 
	               	 if(red==0){
	               		 planSeleccionado = plan.value;
	               	 }else{
	               		 planSeleccionado = planIxe.value;
	               	 }
         			return planSeleccionado;
         		}
         		
         		function inicializarCiudades(){
         			document.getElementById("forma").submit();
         		}
         		
         		function cargarXMLGiros(){
         			//if(red==0 || red==null){
         				//loadCoType('../img_contracts/inc/cotype.xml', 'forma:client_categorycode');//FIXME - Produccion
        				loadCoType('inc/cotype.xml', 'forma:client_categorycode'); //FIXME - Local 
         			//}else{//IXE RED=1
//         				loadCoType('../img_contracts/inc/cotypeIxe.xml', 'forma:client_categorycode'); //FIXME - Produccion
         			  //loadCoType('inc/cotypeIxe.xml', 'forma:client_categorycode'); //FIXME - Local
         			//}
         		}
         		
         		function onchangeManTranscriptor(){
         			var comboBuroExterno = document.getElementById("forma:affiliation_externalcredithistory");
         			var maquinaTranscriptora = document.getElementById("forma:affiliation_quantmanual");
         			var productoSeleccionado = document.getElementById("forma:affiliation_productdesc").value;
         			
         			if(productoSeleccionado=="Interredes" || productoSeleccionado=="Terminal punto de venta"){
         				if(maquinaTranscriptora.value>0){
             				comboBuroExterno.disabled=false;
             				alert('Favor de revisar Buro Externo');
             				comboBuroExterno.focus();
             			}else{
                 			//comboBuroExterno.disabled=true;//F-98896 Mejora Garantia Liquida
                 			comboBuroExterno.disabled=false;//activa buro externo
             			}
         			}else{
         				//comboBuroExterno.disabled=true;//F-98896 Mejora Garantia Liquida
         				comboBuroExterno.disabled=false;//activa buro externo
         			}
         		}
         		
         		function inicializarBuroExterno(){
         			var comboBuroExterno = document.getElementById("forma:affiliation_externalcredithistory");
         			var maquinaTranscriptora = document.getElementById("forma:affiliation_quantmanual");
         			var productoSeleccionado = document.getElementById("forma:affiliation_productdesc").value;
         			
         			if(productoSeleccionado=="Interredes" || productoSeleccionado=="Terminal punto de venta"){
         				if(maquinaTranscriptora.value>0){
             				comboBuroExterno.disabled=false;
             			}else{
             				//comboBuroExterno.disabled=true;//F-98896 Mejora Garantia Liquida
             				comboBuroExterno.disabled=false;//activa buro externo
             			}
         			}else{
         				//comboBuroExterno.disabled=true;	//F-98896 Mejora Garantia Liquida
         				comboBuroExterno.disabled=false;//activa buro externo
         			}
         		}
         		
         		/**
         		 * Metodo para habilitar o deshabilitar los checkbox de los servicios especiales para cada producto y alianza
         		 */
         		function inicializarServiciosEspeciales(){
         			var productoSeleccionado = document.getElementById("forma:affiliation_productdesc").value;
         			var alianza 		 = document.getElementById("forma:affiliation_alliance").value;
         			var integracion 	 = document.getElementById("forma:affiliation_integration").value;
         			
         			if(productoSeleccionado == "Cargo Recurrente"){
         					setValueServicios(true, true, true, true, true, true, true, true, false, false, true);
         			}else if(productoSeleccionado == "Comercio Electronico"){
         				setValueServicios(true, true, true, true, true, true, true, true, false, false, false);
         			}else if(productoSeleccionado == "Interredes"){
         				setValueServicios(false, false, false, true, false, true, false, true, false, false, false);
         			}else if(productoSeleccionado == "Terminal punto de venta"){
         				setValueServicios(false, false, false, true, false, false, false, true, false, false, false);
         			}else if(productoSeleccionado == "Terminal Personal Banorte (Mpos)"){
         					setValueServicios(true, true, true, true, true, true, false, true, false, false, true);
         			}
         			
         			if((alliance == "Tradicional" || alliance == "Hosted" || alliance == "Call Center" || (alliance == "Agregador" && productoSeleccionado == "Comercio Electronico")) && (integracion != "0" && integracion != "")){
         				setValueServicios(true, true, true, true, true, true, true, true, false, false, false);
         			}else if(alianza == "Netpay"){
         				setValueServicios(true, true, true, false, false, true, false, true, true, false, true);
         			}else if(alianza == "Micros"){
         				setValueServicios(false, false, true, true, true, false, false, true, false, false, true);
         			}
         			
         			var x = 0;
         		}
         		         		
         		function setValueServicios(tecladoAbierto, ventaForzada, qps, tiempoAire, cashback, dcc, amex, tpvDesatendida, mct, msp, pagoMovil){
         			var affiliation_openkey = document.getElementById("forma:affiliation_openkey");
         			var affiliation_forceauth = document.getElementById("forma:affiliation_forceauth");
         			var affiliation_qps = document.getElementById("forma:affiliation_qps");
         			var affiliation_tiempoaire = document.getElementById("forma:affiliation_tiempoaire");
         			var aff_cashback = document.getElementById("forma:aff_cashback");
         			var dcc_chk = document.getElementById("forma:dcc");
         			var amex_chk = document.getElementById("forma:amex");
         			var tvpUnattended = document.getElementById("forma:tvpUnattended");
         			var transConciliation = document.getElementById("forma:transConciliation");
         			var promNoteRqst = document.getElementById("forma:promNoteRqst");
         			var mobilePymnt = document.getElementById("forma:mobilePymnt");
         			
         			affiliation_openkey.disabled = tecladoAbierto;
     				affiliation_forceauth.disabled = ventaForzada;
     				affiliation_qps.disabled = qps;
     				affiliation_tiempoaire.disabled = tiempoAire;
     				aff_cashback.disabled = cashback;
     				dcc_chk.disabled = dcc;
     				amex_chk.disabled = amex;
     				tvpUnattended.disabled = tpvDesatendida;
     				//transConciliation.disabled = mct;
     				promNoteRqst.disabled = msp;
     				mobilePymnt.disabled = pagoMovil;
         		}
         		
         		function validarRentaMensual(){
         			var producto = document.getElementById("forma:affiliation_productdesc").value;
         			var alianza = document.getElementById("forma:affiliation_alliance").value;
         			var divisa = document.getElementById("forma:affiliation_currency").value;
         			
         			if(producto == "Terminal punto de venta" && alianza == "Netpay"){
         				var rentaMensualPesos = document.getElementById("forma:affiliation_monthlyratemn");
                   		var cboRentaMensualPesos = document.getElementById("forma:optionMonthlyratemn");
                    	var rentaMensualDolares = document.getElementById("forma:affiliation_monthlyratedlls");
                    	var cboRentaMensualDolares = document.getElementById("forma:optionMonthlyratedlls");
                    	
         				if(divisa == "Pesos"){
         					rentaMensualPesos.disabled=false;
         					cboRentaMensualPesos.disabled=false;
         					rentaMensualDolares.disabled=true;
         					cboRentaMensualDolares.disabled=true;
         				}else if(divisa == "Dolares"){
         					rentaMensualPesos.disabled=true;
         					cboRentaMensualPesos.disabled=true;
         					rentaMensualDolares.disabled=false;
         					cboRentaMensualDolares.disabled=false;
         				}else{
         					rentaMensualPesos.disabled=false;
         					cboRentaMensualPesos.disabled=false;
         					rentaMensualDolares.disabled=false;
         					cboRentaMensualDolares.disabled=false;
         				}
         			}
         				
         		}
         		
         		function inicializarDivisas(){
         			var comboDivisas = document.getElementById("forma:affiliation_currency");
         			if("Terminal Personal Banorte (Mpos)" == document.getElementById("forma:affiliation_productdesc").value){
         				comboDivisas.options[2].selected = true;
         				comboDivisas.options[0].disabled = true;
         				comboDivisas.options[1].disabled = true;
         			}else{
         				comboDivisas.options[0].selected = true;
         				comboDivisas.options[0].disabled = false;
         				comboDivisas.options[1].disabled = false;
         			}
         			onChangeDivisa();
         		}
             
         		function onKeyPressRentaMensual(){
         			var red = valorRed();
         			var producto = document.getElementById("forma:affiliation_productdesc").value;
         			var alianza = document.getElementById("forma:affiliation_alliance").value;
         			
         			if(red == 0 && producto == "Terminal punto de venta" && alianza == "Netpay" )
         				alert("La renta Mensual puede ser sustituida por Reciprocidad o Facturaci\u00f3n seg\u00fan la tabla de Sustituci\u00f3n");
         		}
         		
         		function cargarCotizadores(red){
         			 var divCotizaBanorte = document.getElementById("divCotizaBanorte");
                	 var divCotizaIxe = document.getElementById("divCotizaIxe");
                	 
         			if(red!=null){
         				if(red==0){//Banorte
         					divCotizaBanorte.style.display="block";
             				divCotizaIxe.style.display="none";
         				}else{//Ixe
         					divCotizaBanorte.style.display="none";
             				divCotizaIxe.style.display="block";
         				}
         			}else{
         				divCotizaBanorte.style.display="none";
         				divCotizaIxe.style.display="none";
         			}
         		}
         		
         		function enabledSustitucionRentaMensual(){
         			var plan = document.getElementById("forma:affiliation_comercialplan").value;
         			var divisa = document.getElementById("forma:affiliation_currency");
         			var sustituirPesos = document.getElementById("forma:optionMonthlyratemn");
         			var sustituirDolares = document.getElementById("forma:optionMonthlyratedlls");
         			if(plan != "0"){
	         			if(divisa.value=="Ambos"){
	         				sustituirPesos.disabled = false;
	         				sustituirDolares.disabled = false;
	         			}else if(divisa.value=="Pesos"){
	         				sustituirPesos.disabled = false;
	         			}else{//Dolares
	         				sustituirDolares.disabled = false;
	         			}
         			}
         		}

         		
         		
         		function alertIntegracionCyberSource(){
         			alert(
         					"A)	Sin Cybersource / Sin 3D: No cuenta con ningún servicio de 3D y Cybersource. \nB)	Sin Cybersource / Con 3D: Cuenta con el servicio de 3D Secure que respalda alguna aclaración. \nC)	Cybersource Enterprise Revisión Manual: Es para Comercios que requieren el control de la transacción incluyendo la revisión manual por medio de un analista (sólo las de review) desde la Consola de Cybersource con costo en USD. \nD)	Cybersource Enterprise Autenticación Selectiva: Es para el Comercio que requiere tener el control de la transacción incluyendo la autenticación 3D Secure (sólo las de review) con costo en USD. \nE)	Cybersource Direct: Es para el Comercio que requiere tener el control de la transacción incluyendo la autenticación 3D Secure (sólo ciertas transacciones). \nF)	Cybersource Hosted: Cuenta con el servicio de 3D Secure y la herramienta de Prevención de Fraudes. \nG)	Cybersource Call Center: Es para el Comercio que requiere tener una herramienta para minimizar las transacciones fraudulentas. \nH)	Cybersource Enterprise Call Center: Es para el Comercio que requiere establecer sus propias reglas de fraudes y minimizar las transacciones fraudulentas."
         					);
         		}
         		
         		
         		/**
                 * Funcion para permitir agregar pago y retiro en efectivo con las familias de giros: 
                 * Gasolineras, Retail, Otros y no clasificados, Refacciones y ferreterías, Misceláneas, Farmacias, Supermercados, Grandes superficies y Agregadores
                */ 
			    function isGiroCashBack(giro){
			    	var bandera = false;
         			var producto 			 = document.getElementById("forma:affiliation_productdesc").value;
			    	var divisa 				 = document.getElementById("forma:affiliation_currency").value;
			    	var girosCashback 		 = new Array("000763","005013","005021","005039","005044","005045","005046","005047","005065","005072","005074","005085","008351", 
														 "005094","005099","005111","005131","005137","005139","005169","005192","005193","005198","005199","005200","005211",
														 "005231","005261","005271","005309","005441","005451","005462","005511","005521","005532","005533","005551","005251",
														 "005541","005542","005561","005571","005592","005598","005611","005621","005631","005641","005651","005655","005411",
														 "005661","005681","005691","005697","005698","005699","005712","005713","005714","005718","005719","005722","005732",
														 "005733","005734","005735","005811","005921","005931","005932","005933","005935","005937","005940","005941","005942",
														 "005943","005944","005945","005946","005947","005948","005949","005950","005963","005965","005970","005971","005972",
														 "005973","005975","005976","005977","005978","005983","005992","005993","005994","005995","005996","005997","005998",
														 "005999","007216","007217","007221","007251","007394","007534","007538","007623","007629","007631","007641","007692",
														 "007699","007993","000780","001520","001711","001731","001740","001750","001761","001771","001799","002741","005331",
														 "002791","002842","004119","004214","004215","004225","004457","004468","004816","004829","005051","005122","005399",
														 "005172","005962","005964","005966","005967","005969","006010","006011","006012","006051","006211","007033","007210",
														 "007211","007261","007273","007276","007277","007278","007297","007298","007299","007311","007321","007333","007338",
														 "007339","007342","007349","007361","007372","007375","007379","007392","007393","007395","007399","007511","007513",
														 "007531","007535","007542","007549","007829","007929","007991","008043","008050","008071","008099","008111","005311",
														 "008651","008661","008675","008699","008734","008911","008931","008999","009223","005422","005912","005300","005310"
														 );
			    				    	
				    	for(var i=0; i<girosCashback.length; i++){
				    		if(giro==girosCashback[i]){
				    			if(divisa == "Pesos"){
						    		bandera= true;
			             			console.log("bandera: "+bandera);
						    		return bandera;
				    			}
				    		}
				    	}
					    return bandera;
				}
			    
			    function isGiroGL(numGiro){
			    	if(!numGiro==""){
    			    	/*var girosGL 		 = new Array("004722","004900","005970","005967","003351","003352","003353",
    			    			"003354","003357","003359","003360","003361","003362","003364","003366","003368","003370","003376",
    			    			"003381","003385","003386","003387","003389","003390","003391","003393","003394","003395","003396",
    			    			"003398","003400","003405","003409","003412","003414","003420","003421","003423","003425","003427",
    			    			"003428","003429","003430","003431","003432","003433","003434","003435","003436","003437","003438",
    			    			"003439","007512","007519","005813","004812","005045","005734","005947","007379","004816","005967",
    			    			"007333","007399","007011","004511","005045","005192","005399","003501","003502","003503","003504",
    			    			"003505","003506","003507","003508","003509","003510","003511","003512","003513","003514","003515",
    			    			"003516","003517","003518","003519","003520","003522","003523","003524","003525","003526","003527",
    			    			"003528","003529","003530","003533","003534","003535","003536","003537","003538","003540","003541",
    			    			"003542","003543","003544","003545","003548","003549","003550","003552","003553","003558","003562",
    			    			"003563","003565","003568","003570","003572","003573","003574","003575","003577","003579","003581",
    			    			"003583","003584","003585","003586","003587","003588","003590","003591","003592","003593","003595",
    			    			"003598","003599","003603","003612","003615","003622","003623","003625","003629","003633","003634",
    			    			"003635","003636","003637","003638","003639","003640","003641","003642","003643","003644","003645",
    			    			"003646","003647","003648","003649","003650","003651","003652","003653","003654","003655","003656",
    			    			"003657","003658","003659","003660","003661","003663","003664","003665","003666","003668","003670",
    			    			"003671","003672","003673","003674","003675","003677","003678","003680","003681","003683","003684",
    			    			"003685","003686","003687","003688","003689","003690","003691","003692","003693","003694","003695",
    			    			"003696","003697","003698","003699","003700","003702","003703","003704","003705","003706","003707",
    			    			"003709","003710","003711","003713","003714","003715","003716","003717","003718","003719","003720",
    			    			"003721","003722","003723","003724","003725","003726","003727","003728","003729","003730","003731",
    			    			"003732","003733","003734","003735","003736","003737","003738","003739","003740","003741","003742",
    			    			"003743","003744","003745","003746","003747","007011","007012","003802","003750","003000","003001",
    			    			"003002","003004","003005","003006","003007","003008","003009","003010","003011","003012","003013",
    			    			"003014","003015","003016","003017","003018","003020","003021","003022","003023","003024","003025",
    			    			"003026","003027","003028","003029","003030","003031","003032","003033","003034","003035","003036",
    			    			"003037","003038","003039","003040","003041","003042","003043","003044","003045","003046","003047",
    			    			"003048","003049","003050","003051","003052","003053","003054","003055","003056","003057","003058",
    			    			"003060","003061","003063","003065","003066","003071","003075","003076","003077","003078","003081",
    			    			"003082","003083","003084","003085","003086","003087","003088","003089","003094","003096","003099",
    			    			"003100","003102","003103","003106","003110","003111","003112","003117","003118","003125","003126",
    			    			"003127","003129","003130","003133","003135","003137","003138","003143","003144","003145","003146",
    			    			"003151","003154","003159","003161","003164","003165","003170","003171","003172","003175","003176",
    			    			"003178","003181","003182","003184","003185","003186","003187","003190","003191","003192","003193",
    			    			"003196","003197","003200","003203","003204","003212","003215","003216","003217","003218","003219",
    			    			"003220","003221","003222","003223","003228","003229","003231","003233","003234","003235","003238",
    			    			"003239","003240","003241","003242","003243","003251","003252","003253","003254","003256","003259",
    			    			"003261","003262","003263","003266","003267","003280","003282","003284","003285","003286","003287",
    			    			"003292","003293","003294","003295","003298","003299","004511","004582","004789",
    			    			//giros alto riesgo
    			    			"004812","005045","005813","005969","007995","005970"
    														 );
    			    		*/
                 		var girosGL 		 = new Array("004722","005967","003351","003352","003353",
    			    			"003354","003357","003359","003360","003361","003362","003364","003366","003368","003370","003376",
    			    			"003381","003385","003386","003387","003389","003390","003391","003393","003394","003395","003396",
    			    			"003398","003400","003405","003409","003412","003414","003420","003421","003423","003425","003427",
    			    			"003428","003429","003430","003431","003432","003433","003434","003435","003436","003437","003438",
    			    			"003439","007512","007519","005045","004816","005967",
    			    			"007333","004511","005045","005192","005399","007012","003000","003001",
    			    			"003002","003004","003005","003006","003007","003008","003009","003010","003011","003012","003013",
    			    			"003014","003015","003016","003017","003018","003020","003021","003022","003023","003024","003025",
    			    			"003026","003027","003028","003029","003030","003031","003032","003033","003034","003035","003036",
    			    			"003037","003038","003039","003040","003041","003042","003043","003044","003045","003046","003047",
    			    			"003048","003049","003050","003051","003052","003053","003054","003055","003056","003057","003058",
    			    			"003060","003061","003063","003065","003066","003071","003075","003076","003077","003078","003081",
    			    			"003082","003083","003084","003085","003086","003087","003088","003089","003094","003096","003099",
    			    			"003100","003102","003103","003106","003110","003111","003112","003117","003118","003125","003126",
    			    			"003127","003129","003130","003133","003135","003137","003138","003143","003144","003145","003146",
    			    			"003151","003154","003159","003161","003164","003165","003170","003171","003172","003175","003176",
    			    			"003178","003181","003182","003184","003185","003186","003187","003190","003191","003192","003193",
    			    			"003196","003197","003200","003203","003204","003212","003215","003216","003217","003218","003219",
    			    			"003220","003221","003222","003223","003228","003229","003231","003233","003234","003235","003238",
    			    			"003239","003240","003241","003242","003243","003251","003252","003253","003254","003256","003259",
    			    			"003261","003262","003263","003266","003267","003280","003282","003284","003285","003286","003287",
    			    			"003292","003293","003294","003295","003298","003299","004511","004582",
    			    			//giros alto riesgo
    			    			"005045","005969","007995",
    							//Galerías de Arte F-92512 Esquemas 2019 RF_008_Agrear Giro
    			    			"005971"
    			    			//Giro Hoteles Quitados F-92512 Esquemas 2019 RF_005_Quitar Giro
    			    			/*"003501","003502","003503","003504","003505","003506","003507","003508","003509","003510","003511",
    			    			"003512","003513","003514","003515",
    							"003516","003517","003518","003519","003520","003522","003523","003524","003525","003526","003527",
    							"003528","003529","003530","003533","003534","003535","003536","003537","003538","003540","003541",
    							"003542","003543","003544","003545","003548","003549","003550","003552","003553","003558","003562",
    			    			"003563","003565","003568","003570","003572","003573","003574","003575","003577","003579","003581",
    			    			"003583","003584","003585","003586","003587","003588","003590","003591","003592","003593","003595",
    			    			"003598","003599","003603","003612","003615","003622","003623","003625","003629","003633","003634",
    			    			"003635","003636","003637","003638","003639","003640","003641","003642","003643","003644","003645",
    			    			"003646","003647","003648","003649","003650","003651","003652","003653","003654","003655","003656",
    			    			"003657","003658","003659","003660","003661","003663","003664","003665","003666","003668","003670",
    			    			"003671","003672","003673","003674","003675","003677","003678","003680","003681","003683","003684",
    			    			"003685","003686","003687","003688","003689","003690","003691","003692","003693","003694","003695",
    			    			"003696","003697","003698","003699","003700","003702","003703","003704","003705","003706","003707",
    			    			"003709","003710","003711","003713","003714","003715","003716","003717","003718","003719","003720",
    			    			"003721","003722","003723","003724","003725","003726","003727","003728","003729","003730","003731",
    			    			"003732","003733","003734","003735","003736","003737","003738","003739","003740","003741","003742",
    			    			"003743","003744","003745","003746","003747","007011","003802","003750",
    			    			"004900",//GOBERNAMENTAL
    							"007399",//Servicios Gubernamentales No Clasificados
    							"005947",//Tiendas de Regalos Artesanías
    							"005970",//Artesanías 
    							"004812",//venta de Equipos y Accesorios de telefonía celular
    							"005734",//venta y servicio de equipo de cómputo
    							"007379",//venta y servicio de equipo de cómputo
    							"005813",//bares y cantinas, centros nocturnos
    							"004789",//servicio de transporte – no clasificado
    							*/
    							);
    				    for(var i=0; i<girosGL.length; i++){
    				    		if(numGiro==girosGL[i]){			    			
    					    		//console.log("bandera: "+bandera);
    				    			return "true";
    				    		}
    				    	}
    	            	}
         			
    					return "false";
    	            	
				}
			    
			    function validarEnNombreDeUnTerceroSi(){
			    	var enNombreDeUnTerceroSi=document.getElementById("forma:enNombreDeUnTerceroSi");
	      			var enNombreDeUnTerceroNo=document.getElementById("forma:enNombreDeUnTerceroNo");
	      			var enNombreDeUnTerceroEspecificacion=document.getElementById("forma:enNombreDeUnTerceroEspecificacion");
	      			
	      			if(enNombreDeUnTerceroSi.checked==true){
	      				enNombreDeUnTerceroNo.checked=false;
	      				enNombreDeUnTerceroEspecificacion.disabled=false;
	      			}else{
	      				enNombreDeUnTerceroSi.checked=false;
	      				enNombreDeUnTerceroEspecificacion.value="";
	      				enNombreDeUnTerceroEspecificacion.disabled=true;
	      			}
	              }
			    
			    function validarEnNombreDeUnTerceroNo(){
			    	var enNombreDeUnTerceroSi=document.getElementById("forma:enNombreDeUnTerceroSi");
	      			var enNombreDeUnTerceroNo=document.getElementById("forma:enNombreDeUnTerceroNo");
	      			var enNombreDeUnTerceroEspecificacion=document.getElementById("forma:enNombreDeUnTerceroEspecificacion");
	      			
	      			if(enNombreDeUnTerceroNo.checked==true){
		      				enNombreDeUnTerceroSi.checked=false;
		      				enNombreDeUnTerceroEspecificacion.value="";
		      				enNombreDeUnTerceroEspecificacion.disabled=true;
		      		}
	              }
			    
			    function validarEnNombreDeUnTerceroSi(){
			    	var enNombreDeUnTerceroSi=document.getElementById("forma:enNombreDeUnTerceroSi");
	      			var enNombreDeUnTerceroNo=document.getElementById("forma:enNombreDeUnTerceroNo");
	      			var enNombreDeUnTerceroEspecificacion=document.getElementById("forma:enNombreDeUnTerceroEspecificacion");
	      			
	      			if(enNombreDeUnTerceroSi.checked==true){
	      				enNombreDeUnTerceroNo.checked=false;
	      				enNombreDeUnTerceroEspecificacion.disabled=false;
	      			}else{
	      				enNombreDeUnTerceroSi.checked=false;
	      				enNombreDeUnTerceroEspecificacion.value="";
	      				enNombreDeUnTerceroEspecificacion.disabled=true;
	      			}
	              }
			    
			    function validarEnNombreDeUnTerceroNo(){
			    	var enNombreDeUnTerceroSi=document.getElementById("forma:enNombreDeUnTerceroSi");
	      			var enNombreDeUnTerceroNo=document.getElementById("forma:enNombreDeUnTerceroNo");
	      			var enNombreDeUnTerceroEspecificacion=document.getElementById("forma:enNombreDeUnTerceroEspecificacion");
	      			
	      			if(enNombreDeUnTerceroNo.checked==true){
		      				enNombreDeUnTerceroSi.checked=false;
		      				enNombreDeUnTerceroEspecificacion.value="";
		      				enNombreDeUnTerceroEspecificacion.disabled=true;
		      		}
	              }
	              
			    function validarTerritorioNacionalSi(){
			    	var territorioNacionalSi=document.getElementById("forma:territorioNacionalSi");
	      			var territorioNacionalNo=document.getElementById("forma:territorioNacionalNo");
	      			var territorioNacionalEspecificacion=document.getElementById("forma:territorioNacionalEspecificacion");
	      			if(territorioNacionalSi.checked==true){
	      				territorioNacionalNo.checked=false;
	      				territorioNacionalEspecificacion.value="";
		      			territorioNacionalEspecificacion.disabled=true;
	      			}
	              }
			    
			    function validarTerritorioNacionalNo(){
			    	var territorioNacionalSi=document.getElementById("forma:territorioNacionalSi");
	      			var territorioNacionalNo=document.getElementById("forma:territorioNacionalNo");
	      			var territorioNacionalEspecificacion=document.getElementById("forma:territorioNacionalEspecificacion");
	      			if(territorioNacionalNo.checked==true){
	      				territorioNacionalSi.checked=false;
	      				territorioNacionalEspecificacion.disabled=false;		      			
		      		}else{
		      			territorioNacionalNo.checked=false;
	      				territorioNacionalEspecificacion.value="";
		      			territorioNacionalEspecificacion.disabled=true;
		      		}
	              }
         		
			    function validarRiesgoReputacionalOperacionalSi(){
			    	var riesgoReputacionalOperacionalSi=document.getElementById("forma:riesgoReputacionalOperacionalSi");
	      			var riesgoReputacionalOperacionalNo=document.getElementById("forma:riesgoReputacionalOperacionalNo");
	      			if(riesgoReputacionalOperacionalSi.checked==true){
	      				riesgoReputacionalOperacionalNo.checked=false;		      			
		      		}
	            }
			    function validarRiesgoReputacionalOperacionalNo(){
			    	var riesgoReputacionalOperacionalSi=document.getElementById("forma:riesgoReputacionalOperacionalSi");
	      			var riesgoReputacionalOperacionalNo=document.getElementById("forma:riesgoReputacionalOperacionalNo");
	      			if(riesgoReputacionalOperacionalNo.checked==true){
	      				riesgoReputacionalOperacionalSi.checked=false;		      			
		      		}
	            }
			    
			    function validarVisitaOcularRecienteSi(){
			    	var visitaOcularRecienteSi=document.getElementById("forma:visitaOcularRecienteSi");
	      			var visitaOcularRecienteNo=document.getElementById("forma:visitaOcularRecienteNo");
	      			if(visitaOcularRecienteSi.checked==true){
	      				visitaOcularRecienteNo.checked=false;		      			
		      		}
	            }
			    function validarVisitaOcularRecienteNo(){
			    	var visitaOcularRecienteSi=document.getElementById("forma:visitaOcularRecienteSi");
	      			var visitaOcularRecienteNo=document.getElementById("forma:visitaOcularRecienteNo");
	      			if(visitaOcularRecienteNo.checked==true){
	      				visitaOcularRecienteSi.checked=false;		      			
		      		}
	            }
			    
			    function validarSolvenciaEconimicaSi(){
			    	var solvenciaEconimicaSi=document.getElementById("forma:solvenciaEconimicaSi");
	      			var solvenciaEconimicaNo=document.getElementById("forma:solvenciaEconimicaNo");
	      			if(solvenciaEconimicaSi.checked==true){
	      				solvenciaEconimicaNo.checked=false;		      			
		      		}
	            }
			    function validarSolvenciaEconimicaNo(){
			    	var solvenciaEconimicaSi=document.getElementById("forma:solvenciaEconimicaSi");
	      			var solvenciaEconimicaNo=document.getElementById("forma:solvenciaEconimicaNo");
	      			if(solvenciaEconimicaNo.checked==true){
	      				solvenciaEconimicaSi.checked=false;		      			
		      		}
	            }
			    
        		//F-98896 Mejora Garantia Liquida: activa campo no. de grupo
			    function inicializarGroupNo(){
			    	var valorVentaMensual= document.getElementById('forma:aff_ventasEstimadasMensuales');
			    	var antiguedad = document.getElementById("forma:antiguedad");
			    	var groupNo = document.getElementById("forma:groupNo");
			    	var rentBy = document.getElementById("forma:rentBy").value;
			    	var isMostrarMsjNoGrupo = document.getElementById("forma:isMostrarMsjNoGrupo");			    	
	            	if(valorVentaMensual.value=="" || valorVentaMensual.value==null){
	            		
	            	}else{
	            		if(valorVentaMensual.value>=100000 && antiguedad.value==-6){
	            			if(groupNo.value == "" || groupNo.value == null || groupNo.value == "undefined"){
	            				if(isMostrarMsjNoGrupo.value=="true"){
	            					alert("Si el comercio pertenece a un grupo, ingrese su número");
	            					isMostrarMsjNoGrupo.value="false";
	            				}
	            			}else{
	            			}
	            			groupNo.disabled = false;
		               	}else{
		               		if(rentBy == "Grupo"){
		                  		 document.getElementById("forma:groupNo").disabled = false;
		                  	 }else{
		                  		groupNo.disabled = true;
			               		groupNo.value = "";
			               		isMostrarMsjNoGrupo.value="true";
		                  	 }
		               		
		               	 }
	            	}

         		}
			    
			    function onchangeGroupNo(){
	            		 inicializarGarantiaLiquida();
	             }
	      /**
	       * F-82716 - Mujer PYME Banorte
           * Funcion para validar numero de cliente con lista dejada por altamira
           * -solo TPV y MPOS
           * -solo con plan 15, 35, 75 y 200
          */	    
	    function inicializarMujerPyME(){
	    	var hdnBtn=document.getElementById("forma:hdnBtn");								// boton para llamar al metodo de la clase
	        var checkboxMujerPyME	  			= document.getElementById("forma:mujerPyME"); 		// checkbox Mujer PyME
	        var mujerPyMESeleccion	  			= document.getElementById("forma:mujerPyME").checked;	//Seleccion del checkbox
	        var producto 			  			= document.getElementById("forma:affiliation_productdesc").value;
	        var plan			  	  			= document.getElementById("forma:affiliation_comercialplan").value;
	        var numCliente		  	  			= document.getElementById("forma:client_sic").value;
	        var checkboxCaptacion	  			= document.getElementById("forma:impulsoCaptacion");
	        var esCuentaMujerPyME  	  			= document.getElementById("forma:esCuentaMujerPyME").value;
	        var cuentaMujerPyMEValidada 		= document.getElementById("forma:cuentaMujerPyMEValidada").value;
	        var noEsCuentaMujerPyMEValidada 	= document.getElementById("forma:noEsCuentaMujerPyMEValidada").value;
	        var isMsgMujerPyME 					= document.getElementById("forma:isMsgMujerPyME");
	        var desactivarMujerPyME 			= document.getElementById("forma:desactivarMujerPyME").value;
	        var listaCuentaMujerPyMEVacia 		= document.getElementById("forma:listaCuentaMujerPyMEVacia").value;
	        var accountnumbermn 	= document.getElementById("forma:affiliation_accountnumbermn").value;
	        var accountnumberdlls 	= document.getElementById("forma:affiliation_accountnumberdlls").value;
	        //version 2 valida cliente y cuentas
	        var esClienteMujerPyME  	  	= document.getElementById("forma:esClienteMujerPyME").value;
	        var clienteMujerPyMEValidado 		= document.getElementById("forma:clienteMujerPyMEValidado").value;
	        var noEsClienteMujerPyMEValidado 	= document.getElementById("forma:noEsClienteMujerPyMEValidado").value;
	        
	        var esCuentaMujerPyMEDlls  	  		= document.getElementById("forma:esCuentaMujerPyMEDlls").value;
	        var cuentaMujerPyMEValidadaDlls		= document.getElementById("forma:cuentaMujerPyMEValidadaDlls").value;
	        var noEsCuentaMujerPyMEValidadaDlls	= document.getElementById("forma:noEsCuentaMujerPyMEValidadaDlls").value;

	        if(numCliente != "" && numCliente != null){
	        	//if((affiliation_accountnumbermn != "" && affiliation_accountnumbermn!= null)||(affiliation_accountnumberdlls != "" && affiliation_accountnumberdlls != null)){
	        	if((accountnumbermn != "" && accountnumbermn!= null)){
	        		if(producto == "Terminal punto de venta" ||producto == "Terminal Personal Banorte (Mpos)"){
	        			if("16" == plan || "17" == plan || "18" == plan || "19" == plan) {//SI ES UN PLAN 15, 35, 75, 200
	        				if(listaCuentaMujerPyMEVacia=="false"){
	        					if(esClienteMujerPyME=="" && clienteMujerPyMEValidado==""){//SI ES LA PRIMERA VEZ QUE SE VALIDA
	        						hdnBtn.click();
	            				}else{
            						if(clienteMujerPyMEValidado==numCliente){//ESTO FUNCIONA PARA CUANDO EDITAN EL FOLIO Y LA INFO VIENE DE BD
            							if((cuentaMujerPyMEValidada==accountnumbermn)){//la cuenta es la misma que se valido
            								if(desactivarMujerPyME == "true"){// SI EL CHECK ESTA DESATIVADO
	            								checkboxMujerPyME.disabled = false;
			                                	checkboxMujerPyME.checked = false;
	            							}else{
	            								if(esClienteMujerPyME=="true"){//revisa si el cliente es MujerPyME
	            									if(esCuentaMujerPyME=="true"){//revisa si el cliente tiene la cuenta escrita
	            										checkboxMujerPyME.disabled = false;
					    		                    	checkboxMujerPyME.checked=true;
					    		                    	checkboxCaptacion.checked=true;	
	            									}else{
	            										checkboxMujerPyME.disabled = true;
					                                	checkboxMujerPyME.checked = false;
	            									}
		            									checkboxMujerPyME.disabled = false;
					    		                    	checkboxMujerPyME.checked=true;
					    		                    	checkboxCaptacion.checked=true;			            								    		                    	
		            							}else{
		            								checkboxMujerPyME.disabled = true;
				                                	checkboxMujerPyME.checked = false;
		            							}
	            							}    
            							}else{
            								if(noEsCuentaMujerPyMEValidada==accountnumbermn){// SI NO TIENE un valor es 
		            							checkboxMujerPyME.disabled = true;
			    		                    	checkboxMujerPyME.checked=false;
	            							}else{    
	            								checkboxCaptacion.checked=false; 
	            								isMsgMujerPyME.value="true";
	        									hdnBtn.click();	            								
	            							}
            							}
            							        							
            						}else{
            							if(noEsClienteMujerPyMEValidado==numCliente){// SI NO TIENE un valor es 
	            							checkboxMujerPyME.disabled = true;
		    		                    	checkboxMujerPyME.checked=false;
            							}else{    
            								checkboxCaptacion.checked=false; 
            								isMsgMujerPyME.value="true";
        									hdnBtn.click();	            								
            							}							
            						}
	            				}
	        				}else{
	        					//si no hay lista de cuentas no se quita seleccion Mujer Pyme por folios viejos que tenga mujer pyme respete la seleccion
	        					if(mujerPyMESeleccion==true){
	        						
	        					}else{
	        						checkboxMujerPyME.disabled = true;
	                        		checkboxMujerPyME.checked = false;
	        					}        					
	        				}
	        			}else{
	        				checkboxMujerPyME.disabled = true;
	                		checkboxMujerPyME.checked = false;
	        			}
	    			}else{//si no cumple con las condiciones de producto
	    				checkboxMujerPyME.disabled = true;
	                	checkboxMujerPyME.checked = false;                		
	            	}
	        	}else{
            		//si no tiene cuenta de cliente
            		checkboxMujerPyME.disabled = true;
            		checkboxMujerPyME.checked = false;	
            	}
    		}else{//si no tiene numero de cliente
    			checkboxMujerPyME.disabled = true;
        		checkboxMujerPyME.checked = false;	
    		}	            		            		    	
        }
	     
			    
		function onchangeNoCliente(){
			inicializarMujerPyME();
		}
			    
			    
		function onchangemujerPyME(){ 	           	
			var mujerPyMESeleccion	  = document.getElementById("forma:mujerPyME").checked;//para que esta chequeado MujerPyME Banorte
            var desactivarMujerPyME = document.getElementById("forma:desactivarMujerPyME");
            var checkboxMujerPyME	  = document.getElementById("forma:mujerPyME"); // checkbox Mujer PyME
            var checkboxCaptacion	  = document.getElementById("forma:impulsoCaptacion");
            if(mujerPyMESeleccion == true){
            	 desactivarMujerPyME.value="false";
            	 checkboxCaptacion.checked=true;
           	 }else{
           		 desactivarMujerPyME.value="true";
           		 checkboxCaptacion.checked=false;
           	 }
           	 inicializarMujerPyME();
        }
			  
		function onchangeAffiliation_accountnumbermn(){
			inicializarMujerPyME();
		}
		function onchangeAffiliation_accountnumberdlls(){
			//inicializarMujerPyME();
		}
		
		function validarDescripcionGiro(){
			var descGiro = document.getElementById("forma:client_categorycode");//giro
			if(!descGiro.value==""){
				//if("false"==validarGiro('../img_contracts/inc/cotype.xml', descGiro.value)){ // produccion
				if("false"==validarGiro('inc/cotype.xml', descGiro.value)){//local
	       		 	alert("Giro mal capturado, favor de seleccionar un giro de la lista precargada.");
	       		 	descGiro.focus();
		       		return "false"; 
		       	 }
				return "true";
			}
		}
		