package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.FriendDelete.FriendDeleteReq;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.friend_delete_req)
public class HandlerFriendDeleteReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = FriendDeleteReq.parseFrom(message);
        
        // Delete friend
        boolean success = session.getPlayer().getFriendList().deleteFriend((int) req.getUId());
        
        // Encode and send
        return session.encodeMsg(success ? NetMsgId.friend_delete_succeed_ack : NetMsgId.friend_delete_failed_ack);
    }

}