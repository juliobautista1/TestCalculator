/* Copy Paste Block

function disableselect(e){
    return false;
}

function reEnable(){
    return true;
}

document.onselectstart=new Function ("return false");

if (window.sidebar){
    document.onmousedown=disableselect;
    document.onclick=reEnable;
}*/

/*  Body */


var xmlDocState = null;
var xmlDocIndSector = null;

function loadXMLDoc(dname) {
  try { //Internet Explorer
    xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
  } catch(e) {
    try { //Firefox, Mozilla, Opera, etc.
      xmlDoc=document.implementation.createDocument("","",null);
    } catch(e) {
      alert(e.message);
    }
  }

  try {
    xmlDoc.async=false;
    xmlDoc.load(dname);
    return(xmlDoc);
  } catch(e) {
    var req = new XMLHttpRequest();
    req.open("GET", dname, false);
    req.send(null);
    // print the name of the root element or error message
    xmlDoc = req.responseXML;
    return(xmlDoc);
  }

  return null;
}


function loadState(fileName, stern) {
  if ( xmlDocState == null )
    xmlDocState = loadXMLDoc(fileName);

  root = xmlDocState;

  x = root.getElementsByTagName('State');
  insertContent = "";
  first = "";
  for (i=0;i<x.length;i++) {
   temp = x[i].attributes.getNamedItem("name").nodeValue;
   insertContent += "<option value=\"" + temp + "\">" + temp + "</option>";
  }

  for (i = 0; i < stern.length; i++) {
    state_sel = document.getElementById(stern[i].state_fill+'--');
    select_innerHTML(state_sel,insertContent);
    if( state_sel.length > 0 ){
      state_sel.options[0].selected = true;
      document.getElementById(stern[i].state_fill).value = state_sel.options[state_sel.selectedIndex].value;
    }
  }
//typeof(federated_media_random_number) == "undefined"
  root = null;
}

function refer(tagto) {
  document.getElementById(tagto).value = document.getElementById(tagto+'--').value;
}

function loadCity(from, tagto) {
  if ( xmlDocState == null )
    xmlDocState = loadXMLDoc(fileName);

  root = xmlDocState;

  indexs = document.getElementById(from+'--');
  selection = indexs.options[indexs.selectedIndex].value;
  document.getElementById(from).value = selection;

  x = root.getElementsByTagName('State');
  insertContent = "";
  first = "";
  for (i=0;i<x.length;i++) {
    temp = x[i].getAttributeNode("name").nodeValue;
    if ( temp == selection ) {
      y = x[i].childNodes;
      if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)){
        for (j=0;j<y.length;j++) {
          name = x[i].childNodes[j].getAttributeNode("name").nodeValue;
          insertContent += "<option value=\"" + name + "\">" + name + "</option>";
          if ( j == 0 ) first = name;
        }
      } else {
        for (j=1;j<y.length;j++) {
          name = x[i].childNodes[j].getAttributeNode("name").nodeValue;
          insertContent += "<option value=\"" + name + "\">" + name + "</option>";
          if ( j == 1 ) first = name;
          j++;
        }
      }
    }
  }

  select_innerHTML(document.getElementById(tagto+'--'),insertContent);

  document.getElementById(tagto).value = first;
}

function loadCoType(fileName, desc) {
  try{
    /*--  Cargamos el XML con la informacion --*/
//    if ( xmlDocIndSector == null )
      xmlDocIndSector = loadXMLDoc(fileName);

    var xmlContent = xmlDocIndSector;


    /*-- Procedemos a Generar los Arrays --*/
    var array_desc = new Array();

    x=xmlContent.getElementsByTagName("Giro");
    for ( i = 0; i < x.length; i++ ) {
      array_desc.push(x[i].childNodes[0].nodeValue);
    }

   actb(document.getElementById(desc), array_desc);

  }catch(e){
  }
}

function validarGiro(fileName, descGiro) {
	  try{
	    /*--  Cargamos el XML con la informacion --*/
//	    if ( xmlDocIndSector == null )
	      xmlDocIndSector = loadXMLDoc(fileName);

	    var xmlContent = xmlDocIndSector;
	    /*-- Procedemos a Generar los Arrays --*/
	    var array_desc = new Array();

	    x=xmlContent.getElementsByTagName("Giro");
	    for ( i = 0; i < x.length; i++ ) {
	      if(descGiro == x[i].childNodes[0].nodeValue){
	    	return "true";
	      }
	    }

	  }catch(e){
	  }

	  return "false";
	}


function sternObject(state, state_fill, city, city_fill){
  this.state = state;
  this.state_fill = state_fill;
  this.city = city;
  this.city_fill = city_fill;
}

function getStateValues(fills){
  state_vals = new Array();
  for ( key in fills ){
    state = document.getElementById(key).value;
    city  = document.getElementById(fills[key]).value;
    state_vals.push(new sternObject(state, key, city, fills[key]));
  }
  return state_vals;
}

function comboLoader(fileName, fills){
  array = getStateValues(fills);
  loadState(fileName, array);
  for ( key in fills ) {  //  from, tagto

    loadCity(key, fills[key]);

  }

  comboSelection(array);
}

function comboSelection(stern){
  for ( z=0;z < stern.length; z++) {
    if( stern[z].state != "" ) {
      indexs = document.getElementById(stern[z].state_fill+'--');
      selection = indexs.options.length;
      for(i=0;i<selection;i++) {
        if(indexs.options[i].value == stern[z].state) {
          indexs.options[i].selected = true;
          break;
        }
      }
      document.getElementById(stern[z].state_fill).value = stern[z].state;

      loadCity(stern[z].state_fill, stern[z].city_fill);

      if( stern[z].city != "" ) {
        indexs = document.getElementById(stern[z].city_fill+'--');
        selection = indexs.options.length;
        for(i=0;i<selection;i++) {
          if(indexs.options[i].value == stern[z].city) {
            indexs.options[i].selected = true;
            break;
          }
        }
        document.getElementById(stern[z].city_fill).value = stern[z].city;
      } else {
        document.getElementById(stern[z].city_fill+'--').options[0].selected = true;
        document.getElementById(stern[z].city_fill).value = document.getElementById(stern[z].city_fill+'--').options[0].value;
      }
    } else {
        document.getElementById(stern[z].state_fill+'--').options[0].selected = true;
        loadCity(stern[z].state_fill, stern[z].city_fill);
        document.getElementById(stern[z].city_fill+'--').options[0].selected = true;
    }
  }
}


function select_innerHTML(objeto,innerHTML){
  objeto.innerHTML = ""
  var selTemp = document.createElement("micoxselect")
  var opt;
  selTemp.id="micoxselect1"
  document.body.appendChild(selTemp)
  selTemp = document.getElementById("micoxselect1")
  selTemp.style.display="none"
  if(innerHTML.indexOf("<option")<0){//se n?o ? option eu converto
    innerHTML = "<option>" + innerHTML + "</option>"
  }

  innerHTML = innerHTML.replace(/<option/g,"<span").replace(/<\/option/g,"</span")
  selTemp.innerHTML = innerHTML


  for(var i=0;i<selTemp.childNodes.length;i++){
    var spantemp = selTemp.childNodes[i];

    if(spantemp.tagName){
      opt = document.createElement("OPTION");

      if(document.all){ //IE
        objeto.add(opt);
      }else{
        objeto.appendChild(opt);
      }

      //getting attributes
      for(var j=0; j<spantemp.attributes.length ; j++){
        var attrName = spantemp.attributes[j].nodeName;
        var attrVal = spantemp.attributes[j].nodeValue;
        if(attrVal){
          try{
            opt.setAttribute(attrName,attrVal);
            opt.setAttributeNode(spantemp.attributes[j].cloneNode(true));
          }catch(e){ alert(e.error);}
        }
      }
      //getting styles
      if(spantemp.style){
        for(var y in spantemp.style){
          try {
            opt.style[y] = spantemp.style[y];
          }catch(e){}
        }
      }
      //value and text
      opt.value = spantemp.getAttribute("value")
      opt.text = spantemp.innerHTML
      //IE
      opt.selected = spantemp.getAttribute('selected');
      opt.className = spantemp.className;
    }
  }
  document.body.removeChild(selTemp)
  selTemp = null
}

/***********************************************
 *
 * Soporte para Campos con dependencia
 *
 */

function activateFill(position, tagfrom, tagto){

	index = document.getElementById(tagfrom);
	
	if (index.selectedIndex != position) {
		//Habilitar
		document.getElementById(tagto).disabled = false;
		//document.getElementById(tagto).focus();
	} else {
		//Deshabilitar
	    document.getElementById(tagto).disabled = true;
	    document.getElementById(tagto).value = "";
	}  
}

/***********************************************
*
* Soporte para Adquiriente
*
*/

