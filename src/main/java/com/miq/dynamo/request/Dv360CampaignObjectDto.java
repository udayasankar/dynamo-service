
package com.miq.dynamo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Dv360CampaignObjectDto {

    private String advertiserId;

    private List<Dv360Campaign> dv360Campaigns;


}
