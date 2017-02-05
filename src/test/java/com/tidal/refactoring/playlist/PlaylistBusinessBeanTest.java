package com.tidal.refactoring.playlist;

import com.tidal.refactoring.playlist.dao.PlaylistDaoBean;
import com.tidal.refactoring.playlist.data.PlayList;
import com.tidal.refactoring.playlist.data.PlayListTrack;
import com.tidal.refactoring.playlist.data.Track;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.AssertJUnit.assertTrue;

public class PlaylistBusinessBeanTest {

    @Mock
    PlaylistDaoBean daoBean;

    @InjectMocks
    PlaylistBusinessBean playlistBusinessBean;

    @BeforeClass
    public void classSetUp() {
        MockitoAnnotations.initMocks(this);
    }

    private String uuid;

    @BeforeMethod
    public void setUp() throws Exception {
        uuid=UUID.randomUUID().toString();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddTracks(){
        Mockito.when(daoBean.getPlaylistByUUID(uuid)).thenReturn(createPlayList(uuid));
        List<Track> trackList = new ArrayList<Track>();
        Track track = new Track();
        track.setArtistId(4);
        track.setTitle("A brand new track");
        track.setId(76868);
        trackList.add(track);
        List<PlayListTrack> playListTracks = playlistBusinessBean.addTracks(uuid, trackList, 5);
        assertTrue(playListTracks.size() > 0);
    }



    //Added below methods from PlayListDaoBean to make testing of PlayListBusinessBean standalone
    private PlayList createPlayList(String uuid) {
        PlayList trackPlayList = new PlayList();
        trackPlayList.setDeleted(false);
        trackPlayList.setDuration((float) (60 * 60 * 2));
        trackPlayList.setId(49834);
        trackPlayList.setLastUpdated(new Date());
        trackPlayList.setNrOfTracks(376);
        trackPlayList.setPlayListName("Collection of great songs");
        trackPlayList.setPlayListTracks(getPlaylistTracks());
        trackPlayList.setUuid(uuid);

        return trackPlayList;
    }

    private static Set<PlayListTrack> getPlaylistTracks() {

        Set<PlayListTrack> playListTracks = new HashSet<PlayListTrack>();
        for (int i = 0; i < 376; i++) {
            PlayListTrack playListTrack = new PlayListTrack();
            playListTrack.setDateAdded(new Date());
            playListTrack.setId(i + 1);
            playListTrack.setIndex(i);
            playListTrack.setTrack(getTrack());

        }

        return playListTracks;
    }
    private static Track getTrack() {
        Random randomGenerator = new Random();

        Track track = new Track();
        track.setArtistId(randomGenerator.nextInt(10000));
        track.setDuration(60 * 3);

        int trackNumber = randomGenerator.nextInt(15);
        track.setTitle("Track no: " + trackNumber);

        return track;
    }


}