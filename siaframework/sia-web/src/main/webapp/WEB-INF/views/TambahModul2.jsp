<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<html>
	<head>
		<title>Tambah Modul</title>
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/nestable/nestable.css" rel="stylesheet" type="text/css">
	</head>
	<body> 
		<div class="row" id="masterpage">
			<div class="col-md-12">
				<div id="panel-unggah" class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Tambah Modul</h4>
					</div>
					<div class="panel-body">
						<div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: 50%;"></div>
						</div>
						<div id="assignment-row" class="row" >
						
							<div class="row">
								<div class="col-md-12">
						    		<p>
			                           	<strong>Kelola Akses Menu</strong>
			                        </p>
						    	</div>
					    	</div>
					    	
					    	<br/>
					    	
					    	<div class="row">
						    	<div class="col-md-12">
						    		<p>
						    			Pilih peran yang ingin dipetakan kemudian pilih menu untuk dipetakan dengan peran yang dipilih. <br/>
						    			Ulangi untuk semua peran.<br/>
						    		</p>
						    	</div>
					    	</div>
						    	
					    	<br/>
					    	
					    	<div class="row">
					    		<div class="col-md-6">
					    			<div class="col-md-12">
							    		<p>
							    			Daftar Peran
							    		</p>
							    	</div>
							    	<div id="role-list" class="col-md-12" style="min-height: 300px">
						                <div class="dd" id="nestable">
						                    <ol class="dd-list">
						                    	<c:forEach items="${roles}" var="role">
						                    		<li class="dd-item" data-id="1">
						                            	<div id="${role.getIdPeran()}" class="dd-handle role-item" style="cursor: pointer;">${role.getNamaPeran()}</div>
						                        	</li>
						                    	</c:forEach>
						                    </ol>
						                </div>
						            </div>
					    		</div>
					    		
					    		<div class="col-md-6">
						    		<div class="col-md-12">
							    		<p>
							    			Daftar Menu
							    		</p>
							    	</div>
							    	<div id="menu-list" class="col-md-12">
						                <div class="dd" id="nestable">
						                    <ol class="dd-list">
							                    <c:forEach items="${menus}" var="menu">
							                    	<li class="dd-item" data-id="1">
							                            <div id="${menu.getIdMenu()}" class="dd-handle role-menu" style="cursor: pointer;">${menu.getNamaMenu() }</div>
							                        </li>
							                    </c:forEach>
						                    </ol>
						                </div>
						            </div>
					    		</div>
					    	</div>
					    	
				            <div class="col-md-12">
				            	<button id="btn-submit" type="button" class="btn btn-info">Simpan</button>
				            </div>
				            
			            </div>
			            
					</div>		
				</div>
			</div>
		</div>
		
		<content tag="scripts">
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/uniform/jquery.uniform.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/js/pages/nestable.js"></script>
			<script>
				var notWizard = false;
			</script>
			<c:if test="${not empty notWizard && notWizard == true}">
				<script>
					notWizard = true;
					$(".progress").css("display", "none");
					$(".panel-title").text("Kelola Akses Menu");
					$("strong").css("display", "none");
				</script>
			</c:if>
			<script>
			
			    var selectedRole = null;
			    var roles = [];
				
				<c:forEach items="${roleMenus}" var="roleMenu">
					roles["${roleMenu.getRoleId()}"] = [];
					<c:forEach items="${roleMenu.getRoleMenus()}" var="item">
						roles["${roleMenu.getRoleId()}"].push("${item}");
					</c:forEach>
				</c:forEach>
			    
			    $(".role-item").click(function() {
			        $(".dd-handle").css({"background-color": "#fafafa", "color": "#333"});
			        $("#menu-list").css("display", "");
			        var idRole = $(this).attr("id");
			        if(! roles[idRole]) {
			            roles[idRole] = [];
			        } 
			        selectedRole = idRole;
			        $(this).css({"background-color": "#12AFCB", "color": "white"});
			        for(var index = 0; index < roles[idRole].length; index++) {
			            var idMenu = roles[idRole][index];
			            $("#" + idMenu + "").css({"background-color": "#12AFCB", "color": "white"});
			        }
			    });
			
			    $(".role-menu").click(function() {
			        if(selectedRole != null) {
			            var idMenu = $(this).attr("id");
			            if($.inArray(idMenu, roles[selectedRole]) == -1) {
			                roles[selectedRole].push(idMenu);   
			                $(this).css({"background-color": "#12AFCB", "color": "white"});
			            } else {
			                var index = $.inArray(idMenu, roles[selectedRole]);
			                roles[selectedRole].splice(index, 1);
			                $(this).css({"background-color": "#fafafa", "color": "#333"});
			            }
			        }
			    });
			    
			    $("#btn-submit").click(function() {
			    	if(selectedRole != null) {
			    		var roleMenus = [];
			    		$(".role-item").each(function() {
			    			var id = $(this).attr("id");
			    			roleMenus.push({roleId: id, roleMenus: roles[id]});
			    		});
			   			$.ajax({
			   				url: contextPath + "/admin/module/uploadWizard/2/submit",
			   				type: 'POST',
			   				contentType: 'application/json',
			   				data: JSON.stringify(roleMenus),
			   				success: function(response) {
								if(response.status == "OK") {
			   						redirect();
			   					} else {
			   						toastr["error"](response.message);
			   						setTimeout(function(){
				   						redirect();
				   					}, 3000);
			   					}	
			    			}
			    		});
			    	} else {
			    		toastr["error"]("mohon untuk mengisi hak akses sebelum melanjutkan");
			    	}
							
			    });
			    
			    function redirect() {
			    	if(!notWizard) {
							window.location.href = contextPath + "/admin/module/uploadWizard/3";
						} else {
							window.location.href = contextPath + "/admin/module/";
						}
			    }
			</script>
		</content>
		
	</body>
</html>

