<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="navbar-inner">
    <div class="sidebar-pusher">
        <a href="javascript:void(0);" class="waves-effect waves-button waves-classic push-sidebar">
            <i class="fa fa-bars"></i>
        </a>
    </div>
    <div class="logo-box">
        <a href="${pageContext.servletContext.contextPath}/" class="logo-text">
        	<img src="${pageContext.servletContext.contextPath}/resources/images/logo SIA new.png">
        </a>
    </div><!-- Logo Box -->
    <div class="topmenu-outer">
        <div class="top-menu">
            <ul class="nav navbar-nav navbar-left">
                <li>		
                    <a href="javascript:void(0);" class="waves-effect waves-button waves-classic sidebar-toggle"><i class="fa fa-bars"></i></a>
                </li>
                <li>		
                    <a href="javascript:void(0);" class="waves-effect waves-button waves-classic toggle-fullscreen"><i class="fa fa-expand"></i></a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
            	<li>
					<select id="role-select" name="idPeran" class="form-control" style="margin-top: 12px;">
						<option value="">-- Hak Akses --</option>
					</select>
            	</li>
            	<li>
					<select id="satman-select" name="idSatMan" class="form-control" style="margin-top: 12px;">
						<option value="">-- Satuan Manajemen --</option>
					</select>
            	</li>
            	<form id="hot-swap-role-form" method="post" action="${pageContext.servletContext.contextPath}/session/chooseUserRole/" style="display: none;">
            		<input id="hot-swap-role" type="text" name="idPeran">
            		<input id="hot-swap-satman" type="text" name="idSatMan">
            	</form>
                <li id="user-control-li" class="dropdown">
                    <a id="user-control-a" href="#" class="dropdown-toggle waves-effect waves-button waves-classic" data-toggle="dropdown">
	                    <c:choose>
	                    	<c:when test="${sessionScope.userRoleSession.getPengguna().getPd().getNamaPd() != null}">
	                    		<span class="user-name">${sessionScope.userRoleSession.getPengguna().getPd().getNamaPd()}<i class="fa fa-angle-down"></i></span>
	                    		<img class="img-circle avatar" src="${pageContext.servletContext.contextPath}/resources/images/Mahasiswa small 60x60 px.png" width="40" height="40" alt="">
	                    	</c:when>
	                    	<c:when test="${sessionScope.userRoleSession.getPengguna().getPtk().getNamaPtk() != null}">
	                    		<span class="user-name">${sessionScope.userRoleSession.getPengguna().getPtk().getNamaPtk()}<i class="fa fa-angle-down"></i></span>
	                    		<img class="img-circle avatar" src="${pageContext.servletContext.contextPath}/resources/images/Dosen small 60x60 px.png" width="40" height="40" alt="">
	                    	</c:when>
	                    	<c:otherwise>
	                    		<span class="user-name">${sessionScope.userRoleSession.getPengguna().getUsername()}<i class="fa fa-angle-down"></i></span>
	                    		<img class="img-circle avatar" src="${pageContext.servletContext.contextPath}/resources/images/Admin small 60x60 px.png" width="40" height="40" alt="">
	                    	</c:otherwise>
	                    </c:choose>
                    </a>
                    <ul class="dropdown-menu dropdown-list" role="menu">
                    	 <c:choose>
	                    	<c:when test="${sessionScope.userRoleSession.getPengguna().getPd().getNamaPd() != null}">
	                    		<li role="presentation"><a href="#"><i class="fa fa-user"></i>${sessionScope.userRoleSession.getPengguna().getPd().getNamaPd()}</a></li>
	                    		<li role="presentation"><a href="#"><i class="fa fa-user"></i>${sessionScope.userRoleSession.getPengguna().getPd().getNiPd()}</a></li>
	                    	</c:when>
	                    	<c:when test="${sessionScope.userRoleSession.getPengguna().getPtk().getNamaPtk() != null}">
	                    		<li role="presentation"><a href="#"><i class="fa fa-user"></i>${sessionScope.userRoleSession.getPengguna().getPtk().getNamaPtk()}</a></li>
	                    		<li role="presentation"><a href="#"><i class="fa fa-user"></i>${sessionScope.userRoleSession.getPengguna().getPtk().getNiPtk()}</a></li>
	                    	</c:when>
	                    </c:choose>
	                    <li role="presentation"><a href="#"><i class="fa fa-envelope"></i>${sessionScope.userRoleSession.getPengguna().getUsername()}</a></li>
                    	<li role="presentation" class="divider"></li>
                        <li role="presentation"><a href="${pageContext.servletContext.contextPath}/j_spring_security_logout"><i class="fa fa-sign-out m-r-xs"></i>Keluar</a></li>
                    </ul>
                </li>
            </ul><!-- Nav -->
        </div><!-- Top Menu -->
    </div>
</div>