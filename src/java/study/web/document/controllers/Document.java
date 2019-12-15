/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package study.web.document.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dzmitry
 */
public class Document extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Document</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Document at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

        switch (action == null ? "default" : action) {
            case "new":
                request.getRequestDispatcher("/WEB-INF/layouts/document/new_document.jsp").forward(request, response);
                break;
            case "edit":
                
                String id = request.getParameter("id");
                request.setAttribute("document", List.reader.search(id));
                request.getRequestDispatcher("/WEB-INF/layouts/document/edit_document.jsp").forward(request, response);
                break;
            case "default":
            default:
                request.setAttribute("list", List.reader.getList());
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
                
                if (List.reader.add(Integer.parseInt(id), title, content)) {
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
                
                study.web.document.entity.Document document = List.reader.search(id);
                        
                if (document != null) {
                    List.reader.delete(id);
                    List.reader.add(Integer.parseInt(id), title, content);
                    List.reader.save();
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
