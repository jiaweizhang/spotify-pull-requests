package data.data_accessors;

import data.Contributor;

import java.util.List;
import java.util.stream.Collectors;

import static db.tables.Contributors.CONTRIBUTORS;

public class ContributorAccessor extends Accessor {

    public ContributorAccessor() {
        super();
    }

    public int addContributor(Contributor contributor) {
        return myQuery.insertInto(CONTRIBUTORS, CONTRIBUTORS.PLAYLIST_ID, CONTRIBUTORS.PLAYLIST_PR_ID, CONTRIBUTORS.SPOTIFY_ID)
                .values(contributor.playlistId, contributor.playlistPrId, contributor.spotifyId).execute();
    }

    public List<Contributor> getContributors(String playlistId) {
        return myQuery.select().from(CONTRIBUTORS).where(CONTRIBUTORS.PLAYLIST_ID.equal(playlistId)).fetch()
                .stream().map(record -> new Contributor(
                        record.getValue(CONTRIBUTORS.PLAYLIST_ID),
                        record.getValue(CONTRIBUTORS.PLAYLIST_PR_ID),
                        record.getValue(CONTRIBUTORS.SPOTIFY_ID)
                )).collect(Collectors.toList());
    }
}
