create or replace FUNCTION "COUNT_SHATIPAC" (pbusqueda varchar)
RETURN VARCHAR2 AS 
vcount number;
BEGIN
  SELECT count(*) into vcount 
  FROM  (
         SELECT  CODIGO,DESCR
         FROM SHATIPAC
         WHERE CODIGO||DESCR like '%'||pbusqueda||'%'
         );
return vcount;
END;