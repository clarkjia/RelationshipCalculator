package model;

import com.jd.alpha.skill.client.entity.request.SkillRequestIntent;
import com.jd.alpha.skill.client.entity.response.directive.Directive;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalcDirective extends Directive {

    private SkillRequestIntent intent;

    private String slotName;

    private String type;

}
