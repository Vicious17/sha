/*
 *  Copyright (C) 2011 - 2016  DVCONSULTORES

    Este programa es software libre: usted puede redistribuirlo y/o modificarlo 
    bajo los terminos de la Licencia Pública General GNU publicada 
    por la Fundacion para el Software Libre, ya sea la version 3 
    de la Licencia, o (a su eleccion) cualquier version posterior.

    Este programa se distribuye con la esperanza de que sea útil, pero 
    SIN GARANTiA ALGUNA; ni siquiera la garantia implicita 
    MERCANTIL o de APTITUD PARA UN PROPoSITO DETERMINADO. 
    Consulte los detalles de la Licencia Pública General GNU para obtener 
    una informacion mas detallada. 

    Deberia haber recibido una copia de la Licencia Pública General GNU 
    junto a este programa. 
    En caso contrario, consulte <http://www.gnu.org/licenses/>.
 */


package org.sha.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andres
 */
	@ManagedBean
	@ViewScoped
	public class Bvt002 extends Bd implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		
		private LazyDataModel<Bvt002> lazyModel;  
		
		
		
		
	    /**
		 * @return the lazyModel
		 */
		public LazyDataModel<Bvt002> getLazyModel() {
			return lazyModel;
		}



	@PostConstruct	
	public void init() {
		if (instancia == null){instancia = "99999";}
		
		//Si no tiene acceso al módulo no puede ingresar
		if (new SeguridadMenuBean().opcmnu("M15")=="false") {
			RequestContext.getCurrentInstance().execute("PF('idleDialogNP').show()");
		}
		
		lazyModel  = new LazyDataModel<Bvt002>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 7217573531435419432L;
			
            @Override
			public List<Bvt002> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) { 
            	//Filtro
            	if (filters != null) {
               	 for(Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
               		 String filterProperty = it.next(); // table column name = field name
                     filterValue = filters.get(filterProperty);
               	 }
                }
            	try { 
            		//Consulta
					select(first, pageSize,sortField, filterValue);
					//Counter
					counter(filterValue);
					//Contador lazy
					lazyModel.setRowCount(rows);  //Necesario para crear la paginación
				} catch (SQLException | NamingException | ClassNotFoundException e) {	
					e.printStackTrace();
				}             
				return list;  
            } 
            
            
            //Arregla bug de primefaces
            @Override
            /**
			 * Arregla el Issue 1544 de primefaces lazy loading porge generaba un error
			 * de divisor equal a cero, es solamente un override
			 */
            public void setRowIndex(int rowIndex) {
                /*
                 * The following is in ancestor (LazyDataModel):
                 * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
                 */
                if (rowIndex == -1 || getPageSize() == 0) {
                    super.setRowIndex(-1);
                }
                else
                    super.setRowIndex(rowIndex % getPageSize());
            }
            
		};
			
		if(vusuario==null){
			vusuario = "";
		}
		
		//llena el chkboxmnu
		try {
			selectSuc();
		} catch (NamingException | SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private String coduser = "";
	private String desuser = "";
	private String cluser = "";
	private String cluser1 = "";
	private String b_codrol = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("b_codrol");
	private String desrol = "";
	private String cluserold = "";
	private String mail = "";
	private String sucursal = ""; 
	private String zsucursal = "";
	private String zcoduser = "";
	private String zdesuser = "";
	private String zcluser = "";
	private String zb_codrol = "";
	private String zdesrol = "";
	private String zdessuc = "";
	private String zmail = "";
	private String zdelete = "";
	private List<Bvt002> list = new ArrayList<Bvt002>();
	private Map<String,String> listSuc = new HashMap<String, String>();   //Lista de sucursales disponibles para acceso a seguridad 
	private List<String> selectedSuc;   //Listado de sucursales seleccionadas
	private Map<String, String> sorted;
	private String exito = "exito";
	private int columns;
	private String[][] arr;
	private Object filterValue = "";
	List<Bvt002> listRoles = new ArrayList<Bvt002>();
	@SuppressWarnings("unchecked")
	List<Bvt002> listRolesSession = (List<Bvt002>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listRolesSession");
	
	private int validarOperacion = 0;
	private String instancia = "";
	//Cambio de password
	StringMD md = new StringMD();
	private String randomKey;
	
	
	//Roles adicionales
	private String vcodrol;
	private String vdesrol;
	private String isRol;


	public String getZmail() {
		return zmail;
	}



	public void setZmail(String zmail) {
		this.zmail = zmail;
	}



	public String getZdessuc() {
		return zdessuc;
	}



	public void setZdessuc(String zdessuc) {
		this.zdessuc = zdessuc;
	}



	public String getZdesrol() {
		return zdesrol;
	}



	public void setZdesrol(String zdesrol) {
		this.zdesrol = zdesrol;
	}



	public String getZcoduser() {
		return zcoduser;
	}



	public void setZcoduser(String zcoduser) {
		this.zcoduser = zcoduser;
	}



	public String getZdesuser() {
		return zdesuser;
	}



	public void setZdesuser(String zdesuser) {
		this.zdesuser = zdesuser;
	}



	public String getZcluser() {
		return zcluser;
	}



	public void setZcluser(String zcluser) {
		this.zcluser = zcluser;
	}



	public String getZb_codrol() {
		return zb_codrol;
	}



	public void setZb_codrol(String zb_codrol) {
		this.zb_codrol = zb_codrol;
	}



	public String getZdelete() {
		return zdelete;
	}



	public void setZdelete(String zdelete) {
		this.zdelete = zdelete;
	}



	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}



	public String getZsucursal() {
		return zsucursal;
	}



	public void setZsucursal(String zsucursal) {
		this.zsucursal = zsucursal;
	}



	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}



	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}



		/**
	 * @return the cluser1
	 */
	public String getCluser1() {
		return cluser1;
	}

	/**
	 * @param cluser1 the cluser1 to set
	 */
	public void setCluser1(String cluser1) {
		this.cluser1 = cluser1;
	}

		/**
	 * @return the coduser
	 */
	public String getCoduser() {
		return coduser;
	}
	
	/**
	 * @param coduser the coduser to set
	 */
	public void setCoduser(String coduser) {
		this.coduser = coduser;
	}
	
	/**
	 * @return the desuser
	 */
	public String getDesuser() {
		return desuser;
	}
	
	/**
	 * @param desuser the desuser to set
	 */
	public void setDesuser(String desuser) {
		this.desuser = desuser;
	}
	
	/**
	 * @return the cluser
	 */
	public String getCluser() {
		return cluser;
	}
	
	/**
	 * @param cluser the cluser to set
	 */
	public void setCluser(String cluser) {
		this.cluser = cluser;
	}
	
	/**
	 * @return the b_codrol
	 */
	public String getb_codrol() {
		return b_codrol;
	}
	
	/**
	 * @param b_codrol the b_codrol to set
	 */
	public void setb_codrol(String b_codrol) {
		this.b_codrol = b_codrol;
	}
	
	
	
	/**
	 * @return the cluserold
	 */
	public String getCluserold() {
		return cluserold;
	}
	
	/**
	 * @param cluserold the cluserold to set
	 */
	public void setCluserold(String cluserold) {
		this.cluserold = cluserold;
	}
	


	/**
	 * @return the validarOperacion
	 */
	public int getValidarOperacion() {
		return validarOperacion;
	}

	/**
	 * @param validarOperacion the validarOperacion to set
	 */
	public void setValidarOperacion(int validarOperacion) {
		this.validarOperacion = validarOperacion;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	

	/**
	 * @return the desrol
	 */
	public String getDesrol() {
		return desrol;
	}



	/**
	 * @param desrol the desrol to set
	 */
	public void setDesrol(String desrol) {
		this.desrol = desrol;
	}
	
	
	

	/**
	 * @return the instancia
	 */
	public String getInstancia() {
		return instancia;
	}



	/**
	 * @param instancia the instancia to set
	 */
	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	

	/**
	 * @return the vcodrol
	 */
	public String getVcodrol() {
		return vcodrol;
	}



	/**
	 * @param vcodrol the vcodrol to set
	 */
	public void setVcodrol(String vcodrol) {
		this.vcodrol = vcodrol;
	}



	/**
	 * @return the vdesrol
	 */
	public String getVdesrol() {
		return vdesrol;
	}



	/**
	 * @param vdesrol the vdesrol to set
	 */
	public void setVdesrol(String vdesrol) {
		this.vdesrol = vdesrol;
	}



	/**
	 * @return the isRol
	 */
	public String getIsRol() {
		return isRol;
	}



	/**
	 * @param isRol the isRol to set
	 */
	public void setIsRol(String isRol) {
		this.isRol = isRol;
	}
	
	

	/**
	 * @return the listRoles
	 */
	public List<Bvt002> getListRoles() {
		return listRoles;
	}




	/**
	 * @return the listRolesSession
	 */
	public List<Bvt002> getListRolesSession() {
		return listRolesSession;
	}



	/**
	 * @param listRolesSession the listRolesSession to set
	 */
	public void setListRolesSession(List<Bvt002> listRolesSession) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listRolesSession", listRolesSession);
	}



	/**
	 * @param listRoles the listRoles to set
	 */
	public void setListRoles(List<Bvt002> listRoles) {
		this.listRoles = listRoles;
	}
	
	

	/**
	 * @return the vusuario
	 */
	public String getVusuario() {
		return vusuario;
	}



	/**
	 * @param vusuario the vusuario to set
	 */
	public void setVusuario(String vusuario) {
		this.vusuario = vusuario;
	}

	
			/**
		 * @return the listSuc
		 */
		public Map<String, String> getListSuc() {
			return listSuc;
		}
		
		/**
		 * @param listSuc the listSuc to set
		 */
		public void setListSuc(Map<String, String> listSuc) {
			this.listSuc = listSuc;
		}
		
		/**
		 * @return the selectedSuc
		 */
		public List<String> getSelectedSuc() {
			return selectedSuc;
		}
		
		/**
		 * @param selectedSuc the selectedSuc to set
		 */
		public void setSelectedSuc(List<String> selectedSuc) {
			this.selectedSuc = selectedSuc;
		}
		
		/**
		 * @return the sorted
		 */
		public Map<String, String> getSorted() {
			return sorted;
		}
		
		/**
		 * @param sorted the sorted to set
		 */
		public void setSorted(Map<String, String> sorted) {
			this.sorted = sorted;
		}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Variables seran utilizadas para capturar mensajes de errores de Oracle y parametros de metodos
	FacesMessage msj = null;
	PntGenerica consulta = new PntGenerica();
	boolean vGacc; //Validador de opciones del menó
	private int rows; //Registros de tabla
	private String login = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //Usuario logeado
	private String vusuario = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bcoduser"); //Usuario logeado
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//Coneccion a base de datos
	//Pool de conecciones JNDI
	Connection con;
	PreparedStatement pstmt = null;
	ResultSet r;
	
	
	/**
     * Inserta Usuarios.
     * <p>
     * Parámetros del Mátodo: String coduser, String desuser, String clave, String b_codrol.
     **/
    public void insertadmin() throws  NamingException {
   		    		
        try {
        	Context initContext = new InitialContext();     
     		DataSource ds = (DataSource) initContext.lookup(JNDI);
            con = ds.getConnection();
            
            if(b_codrol==null){
            	b_codrol = " - ";
      		}
      		if(b_codrol==""){
      			b_codrol = " - ";
      		}             
    		String[] vecb_codrol = b_codrol.split("\\ - ", -1);
            
            String query = "INSERT INTO SHABVT002 VALUES (?,?,?,?,?,?,'" + getFecha() + "',?,'" + getFecha() + "','TODAS')";

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, coduser.toUpperCase().trim());
            pstmt.setString(2, desuser.toUpperCase());
            pstmt.setString(3, md.getStringMessageDigest(cluser, StringMD.SHA256));
            pstmt.setString(4, vecb_codrol[0].toUpperCase());
            pstmt.setString(5, mail.toLowerCase());
            pstmt.setString(6, login);
            pstmt.setString(7, login);

            //System.out.println(query);
            try {
                //Avisando
            	pstmt.executeUpdate();
            	msj = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage("msnInsert"), "");
                limpiarValores();                
             } catch (SQLException e)  {
            	msj = new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), "");
            }
            
            pstmt.close();
            con.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }	
        FacesContext.getCurrentInstance().addMessage(null, msj);
    }
    
	/**
     * Inserta Usuarios.
     * <p>
     * Parámetros del Mátodo: String coduser, String desuser, String clave, String b_codrol.
     **/
    public void insert(String pcodsuc) throws  NamingException {
   		    		
        try {
        	Context initContext = new InitialContext();     
     		DataSource ds = (DataSource) initContext.lookup(JNDI);
            con = ds.getConnection();
            
            if(b_codrol==null){
            	b_codrol = " - ";
      		}
      		if(b_codrol==""){
      			b_codrol = " - ";
      		}             
    		String[] vecb_codrol = b_codrol.split("\\ - ", -1);
            
            String query = "INSERT INTO SHABVT002 VALUES (?,?,?,?,?,?,'" + getFecha() + "',?,'" + getFecha() + "',?)";

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, coduser.toUpperCase().trim());
            pstmt.setString(2, desuser.toUpperCase());
            pstmt.setString(3, md.getStringMessageDigest(cluser, StringMD.SHA256));
            pstmt.setString(4, vecb_codrol[0].toUpperCase());
            pstmt.setString(5, mail.toLowerCase());
            pstmt.setString(6, login);
            pstmt.setString(7, login);
            pstmt.setString(8, pcodsuc.toUpperCase());

            //System.out.println(query);
            //System.out.println(pcodsuc.toUpperCase());
            try {
                //Avisando
            	pstmt.executeUpdate();
                
             } catch (SQLException e)  {
            	msj = new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), "");
            	FacesContext.getCurrentInstance().addMessage(null, msj);
            }
            
            pstmt.close();
            con.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }	        
    }
    
    public void delete() throws NamingException  {  
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	String[] chkbox = request.getParameterValues("toDelete");
    	
    	if (chkbox==null){
    		msj = new FacesMessage(FacesMessage.SEVERITY_WARN, getMessage("del"), "");
    	} else {
	        try {
	       	
	        	Context initContext = new InitialContext();     
	     		DataSource ds = (DataSource) initContext.lookup(JNDI);
	     		con = ds.getConnection();		
	     		
	        	String param = "'" + StringUtils.join(chkbox, "','") + "'";

	        	String query = "DELETE from SHABVT002 WHERE coduser||sucursal in (" + param + ")";
	        		        	
	            pstmt = con.prepareStatement(query);
	            //System.out.println(query);
	            //System.out.println("param: " + param);
	
	            try {
	                //Avisando
	                pstmt.executeUpdate();
	                msj = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage("msnDelete"), "");

	                limpiarValores();	
	            } catch (SQLException e) {
	                e.printStackTrace();
	                msj = new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), "");
	            }
	
	            pstmt.close();
	            con.close();
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    	}
    	FacesContext.getCurrentInstance().addMessage(null, msj); 
    }
    
    /**
     * Genera las operaciones de guardar o modificar
     * @throws NamingException 
     * @throws SQLException 
     * @throws IOException 
     * @throws ClassNotFoundException 
     **/ 
    public void guardar() throws NamingException, SQLException, ClassNotFoundException{  
    	
    	if(b_codrol==null){
           b_codrol = " - ";
   		}
   		if(b_codrol==""){
   		   b_codrol = " - ";
   		}             
 		String[] vecb_codrol = b_codrol.split("\\ - ", -1);
 		//System.out.println("vecb_codrol: " + vecb_codrol[0]);
 		
        if (vecb_codrol[0].equals("1")){
        	//System.out.println("entre al IF");
        	//System.out.println("vecb_codrol: " + vecb_codrol[0]);
        	//System.out.println("validacion de la condicion: " + vecb_codrol[0].equals("1"));
         insertadmin();
    	} else {  	
    		//System.out.println("entre al ELSE");
        	//System.out.println("vecb_codrol: " + vecb_codrol[0]);
    		//System.out.println("validacion de la condicion: " + vecb_codrol[0].equals("1"));
    	 insertregular();
    	}
    }
        
    /**
     * Genera las operaciones de guardar o modificar
     * @throws NamingException 
     * @throws SQLException 
     * @throws IOException 
     * @throws ClassNotFoundException 
     **/ 
    public void insertregular() throws NamingException, SQLException, ClassNotFoundException{  
        if (selectedSuc.size()<=0){
    		msj = new FacesMessage(FacesMessage.SEVERITY_WARN, getMessage("bvt007IngRep"), "");
    		FacesContext.getCurrentInstance().addMessage(null, msj);
    	} else {  	
    	   for (int i = 0; i< selectedSuc.size(); i++){
    		   ////System.out.println("Selected :" + selectedAcccat1.get(i));
    		insert(selectedSuc.get(i));           
    	   }
   		limpiarValores();   
        if(exito=="exito"){
        	msj = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage("msnInsert"), "");
        	FacesContext.getCurrentInstance().addMessage(null, msj);
        }
    	}
    }
    
    /**
     * Actualiza Usuarios
     * <p> Parámetros del Método: String coduser, String desuser, String cluser, String p_codrol.
     **/
    public void updatea() throws  NamingException {
        try {
        	Context initContext = new InitialContext();     
     		DataSource ds = (DataSource) initContext.lookup(JNDI);

     		con = ds.getConnection();	
            //Class.forName(getDriver());
            //con = DriverManager.getConnection(
            //        getUrl(), getUsuario(), getClave());
            String query = "UPDATE SHABVT002";
             query += " SET CLUSER = ?";
             query += " WHERE CODUSER = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, md.getStringMessageDigest(cluser, StringMD.SHA256));
            pstmt.setString(2, login.toUpperCase());
            try {
            	if(!cluser.equals(cluser1)){
            		 msj = new FacesMessage(FacesMessage.SEVERITY_ERROR,  getMessage("bvt002Cl1Msj"), "");
            	} else {
                //Avisando
                pstmt.executeUpdate();
                msj = new FacesMessage(FacesMessage.SEVERITY_INFO,  getMessage("bvt002up"), "");
               
            	}
            } catch (SQLException e) {
                e.printStackTrace();
                msj = new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), "");
            }
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	FacesContext.getCurrentInstance().addMessage(null, msj); 
    }
    
    /**
     * Leer Datos de paises
     * @throws NamingException 
     * @throws IOException 
     **/ 	
  	public void select(int first, int pageSize, String sortField, Object filterValue) throws SQLException, ClassNotFoundException, NamingException {
  		
  		Context initContext = new InitialContext();     
 		DataSource ds = (DataSource) initContext.lookup(JNDI);
 		con = ds.getConnection();				
 		
 		String query = " select * from ";
        	   query += " ( select query.*, rownum as rn from";
        	   query += " (SELECT trim(A.CODUSER) AS CODUSER, trim(A.DESUSER), trim(A.CLUSER), A.B_CODROL, CASE WHEN A.SUCURSAL IS NULL THEN 'TODAS' ELSE A.SUCURSAL END AS SUCURSAL, B.ROL, CASE WHEN C.DESSUC IS NULL THEN 'TODAS' ELSE C.DESSUC END AS DESSUC, A.MAIL";
        	   query += " FROM SHABVT002 A LEFT OUTER JOIN SHATIPROL B ON A.B_CODROL = B.CODIGO" ;
        	   query += " LEFT OUTER JOIN NM_TRABAJADOR@INFOCENT_CALENDARIO C ON A.SUCURSAL = C.CODSUC " ;
        	   query += " WHERE A.CODUSER like '" + coduser.toUpperCase() + "%'";
        	   query += " AND A.CODUSER||A.DESUSER||A.B_CODROL||A.SUCURSAL||B.ROL||C.DESSUC like '%" + ((String) filterValue).toUpperCase() + "%'";
        	   query += " GROUP BY A.CODUSER, A.DESUSER, A.CLUSER, A.B_CODROL, A.SUCURSAL, B.ROL, C.DESSUC, A.MAIL";
        	   query += ")query ) " ;
        	   query += " WHERE ROWNUM <="+pageSize;
        	   query += " AND rn > ("+ first +")";
        	   query += " ORDER BY  " + sortField.replace("z", "");
        
        pstmt = con.prepareStatement(query);
        //System.out.println(query);
  		
        r =  pstmt.executeQuery();
        
        while (r.next()){
        Bvt002 select = new Bvt002();
     	select.setZcoduser(r.getString(1));
     	select.setZdesuser(r.getString(2));
        select.setZcluser(r.getString(3));
        select.setZb_codrol(r.getString(4) + " - " + r.getString(6));
        select.setZsucursal(r.getString(5));
        select.setZdesrol(r.getString(6));
        select.setZdessuc(r.getString(7));
        select.setZmail(r.getString(8));
        select.setZdelete(r.getString(1) + "" + r.getString(5));
        
        	//Agrega la lista
        	list.add(select);
        }
        //Cierra las conecciones
        pstmt.close();
        con.close();
  	}
  	
 	/**
     * Leer registros en la tabla
     * @throws NamingException 
     * @throws IOException 
     **/ 	
  	public void counter(Object filterValue ) throws SQLException, NamingException {
     try {	
    	Context initContext = new InitialContext();     
   		DataSource ds = (DataSource) initContext.lookup(JNDI);
   		con = ds.getConnection();
   		
        if(b_codrol==null){
        	b_codrol = " - ";
  		}
  		if(b_codrol==""){
  			b_codrol = " - ";
  		}  

         String[] vecb_codrol = b_codrol.split("\\ - ", -1);
   	      		
 		String query = "SELECT count_shabvt002('" + ((String) filterValue).toUpperCase() + "','" + vecb_codrol[0] + "') from dual";
        
        pstmt = con.prepareStatement(query);
        //System.out.println(query);

         r =  pstmt.executeQuery();
        
        
        while (r.next()){
        	rows = r.getInt(1);
        }
     } catch (SQLException e){
         e.printStackTrace();    
     }
        //Cierra las conecciones
        pstmt.close();
        con.close();
        r.close();

  	}

        
    /**
     * Leer datos de Usuarios
     *<p> Parómetros del Mótodo: String coduser, String desuser.
      * * Fila desde y hasta para paginación, orden de la consulta.
     * @throws NamingException 
     * @throws IOException 
     **/

    @SuppressWarnings("null")
	public void  selectBvt002a(String usuario, String orden, String pool) throws NamingException  {

        //Pool de conecciones JNDI. Cambio de metodología de conexión a bd. Julio 2010
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup(JNDI);
        try {
            Statement stmt = null;
            ResultSet rs;
            Connection con = ds.getConnection();
            
            
            String query = "SELECT CODUSER , DESUSER" +
           " FROM SHABVT002" +
           " WHERE CODUSER = '" + usuario.toUpperCase() + "'" +
           " ORDER BY " + orden ;
            //System.out.println(query);
            try{
            rs = stmt.executeQuery(query);
            rows = 1;
 		    rs.last();
 		    rows = rs.getRow();
            //System.out.println(rows);

            ResultSetMetaData rsmd = rs.getMetaData();
        	columns = rsmd.getColumnCount();
 		    //System.out.println(columns);
        	arr = new String[rows][columns];

            int i = 0;
 		    rs.beforeFirst();
            while (rs.next()){
                for (int j = 0; j < columns; j++)
 				arr [i][j] = rs.getString(j+1);
 				i++;
               }
                    } catch (SQLException e) {
                    e.printStackTrace();
                }
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     /**
      * Leer Datos de Usuarios
      *<p> Parametros del Metodo: String coduser, String desuser.
      * String pool
     * @throws IOException 
      **/
     public void selectLogin(String user, String pool) throws NamingException {

         //Pool de conecciones JNDI. Cambio de metodologia de conexion a Bd. Julio 2010
         Context initContext = new InitialContext();
         DataSource ds = (DataSource) initContext.lookup(JNDI);
         try {
             Statement stmt;
             ResultSet rs;
             Connection con = ds.getConnection();
             //Class.forName(getDriver());
             //con = DriverManager.getConnection(
             //        getUrl(), getUsuario(), getClave());
             stmt = con.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY);
             String query = "SELECT CODUSER, CLUSER, B_CODROL, DESUSER, MAIL, SUCURSAL";
	                query += " FROM SHABVT002";
	                query += " WHERE CODUSER = '" + user.toUpperCase() + "'";       		
             
             //System.out.println(query);
             try {
                 rs = stmt.executeQuery(query);
                 rows = 1;
                 rs.last();
                 rows = rs.getRow();
                 //System.out.println(rows);

                 ResultSetMetaData rsmd = rs.getMetaData();
                 columns = rsmd.getColumnCount();
                 //System.out.println(columns);
                 arr = new String[rows][columns];

                 int i = 0;
                 rs.beforeFirst();
                 while (rs.next()) {
                     for (int j = 0; j < columns; j++) {
                         arr[i][j] = rs.getString(j + 1);
                     }
                     i++;
                 }
             } catch (SQLException e) {
             }
             stmt.close();
             con.close();

         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     /**
      * @return Retorna el arreglo
      **/
     public String[][] getArray(){
     	return arr;
     }

     /**
      * @return Retorna número de filas
      **/
     public int getRows(){
     	return rows;
     }
     /**
      * @return Retorna número de columnas
      **/
     public int getColumns(){
     	return columns;
     }

   	private void limpiarValores() {
 		// TODO Auto-generated method stub
   		coduser = "";
   		desuser = "";
   		cluser = "";
   		b_codrol = "";
   		cluserold = "";
   		mail = "";
 	}
   	
    public void reset() {
    	// TODO Auto-generated method stub
  		b_codrol = null;   		
    }    
   	
   	/**
     * Envía notificación a usuario al cambiar la contraseña
    **/
    private void ChangePassNotification2(String mail, String clave) {
    	try {
    		Context initContext = new InitialContext();     
        	Session session = (Session) initContext.lookup(JNDIMAIL);
    			// Crear el mensaje a enviar
    			MimeMessage mm = new MimeMessage(session);
    			// Establecer las direcciones a las que será enviado
    			// el mensaje (test2@gmail.com y test3@gmail.com en copia
    			// oculta)
    			// mm.setFrom(new
    			// InternetAddress("opennomina@dvconsultores.com"));
    			mm.addRecipient(Message.RecipientType.TO,
    					new InternetAddress(mail));
	        
    			// Establecer el contenido del mensaje
    			mm.setSubject(getMessage("mailUserUserChgPass"));
    			mm.setText(getMessage("mailNewUserMsj2"));
    			
    			// use this is if you want to send html based message
                mm.setContent(getMessage("mailNewUserMsj6") + " " + coduser.toUpperCase() + " / " + clave + "<br/><br/> " + getMessage("mailNewUserMsj2"), "text/html; charset=utf-8");

    			// Enviar el correo electrónico
    			Transport.send(mm);
    			//System.out.println("Correo enviado exitosamente");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
     }
   	
   	
   	//Recuperación de contraseñas
   	/**
     * Recuperación de contraseña por parte del usuario
     * @throws NamingException 
   	 * @throws ClassNotFoundException 
     * @throws IOException 
     **/ 
  	public void reqChgpass() throws NamingException, SQLException, ClassNotFoundException{
  			try {
  	        	Context initContext = new InitialContext();     
  	        	DataSource ds = (DataSource) initContext.lookup(JNDI);
  	        	con = ds.getConnection();
  	        
  	        randomKey = "CA"+md.randomKey();
  	        	
  			String query = "UPDATE shabvt002";
  			       query+= " set cluser = '" + md.getStringMessageDigest(randomKey.toUpperCase(),StringMD.SHA256) + "'";
  			       query+= " where coduser = '" +  coduser.toUpperCase() + "'";
  			pstmt = con.prepareStatement(query); 
  			//System.out.println(query);
  			//System.out.println("usuario: " + coduser.toUpperCase());
  			Bvt002 seg = new Bvt002(); // Crea objeto para el login
  			int rows = 0;
  			// LLama al método que retorna el usuario y la contraseña
  			seg.selectLogin(coduser.toUpperCase(), JNDI);
  			rows = seg.getRows();
  			String[][] vltabla = seg.getArray();
  			//System.out.println("Mail: " + vltabla[0][4].toLowerCase());
        	//System.out.println("RandomKey: " + randomKey);
            //Valida que exista el usuario
  			if (rows == 0) {
  				msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessage("noreg"), "");

  			} else {
  			pstmt.executeUpdate();

            	msj = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage("chgpass7"), "");
            	ChangePassNotification2(vltabla[0][4].toLowerCase(), randomKey);
            	
            cluser= "";
  			}
  			} catch (SQLException e) {
                e.printStackTrace();
                msj = new FacesMessage(FacesMessage.SEVERITY_FATAL,  e.getMessage(), "");
            }
  			
  			pstmt.close();
            con.close();
              
  		FacesContext.getCurrentInstance().addMessage(null, msj); 
  	}
	
 	/**
     * Leer Datos de nominas para asignar a menucheck
     * @throws NamingException 
	 * @throws SQLException 
     * @throws IOException 
     **/ 	
  	public void selectSuc() throws NamingException, SQLException  {
  		
  		//System.out.println("llame al metodo");
  		
  		Context initContext = new InitialContext();     
    	DataSource ds = (DataSource) initContext.lookup(JNDI);
        con = ds.getConnection();	   		

 		String query = "SELECT A.CODSUC, A.CODSUC||' - '||A.DESSUC AS SUCURSAL";
	           query += " FROM NM_TRABAJADOR@INFOCENT_CALENDARIO A";
	           query += " WHERE A.CODSUC IS NOT NULL";
	           query += " GROUP BY A.CODSUC, A.DESSUC";
	           query += " ORDER BY A.CODSUC";
        
        pstmt = con.prepareStatement(query);		
        r =  pstmt.executeQuery();
        //System.out.println(query);

        while (r.next()){
        	String codsuc = new String(r.getString(1));
        	String dessuc = new String(r.getString(2));
        	
            //System.out.println("codsuc: " + codsuc);
            //System.out.println("dessuc: " + dessuc);
            
            listSuc.put(dessuc, codsuc);
            sorted = sortByValues(listSuc);
            //System.out.println(query);
            
        }
        //Cierra las conecciones
        pstmt.close();
        con.close();
        
  	}
}