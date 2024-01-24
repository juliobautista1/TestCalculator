package com.banorte.contract.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.banorte.contract.business.CitiesRemote;
import com.banorte.contract.business.StatesRemote;
import com.banorte.contract.model.Cities;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.States;
import com.banorte.contract.model.Template;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.FTPProperties;
import com.banorte.contract.util.ProductType;
import com.banorte.contract.util.pdf.PdfTemplateBinding;
import com.banorte.contract.util.pdf.PdfTemplateBindingContract;

public class MttoTPVGeneric extends ContractAbstractMB{
	
	protected StatesRemote statesBean;
	protected CitiesRemote citiesBean;
	
	private SelectItem[] statesArray;
	private SelectItem[] citiesArray;
	
	//Datos de Afiliacion
	private Integer affiliationId;
	private Integer clientSic;
	private Integer fisicalType;
	private String fiscalName;
	private String merchantName;
	private String rfc;
	private Integer accountNumber;
	private String currency;
	private String bank;
	
	//Datos Domicilio Nuevo
	private String street;
	private String colony;
	private Integer zipcode;
	private String state;
	private String city;
	
	//Cambio de Telefono
	/*Telefono*/
	
	//Servicios Especiales
	/*CheckBox*/
	
	//Cambio de Banco
	
	
	//CR Gestor
	private String crGestor;
	private String branchnameGestor;
	private String branchstreetGestor;
	private String branchcolonyGestor;
	private String branchzipcodeGestor;
	private String branchstateGestor;
	private String branchcityGestor;
	

	//Cambio de URL
	String url;//no se encontro y asi se definio
	
	//Addendum herramienta Web
	
	//Reenvio de Claves Payworks
	String email;//no se encontro y asi se definio
	
	//Cambio de Correo electronico
	String newemail;//no se encontro y asi se definio
	
	//Cambio de Aplicacion
	
	//Cambio de Cuenta de Cheques
	String numcuenta;//no se encontro y asi se definio
	
	//Cambio Nombre Comercial
	String newmerchantName;//no se encontro y asi se definio
	
	//Incremento en el limite de techo
	
	//Incremento en al limite de voucher
	
	//Equipos Temporales
	
	//Realizacion de 3D Secure
	
	//Cambio de apoderado
	//private LegalRepresentative legalRep1;//crear get y set
	//private LegalRepresentative legalRep2;//crear get y set
	
	String legalrepresentative_name_1;
	String legalrepresentative_lastname_1;
	String legalrepresentative_mothersln_1;
	String legalrepresentative_rfc_1;
	String legalrepresentative_email_1;
	
	String legalrepresentative_name_2;
	String legalrepresentative_lastname_2;
	String legalrepresentative_mothersln_2;
	String legalrepresentative_rfc_2;
	String legalrepresentative_email_2;
	
	//Cancelacion por Monitoreo
	
	//Cambio de giro
	
	//Cambio de RFC
	String newrfc;
	
	//Cambio de Cliente VIP
	
	//Baja de Terminal
	
	//Id Adicionales y cambios de equipo
	
	//Cambios de SIM
	
	//Bajas de comision
	
	//Pagos Diferidos 15 Bancos
	
	//Pagos diferidos Banorte IXE
	
	
	//Adjuntar archivo de soporte
	private UploadedFile fileSuport;
	private List<UploadedFile> filesSuport;
	private List<String> filesSuportNames;
	
	//Datos del funcionario facultado
	String officerebankingempnumber;
	String officerebankingname;
	String officerebankinglastname;
	String officerebankingmothersln;
	String officerbankingposition;
	
	//Datos de la Sucursal
	String crnumber;
	String branchname;
	String branchstreet;
	String branchcolony;
	
	//Datos Funcionarios Banorte
	private String officerrepname_1;
	private String officerreplastname_1;
	private String officerrepmothersln_1;
	private String officerrepempnumber_1;
	private String officerrepposition_1;
	private String officerrepname_2;
	private String officerreplastname_2;
	private String officerrepmothersln_2;
	private String officerrepempnumber_2;
	private String officerrepposition_2;
	
	private String mttoName;
	FTPProperties ftpPoperties = new FTPProperties();
	
