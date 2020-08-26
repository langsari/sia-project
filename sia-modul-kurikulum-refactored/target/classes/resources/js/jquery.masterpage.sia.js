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

(function($){
    $.masterPage = function(el, options){
        // To avoid scope issues, use 'base' instead of 'this'
        // to reference this class from internal events and functions.
		var _addMode = true;
        var base = this;

        // Access to jQuery and DOM versions of element
        base.$el = $(el);
        base.el = el;

        // Add a reverse reference to the DOM object
        base.$el.data("masterPage", base);

        base.init = function(){
            base.options = $.extend({},$.masterPage.defaultOptions, options);
			base.options.appSelector = '#' + base.el.id;
			base.options.formDetail = base.options.dialogDetail + ' .formdetail';
			base.options.tableForm = base.options.appSelector + ' .tableform';

            // set add button
			if(base.options.showAddButton){
				$('.masteractions .btn-action', base.$el).prepend("<button type=\"button\" class=\"btn-success btn addButton " + base.options.addButtonClass + "\" style=\"margin-left: 10px;\">" + base.options.addButtonLabel + "</button>");
				$('.masteractions .btn-action .addButton', base.$el).click(base.showAddForm);
			}
			
			// set del button
			if(base.options.showDelButton){
				$('.masteractions .btn-action', base.$el).prepend("<button type=\"button\" class=\"btn-danger btn delButton " + base.options.delButtonClass + "\" style=\"margin-left: 10px;\">" + base.options.delButtonLabel + "</button>");
				$('.masteractions .btn-action .delButton', base.$el).click(base.deleteData);
			}

			//add save and cancel
			if((base.options.editOnClick || base.options.editOnClickRow || base.options.showSaveButton ) && base.options.showDefaultControl.save){
				$('.detailcontrol', base.options.formDetail).append("<button class=\"btn btn-success btnDetailSave green\" type=\"button\">" + base.options.labelDefaultControl.save + "</button>");
				$('.detailcontrol .btnDetailSave', base.options.formDetail).click(base.saveData);
			}
			if((base.options.editOnClick || base.options.editOnClickRow || base.options.showCancelButton) && base.options.showDefaultControl.cancel){
				$('.detailcontrol', base.options.formDetail).append("<button class=\"btn btnDetailCancel btn-primary\" type=\"button\" style=\"margin-left: 10px\">" + base.options.labelDefaultControl.cancel + "</button>");
				$('.detailcontrol .btnDetailCancel', base.options.formDetail).click(base.closeFormDetail);
			}
			//add other control
			if(base.options.editOnClick){
				var controlIndex;
				for(controlIndex in base.options.detailControls){
					var cControl = base.options.detailControls[controlIndex];
					if(typeof cControl.inner != 'undefined'){
						$('.detailcontrol', base.options.formDetail).append("<button class=\"btn btnDetail" + controlIndex + "\" type=\"button\" style=\"margin-right: 5px;\">" + cControl.label + "</button>");
						eval("$('.detailcontrol .btnDetail" + controlIndex + "', base.options.formDetail).click(" + cControl.inner + ");");
					}else{
						$('.detailcontrol', base.options.formDetail).append("<button class=\"btn btnDetail" + controlIndex + "\" type=\"button\" style=\"margin-right: 5px;\" onclick=\"" + cControl.onclick + "\">" + cControl.label + "</button>");
					}
				}
			}
			
			$(base.options.tableForm+' .checkbox-all').change(function(){
		        if ($(this).prop("checked") == true) {
					$(base.options.tableForm+' .checkbox-data').each(function(){
						$(this).prop("checked",true);
			            $(this).uniform();
					});
		        } else {
		        	$(base.options.tableForm+' .checkbox-data').each(function(){
						$(this).prop("checked",false);
			            $(this).uniform();
					});
		        }
		    });
						
			//init datatable
			var masterDT = $('.datatable', base.options.appSelector).dataTable({
								//"bStateSave": true,
								"bProcessing": true,
								"bServerSide": true,
								"sAjaxSource": base.options.dataUrl,
								"order": base.options.order,
								"sDom": "<'row'<'dataTables_header clearfix'<'col-md-3'<l>><'col-md-9'f<'pull-right'CT>>r>>t<'row-fluid'<'dataTables_footer clearfix'<'col-md-6'i><'col-md-6'p>>>",
								tableTools: {
									"sSwfPath": context_path+"resources/plugins/jquery.datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf"
								},
								"bRetrieve" :true,
								"bAutoWidth": false,
								"bSortClasses": false,
								"fnServerData": function ( sSource, aoData, fnCallback ) {
									
									if(typeof base.options.filters != 'undefined')
									{
										for(var i=0; i< base.options.filters.length;i++)
										{ aoData.push(base.pushAoData(base.options.filters[i].name, base.options.filters[i].id)); }
									}

									$.ajax( {
										"dataType": 'json',
										"type": "POST",
										"url": sSource,
										"data": aoData,
										"success": fnCallback,
										"error" : $.ajaxErrorHandler
									} );
									
								},
								"oColVis": {
									"buttonText": "Columns <i class='icon-angle-down'></i>",
									"iOverlayFade": 0,
									"aiExclude": base.options.columnExclude
								},
								"aoColumns": base.options.cols,
								"oTableTools": {
									"aButtons": [
										"copy",
										"print",
										"csv",
										"xls",
										"pdf"
									],
									"sSwfPath": context_path+"assets/assets/datatables/tabletools/swf/copy_csv_xls_pdf.swf"
								},
								// set the initial value
								//"sScrollX": "100%",
								//"sScrollXInner": "100%",
								"bScrollCollapse": true,
								fnDrawCallback: function () {
									if ($.fn.uniform) {
										var checkBox = $(base.options.tableForm+" tbody").find("input[type=checkbox]:not(.switchery), input[type=radio]:not(.no-uniform)");
									    if (checkBox.size() > 0) {
									        checkBox.each(function() {
									            $(this).uniform();
									        });
									    };
									}
									
									//var thead = $(base.options.appSelector+" .dataTables_scrollBody thead");
									//console.log(thead.hide());
									
									if($(base.options.tableForm+" .editrow")) {
										//fitur edit per row
										$(base.options.tableForm+' .editrow').click(function(){
												if(masterDT.fnGetData().length > 0 && $('#'+base.options.primaryKey+'s', this).length == 0)
												{
													var aData = masterDT.fnGetData( $(this).parent().parent()[0] );
													if(typeof base.options.callOnSelect != 'undefined')
														base.options.callOnSelect(aData, base.options);

													base.showEditForm(aData);
												}
										});
									}
									
									if($(base.options.tableForm+" .deleterow")) {
										//fitur delete per row
										$(base.options.tableForm+" .deleterow").click(function(){
												if(masterDT.fnGetData().length > 0 && $('#'+base.options.primaryKey+'s', this).length == 0)
												{
													var aData = masterDT.fnGetData( $(this).parent().parent()[0] );
													base.deleteDataRow(aData);
												}
										});
									}
									
									/*
									if ($.fn.select2) {
										$('.dataTables_length select').select2({
											minimumResultsForSearch: "-1"
										});
									}*/

									// SEARCH - Add the placeholder for Search and Turn this into in-line formcontrol
									var search_input = $(this).closest('.dataTables_wrapper').find('div[id$=_filter] input');

									// Only apply settings once
									if (search_input.parent().hasClass('input-group')) return;


									// Responsive 
									/*if (typeof responsiveHelper != 'undefined') {
										responsiveHelper.respond();
									}*/

									//$(base.options.tableForm+' .checkbox-data input').iCheck({checkboxClass:"icheckbox_flat",increaseArea:"20%"});
									//$(base.options.tableForm+' .radio-data input').iCheck({radioClass:"iradio_flat",increaseArea:"20%"});

								}
								
							});
			
			$('.datatable tbody', base.$el).on('click', 'td', function(){
					//console.log('hello');
				if($(this).find('.checkdata').length == 0) 
				{
					if(masterDT.fnGetData().length > 0 && $('#'+base.options.primaryKey+'s', this).length == 0)
					{
						var aData = masterDT.fnGetData( $(this).parent()[0] );
						if(typeof base.options.callOnSelect != 'undefined')
							base.options.callOnSelect(aData, base.options);

						if(base.options.editOnClick)
						{base.showEditForm(aData);}
					}
				}
			});
			
			//refresh button
			$('.dataTables_filter', base.$el).prepend('<button type="button" class="btn btn-sm dt-refresh-bt"><i class="icon-refresh"></i></button>');
			$('.dataTables_filter .dt-refresh-bt', base.$el).click(base.refresh);

			// FILTER
			for(var i=0; i< base.options.filters.length;i++)
			{ $(base.options.filters[i].id, base.options.appSelector).change(function(){ base.refresh(); }); }

			//init validation
			if(typeof base.options.validationRules != 'undefined'){
				base.options.formValidator = $.initInputValidation(base.options.formDetail, base.options.validationRules);
				if(typeof base.options.formValidator != 'undefined')
					base.options.activeRules = base.options.formValidator.settings.rules;
			}
			
			//filter kolom
			$('.filter_column input[type="checkbox"]', base.$el).change(function(){
				/* Get the DataTables object again - this is not a recreation, just a get of the object */
				var iCol = parseInt($(this).attr("data-column"));
				var bVis = masterDT.fnSettings().aoColumns[iCol].bVisible;
				masterDT.fnSetColumnVis(iCol, (bVis ? false : true));
			});
			
			
			return base;
        };

		base.pushAoData = function(name, id)
		{ return {'name' : name, 'value' : $(id).val()} }

        base.buildParameter = function(parameter){
			if(parameter.length == 0) return '';
			var temp = '';
			for(var i in parameter){
				temp += '&' + parameter[i].name + '=' + $(parameter[i].id).val();
			}
			return temp;
        };

		base.refresh = function(){
			$(base.$el).find('.dataTables_length select').change();
		}

		base.showAddForm = function(){
			base.changeDefaultButtonState(true);
			if(typeof base.options.callOnAdd != 'undefined')
				base.options.callOnAdd(base.options);
			$(base.options.dialogDetail).show();
			$(base.options.appSelector).hide();

			$.resetForm(base.options.formDetail, base.options.notFormInput);

			//set default value
			var defVal;
			for(defVal in base.options.defaultValue){
				$(base.options.defaultValue[defVal].id).val(base.options.defaultValue[defVal].val);
			}

			// callback after reset
			if(typeof base.options.callOnAddAfterReset != 'undefined')
				base.options.callOnAddAfterReset();
			$(base.options.detailFocusId).focus();
		}

		base.showEditForm = function(aData){
			base.changeDefaultButtonState(false);
			blockUI($(base.options.formDetail));
			if(typeof base.options.callOnEdit != 'undefined')
				base.options.callOnEdit(aData, base.options);
			eval("var tempData = {'"+base.options.primaryKey+"' : '"+aData[0]+"'};");
			$.ajax({
				url: base.options.detailUrl,
				data : tempData,
				type : 'post',
				success: function(data)
				{
					unblockUI($(base.options.formDetail));
					if(data.status=='ok')
					{
						$.resetForm(base.options.formDetail, base.options.notFormInput);
						$.fillToForm(base.options.formDetail, data.data);
						// show hide
						$(base.options.dialogDetail).show();
						$(base.options.appSelector).hide();
						$(base.options.detailFocusId).focus();
						if(typeof base.options.callOnFillForm != 'undefined')
							base.options.callOnFillForm(data, base.options);
					}
					else if(data.status=='expired')
					{ document.location=data.message; }
					else
					{ show_message('Error', data.message);}
				},
				error: $.ajaxErrorHandler,
				dataType : 'json'
			});
		}

		base.saveData = function(){
			if(typeof base.options.callOnSave != 'undefined')
				base.options.callOnSave();
			
			if(base.options.formValidator.form()){
				if(typeof base.options.overrideSave != 'undefined'){
					base.options.overrideSave(base, _addMode ? base.options.addUrl : base.options.editUrl, _addMode);
					return;
				}
				blockUI($(base.options.formDetail));
				$.ajax({
					url: _addMode ? base.options.addUrl : base.options.editUrl,
					data : $(base.options.formDetail).serialize() + base.buildParameter(base.options.additionalInput),
					type : 'post',
					success: function(data)
					{
						unblockUI($(base.options.formDetail));
						if(data.status=='ok')
						{
							show_message('Sukses', data.message);
							base.refresh();
							base.closeFormDetail();
						}
						else if(data.status=='expired')
						{ document.location=data.message; }
						else
						{ 
							show_message('Error', data.message,true);
							if(data.data!=null)
	                			for(i=0;i<data.data.length;i++)
                				{
                					$('#'+data.data[i].field).parent().addClass("has-error");
	                				if($('#'+data.data[i].field).parent().find('.error').length>0)
	                				{
	                					if(data.data[i].bindingFailure) $('#'+data.data[i].field).parent().find('.error').text("input tidak valid");
	                					else $('#'+data.data[i].field).parent().find('.error').text(data.data[i].defaultMessage);
	                				}
	                				else 
	                				{
	                					if(data.data[i].bindingFailure) $('#'+data.data[i].field).after('<label for="'+data.data[i].field+'" class="error text-danger">input tidak valid</label>');
	                					else $('#'+data.data[i].field).after('<label for="'+data.data[i].field+'" class="error text-danger">'+data.data[i].defaultMessage+'</label>');
	                				}
	                				$('#'+data.data[i].field).parent().find('.error').show();
                				}
						}

						if(_addMode && typeof base.options.callAfterSave != 'undefined') base.options.callAfterSave(_addMode, data);
					},
					error:$.ajaxErrorHandler,
					dataType : 'json'
				});
			}
		}

		base.deleteData = function(){
			if($('input[name="' + base.options.primaryKey + '[]"]:checked').length == 0){ alert(base.options.selectDataMsg); return false; }
			if(!confirm(base.options.deleteDataMsg)) return false;

			blockUI($(base.options.tableForm));
			$.ajax({
				url: base.options.deleteUrl,
				data : $(base.options.tableForm).serialize() + base.buildParameter(base.options.additionalInput),
				type : 'post',
				success: function(data)
				{
					unblockUI($(base.options.tableForm));
					if(data.status == 'ok')
					{
						base.refresh();
						show_message('Sukses', 'Data telah dihapus');
						if(typeof base.options.callOnDelete != 'undefined')
							base.options.callOnDelete();
					}
					else if(data.status=='expired')
					{ document.location=data.message; }
					else
					{ show_message('Error', data.message); }

				},
				error: $.ajaxErrorHandler,
				dataType : 'json'
			});
		}
		
		base.deleteDataRow = function(aData)
		{
			if(!confirm(base.options.deleteDataMsg)) return false;
			
			eval("var tempData = {'"+base.options.primaryKey+"[]' : '"+aData[0]+"'};");			
			blockUI($(base.options.tableForm));
				$.ajax({
				url: base.options.deleteUrl,
				data : tempData,
				type : 'post',
				success: function(data)
				{
					unblockUI($(base.options.tableForm));
					if(data.status == 'ok')
					{
						base.refresh();
						show_message('Sukses', 'Data telah dihapus');
						if(typeof base.options.callOnDelete != 'undefined')
							base.options.callOnDelete();
					}
					else if(data.status=='expired')
					{ document.location=data.message; }
					else
					{ show_message('Error', data.message); }

				},
				error: $.ajaxErrorHandler,
				dataType : 'json'
			});
		}

		base.closeFormDetail = function(){
			if(typeof base.options.formValidator != 'undefined'){
				base.options.formValidator.resetForm();
				//remove manual css class error
				$('.f_error', base.options.dialogDetail).removeClass('f_error');
			}
			$(base.options.dialogDetail).hide();
			$(base.options.appSelector).show();
		}

		base.changeDefaultButtonState = function(addMode){
			_addMode = addMode;
			$('.detailcontrol .btnDetailSave', base.options.formDetail).html(addMode ? base.options.labelDefaultControl.add : base.options.labelDefaultControl.save);
			if(addMode){
				$('.on_add', base.options.formDetail).show();
				$('.on_edit', base.options.formDetail).hide();
			}else{
				$('.on_add', base.options.formDetail).hide();
				$('.on_edit', base.options.formDetail).show();
			}
		}
		
        // Run initializer
        return base.init();
    };

    $.masterPage.defaultOptions = {
		appSelector: '',//set in init
		dialogDetail: '#master-detail',
		formDetail: '',//set in init
		tableForm: '',//set in init
		detailFocusId: undefined,
		primaryKey: undefined,
		dataUrl: undefined,
		detailUrl: undefined,
		addUrl: undefined,
		editUrl: undefined,
		deleteUrl: undefined,
		cols: [],
		deleteMsg: '',
		filters: [], //sample [ {id:'#id', name:'name'}, {}, {} ]
		showAddButton: true,
		showDelButton: true,
		editOnClick: true,
		editOnClickRow: false,
		detailControls: [], //sample => {inner: 'base.closeFormDetail', label: 'Batal'}
		showDefaultControl: {save: true, cancel: true},
		labelDefaultControl: {add: "Tambah", save: "Simpan", cancel: "Kembali"},
		notFormInput: [],
		additionalInput: [],
		defaultValue: [],
		validationRules: undefined, //validationRules: {pag_id: {digits: true},pag_uri: {required: true,maxlength: 50}} sample
		checkData:true,
		radioData:false,
		order:[[0,"asc"]],
		showCancelButton:false,
		showSaveButton:false,
		columnExclude:[0],

		//callback
		callOnAdd: undefined,
		callOnAddAfterReset: undefined,
		callOnEdit: undefined,
		callOnFillForm: undefined,
		callOnSelect: undefined,
		callOnSave: undefined,
		callOnDelete: undefined,
		callAfterSave: undefined,
		overrideSave: undefined,

		//static default
		addButtonClass: 'btn pull-right',
		addButtonLabel: "Tambah",
		delButtonClass: 'btn red pull-right',
		delButtonLabel: "Hapus",
		selectDataMsg: "Tidak ada data terpilih",
		deleteDataMsg: "Hapus data?",

		//validator object move here
		formValidator: undefined,
		activeRules: undefined,
    };

    $.fn.masterPage = function(options){
        return this.each(function(){
            (new $.masterPage(this, options));
        });
    };

})(jQuery);
