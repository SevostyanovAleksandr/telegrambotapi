import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    // создаем класс
    public static void main(String[] args) {      // создаем точку входа
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();// cоздаем обьект
        try {
            telegramBotsApi.registerBot(new Bot()); //регистрация нашего бота
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
    private void sendMsg(Message message, String text) { //метод что бот будет наи отвечать (отправка сообщений)
        SendMessage sendMessage = new SendMessage(); // создание обьекта
        sendMessage.enableMarkdown(true);//включение возможности разметки
        sendMessage.setChatId(message.getChatId().toString()); // создание айди чата чтобы понятно было кому отвечать
        sendMessage.setReplyToMessageId(message.getMessageId());// определится на какой сообщение нужно отвечать
        sendMessage.setText(text); // текст который мы отправляли в этот метод
        try {                     // сама отправка сообщения
            // добавили кнопку в отправку
            setButtons(sendMessage);//связали созданные кнопки с сообщениями
            sendMessage(sendMessage);// сообщения с ответом

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model(); // создаем модель которая хранит данные
        Message message = update.getMessage();
        if (message != null && message.hasText()) { // если содержит текст
            switch (message.getText()) { //условия для сравнения
                case "настройки":
                    sendMsg(message, "\uD83E\uDD28чем я могу помочь");
                    break;
                case "справка":
                    sendMsg(message, "\uD83E\uDD14Правила ипользования данного бота гласят, пишите название города  ПРИМЕР (Москва) - получите в ответ информацию о погоде!\uD83D\uDE09" );
                    break;
                case "Как вам бот?":
                    sendMsg(message, "Выберите от 1 до 5\uD83E\uDD13" );
                    break;
                case "1":
                    sendMsg(message, "Будем работать\uD83E\uDD13!" );
                    break;
                case "2":
                    sendMsg(message, "Будем работать\uD83E\uDD13!" );
                    break;
                case "3":
                    sendMsg(message, "Спасибо.Будем работать\uD83E\uDD13!" );
                    break;
                case "4":
                    sendMsg(message, "Спасибо за оценку\uD83D\uDC97!" );
                    break;
                case "5":
                    sendMsg(message, "Спасибо, пользуйтесь с удовольсвием\uD83D\uDC9E!" );
                    break;
                case "Пожаловаться":
                    sendMsg(message, "Пишие в нашу поддержку @ENigmaA9\uD83D\uDC68\u200D\uD83D\uDCBB, мы постараемся учьти все ваши пожелания\uD83D\uDE09." );
                    break;
                case "/start":
                    sendMsg(message, "Доброго времени суток \uD83D\uDD90, я \uD83E\uDD16 погодый бот\uD83C\uDF24\uD83C\uDF2A\uD83C\uDF27! Я могу по вашему запросу например (Лондон) \uD83C\uDF0F сказать параметры погоды ( температура, влажность , наличие осадков ) на данный момент! Приятного использования\uD83D\uDE07. ");
                    break;



                default:
                    try {
                        sendMsg(message,weather.getweather(message.getText(),model));
                    } catch (IOException e) {
                        sendMsg(message,"\uD83E\uDD37\uD83C\uDFFF\u200D♂️ Хм возможно где то ошибка\uD83E\uDD14. Будьте внимателельней, нет такого города! " +
                                "Обратите внимание на правильность написание города\uD83E\uDD28.");
                    }

            }
        }

    }

    private void setButtons(SendMessage sendMessage) {// метод по создание
        // кнопок клавиатуры
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        // строчка инициализирует клавиатуру
        sendMessage.setReplyMarkup(replyKeyboardMarkup);//разметка для нашей
        // клавиатуры
        replyKeyboardMarkup.setSelective(true); // cвязать наши сообщения с
        // клавиатурой  параметр который будет
                                                 //выводить клавиатуру всем
        // пользователям либо определенному
        replyKeyboardMarkup.setResizeKeyboard(true);//подконка клавиатуры под
        // количество кнопок сделать ее больше или меньше
        replyKeyboardMarkup.setOneTimeKeyboard(false);// параметр скрывать после
        // нажатия кнопки или не скрывать выбрали нескрывать

        List<KeyboardRow> keyboardRowList = new ArrayList<>(); // создали кнопки
        KeyboardRow keyboardfirstRow = new KeyboardRow();// установили первую строку

        keyboardfirstRow.add(new KeyboardButton("справка "));
        keyboardfirstRow.add(new KeyboardButton("Как вам бот?"));
        keyboardfirstRow.add(new KeyboardButton("Пожаловаться"));


        keyboardRowList.add(keyboardfirstRow);//добавляем все строчки клавиатуры в список
        replyKeyboardMarkup.setKeyboard(keyboardRowList);// устаналвиваем список  клавиатуре

    }

    public String getBotUsername() {    // метод нужен для того чтобы вернуть имя  при регистрации

        return "MykursachBOT";
    }

    public String getBotToken() {
        return "1862525976:AAHi8JZJyKFOzLF_Qufzbts1eIo5MtzJ5x4";
    }


}











