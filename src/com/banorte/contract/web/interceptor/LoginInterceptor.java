/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banorte.contract.web.interceptor;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banorte.contract.business.BitacoraRemote;
import com.banorte.contract.business.EmployeeRemote;
import com.banorte.contract.business.UserProfileRemote;
import com.banorte.contract.model.Employee;
import com.banorte.contract.model.UserProfile;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.BitacoraType;
import com.banorte.contract.util.BitacoraUtil;
import com.banorte.contract.web.EjbInstanceManager;

/**
 *
 * @author Carlos Trevi√±o
 */

public class LoginInterceptor implements PhaseListener {
	UserProfileRemote userProfileBean;
	BitacoraRemote bitacoraBean;
	EmployeeRemote employeeBean;

    public void afterPhase(PhaseEvent event) {
    	String role 		= ApplicationConstants.EMPTY_STRING;
        String uid 			= ApplicationConstants.EMPTY_STRING;
        String uname		= ApplicationConstants.EMPTY_STRING;
        String title 		= ApplicationConstants.EMPTY_STRING;
        String roleOblix 	= ApplicationConstants.EMPTY_STRING;
        String userProfile = "";
        String estado="";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) (facesContext.getExternalContext().getSession(true));
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Map<String, String> userSession = null;

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    	String cookie = "mycookie=JSESSIONID; Secure; HttpOnly";
        response.addHeader("Set-Cookie", cookie);
    	response.addHeader("X-XSS-Protection", "1; mode=block");
    	response.addHeader("X-Content-Type-Options", "nosniff");
    	response.addHeader("X-Frame-Options", "SAMEORIGIN");
                
