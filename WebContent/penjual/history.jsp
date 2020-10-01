
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="controller.penjual.ServletHistoriPenjual" %> 
<%@include file="template_header.jsp" %>

<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>
<div class="col-sm-9 padding-right">

    <h2>Histori transaksi - ${LoginUser.username}
    	<a class="btn btn-primary" style="float: right;" href="LaporanJual">Laporan Penjualan</a>
    </h2>




    <div class="table-responsive cart_info">
        <table class="table table-condensed">
            <thead>

                <tr class="cart_menu">
                    <td class="quantity">Id transaksi</td>
                    <td class="description">Tanggal pembelian</td>
                    <td class="quantity">Quantity</td>
                    <td class="quantity">Id kue</td>
                    <td class="quantity">Idpembeli</td>
					<td class="quantity">Status transaksi</td>
                    <td></td>
                </tr>
            </thead>
            <tbody>

                <c:if test="${!empty requestScope.allHistoryPenjual}">

                    <c:forEach items="${requestScope.allHistoryPenjual}" var="history">
                        <tr>
                           <td class="cart_quantity" style="word-wrap: break-word; word-break: break-all;">
                                <p>${history.idtransaksi}</p>
                            </td>
                            <td class="cart_description">
                                <p>${history.tglpembelian}</p>

                            </td>
                            <td class="cart_quantity">
                                <p>${history.quantity}</p>
                            </td>
                            <td class="cart_quantity">
                                <p>${history.idkue}</p>
                            </td>
                            <td class="cart_quantity">
                                <p>${history.idpembeli}</p>
                            </td>
                          	<td class="cart_quantity">
                                <c:choose>
								    <c:when test="${history.statustransaksi==0}">
								       	<button type="Submit" class="btn btn-danger">Belum bayar</button>
								    </c:when>
								    <c:when test="${history.statustransaksi==1}">
								       	<button type="Submit" class="btn btn-success">Sudah bayar</button>
								       	
								    </c:when>
								    <c:otherwise>
								        <button type="Submit" class="btn btn-info">Sudah dikirim</button>
								    </c:otherwise>
								</c:choose>
                            </td>
                          
                        </tr>
                    </c:forEach>
                </c:if>


            </tbody>
        </table>
    </div>

</div>
</div>
</div>
</section> <!--/#cart_items-->

<%-- include footer file --%> 
<%@include file="template_footer.jsp" %>

