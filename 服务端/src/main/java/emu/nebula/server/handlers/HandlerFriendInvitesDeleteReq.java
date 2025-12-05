package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.FriendInvitesDelete.FriendInvitesDeleteReq;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.friend_invites_delete_req)
public class HandlerFriendInvitesDeleteReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = FriendInvitesDeleteReq.parseFrom(message);
        
        // Delete all invites
        for (long uid : req.getMutableUIds()) {
            session.getPlayer().getFriendList().handleFriendRequest((int) uid, false);
        }
        
        // Encode and send
        return session.encodeMsg(NetMsgId.friend_invites_delete_succeed_ack);
    }

}
