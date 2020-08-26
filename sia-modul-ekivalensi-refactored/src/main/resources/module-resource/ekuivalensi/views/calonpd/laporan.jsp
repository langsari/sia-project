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
	
		<div class="row" id="masterpage" style="">
			<div class="col-md-8 col-md-offset-2" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Riwayat Ekuivalensi</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-8">
								<div class="col-md-12">
									<table class='table borderless'>
										<tr>
											<td>Satuan Manajemen</td>
											<td>
												<select id="filter-satman" class='form-control'>
													<option value="">--Semua--</option>
													<c:forEach items="${listSatManProdi}" var="satMan">
													<option value="${satMan.idSatMan}">${satMan.nmSatMan}</option>
													</c:forEach>
												</select>
											</td>
										</tr>
										<tr>
											<td>Peserta Didik Alih Jenjang</td>
											<td>
												<input type='text' id='nmCalonPD' class='form-control' />
												<input type='hidden' id='idCalonPD' />
											</td>
										</tr>
									</table>
									<button id="cetak" class="btn btn-primary" onclick="cetak()" disabled><i class='fa fa-print'></i> Cetak</button>												
								</div>
							</div>
							<div class="col-md-12">
								<div class="table-responsive">
									<table id='tabel-riwayat'style='display:none' class="table borderless">
										<thead>
											<th>Kode</th>
											<th>Matakuliah</th>
											<th>SKS</th>
											<th>Sifat</th>
											<th>Status</th>
										</thead>
										<tbody id='body-riwayat'>
										</tbody>
									</table>
								</div>
							</div>										
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		<div id="modal-peserta-didik" class="modal fade">
			<div class="modal-dialog modal-lg">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title">Peserta Didik ALih Jenjang</h4>
		      		</div>
		      		<div class="modal-body">
				      	<div id="masterpage-modal-peserta-didik">
							<div class="form-group">
		                    </div>
							<form class="tableform">
								<div class="table-responsive">
									<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable" style="width:100%">
										<thead>
											<tr>
												<td>
													#
												</td>									
												<td>Satuan Manajemen</td>
												<td>Calon Peserta Didik</td>									
												<td>Perguruan Tinggi Asal</td>
												<td>Katalog Alihjenjang</td>
												<td>Status Ekuivalensi</td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</form>
						</div>
		      		</div>
			        <div class="modal-footer">					        	
			        </div>
		    	</div><!-- /.modal-content -->
		  	</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	
		<content tag="scripts">			
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
			<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>					
			<script src="${pageContext.servletContext.contextPath}/resources/js/calonpd/laporan.js" type="text/javascript" ></script>
			<jsp:include page="../Footer.jsp" />
		</content>
	</body>
</html>