
<%@ page import="controller.admin.AdminKategoriServlet" %> 
<%@include file="template_header.jsp" %>

<%-- include slidebar file --%> 
<%@include file="slidebar.jsp" %>

<div class="col-sm-9 padding-right">

    <h2>Category
       <a class="btn btn-default" style="float: right;" href="addcategory.jsp">New Category</a>
    </h2>

    <div class="table-responsive cart_info">
        <table class="table table-condensed">
            <thead>

                <tr class="cart_menu">
                    <td class="id">Id</td>
                    <td class="name">Name</td>
                </tr>
            </thead>
            <tbody>
            <c:if test="${!empty requestScope.allKategorisAdmin}">

                    <c:forEach items="${requestScope.allKategorisAdmin}" var="category">
                        <tr>
                         
                            <td class="cart_id">
                                <p>${category.id}</p>
                                
                            </td>
                    
                            <td class="cart_id">
                                <p>${category.name}</p>
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