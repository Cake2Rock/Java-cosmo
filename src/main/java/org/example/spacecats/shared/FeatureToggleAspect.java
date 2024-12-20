package org.example.spacecats.shared;

import org.example.spacecats.services.FeatureToggleService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Autowired
    public FeatureToggleAspect(FeatureToggleService featureToggleService) {
        this.featureToggleService = featureToggleService;
    }

    @Before("@annotation(featureToggle)")
    public void checkFeatureEnabled(FeatureToggle featureToggle) {
        String featureName = featureToggle.value();
        if (!featureToggleService.isFeatureEnabled(featureName)) {
            throw new FeatureNotAvailableException("Feature " + featureName + " is not enabled.");
        }
    }
}
