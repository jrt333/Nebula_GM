package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.FriendUidSearch.FriendUIdSearchReq;
import emu.nebula.proto.FriendUidSearch.FriendUIdSearchResp;
import emu.nebula.net.HandlerId;
import emu.nebula.Nebula;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.friend_uid_search_req)
public class HandlerFriendUidSearchReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = FriendUIdSearchReq.parseFrom(message);
        int uid = (int) req.getId();
        
        // Get target player
        var target = Nebula.getGameContext().getPlayerModule().getPlayer(uid);
        
        if (target == null) {
            return session.encodeMsg(NetMsgId.friend_uid_search_failed_ack);
        }
        
        // Build response
        var rsp = FriendUIdSearchResp.newInstance()
                .setFriend(target.getFriendProto());
        
        // Encode and send
        return session.encodeMsg(NetMsgId.friend_uid_search_succeed_ack, rsp);
    }

}
