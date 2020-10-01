
<%@ include file="template_header.jsp"%>

	<div align="center" style="margin-top: 3%">
		<form action="ActionController" method="post">
			<button class="btn btn-primary" type="submit"  name="action" value="retrieve_all_kue">Tampilkan Semua Kue</button>
			<button class="btn btn-primary" type="submit"  name="action" value="retrieve_penjual">Tampilkan Semua Penjual</button>
			<button class="btn btn-primary" type="submit"  name="action" value="to_input_penjual">Input Penjual Baru</button>
			<button class="btn btn-primary" type="submit"  name="action" value="to_filter_penjual">Filter</button>
        </form>
		<table cellpadding="5" class="table table-striped table-responsive">
            <tr>
                <th>Nama</th>
                <th>Nomor Telepon</th>
                <th>Alamat</th>
                <th>Username</th>
                <th>filegbrakun</th>
                <th>Tipe akun</th>
                <th>Nomor rekening</th>
                <th>Daftar Kue</th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${info_penjual}" var="info">
            	<form action="ActionController" method="post">
			        <tr>
			            <td>${info.namapenjual}</td>
			            <td>${info.notelp}</td>
			            <td>${info.alamat}</td>
			            <td>${info.username}</td>
			            <td>${info.filegbrakun}</td>
			            <td>${info.tipeakun}</td>
			            <td>${info.norekening}</td>
			            <td>${info.totalkue}</td>
			            <td><button type="submit" class="btn btn-primary" name="action" value="delete_penjual">Hapus</button></td>
			            <td><button type="submit" class="btn btn-primary" name="action" value="update_penjual">Update</button></td>
			    		<td align="center"><button class="btn btn-primary" type="submit"  name="action" value="kueInfo">Daftar Kue</button></td>
			        </tr>
			        <input type="hidden" name="idpenjual" value="${info.idpenjual}">
			        <input type="hidden" name="namapenjual" value="${info.namapenjual}">
			        <input type="hidden" name="alamat" value="${info.alamat}">
			        <input type="hidden" name="notelp" value="${info.notelp}">
			        <input type="hidden" name="filegbrakun" value="${info.filegbrakun}">
			        <input type="hidden" name="tipeakun" value="${info.tipeakun}">
			        <input type="hidden" name="username" value="${info.username}">
			        <input type="hidden" name="password" value="${info.password}">
			        <input type="hidden" name="tipeakun" value="${info.tipeakun}">
			        <input type="hidden" name="pendapatan" value="${info.pendapatan}">
			        <input type="hidden" name="statusverifikasi" value="${info.statusverifikasi}">
			        <input type="hidden" name="norekening" value="${info.norekening}">
			     </form>
		    </c:forEach>
        </table>
    </div>

<%@ include file="template_footer.jsp"%>
</html>