package tropico.Object;

public enum Season {

    SUMMER("Été"),
    WINTER("Hiver"),
    AUTUMN("Automne"),
    SPRING("Printemps");

    private String name;

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
}
