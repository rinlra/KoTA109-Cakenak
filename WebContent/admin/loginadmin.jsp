
<%@ include file="template_header.jsp"%>
<section style="margin-bottom: 10%">
<img src="../images/home/logo.png" width="55px" height="55px" class="img-responsive mx-auto d-block" alt="" style="margin-top:5%;"/>
<h3 style="margin-top:3px; text-align:center;">Login Admin</h3>
    <form action="SigninAdmin" method="post" id="SigninForm" style="margin: 3%;">
        <div class="form-group mx-auto" style="width: 50%;">
          <input type="text" class="form-control" name="username" placeholder="Username" required>
        </div>
        <div class="form-group mx-auto" style="width: 50%;">
          <input type="password" class="form-control" name="password" placeholder="Password" required>
        </div>
        <div style="margin-left: 25%; margin-right: 25%;">
        <button type="submit" class="btn btn-primary float-right">Login</button><br><br>
        </div>
      </form>  
</section>
<%@ include file="template_footer.jsp"%>