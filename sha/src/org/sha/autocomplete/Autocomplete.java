/*
 *  Copyright (C) 2011  DVCONSULTORES

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

package org.sha.autocomplete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import org.sha.util.Bd;
import org.sha.util.PntGenerica;

/*
 * Esta clase permite mostrar las listas de valores en todas las páginas
 * utilizando Primefaces y sustituyendo a JQuery
 */

@ManagedBean
@RequestScoped
public class Autocomplete extends Bd {
	PntGenerica consulta = new PntGenerica();
	int rows;
	String[][] tabla;	
	private String login = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); //Usuario logeado

	public String getLogin() {
		return login;
	}

	
		
	/**
	 * Lista Compa;ias.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	 **/
	public List<String> completeComp(String query) throws NamingException,IOException {

		List<String> results = new ArrayList<String>();

		String querysb = " SELECT A.CODCIA||' - '||A.DESCIA " 
				       + " FROM NM_TRABAJADOR@INFOCENT_CALENDARIO A"
					   + " WHERE A.CODCIA || A.DESCIA LIKE '%" + query.toUpperCase() + "%'"
					   + " GROUP BY A.CODCIA, A.DESCIA"
					   + " ORDER BY A.CODCIA";
		 
		//System.out.println(querysb);

		consulta.selectPntGenerica(querysb,JNDI);

		rows = consulta.getRows();

		tabla = consulta.getArray();

		for (int i = 0; i < rows; i++) {
			results.add(tabla[i][0]);
		}
		return results;
	}
	
	/**
	 * Lista sucursales.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	 **/
	public List<String> completeCodsuc(String query) throws NamingException,IOException {
		
		String comp = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("comp"); // Usuario logeado

		if (comp == null) {
			comp = " - ";
		}
		if (comp == "") {
			comp = " - ";
		}
	
		String[] veccomp = comp.split("\\ - ", -1);
		
		List<String> results = new ArrayList<String>();

		String querysb = " SELECT A.CODSUC||' - '||A.DESSUC" 
				       + " FROM NM_TRABAJADOR@INFOCENT_CALENDARIO A"
					   + " WHERE CODSUC || DESSUC LIKE '%" + query.toUpperCase() + "%'"
					   + " AND A.CODCIA = '" + veccomp[0].toUpperCase() + "'"
					   + " GROUP BY CODSUC, DESSUC"
					   + " ORDER BY CODSUC";

		//System.out.println(querysb);

		consulta.selectPntGenerica(querysb,JNDI);

		rows = consulta.getRows();

		tabla = consulta.getArray();

		for (int i = 0; i < rows; i++) {
			results.add(tabla[i][0]);
		}
		return results;
	}
		
	/**
	 * Lista de tipo de usuario.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	 **/
	public List<String> completeTiprol(String query) throws NamingException,IOException {

		List<String> results = new ArrayList<String>();

		String querysb = " SELECT A.CODIGO||' - '||A.ROL " 
				       + " FROM SHATIPROL A"
					   + " WHERE A.CODIGO || A.ROL LIKE '%" + query.toUpperCase() + "%'"
					   + " GROUP BY A.CODIGO, A.ROL"
					   + " ORDER BY A.CODIGO";
		 
		//System.out.println(querysb);

		consulta.selectPntGenerica(querysb,JNDI);

		rows = consulta.getRows();

		tabla = consulta.getArray();

		for (int i = 0; i < rows; i++) {
			results.add(tabla[i][0]);
		}
		return results;
	}	
	
	/**
	 * Lista de incapacidades.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	**/
	
	public List<String> completeIncap(String query) throws NamingException,IOException {
				
		List<String> results = new ArrayList<String>();

		String querysb = " SELECT A.CODIGO||' - '||A.INCAP" 
				       + " FROM SHAINCAP A"
					   + " WHERE A.CODIGO || A.INCAP LIKE '%" + query.toUpperCase() + "%'"
					   + " GROUP BY A.CODIGO, A.INCAP"
					   + " ORDER BY A.CODIGO, A.INCAP";

		//System.out.println(querysb);

		consulta.selectPntGenerica(querysb,JNDI);

		rows = consulta.getRows();

		tabla = consulta.getArray();

		for (int i = 0; i < rows; i++) {
			results.add(tabla[i][0]);
		}
		return results;
	} 
	
	/**
	 * Lista de tipos de accidentes.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	**/
	
	public List<String> completeAcc(String query) throws NamingException,IOException {
				
		List<String> results = new ArrayList<String>();

		String querysb = " SELECT A.CODIGO||' - '||A.DESCR" 
				       + " FROM SHATIPAC A"
					   + " WHERE A.CODIGO || A.DESCR LIKE '%" + query.toUpperCase() + "%'"
					   + " GROUP BY A.CODIGO, A.DESCR"
					   + " ORDER BY A.CODIGO, A.DESCR";

		//System.out.println(querysb);

		consulta.selectPntGenerica(querysb,JNDI);

		rows = consulta.getRows();

		tabla = consulta.getArray();

		for (int i = 0; i < rows; i++) {
			results.add(tabla[i][0]);
		}
		return results;
	} 
	
	/**
	 * Lista de incidencias reportadas.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	**/
	
	public List<String> completeReportado(String query) throws NamingException,IOException {
				
		List<String> results = new ArrayList<String>();

		String querysb = " SELECT A.CODIGO||' - '||A.DESCR" 
				       + " FROM SHAINCREPORTADAS A"
					   + " WHERE A.CODIGO || A.DESCR LIKE '%" + query.toUpperCase() + "%'"
					   + " GROUP BY A.CODIGO, A.DESCR"
					   + " ORDER BY A.CODIGO, A.DESCR";

		//System.out.println(querysb);

		consulta.selectPntGenerica(querysb,JNDI);

		rows = consulta.getRows();

		tabla = consulta.getArray();

		for (int i = 0; i < rows; i++) {
			results.add(tabla[i][0]);
		}
		return results;
	} 
	
	/**
	 * Lista de TURNOS.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	**/
	
	public List<String> completeTurno(String query) throws NamingException,IOException {
				
		List<String> results = new ArrayList<String>();

		String querysb = " SELECT A.CODIGO||' - '||A.TURNO" 
				       + " FROM SHATURNOS A"
					   + " WHERE A.CODIGO || A.TURNO LIKE '%" + query.toUpperCase() + "%'"
					   + " GROUP BY A.CODIGO, A.TURNO"
					   + " ORDER BY A.CODIGO, A.TURNO";

		//System.out.println(querysb);

		consulta.selectPntGenerica(querysb,JNDI);

		rows = consulta.getRows();

		tabla = consulta.getArray();

		for (int i = 0; i < rows; i++) {
			results.add(tabla[i][0]);
		}
		return results;
	} 
	
	/**
	 * Lista de razones por las que no se reporto el incidente.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	**/
	
	public List<String> completeRazon(String query) throws NamingException,IOException {
				
		List<String> results = new ArrayList<String>();

		String querysb = " SELECT A.CODIGO||' - '||A.DESCR" 
				       + " FROM SHARAZON A"
					   + " WHERE A.CODIGO || A.DESCR LIKE '%" + query.toUpperCase() + "%'"
					   + " AND A.CODIGO != 0"
					   + " GROUP BY A.CODIGO, A.DESCR"
					   + " ORDER BY A.CODIGO, A.DESCR";

		//System.out.println(querysb);

		consulta.selectPntGenerica(querysb,JNDI);

		rows = consulta.getRows();

		tabla = consulta.getArray();

		for (int i = 0; i < rows; i++) {
			results.add(tabla[i][0]);
		}
		return results;
	} 
	
	/**
	 * Lista lesiones.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	**/
	
	public List<String> completeLesion(String query) throws NamingException,IOException {
				
		List<String> results = new ArrayList<String>();

		String querysb = " SELECT A.CODIGO||' - '||A.DESCR" 
				       + " FROM SHALESION A"
					   + " WHERE A.CODIGO || A.DESCR LIKE '%" + query.toUpperCase() + "%'"
					   + " GROUP BY A.CODIGO, A.DESCR"
					   + " ORDER BY A.CODIGO, A.DESCR";

		//System.out.println(querysb);

		consulta.selectPntGenerica(querysb,JNDI);

		rows = consulta.getRows();

		tabla = consulta.getArray();

		for (int i = 0; i < rows; i++) {
			results.add(tabla[i][0]);
		}
		return results;
	} 
	
	
	/**
	 * Listas de CI de empleados activos.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	 **/
	public List<String> completeCi(String query) throws NamingException,IOException {
		//System.out.println("entre al metodo nivapp");

		String validar = "1";
		String querycon = " SELECT B_CODROL FROM SHABVT002"
						+ " WHERE CODUSER LIKE '" + login.toUpperCase() + "%'";
		
			//System.out.println(querycon);
			
			consulta.selectPntGenerica(querycon, JNDI);
			
			rows = consulta.getRows();
			tabla = consulta.getArray();
			//System.out.println(tabla[0][0]);
			//System.out.println(tabla[0][1]);
			
			if (tabla[0][0].equals(validar)) {
	    		//System.out.println(tabla1[0][0]);
	    		//System.out.println(validar);
	    		//System.out.println("EL USUARIO ES IGUAL");			

			String querysb = " SELECT 'C - '||trim(A.T$CEDU$O) AS CEDULA" 
					       + " FROM ttfinn911100@baan_oracle A"
						   + " WHERE A.T$CEDU$O LIKE '%" + query.toUpperCase() + "%'"
				    	   + " AND A.T$TIPO$O = 2 "
				    	   + " GROUP BY (A.T$CEDU$O)"
						   + " UNION ALL"
						   + " SELECT A.TIPDOC||' - '||A.CEDULA AS CEDULA"
					       + " FROM NM_TRABAJADOR@INFOCENT_CALENDARIO A"
						   + " WHERE A.TIPDOC || A.CEDULA LIKE '%" + query.toUpperCase() + "%'"
				    	   + " AND A.FECRET IS NULL "
				    	   + " OR A.FECRET > SYSDATE"
						   + " GROUP BY A.TIPDOC, A.CEDULA"
						   + " ORDER BY CEDULA";

				//System.out.println(querysb);
				//System.out.println(JNDISB);
				List<String> results = new ArrayList<String>();

				consulta.selectPntGenerica(querysb, JNDI);

				rows = consulta.getRows();
				tabla = consulta.getArray();
				for (int i = 0; i < rows; i++) {
					results.add(tabla[i][0]);
				}
				return results;

	    }
	    	else {
	    		//System.out.println(tabla1[0][0]);
	    		//System.out.println(validar);
	    		//System.out.println("EL USUARIO NO ES IGUAL");

				String querysb = " SELECT 'C - '||trim(A.T$CEDU$O) AS CEDULA" 
					       + " FROM ttfinn911100@baan_oracle A"
						   + " WHERE A.T$CEDU$O LIKE '%" + query.toUpperCase() + "%'"
				    	   + " AND A.T$TIPO$O = 2 "
				    	   + " GROUP BY (A.T$CEDU$O)"
						   + " UNION ALL"
						   + " SELECT A.TIPDOC||' - '||A.CEDULA AS CEDULA " 
		    		       + " FROM NM_TRABAJADOR@INFOCENT_CALENDARIO A, SHASUCURSAL B, SHABVT002 C "
		    		       + " WHERE A.CODSUC = B.SUCURSAL "
		    		       + " AND TO_CHAR(B.CODSUC) = C.SUCURSAL "
		    		       + " AND A.FECRET IS NULL "
		    		       + " OR A.FECRET > SYSDATE"
		    		       + " AND A.TIPDOC || A.CEDULA LIKE '%" + query.toUpperCase() + "%'"
		    			   + " GROUP BY A.TIPDOC, A.CEDULA "
		    			   + " ORDER BY CEDULA";
	 
				//System.out.println(querysb);
				//System.out.println(JNDISB);
				List<String> results = new ArrayList<String>();

				consulta.selectPntGenerica(querysb, JNDI);

				rows = consulta.getRows();
				tabla = consulta.getArray();
				for (int i = 0; i < rows; i++) {
					results.add(tabla[i][0]);
				}
				return results;
	    		
	    	}	
	}
	
	/**
	 * Listas de centros operativos.
	 * 
	 * @throws NamingException
	 * @return List String
	 * @throws IOException
	 **/
	public List<String> completeCentop(String query) throws NamingException,IOException {
		//System.out.println("entre al metodo nivapp");

		String validar = "1";
		String querycon = " SELECT B_CODROL FROM SHABVT002"
						+ " WHERE CODUSER LIKE '" + login.toUpperCase() + "%'";
		
			//System.out.println(querycon);
			
			consulta.selectPntGenerica(querycon, JNDI);
			
			rows = consulta.getRows();
			tabla = consulta.getArray();
			//System.out.println(tabla[0][0]);
			//System.out.println(tabla[0][1]);
			
			if (tabla[0][0].equals(validar)) {
	    		//System.out.println(tabla1[0][0]);
	    		//System.out.println(validar);
	    		//System.out.println("EL USUARIO ES IGUAL");
	    		
				String querysb = " SELECT A.CODSUC||' - '||A.DESSUC" 
					       + " FROM SHASUCURSAL A"
						   + " WHERE A.CODSUC||A.DESSUC LIKE '%" + query.toUpperCase() + "%'"
						   + " GROUP BY A.CODSUC, A.DESSUC"
						   + " ORDER BY TO_NUMBER(A.CODSUC)";

				//System.out.println(querysb);
				//System.out.println(JNDISB);
				List<String> results = new ArrayList<String>();

				consulta.selectPntGenerica(querysb, JNDI);

				rows = consulta.getRows();
				tabla = consulta.getArray();
				for (int i = 0; i < rows; i++) {
					results.add(tabla[i][0]);
				}
				return results;

	    }
	    	else {
	    		//System.out.println(tabla1[0][0]);
	    		//System.out.println(validar);
	    		//System.out.println("EL USUARIO NO ES IGUAL");

				String querysb = " SELECT A.CODSUC||' - '||A.DESSUC" 
					       + " FROM SHASUCURSAL A, SHABVT002 B"
		    		       + " WHERE A.CODSUC = B.SUCURSAL "
						   + " AND A.CODSUC||A.DESSUC LIKE '%" + query.toUpperCase() + "%'"
						   + " GROUP BY A.CODSUC, A.DESSUC"
						   + " ORDER BY TO_NUMBER(A.CODSUC)";
				
				//System.out.println(querysb);
				//System.out.println(JNDISB);
				List<String> results = new ArrayList<String>();

				consulta.selectPntGenerica(querysb, JNDI);

				rows = consulta.getRows();
				tabla = consulta.getArray();
				for (int i = 0; i < rows; i++) {
					results.add(tabla[i][0]);
				}
				return results;
	    		
	    	}	
	}
}