<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>

	<head>
		<title>Tambah Modul</title>	
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
							<div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div>
						</div>	
						
						<div id="detail-row" class="row" >
			            	<div class="col-md-12">
					    		<p>
		                           	<strong>Keterangan Akhir Modul</strong>
		                        </p>
					    	</div>
					    	
					    	<div class="col-md-6">
						    	<c:if test="${not empty modul.getGambar()}">
						    		<img id="modul-img" src="data:image/png;base64,${modul.getBase64EncodedImage()}" alt="Module image" width="200" height="200"/>
				        			<br/>
				        			<br/>
				        		</c:if>
					    		<p>
					    			Nama Modul: ${modul.getNamaModul()}<br>
					    			Url Modul: ${modul.getUrlMapping()}<br>
					    			Versi: ${modul.getVersi()}<br>
								</p>
							</div>
							
			                <div class="col-md-6">
			                	<table class="table table-striped">
			                		<thead>
			                			<tr>
			                				<th>Menu</th>
			                				<th>Peran</th>
			                           	</tr>
			                       	</thead>
			                       	<tbody>
			                       		<c:forEach items="${modul.getMenus()}" var="menu">
				                       		<tr>
				                       			<td>${menu.getNamaMenu()}</td>
				                       			<td>
				                       				<ul>
				                       					<c:forEach items="${menu.getDaftarMenuPeran()}" var="menuPeran">
				                       						<li>${menuPeran.getPeran().getNamaPeran()}</li>
				                       					</c:forEach>
				                       				</ul>
				                       			</td>
			                       			</tr>
			                       		</c:forEach>
			                       	</tbody>
			                  	</table>
			            	</div>
			            	
			            	<div class="col-md-12">
			            		<button type="submit" class="btn btn-info btn-back">Kembali</button>
				            	<form class="m-t-md" action="${pageContext.servletContext.contextPath}/admin/module/uploadWizard/4/end" method="post">
		                            <input type="text" name="wizardEnd" hidden="true">
			                        <button type="submit" class="btn btn-success">Selesai</button>
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
				$(".btn-back").click(function() {
					window.location.href = contextPath + "/admin/module/uploadWizard/3";
				});
			</script>
		</content>
	</body>

</html>