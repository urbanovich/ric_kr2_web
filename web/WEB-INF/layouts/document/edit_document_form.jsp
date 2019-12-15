<%-- 
    Document   : content
    Created on : Oct 2, 2019, 3:33:52 PM
    Author     : dzmitry
--%>
<%@page import="study.ejb.document.entity.Document"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Document document = (Document) request.getAttribute("document"); %>
<form action="${pageContext.request.contextPath}/Document?action=update&id=<% out.print(document.getId()); %>" method="post">
    <div class="form-group">
        <label for="title">Номер документа</label>
        <input type="text" class="form-control" id="id" name="id" value="<% out.print(document.getId()); %>">
    </div>
    <div class="form-group">
        <label for="title">Название документа</label>
        <input type="text" class="form-control" id="title" name="title" value="<% out.print(document.getTitle()); %>">
    </div>
    <div class="form-group">
        <label for="content">Содержание документа</label>
        <textarea cols="10" rows="10" id="content" class="form-control" name="content"><% out.print(document.getContent()); %></textarea>
    </div>
    
    <button type="submit" class="btn btn-default">Отправить</button>
</form>