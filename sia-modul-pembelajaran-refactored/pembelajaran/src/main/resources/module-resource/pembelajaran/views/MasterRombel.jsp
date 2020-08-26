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
	<div class="row" id="masterpage">
				<div class="col-md-12">
				<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title" id="title">Rombel</h4>
							</div>
							<div class="panel-body">
					
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
						<div class="col-md-8 masteractions">
							<div class="btn-action pull-right">
							</div>
						</div>
					</div>
					<form class="tableform">
						<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
							<thead>
								<tr>
									<td>
										<input class="checkbox-all" type="checkbox" id="flat-checkbox-1">
									</td>
									<td>Nama Rombel</td>
									<td>Tanggal terbuat</td>
									<td>Status hapus</td>
									<td>Aksi</td>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						</div>
					</form>
				</div>
			</div>
		</div>
		</div>
		
		<div class="row" id="master-detail" style="display:none;">
				<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title" id="title">Detil Rombel</h4>
							</div>
							<div class="panel-body">
					
					<form:form role="form" commandName="rombel" class="formdetail">
						<div class="form-group">
							<label>Nama Rombel</label>
							<form:input path="nmRombel" class="form-control" placeholder="Berisi nama rombel" />
							<form:hidden path="idRombel" class="form-control" />
						</div>
						<div class="form-group detailcontrol">
						</div>
			        </form:form>
				</div>
			</div>
		</div>
		</div>
		
		<div class="row" id="isi-rombel" style="display:none">
				<div class="col-md-12">					
					<div class="row">
						<div id="anggota-rombel" class="col-md-12">
						
					<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title" id="form-title">Perwalian</h4>
							</div>
							<div class="panel-body">							
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label>Angkatan</label>
										<select id="anggota-rombel-filter" class="form-control">
											<option value="">Semua</option>
											<c:forEach items="${listAngkatan}" var="angkatan">
												<option value="${angkatan}">${angkatan}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-8 masteractions">
									<div class="btn-action pull-right">
										<button type="button" class="btn btn-default anggota-close" style="display:none">Tutup</button>
										<button type="button" class="btn btn-success anggota-add">Tambah</button>
									</div>
								</div>
							</div>
							<form class="tableform">
						<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
									<thead>
										<tr>
											<td>
												<input class="checkbox-all" type="checkbox" id="flat-checkbox-1">
											</td>
											<td>NIM</td>
											<td>Nama</td>
											<td>Angakatan</td>
											<td>Aksi</td>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
								</div>
							</form>
						</div>
						</div>
						</div>
						<div id="pesertadidik" class="col-md-6" style="display:none">
					<div class="panel panel-white">
						<div class="panel-heading">
								<h4 class="panel-title">Mahasiswa</h4>
							</div>
							<div class="panel-body">	
							<div class="row">
								<div class="col-md-8">
									<div class="form-group">
										<label>Prodi</label>
										<select id="satman-filter" class="form-control">
											<c:forEach items="${listSatMan}" var="satMan">
												<option value="${satMan.idSatMan}">${satMan.nmSatMan}</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<label>Angkatan</label>
										<select id="pesertadidik-filter" class="form-control">
											<option value="">Semua</option>
											<c:forEach items="${listAngkatan}" var="angkatan">
												<option value="${angkatan}">${angkatan}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-4 masteractions">
									<div class="btn-action pull-right">
										<button type="button" class="btn btn-primary tambahkan-all">Tambahkan</button>
									</div>
								</div>
							</div>
							<form class="tableform">
								<input type="hidden" id="idRombel-isi" name="idRombel"/>
						<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
									<thead>
										<tr>
											<td>
												<input class="checkbox-all" type="checkbox" id="flat-checkbox-1">
											</td>
											<td>NIM</td>
											<td>Nama</td>
											<td>Angakatan</td>
											<td>Aksi</td>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
								</div>
							</form>
						</div>
					</div>
					</div>
					</div>
					<hr/>
					<div class="panel panel-white">
							<div class="panel-body">
					<div class="form-group detailcontrol">
						<button type="button" onClick="kembali()" class="btn btn-primary cancel">Kembali</button>
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
			<script src="${pageContext.servletContext.contextPath}/resources/js/pembelajaran/rombel.js" type="text/javascript" ></script>
		
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
