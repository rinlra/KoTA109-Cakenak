<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cakenak - Filter Kue Harga</title>
</head>
<body>
	<form action="ActionController" method="post">
			Harga minimum (perbulan) : <input type="number" name="minHarga"><BR>
			Harga maksimum (perbulan) : <input type="number" name="maxHarga"><BR>
			<button type="submit"  name="action" value="kueByHarga">Cari</button>
		</form>
</body>
</html>