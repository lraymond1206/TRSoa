package org.trs.oa.controller;import org.trs.oa.service.MessageService;import org.trs.oa.service.ProjectService;import org.trs.oa.utility.Constants;import org.trs.oa.vo.ProjectVO;import org.apache.log4j.Logger;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.ui.ModelMap;import org.springframework.validation.BindingResult;import org.springframework.web.bind.annotation.ModelAttribute;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;import org.springframework.web.servlet.mvc.support.RedirectAttributes;import javax.servlet.http.HttpSession;@Controller@RequestMapping({"/project"})public class ProjectController {    private static final Logger LOGGER = Logger.getLogger(ProjectController.class);    @Autowired    ProjectService projectService;    @Autowired    MessageService msg;    @RequestMapping(value = "/create", method = RequestMethod.GET)    public String showCreateProject(ModelMap model) {        model.addAttribute("projectVO", new ProjectVO());        return "/project/create_new_project";    }    @RequestMapping(value = "/create", method = RequestMethod.POST)    public String createProject(@ModelAttribute ProjectVO projectVO,                                final RedirectAttributes redirectAttributes,                                HttpSession session,BindingResult result) {//        LOGGER.info("////////" + projectVO + " " + session.getAttribute(Constants.USER_ID) + " " + session.getAttribute(Constants.ENTERPRISE_ID));        if(result.hasErrors()){            redirectAttributes.addFlashAttribute("errorMsg", msg.getMessage("project_created_fail", null));        }else {            projectVO.setEnterpriseId((Integer) session.getAttribute(Constants.ENTERPRISE_ID));            if(projectService.saveProject(projectVO) != null) {                redirectAttributes.addFlashAttribute("successMsg", msg.getMessage("project_created_success", null));            }else{                redirectAttributes.addFlashAttribute("errorMsg", msg.getMessage("project_created_fail", null));            }        }        return "redirect:/dashboard";    }    @RequestMapping("/manage")    public String showProject(ModelMap model) {        model.addAttribute("projList",projectService.getProjListForDashboard());        return "/project/update_project";    }}