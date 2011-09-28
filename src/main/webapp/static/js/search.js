
var SEARCH = {

    load: function() {
        $('#results').hide();
    },
    
    ajax_index_all: function() {
        $.ajax({
            type: "GET",
            url: "/index/all",
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
            "/search/byKey?key="+key,
            function(data) {
            	$("#products_summary_table_body").children().remove();
                $.each(data, function(index, array) {
                    var tr_content = '<tr>';
                    tr_content += '<td>'+array['sid']+'</td>';
                    tr_content += '<td>'+array['code']+'</td>';
                    tr_content += '<td>'+array['name']+'</td>';
                    tr_content += '<td>'+array['category']+'</td>';
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
                //$('#status').text('Search Key invoked!');
			})
        .complete(function() {
                setTimeout(function() {
                        //$('#status').slideUp('slow');
                    },
                    3000
                );
                $('#results').show();
            });
    }
}

$(document).ready(function(){
    SEARCH.load();

    $('#index_all').click(function(){
        SEARCH.ajax_index_all();
    });
    $('#key').keyup(function() {
	  	SEARCH.ajax_search_key();
	});
    $('#search_key').click(function(){
        SEARCH.ajax_search_key();
    });
});