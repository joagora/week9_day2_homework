package Controllers;

import db.DBHelper;
import models.Department;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
public class DepartmentController {
    public DepartmentController(){
        setupEndpoints();
    }

    private static void setupEndpoints(){
        get("/departments", (req, res) -> {
            List<Department> departments = DBHelper.getAll(Department.class);
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/index.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/departments/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Department department = DBHelper.find(id, Department.class);
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/show.vtl");
            model.put("department", department);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


    }

}
