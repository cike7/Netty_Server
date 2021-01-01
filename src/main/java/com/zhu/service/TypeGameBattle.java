package com.zhu.service;

import com.zhu.Info.DataPackage;
import com.zhu.Info.GameType;
import com.zhu.server.ServerHandler;

public class TypeGameBattle  implements StreamData {

    @Override
    public void receiveData(ServerHandler serverHandler, DataPackage dataPackage) {

        if(dataPackage.requestType == GameType.game_battle){

        }
    }
}
