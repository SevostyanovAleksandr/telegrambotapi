//da350be901e9d23a891073f5986507d6 api ключ

//создадим метод которй мы будем передовать наше сообшение и нашу модель

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

class weather {
    public static String getweather(String message, Model model) throws IOException {

         //https://api.openweathermap.org/data/2.5/weather?q=
        //создаем метод который будет  передавать наше значение и модель
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=fe6baea6f561ff8ac3dd8d79340c5e73");

        // метод который считает что нам отправил URL

        Scanner in = new Scanner((InputStream) url.getContent());
        // переменная типа String в которую будет перемещен результат
        String result = "";
        while (in.hasNext()) {//считываем строку и сохраняем в переменную.
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));// вытаскиваем из джсона параметр имя
        JSONObject main = object.getJSONObject("main");// вытаскиваем из джсона тот параметр который нам нужен
        model.setTemp(main.getDouble("temp"));// вытаскиваем из джсона параметр температура
        model.setHumidity((main.getDouble("humidity")));// вытаскиваем из джсона параметр влажность


        JSONArray getArray = object.getJSONArray("weather"); // вытаскиваем с массива параметры
        for (int i = 0; i < getArray.length(); i++) {                   // перебираем массив
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));            // метод как перебирая массив находим нужый параметр и вставляем его
            model.setMain((String) obj.get("main"));             // метод как перебирая массив находим нужый параметр и вставляем его
        }
        return "City" + model.getName() + "\n" + // выводим на экран нужные нам параметры
                "Temperature: " + model.getTemp() + "C" + "\n" +
                "main:" + model.getMain() + "\n" +
                "Humidity" + model.getHumidity() + "%" + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }


}

