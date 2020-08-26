function show_message(title, message, is_error){
	if(is_error == true) toastr.error(title, message);
	else toastr.success(title, message);
}


function blockUI(el, centerY) {
	var el = jQuery(el); 
	el.block({
			message: '<img src="' + context_path + 'resources/images/loading.gif" align="">',
			centerY: centerY != undefined ? centerY : true,
			css: {
				top: '10%',
				border: 'none',
				padding: '2px',
				backgroundColor: 'none'
			},
			overlayCSS: {
				backgroundColor: '#000',
				opacity: 0.05,
				cursor: 'wait'
			}
		});
}

function unblockUI (el) {
	jQuery(el).unblock({
			onUnblock: function () {
				jQuery(el).removeAttr("style");
			}
		});
}

$(document).ready(function(){
	var ldgSelector = null;
	/*
	---------------------------------------------------------------------------
	| FUNCTIONS Reset Form
	---------------------------------------------------------------------------
	| Reset Form
	| @author : Doni Setio Pambudi
	*/
	$.resetForm = function(form, notFormInput, notSelector)
	{
		if(typeof notFormInput != 'undefined')
		{ for(i = 0; i < notFormInput.length; i++){ $('#' + notFormInput[i] ).html(''); } }

		$('.validation_error').remove();
		$('.form-group',form).each(function(){
			$(this).removeClass("has-error");
		});
		$('input:not([type=submit], [type=button], [type=reset], [type=radio], [type=checkbox]), textarea', form).each(function(){
			if(typeof notSelector != 'undefined')
				if($(this).is(notSelector)) return;
			if(typeof $(this).attr('no_reset') != 'undefined') return;
			$(this).val('');
		});
		$('select', form).each(function(){
			if(typeof notSelector != 'undefined')
				if( $(this).is(notSelector)) return;
			if(typeof $(this).attr('no_reset') != 'undefined') return;
			$(this).val($('option:nth-child(0)', this).val());
		});
		$('input:checkbox, input:radio', form).each(function(){
			if(typeof notSelector != 'undefined')
				if( $(this).is(notSelector)) return;
			if(typeof $(this).attr('no_reset') != 'undefined') return;
			$(this).prop('checked', false);
		});
	}

	/*
	---------------------------------------------------------------------------
	| FUNCTION TO HANDLE ERROR ON AJAX
	---------------------------------------------------------------------------
	| Handle error on ajax
	| @author : Doni Setio Pambudi
	*/
	$.ajaxErrorHandler = function(jqXHR, textStatus, errorThrown)
	{
		if(ldgSelector != null) App.unblockUI(ldgSelector);
		if(textStatus == 'timeout' || textStatus == 'parsererror')
		{
			if(jqXHR.responseText.length > 7 && jqXHR.responseText.substring(0, 7) == 'http://'){ document.location = jqXHR.responseText; }
			else{ show_message('Error', 'Terjadi Kesalahan Pada Jaringan', true, true);  }
		}
		else
		{
			show_message('Error', jqXHR.responseText, true, true);
		}
	}

	/*
	---------------------------------------------------------------------------
	| FUNCTION FILL JSON DATA TO FORM
	---------------------------------------------------------------------------
	| extract json data and fill to form
	| @author : Doni Setio Pambudi
	*/
	$.fillToForm = function(form, formdata)
	{
		for(id in formdata)
		{
			//var a = x;
			currentElement = $('#' + id, form);
			if(currentElement.length == 0){ currentElement = $('[name="' + id +'"]', form); }
			if(currentElement.length > 0){
				tag = currentElement.get(0).tagName;
				type = currentElement.get(0).type;
				if(tag == 'INPUT' && (type == 'file')){ //console.log('file');
				}
				else if(tag == 'INPUT' && (type == 'radio')){ $('[name="' + id + '"][value="' + formdata[id] + '"]', form).prop('checked', true);}
				else if(tag == 'INPUT' && (type == 'checkbox')){
					if(formdata[id] == true){
						 currentElement.prop('checked', true);
					}else{ currentElement.prop('checked', false); }
				}
				else if(tag == 'INPUT' || tag == 'SELECT' || tag == 'TEXTAREA'){ currentElement.val((""+formdata[id]+"")); }
				else { currentElement.html(formdata[id]); }
			}
		}
	}
	
	$.initInputValidation = function(selector, rules, showMessage){
		if(typeof showMessage == 'undefined')
			showMessage = true;
		var vResult = $(selector).validate({
						onkeyup: false,
						onfocusout: false,
						errorClass: 'error text-danger',
						validClass: 'valid',
						highlight: function(element) {
							$(element).closest('div').addClass("has-error");
						},
						unhighlight: function(element) {
							$(element).closest('div').removeClass("has-error");
						},
						errorPlacement: function(error, element) {
							$(element).closest('div').append(error);
						},
						rules: rules,
						invalidHandler: function(form, validator) {
							if(showMessage)
								show_message('Error Input', 'Salah satu input yang Anda masukkan salah',true);
						}
					});
		return vResult;
	}
});