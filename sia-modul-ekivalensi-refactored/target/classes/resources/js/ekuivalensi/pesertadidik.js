var tambahkan;
$(document).ready(function(){
	$('#masterpage').masterPage(
	{
		detailFocusId: '#idPd',
		dataUrl: context_path+'modul/ekuivalensi/ekuivalensi/pesertadidik/jsonekuivalensipd',
		primaryKey: 'idEkuivalensiPd',
        order: [[1,"asc"]],
		editOnClick: false,
		editOnClickRow: true,
		showAddButton: false,
		showDelButton: false,
		columnExclude:[0,6,7,8,9,10],
		cols: [
			/* idEkuivalensiPd */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idEkuivalensiPd[]" value="'+data+'">';
				}
			},
			/* nimPd */
			{ "bVisible":    true },
			/* nmPd */
			{ "bVisible":    true },
			/* nmPtk */
			{ "bVisible":    false },
			/* kurikulumLama */
			{ "bVisible":    true },
			/* kurikulumBaru */
			{ "bVisible":    true },
			/* tglPembuatan */
			{ "bVisible":    false },
			/* idKurikulumLama */
			{ "bVisible":    false },
			/* idKurikulumBaru */
			{ "bVisible":    false },
			/* idPd */
			{ "bVisible":    false },
			/* aEkuivalensi */
			{ "bVisible":    false },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var idRelasi = full[7]+";"+full[8];
					var action = "";
					if(full[10] == "false")
						action = '<button type="button" class="btn btn-primary" onclick="show_modal(\''+full[9]+'\',\''+idRelasi+'\')">Kelola Ekuivalensi</button>';
					else
					{
						action = '<button type="button" class="btn btn-danger" onclick="buka_ekuivalensi(\''+full[9]+'\',\''+idRelasi+'\')">Batalkan Ekuivalensi</button>';
						action += '<button type="button" class="btn btn-primary" style="margin-left:10px;" onclick="laporan(\''+full[0]+'\')">Cetak Laporan</button>';
					}
						
					return action;
				}
			}
		],
		filters: [{id:'#filter-kurikulum-lama', name:'idKurikulumLama'}, {id:'#filter-kurikulum-baru', name:'idKurikulumBaru'}],
	});
	
	$('#add-pd').click(function(){
		$('#modal-ekuivalensi').modal("toggle");
	});
	
	$('#masterpage-modal-peserta-didik').masterPage(
	{
		detailFocusId: '#idPd',
		dataUrl: context_path+'modul/ekuivalensi/ekuivalensi/pesertadidik/jsonpd',
		primaryKey: 'idPd',
        order: [[1,"asc"]],
        editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		cols: [
			/* idPd */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idPd[]" value="'+full[0]+'"/>';
				}
			},
			/* NIM */
			{ "bVisible":    true },
			/* Mahasiswa */
			{ "bVisible":    true },
			/* Angkatan */
			{ "bVisible":    true }
		],
		filters: [{id:'#idRelasi-hidden', name:'idRelasi'}],
		callOnSelect:function(aData, options){
			$('#idPd-hidden').val(aData[0]);
			$("#masterpage-modal-ekuivalensi").find('.dataTables_length select').change();
			$('#modal-peserta-didik').modal("toggle");
			manage_ekuivalensi(aData[0], $('#idRelasi-hidden').val());
		}
	});	
	
	show_modal = function(idPd,idRelasi)
	{
		$('#idPd-hidden').val(idPd);
		$('#idRelasi-hidden').val(idRelasi);
		manage_ekuivalensi(idPd,idRelasi);		
	}
	
	$('#masterpage-modal-ekuivalensi').masterPage(
	{
		detailFocusId: '#idRelasi',
		dataUrl: context_path+'modul/ekuivalensi/ekuivalensi/pesertadidik/json',
		primaryKey: 'idRelasi',
        order: [[0,"asc"]],
        editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		cols: [
			/* idKurikulumLama, idKurikulumBaru */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idRelasi[]" value="'+full[0]+'"/>';
				}
			},
			/* kurikulumLama */
			{ "bVisible":    true },
			/* kurikulumBaru */
			{ "bVisible":    true },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = ' <button type="button" class="btn btn-danger">Pilih</button>';
					var tmp = full[0].split(";");
					
					
//					action += ' <button type="button" class="btn btn-success" onclick="masterpage_to_manage_ekuivalensi(\''+tmp[0]+'\',\''+tmp[1]+'\')">Kelola Ekuivalensi</button>';
					return action;
				}
			}
		],
		callOnSelect:function(aData, options){
			$('#idRelasi-hidden').val(aData[0]);
			$('#masterpage-modal-peserta-didik').find('.dataTables_length select').change();
			$('#modal-ekuivalensi').modal("toggle");
			$('#modal-peserta-didik').modal("toggle");
		}
	});	
	
	fill_pd = function(idPd)
	{
		blockUI($("#block-pd-detail"));
		eval("var tempData = {'idPd' : '"+idPd+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/ekuivalensi/pesertadidik/getpddetail",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#block-pd-detail"));
				if(data.status=='ok')
				{
					$('#pd-detail').html("");
					var append = "";
					append += "<tr>";
					append += "<td>Nama Mahasiswa</td>";
					append += "<td>:</td>";
					append += "<td>"+data.data.nmPd+"</td>";
					append += "<td>Angkatan</td>";
					append += "<td>:</td>";
					append += "<td>"+data.data.angkatanPd+"</td>";
					append += "</tr>";
					append += "<tr>";
					append += "<td>NIM</td>";
					append += "<td>:</td>";
					append += "<td>"+data.data.nimPd+"</td>";
					append += "</tr>";
					$('#pd-detail').append(append);
				}
				else if(data.status=='expired')
				{ document.location=data.message; }
				else
				{ show_message('Error', data.message, true);}
			},
			error: $.ajaxErrorHandler,
			dataType : 'json'
		});
	}
	
	manage_ekuivalensi = function(idPd,idRelasi)
	{
		$('#masterpage').hide();
		fill_pd(idPd);
		$('#mk-wajib-ambil').show();
		$('#idRelasi-hidden').val(idRelasi);
		blockUI($("#block-mk"));
		eval("var tempData = {'idPd' : '"+idPd+"', 'idRelasi' : '"+idRelasi+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/ekuivalensi/pesertadidik/getmkekuivalensi",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#block-mk"));
				if(data.status=='ok')
				{
					console.log(data.data);
//					return false;
					show_message("Sukses", data.message);
					$("#mk-lama").html("");
					for(var i=0;i<data.data.idMKLama.length;i++)
					{
						var append = "";
						append += "<tr>";
						append += "<td>"+data.data.kodeMKLama[i]+"</td>";
						append += "<td>"+data.data.namaMKLama[i]+"</td>";						
						append += "<td>"+data.data.sksMKLama[i]+"</td>";						
						if(data.data.sifatMKLama[i] == "true")
							append += "<td><label class='label label-warning'>Wajib</label></td>";						
						else
							append += "<td><label class='label label-success'>Pilihan</label></td>";
						if(data.data.statusMKLama[i] == "1")
						{
							append += "<td><label id='lbl-"+data.data.idMKLama[i]+"' class='label label-success'>Lulus</label></td>";
							append += "<td><div><input type='checkbox' id='check-"+data.data.idMKLama[i]+"' class='check check-mk-lama' value = '"+data.data.idMKLama[i]+"' onclick='check_lama(\""+data.data.idMKLama[i]+"\")'></div></td>";							
						}							
						else if(data.data.statusMKLama[i] == "2")
						{
							append += "<td><label class='label label-warning'>Tidak Lulus</label></td>";
							append += "<td></td>";
						}
						else if(data.data.statusMKLama[i] == "3")
						{
							append += "<td><label class='label label-primary'>Belum Ambil</label></td>";
							append += "<td></td>";
						}	
						else if(data.data.statusMKLama[i] == "4")
						{
							append += "<td><label class='label label-danger'>Hapus</label></td>";
							append += "<td><div><input type='checkbox' checked id='check-"+data.data.idMKLama[i]+"' class='check check-mk-lama' value = '"+data.data.idMKLama[i]+"' onclick='check_lama(\""+data.data.idMKLama[i]+"\")'></div></td>";
						}	
						append += "</tr>";
						$("#mk-lama").append(append);
						$('#check-'+data.data.idMKLama[i]).uniform();
						$('#check-'+data.data.idMKLama[i]).change();
					}
					
					$("#mk-baru").html("");
					for(var i=0;i<data.data.idMKBaru.length;i++)
					{
						var append = "";
						append += "<tr>";
						append += "<td>"+data.data.kodeMKBaru[i]+"</td>";
						append += "<td>"+data.data.namaMKBaru[i]+"</td>";						
						append += "<td>"+data.data.sksMKBaru[i]+"</td>";
						if(data.data.sifatMKBaru[i] == "true")
							append += "<td><label class='label label-warning'>Wajib</label></td>";
						else append += "<td><label class='label label-success'>Pilihan</label></td>";
						if(data.data.statusMKBaru[i] == "true")
						{
							append += "<td><label id='lbl-"+data.data.idMKBaru[i]+"' class='label label-warning'>Ambil</label></td>";
							append += "<td><div><input type='checkbox' checked id='check-"+data.data.idMKBaru[i]+"' class='check check-mk-baru' value = '"+data.data.idMKBaru[i]+"' onclick='check_baru(\""+data.data.idMKBaru[i]+"\")' checked></div></td>";
						}							
						else
						{
							append += "<td><label id='lbl-"+data.data.idMKBaru[i]+"' class='label label-success'>Bebas</label></td>";
							/*disabled*/
							append += "<td><div><input type='checkbox' id='check-"+data.data.idMKBaru[i]+"' class='check check-mk-baru' value = '"+data.data.idMKBaru[i]+"' onclick='check_baru(\""+data.data.idMKBaru[i]+"\")'></div></td>";							
						}						
						append += "</tr>";
						$("#mk-baru").append(append);
						$('#check-'+data.data.idMKBaru[i]).uniform();
						$('#check-'+data.data.idMKBaru[i]).change();
					}
				}
				else if(data.status=='expired')
				{ document.location=data.message; }
				else
				{ show_message('Error', data.message, true);}
			},
			error: $.ajaxErrorHandler,
			dataType : 'json'
		});
	}
	
	check_lama = function(id)
	{
		/*
		var idMK = new Array();
		var statusMK = new Array();
		var row = new Array();
		$(".check-mk-lama").each(function(){
			idMK.push($(this).val());
		*/
			/* 
			 * 1 = Lulus dan Hapus
			 * 2 = Tidak Lulus atau Tidak Hapus
			 * 3 = Hapus dan onChange
			 * 4 = Tidak Hapus dan onChange
			 */ 
		/*	
			if($(this).prop("checked") == true)
			{
				if($(this).val() == id)
					statusMK.push("3");
				else statusMK.push("1");
			}
			else
			{
				if($(this).val() == id)
					statusMK.push("4");
				else statusMK.push("2");
			}
		});
		
		row = {
			"idMK" : idMK,
			"statusMK" : statusMK
		};
		
		var json_stringify = JSON.stringify(row);
		$.ajax({
			url: context_path+"modul/ekuivalensi/ekuivalensi/checkedmklama",
			data : json_stringify,
			type : 'post',				
			contentType: "application/json", //this is required for spring 3 - ajax to work (at least for me)
			success: function(data)
			{
//				unblockUI($("#ekuivalensi-mk"));
				console.log(data.data);
				if(data.status=='ok')
				{
					show_message("Sukses", data.message);
					if(data.data.idMK != null)
					{
						for(var i=0;i<data.data.idMK.length;i++)
						{
							$('#check-'+data.data.idMK[i]).removeAttr("disabled");
							$('#check-'+data.data.idMK[i]).prop("checked",true);
							$('#check-'+data.data.idMK[i]).uniform();
							$('#check-'+data.data.idMK[i]).change();
						}
						$('#check-'+id).attr("disabled","true");
						$('#check-'+id).uniform();
						$('#check-'+id).change();
					}
				}
				else if(data.status=='expired')
				{ document.location=data.message; }
				else
				{ show_message('Error', data.message);}
			},
			error: $.ajaxErrorHandler,
			dataType : 'json'
		});
		*/
		//Cek ketika uncheck
		if($('#check-'+id).prop("checked") == true)
		{
			//ajax
			$("#lbl-"+id).removeClass("label-success");
			$("#lbl-"+id).addClass("label-danger");
			$("#lbl-"+id).text("Hapus");
		}
		else
		{
			$("#lbl-"+id).removeClass("label-danger");
			$("#lbl-"+id).addClass("label-success");
			$("#lbl-"+id).text("Lulus");
		}
	}
	
	check_baru = function(id)
	{
		if($('#check-'+id).prop("checked") == true)
		{
			//ajax
			$("#lbl-"+id).removeClass("label-success");
			$("#lbl-"+id).addClass("label-warning");
			$("#lbl-"+id).text("Ambil");
		}
		else
		{
			$("#lbl-"+id).removeClass("label-warning");
			$("#lbl-"+id).addClass("label-success");
			$("#lbl-"+id).text("Bebas");
		}
	}
	
	simpan_permanen = function()
	{		
		blockUI($("#block-mk"));
		eval("var tempData = {'idPd' : '"+$('#idPd-hidden').val()+"','idRelasi' : '"+$('#idRelasi-hidden').val()+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/ekuivalensi/pesertadidik/simpanpermanen",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#block-mk"));
				if(data.status=='ok')
				{
//					show_message("Sukses", data.message);
//					$("#masterpage").find('.dataTables_length select').change();
				}
				else if(data.status=='expired')
				{ document.location=data.message; }
				else
				{ show_message('Error', data.message, true);}
			},
			error: $.ajaxErrorHandler,
			dataType : 'json'
		});
		
	}
	
	simpan_mkwajib = function(mode)
	{
		var idMK = new Array();
		var statusMK = new Array();
		var row = new Array();
		//True untuk MK yang krs nya dihapus
		//False untuk MK yang diwajibkan
		$('.check').each(function(){
			
			if($(this).prop("checked") == true)
			{
				idMK.push($(this).val());
				if($(this).hasClass("check-mk-lama"))
					statusMK.push("true");
				else if($(this).hasClass("check-mk-baru"))
					statusMK.push("false");
			}
		});
		
		row = {
			"idMK" : idMK,
			"statusMK" : statusMK,
			"idPd" : $('#idPd-hidden').val(),
			"idRelasi" : $('#idRelasi-hidden').val()
		};
		
		blockUI($("#block-mk"));
		var json_stringify = JSON.stringify(row);
		$.ajax({
			url: context_path+"modul/ekuivalensi/ekuivalensi/pesertadidik/saveekuivalensi",
			data : json_stringify,
			type : 'post',				
			contentType: "application/json", //this is required for spring 3 - ajax to work (at least for me)
			success: function(data)
			{
				unblockUI($("#block-mk"));
				console.log(data.data);
				if(data.status=='ok')
				{
					show_message("Sukses", data.message);
					if(mode == 0)
						simpan_permanen();
					setTimeout(function(){
                         location.reload();
                    }, 3000);
				}
				else if(data.status=='expired')
				{ document.location=data.message; }
				else
				{ show_message('Error', data.message, true);}
			},
			error: $.ajaxErrorHandler,
			dataType : 'json'
		});
	}
	
	manage_mkwajib_to_masterpage = function()
	{
		$('#mk-wajib-ambil').hide();
		$('#masterpage').show();
	}
	
	buka_ekuivalensi = function(id, idRelasi)
	{
		blockUI($("#masterpage"));
		eval("var tempData = {'idPd' : '"+id+"','idRelasi' : '"+idRelasi+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/ekuivalensi/pesertadidik/bukaekuivalensi",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterpage"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message);
					$("#masterpage").find('.dataTables_length select').change();
				}
				else if(data.status=='expired')
				{ document.location=data.message; }
				else
				{ show_message('Error', data.message, true);}
			},
			error: $.ajaxErrorHandler,
			dataType : 'json'
		});
	}
	
	laporan = function(idEkuivalensiPd)
	{
		 var redirectWindow = window.open(context_path+"modul/ekuivalensi/ekuivalensi/pesertadidik/cetak/"+idEkuivalensiPd, '_blank');
		   redirectWindow.location;
	}
});