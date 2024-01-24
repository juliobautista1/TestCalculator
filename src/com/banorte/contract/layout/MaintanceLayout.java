package com.banorte.contract.layout;

import com.banorte.contract.business.MaintanceBean;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.DirectaLoad;
import com.banorte.contract.model.Maintance;
import com.banorte.contract.model.StatusDirecta;
import com.banorte.contract.util.DirectaStatus;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.Formatter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Joseles Sanchez
 */
public class MaintanceLayout extends LayoutOperationsTemplate implements Layout {

    static Logger logger = Logger.getLogger(MaintanceLayout.class.getName());

	private final String FILLS[] = { "client_sic", "client_fiscalname",
			"crnumber", "branchname", "legalrepresentative_nameComplete1",
			"clientcontact_nameComplete1", "clientcontact_nameComplete2",
			"clientcontact_nameComplete3", "clientcontact_phone1",
			"clientcontact_phone2", "clientcontact_phoneext1",
			"clientcontact_phoneext2", "clientcontact_phone3",
			"clientcontact_phoneext3", "clientcontact_email1",
			"clientcontact_email2", "clientcontact_email3", "accownnum_1",
			"accownnum_2", "accownnum_3", "accownnum_4", "accownnum_5",
			"accownnum_6", "accownnum_7", "accownnum_8", "accownnum_9",
			"accownnum_10", "accownnum_11", "accownnum_12", "accownnum_13",
			"accownnum_14", "accownnum_15", "client_areacode" };

	/**
	 * Constructor en base a una lista de contratos(tipo de
	 * mantenimiento:"Alta")
	 * 
	 * @param contracts
	 */
	public MaintanceLayout(List<Contract> contracts) {
		for (Contract contract : contracts) {
			Maintance entity = this.parseContract(contract);

			this.setMaintance(entity);

		}
	}

	public MaintanceLayout() {

	}

	public boolean hasElements() {

		if (this.getMaintances().size() > 0) {
			return true;
		} else
			return false;

	}

