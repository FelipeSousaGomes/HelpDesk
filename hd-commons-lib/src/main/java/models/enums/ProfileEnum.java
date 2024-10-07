package models.enums;

import java.util.Arrays;

public enum ProfileEnum {
    ROLE_ADIMN("ROLE_ADMIN"),
    ROLE_CUSTOMER("ROLE_CUSTOMER"),
    ROLE_TECHICIAN("ROLE_TECHICIAN");

    private final String description;

    ProfileEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ProfileEnum toEnum( final String description) {

    return Arrays.stream(ProfileEnum.values())
            .filter(profileEnum -> profileEnum.getDescription().equals(description))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(description + " is not a valid profile"));

    }
}
