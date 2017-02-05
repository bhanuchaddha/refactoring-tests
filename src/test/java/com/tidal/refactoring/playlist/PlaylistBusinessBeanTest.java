package com.tidal.refactoring.playlist;

import com.tidal.refactoring.playlist.dao.PlaylistDaoBean;
import com.tidal.refactoring.playlist.data.PlayList;
import com.tidal.refactoring.playlist.data.PlayListTrack;
import com.tidal.refactoring.playlist.data.Track;
import com.tidal.refactoring.playlist.exception.PlaylistException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
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
        Mockito.when(daoBean.getPlaylistByUUID(uuid)).thenReturn(createPlayList(uuid,10));
        List<PlayListTrack> playListTracks = playlistBusinessBean.addTracks(uuid, createTrackListHaving1Track(), 5);
        assertTrue(playListTracks.size() > 0);
    }

    @Test(expectedExceptions = PlaylistException.class)
    public void test_more_than_500_tracks_can_not_be_added_to_playlist(){
        Mockito.when(daoBean.getPlaylistByUUID(uuid)).thenReturn(createPlayList(uuid,500));
        List<PlayListTrack> playListTracks = playlistBusinessBean.addTracks(uuid, createTrackListHaving1Track(), 5);
    }



    @Test
    public void test_track_should_be_be_added_to_correct_index(){
        PlayList existingPlayList=createPlayList(uuid,10);
        Mockito.when(daoBean.getPlaylistByUUID(uuid)).thenReturn(existingPlayList);
        List<PlayListTrack> playListTracks = playlistBusinessBean.addTracks(uuid, createTrackListHaving1Track(), 5);
        PlayListTrack playListTrack=null;
        for (PlayListTrack track:existingPlayList.getPlayListTracks()){
            if(track.getIndex()==5){
                playListTrack=track;
            }
        }
        Assert.assertEquals(playListTrack.getTrack().getId(),76868,"track id at index 5 should be 76868");
    }



    @Test
    public void test_track_should_be_be_added_last_index_if_given_index_is_greater_than_existing_indexes(){
        int currentNumberOfTracks=10;
        int currentIndex=currentNumberOfTracks-1;
        int nextExpectedIndex=currentIndex+1;
        PlayList existingPlayList=createPlayList(uuid,currentNumberOfTracks);
        Mockito.when(daoBean.getPlaylistByUUID(uuid)).thenReturn(existingPlayList);
        playlistBusinessBean.addTracks(uuid, createTrackListHaving1Track(), 13);
        PlayListTrack playListTrack=null;
        for (PlayListTrack track:existingPlayList.getPlayListTracks()){
            if(track.getTrack().getId()==76868){
                playListTrack=track;
            }
        }
        Assert.assertEquals(playListTrack.getIndex(),nextExpectedIndex,"track should be added to index "+nextExpectedIndex);
    }


    /*
    As per the comments if index is out of bound.track should be added to last
    * BUG:Failing Test before Refactoring
    * */
    @Test
    public void test_track_should_be_added_to_end_if_index_is_negative(){
        int currentNumberOfTracks=10;
        int currentIndex=currentNumberOfTracks-1;
        int nextExpectedIndex=currentIndex+1;
        PlayList existingPlayList=createPlayList(uuid,currentNumberOfTracks);
        Mockito.when(daoBean.getPlaylistByUUID(uuid)).thenReturn(existingPlayList);
        playlistBusinessBean.addTracks(uuid, createTrackListHaving1Track(), -10);
        PlayListTrack playListTrack=null;
        for (PlayListTrack track:existingPlayList.getPlayListTracks()){
            if(track.getTrack().getId()==76868){
                playListTrack=track;
            }
        }
        Assert.assertEquals(playListTrack.getIndex(),nextExpectedIndex,"track should be added to index "+nextExpectedIndex);
    }

    /*
    * taking 7601121 nanoseconds before refactoring
    * */
    @Test
    public void test_performance_of_add_method(){
        Mockito.when(daoBean.getPlaylistByUUID(uuid)).thenReturn(createPlayList(uuid,400));
        List<Track> tracksToBeAdded=createTrackListHaving100Track();
        long startTime = System.nanoTime();
        List<PlayListTrack> playListTracks = playlistBusinessBean.addTracks(uuid,tracksToBeAdded , 200);
        long endTime = System.nanoTime();
        System.out.println("That took " + (endTime - startTime) + " nanoseconds");
        Assert.assertEquals(playListTracks.size(),100,"100 new tracks should be added");
        Assert.assertTrue((endTime - startTime)<8001121);
    }


    private List<Track> createTrackListHaving1Track(){
        List<Track> trackList = new ArrayList<>();
        Track track = new Track();
        track.setArtistId(4);
        track.setTitle("A brand new track");
        track.setId(76868);
        trackList.add(track);
        return trackList;
    }

    private List<Track> createTrackListHaving100Track(){
        List<Track> trackList=new ArrayList<>();
        for(int i=1;i<=100;i++){
            Track track = new Track();
            track.setArtistId(4);
            track.setTitle("A brand new track"+"_"+i);
            track.setId(76868+i);
            trackList.add(track);
        }
        return trackList;
    }



    /*Added below methods from PlayListDaoBean to make
     testing of PlayListBusinessBean standalone*/

    private PlayList createPlayList(String uuid,int numberOfTracks) {
        PlayList trackPlayList = new PlayList();
        Set<PlayListTrack> playListTracks=getPlaylistTracks(numberOfTracks);
        trackPlayList.setDeleted(false);
        trackPlayList.setDuration((float) (60 * 60 * 2));
        trackPlayList.setId(49834);
        trackPlayList.setLastUpdated(new Date());
        trackPlayList.setNrOfTracks(playListTracks.size());
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
            playListTrack.setTrack(getTrack());
            playListTracks.add(playListTrack);
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