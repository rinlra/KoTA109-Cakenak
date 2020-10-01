

<%-- include header file --%> 
<%@include file="template_header.jsp" %>
<%@ page import="controller.CartHandlerServlet" %> 




<section id="cart_items">
    <div class="container">

        <div class="review-payment">
            <h2>Cart Pembeli ${LoginUser.username}</h2>
        </div>

        <div class="table-responsive cart_info">
            <table class="table table-condensed">
                <thead>
                    <tr class="cart_menu">
                        <td class="image">Item</td>
                        <td class="description"></td>
                        <td class="harga">Price</td>
                        <td class="quantity">Quantity</td>
                        <td class="total">Total</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>



                    <c:set var="total" value="${0}"/>
					<c:set var="index" value="${0}"/>
                    <c:if test="${!empty requestScope.carts}">
                       

                        <c:forEach items="${requestScope.carts}" var="cart">
                            <tr>



                                <td class="cart_product">
                                    <a href=""><img src="/uploads/images/kue/${cart.gambar}" alt=""></a>
                                </td>
                                <td class="cart_description">
                                   
                                    <p>Product ID: ${cart.idkue}</p>
                                    <p>Penjual : ${penjual[index]}</p>
                                </td>
                                <td class="cart_price">

                                    <p id="P_${cart.idcart}">Rp. <fmt:parseNumber value="${cart.harga}"/></p>
                                </td>
                                <td class="cart_quantity">
                                    <div class="cart_quantity_button">
                                        <a class="cart_quantity_up" id="U_${cart.idcart}"> + </a>
                                        <input class="cart_quantity_input" 
                                               type="text" name="quantity" id="V_${cart.idcart}"
                                               value="${cart.quantity}" autocomplete="off" 
                                               size="2" disabled>
<!--                                        <p>${cart.quantity}</p>-->
                                        <a class="cart_quantity_down" id="D_${cart.idcart}" > - </a>
                                    </div>
                                </td>
                                <td class="cart_total">
                                    <p class="cart_total_price"  id="T_${cart.idcart}">
                                            Rp. ${cart.harga*cart.quantity}
                                    </p>

                                </td>
                                <td class="cart_delete">
                                    <INPUT type="hidden" id="hiddenCart" value="${cart.idcart}"/>
                                  
                                    <a  href="DeleteCart?id=${cart.idcart}"><i class="fa fa-times"></i></a>
                         

                                </td>
                                <c:set var="total" value="${total+(cart.harga*cart.quantity)}"/>
                            </tr>
                            <c:set var="index" value="${index+1}"/>
                        </c:forEach>
                    </c:if>


                    <tr>
                        <td colspan="4">&nbsp;</td>
                        <td colspan="2">
                            <table class="table table-condensed total-result">
                                <tr>
                                    <td>Cart Sub Total</td>
                                    <td id="subTotal">Rp. <fmt:parseNumber value="${total}"/></td>
                                </tr>
                                <tr class="shipping-cost">
                                    <td>Shipping Cost</td>
                                    <td>Free</td>										
                                </tr>
                                <tr>
                                    <td>Total</td>
                                    <td><span id="total">Rp. <fmt:parseNumber value="${total}"/>
                                        </span></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div >
            <a class="btn btn-primary" href="Checkout">Checkout</a>
        </div>
    </div>
</section> <!--/#cart_items-->

<%-- include footer file --%> 
<%@include file="template_footer.jsp" %>
