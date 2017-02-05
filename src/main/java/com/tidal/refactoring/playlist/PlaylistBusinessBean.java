package com.tidal.refactoring.playlist;

import com.google.inject.Inject;
import com.tidal.refactoring.playlist.dao.PlaylistDaoBean;
import com.tidal.refactoring.playlist.data.PlayList;
import com.tidal.refactoring.playlist.data.PlayListTrack;
import com.tidal.refactoring.playlist.data.Track;
import com.tidal.refactoring.playlist.exception.PlaylistException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistBusinessBean {

    //this should be configurable vie properties file or resource database
    public static final int MAX_SIZE_OF_PLAYLIST=500;

    private PlaylistDaoBean playlistDaoBean;

    @Inject
    public PlaylistBusinessBean(PlaylistDaoBean playlistDaoBean){
        this.playlistDaoBean = playlistDaoBean;
    }

    /**
     * Add tracks to the index returns added tracks
     */
    public List<PlayListTrack> addTracks(String uuid, List<Track> tracksToAdd, int toIndex) throws PlaylistException {
        try{
            //exception handling code should be handled in different method from actual logic
            return addTracksInternal(uuid, tracksToAdd, toIndex);
        }catch (Exception e) {
            e.printStackTrace();
            throw new PlaylistException("Generic error");
        }
    }

    /**
     * Remove the tracks from the playlist located at the sent indexes
     */
    public List<PlayListTrack> removeTracks(String uuid, List<Integer> indexes) throws PlaylistException {
        try{
            //exception handling code should be handled in different method from actual logic
            return removeTracksInternal(uuid,indexes);
        }catch (Exception e) {
            e.printStackTrace();
            throw new PlaylistException("Generic error");
        }
    }

    private List<PlayListTrack> removeTracksInternal(String uuid, List<Integer> indexes){
        List<PlayListTrack> removedTracks= new ArrayList<>(indexes.size());
        PlayList playList = playlistDaoBean.getPlaylistByUUID(uuid);
        if(!validatePlayListForRemoval(playList) ||!validateIndexToBeRemoved(indexes)){
            return Collections.emptyList();
        }
        int playlistSize=playList.getNrOfTracks();
        for(Integer i:indexes){
            if(indexExists(i,playlistSize)){
                    removedTracks.add(removeSingleTrack(i,playList));
            }else{
                //in real code Logging framework should be used
                System.out.println("LOGGER.warn(Index "+i+ "do not exist in playlist id "+playList.getUuid()+" )");
                throw new PlaylistException("Index "+i+ " do not exist in playlist id "+playList.getUuid());
            }
        }
        return removedTracks;
    }



    private List<PlayListTrack> addTracksInternal(String uuid, List<Track> tracksToAdd, int toIndex){

        List<PlayListTrack> addedTracks= new ArrayList<>(tracksToAdd.size());
        PlayList playList = playlistDaoBean.getPlaylistByUUID(uuid);

        //if tracks to be added or playlist is null,empty list should be returned
        if(!validateTracksToBeAdded(tracksToAdd)|| !validatePlayList(playList)){
            return Collections.emptyList();
        }
        validateMaximumTracksInPlayList(playList,tracksToAdd);
        for (Track track:tracksToAdd){
            addedTracks.add(addSingleTrackToPlaylist(toIndex++,track,playList));
        }
        return addedTracks;
    }

    private PlayListTrack addSingleTrackToPlaylist(int toIndex,Track track, PlayList playList) {
        int actualIndex= getActualNextIndex(playList,toIndex);
        PlayListTrack playListTrack=PlayListTrack.newPlayListTrackFromPlayListAndTrack(playList,track);
        playList.add(actualIndex,playListTrack);
        playList.setDuration(addTrackDurationToPlaylist(playList, track));
        return playListTrack;
    }

    /*
    * The index is out of bounds, put it in the end of the list.
    * existing logic is ambiguous . adding list to end if given index is -1 or greater then existing index
    * but do nothing if index given is negative.its a possible bug
    * fixed in below implementation
    * */
    private int getActualNextIndex(PlayList playList, int toIndex) {
        int currentMaxIndexSize=playList.getNrOfTracks()-1;//
        int actualIndex=toIndex;
        if(toIndex<0 || toIndex>currentMaxIndexSize){
            actualIndex=currentMaxIndexSize+1;
        }
        return actualIndex;
    }

    /*
    * playlist should not be null
    * */
    private boolean validatePlayList(PlayList playList) {
        return !(playList==null);
    }

    private boolean validatePlayListForRemoval(PlayList playList){
        //re using existing method validatePlayList
        return validatePlayList(playList)||!playList.isEmpty();
    }

    /*
    * track to be added should not be null of empty.
    * more conditions can be added in future here. thus its good idea to put this in separate method
    * */
    private boolean validateTracksToBeAdded(List<Track> tracksToAdd) {
        return !(tracksToAdd==null ||tracksToAdd.isEmpty());
    }

    private boolean validateIndexToBeRemoved(List<Integer> indexList) {
        return !(indexList==null ||indexList.isEmpty());
    }

    //We do not allow > 500 tracks in new playlists
    private void validateMaximumTracksInPlayList(PlayList playList, List<Track> tracksToAdd) {
        int noOfExistingTracks=playList.getNrOfTracks();
        int noOfTracksToBeAdded=tracksToAdd.size();
        int newPlayListSize=noOfExistingTracks+noOfTracksToBeAdded;
        if (newPlayListSize> MAX_SIZE_OF_PLAYLIST) {
            throw new PlaylistException("Playlist cannot have more than " + 500 + " tracks");
        }
    }



    private float addTrackDurationToPlaylist(PlayList playList, Track track) {
        return (track != null ? track.getDuration() : 0)
                + (playList != null && playList.getDuration() != null ? playList.getDuration() : 0);
    }


    private PlayListTrack removeSingleTrack(Integer i, PlayList playList) {
        return playList.remove(i);
    }

    /*
    * checking if index fall in range of current playlist indexes
    * */
    private boolean indexExists(int index,int playListSize) {
        return (index>=0 && index<playListSize);
    }
}
