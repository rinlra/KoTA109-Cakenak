
<%@ include file="template_headerlogin.jsp"%>
<img src="images/home/logo.png" width="55px" height="55px" class="img-responsive" alt="" style="margin-top:4%; margin-left:auto; margin-right:auto;"/>
<h3 style="margin-top:3px; text-align:center;">Registrasi Pembeli</h3>
<section>
	<form action="Signup" method="post" id="SignupForm" onsubmit="return validateSignup();" style="margin: 3%; padding-bottom: 10%">
	
        <div class="form-group mx-auto" style="width: 50%;">
             <input type="text" class="form-control" name="username" placeholder="Username" required>
        </div>
        <div class="form-group mx-auto" style="width: 50%;">
             <input type="password" class="form-control" name="password" id="password" placeholder="Password" required>
        </div>
        <div class="form-group mx-auto" style="width: 50%;">
        	<input type="password" class="form-control" name="confirmpassword" id="confirmpassword" placeholder="Confirm Password" required>
        </div>
        <div class="form-group mx-auto" style="width: 50%;">
            <input type="email" class="form-control" name="email" placeholder="Email" required>
        </div>
        <div class="form-group mx-auto" style="width: 50%;">
            <input type="text" class="form-control" name="nama" placeholder="Nama" required>
        </div>
        <div class="form-group mx-auto" style="width: 50%;">
            <input type="text" class="form-control" name="alamat" placeholder="Alamat" required>
        </div>
        <div class="form-group mx-auto" style="width: 50%;">
            <input type="text" class="form-control" name="notelp" placeholder="Nomor Telepon" required>
        </div>
        <div style="margin-left: 25%; margin-right: 25%;">
        <button type="submit" class="btn btn-primary float-left mx-auto">Register</button>
        </div>
        
      </form>
</section>
<%@ include file="template_footer.jsp"%>