function adquirerOption() {
   var sel_prod 	= document.getElementById("forma:affiliation_productdesc");
   var sel_sol 		= document.getElementById("forma:affiliation_soluciontype");
   var int_mod 		= document.getElementById("forma:affiliation_modality");
   var giros_val 	= document.getElementById("forma:client_categorycode").value;
   
   document.getElementById("forma:affiliation_comercialplan").disabled =false;
   var alliance 		= document.getElementById("forma:affiliation_alliance");
   var alianza_val 		= document.getElementById("forma:affiliation_alliance").value;
   var sel_prod_val 	= sel_prod.options[sel_prod.selectedIndex].value;
   var sel_sol_val 		= sel_sol.options[sel_sol.selectedIndex].value;
   var numero_giro 		= giros_val.substring(0,6);
   var paquete 			= document.getElementById("forma:affiliation_chargeType").value;

   enableCashback(); // Habilita campo de cashback si se cumplen las condiciones
   resetComission(); //si se selecciono cashback se habiliata el campo para editar la comision, si no se pone en cero y deshabilitado
   activateIntegrationCombo(alianza_val);//si se selecciona cybersource se habilita el combo de integracion
   activateRentaDolar();//se habilita la renta en dolares en base a la integracion elegida, si se selecciono cybersource
   
   //Si se selecciona alianza Netpay
   if(alianza_val == "Netpay"){
   	var tiempo_aire =  document.getElementById("forma:affiliation_tiempoaire");
   	 tiempo_aire.disabled =false;
   	 if(tiempo_aire.checked==true){// y se habilita la opcion de venta tiempo aire
   		 activateTA();// se activa el recuadro de comisiones de compa�ia telefonica
   	 }
   }else{
   	deactivateTA();//si se selecciona cualquier otra alianza  se desactiva el recuadro
   }
   
   //Si el producto es Cargo Recurrente
   if ( sel_prod_val == "Cargo Recurrente" ) {
       int_mod.disabled = true; // se deshabilita la modalidad
       sel_sol.disabled = false; //y se habilita el combo de tipo de solucion
       displayEquipments("none"); // no se muestran los equipos
       blockCombosAlliance(); //se deshabilita el paquete y se pone "seleccione"
       
   } else if ( sel_prod_val == "Comercio Electronico" ) { //si el producto es Comercio Electronico
       int_mod.disabled = true; // se deshabilita la modalidad
       sel_sol.disabled = true; // se deshabilita tipo de solucion
       displayEquipments("none"); //no se muestran los equipos
       document.getElementById("forma:affiliation_alliance").disabled =false; // se habilita el combo de alianza

       var alianza_val 	= document.getElementById("forma:affiliation_alliance").value; // alianza
       var chargeType 		= document.getElementById("forma:affiliation_chargeType"); // paquete
		
       if( (alianza_val=="Ninguna") || alianza_val=="Agregador"){
			document.getElementById("forma:affiliation_chargeType").disabled =true;//paquete se deshabilita
			document.getElementById("forma:affiliation_chargeType").value = 0; // se pone valor 0 a paquete
		}else { //si se elige alguna alianza diferente a agregador
			fillComboPaquete(chargeType.selectedIndex, chargeType.value); //se llena el combo de paquete y manda la informacion en caso de que ya haya seleccionado
			chargeType.disabled =false; // se habilita el combo de paquetes
		}
		
	} else if(sel_prod_val == "Interredes" || sel_prod_val == "Terminal punto de venta"){ // si el producto es TPV o Interredes
	   	if(sel_sol_val =="Internet"){ // y la solucion es internet
	   		if(numero_giro == "005814" || numero_giro == "005812" || // y el giro esta dentro de estos
	   				numero_giro == "005813" || numero_giro == "003747" || 
	   				numero_giro == "007011" || numero_giro == "007012" )
	   		{
	   			document.getElementById("forma:affiliation_alliance").disabled =false; //se habilita la alianza
	   			microsEquipments();//depende de la alianza se habilitan los equipos y depende el paquete se habilitan los planes
		   		if( alianza_val == "Micros" || alianza_val == "Netpay"){ //si la alianza es micros o netpay
		   			document.getElementById("forma:affiliation_chargeType").disabled =false; //se habilita el combo paquetes
		   		}else {
		   			document.getElementById("forma:affiliation_chargeType").disabled =true;//se deshabilita paquete
		   			document.getElementById("forma:affiliation_chargeType").value = 0;
		   		}
		   	}else{//si el giro es diferente 
		   		if(sel_prod_val == "Interredes"){// y el producto es interredes
		   				 document.getElementById("forma:affiliation_alliance").disabled =false; //se habilita el combo alianza
		   				 microsEquipments(); //depende de la alianza se habilitan los equipos y depende el paquete se habilitan los planes
		   		}else{//si es tpv
		   			blockCombosAlliance(); //se deshabilita el combo paquete y se pone "seleccione"
		   		}
		   	}
	   		if(alianza_val == "Micros" || alianza_val == "Netpay"){ //si la alianza es micros o netpay
	   			document.getElementById("forma:affiliation_chargeType").disabled =false;// paquete se habilita
			}else{
				document.getElementById("forma:affiliation_chargeType").disabled =true;// paquete se deshabilita
				document.getElementById("forma:affiliation_chargeType").value = 0;
				var red = valorRed();
				if(red==0){ //si es banorte
					document.getElementById("forma:affiliation_comercialplan").disabled=false; //se habilita el plan banorte
				}else if(red==1){//si es ixe
					document.getElementById("forma:affiliation_comercialplanIxe").disabled=false;//se habilita el plan ixe	
				}
			}
	   	}else{// si la solucion es tradicional (tpv o interredes)
	   		blockCombosAlliance(); //se deshabilita el combo paquete y se pone "seleccione"
	   	}
	   	adquirerOptionElse(); //si es tpv o interredes se activa modalidad y tipo de solucion y se muestran los equipos
   }else{//en teoria no deberia entrar nunca porque siempre hay un "producto elegido"
   	adquirerOptionElse();
   	blockCombosAlliance();
   }
   recalculateCommisionTable();//se activa o desactiva el campo "otro" en base al plan
   								//se recalcula la tabla de comisiones, se activa o desactiva el combo de sustituir renta mensual en base al plan
}



function adquirerOptionElse(){
	var sel_sol = document.getElementById("forma:affiliation_soluciontype");
    var int_mod = document.getElementById("forma:affiliation_modality");
    int_mod.disabled = false; //se activa modalidad
    sel_sol.disabled = false; // se activa tipo de solucion
    displayEquipments("block"); //se muestra el cuadro de equipos
}


function blockCombosAlliance(){
	document.getElementById("forma:affiliation_chargeType").disabled =true; //combo paquete deshabilitado
	document.getElementById("forma:affiliation_chargeType").value = 0; //valor paquete en 0
}

