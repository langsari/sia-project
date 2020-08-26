var tambahkan;
$(document).ready(function(){
	$('#masterpage').masterPage(
	{
		detailFocusId: '#idKurikulum',
		dataUrl: context_path+'modul/ekuivalensi/ekuivalensi/json',
		deleteUrl: context_path+'modul/ekuivalensi/ekuivalensi/deletemany',
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
			{ "bVisible":    true, bSortable: false, },
			/* kurikulumBaru */
			{ "bVisible":    true, bSortable: false, },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = ' <button type="button" class="btn btn-danger deleterow">Hapus</button>';
					var tmp = full[0].split(";");
					
					console.log(tmp[0]);
					
					action += ' <button type="button" class="btn btn-success" onclick="masterpage_to_manage_ekuivalensi(\''+tmp[0]+'\',\''+tmp[1]+'\',\''+full[1]+'\',\''+full[2]+'\')">Kelola Ekuivalensi</button>';
					return action;
				}
			}
		]
	});	
	
	$('#masterpage-kurikulum-lama').masterPage(
	{
		detailFocusId: '#idKurikulum',
		dataUrl: context_path+'modul/ekuivalensi/ekuivalensi/jsonkurikulum',
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
//		validationRules: {idMKLuar:{required: false},idPedomanEkuivalensi:{required: true}, nmMK:{required: true}, sks:{required: false}, deskripsiMKLuar:{required: false}, aMKLuarTerhapus:{required:false}},
//		filters: [{id:'#filter', name:'satMan'}],
		callOnSelect:function(aData, options){
			if(aData[0] == $("#idKurikulumBaru").val())
			{
				alert("Silahkan pilih kurikulum yang berbeda");
				return false;
			}
			$('#modal-kurikulum-lama').modal("toggle");			
			$("#idKurikulumLama").val(aData[0]);
			$("#kurikulumLama").val(aData[2]);
		}
	});
	
	$('#master-to-add-ekuivalensi').click(function(){
		$('#masterpage').hide();
		$('#idKurikulumLama').val("");
		$('#kurikulumLama').val("");
		$('#idKurikulumBaru').val("");
		$('#kurikulumBaru').val("");
		$('#master-add-ekuivalensi').show();
	});
	
	
	$('#kurikulumLama').click(function(){
		$('#modal-kurikulum-lama').modal('show');
	});
	
	$('#masterpage-kurikulum-baru').masterPage(
	{
		detailFocusId: '#idKurikulum',
		dataUrl: context_path+'modul/ekuivalensi/ekuivalensi/jsonkurikulum',
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
//				validationRules: {idMKLuar:{required: false},idPedomanEkuivalensi:{required: true}, nmMK:{required: true}, sks:{required: false}, deskripsiMKLuar:{required: false}, aMKLuarTerhapus:{required:false}},
//				filters: [{id:'#filter', name:'satMan'}],
		callOnSelect:function(aData, options){
			if(aData[0] == $("#idKurikulumLama").val())
			{
				alert("Silahkan pilih kurikulum yang berbeda");
				return false;
			}
			$('#modal-kurikulum-baru').modal("toggle");
			$("#idKurikulumBaru").val(aData[0]);
			$("#kurikulumBaru").val(aData[2]);
		}
	});
	
	$('#kurikulumBaru').click(function(){
		$('#modal-kurikulum-baru').modal('show');
	});
	
	$('#kelola-ekuivalensi-to-masterpage').click(function(){
		$('#masterpage').show();
		$('#master-add-ekuivalensi').hide();
	});
	
	$('#kelola-ekuivalensi').click(function(){
		if($('#idKurikulumBaru').val() == '' || $('#idKurikulumLama').val() == '')
		{
			alert('Silahkan pilih kurikulum');
		}
		else
		{
			$('#master-add-ekuivalensi').hide();
			manage_ekuivalensi($('#idKurikulumLama').val(),$('#idKurikulumBaru').val());
		}
	})
	
	masterpage_to_manage_ekuivalensi = function(idKurikulumLama, idKurikulumBaru, nmKurikulumLama, nmKurikulumBaru)
	{
		$('#title-kurikulum-lama').text("");
		$('#title-kurikulum-baru').text("");
		$('#title-kurikulum-lama').text(nmKurikulumLama);
		$('#title-kurikulum-baru').text(nmKurikulumBaru);
		$("#idKurikulumLama").val(idKurikulumLama);
		$("#idKurikulumBaru").val(idKurikulumBaru);
		$('#masterpage').hide();
		manage_ekuivalensi(idKurikulumLama, idKurikulumBaru);
	}
	
	manage_ekuivalensi = function(idKurikulumLama, idKurikulumBaru)
	{
		$('#manage-ekuivalensi').show();
		load_ekuivalensi_mk(idKurikulumLama, idKurikulumBaru);
		Rows = new Array();
		reset_input();
		$('#mk-ekuivalen').html("");
	}
	
	$('#masterpage-mk-lama').masterPage(
	{
		detailFocusId: '#idKurikulumLama',
		dataUrl: context_path+'modul/ekuivalensi/ekuivalensi/getmk',
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
					if(full[4] == "true")
						return "<label class='label label-warning'>Wajib</label>";
					else return "<label class='label label-success'>Pilihan</label>";
				}
			}
		],
		validationRules: {idMK:{required: true},namaM:{required: false}, sks:{required: true}},
		filters: [{id:'#idKurikulumLama', name:'idKurikulum'}],
		callOnSelect:function(aData, options){
			if(CekSelectedMK(0,aData[0]))
			{
				$('#modal-mk-lama').modal('toggle');
				var append = "";
				append += "<tr>";
				append += "<td id='td-mklama-"+aData[0]+"' style='display:none'>"+aData[0]+"</td>";
				append += "<td>"+aData[1]+"</td>";
				append += "<td>"+aData[2]+"</td>";
				append += "<td>"+aData[3]+"</td>";
				if(aData[4] == "true")
					aData[4] = "<label class='label label-warning'>Wajib</label>";
				else aData[4] = "<label class='label label-success'>Pilihan</label>";
				append += "<td>"+aData[4]+"</td>";
				$('#mk-lama-selected').append(append);
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
			if(document.getElementById('td-mklama-'+idMK) != null)
				return false;
			else return true;
		}
		else
		{
			if(document.getElementById('td-mkbaru-'+idMK) != null)
				return false;
			else return true;
		}
	}
	
	$('#tambah-mk-lama').click(function(){
		$('#masterpage-mk-lama').find('.dataTables_length select').change();
		$('#modal-mk-lama').modal('show');
	});
	
	$('#masterpage-mk-baru').masterPage(
	{
		detailFocusId: '#idKurikulumBaru',
		dataUrl: context_path+'modul/ekuivalensi/ekuivalensi/getmk',
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
					if(full[4] == "true")
						return "<label class='label label-warning'>Wajib</label>";
					else return "<label class='label label-success'>Pilihan</label>";
				}
			}
		],
		validationRules: {idMK:{required: true},namaM:{required: false}, sks:{required: true}},
		filters: [{id:'#idKurikulumBaru', name:'idKurikulum'}],
		callOnSelect:function(aData, options){
			if(CekSelectedMK(1,aData[0]))
			{
				$('#modal-mk-baru').modal('toggle');
				var append = "";
				append += "<tr>";
				append += "<td id='td-mkbaru-"+aData[0]+"' style='display:none'>"+aData[0]+"</td>";
				append += "<td>"+aData[1]+"</td>";
				append += "<td>"+aData[2]+"</td>";
				append += "<td>"+aData[3]+"</td>";
				if(aData[4] == "true")
					aData[4] = "<label class='label label-warning'>Wajib</label>";
				else aData[4] = "<label class='label label-success'>Pilihan</label>";
				append += "<td>"+aData[4]+"</td>";
				$('#mk-baru-selected').append(append);
			}
			else 
			{
				alert('Matakuliah sudah dipilih');
				return false;
			}
		}
	});
	
	$('#dropdown-relasi-mk-baru').change(function(){
		if($('#dropdown-relasi-mk-baru').val()==0)
		{
			$('#textbox-relasi-mk-baru').show();
		}
		else $('#textbox-relasi-mk-baru').hide();
	});
	
	$('#dropdown-relasi-mk-lama').change(function(){
		if($('#dropdown-relasi-mk-lama').val()==0)
		{
			$('#textbox-relasi-mk-lama').show();
		}
		else $('#textbox-relasi-mk-lama').hide();
	});
	
	$('#tambah-mk-baru').click(function(){
		$('#masterpage-mk-baru').find('.dataTables_length select').change();
		$('#modal-mk-baru').modal('show');
	});
	
	$('#add-ekuivalensi').click(function(){
		var mklama = [];
		var mkbaru = [];
		var relasilama;
		var relasibaru;
		
		$('#mk-baru-selected tr').each(function(){
			var td = [];
			$(this).find("td").each(function(){
				td.push($(this).text());
			});
			mkbaru.push(td);
		});
		
		$('#mk-lama-selected tr').each(function(){
			var td = [];
			$(this).find("td").each(function(){
				td.push($(this).text());
			});
			mklama.push(td);
		});
		
		if(mklama.length == 0 || mkbaru.length == 0)
		{
			alert('Silahkan masukkan matakuliah');
			return false;
		}
		
		if(mklama.length > 1)
		{
			if($('#dropdown-relasi-mk-lama').val() == 1)
			{
				alert('Silahkan masukkan relasi matakuliah');
				return false;
			}
			else if($('#dropdown-relasi-mk-lama').val() == 0)
			{
				//cek validasi
				if(!validasi_relasi($('#textbox-relasi-mk-lama').val()))
				{
					alert("Relasi tidak valid");
					return false;
				}
			}
		}
		else if(mklama.length == 1 && $('#dropdown-relasi-mk-lama').val() != 1)
		{
			alert('Relasi tidak valid');
			return false;
		}
		
		if(mkbaru.length > 1)
		{
			if($('#dropdown-relasi-mk-baru').val() == 1)
			{
				alert('Silahkan masukkan relasi matakuliah');
				return false;
			}
			else if($('#dropdown-relasi-mk-baru').val() == 0)
			{
				//cek validasi
				if(!validasi_relasi($('#textbox-relasi-mk-baru').val()))
				{
					alert("Relasi tidak valid");
					return false;
				}
			}
		}
		else if(mkbaru.length == 1 && $('#dropdown-relasi-mk-baru').val() != 1)
		{
			alert('Relasi tidak valid');
			return false;
		}
		
		if($('#dropdown-relasi-mk-lama').val() == 1)
			relasilama = '-';
		else if($('#dropdown-relasi-mk-lama').val() == 0)
			relasilama = $('#textbox-relasi-mk-lama').val();
		else relasilama = $('#dropdown-relasi-mk-lama').val();
		
		if($('#dropdown-relasi-mk-baru').val() == 1)
			relasibaru = '-';
		else if($('#dropdown-relasi-mk-baru').val() == 0)
			relasibaru = $('#textbox-relasi-mk-baru').val();
		else relasibaru = $('#dropdown-relasi-mk-baru').val();
		
		fill_tabel_mk(mklama,mkbaru,relasilama,relasibaru, 1, -1);
		reset_input();
	});
	
	var Rows = new Array();
	
	
	fill_tabel_mk = function(mklama, mkbaru, relasilama, relasibaru, mode, index)
	{
		var row = new Array();
		var idMKLama = new Array();
		var kodeMKLama = new Array();
		var namaMKLama = new Array();
		var sksMKLama = new Array();
		var sifatMKLama = new Array();
		
		var idMKBaru = new Array();
		var kodeMKBaru = new Array();
		var namaMKBaru = new Array();
		var sksMKBaru = new Array();
		var sifatMKBaru = new Array();
		
		var rows_length = Rows.length;
		if(mode == 0)
			rows_length = index;
		var max = mklama.length;
		if(max < mkbaru.length)
			max = mkbaru.length;
		var selisih = Math.abs(mklama.length-mkbaru.length);
		for(var i=0;i<max;i++)
		{
			var append = "";
			var td_aksi = "";
			var td_relasibaru = "";
			var td_relasilama = "";
			
			append += "<tr>";
			
			if(i==0)
			{
				append += "<td class='td-no' rowspan='"+max+"'>"+(rows_length+1)+"</td>";
				if(max > 1)
				{
					td_relasilama = "<td rowspan='"+max+"'>"+relasilama+"</td>";
					td_relasibaru = "<td rowspan='"+max+"'>"+relasibaru+"</td>";
					td_aksi = "<td rowspan='"+max+"'><button class='btn btn-danger' onclick='hapus_mk_ekuivalen("+rows_length+")'>Hapus</button></td>";
				}
				else
				{
					td_relasilama = "<td>"+relasilama+"</td>";
					td_relasibaru = "<td>"+relasibaru+"</td>";
					td_aksi = "<td><button class='btn btn-danger' onclick='hapus_mk_ekuivalen("+rows_length+")'>Hapus</button></td>";
				}
			}
			
			if(i+1 == mklama.length)
			{
				idMKLama.push(mklama[i][0]);
				kodeMKLama.push(mklama[i][1]);
				namaMKLama.push(mklama[i][2]);
				sksMKLama.push(mklama[i][3]);
				sifatMKLama.push(mklama[i][4]);
				append += "<td rowspan='"+(max-mklama.length+1)+"'>"+mklama[i][1]+"</td>";
				append += "<td rowspan='"+(max-mklama.length+1)+"'>"+mklama[i][2]+"</td>";
				append += "<td rowspan='"+(max-mklama.length+1)+"'>"+mklama[i][3]+"</td>";
				if(mklama[i][4] == "Wajib")
					append += "<td rowspan='"+(max-mklama.length+1)+"'><label class='label label-warning'>"+mklama[i][4]+"</label></td>";
				else append += "<td rowspan='"+(max-mklama.length+1)+"'><label class='label label-success'>"+mklama[i][4]+"</label></td>";
			}
			else if(i<mklama.length)
			{
				idMKLama.push(mklama[i][0]);
				kodeMKLama.push(mklama[i][1]);
				namaMKLama.push(mklama[i][2]);
				sksMKLama.push(mklama[i][3]);
				sifatMKLama.push(mklama[i][4]);
				append += "<td>"+mklama[i][1]+"</td>";
				append += "<td>"+mklama[i][2]+"</td>";
				append += "<td>"+mklama[i][3]+"</td>";
				if(mklama[i][4] == "Wajib")
					append += "<td><label class='label label-warning'>"+mklama[i][4]+"</label></td>";
				else append += "<td><label class='label label-success'>"+mklama[i][4]+"</label></td>";
			}
			
			append += td_relasilama;
			
			if(i+1 == mkbaru.length)
			{
				idMKBaru.push(mkbaru[i][0]);
				kodeMKBaru.push(mkbaru[i][1]);
				namaMKBaru.push(mkbaru[i][2]);
				sksMKBaru.push(mkbaru[i][3]);
				sifatMKBaru.push(mkbaru[i][4]);
				append += "<td rowspan='"+(max-mkbaru.length+1)+"'>"+mkbaru[i][1]+"</td>";
				append += "<td rowspan='"+(max-mkbaru.length+1)+"'>"+mkbaru[i][2]+"</td>";
				append += "<td rowspan='"+(max-mkbaru.length+1)+"'>"+mkbaru[i][3]+"</td>";
				if(mkbaru[i][4] == "Wajib")
					append += "<td rowspan='"+(max-mkbaru.length+1)+"'><label class='label label-warning'>"+mkbaru[i][4]+"</label></td>";
				else append += "<td rowspan='"+(max-mkbaru.length+1)+"'><label class='label label-success'>"+mkbaru[i][4]+"</label></td>";
			}
			else if(i<mkbaru.length)
			{
				idMKBaru.push(mkbaru[i][0]);
				kodeMKBaru.push(mkbaru[i][1]);
				namaMKBaru.push(mkbaru[i][2]);
				sksMKBaru.push(mkbaru[i][3]);
				sifatMKBaru.push(mkbaru[i][4]);
				append += "<td>"+mkbaru[i][1]+"</td>";
				append += "<td>"+mkbaru[i][2]+"</td>";
				append += "<td>"+mkbaru[i][3]+"</td>";
				if(mkbaru[i][4] == "Wajib")
					append += "<td><label class='label label-warning'>"+mkbaru[i][4]+"</label></td>";
				else append += "<td><label class='label label-success'>"+mkbaru[i][4]+"</label></td>";
			}
			
			append += td_relasibaru;
			append += td_aksi;
			append += "</tr>";
			
			$('#mk-ekuivalen').append(append);
		}
		if(max > 0)
		{
			row = {
				"idMKLama" : idMKLama,
				"kodeMKLama" : kodeMKLama,
				"namaMKLama" : namaMKLama,
				"sksMKLama" : sksMKLama,
				"sifatMKLama" : sifatMKLama,
				"relasiMKLama" : relasilama,
				"idMKBaru" : idMKBaru,
				"kodeMKBaru" : kodeMKBaru,
				"namaMKBaru" : namaMKBaru,
				"sksMKBaru" : sksMKBaru,
				"sifatMKBaru" : sifatMKBaru,
				"relasiMKBaru" : relasibaru,
				"idKurikulumLama" : $('#idKurikulumLama').val(),
				"idKurikulumBaru" : $('#idKurikulumBaru').val()
			};
			if(mode == 1)
				Rows.push(row);
		}
		console.log(Rows);
	}
	
	$('#delete-mk').click(function(){
		reset_input();
	});
	
	reset_input = function(){
		$('#mk-baru-selected').html("");
		$('#mk-lama-selected').html("");
		document.getElementById('dropdown-relasi-mk-baru').selectedIndex = 0;
		document.getElementById('dropdown-relasi-mk-lama').selectedIndex = 0;
		$('#textbox-relasi-mk-baru').val('');
		$('#textbox-relasi-mk-lama').val('');
		$('#textbox-relasi-mk-baru').hide();
		$('#textbox-relasi-mk-lama').hide();
	}
	
	validasi_relasi = function(tmp)
	{
//		var tmp = $('#textbox-relasi').val();
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
			else return false;
		}
		if(jumlahBuka != jumlahTutup)
			return false;
		return true;
	}
	
	$('#manage-ekuivalensi-to-manage-pekuivalensi').click(function(){
		$('#manage-ekuivalensi').hide();
		$('#masterpage').show();
		Rows = new Array();
		console.log(Rows);
	});
	
	$('#simpan-mk-ekuivalen').click(function(){
		if(Rows.length == 0)
		{
			var row = new Array();
			var idMKLama = new Array();
			var kodeMKLama = new Array();
			var namaMKLama = new Array();
			var sksMKLama = new Array();
			var sifatMKLama = new Array();
			
			var idMKBaru = new Array();
			var kodeMKBaru = new Array();
			var namaMKBaru = new Array();
			var sksMKBaru = new Array();
			var sifatMKBaru = new Array();
			
			row = {
					"idMKLama" : idMKLama,
					"kodeMKLama" : kodeMKLama,
					"namaMKLama" : namaMKLama,
					"sksMKLama" : sksMKLama,
					"sifatMKLama" : sifatMKLama,
					"relasiMKLama" : "",
					"idMKBaru" : idMKBaru,
					"kodeMKBaru" : kodeMKBaru,
					"namaMKBaru" : namaMKBaru,
					"sksMKBaru" : sksMKBaru,
					"sifatMKBaru" : sifatMKBaru,
					"relasiMKBaru" : "",
					"idKurikulumLama" : $('#idKurikulumLama').val(),
					"idKurikulumBaru" : $('#idKurikulumBaru').val()
			};
			Rows.push(row);
		}
		
		var json_stringify = JSON.stringify(Rows);
		blockUI($("#manage-ekuivalensi"));
		$.ajax({
			url: context_path+"modul/ekuivalensi/ekuivalensi/saveekuivalen",
			data : json_stringify,
			type : 'post',
			contentType: "application/json", //this is required for spring 3 - ajax to work (at least for me)
		    success: function(data)
			{
				unblockUI($("#manage-ekuivalensi"));
				if(data.status=='ok')
				{
					console.log(data);
					show_message("Sukses", data.message);
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
	});
	
	load_ekuivalensi_mk = function(idKurikulumLama, idKurikulumBaru)
	{
		blockUI($('#manage-ekuivalensi'));
		eval("var tempData = {'idKurikulumLama' : '"+idKurikulumLama+"', 'idKurikulumBaru' : '"+idKurikulumBaru+"'};");
		$.ajax({
			url: context_path+"modul/ekuivalensi/ekuivalensi/getlistmkekuivalen",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($('#manage-ekuivalensi'));
				if(data.status=='ok')
				{
					if(data.data != null)
					{
						for(var i=0;i<data.data.length;i++)
						{
							var max = data.data[i].idMKLama.length;
							if(max < data.data[i].idMKBaru.length)
								max = data.data[i].idMKBaru.length;
							var mklama = [];
							var mkbaru = [];
							var relasilama = data.data[i].relasiMKLama;
							var relasibaru = data.data[i].relasiMKBaru;
							for(var j=0;j<max;j++)
							{
								var td = [];
								if(j<data.data[i].idMKLama.length)
								{
									td.push(data.data[i].idMKLama[j]);
									td.push(data.data[i].kodeMKLama[j]);
									td.push(data.data[i].namaMKLama[j]);
									td.push(data.data[i].sksMKLama[j]);
									td.push(data.data[i].sifatMKLama[j]);
									mklama.push(td);
								}
								td = [];
								if(j<data.data[i].idMKBaru.length)
								{
									td.push(data.data[i].idMKBaru[j]);
									td.push(data.data[i].kodeMKBaru[j]);
									td.push(data.data[i].namaMKBaru[j]);
									td.push(data.data[i].sksMKBaru[j]);
									td.push(data.data[i].sifatMKBaru[j]);
									mkbaru.push(td);
								}
							}
							fill_tabel_mk(mklama, mkbaru, relasilama, relasibaru, 1, -1);
						}
					}					
				}
				else if(data.status=='expired')
				{ document.location=data.message; }
				else
				{ 
					show_message('Error', data.message,true);
					setTimeout(function(){
                        location.reload();
                   }, 3000);
				}
			},
			error: $.ajaxErrorHandler,
			dataType : 'json'
		});
	}
	
	hapus_mk_ekuivalen = function(id)
	{
		Rows.splice(id,1);
		$('#mk-ekuivalen').html("");
		for(var i=0;i<Rows.length;i++)
		{
			var max = Rows[i].idMKLama.length;
			if(max < Rows[i].idMKBaru.length)
				max = Rows[i].idMKBaru.length;
			var mklama = [];
			var mkbaru = [];
			var relasilama = Rows[i].relasiMKLama;
			var relasibaru = Rows[i].relasiMKBaru;
			for(var j=0;j<max;j++)
			{
				var td = [];
				if(j<Rows[i].idMKLama.length)
				{
					td.push(Rows[i].idMKLama[j]);
					td.push(Rows[i].kodeMKLama[j]);
					td.push(Rows[i].namaMKLama[j]);
					td.push(Rows[i].sksMKLama[j]);
					td.push(Rows[i].sifatMKLama[j]);
					mklama.push(td);
				}
				td = [];
				if(j<Rows[i].idMKBaru.length)
				{
					td.push(Rows[i].idMKBaru[j]);
					td.push(Rows[i].kodeMKBaru[j]);
					td.push(Rows[i].namaMKBaru[j]);
					td.push(Rows[i].sksMKBaru[j]);
					td.push(Rows[i].sifatMKBaru[j]);
					mkbaru.push(td);
				}
			}
			fill_tabel_mk(mklama, mkbaru, relasilama, relasibaru, 0, i);
		}
	}
	
	$('.info').click(function(){
		$('#modal-info-relasi').modal("toggle");
	});
});