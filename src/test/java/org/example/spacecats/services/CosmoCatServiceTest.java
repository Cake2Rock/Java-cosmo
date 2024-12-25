package org.example.spacecats.services;

import org.example.spacecats.shared.FeatureNotAvailableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CosmoCatServiceTest {

    @Autowired
    private CosmoCatService cosmoCatService;

    @DynamicPropertySource
    static void enableFeature(DynamicPropertyRegistry registry) {
        registry.add("feature.cosmoCats.enabled", () -> true);
    }

    @Test
    void testGetCosmoCats_FeatureEnabled() {
        String result = cosmoCatService.getCosmoCats();
        assertEquals("List of intergalactic Cosmo Cats!", result);
    }

    @DynamicPropertySource
    static void disableFeature(DynamicPropertyRegistry registry) {
        registry.add("feature.cosmoCats.enabled", () -> false);
    }

    @Test
    void testGetCosmoCats_FeatureDisabled() {
        assertThrows(FeatureNotAvailableException.class, () -> cosmoCatService.getCosmoCats());
    }
}
