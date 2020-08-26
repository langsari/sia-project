<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
	
<html>

	<head>
		<title>Sistem Informasi Akademik - Daftar Satuan Manajemen</title>
	</head>
	<body>
		<div class="row">
		
			<div class="col-md-12">
			
				<div class="panel panel-white" >
				
				    <div class="panel-heading">
				        <h4 class="no-m m-b-lg">Daftar Satuan Manajemen</h4>
				    </div>
				    
				    <div class="panel-body">
				        
				        <div class="row">
					    	<div class="table-responsive col-md-12">
					            <table id="satMan-table" class="display table table-striped" style="width: 100%; cellspacing: 0;">
					                <thead>
					                    <tr>
					                        <th>Satuan Manajemen</th>
					                    </tr>
					                </thead>
					         
					                <tfoot>
					                    <tr>
					                        <th>Satuan Manajemen</th>
					                    </tr>
					                </tfoot>
					                
					                <tbody>
					                	<c:forEach items="${satManList}" var="satMan">
					                		<tr id="${satMan.getIdSatMan()}">
						                        <td>${satMan.getNmSatMan()}</td>
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
	
	</body>	
	
	<content tag="scripts">
		$('#user-table').DataTable();
	</content>
	
</html>