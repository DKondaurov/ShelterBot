package pro.sky.java.shelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
            TelegramBot bot = new TelegramBot(token);
            Integer chatId = Math.toIntExact(update.message().chat().id());
            Long id = Long.valueOf(update.message().messageId());
            String messageText = parseMessage(update.message().text(), chatId);
            SendMessage message = new SendMessage(chatId, messageText);
            SendResponse response = bot.execute(message);

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public String parseMessage(String messageText, long chatId) {
        String response = "";
        if (messageText.equals("/start")) {
            response = "Приветственное сообщение";
        }
        return response;
    }

}
