package com.calculator.relationship.service;

import com.jd.alpha.skill.client.RequestHandler;
import com.jd.alpha.skill.client.constant.ResponseOutputTypeConstants;
import com.jd.alpha.skill.client.entity.request.SkillData;
import com.jd.alpha.skill.client.entity.request.SkillRequestIntent;
import com.jd.alpha.skill.client.entity.request.SkillRequestSlot;
import com.jd.alpha.skill.client.entity.response.SkillResponse;
import com.jd.alpha.skill.client.entity.response.SkillResponseDetails;
import com.jd.alpha.skill.client.entity.response.SkillResponseOutput;
import com.jd.alpha.skill.client.entity.response.directive.Directive;
import model.CalcDirective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CalcRequestHandler extends RequestHandler {


    @Autowired
    private CalcHandler calculatorModel;
    private final static Logger LOG = LoggerFactory.getLogger(CalcRequestHandler.class);
    private String skillApplicationId;

    private static final String INTENT_RELATIONSHIP_CALCULATOR = "RelationshipCalculator";
    public static final String SLOT_NAME_SHIPNAME = "SHIPNAME";

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

                // 计算称呼
                Map<String, SkillRequestSlot> slots = skillRequestIntent.getSlots();
                SkillRequestSlot slot = slots.get(SLOT_NAME_SHIPNAME);
                String value = slot.getValue();
                String inputText = "我的" + value;
                calculatorModel.setInputText(inputText);
                calculatorModel.calcProcess();
                String result = calculatorModel.getResult();

                //构造返回response
                SkillResponse response = new SkillResponse();
                SkillResponseDetails skillResponseDetails = new SkillResponseDetails();
                SkillResponseOutput skillResponseOutput = new SkillResponseOutput();
                String resultText = result;
                skillResponseOutput.setText(resultText);
                skillResponseOutput.setType(ResponseOutputTypeConstants.PLAIN_TEXT);
                skillResponseDetails.setOutput(skillResponseOutput);
                response.setResponse(skillResponseDetails);
                response.setVersion("1.0");
                response.setSkill("RelationshipCalculator");
                response.setIntent("CalcResult");

                Map<String, Object> contexts = new HashMap<>();
                contexts.put(SLOT_NAME_SHIPNAME, slot.getValue());
                response.setContexts(contexts);
                List<Directive> directives = new ArrayList<>();
                CalcDirective directive = new CalcDirective();
                directive.setIntent(skillRequestIntent);
                directive.setSlotName(SLOT_NAME_SHIPNAME);
                directive.setType("RelationshipCalculator");
                directives.add(directive);
                response.setDirectives(directives);

                return response;
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
        skillResponseOutput.setText("已退出，亲戚称呼查询服务期待您再次使用");
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