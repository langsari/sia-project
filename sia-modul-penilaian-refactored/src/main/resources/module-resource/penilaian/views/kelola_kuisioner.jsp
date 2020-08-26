<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Kelola Kuisioner Dosen</title>
		
		<!-- Styles -->
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/uniform/css/uniform.default.min.css"
			rel="stylesheet" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/line-icons/simple-line-icons.css"
			rel="stylesheet" type="text/css" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/switchery/switchery.min.css"
			rel="stylesheet" type="text/css" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/css/style.css"
			rel="stylesheet" type="text/css" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/slidepushmenus/css/component.css"
			rel="stylesheet" type="text/css" />
					<link href="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/css/datepicker3.css" rel="stylesheet" type="text/css"/>
			
		<!-- Theme Styles -->
		<link
			href="${pageContext.servletContext.contextPath}/resources/css/custom.css"
			rel="stylesheet" type="text/css" />
			
		<script
			src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/modernizr.js"></script>
		<script
			src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/snap.svg-min.js"></script>
		
	</head>
	<body>
		<!-- content -->
		<div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Kelola Kuisioner</h4>
						</div>
						<div class="panel-body">
							<table class="table">
								<thead>
									<tr>
										<th>Nama Kuisioner</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="kuisioner" items="${listKuisioner}">
									<tr id="${kuisioner.getIdKuisioner()}" name="${kuisioner.getIdKuisioner()}">
										<td><c:out value="${kuisioner.getNmKuisioner()}"></c:out></td>
										<td>
											<button type="button" class="btn btn-primary tombolUbahKuisioner" data-toggle="modal" data-target="#modalKuisioner">
												<i class="glyphicon glyphicon-pencil"></i>
											</button>
											<button type="button" class="btn btn-danger tombolHapusKuisioner">
												<i class="glyphicon glyphicon-remove"></i>
											</button>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<button type="button" class="btn btn-primary pull-right" data-toggle="modal" data-target="#modalKuisioner">Buat Kuisioner Baru</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end of content -->
		
		<!-- modal nama kuisioner -->
		<div class="modal fade" id="modalKuisioner" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title">Edit Kuisioner</h4>
					</div>
					<div id="contentNamaKuisioner">					
						<div class="modal-body">
							<p>Silahkan masukkan nama kuisioner yang akan dibuat beserta skala penilaian yang akan digunakan.</p>
							<form id="formNamaKuisioner">
								<div class="form-group">
									<label for="namaKuisioner">Nama Kuisioner</label>
									<input type="text" class="form-control" id="namaKuisioner" placeholder="Nama Kuisioner"/>
								</div>
								<div class="form-group">
									<label for="skalaKuisioner">Skala Kuisioner</label>
									<input type="number" class="form-control" id="skalaKuisioner">
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary pull-right" id="buttonSubmitNama">Next &gt;</button>
						</div>
					</div>
					
					<div class="hide" id="contentPertanyaanKuisioner">
						<div class="modal-body">
							<p>Silahkan masukkan pertanyaan-pertanyaan dari kuisioner yang akan dibuat. Usahakan pertanyaan yang dibuat bisa terjawab dengan skor.</p>
							<form id="formPertanyaanKuisioner">
								<table class="table">
									<thead>
										<tr>
											<th>Pertanyaan</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
										<tr id="rowPertanyaanBaru">
											<td><input type="text" class="form-control" id="pertanyaanBaru"/></td>
											<td><button type="button" class="btn btn-success" id="tombolTambahPertanyaan"><i class="glyphicon glyphicon-plus"></i></button></td>
										</tr>
									</tbody>
								</table>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary pull-right" id="buttonSubmitPertanyaan" data-loading-text="Menyimpan...">Selesai</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end of modal -->
		
		<!-- script khusus -->
		<content tag="scripts">	
			<script>
				$(document).ready(function() {
					var idKuisioner;
					
					// close modal
					$("button.close").click(function() {
						if($("#contentNamaKuisioner").hasClass("hide")) {
							$("#contentNamaKuisioner").toggleClass("hide");
						}
						if($("#contentPertanyaanKuisioner").hasClass("hide") == false) {
							$("#contentPertanyaanKuisioner").toggleClass("hide");
						}
						
						$("#namaKuisioner").val("");
						$("#skalaKuisioner").val("");
						$("#pertanyaanBaru").val("");
						
						$("tr.pertanyaan").each(function(index, element) {
							$(element).remove();
						});
					});
					
					// tambah kuisioner
					$("#buttonSubmitNama").click(function() {
						if($("#namaKuisioner").val() != "" && $("#skalaKuisioner").val() != "") {
							var namaKuisioner = $("#namaKuisioner").val();
							var skalaKuisioner = $("#skalaKuisioner").val();
							
							var kuisioner = {
									"idKuisioner" : null,
									"nmKuisioner" : namaKuisioner,
									"skalaKuisioner" : skalaKuisioner,
									"aKuisionerAktif" : true
							};
							
							$.ajax({
								url : "tambah_kuisioner/",
								type : "POST",
								contentType: "application/json",
								data : JSON.stringify(kuisioner),
								success : function(data) {
									if(data.status == "ok") {
										idKuisioner = data.data;
										$("#contentNamaKuisioner, #contentPertanyaanKuisioner").toggleClass("hide");
									}
								}
							});
						}
					});
					
					// hapus kuisioner
					$("body").on("click", ".tombolHapusKuisioner", function() {
						idKuisioner = $(this).closest("tr").attr("name");
						toastr["warning"]("Apakah anda yakin akan menghapus kuisioner?<br /><br /><button type='button' class='btn btn-success' id='konfirmasiHapusKuisioner'>Ya</button> <button type='button' class='btn btn-danger'>Tidak</button>", "Peringatan");
					});
					
					$("body").on("click", "#konfirmasiHapusKuisioner", function() {
						$.ajax({
							url : "hapus_kuisioner/",
							type : "POST",
							data : {"idKuisioner" : idKuisioner},
							success : function(data) {
								if(data.status == "ok") {
									$("body").find("#" + idKuisioner).remove();
									toastr["success"]("Kuisioner berhasil dihapus", "Sukses");
								}
							},
							dataType: "json"
						});
					});
					
					// tambah pertanyaan
					$("#tombolTambahPertanyaan").click(function() {
						var tombol = $(this);
						if($("#pertanyaanBaru").val() != "") {
							$(tombol).button("loading");
							var pertanyaan = $("#pertanyaanBaru").val();
							var jsonPertanyaan = {
								"idPertanyaan" : null,
								"pertanyaan" : pertanyaan,
								"idKuisioner" : idKuisioner
							};
							
							$.ajax({
								url : "tambah_pertanyaan/",
								type : "POST",
								contentType: "application/json",
								data : JSON.stringify(jsonPertanyaan),
								success : function(data) {
									if(data.status == "ok") {
										$("#rowPertanyaanBaru").before('<tr class="pertanyaan">'
											+ '<td><input type="text" class="form-control pertanyaanKuisionerBaru" value="' + pertanyaan + '"/></td>'
											+ '<td><button type="button" class="btn btn-danger tombolHapusPertanyaan" name="' + data.data + '"><i class="glyphicon glyphicon-minus"></i></button></td>'
											+ '</tr>'
										);
										$("#pertanyaanBaru").val("");
										$(tombol).button("reset");
									}
								}
							});
						}
					});
					
					// hapus pertanyaan
					$("body").on("click", ".tombolHapusPertanyaan", function() {
						var tombol = $(this);				
						$(tombol).button("loading");
						
						var idPertanyaan = $(this).attr('name');
						
						$.ajax({
							url : "hapus_pertanyaan/",
							type : "POST",
							data : {"idPertanyaan" : idPertanyaan},
							success : function(data) {
								$(tombol).closest("tr").remove();
							},
							dataType : 'json'
						});
					});
					
					//simpan pertanyaan
					$("#buttonSubmitPertanyaan").click(function() {
						$("#buttonSubmitPertanyaan").button("loading");
						var listPertanyaan = new Array();
						$("tr.pertanyaan").each(function(index, element) {
							var idPertanyaan = $(element).find("button").attr("name");
							var pertanyaan = $(element).find("input").val();
							
							var jsonPertanyaan = {
								"idPertanyaan" : idPertanyaan,
								"pertanyaan" : pertanyaan,
								"idKuisioner" : idKuisioner
							};
							
							listPertanyaan.push(jsonPertanyaan);
						});
						
						$.ajax({
							url : "simpan_pertanyaan/",
							type : "POST",
							contentType : "application/json",
							data : JSON.stringify(listPertanyaan),
							success : function(data) {
								$("#buttonSubmitPertanyaan").button("reset");
								location.reload();
							}
						});
					});
					
					//ambil pertanyaan
					$("body").on("click", ".tombolUbahKuisioner", function() {
						idKuisioner = $(this).closest("tr").attr("name");
						$("#contentNamaKuisioner, #contentPertanyaanKuisioner").toggleClass("hide");
						
						$.ajax({
							url : "ambil_pertanyaan/",
							type : "POST",
							data : {"idKuisioner" : idKuisioner},
							success : function(data) {
								for(i=0; i<data.data.length; i++) {
									$("#rowPertanyaanBaru").before('<tr class="pertanyaan">'
										+ '<td><input type="text" class="form-control pertanyaanKuisionerBaru" value="' + data.data[i].pertanyaan + '"/></td>'
										+ '<td><button type="button" class="btn btn-danger tombolHapusPertanyaan" name="' + data.data[i].idPertanyaanKuisioner + '"><i class="glyphicon glyphicon-minus"></i></button></td>'
										+ '</tr>'
									);
								}
							},
							dataType : "json"
						});
					});
				});
			</script>
			<!-- end of script khusus -->
			
			<%@include file="footer.jsp" %>
		</content>
	</body>
</html>