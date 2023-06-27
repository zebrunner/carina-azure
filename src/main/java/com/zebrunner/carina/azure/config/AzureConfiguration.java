package com.zebrunner.carina.azure.config;

import com.zebrunner.carina.utils.commons.SpecialKeywords;
import com.zebrunner.carina.utils.config.Configuration;
import com.zebrunner.carina.utils.config.IParameter;

import java.util.Arrays;
import java.util.Optional;

public final class AzureConfiguration extends Configuration {

    public enum Parameter implements IParameter {

        AZURE_ACCOUNT_NAME("azure_account_name"),

        AZURE_CONTAINER_NAME("azure_container_name"),

        AZURE_BLOB_URL("azure_blob_url"),

        AZURE_ACCESS_KEY_TOKEN("azure_access_key_token") {
            @Override
            public boolean hidden() {
                return true;
            }
        },

        AZURE_LOCAL_STORAGE("azure_local_storage");

        private final String key;

        Parameter(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    @Override
    public String toString() {
        Optional<String> asString = asString(Arrays.stream(Parameter.values()).filter(param -> {
            if (Parameter.AZURE_BLOB_URL.equals(param)) {
                Optional<String> value = Configuration.get(param);
                //#1451 hide WARN! Value not resolved by key: azure_container_name
                if (value.isPresent() && value.get().toLowerCase().contains(SpecialKeywords.NULL.toLowerCase())) {
                    return false;
                }
            }
            return true;
        }).toArray(IParameter[]::new));

        if (asString.isEmpty()) {
            return "";
        }
        return "\n============= Azure configuration =============\n" +
                asString.get();
    }
}
