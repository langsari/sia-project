<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Tambah Modul | Sistem Informasi Akademik</title>
	</head>
	<body>
		<div class="row">
			<div class="col-md-12">
				<div id="panel-unggah" class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Tambah Modul</h4>
					</div>
					<div class="panel-body">
						<div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100" style="width: 25%;"></div>
						</div>	
						<div id="upload-form" class="row">
							<div class="col-md-12">
					    		<p>
		                           	<strong>Unggah Modul</strong>
		                           </p>
					    	</div>
							<div class="col-md-12">
								<form enctype="multipart/form-data" id="uploadForm" action="${pageContext.servletContext.contextPath}/admin/module/uploadWizard/1/upload" method="post">
						    		<input id="fileInput" type="file" accept=".jar, .war" name="file"/>
						    		<br/>
						        	<button type="submit" class="btn btn-info m-b-sm" id="addModuleBtn">Unggah</button>
						    	</form>
					    	</div>
				    	</div>
					</div>
				</div>
				
			</div>
		</div>
		
		<c:if test="${uploadFailed != null}">
			<content tag=scripts>
				<script>
					toastr["error"]("${uploadFailed.getMessage()}");
				</script>
			</content>
		</c:if>
		
	</body>

</html>
