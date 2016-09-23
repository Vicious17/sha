/*
 *  Copyright (C) 2011  ANDRES DOMINGUEZ

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.sha.util.PntGenerica;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author Mauricio
 */
@ManagedBean
@ViewScoped
public class Shasuc extends Bd implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LazyDataModel<Shasuc> lazyModel;  
	
	
	/**
	 * @return the lazyModel
	 */
	public LazyDataModel<Shasuc> getLazyModel() {
		return lazyModel;
	}	

@PostConstruct
public void init() {
	//System.out.println("entre al metodo INIT");
	lazyModel  = new LazyDataModel<Shasuc>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 7217573531435419432L;
		
        @Override
		public List<Shasuc> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) { 
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
	//llena el chkboxmnu
			try {
				selectSuc();
			} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} 
}


	/**
	 * 
	 */
	private String codigo = "";
	private String desc = "";
	private Object filterValue = "";
	private List<Shasuc> list = new ArrayList<Shasuc>();
	private int validarOperacion = 0;
	private Map<String,String> listSuc = new HashMap<String, String>();   //Lista de sucursales disponibles para acceso a seguridad 
	private List<String> selectedSuc;   //Listado de sucursales seleccionadas
	private Map<String, String> sorted;
	private String exito = "exito";
	//private String instancia = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("instancia"); //Usuario logeado
	private String zcodigo = "";
	private String zdesc = "";
	private String zcodsucspi = "";
	private String zdessucspi = "";
	private String zdelete = "";

	public String getZdelete() {
		return zdelete;
	}

	public void setZdelete(String zdelete) {
		this.zdelete = zdelete;
	}

	public String getZcodsucspi() {
		return zcodsucspi;
	}

	public void setZcodsucspi(String zcodsucspi) {
		this.zcodsucspi = zcodsucspi;
	}

	public String getZdessucspi() {
		return zdessucspi;
	}

	public void setZdessucspi(String zdessucspi) {
		this.zdessucspi = zdessucspi;
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
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getZcodigo() {
		return zcodigo;
	}

	public void setZcodigo(String zcodigo) {
		this.zcodigo = zcodigo;
	}

	public String getZdesc() {
		return zdesc;
	}

	public void setZdesc(String zdesc) {
		this.zdesc = zdesc;
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
	
	//Formateador de la fecha sdfecha
    //java.text.SimpleDateFormat sdfecha = new java.text.SimpleDateFormat("dd/MM/yyyy", locale);
    //String fecha = sdfecha.format(fecact); //Fecha formateada para insertar en tablas


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Variables seran utilizadas para capturar mensajes de errores de Oracle y parametros de metodos
	FacesMessage msj = null;
	PntGenerica consulta1 = new PntGenerica();
	boolean vGacc; //Validador de opciones del menú
	private int rows; //Registros de tabla Sybase
	//private int rows1; //Registros de tabla oracle
	private String login = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //Usuario logeado
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	//Coneccion a base de datos
	//Pool de conecciones JNDI
		Connection con;
		PreparedStatement pstmt = null;
		ResultSet r;


/**
 * Inserta categoria1.
 * <p>
 * <b>Parametros del Metodo:<b> String codcat1, String descat1 unidos como un solo string.<br>
 * String pool, String login.<br><br>
 **/
public void insert(String pcodsuc) throws  NamingException {   	
	//System.out.println("entre al metodo INSERT");	
    try {
    	Context initContext = new InitialContext();     
 		DataSource ds = (DataSource) initContext.lookup(JNDI);
        con = ds.getConnection();
                           
        String query = "INSERT INTO SHASUCURSAL VALUES (?,?,?,'" + getFecha() + "',?,'" + getFecha() + "',?)";

        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, Integer.parseInt(codigo));
        pstmt.setString(2, desc.toUpperCase());
        pstmt.setString(3, login);
        pstmt.setString(4, login);
        pstmt.setString(5, pcodsuc.toUpperCase());

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

        	String query = "DELETE from SHASUCURSAL WHERE CODSUC||SUCURSAL in (" + param + ") ";
        		        	
            pstmt = con.prepareStatement(query);
            //System.out.println(query);

            try {
                //Avisando
                pstmt.executeUpdate();
                msj = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage("msnDelete"), "");
                limpiarValores();	
            } catch (SQLException e) {
                e.printStackTrace();
               	
                	//System.out.println("no se cumple la condicion y muestro otro msg");
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
 * Actualiza categoria1
 * <b>Parametros del Metodo:<b> String codcat1, String descat1 unidos como un solo string.<br>
 * String pool, String login.<br><br>
 **/
public void update() throws  NamingException {
	//System.out.println("entre al metodo UPDATE");
     try { 	 
    	 Context initContext = new InitialContext();     
  		DataSource ds = (DataSource) initContext.lookup(JNDI);
  		
  		con = ds.getConnection();		
  		
        String query = "UPDATE SHASUCURSAL";
         query += " SET DESSUC = ?, ";
         query += " USRACT = ?,";
         query += " FECACT = '" + getFecha() + "'";
         query += " WHERE CODSUC = ? ";

        pstmt = con.prepareStatement(query);
        pstmt.setString(1, desc.toUpperCase());
        pstmt.setString(2, login);
        pstmt.setString(3, codigo.toUpperCase());           

        //System.out.println(query);
        //System.out.println(codigo);
        //System.out.println(desc);
        
        // Antes de ejecutar valida si existe el registro en la base de Datos.
        try {
            //Avisando
            pstmt.executeUpdate();
            if(pstmt.getUpdateCount()==0){
            	msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessage("msnNoUpdate"), "");
            } else {
            	msj = new FacesMessage(FacesMessage.SEVERITY_INFO,  getMessage("msnUpdate"), "");
            }
            limpiarValores();
        } catch (SQLException e) {
        	msj = new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), "");
        }
        pstmt.close();
        con.close();
    } catch (Exception e) {
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
 * Leer Datos de paises
 * @throws NamingException 
* @throws IOException 
 **/ 	
	public void select(int first, int pageSize, String sortField, Object filterValue) throws SQLException, ClassNotFoundException, NamingException {
  		
		//System.out.println("entre al metodo SELECT");	
		Context initContext = new InitialContext();     
		DataSource ds = (DataSource) initContext.lookup(JNDI);
		con = ds.getConnection();		
		//Reconoce la base de datos de conección para ejecutar el query correspondiente a cada uno
		DatabaseMetaData databaseMetaData = con.getMetaData();
		productName    = databaseMetaData.getDatabaseProductName();//Identifica la base de datos de conección
		
		//Consulta paginada
     String query = "SELECT * FROM"; 
	    query += "(select query.*, rownum as rn from";
		query += "(SELECT A.CODSUC, A.DESSUC, A.SUCURSAL, B.DESSUC AS DESCR";
	    query += " FROM SHASUCURSAL A, NM_TRABAJADOR@INFOCENT_CALENDARIO B";
	    query += " WHERE A.SUCURSAL = B.CODSUC";
	    query += " AND A.CODSUC || A.DESSUC || A.SUCURSAL || B.DESSUC like '%" + ((String) filterValue).toUpperCase() + "%'";
	    query += " GROUP BY A.CODSUC, A.DESSUC, A.SUCURSAL, B.DESSUC";
	    query += " ORDER BY TO_NUMBER(A.CODSUC))query ) " ;
	    query += " WHERE ROWNUM <="+pageSize;
	    query += " AND rn > ("+ first +")";
	    query += " ORDER BY  " + sortField.replace("z", "");

    pstmt = con.prepareStatement(query);
    System.out.println(query);
		
    r =  pstmt.executeQuery();
    
    while (r.next()){
 	Shasuc select = new Shasuc();
 	select.setZcodigo(r.getString(1));
 	select.setZdesc(r.getString(2));
 	select.setZcodsucspi(r.getString(3));
 	select.setZdessucspi(r.getString(3) + " - " + r.getString(4));
 	select.setZdelete(r.getString(1) + "" + r.getString(3));
   	
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

    public void counter(Object filterValue) throws SQLException, NamingException {
    	//System.out.println("entre al metodo COUNTER");
        try {	
    
       	    Context initContext = new InitialContext();     
      		DataSource ds = (DataSource) initContext.lookup(JNDI);

      		con = ds.getConnection();

     		//Consulta paginada
     		String query = "SELECT COUNT_SHASUCURSAL('" + ((String) filterValue).toUpperCase() + "') FROM DUAL";

           
           pstmt = con.prepareStatement(query);
           //System.out.println(query);
           //System.out.println(veccodven[0]);

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
  	 * @return the rows
  	 */
  	public int getRows() {
  		return rows;
  	}

  	private void limpiarValores() {
		// TODO Auto-generated method stub
  		codigo = "";
  		desc = "";
  		validarOperacion = 0;
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
