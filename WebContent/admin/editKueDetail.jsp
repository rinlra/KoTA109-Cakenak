
<%@include file="template_header.jsp" %>

<c:choose>
    <c:when test="${empty product.gambarkue}">
        <c:set var="photo" value="/uploads/images/kue/Cakenak.png"/>
        <c:set var="photoscr" value="Cakenak.png"/>
    </c:when>
    <c:otherwise>
        <c:set var="photo" value="/uploads/images/kue/${product.gambarkue}"/>
        <c:set var="photoscr" value="${product.gambarkue}"/>
    </c:otherwise>
</c:choose>

	<form action="ActionController" style="margin-top: 5%" method="post">
		<div class="form-group mx-auto" style="width: 50%;">
		<input type="text" name="idpenjual" class="form-control" value="${kue.idpenjual}" readonly> <BR>
		<input type="text" name="idkue" class="form-control" value="${kue.idkue}" readonly> <BR>
		Update nama kue : <input type="text" class="form-control" name="namakue" value="${kue.namakue}"> <BR>
		Update jenis : <input type="text" class="form-control" name="jenis" value="${kue.jenis}"> <BR>
		Update harga : <input type="number" class="form-control" name="harga" value="${kue.harga}"> <BR>
<!-- 		Update Jenis kue : <BR>
			<input type="radio" name="jenisJenis" value="dalam">Kue Mandi Dalam<BR>
			<input type="radio" name="jenisJenis" value="luar">Kue Mandi Luar<BR> -->
		Masukan gambar kue : <input type="file" class="form-control" size = "50" name="gambarkue" accept="image/*"> <BR>
		Update stock : <input type="number" class="form-control" name="physicalstock" value="${kue.physicalstock}"> <BR>
		<input type="hidden"  name="availablestock" value="${kue.availablestock}">
		<input type="hidden" name="oldphysicalstock" value="${kue.physicalstock}">
		Update berat : <input type="text" class="form-control" name="berat" value="${kue.berat}"> <BR>
		Update deskripsi : <input type="text" class="form-control" name="deskripsi" value="${kue.deskripsi}"> <BR>
		Update layakjual : <input type="number" class="form-control" name="layakjual" value="${kue.layakjual}"> <BR>
		<input type="hidden" name="action" value="edit_kue">
		<input type="hidden" name="photo" value="${photoscr}"/>
		<input type="submit" class="btn btn-primary" value="Selesai"/>
		</div>
	</form>

<%@include file="template_footer.jsp" %>
