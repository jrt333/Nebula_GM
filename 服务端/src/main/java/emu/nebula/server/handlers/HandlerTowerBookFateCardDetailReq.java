package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.TowerBookFateCardDetail.TowerBookFateCardDetailResp;
import emu.nebula.net.HandlerId;
import emu.nebula.data.GameData;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.tower_book_fate_card_detail_req)
public class HandlerTowerBookFateCardDetailReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Build response
        var rsp = TowerBookFateCardDetailResp.newInstance();
        
        for (int card : session.getPlayer().getProgress().getFateCards()) {
            rsp.addCards(card);
        }
        
        for (var quest : GameData.getStarTowerBookFateCardQuestDataTable()) {
            rsp.addQuests(quest.getId());
        }
        
        // Encode and send
        return session.encodeMsg(NetMsgId.tower_book_fate_card_detail_succeed_ack, rsp);
    }

}
