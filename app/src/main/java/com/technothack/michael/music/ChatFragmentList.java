package com.technothack.michael.music;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.technothack.michael.music.dummy.DummyContent;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TG;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ChatFragmentList extends Fragment {
    public static String ARG_MSG = "message";

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ChatHistory chatHistory;
    private ChatMessageRecyclerViewAdapter adapter;
    private Button button;
    private int counter;
    private Semaphore semaphore = new Semaphore(0);

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChatFragmentList() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ChatFragmentList newInstance(int columnCount) {
        ChatFragmentList fragment = new ChatFragmentList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        counter = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chat_list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        chatHistory = new ChatHistory();
        String username = "dihtmipt";
        TdApi.SearchPublicChat searchPublicChat=new TdApi.SearchPublicChat(username);
        TG.getClientInstance().send(searchPublicChat, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TdApi.Chat chat = (TdApi.Chat) object;
                TdApi.Message topMessage = chat.topMessage;

                long chatId = chat.id;

                TdApi.GetChatHistory getChatHistory = new TdApi.GetChatHistory(chatId, topMessage.id, 0, 15);
                TG.getClientInstance().send(getChatHistory, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        TdApi.Messages messages = (TdApi.Messages) object;
                        for (TdApi.Message message: messages.messages) {
                            if (message.content instanceof TdApi.MessageText) {
                                chatHistory.addItem(new ChatHistory.ChatMessage(
                                        Integer.valueOf(message.id).toString(),
                                        ((TdApi.MessageText) message.content).text,
                                        ""));
                            }
                        }
                        semaphore.release();
                    }
                });
            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        adapter = new ChatMessageRecyclerViewAdapter(chatHistory, mListener);
        recyclerView.setAdapter(adapter);
        button = view.findViewById(R.id.sendToBot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                chatHistory.addItem(new ChatHistory.ChatMessage(Integer.valueOf(counter).toString(), "Новое сообщение", ""));
                adapter.notifyItemInserted(chatHistory.items.size()-1);
                /*FragmentManager manager = getFragmentManager();
                MyAlertDialogFragment myDialogFragment = new MyAlertDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ARG_MSG, "Вот такая ошибка");
                myDialogFragment.setArguments(bundle);
                myDialogFragment.show(manager, "dialog");*/
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ChatHistory.ChatMessage item);
    }

    public class ChatMessageRecyclerViewAdapter extends RecyclerView.Adapter<ChatMessageRecyclerViewAdapter.ViewHolder> {

        private ChatHistory values;
        private List<ChatHistory.ChatMessage> mValues;
        private final OnListFragmentInteractionListener mListener;

        public ChatMessageRecyclerViewAdapter(ChatHistory items, OnListFragmentInteractionListener listener) {
            values = items;
            mValues = values.items;
            mListener = listener;
        }

        @Override
        public ChatMessageRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item, parent, false);
            return new ChatMessageRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ChatMessageRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public ChatHistory.ChatMessage mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}