package com.tidal.refactoring.playlist.dao;

import com.tidal.refactoring.playlist.data.PlayList;

/**
 * Created by ben on 05-02-2017.
 */
//created interface to define dao type. Type should be refereed by Interface
public interface PlaylistDaoBean {
    PlayList getPlaylistByUUID(String uuid);
}