	private PdfTemplateBindingContract pdfTemplateBinding;
	
	
	
	
	public MttoTPVGeneric(){
		
		if (statesBean == null) {
			statesBean = (StatesRemote) EjbInstanceManager.getEJB(StatesRemote.class);
		}
		if (citiesBean == null) {
			citiesBean = (CitiesRemote) EjbInstanceManager.getEJB(CitiesRemote.class);
		}

		this.affiliationId = 0;
		this.clientSic = 0;
		this.fisicalType = 0;
		this.fiscalName = "";
		this.merchantName = "";
		this.rfc = "";
		this.accountNumber = 0;
		this.currency = "";
		this.bank = "";
		this.street = "";
		this.colony = "";
		this.zipcode = 0;
		this.state = "";
		this.city = "";
		this.filesSuport = new ArrayList<UploadedFile>();
		this.filesSuportNames = new ArrayList<String>();
		
		this.crGestor="";
		this.branchnameGestor="";
		this.branchstreetGestor="";
		this.branchcolonyGestor="";
		this.branchzipcodeGestor="";
		this.branchstateGestor="";
		this.branchcityGestor="";
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		this.mttoName = params.get("url");
				
		//this.statesArray = new SelectItem[ApplicationConstants.EMPTY_LIST];
	}
	
	
	

	@Override
	public boolean savePartialContract() {
		Contract contract = getContract();
		ArrayList<ContractAttribute> list = new ArrayList<ContractAttribute>();
		
		return true;
	}
	
	@Override
	public PdfTemplateBinding getPdfTemplateBinding() {
		return pdfTemplateBinding;
	}
	
	@Override
	public Template[] getFormatList() {
		this.setToPrint(false);
		Collection<Template> templateCollection = getTemplateOption(this.getProduct().getProductid());
		Collection<Template> templateColl2 = new ArrayList();
		for(Template t: templateCollection){
   	        templateColl2.add(t);	
		}
		
		Template[] templateArray = new Template[templateColl2.size()];
		return templateColl2.toArray(templateArray); 
	}
	
	@Override
	public void setResetForm() {
		System.out.println("resetForm");
	}
	
	@Override
	public void getProductIdDetail() {
		setProduct(productBean.findById(new Integer(ProductType.MTTO_ALTA_CUENTAS.value())));
	}
	
	@Override
	public void setEditForm() {
	}
	
	public void findAffiliation(){
		String algo = "";
		System.out.println("findAffiliation");
	}
	
	
	public String addFile() {
		if (fileSuport != null) {
			getFilesSuport().add(fileSuport);
			getFilesSuportNames().add(trimFilePath(fileSuport.getName()));
		}
		
		
		
		
		/*FileLayout fLayout = new FileLayout();
		Formatter format = new Formatter();
		String date = format.formatDateToStringLayout(new Date());
		FTPProperties fprops = new FTPProperties();
		
		InputStream fileContent = null;
		if (filesSuport == null) {
			return "success";
		}
		try {
			fileContent = filesSuport.getInputStream();
		
			byte[] content = IOUtils.toByteArray(fileContent);
		
		
			// Upload report to FTP
			fLayout.uploadToFtp(trimFilePath(filesSuport.getName()), content, fprops, "mttoTPV"); //FIXME vicman para produccion
//			fLayout.storeLocalFile(fileName, content); 				//FIXME vicman para desarrollo
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		try {
			fileContent.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		
		
		
		
		
		/*File savedFileName;
		String dirPath = "C:\\Prueba\\";
		InputStream fileContent = null;
		if (filesSuport == null) {
			return "success";
		}
		try {
			fileContent = filesSuport.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String uploadFileName = trimFilePath(filesSuport.getName());
		savedFileName = new File(dirPath + uploadFileName);
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(savedFileName));
		} catch (FileNotFoundException e) {
		}
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = fileContent.read(buffer)) >= 0) {
				bos.write(buffer, 0, len);
			}
		} catch (IOException e) {
		}
		try {
			fileContent.close();
			bos.close();
		} catch (IOException e) {
		}*/
		return "success";
	}
	
	public static String trimFilePath(String fileName) {
		return fileName.substring(fileName.lastIndexOf("/") + 1).substring(
				fileName.lastIndexOf("\\") + 1);
	}
	
	public String processInfo() {
		
		String algo="";
		return algo;
	}
	
	//Datos de la Afiliacion
	public Integer getAffiliationId() {
		return affiliationId;
	}
	public void setAffiliationId(Integer affiliationId) {
		this.affiliationId = affiliationId;
	}
	
	public Integer getClientSic() {
		return clientSic;
	}
	public void setClientSic(Integer clientSic) {
		this.clientSic = clientSic;
	}

	public Integer getFisicalType() {
		return fisicalType;
	}
	public void setFisicalType(Integer fisicalType) {
		this.fisicalType = fisicalType;
	}

	public String getFiscalName() {
		return fiscalName;
	}
	public void setFiscalName(String fiscalName) {
		this.fiscalName = fiscalName;
	}

	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	//
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	public String getColony() {
		return colony;
	}
	public void setColony(String colony) {
		this.colony = colony;
	}

