<%@ page import="controller.penjual.ServletProdukPenjual" %> 
<%@include file="template_header.jsp" %>

<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>

<div class="col-md-10 float-right">

    <h2>Daftar kue
        <a class="btn btn-default" style="float: right;" href="addproduct.jsp">Input kue baru</a>
    </h2>

    <div class="cart_info"  style="margin-top: 5%">
        <table class="table table-sm">
            <thead>

                <tr class="cart_menu">
                    <td class="image">Item</td>
                    <td class="description"></td>
                    <td class="price">Harga</td>
                    <td class="quantity">Stok</td>
                    <td></td>
                </tr>
            </thead>
            <tbody>
                <c:if test="${!empty requestScope.daftar_kue_penjual}">

                    <c:forEach items="${requestScope.daftar_kue_penjual}" var="product">
                        <tr>
                            <td class="cart_product" style="text-align:center">
                                <a href=""><img src="/uploads/images/kue/${product.gambarkue}" alt=""></a>
                            </td>
                            <td class="cart_description">
                                <p>${product.namakue}</p>
                                
                            </td>
                            <td class="cart_price">
                                <h4>Rp. <fmt:parseNumber value="${product.harga}"/></h4>
                            </td>
                            <c:choose>
							    <c:when test="${product.layakjual == 0}">
									<td class="cart_price">
		                                <p class="text-danger">*TIDAK LAYAK JUAL*</p>
		                            </td>
							    </c:when>
							    <c:otherwise>
								    <td class="cart_price">
		                                <p>${product.physicalstock}</p>
		                            </td>
							    </c:otherwise>
							</c:choose>
                            
                            <td class="cart_delete">
                            	<a class="cart_quantity_delete btn btn-danger" href="KuePenjual?id=${product.idkue}"><i class="fa fa-pencil">Edit</i></a>
                                <a class="cart_quantity_delete btn btn-danger" href="DeleteKue?id=${product.idkue}"><i class="fa fa-times">Delete</i></a>
                                
                                <%-- <a class="cart_quantity_delete" href="AddSlider?id=${product.idkue}"><i class="fa fa-star"></i></a> --%>
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
<%-- include notify file --%> 
<%@include file="notify.jsp" %>