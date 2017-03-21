/*
 *  Copyright (C) 2011  MAURICIO ALBANESE

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
	private String ci = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ci"); //Variable de sesion
	private String centop = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("centop"); //Variable de sesion
	private String situacion = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("situacion"); //Variable de sesion
	private String nombre = "";
	private String cargo = "";
	private String genero = "";
	private String turno = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("turno"); //Variable de sesion
	private String area = "";
	private String reportado = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("reportado"); //Variable de sesion
	private String razon = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("razon"); //Variable de sesion
	private String tipoin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipoin"); //Variable de sesion
	private String tipoac = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipoac"); //Variable de sesion
	private String tipole = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipole"); //Variable de sesion
	private String cuerpo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cuerpo"); //Variable de sesion
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
	private String zcentop = "";
	private String zdepartamento = "";
	private String zdessuc = "";
	private String zcendessuc = "";
	private String zreportado = "";
	private String zdescreportado = "";
	private String zrazon = "";
	private String zdescrazon = "";
	private String zdesctipole = "";
	private String zdescturno = "";
	private String zdesccuerpo = "";
	private String zdescsituacion = "";
	private String zsituacion = "";
	private Object filterValue = "";
	private List<Shaincidencias> list = new ArrayList<Shaincidencias>();
	private int validarOperacion = 0;
	PntGenerica consulta = new PntGenerica();
	String[][] tabla;


	public String getZsituacion() {
		return zsituacion;
	}

	public void setZsituacion(String zsituacion) {
		this.zsituacion = zsituacion;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public String getZdescsituacion() {
		return zdescsituacion;
	}

	public void setZdescsituacion(String zdescsituacion) {
		this.zdescsituacion = zdescsituacion;
	}

	public String getZdesccuerpo() {
		return zdesccuerpo;
	}

	public void setZdesccuerpo(String zdesccuerpo) {
		this.zdesccuerpo = zdesccuerpo;
	}

	public String getZdescturno() {
		return zdescturno;
	}

	public void setZdescturno(String zdescturno) {
		this.zdescturno = zdescturno;
	}

	public String getZdesctipole() {
		return zdesctipole;
	}

	public void setZdesctipole(String zdesctipole) {
		this.zdesctipole = zdesctipole;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public String getZrazon() {
		return zrazon;
	}

	public void setZrazon(String zrazon) {
		this.zrazon = zrazon;
	}

	public String getZdescrazon() {
		return zdescrazon;
	}

	public void setZdescrazon(String zdescrazon) {
		this.zdescrazon = zdescrazon;
	}

	public String getReportado() {
		return reportado;
	}

	public void setReportado(String reportado) {
		this.reportado = reportado;
	}

	public String getZreportado() {
		return zreportado;
	}

	public void setZreportado(String zreportado) {
		this.zreportado = zreportado;
	}

	public String getZdescreportado() {
		return zdescreportado;
	}

	public void setZdescreportado(String zdescreportado) {
		this.zdescreportado = zdescreportado;
	}

	public String getZdessuc() {
		return zdessuc;
	}

	public void setZdessuc(String zdessuc) {
		this.zdessuc = zdessuc;
	}

	public String getZcendessuc() {
		return zcendessuc;
	}

	public void setZcendessuc(String zcendessuc) {
		this.zcendessuc = zcendessuc;
	}

	public String getCentop() {
		return centop;
	}

	public void setCentop(String centop) {
		this.centop = centop;
	}

	public String getZcentop() {
		return zcentop;
	}

	public void setZcentop(String zcentop) {
		this.zcentop = zcentop;
	}

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
 		if(centop==null){
 			centop = " - ";
 		}
 		if(centop==""){
 			centop = " - ";
 		}  
 		if(reportado==null){
 			reportado = " - ";
 		}
 		if(reportado==""){
 			reportado = " - ";
 		}  
 		if(razon==null){
 			razon = " - ";
 		}
 		if(razon==""){
 			razon = " - ";
 		}  
 		if(tipole==null){
 			tipole = " - ";
 		}
 		if(tipole==""){
 			tipole = " - ";
 		}  
 		if(turno==null){
 			turno = " - ";
 		}
 		if(turno==""){
 			turno = " - ";
 		}  
 		if(cuerpo==null){
 			cuerpo = " - ";
 		}
 		if(cuerpo==""){
 			cuerpo = " - ";
 		}  
 		if(situacion==null){
 			situacion = " - ";
 		}
 		if(situacion==""){
 			situacion = " - ";
 		}  
 		
        String[] vecsituacion = situacion.split("\\ - ", -1); 		
        String[] veccuerpo = cuerpo.split("\\ - ", -1);
        String[] vecturno = turno.split("\\ - ", -1);
        String[] veclesion = tipole.split("\\ - ", -1);
        String[] vecrazon = razon.split("\\ - ", -1);
        String[] vecreport = reportado.split("\\ - ", -1);
        String[] veccentop = centop.split("\\ - ", -1);
        String[] vecztres = ztres.split("\\ - ", -1);
        String[] vectipoac = tipoac.split("\\ - ", -1);
        String[] vecincap = tipoin.split("\\ - ", -1);
                
    	/////////////////////////////////////////////////////////////////////////////////////////////////
    	// ESTOS CONDICIONADORES PARA LAS FECHAS PERMITEN EL ALMACENAMIENTO DE LA HORA:MINUTO		   //
    	// PARA LA INSERSION EN LA BD ADICIONAL A LA FECHA NORMAL DD/MM/YYYY.                          //
    	/////////////////////////////////////////////////////////////////////////////////////////////////
        
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
    	
    	/////////////////////////////////////////////////////////////////////////////////////////////////
    	// ESTOS 2 CONDICIONADORES IF'S ESTAN CON EL PROPOSITO DE DARLE CONSISTENCIA AL TBRESULT       //
    	// AL MOMENTO DE HACER EL SELECT DEBIDO A LA MAGNITUD DE LAS TABLAS Y SIMPLIFICAR LA SELECCION //
    	// EVITANDO QUE SE GUARDE UN VALOR NULO EN LA BD Y ADMINISTRANDO UN VALOR QUE ES INVISIBLE     //
    	// PARA EL USUARIO.                                                                            //
    	/////////////////////////////////////////////////////////////////////////////////////////////////
    	
    	//System.out.println(vecreport[0]);
    	//System.out.println(vecreport[0].equals("1"));
    	if(vecreport[0].equals("1")){
    		//System.out.println("entre al if de razon");
    		vecrazon[0] = "0";
 		}  
    	if(vecreport[0].equals("2")){
    		//System.out.println("entre al if de inpsasel");
    		inpsasel = "NO REPORTADO";
 		} 
        
        String query = "INSERT INTO SHAINCIDENCIAS VALUES (?,?,?,?," + to_date + ",?,?,?,?,?,?,?,?,?,'" + getFecha() + "',?,'" + getFecha() + "',NULL,?,?,?,?)";
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, ci.toUpperCase());
        pstmt.setString(2, zuno.toUpperCase());
        pstmt.setString(3, zdos.toUpperCase());
        pstmt.setString(4, vecztres[0].toUpperCase());
        pstmt.setString(5, vecturno[0].toUpperCase());
        pstmt.setString(6, area.toUpperCase());
        pstmt.setString(7, vecincap[0].toUpperCase());
        pstmt.setString(8, vectipoac[0].toUpperCase());
        pstmt.setString(9, veclesion[0].toUpperCase());
        pstmt.setString(10, veccuerpo[0].toUpperCase());
        pstmt.setString(11, hechos.toUpperCase());
        pstmt.setString(12, inpsasel.toUpperCase());
        pstmt.setString(13, login);
        pstmt.setString(14, login);  
        pstmt.setString(15, veccentop[0].toUpperCase());
        pstmt.setString(16, vecreport[0].toUpperCase());
        pstmt.setString(17, vecrazon[0].toUpperCase());
        pstmt.setString(18, vecsituacion[0].toUpperCase());
        
        //System.out.println(query);
        //System.out.println("ci: " + ci);
        //System.out.println("zuno: " + zuno);
        //System.out.println("zdos: " + zdos);
        //System.out.println("ztres: " + vecztres[0]);
        //System.out.println("fecha: " + to_date);
        //System.out.println("turno: " + vecturno[0]);
        //System.out.println("area: " + area);
        //System.out.println("tipoin: " + vecincap[0]);
        //System.out.println("tipoac: " + vectipoac[0]);
        //System.out.println("tipole: " + vectipole[0]);
        //System.out.println("cuerpo: " + veccuerpo[0]);
        //System.out.println("hechos: " + hechos);
        //System.out.println("inpsasel: " + inpsasel);
        //System.out.println("centro operativo: " + veccentop[0]);
        //System.out.println("reportada: " + vecreport[0]);
        //System.out.println("razon: " + vecrazon[0]);
        //System.out.println("situacion: " + vecsituacion[0]);
        
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
 		if(centop==null){
 			centop = " - ";
 		}
 		if(centop==""){
 			centop = " - ";
 		}  
 		if(reportado==null){
 			reportado = " - ";
 		}
 		if(reportado==""){
 			reportado = " - ";
 		}  
 		if(razon==null){
 			razon = " - ";
 		}
 		if(razon==""){
 			razon = " - ";
 		}  
 		if(tipole==null){
 			tipole = " - ";
 		}
 		if(tipole==""){
 			tipole = " - ";
 		}  
 		if(turno==null){
 			turno = " - ";
 		}
 		if(turno==""){
 			turno = " - ";
 		}  
 		if(cuerpo==null){
 			cuerpo = " - ";
 		}
 		if(cuerpo==""){
 			cuerpo = " - ";
 		}  
 		if(situacion==null){
 			situacion = " - ";
 		}
 		if(situacion==""){
 			situacion = " - ";
 		}  
 		
        String[] vecsituacion = situacion.split("\\ - ", -1); 
        String[] veccuerpo = cuerpo.split("\\ - ", -1);
        String[] vecturno = turno.split("\\ - ", -1);
        String[] veclesion = tipole.split("\\ - ", -1);
        String[] vecrazon = razon.split("\\ - ", -1);
        String[] vecreport = reportado.split("\\ - ", -1);
        String[] veccentop = centop.split("\\ - ", -1);
        String[] vectipoac = tipoac.split("\\ - ", -1);
        String[] vecincap = tipoin.split("\\ - ", -1);
        
		/////////////////////////////////////////////////////////////////////////////////////////////////
		// ESTOS 2 CONDICIONADORES IF'S ESTAN CON EL PROPOSITO DE DARLE CONSISTENCIA AL TBRESULT       //
		// AL MOMENTO DE HACER EL SELECT DEBIDO A LA MAGNITUD DE LAS TABLAS Y SIMPLIFICAR LA SELECCION //
		// EVITANDO QUE SE GUARDE UN VALOR NULO EN LA BD Y ADMINISTRANDO UN VALOR QUE ES INVISIBLE     //
		// PARA EL USUARIO.                                                                            //
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		//System.out.println(vecreport[0]);
		//System.out.println(vecreport[0].equals("1"));
		if(vecreport[0].equals("1")){
		//System.out.println("entre al if de razon");
		vecrazon[0] = "0";
		}  
		if(vecreport[0].equals("2")){
		//System.out.println("entre al if de inpsasel");
		inpsasel = "NO REPORTADO";
		} 
    	
        String query = "UPDATE SHAINCIDENCIAS A";
         query += " SET A.FECHA = " + to_date + ", ";
         query += " A.TURNO = ?,";
         query += " A.AREAEVENT = ?,";
         query += " A.TIPOINCAP = ?,";
         query += " A.TIPOACC = ?,";
         query += " A.TIPOLES = ?,";
         query += " A.UBILES = ?,";
         query += " A.DESCHEC = ?,";
         query += " A.REPORTADO = ?,";
         query += " A.INPSASEL = ?,";
         query += " A.RAZON = ?,";
         query += " A.SITUACION = ?,";
         query += " USRACT = ?,";
         query += " FECACT = '" + getFecha() + "',";
         query += " CENTOP = ?";
         query += " WHERE A.REGIST = ? ";

        pstmt = con.prepareStatement(query);
        pstmt.setString(1, vecturno[0].toUpperCase());
        pstmt.setString(2, area.toUpperCase());
        pstmt.setString(3, vecincap[0].toUpperCase());
        pstmt.setString(4, vectipoac[0].toUpperCase());
        pstmt.setString(5, veclesion[0].toUpperCase());
        pstmt.setString(6, veccuerpo[0].toUpperCase());
        pstmt.setString(7, hechos.toUpperCase());
        pstmt.setString(8, vecreport[0].toUpperCase());
        pstmt.setString(9, inpsasel.toUpperCase());
        pstmt.setString(10, vecrazon[0].toUpperCase());
        pstmt.setString(11, vecsituacion[0].toUpperCase());
        pstmt.setString(12, login);   
        pstmt.setString(13, veccentop[0].toUpperCase());
        pstmt.setInt(14,Integer.parseInt(regist));

        //System.out.println(query);
        //System.out.println("fecha: " + to_date);
        //System.out.println("turno: " + vecturno[0]);
        //System.out.println("area: " + area);
        //System.out.println("tipoin: " + vecincap[0]);
        //System.out.println("tipoac: " + vectipoac[0]);
        //System.out.println("tipole: " + veclesion[0]);
        //System.out.println("cuerpo: " + veccuerpo[0]);
        //System.out.println("hechos: " + hechos);
        //System.out.println("reportado: " + vecreport[0]);
        //System.out.println("inpsasel: " + inpsasel);
        //System.out.println("razon: " + vecrazon[0]);
        //System.out.println("situacion: " + vecsituacion[0]);
        //System.out.println("centro operativo: " + veccentop[0]);
        //System.out.println("registro: " + regist);
        
        // Antes de ejecutar valida si existe el registro en la base de Datos.
        try {
            //Avisando
            pstmt.executeUpdate();
            if(pstmt.getUpdateCount()==0){
            	msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessage("msnNoUpdate"), "");
            } else {
            	msj = new FacesMessage(FacesMessage.SEVERITY_INFO,  getMessage("msnUpdate"), "");
            	limpiarValores();
            }
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
	 		if(centop==null){
	 			centop = " - ";
	 		}
	 		if(centop==""){
	 			centop = " - ";
	 		}  
	 		if(reportado==null){
	 			reportado = " - ";
	 		}
	 		if(reportado==""){
	 			reportado = " - ";
	 		}  
	 		if(razon==null){
	 			razon = " - ";
	 		}
	 		if(razon==""){
	 			razon = " - ";
	 		}  
	 		if(tipole==null){
	 			tipole = " - ";
	 		}
	 		if(tipole==""){
	 			tipole = " - ";
	 		}  
	 		if(turno==null){
	 			turno = " - ";
	 		}
	 		if(turno==""){
	 			turno = " - ";
	 		}  
	 		if(cuerpo==null){
	 			cuerpo = " - ";
	 		}
	 		if(cuerpo==""){
	 			cuerpo = " - ";
	 		}  
	 		if(situacion==null){
	 			situacion = " - ";
	 		}
	 		if(situacion==""){
	 			situacion = " - ";
	 		}  
	 		
	        String[] vecsituacion = cuerpo.split("\\ - ", -1); 
	        String[] veccuerpo = cuerpo.split("\\ - ", -1);
	        String[] vecturno = turno.split("\\ - ", -1);
	        String[] veclesion = tipole.split("\\ - ", -1);
	        String[] vecrazon = razon.split("\\ - ", -1);
	        String[] vecreport = reportado.split("\\ - ", -1);
	        String[] veccentop = centop.split("\\ - ", -1);
	        String[] vectipoac = tipoac.split("\\ - ", -1);
	        String[] vecincap = tipoin.split("\\ - ", -1);
		
	 if (tabla[0][0].equals(validar)) { 
	     
		    /////////////////////////////////////////////////////////////////////////////////////////////////
	      	// ESTE ES EL SELECT DE LOS USUARIOS ADMINISTRADORES.                                          //
	      	/////////////////////////////////////////////////////////////////////////////////////////////////

		//System.out.println("Entre al IF");
		//Consulta paginada
     String query = "SELECT * FROM"; 
	    query += "(select query.*, rownum as rn from";
		query += "(SELECT A.CI, A.NOMBRE, A.GENERO, A.CARGO, TO_CHAR(A.FECHA,'DD/MM/YYYY HH24:MI') AS FECHA, A.TURNO, A.AREAEVENT, A.TIPOINCAP, A.TIPOACC, A.TIPOLES, A.UBILES, A.DESCHEC, A.INPSASEL, B.INCAP, C.DESCR, D.DESCIA, D.DESNOM, D.CODSUC||' - '||D.DESSUC AS SUCURSAL, D.CODDEP||' - '||D.DESDEP AS DEPARTAMENTO, D.DESCAR, A.REGIST, A.CENTOP, F.DESSUC, A.REPORTADO, G.DESCR AS DESCREPORT, A.RAZON, H.DESCR AS DESCRAZON, I.DESCR AS DESCLESION, J.TURNO AS DESCTURNO, K.DESCR AS DESCCUERPO, A.SITUACION, L.DESCR AS DESCSITUACION";
	    query += " FROM SHAINCIDENCIAS A, SHAINCAP B, SHATIPAC C, NM_TRABAJADOR@INFOCENT_CALENDARIO D, SHASUCURSAL F, SHAINCREPORTADAS G, SHARAZON H, SHALESION I, SHATURNOS J, SHACUERPO K, SHASITUACION L";
	    query += " WHERE A.TIPOINCAP = B.CODIGO";
	    query += " AND A.TIPOACC = C.CODIGO";
	    query += " AND A.CARGO = D.CODCAR";
	    query += " AND A.CI = D.TIPDOC||' - '||D.CEDULA";
	    query += " AND A.CENTOP = TO_CHAR(F.CODSUC)";
	    query += " AND A.REPORTADO = G.CODIGO";
	    query += " AND A.RAZON = H.CODIGO";
	    query += " AND A.TIPOLES = I.CODIGO";
	    query += " AND A.TURNO = J.CODIGO";
	    query += " AND A.UBILES = K.CODIGO";
	    query += " AND A.SITUACION = L.CODIGO";
	    query += " AND A.CI||A.NOMBRE||A.GENERO||A.CARGO||TO_CHAR(A.FECHA,'DD/MM/YYYY HH24:MI')||A.TURNO||A.AREAEVENT||A.TIPOINCAP||A.TIPOACC||A.TIPOLES||A.UBILES||A.DESCHEC||A.INPSASEL||B.INCAP||C.DESCR||D.DESCIA||D.DESNOM||D.CODSUC||D.DESSUC||D.CODDEP||D.DESDEP||D.DESCAR||A.REPORTADO||G.DESCR||A.RAZON||H.DESCR||I.DESCR||J.TURNO||K.DESCR||A.SITUACION||L.DESCR like '%" + ((String) filterValue).toUpperCase() + "%'";
	    query += " AND A.CI LIKE '" + ci.toUpperCase() + "%'";
	    query += " AND A.TIPOINCAP LIKE '" + vecincap[0] + "%'";
	    query += " AND A.TIPOACC LIKE '" + vectipoac[0] + "%'";
	    query += " AND A.CENTOP LIKE '" + veccentop[0] + "%'";
	    query += " AND A.REPORTADO LIKE '" + vecreport[0].toUpperCase() + "%'";
	    query += " AND A.RAZON LIKE '" + vecrazon[0].toUpperCase() + "%'";
	    query += " AND A.TIPOLES LIKE '" + veclesion[0].toUpperCase() + "%'";
	    query += " AND A.TURNO LIKE '" + vecturno[0].toUpperCase() + "%'";
	    query += " AND A.UBILES LIKE '" + veccuerpo[0].toUpperCase() + "%'";
	    query += " AND A.SITUACION LIKE '" + vecsituacion[0].toUpperCase() + "%'";
	    query += " GROUP BY A.CI,A.NOMBRE, A.GENERO, A.CARGO, A.FECHA, A.TURNO, A.AREAEVENT, A.TIPOINCAP, A.TIPOACC, A.TIPOLES, A.UBILES, A.DESCHEC, A.INPSASEL, B.INCAP, C.DESCR, D.DESCIA, D.DESNOM, D.CODSUC, D.DESSUC, D.CODDEP, D.DESDEP, D.DESCAR, A.REGIST, A.CENTOP, F.DESSUC, A.REPORTADO, G.DESCR, A.RAZON, H.DESCR, I.DESCR, J.TURNO, K.DESCR, A.SITUACION, L.DESCR";
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
 	select.setZcentop(r.getString(22));
 	select.setZdessuc(r.getString(23));
 	select.setZcendessuc(r.getString(22)+ " - " + r.getString(23));
 	select.setZreportado(r.getString(24));
 	select.setZdescreportado(r.getString(24)+ " - " + r.getString(25));
 	select.setZrazon(r.getString(26));
 	select.setZdescrazon(r.getString(26)+ " - " + r.getString(27));
 	select.setZdesctipole(r.getString(10)+ " - " + r.getString(28));
 	select.setZdescturno(r.getString(6)+ " - " + r.getString(29));
 	select.setZdesccuerpo(r.getString(11)+ " - " + r.getString(30));
 	select.setZsituacion(r.getString(31));
 	select.setZdescsituacion(r.getString(31)+ " - " + r.getString(32));

   	
    	//Agrega la lista
    	list.add(select);
    }
	}
	 
	 else { 
		 
		    /////////////////////////////////////////////////////////////////////////////////////////////////
	      	// ESTE ES EL SELECT DE LOS USUARIOS REGULARES      .                                          //
	      	/////////////////////////////////////////////////////////////////////////////////////////////////
		 
		 	//System.out.println("Entre al ELSE");
			//Consulta paginada
	     String query = "SELECT * FROM"; 
		    query += "(select query.*, rownum as rn from";
			query += "(SELECT A.CI, A.NOMBRE, A.GENERO, A.CARGO, TO_CHAR(A.FECHA,'DD/MM/YYYY HH24:MI') AS FECHA, A.TURNO, A.AREAEVENT, A.TIPOINCAP, A.TIPOACC, A.TIPOLES, A.UBILES, A.DESCHEC, A.INPSASEL, B.INCAP, C.DESCR, D.DESCIA, D.DESNOM, F.CODSUC||' - '||F.DESSUC AS SUCURSAL, D.CODDEP||' - '||D.DESDEP AS DEPARTAMENTO, D.DESCAR, A.REGIST, A.CENTOP, F.DESSUC, A.REPORTADO, G.DESCR AS DESCREPORT, A.RAZON, H.DESCR AS DESCRAZON, I.DESCR AS DESCLESION, J.TURNO AS DESCTURNO, K.DESCR AS DESCCUERPO, A.SITUACION, L.DESCR AS DESCSITUACION";
		    query += " FROM SHAINCIDENCIAS A, SHAINCAP B, SHATIPAC C, NM_TRABAJADOR@INFOCENT_CALENDARIO D, SHABVT002 E, SHASUCURSAL F, SHAINCREPORTADAS G, SHARAZON H, SHALESION I, SHATURNOS J, SHACUERPO K, SHASITUACION L";
		    query += " WHERE A.TIPOINCAP = B.CODIGO";
		    query += " AND A.TIPOACC = C.CODIGO";
		    query += " AND A.CARGO = D.CODCAR";
		    query += " AND A.CENTOP = F.CODSUC";
		    query += " AND A.REPORTADO = G.CODIGO";
		    query += " AND A.RAZON = H.CODIGO";
		    query += " AND A.TIPOLES = I.CODIGO";
		    query += " AND A.TURNO = J.CODIGO";
		    query += " AND A.UBILES = K.CODIGO";
		    query += " AND A.SITUACION = L.CODIGO";
			query += " AND E.SUCURSAL = TO_CHAR(F.CODSUC)";
		    query += " AND A.CI = D.TIPDOC||' - '||D.CEDULA";
		    query += " AND A.CI||A.NOMBRE||A.GENERO||A.CARGO||TO_CHAR(A.FECHA,'DD/MM/YYYY HH24:MI')||A.TURNO||A.AREAEVENT||A.TIPOINCAP||A.TIPOACC||A.TIPOLES||A.UBILES||A.DESCHEC||A.INPSASEL||B.INCAP||C.DESCR||D.DESCIA||D.DESNOM||F.CODSUC||F.DESSUC||D.CODDEP||D.DESDEP||D.DESCAR||A.REPORTADO||G.DESCR||A.RAZON||H.DESCR||I.DESCR||J.TURNO||K.DESCR||A.SITUACION||L.DESCR like '%" + ((String) filterValue).toUpperCase() + "%'";
		    query += " AND A.CI LIKE '" + ci.toUpperCase() + "%'";
		    query += " AND A.TIPOINCAP LIKE '" + vecincap[0] + "%'";
		    query += " AND A.TIPOACC LIKE '" + vectipoac[0] + "%'";
		    query += " AND A.CENTOP LIKE '" + veccentop[0] + "%'";
		    query += " AND A.REPORTADO LIKE '" + vecreport[0].toUpperCase() + "%'";
		    query += " AND A.RAZON LIKE '" + vecrazon[0].toUpperCase() + "%'";
		    query += " AND A.TIPOLES LIKE '" + veclesion[0].toUpperCase() + "%'";
		    query += " AND A.TURNO LIKE '" + vecturno[0].toUpperCase() + "%'";
		    query += " AND A.UBILES LIKE '" + veccuerpo[0].toUpperCase() + "%'";
		    query += " AND A.SITUACION LIKE '" + vecsituacion[0].toUpperCase() + "%'";
		    query += " GROUP BY A.CI,A.NOMBRE, A.GENERO, A.CARGO, A.FECHA, A.TURNO, A.AREAEVENT, A.TIPOINCAP, A.TIPOACC, A.TIPOLES, A.UBILES, A.DESCHEC, A.INPSASEL, B.INCAP, C.DESCR, D.DESCIA, D.DESNOM, F.CODSUC, F.DESSUC, D.CODDEP, D.DESDEP, D.DESCAR, A.REGIST, A.CENTOP, F.DESSUC, A.REPORTADO, G.DESCR, A.RAZON, H.DESCR, I.DESCR, J.TURNO, K.DESCR, A.SITUACION, L.DESCR";
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
	 	select.setZcentop(r.getString(22));
	 	select.setZdessuc(r.getString(23));
	 	select.setZcendessuc(r.getString(22)+ " - " + r.getString(23));
	 	select.setZreportado(r.getString(24));
	 	select.setZdescreportado(r.getString(24)+ " - " + r.getString(25));
	 	select.setZrazon(r.getString(26));
	 	select.setZdescrazon(r.getString(26)+ " - " + r.getString(27));
	 	select.setZdesctipole(r.getString(10)+ " - " + r.getString(28));
	 	select.setZdescturno(r.getString(6)+ " - " + r.getString(29));
	 	select.setZdesccuerpo(r.getString(11)+ " - " + r.getString(30));
	 	select.setZsituacion(r.getString(31));
	 	select.setZdescsituacion(r.getString(31)+ " - " + r.getString(32));
	   	
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
      		
      		String validar = "1";
    		String querycon = " SELECT B_CODROL FROM SHABVT002"
    						+ " WHERE CODUSER LIKE '" + login.toUpperCase() + "%'";
    		
    			//System.out.println(querycon);
    			
    			consulta.selectPntGenerica(querycon, JNDI);
    			
    			rows = consulta.getRows();
    			tabla = consulta.getArray();
    			//System.out.println(tabla[0][0]);
    			
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
	 		if(centop==null){
	 			centop = " - ";
	 		}
	 		if(centop==""){
	 			centop = " - ";
	 		}  
	        String[] veccentop = centop.split("\\ - ", -1);
	        String[] vectipoac = tipoac.split("\\ - ", -1);
            String[] vecincap = tipoin.split("\\ - ", -1);
      		
            if (tabla[0][0].equals(validar)) { 
        		
    		    /////////////////////////////////////////////////////////////////////////////////////////////////
    	      	// ESTE ES EL COUNTER DE LOS USUARIOS ADMINISTRADORES.                                         //
    	      	/////////////////////////////////////////////////////////////////////////////////////////////////
            	
        		//System.out.println("Entre al IF");
     		//Consulta paginada
     		String query = "SELECT COUNT_SHAINCIDENCIAS('" + ((String) filterValue).toUpperCase() + "','" + ci.toUpperCase() + "','" + vecincap[0].toUpperCase() + "','" + vectipoac[0].toUpperCase() + "','" + veccentop[0].toUpperCase() + "') FROM DUAL";

           
           pstmt = con.prepareStatement(query);
           //System.out.println(query);

            r =  pstmt.executeQuery();
           
           
           while (r.next()){
           	rows = r.getInt(1);
           	
           }
           }
            else {
            	
    		    /////////////////////////////////////////////////////////////////////////////////////////////////
    	      	// ESTE ES EL COUNTER DE LOS USUARIOS REGULARES.                                               //
    	      	/////////////////////////////////////////////////////////////////////////////////////////////////
            	
    		 	//System.out.println("Entre al ELSE");
    		 	//Consulta paginada
         		String query = "SELECT COUNT_SHAINCIDENCIAS2('" + ((String) filterValue).toUpperCase() + "','" + ci.toUpperCase() + "','" + vecincap[0].toUpperCase() + "','" + vectipoac[0].toUpperCase() + "','" + veccentop[0].toUpperCase() + "') FROM DUAL";

               
               pstmt = con.prepareStatement(query);
               //System.out.println(query);

                r =  pstmt.executeQuery();
               
               
               while (r.next()){
               	rows = r.getInt(1);
               	
               }
            	
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
  		centop = "";
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
  		reportado = "";
  		inpsasel = "";
  		razon = "";
  		situacion = "";
  		validarOperacion = 0;
	}
  	
    public void reset() {
    	// TODO Auto-generated method stub
  		ci = null; 
  		centop = null;
  		tipoin = null;
  		tipoac = null;
  		tipole = null;
  		reportado = null;
  		razon = null;
  		turno = null;
  		cuerpo = null;
  		situacion = null;
    }    
   	
  	  public void onselectCi() {
          if(ci==null){
          	ci = " - ";
          }  

      	/////////////////////////////////////////////////////////////////////////////////////////////////
      	// CONSULTA QUE HACE LA SELECCION AUTOMATICA PARA LOS INPUTTEXT DE LOS EMPLEADOS, RETORNANDO   //
      	// LOS VALORES DE  NOMBRE, GENERO Y CARGO DE CADA UNO QUE NO SEA NOMINA CONFIDENCIAL, A LOS DE //
      	// DICHA NOMINA LE RETORNA VALORES PREDEFINIDOS DEBIDO AL ESQUEMA DE ALMACEN DE DATA           //
      	// CONFIDENCIAL QUE MANEJA BAAN EN SU BD.                                                      //
      	/////////////////////////////////////////////////////////////////////////////////////////////////

   	     String query = " SELECT TRIM(T$NOM1$O)||' '||TRIM(T$NOM2$O)||' '||TRIM(T$APE1$O)||' '||TRIM(T$APE2$O) AS NOMBRE, 'CONFIDENCIAL' AS SEXO,'0A1 - CONFIDENCIAL' AS CARGO";
 	  		    query += " FROM ttfinn911100@baan_oracle A";
 	  		    query += " WHERE A.T$TIPO$O = 2";
 	  		    query += " AND 'C - '||TRIM(A.T$CEDU$O) LIKE '" + ci.toUpperCase() + "%'";
 	  		    query += " GROUP BY T$NOM1$O, T$NOM2$O, T$APE1$O, T$APE2$O";
 	  		    query += " UNION ALL";
 	  		    query += " SELECT NOMBRE1||' '||NOMBRE2||' '||APELLIDO1||' '||APELLIDO2 AS NOMBRE, SEXO, CODCAR||' - '||DESCAR AS CARGO";
 	  		    query += " FROM NM_TRABAJADOR@INFOCENT_CALENDARIO";
 	  		    query += " WHERE TIPDOC||' - '||CEDULA like '" + ci.toUpperCase() + "%'";
 	  		    query += " AND FECRET IS NULL";
 	  		    query += " OR FECRET > SYSDATE";
 	  		    query += " GROUP BY NOMBRE1, NOMBRE2, APELLIDO1, APELLIDO2, SEXO, CODCAR, DESCAR";
 	  		    query += " ORDER BY NOMBRE";
 	  		    
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
