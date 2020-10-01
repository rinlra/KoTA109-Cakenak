
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="controller.admin.AdminHistoryServlet" %> 
<%@include file="template_header.jsp" %>

<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>
<div class="col-sm-9 padding-right">

    <h2>History</h2>




    <div class="cart_info">
        <table class="table table-sm table-responsive">
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

                <c:if test="${!empty requestScope.allHistoryAdmin}">

                    <c:forEach items="${requestScope.allHistoryAdmin}" var="history">
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
                                <p>${history.statustransaksi}</p>
                            </td>
                          	<td class="cart_delete" style="margin-left: -1rem">
                          		<a class="cart_quantity_delete" href="AdminProduct?id=${product.productId}"><i class="fa fa-pencil">Konfirmasi</i></a>
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

