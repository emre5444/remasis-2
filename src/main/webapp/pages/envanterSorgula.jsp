<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: fcabi
  Date: 10.01.2015
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<c:if test="${empty envanter}">
  <h3> Aradığınız kayıt bulunamadı </h3>
</c:if>
<c:if test="${not empty envanter}">
<table>
  <tr>
    <td style="color: red;">Barkod No</td>
    <td> : ${envanter.barkodNo}</td>
  </tr>
  <tr>
    <td style="color: red;">Kategori</td>
    <td> : ${envanter.kategori.aciklama}</td>
  </tr>
  <tr>
    <td style="color: red;">Marka</td>
    <td> : ${envanter.marka}</td>
  </tr>
  <tr>
    <td style="color: red;">Model</td>
    <td> : ${envanter.model}</td>
  </tr>
  <tr>
    <td style="color: red;">Ürün adı</td>
    <td> : ${envanter.urunAdi}</td>
  </tr>
  <tr>
    <td style="color: red;">Satıcı Firma</td>
    <td> : ${envanter.saticiFirma}</td>
  </tr>
  <tr>
    <td style="color: red;">Alımı Yapan Personel</td>
    <td> : ${envanter.alimiYapanPersonel}</td>
  </tr>
  <tr>
    <td style="color: red;">Zimmetli Personel</td>
    <td> : ${envanter.zimmetliPersonel}</td>
  </tr>
  <tr>
    <td style="color: red;">Departman</td>
    <td> : ${envanter.departman}</td>
  </tr>
  <tr>
    <td style="color: red;">Alım Zamanı</td>
    <td> : ${envanter.alimTarihi}</td>
  </tr>
  <tr>
    <td style="color: red;">Garanti Başlangıç Zamanı</td>
    <td> : ${envanter.garantiBaslangicTarihi}</td>
  </tr>
  <tr>
    <td style="color: red;">Garanti Süresi</td>
    <td> : ${envanter.garantiSuresi}</td>
  </tr>
  <tr>
    <td style="color: red;">Miktar</td>
    <td> : ${envanter.miktar}</td>
  </tr>
  <tr>
    <td style="color: red;">Birim Fiyat</td>
    <td> : ${envanter.birimFiyat}</td>
  </tr>
  <tr>
    <td style="color: red;">Açıklama</td>
    <td> : ${envanter.aciklama}</td>
  </tr>
  <tr>
    <td style="color: red;">Tanıtım Zamanı</td>
    <td> : ${envanter.tanitimZamani}</td>
  </tr>
</table>
</c:if>
</body>
</html>