function displayEquipments(display) {
	
    var prefix = "forma:";
	var equipments = new Array(
			"affiliation_quantmanual",
			"tpvTel",
			"tpvMovil",
			"tpvInternet",
			"tpvInternetTel",
			"tpvBlueTel",
			"tpvGprs",
			"tpvBlueInternet",
			"wifiMovil",
			"wifiTel",
			"affiliation_quantmanual",
			"affiliation_quantpinpad"
			);
	
	var div_equipment = document.getElementById("forma:equipmentDetail");
	
	if(display == "none"){
		for (i = 0; i < equipments.length; i++){
			document.getElementById(prefix + equipments[i]).value="0";
		}
	}	
	
    div_equipment.style.display =display;

}

	function microsEquipments(){
		var alianza_val 	= document.getElementById("forma:affiliation_alliance").value;
		var chargeType_val 	= document.getElementById("forma:affiliation_chargeType").value;
		var chargeType = document.getElementById("forma:affiliation_chargeType");
		
		fillComboPaquete(chargeType.selectedIndex, chargeType_val); //se carga el combo del paquete (se envian valores seleccionados)
		
		if( alianza_val == "Micros" || alianza_val == "Netpay"){ // si la alianza es micros o netpay
			deactivateEquipments(); // se desactiva la seccion de equipos fijos y moviles
		}else { // si la alianza es otra
			activateEquipments();//se activan los equipos
		}
		
		var red = valorRed();
		if(chargeType_val == "Estudio de Rentabilidad"){ // si el paquete es estudio de rentabilidad
			if(red==0){//banorte
				document.getElementById("forma:affiliation_comercialplan").value = 5; //se deshabilita el combo plan y se pone estudio rentabilidad
				document.getElementById("forma:affiliation_comercialplan").disabled =true;	
			}else{//ixe
				document.getElementById("forma:affiliation_comercialplanIxe").value = 5;//se deshabilita el combo plan y se pone estudio rentabilidad
				document.getElementById("forma:affiliation_comercialplanIxe").disabled =true;	
			}
		}else if (chargeType_val == "Reciprocidad"){ // si el paquete es reciprocidad
			if(red==0){//banorte
				document.getElementById("forma:affiliation_comercialplan").value = 5;////se deshabilita el combo plan y se pone estudio rentabilidad
				document.getElementById("forma:affiliation_comercialplan").disabled =true;	
			}else{//ixe
				document.getElementById("forma:affiliation_comercialplanIxe").value = 5;//se deshabilita el combo plan y se pone estudio rentabilidad
				document.getElementById("forma:affiliation_comercialplanIxe").disabled =true;
			}
		}else {
			if(red==0){//banorte
				document.getElementById("forma:affiliation_comercialplan").disabled =false; //se habilita el combo de plan
			}else{//ixe
				document.getElementById("forma:affiliation_comercialplanIxe").disabled =false; //se habilita combo de plan
			}
		}
		
		recalculateCommisionTable();
	}
	
	
	function activateAllianceEquipments(equipment){
//		var lan_val 	= document.getElementById("forma:affiliation_quantlan").value ;
		var pinpa_val 	= document.getElementById("forma:affiliation_quantpinpad").value;
		var alliance 	= document.getElementById("forma:affiliation_alliance").value;
    	if(alliance == "Micros"){
  	    	if(pinpa_val >0 ){//&& lan_val ==0 ){
				var resultado = confirm("Si se requiere y asi se negocio con el cliente, " +
						"podras seleccionar algun otro equipo como GPRS, WiFi, Bluetooth o Placa transcriptora, " +
						"en el entendido de que estos equipos no estaran conectados " +
						"bajo la misma plataforma de MICROS. " +
						"Quieres seleccionar equipos adicionales?");
				
				if(resultado){
					activateEquipments();
				}
				
			}else if(pinpa_val == 0) {//&& lan_val >0 ){
				var resultado = confirm("Si se requiere y asi se negocio con el cliente, " +
						"podras seleccionar algun otro equipo como GPRS, WiFi, Bluetooth o Placa transcriptora, " +
						"en el entendido de que estos equipos no estaran conectados " +
						"bajo la misma plataforma de MICROS. " +
						"Quieres seleccionar equipos adicionales?");
				
				if(resultado){
					activateEquipments();
				}
				
			}
			
			else if (pinpa_val == 0) {//&& lan_val ==0){
				deactivateEquipments();
			}
  	    }else  if(alliance == "Netpay"){
  	    	
  	    	if(pinpa_val >0 && equipment=='pinpad'){
  	  			var resultado = confirm("Si se requiere y asi se negocio con el cliente, " +
  	  					"podras seleccionar algun otro equipo como Dial up, GPRS, WiFi, Bluetooth o Placa transcriptora, " +
  	  						"en el entendido de que estos equipos no estaran conectados " +
  	  						"bajo la misma plataforma de NETPAY (Caja Total Banorte). " +
  	  						"Quieres seleccionar equipos adicionales?");
  	  				
  	  			if(resultado){
  					activateEquipments();
//  					document.getElementById("forma:affiliation_quantlan").disabled =false;
    			}	
  	    	}
  	    }
		recalculateCommisionTable();
	}
	
	function activateEquipments(){
		document.getElementById("forma:affiliation_quantmanual").disabled =false;
		document.getElementById("forma:tpvTel").disabled=false;
		document.getElementById("forma:tpvMovil").disabled=false;
		document.getElementById("forma:tpvInternet").disabled=false;
		document.getElementById("forma:tpvInternetTel").disabled=false;
		document.getElementById("forma:tpvBlueTel").disabled=false;
		document.getElementById("forma:tpvGprs").disabled=false;
		document.getElementById("forma:tpvBlueInternet").disabled=false;
		document.getElementById("forma:wifiMovil").disabled=false;
		document.getElementById("forma:wifiTel").disabled=false;
		
	}
	
	function deactivateEquipments(){
		var equipments = new Array(
				"forma:affiliation_quantmanual",
				"forma:tpvTel",
				"forma:tpvMovil",
				"forma:tpvInternet",
				"forma:tpvInternetTel",
				"forma:tpvBlueTel",
				"forma:tpvGprs",
				"forma:tpvBlueInternet",
				"forma:wifiMovil",
				"forma:wifiTel",
				"forma:affiliation_quantpinpad"
				);
		
		if(validateEmpty()){
			for (var i = 0; i < equipments.length; i++){
				document.getElementById(equipments[i]).value="0";
				document.getElementById(equipments[i]).disabled=true;
			}
		}
	}
	
	
	function validateEmpty(){
		var manual = document.getElementById("forma:affiliation_quantmanual").value;
		var pinpad = document.getElementById("forma:affiliation_quantpinpad").value;
		var tpvTel = document.getElementById("forma:tpvTel").value;
		var tpvMovil = document.getElementById("forma:tpvMovil").value;
		var tpvInternet = document.getElementById("forma:tpvInternet").value;
		var tpvInternetTel = document.getElementById("forma:tpvInternetTel").value;
		var tpvBlueTel = document.getElementById("forma:tpvBlueTel").value;
		var tpvGprs = document.getElementById("forma:tpvGprs").value;
		var tpvBlueInternet = document.getElementById("forma:tpvBlueInternet").value;
		var wifiMovil = document.getElementById("forma:wifiMovil").value;
		var wifiTel = document.getElementById("forma:wifiTel").value;
		
		if(manual==0 && pinpad==0 && tpvTel==0 && tpvMovil==0 &&
				tpvInternet==0 && tpvInternetTel==0 && tpvBlueTel==0 &&
				tpvGprs==0 && tpvBlueInternet==0 && wifiMovil==0 && wifiTel==0
		){
			return true;
		}
//		if(document.getElementById("forma:affiliation_quantgprs").value == 0){
//			if(document.getElementById("forma:affiliation_quantwifi").value == 0){
//				if(document.getElementById("forma:affiliation_quantblue").value == 0){
//					if(document.getElementById("forma:affiliation_quantdialup").value == 0){
//						if(document.getElementById("forma:affiliation_quantmanual").value == 0){
//							return true;
//						}
//					}
//				}
//			}
//		}
		return false;
	}

function getAdquirerPesosCtrlsArray(){
	
	return new Array("affiliation_otherconcept1des","affiliation_avcommisiontcmn","affiliation_avcommisiontdmn","affiliation_avcommisionintnlmn",
	        "affiliation_transcriptorratemn", //elimine cuota mensual ya que la querian editable
	        "affiliation_monthlyinvoicingminmn", "affiliation_failmonthlyinvoicingmn",
	        "affiliation_minimiunbalancemn", "affiliation_failminimiunbalancemn", 
	        "affiliation_promnotemn", "affiliation_failpromnotemn", "affiliation_avsmn",
	        "affiliation_monthlyrate3dsmn",// renta mensual 3d secure para hacerlo editable
	        "affiliation_otherconcept1mn",
	        "replaceAmountratemn","transactionFeeCyberMn","transactionFeeMicrosMn","affiliation_rateMposmn"
	        );
}
function getAdquirerDollarCtrlsArray(){
	
	return new Array("affiliation_otherconcept1des","affiliation_avcommisiontcdlls","affiliation_avcommisiontddlls","affiliation_avcommisionintnldlls",
	        "affiliation_transcriptorratedlls", //elimine renta mensual porque la querian editable
	        "affiliation_monthlyinvoicingmindlls","affiliation_failmonthlyinvoicingdlls", "affiliation_minimiunbalancedlls", 
	        "affiliation_failminimiunbalancedlls", "affiliation_promnotedlls", "affiliation_failpormnotedlls", 
	        "affiliation_avsdlls", "affiliation_monthlyrate3dsdlls", //renta mensual 3d secure para hacerlo editable
	        "affiliation_otherconcept1dlls","replaceAmountratedlls", "transactionFeeCyberDlls",
	        "transactionFeeMicrosDlls","affiliation_rateMposdlls"
	        );

}
function adquirerPesosDolares() {
    var pre = "forma:";
    var sel_opt = document.getElementById(pre + "affiliation_currency");
    var option = sel_opt.options[sel_opt.selectedIndex].value;
    var sel_serv_opt = document.getElementById(pre + "affiliation_servicetype");
    var option_serv = sel_serv_opt.options[sel_serv_opt.selectedIndex].value;
    
    enableCashback(); // Habilita campo de cashback si se cumplen las condiciones
    resetComission(); //limpia y deshabilita comision cashback dependiendo si se eligio o no
    
    if ( option == "Pesos" ) {//si la divisa es pesos
       	if ( option_serv == "Alta" ) {//tramite
        	document.getElementById(pre + "affiliation_numaffilmn").disabled = true;//afiliacion pesos deshabilitado
        	document.getElementById(pre + "affiliation_numaffildlls").disabled = true;//afiliacion dolares deshabilitado
        }
        //se habilita cuenta concentradora pesos
    	document.getElementById(pre + "affiliation_accountnumbermn").disabled = false;

    	//se deshabilitan y se limpian valores de cuenta concentradora y afiliaciones
        document.getElementById(pre + "affiliation_accountnumberdlls").value = "";
        document.getElementById(pre + "affiliation_numaffildlls").value = "";
        document.getElementById(pre + "affiliation_accountnumberdlls").disabled = true;
        document.getElementById(pre + "affiliation_numaffildlls").disabled = true;
        
    } else {//si la divisa es dolares o ambos
        if ( option_serv == "Alta" ) { //tramite
            document.getElementById(pre + "affiliation_numaffilmn").disabled = false; //se habilia numero de afiliacion
            document.getElementById(pre + "affiliation_numaffildlls").disabled = true;//se deshabilitan numero de afiliacion en dolares
        }
        //Pesos
    	document.getElementById(pre + "affiliation_accountnumbermn").disabled = false; //se habilita numero cuenta pesos
    	//Dolares
        document.getElementById(pre + "affiliation_accountnumberdlls").disabled = false; // se habilita numero cuenta dolares
        document.getElementById(pre + "affiliation_numaffildlls").disabled = false;//se deshabilita afiliacion dolares
    }

    setCommisionTable(); //en base al plan y la divisa se habilitan y deshabilitan los campos de la tabla de comisiones de pesos o dolares
    recalculateCommisionTable();
    unblockOptionMonthly();//en base a la divisa se activa/desactiva campo de sustituir renta
    checkRate();//se establece cuota de afiliacion en base a la divisa y el producto
}


