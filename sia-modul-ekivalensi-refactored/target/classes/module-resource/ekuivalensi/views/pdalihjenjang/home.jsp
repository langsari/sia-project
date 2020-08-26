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
						<h4 class="panel-title">Peserta Didik Alih Jenjang</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label>Satuan Manajemen</label>
									<select id="filter-satman" name="filter" class='form-control'>
										<option value="">Semua</option>
										<c:forEach items="${listSatMan}" var="satMan">
										<option value="${satMan.idSatMan}">${satMan.nmSatMan}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<label>Katalog</label>
									<select id="filter-katalog" name="filter" class='form-control'>
										<option value="">Semua</option>
										<c:forEach items="${listKatalog}" var="katalog">
										<option value="${katalog.idKatalogAlihjenjang}">${katalog.nmKatalog}</option>
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
											<td>Satuan Manajemen</td>
											<td>Nama</td>									
											<td>Perguruan Tinggi Asal</td>
											<td>Katalog</td>
											<td>Status Ekuivalensi</td>
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
				<div class="panel-heading clearfix">
					<h4 class="panel-title">Kelola Peserta Didik Alih Jenjang</h4>
				</div>
				<div class="panel-body">
					<form:form role="form" commandName="calonPD" class="formdetail">
						<div class="form-group">
							<label>Satuan Manajemen</label>
							<select id="satMan" name="idSatMan" class="form-control">								
								<c:forEach items="${listSatMan}" var="satMan">
									<option value="${satMan.idSatMan}">${satMan.nmSatMan}</option>
								</c:forEach>
							</select>
						</div>
						<div class="checkbox">
							<label>
								<input type="hidden" id="idKatalogAlihjenjang-input" name="idKatalogAlihjenjang" />
								<input type="checkbox" id="penyetaraan-mk" />
								Lakukan proses penyetaraan matakuliah
							</label>
						</div>
						<div id='pedomanekuivalensi-field'>
							<div class="form-group">
								<label>Katalog</label>											
								<select id="katalogAlihjenjang" name="idKatalogAlihjenjang" class="form-control">								
									<c:forEach items="${listKatalog}" var="katalog">
										<option value="${katalog.idKatalogAlihjenjang}">${katalog.nmKatalog}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label>Nama Peserta Didik Alih Jenjang</label>
							<form:input path="nmCalonPD" class="form-control" placeholder="Berisi Nama Peserta Didik Alih Jenjang" />
							<form:hidden path="idCalonPD" class="form-control" />
						</div>
						
						<div class="form-group">
							<label>Tempat Lahir</label>
							<form:input path="tempatLahir" class="form-control" placeholder="Berisi Tempat Lahir" />
						</div>
									
						<div class="form-group">
							<label>Tanggal Lahir</label>
							<form:input path="tglLahir" class="form-control date-picker" placeholder="Berisi Tanggal Lahir" />
						</div>
						
						<div class="form-group">
							<label>Alamat</label>
							<form:input path="alamat" class="form-control" placeholder="Berisi Alamat" />
						</div>			
						
						<div class="form-group">
							<label>Perguruan Tinggi Asal</label>
							<form:input path="ptAsal" class="form-control" placeholder="Berisi Perguruan Tinggi Asal" />
						</div>
						
						<div class="form-group detailcontrol">
						</div>
			        </form:form>
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
			<script src="${pageContext.servletContext.contextPath}/resources/js/pdalihjenjang/home.js" type="text/javascript" ></script>
			<jsp:include page="../Footer.jsp" />
		</content>
	</body>
</html>