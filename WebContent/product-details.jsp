
<%-- include header file --%> 
<%@include file="template_header.jsp" %>


<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>

<div class="col-sm-12 padding-right" style="margin: auto;">
    <div class="product-details"><!--product-details-->
    <div class="row row-cols-2">
        <div class="col-sm-5">
            <div class="view-product">
                <img src="/uploads/images/kue/${product.gambarkue}" alt=""/>

            </div>


        </div>
        <div class="col-sm-7">
            <div class="product-information"><!--/product-information-->
                <h2>${product.namakue}</h2>
                <p>Penjual kue: ${namapenjualkue}</p>
                <span>
                    <span>Rp. ${product.harga}</span><br>
                    <label>Jumlah:</label>
                    <input type="number" id="quan" value="1" min="1" /><br>
                    <button type="button" id="${product.idkue}" class="btn btn-fefault cart add-to-my" style="margin-top:10px;">
                        <i class="fa fa-shopping-cart"></i>
                        Masukkan ke cart
                    </button>
                </span>
                <p> Stok tersedia : ${product.availablestock} </p><br/>
                <div class="form-check">
				  <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
				  <label class="form-check-label" for="defaultCheck1">
				    Tanggal produksi : ${product.tglproduksi} | Baik sebelum : ${product.tglbaiksblm}<br/>
				  </label>
				</div>
				<div class="form-check">
				  <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
				  <label class="form-check-label" for="defaultCheck1">
				   	Tanggal produksi : ${product.tglproduksi} | Baik sebelum : ${product.tglbaiksblm}<br/>
				  </label>
				</div>
				<div class="form-check">
				  <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
				  <label class="form-check-label" for="defaultCheck1">
				    Tanggal produksi : ${product.tglproduksi} | Baik sebelum : ${product.tglbaiksblm}<br/>
				  </label>
				</div>
                
                
                
                <p><b>Deskripsi</b> ${product.deskripsi}</p>
                <%-- <%-- <span>Add on : ${product.tgldiinput} | Model : ${product.model} </span> --%>

            </div><!--/product-information-->
        </div>
        </div>
    </div><!--/product-details-->



    <div class="recommended_items"><!--recommended_items-->
        <!-- <h2 class="title text-center">recommended items</h2> -->

        <div id="recommended-item-carousel" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner">
                ${loop.index}
                
                <c:forEach items="${recomed}" var="item"  varStatus="loop">
                    
                    <c:if test="${loop.index == 0}">
                        <div class="item active">
                    </c:if>
                    <c:if test="${loop.index == 3}">
                        <div class="item">
                    </c:if>
                    
                    <div class="col-sm-4">
                        <div class="product-image-wrapper">
                            <div class="single-products">
                                <div class="productinfo text-center">
                                    <a href="Product?id=${item.idkue}"><img src="${item.gambar}" /></a>
                                    <h2>Rp. ${item.harga}</h2>
                                    <p>${item.namakue}</p>
                                    <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <c:if test="${loop.index ==2  || loop.index == 5 || loop.index == fn:length(recomed)-1}">
                        </div>
                    </c:if>
                
                </c:forEach>
   
<!--             </div>
            <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
                <i class="fa fa-angle-left"></i>
            </a>
            <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
                <i class="fa fa-angle-right"></i>
            </a>			
        </div>
    </div>/recommended_items -->

</div>
</div>
</div>
</section>

<%-- include footer file --%> 
<%@include file="template_footer.jsp" %>