function unblockOptionMonthly(){

    var sel_opt = document.getElementById("forma:affiliation_currency");
    var option = sel_opt.options[sel_opt.selectedIndex].value;
    
	if ( option == "Pesos" ) {
    	document.getElementById("forma:optionMonthlyratemn").disabled = false;
    	document.getElementById("forma:optionMonthlyratedlls").disabled = true;
    }else if (option == "Dolares"){
    	document.getElementById("forma:optionMonthlyratemn").disabled = true;
    	document.getElementById("forma:optionMonthlyratedlls").disabled = false;
    }else if (option == "Ambos"){
    	document.getElementById("forma:optionMonthlyratemn").disabled = false;
    	document.getElementById("forma:optionMonthlyratedlls").disabled = true;
    }
}

function recalculateCommisionTable(){
	var comercialPlan = document.getElementById("forma:affiliation_comercialplan");
	var comercialPlanIxe = document.getElementById("forma:affiliation_comercialplanIxe");
	var otherPlan = document.getElementById("forma:otherCommercialPlan");

	if(otherPlan!=null){
		if(comercialPlan.value == 6 || comercialPlanIxe.value==6)//si el plan seleccionado es "otro"
		{
			otherPlan.disabled =false; //se activa el campo de "especifique"
		}else{
			otherPlan.value =""; //se limpia el campo de "especifique"
			otherPlan.disabled =true; // y se deshabilita
		}
	}
	document.getElementById("forma:recalculateCommisionTable").value = true; //se tiene que recalcular la tabla
	checkPlanForMonthlyRateRecall();//se habilita o deshabilita el combo de sustituir renta mensual en base al plan
}



function setCommisionTable(){
    var pre = "forma:";
    var currencyType ="Pesos";
    
	pesos = getAdquirerPesosCtrlsArray();
	dolares = getAdquirerDollarCtrlsArray();

	if(document.getElementById(pre + "isOIP").value == '' || document.getElementById(pre + "isOIP") == 'false'){
	    var sel_opt = document.getElementById(pre + "affiliation_currency");
	    currencyType = sel_opt.options[sel_opt.selectedIndex].value;
	}
        
    var comercialPlan = document.getElementById(pre + "affiliation_comercialplan");
    var comercialPlanIxe = document.getElementById(pre + "affiliation_comercialplanIxe");
    var comercialPlanSelected = comercialPlan[comercialPlan.selectedIndex].value;
    var comercialPlanSelectedIxe = comercialPlanIxe[comercialPlanIxe.selectedIndex].value;
    
	//si el plan es diferente a estudio de rentabilidad y otro
    if(comercialPlanSelected!=5 && comercialPlanSelected!=6 &&
    		comercialPlanSelectedIxe!=5 && comercialPlanSelectedIxe!=6	)
    {
    	document.getElementById(pre + "commchargetype").disabled = true; //comision por trasaccion (porcentaje/monto)
    	document.getElementById(pre + "affiliation_otherconcept1des").disabled = true; //otros conceptos
    	
    	//se deshabilitan los campos de dolares
		for (i = 0; i < dolares.length; i++) {
			document.getElementById(pre + dolares[i]).disabled = true;
		}
		//se deshabilitan los campos de pesos
		for (i = 0; i < pesos.length; i++) {
			document.getElementById(pre + pesos[i]).disabled = true;
		}
	} else {//si el plan es estudio de rentabilidad u otro
			if (currencyType == "Pesos") {
				//si es pesos, se limpian los campos de dolares y se deshabilitan
				for (i = 0; i < dolares.length; i++) {
					document.getElementById(pre + dolares[i]).value = "";
					document.getElementById(pre + dolares[i]).disabled = true;
				}
				//se habilitan los campso de pesos
				for (i = 0; i < pesos.length; i++) {
					document.getElementById(pre + pesos[i]).disabled = false;
				}
			} else {//si la divisa es diferente a pesos
				//se habilitan los campos de dolares
				for (i = 0; i < dolares.length; i++) {
					document.getElementById(pre + dolares[i]).disabled = false;
				}
				//se habilitan los campos de pesos
				for (i = 0; i < pesos.length; i++) {
					document.getElementById(pre + pesos[i]).disabled = false;
				}
			}
	}
}


function isValidAccNum(fill){
	var fieldName= document.getElementById('forma:' + fill);
    if(fieldName.value.length <10){
    	if(fieldName.value.length>8){
    		fieldName.value = "0" + fieldName.value 
    	}else {
    		if(confirm("Por favor capture un numero de cuenta de 10 posiciones")) {
    			fieldName.focus();
    		}
    	}
    }
	
}

function isValidAccNumMtto(fill){
	var fieldName= document.getElementById('forma:' + fill);
    if(fieldName.value.length <10){
    	if(fieldName.value.length>9){
    		//fieldName.value = "0" + fieldName.value 
    	}else {
    		if(confirm("Por favor capture un numero de cuenta de 10 posiciones")) {
    			//fieldName.focus();
    		}
    	}
    }
	
}

function isValidTokenNumMtto(fill){
	var fieldName= document.getElementById('forma:' + fill);
    if(fieldName.value.length!=9){
//    	if(fieldName.value.length>9){
    		//fieldName.value = "0" + fieldName.value 
//    	}else {
    		if(alert("Por favor capture un numero de token de 9 posiciones")) {
    			fieldName.focus();
//    		}
    		
    	}
    }
	
}

function isValidAccNumPesos(fill){
	var valorDivisa = document.getElementById("forma:affiliation_currency").value;
	if(valorDivisa == "Pesos" || valorDivisa == "Ambos" ){
			var fieldName= document.getElementById('forma:' + fill);
		    if(fieldName.value.length <10){
		    	if(fieldName.value.length>8){
		    		fieldName.value = "0" + fieldName.value 
		    	}else {
		    		if(confirm("Por favor capture un numero de cuenta de 10 posiciones")) {
		    			fieldName.focus();
		    		}
		    	}
		    }
	}
	
}

function isValidAccNumDll(fill){
	var valorDivisa = document.getElementById("forma:affiliation_currency").value;
	if(valorDivisa == "Dolares" || valorDivisa == "Ambos" ){
			var fieldName= document.getElementById('forma:' + fill);
		    if(fieldName.value.length <10){
		    	if(fieldName.value.length>8){
		    		fieldName.value = "0" + fieldName.value 
		    	}else {
		    		if(confirm("Por favor capture un numero de cuenta de 10 posiciones")) {
		    			fieldName.focus();
		    		}
		    	}
		    }
	}
	
}

function isValidValue(fill, len, name){
	var fieldName= document.getElementById('forma:' + fill);
	if (fieldName.value.length < len || fieldName.value<=0){
		if(confirm("Por favor capture un valor correcto para el apartado de " + name)) {
			fieldName.focus();
		}
	}
}

function isExent(){

    var selectFillName = document.getElementById('forma:' + "affiliation_havedepositcompany");
    var fillOptionsSelect = selectFillName.selectedIndex;
    var destinationFill2 = document.getElementById('forma:' + "affiliation_officerdepositexent");
    	
    	if( selectFillName.options[fillOptionsSelect].value == 0 && selectFillName.disabled==false) {
    		isNotEmpty('affiliation_officerdepositexent','Nombre de quien autoriza excentar la fianza');
    	}
	
}

function isNotEmpty(fill, name){
	var fieldName= document.getElementById('forma:' + fill);
	if (fieldName.value.length <=0){
		if(confirm("Por favor capture un valor correcto para el apartado de " + name)) {
			fieldName.focus();
		}
	}
}

function hiddenObjet(fill, divs) {
    this.fill = fill;
    this.divs = divs;
}

function hiddenFill(opt) {

    var fillName = document.getElementById(opt.fill);
    var fillOptionsSelect = fillName.selectedIndex;


    for ( i = 0; i < opt.divs.length; i++){
        var temp = opt.divs[i];
        if( temp == fillName.options[fillOptionsSelect].value) {
            document.getElementById(temp).style.display = "block";
        } else {
            document.getElementById(temp).style.display = "none";
        }
    }
    return false;
}

function edFill(from, to, value) {

    var selectFillName = document.getElementById(from);
    var fillOptionsSelect = selectFillName.selectedIndex;
    var destinationFill = document.getElementById(to);


    if( selectFillName.options[fillOptionsSelect].value != value ) {
        destinationFill.disabled = true;
    } else {
        destinationFill.disabled = false;
    }
    return false;
}

function edFills(from, to, to2, value) {
    var selectFillName = document.getElementById(from);
    var fillOptionsSelect = selectFillName.selectedIndex;
    var destinationFill = document.getElementById(to);
    var destinationFill2 = document.getElementById(to2);


    if( selectFillName.options[fillOptionsSelect].value == value ) {
        destinationFill.disabled = true;
        destinationFill2.disabled = true;
    } else {
        destinationFill.disabled = false;
        destinationFill2.disabled = false;
    }
    return false;
}

//funcion para habilitar y deshabiliar los campos de fianza en adquirente
function onchangeFianza() {
    var pre = "forma:";
    var exentarFianza = document.getElementById(pre + "exentDep");//checkbox exentar fianza
    var companiaFianza = document.getElementById(pre + "affiliation_depositcompany");//compa�ia fianza
    var autorizaExentar = document.getElementById(pre + "affiliation_officerdepositexent");//quien autoriza exentar
    var montoFianza = document.getElementById(pre + "affiliation_depositamount");//monto fianza
    var fechaExpFianza = document.getElementById(pre + "affiliation_duedate");//fecha expiracion
    var comboReqDep = document.getElementById("forma:affiliation_havedepositcompany"); //requiere fianza combo

    if(comboReqDep.value==0){//si no requiere fianza
    	exentarFianza.checked=false; exentarFianza.disabled=true; //se limpian valores y se deshabilitan
    	companiaFianza.disabled = true; companiaFianza.value="";
        autorizaExentar.disabled = true; autorizaExentar.value="";
        montoFianza.disabled = true; montoFianza.value="";
        fechaExpFianza.disabled = true; fechaExpFianza.value="";
    }else if(exentarFianza.checked==false){//si requiere fianza no esta marcado
    	exentarFianza.disabled=false;//se habilitan los campos
    	companiaFianza.disabled=false;
    	montoFianza.disabled = false;
    	fechaExpFianza.disabled = false;
    }
    return false;
}



