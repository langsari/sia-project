var ambil = function(nomor)
{
	blockUI($("#masterpage"));
	eval("var tempData = {'idPemb' : '"+$('#select-'+nomor).val()+"','idTglSmt':'"+idTglSmt+"'}");
	$.ajax({
		url: context_path+"modul/pembelajaran/karturencanastudi/ambilmk",
		data : tempData,
		type : 'post',
		success: function(data)
		{
			unblockUI($("#masterpage"));
			if(data.status=='ok')
			{
				show_message("Sukses", data.message);
				$('#tableKrs tbody').html('');
				sksTerambil =0
				for(i=0;i<data.data.listKrs.length;i++)
				{
					sksTerambil += data.data.listKrs[i].pemb.mk.jumlahSKS;
					$('#tableKrs tbody').append(
							"<tr " 
							+ 
							(tingkat==null?'':
								(data.data.listSatManMK[i]!=null?
								(tingkat>data.data.listSatManMK[i].tingkatPemb?
								'style="background:#f6f2dd" data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester bawah"':
								(tingkat<data.data.listSatManMK[i].tingkatPemb?
										'style="background:#f1d9d9"	data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester atas"':''
								)
								):''
							))
							+" > <td>"
							+data.data.listKrs[i].pemb.mk.kodeMK
							+"</td> <td>"
								+data.data.listKrs[i].pemb.mk.namaMK
							+"</td> <td>"
								+data.data.listKrs[i].pemb.nmPemb
							+"</td> <td>"
								+data.data.listKrs[i].pemb.mk.jumlahSKS
							+'</td> <td> <button type="button"'
								+' onclick="drop(\''+data.data.listKrs[i].idKrs+'\')" class="btn btn-danger">Hapus</button>'
							+'</td> </tr>');
				}
				$("#sisa").text(batasPengambilan-sksTerambil);
				$("#terambil").text(sksTerambil);
				$('[data-toggle="tooltip"]').tooltip();
			}
			else if(data.status=='expired')
			{ document.location=data.message; }
			else
			{ show_message('Error', data.message,true);}
		},
		error: $.ajaxErrorHandler,
		dataType : 'json'
	});
}

var reportKrs = function()
{
	blockUI($("#masterpage"));
	eval("var tempData = {'idThnAjaran':'"+$('#thnAjaran').val()+"','idSmt':'"+$("#smt").val()+"'}");
	$.ajax({
		url: context_path+"modul/pembelajaran/karturencanastudi/getkrsperiode",
		data : tempData,
		type : 'post',
		success: function(data)
		{
			unblockUI($("#masterpage"));
			if(data.status=='ok')
			{
				show_message("Sukses", data.message);
				idTglSmt = data.data.tglSmt.idTglSmt;
				if(data.data.dapatDisusun == false) 
				{
					$("#ambilMatakuliah").hide();
					$("#aksi").text("Nilai");
				}
				else 
				{
					$("#ambilMatakuliah").show();
					$("#aksi").text("Aksi");
				}
				
				if(data.data.statusPembayaran == false) 
				{
					$("#pembayaran").html('<div class="alert alert-danger text-center" role="alert">Peserta didik belum melakukan pembayaran pada periode ini</div>');
				}
				else 
				{
					$("#pembayaran").html('');
				}
				$("#batas").text(data.data.batasPengambilan.batasPengambilanSks);
				
				$("#awalPenyusunan").text(data.data.awalPenyusunan);
				$("#akhirPenyusunan").text(data.data.akhirPenyusunan);
				$("#awalPerubahan").text(data.data.awalPerubahan);
				$("#akhirPerubahan").text(data.data.akhirPerubahan);
				$("#awalPembatalan").text(data.data.awalPembatalan);
				$("#akhirPembatalan").text(data.data.akhirPembatalan);

				$("#ips").text(data.data.ips==null?"0":data.data.ips.nilaiIps);
				
				$('#tableKrs tbody').html('');
				sksTerambil =0
				if(data.data.listKrs.length>0 && data.data.listKrs[0].aKrsDisetujui==true){
					$("#setujui").html('<div class="alert alert-success text-center" role="alert">KRS telah di setujui</div>');
				}
				else $("#setujui").html('<div class="alert alert-danger text-center" role="alert">KRS belum setujui</div>');
				for(i=0;i<data.data.listKrs.length;i++)
				{
					console.log(data.data.tingkat);
					sksTerambil += data.data.listKrs[i].pemb.mk.jumlahSKS;
					$('#tableKrs tbody').append(
							"<tr " 
							+ 
							(data.data.tingkat==null?'':
								(data.data.listSatManMK[i]!=null?
								(data.data.tingkat>data.data.listSatManMK[i].tingkatPemb?
								'style="background:#f6f2dd" data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester bawah"':
								(data.data.tingkat<data.data.listSatManMK[i].tingkatPemb?
										'style="background:#f1d9d9"	data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester atas"':''
								)
								):""
							))
							+" > <td>"
							+data.data.listKrs[i].pemb.mk.kodeMK
							+"</td> <td>"
								+data.data.listKrs[i].pemb.mk.namaMK
							+"</td> <td>"
								+data.data.listKrs[i].pemb.nmPemb
							+"</td> <td>"
								+data.data.listKrs[i].pemb.mk.jumlahSKS
							+"</td><td>"
							+(
								(data.data.listKrs[i].aKrsDisetujui == true?
										(data.data.listKuisioner[i]==false?"-":data.data.listKrs[i].konversiNilai==null?"-":data.data.listKrs[i].konversiNilai.huruf)
								:data.data.dapatDisusun==false?(data.data.listKuisioner[i]==false?"-":data.data.listKrs[i].konversiNilai==null?"-":data.data.listKrs[i].konversiNilai.huruf)
								:'<button type="button"'
									+' onclick="drop(\''+data.data.listKrs[i].idKrs+'\')" class="btn btn-danger">Hapus</button>'
								)
							 )
							+'</td> </tr>');
				}
				batasPengambilan = data.data.batasPengambilan==null?0:data.data.batasPengambilan.batasPengambilanSks;
				$("#sisa").text(batasPengambilan-sksTerambil);
				$("#terambil").text(sksTerambil);
				$('[data-toggle="tooltip"]').tooltip();
			}
			else if(data.status=='expired')
			{ document.location=data.message; }
			else
			{ show_message('Error', data.message,true);}
		},
		error: $.ajaxErrorHandler,
		dataType : 'json'
	});
}

