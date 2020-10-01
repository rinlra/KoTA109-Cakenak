$(document).ready(function () {

    //---------------- event on page ------------------------
    //init number of product for user
    if ("idpembeli" in window)
        getInitProductNumber(idpembeli);

    //add in home & shop page
    $('.add-to-cart').click(function () {
        var id = $(this).attr('id');
        addProduct(id, 1);
    });

    //add from product details
    $('.add-to-my').click(function () {
        var id = $(this).attr('id');
        var quantity = $("#quan").val();
        addProduct(id, quantity);
    });
    
    $(".cart_quantity_down").click(function(){
        var id = $(this).attr('id').substring(2);
        //check if number of product greater than zero 
        if($("#V_"+id).val() > 1 )
            reduceProduct(id);
    });
    
    $(".cart_quantity_up").click(function(){
        var id = $(this).attr('id').substring(2);
        increaseProduct(id);
    });
 
    //------------------ function on page ---------------------

    // add product to cart
    function addProduct(id, quantity) {
        $.ajax({
            url: 'addCart', //servlet url
            type: 'GET',
            data: {"idkue": id, "quantity": quantity},
            success: (data) => {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                }else{
                    $("#number").html(data);
                    showNotification('Kue ditambahkan kedalam cart','success');
                }
                
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert("error");
                if (thrownError.redirect.length) {
                    window.location.replace(thrownError.redirect);
                } else {
                    alert('There was an error processing your request, please try again');
                }
            }
        });
    }

    function getInitProductNumber(id) {
        $.ajax({
            url: 'getCartCount', //servlet url
            type: 'GET',
            data: {"id": id},
            success: (data) => {
                $("#number").html(data);
            }
        });
    }
    
    //reduce product from cart 
    function reduceProduct(id){
       $.ajax({
            url: 'ReduceQuantity', //servlet url
            type: 'GET',
            data: {"id": id},
            success: (data) => {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                }else{
                    $("#number").html(data);
                    showNotification('quantity kue di cart berkurang','success');
                    reCalculateDecrease(id);
                }
                
            }
        });
    }
    
    //increase product to cart 
    function increaseProduct(id){
        $.ajax({
            url: 'increaseQuantity', //servlet url
            type: 'GET',
            data: {"id": id},
            success: (data) => {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                }else{
                    $("#string").html(data);
                    showNotification('quantity kue di cart bertambah','success');
                    reCalculateIncrease(id);
                }
                
            }
        });
    }
    
    //recalculate price and total price of prodcut
    function reCalculateDecrease(id){
        //get price for peice 
        // #P_id is id div for price & substring to remove $ sign
        var price = parseInt($("#P_"+id).text().substring(1));
        //get quantity
        $("#V_"+id).val($("#V_"+id).val()-1);
        var pieces = parseInt($("#V_"+id).val());
        //edit total for pieces 
        $("#T_"+id).text("$"+( pieces*price) );
        
        //edit total and sub total price
        var total = parseInt($("#subTotal").text().substring(1));
        $("#subTotal").text("$"+(total - price));
        $("#total").text("$"+(total - price));
        
    }
    
    //recalculate price and total price of prodcut
    function reCalculateIncrease(id){
        //get price for peice 
        // #P_id is id div for price & substring to remove $ sign
        var price = parseInt($("#P_"+id).text().substring(1));
        //get quantity
        var pieces = parseInt($("#V_"+id).val()) + 1 ;
        $("#V_"+id).val(pieces);
        //edit total for pieces 
        $("#T_"+id).text("$"+( pieces*price) );
        
        //edit total and sub total price
        var total = parseInt($("#subTotal").text().substring(1));
        $("#subTotal").text("$"+(total + price));
        $("#total").text("$"+(total + price));
    }
    
});