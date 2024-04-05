package com.miq.dynamo.mapper;



import com.miq.dynamo.model.DV360CampaignObject;
import com.miq.dynamo.model.Dv360Campaign;
import com.miq.dynamo.request.ReqDV360CampaignObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DspMapper {
    @Mapping(source = "reqDV360CampaignObject.activationId", target = "activationId")
    @Mapping(source = "reqDV360CampaignObject.campaignId", target = "campaignId")
    @Mapping(source = "dv360CampaignObjectDto.advertiserId", target = "mDv360CampaignObjectDto.mAdvertiserId")
    @Mapping(source = "dv360CampaignObjectDto.dv360Campaigns", target = "mDv360CampaignObjectDto.mDv360Campaigns", qualifiedByName = "mapDv360Campaigns")
    DV360CampaignObject map(ReqDV360CampaignObject reqDV360CampaignObject);

    @Named("mapDv360Campaigns")
    default List<Dv360Campaign> mapDv360Campaigns(List<com.miq.dynamo.request.Dv360Campaign> dv360Campaigns)
    {
        return dv360Campaigns.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Mapping(source = "displayName", target = "mDisplayName")
    @Mapping(source = "entityStatus", target = "mEntityStatus")
    Dv360Campaign mapToDto(com.miq.dynamo.request.Dv360Campaign campaign);

}
