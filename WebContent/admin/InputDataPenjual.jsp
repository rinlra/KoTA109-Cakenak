<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ page import = "javax.servlet.*,java.text.*" %>

<%@include file="template_header.jsp" %>
	<%
         Date dNow = new Date( );
         SimpleDateFormat ft = 
         new SimpleDateFormat ("dd.MM.yyyy HH:mm:ss");
         String date = ft.format(dNow);
    %>
	<form action="ActionController" style="margin-top: 3%" method="post" id="formulario" enctype = "multipart/form-data">
		
		<div class="form-group mx-auto" style="width: 50%;">
		<h3>Input data penjual</h3> <br>
		Masukan Nama : <input type="text" class="form-control" name="namapenjual"> <BR>
		Masukan Nomor Telepon : <input type="number" class="form-control" name="notelp" value=""> <BR>
		Masukan Alamat : <input type="text" class="form-control" name="alamat" value=""> <BR>
		Masukan Username : <input type="text" class="form-control" name="username"> <BR>
		Masukan Password : <input type="password" class="form-control" name="password"> <BR>
		<input type="hidden" class="form-control" name="filegbrakun" value="account.png">
		
		<input type="hidden" name="tglakundibuat" value='<%=date%>'>
		<input type="hidden" name="tipeakun" value="ROLE_SELLER"> 
		Masukan nomor rekening : <input type="text" class="form-control" name="norekening"> <BR>
		<input type="hidden" name="action" value="insert_penjual">
		
		
		<div class="test" id="dynamicInput[0]">
	        <input id="info" type="button" class="btn btn-info" value="Atribut baru" onClick="addInput();">
   		</div>
 		<!--<div id="myDiv" style="display: flex; flex-direction: row;">
				<div class="row">
				  <div class="col">
		          <label style="display: block;" for="newcf">Column Family :</label>
				  <input class="form-control" style="display: block;" type="text" id="newcf" name="newcf" value="NewColumnFamily" />
				  <label style="display: block;" for="newc">Column Qualifier :</label>
				  <input class="form-control" style="display: block;" type="text" id="newc" name="newc" />
				  </div>
				</div>
				<div class="row">
				  <div class="col">
				  <label style="display: block;" for="newv">Value :</label>
				  <input class="form-control" style="display: block;" type="text" id="newv" name="newv" />
				  </div>
				</div>
				</input>
		</div> -->
		<input class="btn btn-primary" type="submit"/>
		</div>
	</form>

<%@include file="template_footer.jsp" %>
