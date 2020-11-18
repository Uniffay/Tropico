package tropico.Object;

public enum Season {

    Summer(), Winter(), Autumn(), Spring();

    Season() {
    }

    public Season getSeasonFromString(String season){
        switch (season){
            case "Winter":
                return Winter;
            case "Summer":
                return Summer;
            case "Autumn":
                return Autumn;
            case "Spring":
                return Spring;
            default: throw  new IllegalArgumentException(season + "is not a season or is badly written");
        }
    }
}
