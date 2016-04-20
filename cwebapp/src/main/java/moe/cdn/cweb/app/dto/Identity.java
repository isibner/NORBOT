package moe.cdn.cweb.app.dto;

import moe.cdn.cweb.app.util.Base64CwebIdAdapter;
import moe.cdn.cweb.security.CwebId;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author davix
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Identity {
    @XmlJavaTypeAdapter(Base64CwebIdAdapter.class)
    private CwebId id;
    private String handle;
    private KeyHash keyPair;

    public Identity() {
    }

    public Identity(CwebId id, String handle, KeyHash keyPair) {
        this.id = id;
        this.handle = handle;
        this.keyPair = keyPair;
    }

    public CwebId getId() {
        return id;
    }

    public void setId(CwebId id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public KeyHash getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyHash keyPair) {
        this.keyPair = keyPair;
    }
}