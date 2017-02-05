package com.tidal.refactoring.playlist.dao;

/**
 * Class faking the data layer, and returning fake playlists
 */
/*
* Mocked class not required.Mocking Handled by Mockito now
* */

/*public class PlaylistDaoBean implements PlaylistDaoBean{

    private final Map<String, PlayList> playlists = new HashMap<String, PlayList>();

    @Override
    public PlayList getPlaylistByUUID(String uuid) {

        PlayList playList = playlists.get(uuid);

        if (playList != null) {
            return playList;
        }

        //return default playlist
        return createPlayList(uuid);
    }

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

    public static Track getTrack() {
        Random randomGenerator = new Random();

        Track track = new Track();
        track.setArtistId(randomGenerator.nextInt(10000));
        track.setDuration(60 * 3);

        int trackNumber = randomGenerator.nextInt(15);
        track.setTitle("Track no: " + trackNumber);

        return track;
    }
}*/
