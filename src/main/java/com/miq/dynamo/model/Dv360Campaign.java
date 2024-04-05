
package com.miq.dynamo.model;


import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Dv360Campaign {


    private String mDisplayName;
    //private List<Dv360InsertionOrder> mDv360InsertionOrders;
    private String mEntityStatus;
    /*private FrequencyCap mFrequencyCap;
    @DynamoDBAttribute
    private String mStatus;
    @DynamoDBAttribute
    private Long mId;*/
}
