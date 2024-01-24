/**
 * 
 */

//Variables generales


/* Validacion del formulario*/
function validateCDForm(){
	var isValid = true;
	
	var affiliationThrough 			= document.getElementById('forma:affiliationThrough');
	var formatLayout 				= document.getElementById('forma:formatLayout');
	var internalcredithistory 		= document.getElementById('forma:internalcredithistory');
	var externalcredithistory 		= document.getElementById('forma:externalcredithistory');
	var selectFillName 				= document.getElementById("forma:affiliation_havedepositcompany");
    var fillOptionsSelect 			= selectFillName.selectedIndex;
    var officerEbankingEspNumber 	= document.getElementById('forma:officerebankingEspEmpnumber');
	var officerEbankingEspName 		= document.getElementById('forma:officerebankingEspName');
	var officerEbankingEspLastName	= document.getElementById('forma:officerebankingEspLastname');
	var officerEbankingTnumber 		= document.getElementById('forma:officerebankingTempnumber');
	var officerEbankingTname 		= document.getElementById('forma:officerebankingTname');
	var onlineBanorteOE 			= document.getElementById("forma:onLineBanorteOE") ;
	var onlineBanorteONE			= document.getElementById("forma:onLineBanorteONE") ;
	var branchDirectorNumber        = document.getElementById("forma:branchDirectorNumber") ;
	var branchDirectorName          = document.getElementById("forma:branchDirectorname") ;
	
	//F-113829 Actualización Formatos Cobranza / abril 2022
	var creditoHistoricoRetrasoSi=document.getElementById("forma:creditoHistoricoRetrasoSi");
	var creditoHistoricoRetrasoNo=document.getElementById("forma:creditoHistoricoRetrasoNo");
	var creditoActivoRetrasoSi=document.getElementById("forma:creditoActivoRetrasoSi");
	var creditoActivoRetrasoNo=document.getElementById("forma:creditoActivoRetrasoNo");
	var nombreDelTerritorioDirector=document.getElementById("forma:nombreDelTerritorioDirector");
	
	var autorizaExentarFianza = document.getElementById("forma:affiliation_officerdepositexent");
	var comboFianza = document.getElementById("forma:affiliation_havedepositcompany");
	
	if( selectFillName.options[fillOptionsSelect].value == "S" ) {
		document.getElementById("forma:affiliation_depositamount").disabled = false;
	}
	

	if (formatLayout.selectedIndex == 0){
		alert('Seleccione un Formato de Layout');
		formatLayout.focus();
		isValid = false;
	}
	else if (internalcredithistory.selectedIndex == 0){
		alert('Seleccione Buro Interno');
		internalcredithistory.focus();
		isValid = false;
	}
	else if (externalcredithistory.selectedIndex == 0){
		alert('Seleccione Buro Nacional');
		externalcredithistory.focus();
		isValid = false;
	}else if(creditoActivoRetrasoSi.checked==false && creditoActivoRetrasoNo.checked==false){
		  alert("Favor de seleccionar si algún crédito activo presenta retraso");
		  creditoActivoRetrasoSi.focus();
		  isValid = false;
	}else if(creditoHistoricoRetrasoSi.checked==false && creditoHistoricoRetrasoNo.checked==false){
		  alert("Favor de seleccionar si algún crédito historico presenta retraso");
		  creditoHistoricoRetrasoSi.focus();
		  isValid = false;
	}else if(nombreDelTerritorioDirector.value=="" || nombreDelTerritorioDirector.value==null){
	  	  alert("Favor capturar nombre del territorio del Director");
	  	nombreDelTerritorioDirector.focus();
	  	isValid = false;
	  }
	
	if( selectFillName.options[fillOptionsSelect].value == "N" ) {
		if(!isNivelBajo())
		  {
				if (officerEbankingEspNumber.value == ""){
					alert('Proporcione la Informacion de un Ejecutivo Ebanking de Banca Especializada');
					officerEbankingEspNumber.focus();
					isValid = false;
				}else if(officerEbankingEspName.value==""){
					alert('Proporcione la Informacion de un Ejecutivo Ebanking de Banca Especializada');
					officerEbankingEspNumber.focus();
					isValid = false;
				}
				
				if (officerEbankingTnumber.value == ""){
					alert('Proporcione la Informacion de un Director Territorial');
					officerEbankingTnumber.focus();
					isValid = false;
				}else if(officerEbankingTname.value==""){
					alert('Proporcione la Informacion de un Director Territorial');
					officerEbankingTnumber.focus();
					isValid = false;
				}
				
				//datos director de sucursal
				if (branchDirectorNumber.value == ""){
					alert('Proporcione la Informacion de un Director de Sucursal');
					branchDirectorNumber.focus();
					isValid = false;
				}else if(branchDirectorName.value==""){
					alert('Proporcione la Informacion de un Director de Sucursal');
					branchDirectorName.focus();
					isValid = false;
				}
		  }
	}
	
	if(!isValid){
		if( selectFillName.options[fillOptionsSelect].value == "S" ) {
			document.getElementById("forma:affiliation_depositamount").disabled = true;
		}
		
	}
//	var interno = internalcredithistory.selectedIndex;
	//var externo = externalcredithistory.selectedIndex;
	//var valorExterno = externalcredithistory.value;
	//alert ('buro interno-'+interno+'externo-'+externo+'isvalid='+isValid+'value '+valorExterno+'nombre: '+officerEbankingEspName.value);
	
	//Para validar que haya escrito quien exenta la fianza en caso de seleccionar NO
	if(comboFianza.value=="N"){
		//if(autorizaExentarFianza.value=="" || autorizaExentarFianza.value==null){
			//isValid=false;
			
		//}
		alert('La Excepcion de Fianza debe firmarse por el Director Territorial');
	}
	
	var redSeleccionada=validarRed();
	if (!redSeleccionada){
		isValid=false;
	}
	
	isNivelBajo();
	
	return isValid;
}

