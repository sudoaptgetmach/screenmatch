package org.mach.screenmatch.model;

public enum Categories {
    ANIMATION,
    DRAMA,
    WAR,
    FANTASY,
    FAMILY,
    ADVENTURE,
    BIOGRAPHY,
    CRIME,
    SCIENCE_FICTION,
    HORROR,
    WESTERN,
    COMEDY,
    THRILLER,
    HISTORY,
    ROMANCE,
    ACTION,
    DOCUMENTARY,
    MUSICAL,
    MYSTERY;

    public static Categories fromString(String name) {
        for (Categories category : values()) {
            if (category.name().equalsIgnoreCase(name)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found for the string: " + name);
    }
}