function rfcByPersonType(from, to, value) {
    var selectFillName = document.getElementById(from);
    var fillOptionsSelect = selectFillName.selectedIndex;
    var destinationFill = document.getElementById(to);


    if( selectFillName.options[fillOptionsSelect].value != value ) {
        // Personal Fisica
        destinationFill.onblur = function() {
            exp = /^[a-zA-Z]{4}\d{6}\w{3}$/i;
            msg = 'Por favor capture el RFC correctamente.\n Ej. EDNI161178RE9';
            if ( !exp.test( destinationFill.value ) ) {
                if ( confirm ( msg ) ) {
                  destinationFill.focus();
                }
            }
            return true;
        };
        //destinationFill.setAttribute("maxlength", 13);
    } else {
        // Persona Moral
        destinationFill.onblur = function() {
            exp = /^[a-z&A-Z]{3}\s\d{6}\w{3}$/i  ;
            msg = 'Por favor capture el RFC correctamente.\n Ej. EDN 161178RE9';
            if ( !exp.test( destinationFill.value ) ) {
                if ( confirm ( msg ) ) {
                  destinationFill.focus();
                }
            }
            return true;
        };
        //destinationFill.setAttribute("maxlength", 12);
    }
    return false;
}

function checkemail(fill) {
    var fieldName= document.getElementById('forma:' + fill);
    emailfilter= /^\w+[\+\.\w-]*@([\w-]+\.)*\w+[\w-]*\.([a-z]{2,4}|\d+)$/i
    var returnval=emailfilter.test(fieldName.value)
    if (returnval==false){
      if(confirm("Por favor capture un email valido.\n Ej. esteban.garcia@banorte.com")) {
        fieldName.focus();
      }
    }

    return true;
}

function obBem(fill) {

	validateBEMNumber();
	
    return true;
}

function validateBEMNumber(){
	
	var isValid = true;
    var fieldName = document.getElementById('forma:bemnumber');
    
    bemNull =  /^\d+$/i
    var returnval = bemNull.test(fieldName.value);
    if (returnval==false){
        alert('El n�mero de BEM es obligatorio');
        fieldName.focus();
        
        isValid=false;
    }
    
    return isValid;
}


/*function isSpecialKey(evt) {
   var charCode = (evt.which) ? evt.which : evt.keyCode
   if (charCode > 32 && (charCode < 48 || charCode > 57) && (charCode < 65 || charCode > 90) && (charCode < 97 || charCode > 122))
      return false;

   return true;
}*/

function isNumberKey(evt) {
  validChar ="0123456789";
  flag = true;

  var charCode = (evt.which) ? evt.which : evt.keyCode;
  if ( charCode < 10 ) return true;
  var charValue = String.fromCharCode(charCode);

  for ( i = 0; i < validChar.length; i++ ) {
    if ( charValue == validChar.charAt(i) ) {
      break;
    }

    if ( i == (validChar.length-1) ) {
      flag = false;
    }
  }

  return flag;
}

function notEditable(evt) {
	  validChar =" ";
	  flag = true;

	  var charCode = (evt.which) ? evt.which : evt.keyCode;
	  if ( charCode < 10 ) return true;
	  var charValue = String.fromCharCode(charCode);

	  for ( i = 0; i < validChar.length; i++ ) {
	    if ( charValue == validChar.charAt(i) ) {
	      break;
	    }

	    if ( i == (validChar.length-1) ) {
	      flag = false;
	    }
	  }

	  return flag;
	}




function isDecimalKey(evt) {
   validChar ="0123456789.";
  flag = true;

  var charCode = (evt.which) ? evt.which : evt.keyCode;
  if ( charCode < 10 ) return true;
  var charValue = String.fromCharCode(charCode);

  for ( i = 0; i < validChar.length; i++ ) {
    if ( charValue == validChar.charAt(i) ) {
      break;
    }

    if ( i == (validChar.length-1) ) {
      flag = false;
    }
  }

  return flag;
}

function isDecimal() {
  return isDecimalKey(event);
}

//function fixDecimal(fill) {
//    var num = fillValue();
//    num.toFixed(2);
//}

function isText(evt) {
  validChar ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789., ";
  flag = true;

  var charCode = (evt.which) ? evt.which : evt.keyCode;
  if ( charCode < 10 ) return true;
  var charValue = String.fromCharCode(charCode);

  for ( i = 0; i < validChar.length; i++ ) {
    if ( charValue == validChar.charAt(i) ) {
      break;
    }

    if ( i == (validChar.length-1) ) {
      flag = false;
    }
  }

  return flag;
}


function isTextRazonSocial(evt) {
  validChar ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,&-_@+#' ";
  flag = true;

  var charCode = (evt.which) ? evt.which : evt.keyCode;
  if ( charCode < 10 ) return true;
  var charValue = String.fromCharCode(charCode);

  for ( i = 0; i < validChar.length; i++ ) {
    if ( charValue == validChar.charAt(i) ) {
      break;
    }

    if ( i == (validChar.length-1) ) {
      flag = false;
    }
  }

  return flag;
}

function isTextFRC(evt) {
  validChar ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789&";
  flag = true;

  var charCode = (evt.which) ? evt.which : evt.keyCode;
  if ( charCode < 10 ) return true;
  var charValue = String.fromCharCode(charCode);

  for ( i = 0; i < validChar.length; i++ ) {
    if ( charValue == validChar.charAt(i) ) {
      break;
    }

    if ( i == (validChar.length-1) ) {
      flag = false;
    }
  }

  return flag;
}

function isTextFRCAdq(evt) {
  validChar ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789& ";
  flag = true;

  var charCode = (evt.which) ? evt.which : evt.keyCode;
  if ( charCode < 10 ) return true;
  var charValue = String.fromCharCode(charCode);

  for ( i = 0; i < validChar.length; i++ ) {
    if ( charValue == validChar.charAt(i) ) {
      break;
    }

    if ( i == (validChar.length-1) ) {
      flag = false;
    }
  }

  return flag;
}

function isTextNON(evt) {
  validChar ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  flag = true;

  var charCode = (evt.which) ? evt.which : evt.keyCode;
  if ( charCode < 10 ) return true;
  var charValue = String.fromCharCode(charCode);

  for ( i = 0; i < validChar.length; i++ ) {
    if ( charValue == validChar.charAt(i) ) {
      break;
    }

    if ( i == (validChar.length-1) ) {
      flag = false;
    }
  }

  return flag;
}

function isTextNAN(evt) {
	  validChar ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
	  flag = true;

	  var charCode = (evt.which) ? evt.which : evt.keyCode;
	  if ( charCode < 10 ) return true;
	  var charValue = String.fromCharCode(charCode);

	  for ( i = 0; i < validChar.length; i++ ) {
	    if ( charValue == validChar.charAt(i) ) {
	      break;
	    }

	    if ( i == (validChar.length-1) ) {
	      flag = false;
	    }
	  }

	  return flag;
	}

function isEmail(evt) {
    validChar ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@-_.";
    flag = true;

    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ( charCode < 10 ) return true;
    var charValue = String.fromCharCode(charCode);

    for ( i = 0; i < validChar.length; i++ ) {
        if ( charValue == validChar.charAt(i) ) {
            break;
        }

        if ( i == (validChar.length-1) ) {
            flag = false;
        }
    }

    return flag;
}

function block(evet) {
    return false;
}

function disabled() {
    return false;
}

function enableDisable(fill, quant) {

  var fillValue = document.getElementById('forma:' + fill);
  var fillIndex = fillValue != null ? fillValue.selectedIndex : 1;
  fillValue = fillValue.options[fillIndex].value;

  var fills = new Array();
  if ( fill == 'quanttokens' ) {
    fills = new Array('serialtoken_', 'referencetoken_','emailtoken_');
  } else if ( fill == 'quantaccown' ) {
    fills = new Array('accownnum_', 'accownmerchantname_', 'accownname_');
  } else if ( fill == 'quantaccothers' ) {
      fills = new Array('accothersnum_', 'accothersname_', 'accothersofficername_', 'accotherscr_', 'accothersbranch_');
  } else if ( fill == 'quantofadminusers') {
      fills = new Array('clientcontact_name', 'clientcontact_lastname', 'clientcontact_mothersln', 'clientcontact_position', 'clientcontact_phone', 'clientcontact_phoneext', 'clientcontact_email');
  }

  enableDisableHelper ( fills, 1, quant, false );
  fillValue++;

  if ( parseInt(fillValue) > 0 ) {
      enableDisableHelper ( fills, fillValue, quant, true );
  }

  return true;
}

function enableDisableHelper(fills, length, quant, flag) {
    for ( var i = length; i <= quant; i++ ) {
        for ( var j = 0; j < fills.length; j++ ) {
            document.getElementById('forma:' + fills[j] + i).disabled = flag;
            if ( flag ) {
                document.getElementById('forma:' + fills[j] + i).value = "";
            }
        }
    }
    return true
}

