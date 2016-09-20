create or replace FUNCTION "COUNT_SHABVT002" (pbusqueda varchar2, pb_codrol varchar2)
  RETURN number AS
vcount number;
begin
select count(*) into vcount 
from SHABVT002
where coduser||desuser  like '%'||pbusqueda||'%'
and b_codrol  like '%'||pb_codrol||'%';
return vcount;
end;