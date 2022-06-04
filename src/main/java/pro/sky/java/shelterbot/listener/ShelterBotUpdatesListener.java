package pro.sky.java.shelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShelterBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(ShelterBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Value("${telegram.bot.token}")
    private String token;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Integer chatId = Math.toIntExact(update.message().chat().id());
            Long id = Long.valueOf(update.message().messageId());
            parseMessage(update.message().text(), chatId);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMessage(String messageText, long chatId) {
        TelegramBot bot = new TelegramBot(token);
        SendMessage message = new SendMessage(chatId, messageText);
        SendResponse response = bot.execute(message);
    }

    public void parseMessage(String messageText, long chatId) {
        String response = "";
        switch (messageText) {
            case "/start":
                response = "Приветственное сообщение";
                break;
            case "/info":
                response = "Узнать информацию о приюте";
                break;
            case "/how_the_shelter_works":
                response = "расписание работы приюта и адрес, схему проезда";
                break;
            case "/safety_precautions":
                response = "рекомендации о технике безопасности на территории приюта";
                break;
            case "/give_contacts":
                response = "принять и записать контактные данные для связи";
                break;
            default:
                response = "Я не понял, что ты сказал, а кто-то не написал часть кода которая зовёт оператора. Сорян";
        }
        sendMessage(response, chatId);
    }

}
