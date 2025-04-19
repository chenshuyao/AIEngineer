package com.aiengineer.student.controller;

import com.aiengineer.student.model.Student;
import com.aiengineer.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final List<String> majorOptions = Arrays.asList(
            "计算机", "土木工程", "理学院", "工商管理", "电子信息", "自动化"
    );

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchType,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> studentPage;

        if (keyword != null && !keyword.isEmpty()) {
            if ("name".equals(searchType)) {
                studentPage = studentService.searchByName(keyword, pageable);
            } else if ("phone".equals(searchType)) {
                studentPage = studentService.searchByPhone(keyword, pageable);
            } else {
                studentPage = studentService.searchByKeyword(keyword, pageable);
            }
        } else {
            studentPage = studentService.getAllStudents(pageable);
        }

        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalItems", studentPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchType", searchType);
        model.addAttribute("size", size);

        return "student/list";
    }

    @GetMapping("/new")
    public String showNewStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("majorOptions", majorOptions);
        return "student/form";
    }

    @PostMapping
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("majorOptions", majorOptions);
            return "student/form";
        }

        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("successMessage", "学生信息保存成功！");
        return "redirect:/students";
    }

    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "student/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("majorOptions", majorOptions);
        return "student/form";
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id,
                               @Valid @ModelAttribute("student") Student student,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            student.setId(id);
            model.addAttribute("majorOptions", majorOptions);
            return "student/form";
        }

        student.setId(id);
        studentService.updateStudent(student);
        redirectAttributes.addFlashAttribute("successMessage", "学生信息更新成功！");
        return "redirect:/students";
    }

    @GetMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "学生信息删除成功！");
        return "redirect:/students";
    }
}