function searchOption(fill, to) {
    var option = document.getElementById('forma:' + fill);
    var optionIndex = option.selectedIndex;
    var optionValue = option.options[optionIndex].value;
    var inputb = document.getElementById('forma:' + to);

    if ( optionValue == 'Folio' ) {
        inputb.setAttribute('maxlength', 10);
    } else if ( optionValue == 'SIC' ) {
        inputb.setAttribute('maxlength', 8);
    }

    return true;
}

function dateTime() {

    var monthArr = new Array("Enero", "Febrero", "Marzo", "Abril",
                            "Mayo", "Junio", "Julio", "Agosto",
                            "Septiembre", "Octubre", "Noviembre",
                            "Diciembre");

    var dayArr = new Array("Domingo", "Lunes", "Martes",
                            "Mi&eacute;rcoles", "Jueves", "Viernes",
                            "S&aacute;bado", "Domingo");

    var currentTime = new Date()
    var month = monthArr[currentTime.getMonth()];
    var daytext = dayArr[currentTime.getDay()];
    var day = currentTime.getDate();
    var year = currentTime.getFullYear();
    document.write(daytext + " " + day + " de " + month + " " + year);
}

function logOut() {
    delCookie('ObSSOCookie');
    window.location = '/contract';
}



function logOutOIP() {
    delCookie('ObSSOCookie');
}

function delCookie(name) {
  var today = new Date();
  var deleteDate = new Date(today.getTime() - 48 * 60 * 60 * 1000); // minus 2 days
  var cookie = name + "=; expires=" + deleteDate;
  document.cookie = cookie;
}


function validateGI() {
    var nom_1 = document.getElementById('forma:officerrepempnumber_1');
    var nom_2 = document.getElementById('forma:officerrepempnumber_2');

    if ( nom_1.value == nom_2.value ) {
      alert( 'Datos del representante legal o apoderado Banorte deben de ser diferentes, por favor corrija e intente nuevamente' );
      return false;
    } else {
      return true;
    }
}

/*  Division Nomina*/
function nominaStart(){
	var displayNumber  = document.getElementById('forma:commtraddispersal'); // Si tiene valor de un contrato ya existente no poner los defaults
	
	activateFill(1, 'forma:payrolltype', 'forma:bemnumber');
	
	if (displayNumber.value=="" || displayNumber.value==null){
		nomina();
	}
	comisionesTexto();
	validacionBancaSegmentoNomina();//Alta emisoras con calidad
}

function nomina() {
    var fill_opt = document.getElementById('forma:packagetype');
    var optionIndex = fill_opt.selectedIndex !=  null ? fill_opt.selectedIndex : 0;
    var optionValue = fill_opt.options[optionIndex].value;
    var fill_emp = document.getElementById('forma:quantemployees');

    comisionesTexto();
    //var msgBox = document.getElementById('comisiones');
    //var msg = '';

    if ( optionValue == 'Enlace Negocios Basica' ){
        //msg = 'Comisiones: Para la cuenta <b>Enlace Negocios Básica</b> se otorgan 50 transacciones tradicionales gratuitas, las adicionales y dispersiones en línea se cobran $5.50 Cada una.';
        employeePackage();
    } else if ( optionValue == 'Enlace Negocios Avanzada' ) {
        //msg = 'Comisiones: Para la cuenta <b>Enlace Negocios Avanzada </b> se otorgan transacciones tradicionales ilimitadas gratuitas, dispersiones en línea se cobran $5.50 Cada una.';
        employeePackage();
    } else {
        //msg = 'Comisiones';

        if ( fill_emp.value != '' ) {
          employeeSalary(fill_emp);
        }

        fill_emp.onblur = function() {
            employeeSalary(fill_emp);
        }
    }

    //msgBox.innerHTML = msg;

    return true;
}

function employeePackage() {
   var displayNumber  = document.getElementById('forma:commtraddispersal');
   var displayNumber2 = document.getElementById('forma:commdispersalanotherbank');
   var displayNumber3 = document.getElementById('forma:commonlinedispersal');
   var displayNumber4 = document.getElementById('forma:commfiletransmition');

   displayNumber.value = (0).toFixed(2);
   displayNumber2.value = (6).toFixed(2);
   displayNumber3.value = (5.50).toFixed(2); //4
   displayNumber4.value = (15).toFixed(2);

   displayNumber.onkeypress = disabled;
   displayNumber2.onkeypress = isDecimal;
   displayNumber2.onblur = function(){
    if ( displayNumber2.value < 6 ) {
      alert('La comisi\u00F3n por dispersion a otros bancos minima permitida es de $6.00 pesos, por favor corrija.');
      displayNumber2.focus();
    }
   };

   displayNumber3.onkeypress = disabled;
   displayNumber4.onkeypress = isDecimal;
   displayNumber4.onblur = function(){
    if ( displayNumber4.value < 15 ) {
      alert('La comisi\u00F3n por transmitir archivos en Sucursal minima permitida es de $15.00 pesos, por favor corrija.');
      displayNumber4.focus();
    }
   };
}

function employeeSalary(employeeNumber) {
   var displayNumber  = document.getElementById('forma:commtraddispersal');
   var displayNumber2 = document.getElementById('forma:commdispersalanotherbank');
   var displayNumber3 = document.getElementById('forma:commonlinedispersal');
   var displayNumber4 = document.getElementById('forma:commfiletransmition');

   var amount = 0.00;

   if ((employeeNumber.value > 0)) {
     amount = 3.50;
   }

   if ((employeeNumber.value > 30)) {
     amount = 3.00;
   }

   if ((employeeNumber.value > 100)) {
     amount = 2.00;
   }

   if ((employeeNumber.value > 500)) {
     amount = 1.00;
   }

   displayNumber.onkeypress = isDecimal;
   displayNumber.onblur = employeeSpecial;
   displayNumber2.onkeypress = isDecimal;
   displayNumber3.onkeypress = isDecimal;
   displayNumber2.onkeypress = isDecimal;

   displayNumber.value = amount.toFixed(2);
   displayNumber2.value = (6.00).toFixed(2);
   displayNumber3.value = (amount + 2).toFixed(2);
   displayNumber4.value = (15.00).toFixed(2);

  return true;
}

function employeeSpecial() {
   var displayNumber  = document.getElementById('forma:commtraddispersal');
   var displayNumber3 = document.getElementById('forma:commonlinedispersal');

   var amount = displayNumber.value != null ? parseFloat(displayNumber.value) : 0;

   displayNumber3.value = (amount + 2).toFixed(2);

  return true;
}

//funcion para esconder la liga de guardar
function hideShow(divHide, divShow){
	document.getElementById(divHide).style.visibility = 'hidden';
	show(divShow);
	return true;
}
//funcion para mostrar el mensaje de "enviando" en lo que acaba el proceso
function show(divShow){
	document.getElementById(divShow).style.visibility = 'visible';
	return true;
}



function hideDiv(){
	 document.getElementById('divGuardar').style.visibility = 'hidden';
	 showDiv();
	 return true;
}
function showDiv(){
  document.getElementById('divGuardando').style.visibility = 'visible';
	 return true;
}

function searchCities(controlId){
	
	var js = "if(typeof jsfcljs == 'function'){jsfcljs(document.forms['forma'],'forma:searchCities,forma:searchCities,anchorSearchCities,"+controlId+"','');}return true";
	
	// create a function from the "js" string
    var newclick = new Function(js);

	document.getElementById('forma:searchCities').setAttribute('onclick', newclick); // click();
	document.getElementById('forma:searchCities').click();

}

function validateNominaForm(){
	
	var bancaSegmento = document.getElementById("forma:bancaSegmentoNomina");
	var folioValidacion = document.getElementById("forma:folioValidacionNomina");
	var esFolioValido = document.getElementById("forma:esFolioValido");
	var isValidar = document.getElementById("forma:isValidarFolio");
	var searchFolio = document.getElementById("forma:searchFolioNomina");
	//alert("1-"+isFolioValido.value);
	//alert("9valor de esFolioValido: "+esFolioValido.value);
	//alert("33validete--> "+isValidar.value);
	 if (bancaSegmento.value == "pyme"){
		 if(folioValidacion.value==""||folioValidacion.value==null){
			 alert("Favor de capturar folio de validacion");
			 folioValidacion.focus();
			 return false;
		 }else{
			 //alert("isValidar.value: "+isValidar.value);
		 	if(isValidar.value=='true'){
		 		alert("Favor de validar folio, dando clic al boton");
		 		searchFolio.focus();
		 		return false;
		 	}else{		 		
		 		//alert(esFolioValido.value);
		 		//alert("esFolioValido:: "+esFolioValido.value);
		 		if(esFolioValido.value=="NO AUTORIZADA" || esFolioValido.value=="NO EXISTE FOLIO EN BASE DE DATOS"){
					alert("Revisar folio: "+esFolioValido.value);
					return false;
				}
		 	}
			
		 }

	 }
	 
	var isValid = true;
	
	var payrolltype = document.getElementById('forma:payrolltype');
	
	if (payrolltype.selectedIndex != 1){

		isValid = validateBEMNumber();
	}
	
	
	return isValid;
}

