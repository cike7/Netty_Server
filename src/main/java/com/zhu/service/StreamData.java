package com.zhu.service;

import com.zhu.Info.DataPackage;
import com.zhu.server.ServerHandler;

public interface StreamData {
    /**
     * 接收数据
     * @param dataPackage
     */
    void receiveData(ServerHandler serverHandler, DataPackage dataPackage);


}
