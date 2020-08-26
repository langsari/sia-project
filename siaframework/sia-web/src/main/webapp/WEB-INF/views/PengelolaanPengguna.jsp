<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<html>

	<head>
		<title>Pengelolaan Pengguna | Sistem Informasi Akademik</title>
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/datatables/css/jquery.datatables.min.css" rel="stylesheet" type="text/css"/>	
	    <link href="${pageContext.servletContext.contextPath}/resources/plugins/datatables/css/jquery.datatables_themeroller.css" rel="stylesheet" type="text/css"/>	
	</head>
	
	<body>
	
		<!-- Modal -->
		<div class="modal fade" id="modal-update" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
		    	<div class="modal-content">
			      	<div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="myModalLabel">Ubah</h4>
			      	</div>
		      	
		   			<div class="modal-body">
		   				<form action="${pageContext.servletContext.contextPath}/admin/user/update" method="post">
		   					<div class="form-group" hidden=true>
						    	<label for="id">id</label>
						    	<input type="text" class="form-control" id="id" placeholder="id" name="id">
						  	</div>
			    			<div class="form-group">
						    	<label for="username">Username</label>
						    	<input type="email" class="form-control" id="username" placeholder="Username" name="username">
						  	</div>
						  	<div class="form-group">
							  	<select class="form-control" id="sel-satman-upd" name="satMan">
									<c:forEach items="${satManList }" var="satMan">
										<option id="${satMan.getIdSatMan()}">${satMan.getNmSatMan()}</option>
									</c:forEach>
							    </select>
						  	</div>
						  	<button type="submit" class="btn btn-default">Submit</button>
						</form>
		      		</div>
			      	
		    	</div>
		  	</div>
		</div>
		
		<div class="row" id="masterpage">
		
			<div class="col-md-12">
			
				<div class="panel panel-white" >
				
				    <div class="panel-heading">
				        <h4 class="no-m m-b-lg">Pengelolaan Pengguna</h4>
				    </div>
				    
				    <div class="panel-body">
				        
				        <div class="row">
				    		<div class="col-md-2">
				    			<button id="add-user" type="button" id="mass-delete-btn" class="btn btn-primary">Tambah</button>
				    		</div>
				    		
				    		<div class="col-md-offset-2"></div>
				    		
				    		<div class="col-md-2">
				    			<select class="form-control" id="sel-satman">
				    				<option>Satuan Manajemen</option>
									<c:forEach items="${satManList }" var="satMan">
										<option id="${satMan.getIdSatMan()}">${satMan.getNmSatMan()}</option>
									</c:forEach>
							    </select>
				    		</div>
				    		
				    		<div class="col-md-2">
				    			<select class="form-control" id="sel-type">
				    				<option>Tipe Pengguna</option>
				    				<c:forEach items="${userTypes}" var="userType">
										<option>${userType}</option>
									</c:forEach>
							    </select>
				    		</div>
				    		
				    		<div class="col-md-2" hidden="true">
				    			<select class="form-control" id="sel-year">
				    				<option>Angkatan</option>
				    				<c:forEach items="${angkatanList}" var="angkatan">
										<option>${angkatan}</option>
									</c:forEach>
							    </select>
				    		</div>
				    		
				    		<div class="col-md-2">
				    			<button type="button" id="mass-delete-btn" class="btn btn-danger">Hapus yang ditandai</button>
				    		</div>
				    		
				    	</div>
				    
					    <br/>		
				    
				    	<div class="row">
					    	<div class="table-responsive col-md-12">
					            <table id="user-table" class="display table table-striped" style="width: 100%; cellspacing: 0;">
					                <thead>
					                    <tr>
					                        <th>Nomor Induk</th>
					                        <th>Nama</th>
					                        <th>Username</th>
					                        <th>Satuan Manajemen</th>
					                        <th>Tipe</th>
					                        <th>Angkatan</th>
					                        <th>Aksi</th>
					                        <th>
					                        	<span id="all-marker" class=""><input type="checkbox"></span>
					                        </th>
					                    </tr>
					                </thead>
					         
					                <tfoot>
					                    <tr>
					                        <th>Nomor Induk</th>
					                        <th>Nama</th>
					                        <th>Username</th>
					                        <th>Satuan Manajemen</th>
					                        <th>Tipe</th>
					                        <th>Angkatan</th>
					                        <th>Aksi</th>
					                        <th>
					                        </th>
					                    </tr>
					                </tfoot>
					                
					                <tbody>
					                	<c:forEach items="${users}" var="user">
					                		<tr id="${user.getIdPengguna()}">
						                        <td class="ni">
							                        	<c:if test="${not empty user.getPd().getNamaPd()}">
							                        		${user.getPd().getNiPd()}
							                        	</c:if>
							                        	<c:if test="${not empty user.getPtk().getNamaPtk()}">
							                        		${user.getPtk().getNiPtk()}
							                        	</c:if>
							                    </td>
						                        <td class="nama">
						                        	<c:choose>
							                        	<c:when test="${not empty user.getPd()}">
							                        		${user.getPd().getNamaPd()}
							                        	</c:when>
							                        	<c:when test="${not empty user.getPtk()}">
							                        		${user.getPtk().getNamaPtk()}
							                        	</c:when>
							                        </c:choose>
						                        </td>
						                        <td class="username">${user.getUsername()}</td>
						                        <td class="satman">${user.getSatMan().getNmSatMan()}</td>
						                        <td class="tipe">${user.getTipePengguna().getNamaTipe()}</td>
						                        <td class="year">
							                        <c:if test="${not empty user.getPd().getAngkatanPd()}">
							                        	${user.getPd().getAngkatanPd()}	
							                        </c:if>
						                        </td>
						                        <td class="action">
						                        	<button type="button" class="btn btn-primary btn-role">Hak Akses</button>
						                        	<button type="button" class="btn btn-primary btn-update" data-toggle="modal" data-target="#modal-update">Ubah</button>
						                        	<button type="button" class="btn btn-danger btn-delete">Hapus</button>
						                        </td>
						                        <td class="marker">
						                        		<span class="user-marker"><input type="checkbox"></span>
					                        	</td>
						                    </tr>
					                	</c:forEach>
					                </tbody>
					            </table>
					        </div>
				    	</div>
				        
				    </div>
				   
				</div>
			</div>
		</div>
	
		<content tag="scripts">
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/uniform/jquery.uniform.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/datatables/js/jquery.datatables.min.js"></script>
			
			<c:if test="${addError != null}">
				<script>
					toastr["error"]("${addError.getMessage()}");
				</script>
			</c:if>
			<script>     
			   	var markedUsers = [];
			     
				var table = $('#user-table').DataTable({
					"columnDefs" : [{
						"targets": [4, 5],
						"visible": false
					},{
						"targets": [ 6, 7],
						"searchable": false,
						"sortable": false
					}]
				});
			
				$('#mass-delete-btn').click(function() {
					if(markedUsers.length > 0) {
						$.ajax({
							url: contextPath + "/admin/user/multiDelete",
							type: 'POST',
							contentType: 'application/json',
							data: JSON.stringify(markedUsers),
							success: function(result) {
								var status = result.status;
								if(status == "success") {
									toastr["success"](result.message);
								} else {
									toastr["error"](result.message);
								}
								setTimeout(function(){
									location.reload();
								}, 3000);
						 	}
						});	
					} else {
						toastr["error"]("mohon tandai pengguna terlebih dahulu");
					}
				});
			     
				$('#all-marker').click(function() {
					var rows = table.rows({ search:'applied' }).nodes().to$();
					if($(this).find("input").is(":checked")) {
						rows.each(function() {
							$(this).addClass("selected");
							$(this).find(".marker").find("span").find("input").prop("checked", true);
							$(this).find(".marker").find(".user-marker").find(".checker").find("span").addClass("checked");
							$(this).find(".marker").find(".user-marker").find(".checker").find("span").find("input").prop("checked", true);
							markedUsers.push($(this).attr("id"));
						});
					} else {
						rows.each(function() {
							$(this).removeClass("selected");
							$(this).find(".marker").find("span").find("input").prop("checked", false);
							$(this).find(".marker").find(".user-marker").find(".checker").find("span").removeClass("checked");
							$(this).find(".marker").find(".user-marker").find(".checker").find("span").find("input").prop("checked", false);
							var index = markedUsers.indexOf($(this).attr("id"));
							if(index > -1) markedUsers.splice(index, 1);
						});
					}
			   	});
			
				table.rows({ search:'applied' }).nodes().to$().each(function() {
					$(this).find(".marker").find(".user-marker").click(function() {
						var userId = $(this).parents("tr").attr("id");
						if($(this).find("input").is(":checked")) {
							$(this).parents("tr").addClass("selected");
							markedUsers.push(userId);
						} else {
							$(this).parents("tr").removeClass("selected");
							var index = markedUsers.indexOf(userId);
							if(index > -1) markedUsers.splice(index, 1);
						}
					});
					$(this).find(".action").find(".btn-delete").click(function() {
						var userId = $(this).parents("tr").attr("id");
						$.ajax({
			 				url: contextPath + "/admin/user/delete",
			 				type: 'POST',
			 				data: {"userId": userId},
			 				success: function(result) {
			 					var status = result.status;
			 					if(status == "success") {
			 						toastr["success"](result.message);
			 					} else {
			 						toastr["error"](result.message);
			 					}
			 					setTimeout(function(){
			 						location.reload();
			 					}, 3000);
			    			 	}
			    			});
					});
					$(this).find(".action").find(".btn-update").click(function() {
						var parentTr = $(this).parents("tr"); 
						var userId = parentTr.attr("id");
						var username = parentTr.find(".username").html();
						var satMan = parentTr.find(".satman").html();
						$("#id").val(userId);
						$("#username").val(username);
						$("#sel-satman-upd").val(satMan);
					});
					$(this).find(".action").find(".btn-role").click(function() {
						var parentTr = $(this).parents("tr"); 
						var userId = parentTr.attr("id");
						window.location.href = contextPath + "/admin/userRole?userId=" + userId;
					});
				});
			
				$('#sel-satman').change(function() {
					var val = $('#sel-satman').val();
					if(val.toLowerCase().indexOf("satuan manajemen") > -1) {
						val = "";
					} 
					table.column(3).search(val).draw();
				});
			   
				$('#sel-type').change(function() {
				 	var val = $('#sel-type').val();
				  	if(val.toLowerCase().indexOf("tipe pengguna") > -1) {
						val = "";
					} 
				  	if(val.toLowerCase().indexOf("pd") > -1) {
				  		$("#sel-year").parent().prop("hidden", false);
				  	} else {
				  		$("#sel-year").parent().prop("hidden", true);
				  	}
				  	table.column(4).search(val).draw();		 
				});
			   
				$('#sel-year').change(function() {
					var val = $('#sel-year').val();
					if(val.toLowerCase().indexOf("angkatan") > -1) {
						val = "";
					} 
					table.column(5).search(val).draw();
				});
			   
			   $("#add-user").click(function() {
				   window.location.href = contextPath + "/admin/user/add";	
			   });
			   
			</script>
		</content>
	
	</body>

</html>