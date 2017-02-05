package com.tidal.refactoring.playlist.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ben on 05-02-2017.
 */

/*
* In original code logic for Indexing was in service class. but logic related to collection should not reside
* in service layer but in collection itself. service layer should focus only on application logic.
* moreover logic in service layer was traversing multiple time for set to arraylist and aarraylist to set conversion
* along with reindexing logic. that was impacting performance of the method.
*
* this class take benefit of existing feature of arraylist index and adds reIndexing logic for playlist index
*performance of method have been improved significantly
*
* */
public class PlayListTrackIndexSet {
    private List<PlayListTrack> list= new ArrayList<>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public PlayListTrack get(int index){
        return list.get(index);
    }

    public boolean contains(PlayListTrack o) {
        return list.contains(o);
    }

    public Iterator<PlayListTrack> iterator() {
        return list.iterator();
    }

    public PlayListTrack[] toArray(PlayListTrack[] a) {
        return list.toArray(a);
    }

    /*
    * this method internally use add method with index
    * */
    public void add(PlayListTrack e) {
        add(list.size(),e);
    }
    public void add(int index,PlayListTrack e) {
        int existingIndex=list.indexOf(e);
        list.add(index,e);
        if(existingIndex!=-1){
            list.remove(existingIndex);
        }
        reIndex();
    }

    /*
    * this method re index all track in playlist
    * */
    private void reIndex() {
        for(int i=0;i<list.size();i++){
            list.get(i).setIndex(i);
        }
    }

    public PlayListTrack remove(int index) {
        PlayListTrack removed =list.remove(index);
        reIndex();
        return removed;
    }

    public boolean remove(PlayListTrack e) {
        boolean flag=list.remove(e);
        reIndex();
        return flag;
    }

    public boolean containsAll(Collection<PlayListTrack> c) {
        return list.containsAll(c);
    }

    public void clear() {
        list.clear();
    }
}
