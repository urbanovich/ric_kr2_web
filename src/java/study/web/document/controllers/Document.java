/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package study.web.document.controllers;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import study.ejb.document.DocumentRemote;

/**
 *
 * @author dzmitry
 */
public class Document extends HttpServlet {
    
    @EJB
    private DocumentRemote document;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String id;

        switch (action == null ? "default" : action) {
            case "new":
                request.getRequestDispatcher("/WEB-INF/layouts/document/new_document.jsp").forward(request, response);
                break;
            case "edit":
                
                id = request.getParameter("id");
                request.setAttribute("document", this.document.search(id));
                request.getRequestDispatcher("/WEB-INF/layouts/document/edit_document.jsp").forward(request, response);
                break;
            case "delete":
                id = request.getParameter("id");
                
                study.ejb.document.entity.Document document = this.document.search(id);
                        
                if (document != null) {
                    this.document.delete(id);
                    this.document.save();
                    request.setAttribute("addSuccess", "Документ удален");
                } else {
                    request.setAttribute("addError", "Документ не найден");
                }
                
                response.sendRedirect(request.getContextPath() + "/List");
                break;
            case "default":
            default:
                request.setAttribute("list", this.document.getList());
                request.getRequestDispatcher("/WEB-INF/layouts/document/index.jsp").forward(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        String id, title, content;
        
        switch (action == null ? "default" : action) {
            case "new":
                id = request.getParameter("id");
                title = request.getParameter("title");
                content = request.getParameter("content");
                
                if (this.document.add(Integer.parseInt(id), title, content)) {
                    request.setAttribute("addSuccess", "Документ создан");
                } else {
                    request.setAttribute("addError", "Документ не создан");
                }
                
                this.doGet(request, response);
                break;
            case "update":
                id = request.getParameter("id");
                title = request.getParameter("title");
                content = request.getParameter("content");
                
                study.ejb.document.entity.Document document = this.document.search(id);
                        
                if (document != null) {
                    this.document.delete(id);
                    this.document.add(Integer.parseInt(id), title, content);
                    this.document.save();
                    request.setAttribute("addSuccess", "Документ обновлен");
                } else {
                    request.setAttribute("addError", "Документ не найден");
                }
                
                response.sendRedirect(request.getContextPath() + "/List");
                break;
            case "default":
            default:
                response.sendRedirect(request.getContextPath() + "/List");
                break;
        }
    }
}
