
<%@ include file="template_header.jsp"%>
<section>
	<form action="Signup" method="post" id="SignupForm" onsubmit="return validateSignup();" style="margin: 3%;">
        <center>
        <div class="form-group" style="width: 50%;">
             <input type="text" class="form-control" name="username" placeholder="Username" required>
        </div>
        <div class="form-group" style="width: 50%;">
             <input type="password" class="form-control" name="password" id="password" placeholder="Password" required>
        </div>
        <div class="form-group" style="width: 50%;">
        	<input type="password" class="form-control" name="confirmpassword" id="confirmpassword" placeholder="Confirm Password" required>
        </div>
        <div class="form-group" style="width: 50%;">
            <input type="email" class="form-control" name="email" placeholder="Email" required>
        </div>
        <div class="form-group" style="width: 50%;">
            <input type="text" class="form-control" name="nama" placeholder="Nama" required>
        </div>
        <div class="form-group" style="width: 50%;">
            <input type="text" class="form-control" name="alamat" placeholder="Alamat" required>
        </div>
        <div class="form-group" style="width: 50%;">
            <input type="text" class="form-control" name="notelp" placeholder="Nomor Telepon" required>
        </div>
        </center>
        <button type="submit" class="btn btn-primary" style="margin-left: 25%;">Register</button>
        
      </form>
</section>
<%@ include file="template_footer.jsp"%>