function validateStartMaintanceProcess(controlId){
	var isValid = false;
	var msj = "";

	var startDate = document.getElementById('forma:startMaintanceOperationDate');
	var processDays = document.getElementById('forma:processDays');
	var processControl = document.getElementById(controlId);
	
	if (startDate.value != "") {
		if (processDays.value != "" && processDays.value != '0'){
			if (confirm("Estas seguro de "+ processControl.outerText +". Procesando:"+processDays.value+" dias, con Fecha de Inicio:"+startDate.value+".")) {
				isValid = true;
			} 
		}else
				msj = "El numero de dias a procesar debe ser mayor a 0";
	}
	else
		msj = "Indicar la Fecha de Inicio para " + processControl.outerText;

	if (msj != "")
		alert(msj);

	return isValid;
}

function validateRangeDates(startDateControl, endDateControl, rangeDays){
	var startDateCtrl = document.getElementById(startDateControl);
	var endDateCtrl = document.getElementById(endDateControl);
	var msj ="";
	if (startDateCtrl.value != "") {
		if (endDateCtrl.value != "") {
			//Formato de la fecha: dd/MM/yyyy 
			var day = startDateCtrl.value.substring(0,2);
			var month = parseInt(startDateCtrl.value.substring(3,5)-1);
			var year = startDateCtrl.value.substring(6,10);	
			var startDate = new Date(year,month,day);			
			day = endDateCtrl.value.substring(0,2);
			month = parseInt(endDateCtrl.value.substring(3,5)-1);
			year = endDateCtrl.value.substring(6,10);	
			var endDate = new Date(year,month,day);			
			if (startDate > endDate){
				msj = "La Fecha Final debe ser mayor que la Fecha Inicial.";
			}else{
				if(undefined != rangeDays){
					if (Math.abs(datediff(startDate, endDate)) > rangeDays){
						msj = "El rango de fechas no puede ser mayor a " + rangeDays + " dias.";
					}
				}
			}
		}else
			msj = "La Fecha Final es Requerida.";
	}else
		msj = "La Fecha Inicial es Requerida.";

	if(msj!=""){
		alert(msj);
		return false;
	}
}

function datediff(startDate, endDate){
	var diff = startDate - endDate;
	return Math.floor( diff / 86400000);
}


function validateBitacora(inicio, fin){
	var inicio = document.getElementById(inicio);
	var fin = document.getElementById(fin);
	var folio = document.getElementById("forma:referenceNumber").value;
	if(folio==""){
		alert("Favor de introducir un numero de folio");
		return false;
	}
	validateRangeDates("forma:startBitacoraDate","forma:endBitacoraDate" );
}

/**
 * Valida si es posible ver la bitacora en pantalla o se debe generar el reporte en la carpeta de ftp
 */
function validateAction(){
	var startDateCtrl = document.getElementById("forma:startBitacoraDate");
	var endDateCtrl = document.getElementById("forma:endBitacoraDate");
	var bitacora = document.getElementById("forma:divBitacora");
	var bitacoraReport=document.getElementById("forma:divBitacoraReport");
			//Formato de la fecha: dd/MM/yyyy 
			var day = startDateCtrl.value.substring(0,2);
			var month = parseInt(startDateCtrl.value.substring(3,5)-1);
			var year = startDateCtrl.value.substring(6,10);	
			var maxDate = new Date(year,month,day);	
			maxDate.setDate(maxDate.getDate()+7);
			day = endDateCtrl.value.substring(0,2);
			month = parseInt(endDateCtrl.value.substring(3,5)-1);
			year = endDateCtrl.value.substring(6,10);	
			var endDate = new Date(year,month,day);			
			if (maxDate > endDate){
				bitacora.disabled=false;
				bitacoraReport.disabled=false;
			}else{//fecha final mayor a la fecha maxima... solo puede generar reporte
				bitacora.disabled=true;
				bitacoraReport.disabled=false;
			}
}

function loadOrdenBEM(){
	
	var pre ="forma:";
	var i=2;
	var index=0;
	var tokensCtrl = document.getElementById(pre + "quanttokens");
	var accountCtrl = document.getElementById(pre + "quantaccown");
	var tokens = tokensCtrl.options[tokensCtrl.selectedIndex].value;
	var accounts = accountCtrl.options[accountCtrl.selectedIndex].value;
	
	var serialCtrl=null;
	var referenceCtrl=null;
	var emailCtrl=null;
	var accownumCtrl=null;
	var regimeCtrl=null;
	var accownnameCtrl=null;
	
	 for (i=2 ; i<=11; i++){
		 
		 index = i+1;
         
		 if(i >= tokens){
			 serialCtrl = document.getElementById(pre + "serialtoken_" + index);
			 if(serialCtrl!=null)
				 serialCtrl.value ="";
			 
			 referenceCtrl=document.getElementById(pre + "referencetoken_" + index);
			 if(referenceCtrl!=null)
				 referenceCtrl.value ="";

			 emailCtrl=document.getElementById(pre + "emailtoken_" + index);
			 if(emailCtrl!=null)
				 emailCtrl.value ="";
         }
     }
	 
	 index=0;
	 for (i=1 ; i<=14; i++){
		 
		 index = i+1;
         
		 if(i >= accounts){
			 accownumCtrl= document.getElementById(pre + "accownnum_" + index);
			 if(accownumCtrl!=null)
				 accownumCtrl.value ="";
			 
			 regimeCtrl= document.getElementById(pre + "regimen_" + index);
			 if(regimeCtrl!=null)
				 regimeCtrl.selectedIndex=0;
			 
			 accownnameCtrl=document.getElementById(pre + "accownname_" + index);
			 if(accownnameCtrl!=null)
				 accownnameCtrl.value ="";
         }
     }
	
	/*var duplicatedTokensCtrl = document.forma.duplicatedTokens;
	
	if(duplicatedTokensCtrl != null){
		duplicatedTokensCtrl.value="false";
		//Validate duplicates in token section
		for (index = 1; index<=12;index++){
			if(!checkToken(index, false)){
				duplicatedTokensCtrl.value = "true";
				break;
			}
		}
	}*/
	if(validatePlan()){
		return true;
	}else{
		return false;
	}
}

function validatePlan(){
	var comboPlan=document.getElementById("forma:selectedbemplan");
	if(comboPlan.value=="0"){
		alert('Por favor seleccione un plan');
		comboPlan.focus();
		return false;
	}else{
		return true;
	}
}



//TODO: Quitar comentarios despues de la liberacion de Formatos BEM
//Metodo para validar que los No Serie de los tonkens no se repitan en el mismo contrato
//function checkToken(tokenId, singleValidation){
//	
//	var prefix = "forma:serialtoken_";
//	var token = document.getElementById(prefix + tokenId);
//	var tokenToCompare = null;
//	var result=true;
//	var index = 0;
//
//	if(token!=null){
//		if(trim(token.value)!=""){
//			for (index = 1; index<=12;index++){
//				if (index!= tokenId){
//					
//					tokenToCompare = document.getElementById(prefix + index);
//					
//					if(tokenToCompare!=null){
//						if(token.name != tokenToCompare.name && trim(tokenToCompare.value)!=""){
//							if(token.value == tokenToCompare.value){
//								if(singleValidation){
//									alert("Datos Contrataci�n Tokens - Serie de Token "+tokenId+" se encuentra duplicado en el Serie de Token "+ index);
//								}
//								result=false;
//								break;
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//	
//	
//	return result;
//}

/*function trim (str) {
	str = str.replace(/^\s+/, '');
	for (var i = str.length - 1; i >= 0; i--) {
		if (/\S/.test(str.charAt(i))) {
			str = str.substring(0, i + 1);
			break;
		}
	}
	return str;
}*/
/**Logout con ajax**/
var logoutWindow=null;
function logoutSimulador(){
	//Hacer el llamado al logout de Java para invalidar la sesion de la aplicacion.
	//BLOQUEAR PANTALLA por 15 segundos
	setTimeout(function(){
		var link=document.getElementById("_formLogout:linkLogout");
		link.click();//Disparo el logout de la pantalla
	},5000);
	logoutWindow=window.open("http://ssodes.unix.banorte.com/oam/server/logout?end_url=","_blank","width=10,height=10,top=0,left=0,resizable=no");
}

function dispararLogoutJava(){
	var link=document.getElementById("_formLogout:linkLogout");
	link.click();//Disparo el logout de la pantalla
	if(logoutWindow!=null){
		logoutWindow.close();
	}
}

function onchangeStateClientF(){
	document.getElementById('forma:SearchClientFCities').click(); 
}

function onchangeStateSucursal(){
	document.getElementById('forma:SearchSucursal').click(); 
}


function iniComboProductos(){
	var checkEnvio = document.getElementById("forma:procesoEnvio");
	var checkLectura = document.getElementById("forma:procesoLectura");
	var comboProductos = document.getElementById("forma:products");
	if(checkEnvio.checked==false){
		comboProductos.disabled=true;
		comboProductos.value=99;
	}else{
		comboProductos.disabled=false;
	}
}
function validaEjecutar(){
	var checkEnvio = document.getElementById("forma:procesoEnvio");
	var checkLectura = document.getElementById("forma:procesoLectura");
	var checkCargaCuentas = document.getElementById("forma:procesoCargaCuentasMujerPyME");
	var valid = false;
	if((checkEnvio.checked==false) && (checkLectura.checked==false ) && (checkCargaCuentas.checked==false )){
		alert('Elija un proceso por favor');
		return false;
	}
}
function confirmActivate(){
	var button=null;
	button=confirm('Confirmo que tengo el formato original de Recibo de Conformidad de Contraseñas BEM firmado por el cliente.');
	hideShow('divLink', 'divWait');
	if(!button){ //si oprimio cancelar
		show('divLink');
	}
	return button;
}
function confirmActivate2(){
	var button=null;
	button=confirm('Se enviará a Operaciones la solicitud de activación de cuentas. Confirmo que validé el formato de Recibo de Conformidad de Contraseñas BEM firmado por el Cliente.');
	hideShow('divLink', 'divWait');
	if(!button){ //si oprimio cancelar
		show('divLink');
	}
	return button;
}