	private Maintance parseContract(Contract contract) {

		Maintance entity = new Maintance();
		Map<String, String> map = new LayoutTempleteContract().bindFrom(
				contract, FILLS);

		String accountNumber1 = "";
		String accountNumber2 = "";
		String accountNumber3 = "";
		String accountNumber4 = "";
		String accountNumber5 = "";
		String accountNumber6 = "";
		String accountNumber7 = "";
		String accountNumber8 = "";
		String accountNumber9 = "";
		String accountNumber10 = "";
		String accountNumber11 = "";
		String accountNumber12 = "";
		String accountNumber13 = "";
		String accountNumber14 = "";
		String accountNumber15 = "";
		String phoneNumber1 = "";
		String phoneNumber2 = "";
		int sic = 0;

		// Desencriptando cuentas propias
		if (map.get("accownnum_1") != null
				&& map.get("accownnum_1").trim().length() > 0) {
			accountNumber1 = map.get("accownnum_1");
		}
		if (map.get("accownnum_2") != null
				&& map.get("accownnum_2").trim().length() > 0) {
			accountNumber2 = map.get("accownnum_2");
		}
		if (map.get("accownnum_3") != null
				&& map.get("accownnum_3").trim().length() > 0) {
			accountNumber3 = map.get("accownnum_3");
		}
		if (map.get("accownnum_4") != null
				&& map.get("accownnum_4").trim().length() > 0) {
			accountNumber4 = map.get("accownnum_4");
		}
		if (map.get("accownnum_5") != null
				&& map.get("accownnum_5").trim().length() > 0) {
			accountNumber5 = map.get("accownnum_5");
		}
		if (map.get("accownnum_6") != null
				&& map.get("accownnum_6").trim().length() > 0) {
			accountNumber6 = map.get("accownnum_6");
		}
		if (map.get("accownnum_7") != null
				&& map.get("accownnum_7").trim().length() > 0) {
			accountNumber7 = map.get("accownnum_7");
		}
		if (map.get("accownnum_8") != null
				&& map.get("accownnum_8").trim().length() > 0) {
			accountNumber8 = map.get("accownnum_8");
		}
		if (map.get("accownnum_9") != null
				&& map.get("accownnum_9").trim().length() > 0) {
			accountNumber9 = map.get("accownnum_9");
		}
		if (map.get("accownnum_10") != null
				&& map.get("accownnum_10").trim().length() > 0) {
			accountNumber10 = map.get("accownnum_10");
		}
		if (map.get("accownnum_11") != null
				&& map.get("accownnum_11").trim().length() > 0) {
			accountNumber11 = map.get("accownnum_11");
		}
		if (map.get("accownnum_12") != null
				&& map.get("accownnum_12").trim().length() > 0) {
			accountNumber12 = map.get("accownnum_12");
		}
		if (map.get("accownnum_13") != null
				&& map.get("accownnum_13").trim().length() > 0) {
			accountNumber13 = map.get("accownnum_13");
		}
		if (map.get("accownnum_14") != null
				&& map.get("accownnum_14").trim().length() > 0) {
			accountNumber14 = map.get("accownnum_14");
		}
		if (map.get("accownnum_15") != null
				&& map.get("accownnum_15").trim().length() > 0) {
			accountNumber15 = map.get("accownnum_15");
		}

		if (map.get("clientcontact_phone1") != null
				&& map.get("clientcontact_phone1").trim().length() > 0) {
			phoneNumber1 = map.get("client_areacode") == null?"":map.get("client_areacode").trim() 
					+ map.get("clientcontact_phone1").trim() + " ext."
					+ map.get("clientcontact_phoneext1");
		}
		if (map.get("clientcontact_phone2") != null
				&& map.get("clientcontact_phone2").trim().length() > 0) {
			phoneNumber2 = map.get("client_areacode") == null?"":map.get("client_areacode").trim() 
					+  map.get("clientcontact_phone2").trim() + " ext."
					+ map.get("clientcontact_phoneext2");
		}
		if (map.get("client_sic") != null
				&& map.get("client_sic").trim().length() > 0) {
			sic = Formatter.isNumeric(map.get("client_sic")) ? Integer
					.parseInt(map.get("client_sic")) : 0;
		}

		// Set maintance entity
		entity.setMerchantNumber(Formatter.fixLenght(contract.getMerchantNumber(),8));
		entity.setContractId(contract.getContractId());
		entity.setCompanyName(map.get("client_fiscalname"));
		entity.setMaintanceType("ALTA");
		entity.setLegalRepresentative(map.get("legalrepresentative_nameComplete1"));
		entity.setManager1(map.get("clientcontact_nameComplete1"));
		entity.setManager2(map.get("clientcontact_nameComplete2"));
		entity.setManager3(map.get("clientcontact_nameComplete3"));
		entity.setCR(Formatter.fixLenght(map.get("crnumber"),5));
		entity.setBranchName(map.get("branchname"));
		entity.setSic(sic);
		entity.setPhoneNumber1(phoneNumber1);
		entity.setPhoneNumber2(phoneNumber2);
		entity.setEmail1(map.get("clientcontact_email1"));
		entity.setEmail2(map.get("clientcontact_email2"));
		entity.setEmail3(map.get("clientcontact_email3"));
		entity.setAccountNumber1(accountNumber1);
		entity.setAccountNumber2(accountNumber2);
		entity.setAccountNumber3(accountNumber3);
		entity.setAccountNumber4(accountNumber4);
		entity.setAccountNumber5(accountNumber5);
		entity.setAccountNumber6(accountNumber6);
		entity.setAccountNumber7(accountNumber7);
		entity.setAccountNumber8(accountNumber8);
		entity.setAccountNumber9(accountNumber9);
		entity.setAccountNumber10(accountNumber10);
		entity.setAccountNumber11(accountNumber11);
		entity.setAccountNumber12(accountNumber12);
		entity.setAccountNumber13(accountNumber13);
		entity.setAccountNumber14(accountNumber14);
		entity.setAccountNumber15(accountNumber15);
		entity.setResend(0);

		StatusDirecta status = new StatusDirecta();
		status.setId(DirectaStatus.HEB_PROCESADO.value());
		entity.setStatus(status);
		
		return entity;
	}

