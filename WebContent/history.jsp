
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="controller.admin.AdminHistoryServlet" %> 
<%@include file="template_header.jsp" %>

<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>

<div class="col-sm-16 padding-right">

    <h2 style="margin-top: 5%">Histori transaksi ${LoginUser.username}</h2>




    <div class="cart_info">
        <table class="table table-sm">
            <thead>

                <tr class="cart_menu">
                    <td class="quantity">Id transaksi</td>
                    <td class="description">Tanggal pembelian</td>
                    <td class="quantity">Quantity</td>
                    <td class="quantity">Id kue</td>
                    <td class="quantity">Idpembeli</td>
					<td class="quantity" style="padding-left: 3rem;">Status transaksi</td>
                    <td></td>
                </tr>
            </thead>
            <tbody>

                <c:if test="${!empty requestScope.allHistoryPembeli}">

                    <c:forEach items="${requestScope.allHistoryPembeli}" var="history">
                        <tr style="border-bottom: 1px solid #ddd;">
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
                          	<td class="cart_delete" style="padding-left: 2rem; text-align: center;">
                          		<c:choose>
								    <c:when test="${history.statustransaksi==0}">
								       	<p>Belum bayar</p>
								    </c:when>
								    <c:when test="${history.statustransaksi==1 && history.statussellerpaid == 0}">
								       	<p>Sudah bayar</p>
								       	<a href="UpdateStatusTransaksi?idtransaksi=${history.idtransaksi}" class="btn btn-info">Belum diterima</a>
								    </c:when>
								    <c:otherwise>
								        <p>Sudah bayar</p>
								       	<a href="#" class="btn btn-success">Sudah diterima</a>
								    </c:otherwise>
								</c:choose>
								<input type="hidden" name="idtransaksi" value="${history.idtransaksi}"/>
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

