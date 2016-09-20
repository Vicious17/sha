create or replace FUNCTION "COUNT_SHAINCIDENCIAS" (pbusqueda varchar, pci varchar, pincap varchar, ptipacc varchar)
RETURN VARCHAR2 AS 
vcount number;
BEGIN
  SELECT count(*) into vcount 
  FROM  (
         SELECT  CI,NOMBRE,GENERO,CARGO,FECHA,TURNO,AREAEVENT,TIPOINCAP,TIPOACC,TIPOLES,UBILES,DESCHEC,INPSASEL
         FROM SHAINCIDENCIAS
         WHERE CI||NOMBRE||GENERO||CARGO||FECHA||TURNO||AREAEVENT||TIPOINCAP||TIPOACC||TIPOLES||UBILES||DESCHEC||INPSASEL like '%'||pbusqueda||'%'
         AND CI like '%'||pci||'%'
         AND TIPOINCAP like '%'||pincap||'%'
         AND TIPOACC like '%'||ptipacc||'%'
         );
return vcount;
END;