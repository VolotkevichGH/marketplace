package com.example.MarketplaceItaly.services.telegram;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

   private final TelegramConfig telegramConfig;

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return telegramConfig.getName();
    }

    @Override
    public String getBotToken() {
        return telegramConfig.getToken();
    }


    public void sendMessage(long id, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(text);
        SendChatAction chatAction = new SendChatAction();
        chatAction.setAction(ActionType.TYPING);
        execute(chatAction);
        execute(message);
    }

    @SneakyThrows
    public void sendFile(long id, String fileName, String message){
        InputFile file = new InputFile();
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        file.setMedia(stream, fileName);
        SendDocument document = new SendDocument();
        document.setChatId(id);
        document.setCaption(message);
        document.setDocument(file);
        execute(document);
    }

    @SneakyThrows
    public void sendFile(long id, File file, String message){
        SendDocument document = new SendDocument();
        document.setChatId(id);
        document.setCaption(message);
        InputFile inputFile = new InputFile(file);
        document.setDocument(inputFile);
        execute(document);
    }
}
