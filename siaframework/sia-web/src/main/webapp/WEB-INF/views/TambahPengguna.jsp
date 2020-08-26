<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
	
<html>

	<head>
		<title>Sistem Informasi Akademik - Tambah Pengguna</title>
	</head>
	
	<body>
		<div class="row" id="masterpage">

			<div class="col-md-12">
			
				<div class="panel panel-white" >
				
				    <div class="panel-heading">
				        <h4 class="no-m m-b-lg">Tambah Pengguna</h4>
				    </div>
				    
				    <div class="panel-body">
				        
				        <br/>
				        
				        <div id="choose" class="row">
				    		<div class="col-md-1">
				    			<button type="button" class="btn btn-primary m-b-sm" id="multi-add-btn">Impor CSV</button>
				    		</div>
				    		<div class="col-md-1">
				    			<button type="button" class="btn btn-primary m-b-sm" id="one-add-btn">Tambah Manual</button>
				    		</div>
				    	</div>
				    
				    	<br/>
				    
				    	<div id="by-csv" class="row" hidden=true>
				    		<div class="col-md-6">
				    			<form enctype="multipart/form-data" id="upload-csv" action="${pageContext.servletContext.contextPath}/admin/user/add/upload" method="post">
						    		<input id="fileInput" type="file" accept=".csv" name="file"/>
						    		<br/>
						        	<button type="submit" class="btn btn-primary m-b-sm" id="upload-csv-btn">Unggah</button>
						    	</form>
				    		</div>
				    		<div class="col-md-6">
				    			<p>
				    				Format Kolom CSV adalah sebagai berikut<br/>
				    				Kolom 1. Nomor induk peserta didik atau pendidik dan tenaga kependidikan<br/>
				    				Kolom 2. Username<br/>
				    				Kolom 3. Tipe pengguna (Peserta Didik / Pendidik dan Tenaga Kependidikan)<br/>
				    				Kolom 4. Nama satuan manajemen. <a href="${pageContext.servletContext.contextPath}/admin/satMan" target="_blank">Klik</a> untuk melihat daftar<br/>
				    			</p>
				    		</div>
				    	</div>
				    
				    	<div id="by-one" class="row" hidden=true>
				    	
				    		<div class=" col-md-4">
						    		
					    		<form action="${pageContext.servletContext.contextPath}/admin/user/add/" method="post">
					    			<div class="form-group">
								    	<label for="nrp">NRP</label>
								    	<input type="text" class="form-control" id="nrp" placeholder="NRP" name="ni">
								  	</div>
					    			<div class="form-group">
								    	<label for="username">Username</label>
								    	<input type="email" class="form-control" id="username" placeholder="Username" name="username">
								  	</div>
								  	<div class="form-group">
										<select class="form-control" id="sel-type" name="tipe">
						    				<c:forEach items="${userTypes}" var="userType">
												<option>${userType}</option>
											</c:forEach>
									    </select>
								  	</div>
								  	<div class="form-group">
									  	<select class="form-control" id="sel-satman" name="satMan">
											<c:forEach items="${satManList }" var="satMan">
												<option id="${satMan.getIdSatMan()}">${satMan.getNmSatMan()}</option>
											</c:forEach>
									    </select>
								  	</div>
								  	<button type="submit" class="btn btn-primary">Submit</button>
								</form>
				    		
				    		</div>
				    	</div>
				        
				    </div>
				   
				</div>
			</div>
		</div>
		
		<content tag="scripts">
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/uniform/jquery.uniform.min.js"></script>
			
			<script>
				
				$("#multi-add-btn").click(function() {
					$("#by-one").prop("hidden", true);
					$("#by-csv").prop("hidden", false);
				});
				
				$("#one-add-btn").click(function() {
					$("#by-csv").prop("hidden", true);
					$("#by-one").prop("hidden", false);
				});
			
			</script>
		</content>
	</body>	
	
</html>