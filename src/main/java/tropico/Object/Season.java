package tropico.Object;

import java.io.Serializable;

/**
 * represents all the season with their name
 * @author Cléis & Quentin
 */
public enum Season implements Serializable {

    SUMMER("Été"),
    WINTER("Hiver"),
    AUTUMN("Automne"),
    SPRING("Printemps");

    /**
     * name of the season
     */
    private final String name;

    static final long serialVersionUID = 1020554481031012518L;

    Season(String name){
        this.name = name;
    }

    /**
     * get name of the season
     * @return name of the season
     */
    public String getName(){
        return name;
    }

    /**
     * get next season
     * @return next season
     */
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

    /**
     * get previous season
     * @return previous season
     */
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
