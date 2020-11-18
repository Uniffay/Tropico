package tropico.Object;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Resource {

    private double farming;
    private double industry;
    private double money;

    public Resource(String jsonPath){
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(jsonPath));
            Map<?, ?> map = gson.fromJson(reader, Map.class);
            farming = (double)map.get("farming");
            industry = (double)map.get("industry");
            money = (double)map.get("money");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void changeFarming(short farming){
        this.farming += farming;
        if(this.farming + industry > 100){
            this.farming -= this.farming + industry - 100;
        }
        else if(this.farming < 0){
            this.farming = 0;
        }
    }

    public void changeIndustry(short industry){
        this.industry += industry;
        if(this.industry + farming > 100){
            this.industry -= this.industry + farming - 100;
        }
        else if(this.industry < 0){
            this.industry = 0;
        }
    }

    public void changeMoney(double money){
        this.money += money;
    }
}
