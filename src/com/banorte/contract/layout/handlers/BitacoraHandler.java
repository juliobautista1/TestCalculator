package com.banorte.contract.layout.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.banorte.contract.business.EmployeeRemote;
import com.banorte.contract.model.Bitacora;
import com.banorte.contract.model.Employee;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.web.EjbInstanceManager;

public class BitacoraHandler extends RowHandler<Bitacora>{
	public BitacoraHandler(){
		if (employeeBean == null) {
			employeeBean = (EmployeeRemote) EjbInstanceManager.getEJB(EmployeeRemote.class);
		}
	}
	private static Logger log = Logger.getLogger(BitacoraHandler.class.getName());
	private Formatter format = new Formatter();
	public EmployeeRemote employeeBean;
	public String[]headers={
			"Fecha",
			"Descripcion",
			"Folio",
			"Versión",
			"SIC",
			"Producto",
			"Tipo",
			"Fecha finalizacion",
			"Numero Usuario",
			"Nombre Usuario",
			"Suc Adm",
			"Clave puesto",
			"Puesto",
			"Numero CR",
			"CR",
			"email",
			"Fecha ingreso",
			"Region",
			"Territorial"
			};
	
	@Override
	public List<String> getHeaders() {
		return Arrays.asList(headers);
	}

	@Override
	public String getLayoutName() {
		return "Bitacora";
	}

	@Override
	public List<String> castObject(Bitacora obj) {
		List<String> row= new ArrayList<String>();
		if(obj!=null){
			Employee employee=lookEmployee(obj.getUserNumber());//obtiene el empleado
			String fecha=obj.getCreationDate()!=null?format.formatDateToString(obj.getCreationDate()):"";
			String desc=obj.getDescription();
			String folio=obj.getFolio();
			String version=obj.getFolioVersion();
			String sic=obj.getSic();
			String prod=obj.getProduct();
			String tipo=obj.getProducttype();
			String fechaFin=obj.getEndDate()!=null?format.formatDateToString(obj.getEndDate()):"";
			String numUsuario=obj.getUserNumber();
			row.add(fecha);
			row.add(desc);
			row.add(folio);
			row.add(version);
			row.add(sic);
			row.add(prod);
			row.add(tipo);
			row.add(fechaFin);
			row.add(numUsuario);
			if (employee!=null){
				String nombreUsu=employee.getName();
				String sucAdm=employee.getSucAdm();
				String clave=employee.getClavePuesto();
				String puesto=employee.getPosition();
				String numCr=employee.getNumCr();
				String cr=employee.getCr();
				String email=employee.getEmail();
				String fechaIngreso=employee.getFechaIngreso()!=null?employee.getFechaIngreso().toString():"";
				String region=employee.getRegion();
				String territorial=employee.getTerritorio();

				row.add(nombreUsu);
				row.add(sucAdm);
				row.add(clave);
				row.add(puesto);
				row.add(numCr);
				row.add(cr);
				row.add(email);
				row.add(fechaIngreso);
				row.add(region);
				row.add(territorial);
			}else{
				row.add("");
				row.add("");
				row.add("");
				row.add("");
				row.add("");
				row.add("");
				row.add("");
				row.add("");
				row.add("");
				row.add("");				
			}
		}
		return row;
	}
	
	public Employee lookEmployee(String numEmp){
		int found = 0;
		Map<String, Employee> mapaEmpleados = new HashMap<String, Employee>();
		Employee emp = new Employee();
		if (numEmp != null) {
			numEmp = numEmp.replaceFirst("A", "");
			for (String numero : mapaEmpleados.keySet()) { //busca en el mapa de empleados
				if (numEmp.equalsIgnoreCase(numero)) {
					found = 1;
				}
			}
			if (found != 1) {// si no esta en el mapa, busca en la BD
				emp = employeeBean.findByNumEmpleado(numEmp);
				if (emp != null) {
					mapaEmpleados.put(numEmp, emp);
				}
			}
			if (emp != null) {
				return emp;
			} 
		}
		return null;
	}
	
	
}
