var tambahkan;
$(document).ready(function(){
	$('#masterpage-modal-peserta-didik').masterPage(
	{
		detailFocusId: '#idPd',
		dataUrl: context_path+'modul/ekuivalensi/ekuivalensi/laporan/jsonpd',
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
		filters: [{id:'#filter-kurikulum', name:'idKurikulum'}],
		callOnSelect:function(aData, options){
			$('#modal-peserta-didik').modal("toggle");
			$('#idPd').val(aData[0]);
			$('#nmPd').val(aData[2]);
			show_mk();
		}
	});	
	
	$('#nmPd').click(function(){
		$('#modal-peserta-didik').modal('toggle');
	});
	
	$('#filter-kurikulum').change(function(){
		$("#masterpage-modal-peserta-didik").find('.dataTables_length select').change();
		show_mk();
	});
	
	show_mk = function()
	{
		if($('#idPd').val() != "" && $('#filter-kurikulum').val() != "")
		{
			blockUI($("#block-pd-detail"));
			eval("var tempData = {'idPd' : '"+$('#idPd').val()+"','idKurikulum' : '"+$('#filter-kurikulum').val()+"'};");
			$.ajax({
				url: context_path+"modul/ekuivalensi/ekuivalensi/laporan/getmkbykurikulum",
				data : tempData,
				type : 'post',
				success: function(data)
				{
					unblockUI($("#block-pd-detail"));
					if(data.status=='ok')
					{
						show_message('ok', data.message);
						$('#body-riwayat').html("");
						if(data.data != null)
						{
							$('#cetak').removeAttr("disabled");
							var append = "";
							for(var i=0;i<data.data.kodeMK.length;i++)
							{
								append += "<tr>";
								append += "<td>"+data.data.kodeMK[i]+"</td>";
								append += "<td>"+data.data.namaMK[i]+"</td>";
								append += "<td>"+data.data.jumlahSKS[i]+"</td>";
								var tmp =data.data.status[i].split(";"); 
								if(tmp[0] == 'true')
									append += "<td><label class='label label-warning'>Wajib</label></td>";
								else append += "<td><label class='label label-success'>Pilihan</label></td>";
								if(tmp[1] == 'true')
									append += "<td><label class='label label-warning'>Ambil</label></td>";
								else append += "<td><label class='label label-success'>Bebas</label></td>";
								append += "</tr>";
							}
							$('#body-riwayat').append(append);
							$('#tabel-riwayat').show();
						}
						else $('#cetak').attr("disabled",true);
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
		else $('#body-riwayat').html("");
	}
	
	cetak = function()
	{
		blockUI($("#masterpage"));
		eval("var tempData = {'idPd' : '"+$('#idPd').val()+"', 'idKurikulum' : '"+$('#filter-kurikulum').val()+"' };");
		$.ajax({
			url: context_path+"modul/ekuivalensi/ekuivalensi/pesertadidik/getidekuivalensipd",
			data : tempData,
			type : 'post',
			success: function(data)
			{
				unblockUI($("#masterpage"));
				if(data.status=='ok')
				{
					show_message('ok', data.message);
					if(data.data != null)
					{
						var redirectWindow = window.open(context_path+"modul/ekuivalensi/ekuivalensi/pesertadidik/cetak/"+data.data, '_blank');
						   redirectWindow.location;
					}
					else $('#cetak').attr("disabled");
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
});