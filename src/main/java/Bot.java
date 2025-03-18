import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaBotMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.File;
import java.security.PublicKey;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    //Кнопка для запуска теста
    private InlineKeyboardButton buttonForStartTgBot = InlineKeyboardButton.builder()
            .text("Запустить")
            .callbackData("запуск")
            .build();

    //Клавиатура
    private InlineKeyboardMarkup keyboardForButtonForStartTgBot = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonForStartTgBot))
            .build();

    private InlineKeyboardButton buttonForReturnMenu = InlineKeyboardButton.builder()
            .text("Вернуться на главное меню")
            .callbackData("вернуться на главное меню")
            .build();


    private InlineKeyboardButton buttonForCategories = InlineKeyboardButton.builder()
            .text("Перейти в категории")
            .callbackData("категории")
            .build();
    private InlineKeyboardButton buttonForMyBasket = InlineKeyboardButton.builder()
            .text("Моя корзина")
            .callbackData("моя корзина")
            .build();

    private InlineKeyboardMarkup keyboardForMenu = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonForCategories))
            .keyboardRow(List.of(buttonForMyBasket))
            .build();



    private InlineKeyboardButton buttonForLiquidCollagen = InlineKeyboardButton.builder()
            .text("Жидкий коллаген")
            .callbackData("жидкий коллаген")
            .build();
    private InlineKeyboardButton buttonForPowderCollagen = InlineKeyboardButton.builder()
            .text("Коллаген в попрошке")
            .callbackData("коллаген в порошке")
            .build();
    private InlineKeyboardButton buttonForTabletsCollagen = InlineKeyboardButton.builder()
            .text("Коллаген в таблетках")
            .callbackData("коллаген в таблетках")
            .build();
    private InlineKeyboardMarkup keyboardForAllCategories = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonForLiquidCollagen))
            .keyboardRow(List.of(buttonForPowderCollagen))
            .keyboardRow(List.of(buttonForTabletsCollagen))
            .keyboardRow(List.of(buttonForReturnMenu))
            .build();

    private InlineKeyboardButton buttonForKinohimitsuCollagenMen530016sDrink = InlineKeyboardButton.builder()
            .text("Kinohimitsu Collagen Men 5300 16's питьевой")
            .callbackData("Kinohimitsu Collagen Men 5300 16")
            .build();

    private InlineKeyboardMarkup keyboardForLiquidCollagen = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonForKinohimitsuCollagenMen530016sDrink))
            .build();
    @Override
    public void onUpdateReceived(Update update) {
        forWorkWithText(update);
        forWorkWithButtons(update);
    }

    public void forWorkWithText(Update update) {
        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            Long idUser = update.getMessage().getFrom().getId();
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(idUser)
                    .text("")
                    .build();

            if (text.equals("/start")) {
                sendMessage.setText("Вас приветствует интернет-магазин!");
                sendMessage.setReplyMarkup(keyboardForButtonForStartTgBot);
            }
            try {

                execute(sendMessage);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void forWorkWithButtons(Update update){
        if(update.hasCallbackQuery()){
            String callbackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

            //Для отправки сообщений
            EditMessageText editMessageText = EditMessageText.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .text("")
                    .build();

            //Для отправки фотографий
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);

            //Для отправки клавиатуры
            EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .build();

            if(callbackData.equals(buttonForStartTgBot.getCallbackData()) ||
                    callbackData.equals(buttonForReturnMenu.getCallbackData())){
                editMessageText.setText("Выберите пункт меню:");
                editMessageReplyMarkup.setReplyMarkup(keyboardForMenu);
            } else if (callbackData.equals(buttonForCategories.getCallbackData())) {
                editMessageText.setText("Выберите категорию товаров");
                editMessageReplyMarkup.setReplyMarkup(keyboardForAllCategories);
            } else if (callbackData.equals(buttonForLiquidCollagen.getCallbackData())) {
                editMessageText.setText("Выберите товар:");
                editMessageReplyMarkup.setReplyMarkup(keyboardForLiquidCollagen);
            } else if (callbackData.equals(buttonForKinohimitsuCollagenMen530016sDrink.getCallbackData())) {
                sendPhoto.setCaption(buttonForKinohimitsuCollagenMen530016sDrink.getText());
                sendPhoto.setPhoto(
                        new InputFile(
                                new File("src/main/resources/data/Collagen-Men-5300-16s.jpg")
                        )
                );
                try{
                    execute(sendPhoto);
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }

            try {
                execute(editMessageText);
                execute(editMessageReplyMarkup);
            }catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "@MatosyanTGBot";
    }

    @Override
    public String getBotToken() {
        return "8004012680:AAEfvyYY8R44wFfIGunrWkTFaowWxH5-zbE";
    }
}
