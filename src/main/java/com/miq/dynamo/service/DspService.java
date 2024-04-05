package com.miq.dynamo.service;

import com.miq.dynamo.model.DV360CampaignObject;
import com.miq.dynamo.request.ReqDV360CampaignObject;

import java.util.Optional;

public interface DspService {
    public Optional<ReqDV360CampaignObject> getDsp(String campaignId, String activationId);
    public Optional<ReqDV360CampaignObject> getByDisplayName(String displayName);
    public DV360CampaignObject processDsp(DV360CampaignObject dv360CampaignObject);
}
