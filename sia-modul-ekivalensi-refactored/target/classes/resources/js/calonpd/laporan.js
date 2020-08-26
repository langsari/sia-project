var tambahkan;
$(document).ready(function(){
	$('#masterpage-modal-peserta-didik').masterPage(
	{
		detailFocusId: '#idPd',
		dataUrl: context_path+'modul/ekuivalensi/calonpd/laporan/jsonpd',
		primaryKey: 'idPd',
        order: [[0,"asc"]],
        editOnClick: false,
		editOnClickRow: false,
		showAddButton: false,
		columnExclude:[0,1,4,5],
		cols: [
		/* idCalonPD */
		{ 
			"bVisible":    true,
			bSortable: false,
			bSearchable: false,
			mRender: function(data,type,full){
				return '<input class="checkbox-data" type="checkbox" name="idCalonPD[]" value="'+data+'"/>';
			}
		},
		/* satMan */
		{ "bVisible":    false },
		/* nmCalonPD */
		{ "bVisible":    true },
		/* ptAsal */
		{ "bVisible":    true },
		/* pedomanEKuivalensi */
		{ "bVisible":    false },
		/* aEkuivalensi */
		{ 
			"bVisible":    false,
			bSortable: true,
			bSearchable: true,
			mRender: function(data){
				if(data == "false")
					return "Belum";
				else return "Sudah";
			}
		}
		],
		filters: [{id:'#filter-satman', name:'idSatMan'}],
		callOnSelect:function(aData, options){
			$('#modal-peserta-didik').modal("toggle");
			$('#idCalonPD').val(aData[0]);
			$('#nmCalonPD').val(aData[2]);
			show_mk();
		}
	});	
	
	$('#nmCalonPD').click(function(){
		$('#modal-peserta-didik').find('.dataTables_length select').change();
		$('#modal-peserta-didik').modal('toggle');
	});
	
	$('#filter-satman').change(function(){
		$('#idCalonPD').val("");
		$('#nmCalonPD').val("");
		show_mk();
	});
	
	show_mk = function()
	{
		if($('#idCalonPD').val() != "" && $('#filter-kurikulum').val() != "")
		{
			blockUI($("#masterpage"));
			eval("var tempData = {'idCalonPD' : '"+$('#idCalonPD').val()+"'};");
			$.ajax({
				url: context_path+"modul/ekuivalensi/calonpd/laporan/getmkbycalonpd",
				data : tempData,
				type : 'post',
				success: function(data)
				{
					unblockUI($("#masterpage"));
					if(data.status=='ok')
					{
						show_message('ok', data.message);
						$('#body-riwayat').html("");
						var append = "";
						if(data.data != null)
						{
//							console.log(data.data);
							$('#cetak').removeAttr("disabled");
							for(var i=0;i<data.data.kodeMK.length;i++)
							{
								append += "<tr>";
								append += "<td>"+data.data.kodeMK[i]+"</td>";
								append += "<td>"+data.data.namaMK[i]+"</td>";
								append += "<td>"+data.data.jumlahSKS[i]+"</td>";
								var tmp = data.data.status[i].split(";"); 
								/*
								 * 1. Diakui
								 * 2. Wajib
								 * 3. Pilihan
								 */
								if(tmp[1] == "true")
									append += "<td><label class='label label-warning'>Wajib</label></td>";
								else append += "<td><label class='label label-success'>Pilihan</label></td>";
								
								if(tmp[0] == '1')
									append += "<td><label class='label label-primary'>Diakui</label></td>";
								else if(tmp[0] == '2')
									append += "<td><label class='label label-warning'>Wajib</label></td>";
								else if(tmp[0] == '3')
									append += "<td><label class='label label-success'>Pilihan</label></td>";
								else if(tmp[0] == '4')
									append += "<td><label class='label label-success'>Bebas</label></td>";
								
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
		var redirectWindow = window.open(context_path+"modul/ekuivalensi/calonpd/cetak/"+$("#idCalonPD").val(), '_blank');
		   redirectWindow.location;
	}
});