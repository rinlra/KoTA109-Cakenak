/*scroll to top*/

$(document).ready(function () {
    $(function () {
        $.scrollUp({
            scrollName: 'scrollUp', // Element ID
            scrollDistance: 300, // Distance from top/bottom before showing element (px)
            scrollFrom: 'top', // 'top' or 'bottom'
            scrollSpeed: 300, // Speed back to top (ms)
            easingType: 'linear', // Scroll to top easing (see http://easings.net/)
            animation: 'fade', // Fade, slide, none
            animationSpeed: 200, // Animation in speed (ms)
            scrollTrigger: false, // Set a custom triggering element. Can be an HTML string or jQuery object
            //scrollTarget: false, // Set a custom target element for scrolling to the top
            scrollText: '<i class="fa fa-angle-up"></i>', // Text for element, can contain HTML
            scrollTitle: false, // Set a custom <a> title if required.
            scrollImg: false, // Set true to use image
            activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
            zIndex: 2147483647 // Z-Index for the overlay
        });
    });

    //to read photo from client 
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#imageView').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    //add listner
    $("#image").change(function () {
        readURL(this);
    });
    
   //change active class
    activeClass();
    
});

/*price range*/

 $('#sl2').slider();

	var RGBChange = function() {
	  $('#RGB').css('background', 'rgb('+r.getValue()+','+g.getValue()+','+b.getValue()+')')
};

 //change class active 
    function activeClass(){
        var link = window.location.href;
        if(link.includes("Shop")){
            $("#shopBtn").addClass("active");
            $("#homeBtn").removeClass("active");
        }else if(link.includes("index.jsp") || link.endsWith("cakenak/")){
            $("#homeBtn").addClass("active");
            $("#shopBtn").removeClass("active");
        }else{
            $("#homeBtn").removeClass("active");
            $("#shopBtn").removeClass("active");
        }
    }


flatpickr("#inputtgl1", {

	minDate: "01.01.1900",
    maxDate: "01.01.2100",
    allowInput: false,
    altInput: true,
	altFormat: 'd-m-Y',
	dateFormat: "d-m-Y",
	onChange: function(selectedDates, dateStr, fp) {
		
        flatpickr("#inputtgl2", {

			dateFormat: "d-m-Y",
			allowInput: false,
    		altInput: true,
			altFormat: 'd-m-Y',
			minDate: flatpickr.parseDate(dateStr, "d-m-Y").fp_incr(8),
			
		});
    },
	onValueUpdate: function(dates, dateStr, fp) {
		document.getElementById('inputtgl2').value = '';
		flatpickr("#inputtgl2", {

			dateFormat: "d-m-Y",
			allowInput: false,
    		altInput: true,
			altFormat: 'd-m-Y',
			minDate: flatpickr.parseDate(dateStr, "d-m-Y").fp_incr(8),
			
		});
	},
});

function getFile() {
  document.getElementById("upfile").click();
}

function sub(obj) {
  var file = obj.value;
  var fileName = file.split("\\");
  document.getElementById("yourBtn").innerHTML = fileName[fileName.length - 1];
  document.myForm.submit();
  event.preventDefault();
}

$('.dropdown-toggle').dropdown()


function addInput(){
    var newdiv = document.createElement('div');
	//newdiv.id = dynamicInput[counter];
    newdiv.innerHTML = "<div class='row'>\
				  <div class='col'>\
		          <label for='newcf'>Column Family :</label>\
				  <input class='form-control' type='text' id='newcf' name='newcf' value='NewColumnFamily' />\
				  <label for='newc'>Column Qualifier :</label>\
				  <input class='form-control' type='text' id='newc' name='newc' />\
				  </div>\
				</div>\
				<div class='row'>\
				  <div class='col'>\
				  <label for='newv'>Value :</label>\
				  <input class='form-control' type='text' id='newv' name='newv' />\
				  </div>\
				</div> \
     <input type='button' value='-' onClick='removeInput(this);'>";
    $('.form-group').find('.test').last().prev().after(newdiv);
}
  
      function removeInput(btn){
          btn.parentNode.remove();
      }