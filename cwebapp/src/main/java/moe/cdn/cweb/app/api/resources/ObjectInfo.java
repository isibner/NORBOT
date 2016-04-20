package moe.cdn.cweb.app.api.resources;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.ByteString;
import moe.cdn.cweb.SecurityProtos;
import moe.cdn.cweb.TorrentTrustProtos;
import moe.cdn.cweb.TrustApi;
import moe.cdn.cweb.app.api.CwebApiEndPoint;
import moe.cdn.cweb.app.dto.TrustRating;

import javax.ws.rs.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * @author davix
 */
@Path("object/{hash}")
public class ObjectInfo extends CwebApiEndPoint {
    private static final String DEFAULT_CONTENT_PROPERTY = "appraisal";

    private static ByteString parseHash(String hash) throws BadRequestException {
        return ByteString.copyFrom(new BigInteger(hash, 16).toByteArray());
    }

    @GET
    @Path("{algo}")
    public TrustRating getTrustRating(@PathParam("hash") String hash,
                                      @PathParam("algo") String algo) throws ExecutionException,
            InterruptedException {
        // TODO: getTrustRating
        Optional<TorrentTrustProtos.User> user = getCwebIdentityApi().getUserIdentity().get();

        TrustApi.TrustMetric trustMetric;
        try {
            trustMetric = TrustApi.TrustMetric.valueOf(algo);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Unknown trust metric: " + algo, e);
        }
//        getCwebTrustApi().trustForObject(user, TorrentTrustProtos.Vote.Assertion.newBuilder().setContentProperty(DEFAULT_CONTENT_PROPERTY).build(),parseHash(hash), trustMetric);
        return new TrustRating(new Random().nextDouble(), "rand");
    }

    @POST
    @Path("up")
    public boolean upVote(@PathParam("hash") String hash) throws SignatureException,
            InvalidKeyException, ExecutionException, InterruptedException {
        getCwebVoteApi()
                .castVote(TorrentTrustProtos.Vote.newBuilder()
                        .addAssertion(TorrentTrustProtos.Vote.Assertion.newBuilder()
                                .setContentProperty(DEFAULT_CONTENT_PROPERTY)
                                .setRating(TorrentTrustProtos.Vote.Assertion.Rating.GOOD))
                        .setContentHash(SecurityProtos.Hash.newBuilder()
                                .setAlgorithm(SecurityProtos.Hash.HashAlgorithm.SHA_1)
                                .setHashValue(parseHash(hash)))
                        .build())
                .get();
        return true;
    }

    @POST
    @Path("down")
    public boolean downVote(@PathParam("hash") String hash) throws SignatureException,
            InvalidKeyException, ExecutionException, InterruptedException {
        getCwebVoteApi()
                .castVote(TorrentTrustProtos.Vote.newBuilder()
                        .addAssertion(TorrentTrustProtos.Vote.Assertion.newBuilder()
                                .setContentProperty(DEFAULT_CONTENT_PROPERTY)
                                .setRating(TorrentTrustProtos.Vote.Assertion.Rating.BAD))
                        .setContentHash(SecurityProtos.Hash.newBuilder()
                                .setAlgorithm(SecurityProtos.Hash.HashAlgorithm.SHA_1)
                                .setHashValue(parseHash(hash)))
                        .build())
                .get();
        return true;
    }
}
