
package com.dynamo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class ReqDV360CampaignObject {
    private String activationId;
    private String campaignId;
    private Dv360CampaignObjectDto dv360CampaignObjectDto;

}
