package com.technothack.michael.music;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by michael on 08.11.17.
 */

public class MusicHolder {
    private static MusicHolder mh;

    public Scanner mp3Scanner;

    // норм справка, кажется, все что нужно есть
    // https://github.com/mpatric/mp3agic
    public ArrayList<Mp3File> mp3files;


    private MusicHolder(){

    }

    public static MusicHolder getMe(){
        if (mh == null){
            mh = new MusicHolder();
        }
        return mh;
    }

    void scanForMusic(ProgressBar progressBar, Runnable run){
        new Loader(progressBar, run).execute();
    }

    private class Loader extends AsyncTask<Void, Void, Void> {


        private ProgressBar progressBar;
        private Runnable post;

        Loader(ProgressBar progressBar, Runnable post) {
            super();
            this.progressBar = progressBar;
            this.post = post;
            mp3Scanner = new Scanner(".mp3");
        }

        @Override
        protected Void doInBackground(Void... unused) {
            mp3Scanner.run();
//            for (String path : mp3Scanner.getAbsolutePaths()){
//                try {
//                    mp3files.add(new Mp3File(path));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (UnsupportedTagException e) {
//                    e.printStackTrace();
//                } catch (InvalidDataException e) {
//                    e.printStackTrace();
//                }
//            }
            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... unused) {
            progressBar.incrementProgressBy(10);
        }

        @Override
        protected void onPostExecute(Void unused) {
            this.post.run();
        }
    }

}
