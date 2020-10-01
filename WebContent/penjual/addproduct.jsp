
<%-- include header file --%> 
<%@include file="template_header.jsp" %>

<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>

<%-- include myTags library --%> 
<%@taglib prefix="lib" uri="/WEB-INF/tlds/myTags_library.tld" %>

<c:if test="${empty requestScope.type}">
    <c:set var="type" value="Add"/>
</c:if>

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

<div class="col">
    <div class="product-details"><!--product-details-->
    <div class="row row-cols-2">
        <div class="col-4 float-right">
            <div class="view-product">
                <img src="${photo}"  id="imageView" alt="" />
            </div>
        </div>
        
        <div class="col-md-8 float-right">
            <div class="product-information"><!--/product-information-->
                <form action="KuePenjual" method="post" enctype="multipart/form-data" >
                    <h2>${jenis} Tambah Kue Untuk Dijual</h2>
                    <label>Nama kue</label>
                    <input type="text" placeholder="Nama kue" name="ProductName" value="${product.namakue}" class="input-field" id="ProductName" required/>
                    <label>Harga</label>
                    <input type="number" placeholder="Harga" name="ProductPrice" value="${product.harga}" step="0.01" class="input-field" id="ProductPrice" required/>
                    <label>Jumlah stok</label>
                    <input type="number" placeholder="Stok" name="ProductQuantity" value="${product.physicalstock}" class="input-field" id="ProductQuantity" required/>
                    <label>Berat</label>
                    <input type="number" placeholder="Berat" name="berat" value="${product.berat}" step="0.1" class="input-field" id="ProductPrice" required/>
                    <label>Tanggal produksi</label>
                    <input type="text" placeholder="dd-mm-yyyy" name="tglproduksi" value="${product.tglproduksi}" class="input-field" id="inputtgl1" required/>
                    <label>Tanggal best use</label>
                    <input type="text" placeholder="dd-mm-yyyy" name="tglbaiksblm" value="${product.tglbaiksblm}" class="input-field" id="inputtgl2" required/>
                    <label>Deskripsi kue</label>
                    <textarea  placeholder="Product Description" name="ProductDescription" class="input-field">${product.deskripsi}</textarea>
                    <%-- <label>Kategori</label>
                    <lib:SelectCategory selectID="${product.category}"/> --%>
                    <!-- <label id="yourBtn" onclick="getFile()">Upload gambar..
	                    <div style='height: 0px;width: 0px; overflow:hidden;'><input type="file" name="gambarkue" id="upfile" accept="image/*" onchange="sub(this)"/></div>
                    </label><br/> -->
                    <label>Gambar</label>
                    <input type="file"  name="gambarkue" id="image" accept="image/*"/><br/>
                    <input type="hidden" name="id" value="${product.idkue}"/>
                    <input type="hidden" name="jenis" value="1"/>
                    <input type="hidden" name="photo" value="${photoscr}"/>
                    <input type="hidden" name="oldphysicalstock" value="${$product.physicalstock}"/>
                    <button type="submit" class="btn btn-primary">${jenis} Input Data Kue</button>
                </form>


            </div><!--/product-information-->
        </div>
        </div>
    </div><!--/product-details-->





</div>
</div>
</div>
</section>


<%@include file="template_footer.jsp" %>