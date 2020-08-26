<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Modern | Layouts - Horizontal Menu</title>
		
		<meta content="width=device-width, initial-scale=1" name="viewport" />
		<meta charset="UTF-8">
		<meta name="description" content="Admin Dashboard Template" />
		<meta name="keywords" content="admin,dashboard" />
		<meta name="author" content="Steelcoders" />
		
		<!-- Styles -->
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/uniform/css/uniform.default.min.css" rel="stylesheet" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/line-icons/simple-line-icons.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/switchery/switchery.min.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/slidepushmenus/css/component.css" rel="stylesheet" type="text/css" />
		
		<!-- Theme Styles -->
		<link href="${pageContext.servletContext.contextPath}/resources/css/custom.css"	rel="stylesheet" type="text/css" />
		<script	src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/modernizr.js"></script>
		<script	src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/snap.svg-min.js"></script>
		
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/css/jquery.dataTables.min.css">
		<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/css/dataTables.colVis.min.css">
		<!-- optional -->
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
		<!-- optional -->

		<link href="${pageContext.servletContext.contextPath}/resources/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css" />
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
		<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/js/mkluar/home.js" type="text/javascript" ></script>
		
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
		        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		        <![endif]-->
		<script>
			var context_path = "${pageContext.servletContext.contextPath}/";
		</script>
		<title>Datatable</title>
	</head>
	<body class="page-header-fixed page-horizontal-bar">
							
					<div class="row" id="masterpage">
						<div class="col-md-12" style="margin-bottom:10px;">
							<div class="panel panel-white">
								<div class="panel-heading clearfix">
									<h4 class="panel-title">Calon Peserta Didik</h4>
								</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-md-4">
											<div class="form-group">
												<label>Status Aktif</label>
												<select id="filter" name="filter">
													<option value="">Semua</option>
													<option value="false">Aktif</option>
													<option value="true">Tidak Aktif</option>													
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
															<div class="checkbox">
																<input class="checkbox-all" type="checkbox" id="flat-checkbox-1">
															</div>
														</td>
														<td>Pedoman Ekuivalensi</td>
														<td>Nama Matakuliah</td>									
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
			<!-- akhir dari content content -->
				</div>
		<!-- Page Content -->	
		<!-- Javascripts -->
		<content tag=”scripts”>
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/switchery/switchery.min.js"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/uniform/jquery.uniform.min.js"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/main.js"></script>
		</content>
	</body>
</html>