	public Integer getZipcode() {
		return zipcode;
	}
	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public UploadedFile getFileSuport() {
		return fileSuport;
	}
	public void setFileSuport(UploadedFile fileSuport) {
		this.fileSuport = fileSuport;
	}

	public List<UploadedFile> getFilesSuport() {
		return filesSuport;
	}
	public void setFilesSuport(List<UploadedFile> filesSuport) {
		this.filesSuport = filesSuport;
	}

	public List<String> getFilesSuportNames() {
		return filesSuportNames;
	}
	public void setFilesSuportNames(List<String> filesSuportNames) {
		this.filesSuportNames = filesSuportNames;
	}

	public String getMttoName() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(null!=params.get("url")){
			setMttoName(params.get("url"));
		}
		return mttoName;
	}
	public void setMttoName(String mttoName) {
		this.mttoName = mttoName;
	}

	public String getProductPrefix() {
		return ApplicationConstants.PREFIJO_MTTOS;
	}
	public void setPdfTemplateBinding(PdfTemplateBindingContract pdfTemplateBinding) {
		this.pdfTemplateBinding = pdfTemplateBinding;
	}
	
	/**
	 * Metodo que identifica y busca la información del gestor mediante el CR
	 */
	public void findCrGestor(ActionEvent actionEvent) {
		
	}
	


	public SelectItem[] getStatesArray() {
		if (this.statesArray == null) {
			// Lista Estados
			List<States> states = statesBean.findAll();
			if (states != null) {
				statesArray = new SelectItem[states.size()];
				int i = 0;
				for (States sta : states) {
					//System.err.println("Estado: " + sta.getName());
					statesArray[i] = new SelectItem(sta.getName(), sta.getName());
					i++;
				}
			}
		}
		return statesArray;
	}

	public void setStatesArray(SelectItem[] statesArray) {
		this.statesArray = statesArray;
	}

	public SelectItem[] getCitiesArray() {
		if(getBranchstateGestor() ==null){
			setBranchstateGestor(ApplicationConstants.EMPTY_STRING);
		}
		if (getBranchstateGestor() != null) {
			List<Cities> cities = citiesBean.findByState(getBranchstateGestor());
			if (cities != null) {
				citiesArray = new SelectItem[cities.size()];
				int i = 0;
				for (Cities cit : cities) {
					//System.err.println("Ciudad: " + cit.getName());
					citiesArray[i] = new SelectItem(cit.getName(),cit.getName());
					i++;
				}
			}
		}
		return citiesArray;
	}

	public void setCitiesArray(SelectItem[] citiesArray) {
		this.citiesArray = citiesArray;
	}
	
	public String getCrGestor() {
		return crGestor;
	}

	public void setCrGestor(String crGestor) {
		this.crGestor = crGestor;
	}
	public String getBranchcityGestor() {
		return branchcityGestor;
	}

	public void setBranchcityGestor(String branchcityGestor) {
		this.branchcityGestor = branchcityGestor;
	}

	private String branchphoneGestor;
	private String branchfaxGestor;
	
	public String getBranchnameGestor() {
		return branchnameGestor;
	}

	public void setBranchnameGestor(String branchnameGestor) {
		this.branchnameGestor = branchnameGestor;
	}

	public String getBranchstreetGestor() {
		return branchstreetGestor;
	}

	public void setBranchstreetGestor(String branchstreetGestor) {
		this.branchstreetGestor = branchstreetGestor;
	}

	public String getBranchcolonyGestor() {
		return branchcolonyGestor;
	}

	public void setBranchcolonyGestor(String branchcolonyGestor) {
		this.branchcolonyGestor = branchcolonyGestor;
	}

	public String getBranchzipcodeGestor() {
		return branchzipcodeGestor;
	}

	public void setBranchzipcodeGestor(String branchzipcodeGestor) {
		this.branchzipcodeGestor = branchzipcodeGestor;
	}

	public String getBranchstateGestor() {
		return branchstateGestor;
	}

	public void setBranchstateGestor(String branchstateGestor) {
		this.branchstateGestor = branchstateGestor;
	}

	public String getBranchphoneGestor() {
		return branchphoneGestor;
	}

	public void setBranchphoneGestor(String branchphoneGestor) {
		this.branchphoneGestor = branchphoneGestor;
	}

	public String getBranchfaxGestor() {
		return branchfaxGestor;
	}

	public void setBranchfaxGestor(String branchfaxGestor) {
		this.branchfaxGestor = branchfaxGestor;
	}
	

	
}
