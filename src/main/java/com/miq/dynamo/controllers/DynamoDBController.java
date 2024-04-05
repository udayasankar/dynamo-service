package com.miq.dynamo.controllers;

import com.miq.dynamo.mapper.DspMapper;
import com.miq.dynamo.model.DV360CampaignObject;
import com.miq.dynamo.request.ReqDV360CampaignObject;
import com.miq.dynamo.service.DspService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/dynamo")
public class DynamoDBController {
    @Autowired
    DspService dspService;
    @Autowired
    DspMapper dspMapper;
    @GetMapping("/{campaignId}/{activationId}")
    public ReqDV360CampaignObject getDsp(@PathVariable String campaignId, @PathVariable String activationId) {
        return dspService.getDsp(campaignId, activationId).get();
    }

    @GetMapping("/{displayName}")
    public ReqDV360CampaignObject getByDisplayName(@PathVariable String displayName) {
        return dspService.getByDisplayName(displayName).get();
    }
    @PostMapping
    public ResponseEntity<DV360CampaignObject>  saveDsp(@RequestBody ReqDV360CampaignObject request) {
        return ResponseEntity.ok(dspService.processDsp(dspMapper.map(request)));
    }
}
