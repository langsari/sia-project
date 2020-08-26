<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Isi Kuisioner</title>
		
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
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/jquery-ui/jquery-ui.min.css"
			rel="stylesheet" type="text/css" />
	</head>
	<body>
		<!-- content -->
		<div class="container">
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title"><c:out value="${kuisioner.getNmKuisioner()}"></c:out></h4>
						</div>
						<div class="panel-body">
							<h2>Kelas <c:out value="${pemb.getMk().getNamaMK()} ${pemb.getNmPemb()}"></c:out> </h2>
							<p>Silahkan masukkan nilai yang ingin anda berikan untuk setiap pertanyaan anda.
							Semakin besar nilai yang ingin anda berikan, berarti semakin baik tanggapan anda terhadap poin yang ditanyakan pada pertanyaan.</p>
							<table class="table">
								<thead>
									<tr>
										<th>No</th>
										<th>Pertanyaan</th>
										<th>Skor</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="pertanyaan" varStatus="status" items="${daftarPertanyaan}">
									<tr name="${pertanyaan.getIdPertanyaanKuisioner()}">
										<td><c:out value="${status.index + 1}"></c:out></td>
										<td><c:out value="${pertanyaan.getPertanyaan()}"></c:out></td>
										<td><div class="slider"></div></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<button id="submitKuisioner" type="button" class="btn btn-primary pull-right" data-loading-text="Menyimpan...">Submit</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end of content -->
		
		<content tag="scripts">	
			<style>
				.slider-tip {
					opacity:1;
			    	bottom:120%;
			    	}
			</style>
			<script>
				$(document).ready(function() {
					var idKrs = "${idKrs}";
					var idKuisioner = "${kuisioner.getIdKuisioner()}";
					var max = "${kuisioner.getSkalaKuisioner()}";
					
					$(".slider").slider({
						value : max/2,
						min : 1,
						max : max,
						slide: function (event, ui) {
							$(ui.handle).html('<div class="tooltip top slider-tip"><div class="tooltip-arrow"></div><div class="tooltip-inner">' + ui.value + '</div></div>');
						}
					});
					
					$(".ui-slider-handle").mouseenter(function() {
						var slider = $(this).closest(".slider");
						var value = $(slider).slider("value");
						$(this).html('<div class="tooltip top slider-tip"><div class="tooltip-arrow"></div><div class="tooltip-inner">' + value + '</div></div>');
					});
					
					$(".slider").mouseleave(function() {
						$(this).find(".ui-slider-handle").html('');
					});
					
					$("#submitKuisioner").click(function() {
						var listNilai = new Array();
						var tombol = $(this);
						
						$(tombol).button("loading");
						
						$(".slider").each(function(index, element) {
							var idPertanyaan = $(element).closest("tr").attr("name");
							var nilaiPertanyaan = $(element).slider("value");
							
							var nilaiJSON = {
								"idPertanyaan" : idPertanyaan,
								"nilaiPertanyaan" : nilaiPertanyaan
							};
							
							listNilai.push(nilaiJSON);
						});
						
						$.ajax({
							url : idKrs + "/" + idKuisioner + "/simpan_kuisioner/",
							type : "POST",
							contentType : "application/json",
							data : JSON.stringify(listNilai),
							success : function(data) {
								if(data.status == "ok") {
									toastr["success"](data.message, "Sukses");
									$(tombol).button("reset");
									location.replace(context_path + "/isi_kuisioner/");
								}
							}
						});
					});
				});				
			</script>
			
			<%@include file="footer.jsp" %>
		</content>
	</body>
</html>