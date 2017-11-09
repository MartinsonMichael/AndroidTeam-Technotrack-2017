package com.technothack.michael.music;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v2ChapterFrameData;
import com.mpatric.mp3agic.ID3v2ChapterTOCFrameData;
import com.mpatric.mp3agic.ID3v2FrameSet;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by michael on 08.11.17.
 */

public class MusicHolder {
    private static MusicHolder mh;

    static private final ID3v2 emptyID3v3 = new ID3v2() {
        @Override
        public boolean getPadding() {
            return false;
        }

        @Override
        public void setPadding(boolean padding) {

        }

        @Override
        public boolean hasFooter() {
            return false;
        }

        @Override
        public void setFooter(boolean footer) {

        }

        @Override
        public boolean hasUnsynchronisation() {
            return false;
        }

        @Override
        public void setUnsynchronisation(boolean unsynchronisation) {

        }

        @Override
        public int getBPM() {
            return 0;
        }

        @Override
        public void setBPM(int bpm) {

        }

        @Override
        public String getGrouping() {
            return null;
        }

        @Override
        public void setGrouping(String grouping) {

        }

        @Override
        public String getKey() {
            return null;
        }

        @Override
        public void setKey(String key) {

        }

        @Override
        public String getDate() {
            return null;
        }

        @Override
        public void setDate(String date) {

        }

        @Override
        public String getComposer() {
            return "None";
        }

        @Override
        public void setComposer(String composer) {

        }

        @Override
        public String getPublisher() {
            return "None";
        }

        @Override
        public void setPublisher(String publisher) {

        }

        @Override
        public String getOriginalArtist() {
            return "None";
        }

        @Override
        public void setOriginalArtist(String originalArtist) {

        }

        @Override
        public String getAlbumArtist() {
            return "None";
        }

        @Override
        public void setAlbumArtist(String albumArtist) {

        }

        @Override
        public String getCopyright() {
            return "None";
        }

        @Override
        public void setCopyright(String copyright) {

        }

        @Override
        public String getArtistUrl() {
            return "None";
        }

        @Override
        public void setArtistUrl(String url) {

        }

        @Override
        public String getCommercialUrl() {
            return "None";
        }

        @Override
        public void setCommercialUrl(String url) {

        }

        @Override
        public String getCopyrightUrl() {
            return "None";
        }

        @Override
        public void setCopyrightUrl(String url) {

        }

        @Override
        public String getAudiofileUrl() {
            return "None";
        }

        @Override
        public void setAudiofileUrl(String url) {

        }

        @Override
        public String getAudioSourceUrl() {
            return "None";
        }

        @Override
        public void setAudioSourceUrl(String url) {

        }

        @Override
        public String getRadiostationUrl() {
            return "None";
        }

        @Override
        public void setRadiostationUrl(String url) {

        }

        @Override
        public String getPaymentUrl() {
            return "None";
        }

        @Override
        public void setPaymentUrl(String url) {

        }

        @Override
        public String getPublisherUrl() {
            return "None";
        }

        @Override
        public void setPublisherUrl(String url) {

        }

        @Override
        public String getUrl() {
            return "None";
        }

        @Override
        public void setUrl(String url) {

        }

        @Override
        public String getPartOfSet() {
            return "None";
        }

        @Override
        public void setPartOfSet(String partOfSet) {

        }

        @Override
        public boolean isCompilation() {
            return false;
        }

        @Override
        public void setCompilation(boolean compilation) {

        }

        @Override
        public ArrayList<ID3v2ChapterFrameData> getChapters() {
            return null;
        }

        @Override
        public void setChapters(ArrayList<ID3v2ChapterFrameData> chapters) {

        }

        @Override
        public ArrayList<ID3v2ChapterTOCFrameData> getChapterTOC() {
            return null;
        }

        @Override
        public void setChapterTOC(ArrayList<ID3v2ChapterTOCFrameData> ctoc) {

        }

        @Override
        public String getEncoder() {
            return "None";
        }

        @Override
        public void setEncoder(String encoder) {

        }

        @Override
        public byte[] getAlbumImage() {
            return new byte[0];
        }

        @Override
        public void setAlbumImage(byte[] albumImage, String mimeType) {

        }

        @Override
        public void setAlbumImage(byte[] albumImage, String mimeType, byte imageType, String imageDescription) {

        }

        @Override
        public void clearAlbumImage() {

        }

        @Override
        public String getAlbumImageMimeType() {
            return "None";
        }

        @Override
        public int getWmpRating() {
            return 0;
        }

        @Override
        public void setWmpRating(int rating) {

        }

        @Override
        public String getItunesComment() {
            return "None";
        }

        @Override
        public void setItunesComment(String itunesComment) {

        }

        @Override
        public String getLyrics() {
            return "None";
        }

        @Override
        public void setLyrics(String lyrics) {

        }

        @Override
        public void setGenreDescription(String text) {

        }

        @Override
        public int getDataLength() {
            return 0;
        }

        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public boolean getObseleteFormat() {
            return false;
        }

        @Override
        public Map<String, ID3v2FrameSet> getFrameSets() {
            return null;
        }

        @Override
        public void clearFrameSet(String id) {

        }

        @Override
        public String getVersion() {
            return "None";
        }

        @Override
        public String getTrack() {
            return "None";
        }

        @Override
        public void setTrack(String track) {

        }

        @Override
        public String getArtist() {
            return "None";
        }

        @Override
        public void setArtist(String artist) {

        }

        @Override
        public String getTitle() {
            return "None";
        }

        @Override
        public void setTitle(String title) {

        }

        @Override
        public String getAlbum() {
            return "None";
        }

        @Override
        public void setAlbum(String album) {

        }

        @Override
        public String getYear() {
            return "None";
        }

        @Override
        public void setYear(String year) {

        }

        @Override
        public int getGenre() {
            return 0;
        }

        @Override
        public void setGenre(int genre) {

        }

        @Override
        public String getGenreDescription() {
            return "None";
        }

        @Override
        public String getComment() {
            return "None";
        }

        @Override
        public void setComment(String comment) {

        }

        @Override
        public byte[] toBytes() throws NotSupportedException {
            return new byte[0];
        }
    };
    public Scanner mp3Scanner;
    // норм справка, кажется, все что нужно есть
    // https://github.com/mpatric/mp3agic
    public ArrayList<Mp3File> mp3files;
    public ArrayList<ID3v2> mp3info;


    private MusicHolder(){

    }

    public static MusicHolder getMe(){
        if (mh == null){
            mh = new MusicHolder();
        }
        return mh;
    }

    public void scanForMusic(ProgressBar progressBar, Runnable run){
        new Loader(progressBar, run).execute();
    }

    public ID3v2 getTrackInfo(int index){

        // Нихуя не работает, блять
        //return mp3info.get(index);

        return emptyID3v3;
    }

    public ArrayList<ID3v2> getTracksInfo(){
        return mp3info;
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
            int index  = 0;

            for (String path : mp3Scanner.getAbsolutePaths()){
                try {
                    mp3files.add(new Mp3File(new URL(path).toString() ));
                    if (mp3files.get(index).hasId3v2Tag()) {
                        mp3info.add(mp3files.get(index).getId3v2Tag());
                    }
                    else{
                        mp3info.add(emptyID3v3);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedTagException e) {
                    e.printStackTrace();
                } catch (InvalidDataException e) {
                    e.printStackTrace();
                }

                index++;
            }
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
