(function ($) {
    "use strict";
    $("#add").click(function() {
                $("#popup").show();
    });

     $("#close, #cancel").click(function() {
        $("#popup").hide();
     });
})(jQuery);