
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

	<div align="center" style="margin-top: 3%">
	<caption><h2>Daftar Kue ${kueList[0].idpenjual}</h2></caption>
		<form action="ActionController" method="post">
			<button type="submit" class="btn btn-primary" name="action" value="retrieve_penjual">Tampilkan Semua Penjual</button>
			<button type="submit" class="btn btn-primary" name="action" value="to_input_kue">Input Kue Baru</button>
			<button type="submit" class="btn btn-primary" name="action" value="search_kue_menu">Cari kue</button>
			<input type="hidden" class="btn btn-primary" name="idpenjual" value="${idpenjual}">
        </form>
        <table cellpadding="5" class="table table-striped table-responsive">
            
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
                <th>Deskripsi</th>
                <th></th>
                <th></th>
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
			            <td>${kue.deskripsi}</td>
			            
			            <td><button type="submit" class="btn btn-primary" name="action" value="delete_kue">Hapus</button></td>
			            <td><button type="submit" class="btn btn-primary" name="action" value="udpdate_kue">Update</button></td>
			        </tr>
			        <input type="hidden" name="idkue" value="${kue.idkue}">
			        <input type="hidden" name="idpenjual" value="${kue.idpenjual}">
			        <input type="hidden" name="namakue" value="${kue.namakue}">
			        <input type="hidden" name="jenis" value="${kue.jenis}">
			        <input type="hidden" name="harga" value="${kue.harga}">
			        <input type="hidden" name="berat" value="${kue.berat}">
			        <input type="hidden" name="deskripsi" value="${kue.deskripsi}">
			        <input type="hidden" name="physicalstock" value="${kue.physicalstock}">
			        <input type="hidden" name="availablestock" value="${kue.availablestock}">
			        <input type="hidden" name="layakjual" value="${kue.layakjual}">
			     </form>
		    </c:forEach>
        </table>
    </div>

<%@include file="template_footer.jsp" %>