	private List<List<String>> getTxtContent(Maintance entity) {
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> list = new ArrayList<String>();
		List<String> clonedList;
		EncryptBd decrypt = new EncryptBd();
		
		try{
			String createDate = new Formatter().formatDateToString(entity.getCreationDate());
			String prefixDate = new Formatter().formatDateToStringTextLayout(entity.getCreationDate());
			String accounts = "";
	
			if(entity.getAccountNumber1() != null && entity.getAccountNumber1().trim().length()>0)
				accounts += Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber1()));
			if(entity.getAccountNumber2() != null && entity.getAccountNumber2().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber2()));
			if(entity.getAccountNumber3() != null && entity.getAccountNumber3().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber3()));
			if(entity.getAccountNumber4() != null && entity.getAccountNumber4().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber4()));
			if(entity.getAccountNumber5() != null && entity.getAccountNumber5().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber5()));
			if(entity.getAccountNumber6() != null && entity.getAccountNumber6().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber6()));
			if(entity.getAccountNumber7() != null && entity.getAccountNumber7().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber7()));
			if(entity.getAccountNumber8() != null && entity.getAccountNumber8().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber8()));
			if(entity.getAccountNumber9() != null && entity.getAccountNumber9().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber9()));
			if(entity.getAccountNumber10() != null && entity.getAccountNumber10().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber10()));
			if(entity.getAccountNumber11() != null && entity.getAccountNumber11().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber11()));
			if(entity.getAccountNumber12() != null && entity.getAccountNumber12().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber12()));
			if(entity.getAccountNumber13() != null && entity.getAccountNumber13().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber13()));
			if(entity.getAccountNumber14() != null && entity.getAccountNumber14().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber14()));
			if(entity.getAccountNumber15() != null && entity.getAccountNumber15().trim().length()>0)
				accounts += "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber15()));
	
			list.add(entity.getMerchantNumber() + prefixDate + "A");// 0
			list.add(entity.getEmail1()==null?"":entity.getEmail1());// 1
			list.add(entity.getCompanyName());// 2
			list.add(entity.getManager1());// 3
			list.add(createDate);// 4
			list.add(accounts);// 5
			result.add(list);
	
			if (entity.getManager2()!=null && !entity.getManager2().equals("")) {
				clonedList = new ArrayList<String>(list);
				clonedList.set(0, entity.getMerchantNumber() + prefixDate + "B");
				clonedList.set(1, entity.getEmail2()==null?"":entity.getEmail2());
				clonedList.set(3, entity.getManager2());
				result.add(clonedList);
			}
			if (entity.getManager3()!=null && !entity.getManager3().equals("")) {
				clonedList = new ArrayList<String>(list);
				clonedList.set(0, entity.getMerchantNumber() + prefixDate + "C");
				clonedList.set(1, entity.getEmail3()==null?"":entity.getEmail3());
				clonedList.set(3, entity.getManager3());
				result.add(clonedList);
			}

		}
		catch(Exception e){
			logger.log(Level.INFO, "MaintanceLayout.getTxtContent(ID="+entity.getId()+"): Error en el parseo a de una entidad Maintance a un List<String> para el envío a Tralix. " +e.getMessage());
			result = null;
		}

		return result;
	}

	public DirectaLoad parseToDirectaLoad(Maintance entity ){
		
		SimpleDateFormat formatFormDate = new SimpleDateFormat("ddMMyyyy");

		String managers ="";
		EncryptBd decrypt = new EncryptBd();
		DirectaLoad load = new DirectaLoad();

		try{
			//Obtener los administradores
			if(entity.getManager1()!=null && !entity.getManager1().trim().equals(""))
				managers +=entity.getManager1();
			if(entity.getManager2()!=null && !entity.getManager2().trim().equals(""))
				managers +="," + entity.getManager2();
			if(entity.getManager3()!=null && !entity.getManager3().trim().equals(""))
				managers +="," + entity.getManager3();
			
	    	load.setId(entity.getId().toString());
	    	load.setCreationDate(entity.getCreationDate());
	    	load.setMerchantNumber(entity.getMerchantNumber());
	    	load.setLoad(formatFormDate.format(entity.getCreationDate()));
	    	load.setCompanyName(entity.getCompanyName());
	    	load.setMaintanceType(entity.getMaintanceType());
	    	load.setLegalRepresentative(entity.getLegalRepresentative());
	    	load.setManagers(managers);
	    	load.setCR(entity.getCR());
	    	load.setBranchName(entity.getBranchName());
	    	load.setModule(entity.getModule());
	    	load.setTerritorial(entity.getTerritorial());
	    	load.setSic(Formatter.fixLenght(entity.getSic().toString(),8));
	    	load.setPhoneNumber1(entity.getPhoneNumber1());
	    	load.setPhoneNumber2(entity.getPhoneNumber2());
	
			if(entity.getAccountNumber1() != null && entity.getAccountNumber1().trim().length()>0)
				load.setAccountNumber1(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber1())));
			if(entity.getAccountNumber2() != null && entity.getAccountNumber2().trim().length()>0)
				load.setAccountNumber2(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber2())));
			if(entity.getAccountNumber3() != null && entity.getAccountNumber3().trim().length()>0)
				load.setAccountNumber3(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber3())));
			if(entity.getAccountNumber4() != null && entity.getAccountNumber4().trim().length()>0)
				load.setAccountNumber4(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber4())));
			if(entity.getAccountNumber5() != null && entity.getAccountNumber5().trim().length()>0)
				load.setAccountNumber5(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber5())));
			if(entity.getAccountNumber6() != null && entity.getAccountNumber6().trim().length()>0)
				load.setAccountNumber6(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber6())));
			if(entity.getAccountNumber7() != null && entity.getAccountNumber7().trim().length()>0)
				load.setAccountNumber7(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber7())));
			if(entity.getAccountNumber8() != null && entity.getAccountNumber8().trim().length()>0)
				load.setAccountNumber8(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber8())));
			if(entity.getAccountNumber9() != null && entity.getAccountNumber9().trim().length()>0)
				load.setAccountNumber9(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber9())));
			if(entity.getAccountNumber10() != null && entity.getAccountNumber10().trim().length()>0)
				load.setAccountNumber10(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber10())));
			if(entity.getAccountNumber11() != null && entity.getAccountNumber11().trim().length()>0)
				load.setAccountNumber11(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber11())));
			if(entity.getAccountNumber12() != null && entity.getAccountNumber12().trim().length()>0)
				load.setAccountNumber12(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber12())));
			if(entity.getAccountNumber13() != null && entity.getAccountNumber13().trim().length()>0)
				load.setAccountNumber13(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber13())));
			if(entity.getAccountNumber14() != null && entity.getAccountNumber14().trim().length()>0)
				load.setAccountNumber14(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber14())));
			if(entity.getAccountNumber15() != null && entity.getAccountNumber15().trim().length()>0)
				load.setAccountNumber15(Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber15())));
			
	    	load.setActive("1");
	    	load.setSchedule("NA");
	    	load.setProgrammed("NO");
	    	load.setSuspended("NO");
	    	
		}
		catch(Exception e){
			logger.log(Level.INFO, "MaintanceLayout.parseToDirectaLoad(ID="+entity.getId()+"): Error en el parseo a la clase DirectaLoad. Para el envío a Directa " +e.getMessage());
			load = null;
		}

    	return load;
	}
	
	public List<String> getXlsContent(Maintance maintance) {

		List<String> list = new ArrayList<String>();
		String managers ="";
		EncryptBd decrypt = new EncryptBd();
		
		if(maintance.getManager1()!=null && !maintance.getManager1().trim().equals(""))
			managers +=maintance.getManager1();
		if(maintance.getManager2()!=null && !maintance.getManager2().trim().equals(""))
			managers +="," + maintance.getManager2();
		if(maintance.getManager3()!=null && !maintance.getManager3().trim().equals(""))
			managers +="," + maintance.getManager3();
		
		list.add(maintance.getId().toString());
		list.add(maintance.getCreationDate().toString());
		list.add(maintance.getMerchantNumber());
		list.add(maintance.getCompanyName());
		list.add(maintance.getMaintanceType());
		list.add(maintance.getLegalRepresentative());
		list.add(managers);
		list.add(maintance.getCR());
		list.add(maintance.getBranchName());
		list.add(maintance.getModule());
		list.add(maintance.getTerritorial());
		list.add(Formatter.fixLenght(maintance.getSic().toString(),8));
		list.add(maintance.getPhoneNumber1());
		list.add(maintance.getPhoneNumber2());
		if(maintance.getAccountNumber1() != null && maintance.getAccountNumber1().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber1())));
		if(maintance.getAccountNumber2() != null && maintance.getAccountNumber2().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber2())));
		if(maintance.getAccountNumber3() != null && maintance.getAccountNumber3().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber3())));
		if(maintance.getAccountNumber4() != null && maintance.getAccountNumber4().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber4())));
		if(maintance.getAccountNumber5() != null && maintance.getAccountNumber5().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber5())));
		if(maintance.getAccountNumber6() != null && maintance.getAccountNumber6().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber6())));
		if(maintance.getAccountNumber7() != null && maintance.getAccountNumber7().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber7())));
		if(maintance.getAccountNumber8() != null && maintance.getAccountNumber8().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber8())));
		if(maintance.getAccountNumber9() != null && maintance.getAccountNumber9().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber9())));
		if(maintance.getAccountNumber10() != null && maintance.getAccountNumber10().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber10())));
		if(maintance.getAccountNumber11() != null && maintance.getAccountNumber11().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber11())));
		if(maintance.getAccountNumber12() != null && maintance.getAccountNumber12().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber12())));
		if(maintance.getAccountNumber13() != null && maintance.getAccountNumber13().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber13())));
		if(maintance.getAccountNumber14() != null && maintance.getAccountNumber14().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber14())));
		if(maintance.getAccountNumber15() != null && maintance.getAccountNumber15().trim().length()>0)
			list.add(Formatter.maskAccount(decrypt.decrypt(maintance.getAccountNumber15())));
		
		return list;

	}
	public List<String> getContent(Contract contract) {

		return null;

	}

	public List<String> getHeader() {

		List<String> list = new ArrayList<String>();

		list.add("Id");
		list.add("Fecha");
		list.add("Id Empresa");
		list.add("Nombre Empresa");
		list.add("Alta / Mantenimiento");
		list.add("Apoderado o Representante Legal de la Empresa");
		list.add("Administrador de la Empresa");
		list.add("CR");
		list.add("Sucursal");
		list.add("Modulo");
		list.add("Territorio");
		list.add("SIC");
		list.add("Tel. 1");
		list.add("Tel. 2");
		list.add("Cuenta1");
		list.add("Cuenta2");
		list.add("Cuenta3");
		list.add("Cuenta4");
		list.add("Cuenta5");
		list.add("Cuenta6");
		list.add("Cuenta7");
		list.add("Cuenta8");
		list.add("Cuenta9");
		list.add("Cuenta10");
		list.add("Cuenta11");
		list.add("Cuenta12");
		list.add("Cuenta13");
		list.add("Cuenta14");
		list.add("Cuenta15");
		list.add("C1");
		list.add("C2");
		list.add("C3");
		list.add("C4");
		list.add("C5");
		list.add("C6");
		list.add("C7");
		list.add("C8");
		list.add("C9");
		list.add("C10");
		list.add("C11");
		list.add("C12");
		list.add("C13");
		list.add("C14");
		list.add("C15");
		list.add("Se hizo contacto con");
		list.add("Nombre de quien atendió la llamada en Empresa");
		list.add("Resultado de la llamada");
		list.add("Reintentos Internos");
		list.add("Comentarios del cliente:");
		list.add("Nombre de quien atendió la llamada en BEM Central");

		return list;

	}

	public void save(Date createDate) {
		this.saveMaintances(createDate);
	}

	public void createTxtLayout(String productName, boolean saveLocalFile) {

		List<List<String>> result = new ArrayList<List<String>>();
		List<List<String>> content = new ArrayList<List<String>>();
		
		String maintanceType = productName.equals("ABEM") ? "ALTA"
				: "MANTENIMIENTO";

		for (Maintance entity : this.getMaintances()) {
			if (entity.getMaintanceType().equals(maintanceType)){
				
				content = this.getTxtContent(entity);
				
				if(content!=null){
					result.addAll(content);
				}
			}
		}

		byte[] contentLayout = this.getContentTxtLayout(productName, "|", result);

		if (contentLayout!= null && contentLayout.length > 0){
			this.saveRemoteFile(productName, contentLayout, saveLocalFile);


			this.updateSentDate("TRALIX");

		}
	}

}
