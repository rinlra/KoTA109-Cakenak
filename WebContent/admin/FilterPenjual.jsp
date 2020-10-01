<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cakenak - Filter Minimal Jumlah Kue Penjual</title>
</head>
<body>
	<form action="ActionController" method="post">
			Minimal Jumlah Kue : <input type="number" name="jumlahKue"><BR>
			<input type="hidden" name="action" value="filter_jumlah_kue">
			<input type="submit" />
		</form>
</body>
</html>