<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<html>
	<head> 
		<title>Pengelolaan Modul | Sistem Informasi Akademik</title>
	</head>
	
	<body>
		<div class="row" id="masterpage">

			<div class="col-md-12">
			
				<div class="panel panel-white" >
				
				    <div class="panel-heading">
				        <h4 class="no-m m-b-lg">Pengelolaan Modul</h4>
				    </div>
				    
				    <div class="panel-body">
				        
				        <br/>
				        
				        <div class="row">
				        	<div class="col-md-8">
				        	
				        		<div class="col-md-12">
				        			<button id="btn-add" type="button" class="btn btn-info">Tambah Modul</button>
				        		</div>
				        		
				        		<div class="table-responsive col-md-12">
					            	<table class="table table-striped">
							            <thead>
							               	<tr>
							               		<th></th>
								               	<th>Modul</th>
												<th>Mapping URL</th>
												<th>Versi</th>
												<th>Status</th>
												<th>Aksi</th>
							               	</tr>
							            </thead> 
					            		<tbody>
					            			<c:choose>
					            				<c:when test="${fn:length(moduleList) > 0}">
					            					<c:set var="i" value="${0}" />
					            					<c:forEach items="${moduleList}" var="module">
					            						<tr id="${module.getIdModul()}">
					            							<c:set var="i" value="${i+1}"/>
					            							<td class="module-item" style="cursor: pointer;"><span class="glyphicon ${module.getNamaIconTemplate()}"></span></td>
					                                        <td class="module-item" style="cursor: pointer;">${module.getNamaModul()}</td>
					                                        <td class="module-item" style="cursor: pointer;">${module.getUrlMapping()}</td>
					                                        <td class="module-item" style="cursor: pointer;">${module.getVersi()}</td>
					                                        <td class="module-item" style="cursor: pointer;">${module.getStatus()}</td>
					                                        <td>
					                                        	<button type="button" class="role-menu btn btn-info">Akses Menu</button>
					                                        	<button type="button" class="module-delete btn btn-danger">Hapus</button>
					                                        <td>
					                                    </tr>
					            					</c:forEach>
					            				</c:when>
					            				<c:otherwise>
					            					<tr class="odd">
					            						<td valign="top" colspan="5" class="dataTables_empty">Tidak ada data</td>
					            					</tr>
					            				</c:otherwise>
					            			</c:choose>
					            		</tbody>
					            	</table>
						        </div>
						        
				        	</div>
				        					        	
				        	<div class="col-md-4">
				        		<c:forEach items="${moduleList}" var="module">
				        			<div id="${module.getIdModul()}-detail" class="module-detail-item" style="display: none;">
						        		<div class="col-md-12">
						        			<img src="data:image/png;base64,${module.getBase64EncodedImage()}" alt="Module image" width="200" height="200"/>
						        			<br/>
						        			<button type="button" class="change-img btn btn-info">Ganti gambar</button>
						        			<br/>		
						        			<p>
					    						Nama Modul: ${module.getNamaModul()}<br/>
								    			Url Modul: ${module.getUrlMapping()}<br/>
								    			Versi: ${module.getVersi()}<br/>
											</p>
						        		</div>
						        		<div class="table-responsive col-md-12">
								        	<table class="table table-striped">
									            <thead>
									               	<tr>
										               	<th>Menu</th>
														<th>Mapping URL</th>
									               	</tr>
									            </thead> 
							            		<tbody>
						           					<c:forEach items="${module.getMenus()}" var="menu">
						           						<tr id="${menu.getIdMenu()}">
					                                       	<td>${menu.getNamaMenu()}</td>
					                                       	<td>${menu.getUrlMenu()}</td>
					                                   	</tr>
						           					</c:forEach>
							            		</tbody>
							            	</table>
									    </div>
								   	</div>
				        		</c:forEach>
				        	</div>
				        	
				        </div>
				        
				    </div>
				   
				</div>
			</div>
		</div>
		
		<content tag="scripts">
			<script>
				$(".module-delete").click(function() {
					var idModul = $(this).parents("tr").attr("id");
					console.log(idModul);
					$.ajax({
						url: contextPath + "/admin/module/delete",
						type: 'POST',
						data: { "idModul" : idModul },
						success: function(response) {
							if(response.status == "OK") {
								toastr["success"](response.message);
								setTimeout(function(){
									window.location.href = contextPath + "/admin/module/";
								}, 3000);	
							} else {
								toastr["error"](response.message);		
							}
						}
					});
				});
				
				$("#btn-add").click(function() {
					window.location.href = contextPath + "/admin/module/uploadWizard/1";
				});
			
				$(".module-item").click(function() {
					$(".module-detail-item").css("display", "none");
					var moduleId = $(this).parent("tr").attr("id");
					moduleId = "#" + moduleId + "-detail";
					$(moduleId).css("display", "");
				});
				
				$(".role-menu").click(function() {
					var moduleId = $(this).parents("tr").attr("id");
					window.location.href = contextPath + "/admin/module/uploadWizard/2?moduleId=" + moduleId; 
				});
				
				$(".change-img").click(function() {
					var moduleId = $(this).parents(".module-detail-item").attr("id");
					var id = moduleId.split("-detail")[0];
					window.location.href = contextPath + "/admin/module/uploadWizard/3?moduleId=" + id;
				});
				
			</script>
			
			<c:if test="${uploadWizardDone != null}">
				<script>
					toastr["success"]("${uploadWizardDone.getMessage()}");
				</script>
			</c:if>
		</content>
		
	</body>

</html>