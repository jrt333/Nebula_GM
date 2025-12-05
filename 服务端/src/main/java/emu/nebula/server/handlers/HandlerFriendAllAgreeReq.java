package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.FriendAllAgree.FriendAllAgreeResp;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.friend_all_agree_req)
public class HandlerFriendAllAgreeReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Agree to all friend requests
        var results = session.getPlayer().getFriendList().acceptAll();
        
        // Scuffed way of getting friend data
        var proto = session.getPlayer().getFriendList().getCachedProto();
        
        // Build response
        var rsp = FriendAllAgreeResp.newInstance();
        
        for (var f : results) {
            rsp.addFriends(f.getFriendProto());
        }
        
        for (var i : proto.getInvites()) {
            rsp.addInvites(i);
        }
        
        // Encode and send
        return session.encodeMsg(NetMsgId.friend_all_agree_succeed_ack, rsp);
    }

}
