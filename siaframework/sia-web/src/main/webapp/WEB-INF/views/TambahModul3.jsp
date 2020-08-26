<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
				<c:if test="${empty notWizard || notWizard == false}">
					<div class="progress">
						<div class="progress-bar" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 75%;"></div>
					</div>	
				</c:if>
				<div id="upload-form" class="row">
					<div class="col-md-12">
			    		<p>
                       		<strong>Unggah Gambar dan Icon Modul</strong>
                       	</p>
			    	</div>
					<div class="col-md-12">
						<form enctype="multipart/form-data" id="uploadForm" action="${pageContext.servletContext.contextPath}/admin/module/uploadWizard/3/submit" method="post">
							<div class="form-group">
								<c:choose>
									<c:when test="${not empty image}">
										<img id="imageThumbnail" src="data:image/png;base64,${image}" alt="module image" width="200" height="200"/>
									</c:when>
									<c:otherwise>
										<img id="imageThumbnail" src="${pageContext.servletContext.contextPath}/resources/images/img-icon.png" alt="module image" width="200" height="200"/>
									</c:otherwise>
								</c:choose>
								<input id="imageInput" type="file" accept=".jpg" name="imageFile" onchange="readUrl(this);"/>
								<c:if test="${not empty notWizard && notWizard == true}">
									<input type="text" name="moduleId" value="${moduleId}" hidden/>
								</c:if>
                            </div>
                            <br/>
                            <div class="form-group">
                            	<c:choose>
									<c:when test="${not empty icon}">
										<input id="iconName" type="text" class="form-control" name="iconName" placeholder="Pilih icon modul" readonly="readonly" style="cursor: pointer;" value="${icon}"/>
									</c:when>
									<c:otherwise>
										<input id="iconName" type="text" class="form-control" name="iconName" placeholder="Pilih icon modul" readonly="readonly" style="cursor: pointer;"/>
									</c:otherwise>
								</c:choose>
                            </div>
				    		<br/>
				        	<button type="submit" class="btn btn-info m-b-sm" id="submitImageBtn">Unggah</button>
				    	</form>
				    	<br/>
				    	<c:if test="${empty notWizard || notWizard == false}">
				    		<button type="submit" class="btn btn-info btn-back">Kembali</button>
			    		</c:if>
			    	</div>
			    	<div id="icon-list" class="col-md-12" style="display: none;">
						<ul class="bs-glyphicons-list clearfix list-unstyled">
                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-asterisk</span>
                            </li>
                            
                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-plus</span>
                            </li>
                            
                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-euro" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-euro</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-minus</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-cloud" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-cloud</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-envelope</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-pencil</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-glass" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-glass</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-music" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-music</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-search</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-heart</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-star</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-star-empty</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-user</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-film" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-film</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-th-large" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-th-large</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-th" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-th</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-th-list</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-ok</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-remove</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-zoom-in</span>
                            </li>
                            
                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-zoom-out</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-off</span>
                            </li>
                       
                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-signal" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-signal</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-cog</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-trash</span>
                            </li>
                            
                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-home</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-file</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-time" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-time</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-road" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-road</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-download-alt</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-download" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-download</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-upload" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-upload</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-inbox" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-inbox</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-play-circle" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-play-circle</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-repeat</span>
                            </li>
                            
                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-refresh</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-list-alt</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-lock</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-flag</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-headphones" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-headphones</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-volume-off" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-volume-off</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-volume-down" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-volume-down</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-volume-up" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-volume-up</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-qrcode" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-qrcode</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-barcode" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-barcode</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-tag" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-tag</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-tags" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-tags</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-book" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-book</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-bookmark</span>
                            </li>
                            
                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-print" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-print</span>
                            </li>
                            
                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-camera" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-camera</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-font" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-font</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-bold" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-bold</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-italic" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-italic</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-text-height" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-text-height</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-text-width" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-text-width</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-align-left</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-align-center" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-align-center</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-align-right" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-align-right</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-align-justify</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-list" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-list</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-indent-left" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-indent-left</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-indent-right" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-indent-right</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-facetime-video" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-facetime-video</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-picture</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-map-marker</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-adjust" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-adjust</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-tint" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-tint</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-edit</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-share" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-share</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-check" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-check</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-move" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-move</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-step-backward" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-step-backward</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-fast-backward" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-fast-backward</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-backward" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-backward</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-play" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-play</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-pause" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-pause</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-stop" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-stop</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-forward" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-forward</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-fast-forward" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-fast-forward</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-step-forward" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-step-forward</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-eject" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-eject</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-chevron-left</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-chevron-right</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-plus-sign</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-minus-sign</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-remove-sign</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-ok-sign</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-question-sign</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-info-sign</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-screenshot" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-screenshot</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-remove-circle</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-ok-circle</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-ban-circle</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-arrow-left</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-arrow-right</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-arrow-up</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-arrow-down</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-share-alt</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-resize-full" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-resize-full</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-resize-small" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-resize-small</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-exclamation-sign</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-gift" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-gift</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-leaf</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-fire</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-eye-open</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-eye-close</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-warning-sign</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-plane" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-plane</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-calendar</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-random" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-random</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-comment" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-comment</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-magnet" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-magnet</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-chevron-up</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-chevron-down</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-retweet" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-retweet</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-shopping-cart</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-folder-close" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-folder-close</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-folder-open</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-resize-vertical" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-resize-vertical</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-resize-horizontal" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-resize-horizontal</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-hdd" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-hdd</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-bullhorn" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-bullhorn</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-bell" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-bell</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-certificate" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-certificate</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-thumbs-up</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-thumbs-down</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-hand-right</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-hand-left" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-hand-left</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-hand-up" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-hand-up</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-hand-down" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-hand-down</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-circle-arrow-right" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-circle-arrow-right</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-circle-arrow-left" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-circle-arrow-left</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-circle-arrow-up</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-circle-arrow-down" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-circle-arrow-down</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-globe" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-globe</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-wrench</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-tasks" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-tasks</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-filter" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-filter</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-briefcase" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-briefcase</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-fullscreen</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-dashboard" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-dashboard</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-paperclip</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-heart-empty" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-heart-empty</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-link" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-link</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-phone" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-phone</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-pushpin" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-pushpin</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-usd</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-gbp" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-gbp</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sort" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sort</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sort-by-alphabet" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sort-by-alphabet</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sort-by-alphabet-alt" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sort-by-alphabet-alt</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sort-by-order" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sort-by-order</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sort-by-order-alt" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sort-by-order-alt</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sort-by-attributes" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sort-by-attributes</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sort-by-attributes-alt</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-unchecked" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-unchecked</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-expand" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-expand</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-collapse-down" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-collapse-down</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-collapse-up" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-collapse-up</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-log-in</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-flash" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-flash</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-log-out</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-new-window" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-new-window</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-record" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-record</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-save</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-open" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-open</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-saved" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-saved</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-import" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-import</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-export" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-export</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-send" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-send</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-floppy-disk</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-floppy-saved" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-floppy-saved</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-floppy-remove" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-floppy-remove</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-floppy-save</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-floppy-open" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-floppy-open</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-credit-card" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-credit-card</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-transfer</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-cutlery</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-header" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-header</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-compressed" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-compressed</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-earphone</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-phone-alt" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-phone-alt</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-tower" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-tower</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-stats</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sd-video" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sd-video</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-hd-video" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-hd-video</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-subtitles" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-subtitles</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sound-stereo" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sound-stereo</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sound-dolby" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sound-dolby</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sound-5-1" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sound-5-1</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sound-6-1" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sound-6-1</span>
                            </li>
                       
                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-sound-7-1" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-sound-7-1</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-copyright-mark" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-copyright-mark</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-registration-mark" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-registration-mark</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-cloud-download" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-cloud-download</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-cloud-upload</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-tree-conifer" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-tree-conifer</span>
                            </li>

                            <li class="icon-item" style="cursor: pointer;">
                                <span class="glyphicon glyphicon-tree-deciduous" aria-hidden="true"></span>
                                <span class="glyphicon-class">glyphicon-tree-deciduous</span>
                            </li>
                        </ul>
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
			window.location.href = contextPath + "/admin/module/uploadWizard/2";
		});
		
		$(".icon-item").hover(function() {
			$(this).children().css("color", "#f9f9f9");
			$(this).css("background-color", "#4E5E6A");
		}, function() {
			if(!$(this).hasClass("selected")) {
				$(this).children().css("color", "#4E5E6A");
				$(this).css("background-color", "#f9f9f9");	
			}
		});
		
		$(".icon-item").click(function() {
			$(".icon-item").children().css("color", "#4E5E6A");
			$(".icon-item").css("background-color", "#f9f9f9");
			$(".icon-item").removeClass("selected");
			$(this).children().css("color", "#f9f9f9");
			$(this).css("background-color", "#4E5E6A");
			$(this).toggleClass("selected");
			var iconName = $(this).children(".glyphicon-class").text();
			iconName = "glyphicon " + iconName;
			$("#iconName").val(iconName);
		});
		
		$("#iconName").click(function() {
			$("#icon-list").css("display", "");
		});
		
		function readUrl(input) {
			if(input.files && input.files[0]) {
				if(input.files[0].size <= 2097152) {
					var reader = new FileReader();
					reader.readAsDataURL(input.files[0]);
					reader.onload = function(e) {
						$("#imageThumbnail").attr("src", e.target.result);
					};
		      	} else {
		        	$("#imageThumbnail").attr("src", "#");
		        	$("#imageInput").replaceWith($("#imageInput").clone());
		        	toastr["error"]("Ukuran gambar tidak boleh melebihi 2 MB");	    	
		      	}
			}
		}
	</script>
	
	<c:if test="${uploadImageFailed != null}">
		<script>
			toastr["error"]("${uploadFailed.getMessage()}");
		</script>
	</c:if>
</content>
	</body>

</html>