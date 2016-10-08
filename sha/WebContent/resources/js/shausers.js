	/*
	 *  Copyright (C) 2011  MAURICIO ALBANESE
	
	    Este programa es software libre: usted puede redistribuirlo y/o modificarlo 
	    bajo los tÃ©rminos de la Licencia PÃºblica General GNU publicada 
	    por la FundaciÃ³n para el Software Libre, ya sea la versiÃ³n 3 
	    de la Licencia, o (a su elecciÃ³n) cualquier versiÃ³n posterior.
	
	    Este programa se distribuye con la esperanza de que sea Ãºtil, pero 
	    SIN GARANTÃ�A ALGUNA; ni siquiera la garantÃ­a implÃ­cita 
	    MERCANTIL o de APTITUD PARA UN PROPÃ“SITO DETERMINADO. 
	    Consulte los detalles de la Licencia PÃºblica General GNU para obtener 
	    una informaciÃ³n mÃ¡s detallada. 
	
	    DeberÃ­a haber recibido una copia de la Licencia PÃºblica General GNU 
	    junto a este programa. 
	    En caso contrario, consulte <http://www.gnu.org/licenses/>.
	 */

	function suchide() {
	    //alert("llame la funcion suchide");
			document.getElementById('formshausers:suc').style.display = 'none';
			document.getElementById('formshausers:suc').checked = false;	
	} 
	
	function sucshow() {
	    //alert("llame la funcion suchide");
			document.getElementById('formshausers:suc').style.display = 'block';
	} 

	function myFunction() {
	    //alert("Page is loaded");
		if (document.getElementById('formshausers:b_codrol').value = "1"||null) {
			//alert("entre al if 1");
			document.getElementById('formshausers:suc').style.display = 'none';
		}
		else {
			//alert("entre al else");
			document.getElementById('formshausers:suc').style.display = 'block';
	    }		
	} 
	
	function inputshow() {		
	    //alert("entre al input show");
		var valor = document.getElementById('formshausers:b_codrol_input');
		var validar = valor.value;
		//alert(validar.split(" ",1));
		if (validar.split(" ",1)==2) {		
			//alert("entre al if");
			//alert(validar.split(" ",1));
			document.getElementById('formshausers:suc').style.display = 'block';
	    }	
		
		else  {
			//alert("entre al else");
			//alert(validar.split(" ",1));
			document.getElementById('formshausers:suc').style.display = 'none';
			document.getElementById('formshausers:suc').checked = false;
	    }		
	} 
	
	function limpiar()
	{  //Llamado por el boton limpiar
	    document.getElementById("formshausers:vop").value="0";
	    clearUpdateInput('formshausers:b_codrol_input', 'white');
	    document.getElementById('formshausers:suc').style.display = 'none';
	}
	