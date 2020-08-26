<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<title>Form Component</title>
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
		<link href="${pageContext.servletContext.contextPath}/resources/datepicker/bootstrap-datepicker-master/css/datepicker.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.servletContext.contextPath}/resources/timepicker/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
	
		<link href="${pageContext.servletContext.contextPath}/resources/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css" />
		
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
			<form role="form" id="formdetail" action="<?php echo base_url();?>iegamesuser/gantipassword_submit" method="post">
					<div class="form-group">
						<label>Tim Name</label>
						<input type="text" class="form-control" id="tim_name" name="old_password_iegames" placeholder="Password" required/>
					</div>
					<div class="form-group">
						<label>Old Password</label>
						<input type="password" class="form-control" id="old_password_iegames" name="old_password_iegames" placeholder="Password" required/>
					</div>
					<div class="form-group">
						<label>New Password</label>
						<input type="password" class="form-control" id="password_iegames" name="password_iegames" placeholder="Password" required/>
					</div>
					<div class="form-group">
						<label>Confirm New Password</label>
						<input type="password" class="form-control" id="confirm-password_iegames" name="confirm-password_iegames" placeholder="Re-enter password" required/>
					</div>
					<div class="form-group">
						<label>Date Picker</label>
						<input type="text" class="form-control" id="date-picker" name="confirm-password_iegames" required/>
					</div>
					<div class="form-group bootstrap-timepicker">
						<label>Time picker</label>
						<input type="text" id="login_time_inchall_jam" name="login_time_inchall_jam" class="form-control timepicker" />
						<span>h:m:s</span>
					</div>
					<div class="form-group">
						<label>CK Editor</label>
						<textarea class="form-control ckeditor"></textarea>
					</div>
					<div class="form-group">
						<input type="submit" id="submit" class="btn btn-primary"/>
					</div>
			</form>
			<button class="btn btn-success" onclick="grittermessage()">Notifikasi</button>
			<button class="btn btn-danger" onclick="gritterstickymessage()">Notifikasi Sticky</button>
			<button class="btn btn-primary" onclick="blockForm()">BlockUI</button>
			<button class="btn btn-warning" onclick="unBlockForm()">Unblock UI</button>
		
				</div>
			</div>
		</div>
		
		<script src="${pageContext.servletContext.contextPath}/resources/datepicker/bootstrap-datepicker-master/js/bootstrap-datepicker.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/timepicker/bootstrap-timepicker.min.js" rel="stylesheet" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/js/jquery.blockui.js" type="text/javascript" ></script>
		<script src="${pageContext.servletContext.contextPath}/resources/ckeditor/ckeditor.js" type="text/javascript" ></script>
		<script src="${pageContext.servletContext.contextPath}/resources/ckeditor/adapters/jquery.js" type="text/javascript"></script>
		
		<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->
		<script>
			$( '.ckeditor' ).ckeditor(); 
			$('#date-picker').datepicker({
				format: "dd-mm-yyyy"
			});
			$(".timepicker").timepicker({
				showInputs: false,
				showMeridian: false
			});
			
			function show_message(title, message){
				var class_name = "";
				$.gritter.add({
					title: title,
					text: message,
					//class_name: 'gritter-blue' kalau ada kelas untuk modifikasi
				});
			}
			
			function show_sticky_message(title, message){
				 var unique_id = $.gritter.add({
					title: title,
					text: message,
					//image: 'img/avatar-mini.png', kalo mau pakai gambar
					sticky: true,
					time: '',
					//class_name: 'my-sticky-class' kalau ada kelas untuk modifikasi
				});
			}
			
			function grittermessage(){
				show_message('ok','pesan dari gritter');
			}
			
			function gritterstickymessage(){
				show_sticky_message('ok','pesan dari sticky gritter');
			}
						
			function blockUI(el, centerY) {
				var el = jQuery(el); 
				el.block({
						message: '<img src="assets/img/loading.gif" align="">',
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
			
			function blockForm()
			{
				blockUI('#formdetail');
			}
			
			function unBlockForm()
			{
				unblockUI('#formdetail');
			}

		</script>
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