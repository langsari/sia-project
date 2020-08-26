var tambahkan;
$(document).ready(function(){
	$('.date-picker').datepicker({
		format: "dd-mm-yyyy"
	});
	
	
	
	$('#masterpage-modal-ekuivalensi').masterPage(
	{
		detailFocusId: '#idRelasi',
		dataUrl: context_path+'modul/ekuivalensi/calonpd/jsonekuivalensi',
		primaryKey: 'idKurikulum',
		columnExclude:[0],
		order: [[1,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		showDelButton: false,
		cols: [
			/* idRelasi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idRelasi[]" value="'+full[0]+'">';
				}
			},
			/* nmKatalog */
			{ "bVisible":    true },
			/* nmKurikulum */
			{ "bVisible":    true }
		],
		filters:[{id:'#idCalonPD-hidden', name:'idCalonPD'},{id:'#filter-statusBerlaku', name:'statusBerlaku'}],
		callOnSelect:function(aData, options){
			var tmp = aData[0].split(';');
			$('#idKatalogAlihjenjang').val(tmp[0]);
			$('#idKurikulum-hidden').val(tmp[1]);
			$('#modal-ekuivalensi').modal('toggle');
			manage_ekuivalensi();
		}
	});
	
	modal_katalog_kurikulum = function(idCalonPD,nmCalonPD)
	{
		$('#idCalonPD-hidden').val(idCalonPD).trigger("change");
		$('#nmCalonPD-hidden').val(nmCalonPD).trigger("change");
		$('#masterpage-modal-ekuivalensi').find('.dataTables_length select').change();
		$('#modal-ekuivalensi').modal('show');
	}
	
	modal_kurikulum = function(idCalonPD,nmCalonPD)
	{
		$('#idCalonPD-hidden').val(idCalonPD).trigger("change");
		$('#nmCalonPD-hidden').val(nmCalonPD).trigger("change");
		$('#masterpage-modal-kurikulum').find('.dataTables_length select').change();
		$('#modal-kurikulum').modal('show');
	}
	
	$('#penyetaraan-mk').change(function() {
        if ($(this).prop("checked") == true) {
        	console.log('cek');
        	$('#pedomanekuivalensi-field').show();
        	$('#idKatalogAlihjenjang').removeAttr("disabled");
        	$('#idKurikulum').removeAttr("disabled");
        	$('#idKatalogAlihjenjang-input').attr("disabled","true");
        	$('#idKurikulum-input').attr("disabled","true");
        } else {
        	console.log('uncek');
        	$('#idKatalogAlihjenjang').attr("disabled","true");
        	$('#idKurikulum').attr("disabled","true");
        	$('#idKatalogAlihjenjang-input').removeAttr("disabled");
        	$('#idKatalogAlihjenjang-input').val(null);
        	$('#idKurikulum-input').removeAttr("disabled");
        	$('#idKurikulum-input').val(null);
        	$('#pedomanekuivalensi-field').hide();
        }
    });
	
	manage_ekuivalensi = function()
	{
		$('#title-ekuivalensi-alihjenjang').text("");
//		$('#title-ekuivalensi-alihjenjang').text("KRS "+$('#nmCalonPD-hidden').val());
		$('#title-ekuivalensi-alihjenjang').text("Matakuliah Tempuh");
		$('#ekuivalensi-alihjenjang').show();
		$('#ekuivalensi-mkluar').find('.dataTables_length select').change();
		$('#ekuivalensi-mk').find('.dataTables_length select').change();
		$('#masterpage').hide();
	}
	
	manage_ekuivalensi_to_master_calonpd = function()
	{
		$('#ekuivalensi-alihjenjang').hide();
		$('#masterpage').show();
	}
	
	$('#ekuivalensi-mkluar').masterPage(
	{
		detailFocusId: '#idCalonPD',
		dataUrl: context_path+'modul/ekuivalensi/calonpd/getdatablemkluar',
		deleteUrl: context_path+'modul/ekuivalensi/calonpd/deletemanykrs',
		primaryKey: 'idKrsCalonPD',
        order: [[1,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		cols: [
			/* idMKLuar */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idKrsCalonPD[]" value="'+data+'">';
				}
			},
			/* nmMKLuar */
			{ "bVisible":    true },	
			/* sks */
			{ "bVisible":    true },
			/* huruf */
			{ "bVisible":    true },	
			/* aLulus */
			{ 
				"bVisible":    true,
				bSortable: true,
				bSearchable: true,
				mRender: function(data){
					if(data == "false")
						return "Tidak Lulus";
					else return "Lulus";
				} 
			},
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = ' <button type="button" class="btn btn-danger deleterow">Hapus</button>';
					return action;
				}
			}
		],
		validationRules: {idMKLuar:{required: true},nmMKLuar:{required: false}, sks:{required: true}},
		filters: [{id:'#idCalonPD-hidden', name:'idCalonPD'}],
		callOnFillForm:function(data,options){
//			$('#satMan').val(data.data.satMan==null?"":data.data.satMan.idSatMan);
		},
	});
	
	$('#ekuivalensi-mk').masterPage(
	{
		detailFocusId: '#idCalonPD',
		dataUrl: context_path+'modul/ekuivalensi/calonpd/getdatablemk',
		deleteUrl: context_path+'modul/ekuivalensi/calonpd/deletemanymkterakui',
		primaryKey: 'idAlihJenjangMKTerakui',
        order: [[2,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		columnExclude:[0,1],
		cols: [
			/* idAlihJenjangMKTerakui */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idAlihJenjangMKTerakui[]" value="'+data+'">';
				}
			},
			/* nmCalonPD */
			{ "bVisible":    false },
			/* nmMK */
			{ "bVisible":    true },	
			/* jumlahSKS */
			{ "bVisible":    true },			
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = ' <button type="button" class="btn btn-danger deleterow">Hapus</button>';
					return action;
				}
			}
		],
		validationRules: {idMK:{required: true},namaM:{required: false}, sks:{required: true}},
		filters: [{id:'#idCalonPD-hidden', name:'idCalonPD'},{id:'#idKurikulum-hidden', name:'idKurikulum'}],
		callOnFillForm:function(data,options){
//			$('#satMan').val(data.data.satMan==null?"":data.data.satMan.idSatMan);
		}
	});
	
	$('#masterpage-mkluar').masterPage(
	{
		detailFocusId: '#idCalonPD',
		dataUrl: context_path+'modul/ekuivalensi/calonpd/getmkluar',
//		detailUrl: context_path+'modul/ekuivalensi/calonpd/edit',
//		addUrl: context_path+'modul/ekuivalensi/calonpd/simpan',
//		editUrl: context_path+'modul/ekuivalensi/calonpd/simpan',
//		deleteUrl: context_path+'modul/ekuivalensi/calonpd/deletemany',
		primaryKey: 'idMKLuar',
        order: [[2,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		columnExclude:[0,1,6],
		cols: [
			/* idMKLuar */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idMKLuar[]" value="'+data+'">';
				}
			},
			/* katalogAlihjenjang */
			{ "bVisible":    false },
			/* kodeMKLuar */
			{ "bVisible":    true },			
			/* nmMKLuar */
			{ "bVisible":    true },	
			/* sks */
			{ "bVisible":    true },
			/* deskripsiMKLuar */
			{ "bVisible":    false },
			/* aMKLuarTerhapus */
			{ "bVisible":    false }
		],
		validationRules: {idMK:{required: true},namaM:{required: false}, sks:{required: true}},
				filters: [{id:'#idCalonPD-hidden', name:'idCalonPD'}],
		callOnFillForm:function(data,options){
//					$('#satMan').val(data.data.satMan==null?"":data.data.satMan.idSatMan);
		},
		callOnSelect:function(aData, options){
			$("#modal-nilai").modal("show");
			$('#modal-mkluar').modal("toggle");
			$("#id-mkluar").val(aData[0]);
		}
	});
	
	$('#btn-success-modal-nilai').click(function(){
		if($("#konversiNilai").val() == 0)
		{
			alert("Pilih nilai");
			return false;
		}
		add_mkluar($("#id-mkluar").val(),$("#konversiNilai").val(),$("#alulus").is(':checked'));
		$("#id-mkluar").val("");
		$('#alulus').prop('checked', false);
		$('#alulus').uniform();
		$('#alulus').change();
		document.getElementById('konversiNilai').selectedIndex = 0;
		$('#modal-nilai').modal("toggle");
	});
	
	$('#btn-cancel-modal-nilai').click(function(){
		$('#modal-mkluar').modal("show");
		$('#modal-nilai').modal("toggle");
	});
	
	$('#masterpage-mk').masterPage(
	{
		detailFocusId: '#idCalonPD',
		dataUrl: context_path+'modul/ekuivalensi/calonpd/getmk',
//		detailUrl: context_path+'modul/ekuivalensi/calonpd/edit',
//		addUrl: context_path+'modul/ekuivalensi/calonpd/simpan',
//		editUrl: context_path+'modul/ekuivalensi/calonpd/simpan',
//		deleteUrl: context_path+'modul/ekuivalensi/calonpd/deletemany',
		primaryKey: 'idMK',
        order: [[2,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		cols: [
			/* idMK */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idMK[]" value="'+data+'">';
				}
			},
			/* kodeMK */
			{ "bVisible":    true },
			/* namaMK */
			{ "bVisible":    true },
			/* jumlahSKS */
			{ "bVisible":    true },	
			/* sifatMK */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					if(full[4]=="true")
						return "<label class='label label-warning'>Wajib</label>";
					else return "<label class='label label-success'>Pilihan</label>";
				}
			}
		],
		validationRules: {idMK:{required: true},namaM:{required: false}, sks:{required: true}},
		filters: [{id:'#idKurikulum-hidden', name:'idKurikulum'},{id:'#idCalonPD-hidden', name:'idCalonPD'}],
		callOnFillForm:function(data,options){
//				$('#satMan').val(data.data.satMan==null?"":data.data.satMan.idSatMan);
		},
		callOnSelect:function(aData, options){
			$('#modal-mk').modal('toggle');
			add_mk(aData[0]);
		}
	});
	
	$('#masterpage-modal-kurikulum').masterPage(
	{
		detailFocusId: '#idKurikulum',
		dataUrl: context_path+'modul/ekuivalensi/calonpd/jsonmodalkurikulum',
		primaryKey: 'idKurikulum',
        order: [[2,"asc"]],
        editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		columnExclude:[0,5],
		cols: [
			/* idKurikulum */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idKurikulum[]" value="'+data+'">';
				}
			},
			/* satMan */
			{ "bVisible":    true },
			/* namaKurikulum */
			{ "bVisible":    true },
			/* thnMulai */
			{ "bVisible":    true },
			/* thnAkhir */
			{ "bVisible":    true },
			/* statusKurikulum */
			{ "bVisible":    false }
		],
		filters: [{id:'#idCalonPD-hidden', name:'idCalonPD'},{id:'#filter-kurikulumStatusBerlaku', name:'statusBerlaku'}],
		callOnSelect:function(aData, options){
			//managemkwajib
			$('#idKurikulum-hidden').val(aData[0]);
			manage_mkwajib();
			$('#modal-kurikulum').modal("toggle");
//			manage_ekuivalensi(aData[0],aData[2]);
			
		}
	});
	
	
	show_modal_mkluar = function()
	{
		$("#modal-mkluar").modal("show");
		$('#masterpage-mkluar').find('.dataTables_length select').change();
	}

	add_mkluar = function(id,nilai,lulus)
	{
		//ajax insert
		blockUI($("#ekuivalensi-mkluar"));
		eval("var tempData = {'idCalonPD' : '"+$('#idCalonPD-hidden').val()+"','idMKLuar' : '"+id+"'," +
				"'idKonversi' : '"+nilai+"', 'aLulus' : '"+lulus+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/calonpd/tambahmkluar",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#ekuivalensi-mkluar"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#ekuivalensi-mkluar").find('.dataTables_length select').change();
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

	show_modal_mk = function()
	{
		$("#modal-mk").modal("show");
		$('#masterpage-mk').find('.dataTables_length select').change();
	}

	add_mk = function(id)
	{
		//ajax insert
		blockUI($("#ekuivalensi-mk"));
		eval("var tempData = {'idCalonPD' : '"+$('#idCalonPD-hidden').val()+"','idMK' : '"+id+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/calonpd/tambahmk",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#ekuivalensi-mk"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#ekuivalensi-mk").find('.dataTables_length select').change();
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
	
	cek_ekuivalensi = function()
	{
		blockUI($("#ekuivalensi-mk"));
		eval("var tempData = {'idCalonPD' : '"+$('#idCalonPD-hidden').val()+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/calonpd/cekekuivalensi",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#ekuivalensi-mk"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message)
					$("#ekuivalensi-mk").find('.dataTables_length select').change();
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
	
	fill_pd = function(idCalonPD)
	{
		blockUI($("#block-calonpd-detail"));
		eval("var tempData = {'idCalonPD' : '"+idCalonPD+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/calonpd/getcalonpddetail",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#block-calonpd-detail"));
				if(data.status=='ok')
				{
					console.log(data.data.tglLahir);
					var tglLahir = (data.data.tglLahir.values[2]<10?"0"+data.data.tglLahir.values[2]:data.data.tglLahir.values[2])+"-"+(data.data.tglLahir.values[1]<10?"0"+data.data.tglLahir.values[1]:data.data.tglLahir.values[1])+"-"+data.data.tglLahir.values[0];
					$('#calon-pd-detail').html("");
					var append = "";
					append += "<tr>";
					append += "<td>Nama</td>";
					append += "<td style='width:30%;'>: "+data.data.nmCalonPD+"</td>";
					append += "<td>Perguran Tinggi Asal</td>";
					append += "<td style='width:30%;'>: "+data.data.ptAsal+"</td>";
					append += "</tr>";					
					
					append += "<tr>";
					append += "<td>Tempat, Tanggal Lahir</td>";
					append += "<td>: "+data.data.tempatLahir+", "+tglLahir+"</td>";
					append += "<td>Alamat</td>";
					append += "<td>: "+data.data.alamat+"</td>";
					append += "</tr>";					
					
					append += "<tr>";
					append += "<td>SKS Wajib Tempuh</td>";
					append += "<td id='skswajib'></td>";
//					append += "<td>Alamat</td>";
//					append += "<td>: "+data.data.alamat+"</td>";
					append += "</tr>";
					
					$('#calon-pd-detail').append(append);
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
	
	manage_mkwajib = function()
	{
//		$("#idCalonPD-hidden").val(id).trigger("change");
		$('#mk-wajib-ambil').show();		
		$('#masterpage').hide();
		fill_pd($('#idCalonPD-hidden').val());
		blockUI($("#block-mk"));
		eval("var tempData = {'idCalonPD' : '"+$('#idCalonPD-hidden').val()+"','idKurikulum' : '"+$('#idKurikulum-hidden').val()+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/calonpd/getmkwajib",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#block-mk"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message);
					$("#mk-wajib").html("");
					$("#mk-alihjenjang").html("");
					$("#mk-satman").html("");
					$('#tabel-mk-alihjenjang').hide();
					$('#tabel-mk-satman').hide();
					$('#tabel-mk-wajib').hide();
					var bool = true;
					for(var i=0;i<data.data.length;i++)
					{						
						var max = data.data[i].idMKAlihjenjang.length;
						if(max < data.data[i].idMKSatMan.length)
							max = data.data[i].idMKSatMan.length;
						var mkalihjenjang = [];
						var mksatman = [];
						var relasialihjenjang = data.data[i].relasiMKAlihjenjang;
						var relasisatman = data.data[i].relasiMKSatMan;
						console.log(relasisatman);
						if(relasisatman != "null")
						{
							console.log(relasisatman);
							for(var j=0;j<max;j++)
							{
								bool = false;
								$('#tabel-mk-wajib').show();
								var td = [];
								if(j<data.data[i].idMKAlihjenjang.length)
								{
									td.push(data.data[i].idMKAlihjenjang[j]);
									td.push(data.data[i].kodeMKAlihjenjang[j]);
									td.push(data.data[i].namaMKAlihjenjang[j]);
									td.push(data.data[i].sksMKAlihjenjang[j]);
									td.push(data.data[i].statusMKAlihjenjang[j]);
									mkalihjenjang.push(td);
								}
								td = [];
								if(j<data.data[i].idMKSatMan.length)
								{
									td.push(data.data[i].idMKSatMan[j]);
									td.push(data.data[i].kodeMKSatMan[j]);
									td.push(data.data[i].namaMKSatMan[j]);
									td.push(data.data[i].sksMKSatMan[j]);
	//								td.push(data.data[i].semesterMKSatMan[j]);
									td.push(data.data[i].sifatMKSatMan[j]);
									td.push(data.data[i].statusMKSatMan[j]);
									mksatman.push(td);
								}
							}							
							fill_tabel_mk(mkalihjenjang, mksatman, relasialihjenjang, relasisatman, 1, -1);
						}
						if(relasialihjenjang == "null" && data.data[i].idMKAlihjenjang[0] != null)
						{
							bool = false;
							$("#tabel-mk-alihjenjang").show();
							var append = "";
							append = "<tr>";
							append += "<td>"+data.data[i].kodeMKAlihjenjang[0]+"</td>";
							append += "<td>"+data.data[i].namaMKAlihjenjang[0]+"</td>";
							append += "<td id='sksalihjenjang-"+data.data[i].idMKSatMan+"'>"+data.data[i].sksMKAlihjenjang[0]+"</td>";
							append += "<td>"+data.data[i].statusMKAlihjenjang[0]+"</td>";
							append += "</tr>";
							$("#mk-alihjenjang").append(append);
						}
						if(relasisatman == "null" && data.data[i].idMKSatMan[0] != null)
						{
							$("#tabel-mk-satman").show();						
								
							var append = "";
							append = "<tr>";
							append += "<td>"+data.data[i].kodeMKSatMan[0]+"</td>";
							append += "<td>"+data.data[i].namaMKSatMan[0]+"</td>";
							append += "<td id='skssatman-"+data.data[i].idMKSatMan+"'>"+data.data[i].sksMKSatMan[0]+"</td>";
							if(data.data[i].sifatMKSatMan[0] == "Wajib")
								append += "<td><label class='label label-warning'>"+data.data[i].sifatMKSatMan[0]+"</label></td>";
							else
								append += "<td><label id='lbl-"+data.data[i].idMKSatMan[0]+"' class='label label-success'>"+data.data[i].sifatMKSatMan[0]+"</label></td>";
//							if(data.data[i].statusMKSatMan[0] == "Ambil")
//								append += "<td><label id='lbl-"+data.data[i].idMKSatMan[0]+"' class='label label-warning'>"+data.data[i].statusMKSatMan[0]+"</label></td>";
//							else append += "<td><label id='lbl-"+data.data[i].idMKSatMan[0]+"' class='label label-success'>"+data.data[i].statusMKSatMan[0]+"</label></td>";
								
							
							if(data.data[i].sifatMKSatMan[0] == "Wajib")
							{
								if(data.data[i].statusMKSatMan[0] == "Ambil")
									append += "<td><div><input value='"+data.data[i].idMKSatMan[0]+"' id='check-"+data.data[i].idMKSatMan[0]+"' onclick='check(\""+data.data[i].idMKSatMan[0]+"\")' class='check-mk mk-wajib' checked type='checkbox'></div></td>";
								else append += "<td><div><input value='"+data.data[i].idMKSatMan[0]+"' id='check-"+data.data[i].idMKSatMan[0]+"' onclick='check(\""+data.data[i].idMKSatMan[0]+"\")' class='check-mk mk-wajib' type='checkbox'></div></td>";
								
							}
							else append += "<td id='td-"+data.data[i].idMKSatMan[0]+"'><input style='display:none' value='"+data.data[i].idMKSatMan[0]+"' class='check-mk' id='check-"+data.data[i].idMKSatMan[0]+"' type='checkbox'></td>";
							append += "</tr>";
							$("#mk-satman").append(append);
							$('.check-mk').uniform();
							$('.check-mk').change();
						}
					}
					if(bool)
					{
						if($("#tabel-mk-satman").hasClass('col-md-7'))
						{
							$("#tabel-mk-satman").removeClass('col-md-7');
							$("#tabel-mk-satman").addClass('col-md-12');
							if($("#block-mk").hasClass('col-md-12'))
							{
								$("#block-mk").removeClass('col-md-12');
								$("#block-mk").addClass('col-md-8 col-md-offset-2');
							}
						}
					}
					else
					{
						if($("#tabel-mk-satman").hasClass('col-md-12'))
						{
							$("#tabel-mk-satman").removeClass('col-md-12');
							$("#tabel-mk-satman").addClass('col-md-7');
							
							if($("#block-mk").hasClass('col-md-8 col-md-offset-2'))
							{
								$("#block-mk").removeClass('col-md-8 col-md-offset-2');
								$("#block-mk").addClass('col-md-12');
							}
						}
					}
					calculateSKS();
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
	
	calculateSKS = function()
	{
		var checkedMKSatMan = new Array();
		var id = new Array();
		var total = 0;
		$('.mk-wajib').each(function(){
			if($.inArray($(this).val(), id))
			{
				id.push($(this).val());
				if (this.checked) {
					total += Number($("#skssatman-"+$(this).val()).html());
				}
				else
				{
//					total += Number($("#skssatman-"+$(this).val()).html());
				}
			}
		});
		$('#skswajib').html("");
		$('#skswajib').append(": "+total);
//		alert(total);
	}
	
	check = function(id)
	{
		calculateSKS();
		if($("#lbl-"+id).hasClass("label-success"))
		{
			$("#lbl-"+id).removeClass("label-success");
			$("#lbl-"+id).addClass("label-warning");
			$("#lbl-"+id).text("Ambil");
        } else {
        	$("#lbl-"+id).removeClass("label-warning");
			$("#lbl-"+id).addClass("label-success");
			$("#lbl-"+id).text("Bebas");
        }
	}
	
	manage_mkwajib_to_master_calonpd = function()
	{
		$('#mk-wajib-ambil').hide();
		$('#masterpage').show();
	}
	
	simpan_mkwajib = function()
	{
		var idMK = new Array();
		var row = new Array();
		var status = new Array();
		$('.check-mk').each(function(){
			
			idMK.push($(this).val());
			if (this.checked) {
				status.push("true");
			}
			else status.push("false");
		});
		row = {
				"idMK" : idMK,
				"idCalonPD" : $("#idCalonPD-hidden").val(),
				"idKurikulum" : $("#idKurikulum-hidden").val(),
//				"kodeMK" : idMK,
//				"namaMK" : idMK,
//				"jumlahSKS" : idMK,
				"status" : status
			};
		
		var json_stringify = JSON.stringify(row);
		blockUI($("#block-mk"));
		$.ajax({
			url: context_path+"modul/ekuivalensi/calonpd/savemkwajib",
			data : json_stringify,
			type : 'post',
			contentType: "application/json", //this is required for spring 3 - ajax to work (at least for me)
		    success: function(data)
			{
				unblockUI($("#block-mk"));
				if(data.status=='ok')
				{
					show_message("Sukses", data.message);
					setTimeout(function(){
                        location.reload();
                   }, 3000);
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
	
	simpan_permanen = function(idCalonPD)
	{
		blockUI($("#masterpage"));
		eval("var tempData = {'idCalonPD' : '"+idCalonPD+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/calonpd/simpanpermanen",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterpage"));
				if(data.status=='ok')
				{
					show_message('Sukses', data.message);
					$("#masterpage").find('.dataTables_length select').change();
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
	
	buka_ekuivalensi = function(id)
	{
		blockUI($("#masterpage"));
		eval("var tempData = {'idCalonPD' : '"+id+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/calonpd/bukaekuivalensi",
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
				{ show_message('Error', data.message);}
			},
			error: $.ajaxErrorHandler,
			dataType : 'json'
		});
	}
	
	laporan = function(idCalonPD)
	{
		 var redirectWindow = window.open(context_path+"modul/ekuivalensi/calonpd/cetak/"+idCalonPD, '_blank');
		   redirectWindow.location;
	}
	
	fill_tabel_mk = function(mkalihjenjang, mksatman, relasialihjenjang, relasisatman, mode, index)
	{
//		var row = new Array();
//		var idMKAlihjenjang = new Array();
//		var kodeMKAlihjenjang = new Array();
//		var namaMKAlihjenjang = new Array();
//		var sksMKAlihjenjang = new Array();
//		var keteranganMKAlihjenjang = new Array();
//		
//		var idMKSatMan = new Array();
//		var kodeMKSatMan = new Array();
//		var namaMKSatMan = new Array();
//		var sksMKSatMan = new Array();
//		var sifatMKSatMan = new Array();
//		var keteranganMKSatMan = new Array();
//		var semesterMKSatMan = new Array();
		
		if(mode == 0)
			rows_length = index;
		var max = mkalihjenjang.length;
		if(max < mksatman.length)
			max = mksatman.length;
		var selisih = Math.abs(mkalihjenjang.length-mksatman.length);
		for(var i=0;i<max;i++)
		{
			var append = "";
			var td_relasialihjenjang = "";
			var td_relasisatman = "";
			
			append += "<tr>";
			
			if(i==0)
			{
//				append += "<td class='td-no' rowspan='"+max+"'>"+(rows_length+1)+"</td>";
				if(max > 1)
				{
					td_relasialihjenjang = "<td rowspan='"+max+"'>"+relasialihjenjang+"</td>";
					td_relasisatman = "<td rowspan='"+max+"'>"+relasisatman+"</td>";					
				}
				else
				{
					td_relasialihjenjang = "<td>"+relasialihjenjang+"</td>";
					td_relasisatman = "<td rowspan='"+max+"'>"+relasisatman+"</td>";
				}
			}
			
			if(i+1 == mkalihjenjang.length)
			{
//				idMKAlihjenjang.push(mkalihjenjang[i][0]);
//				kodeMKAlihjenjang.push(mkalihjenjang[i][1]);
//				namaMKAlihjenjang.push(mkalihjenjang[i][2]);
//				sksMKAlihjenjang.push(mkalihjenjang[i][3]);
//				keteranganMKAlihjenjang.push(mkalihjenjang[i][4]);
				append += "<td rowspan='"+(max-mkalihjenjang.length+1)+"'>"+mkalihjenjang[i][1]+"</td>";
				append += "<td rowspan='"+(max-mkalihjenjang.length+1)+"'>"+mkalihjenjang[i][2]+"</td>";
				append += "<td id='sksalihjenjang-"+mkalihjenjang[i][0]+"' rowspan='"+(max-mkalihjenjang.length+1)+"'>"+mkalihjenjang[i][3]+"</td>";
				append += "<td rowspan='"+(max-mkalihjenjang.length+1)+"'>"+mkalihjenjang[i][4]+"</td>";
			}
			else if(i<mkalihjenjang.length)
			{
//				idMKAlihjenjang.push(mkalihjenjang[i][0]);
//				kodeMKAlihjenjang.push(mkalihjenjang[i][1]);
//				namaMKAlihjenjang.push(mkalihjenjang[i][2]);
//				sksMKAlihjenjang.push(mkalihjenjang[i][3]);
//				keteranganMKAlihjenjang.push(mkalihjenjang[i][4]);
				append += "<td>"+mkalihjenjang[i][1]+"</td>";
				append += "<td>"+mkalihjenjang[i][2]+"</td>";
				append += "<td id='sksalihjenjang-"+mkalihjenjang[i][0]+"'>"+mkalihjenjang[i][3]+"</td>";
				append += "<td>"+mkalihjenjang[i][4]+"</td>";
			}
			
			append += td_relasialihjenjang;
			
			if(i+1 == mksatman.length)
			{
//				idMKSatMan.push(mksatman[i][0]);
//				kodeMKSatMan.push(mksatman[i][1]);
//				namaMKSatMan.push(mksatman[i][2]);
//				sksMKSatMan.push(mksatman[i][3]);
//				semesterMKSatMan.push(mksatman[i][4]);
//				sifatMKSatMan.push(mksatman[i][4]);
//				keteranganMKSatMan.push(mksatman[i][5]);
				append += "<td rowspan='"+(max-mksatman.length+1)+"'>"+mksatman[i][1]+"</td>";
				append += "<td rowspan='"+(max-mksatman.length+1)+"'>"+mksatman[i][2]+"</td>";
				append += "<td id='skssatman-"+mksatman[i][0]+"' rowspan='"+(max-mksatman.length+1)+"'>"+mksatman[i][3]+"</td>";				
//				append += "<td rowspan='"+(max-mksatman.length+1)+"'>"+mksatman[i][4]+"</td>";
				if(mksatman[i][4] == "Wajib")
					append += "<td rowspan='"+(max-mksatman.length+1)+"'><label class='label label-warning'>"+mksatman[i][4]+"</label></td>";
				else append += "<td rowspan='"+(max-mksatman.length+1)+"'><label class='label label-success'>"+mksatman[i][4]+"</label></td>";				
//				append += "<td rowspan='"+(max-mksatman.length+1)+"'>"+mksatman[i][5]+"</td>";
//				if(mksatman[i][5] == "Ambil")
//					append += "<td rowspan='"+(max-mksatman.length+1)+"'><label id='lbl-"+mksatman[i][0]+"' class='label label-warning'>"+mksatman[i][5]+"</label></td>";
//				else append += "<td rowspan='"+(max-mksatman.length+1)+"'><label id='lbl-"+mksatman[i][0]+"' class='label label-success'>"+mksatman[i][5]+"</label></td>";
				if(mksatman[i][4] == "Wajib")
				{
					if(mksatman[i][5] == "Ambil")
						append += "<td rowspan='"+(max-mksatman.length+1)+"'><div><input value='"+mksatman[i][0]+"' class='check-mk mk-wajib' onclick='check(\""+mksatman[i][0]+"\")' id='check-"+mksatman[i][0]+"' checked type='checkbox'></div></td>";
					else append += "<td rowspan='"+(max-mksatman.length+1)+"'><div><input value='"+mksatman[i][0]+"' input class='check-mk mk-wajib' onclick='check(\""+mksatman[i][0]+"\")' id='check-"+mksatman[i][0]+"' type='checkbox'></div></td>";
				}
				else append += "<td id='td-"+mksatman[i][0]+"' rowspan='"+(max-mksatman.length+1)+"'><input style='display:none' value='"+mksatman[i][0]+"' class='check-mk' id='check-"+mksatman[i][0]+"' type='checkbox'></td>";
				
			}
			else if(i<mksatman.length)
			{
//				idMKSatMan.push(mksatman[i][0]);
//				kodeMKSatMan.push(mksatman[i][1]);
//				namaMKSatMan.push(mksatman[i][2]);
//				sksMKSatMan.push(mksatman[i][3]);
////				semesterMKSatMan.push(mksatman[i][4]);
//				sifatMKSatMan.push(mksatman[i][4]);
//				keteranganMKSatMan.push(mksatman[i][5]);
				append += "<td>"+mksatman[i][1]+"</td>";
				append += "<td>"+mksatman[i][2]+"</td>";
				append += "<td id='skssatman-"+mksatman[i][0]+"'>"+mksatman[i][3]+"</td>";
//				append += "<td>"+mksatman[i][4]+"</td>";
				if(mksatman[i][4] == "Wajib")
					append += "<td><label class='label label-warning'>"+mksatman[i][4]+"</label></td>";
				else append += "<td><label class='label label-success'>"+mksatman[i][4]+"</label></td>";	
//				if(mksatman[i][5] == "Ambil")
//					append += "<td><label id='lbl-"+mksatman[i][0]+"' class='label label-warning'>"+mksatman[i][5]+"</label></td>";
//				else append += "<td><label id='lbl-"+mksatman[i][0]+"' class='label label-success'>"+mksatman[i][5]+"</label></td>";
				if(mksatman[i][4] == "Wajib")
				{
					if(mksatman[i][5] == "Ambil")
						append += "<td><div><input value='"+mksatman[i][0]+"' id='check-"+mksatman[i][0]+"' onclick='check(\""+mksatman[i][0]+"\")' class='check-mk mk-wajib' checked type='checkbox'></div></td>";
					else append += "<td><div><input value='"+mksatman[i][0]+"' id='check-"+mksatman[i][0]+"' onclick='check(\""+mksatman[i][0]+"\")' class='check-mk mk-wajib' type='checkbox'></div></td>";
					
				}
				else append += "<td id='td-"+mksatman[i][0]+"'><input style='display:none' value='"+mksatman[i][0]+"' class='check-mk' id='check-"+mksatman[i][0]+"' type='checkbox'></td>";				
				
			}
			append += td_relasisatman;
			
//			append += td_aksi;
			append += "</tr>";
			
			$('#mk-wajib').append(append);
			$('.check-mk').uniform();
			$('.check-mk').change();
		}
//		if(max > 0)
//		{
//			row = {
//				"idMKAlihjenjang" : idMKAlihjenjang,
//				"kodeMKAlihjenjang" : kodeMKAlihjenjang,
//				"namaMKAlihjenjang" : namaMKAlihjenjang,
//				"sksMKAlihjenjang" : sksMKAlihjenjang,
//				"relasiMKAlihjenjang" : relasialihjenjang,
//				"idMKSatMan" : idMKSatMan,
//				"kodeMKSatMan" : kodeMKSatMan,
//				"namaMKSatMan" : namaMKSatMan,
//				"sksMKSatMan" : sksMKSatMan,
////				"semesterMKSatMan" : semesterMKSatMan,
//				"sifatMKSatMan" : sifatMKSatMan,				
//				"relasiMKSatMan" : relasisatman,
//				"idKatalogAlihjenjang" : $('#idKatalog-hidden').val(),
//				"idKurikulum" : $('#idKurikulum-hidden').val()
//			};
//			if(mode == 1)
//				Rows.push(row);
//		}
		
//		console.log(Rows);
	}
	
//	check = function(id)
//	{
//		if($("#lbl-"+id).hasClass("label-success"))
//		{
//			$("#lbl-"+id).removeClass("label-success");
//			$("#lbl-"+id).addClass("label-warning");
//			$("#lbl-"+id).text("Ambil");
//        } else {
//        	$("#lbl-"+id).removeClass("label-warning");
//			$("#lbl-"+id).addClass("label-success");
//			$("#lbl-"+id).text("Bebas");
//        }
//	}

});