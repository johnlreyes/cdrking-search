var PRODUCTS = {
    load: function() {
        $('#results').hide();
    },
    
    display_all_product: function() {
    	var batch_limit = 20;
    	var sid = 14;
    	    	
    	do {
    		var requestArray = new Array();
    		var index = 0;
    		var product_list = null;
    		
	    	while (index < batch_limit) {
		    	var product = store.get(sid);
		    	if (product != null) {
		    		requestArray[index] = { sid: product.sid, updated: product.updated };
		    	} else {
		    		requestArray[index] = { sid: sid };
		    	}
		    	index++;
		    	sid++;
	    	}
	    	product_list = PRODUCTS.ajax_get_updated_list(requestArray);
	    	retry = 0;
	    	while (product_list == null && retry < 3) {
	    		product_list = PRODUCTS.ajax_get_updated_list(requestArray);
	    		retry ++;
	    	}
	    	if (product_list != null) {
	    		for (x in product_list) {
					store.set(x.sid, x);
				}
	    	}
    	} while (product_list != null);
    },
    
    ajax_get_updated_list: function(requestArray) {  
    	var returnValue = null;  
    	$.getJSON(
            "/json/controller?action=product_list",
            requestArray
        )
        .success(
            function(data) {
                returnValue = data;
            }
        );
        return returnValue;
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
        PRODUCTS.display_all_product();
    });
});