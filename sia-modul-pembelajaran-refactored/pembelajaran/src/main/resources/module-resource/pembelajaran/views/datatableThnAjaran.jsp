<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/resources/favicon_16.ico">
	<link rel="bookmark" href="${pageContext.servletContext.contextPath}/resources/favicon_16.ico">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/site.min.css">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/sia.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/site.min.js"></script>
	<!--[if lt IE 9]>
	<script src="assets/js/html5shiv.js">
	</script>
		  <script src="assets/js/respond.min.js">
	</script>
	<![endif]-->
	<script>
		var context_path = "${pageContext.servletContext.contextPath}/";
	</script>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/jquery.datatables/media/css/jquery.dataTables.min.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/jquery.datatables/extensions/TableTools/css/dataTables.tableTools.min.css"> <!-- optional -->
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/jquery.datatables/extensions/TableTools/js/dataTables.tableTools.min.js"></script>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/jquery.datatables/extensions/ColVis/css/dataTables.colVis.min.css"> <!-- optional -->
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script> <!-- optional -->
	<link href="${pageContext.servletContext.contextPath}/resources/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css" />
	<script src="${pageContext.servletContext.contextPath}/resources/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
	
	
<title>Datatable</title>
</head>
<body style="background:url(${pageContext.servletContext.contextPath}/resources/img/wild_flowers.png) repeat 0 0">
	<div class="container">
		<div class="wrapper">
			<div class="row">
				<div class="col-md-12">
					<img class="img-responsive" src="${pageContext.servletContext.contextPath}/resources/img/logo.png"></img>
				</div>
			</div>
			<nav class="navbar navbar-default navbar-sia" role="navigation" style="background-color: #3bafda;border-radius:0;border-color:#3bafda;">
				<div class="container">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
							<span class="sr-only">Toggle navigation</span> 
							<span class="icon-bar"></span> 
							<span class="icon-bar"></span> 
							<span class="icon-bar"></span>
						</button> 
						<a class="navbar-brand" href="index.html">
							Beranda
						</a>
					</div>
					<div class="collapse navbar-collapse">
						<ul class="nav navbar-nav">
							<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">Pembelajaran <b class="caret">
							</b>
							</a>
								<ul class="dropdown-menu" role="menu">
									<li class="dropdown-submenu">
										<a href="#">Kelola Kalender Akademik</a>
										<ul class="dropdown-menu">
											<li><a href="#">Kelola Tahun Ajaran</a></li>
											<li><a href="#">Kelola Peride</a></li>
											<li><a href="#">Kelola Tanggal Penting</a></li>
										</ul>
									</li>
									<li>
										<a href="#">Kelola Pembelajaran</a>
									</li>
									<li>
										<a href="#">Perwalian</a>
									</li>
									<li>
										<a href="#">Rombongan Belajar</a>
									</li>
									<li>
										<a href="#">KRS</a>
									</li>
									<li>
										<a href="#">Absensi</a>
									</li>
								</ul>
							</li>	
						</ul>
						<ul  class="nav navbar-nav navbar-right">				
							<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">Akun <b class="caret">
							</b>
							</a>
								<ul class="dropdown-menu" role="menu">
									<li>
										<a href="#">Profil</a>
									</li>
									<li>
										<a href="#">Pilih hak akses</a>
									</li>
									<li>
										<a href="#">Keluar</a>
									</li>
								</ul>
							</li>	
						</ul>
					</div>
				</div>
			</nav>
		<!-- akhir dari header -->
		
		<!-- Content -->
		
		
		<div class="row" id="masterpage">
			<div class="container">
				<div class="col-md-12" style="margin-bottom:10px;">
					<ol class="breadcrumb">
						<li>
							<a href="#">Beranda</a>
						</li>
						<li>
							<a href="#">Kelola Kalender Akademik</a>
						</li>
						<li class="active">Kelola Tahun Ajaran</li>
					</ol>
					
					<p>Some default panel content here. Nulla vitae elit libero, a pharetra augue. Aenean lacinia bibendum nulla sed consectetur. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label>Status Aktif</label>
								<select id="filter" name="filter">
									<option value="false">Aktif</option>
									<option value="">Semua</option>
								</select>
							</div>
						</div>
						<div class="col-md-8">
							<div class="button-action pull-right">
								<button class="btn btn-success add">Tambah</button>
								<button class="btn btn-danger delete">Hapus</button>
							</div>
						</div>
					</div>
					<form class="form-master">
						<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
							<thead>
								<tr>
									<td>
										<div class="checkbox">
											<input class="checkbox-all" type="checkbox" id="flat-checkbox-1">
										</div>
									</td>
									<td>Tahun</td>
									<td>Minimal Kehadiran Peserta Didik</td>
									<td>Minimal Pertemuan Pembelajaran</td>
									<td>Status hapus</td>
									<td>Aksi</td>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="row" id="master-detail" style="display:none;">
			<div class="container">
				<div class="col-md-6" style="margin-bottom:10px;">
					<h4 id="title">Tahun Ajaran</h4>
					<form:form role="form" action="login" commandName="thnAjaran">
						<div class="form-group">
							<label>Tahun</label>
							<form:input path="thnThnAjaran" class="form-control" placeholder="Berisi tahun ajaran" required="true" digits="true" />
							<form:hidden path="idThnAjaran" class="form-control" />
						</div>
						<div class="form-group">
							<label>Minimal Pertemuan Pembelajaran</label>
							<form:input path="persenMinimPertemuan" class="form-control" placeholder="Berisi minimal pertemuan pembelajaran dalam persen" required="true" />
						</div>
						<div class="form-group">
							<label>Minimal Kehadiran Peserta Didik</label>
							<form:input path="persenHadirMinimPd" class="form-control" placeholder="Berisi minimal kehadiran peserta didik dalam persen" required="true" />
						</div>
			        </form:form>
			        <button onclick="simpan()" class="btn btn-primary">Simpan</button>
			        <button class="btn btn-default cancel">Batal</button>
				</div>
			</div>
		</div>
		<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->
		<content tag=”scripts”>
		<script>
			function blockUI(el, centerY) {
				var el = jQuery(el); 
				el.block({
						message: '<img src="'+context_path+'resources/img/loading.gif" align="">',
						centerY: centerY != undefined ? centerY : true,
						css: {
							top: '10%',
							border: 'none',
							padding: '2px',
							backgroundColor: 'none'
						},
						overlayCSS: {
							backgroundColor: '#000',
							opacity: 0.05,
							cursor: 'wait'
						}
					});
			}
	
			function unblockUI (el) {
				jQuery(el).unblock({
						onUnblock: function () {
							jQuery(el).removeAttr("style");
						}
					});
			}
			
			$.resetForm = function(form, notFormInput, notSelector)
			{
				if(typeof notFormInput != 'undefined')
				{ for(i = 0; i < notFormInput.length; i++){ $('#' + notFormInput[i] ).html(''); } }
	
				$('.validation_error').remove();
				$('input:not([type=submit], [type=button], [type=reset], [type=radio], [type=checkbox]), textarea', form).each(function(){
					if(typeof notSelector != 'undefined')
						if($(this).is(notSelector)) return;
					if(typeof $(this).attr('no_reset') != 'undefined') return;
					$(this).val('');
				});
				$('select', form).each(function(){
					if(typeof notSelector != 'undefined')
						if( $(this).is(notSelector)) return;
					if(typeof $(this).attr('no_reset') != 'undefined') return;
					$(this).val($('option:nth-child(0)', this).val());
				});
				$('input:checkbox, input:radio', form).each(function(){
					if(typeof notSelector != 'undefined')
						if( $(this).is(notSelector)) return;
					if(typeof $(this).attr('no_reset') != 'undefined') return;
					$(this).attr('checked', false);
					$(this).removeAttr('checked');
				});
			}
		
			$.fillToForm = function(form, formdata)
			{
				for(id in formdata)
				{
					//var a = x;
					currentElement = $('#' + id, form);
					if(currentElement.length == 0){ currentElement = $('[name="' + id +'"]', form); }
					if(currentElement.length > 0){
						tag = currentElement.get(0).tagName;
						type = currentElement.get(0).type;
						if(tag == 'INPUT' && (type == 'radio')){ $('[name="' + id + '"][value="' + formdata[id] + '"]', form).attr('checked', 'checked');}
						else if(tag == 'INPUT' && (type == 'checkbox')){
							if(formdata[id] == 1){
								 currentElement.attr('checked', 'checked');
							}else{ currentElement.removeAttr('checked'); }
						}
						else if(tag == 'INPUT' || tag == 'SELECT' || tag == 'TEXTAREA'){ currentElement.val(formdata[id]); }
						else { currentElement.html(formdata[id]); }
					}
				}
			}
		
			function show_message(title, message){
				var class_name = "";
				$.gritter.add({
					title: title,
					text: message,
					//class_name: 'gritter-blue' kalau ada kelas untuk modifikasi
				});
			}
			
			$(".add").click(function(){
				$("#masterpage").hide();
    			unblockUI($("#master-detail"));
				$.resetForm(("#thnAjaran"));
				$("#master-detail").show();
			});
			
			$(".delete").click(function(){
			   var thnAjaran = $(".form-master").serialize();
				blockUI($("#masterpage"));
				$.ajax( {
	                "dataType": 'json',
	                "type": "POST",
	                "url": context_path+"modul/pembelajaran/tahunajaran/deletemany",
	                "data": thnAjaran,
	                "success": function(data){
	                	unblockUI($("#masterpage"))
	                	if(data.status =="ok") 
                		{
                			show_message(data.status,data.message);
            				$('.dataTables_length select').change();
                		}
	                	else 
	                	{
                			show_message(data.status,data.message);
	                	}
	                }
	            } );
			});
			
			$(".cancel").click(function(){
				$("#masterpage").show();
				$("#master-detail").hide();
			});
			
			function closeform(){
				$("#masterpage").show();
				$("#master-detail").hide();
			}
			
			$("#thnAjaran").validate({
					invalidHandler: function(form, validator) {
						show_message('Error Input', 'Salah satu input yang Anda masukkan salah');
					},
				   submitHandler: function() { 
					   var thnAjaran = $("#thnAjaran").serialize();
						blockUI($("#master-detail"))
						$.ajax( {
			                "dataType": 'json',
			                "type": "POST",
			                "url": context_path+"modul/pembelajaran/tahunajaran/simpan",
			                "data": thnAjaran,
			                "success": function(data){
			                	if(data.status =="ok") 
		                		{
		                			show_message(data.status,data.message);
		            				$('.dataTables_length select').change();
		            				closeform();
		                		}
			                	else 
			                		{
			                			show_message(data.status,data.message);
			                			if(data.data!=null)
			                			for(i=0;i<data.data.length;i++)
		                				{
			                				if($('#'+data.data[i].field).parent().find('.error').length>0)
			                				{
			                					if(data.data[i].bindingFailure) $('#'+data.data[i].field).parent().find('.error').text("input tidak valid");
			                					else $('#'+data.data[i].field).parent().find('.error').text(data.data[i].defaultMessage);
			                				}
			                				else 
			                				{
			                					if(data.data[i].bindingFailure) $('#'+data.data[i].field).after('<label for="'+data.data[i].field+'" class="error">input tidak valid</label>');
			                					else $('#'+data.data[i].field).after('<label for="'+data.data[i].field+'" class="error">'+data.data[i].defaultMessage+'</label>');
			                				}
			                				$('#'+data.data[i].field).parent().find('.error').show();
		                				}
			                			unblockUI($("#master-detail"));
			                		}
			                }
			            } ); 
					}
				})
				
			var simpan = function()
			{
				$("#thnAjaran").submit();
			}
			
			var deleteData = function(id)
			{
				blockUI($("#masterpage"));
				eval("var tempData = {'idThnAjaran' : '"+id+"'};");
				$.ajax( {
	                "dataType": 'json',
	                "type": "POST",
	                "url": context_path+"modul/pembelajaran/tahunajaran/delete",
	                "data": tempData,
	                "success": function(data){
	                	unblockUI($("#masterpage"))
	                	if(data.status =="ok") 
                		{
                			show_message(data.status,data.message);
            				$('.dataTables_length select').change();
                		}
	                	else 
	                	{
                			show_message(data.status,data.message);
	                	}
	                }
	            } );
			}
			
			var edit = function(id)
			{
            	$.resetForm($('#thnAjaran'));
				$("#masterpage").hide();
				$("#master-detail").show();
				blockUI($("#master-detail"));
				eval("var tempData = {'idThnAjaran' : '"+id+"'};");
				$.ajax( {
	                "dataType": 'json',
	                "type": "POST",
	                "url": context_path+"modul/pembelajaran/tahunajaran/edit",
	                "data": tempData,
	                "success": function(data){
	                	unblockUI($("#master-detail"));
	                	$.fillToForm($('#thnAjaran'), data.data);
	                }
	            } );
			}
			
			$('#filter').change(function(){
				$('.dataTables_length select').change();
			});
			$('.checkbox-all').on('ifChecked ifUnchecked', function(event) {
		        if (event.type == 'ifChecked') {
		        	$('.checkbox-data input').iCheck('check');
		        } else {
		        	$('.checkbox-data input').iCheck('uncheck');
		        }
		    });
			$('.datatable').dataTable({
				"bProcessing": true,
		        "bServerSide": true,
		        "order": [[1,"desc"]],
		        "sAjaxSource": context_path + "modul/pembelajaran/tahunajaran/json",
				"sDom": "<'row'<'dataTables_header clearfix'<'col-md-3'<l>><'col-md-9'f<'pull-right'CT>>r>>t<'row-fluid'<'dataTables_footer clearfix'<'col-md-6'i><'col-md-6'p>>>",
				tableTools: {
					"sSwfPath": context_path+"resources/jquery.datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf"
				},
				"oColVis": {
					"buttonText": "Columns <i class='icon-angle-down'></i>",
					"iOverlayFade": 0,
					"aiExclude": [0]
				},
				"fnServerData": function ( sSource, aoData, fnCallback ) {
					aoData.push({'name':'a_thn_ajaran_terhapus','value':$('#filter').val()});
					
		            $.ajax( {
		                "dataType": 'json',
		                "type": "POST",
		                "url": sSource,
		                "data": aoData,
		                "success": fnCallback
		            } );
		        },
		        fnDrawCallback:function(){
			        $('.checkbox-data input').iCheck({checkboxClass:"icheckbox_flat",increaseArea:"20%"});
		        },
				"sScrollX": "100%",
				"aoColumns":[
					/* id */
					{ 
						"bVisible":    true,
						bSortable: false,
						bSearchable: false,
						mRender: function(data,type,full){
							return '<div class="checkbox-data"><input type="checkbox" name="idThnAjaran" value="'+data+'"></div>';
						}
					},
					/* Tahun */
					{ "bVisible":    true },
					/* Minimal Kehadiran Peserta Didik */
					{ "bVisible":    true },
					/* Minimal Kehadiran Peserta Didik */
					{ "bVisible":    true },
					/* Status Aktif */
					{ "bVisible":    false },
					/* Aksi */
					{ 
						"bVisible":    true,
						bSortable: false,
						bSearchable: false,
						mRender: function(data,type,full){
							var action = '<button type="button" onclick="edit(\''+full[0]+'\')" class="btn btn-primary">Edit</button>';
							if(full[4]=='false') action += ' <button type="button" onclick="deleteData(\''+full[0]+'\')" class="btn btn-danger">Hapus</button>'
							return action;
						}
					}
				]
			});
		</script>
		</content>
		<!-- akhir script custom pada halaman -->
		
		<!-- akhir dari content content -->
		
		<!-- footer -->
		<div class="site-footer">
				<div class="container">		
					<div class="copyright clearfix">
					<p>&copy; 2015 <b>SIA UNIVERSITAS X</b> | Powered By <a href="#" target="_blank">SE ITS</a></p>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>