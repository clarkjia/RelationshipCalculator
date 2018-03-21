package com.calculator.relationship.service;

import com.jd.alpha.skill.client.RequestHandler;
import com.jd.alpha.skill.client.entity.request.SkillData;
import com.jd.alpha.skill.client.entity.request.SkillRequestIntent;
import com.jd.alpha.skill.client.entity.request.SkillRequestSlot;
import com.jd.alpha.skill.client.entity.response.SkillResponse;
import com.jd.alpha.skill.client.entity.response.SkillResponseDetails;
import com.jd.alpha.skill.client.entity.response.SkillResponseOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class CalcCalHandler extends RequestHandler {

   @Autowired
   private CalculatorHandler calculatorModel;
   private final static Logger LOG = LoggerFactory.getLogger(CalcCalHandler.class);
   @Value("${xxx_skill_id}")
   private String skillApplicationId;
  
   private static final String INTENT_QUERY = "query";
   private static final String INTENT_RELATIONSHIP_CALCULATOR = "RelationshipCalculator";
   private static final String SLOT_SHIPNAME = "shipname";
   private static final String SLOT_KEYWORD = "keyword";
   private static final String SLOT_CATEGORY = "category";

   @Override
   public boolean validate(SkillData skillData) {
      return skillApplicationId.equals(skillData.getSession().getApplication().getApplicationId());
   }

   @Override
   public void onSessionStarted(SkillData skillData) {
      LOG.info("Session Started");
   }

   @Override
   public SkillResponse onLaunchRequest(SkillData skillData) {
      LOG.info("Skill Launched");
      SkillResponse response = new SkillResponse();
      response.setSkill(skillApplicationId);
      response.setShouldEndSession(false);
      SkillResponseDetails skillResponseDetails = new SkillResponseDetails();
      SkillResponseOutput skillResponseOutput = skillResponseDetails.getOutput();
      skillResponseOutput.setText("欢迎使用亲戚称呼查询服务，您可以对我说您想问的亲戚怎么称呼，比如：我的妈妈的妈妈的弟弟称呼什么");
      skillResponseDetails.setOutput(skillResponseOutput);
      response.setResponse(skillResponseDetails);
      return response;
   }

   @Override
   public SkillResponse onIntentRequest(SkillData skillData) {
      try {
         SkillRequestIntent skillRequestIntent = skillData.getRequest().getIntent();
         if (INTENT_RELATIONSHIP_CALCULATOR.equals(skillRequestIntent.getName())) {
            Map<String, SkillRequestSlot> slots = skillRequestIntent.getSlots();
            SkillRequestSlot slot = slots.get("SLOT_SHIPNAME");
            String value  = slot.getValue();
            calculatorModel.setInputText(value);
            String result = calculatorModel.getResult();
            SkillResponse response = new SkillResponse();
            SkillResponseDetails skillResponseDetails = new SkillResponseDetails();
            SkillResponseOutput skillResponseOutput = skillResponseDetails.getOutput();
            skillResponseOutput.setText("您应该称呼"+result);
            skillResponseDetails.setOutput(skillResponseOutput);
            response.setResponse(skillResponseDetails);
            return response;
         } else if (INTENT_QUERY.equals(skillRequestIntent.getName())) {
            // TODO
         } else {
            return new SkillResponse();
         }
      } catch (Exception e) {
         LOG.error("onIntentRequest error", e);
      }
      return null;
   }

   @Override
   public void onSessionEndedRequest(SkillData skillData) {
      LOG.info("Session Ended");
   }

   @Override
   public SkillResponse onCancelIntent(SkillData skillData) {

      SkillResponse response = new SkillResponse();
      response.setSkill(skillApplicationId);
      SkillResponseDetails skillResponseDetails = new SkillResponseDetails();
      SkillResponseOutput skillResponseOutput = skillResponseDetails.getOutput();
      skillResponseOutput.setText("已退出，小七期待您再次使用");
      skillResponseDetails.setOutput(skillResponseOutput);
      response.setResponse(skillResponseDetails);
      response.setShouldEndSession(true);
      return response;
   }

   @Override
   public SkillResponse onHelpIntent(SkillData skillData) {
      return onLaunchRequest(null);
   }

   @Override
   public SkillResponse onNextIntent(SkillData skillData) {
      return null;
   }

   @Override
   public SkillResponse onRepeatIntent(SkillData skillData) {
      return null;
   }

   @Override
   public SkillResponse onOtherBuildInIntent(SkillData skillData) {
      return null;
   }

   @Override
   public SkillResponse defaultResponse(SkillData skillData) {
      return null;
   }
}