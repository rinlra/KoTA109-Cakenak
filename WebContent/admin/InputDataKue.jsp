<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@include file="template_header.jsp" %>
<head>
<meta charset="ISO-8859-1">
<title>Cakenak - Input Data Kue</title>
</head>
<body>
	<center>
	<form action="ActionController" method="post" enctype = "multipart/form-data" style="width: 70%;"> 
		<input type="hidden" name="idpenjual" value="${idpenjual}"> <BR>
		Masukan nama kue : <input type="text" class="form-control" name="namakue"> <BR>
		Masukan jenis : <input type="number" class="form-control" name="jenis"> <BR>
		Masukan harga : <input type="number" class="form-control" name="harga"> <BR>
		Masukan gambar kue : <input type="file" class="form-control" size = "50" name="gambarkue"> <BR>
		Masukan stock : <input type="number"  class="form-control"name="physicalstock"> <BR>
		Masukan berat : <input type="text" class="form-control"  name="berat"> <BR>
		Masukan deskripsi : <input type="text" class="form-control"  name="deskripsi" value=" "> <BR>
		<button type="submit" class="btn btn-primary" name="action" value="insert_kue">Input Kue Data</button>
	</form>
	</center>>
</body>
<%@include file="template_footer.jsp" %>
