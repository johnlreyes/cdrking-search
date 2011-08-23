
var SYNCHRONIZE = {

    load: function() {
        console.log('load');
         $('#results').hide();
    },
    display: function(image_url) {
        console.log('display');
    },
    ajax_synchronize: function() {
        console.log('ajax_synchronize');
        $.ajax({
            type: "GET",
            url: "/json/synchronize",
            error: function() {
                $('#status').text('Synchronize failed. Try again.').slideDown('slow');
            },
            success: function() {
                $('#status').text('Synchronize invoked!');
            },
            complete: function() {
                setTimeout(function() {
                $('#status').slideUp('slow');
                }, 3000);

            }
        });
    },
    ajax_history: function() {
        console.group('ajax_history');
        $.getJSON(
            "/json/synchronize_history",
            function(data) {
                console.group('getJSON');
                $("#history_table_body").children().remove();
                $.each(data, function(index, array) {
                    var tr_content = '<tr>';
                    tr_content += '<td>'+array['id']+'</td>';
                    tr_content += '<td class="date_width">'+new Date(array['startDate']).toLocaleString()+'</td>';
                    tr_content += '<td class="date_width">'+new Date(array['endDate']).toLocaleString()+'</td>';
                    tr_content += '<td>'+array['startSid']+'</td>';
                    tr_content += '<td>'+array['endSid']+'</td>';
                    tr_content += '<td>'+(parseInt(array['endSid'])-parseInt(array['startSid'])+1)+'</td>';
                    tr_content += '</tr>';
                    $(tr_content).appendTo('#history_table_body');
                });
                console.groupEnd();
            }
        )
        .error(function() {
                $('#status').text('History failed. Try again.').slideDown('slow');
                 $('#results').hide();
            })
        .success(function(jsonArr) {
                console.group('success');
                $('#status').text('History invoked!');

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
    ajax_stop_ongoing: function() {
        $.getJSON(
            "/json/synchronize_stop_ongoing",
            function(data) {
               console.log('data='+data);
            }
        );
    }
}



$(document).ready(function(){
    SYNCHRONIZE.load();

    $('#synchronize').click(function(){
        SYNCHRONIZE.ajax_synchronize();
    });
    $('#stop_ongoing').click(function() {
        SYNCHRONIZE.ajax_stop_ongoing();
    });
    $('#history').click(function(){
        SYNCHRONIZE.ajax_history();
    });
});