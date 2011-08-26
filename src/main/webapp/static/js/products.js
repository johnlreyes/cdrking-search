var PRODUCTS = {
    load: function() {
        $('#results').hide();
    },
    ajax_get_all: function() {
        $.getJSON(
            "/json/products_all",
            function(data) {
                $("#products_table_body").children().remove();
                $.each(data, function(index, array) {
                    var tr_content = '<tr>';
                    tr_content += '<td>'+array['sid']+'</td>';
                    tr_content += '<td>'+array['code']+'</td>';
                    tr_content += '<td>'+array['name']+'</td>';
                    tr_content += '<td>'+array['category']+'</td>';
                    tr_content += '<td>'+array['price']+'</td>';
                    tr_content += '<td>'+array['status']+'</td>';
                    tr_content += '</tr>';
                    $(tr_content).appendTo('#products_table_body');
                });
            }
        )
        .error(
            function() {
                $('#status').text('Products failed. Try again.').slideDown('slow');
                $('#results').hide();
            }
        )
        .success(
            function(jsonArr) {
                $('#status').text('Products invoked!').slideDown('slow');
            }
        )
        .complete(
            function() {
                setTimeout(function() {
                        $('#status').slideUp('slow');
                    },
                    3000
                );
                $('#results').show();
            }
        );
    },

}

$(document).ready(function(){
    PRODUCTS.load();
    $('#display_all').click(function(){
        PRODUCTS.ajax_get_all();
    });
});