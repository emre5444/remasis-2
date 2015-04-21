
delete from rol_yetki;

update yetki y
set y.bagli_oldugu_yetki_id = null;

delete from yetki;

ALTER TABLE `yetki`
AUTO_INCREMENT = 1 ;

ALTER TABLE `rol_yetki`
AUTO_INCREMENT = 1 ;


INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (9999,'Tum Kullan?c?larda olmas? gereken yetki',1,'root_yetki',NULL);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (1,'Sistem Yönetimi ??lemleri',1,'sistem_yonetimi',9999);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (2,'Rezidans Yönetimi ??lemleri',1,'rezidans_yonetimi',9999);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (3,'Rapor ??lemleri',1,'rapor_islemleri',9999);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (4,'Anket ??lemleri',1,'anket_islemleri',9999);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (5,'Talep ??lemleri',1,'talep_islemleri',9999);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (6,'Finans ??lemleri',1,'finans_islemleri',9999);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (7,'Envanter ??lemleri',1,'envanter_yonetimi',9999);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (8,'Sistem Ayarlar?',1,'sistem_ayarlari',1);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (9,'Kullan?c? ??lemleri',1,'kullanici_islemleri',1);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (10,'Kullan?c? Sorgulama',1,'kullanici_sorgulama',9);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (11,'Kullan?c? Ekleme',1,'kullanici_ekleme',9);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (12,'Rol / Yetki ??lemleri',1,'rol_yetki_islemleri',1);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (13,'Rol Sorgulama',1,'rol_sorgulama',12);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (14,'Rol Ekleme',1,'rol_ekleme',12);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (15,'Bildirim Tipi ??lemleri',1,'yetki_bildirim_tipi_islemleri',1);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (16,'Bildirim Tipi Hedef Kitle Sorgulama',1,'yetki_bildirim_tipi_islemleri',15);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (17,'Blok ??lemleri',1,'blok_islemleri',2);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (18,'Blok Sorgulama',1,'blok_sorgulama',17);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (19,'Blok Ekleme',1,'blok_ekleme',17);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (20,'Daire ??lemleri',1,'daire_islemleri',2);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (21,'Daire Sorgulama',1,'daire_sorgula',20);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (22,'Daire Ekleme',1,'daire_ekleme',20);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (23,'Aidat Ekleme',1,'aidat_ekleme',6);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (24,'Ödeme Ekleme',1,'odeme_ekleme',6);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (25,'Aidat Sorgulama',1,'aidat_sorgulama',6);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (26,'Anket Sorgulama',1,'anket_sorgulama',4);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (27,'Anket Ekleme',1,'anket_ekleme',4);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (28,'Site ?çi Payla??m ??lemleri',1,'site_ici_paylasim_islemleri',9999);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (29,'Belge ??lemleri',1,'belge_islemleri',28);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (30,'Duyuru ??lemleri',1,'duyuru_islemleri',28);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (31,'?lan ??lemleri',1,'ilan_islemleri',28);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (32,'Talep Sorgulama',1,'talep_sorgulama',5);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (33,'Ar?za Talebi ??lemleri',1,'yetki_ariza_talep',32);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (34,'Aidat ?tiraz Talebi ??lemleri',1,'yetki_aidat_itiraz_talep',32);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (35,'?ikayet Talebi ??lemleri',1,'yetki_sikayet_talep',32);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (36,'Belge ve DökümanTalebi ??lemleri',1,'yetki_belge_talep',32);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (37,'Talep Onaylama',1,'talep_onaylama',5);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (38,'Talep Reddetme',1,'talep_reddetme',5);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (39,'Talep ?ptal Etme',1,'talep_iptali',5);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (40,'Envanter Sorgulama',1,'envanter_sorgulama',7);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (41,'Envanter Giri?i',1,'envanter_girisi',7);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (42,'Kategori ??lemleri',1,'kategori_islemleri',7);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (43,'Kategori Sorgulama',1,'kategori_sorgulama',42);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (44,'Kategori Ekleme',1,'kategori_ekleme',42);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (45,'Daire Baz?nda Borçlu Daire Listesi Raporu',1,'yetki_r101',3);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (46,'Dönem Baz?nda Aidat Detay Raporu',1,'yetki_r201',3);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (47,'Belge Ekleme',1,'belge_ekleme',29);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (48,'Belge Silme',1,'belge_silme',29);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (49,'Belge ?ndirme',1,'belge_indirme',29);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (50,'Duyuru Ekleme',1,'duyuru_ekleme',30);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (51,'Duyuru Silme',1,'duyuru_silme',30);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (52,'Duyuru Görüntüleme',1,'duyuru_goruntuleme',30);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (53,'Anket Oy Kullanma',1,'anket_katilma',26);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (54,'Anket Görüntüleme',1,'anket_goruntuleme',26);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (55,'?lan Ekleme',1,'ilan_ekleme',31);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (56,'?lan Silme',1,'ilan_silme',31);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (57,'?lan Görüntüleme',1,'ilan_goruntuleme',31);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (58,'?leti?im Bilgileri ??lemleri',1,'iletisim_bilgileri_islemleri',28);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (59,'?leti?im Bilgisi Ekleme',1,'iletisim_bilgisi_ekleme',58);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (60,'?leti?im Bilgisi Güncelleme',1,'iletisim_bilgisi_guncelleme',58);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (61,'Envanter Görüntüleme',1,'envanter_goruntuleme',7);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (62,'Envanter Güncelleme',1,'envanter_guncelleme',7);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (63,'Envanter Silme',1,'envanter_silme',7);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (64,'Kategori Silme',1,'kategori_silme',42);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (65,'Aidat Gunceleme',1,'aidat_guncelleme',6);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (66,'Aidat Silme',1,'aidat_silme',6);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (67,'Blok Güncelleme',1,'blok_guncelleme',17);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (68,'Blok Silme',1,'blok_silme',17);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (69,'Daire Güncelleme',1,'daire_guncelleme',20);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (70,'Daire Silme',1,'daire_silme',20);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (71,'Daire Görüntüleme',1,'daire_goruntuleme',20);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (72,'Kullan?c? Görüntüleme',1,'kullanici_goruntuleme',10);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (73,'Kullan?c? Güncelleme',1,'kullanici_guncelleme',10);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (74,'Kullan?c? Rol Atama',1,'kullanici_rol_atama',10);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (75,'Kullan?c? Daire ?li?kilendirme',1,'kullanici_daire_iliskilendirme',10);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (76,'Kullan?c? ?ifre S?f?rlama',1,'kullanici_sifre_sifirlama',10);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (77,'Anket Detay Görüntüleme',1,'anket_detay_goruntule',4);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (78,'Anket Güncelleme',1,'anket_guncelleme',26);
INSERT INTO `yetki` (`id`,`ad`,`durum_id`,`link`,`BAGLI_OLDUGU_YETKI_ID`) VALUES (79,'Anket Silme',1,'anket_silme',26);
