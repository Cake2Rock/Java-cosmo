package org.example.spacecats.services;
import org.example.spacecats.services.FeatureToggleService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureToggleService {

    @Value("${feature.cosmoCats.enabled:true}")
    private boolean cosmoCatsEnabled;

    @Value("${feature.kittyProducts.enabled:false}")
    private boolean kittyProductsEnabled;

    public boolean isCosmoCatsEnabled() {
        return cosmoCatsEnabled;
    }

    public boolean isKittyProductsEnabled() {
        return kittyProductsEnabled;
    }

    public boolean isFeatureEnabled(String featureName) {
        return switch (featureName) {
            case "cosmoCats" -> isCosmoCatsEnabled();
            case "kittyProducts" -> isKittyProductsEnabled();
            default -> false;
        };
    }
}

