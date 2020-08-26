var tambahkan;
$(document).ready(function(){
	$('#masterpage').masterPage(
	{
		detailFocusId: '#idMKAlihjenjang',
		dataUrl: context_path+'modul/ekuivalensi/mkalihjenjang/json',
		detailUrl: context_path+'modul/ekuivalensi/mkalihjenjang/edit',
		addUrl: context_path+'modul/ekuivalensi/mkalihjenjang/simpan',
		editUrl: context_path+'modul/ekuivalensi/mkalihjenjang/simpan',
		deleteUrl: context_path+'modul/ekuivalensi/mkalihjenjang/deletemany',
		primaryKey: 'idMKAlihjenjang',
        order: [[2,"asc"]],
        columnExclude:[0,6],
		editOnClick: false,
		editOnClickRow: true,
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
			{ "bVisible":    true },
			/* kodeMKAlihjenjang */
			{ "bVisible":    true },
			/* nmMKAlihjenjang */
			{ "bVisible":    true },
			/* jumlahSKS */
			{ "bVisible":    true },
			/* deskripsiMKAlihjenjang */
			{ "bVisible":    true },
			/* aMKAlihjenjangTerhapus */
			{ "bVisible":    false },
			/* Aksi */
			{ 
				"bVisible":    true,
				bSortable: false,
				bSearchable: false,
				mRender: function(data,type,full){
					var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
					if(full[6]=='false') action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>';
					return action;
				}
			}
		],
		validationRules: {idMKAlihjenjang:{required: false},idKatalogAlihjenjang:{required: true}, nmMKAlihjenjang:{required: true}, jumlahSKS:{required: true}, deskripsiMKAlihjenjang:{required: false}, aMKAlihjenjangTerhapus:{required:false}},
		filters: [{id:'#filter-katalog', name:'idKatalogAlihjenjang'}],
		callOnFillForm:function(data,options){
			$('#katalogAlihjenjang').val(data.data.katalogAlihjenjang==null?"":data.data.katalogAlihjenjang.idKatalogAlihjenjang);
		}
	});
});