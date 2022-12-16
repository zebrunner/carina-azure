package com.zebrunner.carina.azure;

import com.zebrunner.carina.commons.artifact.ArtifactManagerFactory;
import com.zebrunner.carina.commons.artifact.IArtifactManager;
import com.zebrunner.carina.commons.artifact.IArtifactManagerFactory;

import java.util.regex.Pattern;

@ArtifactManagerFactory
public class AzureArtifactManagerFactory implements IArtifactManagerFactory {
    private static final Pattern AZURE_ENDPOINT_PATTERN = Pattern.compile(
            "\\/\\/([a-z0-9]{3,24})\\.blob.core.windows.net\\/(?:(\\$root|(?:[a-z0-9](?!.*--)[a-z0-9-]{1,61}[a-z0-9]))\\/)?(.{1,1024})");

    @Override
    public boolean isSuitable(String url) {
        return AZURE_ENDPOINT_PATTERN.matcher(url).find();
    }

    @Override
    public IArtifactManager getInstance() {
        return AzureManager.getInstance();
    }
}
