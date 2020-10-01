<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cakenak - Input Data Penjual</title> 
</head>
<body>
	<form action="ActionController" method="post">
		<input type="hidden" name="idkue" value="${idkue}">
		Nama Komentar : <input type="text" name="namaKomentar"> <BR>
		Jumlah Komentar : <input type="number" name="jumlah"> <BR>
		<input type="hidden" name="action" value="insert_komentar">
		<input type="submit" />
	</form>
</body>
</html>