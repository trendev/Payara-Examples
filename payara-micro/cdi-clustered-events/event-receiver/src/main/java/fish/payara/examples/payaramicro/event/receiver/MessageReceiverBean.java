/*

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright (c) 2015 C2B2 Consulting Limited. All rights reserved.

 The contents of this file are subject to the terms of the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License.  You can
 obtain a copy of the License at
 https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 or packager/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.

 When distributing the software, include this License Header Notice in each
 file and include the License file at packager/legal/LICENSE.txt.
 */
package fish.payara.examples.payaramicro.event.receiver;

import fish.payara.examples.payaramicro.eventdata.CustomMessage;
import fish.payara.micro.cdi.Inbound;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

/**
 * An Application Scoped CDI Bean that receives clustered CDI events
 *
 * @author steve
 */
@Singleton
public class MessageReceiverBean {

    @Inject
    MessageTracker messageTracker;

    @PostConstruct
    public void init() {
        Logger.getLogger(this.getClass().getName()).info(
                "MessageReceiverBean initialized");
    }

    /**
     * Observer method that receives events Inbound from the cluster to the
     * server You must use the @Inbound annotation to receive cluster events
     *
     * @param event
     */
    public void observe(
            @Observes @Inbound(eventName = "NewCustomMessage") /*@NewCustomMessage*/ String event) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                "MessageReceiverBean Received Event {0}", event);
        messageTracker.add(JsonbBuilder.create().fromJson(event,
                CustomMessage.class));
    }

    public Collection<CustomMessage> getMessagesReceived() {
        return messageTracker.getMessagesReceived();
    }

}
