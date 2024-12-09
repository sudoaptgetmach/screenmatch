package org.mach.screenmatch.model;

public enum Categories {
    ACAO("Action"),
    AVENTURA("Adventure"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    FANTASIA("Fantasy"),
    TERROR("Horror"),
    MISTERIO("Mystery"),
    ROMANCE("Romance"),
    FICCAO_CIENTIFICA("Sci-Fi"),
    SUSPENSE("Thriller"),
    ANIMACAO("Animation"),
    DOCUMENTARIO("Documentary"),
    FAMILIA("Family"),
    CRIME("Crime"),
    MUSICAL("Musical"),
    BIOGRAFIA("Biography"),
    HISTORIA("History"),
    GUERRA("War"),
    FAROESTE("Western");

    private final String englishName;

    Categories(String englishName) {
        this.englishName = englishName;
    }

    public String getEnglishName() {
        return englishName;
    }
}