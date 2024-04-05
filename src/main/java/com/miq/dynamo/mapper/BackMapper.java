package com.miq.dynamo.mapper;

import com.miq.dynamo.model.DV360CampaignObject;
import com.miq.dynamo.model.Dv360CampaignObjectDto;
import com.miq.dynamo.request.ReqDV360CampaignObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring")
public interface BackMapper {
    @Mapping(source = "activationId", target = "activationId")
    @Mapping(source = "campaignId", target = "campaignId")
    @Mapping(source = "MDv360CampaignObjectDto", target = "dv360CampaignObjectDto", qualifiedByName = "mAdvertiserId")
    @Mapping(source = "MDv360CampaignObjectDto.MDv360Campaigns", target = "dv360CampaignObjectDto.dv360Campaigns", qualifiedByName = "dv360Campaigns")
    ReqDV360CampaignObject mapRequest(DV360CampaignObject dv360CampaignObject);

    @Named("mAdvertiserId")
    default String mapAdvertiserId(Dv360CampaignObjectDto dv360CampaignObjectDto) {
        return dv360CampaignObjectDto.getMAdvertiserId();
    }
    @Named("dv360Campaigns")
    default List<com.miq.dynamo.request.Dv360Campaign> mapDv360Campaigns(List<com.miq.dynamo.model.Dv360Campaign> dv360Campaigns) {
        return dv360Campaigns.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Mapping(source = "MDisplayName", target = "displayName")
    @Mapping(source = "MEntityStatus", target = "entityStatus")
    com.miq.dynamo.request.Dv360Campaign mapToDto(com.miq.dynamo.model.Dv360Campaign campaign);
}
