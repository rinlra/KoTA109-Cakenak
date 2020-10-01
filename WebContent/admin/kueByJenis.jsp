<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cakenak - Filter Kue Jenis</title>
</head>
<body>
	<form action="ActionController" method="post">
			Jenis kue : <BR>
			<input type="radio" name="jenis" value="dalam">Kue Mandi Dalam<BR>
			<input type="radio" name="jenis" value="luar">Kue Mandi Luar<BR>
			<button type="submit"  name="action" value="kueByJenis">Cari</button>
		</form>
</body>
</html>