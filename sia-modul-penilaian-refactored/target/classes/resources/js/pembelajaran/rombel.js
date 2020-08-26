var tambahkan;
$(document).ready(function(){
	$('#masterpage').masterPage(
	{
		detailFocusId: '#idRombel',
		dataUrl: context_path+'rombel/json',
		detailUrl: context_path+'rombel/edit',
		addUrl: context_path+'rombel/simpan',
		editUrl: context_path+'rombel/simpan',
		deleteUrl: context_path+'rombel/deletemany',
		primaryKey: 'idRombel',
        order: [[2,"desc"]],
		editOnClick: false,
		editOnClickRow: true,
		cols: [
			/* idRombel */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idRombel[]" value="'+data+'">';
				}
			},
			/* nmRombel */
			{ "bVisible":    true },
			/* tglBuatRombel */
			{ "bVisible":    true },
			/* aRombelTerhapus */
			{ "bVisible":    false },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = '<button type="button" class="btn btn-success" onClick="getanggotarombel(\''+full[0]+'\',\''+full[1]+'\')">Isi Rombongan Belajar</button> <button type="button" class="btn btn-primary editrow">Edit</button>';
					if(full[3]=='false') action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
					return action;
				}
			}
		],
		validationRules: {idRombel:{required: false},nmRombel:{required: true}},
		filters: [{id:'#filter', name:'a_rombel_terhapus'}],
		callOnFillForm: function(data, options){
			$('input').iCheck("update");
		},
		callOnAddAfterReset:function(options){
			$('input').iCheck("update");
		}
	});
	
	$('#pesertadidik').masterPage(
	{
		detailFocusId: '#idPd',
		dataUrl: context_path+'rombel/jsonpesertadidik',
		primaryKey: 'idPd',
        order: [[1,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		showDelButton: false,
		columnExclude:[0],
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
		filters:[{id:'#pesertadidik-filter',name:'angkatan_pd'}]
	});
	
	$('#anggota-rombel').masterPage(
	{
		detailFocusId: '#idAnggotaRombel',
		dataUrl: context_path+'rombel/jsonanggotarombel',
		deleteUrl: context_path+'rombel/deleteanggotarombel',
		primaryKey: 'idAnggotaRombel',
        order: [[1,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		showDelButton: true,
		columnExclude:[0],
		cols: [
			/* nimPd */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idAnggotaRombel[]" value="'+data+'"/>';
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
					var action = ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
					return action;
				}
			}
		],
		callOnSelect:function(aData, options){
			console.log(aData);
		},
		filters:[{id:'#idRombel-isi', name:'idRombel'},{id:'#anggota-rombel-filter',name:'angkatan_pd'}]
	});
	
	getanggotarombel = function(id,nama){
		$("#idRombel-isi").val(id).trigger("change");
		$("#form-title").text("Rombongan Belajar "+nama);
		$("#anggota-rombel-filter").val('').change();
		$("#pesertadidik-filter").val('').change();
		$('#isi-rombel').show();
		$('#masterpage').hide();
	}
	
	kembali = function()
	{
		$('.anggota-close').hide();
		$('.anggota-add').show();
		$('#anggota-rombel').removeClass("col-md-6");
		$('#pesertadidik').hide();
		$('#pesertadidik .checkbox-data').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#pesertadidik .checkbox-all').prop("checked",false);
		$('#pesertadidik .checkbox-all').uniform();
		$('#anggota-rombel .checkbox-all').prop("checked",false);
		$('#anggota-rombel .checkbox-all').uniform();
		$('#anggota-rombel .checkbox-data').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#isi-rombel').hide();
		$('#masterpage').show();
	}
	
	tambahkan = function(id){
		blockUI($("#pesertadidik"));
		eval("var tempData = {'idPd' : '"+id+"','idRombel' : '"+$("#idRombel-isi").val()+"'};");
		$.ajax({
			url: context_path+"rombel/tambahkan",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#pesertadidik"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#anggota-rombel").find('.dataTables_length select').change();
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
	
	$('.tambahkan-all').click(function(){
		if($('input[name="idPd[]"]:checked').length == 0){ alert("Tidak ada data terpilih"); return false; }
		if(!confirm("Tambahkan semua peserta didik terpilih?")) return false;

		blockUI($("#pesertadidik"));
		var formdata = $("#pesertadidik .tableform").serialize();
		$.ajax({
			url: context_path+"rombel/tambahkanmany",
			data : formdata,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#pesertadidik"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#anggota-rombel").find('.dataTables_length select').change();
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
	
	$('.anggota-add').click(function(){
		$('.anggota-close').show();
		$('.anggota-add').hide();
		$('#anggota-rombel').addClass("col-md-6");
		$('#pesertadidik').show();
	});
	
	$('.anggota-close').click(function(){
		$('.anggota-close').hide();
		$('.anggota-add').show();
		$('#anggota-rombel').removeClass("col-md-6");
		$('#pesertadidik').hide();
		$('#pesertadidik .checkbox-all').prop("checked",false);
		$('#pesertadidik .checkbox-all').uniform();
		$('#pesertadidik .checkbox-data').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
	});
});