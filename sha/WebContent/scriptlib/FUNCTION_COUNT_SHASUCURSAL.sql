create or replace FUNCTION "COUNT_SHASUCURSAL" (pbusqueda varchar)
RETURN VARCHAR2 AS 
vcount number;
BEGIN
  SELECT count(*) into vcount 
  FROM  (
         SELECT  CODSUC,DESSUC
         FROM SHASUCURSAL
         WHERE CODSUC||DESSUC like '%'||pbusqueda||'%'
         );
return vcount;
END;