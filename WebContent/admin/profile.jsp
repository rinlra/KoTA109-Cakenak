<%-- include header file --%> 
<%@include file="template_header.jsp" %>

<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>


<!--handle photo --> 
<c:choose>
    <c:when test="${empty userInfo.filegbrakun}">
        <c:set var="photo" value="/uploads/images/pembeli/akunpembelidefault.png"/>
        <c:set var="photoscr" value="akunpembelidefault.png"/>
    </c:when>
    <c:otherwise>
        <c:set var="photo" value="uploads/images/pembeli/${userInfo.filegbrakun}"/>
        <c:set var="photoscr" value="${userInfo.filegbrakun}"/>
    </c:otherwise>
</c:choose>

<div class="col-sm-8 padding-right">
    <div class="product-details"><!--product-details-->
    <div class="row row-cols-2">
        <div class="col-sm-4">
            <div class="view-product">
                <img src="${photo}"  id="imageView" alt="" />
            </div>
        </div>
            
        <div class="col-sm-8">
           
            
            <div class="product-information"><!--/product-information-->
                <form action="AdminProfile" method="post" id="editProfileForm" enctype="multipart/form-data" onsubmit="return validateSignup();">	
                    <h2>Admin - edit data ${userInfo.username}</h2>
                                <p>Setting akun</p>
                                <label>Nama</label>
                                <input type="text" placeholder="Name"  class="input-field"  id="editUserName" value="${userInfo.username}" disabled required/>
                                <input type="hidden" name="username" value="${userInfo.username}"/>
                                <label>E-mail</label>
                                <input type="email" placeholder="E-mail"  class="input-field" name="email" id="editEmail" value="${userInfo.email}" required/>
                                <label>Password</label>
                                <input type="Password" placeholder="Password"  class="input-field" name="password" id="password" required/>
                                <label>Konfirmasi Password</label>
                                <input type="Password" placeholder="Konfirmasi Password"  class="input-field" id="confirmpassword" required/>
                               
                                <label>Alamat</label>
                                <input type="text" placeholder="Alamat"  class="input-field" name="alamat" id="editAddress" value="${userInfo.alamat}" />
                                <label>Kota</label>
                                <input type="text" placeholder="Kota"  class="input-field" name="kota" id="editKota" value="${userInfo.kota}"/>
                                <label>Provinsi</label>
                                <input type="text" placeholder="Provinsi"  class="input-field" name="provinsi" id="editProvinsi" value="${userInfo.provinsi}"/>
                                <label>Kodepos</label>
                                <input type="text" placeholder="Kodepos"  class="input-field" name="kodepos" id="editKodepos" value="${userInfo.kodepos}"/>
                                <label>Nomor Telpon</label>
                                <input type="text" placeholder="Kontak"  class="input-field" name="notelp" id="editKontak" value="${userInfo.notelp}"/>
                                <label>Credit Card</label>
                                <input type="text" placeholder="Credit Card"  class="input-field" name="creditcard" id="SignupCreditCard" value="${userInfo.norekening}" required/>
                               

                                <label>Image</label>
                                <input type="file" name="filegbrakun" id="image" accept="images/*" /><br/>
                                <input type="hidden" name="photo" value="${photoscr}"/>
                                <button type="submit" style="margin-top:2%" class="btn btn-default" id="editSubmitBtn">Update</button>
                </form>
            </div>
            </div>
        </div><!--/product-details-->





    </div>
</div>
</div>
</section>


<%-- include footer file --%> 
<%@include file="template_footer.jsp" %>