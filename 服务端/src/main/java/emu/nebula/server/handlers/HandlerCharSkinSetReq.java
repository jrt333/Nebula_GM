package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.CharSkinSet.CharSkinSetReq;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.char_skin_set_req)
public class HandlerCharSkinSetReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = CharSkinSetReq.parseFrom(message);
        
        // Get character
        var character = session.getPlayer().getCharacters().getCharacterById(req.getCharId());
        if (character == null) {
            return session.encodeMsg(NetMsgId.char_skin_set_failed_ack);
        }
        
        // Set skin
        var result = character.setSkin(req.getSkinId());
        if (!result) {
            return session.encodeMsg(NetMsgId.char_skin_set_failed_ack);
        }
        
        // Encode and send
        return session.encodeMsg(NetMsgId.char_skin_set_succeed_ack);
    }

}
