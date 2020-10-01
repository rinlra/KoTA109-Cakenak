

<%-- include header file --%> 
<%@include file="template_header.jsp" %>

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

<section>
    <div class="container" style="margin-top:5%;">
        <div class="row">
            <div class="alert alert-warning" role="alert" id="error" style="display: none;"></div>
         
            <div class="col-sm-4 float-left">
                <div class="product-details"><!--product-details-->
                        <div class="view-product">
                            <img src="/uploads/images/pembeli/${LoginUser.filegbrakun}" id="imageView" alt="" />

						</div>
				</div>
           	</div>
                    <div class="col-sm-8 float-right">
                    	<h3>Edit profil</h3>
                        <div class="product-information"><!--/product-information-->
                            <form action="Profile" method="post" id="editProfileForm" enctype="multipart/form-data" onsubmit="return validateSignup();">	
                                <h2>${LoginUser.username}</h2>
                                <p>Setting akun</p>
                                <label>Nama</label>
                                <input type="text" placeholder="Name"  class="input-field"  id="editUserName" value="${LoginUser.username}" disabled required/>
                                <input type="hidden" name="username" value="${LoginUser.username}"/>
                                <label>E-mail</label>
                                <input type="email" placeholder="E-mail"  class="input-field" name="email" id="editEmail" value="${LoginUser.email}" required/>
                                <label>Password</label>
                                <input type="Password" placeholder="Password"  class="input-field" name="password" id="password" required/>
                                <label>Konfirmasi Password</label>
                                <input type="Password" placeholder="Konfirmasi Password"  class="input-field" id="confirmpassword" required/>
                               
                                <label>Alamat</label>
                                <input type="text" placeholder="Alamat"  class="input-field" name="alamat" id="editAddress" value="${LoginUser.alamat}" />
                                <label>Kota</label>
                                <input type="text" placeholder="Kota"  class="input-field" name="kota" id="editKota" value="${LoginUser.kota}"/>
                                <label>Provinsi</label>
                                <input type="text" placeholder="Provinsi"  class="input-field" name="provinsi" id="editProvinsi" value="${LoginUser.provinsi}"/>
                                <label>Kodepos</label>
                                <input type="text" placeholder="Kodepos"  class="input-field" name="kodepos" id="editKodepos" value="${LoginUser.kodepos}"/>
                                <label>Nomor Telpon</label>
                                <input type="text" placeholder="Kontak"  class="input-field" name="notelp" id="editKontak" value="${LoginUser.notelp}"/>
                                <label>Credit Card</label>
                                <input type="text" placeholder="Credit Card"  class="input-field" name="creditcard" id="SignupCreditCard" value="${LoginUser.norekening}" required/>
                               

                                <label>Image</label>
                                <input type="file" name="filegbrakun" id="image" accept="images/*" /><br/>
                                <input type="hidden" name="photo" value="${photoscr}"/>
                                <button type="submit" style="margin-top:2%" class="btn btn-default" id="editSubmitBtn">Update</button>
                            </form>
                        </div><!--/product-information-->
                    </div>
                <!--/product-details-->





            
        </div>
    </div>
</section>

<%-- include footer file --%> 
<%@include file="template_footer.jsp" %>
