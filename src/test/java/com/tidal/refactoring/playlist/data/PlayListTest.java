package com.tidal.refactoring.playlist.data;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
    * BUG:Failing Test before Refactoring
    * */
    @Test
    public void test_playlist_number_of_track_should_depend_upon_size_of_underneath_collection(){
        PlayList playList=createPlayListWithMutableNumberOfTracks(uuid,10);
        Assert.assertEquals(playList.getNrOfTracks(),playList.getPlayListTracks().size(),"Number of tracks should be 10");
    }

    private PlayList createPlayListWithMutableNumberOfTracks(String uuid,int numberOfTracks) {
        PlayList trackPlayList = new PlayList();
        Set<PlayListTrack> playListTracks=getPlaylistTracks(numberOfTracks);
        trackPlayList.setDeleted(false);
        trackPlayList.setDuration((float) (60 * 60 * 2));
        trackPlayList.setId(49834);
        trackPlayList.setLastUpdated(new Date());
        trackPlayList.setNrOfTracks(100);
        trackPlayList.setPlayListName("Collection of great songs");
        trackPlayList.setPlayListTracks(playListTracks);
        trackPlayList.setUuid(uuid);

        return trackPlayList;
    }
    private static Set<PlayListTrack> getPlaylistTracks(int numberOfTracks) {

        Set<PlayListTrack> playListTracks = new HashSet<PlayListTrack>();
        for (int i = 0; i < numberOfTracks; i++) {
            PlayListTrack playListTrack = new PlayListTrack();
            playListTrack.setDateAdded(new Date());
            playListTrack.setId(i + 1);
            playListTrack.setIndex(i);
            playListTrack.setTrack(new Track());
            playListTracks.add(playListTrack);
        }

        return playListTracks;
    }
}
