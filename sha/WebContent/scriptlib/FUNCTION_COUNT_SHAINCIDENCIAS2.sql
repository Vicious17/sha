create or replace FUNCTION "COUNT_SHAINCIDENCIAS2" (pbusqueda varchar, pci varchar, pincap varchar, ptipacc varchar, psuc varchar)
RETURN VARCHAR2 AS 
vcount number;
BEGIN
  SELECT count(*) into vcount 
  FROM  (
         SELECT  CI,NOMBRE,GENERO,CARGO,FECHA,TURNO,AREAEVENT,TIPOINCAP,TIPOACC,TIPOLES,UBILES,DESCHEC,INPSASEL,CENTOP
         FROM SHAINCIDENCIAS, SHABVT002
         WHERE CENTOP = SUCURSAL
         AND CI||NOMBRE||GENERO||CARGO||TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI')||TURNO||AREAEVENT||TIPOINCAP||TIPOACC||TIPOLES||UBILES||DESCHEC||INPSASEL||CENTOP like '%'||pbusqueda||'%'
         AND CI like '%'||pci||'%'
         AND TIPOINCAP like '%'||pincap||'%'
         AND TIPOACC like '%'||ptipacc||'%'
         AND CENTOP like '%'||psuc||'%'
         );
return vcount;
END;