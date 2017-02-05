package com.tidal.refactoring.playlist.data;

import java.util.Date;
import java.util.Iterator;
import java.util.UUID;


/**
 * A very simplified version of TrackPlaylist
 *
 * List of PlayList is managed by PlayListTrackIndexSet collection.
 * this collection take care of index management of tracks when collection is manipulated
 * Implementation of collection is hidden now. clien only use methods available in  PlayList class
 * Implimenraion can be changed in future without impacting clients
 */
public class PlayList {
    private Integer id;
    private String playListName;
    private PlayListTrackIndexSet playListTracks= new PlayListTrackIndexSet();
    private Date registeredDate;
    private Date lastUpdated;
    private String uuid;
    private int nrOfTracks;
    private boolean deleted;
    private Float duration;

    public PlayList() {
        this.uuid = UUID.randomUUID().toString();
        Date d = new Date();
        this.registeredDate = d;
        this.lastUpdated = d;
        this.playListTracks =new PlayListTrackIndexSet();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    private PlayListTrackIndexSet getPlayListTracks() {
        return playListTracks;
    }

    private void setPlayListTracks(PlayListTrackIndexSet playListTracks) {
        this.playListTracks = playListTracks;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }


    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    //Number of tracks should be governed by the underlying collection
    public int getNrOfTracks() {
        return playListTracks.size();
    }


    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public boolean isEmpty() {
        return getPlayListTracks().isEmpty();
    }

    public PlayListTrack get(int index) {
        return getPlayListTracks().get(index);
    }

    public Iterator<PlayListTrack> iterator() {
        return getPlayListTracks().iterator();
    }

    public void add(PlayListTrack e) {
        getPlayListTracks().add(e);
    }

    public void add(int index, PlayListTrack e) {
        getPlayListTracks().add(index, e);
    }

    public PlayListTrack remove(int index) {
        return getPlayListTracks().remove(index);
    }

    public boolean remove(PlayListTrack e) {
        return getPlayListTracks().remove(e);
    }




}