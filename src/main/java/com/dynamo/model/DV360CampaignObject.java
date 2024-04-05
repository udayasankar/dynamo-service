
package com.dynamo.model;


import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import com.miq.dynamo.model.Dv360CampaignObjectDto;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class DV360CampaignObject {
    private String campaignId;
    private String activationId;
    private Dv360CampaignObjectDto mDv360CampaignObjectDto;
    @DynamoDbPartitionKey
    @DynamoDbAttribute("campaignId")
    @DynamoDbSecondaryPartitionKey(indexNames = { "campaignId-index" })
    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("activationId")
    public String getActivationId() {
        return activationId;
    }
}
