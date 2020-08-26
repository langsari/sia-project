var tambahkan;
$(document).ready(function(){
	
	$('#masterpage-katalog-kurikulum').masterPage(
	{
		detailFocusId: '#idRelasi',
		dataUrl: context_path+'modul/ekuivalensi/katalogsatman/jsonkatalogkurikulum',
		deleteUrl: context_path+'modul/ekuivalensi/katalogsatman/deletemanykatalogkurikulum',
		primaryKey: 'idKatalogSatMan',
        order: [[1,"asc"]],
        columnExclude:[0,4],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		cols: [
			/* idKatalogAlihjenjang */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idRelasi[]" value="'+data+'">';
				}
			},
			/* nmKatalog */
			{ "bVisible":    true },
			/* catatan */
			{ "bVisible":    true },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = '';
						action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>';
						var tmp = full[0].split(';');
						action += ' <button type="button" class="btn btn-success" onclick="manage_ekuivalensi(\''+tmp[1]+'\',\''+full[2]+'\')">Kelola</button>';
					return action;
				}
			}
		],
		filters:[{id:'#idKatalog-hidden', name:'idKatalog'},{id:'#idSatMan-hidden', name:'idSatMan'}]
	});
	
	$('#masterpage-modal-kurikulum').masterPage(
	{
		detailFocusId: '#idKurikulum',
		dataUrl: context_path+'modul/ekuivalensi/katalogsatman/jsonmodalkurikulum',
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
		filters: [{id:'#idKatalogSatMan-hidden', name:'idKatalogSatMan'}],
		callOnSelect:function(aData, options){
			manage_ekuivalensi(aData[0],aData[2]);
			$('#modal-kurikulum').modal("toggle");
		}
	});
	
	masterpage_to_masterkatalogkurikulum = function(idKatalogSatMan,idKatalog,nmKatalog,idSatMan)
	{
		$('#idKatalogSatMan-hidden').val(idKatalogSatMan);
		$('#idKatalog-hidden').val(idKatalog);
		$('#nmKatalog-hidden').val(nmKatalog);
		$('#idSatMan-hidden').val(idSatMan);
		$('#masterpage').hide();
		$("#masterpage-katalog-kurikulum").find('.dataTables_length select').change();
		$('#masterpage-katalog-kurikulum').show();
	}
	
	$('#tambah-katalog-kurikulum').click(function(){
		$("#masterpage-modal-kurikulum").find('.dataTables_length select').change();
		$('#modal-kurikulum').modal("show");
	});
	
	manage_ekuivalensi = function(idKurikulum,nmKurikulum){
		$("#idKurikulum-hidden").val(idKurikulum).trigger("change");
		$('#title-ekuivalensi-mk-alihjenjang').text("");
		$('#title-ekuivalensi-mk-alihjenjang').text("Matakuliah "+$('#nmKatalog-hidden').val());
		$('#title-ekuivalensi-mk-satman').text("");
		$('#title-ekuivalensi-mk-satman').text("Matakuliah "+nmKurikulum);
		
		$('#manage-ekuivalensi').show();
		$('#masterpage-katalog-kurikulum').hide();
		
		load_ekuivalensi_mk($("#idKatalog-hidden").val(),$("#idKurikulum-hidden").val());
		Rows = new Array();
		reset_input();
		$('#mk-ekuivalen').html("");
		console.log(Rows);
	}
	
	$('#masterpage-modal-mk-alihjenjang').masterPage(
	{
		detailFocusId: '#idMKAlihjenjang',
		dataUrl: context_path+'modul/ekuivalensi/katalogsatman/jsonmkalihjenjangbykatalog',
		primaryKey: 'idMKAlihjenjang',
        order: [[2,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		columnExclude:[0,1,6],
		cols: [
			/* idMKAlihjenjang */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					return '<input class="checkbox-data" type="checkbox" name="idMKAlihjenjang[]" value="'+data+'">';
				}
			},
			/* nmKatalog */
			{ "bVisible":    false },
			/* kodeMKAlihjenjang */
			{ "bVisible":    true },
			/* nmMKAlihjenjang */
			{ "bVisible":    true },
			/* jumlahSKS */
			{ "bVisible":    true },
			/* deskripsiMKAlihjenjang */
			{ "bVisible":    false },
			/* aMKAlihjenjangTerhapus */
			{ "bVisible":    false },
		],
		filters: [{id:'#idKatalog-hidden', name:'idKatalog'}],
		callOnSelect:function(aData, options){
			if(CekSelectedMK(0,aData[0]))
			{
				$('#modal-mk-alihjenjang').modal('toggle');
				var append = "";
				append += "<tr>";
				append += "<td id='td-alihjenjang-"+aData[0]+"' style='display:none'>"+aData[0]+"</td>";
				append += "<td>"+aData[2]+"</td>";
				append += "<td>"+aData[3]+"</td>";
				append += "<td>"+aData[4]+"</td>";
				$('#mk-alihjenjang-selected').append(append);
			}
			else 
			{
				alert('Matakuliah sudah dipilih');
				return false;
			}
		}
	});
	
	CekSelectedMK = function(mode,idMK)
	{		
		if(mode == 0)
		{
			if(document.getElementById('td-alihjenjang-'+idMK) != null)
				return false;
			else return true;
		}
		else
		{
			if(document.getElementById('td-satman-'+idMK) != null)
				return false;
			else return true;
		}
	}
	
	$('#masterpage-modal-mk-satman').masterPage(
	{
		detailFocusId: '#idMK',
		dataUrl: context_path+'modul/ekuivalensi/katalogsatman/jsonmkbykurikulum',
		primaryKey: 'idMK',
        order: [[2,"asc"]],
		editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		columnExclude:[0],
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
			/* tingkatPemb */
//			{ "bVisible":    true },
			/* sifatMK */
			{ "bVisible":    true, 
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = ' ';
					if(full[4] == "true")
						action += '<label class="label label-warning">Wajib</label>';
					else action += '<label class="label label-success">Pilihan</label>';
					return action;
				}
			}			
		],
		validationRules: {idMK:{required: true},namaM:{required: false}, sks:{required: true}},
		filters: [{id:'#idKurikulum-hidden', name:'idKurikulum'}],
		callOnSelect:function(aData, options){
			//------------Cek Prasyarat--------------
			eval("var tempData = {'idMK' : '"+aData[0]+"'};");
			$.ajax({
				url: context_path+"modul/ekuivalensi/katalogsatman/cekprasyarat",
				data : tempData,
				type : 'post',
				success: function(data)
				{
//					unblockUI($("#pesertadidik"));
					if(data.status=='ok')
					{
						if(data.data == 'true')
						{
							show_message('', "Matakuliah memiliki prasyarat", true);
//							alert("Matakuliah memiliki prasyarat");
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
			
			if(CekSelectedMK(1,aData[0]))
			{
				$('#modal-mk-satman').modal('toggle');
				var append = "";
				append += "<tr>";
				append += "<td id='td-satman-"+aData[0]+"' style='display:none'>"+aData[0]+"</td>";
				append += "<td>"+aData[1]+"</td>";
				append += "<td>"+aData[2]+"</td>";
				append += "<td>"+aData[3]+"</td>";
//				append += "<td>"+aData[4]+"</td>";
				if(aData[4] == "true")
				{
					aData[4] = "Wajib";
					append += "<td><label class='label label-warning'>"+aData[4]+"</label></td>";
				}				
				else 
				{	
					aData[4] = "Pilihan";
					append += "<td><label class='label label-success'>"+aData[4]+"</label></td>";
				}
				
			}
			else
			{
				alert('Matakuliah sudah dipilih');
				return false;
			}
			
			$('#mk-satman-selected').append(append);
		}
	});
	
	
	load_ekuivalensi_mk = function(idKatalogAlihjenjang, idKurikulum)
	{
		blockUI($('#manage-ekuivalensi'));
		eval("var tempData = {'idKatalogAlihjenjang' : '"+idKatalogAlihjenjang+"','idKurikulum' : '"+idKurikulum+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/katalogsatman/getlistmkekuivalen",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($('#manage-ekuivalensi'));
				if(data.status=='ok')
				{
					for(var i=0;i<data.data.length;i++)
					{
						var max = data.data[i].idMKAlihjenjang.length;
						if(max < data.data[i].idMKSatMan.length)
							max = data.data[i].idMKSatMan.length;
						var mkalihjenjang = [];
						var mksatman = [];
						var relasialihjenjang = data.data[i].relasiMKAlihjenjang;
						var relasisatman = data.data[i].relasiMKSatMan;
						for(var j=0;j<max;j++)
						{
							var td = [];
							if(j<data.data[i].idMKAlihjenjang.length)
							{
								td.push(data.data[i].idMKAlihjenjang[j]);
								td.push(data.data[i].kodeMKAlihjenjang[j]);
								td.push(data.data[i].namaMKAlihjenjang[j]);
								td.push(data.data[i].sksMKAlihjenjang[j]);
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
								mksatman.push(td);
							}
						}
						fill_tabel_mk(mkalihjenjang, mksatman, relasialihjenjang, relasisatman, 1, -1);
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
	}	
	
	getsatman = function(){
		eval("var tempData = {'idKatalogAlihjenjang' : '"+$('#idKatalog-hidden').val()+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/katalogsatman/getsatman",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				if(data.status=='ok')
				{
					$("#idSatMan-hidden").val(data.data).trigger("change");					
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
	
	$('#manage-ekuivalensi-to-masterpage-kurikulum').click(function(){
		$('#manage-ekuivalensi').hide();
		$('#masterpage-katalog-kurikulum').find('.dataTables_length select').change();
		$('#masterpage-katalog-kurikulum').show();
	});
	
	$('#katalog-kurikulum-to-masterpage').click(function(){
		$('#masterpage-katalog-kurikulum').hide();
		$('#masterpage').find('.dataTables_length select').change();
		$('#masterpage').show();
	})
	
	$('#tambah-mk-alihjenjang').click(function(){
		$('#masterpage-modal-mk-alihjenjang').find('.dataTables_length select').change();
		$('#modal-mk-alihjenjang').modal('show');
	});
	
	$('#tambah-mk-satman').click(function(){
		$('#masterpage-modal-mk-satman').find('.dataTables_length select').change();
		$('#modal-mk-satman').modal('show');
	});
	
	$('#dropdown-relasi-mk-alihjenjang').change(function(){
		if($('#dropdown-relasi-mk-alihjenjang').val()==0)
		{
			$('#textbox-relasi-mk-alihjenjang').show();
		}
		else $('#textbox-relasi-mk-alihjenjang').hide();
	});
	
	$('#dropdown-relasi-mk').change(function(){
		if($('#dropdown-relasi-mk').val()==0)
		{
			$('#textbox-relasi-mk').show();
		}
		else $('#textbox-relasi-mk').hide();
	});
	
	$('#add-ekuivalensi').click(function(){
		var mkalihjenjang = [];
		var mksatman = [];
		var relasialihjenjang;
		var relasisatman;
		
		$('#mk-alihjenjang-selected tr').each(function(){
			var td = [];
			$(this).find("td").each(function(){
				td.push($(this).text());
			});
			mkalihjenjang.push(td);
		});
		
		$('#mk-satman-selected tr').each(function(){
			var td = [];
			$(this).find("td").each(function(){
				td.push($(this).text());
			});
			mksatman.push(td);
		});
		
		if(mkalihjenjang.length == 0 || mksatman.length == 0)
		{
			alert('Silahkan masukkan matakuliah');
			return false;
		}
		
		if(mkalihjenjang.length > 1)
		{
			if($('#dropdown-relasi-mk-alihjenjang').val() == 1)
			{
				alert('Silahkan masukkan relasi matakuliah');
				return false;
			}
			else if($('#dropdown-relasi-mk-alihjenjang').val() == 0)
			{
				//cek validasi
				if(!validasi_relasi($('#textbox-relasi-mk-alihjenjang').val()))
				{
					alert("Relasi tidak valid");
					return false;
				}
			}
		}
		else if(mkalihjenjang.length == 1 && $('#dropdown-relasi-mk-alihjenjang').val() != 1)
		{
			alert('Relasi tidak valid');
			return false;
		}
		
		if(mksatman.length > 1)
		{
			if($('#dropdown-relasi-mk').val() == 1)
			{
				alert('Silahkan masukkan relasi matakuliah');
				return false;
			}
			else if($('#dropdown-relasi-mk').val() == 0)
			{
				//cek validasi
				if(!validasi_relasi($('#textbox-relasi-mk').val()))
				{
					alert("Relasi tidak valid");
					return false;
				}
			}
		}
		else if(mksatman.length == 1 && $('#dropdown-relasi-mk').val() != 1)
		{
			alert('Relasi tidak valid');
			return false;
		}
		
		if($('#dropdown-relasi-mk-alihjenjang').val() == 1)
			relasialihjenjang = '-';
		else if($('#dropdown-relasi-mk-alihjenjang').val() == 0)
			relasialihjenjang = $('#textbox-relasi-mk-alihjenjang').val();
		else relasialihjenjang = $('#dropdown-relasi-mk-alihjenjang').val();
		
		if($('#dropdown-relasi-mk').val() == 1)
			relasisatman = '-';
		else if($('#dropdown-relasi-mk').val() == 0)
			relasisatman = $('#textbox-relasi-mk').val();
		else relasisatman = $('#dropdown-relasi-mk').val();
		
		fill_tabel_mk(mkalihjenjang, mksatman, relasialihjenjang, relasisatman, 1, -1);
		reset_input();
	});
	
	$('#delete-mk').click(function(){
		reset_input();
	});
	
	var Rows = new Array();
	
	fill_tabel_mk = function(mkalihjenjang, mksatman, relasialihjenjang, relasisatman, mode, index)
	{
		var row = new Array();
		var idMKAlihjenjang = new Array();
		var kodeMKAlihjenjang = new Array();
		var namaMKAlihjenjang = new Array();
		var sksMKAlihjenjang = new Array();
		
		var idMKSatMan = new Array();
		var kodeMKSatMan = new Array();
		var namaMKSatMan = new Array();
		var sksMKSatMan = new Array();
		var sifatMKSatMan = new Array();
//		var semesterMKSatMan = new Array();
		
		var rows_length = Rows.length;
		if(mode == 0)
			rows_length = index;
		var max = mkalihjenjang.length;
		if(max < mksatman.length)
			max = mksatman.length;
		var selisih = Math.abs(mkalihjenjang.length-mksatman.length);
		for(var i=0;i<max;i++)
		{
			var append = "";
			var td_aksi = "";
			var td_relasialihjenjang = "";
			var td_relasisatman = "";
			
			append += "<tr>";
			
			if(i==0)
			{
				append += "<td class='td-no' rowspan='"+max+"'>"+(rows_length+1)+"</td>";
				if(max > 1)
				{
					td_relasialihjenjang = "<td rowspan='"+max+"'>"+relasialihjenjang+"</td>";
					td_relasisatman = "<td rowspan='"+max+"'>"+relasisatman+"</td>";
					td_aksi = "<td rowspan='"+max+"'><button class='btn btn-danger' onclick='hapus_mk_ekuivalen("+rows_length+")'>Hapus</button></td>";
				}
				else
				{
					td_relasialihjenjang = "<td>"+relasialihjenjang+"</td>";
					td_relasisatman = "<td rowspan='"+max+"'>"+relasisatman+"</td>";
					td_aksi = "<td><button class='btn btn-danger' onclick='hapus_mk_ekuivalen("+rows_length+")'>Hapus</button></td>";
				}
			}
			
			if(i+1 == mkalihjenjang.length)
			{
				idMKAlihjenjang.push(mkalihjenjang[i][0]);
				kodeMKAlihjenjang.push(mkalihjenjang[i][1]);
				namaMKAlihjenjang.push(mkalihjenjang[i][2]);
				sksMKAlihjenjang.push(mkalihjenjang[i][3]);
				append += "<td rowspan='"+(max-mkalihjenjang.length+1)+"'>"+mkalihjenjang[i][1]+"</td>";
				append += "<td rowspan='"+(max-mkalihjenjang.length+1)+"'>"+mkalihjenjang[i][2]+"</td>";
				append += "<td rowspan='"+(max-mkalihjenjang.length+1)+"'>"+mkalihjenjang[i][3]+"</td>";
			}
			else if(i<mkalihjenjang.length)
			{
				idMKAlihjenjang.push(mkalihjenjang[i][0]);
				kodeMKAlihjenjang.push(mkalihjenjang[i][1]);
				namaMKAlihjenjang.push(mkalihjenjang[i][2]);
				sksMKAlihjenjang.push(mkalihjenjang[i][3]);
				append += "<td>"+mkalihjenjang[i][1]+"</td>";
				append += "<td>"+mkalihjenjang[i][2]+"</td>";
				append += "<td>"+mkalihjenjang[i][3]+"</td>";
			}
			
			append += td_relasialihjenjang;
			
			if(i+1 == mksatman.length)
			{
				idMKSatMan.push(mksatman[i][0]);
				kodeMKSatMan.push(mksatman[i][1]);
				namaMKSatMan.push(mksatman[i][2]);
				sksMKSatMan.push(mksatman[i][3]);
//				semesterMKSatMan.push(mksatman[i][4]);
				sifatMKSatMan.push(mksatman[i][4]);
				append += "<td rowspan='"+(max-mksatman.length+1)+"'>"+mksatman[i][1]+"</td>";
				append += "<td rowspan='"+(max-mksatman.length+1)+"'>"+mksatman[i][2]+"</td>";
				append += "<td rowspan='"+(max-mksatman.length+1)+"'>"+mksatman[i][3]+"</td>";
//				append += "<td rowspan='"+(max-mksatman.length+1)+"'>"+mksatman[i][4]+"</td>";
				if(mksatman[i][4] == "Wajib")
					append += "<td rowspan='"+(max-mksatman.length+1)+"'><label class='label label-warning'>"+mksatman[i][4]+"</label></td>";
				else append += "<td rowspan='"+(max-mksatman.length+1)+"'><label class='label label-success'>"+mksatman[i][4]+"</label></td>";
			}
			else if(i<mksatman.length)
			{
				idMKSatMan.push(mksatman[i][0]);
				kodeMKSatMan.push(mksatman[i][1]);
				namaMKSatMan.push(mksatman[i][2]);
				sksMKSatMan.push(mksatman[i][3]);
//				semesterMKSatMan.push(mksatman[i][4]);
				sifatMKSatMan.push(mksatman[i][4]);
				append += "<td>"+mksatman[i][1]+"</td>";
				append += "<td>"+mksatman[i][2]+"</td>";
				append += "<td>"+mksatman[i][3]+"</td>";
//				append += "<td>"+mksatman[i][4]+"</td>";
				if(mksatman[i][4] == "Wajib")
					append += "<td><label class='label label-warning'>"+mksatman[i][4]+"</label></td>";
				else append += "<td><label class='label label-success'>"+mksatman[i][4]+"</label></td>";
			}
			append += td_relasisatman;
			
			append += td_aksi;
			append += "</tr>";
			
			$('#mk-ekuivalen').append(append);
		}
		if(max > 0)
		{
			row = {
				"idMKAlihjenjang" : idMKAlihjenjang,
				"kodeMKAlihjenjang" : kodeMKAlihjenjang,
				"namaMKAlihjenjang" : namaMKAlihjenjang,
				"sksMKAlihjenjang" : sksMKAlihjenjang,
				"relasiMKAlihjenjang" : relasialihjenjang,
				"idMKSatMan" : idMKSatMan,
				"kodeMKSatMan" : kodeMKSatMan,
				"namaMKSatMan" : namaMKSatMan,
				"sksMKSatMan" : sksMKSatMan,
//				"semesterMKSatMan" : semesterMKSatMan,
				"sifatMKSatMan" : sifatMKSatMan,				
				"relasiMKSatMan" : relasisatman,
				"idKatalogAlihjenjang" : $('#idKatalog-hidden').val(),
				"idKurikulum" : $('#idKurikulum-hidden').val()
			};
			if(mode == 1)
				Rows.push(row);
		}
		
		console.log(Rows);
	}
	
	reset_input = function(){
		$('#mk-alihjenjang-selected').html("");
		$('#mk-satman-selected').html("");
		document.getElementById('dropdown-relasi-mk-alihjenjang').selectedIndex = 0;
		$('#textbox-relasi-mk-alihjenjang').val('');
		$('#textbox-relasi-mk-alihjenjang').hide();
	}
	
	hapus_mk_ekuivalen = function(id)
	{
		Rows.splice(id,1);
		$('#mk-ekuivalen').html("");
		for(var i=0;i<Rows.length;i++)
		{
			var max = Rows[i].idMKAlihjenjang.length;
			if(max < Rows[i].idMKSatMan.length)
				max = Rows[i].idMKSatMan.length;
			var mkalihjenjang = [];
			var mksatman = [];
			var relasialihjenjang = Rows[i].relasiMKAlihjenjang;
			var relasisatman = Rows[i].relasiMKSatMan;
			for(var j=0;j<max;j++)
			{
				var td = [];
				if(j<Rows[i].idMKAlihjenjang.length)
				{
					td.push(Rows[i].idMKAlihjenjang[j]);
					td.push(Rows[i].kodeMKAlihjenjang[j]);
					td.push(Rows[i].namaMKAlihjenjang[j]);
					td.push(Rows[i].sksMKAlihjenjang[j]);
					mkalihjenjang.push(td);
				}
				td = [];
				if(j<Rows[i].idMKSatMan.length)
				{
					td.push(Rows[i].idMKSatMan[j]);
					td.push(Rows[i].kodeMKSatMan[j]);
					td.push(Rows[i].namaMKSatMan[j]);
					td.push(Rows[i].sksMKSatMan[j]);
//					td.push(Rows[i].semesterMKSatMan[j]);
					td.push(Rows[i].sifatMKSatMan[j]);
					mksatman.push(td);
				}
			}
			fill_tabel_mk(mkalihjenjang, mksatman, relasialihjenjang, relasisatman, 0, i);
		}
	}
	
	$('#simpan-mk-ekuivalen').click(function(){
		if(Rows.length == 0)
		{
			var row = new Array();
			var idMKAlihjenjang = new Array();
			var namaMKAlihjenjang = new Array();
			var kodeMKAlihjenjang = new Array();
			var sksMKAlihjenjang = new Array();
			
			var idMKSatMan = new Array();
			var kodeMKSatMan = new Array();
			var namaMKSatMan = new Array();
			var sksMKSatMan = new Array();
			var semesterMKSatMan = new Array();
			var sifatMKSatMan = new Array();
			
			row = {
					"idMKAlihjenjang" : idMKAlihjenjang,
					"kodeMKAlihjenjang" : kodeMKAlihjenjang,
					"namaMKAlihjenjang" : namaMKAlihjenjang,
					"sksMKAlihjenjang" : sksMKAlihjenjang,
					"relasiMKAlihjenjang" : "",
					"idMKSatMan" : idMKSatMan,
					"kodeMKSatMan" : kodeMKSatMan,
					"namaMKSatMan" : namaMKSatMan,
					"sksMKSatMan" : sksMKSatMan,
//					"semesterMKSatMan" : semesterMKSatMan,
					"sifatMKSatMan" : sifatMKSatMan,
					"relasiMKSatMan" : "",
					"idKatalogAlihjenjang" : $('#idKatalog-hidden').val(),
					"idKurikulum" : $('#idKurikulum-hidden').val()
			};
			Rows.push(row);			
		}
		console.log(Rows);
		var json_stringify = JSON.stringify(Rows);
		blockUI($("#manage-ekuivalensi"));
		$.ajax({
			url: context_path+"modul/ekuivalensi/katalogsatman/saveekuivalen",
			data : json_stringify,
			type : 'post',
			contentType: "application/json", //this is required for spring 3 - ajax to work (at least for me)
		    success: function(data)
			{
				unblockUI($("#manage-ekuivalensi"));
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
	});
	
	validasi_relasi = function(tmp)
	{
		tmp = tmp.replace(/\s+/g,"");
		var jumlahBuka = 0;
		var jumlahTutup = 0;
		//ascii huruf a
		var min = 97;
		//ascii huruf z
		var max = 122;
		if(tmp < 3)
			return false;
		for(var i=0;i<tmp.length;i++)
		{
			var angka = tmp.charCodeAt(i);
			if(tmp[i] == '(')
			{
				if(i+1 < tmp.length)
				{
					if(tmp[i+1] == '^' || tmp[i+1] == '/')
						return false;
				}
				else return false;
				jumlahBuka++;
			}
			else if(tmp[i] == ')')
			{
				if(i+1 < tmp.length)
				{
					angka = tmp.charCodeAt(i+1);
					if(angka >= min && angka <= max)
						return false;
				}
				else if(i-1 >= 0)
				{
					angka = tmp.charCodeAt(i-1);
					if(!((angka >= min && angka <= max) || tmp[i-1] == ')'))
						return false
				}
				jumlahTutup++;
			}
			else if(angka >= min && angka <= max)
			{
				if(i+1 < tmp.length)
				{
					angka = tmp.charCodeAt(i+1);
					if((angka >= min && angka <= max) || tmp[i+1] == '(')
						return false;					
				}
			}
			else if(tmp[i] == '^' || tmp[i] =='/')
			{
				if(i+1 < tmp.length)
				{
					if(tmp[i+1] == '/' || tmp[i+1] == '/' || tmp[i+1] == ')')
						return false;
				}					
				else return false;
			}
			else
			{
				return false;
			}
		}
		if(jumlahBuka != jumlahTutup)
			return false;
		return true;
	}
	
	$('#info').click(function(){
		$('#modal-info-relasi').modal("toggle");
	});
	
});