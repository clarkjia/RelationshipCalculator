package com.calculator.relationship.controller;

import com.calculator.relationship.service.CalcCalHandler;
import com.jd.alpha.skill.client.entity.request.SkillData;
import com.jd.alpha.skill.client.entity.response.SkillResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/relation")
public class JobController {
    private static Logger log = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private CalcCalHandler calcCalHandler;

    @RequestMapping("/appState")
    String appState() {
        return "OK";
    }

    @RequestMapping("/calc")
    SkillResponse calc(SkillData skillData) {
        return calcCalHandler.onIntentRequest(skillData);
    }

}
