
<%-- include header file --%> 
<%@ page import="controller.admin.AdminUserServlet" %> 

<%@include file="template_header.jsp" %>

<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>

<div class="col-sm-9 padding-right">

    <h2>Daftar Pembeli</h2>


    <div class="table-responsive cart_info">
        <table class="table table-condensed">
            <thead>
                <tr class="cart_menu">
                    <td class="image">Pembeli</td>
                    <td class="description"></td>
                    <td></td>
                </tr>
            </thead>
            <tbody>
                 <c:if test="${!empty requestScope.allUsersAdmin}">

                     <c:forEach items="${requestScope.allUsersAdmin}" var="user">
                <tr>
                    <td class="cart_product" style="text-align:center">
                        <img src="/uploads/images/pembeli/${user.filegbrakun}" alt="">
                    </td>
                    <td class="cart_description">
                    	<h4>${user.username}</h4>
                        <p>${user.nama}</p>
                        <p>${user.email}</p>
                    </td>

                    <td class="cart_delete">
                        <a class="cart_quantity_delete" href="AdminProfile?id=${user.idpembeli}"><i class="fa fa-pencil"></i></a>
                    </td>
                </tr>
                </c:forEach>
                  </c:if>


            </tbody>
        </table>
    </div>

</div>
</div>
</div>
</section> <!--/#cart_items-->


<%-- include footer file --%> 
<%@include file="template_footer.jsp" %>
<%-- include notify file --%> 
<%@include file="notify.jsp" %>

