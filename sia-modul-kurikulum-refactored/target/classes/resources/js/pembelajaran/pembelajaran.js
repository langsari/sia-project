var showModal;
var tambahkan;
var satMan;
$(document).ready(function(){
	$('#masterpage').masterPage(
	{
		detailFocusId: '#idPemb',
		dataUrl: context_path+'pembelajaran/json',
		detailUrl: context_path+'pembelajaran/edit',
		addUrl: context_path+'pembelajaran/simpan',
		editUrl: context_path+'pembelajaran/simpan',
		deleteUrl: context_path+'pembelajaran/deletemany',
		primaryKey: 'idPemb',
        order: [[1,"asc"]],
		editOnClick: false,
		editOnClickRow: true,
		cols: [
			/* idPemb */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idPemb[]" value="'+data+'"/>';
				}
			},
			/* namaMK */
			{ "bVisible":    true },
			/* nmPemb */
			{ "bVisible":    true },
			/* thnAjaran */
			{ "bVisible":    true },
			/* smt */
			{ "bVisible":    true },
			/* kuota */
			{ "bVisible":    true },
			/* temuDalamSeminggu */
			{ "bVisible":    false },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = '<button type="button" class="btn btn-success" onClick="getpendidik(\''+full[0]+'\',\''+full[1]+' '+full[2]+'\')">Tambahkan pendidik</button> ';
					action += '<button type="button" class="btn btn-success" onClick="getPesertaDidik(\''+full[0]+'\',\''+full[1]+' '+full[2]+'\')">Peserta didik</button> ';
					action +=' <button type="button" class="btn btn-primary editrow">Edit</button>';
					if(data == "true") action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
					return action;
				}
			}
		],
		validationRules: {idPemb:{required: false},namaMK:{required: true},idMK:{required: true},nmPemb:{required: true},temuDalamSeminggu:{required: true}},
		filters: [{id:'#filter', name:'id_tgl_smt'}],
		callOnFillForm:function(data,options){
			$("#nmThnAjaran").val(namaThnAjaran);
			$("#idMK").val(data.data.mk.idMK);
			$("#namaMK").val(data.data.mk.namaMK);
			for(i=0;i<data.data.listSatManList.length;i++)
			{
				//$('.checkboxSatMan[value="'+data.data.listSatManList[i]+'"]').iCheck("check");
				$('.checkboxSatMan[value="'+data.data.listSatManList[i]+'"]').prop("checked",true);
				$('.checkboxSatMan[value="'+data.data.listSatManList[i]+'"]').uniform();
			}
			//$('.checkboxSatMan').iCheck("update");
		},
		callOnAddAfterReset:function(){
			$("#nmThnAjaran").val(namaThnAjaran);
			//$('.checkboxSatMan').iCheck("update");
		}
	});
	
	$('#masterpageMK').masterPage(
	{
		detailFocusId: '#idMK',
		dataUrl: context_path+'pembelajaran/jsonmatakuliah',
		detailUrl: context_path+'pembelajaran/editmatakuliah',
		primaryKey: 'idMK',
        order: [[1,"asc"]],
		editOnClick: false,
		dialogDetail: '',
		editOnClickRow: false,
		cols: [
			/* idPd */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<button type="button" class="btn btn-primary">Pilih</button>';
				}
			},
			/* Kode */
			{ "bVisible":    true },
			/* Kurikulum */
			{ "bVisible":    false },
			/* Satman */
			{ "bVisible":    false },
			/* rumpun mk */
			{ "bVisible":    true },
			/* nmMK */
			{ "bVisible":    true },
			/* semester */
			{ "bVisible":    true },
			/* deskripsi */
			{ "bVisible":    false },
			/* sifat */
			{ "bVisible":    false }
		],
		filters: [{id:'#filter-mk', name:'idKurikulum'}],
		callOnSelect:function(aData, options){
			$("#idMK").val(aData[0]);
			$("#namaMK").val(aData[5]);
			$('#myModal').modal('toggle');
		}
	});
	
	$('#masterPendidikPengajar').masterPage(
	{
		detailFocusId: '#idPendidikPengajar',
		dataUrl: context_path+'pembelajaran/jsonpendidikpengajar',
		deleteUrl: context_path+'pembelajaran/deleteanggotarombel',
		primaryKey: 'idPendidikPengajar',
        order: [[2,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		dialogDetail: '',
		cols: [
			/* idPendidikPengajar */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idPendidikPengajar[]" value="'+data+'"/>';
				}
			},
			/* nipPtk */
			{ "bVisible":    true },
			/* nmPtk */
			{ "bVisible":    true },
			/* aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = '';
					if(data == "false") action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
					return action;
				} 
			}
		],
		filters: [{id:'#idPemb-pendidik', name:'idPemb'}]
	});
	
	$('#masterPendidik').masterPage(
	{
		detailFocusId: '#idPtk',
		dataUrl: context_path+'pembelajaran/jsonpendidik',
		primaryKey: 'idPtk',
        order: [[2,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		showDelButton: false,
		dialogDetail: '',
		cols: [
			/* idPtk */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idPtk[]" value="'+data+'"/>';
				}
			},/* nipPtk */
			{ "bVisible":    true },
			/* nmPtk */
			{ "bVisible":    true },
			/* statusPtk */
			{ 
				"bVisible":    true,
				mRender: function(data,type,full){
					if(full[3]=='false') return "Tenaga Kependidikan";
					else return "Pendidik";
				}
			},
			/* aPtkTerhapus */
			{ "bVisible":    false },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = '<button type="button" class="btn btn-primary" onclick="tambahkan(\''+full[0]+'\')">Tambahkan</button>';
					return action;
				}
			}
		],
		filters: [{id:'#idPemb-pendidik', name:'idPemb'}]
	});
	
	showModal = function (){
		$('#myModal').modal('show');
	}
	
	showModalSatMan = function (){
		$('#satManModal').modal('show');
	}
	
	$('#myModal').on('shown.bs.modal', function (e) {
		$("#masterpageMK").find('.dataTables_length select').change();
		  //if (!data) return e.preventDefault() // stops modal from being shown
	})
	
	$('#satManModal').on('shown.bs.modal', function (e) {
		$("#masterpageSatMan").find('.dataTables_length select').change();
		  //if (!data) return e.preventDefault() // stops modal from being shown
	})
	

	getpendidik = function(id,nama){
		$("#idPemb-pendidik").val(id).trigger("change");
		$("#idPemb-peserta").val(id).trigger("change");
		$("#idPemb-rombel").val(id).trigger("change");
		$("#idPemb-pd").val(id).trigger("change");
		$("#title").text("Pengajar "+nama);
		$("#masterPendidikPengajar").find('.dataTables_length select').change();
		$('#master-pengajar').show();
		$('#masterpage').hide();
	}
	
	kembali = function()
	{
		$('.pendidik-close').hide();
		$('.pendidik-add').show();
		$('.peserta-close').hide();
		$('.rombel-add').show();
		$('#masterPendidikPengajar').removeClass("col-md-6");
		$('#masterPesertaPembelajaran').removeClass("col-md-6");
		$('#masterPendidik').hide();
		$('#masterRombel').hide();
		$('#masterPendidik .checkbox-data').prop("checked",false);
		$('#masterPendidik .checkbox-data').uniform();
		$('#masterPendidik .checkbox-all').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#masterPendidikPengajar .checkbox-data').prop("checked",false);
		$('#masterPendidikPengajar .checkbox-data').uniform();
		$('#masterPendidikPengajar .checkbox-all').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#masterPesertaPembelajaran .checkbox-data').prop("checked",false);
		$('#masterPesertaPembelajaran .checkbox-all').uniform();
		$('#masterPesertaPembelajaran .checkbox-all').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#masterRombel .checkbox-data').prop("checked",false);
		$('#masterRombel .checkbox-data').uniform();
		$('#masterRombel .checkbox-all').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#master-pengajar').hide();
		$('#master-pesertadidik').hide();
		$('#masterpage').show();
	}
	
	tambahkan = function(id){
		blockUI($("#masterPendidik"));
		eval("var tempData = {'idPtk' : '"+id+"','idPemb' : '"+$("#idPemb-pendidik").val()+"'};");
		$.ajax({
			url: context_path+"pembelajaran/tambahkanpendidik",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterPendidik"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#masterPendidikPengajar").find('.dataTables_length select').change();
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
		if($('input[name="idPtk[]"]:checked').length == 0){ alert("Tidak ada data terpilih"); return false; }
		if(!confirm("Tambahkan semua peserta didik terpilih?")) return false;

		blockUI($("#masterPendidik"));
		var formdata = $("#masterPendidik .tableform").serialize();
		$.ajax({
			url: context_path+"pembelajaran/tambahkanmany",
			data : formdata,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterPendidik"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#masterPendidikPengajar").find('.dataTables_length select').change();
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
	
	$('.pendidik-add').click(function(){
		$('.pendidik-close').show();
		$('.pendidik-add').hide();
		$('#masterPendidikPengajar').addClass("col-md-6");
		$("#masterPendidik").find('.dataTables_length select').change();
		$("#masterPendidikPengajar").find('.dataTables_length select').change();
		$('#masterPendidik').show();
	});
	
	$('.pendidik-close').click(function(){
		$('.pendidik-close').hide();
		$('.pendidik-add').show();
		$('#masterPendidikPengajar').removeClass("col-md-6");
		$("#masterPendidikPengajar").find('.dataTables_length select').change();
		$('#masterPendidik').hide();
		$('#masterPendidik .checkbox-data').prop("checked",false);
		$('#masterPendidik .checkbox-data').uniform();
		$('#masterPendidik .checkbox-all').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
	});
	
	/*==============================================================Peserta didik==================================================*/
	$('#masterPesertaPembelajaran').masterPage(
	{
		detailFocusId: '#idPd',
		dataUrl: context_path+'pembelajaran/jsonpeserta',
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
					var action = ' <button type="button" class="btn btn-danger" onClick="deletePeserta(\''+full[0]+'\')">Hapus</button>'
					return action;
				}
			}
		],
		callOnSelect:function(aData, options){
			console.log(aData);
		},
		filters:[{id:'#idPemb-pendidik', name:'idPemb'}]
	});
	
	$('#masterRombel').masterPage(
	{
		detailFocusId: '#idRombel',
		dataUrl: context_path+'pembelajaran/jsonrombel',
		primaryKey: 'idRombel',
        order: [[2,"desc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		showDelButton: false,
		cols: [
			/* idRombel */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idRombel[]" value="'+data+'"/>';
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
					var action = '<a href="'+context_path+'rombel/anggota" target="_blank" class="btn btn-primary">Lihat Anggota</a>';
					action += ' <button type="button" class="btn btn-success" onclick="tambahkanRombel(\''+full[0]+'\')"">Tambahkan</button>'
					return action;
				}
			}
		],
		validationRules: {idRombel:{required: false},nmRombel:{required: true}},
		callOnFillForm: function(data, options){
			//$('input').iCheck("update");
		},
		callOnAddAfterReset:function(options){
			//$('input').iCheck("update");
		}
	});
	
	$('#masterPd').masterPage(
	{
		detailFocusId: '#idPd',
		dataUrl: context_path+'pembelajaran/jsonpesertadidik',
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
					var action = ' <button type="button" class="btn btn-primary" onClick="tambahPeserta(\''+full[0]+'\')">Tambahkan</button>'
					return action;
				}
			}
		],
		callOnSelect:function(aData, options){
			console.log(aData);
		},
		filters:[{id:'#pd-filter',name:'angkatan_pd'}]
	});
	
	getPesertaDidik = function(id,nama){
		$("#idPemb-pendidik").val(id).trigger("change");
		$("#idPemb-peserta").val(id).trigger("change");
		$("#idPemb-rombel").val(id).trigger("change");
		$("#idPemb-pd").val(id).trigger("change");
		$("#title-peserta").text("Peserta Pembelajaran "+nama);
		$("#masterPesertaPembelajaran").find('.dataTables_length select').change();
		$('#master-pesertadidik').show();
		$('#masterpage').hide();
	}
	
	$('.rombel-add').click(function(){
		$('.peserta-close').show();
		$('.rombel-add').hide();
		$('.pd-add').hide();
		$('#masterPesertaPembelajaran').addClass("col-md-6");
		$("#masterRombel").find('.dataTables_length select').change();
		$("#masterPesertaPembelajaran").find('.dataTables_length select').change();
		$('#masterRombel').show();
		$('#masterPd').hide();
	});
	
	$('.pd-add').click(function(){
		$('.peserta-close').show();
		$('.rombel-add').hide();
		$('.pd-add').hide();
		$('#masterPesertaPembelajaran').addClass("col-md-6");
		$("#masterPd").find('.dataTables_length select').change();
		$("#masterPesertaPembelajaran").find('.dataTables_length select').change();
		$('#masterPd').show();
		$('#masterRombel').hide();
	});
	
	$('.peserta-close').click(function(){
		$('.peserta-close').hide();
		$('.rombel-add').show();
		$('.pd-add').show();
		$('#masterPesertaPembelajaran').removeClass("col-md-6");
		$("#masterPesertaPembelajaran").find('.dataTables_length select').change();
		$('#masterRombel').hide();
		$('#masterRombel .checkbox-data').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#masterRombel .checkbox-all').prop("checked",false);
		$('#masterRombel .checkbox-all').uniform();
		$('#masterPd').hide();
		$('#masterPd .checkbox-data').each(function(){
			$(this).prop("checked",false);
			$(this).uniform();
		});
		$('#masterPd .checkbox-all').prop("checked",false);
		$('#masterPd .checkbox-all').uniform();
	});
	
	$(".peserta-delete").click(function(){
		if($('#masterPesertaPembelajaran input[name="idPd[]"]:checked').length == 0){ alert("Tidak ada data terpilih"); return false; }
		if(!confirm("Tambahkan semua peserta didik terpilih?")) return false;

		blockUI($("#masterPesertaPembelajaran"));
		var formdata = $("#masterPesertaPembelajaran .tableform").serialize();
		$.ajax({
			url: context_path+'pengambilankrs/deletepdinpemb',
			data : formdata,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterPesertaPembelajaran"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#masterPesertaPembelajaran").find('.dataTables_length select').change();
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
	
	deletePeserta = function(id){
		blockUI($("#masterPesertaPembelajaran"));
		eval("var tempData = {'idPd[]' : '"+id+"','idPemb' : '"+$("#idPemb-peserta").val()+"'};");
		$.ajax({
			url: context_path+'pengambilankrs/deletepdinpemb',
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterPesertaPembelajaran"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#masterPesertaPembelajaran").find('.dataTables_length select').change();
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
	
	tambahkanRombel = function(id){
		blockUI($("#masterRombel"));
		eval("var tempData = {'idRombel[]' : '"+id+"','idPemb' : '"+$("#idPemb-rombel").val()+"'};");
		$.ajax({
			url: context_path+'pengambilankrs/tambahrombel',
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterRombel"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#masterPesertaPembelajaran").find('.dataTables_length select').change();
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
	
	$(".tambahkan-rombel-all").click(function(){
		if($('#masterRombel input[name="idRombel[]"]:checked').length == 0){ alert("Tidak ada data terpilih"); return false; }
		if(!confirm("Tambahkan semua peserta didik terpilih?")) return false;

		blockUI($("#masterRombel"));
		var formdata = $("#masterPesertaPembelajaran .tableform").serialize();
		$.ajax({
			url: context_path+'pengambilankrs/tambahrombel',
			data : formdata,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterRombel"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#masterPesertaPembelajaran").find('.dataTables_length select').change();
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

	tambahPeserta = function(id){
		blockUI($("#masterpd"));
		eval("var tempData = {'idPd[]' : '"+id+"','idPemb' : '"+$("#idPemb-pd").val()+"'};");
		$.ajax({
			url: context_path+'pengambilankrs/tambahpd',
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterpd"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#masterPesertaPembelajaran").find('.dataTables_length select').change();
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
	
	$(".tambahkan-pd-all").click(function(){
		if($('#masterpd input[name="idPd[]"]:checked').length == 0){ alert("Tidak ada data terpilih"); return false; }
		if(!confirm("Tambahkan semua peserta didik terpilih?")) return false;

		blockUI($("#masterpd"));
		var formdata = $("#masterpd .tableform").serialize();
		$.ajax({
			url: context_path+'pengambilankrs/tambahpd',
			data : formdata,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterpd"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#masterPesertaPembelajaran").find('.dataTables_length select').change();
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