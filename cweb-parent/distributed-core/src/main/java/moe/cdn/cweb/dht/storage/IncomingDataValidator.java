package moe.cdn.cweb.dht.storage;

import javax.inject.Inject;

import moe.cdn.cweb.TorrentTrustProtos;
import moe.cdn.cweb.TorrentTrustProtos.SignedVote;
import moe.cdn.cweb.dht.annotations.UserDomain;
import moe.cdn.cweb.dht.annotations.VoteDomain;
import moe.cdn.cweb.dht.security.CwebSignatureValidationService;
import net.tomp2p.peers.Number160;
import net.tomp2p.storage.Data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.protobuf.InvalidProtocolBufferException;

class IncomingDataValidator {
    private static final Logger logger = LogManager.getLogger();
    private final Number160 voteDomainKey;
    private final Number160 userDomainKey;

    private final CwebSignatureValidationService cwebSignatureValidationService;

    @Inject
    public IncomingDataValidator(@VoteDomain Number160 voteDomainKey,
                                 @UserDomain Number160 userDomainKey,
                                 CwebSignatureValidationService cwebSignatureValidationService) {
        this.voteDomainKey = voteDomainKey;
        this.userDomainKey = userDomainKey;
        this.cwebSignatureValidationService = cwebSignatureValidationService;
    }


    public boolean validate(Number160 domainKey, Data data) {
    	// TODO: What is a sane way to log validation actions?
        if (voteDomainKey.equals(domainKey)) {
            return validateVoteRawData(data);
        }
        if (userDomainKey.equals(domainKey)) {
            return validateUserRawData(data);
        }
        return false;
    }

    private boolean validateVoteRawData(Data data) {
        try {
            SignedVote signedVote = SignedVote.PARSER.parseFrom(data.toBytes());
            return cwebSignatureValidationService.validateVote(signedVote);
        } catch (InvalidProtocolBufferException e) {
            return false;
        }
    }

    private boolean validateUserRawData(Data data) {
        try {
            TorrentTrustProtos.SignedUser signedUser = TorrentTrustProtos.SignedUser.PARSER
                    .parseFrom(data.toBytes());
            return cwebSignatureValidationService.validateUser(signedUser);
        } catch (InvalidProtocolBufferException e) {
            return false;
        }
    }
}
