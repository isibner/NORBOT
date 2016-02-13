package moe.cdn.cweb.vote;

import java.util.Collection;

import moe.cdn.cweb.SecurityProtos.Hash;
import moe.cdn.cweb.TorrentTrustProtos.SignedVote;

public interface CwebVoteService {
    /**
     * Gets a collection of all votes for a certain object hash
     * 
     * @param object
     * @return
     */
    Collection<SignedVote> getAllVotes(Hash object);

    /**
     * Cast a vote for a certain object
     * 
     * @param vote
     * @return boolean indicator indicating whether the vote was successfully
     *         cast
     */
    boolean castVote(SignedVote vote);
}
