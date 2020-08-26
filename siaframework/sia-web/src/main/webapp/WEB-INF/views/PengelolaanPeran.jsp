<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<html>
	<head>
		<title>Pengelolaan Peran | Sistem Informasi Akademik</title>
	</head>
	<body>
		<div class="row">
			<div class="col-md-12">
			
				<div class="panel panel-white">
				
				    <div class="panel-heading">
				        <h4 class="no-m m-b-lg">
				        	<c:choose>
				        		<c:when test="${chooseOnly == null}">Pengelolaan Peran</c:when>
				        		<c:otherwise>Pilih Peran</c:otherwise>
				        	</c:choose>
				        </h4>
				    </div>	
				    
				    <div class="panel-body">
				    
				    	<br/>
				    
				    	<div class="row">
					    	<div class="col-md-12">
					    		<button type="button" class="btn btn-info" data-toggle="modal" data-target="#modal-add">
					    			<c:if test="${chooseOnly == null}">Tambah</c:if>
					    		</button>
					    	</div>
				    	</div>
				        
				        <br/>
				        
				        <div class="modal fade" id="modal-add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" style="display: none;">
		                    <div class="modal-dialog modal-sm">
		                        <div class="modal-content">
		                            <div class="modal-header">
		                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                                <h4 class="modal-title" id="myModalLabel">Tambah Peran</h4>
		                            </div>
		                            <form action="${pageContext.servletContext.contextPath}/admin/role/add" method="post" >
			                            <div class="modal-body">
			                            	<div class="form-group"> 
				                                <input type="text" name="namaPeran" id="role-add" class="form-control" placeholder="Peran" required="true">
				                            </div>
			                            </div>
			                            <div class="modal-footer">
			                                <button type="submit" class="btn btn-info">Tambah</button>
			                            </div>
		                            </form>
		                        </div>
		                    </div>
		                </div>
		                
		                <div class="modal fade" id="modal-edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" style="display: none;">
		                    <div class="modal-dialog modal-sm">
		                        <div class="modal-content">
		                            <div class="modal-header">
		                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                                <h4 class="modal-title" id="myModalLabel">Ubah Peran</h4>
		                            </div>
		                            <form action="${pageContext.servletContext.contextPath}/admin/role/edit" method="post" >
			                            <div class="modal-body">
			                            	<div class="form-group"> 
			                            		<input type="text" name="idPeran" id="role-edit-id" class="form-control" required="" style="display: none;">
				                                <input type="text" name="namaPeran" id="role-edit" class="form-control" placeholder="Peran" required="">
				                            </div>
			                            </div>
			                            <div class="modal-footer">
			                                <button type="submit" class="btn btn-info">Ubah</button>
			                            </div>
		                            </form>
		                        </div>
		                    </div>
		                </div>
				        
				        <div class="col-md-3"></div>
				        
				        <div class="table-responsive col-md-6">
				        	
				            	<table class="table table-striped">
						            <thead>
						               	<tr>
						               		<th>#</th>
							               	<th>Peran</th>
							               	<c:if test="${chooseOnly == null}">
								               	<th>Ubah</th>
								               	<th>Hapus</th>
							               	</c:if>
							               	<th>Tetapkan Pengguna</th>
						               	</tr>
						            </thead>
				            		<tbody>
				            			<c:choose>
				            				<c:when test="${fn:length(daftarPeran) > 0}">
				            					<c:set var="i" value="${0}" />
				            					<c:forEach items="${daftarPeran}" var="peran">
				            						<tr id="${peran.getIdPeran()}">
				            							<c:set var="i" value="${i+1}" />
				            							<td><c:out value="${i}"/></td>
				                                        <td class="role-name">${peran.getNamaPeran()}</td>
				                                        <c:if test="${chooseOnly == null}">
					                                        <td><button type="button" class="btn btn-info edit" data-toggle="modal" data-target="#modal-edit">Ubah</button></td>
					                                        <td>
					                                        	<form action="${pageContext.servletContext.contextPath}/admin/role/delete" method="post">
					                                        		<input type="hidden" value="${peran.getIdPeran()}" name="idPeran"/>
					                                        		<button type="submit" class="btn btn-danger delete">Hapus</button>
					                                        	</form>
					                                        </td>
					                                    </c:if>
				                                        <td>
				                                        	<form method="post" action="${pageContext.servletContext.contextPath}/pengelolaanPeranPengguna/tetapkanPeran">
				                                        		<input type="hidden" value="${peran.getIdPeran()}" name="idPeran"/>
				                                        		<button class="btn btn-default">Tetapkan Pengguna</button>
				                                        	</form>
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
				        
				        <div class="col-md-3"></div>
				        
				    </div>
				   
				</div>
			</div>
		</div>
		
		<content tag="scripts">		
			<script>
				$(".edit").click(function() {
					var namaPeran = $(this).parents("tr").find(".role-name").html();
					var idPeran = $(this).parents("tr").attr("id");
					$("#role-edit").val(namaPeran);
					$("#role-edit-id").val(idPeran);
				});
			</script>
			
			<c:if test="${addResponse != null}">
				<script>
					var status = "${addResponse.getStatus()}";
					var msg = "${addResponse.getMessage()}";
					
					if(status == "error") {
						toastr["error"](msg);
					} else {
						toastr["success"](msg);
					}
				</script>
			</c:if>
		</content>
		
	</body>
	
</html>