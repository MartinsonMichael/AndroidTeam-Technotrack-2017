package com.technothack.michael.music;

import android.content.Context;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TG;
import org.drinkless.td.libcore.telegram.TdApi;


public class TelegramClient {
    private final Client client;
    private static volatile TelegramClient instance;

    public TelegramClient() {
        client = TG.getClientInstance();
    }

    public TelegramClient(Context context, Client.ResultHandler updatesHandler) {
        TG.setDir(context.getCacheDir().getAbsolutePath());
        TG.setFilesDir(context.getFilesDir().getAbsolutePath());
        client = TG.getClientInstance();
        TG.setUpdatesHandler(updatesHandler);
    }

    public static TelegramClient getInstance() {
        TelegramClient localInstance = instance;
        if (localInstance == null) {
            synchronized (TelegramClient.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TelegramClient();
                }
            }
        }
        return localInstance;
    }

    public static TelegramClient getInstance(Context context, Client.ResultHandler updatesHandler) {
        TelegramClient localInstance = instance;
        if (localInstance == null) {
            synchronized (TelegramClient.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TelegramClient(context, updatesHandler);
                }
            }
        }
        return localInstance;
    }

    public void clearAuth(Client.ResultHandler resultHandler) {
        TdApi.ResetAuth request = new TdApi.ResetAuth(true);
        client.send(request, resultHandler);
    }

    public void getAuthState(Client.ResultHandler resultHandler) {
        TdApi.GetAuthState req = new TdApi.GetAuthState();
        client.send(req, resultHandler);
    }

    public void sendPhone(String phone, Client.ResultHandler resultHandler) {
        TdApi.SetAuthPhoneNumber smsSender = new TdApi.SetAuthPhoneNumber(phone, false, true);
        client.send(smsSender, resultHandler);

    }

    public void checkCode(String code, String firstName, String lastName, Client.ResultHandler resultHandler) {
        TdApi.CheckAuthCode request = new TdApi.CheckAuthCode(code, firstName, lastName);
        client.send(request, resultHandler);
    }


    public void sendMessage(long chatId, String text, Client.ResultHandler resultHandler) {
        TdApi.InputMessageContent msg = new TdApi.InputMessageText(text, false, false, null, null);
        TdApi.SendMessage request = new TdApi.SendMessage(chatId, 0, false, false, null, msg);
        client.send(request, resultHandler);
    }

    public void getLastIncomingMessage(long chatId, int fromMessageId, Client.ResultHandler resultHandler) {
        getChat(chatId, chatObj -> {
            if (chatObj instanceof TdApi.Chat) {
                TdApi.GetChatHistory getChatHistory = new TdApi.GetChatHistory(chatId, fromMessageId, -1, 5);
                client.send(getChatHistory, messagesObj -> {
                    if (messagesObj instanceof TdApi.Messages) {
                        TdApi.Messages messages = (TdApi.Messages) messagesObj;
                        if (messages.totalCount > 0) {
                            for (TdApi.Message message : messages.messages) {

                                if (message.id != fromMessageId) {
                                    resultHandler.onResult(message);
                                    return;
                                }
                            }
                        }
                        resultHandler.onResult(new TdApi.Error(0, "Unable to get incoming message"));
                    } else resultHandler.onResult(messagesObj);
                });
            } else resultHandler.onResult(chatObj);
        });


    }

    public void getChat(long chatId, Client.ResultHandler resultHandler) {
        TdApi.GetChat getChat = new TdApi.GetChat(chatId);
        client.send(getChat, resultHandler);
    }


    public void searchContact(String username, Client.ResultHandler resultHandler) {
        TdApi.SearchPublicChat searchContacts = new TdApi.SearchPublicChat(username);
        client.send(searchContacts, resultHandler);
    }

    public void getMe(Client.ResultHandler resultHandler) {
        client.send(new TdApi.GetMe(), resultHandler);
    }

    public void changeUsername(String username, Client.ResultHandler resultHandler) {
        client.send(new TdApi.ChangeUsername(username), resultHandler);
    }

    public void startChatWithBot(int botUserId, long chatId, Client.ResultHandler resultHandler) {

        TdApi.CloseChat closeChat = new TdApi.CloseChat(chatId);
        client.send(closeChat, resClose -> {
            TdApi.OpenChat openChat = new TdApi.OpenChat(chatId);
            client.send(openChat, resOpen -> {
                if (resOpen instanceof TdApi.Error) {
                    resultHandler.onResult(resOpen);
                    return;
                }

                TdApi.SendBotStartMessage request = new TdApi.SendBotStartMessage(botUserId, chatId, "/start");
                client.send(request, resultHandler);
            });
        });
    }

    public void logout(Client.ResultHandler resultHandler) {
        client.send(new TdApi.ResetAuth(false), resultHandler);
    }
}
