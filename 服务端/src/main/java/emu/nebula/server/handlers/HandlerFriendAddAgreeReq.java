package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.FriendAddAgree.FriendAddAgreeReq;
import emu.nebula.proto.FriendAddAgree.FriendAddAgreeResp;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.friend_add_agree_req)
public class HandlerFriendAddAgreeReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = FriendAddAgreeReq.parseFrom(message);
        
        // Handle friend request
        var target = session.getPlayer().getFriendList().handleFriendRequest((int) req.getUId(), true);
        
        if (target == null) {
            return session.encodeMsg(NetMsgId.friend_add_agree_failed_ack);
        }
        
        // Build response
        var rsp = FriendAddAgreeResp.newInstance()
                .setFriend(target.getFriendProto());
        
        // Encode and send
        return session.encodeMsg(NetMsgId.friend_add_agree_succeed_ack, rsp);
    }

}
