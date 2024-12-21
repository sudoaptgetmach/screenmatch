package org.mach.screenmatch.model;

public enum Categories {
    ANIMATION("Animation", "Animação"),
    DRAMA("Drama", "Drama"),
    WAR("War", "Guerra"),
    FANTASY("Fantasy", "Fantasia"),
    FAMILY("Family", "Família"),
    ADVENTURE("Adventure", "Aventura"),
    BIOGRAPHY("Biography", "Biografia"),
    CRIME("Crime", "Crime"),
    SCIENCE_FICTION("Science Fiction", "Ficção Científica"),
    HORROR("Horror", "Terror"),
    WESTERN("Western", "Faroeste"),
    COMEDY("Comedy", "Comédia"),
    THRILLER("Thriller", "Suspense"),
    HISTORY("History", "História"),
    ROMANCE("Romance", "Romance"),
    ACTION("Action", "Ação"),
    DOCUMENTARY("Documentary", "Documentário"),
    MUSICAL("Musical", "Musical"),
    MYSTERY("Mystery", "Mistério");

    private final String omdbCategory;
    private final String portugueseCategory;

    Categories(String omdbCategory, String portugueseCategory) {
        this.omdbCategory = omdbCategory;
        this.portugueseCategory = portugueseCategory;
    }

    public static Categories fromPortuguese(String name) {
        for (Categories category : values()) {
            if (category.portugueseCategory.equalsIgnoreCase(name)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found for the string: " + name);
    }

    public static Categories fromString(String name) {
        for (Categories category : values()) {
            if (category.omdbCategory.equalsIgnoreCase(name)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found for the string: " + name);
    }
}