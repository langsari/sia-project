var getperwalian;
var lepaskan;
var tambahkan;
$(document).ready(function(){
	$('#masterpage').masterPage(
	{
		detailFocusId: '#idPtk',
		dataUrl: context_path+'perwalian/jsonperwalian',
		primaryKey: 'idPtk',
        order: [[0,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showCancelButton: true,
		showAddButton: false,
		showDelButton: false,
		columnExclude:[],
		cols: [
			/* nipPtk */
			{ "bVisible":    true },
			/* nmPtk */
			{ "bVisible":    true },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = '<button type="button" class="btn btn-primary" onClick="getperwalian(\''+data+'\',\''+full[1]+'\')">Kelola Perwalian</button>';
					return action;
				}
			}
		],
		callOnSelect:function(aData, options){
			console.log(aData);
		}
	});
	
	$('#mahasiswa').masterPage(
	{
		detailFocusId: '#idPd',
		dataUrl: context_path+'perwalian/jsonanakwali',
		primaryKey: 'idPd',
        order: [[0,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		showDelButton: false,
		cols: [
			/* idPd */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idPd[]" value="'+data+'"/>';
				}
			},
			/* nimPd */
			{ "bVisible":    true },
			/* nmPd */
			{ "bVisible":    true },
			/* angkatanPd */
			{ "bVisible":    true },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = '<button type="button" class="btn btn-danger" onClick="lepaskan(\''+data+'\')">Lepaskan</button>';
					return action;
				}
			}
		],
		callOnSelect:function(aData, options){
			console.log(aData);
		},
		filters:[{id:'#idPtk', name:'idPtk'},{id:'#wali-filter',name:'angkatan_pd'}]
	});
	
	$('#mahasiswa-nonwali').masterPage(
	{
		detailFocusId: '#idPd',
		dataUrl: context_path+'perwalian/jsonnonwali',
		primaryKey: 'idPd',
        order: [[0,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		showDelButton: false,
		columnExclude:[],
		cols: [
			/* idPd */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idPd[]" value="'+data+'"/>';
				}
			},
			/* nimPd */
			{ "bVisible":    true },
			/* nmPd */
			{ "bVisible":    true },
			/* angkatanPd */
			{ "bVisible":    true },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = '<button type="button" class="btn btn-primary" onClick="tambahkan(\''+data+'\')">Tambahkan</button>';
					return action;
				}
			}
		],
		callOnSelect:function(aData, options){
			console.log(aData);
		},
		filters:[{id:'#non-wali-filter',name:'angkatan_pd'}]
	});
	
	getperwalian = function(id,nama){
		$("#idPtk").val(id).trigger("change");
		$("#form-title").text("Perwalian "+nama);
		$("#non-wali-filter").val('').change();
		$("#wali-filter").val('').change();
		$('#master-detail').show();
		$('#masterpage').hide();
		$('.wali-close').hide();
		$('.wali-add').show();
		$('#mahasiswa').removeClass("col-md-6");
		$('#mahasiswa-nonwali').hide();
		$('#mahasiswa-nonwali .checkbox-all').prop("checked",false);
		$('#mahasiswa-nonwali .checkbox-all').uniform();
		$('#mahasiswa-nonwali .checkbox-data').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#mahasiswa .checkbox-data').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#mahasiswa .checkbox-all').prop("checked",false);
		$('#mahasiswa .checkbox-all').uniform();
	}
	
	lepaskan = function(id)
	{
		blockUI($("#mahasiswa"));
		eval("var tempData = {'idPd[]' : '"+id+"','idPtk' : '"+$("#idPtk").val()+"'};");
		$.ajax({
			url: context_path+"perwalian/lepaskan",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#mahasiswa"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#mahasiswa").find('.dataTables_length select').change();
					$("#mahasiswa-nonwali").find('.dataTables_length select').change();
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
	
	tambahkan = function(id){
		blockUI($("#mahasiswa-nonwali"));
		eval("var tempData = {'idPd[]' : '"+id+"','idPtk' : '"+$("#idPtk").val()+"'};");
		$.ajax({
			url: context_path+"perwalian/tambahkan",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#mahasiswa-nonwali"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#mahasiswa").find('.dataTables_length select').change();
					$("#mahasiswa-nonwali").find('.dataTables_length select').change();
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
	
	$('.wali-add').click(function(){
		$('.wali-close').show();
		$('.wali-add').hide();
		$('#mahasiswa').addClass("col-md-6");
		$('#mahasiswa-nonwali').show();
		$("#mahasiswa-nonwali").find('.dataTables_length select').change();
	});
	
	$('.wali-close').click(function(){
		$('.wali-close').hide();
		$('.wali-add').show();
		$('#mahasiswa').removeClass("col-md-6");
		$('#mahasiswa-nonwali').hide();
		$('#mahasiswa-nonwali .checkbox-data').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#mahasiswa-nonwali .checkbox-all').prop("checked",false);
		$('#mahasiswa-nonwali .checkbox-all').uniform();
	});
	
	$('.lepaskan-all').click(function(){
		blockUI($("#mahasiswa"));
		var formdata = $("#mahasiswa .tableform").serialize()+"&idPtk="+$("#idPtk").val()+"";
		$.ajax({
			url: context_path+"perwalian/lepaskan",
			data : formdata,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#mahasiswa"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#mahasiswa").find('.dataTables_length select').change();
					$("#mahasiswa-nonwali").find('.dataTables_length select').change();
				}
				else if(data.status=='expired')
				{ document.location=data.message; }
				else
				{ show_message('Error', data.message);}
			},
			error: $.ajaxErrorHandler,
			dataType : 'json'
		});
	});
	
	$('.tambahkan-all').click(function(){
		blockUI($("#mahasiswa-nonwali"));
		var formdata = $("#mahasiswa-nonwali .tableform").serialize()+"&idPtk="+$("#idPtk").val()+"";
		$.ajax({
			url: context_path+"perwalian/tambahkan",
			data : formdata,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#mahasiswa-nonwali"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#mahasiswa").find('.dataTables_length select').change();
					$("#mahasiswa-nonwali").find('.dataTables_length select').change();
				}
				else if(data.status=='expired')
				{ document.location=data.message; }
				else
				{ show_message('Error', data.message);}
			},
			error: $.ajaxErrorHandler,
			dataType : 'json'
		});
	});
});