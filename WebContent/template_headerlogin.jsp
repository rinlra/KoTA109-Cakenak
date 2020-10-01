<!doctype html>
<html>
<head>
	<meta charset="UTF-8">

	<title>Cakenak</title>	
	<!-- <link rel="stylesheet" type="text/css" href="<?php echo base_url() ?>assets/css/style.css"> -->
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
   	<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
	<link href="css/bootstrap2.min.css" rel="stylesheet" type="text/css"> -->
	<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="css/prettyPhoto.css" rel="stylesheet" type="text/css">
    <link href="css/animate.css" rel="stylesheet" type="text/css">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <link href="css/responsive.css" rel="stylesheet" type="text/css">
    <link href="css/price-range.css" rel="stylesheet" type="text/css"/>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@taglib prefix="myCate" uri="/WEB-INF/tlds/myTags_library.tld" %>
</head>
<body>
	
        <header>
            <div id='headertext' >
            <nav class="navbar navbar-expand-lg navbar-light bg-light" >
                <a class="nav-link" href="index.jsp" class="headerBar" id="homeBtn" style="font-family: 'Cream Cake'; color: #FF7C93; font-size:3vw;"> Cakenak </a>
                <!-- <a class="navbar-brand" href="#">Navbar</a> -->
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                
                <div class="col-6" style="margin-right: 3rem;">
                           
                </div>
                
                <div class="shop-menu float-right">
                <div class="navbar-collapse collapse" id="navbarNav"> 
                    <ul class="navbar-nav" style="margin-right: 2rem;">
                    <c:if test="${empty LoginUser}">
                    <li class="nav-item " style="padding-right: 2rem; margin-bottom: 0.5rem;">
                        <a class="nav-link" href="index.jsp" class="headerBar" id="homeBtn">HOME </a>
                    </li>
                    <li class="nav-item" style="padding-right: 2rem;">
                        <a class="nav-link" href="Shop?page=1" class="headerBar" id="shopBtn">SHOP</a>
                    </li>
                    </c:if>
                    <c:if test="${!empty LoginUser && LoginUser.tipeakun =='ROLE_COSTUMER' }">
                                        <li class="nav-item" style="padding-right: 10rem;">
                                        	<a href="Profile"><i class="fa fa-user"></i> ${LoginUser.username}</a>
                                        </li>
                                        <li class="nav-item" style="padding-right: 10rem;">
                                        	<a href="CartHandlerServlet"><i class="fa fa-shopping-cart"></i> Cart (<span id="number"></span> )</a>
                                        </li>
                                        <li class="nav-item" style="padding-right: 10rem;"><a href="#">
                                        	<i class="fa fa-usd"></i>${LoginUser.cash}</a>
                                        </li>
                                         <!-- <li class="nav-item" style="padding-right: 10rem;">
                                         	<a href="ScratchCards.jsp"><i class="fa fa-cc-visa"></i> Charge </a>
                                         </li> -->
                                        <li class="nav-item" style="padding-right: 10rem;">
                                        	<a href="logout"><i class="fa fa-sign-out"></i> Logout</a>
                                        </li>
                    	<script type="text/javascript">var userID = '${LoginUser.idpembeli}';</script>
                    </c:if>
                                        
                    <c:if test="${!empty LoginUser && LoginUser.tipeakun =='ROLE_ADMIN' }">
                    	<li class="nav-item" style="padding-right: 10rem;"><a href="admin"><i class="fa fa-cog"></i> Panel Admin </a></li>
                    	<li class="nav-item" style="padding-right: 10rem;"><a href="logoutadmin"><i class="fa fa-sign-out"></i> Logout</a></li>
                    </c:if>
                    
                    <c:if test="${!empty LoginUser && LoginUser.tipeakun =='ROLE_SELLER' }">
                    	<li class="nav-item" style="padding-right: 10rem;"><a href="penjual"><i class="fa fa-cog"></i> Panel Seller </a></li>
                    	<li class="nav-item" style="padding-right: 10rem;"><a href="logoutpenjual"><i class="fa fa-sign-out"></i> Logout</a></li>
                    </c:if>

                    <c:if test="${empty LoginUser}">
                    <li class="nav-item" style="padding-right: 2rem;" >
                        <button class="btnd btn-secondary dropdown-toggle navbar-toggle" style="margin-bottom: 2%;" type="button" 
										  id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										    Login
										  </button>
										  <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
										    <a class="dropdown-item" href="login.jsp">Pembeli</a>
										    <a class="dropdown-item" href="penjual/loginpenjual.jsp">Penjual</a>
										    <a class="dropdown-item" href="admin/loginadmin.jsp">Admin</a>
										  </div>
                    </li>
                    </c:if>

                    
                    </ul>
                </div>
                </div>
            </nav>
        </div>
  </header>
		<div id="contentwrapper">


