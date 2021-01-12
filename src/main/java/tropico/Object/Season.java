package tropico.Object;

import java.io.Serializable;

public enum Season implements Serializable {

    SUMMER("Été"),
    WINTER("Hiver"),
    AUTUMN("Automne"),
    SPRING("Printemps");

    private final String name;

    Season(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Season next() {
        switch (this){
            case SUMMER:
                return AUTUMN;
            case AUTUMN:
                return WINTER;
            case SPRING:
                return SUMMER;
            default:
                return SPRING;
        }
    }

    public Season previous() {
        switch(this){
            case SUMMER:
                return SPRING;
            case AUTUMN:
                return SUMMER;
            case WINTER:
                return AUTUMN;
            default:
                return WINTER;
        }
    }
}
