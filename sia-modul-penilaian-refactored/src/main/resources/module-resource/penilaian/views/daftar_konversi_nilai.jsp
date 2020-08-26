<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Pengaturan Konversi Nilai</title>
		
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
				<div class="col-md-6 col-md-offset-3">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Daftar Konversi Nilai</h4>
						</div>
						<div class="panel-body">
							<table class="table">
								<thead>
									<tr>
										<th>Indeks Huruf</th>
										<th>Indeks Nilai</th>
										<th>Batas Nilai</th>
										<th>Action</th>
									</tr>
								<thead>
								<tbody>
									<c:forEach var="konversi" items="${listKonversi}">
									<tr class="row-konversi">
										<td><input type="text" class="form-control hurufKonversi" value="${konversi.getHuruf()}" /></td>
										<td><input type="text" class="form-control nilaiKonversi" value="${konversi.getNilaiHuruf()}" /></td>
										<td><input type="text" class="form-control batasKonversi" value="${konversi.getBatasBawah()}" /></td>
										<td><button type="button" class="btn btn-danger tombolHapusKonversi" name="${konversi.getIdKonversi()}"><i class="glyphicon glyphicon-remove"></i></button></td>
									</tr>
									</c:forEach>
									<tr id="newKonversi">
										<td><input type="text" class="form-control" id="hurufKonversiNew" value="" /></td>
										<td><input type="text" class="form-control" id="nilaiKonversiNew" value="" /></td>
										<td><input type="text" class="form-control" id="batasKonversiNew" value="" /></td>
										<td><button type="button" class="btn btn-success" id="tombolTambahKonversi"><i class="glyphicon glyphicon-plus"></i></button></td>
									</tr>
								</tbody>
							</table>
							<button type="button" class="btn btn-primary pull-right" id="tombolSimpanKonversi">Simpan</button>
						</div>
					</div>
				</div>							
			</div>
		</div>
		<!-- end of content -->
		
		<!-- script ajax -->
		<content tag="scripts">	
			<script>
				$(document).ready(function() {		
					//script tambah konversi
					$("#tombolTambahKonversi").click(function() {
						if($("#hurufKonversiNew").val() != "" && $("#nilaiKonversiNew").val() != "" && $("#batasKonversiNew").val() != "") {
							var huruf = $("#hurufKonversiNew").val();
							var nilai = $("#nilaiKonversiNew").val();
							var batas = $("#batasKonversiNew").val();
							
							var konversi = {
									"idKonversi" : null,
									"huruf" : huruf,
									"nilaiHuruf" : nilai,
									"batasBawah" : batas,
									"aStatusKonversiAktif" : true
							};
							
							$.ajax({
								url : "tambah_konversi/",
								type : "POST",
								contentType : "application/json",
								data : JSON.stringify(konversi),
								success : function(data) {
									if(data.status == "ok") {
										$("#newKonversi").before('<tr class="row-konversi">'
											+ '<td><input type="text" class="form-control hurufKonversi" value="' + huruf + '" /></td>'
											+ '<td><input type="text" class="form-control nilaiKonversi" value="' + nilai + '" /></td>'
											+ '<td><input type="text" class="form-control batasKonversi" value="' + batas + '" /></td>'
											+ '<td><button type="button" class="btn btn-danger tombolHapusKonversi" name="' + data.data + '"><i class="glyphicon glyphicon-minus"></i></button></td>'
											+ '</tr>'
										);
										$("#hurufKonversiNew").val("");
										$("#nilaiKonversiNew").val("");
										$("#batasKonversiNew").val("");
										toastr["success"](data.message, "Sukses");
									}
									else if(data.status == "fail") {
										toastr["error"](data.message, "Error");
									}
								}
							});
						}
					});
					
					//script hapus konversi
					$(".tombolHapusKonversi").on("click", function() {
						var idKonversi = $(this).attr("name");
						var button = $(this);
						$.ajax({
							url : "hapus_konversi/",
							type : "POST",
							data : {"idKonversi" : idKonversi},
							success : function(data) {
								if(data.status == "ok") {
									$(button).closest("tr").remove();
									toastr["success"](data.message, "Sukses");
								}
							},
							dataType : "json"
						});
					});
					
					//script simpan konversi
					$("#tombolSimpanKonversi").click(function() {
						var listKonversi = new Array();
						var aNilaiNolAda = false;
						
						$("tr.row-konversi").each(function(index, element) {
							var idKonversi = $(element).find("button").attr("name");
							var huruf = $(element).find("input.hurufKonversi").val();
							var nilai = $(element).find("input.nilaiKonversi").val();
							var batas = $(element).find("input.batasKonversi").val();
							
							if (nilai == 0) {
								aNilaiNolAda = true;
							}
							
							var konversi = {
									"idKonversi" : idKonversi,
									"huruf" : huruf,
									"nilaiHuruf" : nilai,
									"batasBawah" : batas,
									"aStatusKonversiAktif" : true
							};
							
							listKonversi.push(konversi);
						});
						
						if(aNilaiNolAda) {
							$.ajax({
								url : "simpan_konversi/",
								type : "POST",
								contentType : "application/json",
								data : JSON.stringify(listKonversi),
								success : function(data) {
									if(data.status == "ok") {
										toastr["success"](data.message, "Sukses");
									}
									else if(data.status == "fail") {
										toastr["error"](data.message, "Error");
									}
								}
							});
						}
						else {
							toastr["warning"](data.message, "Peringatan");
						}
					});
				});
			</script>
			<!-- end of script ajax -->
			
			<%@include file="footer.jsp" %>
		</content>
	</body>
</html>