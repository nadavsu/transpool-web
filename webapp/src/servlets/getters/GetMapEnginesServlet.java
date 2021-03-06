package servlets.getters;

import api.MapsEngine;
import com.google.gson.Gson;
import utils.ServletUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A servlet which gets the details on all map engines - used in the homepgae to create the map cards.
 */
@WebServlet(name = "GetMapEnginesDetailsServlet", urlPatterns = {"/get-map-engines"})
public class GetMapEnginesServlet extends HttpServlet {
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());
            Gson gson = new Gson();
            String mapJson = gson.toJson(mapsEngine.getAllMapEnginesDetails());
            out.print(mapJson);
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }
}
