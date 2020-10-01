<%@ include file="template_header.jsp"%>

	<form action="ActionController" style="margin-top: 5%" method="post">
		<div class="form-group mx-auto" style="width: 50%;">
		<h3>Update data ${info.idpenjual}</h3>
			Update Nama : <input type="text" class="form-control" name="namapenjual" value="${info.namapenjual}" ><BR>
			Update Nomor Telepon : <input type="text" class="form-control" name="notelp" value="${info.notelp}" ><BR>
			Update Alamat : <input type="text" class="form-control" name="alamat" value="${info.alamat}" ><BR>
			Update Username : <input type="text" class="form-control" name="username" value="${info.username}"> <BR>
			Update Password : <input type="text" class="form-control" name="password" value="${info.password}"> <BR>
			Update Gambar : <input type="text" class="form-control" name="filegbrakun" value="${info.filegbrakun}"> <BR>
			Update Nomor Rekening : <input type="text" class="form-control" name="norekening" value="${info.norekening}"> <BR>
			<input type="hidden" name="tglakundibuat" value="${info.tglakundibuat}">
			<input type="hidden" name="tipeakun" value="${info.tipeakun}">
			<input type="hidden" name="idpenjual" value="${info.idpenjual}">
			<input type="hidden" name="pendapatan" value="${info.pendapatan}">
			<input type="hidden" name="action" value="edit_penjual">
			<input class="btn btn-primary" type="submit" />
			</div>
		</form>
<%@ include file="template_footer.jsp"%>