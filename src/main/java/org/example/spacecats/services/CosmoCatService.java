package org.example.spacecats.services;

import org.example.spacecats.shared.FeatureNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CosmoCatService {

    private final FeatureToggleService featureToggleService;

    @Autowired
    public CosmoCatService(FeatureToggleService featureToggleService) {
        this.featureToggleService = featureToggleService;
    }

    public String getCosmoCats() {
        if (!featureToggleService.isFeatureEnabled("feature.cosmoCats.enabled")) {
            throw new FeatureNotAvailableException("Feature 'cosmoCats' is not enabled");
        }
        return "List of intergalactic Cosmo Cats!";
    }
}
