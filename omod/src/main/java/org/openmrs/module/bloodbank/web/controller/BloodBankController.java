package org.openmrs.module.bloodbank.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "module/bloodbank/")
public class BloodBankController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UserService userService;
	
	private final String VIEW = "module/bloodbank/users/users";
	
	@RequestMapping(value = "users", method = RequestMethod.GET)
	public String onGet() {
		return VIEW;
	}
	
	@RequestMapping(value = "bloodbank", method = RequestMethod.POST)
	public String onPost(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject,
	        BindingResult errors) {
		
		if (errors.hasErrors()) {
			// return error view
		}
		
		return VIEW;
	}
	
	@ModelAttribute("users")
	protected List<User> getUsers() throws Exception {
		List<User> users = userService.getAllUsers();
		return users;
	}
	
}
