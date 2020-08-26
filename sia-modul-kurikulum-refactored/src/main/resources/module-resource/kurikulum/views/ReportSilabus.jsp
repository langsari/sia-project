<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<!-- Styles -->
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/uniform/css/uniform.default.min.css"
			rel="stylesheet" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/line-icons/simple-line-icons.css"
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
		
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/select2/css/select2.min.css" />
	
		<link rel="stylesheet"
				href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/css/jquery.dataTables.min.css">
		
		<link rel="stylesheet"
			href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/TableTools/css/dataTables.tableTools.min.css">
		<!-- optional -->
		<link rel="stylesheet"
			href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/css/dataTables.colVis.min.css">
		<!-- optional -->
	</head>
	<body>
	
		<div class="row"> 
			<div class="col-md-12" style="margin-bottom:10px;"> 
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Laporan Silabus</h4>
					</div>
					<div class="panel-body">  
						 <div class="row">
						 	<button type="button" class="btn btn-primary col-md-4 col-md-offset-4" onclick="printSilabus(this)" name="${mk2.idMK}">Cetak Silabus&nbsp;&nbsp;<span class="glyphicon glyphicon-print"></span></button>
						 	
						 	<div class="col-md-8 col-md-offset-2">   
						 			<br />
						 			<p><b>Kurikulum :</b> ${mk2.kurikulum.namaKurikulum}</p>
						 			<p><b>Kode Mata Kuliah :</b> ${mk2.kodeMK}</p>
						 			<p><b>Nama Mata Kuliah :</b> ${mk2.namaMK}</p>
						 			<p><b>Rumpun Mata Kuliah :</b> ${mk2.rumpunMK.namaRumpunMK}</p>
						 			<p><b>Jurusan :</b></p>
						 			<ul>
								 		<c:forEach items="${smmkList}" var="smmk"> 
								 			<li>${smmk.satMan.nmSatMan} - Semester : ${smmk.tingkatPemb}</li> 
								 		</c:forEach>
						 			</ul>
						 			<p><b>Kredit :</b> ${mk2.jumlahSKS} sks</p> 
						 			<p><b>Deskripsi :</b></p>
						 			<p>${mk2.deskripsiMK}</p>
					 			<br /> 
					 			<p><b>Capaian Satuan Manajemen yang Didukung</b></p>
								<ul>
						 			<c:forEach items="${cpList}" var="cpList">
						 			 	<li>${cpList.satMan.nmSatMan} | ${cpList.deskripsiCapPemb}</li>
						 			</c:forEach>
						 		</ul>
						 		<br />
						 		<p><b>Capaian Mata Kuliah</b></p> 
								<ul>
						 			<c:forEach items="${cpmkList}" var="cpmkList">
						 			 	<li>${cpmkList.deskripsiCapPembMK}</li>
						 			</c:forEach>
						 		</ul>
						 		<br />
						 		<p><b>Pokok bahasan</b></p>
						 		<ul>
						 		<c:forEach items="${dsList}" var="dsList"> 
						 			<li>${dsList.pokokBahasan}</li> 
						 		</c:forEach>
						 		</ul>
						 		<br />
						 		<p><b>Prasyarat Mata Kuliah</b></p>
						 		<ul>
						 		<c:if test="${prasyaratList != null}">
									<c:forEach items="${prasyaratList}" var="prasyaratList"> 
						 				<li>${prasyaratList.parentMK.kodeMK} - ${prasyaratList.parentMK.namaMK}</li>
						 			</c:forEach>
								</c:if>  
								</ul>
								<c:if test="${prasyaratList == null}">
									<p>Tidak ada prasyarat</p>
								</c:if> 
						 		<br />
						 		<p><b>Pustaka Utama</b></p>
						 		<ul>
						 		<c:forEach items="${dpList}" var="dp"> 
						 			<c:if test="${dp.pustaka.sifatPustaka == 'U'}">
						 				<li>${dp.pustaka.namaPustaka}</li> 
						 			</c:if>
						 		</c:forEach>
						 		</ul>
						 		<p><b>Pustaka Pendukung</b></p>
						 		<ul>
						 		<c:forEach items="${dpList}" var="dp"> 
						 			<c:if test="${dp.pustaka.sifatPustaka == 'P'}">
						 				<li>${dp.pustaka.namaPustaka}</li> 
						 			</c:if> 
						 		</c:forEach>
						 		</ul>
							</div>
						 </div>
					</div>
				 </div>
			</div>
		</div>
		
		<content tag="scripts">
			<%@include file="footer.jsp" %>
			
			<script>
				var context_path = "${pageContext.servletContext.contextPath}/";
			</script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/select2/js/select2.min.js" type="text/javascript"></script>
			<script type="text/javascript"
				src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/modernizr.js"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/snap.svg-min.js"></script>
			<script type="text/javascript"
				src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/TableTools/js/dataTables.tableTools.min.js"></script>
			<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js"
				rel="stylesheet" type="text/javascript"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-blockui/jquery.blockui.js"
				type="text/javascript"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js"
				type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/js/date.js" type="text/javascript" ></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script> 
				$(document).ready(function(){
					printSilabus = function printSilabus(button){
						var mk = $(button).attr('name'); 
						window.open(context_path+'modul/kurikulum/silabus/kelola/laporan/'+mk,'_blank');
					}
				});
			</script>
		</content>
	</body>
</html>