function validarRed(){
	var red=valorRed();
	var seccionRed=document.getElementById('forma:red');
	if(red==null){
		alert('Por favor seleccione Red: Banorte o Ixe');
		seccionRed[0].focus();
		return false;
	}
	return true;
}

//function validateIssuerCategory(){
//	var issuerCategory=document.getElementById("forma:giroEmisora");
//	if(issuerCategory.value==""||issuerCategory.value==null){
//		alert('Por favor seleccione el giro de la emisora');
//		issuerCategory.focus();
//		return false;
//	}else{
//		return true;
//	}
//}


/* Modificado para que siempre este desactivado*/
function validationPaywork(){
	document.getElementById("forma:autoRegister").disabled = true ;
				  /*if(document.getElementById("forma:payworksClabe").checked){
					  if(document.getElementById("forma:autoRegister").disabled){
						  document.getElementById("forma:autoRegister").disabled = true ;
					  }
				  }else {
					  document.getElementById("forma:autoRegister").disabled = true ;
				  }*/
			  }
				  
				  
              function payworkSelect() {
            	  if(document.getElementById("forma:payworksClabe").checked){
            		  document.getElementById("forma:autoRegister").checked = true;
            	  }
            	  else{
            		  document.getElementById("forma:autoRegister").checked = false;
            	  }
            	  onchangePayworkSelect();
              }
              
              function onchangePayworkSelect(){
            	  var tablaDatosPayworks=document.getElementById("forma:pwData");
            	  var checkPayworks=document.getElementById("forma:payworksClabe");
            	  if(!checkPayworks.checked){
            		  tablaDatosPayworks.style.display="none";
            	  }else{
            		  tablaDatosPayworks.style.display="";
            	  }
              }
              
         
				
              function affiliationSelect(){
            	  if(document.getElementById("forma:affiliationThrough").value == "Emisor"){
            		 document.getElementById("forma:referenceName").disabled = true ;
            		 document.getElementById("forma:referenceLength").disabled = true ;
            		 document.getElementById("forma:referenceType").disabled = true ;
            		 document.getElementById("forma:referenceRequired").disabled = true ;
            		 document.getElementById("forma:referenceDV").disabled = true ;
            		 document.getElementById("forma:referenceModuleType").disabled = true ;
            		 
            		 document.getElementById("forma:referenceName").value= "" ;
            		 document.getElementById("forma:referenceLength").value= "" ;
            		 document.getElementById("forma:referenceType").value= "" ;
            		 document.getElementById("forma:referenceRequired").value= "" ;
            		 document.getElementById("forma:referenceDV").value= "" ;
            		 document.getElementById("forma:referenceModuleType").value= "" ;
            		 
            		 document.getElementById("forma:referenceName").style.backgroundColor= "#E3E3E3";
            		 document.getElementById("forma:referenceLength").style.backgroundColor= "#E3E3E3";
            		 document.getElementById("forma:referenceType").style.backgroundColor= "#E3E3E3";
            		 document.getElementById("forma:referenceRequired").style.backgroundColor= "#E3E3E3";
            		 document.getElementById("forma:referenceDV").style.backgroundColor= "#E3E3E3";
            		 document.getElementById("forma:referenceModuleType").style.backgroundColor= "#E3E3E3";
            	  }
            	  else {
            		 document.getElementById("forma:referenceName").disabled = false ;
             		 document.getElementById("forma:referenceLength").disabled = false ;
             		 document.getElementById("forma:referenceType").disabled = false ;
             		 document.getElementById("forma:referenceRequired").disabled = false ;
             		 document.getElementById("forma:referenceDV").disabled = false ;
             		 document.getElementById("forma:referenceModuleType").disabled = false ;
             		 
             		document.getElementById("forma:referenceName").style.backgroundColor= "#fff";
           		 document.getElementById("forma:referenceLength").style.backgroundColor= "#fff";
           		 document.getElementById("forma:referenceType").style.backgroundColor= "#fff";
           		 document.getElementById("forma:referenceRequired").style.backgroundColor= "#fff";
           		 document.getElementById("forma:referenceDV").style.backgroundColor= "#fff";
           		 document.getElementById("forma:referenceModuleType").style.backgroundColor= "#fff";
            	  }
              }
              
              function fianzaCD(){
            	  var selectFillName 		= document.getElementById("forma:affiliation_havedepositcompany");
            	  var fillOptionsSelect 	= selectFillName.selectedIndex;
            	  var valorSelected 		= selectFillName.options[fillOptionsSelect].value;
            	  if( isNivelBajo() ){
            		  valorSelected = "N";
            	  }
            	  
            	  var destinationFill1 = document.getElementById("forma:affiliation_depositcompany");
        		  var destinationFill2 = document.getElementById("forma:affiliation_depositamount");
        		  var destinationFill3 = document.getElementById("forma:affiliation_duedate");
        		  //var destinationFill4 = document.getElementById("forma:affiliation_officerdepositexent");
        		  
            	  if( valorSelected == "S" ) {
            		  destinationFill1.disabled = false;
            		  destinationFill2.disabled = false;
            		  destinationFill3.disabled = false;
            		  //destinationFill4.disabled = true;
            		  //destinationFill4.value = "";
            		  calculateDepositAmount();
            	  }else{
            		  destinationFill1.disabled = true;
            		  destinationFill1.value = "";
            		  destinationFill2.disabled = true;
            		  destinationFill2.value = "";
            		  destinationFill3.disabled = true;
            		  destinationFill3.value = "";
            		  //destinationFill4.disabled = false;
            		  if(document.getElementById("forma:isChangeFianza").value == "true")
            			  alert('La Excepcion de Fianza debe firmarse por el Director Territorial');
            	  }
              }

 
              function fianzaCD_Old() {
            	    var pre = "forma:";
            	    var selectFillName 		= document.getElementById(pre + "affiliation_havedepositcompany");
            	    var fillOptionsSelect 	= selectFillName.selectedIndex;
            	    var destinationFill1 	= document.getElementById(pre + "affiliation_depositcompany");
            	    var destinationFill2 	= document.getElementById(pre + "affiliation_depositamount");
            	    var destinationFill3 	= document.getElementById(pre + "affiliation_duedate");
            	    //var destinationFill4 	= document.getElementById(pre + "monthlyAmount");
            	    var officerdepositexent	= document.getElementById(pre + "affiliation_officerdepositexent");
            	    var divEspecializada 	= document.getElementById("bancaEsp");
            	    var divTerritorial 		= document.getElementById("bancaTerritorial");
            	    var valorSelected		= selectFillName.options[fillOptionsSelect].value;
            	    
            	    if( isNivelBajo() ){
            	    	valorSelected = "S";
            	    }
            	    
            	   
            	    if( valorSelected == "S" ) {
            	        destinationFill1.disabled = false;
            	        destinationFill3.disabled = false;
            	        //destinationFill4.disabled = false;
            	        destinationFill2.disabled = false;
            	        destinationFill1.style.backgroundColor= "#fff";
            	        destinationFill3.style.backgroundColor= "#fff";
            	        //destinationFill4.style.backgroundColor= "#fff";
            	        destinationFill2.style.backgroundColor= "#fff";
            	        //divEspecializada.style.display="none";
            	        //divTerritorial.style.display="none";  
//            	        document.getElementById("forma:officerebankingEspEmpnumber").disabled = true;
//            	        document.getElementById("forma:officerebankingEspName").disabled = true; 
//            	        document.getElementById("forma:officerebankingEspLastname").disabled = true ;
//            	        document.getElementById("forma:officerebankingEspMothersln").disabled = true; 
//            	        document.getElementById("forma:officerebankingEspPosition").disabled = true; 
//            	        document.getElementById("forma:officerebankingTempnumber").disabled = true; 
//            	        document.getElementById("forma:officerebankingTname").disabled = true ;
//            	        document.getElementById("forma:officerebankingTlastname").disabled = true; 
//            	        document.getElementById("forma:officerebankingTmothersln").disabled = true;
//            	        document.getElementById("forma:officerebankingTposition").disabled = true;
            	        //officerdepositexent.disabled = true;
            	        
//            	        document.getElementById("forma:officerebankingEspEmpnumber").value = ""; 
//            	        document.getElementById("forma:officerebankingEspName").value = ""; 
//            	        document.getElementById("forma:officerebankingEspLastname").value = ""; 
//            	        document.getElementById("forma:officerebankingEspMothersln").value = ""; 
//            	        document.getElementById("forma:officerebankingEspPosition").value = "";
//            	        document.getElementById("forma:officerebankingTempnumber").value = ""; 
//            	        document.getElementById("forma:officerebankingTname").value = ""; 
//            	        document.getElementById("forma:officerebankingTlastname").value = ""; 
//            	        document.getElementById("forma:officerebankingTmothersln").value = "";
//            	        document.getElementById("forma:officerebankingTposition").value = "";
            	        //officerdepositexent.value = "";
            	        
//            	        document.getElementById("forma:officerebankingEspEmpnumber").style.backgroundColor= "#E3E3E3"; 
//            	        document.getElementById("forma:officerebankingEspName").style.backgroundColor= "#E3E3E3"; 
//            	        document.getElementById("forma:officerebankingEspLastname").style.backgroundColor= "#E3E3E3"; 
//            	        document.getElementById("forma:officerebankingEspMothersln").style.backgroundColor= "#E3E3E3";
//            	        document.getElementById("forma:officerebankingEspPosition").style.backgroundColor= "#E3E3E3";
//            	        document.getElementById("forma:officerebankingTempnumber").style.backgroundColor= "#E3E3E3"; 
//            	        document.getElementById("forma:officerebankingTname").style.backgroundColor= "#E3E3E3";
//            	        document.getElementById("forma:officerebankingTlastname").style.backgroundColor= "#E3E3E3";
//            	        document.getElementById("forma:officerebankingTmothersln").style.backgroundColor= "#E3E3E3";
//            	        document.getElementById("forma:officerebankingTposition").style.backgroundColor= "#E3E3E3";
            	        //officerdepositexent.style.backgroundColor= "#E3E3E3";
            	        
            	    } else {
            	        destinationFill1.disabled = true;
            	        destinationFill3.disabled = true;
            	        //destinationFill4.disabled = true;
            	        destinationFill2.disabled = true;
            	        destinationFill1.value = "";
            	        destinationFill3.value = "";
            	        //destinationFill4.value = "";
            	        destinationFill2.value = "";
            	        destinationFill1.style.backgroundColor= "#E3E3E3";
            	        destinationFill3.style.backgroundColor= "#E3E3E3";
            	        //destinationFill4.style.backgroundColor= "#E3E3E3";
            	        destinationFill2.style.backgroundColor= "#E3E3E3";
            	        
            	        //divEspecializada.style.display="";
            	        //divTerritorial.style.display="";
//            	        if( document.getElementById("forma:officerebankingEspEmpnumber").value==""){
//	            	        document.getElementById("forma:officerebankingEspEmpnumber").disabled = false; 
//	            	        document.getElementById("forma:officerebankingEspName").disabled = false ;
//	            	        document.getElementById("forma:officerebankingEspLastname").disabled = false;
//	            	        document.getElementById("forma:officerebankingEspMothersln").disabled = false;
//	            	        document.getElementById("forma:officerebankingEspPosition").disabled = false
//	            	        
//	            	        document.getElementById("forma:officerebankingEspEmpnumber").style.backgroundColor= "#fff"; 
//	            	        document.getElementById("forma:officerebankingEspName").style.backgroundColor= "#fff"; 
//	            	        document.getElementById("forma:officerebankingEspLastname").style.backgroundColor= "#fff"; 
//	            	        document.getElementById("forma:officerebankingEspMothersln").style.backgroundColor= "#fff";
//	            	        document.getElementById("forma:officerebankingEspPosition").style.backgroundColor= "#fff";
//	            	        }
//            	        if( document.getElementById("forma:officerebankingTempnumber").value==""){
//	            	        document.getElementById("forma:officerebankingTempnumber").disabled = false; 
//	            	        document.getElementById("forma:officerebankingTname").disabled = false ;
//	            	        document.getElementById("forma:officerebankingTlastname").disabled = false; 
//	            	        document.getElementById("forma:officerebankingTmothersln").disabled = false;
//	            	        document.getElementById("forma:officerebankingTposition").disabled = false
//	            	        
//	            	        document.getElementById("forma:officerebankingTempnumber").style.backgroundColor= "#fff"; 
//	            	        document.getElementById("forma:officerebankingTname").style.backgroundColor= "#fff";
//	            	        document.getElementById("forma:officerebankingTlastname").style.backgroundColor= "#fff";
//	            	        document.getElementById("forma:officerebankingTmothersln").style.backgroundColor= "#fff";
//	            	        document.getElementById("forma:officerebankingTposition").style.backgroundColor= "#fff";
//            	        }
            	        
	            	    //officerdepositexent.disabled = false;
            	        //officerdepositexent.style.backgroundColor= "#fff";
            	        
            	    }
            	    return false;
            	}
              
              function validacionBuroInternal() {
            	  var divSend 	= document.getElementById("send");
            	  if( document.getElementById("forma:internalcredithistory").value=="Negro" ||
            			  document.getElementById("forma:internalcredithistory").value=="Rojo"){
            		  alert("No se Puede Realizar el Contrato, debido al Tipo de Buro Interno");
            		  divSend.style.display="none";
            		  statusCampos(true);
            	  }else{
            		  if(document.getElementById("forma:externalcredithistory").value!="Malo"){
            			  divSend.style.display="";
            			  statusCampos(false);
            		  }
            	  }
            	  affiliationSelect();
            	  var buroExterno = document.getElementById("forma:externalcredithistory");
            	  validarOpcionesBuroNacional(buroExterno.selectedIndex);
            	  setDepositAmount();
              }
              
              
              //nueva funcion de buro
              function validarOpcionesBuroNacional(selIndex){
            	  var buroExterno = document.getElementById("forma:externalcredithistory");
            	  var buroInterno = document.getElementById("forma:internalcredithistory");
            	  var valorActual = buroExterno.options[selIndex].value;
            	  var elementos = ["Seleccione una Opcion", "Sin registro","Bueno", "Regular A", "Malo"];
            	  var elementosSinColor = ["Seleccione una Opcion","Sin registro bueno"];
            	  var elementosNivel1 = ["Seleccione una Opcion", "Sin registro","Bueno", "Regular A"];
            	  var elementosAnaranjado = ["Seleccione una Opcion","Regular B"];
            	  var elementosRojoYNegro = ["Seleccione una Opcion","Malo"];
            	  
            	  buroExterno.length=0;
            	  
            	  if(buroInterno.value=="Sin Color"){
            		  for(var i =0; i<elementosSinColor.length; i++){
                		  var option = document.createElement('option');
                		  buroExterno.options.add(option,i);
                		  buroExterno.options[i].value=elementosSinColor[i];
                		  buroExterno.options[i].innerText=elementosSinColor[i];
                		  buroExterno.options[i].innerHTML=elementosSinColor[i];
                	  }
            	  }else if(buroInterno.value=="Sin Registro"
            		  		|| buroInterno.value=="Beige"
            		  		|| buroInterno.value=="Amarillo"){
            		  for(var i =0; i<elementosNivel1.length; i++){
                		  var option = document.createElement('option');
                		  buroExterno.options.add(option,i);
                		  buroExterno.options[i].value=elementosNivel1[i];
                		  buroExterno.options[i].innerText=elementosNivel1[i];
                		  buroExterno.options[i].innerHTML=elementosNivel1[i];
                	  }
            	  }else if(buroInterno.value=="Anaranjado"){
            		  for(var i =0; i<elementosAnaranjado.length; i++){
                		  var option = document.createElement('option');
                		  buroExterno.options.add(option,i);
                		  buroExterno.options[i].value=elementosAnaranjado[i];
                		  buroExterno.options[i].innerText=elementosAnaranjado[i];
                		  buroExterno.options[i].innerHTML=elementosAnaranjado[i];
                	  }
            	  }else if(buroInterno.value=="Rojo"
            		  	|| buroInterno.value=="Negro"){
            		  for(var i =0; i<elementosRojoYNegro.length; i++){
                		  var option = document.createElement('option');
                		  buroExterno.options.add(option,i);
                		  buroExterno.options[i].value=elementosRojoYNegro[i];
                		  buroExterno.options[i].innerText=elementosRojoYNegro[i];
                		  buroExterno.options[i].innerHTML=elementosRojoYNegro[i];
                	  }
            	  }else{
            		  for(var i =0; i<elementos.length; i++){
                		  var option = document.createElement('option');
                		  buroExterno.options.add(option,i);
                		  buroExterno.options[i].value=elementos[i];
                		  buroExterno.options[i].innerText=elementos[i];
                		  buroExterno.options[i].innerHTML=elementos[i];
                	  }  
            	  }
            	  
            	  
              	  for(var i=0; i<buroExterno.options.length; i++){
           	    	if(valorActual == buroExterno.options[i].value){
           	    		buroExterno.options[i].selected=true;
           	    	}
              	  }
              }
              
              
              function validacionBuroExternal() {
            	  var divSend 	= document.getElementById("send");
            	  if( document.getElementById("forma:externalcredithistory").value=="Malo"){
            		  alert("No se Puede Realizar el Contrato, debido al Tipo de Buro Nacional");
            		  divSend.style.display="none";
            		  statusCampos(true);
            	  } else{
            		  if( document.getElementById("forma:internalcredithistory").value!="Negro" ){
            				 if( document.getElementById("forma:internalcredithistory").value!="Rojo"){
            					 divSend.style.display="";
            					 statusCampos(false);
            				 }
            		  }
            	  }
            	  affiliationSelect();
            	  setDepositAmount();
              }
              
              
              function statusCampos(status){
            	  document.getElementById("forma:bemnumber").disabled = status; 
            	  document.getElementById("forma:accountnumbermn").disabled = status; 
            	  document.getElementById("forma:affiliationThrough").disabled = status; 
            	  document.getElementById("forma:formatLayout").disabled = status; 
            	  document.getElementById("forma:payworksClabe").disabled = status;
            	  document.getElementById("forma:retries").disabled = status; 
            	  document.getElementById("forma:maxAmount").disabled = status; 
            	  document.getElementById("forma:affiliation_havedepositcompany").disabled = status; 
            	  document.getElementById("forma:affiliation_depositcompany").disabled = status; 
            	  document.getElementById("forma:affiliation_duedate").disabled = status; 
            	  document.getElementById("forma:referenceName").disabled = status ;
          		  document.getElementById("forma:referenceLength").disabled = status ;
          		  document.getElementById("forma:referenceType").disabled = status ;
          		  document.getElementById("forma:referenceRequired").disabled = status ;
          		  document.getElementById("forma:referenceDV").disabled = status ;
          		  document.getElementById("forma:referenceModuleType").disabled = status ;
            	  document.getElementById("forma:comments").disabled = status; 
              }
              
              

	function setDepositAmount(){
		var officerdepositexent	= document.getElementById("forma:affiliation_officerdepositexent");
		if( isNivelBajo() ){
			//officerdepositexent.disabled = false;
			//officerdepositexent.style.backgroundColor = "#fff";
			//blockFianzaInfo();
			
			var selectHaveCompany = document.getElementById("forma:affiliation_havedepositcompany");
			selectHaveCompany.getElementsByTagName('option')[1].selected = 'selected';
			selectHaveCompany.disabled = true;
			fianzaCD();
		}else{
			//officerdepositexent.disabled = true;
			//officerdepositexent.value = "";
			//officerdepositexent.style.backgroundColor = "#E3E3E3";
			var selectHaveCompany = document.getElementById("forma:affiliation_havedepositcompany");
			selectHaveCompany.getElementsByTagName('option')[0].selected = 'selected';
			selectHaveCompany.disabled = false;
			fianzaCD();
			
			calculateDepositAmount();
			
		}
		
		showNivelRiesgo();
                     		  
	}




		function isNivel1(){
			if( document.getElementById("forma:internalcredithistory").value=="Sin Registro" ||
					document.getElementById("forma:internalcredithistory").value=="Beige" ||
					document.getElementById("forma:internalcredithistory").value=="Amarillo"){
				
				if( document.getElementById("forma:externalcredithistory").value=="Sin registro" ||
						document.getElementById("forma:externalcredithistory").value=="Bueno" ||
						document.getElementById("forma:externalcredithistory").value=="Regular A"){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		
		function isNivel2(){
			if( document.getElementById("forma:internalcredithistory").value=="Anaranjado"){
				
				if( document.getElementById("forma:externalcredithistory").value=="Regular B"){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		
		function isNivelBajo(){
			//var giros_val 		= document.getElementById("forma:client_categorycode").value;
			//var numero_giro 	= giros_val.substring(0,6);
			
			if( document.getElementById("forma:internalcredithistory").value=="Sin Color"){
				  
				if( document.getElementById("forma:externalcredithistory").value=="Sin registro bueno"){
					return validaGiroBajo();
					/*if(numero_giro == "062000" || numero_giro == "063000" || numero_giro == "121200" || numero_giro == "172000" || 
							numero_giro == "173000" || numero_giro == "174000" || numero_giro == "181000" || numero_giro == "182000" ){
						return true;
					}else
						return false;
					*/
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		
		function blockFianzaInfo(flag){
			if(flag){
				var selectHaveCompany = document.getElementById("forma:affiliation_havedepositcompany");
	      	    var optionsSelect 	= selectHaveCompany.selectedIndex;
			  
	      	    selectHaveCompany.disabled = true;
	      	    selectHaveCompany.options[optionsSelect].value = "N";
	      	    var destinationFill1 	= document.getElementById("forma:affiliation_depositcompany");
	      	    //var destinationFill2 	= document.getElementById("forma:monthlyAmount");
	      	    var destinationFill3 	= document.getElementById("forma:affiliation_depositamount");
	      	    var destinationFill4 	= document.getElementById("forma:affiliation_duedate");
	      	    var destinationFill5 	= document.getElementById("forma:affiliation_officerdepositexent");
	    	    
	    	    destinationFill1.disabled = true;
	    	    //destinationFill2.disabled = true;
		        destinationFill3.disabled = true;
		        destinationFill4.disabled = true;
		        destinationFill5.disabled = true;
		        destinationFill1.value = "";
		        //destinationFill2.value = "";
		        destinationFill3.value = "";
		        destinationFill4.value = "";
		        destinationFill5.value = "";
		        destinationFill1.style.backgroundColor = "#E3E3E3";
		        //destinationFill2.style.backgroundColor = "#E3E3E3";
		        destinationFill3.style.backgroundColor = "#E3E3E3";
		        destinationFill4.style.backgroundColor = "#E3E3E3";
		        destinationFill5.style.backgroundColor = "#E3E3E3";
			}else{
				var selectHaveCompany = document.getElementById("forma:affiliation_havedepositcompany");
	      	    var optionsSelect 	= selectHaveCompany.selectedIndex;
			  
	      	    selectHaveCompany.disabled = false;
	      	    selectHaveCompany.options[optionsSelect].value = "N";
	      	    var destinationFill1 	= document.getElementById("forma:affiliation_depositcompany");
	      	    //var destinationFill2 	= document.getElementById("forma:monthlyAmount");
	      	    var destinationFill3 	= document.getElementById("forma:affiliation_depositamount");
	      	    var destinationFill4 	= document.getElementById("forma:affiliation_duedate");
	      	    var destinationFill5 	= document.getElementById("forma:affiliation_officerdepositexent");
	    	    
	    	    destinationFill1.disabled = false;
	    	    //destinationFill2.disabled = false;
		        destinationFill3.disabled = false;
		        destinationFill4.disabled = false;
		        destinationFill5.disabled = false;
		        destinationFill1.value = "";
		        //destinationFill2.value = "";
		        destinationFill3.value = "";
		        destinationFill4.value = "";
		        destinationFill5.value = "";
		        destinationFill1.style.backgroundColor = "#fff";
		        //destinationFill2.style.backgroundColor = "#fff";
		        destinationFill3.style.backgroundColor = "#fff";
		        destinationFill4.style.backgroundColor = "#fff";
		        destinationFill5.style.backgroundColor = "#fff";
			}
			
    	    
		}
		
		
		function comments(){
			var comentarios = document.getElementById("forma:comments");
			var etiquetaComentario = document.getElementById("forma:commMessage");
			if(comentarios.value.length>254){
				etiquetaComentario.value="Maximo 255 caracteres";
				etiquetaComentario.innerText="Maximo 255 caracteres";
				etiquetaComentario.innerHTML="Maximo 255 caracteres";
			}else{
				etiquetaComentario.vallue="";
				etiquetaComentario.innerText="";
				etiquetaComentario.innerHTML="";
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
		
		function onchangeStateIssuer(){
			document.getElementById('forma:SearchIssuerCity').click(); 
		}
		
		
		function mostratLeyenda(){
			var internalcredithistory = document.getElementById('forma:internalcredithistory');
			var externalcredithistory = document.getElementById('forma:externalcredithistory');
			
			if(internalcredithistory == "Sin Color"
				&& externalcredithistory == "Sin registro bueno")
				alert("Buro Bajo");
			else if(internalcredithistory == "Anaranjado"
				&& externalcredithistory == "Regular B")
				alert("Moderado con Nivel Fianza 2");
			else if((internalcredithistory == "Rojo" || internalcredithistory == "Negro")
				&& externalcredithistory == "Malo")
				alert("El servicio no se puede contratar con un nivel de riesgo alto");
		}
		
		function validaGiroBajo(){
			var giros_val = document.getElementById("forma:client_categorycode").value;
			var numero_giro = giros_val.substring(0,6);
			
			if(numero_giro == "062000" || numero_giro == "063000" || numero_giro == "121200" || numero_giro == "172000" || 
					numero_giro == "173000" || numero_giro == "174000" || numero_giro == "181000" || numero_giro == "182000" ||
					numero_giro == ""){
				return true;
			}else{
				alert("Giro seleccionado no valido, solo se permiten giros de Gobiernos, Tel\u00e9fono (Telmex/Telnor) o Servicios de Luz (CFE) (Servicios B\u00e1sicos).");
				document.getElementById("forma:client_categorycode").value = "";
				return false;
			}
		}
		
		function showNivelRiesgo(){
			var internalcredithistory = document.getElementById('forma:internalcredithistory').value;
			var externalcredithistory = document.getElementById('forma:externalcredithistory').value;
			var nivelRiesgo = document.getElementById('nivelRiesgo');
			var txtNivelRiesgo = document.getElementById('txtNivelRiesgo');
			var nivelRiesgoCD = document.getElementById('forma:nivelRiesgoCD');
			
			if((internalcredithistory != 0
					&& externalcredithistory != "Seleccione una Opcion")
					|| (externalcredithistory != 0 
					&& externalcredithistory != "Seleccione una Opcion")){
				if(isNivelBajo()){
					txtNivelRiesgo.innerHTML ="Bajo";
					nivelRiesgo.style.display = "block";
					nivelRiesgo.style.background = "#5cb85c";
					nivelRiesgoCD.value="Bajo";
				}else if(isNivel1()){
					txtNivelRiesgo.innerHTML="Moderado Nivel Fianza 1";
					nivelRiesgo.style.display = "block";
					nivelRiesgo.style.background = "#FFFF00";
					nivelRiesgoCD.value="Moderado Nivel Fianza 1";
				}else if(isNivel2()){
					txtNivelRiesgo.innerHTML="Moderado Nivel Fianza 2";
					nivelRiesgo.style.display = "block";
					nivelRiesgo.style.background = "#FF8000";
					nivelRiesgoCD.value="Moderado Nivel Fianza 2";
				}else if( internalcredithistory == "Negro" 
						|| internalcredithistory == "Rojo"
						|| externalcredithistory == "Malo"	){
					txtNivelRiesgo.innerHTML="Alto";
					nivelRiesgo.style.display = "block";
					nivelRiesgo.style.background = "#DF0101";
					nivelRiesgoCD.value="Alto";
				}else{
					txtNivelRiesgo.innerHTML="";
					nivelRiesgo.style.background = "#FFFFFF";
					nivelRiesgoCD.value="";
				}
			}else{
				txtNivelRiesgo.innerHTML="";
				nivelRiesgo.style.background = "#FFFFFF";
				nivelRiesgoCD.value="";
			}
			
		}
		
		
		
		function calculateDepositAmount(){
			var importeMensual 	= document.getElementById("forma:maxAmount").value; 
			var deposito 		= document.getElementById("forma:affiliation_depositamount");
			
			if(importeMensual != "0"){
				if(importeMensual == "$0 a $100,000"){
					if( isNivel1() ){
						deposito.value = 50000 ;
					}else if( isNivel2() ){
						deposito.value = 75000 ;
					}else{
						deposito.value = 0 ;
					}
					
				}else  if(importeMensual == "$100,001 a $150,000"){
					if( isNivel1() ){
						deposito.value = 75000 ;
					}else if( isNivel2() ){
						deposito.value = 112500 ;
					}else{
						deposito.value = 0 ;
					}
					
				}else if(importeMensual == "$150,001 a $200,000"){
					if( isNivel1() ){
						deposito.value = 100000 ;
					}else if( isNivel2() ){
						deposito.value = 150000 ;
					}else{
						deposito.value = 0 ;
					}
				  
				}else if(importeMensual == "$200,001 a $250,000"){
					if( isNivel1() ){
						deposito.value = 125000 ;	
					}else if( isNivel2() ){
						deposito.value = 187500 ;
					}else{
						deposito.value = 0 ;
					}
					
				}else if(importeMensual == "$250,001 a $300,000"){
					if( isNivel1() ){
						deposito.value = 150000 ;
					}else if( isNivel2() ){
						deposito.value = 225000 ;
					}else{
						deposito.value = 0 ;
					}
					
				}else if(importeMensual == "$300,001 a $400,000"){
					if( isNivel1() ){
						deposito.value = 200000 ;
					}else if( isNivel2() ){
						deposito.value = 300000 ;
					}else{
						deposito.value = 0 ;
					}
					
				}else if(importeMensual == "$400,001 a $450,000"){
					if( isNivel1() ){
						deposito.value = 225000 ;
					}else if( isNivel2() ){
						deposito.value = 337500 ;
					}else{
						deposito.value = 0 ;	
					}
					
				}else if(importeMensual == "$450,001 a $500,000"){
					if( isNivel1() ){
						deposito.value = 250000 ;
					}else if( isNivel2() ){
						deposito.value = 375000 ;
					}else{
						deposito.value = 0 ;
					}
					
				}else if(importeMensual == "$500,001 a En adelante"){
					if( isNivel1() ){
						deposito.value = 350000 ;
					}else if( isNivel2() ){
						deposito.value = 500000 ;
					}else{
						deposito.value = 0 ;
					}
				}
			}else{
				deposito.value = 0 ;
			}
		}
		
		
		function validarCreditoActivoRetrasoSi(){
	    	var creditoActivoRetrasoSi=document.getElementById("forma:creditoActivoRetrasoSi");
  			var creditoActivoRetrasoNo=document.getElementById("forma:creditoActivoRetrasoNo");
  			if(creditoActivoRetrasoSi.checked==true){
  				creditoActivoRetrasoNo.checked=false;		      			
      		}
        }
	    function validarCreditoActivoRetrasoNo(){
	    	var creditoActivoRetrasoSi=document.getElementById("forma:creditoActivoRetrasoSi");
  			var creditoActivoRetrasoNo=document.getElementById("forma:creditoActivoRetrasoNo");
  			if(creditoActivoRetrasoNo.checked==true){
  				creditoActivoRetrasoSi.checked=false;		      			
      		}
        }	
			
	    function validarCreditoHistoricoSi(){
	    	var creditoHistoricoRetrasoSi=document.getElementById("forma:creditoHistoricoRetrasoSi");
  			var creditoHistoricoRetrasoNo=document.getElementById("forma:creditoHistoricoRetrasoNo");
  			if(creditoHistoricoRetrasoSi.checked==true){
  				creditoHistoricoRetrasoNo.checked=false;		      			
      		}
        }
	    function validarCreditoHistoricoNo(){
	    	var creditoHistoricoRetrasoSi=document.getElementById("forma:creditoHistoricoRetrasoSi");
  			var creditoHistoricoRetrasoNo=document.getElementById("forma:creditoHistoricoRetrasoNo");
  			if(creditoHistoricoRetrasoNo.checked==true){
  				creditoHistoricoRetrasoSi.checked=false;		      			
      		}
        }	
			
			
			
			
			
		