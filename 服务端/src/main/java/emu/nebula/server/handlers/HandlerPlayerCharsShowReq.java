package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.PlayerCharsShow.PlayerCharsShowReq;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.player_chars_show_req)
public class HandlerPlayerCharsShowReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse req
        var req = PlayerCharsShowReq.parseFrom(message);
        
        // Set
        boolean success = session.getPlayer().setShowChars(req.getCharIds());
        
        // Encode and send
        return session.encodeMsg(success ? NetMsgId.player_chars_show_succeed_ack : NetMsgId.player_chars_show_failed_ack);
    }

}
