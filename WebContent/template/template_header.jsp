<!doctype html>
<html>
<head>
	<meta charset="UTF-8">

	<title>Template </title>	
	<!-- <link rel="stylesheet" type="text/css" href="<?php echo base_url() ?>assets/css/style.css"> -->
	<!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
	<link href="css/bootstrap2.min.css" rel="stylesheet" type="text/css">
    <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="css/prettyPhoto.css" rel="stylesheet" type="text/css">
    <link href="css/animate.css" rel="stylesheet" type="text/css">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <link href="css/responsive.css" rel="stylesheet" type="text/css">
    <link href="css/price-range.css" rel="stylesheet" type="text/css"/>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@taglib prefix="myCate" uri="myTags_library.tld" %>
</head>
<body>
	<div id="wrapper">
        <header>
            <div id='headertext' >
            <nav class="navbar navbar-expand-lg navbar-light bg-light" >
                <h1 style="font-family: 'Cream Cake'; color: #FF7C93;"> Cakenak </h1>
                <!-- <a class="navbar-brand" href="#">Navbar</a> -->
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                <div class="col-sm-8">
                            <div class="search_box pull-right">
                                <form action="SearchForProduct">
                                <input type="text" placeholder="Search" name="search"/>
                                </form>
                            </div>
                </div>
                
                <div class="navbar-collapse collapse" id="navbarNav"> 
                    <ul class="navbar-nav ml-auto" style="margin-right: 10px;">
                    <li class="nav-item " style="padding-right: 10px;">
                        <a class="nav-link" href="home">HOME </a>
                    </li>
                    <li class="nav-item" style="padding-right: 10px;">
                        <a class="nav-link" href="cart">SHOP</a>
                    </li>
                    <li class="nav-item" style="padding-right: 10px;">
                        <a class="nav-link" href="cart">CART</a>
                    </li>
                    <c:if test="${!empty LoginUser && LoginUser.tipeakun =='ROLE_COSTUMER' }">
                                        <li class="nav-item" style="padding-right: 10px;">
                                        	<a href="Profile"><i class="fa fa-user"></i> ${LoginUser.username}</a>
                                        </li>
                                        <li class="nav-item" style="padding-right: 10px;">
                                        	<a href="CartHandlerServlet"><i class="fa fa-shopping-cart"></i> Cart (<span id="number"></span> )</a>
                                        </li>
                                        <li class="nav-item" style="padding-right: 10px;"><a href="#">
                                        	<i class="fa fa-usd"></i> ${LoginUser.cash}</a>
                                        </li>
                                         <li class="nav-item" style="padding-right: 10px;">
                                         	<a href="ScratchCards.jsp"><i class="fa fa-cc-visa"></i> Charge </a>
                                         </li>
                                        <li class="nav-item" style="padding-right: 10px;">
                                        	<a href="logout"><i class="fa fa-sign-out"></i> Logout</a>
                                        </li>
                    	<script type="text/javascript">var userID = '${LoginUser.idpembeli}';</script>
                    </c:if>
                                        
                    <c:if test="${!empty LoginUser && LoginUser.tipeakun =='ROLE_ADMIN' }">
                    	<li class="nav-item" style="padding-right: 10px;"><a href="admin"><i class="fa fa-cog"></i> Admin Panel</a></li>
                    	<li class="nav-item" style="padding-right: 10px;"><a href="admin/logout"><i class="fa fa-sign-out"></i> Logout</a></li>
                    </c:if>

                    <c:if test="${empty LoginUser}">
                    <li class="nav-item" style="padding-right: 10px;" >
                        <a class="nav-link" href="login.jsp" >LOGIN</a>
                    </li>
                    </c:if>

                    
                    </ul>
                </div>
            </nav>
        </div>
        </header>



