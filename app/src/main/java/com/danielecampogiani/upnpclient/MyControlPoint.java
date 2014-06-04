package com.danielecampogiani.upnpclient;

import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.device.NotifyListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;

/**
 * Created by danielecampogiani on 04/06/14.
 */
public class MyControlPoint extends ControlPoint implements NotifyListener, SearchResponseListener {

    public MyControlPoint() {
        addNotifyListener(this);
        addSearchResponseListener(this);
    }


    @Override
    public void deviceNotifyReceived(SSDPPacket ssdpPacket) {

    }

    @Override
    public void deviceSearchResponseReceived(SSDPPacket ssdpPacket) {

    }
}