        if (session.getAttribute((ApplicationConstants.PROFILE)) != null) {
            userSession = (HashMap) session.getAttribute(ApplicationConstants.PROFILE);
        }
        if (userSession == null) {
            if (request.getHeader(ApplicationConstants.ROLE) == null) {
           	
//           	role = "ADMIN"; 
//         uid = "HRITM780"; //HRITM780 / A8027553 / A8024462 / hritm780 / Carlos Eduardo (A8024462)
//           uname = "Marcela Rios";//"Miguel Borbolla"
//       title = "Title";
//          roleOblix = "Staff";
//          userProfile="soporte"; 
//          estado=buscarEstado(uid); 
            	
            	//System.out.println("DENTRO --------____________------------------");
             // /* comente para coformalizar
                role = "ADMIN"; 
                uid = "hmedg870"; //HRITM780 / A8027553 / A8024462 / hritm780 / Carlos Eduardo (A8024462)
                uname = "David Vizcaya";//"Marcela Rios"
                title = "Title";
                roleOblix = "Staff"; // Staff
                userProfile="soporte"; 
                estado=buscarEstado(uid); 
             // */
                String profile = "solicitante";
                if(session.getAttribute("userprofile")==null){
                	profile = userProfile(uid);
                }
                userProfile=profile;
                session.setAttribute("userprofile", profile);
            } else {
                role = request.getHeader(ApplicationConstants.ROLE);
                uid = request.getHeader(ApplicationConstants.UID);
                uname = request.getHeader(ApplicationConstants.UNAME);
                title = request.getHeader(ApplicationConstants.TITLE);
                if(uid.equalsIgnoreCase("hritm780") ||
                		uid.equalsIgnoreCase("hmedg870") ||
                		uid.equalsIgnoreCase("A8027553") ||
                		uid.equalsIgnoreCase("HROPV851") ||
                		uid.equalsIgnoreCase("A3936864") ||//Mayra Cavazos
                		uid.equalsIgnoreCase("A3556867") ||//Israel
                		uid.equalsIgnoreCase("A3991695") ||//Mauricio Miron
                		uid.equalsIgnoreCase("A3915336") ||//GUILLERMO ROSALES JARAMILLO
                		uid.equalsIgnoreCase("A3059634") ||//Jose de Jesus Barrios Martinez
                		uid.equalsIgnoreCase("A3802809") ||//Edmundo Guajardo Guajardo
                		uid.equalsIgnoreCase("A0062113") ||//Elsa Ortiz Gonzalez
                		uid.equalsIgnoreCase("A8027649") ||//Pamela Saucedo
                		uid.equalsIgnoreCase("A3652963") ||// Francisco Tovar
                		uid.equalsIgnoreCase("A8024462") ||// Carlos Eduardo Gonzalez Araujo
                		uid.equalsIgnoreCase("A3861872") ||// JESUS ENRIQUE MU—IZ RAMIREZ
                		uid.equalsIgnoreCase("A3838323") ||// MONICA SELENE GARCIA CAVAZOS
                		uid.equalsIgnoreCase("A8030640") // GENESIS ESTEFANIA CAVAZOS GARCIA
                		){ 
                	roleOblix =  "Staff" ;
                	userProfile="Soporte";//para perfiles
                }else{
                	roleOblix =  "Admin" ;
                }
                estado=buscarEstado(uid); 
            }
            
            if (role != null && uid != null && uname != null) {
                userSession = new HashMap<String, String>();
                userSession.put(ApplicationConstants.ROLE, role);
                userSession.put(ApplicationConstants.UID, uid);
                userSession.put(ApplicationConstants.UNAME, uname);
                userSession.put(ApplicationConstants.TITLE, title);
                userSession.put(ApplicationConstants.ROLE_OBLIX,roleOblix);
                userSession.put("userprofile", userProfile);
                userSession.put("estado", estado);
                session.setAttribute(ApplicationConstants.PROFILE, userSession);
                /*for (Map.Entry<String, String> entry : userSession.entrySet()) {
                    System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
                }
                mostrarInfo();*/
            } else {
                //Enviamos a pagina de welcome
            	BitacoraUtil.getInstance().saveBitacoraGeneral(BitacoraType.LOGIN, uid);            	
                facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, "welcome");
                //mostrarInfo();
            }
            BitacoraUtil.getInstance().saveBitacoraGeneral(BitacoraType.LOGIN, uid);
        }
        
        
    }

    

    public void beforePhase(PhaseEvent event) {
    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
    
    

    public String buscarEstado(String uid){
    	String estado=null;
    	if(uid.equalsIgnoreCase("hritm780")){
    		estado="SAN LUIS POTOSI";
    	}else{
    		if (employeeBean == null) {
    			employeeBean = (EmployeeRemote) EjbInstanceManager.getEJB(EmployeeRemote.class);
    		}
        	
        	Employee employee = new Employee();
        	String numEmp = uid.replaceFirst("A", "");
        	employee=employeeBean.findByNumEmpleado(numEmp);
//        	System.out.println(numEmp);
        	if(employee!=null){
        		estado=employee.getEstado();
        	}
    	}
    	return estado;
    }
    
    
	public void terminar(ActionEvent evt){
		FacesContext ctx = FacesContext.getCurrentInstance();	
		ExternalContext eCtx = ctx.getExternalContext();
		HttpSession sesion = (HttpSession) eCtx.getSession(false);
		sesion.setAttribute("logout", "1");
		redireccionar(eCtx);
	}
	
	private void redireccionar(ExternalContext eCtx) {
		try {
			eCtx.redirect("logout.jsp");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void mostrarInfo(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) (facesContext.getExternalContext().getSession(true));
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Enumeration<String> atributes= session.getAttributeNames();
       
        System.out.println("==========Valores de la sesion==========");
        while(atributes.hasMoreElements()){
        	String atributo=atributes.nextElement();
        	Object valor=session.getAttribute(atributo);
        	String val=(valor!=null)?valor.toString():"";
        	System.out.println("Atributo:"+atributo+"|Valor:"+val);
        }
        System.out.println("==========Valores del header==========");
        Enumeration<String> headers=request.getHeaderNames();
        while(headers.hasMoreElements()){
        	String header=headers.nextElement();
        	String valor=request.getHeader(header);
        	System.out.println("Header:"+header+"|Valor:"+valor);
        }
	}
	
/*	private void limpiarCookies(ExternalContext eCtx){
		HttpServletRequest request=(HttpServletRequest)eCtx.getRequest();
		HttpServletResponse response=(HttpServletResponse)eCtx.getResponse();
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length>0){
			for(Cookie cookie:cookies){
				String nombre=cookie.getName();
				//Si el cookie es igual al de oblix, entonces se remueve los datos del cookie y se agrega al response de la peticion.
				if("ObSSOCookie".equalsIgnoreCase(nombre)){
					cookie.setValue(null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					break;
				}
			}
		}
	} */
	
////inicio 
//  Object log=session.getAttribute("logout");
//  boolean salir=(log!=null && !log.toString().isEmpty() && "1".equals(log.toString()));
////  System.out.println("Es salir:"+salir);
////  mostrarInfo();
//  if(salir){
//		session.invalidate();
////		facesContext.responseComplete();
//		return;
//  }
////fin 
	private String userProfile(String empNum){
		UserProfile profile = new UserProfile();
		if (userProfileBean == null) {
			userProfileBean = (UserProfileRemote) EjbInstanceManager.getEJB(UserProfileRemote.class);
		}
		profile = userProfileBean.findProfile(empNum);
		return profile.getProfileDesc();
	}

}
