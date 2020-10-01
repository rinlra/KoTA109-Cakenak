
<%@ page import="controller.HomeController"%> 
<%--<jsp:include page="/IndexProductServlet"/> --%> 
<%-- include header file --%> 
<%@ include file="template_header.jsp"%>


<%-- include slider file --%> 
<%-- <%@include file="slider.jsp" %>


<%-- include slidebar file --%> 
<section style="margin-top: 5%;">
<%@include file="slidebar.jsp" %>
<jsp:include page="/HomeController"/>
<div class="col-sm-12 padding-right">
    <div class="features_items"><!--features_items-->
        <h2 class="title text-center">Daftar Kue</h2>

        <c:if test="${!empty requestScope.kueList}">
			<div class="row">
            <c:forEach items="${requestScope.kueList}" var="product">
            	<c:if test="${product.layakjual != 0}">
                <div class="col-4">
                    <div class="product-image-wrapper">
                        <div class="single-products">
                            <div class="productinfo text-center">
                                <img src="/uploads/images/kue/${product.gambarkue}" alt="" width="200px" height="200px"/>
                                <h2>Rp. ${product.harga}</h2>
                                <p>${product.namakue}</p>
                                <a class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
                            </div>
                            <div class="product-overlay">
                                <div class="overlay-content">
                                    <h2>Rp. <fmt:parseNumber value="${product.harga}"/></h2>
                                    <p>${product.namakue}</p>
                                    <c:if test="${product.availablestock > 0 }">
                                    <a id="${product.idkue}" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
                                    </c:if>
                                    <c:if test="${product.availablestock == 0 }">
                                    <button class="btn btn-default add-to-cart" disabled><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                    </c:if>
                                </div>
                            </div>
                            <img src="images/home/new.png" class="new" alt="" />
                        </div>
                        <div class="choose">
                            <ul class="nav nav-pills nav-justified">
                                <li><a href="Product?id=${product.idkue}"><i class="fa fa-plus-square"></i>View Details</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                </c:if>
            </c:forEach>
            </div>
        </c:if>

    </div><!--features_items-->
</div>
</section>

<%-- include footer file --%> 
<%@include file="template_footer.jsp" %>
