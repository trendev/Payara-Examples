/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.payaramicro.event.receiver;

import fish.payara.cluster.Clustered;
import fish.payara.cluster.DistributedLockType;
import fish.payara.examples.payaramicro.eventdata.CustomMessage;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;

/**
 *
 * @author jsie
 */
@Clustered(callPostConstructOnAttach = false, callPreDestoyOnDetach = false,
        lock = DistributedLockType.LOCK, keyName = "messageTracker")
@Singleton
public class MessageTracker implements Serializable {

    private Collection<CustomMessage> messagesReceived = new HashSet<>();

    private String uuid;

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        Logger.getLogger(this.getClass().getName()).info(
                "MessageTracker initialised");
        Logger.getLogger(this.getClass().getName()).warning(
                "##################### STARTING " + uuid
                + "  - CLUSTER #####################");
    }

    @PreDestroy
    public void clean() {
        Logger.getLogger(this.getClass().getName()).warning(
                "STOPING " + uuid + "  - CLUSTER");
    }

    public Collection<CustomMessage> getMessagesReceived() {
        return messagesReceived;
    }

    public void add(CustomMessage message) {
        Logger.getLogger(this.getClass().getName()).info(
                "ADDING " + message.getMessage());
        this.messagesReceived.add(message);
    }

}
