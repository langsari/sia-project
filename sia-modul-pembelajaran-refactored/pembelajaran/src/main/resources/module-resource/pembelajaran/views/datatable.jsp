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
		var context_path = "${pageContext.servletContext.contextPath}";
	</script>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/jquery.datatables/media/css/jquery.dataTables.min.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/jquery.datatables/extensions/TableTools/css/dataTables.tableTools.min.css"> <!-- optional -->
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/jquery.datatables/extensions/TableTools/js/dataTables.tableTools.min.js"></script>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/jquery.datatables/extensions/ColVis/css/dataTables.colVis.min.css"> <!-- optional -->
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script> <!-- optional -->
	
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
		
		
		<div class="row">
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
			<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
				<thead>
					<tr>
						<td>
							<div class="checkbox">
								<input type="checkbox" id="flat-checkbox-1">
							</div>
						</td>
						<td>Tahun</td>
						<td>Minimal Pertemuan Pembelajaran</td>
						<td>Minimal Kehadiran Peserta Didik</td>
						<td>Status Aktif</td>
						<td>Aksi</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<div class="checkbox">
								<input type="checkbox" id="flat-checkbox-1">
							</div>
						</td>
						<td>2014</td>
						<td>90%</td>
						<td>80%</td>
						<td>Aktif</td>
						<td>
							<button type="button" class="btn btn-danger">Hapus</button>
						</td>
					</tr>
					<tr>
						<td>
							<div class="checkbox">
								<input type="checkbox" id="flat-checkbox-1">
							</div>
						</td>
						<td>2015</td>
						<td>90%</td>
						<td>80%</td>
						<td>Tidak Aktif</td>
						<td>
							<button type="button" class="btn btn-primary">Aktifkan</button>
							<button type="button" class="btn btn-danger">Hapus</button>
						</td>
					</tr>
				</tbody>
			</table>
				</div>
			</div>
		</div>
		<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->
		<content tag=”scripts”>
		<script>
			$('.datatable').dataTable({
				"sDom": "<'row'<'dataTables_header clearfix'<'col-md-3'<l>><'col-md-9'f<'pull-right'CT>>r>>t<'row-fluid'<'dataTables_footer clearfix'<'col-md-6'i><'col-md-6'p>>>",
				tableTools: {
					"sSwfPath": context_path+"/resources/jquery.datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf"
				},
				"oColVis": {
					"buttonText": "Columns <i class='icon-angle-down'></i>",
					"iOverlayFade": 0,
					"aiExclude": [0]
				},
				"sScrollX": "100%",
				"aoColumns":[
					/* id */
					{ 
						"bVisible":    true,
						bSortable: false,
						bSearchable: false
					},
					/* Tahun */
					{ "bVisible":    true },
					/* Minimal Kehadiran Peserta Didik */
					{ "bVisible":    true },
					/* Minimal Kehadiran Peserta Didik */
					{ "bVisible":    true },
					/* Status Aktif */
					{ "bVisible":    true },
					/* Aksi */
					{ 
						"bVisible":    true,
						bSortable: false,
						bSearchable: false						
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