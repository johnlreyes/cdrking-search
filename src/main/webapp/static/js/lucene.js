
var LUCENE = {

    load: function() {
        console.log('load');
         $('#results').hide();
    },
    ajax_index_all: function() {
        console.log('ajax_index_all');
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
        console.group('ajax_search_key');
        var key = $('#key').val();
        $.getJSON(
            "/json/search_key?key="+key,
            function(data) {
                console.group('getJSON');
                
                console.groupEnd();
            }
        )
        .error(function() {
                $('#status').text('Search Key failed. Try again.').slideDown('slow');
                 $('#results').hide();
            })
        .success(function(jsonArr) {
                console.group('success');
                $('#status').text('Search Key invoked!');
				console.groupEnd();
            })
        .complete(function() {
                console.group('complete');
                setTimeout(function() {
                        $('#status').slideUp('slow');
                    },
                    3000
                );
                $('#results').show();
                console.groupEnd();
            });
        console.groupEnd();
    },
   
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