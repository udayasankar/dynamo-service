package com.miq.dynamo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miq.dynamo.mapper.BackMapper;
import com.miq.dynamo.model.DV360CampaignObject;
import com.miq.dynamo.model.Dv360CampaignObjectDto;
import com.miq.dynamo.request.ReqDV360CampaignObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class DspServiceImpl implements DspService {

    @Autowired
    DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    BackMapper backMapper;


    @Override
    public Optional<ReqDV360CampaignObject> getDsp(String campaignId, String activationId) {
        DynamoDbTable<DV360CampaignObject> mappedTable = dynamoDbEnhancedClient.table("dspinfo", TableSchema.fromBean(DV360CampaignObject.class));
        // Construct the key with partition and sort key
        Key key = Key.builder().partitionValue(campaignId)
                .sortValue(activationId)
                .build();

        DV360CampaignObject dv360CampaignObject = mappedTable.getItem(key);
        return Optional.ofNullable(backMapper.mapRequest(dv360CampaignObject));
    }

    @Override
    public Optional<ReqDV360CampaignObject> getByDisplayName(String displayName) {
        DynamoDbIndex<DV360CampaignObject> secIndex = dynamoDbEnhancedClient.table(
                "dspinfo",
                TableSchema.fromBean(DV360CampaignObject.class)
        ).index("campaignId-index");

        AttributeValue attVal = AttributeValue.builder()
                .s(displayName)
                .build();

        QueryConditional queryConditional = QueryConditional.keyEqualTo(
                Key.builder().partitionValue(attVal).build());
        Iterable<Page<DV360CampaignObject>> results = (Iterable<Page<DV360CampaignObject>>)
                secIndex.query(
                        QueryEnhancedRequest.builder()
                                .queryConditional(queryConditional)
                                .build()
                );
        AtomicInteger atomicInteger = new AtomicInteger();
        List<DV360CampaignObject> dv360CampaignObjectList = new ArrayList<>();
        atomicInteger.set(0);
        results.forEach(value -> {
            dv360CampaignObjectList.add(value.items().get(atomicInteger.get()));
            atomicInteger.incrementAndGet();
        });
        return Optional.ofNullable(backMapper.mapRequest(dv360CampaignObjectList.get(0)));
    }

    @Override
    public DV360CampaignObject processDsp(DV360CampaignObject dv360CampaignObject) {
        try {
            DynamoDbTable<DV360CampaignObject> mappedTable = dynamoDbEnhancedClient.table("dspinfo", TableSchema.fromBean(DV360CampaignObject.class));
            DV360CampaignObject dv360CampaignObjectSave = DV360CampaignObject.builder().campaignId(dv360CampaignObject.getCampaignId())
                    .activationId(dv360CampaignObject.getActivationId())
                    .mDv360CampaignObjectDto(Dv360CampaignObjectDto.builder().mDv360Campaigns(dv360CampaignObject.getMDv360CampaignObjectDto().getMDv360Campaigns())
                            .mAdvertiserId(dv360CampaignObject.getMDv360CampaignObjectDto().getMAdvertiserId()).build())
                    .build();

            PutItemEnhancedRequest<DV360CampaignObject> enReq = PutItemEnhancedRequest.builder(DV360CampaignObject.class)
                    .item(dv360CampaignObjectSave)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String itemJson = objectMapper.writeValueAsString(dv360CampaignObjectSave); // Serialize item to JSON
            int itemSizeBytes = itemJson.getBytes(StandardCharsets.UTF_8).length;

            if (itemSizeBytes > 400 * 1024) {
                // Item size exceeds the limit
                System.out.println("Item size exceeds the limit");
            } else {
                // Item size is within the limit
                System.out.println("Item size does not exceeds the limit");
            }

            PutItemEnhancedResponse<DV360CampaignObject> putItemEnhancedResponse = mappedTable.putItemWithResponse(enReq);
            System.out.println(putItemEnhancedResponse.itemCollectionMetrics()+":"+putItemEnhancedResponse.attributes());
            return dv360CampaignObject;
        }catch(Exception ex)
        {
            log.info("messge {}",ex.getMessage());

        }
        return dv360CampaignObject;
    }
}