function validSerialToken(campo){
	if(campo.value.length!=9){
		alert('El numero de serie debe ser de 9 caracteres');
		campo.focus();
	}
}

//function onchangeBancaSegmentoNomina(){//Alta emisoras con calidad
function validacionBancaSegmentoNomina(){//Alta emisoras con calidad
	 var bancaSegmento = document.getElementById("forma:bancaSegmentoNomina");
	 var folioValidacion = document.getElementById("forma:folioValidacionNomina");
	 //var searchFolio = document.getElementById("forma:searchFolioNomina");
	 var esFolioValido = document.getElementById("forma:esFolioValido");
	 var isValidar = document.getElementById("forma:isValidarFolio");

/*	 alert("1-esFolioValido==null: "+(esFolioValido==null));
	 alert("2-esFolioValido.value==null: "+(esFolioValido.value==null));
	 alert("3-esFolioValido.value==: "+(esFolioValido.value==""));//true
	 alert("4-esFolioValido.value==null: "+(esFolioValido.value==null));
*/
	 //if(esFolioValido.value!=""||esFolioValido.value!=null||esFolioValido!=null){
/*	 if(esFolioValido.value!=""){
		 alert("==>valor de esFolioValido: "+esFolioValido.value);
	 }else{
		 alert("valor nulo/vacio");
	 }*/
	 if (bancaSegmento.value == "pyme"){
		 folioValidacion.disabled = false;
		 //searchFolio.disabled = false;
		 //esFolioValido.value=true;
		 //alert("2-"+esFolioValido.value);
		 //if(esFolioValido.value!="" && esFolioValido.value=="NO AUTORIZADA"){
		 //alert("VALOR DE isValidar: "+isValidar.value);
		 //alert("VALOR DE esFolioValido.value: "+esFolioValido.value);
		 //if(esFolioValido.value!=""){
			// alert("Folio "+folioValidacion.value+": "+esFolioValido.value);
		 //}
	 }else{ //si la divisa es dolares o ambos
		folioValidacion.disabled = true;
		folioValidacion.value="";
		//searchFolio.disabled = true;
		//esFolioValido.value=false;
		 //alert("3-"+esFolioValido.value);
	    
	 }
	 

}

function onchangeFolioValidacion(){//Alta emisoras con calidad
	var isValidar = document.getElementById("forma:isValidarFolio");
	
	isValidar.value=true;
}

function comisionesTexto() {
	var fill_opt = document.getElementById('forma:packagetype');
    var optionIndex = fill_opt.selectedIndex !=  null ? fill_opt.selectedIndex : 0;
    var optionValue = fill_opt.options[optionIndex].value;
    var msgBox = document.getElementById('comisiones');
    var msg = '';

    if ( optionValue == 'Enlace Negocios Basica' ){
        msg = 'Comisiones: Para la cuenta <b>Enlace Negocios Básica</b> se otorgan 50 transacciones tradicionales gratuitas, las adicionales y dispersiones en línea se cobran $5.50 Cada una.';
    } else if ( optionValue == 'Enlace Negocios Avanzada' ) {
        msg = 'Comisiones: Para la cuenta <b>Enlace Negocios Avanzada </b> se otorgan transacciones tradicionales ilimitadas gratuitas, dispersiones en línea se cobran $5.50 Cada una.';
    } else {
        msg = 'Comisiones';

    }

    msgBox.innerHTML = msg;

}

/*function validateFolio(){
	var bancaSegmento = document.getElementById("forma:bancaSegmentoNomina");
	var folioValidacion = document.getElementById("forma:folioValidacionNomina");
	var esFolioValido = document.getElementById("forma:esFolioValido");
	var isValidar = document.getElementById("forma:isValidarFolio");
	var searchFolio = document.getElementById("forma:searchFolioNomina");
	//alert("1-"+isFolioValido.value);
	//alert("9valor de esFolioValido: "+esFolioValido.value);
	//alert("33validete--> "+isValidar.value);
	 if (bancaSegmento.value == "pyme"){
		 if(folioValidacion.value==""||folioValidacion.value==null){
			 alert("Favor de capturar folio de validacion");
			 folioValidacion.focus();
			 return false;
			 //bandera=false;
		 }else{
			 //alert("isValidar.value: "+isValidar.value);
		 	if(isValidar.value=='true'){
		 		alert("Favor de validar folio, dando clic al boton");
		 		searchFolio.focus();
		 		return false;
		 		//bandera=false;
		 	}else{		 		
		 		//alert(esFolioValido.value);
		 		//alert("esFolioValido:: "+esFolioValido.value);
		 		if(esFolioValido.value=="NO AUTORIZADA"){
					alert("Revisar folio: "+esFolioValido.value);
					return false;
					//bandera=false;
				}
		 	}
			
		 }

	 }
	 return bandera;
}
*/
function reValidarFolio(){
	var bancaSegmento = document.getElementById("forma:bancaSegmentoNomina");
	var folioValidacion = document.getElementById("forma:folioValidacionNomina");
	var esFolioValido = document.getElementById("forma:esFolioValido");
	var isValidar = document.getElementById("forma:isValidarFolio");
	var searchFolio = document.getElementById("forma:searchFolioNomina");
	alert("en JS metodo revalidar");
	var reValidarFolio = document.getElementById("forma:repetirValidarFolio");
    
    alert("b "+reValidarFolio.value);
   
    reValidarFolio.value=true;
    alert("b2. "+reValidarFolio.value);
    alert("2a. "+bancaSegmento.value);
    
	//alert("1-"+isFolioValido.value);
	//alert("9valor de esFolioValido: "+esFolioValido.value);
	//alert("33validete--> "+isValidar.value);
/*	 if (bancaSegmento.value == "pyme"){
		 if(folioValidacion.value==""||folioValidacion.value==null){
			 alert("Favor de capturar folio de validacion");
			 folioValidacion.focus();
			 return false;
			 //bandera=false;
		 }else{
			 //alert("isValidar.value: "+isValidar.value);
		 	if(isValidar.value=='true'){
		 		alert("Favor de validar folio, dando clic al boton");
		 		searchFolio.focus();
		 		return false;
		 		//bandera=false;
		 	}else{		 		
		 		//alert(esFolioValido.value);
		 		//alert("esFolioValido:: "+esFolioValido.value);
		 		if(esFolioValido.value=="NO AUTORIZADA"){
					alert("Revisar folio: "+esFolioValido.value);
					return false;
					//bandera=false;
				}
		 	}
			
		 }

	 }
	 return bandera;
*/
}


function mensajeFolioValidacion(){//Alta emisoras con calidad
	 var bancaSegmento = document.getElementById("forma:bancaSegmentoNomina");
	 var folioValidacion = document.getElementById("forma:folioValidacionNomina");
	 //var searchFolio = document.getElementById("forma:searchFolioNomina");
	 var esFolioValido = document.getElementById("forma:esFolioValido");
	 var isValidar = document.getElementById("forma:isValidarFolio");
	 var reValidarFolio = document.getElementById("forma:repetirValidarFolio");
     alert("bbb "+reValidarFolio.value);

/*	 alert("1-esFolioValido==null: "+(esFolioValido==null));
	 alert("2-esFolioValido.value==null: "+(esFolioValido.value==null));
	 alert("3-esFolioValido.value==: "+(esFolioValido.value==""));//true
	 alert("4-esFolioValido.value==null: "+(esFolioValido.value==null));
*/
	 //if(esFolioValido.value!=""||esFolioValido.value!=null||esFolioValido!=null){
/*	 if(esFolioValido.value!=""){
		 alert("==>valor de esFolioValido: "+esFolioValido.value);
	 }else{
		 alert("valor nulo/vacio");
	 }*/
	 if (bancaSegmento.value == "pyme"){
		 alert("dntro");
		 //folioValidacion.disabled = false;
		 
		 //searchFolio.disabled = false;
		 //esFolioValido.value=true;
		 //alert("2-"+esFolioValido.value);
		 //if(esFolioValido.value!="" && esFolioValido.value=="NO AUTORIZADA"){
		 //alert("VALOR DE isValidar: "+isValidar.value);
		 //alert("VALOR DE esFolioValido.value: "+esFolioValido.value);
		 alert("coment "+(esFolioValido.value!=""));
		 if(reValidarFolio.value=='false'){
			//alert("hola mundo");
			 if(esFolioValido.value!=""){
				 alert("Folio "+folioValidacion.value+": "+esFolioValido.value);
			 }
	 	}else{
	 		alert("patito");
	 		reValidarFolio=true;
	 	}
	 }else{ //si la divisa es dolares o ambos
		//folioValidacion.disabled = true;
		//folioValidacion.value="";
		
		 //searchFolio.disabled = true;
		//esFolioValido.value=false;
		 //alert("3-"+esFolioValido.value);
	    
	 }
	 

}
