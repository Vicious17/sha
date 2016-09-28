create or replace FUNCTION "COUNT_SHAMETAS" (pbusqueda varchar, psuc varchar)
RETURN VARCHAR2 AS 
vcount number;
BEGIN
  SELECT count(*) into vcount 
  FROM  (
         SELECT  ANIO, MES, SUCURSAL, PROMEDIO, CUMPLIMIENTO, NUMTRABAJADOR, REUNIONES, HEADCOUNT
         FROM SHAMETAS
         WHERE ANIO||MES||SUCURSAL||PROMEDIO||CUMPLIMIENTO||NUMTRABAJADOR||REUNIONES||HEADCOUNT like '%'||pbusqueda||'%'
         AND SUCURSAL like '%'||psuc||'%'
         );
return vcount;
END;