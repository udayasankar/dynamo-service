
package com.miq.dynamo.model;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Dv360CampaignObjectDto {
    private String mAdvertiserId;
    private List<Dv360Campaign> mDv360Campaigns;
}
