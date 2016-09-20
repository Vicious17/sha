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
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;

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
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author Mauricio
 */
@ManagedBean
@ViewScoped
public class Shaincidencias extends Bd implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LazyDataModel<Shaincidencias> lazyModel;  
	
	
	/**
	 * @return the lazyModel
	 */
	public LazyDataModel<Shaincidencias> getLazyModel() {
		return lazyModel;
	}	

@PostConstruct
public void init() {
	//System.out.println("entre al metodo INIT");
	lazyModel  = new LazyDataModel<Shaincidencias>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 7217573531435419432L;
		
        @Override
		public List<Shaincidencias> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) { 
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
	private String ci = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ci"); //Usuario logeado
	private String nombre = "";
	private String cargo = "";
	private String genero = "";
	private String turno = "";
	private String area = "";
	private String tipoin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipoin"); //Usuario logeado
	private String tipoac = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipoac"); //Usuario logeado
	private String tipole = "";
	private String cuerpo = "";
	private String hechos = "";
	private String inpsasel = "";
	private String regist = "";	
	private String zuno = "";
	private String zdos = "";
	private String ztres = "";
	private Date fevento = new Date();
	private String zturno = "";
	private String zarea = "";
	private String ztipoin = "";
	private String ztipoac = "";
	private String ztipole = "";
	private String zcuerpo = "";
	private String zhechos = "";
	private String zinpsasel = "";
	private String zci = "";
	private String znombre = "";
	private String zfevento = "";
	private String zincap = "";
	private String zdescr = "";
	private String zcompany = "";
	private String znomina = "";
	private String zsucursal = "";
	private String zregist = "";
	private String zdepartamento = "";
	private Object filterValue = "";
	private List<Shaincidencias> list = new ArrayList<Shaincidencias>();
	private int validarOperacion = 0;
	PntGenerica consulta = new PntGenerica();
	String[][] tabla;


	public String getRegist() {
		return regist;
	}

	public void setRegist(String regist) {
		this.regist = regist;
	}

	public String getZregist() {
		return zregist;
	}

	public void setZregist(String zregist) {
		this.zregist = zregist;
	}

	public String getZcompany() {
		return zcompany;
	}

	public void setZcompany(String zcompany) {
		this.zcompany = zcompany;
	}

	public String getZnomina() {
		return znomina;
	}

	public void setZnomina(String znomina) {
		this.znomina = znomina;
	}

	public String getZsucursal() {
		return zsucursal;
	}

	public void setZsucursal(String zsucursal) {
		this.zsucursal = zsucursal;
	}

	public String getZdepartamento() {
		return zdepartamento;
	}

	public void setZdepartamento(String zdepartamento) {
		this.zdepartamento = zdepartamento;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getZdescr() {
		return zdescr;
	}

	public void setZdescr(String zdescr) {
		this.zdescr = zdescr;
	}

	public String getZincap() {
		return zincap;
	}

	public void setZincap(String zincap) {
		this.zincap = zincap;
	}

	public String getZfevento() {
		return zfevento;
	}

	public void setZfevento(String zfevento) {
		this.zfevento = zfevento;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTipoin() {
		return tipoin;
	}

	public void setTipoin(String tipoin) {
		this.tipoin = tipoin;
	}

	public String getTipoac() {
		return tipoac;
	}

	public void setTipoac(String tipoac) {
		this.tipoac = tipoac;
	}

	public String getTipole() {
		return tipole;
	}

	public void setTipole(String tipole) {
		this.tipole = tipole;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public String getHechos() {
		return hechos;
	}

	public void setHechos(String hechos) {
		this.hechos = hechos;
	}

	public String getInpsasel() {
		return inpsasel;
	}

	public void setInpsasel(String inpsasel) {
		this.inpsasel = inpsasel;
	}

	public String getZturno() {
		return zturno;
	}

	public void setZturno(String zturno) {
		this.zturno = zturno;
	}

	public String getZarea() {
		return zarea;
	}

	public void setZarea(String zarea) {
		this.zarea = zarea;
	}

	public String getZtipoin() {
		return ztipoin;
	}

	public void setZtipoin(String ztipoin) {
		this.ztipoin = ztipoin;
	}

	public String getZtipoac() {
		return ztipoac;
	}

	public void setZtipoac(String ztipoac) {
		this.ztipoac = ztipoac;
	}

	public String getZtipole() {
		return ztipole;
	}

	public void setZtipole(String ztipole) {
		this.ztipole = ztipole;
	}

	public String getZcuerpo() {
		return zcuerpo;
	}

	public void setZcuerpo(String zcuerpo) {
		this.zcuerpo = zcuerpo;
	}

	public String getZhechos() {
		return zhechos;
	}

	public void setZhechos(String zhechos) {
		this.zhechos = zhechos;
	}

	public String getZinpsasel() {
		return zinpsasel;
	}

	public void setZinpsasel(String zinpsasel) {
		this.zinpsasel = zinpsasel;
	}

	public Date getFevento() {
		return fevento;
	}

	public void setFevento(Date fevento) {
		this.fevento = fevento;
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

	public String getZuno() {
		return zuno;
	}

	public void setZuno(String zuno) {
		this.zuno = zuno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getZnombre() {
		return znombre;
	}

	public void setZnombre(String znombre) {
		this.znombre = znombre;
	}

	public String getci() {
		return ci;
	}

	public void setci(String ci) {
		this.ci = ci;
	}

	public String getZci() {
		return zci;
	}

	public void setZci(String zci) {
		this.zci = zci;
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
public void insert() throws  NamingException {   	
	//System.out.println("entre al metodo INSERT");	
    try {
    	Context initContext = new InitialContext();     
 		DataSource ds = (DataSource) initContext.lookup(JNDI);
        con = ds.getConnection();
        
        if(ztres==null){
 			ztres = " - ";
 		}
 		if(ztres==""){
 			ztres = " - ";
 		}  
 		if(tipoin==null){
 			tipoin = " - ";
 		}
 		if(tipoin==""){
 			tipoin = " - ";
 		}  
 		if(tipoac==null){
 			tipoac = " - ";
 		}
 		if(tipoac==""){
 			tipoac = " - ";
 		}  
        String[] vecztres = ztres.split("\\ - ", -1);
        String[] vectipoac = tipoac.split("\\ - ", -1);
        String[] vecincap = tipoin.split("\\ - ", -1);
                
 		String vlfecha;
 		java.text.SimpleDateFormat sdfecha_es = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", locale );
    	java.text.SimpleDateFormat sdfecha_en = new java.text.SimpleDateFormat("dd/MMM/yyyy HH:mm", locale );
    	String to_date;
    	if(SHA_BD_LANG.equals("es")){
    		vlfecha = sdfecha_es.format(fevento);
    		to_date = "to_date('" + vlfecha + "', 'dd/mm/yyyy hh24:mi')";
    	} else {
    		vlfecha = sdfecha_en.format(fevento);
    		to_date = "to_date('" + vlfecha + "', 'dd/mon/yyyy hh24:mi')";
    	}
        
        String query = "INSERT INTO SHAINCIDENCIAS VALUES (?,?,?,?," + to_date + ",?,?,?,?,?,?,?,?,?,'" + getFecha() + "',?,'" + getFecha() + "',NULL)";
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, ci.toUpperCase());
        pstmt.setString(2, zuno.toUpperCase());
        pstmt.setString(3, zdos.toUpperCase());
        pstmt.setString(4, vecztres[0].toUpperCase());
        pstmt.setString(5, turno.toUpperCase());
        pstmt.setString(6, area.toUpperCase());
        pstmt.setString(7, vecincap[0].toUpperCase());
        pstmt.setString(8, vectipoac[0].toUpperCase());
        pstmt.setString(9, tipole.toUpperCase());
        pstmt.setString(10, cuerpo.toUpperCase());
        pstmt.setString(11, hechos.toUpperCase());
        pstmt.setString(12, inpsasel.toUpperCase());
        pstmt.setString(13, login);
        pstmt.setString(14, login);            
        
        //System.out.println(query);
        //System.out.println("ci: " + ci);
        //System.out.println("zuno: " + zuno);
        //System.out.println("zdos: " + zdos);
        //System.out.println("ztres: " + vecztres[0]);
        //System.out.println("fecha: " + to_date);
        //System.out.println("turno: " + turno);
        //System.out.println("area: " + area);
        //System.out.println("tipoin: " + tipoin);
        //System.out.println("tipoac: " + tipoac);
        //System.out.println("tipole: " + tipole);
        //System.out.println("cuerpo: " + cuerpo);
        //System.out.println("hechos: " + hechos);
        //System.out.println("inpsasel: " + inpsasel);

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

        	String query = "DELETE from SHAINCIDENCIAS WHERE REGIST in (" + param + ") ";
        		        	
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
  		
  		String vlfecha;
 		java.text.SimpleDateFormat sdfecha_es = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", locale );
    	java.text.SimpleDateFormat sdfecha_en = new java.text.SimpleDateFormat("dd/MMM/yyyy HH:mm", locale );
    	String to_date;
    	if(SHA_BD_LANG.equals("es")){
    		vlfecha = sdfecha_es.format(fevento);
    		to_date = "to_date('" + vlfecha + "', 'dd/mm/yyyy hh24:mi')";
    	} else {
    		vlfecha = sdfecha_en.format(fevento);
    		to_date = "to_date('" + vlfecha + "', 'dd/mon/yyyy hh24:mi')";
    	}
       
 		if(tipoin==null){
 			tipoin = " - ";
 		}
 		if(tipoin==""){
 			tipoin = " - ";
 		}  
 		if(tipoac==null){
 			tipoac = " - ";
 		}
 		if(tipoac==""){
 			tipoac = " - ";
 		}  
        String[] vectipoac = tipoac.split("\\ - ", -1);
        String[] vecincap = tipoin.split("\\ - ", -1);
    	
        String query = "UPDATE SHAINCIDENCIAS A";
         query += " SET A.FECHA = " + to_date + ", ";
         query += " A.TURNO = ?,";
         query += " A.AREAEVENT = ?,";
         query += " A.TIPOINCAP = ?,";
         query += " A.TIPOACC = ?,";
         query += " A.TIPOLES = ?,";
         query += " A.UBILES = ?,";
         query += " A.DESCHEC = ?,";
         query += " A.INPSASEL = ?,";
         query += " USRACT = ?,";
         query += " FECACT = '" + getFecha() + "'";
         query += " WHERE A.REGIST = ? ";

        pstmt = con.prepareStatement(query);
        pstmt.setString(1, turno.toUpperCase());
        pstmt.setString(2, area.toUpperCase());
        pstmt.setString(3, vecincap[0].toUpperCase());
        pstmt.setString(4, vectipoac[0].toUpperCase());
        pstmt.setString(5, tipole.toUpperCase());
        pstmt.setString(6, cuerpo.toUpperCase());
        pstmt.setString(7, hechos.toUpperCase());
        pstmt.setString(8, inpsasel.toUpperCase());
        pstmt.setString(9, login);   
        pstmt.setInt(10,Integer.parseInt(regist));

        //System.out.println(query);
        //System.out.println("fecha: " + to_date);
        //System.out.println("turno: " + turno);
        //System.out.println("area: " + area);
        //System.out.println("tipoin: " + tipoin);
        //System.out.println("tipoac: " + tipoac);
        //System.out.println("tipole: " + tipole);
        //System.out.println("cuerpo: " + cuerpo);
        //System.out.println("hechos: " + hechos);
        //System.out.println("inpsasel: " + inpsasel);
        //System.out.println("registro: " + regist);
        
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
		
		String validar = "1";
		String querycon = " SELECT B_CODROL FROM SHABVT002"
						+ " WHERE CODUSER LIKE '" + login.toUpperCase() + "%'";
		
			//System.out.println(querycon);
			
			consulta.selectPntGenerica(querycon, JNDI);
			
			rows = consulta.getRows();
			tabla = consulta.getArray();
			//System.out.println(tabla[0][0]);
					
	 		if(tipoin==null){
	 			tipoin = " - ";
	 		}
	 		if(tipoin==""){
	 			tipoin = " - ";
	 		}  
	 		if(tipoac==null){
	 			tipoac = " - ";
	 		}
	 		if(tipoac==""){
	 			tipoac = " - ";
	 		}  
	 		if(ci==null){
	 			ci = "";
	 		}
	        String[] vectipoac = tipoac.split("\\ - ", -1);
	        String[] vecincap = tipoin.split("\\ - ", -1);
		
	 if (tabla[0][0].equals(validar)) {
		//System.out.println("Entre al IF");
		//Consulta paginada
     String query = "SELECT * FROM"; 
	    query += "(select query.*, rownum as rn from";
		query += "(SELECT A.CI, A.NOMBRE, A.GENERO, A.CARGO, TO_CHAR(A.FECHA,'DD/MM/YYYY HH24:MI'), A.TURNO, A.AREAEVENT, A.TIPOINCAP, A.TIPOACC, A.TIPOLES, A.UBILES, A.DESCHEC, A.INPSASEL, B.INCAP, C.DESCR, D.DESCIA, D.DESNOM, D.CODSUC||' - '||D.DESSUC AS SUCURSAL, D.CODDEP||' - '||D.DESDEP AS DEPARTAMENTO, D.DESCAR, A.REGIST";
	    query += " FROM SHAINCIDENCIAS A, SHAINCAP B, SHATIPAC C, NM_TRABAJADOR@INFOCENT_CALENDARIO D";
	    query += " WHERE A.TIPOINCAP = B.CODIGO";
	    query += " AND A.TIPOACC = C.CODIGO";
	    query += " AND A.CARGO = D.CODCAR";
	    query += " AND A.CI = D.TIPDOC||' - '||D.CEDULA";
	    query += " AND A.CI||A.NOMBRE||A.GENERO||A.CARGO||A.FECHA||A.TURNO||A.AREAEVENT||A.TIPOINCAP||A.TIPOACC||A.TIPOLES||A.UBILES||A.DESCHEC||A.INPSASEL||B.INCAP||C.DESCR||D.DESCIA||D.DESNOM||D.CODSUC||D.DESSUC||D.CODDEP||D.DESDEP||D.DESCAR like '%" + ((String) filterValue).toUpperCase() + "%'";
	    query += " AND A.CI LIKE '" + ci.toUpperCase() + "%'";
	    query += " AND A.TIPOINCAP LIKE '" + vecincap[0] + "%'";
	    query += " AND A.TIPOACC LIKE '" + vectipoac[0] + "%'";
	    query += " GROUP BY A.CI,A.NOMBRE, A.GENERO, A.CARGO, A.FECHA, A.TURNO, A.AREAEVENT, A.TIPOINCAP, A.TIPOACC, A.TIPOLES, A.UBILES, A.DESCHEC, A.INPSASEL, B.INCAP, C.DESCR, D.DESCIA, D.DESNOM, D.CODSUC, D.DESSUC, D.CODDEP, D.DESDEP, D.DESCAR, A.REGIST";
	    query += ")query ) " ;
	    query += " WHERE ROWNUM <="+pageSize;
	    query += " AND rn > ("+ first +")";
	    query += " ORDER BY  " + sortField.replace("z", "");

    pstmt = con.prepareStatement(query);
    //System.out.println(query);
		
    r =  pstmt.executeQuery();
    
    while (r.next()){
 	Shaincidencias select = new Shaincidencias();
 	select.setZci(r.getString(1));
 	select.setZuno(r.getString(2));
 	select.setZdos(r.getString(3));
 	select.setZtres(r.getString(4)+ " - " + r.getString(20));
 	select.setZfevento(r.getString(5));
 	select.setZturno(r.getString(6));
 	select.setZarea(r.getString(7));
 	select.setZtipoin(r.getString(8)+ " - " + r.getString(14));
 	select.setZtipoac(r.getString(9)+ " - " + r.getString(15));
 	select.setZtipole(r.getString(10));
 	select.setZcuerpo(r.getString(11));
 	select.setZhechos(r.getString(12));
 	select.setZinpsasel(r.getString(13));
 	select.setZincap(r.getString(14));
 	select.setZdescr(r.getString(15));
 	select.setZcompany(r.getString(16));
 	select.setZnomina(r.getString(17));
 	select.setZsucursal(r.getString(18));
 	select.setZdepartamento(r.getString(19));
 	select.setZregist(r.getString(21));

   	
    	//Agrega la lista
    	list.add(select);
    }
	}
	 
	 else {
		 	//System.out.println("Entre al ELSE");
			//Consulta paginada
	     String query = "SELECT * FROM"; 
		    query += "(select query.*, rownum as rn from";
			query += "(SELECT A.CI, A.NOMBRE, A.GENERO, A.CARGO, TO_CHAR(A.FECHA,'DD/MM/YYYY HH24:MI'), A.TURNO, A.AREAEVENT, A.TIPOINCAP, A.TIPOACC, A.TIPOLES, A.UBILES, A.DESCHEC, A.INPSASEL, B.INCAP, C.DESCR, D.DESCIA, D.DESNOM, D.CODSUC||' - '||D.DESSUC AS SUCURSAL, D.CODDEP||' - '||D.DESDEP AS DEPARTAMENTO, D.DESCAR, A.REGIST";
		    query += " FROM SHAINCIDENCIAS A, SHAINCAP B, SHATIPAC C, NM_TRABAJADOR@INFOCENT_CALENDARIO D, SHABVT002 E";
		    query += " WHERE A.TIPOINCAP = B.CODIGO";
		    query += " AND A.TIPOACC = C.CODIGO";
		    query += " AND A.CARGO = D.CODCAR";
		    query += " AND D.CODSUC = E.SUCURSAL";
		    query += " AND A.CI = D.TIPDOC||' - '||D.CEDULA";
		    query += " AND A.CI||A.NOMBRE||A.GENERO||A.CARGO||A.FECHA||A.TURNO||A.AREAEVENT||A.TIPOINCAP||A.TIPOACC||A.TIPOLES||A.UBILES||A.DESCHEC||A.INPSASEL||B.INCAP||C.DESCR||D.DESCIA||D.DESNOM||D.CODSUC||D.DESSUC||D.CODDEP||D.DESDEP||D.DESCAR like '%" + ((String) filterValue).toUpperCase() + "%'";
		    query += " AND A.CI LIKE '" + ci.toUpperCase() + "%'";
		    query += " AND A.TIPOINCAP LIKE '" + vecincap[0] + "%'";
		    query += " AND A.TIPOACC LIKE '" + vectipoac[0] + "%'";
		    query += " GROUP BY A.CI,A.NOMBRE, A.GENERO, A.CARGO, A.FECHA, A.TURNO, A.AREAEVENT, A.TIPOINCAP, A.TIPOACC, A.TIPOLES, A.UBILES, A.DESCHEC, A.INPSASEL, B.INCAP, C.DESCR, D.DESCIA, D.DESNOM, D.CODSUC, D.DESSUC, D.CODDEP, D.DESDEP, D.DESCAR, A.REGIST";
		    query += ")query ) " ;
		    query += " WHERE ROWNUM <="+pageSize;
		    query += " AND rn > ("+ first +")";
		    query += " ORDER BY  " + sortField.replace("z", "");

	    pstmt = con.prepareStatement(query);
	    //System.out.println(query);
			
	    r =  pstmt.executeQuery();
	    
	    while (r.next()){
	 	Shaincidencias select = new Shaincidencias();
	 	select.setZci(r.getString(1));
	 	select.setZuno(r.getString(2));
	 	select.setZdos(r.getString(3));
	 	select.setZtres(r.getString(4)+ " - " + r.getString(20));
	 	select.setZfevento(r.getString(5));
	 	select.setZturno(r.getString(6));
	 	select.setZarea(r.getString(7));
	 	select.setZtipoin(r.getString(8)+ " - " + r.getString(14));
	 	select.setZtipoac(r.getString(9)+ " - " + r.getString(15));
	 	select.setZtipole(r.getString(10));
	 	select.setZcuerpo(r.getString(11));
	 	select.setZhechos(r.getString(12));
	 	select.setZinpsasel(r.getString(13));
	 	select.setZincap(r.getString(14));
	 	select.setZdescr(r.getString(15));
	 	select.setZcompany(r.getString(16));
	 	select.setZnomina(r.getString(17));
	 	select.setZsucursal(r.getString(18));
	 	select.setZdepartamento(r.getString(19));
	 	select.setZregist(r.getString(21));

	   	
	    	//Agrega la lista
	    	list.add(select);
	 }
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
     		if(ci==" - "){
     			ci = "";
     		}
     		if(tipoin==null){
     			tipoin = " - ";
     		}
     		if(tipoin==""){
     			tipoin = " - ";
     		}  
	 		if(tipoac==null){
	 			tipoac = " - ";
	 		}
	 		if(tipoac==""){
	 			tipoac = " - ";
	 		}  
	        String[] vectipoac = tipoac.split("\\ - ", -1);
            String[] vecincap = tipoin.split("\\ - ", -1);
      		
     		//Consulta paginada
     		String query = "SELECT COUNT_SHAINCIDENCIAS('" + ((String) filterValue).toUpperCase() + "','" + ci.toUpperCase() + "','" + vecincap[0].toUpperCase() + "','" + vectipoac[0].toUpperCase() + "') FROM DUAL";

           
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
  	 * @return the rows
  	 */
  	public int getRows() {
  		return rows;
  	}

  	private void limpiarValores() {
		// TODO Auto-generated method stub
  		ci = "";
  		nombre = "";
  		cargo = "";
  		genero = "";
  		zuno = "";
  		zdos = "";
  		ztres = "";
  		fevento = new Date();
  		turno = "";
  		area = "";
  		tipoin = "";
  		tipoac = "";
  		tipole = "";
  		cuerpo = "";
  		hechos = "";
  		inpsasel = "";
  		validarOperacion = 0;
	}
  	
    public void reset() {
    	// TODO Auto-generated method stub
  		ci = null; 
  		tipoin = null;
  		tipoac = null;
    }    
   	
  	  public void onselectCi() {
          if(ci==null){
          	ci = " - ";
          }  

			//Consulta que hace la seleccion automatica para los inputtext de los empelados, valores nombre completo, genero, cargo!!!
          
   	     String query = "SELECT NOMBRE1||' '||NOMBRE2||' '||APELLIDO1||' '||APELLIDO2 AS NOMBRE, SEXO, CODCAR||' - '||DESCAR AS CARGO";
 	  		    query += " FROM NM_TRABAJADOR@INFOCENT_CALENDARIO";
 	  		    query += " WHERE TIPDOC||' - '||CEDULA like '" + ci.toUpperCase() + "%'";
 	  		    query += " GROUP BY NOMBRE1, NOMBRE2, APELLIDO1, APELLIDO2, SEXO, CODCAR, DESCAR";
 	  		    query += " ORDER BY NOMBRE1, NOMBRE2, APELLIDO1, APELLIDO2";
 	  		    
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
              	 ztres = vltabla[0][2];
               }
               //System.out.println(query);
               //System.out.println("zuno:" + zuno);
               //System.out.println("zuno:" + zdos);
               //System.out.println("zuno:" + ztres);
       	}
       
}
