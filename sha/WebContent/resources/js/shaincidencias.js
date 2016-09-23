	/*
	 *  Copyright (C) 2011  ANDRES DOMINGUEZ, CHRISTIAN DÃ�AZ
	
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
	
	function limpiar()
	{  //Llamado por el boton limpiar
	    document.getElementById("formshaincidencias:vop").value="0";
	    clearUpdateInput('formshaincidencias:ci_input', 'white');
	    clearUpdateInput('formshaincidencias:nombre', 'white');
	    clearUpdateInput('formshaincidencias:genero', 'white');
	    clearUpdateInput('formshaincidencias:cargo', 'white');
	    clearUpdateInput('formshaincidencias:nombre', '#F2F2F2');
	    clearUpdateInput('formshaincidencias:genero', '#F2F2F2');
	    clearUpdateInput('formshaincidencias:cargo', '#F2F2F2');
	}
	
	function enviar(vT0,vT1,vT2,vT3,vT4,vT5,vT6,vT7,vT8,vT9,vT10,vT11,vT12,vT13,vT14,vT15){
		  document.getElementById("formshaincidencias:ci_input").value= rTrim(vT0);
		  document.getElementById("formshaincidencias:nombre").value= rTrim(vT1);
		  document.getElementById("formshaincidencias:genero").value= rTrim(vT2);
		  document.getElementById("formshaincidencias:cargo").value= rTrim(vT3);
		  document.getElementById("formshaincidencias:fevento_input").value= rTrim(vT4);
		  document.getElementById("formshaincidencias:turno").value= rTrim(vT5);
		  document.getElementById("formshaincidencias:centop_input").value= rTrim(vT6);
		  document.getElementById("formshaincidencias:area").value= rTrim(vT7);
		  document.getElementById("formshaincidencias:tipoin_input").value= rTrim(vT8);
		  document.getElementById("formshaincidencias:tipoac_input").value= rTrim(vT9);
		  document.getElementById("formshaincidencias:tipole").value= rTrim(vT10);
		  document.getElementById("formshaincidencias:cuerpo").value= rTrim(vT11);
		  document.getElementById("formshaincidencias:hechos").value= rTrim(vT12);
		  document.getElementById("formshaincidencias:inpsasel").value= rTrim(vT13);
		  document.getElementById("formshaincidencias:regist").value= rTrim(vT14);
		  document.getElementById("formshaincidencias:vop").value= rTrim(vT15);
		  updateInput('formshaincidencias:ci_input', '#F2F2F2');
		}
	
	function detalle(vT0,vT1,vT2,vT3,vT4,vT5,vT6,vT7){
		$("#txt_det_8").text(vT0);
		$("#txt_det_14").text(vT1);
		$("#txt_det_9").text(vT2);
		$("#txt_det_10").text(vT3);
		$("#txt_det_11").text(vT4);
		$("#txt_det_12").text(vT5);
		$("#txt_det_13").text(vT6);
		$("#txt_det_1").text(vT7);
	}
	
	function modal(vT0){
		if(vT0=="1"){
		$( document ).ready( function() {
		    $( '#myModal' ).modal( 'toggle' );
		});
	}
	}
	
	
	function dismissModal(){
		$( document ).ready( function() {
		    $( '#myModal' ).modal( 'hide' );
		});
	}
	
	//Modal2
	function mymodal2(vT0,vT1,vT2,vT3,vT4,vT5){
		$( document ).ready( function() {
			$("#myModal2").modal( 'toggle' );
			$("#txt_det_2").text(vT0);
			$("#txt_det_3").text(vT1);
			$("#txt_det_4").text(vT2);
			$("#txt_det_5").text(vT3);
			$("#txt_det_6").text(vT4);
			$("#txt_det_7").text(vT5);
		});
	}
	

	//Modal2 Hide
	function mymodal2Hide(){
		$("#myModal2").modal('hide');
	}