<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="template_header.jsp" %>
<%@ page import="controller.Checkout"%>
<body>
<section>
    <div id="container" style="margin: 100px">
    <c:set var="total" value="${0}"/>
    <c:set var="index" value="${0}"/>
        <div class="col-md-12">
        <div class="row" style="padding-bottom: 2%;">
            <div class="col-8">
            <legend><strong>Form Data Pembeli</strong></legend>
                <label> Nama : </label>
                ${LoginUser.nama}
                <label> No Handphone : </label>
                ${LoginUser.notelp}<br>
                <label> Alamat : </label>
                ${LoginUser.alamat}, Kota ${LoginUser.kota}, ${LoginUser.kodepos}
            </div>
        </div>
        </div>
                <table width="100%" style="text-align:center;margin: 40px 0;" class="table">
            <tr>
                <th style="width:10%;text-align:center">Id</th>
                <th style="width:20%;text-align:center">Nama Kue</th>
                <th style="width:20%;text-align:center">Penjual</th>
                <th style="width:20%;text-align:center">Qty</th>
                <th style="width:10%;text-align:center">Berat </th>
                <th style="width:20%;text-align:center">Harga</th>
                <th style="width:20%;text-align:center">Total</th>

            </tr>
        <c:if test="${!empty requestScope.carts}">
                       

			<c:forEach items="${requestScope.carts}" var="cart">
            <tr>
                <td>${cart.idkue}</td>
                <td>${kue[index].namakue}</td>
                <td>${penjual[index].namapenjual}</td>
                <td>${cart.quantity}</td>
                <td>${kue[index].berat}</td>
                <td>${cart.harga}</td>
                <td>${cart.harga*cart.quantity}</td>
                <td>
                </td>
            </tr>
            	<c:set var="index" value="${index+1}"/>
            	<c:set var="total" value="${total+(cart.harga*cart.quantity)}"/>
            </c:forEach>
		</c:if>
            
        </table>
        <div class="col-sm-12 text-right">
        
                <legend><strong>Informasi Pembayaran</strong></legend>
                <label> <strong>Total Harga : </strong></label>
                 Rp. ${total}<br>
         
         </div>
         <div class="payment-options" style="margin-top: 2%">
         <a class="btn btn-primary" href="CartHandlerServlet" >Kembali</a>
         <a class="btn btn-primary float-right" href="Pay" style="margin-right: 1%">Pay</a>   
        </div>
        </div>
</section>
</body>
<%@include file="template_footer.jsp" %>