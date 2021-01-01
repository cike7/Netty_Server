package com.zhu.service;

import io.netty.channel.Channel;

import java.util.HashMap;

public class Room {
    public Integer roomId;
    public HashMap<Integer, Channel> players = new HashMap<>();
}
