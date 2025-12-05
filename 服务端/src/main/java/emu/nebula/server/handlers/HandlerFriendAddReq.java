package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.FriendAdd.FriendAddReq;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.friend_add_req)
public class HandlerFriendAddReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = FriendAddReq.parseFrom(message);
        int uid = (int) req.getUId();
        
        // Send friend request
        boolean success = session.getPlayer().getFriendList().sendFriendRequest(uid);
        
        // Encode and send
        return session.encodeMsg(success ? NetMsgId.friend_add_succeed_ack : NetMsgId.friend_add_failed_ack);
    }

}
