<%-- 
    Document   : content
    Created on : Jun 2, 2019, 3:33:52 PM
    Author     : dzmitry
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="study.web.document.entity.Document"%>
<table class="table table-striped">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Название документа</th>
            <th scope="col">Содержание</th>
        </tr>
    </thead>
    <tbody>
        <% List<Document> documents = (ArrayList<Document>) request.getAttribute("list");
            for (Document document : documents) {%>
                <tr>
                    <th scope="row">
                        <a href="${pageContext.request.contextPath}/Document?action=edit&id=<% out.print(document.getId()); %>">
                            <% out.print(document.getId()); %>
                        </a>
                    </th>
                    <td><% out.print(document.getTitle()); %></td>
                    <td><% out.print(document.getContent()); %></td>
                </tr>
        <% }%>
    </tbody>
</table>