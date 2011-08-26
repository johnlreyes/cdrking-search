
var LUCENE = {

    load: function() {
        $('#results').hide();
    },
    
    ajax_index_all: function() {
        $.ajax({
            type: "GET",
            url: "/json/index_all",
            error: function() {
                $('#status').text('Index All failed. Try again.').slideDown('slow');
            },
            success: function() {
                $('#status').text('Index All invoked!');
            },
            complete: function() {
                setTimeout(function() {
                $('#status').slideUp('slow');
                }, 3000);

            }
        });
    },
    
    ajax_search_key: function() {
        var key = $('#key').val();
        $.getJSON(
            "/json/search_key?key="+key,
            function(data) {
            	$("#products_summary_table_body").children().remove();
                $.each(data, function(index, array) {
                    var tr_content = '<tr>';
                    tr_content += '<td>'+array['sid']+'</td>';
                    tr_content += '<td>'+array['code']+'</td>';
                    tr_content += '<td>'+array['name']+'</td>';
                    tr_content += '<td>'+array['price']+'</td>';
                    tr_content += '<td>'+array['status']+'</td>';
                    tr_content += '</tr>';
                    $(tr_content).appendTo('#products_summary_table_body');
                });
            }
        )
        .error(function() {
                $('#status').text('Search Key failed. Try again.').slideDown('slow');
                $('#results').hide();
            })
        .success(function(jsonArr) {
                $('#status').text('Search Key invoked!');
			})
        .complete(function() {
                setTimeout(function() {
                        $('#status').slideUp('slow');
                    },
                    3000
                );
                $('#results').show();
            });
    }
}

$(document).ready(function(){
    LUCENE.load();

    $('#index_all').click(function(){
        LUCENE.ajax_index_all();
    });
    $('#search_key').click(function(){
        LUCENE.ajax_search_key();
    });
});