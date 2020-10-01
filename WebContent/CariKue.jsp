<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cakenak- Cari Kue</title> 
</head>
<body>
	<form action="ActionController" method="post">
		<p>Cari berdasarkan: <p/>
		<button type="submit"  name="action" value="kue_by_berat">Berat</button><BR>
		<button type="submit"  name="action" value="kue_by_komentar">Komentar yang tersedia</button><BR>
		<button type="submit"  name="action" value="kue_by_harga">Harga</button><BR>
		<button type="submit"  name="action" value="kue_by_jenis">Jenis Kue</button><BR>
	</form>
</body>
</html>