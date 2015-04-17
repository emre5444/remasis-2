// 1 nolu role yetki atam scripti

-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `role_yetki_atama`()
BEGIN



 DECLARE income INT;

   SET income = 0;

insert into rol_yetki (rol_id , yetki_id)
values(1 , 9999);


   label1: LOOP
     SET income = income + 1;
     IF income < 80 THEN

insert into rol_yetki (rol_id , yetki_id)
values(1 , income);

       ITERATE label1;
     END IF;
     LEAVE label1;
   END LOOP label1;

END
-- --------------------------------------------------------------------------------