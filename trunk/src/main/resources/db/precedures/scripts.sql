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
CREATE DEFINER=`root`@`localhost` PROCEDURE `varsayilan_kullanici_belirle`()
BEGIN


DECLARE c_daire_kodu , c_sakin_first ,c_sakin_last , c_malik_first , c_malik_last ,c_blok_adi VARCHAR(45);
DECLARE c_daire_no INT;
DECLARE l_blok_id INT;
DECLARE l_daire_id INT;
DECLARE l_kullanici_id INT;
DECLARE l_kullanici_count INT;

DECLARE l_kullanici_daire_count INT;
DECLARE l_kullanici_daire_count1 INT;


DECLARE l_kullanici_daire_id INT;

DECLARE l_sakin_count INT;
DECLARE l_malik_count INT;




  DECLARE daire_list CURSOR FOR
                select d.id
                from daire d;

  OPEN daire_list;
  read_loop: LOOP
    FETCH daire_list INTO l_daire_id;


                                               -- kaç tane kullan?c? daire var die bak?l?r
                                                               select count(kd.id)
                                                               into l_kullanici_daire_count
                                                               from kullanici_daire kd
                                                               where kd.daire_id = l_daire_id;

                                               -- eger bir den fazla varsa direk sakine bak?caz
                                                  if l_kullanici_daire_count > 1 then

                                                               -- sakin say?s?ndan emin olruz
                                                               select count(kd.id)
                                                               into l_sakin_count
                                                               from kullanici_daire kd
                                                               where kd.daire_id = l_daire_id
                                                               and kd.kallanici_tipi_id = 2;

                                                               -- sakin say?s? 0 dan buyukse
                                                               if l_sakin_count > 0 then

                                                                                 select kd.id
                                                                                              into l_kullanici_daire_id
                                                                                              from kullanici_daire kd
                                                                                              where kd.daire_id = l_daire_id
                                                                                              and kd.kallanici_tipi_id = 2
                                                                                              LIMIT 1;

                                                                              update kullanici_daire kd
                                                                              set kd.varsayilan_mi = 2
                                                                              where kd.id = l_kullanici_daire_id;

                                                               update kullanici_daire kd
                                                                              set kd.varsayilan_mi = 1
                                                                              where kd.daire_id = l_daire_id
                                                                              and kd.id != l_kullanici_daire_id;


                                                               ELSE

                                                                              select kd.id
                                                                                              into l_kullanici_daire_id
                                                                                              from kullanici_daire kd
                                                                                              where kd.daire_id = l_daire_id
                                                                                              and kd.kallanici_tipi_id = 1
                                                                                              LIMIT 1;

                                                                              update kullanici_daire kd
                                                                              set kd.varsayilan_mi = 2
                                                                              where kd.id = l_kullanici_daire_id;

                                                                              update kullanici_daire kd
                                                                              set kd.varsayilan_mi = 1
                                                                              where kd.daire_id = l_daire_id
                                                                              and kd.id != l_kullanici_daire_id;



                                                               END IF;


                                                               ELSE
                                               -- kaç tane kullan?c? daire var die bak?l?r
                                                               select count(kd.id)
                                                               into l_kullanici_daire_count1
                                                               from kullanici_daire kd
                                                               where kd.daire_id = l_daire_id;

                                                               if l_kullanici_daire_count1 > 0 then

                                                       update kullanici_daire kd
                                                                              set kd.varsayilan_mi = 2
                                                                              where kd.daire_id = l_daire_id;

                                        END IF;


                                                  END IF;


  END LOOP;
  CLOSE daire_list;









END
