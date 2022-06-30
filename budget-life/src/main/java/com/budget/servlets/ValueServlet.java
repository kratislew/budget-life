package com.budget.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.budget.beans.*;
import com.budget.dao.ApplicationDao;
import com.budget.inmemory.ApplicationInMemory;
import com.budget.services.ApplicationService;

/**
 * Servlet implementation class ValueServlet
 */
//@WebServlet(description = "Budget Life", urlPatterns = { "/budget" })
public class ValueServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ApplicationService values;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValueServlet() {
        super();
        this.values = new ApplicationInMemory();
        this.values = new ApplicationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            delete(request);
        }

        String title = "";
        double amount = 0.0;
        String description = "";
        String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            // Initialize id and continue with the rendering.
            id = "";
        } else {
            // Read the record from memory.
            Value value = this.values.readValue(id);
            if (value == null) {
                // Value not found, initialize id and continue with the rendering.
                id = "";
            } else {
                // Value found, initialize title, amount, and description.
                title = value.getTitle();
                amount = value.getAmount();
                description = value.getDesc();
            }
        }

        // Render response.
        String htmlResponse = printOutHead(request.getContextPath());
        htmlResponse += printOutBodyForm(id, title, String.valueOf(amount), description);
        // Read all logs, assign to local variable and sent to printOutBodyList
        Map<UUID, Value> values = this.values.readValues();
        htmlResponse += printOutBodyList(values);
        htmlResponse += printOutFoot();
        PrintWriter writer = response.getWriter();
        writer.write(htmlResponse);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Value value = null;
        String id = request.getParameter("id");
        String amount = request.getParameter("amount");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        if (id == null || "".equals(id)) {
            // Create the value.
            value = new Expense((Double.valueOf(amount)), title, description);
        } else {
            // Read the log.
            value = this.values.readValue(id);
            value.setTitle(title);
            value.setAmount(Double.valueOf(amount));
            value.setDesc(description);
        }
        // Update the log.
        this.values.createOrUpdateValue(value);

        // Process GET for rendering the page with updates.
        doGet(request, response);
    }

    private void delete(HttpServletRequest request) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.equals("null")) {
            // Remove the log.
            this.values.deleteValue(id);
        }
    }

    // This is the HTML code generated entirely from the Servlet.
    private String printOutHead(String root) {
        String out = "<!DOCTYPE html>\n" + "	<html lang=\"en\">\n" + "	    <head>\n"
                + "	        <title>Example</title>\n" + "         <body id=\"page-top\">\n";

        return out;

    }

    private String printOutBodyForm(String id, String title, String amount, String desc) {
        String out = "        <!-- Form Section-->\n"
                + "        <header class=\"masthead bg-primary text-white text-center\">\n"
                + "        <h1>Expense ahead</h1>\n" + "        <form action=\"budget\" method=\"post\">\n"
                + "          <input type=\"hidden\" name=\"id\" value=\"" + id + "\">"
                + "          <label for=\"title\">Title:</label><br>\n"
                + "          <input type=\"text\" id=\"title\" name=\"title\" value=\"" + title + "\"><br>\n"
                + "			 <label for=\"amount\">Amount:</label><br>\n"
                + "			 <input type=\"text\" id=\"amount\" name=\"amount\" value=\"" + amount + "\"><br>\n"
                + "          <label for=\"description\">Description:</label><br>\n"
                + "          <input type=\"text\" id=\"description\" name=\"description\" value=\"" + desc + "\"><br><br>\n"
                + "          <input type=\"submit\" value=\"Submit\">\n"
                + "          <input type=\"button\" value=\"Cancel\" onclick=\"window.location='budget'\">\n"
                + "        </form>\n" + "        </header>\n";
        return out;
    }

    private String printOutBodyList(Map<UUID, Value> values) {
        // Body list top.
        String out = "\n" + "        <!-- Content Section-->\n" + "            <!-- Budget Items-->\n"
                + "            <div class=\"row\">\n";
        // This is the actual List.
        out += "                  <table class=\"table\">\n" + "                    <thead>\n"
                + "                      <tr>\n" + "                        <th scope=\"col\" class=\"col-2\">#</th>\n"
                + "                        <th scope=\"col\" class=\"col-2\">Title</th>\n"
                + "                        <th scope=\"col\">Amount</th>\n"
                + "                        <th scope=\"col\">Description</th>\n"
                + "                        <th scope=\"col\" class=\"col-2\">Actions</th>\n"
                + "                      </tr>\n" + "                    </thead>\n" + "                    <tbody>\n";

        for (Map.Entry<UUID, Value> valueItem : values.entrySet()) {
            out += printOutBodyItem(valueItem.getValue().getId().toString(), valueItem.getValue().getTitle(),
                    String.valueOf(valueItem.getValue().getAmount()), valueItem.getValue().getDesc());
        }
        out += "                    </tbody>\n" + "                  </table>\n";
        // Body list bottom.
        out += "            </div>\n";

        return out;
    }

    private String printOutBodyItem(String id, String title, String amount, String desc) {
        String out = "                      <tr>\n" + "                        <th scope=\"row\">" + id.substring(0, 8)
                + "</th>\n" + "                        <td>" + title + "</td>\n" + "                        <td>"
                + amount + "</td>\n" + "                        <td>" + "					<td>"
                + desc + "</td>\n" + "<a href=\"budget?id=" + id + "\">Edit</a>\n"
                + "<a href=\"budget?id=" + id + "&action=delete\">Delete</a>\n" + "                      </td>\n"
                + "                      </tr>\n";

        return out;
    }

    private String printOutFoot() {
        String out = "    </body>\n" + "</html>\n";

        return out;
    }

}
