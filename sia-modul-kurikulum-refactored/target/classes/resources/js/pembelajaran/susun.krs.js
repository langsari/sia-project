var ambil = function(nomor)
{
	blockUI($("#masterpage"));
	eval("var tempData = {'idPemb' : '"+$('#select-'+nomor).val()+"'}");
	$.ajax({
		url: context_path+"karturencanastudi/ambilmk",
		data : tempData,
		type : 'post',
		success: function(data)
		{
			unblockUI($("#masterpage"));
			if(data.status=='ok')
			{
				show_message("Sukses", data.message);
				$('#tableKrs tbody').html('');
				for(i=0;i<data.data.length;i++)
				{
					$('#tableKrs tbody').append(
							"<tr> <td>"
							+data.data[i].pemb.mk.kodeMK
							+"</td> <td>"
								+data.data[i].pemb.mk.namaMK
							+"</td> <td>"
								+data.data[i].pemb.nmPemb
							+"</td> <td>"
								+data.data[i].pemb.mk.jumlahSKS
							+'</td> <td> <button type="button"'
								+' onclick="drop(\''+data.data[i].idKrs+'\')" class="btn btn-danger">Drop</button>'
							+'</td> </tr>');
				}
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
	blockUI($("#masterpage"));
	eval("var tempData = {'idKrs' : '"+id+"'}");
	$.ajax({
		url: context_path+"karturencanastudi/hapusmk",
		data : tempData,
		type : 'post',
		success: function(data)
		{
			unblockUI($("#masterpage"));
			if(data.status=='ok')
			{
				show_message("Sukses", data.message);
				$('#tableKrs tbody').html('');
				for(i=0;i<data.data.length;i++)
				{
					$('#tableKrs tbody').append(
							"<tr> <td>"
							+data.data[i].pemb.mk.kodeMK
							+"</td> <td>"
								+data.data[i].pemb.mk.namaMK
							+"</td> <td>"
								+data.data[i].pemb.nmPemb
							+"</td> <td>"
								+data.data[i].pemb.mk.jumlahSKS
							+'</td> <td> <button type="button"'
								+' onclick="drop(\''+data.data[i].idKrs+'\')" class="btn btn-danger">Drop</button>'
							+'</td> </tr>');
				}
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
	window.open(context_path+'pembelajaran/peserta/'+$('#select-'+nomor).val());
}