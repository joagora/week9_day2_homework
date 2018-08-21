package Controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class ManagerController {

    public ManagerController(){
        setupEndpoint();

    }

    private void setupEndpoint(){
        get("/managers", (req, res) -> {
            List<Manager> managers = DBHelper.getAll(Manager.class);
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/index.vtl");
            model.put("managers", managers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/managers/new", (req, res) -> {
            List<Department> departments = DBHelper.getAll(Department.class);
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/new.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/managers", (req, res) -> {
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            int departmentId = Integer.parseInt(req.queryParams("departmentId"));
            Department department = DBHelper.find(departmentId, Department.class);
            double budget = Double.parseDouble(req.queryParams("budget"));

            Manager manager = new Manager(firstName, lastName, salary, department, budget);
            DBHelper.save(manager);

            HashMap<String, Object> model = new HashMap<>();
            res.redirect("/managers");
            return null;
        });
    }
}
