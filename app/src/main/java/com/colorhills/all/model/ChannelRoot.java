package com.colorhills.all.model;

import java.io.Serializable;

public class ChannelRoot implements Serializable {
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
