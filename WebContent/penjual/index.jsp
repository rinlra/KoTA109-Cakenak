
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="template_header.jsp" %>
<%-- include slider file --%> 


<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>

<jsp:include page="/IndexPenjual"/>
	<%-- <p>profit : ${profit}</p> --%>
    <div class="col-md-5">
        
        <div class="features_items">
                <div class="product-image-wrapper">
                    <div class="single-products">
                        <div class="productinfo text-center">
                            <i class="fa fa-shopping-bag fa-5x" aria-hidden="true" ></i>
                            <h2>Daftar data produk</h2>

                            <a href="ServletProdukPenjual" class="btn btn-default my_btn"><i class="fa fa-shopping-bag"></i> products</a>
                        </div>
                        <div class="product-overlay">
                            <div class="overlay-content">
                                <h2>Lihat daftar kue, menambahkan, menghapus, dan mengedit data kue</h2>
                                <a href="ServletProdukPenjual" class="btn btn-default my_btn"><i class="fa fa-shopping-bag" ></i> products</a>

                            </div>
                        </div>

                    </div>
                </div>
		</div>
 	</div>
 	
 	<div class="col-md-5">
 	
 		<div class="features_items">
                <div class="product-image-wrapper">
                    <div class="single-products">
                        <div class="productinfo text-center">
                            <i class="fa fa-file-text fa-5x" aria-hidden="true" ></i>
                            <h2>Histori</h2>

                            <a href="AdminHistoryServlet" class="btn btn-default my_btn"><i class=" fa fa-file-text "></i> History</a>
                        </div>
                        <div class="product-overlay">
                            <div class="overlay-content">
                                <h2>Lihat histori </h2>
                                <a href="ServletHistoriPenjual" class="btn btn-default my_btn"><i class="fa fa-file-text" ></i> History</a>

                            </div>
                        </div>

                    </div>
                </div>
	    	</div>
		</div>

       </div>
    </div>
</section>


<%-- include footer file --%> 
<%@include file="template_footer.jsp" %>