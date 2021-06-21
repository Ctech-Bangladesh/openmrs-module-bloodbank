package org.openmrs.module.bloodbank.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.bloodbank.api.model.BloodCompatibility;
import org.openmrs.module.bloodbank.api.model.BloodDonorPhysicalSuitability;
import org.openmrs.module.bloodbank.api.model.Questionnaire;
import org.openmrs.module.bloodbank.api.model.enums.Status;
import org.openmrs.module.bloodbank.api.service.BloodBankService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/bloodbank")
public class BloodBankController {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private BloodBankService bloodBankService;
	
	@RequestMapping(method = RequestMethod.GET, value = "bloodSelectedFromDonor/list")
	@ResponseBody
	public List<BloodDonorPhysicalSuitability> getAllDonorTestsResult() {
		List<BloodDonorPhysicalSuitability> bloodDonorPhysicalSuitability = bloodBankService.getAllDonorTestsResult();
		log.info("Blood Donor Physical Suitability Test Lists :: " + bloodDonorPhysicalSuitability);
		return bloodDonorPhysicalSuitability;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "bloodCompatibilityTest/add")
	@ResponseBody
	public ResponseEntity<Object> saveBloodCompatibility(@Valid @RequestBody BloodCompatibility bloodCompatibility){
		if (bloodCompatibility.getBloodCompatibilityId() == null){
			bloodBankService.saveBloodDonorPhysicalSuitability(bloodCompatibility);
			log.info("Blood Compatibility is saved successfully :: " + bloodCompatibility);
			return new ResponseEntity<>(bloodCompatibility, HttpStatus.CREATED);
		}
		bloodBankService.updateBloodCompatibility(bloodCompatibility);
		log.info("Blood Compatibility is updated successfully :: " + bloodCompatibility);
		return new ResponseEntity<>(bloodCompatibility, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "bloodCompatibilityTest/list")
	@ResponseBody
	public List<BloodCompatibility> getAllBloodCompatibility() {
		List<BloodCompatibility> bloodCompatibilities = bloodBankService.getAllBloodCompatibility();
		log.info("Blood Compatibility Lists :: " + bloodCompatibilities);
		return bloodCompatibilities;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "bloodCompatibilityTest/{id}")
	@ResponseBody
	public ResponseEntity<Object> getBloodCompatibilityById(@PathVariable Integer id){
		try {
			if (id != null) {
				BloodCompatibility bloodCompatibility = bloodBankService.getBloodCompatibilityById(id);
				log.info("Blood Compatibility is retrieved successfully :: " + bloodCompatibility);
				return new ResponseEntity<>(bloodCompatibility, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Runtime error while trying to find the Blood Compatibility", e);
			return new ResponseEntity<>(RestUtil.wrapErrorResponse(e, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "bloodCompatibilityTest/delete/{id}")
	@ResponseBody
	public ResponseEntity<Object> deleteBloodCompatibilityById(@PathVariable Integer id){
		BloodCompatibility bloodCompatibility = bloodBankService.getBloodCompatibilityById(id);
		bloodCompatibility.setStatus(Status.DELETE.getValue());
		bloodBankService.updateBloodCompatibility(bloodCompatibility);
		log.info("Blood Compatibility is deleted successfully :: " + bloodCompatibility);
		return new ResponseEntity<>(bloodCompatibility, HttpStatus.ACCEPTED);
	}
}
