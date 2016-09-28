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
public class Shametas extends Bd implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LazyDataModel<Shametas> lazyModel;  
	
	
	/**
	 * @return the lazyModel
	 */
	public LazyDataModel<Shametas> getLazyModel() {
		return lazyModel;
	}	

@PostConstruct
public void init() {
	//System.out.println("entre al metodo INIT");
	lazyModel  = new LazyDataModel<Shametas>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 7217573531435419432L;
		
        @Override
		public List<Shametas> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) { 
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
}


	/**
	 * 
	 */
	private String anio = "";
	private String mes = "";
	private String numtrabajadores = "";
	private String sucursal = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sucursal"); //Usuario logeado
	private String headcount = "";
	private String promedio = "";
	private String reuniones = "";
	private String cumplimiento = "";
	private Object filterValue = "";
	private List<Shametas> list = new ArrayList<Shametas>();
	private int validarOperacion = 0;
	//private String instancia = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("instancia"); //Usuario logeado
	private String zanio = "";
	private String zmes = "";
	private String znumtrabajadores = "";
	private String zsucursal = "";
	private String zheadcount = "";
	private String zpromedio = "";
	private String zreuniones = "";
	private String zcumplimiento = "";
	private String zuno = "";
	private String zdos = "";
	private String ztres = "";
	private String zdelete = "";
	private String zdessuc = "";

	public String getZdessuc() {
		return zdessuc;
	}

	public void setZdessuc(String zdessuc) {
		this.zdessuc = zdessuc;
	}

	public String getZdelete() {
		return zdelete;
	}

	public void setZdelete(String zdelete) {
		this.zdelete = zdelete;
	}

	public String getZuno() {
		return zuno;
	}

	public void setZuno(String zuno) {
		this.zuno = zuno;
	}

	public String getZdos() {
		return zdos;
	}

	public void setZdos(String zdos) {
		this.zdos = zdos;
	}

	public String getZtres() {
		return ztres;
	}

	public void setZtres(String ztres) {
		this.ztres = ztres;
	}

	public String getNumtrabajadores() {
		return numtrabajadores;
	}

	public void setNumtrabajadores(String numtrabajadores) {
		this.numtrabajadores = numtrabajadores;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getHeadcount() {
		return headcount;
	}

	public void setHeadcount(String headcount) {
		this.headcount = headcount;
	}

	public String getPromedio() {
		return promedio;
	}

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}

	public String getReuniones() {
		return reuniones;
	}

	public void setReuniones(String reuniones) {
		this.reuniones = reuniones;
	}

	public String getCumplimiento() {
		return cumplimiento;
	}

	public void setCumplimiento(String cumplimiento) {
		this.cumplimiento = cumplimiento;
	}

	public String getZnumtrabajadores() {
		return znumtrabajadores;
	}

	public void setZnumtrabajadores(String znumtrabajadores) {
		this.znumtrabajadores = znumtrabajadores;
	}

	public String getZsucursal() {
		return zsucursal;
	}

	public void setZsucursal(String zsucursal) {
		this.zsucursal = zsucursal;
	}

	public String getZheadcount() {
		return zheadcount;
	}

	public void setZheadcount(String zheadcount) {
		this.zheadcount = zheadcount;
	}

	public String getZpromedio() {
		return zpromedio;
	}

	public void setZpromedio(String zpromedio) {
		this.zpromedio = zpromedio;
	}

	public String getZreuniones() {
		return zreuniones;
	}

	public void setZreuniones(String zreuniones) {
		this.zreuniones = zreuniones;
	}

	public String getZcumplimiento() {
		return zcumplimiento;
	}

	public void setZcumplimiento(String zcumplimiento) {
		this.zcumplimiento = zcumplimiento;
	}

	public String getanio() {
		return anio;
	}

	public void setanio(String anio) {
		this.anio = anio;
	}

	public String getmes() {
		return mes;
	}

	public void setmes(String mes) {
		this.mes = mes;
	}

	public String getZanio() {
		return zanio;
	}

	public void setZanio(String zanio) {
		this.zanio = zanio;
	}

	public String getZmes() {
		return zmes;
	}

	public void setZmes(String zmes) {
		this.zmes = zmes;
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
 * <b>Parametros del Metodo:<b> String codcat1, String mesat1 unidos como un solo string.<br>
 * String pool, String login.<br><br>
 **/
		public void insert() throws  NamingException {   	
	//System.out.println("entre al metodo INSERT");	
    try {
    	Context initContext = new InitialContext();     
 		DataSource ds = (DataSource) initContext.lookup(JNDI);
        con = ds.getConnection();
        
        if(sucursal==null){
 			sucursal = " - ";
 		}
 		if(sucursal==""){
 			sucursal = " - ";
 		}  
        String[] vecsuc = sucursal.split("\\ - ", -1);

        String query = "INSERT INTO SHAMETAS VALUES (?,?,?,?,?,?,?,?,?,'" + getFecha() + "',?,'" + getFecha() + "')";
        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, Integer.parseInt(anio));
        pstmt.setInt(2, Integer.parseInt(mes));
        pstmt.setInt(3, Integer.parseInt(numtrabajadores));
        pstmt.setString(4, vecsuc[0].toUpperCase());
        pstmt.setInt(5, Integer.parseInt(zuno));
        pstmt.setFloat(6, Float.parseFloat(zdos));
        pstmt.setInt(7, Integer.parseInt(reuniones));
        pstmt.setInt(8, Integer.parseInt(cumplimiento));
        pstmt.setString(9, login);
        pstmt.setString(10, login);            
        
        System.out.println(query);
        System.out.println("anio: " + anio);
        System.out.println("mes: " + mes);
        System.out.println("numtrabajadores: " + numtrabajadores);
        System.out.println("sucursal: " + vecsuc[0]);
        System.out.println("headcount: " + zuno);
        System.out.println("promedio: " + zdos);
        System.out.println("reuniones: " + reuniones);
        System.out.println("cumplimiento: " + cumplimiento);
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

        	String query = "DELETE from SHAMETAS WHERE anio||mes||sucursal in (" + param + ") ";
        		        	
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
 * <b>Parametros del Metodo:<b> String codcat1, String mesat1 unidos como un solo string.<br>
 * String pool, String login.<br><br>
 **/
public void update() throws  NamingException {
	//System.out.println("entre al metodo UPDATE");
     try { 	 
    	 Context initContext = new InitialContext();     
  		DataSource ds = (DataSource) initContext.lookup(JNDI); 		
  		con = ds.getConnection();	
  		
        if(sucursal==null){
 			sucursal = " - ";
 		}
 		if(sucursal==""){
 			sucursal = " - ";
 		}  
        String[] vecsuc = sucursal.split("\\ - ", -1);
  		
        String query = "UPDATE SHAMETAS A";
         query += " SET A.NUMTRABAJADOR = ?, ";
         query += " A.HEADCOUNT = ?,";
         query += " A.PROMEDIO = ?,";
         query += " A.REUNIONES = ?,";
         query += " A.CUMPLIMIENTO = ?,";
         query += " A.USRACT = ?,";
         query += " A.FECACT = '" + getFecha() + "'";
         query += " WHERE A.ANIO = ? ";
         query += " AND A.MES = ? ";
         query += " AND A.SUCURSAL = ? ";

        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, Integer.parseInt(numtrabajadores));
        pstmt.setInt(2, Integer.parseInt(headcount));
        pstmt.setInt(3, Integer.parseInt(promedio));
        pstmt.setInt(4, Integer.parseInt(reuniones));
        pstmt.setInt(5, Integer.parseInt(cumplimiento));
        pstmt.setString(6, login);
        pstmt.setInt(7, Integer.parseInt(anio));
        pstmt.setInt(8, Integer.parseInt(mes));
        pstmt.setString(9, vecsuc[0].toUpperCase());

        //System.out.println(query);
        //System.out.println(anio);
        //System.out.println(mes);
        
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

	public void guardar() throws NamingException, SQLException{   	
		if(validarOperacion==0){
			insert();
		} else {
			update();
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

		if(sucursal==null){
 			sucursal = " - ";
 		}
 		if(sucursal==""){
 			sucursal = " - ";
 		}  
        String[] vecsuc = sucursal.split("\\ - ", -1);
        
		//Consulta paginada
     String query = "SELECT * FROM"; 
	    query += "(select query.*, rownum as rn from";
		query += "(SELECT A.ANIO, A.MES, A.NUMTRABAJADOR, A.SUCURSAL, A.HEADCOUNT, A.PROMEDIO, A.REUNIONES, A.CUMPLIMIENTO, B.DESSUC ";
	    query += " FROM SHAMETAS A, SHASUCURSAL B";
	    query += " WHERE A.SUCURSAL = B.CODSUC";
	    query += " AND A.ANIO||A.MES||A.NUMTRABAJADOR||A.SUCURSAL||A.HEADCOUNT||A.PROMEDIO||A.REUNIONES||A.CUMPLIMIENTO||B.DESSUC  like '%" + ((String) filterValue).toUpperCase() + "%'";
	    query += " AND A.SUCURSAL LIKE '" + vecsuc[0].toUpperCase() + "%'";
	    query += " GROUP BY A.ANIO, A.MES, A.NUMTRABAJADOR, A.SUCURSAL, A.HEADCOUNT, A.PROMEDIO, A.REUNIONES, A.CUMPLIMIENTO, B.DESSUC ";
	    query += ")query ) " ;
	    query += " WHERE ROWNUM <="+pageSize;
	    query += " AND rn > ("+ first +")";
	    query += " ORDER BY  " + sortField.replace("z", "");

    pstmt = con.prepareStatement(query);
    //System.out.println(query);
		
    r =  pstmt.executeQuery();
    
    while (r.next()){
 	Shametas select = new Shametas();
 	select.setZanio(r.getString(1));
 	select.setZmes(r.getString(2));
 	select.setZnumtrabajadores(r.getString(3));
 	select.setZsucursal(r.getString(4));
 	select.setZheadcount(r.getString(5));
 	select.setZpromedio(r.getString(6));
 	select.setZreuniones(r.getString(7));
 	select.setZcumplimiento(r.getString(8));
 	select.setZdelete(r.getString(1)+ "" + r.getString(2)+ "" + r.getString(4));
 	select.setZdessuc(r.getString(4)+ " - " + r.getString(9));
   	
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

      		if(sucursal==null){
     			sucursal = " - ";
     		}
     		if(sucursal==""){
     			sucursal = " - ";
     		}  
            String[] vecsuc = sucursal.split("\\ - ", -1);
            
     		//Consulta paginada
     		String query = "SELECT COUNT_SHAMETAS('" + ((String) filterValue).toUpperCase() + "','" + vecsuc[0].toUpperCase() + "') FROM DUAL";

           
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
  		anio = "";
  		mes = "";
  		numtrabajadores = "";
  		sucursal = "";
  		headcount = "";
  		promedio = "";
  		reuniones = "";
  		cumplimiento = "";
  		zuno = "";
  		zdos = "";
  		validarOperacion = 0;
	}
  	
    public void reset() {
    	// TODO Auto-generated method stub
  		sucursal = null;
    }    
  	

	  public void onselectSuc() {	  
		        
          if(sucursal==null){
          	sucursal = " - ";
          }  
          
          String[] vecsuc = sucursal.split("\\ - ", -1);
          
          
          HttpServletRequest rq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
          String ntrabajador = rq.getParameter("formshametas:numtrabajadores");
			//Consulta que hace la seleccion automatica para los inputtext de los empelados, valores nombre completo, genero, cargo!!!
        
          //System.out.println("numtrabajadores: " + ntrabajador);

          //System.out.println("anio: " + anio);
          //System.out.println("mes: " + mes);
   	     String query = " SELECT COUNT(A.FICTRA) AS HEADCOUNT, CASE WHEN COUNT(A.FICTRA) = 0 THEN 0 WHEN COUNT(A.FICTRA) IS NULL THEN 0 ELSE TRUNC(" + ntrabajador + "/COUNT(A.FICTRA),2) END AS PROMEDIO";
 	  		    query += " FROM NM_TRABAJADOR@INFOCENT_CALENDARIO A, SHASUCURSAL B ";
 	  		    query += " WHERE A.CODSUC = B.SUCURSAL";
 	  		    query += " AND B.CODSUC like '" + vecsuc[0].toUpperCase() + "%'";
	  		    query += " AND A.FECRET IS NULL ";
 	  		    query += " ORDER BY 1";
 	  		    
      		 PntGenerica select = new PntGenerica();
               try {
  				select.selectPntGenerica(query,JNDI);
  			} catch (NamingException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
               int rows = select.getRows();
               String vltabla[][] = select.getArray();
               if(rows>0){
              	 zuno = vltabla[0][0];
              	 zdos = vltabla[0][1];
              	 //ztres = vltabla[0][2];
               }
               //System.out.println(query);
               //System.out.println("zuno:" + zuno);
               //System.out.println("zuno:" + zdos);
               //System.out.println("zuno:" + ztres);
       	}
		   
       
}
