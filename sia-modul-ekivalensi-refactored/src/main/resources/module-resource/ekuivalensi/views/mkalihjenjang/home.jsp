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
			<div class="col-md-12" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Matakuliah Alih Jenjang</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-4">
<!-- 								<div class="form-group"> -->
<!-- 									<label>Status Aktif</label> -->
<!-- 									<select id="filter" name="filter" class='form-control'> -->
<!-- 										<option value="">Semua</option> -->
<!-- 										<option value="false">Aktif</option> -->
<!-- 										<option value="true">Tidak Aktif</option>													 -->
<!-- 									</select> -->
<!-- 								</div> -->
								<div class="form-group">
									<label>Katalog</label>
									<select id="filter-katalog" name="filter" class='form-control'>
										<option value="">Semua</option>
										<c:forEach items="${listKatalogAlihjenjang}" var="katalogAlihjenjang">
										<option value="${katalogAlihjenjang.idKatalogAlihjenjang}">${katalogAlihjenjang.nmKatalog}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-8 masteractions panel-body">
								<div class="btn-action pull-right"></div>
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
											<td>Katalog</td>
											<td>Kode</td>	
											<td>Matakuliah</td>		
											<td>SKS</td>
											<td>Deskripsi</td>
											<td>Status</td>
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
			<div class="col-md-6 col-md-offset-3" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Kelola Matakuliah</h4>
					</div>
					<div class="panel-body">
						<form:form role="form" commandName="mkAlihjenjang" class="formdetail">
							<div class="form-group">
								<label>Katalog</label>
								<select id="katalogAlihjenjang" name="idKatalogAlihjenjang" class="form-control">								
									<c:forEach items="${listKatalogAlihjenjang}" var="katalogAlihjenjang">
										<option value="${katalogAlihjenjang.idKatalogAlihjenjang}">${katalogAlihjenjang.nmKatalog}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label>Kode Matakuliah</label>
								<form:input path="kodeMKAlihjenjang" class="form-control" placeholder="Berisi Kode Matakuliah" />
							</div>
							<div class="form-group">
								<label>Nama Matakuliah</label>
								<form:input path="nmMKAlihjenjang" class="form-control" placeholder="Berisi Nama Matakuliah" />
								<form:hidden path="idMKAlihjenjang" class="form-control" />
							</div>										
							<div class="form-group">
								<label>SKS</label>
								<form:input path="jumlahSKS" class="form-control" placeholder="Berisi jumlah sks" />
							</div>
							<div class="form-group">
								<label>Deskripsi</label>
								<form:input path="deskripsiMKAlihjenjang" class="form-control" placeholder="Berisi deskripsi matakuliah" />
							</div>
							<div class="form-group detailcontrol">
							</div>
				        </form:form>
					</div>
				</div>
			</div>
		</div>			
<!-- akhir dari content content -->
		<content tag="scripts">			
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
			<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>					
			<script src="${pageContext.servletContext.contextPath}/resources/js/mkalihjenjang/home.js" type="text/javascript" ></script>
			<jsp:include page="../Footer.jsp" />
		</content>
	</body>
</html>