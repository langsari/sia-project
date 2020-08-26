var tambahkan;
$(document).ready(function(){
	$('.date-picker').datepicker({
		format: "dd-mm-yyyy"
	});
	
	$('#masterpage').masterPage(
	{
		detailFocusId: '#idCalonPD',
		dataUrl: context_path+'modul/ekuivalensi/pdalihjenjang/json',
		detailUrl: context_path+'modul/ekuivalensi/pdalihjenjang/edit',
		addUrl: context_path+'modul/ekuivalensi/pdalihjenjang/simpan',
		editUrl: context_path+'modul/ekuivalensi/pdalihjenjang/simpan',
		deleteUrl: context_path+'modul/ekuivalensi/pdalihjenjang/deletemany',
		primaryKey: 'idCalonPD',
        order: [[2,"asc"]],
		editOnClick: false,
		editOnClickRow: true,
		columnExclude:[0,5],
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
			{ "bVisible":    true },
			/* nmCalonPD */
			{ "bVisible":    true },
			/* ptAsal */
			{ "bVisible":    true },
			/* katalogAlihjenjang */
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
			},
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
					action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>';					
					return action;
				}
			}
		],
		validationRules: {idCalonPD:{required: false},idSatMan:{required: true}, nmCalonPD:{required: true}, ptAsal:{required: true}, idKatalogAlihjenjang:{required:true}, aEkuivalensi:{required: false},
			alamat:{required: true}, tglLahir:{required:true}, tempatLahir:{required: true}},
		filters: [{id:'#filter-satman', name:'idSatMan'},{id:'#filter-katalog', name:'idKatalog'}],
		callOnFillForm:function(data,options){
			eval("var tempData = {'idCalonPD' : '"+data.data.idCalonPD+"'};");
			$.ajax({
				url: context_path+"modul/ekuivalensi/calonpd/cekkrs",
				data : tempData,
				type : 'post',
				success: function(data)
				{
//					unblockUI($("#pesertadidik"));
					if(data.status=='ok')
					{
						if(data.data == 'false')
						{
							$('#katalogAlihjenjang').attr("disabled","true");
							$('#idKatalogAlihjenjang-input').removeAttr("disabled");
							$('#penyetaraan-mk').attr("disabled","true");											
						}
						else
						{
//							$('#idKatalogAlihjenjang-input').attr("disabled","true");
//							$('#idKatalogAlihjenjang-input').removeAttr("disabled");
							$('#katalogAlihjenjang').removeAttr("disabled");
							$('#penyetaraan-mk').removeAttr("disabled");
						}
						show_message("Sukses", data.message);
					}
					else if(data.status=='expired')
					{ document.location=data.message; }
					else
					{ show_message('Error', data.message);}
				},
				error: $.ajaxErrorHandler,
				dataType : 'json'
			});
			if(data.data.katalogAlihjenjang == null)
			{
				$('#penyetaraan-mk').prop('checked',false);
				$('#penyetaraan-mk').uniform();
				$('#penyetaraan-mk').change();
			}
			else 
			{
				$('#penyetaraan-mk').prop("checked",true);
				$('#penyetaraan-mk').uniform();
				$('#penyetaraan-mk').change();
			}
			console.log(data.data.tglLahir);
			$('#tglLahir').datepicker('update',(data.data.tglLahir.values[2]<10?"0"+data.data.tglLahir.values[2]:data.data.tglLahir.values[2])+"-"+(data.data.tglLahir.values[1]<10?"0"+data.data.tglLahir.values[1]:data.data.tglLahir.values[1])+"-"+data.data.tglLahir.values[0]);
			
			$('#satMan').val(data.data.satMan==null?"":data.data.satMan.idSatMan);
			$('#katalogAlihjenjang').val(data.data.katalogAlihjenjang==null?"":data.data.katalogAlihjenjang.idKatalogAlihjenjang);
			$('#idKatalogAlihjenjang-input').val(data.data.katalogAlihjenjang==null?"":data.data.katalogAlihjenjang.idKatalogAlihjenjang);
			$('#aEkuivalensi-input').val(data.data.aEkuivalensi==null?"":data.data.aEkuivalensi);
			
		},
		
		callOnAddAfterReset:function(){
			$('#katalogAlihjenjang').attr("disabled","true");
			$('#idKatalogAlihjenjang-input').removeAttr("disabled");
			$('#pedomanekuivalensi-field').hide();
			$('#penyetaraan-mk').prop('checked',false);
			$('#penyetaraan-mk').uniform();
			$('#penyetaraan-mk').change();
		}
	});
	
	$('#penyetaraan-mk').change(function() {
        if ($(this).prop("checked") == true) {
        	$('#pedomanekuivalensi-field').show();
        	$('#katalogAlihjenjang').removeAttr("disabled");
        	$('#idKatalogAlihjenjang-input').attr("disabled","true");
        } else {
        	$('#katalogAlihjenjang').attr("disabled","true");
        	$('#idKatalogAlihjenjang-input').removeAttr("disabled");
        	$('#idKatalogAlihjenjang-input').val(null);
        	$('#pedomanekuivalensi-field').hide();
        }
    });
});