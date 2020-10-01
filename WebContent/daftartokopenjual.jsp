<%@ page import="controller.Shop" %> 
<%-- include header file --%> 
<%@include file="template_header.jsp" %>


<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>

<div class="col-sm-9 padding-right">
    <div class="features_items"><!--features_items-->
        <h2 class="title text-center">Daftar Toko</h2>

        <c:if test="${!empty requestScope.allStore}">
			<div class="row">
            <c:forEach items="${requestScope.allStore}" var="toko">

                <div class="col-4">
                    <div class="product-image-wrapper">
                        <div class="single-products">
                            <div class="productinfo text-center">
                                <img src="/uploads/images/penjual/${toko.filegbrakun}" alt="" />
                                <h2>${toko.namapenjual}</h2>
                                <p>${toko.alamat}</p>
                                <a href="#" class="btn btn-default add-to-cart"><i class="fa fa-chevron-right"></i>List Kue</a>
                            </div>
                            <div class="product-overlay">
                                <div class="overlay-content">
                                    <h2>${toko.namapenjual}</h2>
                                    <p>${toko.alamat}</p>
                                     <a href="TokoKue?page=1&amp;idpenjual=${toko.idpenjual}" id="${toko.idpenjual}" class="btn btn-default"><i class="fa fa-chevron-right"></i>ListKue</a>
                                	<input type="hidden" name="idpenjual" value="${toko.idpenjual}"/>
                                </div>
                            </div>
                        </div>
                        <div class="choose">
                            <ul class="nav nav-pills nav-justified">
                                <%-- <li><a href="toko?id=${toko.idpenjual}"><i class="fa fa-plus-square"></i>View Details</a></li> --%>
                            </ul>
                        </div>
                    </div>
                </div>


            </c:forEach>
            </div>
        </c:if>

        <!-- End Sara features_items-->

    </div><!--features_items-->






    <ul class="pagination">
        <%--handel Category--%>
        <c:if test="${not empty query}">
            <c:set var="stringQuery" value="&cate=${query}"/>
        </c:if>
        
        <%--to display Previous arrow except for the 1st page --%>
        <c:if test="${currentPage != 1}">
             <li><a href="DaftarToko?page=${currentPage - 1}${stringQuery}">&laquo;</a></li>
	</c:if>
        
        <%--to displaying Page numbers--%>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage == i}">
                    <li class="active"><a href="">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                    <li><a href="DaftarToko?page=${i}${stringQuery}">${i}</a></li>

                </c:otherwise>
            </c:choose>
        </c:forEach>

        <%--to display Next arrow --%>
        <c:if test="${currentPage lt noOfPages}">
            <li><a href="DaftarToko?page=${currentPage + 1}${stringQuery}">&raquo;</a></li>
	</c:if>
    </ul>
</div>
</div>
</div>
</section>

<%-- include footer file --%> 
<%@include file="template_footer.jsp" %>