var drop = function(id)
{
	if(!confirm("Hapus matakuliah terpilih?")) return false;
	
	blockUI($("#masterpage"));
	eval("var tempData = {'idKrs' : '"+id+"','idTglSmt':'"+idTglSmt+"'}");
	$.ajax({
		url: context_path+"modul/pembelajaran/karturencanastudi/hapusmk",
		data : tempData,
		type : 'post',
		success: function(data)
		{
			unblockUI($("#masterpage"));
			if(data.status=='ok')
			{
				show_message("Sukses", data.message);
				$('#tableKrs tbody').html('');
				sksTerambil =0
				for(i=0;i<data.data.listKrs.length;i++)
				{
					sksTerambil += data.data.listKrs[i].pemb.mk.jumlahSKS;
					$('#tableKrs tbody').append(
							"<tr " 
							+ 
							(tingkat==null?'':
								(data.data.listSatManMK[i]!=null?
								(tingkat>data.data.listSatManMK[i].tingkatPemb?
								'style="background:#f6f2dd" data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester bawah"':
								(tingkat>data.data.listSatManMK[i].tingkatPemb?
										'style="background:#f1d9d9"	data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester atas"':''
								)
								):''
							))
							+" > <td>"
							+data.data.listKrs[i].pemb.mk.kodeMK
							+"</td> <td>"
								+data.data.listKrs[i].pemb.mk.namaMK
							+"</td> <td>"
								+data.data.listKrs[i].pemb.nmPemb
							+"</td> <td>"
								+data.data.listKrs[i].pemb.mk.jumlahSKS
							+'</td> <td> <button type="button"'
								+' onclick="drop(\''+data.data.listKrs[i].idKrs+'\')" class="btn btn-danger">Hapus</button>'
							+'</td> </tr>');
				}
				$("#sisa").text(batasPengambilan-sksTerambil);
				$("#terambil").text(sksTerambil);
				$('[data-toggle="tooltip"]').tooltip();
			}
			else if(data.status=='expired')
			{ document.location=data.message; }
			else
			{ show_message('Error', data.message,true);}
		},
		error: $.ajaxErrorHandler,
		dataType : 'json'
	});
}

var lihat = function(nomor)
{
	window.open(context_path+'modul/pembelajaran/pembelajaran/peserta/'+$('#select-'+nomor).val());
}

var lihatPendidik = function(nomor)
{
	window.open(context_path+'modul/pembelajaran/pembelajaran/pendidik/'+$('#select-'+nomor).val());
}

var cetak = function()
{
	window.open(context_path+'modul/pembelajaran/karturencanastudi/cetak/'+idPd+'/'+$('#thnAjaran').val()+'/'+$('#smt').val());
}