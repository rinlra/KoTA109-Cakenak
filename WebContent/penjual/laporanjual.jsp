<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ page import="controller.penjual.ServletHistoriPenjual" %> 
<!DOCTYPE html>
<html>
<head>
	<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="../css/prettyPhoto.css" rel="stylesheet" type="text/css">
    <link href="../css/animate.css" rel="stylesheet" type="text/css">
    <link href="../css/main.css" rel="stylesheet" type="text/css">
<meta charset="ISO-8859-1">
<title>Laporan jual ${LoginUser.username}</title>

</head>
<header>
<div id='headertext' >
            <nav class="navbar navbar-expand-lg navbar-light bg-light" >
                <a class="nav-link" href="index.jsp" class="headerBar" id="homeBtn" style="font-family: 'Cream Cake'; color: #FF7C93; font-size:3vw;"> Cakenak </a>
                <!-- <a class="navbar-brand" href="#">Navbar</a> -->
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            </nav>
            <h1 class="ml-auto d-block" style="text-align: center;">Laporan Penjualan ${LoginUser.username}</h1>
</div>
</header>
<body>
	<div class="table-responsive cart_info mx-auto" style="width: 90%; margin-top: 5%">
        <table border="1" class="table table-striped table-condensed">
            <thead>
			
                <tr class="cart_menu">
                    <td class="quantity">Id transaksi</td>
                    <td class="description">Tanggal pembelian</td>
                    <td class="quantity">Quantity</td>
                    <td class="quantity">Id kue</td>
                    <td class="quantity">Idpembeli</td>
					<td class="quantity">Status transaksi</td>
                    <td class="quantity">Status pengiriman</td>
                </tr>
            </thead>
            <tbody>

                <c:if test="${!empty requestScope.allHistoryPenjual}">

                    <c:forEach items="${requestScope.allHistoryPenjual}" var="history">
                        <tr>
                           <td class="cart_quantity" style="word-wrap: break-word; word-break: break-all;">
                                <p>${history.idtransaksi}</p>
                            </td>
                            <td class="cart_description">
                                <p>${history.tglpembelian}</p>

                            </td>
                            <td class="cart_quantity">
                                <p>${history.quantity}</p>
                            </td>
                            <td class="cart_quantity">
                                <p>${history.idkue}</p>
                            </td>
                            <td class="cart_quantity">
                                <p>${history.idpembeli}</p>
                            </td>
                          	<td class="cart_quantity">
                                <p>${history.statustransaksi}</p>
                            </td>
                            <td class="cart_quantity">
                                <p>${history.statussellerpaid}</p>
                            </td>
                          
                        </tr>
                    </c:forEach>
                </c:if>


            </tbody>
        </table>
    </div>
</body>
</html>