package com.tidal.refactoring.playlist.data;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by ben on 05-02-2017.
 */
public class PlayListTest {

    @InjectMocks
    PlayList playList;

    @BeforeClass
    public void classSetUp() {
        MockitoAnnotations.initMocks(this);
    }
    private String uuid;

    @BeforeMethod
    public void setUp() throws Exception {
        uuid= UUID.randomUUID().toString();
    }

    /*
    No of tracks could be menupulated irrespective of size of underlying collection
    * BUG:Failing Test before Refactoring
    *
    *
    * */
    @Test
    public void test_playlist_number_of_track_should_depend_upon_size_of_underneath_collection(){
        PlayList playList=createPlayListWithMutableNumberOfTracks(uuid,10);
        Assert.assertEquals(playList.getNrOfTracks(),10,"Number of tracks should be 10");
    }

    private PlayList createPlayListWithMutableNumberOfTracks(String uuid,int numberOfTracks) {
        PlayList trackPlayList = getPlaylistTracks(numberOfTracks);
        trackPlayList.setDeleted(false);
        trackPlayList.setDuration((float) (60 * 60 * 2));
        trackPlayList.setId(49834);
        trackPlayList.setLastUpdated(new Date());
        trackPlayList.setPlayListName("Collection of great songs");
        trackPlayList.setUuid(uuid);

        return trackPlayList;
    }
    private static PlayList getPlaylistTracks(int numberOfTracks) {

        PlayList playListTracks= new PlayList();
        for (int i = 0; i < numberOfTracks; i++) {
            PlayListTrack playListTrack = new PlayListTrack();
            playListTrack.setDateAdded(new Date());
            playListTrack.setId(i + 1);
            playListTrack.setIndex(i);
            playListTrack.setTrack(getTrack(i));
            playListTracks.add(playListTrack);
        }

        return playListTracks;
    }

    private static Track getTrack(int id) {
        Random randomGenerator = new Random();

        Track track = new Track();
        track.setArtistId(randomGenerator.nextInt(10000));
        track.setDuration(60 * 3);

        int trackNumber = randomGenerator.nextInt(15);
        track.setTitle("Track no: " + trackNumber);
        track.setId(id);

        return track;
    }
}
