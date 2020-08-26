<%@page import="java.util.ArrayList"%>
<%@page import="com.sia.modul.domain.PertemuanPembelajaran"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/css/dataTables.colVis.min.css">
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	
	<div class="row">
			<div class="col-md-8 col-md-offset-2" >
				<div class="col-md-4">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title" id="title">Laporan Berita Acara</h4>
							<h4 class="panel-title">${pemb.mk.namaMK} ${pemb.nmPemb}</h4>
						</div>
						<div class="panel-body">
						</div>
					</div>
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title" id="title">Pendidik</h4>
						</div>
						<div class="panel-body">
							<ul>
								<c:forEach items="${listPendidikPengajar}" var="pendidikPengajar">
									<li>${pendidikPengajar.ptk.nmPtk}</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-md-8">
					<c:if test="${max==0 }">
						<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title" id="title">Belum ada berita acara untuk pembelajaran ini</h4>
							</div>
							<div class="panel-body">
							</div>
						</div>
					</c:if>
					<c:forEach items="${listPertemuanPembelajaran}" var="pertemuanPembelajaran" varStatus="status">
						<div class="pertemuanPembelajaran" id="pertemuanPembelajaran-${status.index+1}" <c:if test="${status.index!=0 }"> style="display:none" </c:if> >
							<div class="panel panel-white">
								<div class="panel-heading">
									<h4 class="panel-title" id="title">Pembelajaran ke ${ pertemuanPembelajaran.pertemuan}</h4>
								</div>
								<div class="panel-body">
									${ pertemuanPembelajaran.tglPertemuan}
								</div>
							</div>
							<div class="panel panel-white">
								<div class="panel-heading">
									<h4 class="panel-title" id="title">Materi</h4>
								</div>
								<div class="panel-body">
									${pertemuanPembelajaran.materi}
								</div>
							</div>
							<div class="panel panel-white">
								<div class="panel-heading">
									<h4 class="panel-title" id="title">Kendala Perkuliahan</h4>
								</div>
								<div class="panel-body">
									${pertemuanPembelajaran.kendalaPerkuliahan}
								</div>
							</div>
							<div class="panel panel-white">
								<div class="panel-heading">
									<h4 class="panel-title" id="title">Tanggapan Peserta Didik</h4>
								</div>
								<div class="panel-body">
									${pertemuanPembelajaran.tanggapanPd}
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
			
		
			<div class="row" >
				<div class="col-md-8 col-md-offset-2" >
					<div class="panel panel-white">
						<div class="panel-body">
							<div class="btn-group pull-right" role="group" aria-label="First group">
								<button id="button-prev" onClick="prev()" type="button" class="btn btn-default"><i class="fa fa-caret-left"></i>|</button>
								<c:forEach items="${listPertemuanPembelajaran}" var="pertemuanPembelajaran" varStatus="status">
									<button id="button-${status.index+1}" onClick="show(${status.index }+1)" type="button" class="btn btn-default button-pagging" <c:if test="${status.index>4}"> style="display:none"</c:if> >${status.index+1}</button>
								</c:forEach>
								<button id="button-next" onClick="next()" type="button" class="btn btn-default">|<i class="fa fa-caret-right"></i></button>
                            </div>
						</div>
					</div>
				</div>
			</div>
				
	
		<content tag="scripts">			
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
			<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>
			<script type="text/javascript">
			var page = 1;
			var max = ${max}; 
			
			var showPagging= function(){
				if(max>5)
				{
					if((page-2)>1) showButton=page-2;
					else showButton=1;
					if((max-showButton)<4) showButton=max-4;
					$(".button-pagging").hide();
					for(var i=showButton;i<showButton+5;i++)
					{
						$("#button-"+i).show();
					}
				}
			}
			
			var show = function(nomor){
				$(".pertemuanPembelajaran").hide();
				$("#pertemuanPembelajaran-"+nomor).show();
				page = nomor;
				showPagging();
			}
			var next = function(){
				if(page!=max)
				{
					page = page+1;
					$(".pertemuanPembelajaran").hide();
					$("#pertemuanPembelajaran-"+page).show();
					showPagging();
				}
			}
			var prev = function(){
				if(page>1)
				{
					page = page-1;
					$(".pertemuanPembelajaran").hide();
					$("#pertemuanPembelajaran-"+page).show();
					showPagging();
				}
			}
		</script>
		<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>