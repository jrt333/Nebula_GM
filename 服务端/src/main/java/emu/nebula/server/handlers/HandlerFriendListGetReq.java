package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.friend_list_get_req)
public class HandlerFriendListGetReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Get cached friends list proto
        var proto = session.getPlayer().getFriendList().toProto();
        
        // Clone response from cached proto
        var rsp = proto.clone();
        
        // Encode and send
        return session.encodeMsg(NetMsgId.friend_list_get_succeed_ack, rsp);
    }

}
