package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty()?null:Integer.valueOf(id), LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),Integer.valueOf(request.getParameter("calories")));
        log.info(meal.isNew()?"Create":"Update");
        repository.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if(action==null){
            log.info("getAll");
            request.setAttribute("meals", MealsUtil.getWithExceeded(repository.getAll(), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if(action.equals("create")){
            final Meal meal = new Meal(LocalDateTime.now(),"",0);
            request.setAttribute("meal",meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request,response);
        } if(action.equals("update")){
            final Meal meal = repository.get(getId(request));
            request.setAttribute("meal",meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request,response);
        } if(action.equals("delete")){
            int id =getId(request);
            log.info("Delete {}",id);
            repository.delete(id);
        }
    }

    private int getId(HttpServletRequest request){
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
