var PRODUCTS = {
    load: function() {
        $('#results').hide();
    },
    ajax_get_all: function() {
        console.group('ajax_get_all');
        $.getJSON(
            "/json/products_all",
            function(data) {
                console.group('getJSON');
                $("#products_table_body").children().remove();
                $.each(data, function(index, array) {
                    console.info(index+"->"+array);
                    var tr_content = '<tr>';
                    tr_content += '<td>'+array['sid']+'</td>';
                    tr_content += '<td>'+array['code']+'</td>';
                    tr_content += '<td>'+array['name']+'</td>';
                    tr_content += '<td>'+array['price']+'</td>';
                    tr_content += '<td>'+array['status']+'</td>';
                    tr_content += '</tr>';
                    $(tr_content).appendTo('#products_table_body');
                });
                console.groupEnd();
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
                console.group('success');
                $('#status').text('Products invoked!').slideDown('slow');
                console.groupEnd();
            }
        )
        .complete(
            function() {
                console.group('complete');
                setTimeout(function() {
                        $('#status').slideUp('slow');
                    },
                    3000
                );
                $('#results').show();
                console.groupEnd();
            }
        );
        console.groupEnd();
    },

}

$(document).ready(function(){
    PRODUCTS.load();
    $('#display_all').click(function(){
        PRODUCTS.ajax_get_all();
    });
});