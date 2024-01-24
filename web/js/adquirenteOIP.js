/**
 * funciones JavaScript para la pagina de SIP
 */


			function startOIP() {
			    edFill('forma:affiliation_servicetype','forma:affiliation_currentbank', 'Cambio de Banco Adquirente');
			    edFill('forma:affiliation_servicetype','forma:affiliation_numaffilmn','Cambio de Banco Adquirente');
			    if(document.getElementById("forma:affiliation_servicetype").value=="Alta"){
			    	document.getElementById("forma:affiliation_numaffilmn").disabled="true";
			    }
			    Calendar.setup({ inputField: "forma:affiliation_duedate", ifFormat: "%d/%m/%Y"});
			    document.getElementById("forma:red").value=0;
			    document.getElementById("forma:isOIP").value="true";
			    document.getElementById("forma:affiliation_soluciontype").value="Internet";
			    document.getElementById("forma:affiliation_productdesc").value="Terminal punto de venta";
			    document.getElementById("forma:affiliation_modality").value="Unicaja";
			    document.getElementById("forma:affiliation_currency").value="Pesos";
			    document.getElementById("forma:commchargetype").value="Porcentaje";
			    recalculateCommisionTableOIP();
			    inicializarEquiposOip();
			}

			
			function getAdquirerPesosCtrlsArrayOIP(){
				return new Array("affiliation_otherconcept1des","avcommisiontcmnComplete","avcommisiontdmnComplete",
				        "affiliation_transcriptorratemn", "affiliation_monthlyratemn",
				        "affiliation_monthlyinvoicingminmn", "affiliation_failmonthlyinvoicingmn",
				        "affiliation_minimiunbalancemn", "affiliation_failminimiunbalancemn", 
				        "affiliation_promnotemn", "affiliation_failpromnotemn", "affiliation_avsmn",
				        "affiliation_activation3dsmn", "affiliation_monthlyrate3dsmn", "affiliation_otherconcept1mn"
				        );
			}

			
			function getAdquirerDollarCtrlsArrayOIP(){
				return new Array("affiliation_otherconcept1des","avcommisiontcdllsComplete","avcommisiontddllsComplete",
				        "affiliation_transcriptorratedlls","affiliation_monthlyratedlls", "affiliation_affiliationratedlls",
				        "affiliation_monthlyinvoicingmindlls","affiliation_failmonthlyinvoicingdlls", "affiliation_minimiunbalancedlls", "affiliation_failminimiunbalancedlls",
				        "affiliation_promnotedlls", "affiliation_failpormnotedlls", "affiliation_avsdlls", "affiliation_activation3dsdlls",
				        "affiliation_monthlyrate3dsdlls", "affiliation_otherconcept1dlls"
				        );
			}

			
			//function setCommisionTable(disableAll,currencyType){
			function setCommisionTableOIP(){
			    var pre = "forma:";
			    var currencyType ="Pesos";
			    
				pesos = getAdquirerPesosCtrlsArrayOIP();
				dolares = getAdquirerDollarCtrlsArrayOIP();
			  
			    var comercialPlan = document.getElementById(pre + "affiliation_comercialplan");
			    var comercialPlanSelected = comercialPlan[comercialPlan.selectedIndex].value;
			    
			    if(comercialPlanSelected!=5 && comercialPlanSelected!= 6){
			    	document.getElementById(pre + "commchargetype").disabled = true;
			    	document.getElementById(pre + "affiliation_otherconcept1des").disabled = true;
			
					for (i = 0; i < dolares.length; i++) {
						document.getElementById(pre + dolares[i]).disabled = true;
					}
					for (i = 0; i < pesos.length; i++) {
						document.getElementById(pre + pesos[i]).disabled = true;
					}
				} else {
						if (currencyType == "Pesos") {
							for (i = 0; i < dolares.length; i++) {
								document.getElementById(pre + dolares[i]).value = "";
								document.getElementById(pre + dolares[i]).disabled = true;
							}
							for (i = 0; i < pesos.length; i++) {
								document.getElementById(pre + pesos[i]).disabled = false;
							}
						} else {
					
							for (i = 0; i < dolares.length; i++) {
								document.getElementById(pre + dolares[i]).disabled = false;
							}
							for (i = 0; i < pesos.length; i++) {
								document.getElementById(pre + pesos[i]).disabled = false;
							}
						}
				}
			    inicializarCuotaAfiliacionOip();
			}
			
			
			/**
			 *Funcion para poner por default 0 en pinpad y deshabilitarlo 
			 */
			function inicializarEquiposOip(){
				var pinpad = document.getElementById("forma:affiliation_quantpinpad");
				pinpad.value="0";
				pinpad.disabled=true;
			}
			
			
			/**
			 * Funcion para poner el default de $300 pesos de cuota de afiliacion 
			 */
			function inicializarCuotaAfiliacionOip(){
				var cuotaPesos = document.getElementById("forma:affiliation_affiliationratemn");
				if(cuotaPesos.value=="" || cuotaPesos.value==null){
					cuotaPesos.value='300';
				}
			}

			function recalculateCommisionTableOIP(){
				setCommisionTableOIP();
				var comercialPlan = document.getElementById("forma:affiliation_comercialplan");
				var otherPlan = document.getElementById("forma:otherCommercialPlan");
				var comercialPlanSelected = comercialPlan[comercialPlan.selectedIndex].value;	
			
				if(otherPlan!=null){
					if(comercialPlanSelected == 6)//OTRO
					{
						otherPlan.disabled =false;
					}else{
						otherPlan.value ="";
						otherPlan.disabled =true;
					}
				} 
				document.getElementById("forma:recalculateCommisionTable").value = true;
			}


			function validateEquipmentOIP(){
				//nuevos equipos contato unico IXE
				var tpvTel = document.getElementById("forma:tpvTel").value;
				var tpvMovil = document.getElementById("forma:tpvMovil").value;
				var tpvInternet = document.getElementById("forma:tpvInternet").value;
				var tpvInternetTel = document.getElementById("forma:tpvInternetTel").value;
				var tpvBlueTel = document.getElementById("forma:tpvBlueTel").value;
				var tpvGprs = document.getElementById("forma:tpvGprs").value;
				var tpvBlueInternet = document.getElementById("forma:tpvBlueInternet").value;
				var wifiMovil = document.getElementById("forma:wifiMovil").value;
				var wifiTel = document.getElementById("forma:wifiTel").value;
				var pinpad = document.getElementById("forma:affiliation_quantpinpad").value;
				
				if(validarEnvioOIP()==false){
					return false;
				}
				
				if(tpvTel+tpvMovil+tpvInternet+tpvInternetTel+tpvBlueTel+tpvGprs+tpvBlueInternet+wifiMovil+wifiTel+pinpad==0){
					alert("Se requiere seleccionar al menos un Modelo de Equipo");
					return false;
				}
				return true;
			}



			/**
             * Funcion que establece si se requiere o no fianza en base al giro
             * llama a onchangeFianza
             */
            function inicializarFianzaOIP(){
           	 var comboRequiereFianza = document.getElementById("forma:affiliation_havedepositcompany"); //requiere fianza combo
           	 var checkboxExentar = document.getElementById("forma:exentDep");//exenta fianza checkbox
           	 var giro = document.getElementById("forma:client_categorycode").value;//giro
           	 var numGiro = giro.substring(0,6);//numero de giro
           	 var altoRiesgo = false;
           	 var comboBuroInterno = document.getElementById("forma:affiliation_internalcredithistory");
           	 var comboBuroExterno = document.getElementById("forma:affiliation_externalcredithistory");
           	 
           	 if(numGiro == "004812" || numGiro == "005045" || numGiro =="005813"|| 
           			 numGiro == "005969" || numGiro == "007995" || numGiro =="005970")
           	 {
           		 altoRiesgo=true;
           	 }
           	if(comboBuroInterno.value=="Malo" || comboBuroExterno.value=="Malo"){
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
           	 onchangeFianzaOIP();
            }
            
			
            /**
             * es llamada por inicializarFianza
             * Funcion que habilita/deshabilita los campos de fianza (monto, exentar, autoriza, expiracion)en base a si requiere o no la fianza
             * @returns {Boolean}
             */
            function onchangeFianzaOIP() {
           	    var checkboxExentarFianza = document.getElementById("forma:exentDep");
           	    var valorDelExentar = document.getElementById("forma:exentDep").value;
           	    var campoCiaFianza = document.getElementById("forma:affiliation_depositcompany");
           	    var campoAutorizaExentar = document.getElementById("forma:affiliation_officerdepositexent");
           	    var campoMontoFianza = document.getElementById("forma:affiliation_depositamount");
           	    var campoFechaExpFianza = document.getElementById("forma:affiliation_duedate");
           	    var comboReqFianza = document.getElementById("forma:affiliation_havedepositcompany");

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
           	    	campoAutorizaExentar.value="";
           	    }else if(checkboxExentarFianza.checked==true){ //cuando "SI" exenta
           	    	campoCiaFianza.disabled=true;
           	    	campoMontoFianza.disabled = true;
           	    	campoFechaExpFianza.disabled = true;
           	    }
           	    return false;
           }
            
            
            /**
             * Funcion que habilita/deshabilita los campos de fianza si se quiere exentar o no
             */
            function onchangeExentDepositOIP(){
           	 var exentar = document.getElementById("forma:exentDep");//exenta fianza checkbox
           	 var auth = document.getElementById("forma:affiliation_officerdepositexent");//quien autoriza exentar
           	 var company = document.getElementById("forma:affiliation_depositcompany");//compañia fianza
           	 var amount = document.getElementById("forma:affiliation_depositamount");//monto fianza
           	 var dueDate = document.getElementById("forma:affiliation_duedate");//fecha expiracion
           	 
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
           		 
           	 }
            }
            
            function validarEnvioOIP(){
            	var plan = document.getElementById("forma:affiliation_comercialplan");
            	if(plan.value==0){
            		alert('Debe elegir un Plan o Esquema Comercial');
            		return false;
            	}
            	return true;
            }
