
<c:if test="${not empty AlertMessage}">
    <script>showNotification('${AlertMessage}','${AlertType}');</script>
    <c:remove var="AlertMessage" scope="session" />
</c:if>