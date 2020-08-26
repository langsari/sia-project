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
	<script src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript" ></script>
			
	
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
								<select id="filter2" name="filter2">
									<option value="">Semua</option>
									<option value="false">Aktif</option>
									<option value="true">Terdrop</option>
								</select>
							</div>
							<div class="form-group">
								<label>Tahun Ajaran</label>
	                            <select class="form-control" id="filter" name="filter">
	                            	<option value=""></option>
	                            	<c:forEach items="${listTglSmt}" var="tglSmt">
	                            		<option value="${tglSmt.idTglSmt}" <c:if test="${tglSmt.aTglSmtAktif == true}">selected="true"</c:if> >${tglSmt.thnAjaran.thnThnAjaran} ${tglSmt.smt.nmSmt}</option>
	                            	</c:forEach>
	                            </select>
							</div>
						</div>
						<div class="col-md-8 masteractions">
							<div class="btn-action pull-right">
							</div>
						</div>
					</div>
					<form class="tableform">
						<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
							<thead>
								<tr>
									<td>
										<div class="checkbox">
											<input class="checkbox-all" type="checkbox" id="flat-checkbox-1">
										</div>
									</td>
									<td>NIM</td>
									<td>Nama</td>
									<td>Angkatan</td>
									<td>Matakuliah</td>
									<td>Pembelajaran</td>
									<td>SKS</td>
									<td>Waktu Ambil</td>
									<td>Status</td>
									<td>Waktu Hapus</td>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
		<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->
		<content tag=”scripts”>
		<script>
			$(document).ready(function(){
				$('#masterpage').masterPage(
				{
					detailFocusId: '#idKrsSementara',
					dataUrl: context_path+'modul/pembelajaran/pengambilankrs/json',
					primaryKey: 'idKrsSementara',
			        order: [[7,"desc"]],
					editOnClick: false,
					editOnClickRow: false,
					showAddButton: false,
					showDelButton: false,
					cols: [
						/* idKrsSementara */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								return '<div class="checkbox-data"><input type="checkbox" name="idSmt[]" value="'+data+'"></div>';
							}
						},
						/* nimPd */
						{ "bVisible":    true },
						/* nmPd */
						{ "bVisible":    true },
						/* angkatanPd */
						{ "bVisible":    false },
						/* namaMK */
						{ "bVisible":    true },
						/* nmPemb */
						{ "bVisible":    true },
						/* jumlahSKS */
						{ "bVisible":    true },
						/* waktuAmbil */
						{ "bVisible":    true },
						/* krsSementaraTerhapus */
						{ 
							"bVisible":    false,
							mRender: function(data,type,full){
								if(full[3]=='false') return "Aktif";
								else return "terdrop";
							}
						},
						/* waktuHapus */
						{ "bVisible":    false }
					],
					validationRules: {idPtk:{required: false},nipPtk:{required: true},nmPtk:{required: true}},
					filters: [{id:'#filter2', name:'aKrsSementaraTerhapus'},{id:'#filter', name:'id_tgl_smt'}]
				});
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