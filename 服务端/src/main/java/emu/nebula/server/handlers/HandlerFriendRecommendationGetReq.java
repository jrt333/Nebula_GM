package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.FriendRecommendationGet.FriendRecommendationGetResp;
import emu.nebula.net.HandlerId;
import emu.nebula.Nebula;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.friend_recommendation_get_req)
public class HandlerFriendRecommendationGetReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Build response
        var rsp = FriendRecommendationGetResp.newInstance();
        
        // Get players
        var players = Nebula.getGameContext().getPlayerModule().getRandomPlayerList(session.getPlayer());
        
        for (var player : players) {
            rsp.addFriends(player.getFriendProto());
        }
        
        // Encode and send
        return session.encodeMsg(NetMsgId.friend_recommendation_get_succeed_ack, rsp);
    }

}
