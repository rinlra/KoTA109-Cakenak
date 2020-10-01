<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div align="center">
		<form action="HomeController" method="get">
			<button type="submit"  name="action" value="retrieve_penjual">Tampilkan Semua Penjual</button>
			<button type="submit"  name="action" value="to_input_kue">Input Kue Baru</button>
			<button type="submit" name="action" value="search_kue_menu">Cari kue</button>
			<input type="hidden" name="idpenjual" value="${idpenjual}">
        </form>
        <table border="1" cellpadding="5" class="table table-striped">
            <caption><h2>List of Kue</h2></caption>
            <tr>
                <th>idkue</th>
                <th>idpenjual</th>
                <th>namakue</th>
                <th>jenis</th>
                <th>harga</th>
                <th>gambarkue</th>
                <th>physical_stock</th>
                <th>available_stock</th>
                <th>berat</th>
                <th>komentar</th>
            </tr>
            <c:forEach items="${kueList}" var="kue">
            	<form action="ActionController" method="post">
			        <tr>
			            <td>${kue.idkue}</td>
			            <td>${kue.idpenjual}</td>
			            <td>${kue.namakue}</td>
			            <td>${kue.jenis}</td>
			           	<td>${kue.harga}</td>
			            <td><img src="/uploads/images/kue/${kue.gambarkue}" width="50" height="50"/></td>
			            <td>${kue.physicalstock}</td>
			            <td>${kue.availablestock}</td>
			            <td>${kue.berat}</td>
			            <!--td>${kue.komentar} (${kue.jenis})</td-->
			            <td align="center"><button type="submit"  name="action" value="edit_komentar">Edit Komentar</button>
						<c:forEach items="${kue.getKomentar()}" var="komentar">
							<li style="list-style-type:none;">${komentar.namaKomentar}, ${komentar.jumlah}</li>
						</c:forEach>
						</td>
			            <td><button type="submit"  name="action" value="delete_kue">Hapus</button></td>
			            <td><button type="submit"  name="action" value="update_kue">Update</button></td>
			        </tr>
			        <input type="hidden" name="idkue" value="${kue.idkue}">
			        <input type="hidden" name="idpenjual" value="${kue.idpenjual}">
			        <input type="hidden" name="namakue" value="${kue.namakue}">
			        <input type="hidden" name="jenis" value="${kue.jenis}">
			        <input type="hidden" name="harga" value="${kue.harga}">
			        <input type="hidden" name="gambar" value="${kue.gambarkue}">
			        <input type="hidden" name="physicalstock" value="${kue.physicalstock}">
			        <input type="hidden" name="availablestock" value="${kue.availablestock}">
			        <!--  input type="hidden" name="komentar" value="${kue.getKomentar()}"-->
			     </form>
		    </c:forEach>
        </table>
    </div>
</body>

</html>